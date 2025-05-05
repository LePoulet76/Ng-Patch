package net.ilexiconn.nationsgui.forge.client.model.entity;

import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import micdoodle8.mods.galacticraft.core.client.model.GCCoreModelPlayer;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.itemskin.AbstractSkin;
import net.ilexiconn.nationsgui.forge.client.itemskin.HandSkin;
import net.ilexiconn.nationsgui.forge.client.itemskin.SkinType;
import net.ilexiconn.nationsgui.forge.client.render.entity.SkinReseter;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class PlayerModelGC extends GCCoreModelPlayer {

   public ModelRenderer bipedLeftArmwear;
   public ModelRenderer bipedRightArmwear;
   public ModelRenderer bipedLeftLegwear;
   public ModelRenderer bipedRightLegwear;
   public ModelRenderer bipedBodyWear;


   public PlayerModelGC(float scale) {
      super(scale);
      this.field_78090_t = 64;
      this.field_78089_u = 64;
      this.field_78113_g = new ModelRenderer(this, 32, 48);
      this.field_78113_g.func_78789_a(-1.0F, -2.0F, -2.0F, 4, 12, 4);
      this.field_78113_g.func_78793_a(5.0F, 2.0F, 0.0F);
      this.field_78124_i = new ModelRenderer(this, 16, 48);
      this.field_78124_i.func_78789_a(-2.0F, 0.0F, -2.0F, 4, 12, 4);
      this.field_78124_i.func_78793_a(1.9F, 12.0F, 0.0F);
      this.field_78122_k = new ModelRenderer(this, 0, 0);
      this.field_78122_k.func_78787_b(64, 32);
      this.field_78122_k.func_78789_a(-5.0F, 0.0F, -1.0F, 10, 16, 1);
      this.field_78114_d = new ModelRenderer(this, 32, 0);
      this.field_78114_d.func_78790_a(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.3F);
      this.bipedLeftArmwear = new ModelRenderer(this, 48, 48);
      this.bipedLeftArmwear.func_78790_a(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.25F);
      this.bipedLeftArmwear.func_78793_a(5.0F, 2.0F, 0.0F);
      this.bipedRightArmwear = new ModelRenderer(this, 40, 32);
      this.bipedRightArmwear.func_78790_a(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.25F);
      this.bipedRightArmwear.func_78793_a(-5.0F, 2.0F, 10.0F);
      this.bipedLeftLegwear = new ModelRenderer(this, 0, 48);
      this.bipedLeftLegwear.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.25F);
      this.bipedLeftLegwear.func_78793_a(1.9F, 12.0F, 0.0F);
      this.bipedRightLegwear = new ModelRenderer(this, 0, 32);
      this.bipedRightLegwear.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.25F);
      this.bipedRightLegwear.func_78793_a(-1.9F, 12.0F, 0.0F);
      this.bipedBodyWear = new ModelRenderer(this, 16, 32);
      this.bipedBodyWear.func_78790_a(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.25F);
      this.bipedBodyWear.func_78793_a(0.0F, 0.0F, 0.0F);
      this.field_78121_j = new ModelRenderer(this, 24, 0);
      this.field_78121_j.func_78789_a(-3.0F, -6.0F, -1.0F, 6, 6, 1);
      this.field_78116_c = new ModelRenderer(this, 0, 0);
      this.field_78116_c.func_78789_a(-4.0F, -8.0F, -4.0F, 8, 8, 8);
      this.field_78116_c.func_78793_a(0.0F, 0.0F, 0.0F);
      this.field_78114_d = new ModelRenderer(this, 32, 0);
      this.field_78114_d.func_78790_a(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.5F);
      this.field_78114_d.func_78793_a(0.0F, 0.0F, 0.0F);
      this.field_78115_e = new ModelRenderer(this, 16, 16);
      this.field_78115_e.func_78789_a(-4.0F, 0.0F, -2.0F, 8, 12, 4);
      this.field_78115_e.func_78793_a(0.0F, 0.0F, 0.0F);
      this.field_78112_f = new ModelRenderer(this, 40, 16);
      this.field_78112_f.func_78789_a(-3.0F, -2.0F, -2.0F, 4, 12, 4);
      this.field_78112_f.func_78793_a(-5.0F, 2.0F, 0.0F);
      this.field_78113_g = new ModelRenderer(this, 40, 16);
      this.field_78113_g.field_78809_i = true;
      this.field_78113_g.func_78789_a(-1.0F, -2.0F, -2.0F, 4, 12, 4);
      this.field_78113_g.func_78793_a(5.0F, 2.0F, 0.0F);
      this.field_78123_h = new ModelRenderer(this, 0, 16);
      this.field_78123_h.func_78789_a(-2.0F, 0.0F, -2.0F, 4, 12, 4);
      this.field_78123_h.func_78793_a(-1.9F, 12.0F, 0.0F);
      this.field_78124_i = new ModelRenderer(this, 0, 16);
      this.field_78124_i.field_78809_i = true;
      this.field_78124_i.func_78789_a(-2.0F, 0.0F, -2.0F, 4, 12, 4);
      this.field_78124_i.func_78793_a(1.9F, 12.0F, 0.0F);
   }

   public void func_78088_a(Entity entity, float limbSwing, float limbSwingAmount, float rotation, float rotationYaw, float rotationPitch, float partialTicks) {
      super.func_78088_a(entity, limbSwing, limbSwingAmount, rotation, rotationYaw, rotationPitch, partialTicks);
      GL11.glPushMatrix();
      if(this.field_78091_s) {
         float scale = 2.0F;
         GL11.glScaled((double)(1.0F / scale), (double)(1.0F / scale), (double)(1.0F / scale));
         GL11.glTranslatef(0.0F, 24.0F * partialTicks, 0.0F);
         this.bipedLeftLegwear.func_78785_a(partialTicks);
         this.bipedRightLegwear.func_78785_a(partialTicks);
         this.bipedLeftArmwear.func_78785_a(partialTicks);
         this.bipedRightArmwear.func_78785_a(partialTicks);
         this.bipedBodyWear.func_78785_a(partialTicks);
      } else {
         if(entity.func_70093_af()) {
            GL11.glTranslatef(0.0F, 0.2F, 0.0F);
         }

         this.bipedLeftLegwear.func_78785_a(partialTicks);
         this.bipedRightLegwear.func_78785_a(partialTicks);
         this.bipedLeftArmwear.func_78785_a(partialTicks);
         this.bipedRightArmwear.func_78785_a(partialTicks);
         this.bipedBodyWear.func_78785_a(partialTicks);
      }

      GL11.glPopMatrix();
   }

   public void func_78087_a(float limbSwing, float limbSwingAmount, float rotation, float rotationYaw, float rotationPitch, float partialTicks, Entity entity) {
      super.func_78087_a(limbSwing, limbSwingAmount, rotation, rotationYaw, rotationPitch, partialTicks, entity);

      try {
         Class cl = Class.forName("fr.nationsglory.ngupgrades.client.ClientHooks");
         Method method = cl.getDeclaredMethod("setRotationAngles", new Class[]{ModelBiped.class, Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, Entity.class});
         method.invoke((Object)null, new Object[]{this, Float.valueOf(limbSwing), Float.valueOf(limbSwingAmount), Float.valueOf(rotation), Float.valueOf(rotationYaw), Float.valueOf(rotationPitch), Float.valueOf(partialTicks), entity});
      } catch (InvocationTargetException var10) {
         ;
      }

      this.copyModelAngles(this.field_78124_i, this.bipedLeftLegwear);
      this.copyModelAngles(this.field_78123_h, this.bipedRightLegwear);
      this.copyModelAngles(this.field_78113_g, this.bipedLeftArmwear);
      this.copyModelAngles(this.field_78112_f, this.bipedRightArmwear);
      this.copyModelAngles(this.field_78115_e, this.bipedBodyWear);
      this.copyModelAngles(this.field_78116_c, this.field_78114_d);
   }

   public void copyModelAngles(ModelRenderer source, ModelRenderer destination) {
      destination.field_78795_f = source.field_78795_f;
      destination.field_78796_g = source.field_78796_g;
      destination.field_78808_h = source.field_78808_h;
      destination.field_78800_c = source.field_78800_c;
      destination.field_78797_d = source.field_78797_d;
      destination.field_78798_e = source.field_78798_e;
   }

   public static void renderFirstPersonArm(RenderPlayer playerRenderer, ModelBiped modelBiped, EntityPlayer entityPlayer) {
      PlayerModelGC playerModel = (PlayerModelGC)modelBiped;
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      playerModel.field_78095_p = 0.0F;
      playerModel.func_78087_a(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, entityPlayer);
      if(playerModel.field_78112_f.field_78805_m != null) {
         playerModel.field_78112_f.field_78805_m.clear();
      }

      AbstractSkin abstractSkin = ClientProxy.SKIN_MANAGER.getUniquePlayerActiveSkins(entityPlayer.field_71092_bJ, new SkinType[]{SkinType.HANDS});
      if(abstractSkin != null && entityPlayer.func_70694_bm() == null && abstractSkin instanceof HandSkin) {
         HandSkin handSkin = (HandSkin)abstractSkin;
         handSkin.applyToBody(handSkin.getId(), playerModel);
         Method m = ReflectionHelper.findMethod(RenderPlayer.class, playerRenderer, new String[]{"getEntityTexture", "func_110775_a"}, new Class[]{Entity.class});

         try {
            ResourceLocation e = (ResourceLocation)m.invoke(playerRenderer, new Object[]{entityPlayer});
            SkinReseter reseter = new SkinReseter(playerModel, e);
            if(playerModel.field_78112_f != null) {
               playerModel.field_78112_f.func_78792_a(reseter);
            }
         } catch (InvocationTargetException var9) {
            throw new RuntimeException(var9);
         }
      }

      playerModel.field_78112_f.func_78785_a(0.0625F);
      GL11.glDisable(2884);
      playerModel.bipedRightArmwear.func_78785_a(0.0625F);
      GL11.glEnable(2884);
   }
}
