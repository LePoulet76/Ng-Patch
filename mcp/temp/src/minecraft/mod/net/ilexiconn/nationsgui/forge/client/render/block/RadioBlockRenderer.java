package net.ilexiconn.nationsgui.forge.client.render.block;

import java.util.Iterator;
import net.ilexiconn.nationsgui.forge.client.render.item.RadioItemRenderer;
import net.ilexiconn.nationsgui.forge.server.block.entity.RadioBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import org.lwjgl.opengl.GL11;

public class RadioBlockRenderer extends TileEntitySpecialRenderer {

   public void func_76894_a(TileEntity tileEntity, double x, double y, double z, float partialTicks) {
      float rotation = (float)(tileEntity.func_70322_n() * 360) / 16.0F;
      GL11.glPushMatrix();
      GL11.glTranslated(x + 0.5D, y + 1.5D, z + 0.5D);
      GL11.glRotatef(-rotation, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
      Minecraft.func_71410_x().func_110434_K().func_110577_a(RadioItemRenderer.TEXTURE);
      RadioItemRenderer.MODEL.func_78088_a((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      GL11.glPopMatrix();
      if(RenderManager.field_85095_o) {
         Iterator var10 = ((RadioBlockEntity)tileEntity).speakers.iterator();

         while(var10.hasNext()) {
            ChunkCoordinates coordinates = (ChunkCoordinates)var10.next();
            GL11.glPushMatrix();
            GL11.glTranslated(x, y, z);
            GL11.glColor4d(1.0D, 1.0D, 1.0D, 1.0D);
            GL11.glDisable(3553);
            GL11.glDisable(2896);
            GL11.glDisable(2884);
            GL11.glDisable(3042);
            Tessellator tessellator = Tessellator.field_78398_a;
            tessellator.func_78371_b(1);
            tessellator.func_78377_a(0.5D, 0.5D, 0.5D);
            tessellator.func_78377_a((double)((float)(coordinates.field_71574_a - tileEntity.field_70329_l) + 0.5F), (double)((float)(coordinates.field_71572_b - tileEntity.field_70330_m) + 0.5F), (double)((float)(coordinates.field_71573_c - tileEntity.field_70327_n) + 0.5F));
            tessellator.func_78381_a();
            GL11.glEnable(3553);
            GL11.glEnable(2896);
            GL11.glEnable(2884);
            GL11.glEnable(3042);
            GL11.glPopMatrix();
         }
      }

   }
}
