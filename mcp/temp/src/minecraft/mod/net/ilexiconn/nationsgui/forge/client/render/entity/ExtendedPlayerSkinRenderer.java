package net.ilexiconn.nationsgui.forge.client.render.entity;

import cpw.mods.fml.relauncher.ReflectionHelper;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.cosmetic.CosmeticCategoryGUI;
import net.ilexiconn.nationsgui.forge.client.itemskin.BodyPartSkin;
import net.ilexiconn.nationsgui.forge.client.itemskin.SkinType;
import net.ilexiconn.nationsgui.forge.client.model.entity.CosmeticModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent.Pre;
import net.minecraftforge.client.event.RenderPlayerEvent.SetArmorModel;
import net.minecraftforge.event.ForgeSubscribe;

public abstract class ExtendedPlayerSkinRenderer {

   private final SkinType skinType;
   private final int slotIndex;
   private boolean renderDone = false;


   public ExtendedPlayerSkinRenderer(SkinType skinType, int slotIndex) {
      this.skinType = skinType;
      this.slotIndex = slotIndex;
   }

   @ForgeSubscribe
   public void onPlayerRenderer(Pre event) {
      this.renderDone = false;
      if(ClientProxy.clientConfig.render3DSkins) {
         ModelBiped playerModel = (ModelBiped)ReflectionHelper.getPrivateValue(RenderPlayer.class, event.renderer, new String[]{"modelBipedMain", "field_77109_a"});
         ModelRenderer bodyPart = this.getBodyPart(playerModel);
         Minecraft minecraft = Minecraft.func_71410_x();
         if(event.entity == minecraft.field_71439_g) {
            if(Minecraft.func_71410_x().field_71474_y.field_74320_O == 0 && !(Minecraft.func_71410_x().field_71462_r instanceof CosmeticCategoryGUI)) {
               return;
            }
         } else if(event.entityPlayer.func_98034_c(Minecraft.func_71410_x().field_71439_g)) {
            return;
         }

         List bippedSkins = ClientProxy.SKIN_MANAGER.getPlayerActiveSkins(event.entityPlayer.field_71092_bJ, this.skinType);
         BodyPartSkin modelSkin = bippedSkins.size() > 0?(BodyPartSkin)bippedSkins.get(0):null;
         if(modelSkin != null) {
            CosmeticModel model = modelSkin.getModel();
            if(model.isReady()) {
               model.updateModel(modelSkin.getTransform("entity"));
               bodyPart.func_78792_a(model.getModel());
               this.renderDone = true;
            }

         }
      }
   }

   @ForgeSubscribe
   public void onArmorModel(SetArmorModel event) {
      if(event.slot == this.slotIndex && this.renderDone) {
         event.result = 0;
      }

   }

   protected abstract ModelRenderer getBodyPart(ModelBiped var1);
}
