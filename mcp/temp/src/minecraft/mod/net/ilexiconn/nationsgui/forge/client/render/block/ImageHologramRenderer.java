package net.ilexiconn.nationsgui.forge.client.render.block;

import fr.zeamateis.nationsglory.client.renders.RenderTransparentBlock;
import fr.zeamateis.nationsglory.common.tileEntity.TileEntityTransparent;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.block.entity.ImageHologramBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class ImageHologramRenderer extends TileEntitySpecialRenderer {

   private RenderBlocks blockRenderer;


   public void func_76894_a(TileEntity t, double x, double y, double z, float arg4) {
      ItemStack itemStack = Minecraft.func_71410_x().field_71439_g.func_70694_bm();
      if(itemStack != null && itemStack.func_77973_b() instanceof ItemBlock && Block.field_71973_m[((ItemBlock)itemStack.func_77973_b()).func_77883_f()].func_71857_b() == RenderTransparentBlock.renderID) {
         Tessellator tile = Tessellator.field_78398_a;
         this.func_110628_a(TextureMap.field_110575_b);
         RenderHelper.func_74518_a();
         GL11.glBlendFunc(770, 771);
         GL11.glEnable(3042);
         GL11.glDisable(2884);
         GL11.glDisable(3553);
         if(Minecraft.func_71379_u()) {
            GL11.glShadeModel(7425);
         } else {
            GL11.glShadeModel(7424);
         }

         tile.func_78382_b();
         tile.func_78373_b((double)((float)x - (float)t.field_70329_l), (double)((float)y - (float)t.field_70330_m), (double)((float)z - (float)t.field_70327_n));
         tile.func_78369_a(1.0F, 0.0F, 0.0F, 0.5F);
         TileEntityTransparent.rendering = true;
         this.blockRenderer.func_78612_b(t.field_70324_q, t.field_70329_l, t.field_70330_m, t.field_70327_n);
         TileEntityTransparent.rendering = false;
         tile.func_78373_b(0.0D, 0.0D, 0.0D);
         tile.func_78369_a(1.0F, 0.0F, 0.0F, 0.5F);
         tile.func_78381_a();
         GL11.glEnable(3553);
         RenderHelper.func_74519_b();
      }

      if(t instanceof ImageHologramBlockEntity) {
         ImageHologramBlockEntity tile1 = (ImageHologramBlockEntity)t;
         String url = tile1.getURL();
         if(!url.isEmpty()) {
            GL11.glPushMatrix();
            GL11.glTranslated(x + 0.5D, y + 0.1D, z + 0.5D);
            GL11.glPushMatrix();
            float scale = 0.026666673F;
            GL11.glNormal3f(0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-RenderManager.field_78727_a.field_78735_i, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(RenderManager.field_78727_a.field_78732_j, 1.0F, 0.0F, 0.0F);
            GL11.glScalef(-scale, -scale, scale);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glDisable(2896);
            ModernGui.drawScaledModalRectWithCustomSizedRemoteTexture(-((int)((float)tile1.imgWidth * ((float)tile1.size / 100.0F))) / 2, -((int)((float)tile1.imgHeight * ((float)tile1.size / 100.0F))) / 2, 0, 0, tile1.imgWidth, tile1.imgHeight, (int)((float)tile1.imgWidth * ((float)tile1.size / 100.0F)), (int)((float)tile1.imgHeight * ((float)tile1.size / 100.0F)), (float)tile1.imgWidth, (float)tile1.imgHeight, false, url);
            GL11.glDisable(3042);
            GL11.glEnable(2896);
            GL11.glPopMatrix();
            GL11.glPopMatrix();
         }
      }

   }

   public void func_76896_a(World par1World) {
      this.blockRenderer = new RenderBlocks(par1World);
   }
}
