package net.ilexiconn.nationsgui.forge.client.render.entity;

import cpw.mods.fml.relauncher.ReflectionHelper;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import net.ilexiconn.nationsgui.forge.client.render.entity.SkinReseter;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderPlayerEvent.Pre;
import net.minecraftforge.event.EventPriority;
import net.minecraftforge.event.ForgeSubscribe;

public class ClearPlayerModels {

   @ForgeSubscribe(
      priority = EventPriority.HIGHEST
   )
   public void clearPlayerModel(Pre event) {
      ModelBiped playerModel = (ModelBiped)ReflectionHelper.getPrivateValue(RenderPlayer.class, event.renderer, new String[]{"modelBipedMain", "field_77109_a"});
      Iterator iterator = playerModel.field_78092_r.iterator();

      while(iterator.hasNext()) {
         Object o = iterator.next();
         if(o instanceof SkinReseter) {
            iterator.remove();
         }
      }

      this.clearBodyPart(playerModel.field_78115_e);
      this.clearBodyPart(playerModel.field_78116_c);
      this.clearBodyPart(playerModel.field_78123_h);
      this.clearBodyPart(playerModel.field_78124_i);
      this.clearBodyPart(playerModel.field_78113_g);
      this.clearBodyPart(playerModel.field_78112_f);
   }

   @ForgeSubscribe(
      priority = EventPriority.LOWEST
   )
   public void applySkinReseters(Pre event) throws InvocationTargetException, IllegalAccessException {
      ModelBiped playerModel = (ModelBiped)ReflectionHelper.getPrivateValue(RenderPlayer.class, event.renderer, new String[]{"modelBipedMain", "field_77109_a"});
      Method m = ReflectionHelper.findMethod(RenderPlayer.class, event.renderer, new String[]{"getEntityTexture", "func_110775_a"}, new Class[]{Entity.class});
      ResourceLocation skin = (ResourceLocation)m.invoke(event.renderer, new Object[]{event.entityPlayer});
      SkinReseter reseter = new SkinReseter(playerModel, skin);
      this.applyReseter(playerModel.field_78115_e, reseter);
      this.applyReseter(playerModel.field_78116_c, reseter);
      this.applyReseter(playerModel.field_78123_h, reseter);
      this.applyReseter(playerModel.field_78124_i, reseter);
      this.applyReseter(playerModel.field_78113_g, reseter);
      this.applyReseter(playerModel.field_78112_f, reseter);
   }

   private void applyReseter(ModelRenderer bodyPart, SkinReseter reseter) {
      if(bodyPart.field_78805_m != null && !bodyPart.field_78805_m.isEmpty()) {
         bodyPart.func_78792_a(reseter);
      }

   }

   private void clearBodyPart(ModelRenderer bodyPart) {
      if(bodyPart.field_78805_m != null) {
         bodyPart.field_78805_m.clear();
      }

   }
}
