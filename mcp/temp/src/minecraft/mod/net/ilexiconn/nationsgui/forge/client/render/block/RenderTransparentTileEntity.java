package net.ilexiconn.nationsgui.forge.client.render.block;

import fr.zeamateis.nationsglory.client.renders.RenderTransparentBlock;
import fr.zeamateis.nationsglory.common.tileEntity.TileEntityTransparent;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class RenderTransparentTileEntity extends TileEntitySpecialRenderer {

   private RenderBlocks blockRenderer;


   public void func_76894_a(TileEntity tileEntity, double par2, double par4, double par6, float par8) {
      ItemStack itemStack = Minecraft.func_71410_x().field_71439_g.func_70694_bm();
      if(itemStack != null && itemStack.func_77973_b() instanceof ItemBlock && Block.field_71973_m[((ItemBlock)itemStack.func_77973_b()).func_77883_f()].func_71857_b() == RenderTransparentBlock.renderID) {
         Tessellator tessellator = Tessellator.field_78398_a;
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

         tessellator.func_78382_b();
         tessellator.func_78373_b((double)((float)par2 - (float)tileEntity.field_70329_l), (double)((float)par4 - (float)tileEntity.field_70330_m), (double)((float)par6 - (float)tileEntity.field_70327_n));
         tessellator.func_78369_a(1.0F, 0.0F, 0.0F, 0.5F);
         TileEntityTransparent.rendering = true;
         this.blockRenderer.func_78612_b(tileEntity.field_70324_q, tileEntity.field_70329_l, tileEntity.field_70330_m, tileEntity.field_70327_n);
         TileEntityTransparent.rendering = false;
         tessellator.func_78373_b(0.0D, 0.0D, 0.0D);
         tessellator.func_78369_a(1.0F, 0.0F, 0.0F, 0.5F);
         tessellator.func_78381_a();
         GL11.glEnable(3553);
         RenderHelper.func_74519_b();
      }

   }

   public void func_76896_a(World par1World) {
      this.blockRenderer = new RenderBlocks(par1World);
   }
}
