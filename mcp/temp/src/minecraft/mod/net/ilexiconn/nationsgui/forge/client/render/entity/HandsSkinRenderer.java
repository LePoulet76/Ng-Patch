package net.ilexiconn.nationsgui.forge.client.render.entity;

import cpw.mods.fml.relauncher.ReflectionHelper;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.cosmetic.CosmeticCategoryGUI;
import net.ilexiconn.nationsgui.forge.client.itemskin.HandSkin;
import net.ilexiconn.nationsgui.forge.client.itemskin.SkinType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent.Pre;
import net.minecraftforge.event.EventPriority;
import net.minecraftforge.event.ForgeSubscribe;

public class HandsSkinRenderer {

   @ForgeSubscribe(
      priority = EventPriority.LOW
   )
   public void onPlayerRenderer(Pre event) {
      if(ClientProxy.clientConfig.render3DSkins) {
         if(event.entityPlayer.func_70694_bm() == null) {
            ModelBiped playerModel = (ModelBiped)ReflectionHelper.getPrivateValue(RenderPlayer.class, event.renderer, new String[]{"modelBipedMain", "field_77109_a"});
            Minecraft minecraft = Minecraft.func_71410_x();
            if(event.entity == minecraft.field_71439_g) {
               if(Minecraft.func_71410_x().field_71474_y.field_74320_O == 0 && !(Minecraft.func_71410_x().field_71462_r instanceof CosmeticCategoryGUI)) {
                  return;
               }
            } else if(event.entityPlayer.func_98034_c(Minecraft.func_71410_x().field_71439_g)) {
               return;
            }

            List bippedSkins = ClientProxy.SKIN_MANAGER.getPlayerActiveSkins(event.entityPlayer.field_71092_bJ, SkinType.HANDS);
            HandSkin handSkin = bippedSkins.size() > 0?(HandSkin)bippedSkins.get(0):null;
            if(handSkin != null) {
               handSkin.applyToBody(handSkin.getId(), playerModel);
            }
         }
      }
   }
}
