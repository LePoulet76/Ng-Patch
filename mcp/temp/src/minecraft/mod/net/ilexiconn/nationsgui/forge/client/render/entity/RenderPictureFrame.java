package net.ilexiconn.nationsgui.forge.client.render.entity;

import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.server.entity.EntityPictureFrame;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderPictureFrame extends RenderEntity {

   private static final ResourceLocation TEXTURE = new ResourceLocation("nationsgui", "textures/entity/pictureframeoff.png");


   private void renderEntityPicture(EntityPictureFrame par1EntityPainting, int par2, int par3) {
      Tessellator tessellator = Tessellator.field_78398_a;
      if((par1EntityPainting.getDownloadImageSkin() == null || !par1EntityPainting.getDownloadImageSkin().isTextureUploaded()) && ClientProxy.clientConfig.displayPictureFrame) {
         GL11.glDisable(3553);
         tessellator.func_78382_b();
         tessellator.func_78369_a(1.0F, 1.0F, 1.0F, 1.0F);
         tessellator.func_78375_b(1.0F, 0.0F, 0.0F);
         tessellator.func_78377_a((double)(par2 / 2), (double)(par3 / 2), 0.0D);
         tessellator.func_78377_a((double)(par2 / 2), (double)(-par3 / 2), 0.0D);
         tessellator.func_78377_a((double)(-par2 / 2), (double)(-par3 / 2), 0.0D);
         tessellator.func_78377_a((double)(-par2 / 2), (double)(par3 / 2), 0.0D);
         tessellator.func_78381_a();
         GL11.glEnable(3553);
      } else {
         this.func_110776_a(ClientProxy.clientConfig.displayPictureFrame?par1EntityPainting.getLocationSkin():TEXTURE);
         tessellator.func_78382_b();
         tessellator.func_78375_b(1.0F, 0.0F, 0.0F);
         tessellator.func_78374_a((double)(par2 / 2), (double)(par3 / 2), 0.0D, 0.0D, 0.0D);
         tessellator.func_78374_a((double)(par2 / 2), (double)(-par3 / 2), 0.0D, 0.0D, 1.0D);
         tessellator.func_78374_a((double)(-par2 / 2), (double)(-par3 / 2), 0.0D, 1.0D, 1.0D);
         tessellator.func_78374_a((double)(-par2 / 2), (double)(par3 / 2), 0.0D, 1.0D, 0.0D);
         tessellator.func_78381_a();
      }

   }

   public void func_76986_a(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
      GL11.glPushMatrix();
      GL11.glTranslated(par2, par4, par6);
      GL11.glRotatef(par8, 0.0F, 1.0F, 0.0F);
      GL11.glEnable('\u803a');
      float f2 = 0.0625F;
      GL11.glScalef(f2, f2, f2);
      EntityPictureFrame entityPictureFrame = (EntityPictureFrame)par1Entity;
      this.renderEntityPicture(entityPictureFrame, entityPictureFrame.getWidthPixels(), entityPictureFrame.getHeightPixels());
      GL11.glDisable('\u803a');
      GL11.glPopMatrix();
   }

   protected ResourceLocation func_110775_a(Entity par1Entity) {
      return TEXTURE;
   }

}
