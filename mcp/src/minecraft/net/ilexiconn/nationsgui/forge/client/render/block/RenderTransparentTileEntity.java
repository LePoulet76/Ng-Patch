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

public class RenderTransparentTileEntity extends TileEntitySpecialRenderer
{
    private RenderBlocks blockRenderer;

    public void renderTileEntityAt(TileEntity tileEntity, double par2, double par4, double par6, float par8)
    {
        ItemStack itemStack = Minecraft.getMinecraft().thePlayer.getHeldItem();

        if (itemStack != null && itemStack.getItem() instanceof ItemBlock && Block.blocksList[((ItemBlock)itemStack.getItem()).getBlockID()].getRenderType() == RenderTransparentBlock.renderID)
        {
            Tessellator tessellator = Tessellator.instance;
            this.bindTexture(TextureMap.locationBlocksTexture);
            RenderHelper.disableStandardItemLighting();
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_CULL_FACE);
            GL11.glDisable(GL11.GL_TEXTURE_2D);

            if (Minecraft.isAmbientOcclusionEnabled())
            {
                GL11.glShadeModel(GL11.GL_SMOOTH);
            }
            else
            {
                GL11.glShadeModel(GL11.GL_FLAT);
            }

            tessellator.startDrawingQuads();
            tessellator.setTranslation((double)((float)par2 - (float)tileEntity.xCoord), (double)((float)par4 - (float)tileEntity.yCoord), (double)((float)par6 - (float)tileEntity.zCoord));
            tessellator.setColorRGBA_F(1.0F, 0.0F, 0.0F, 0.5F);
            TileEntityTransparent.rendering = true;
            this.blockRenderer.renderBlockByRenderType(tileEntity.blockType, tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
            TileEntityTransparent.rendering = false;
            tessellator.setTranslation(0.0D, 0.0D, 0.0D);
            tessellator.setColorRGBA_F(1.0F, 0.0F, 0.0F, 0.5F);
            tessellator.draw();
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            RenderHelper.enableStandardItemLighting();
        }
    }

    /**
     * Called when the ingame world being rendered changes (e.g. on world -> nether travel) due to using one renderer
     * per tile entity type, rather than instance
     */
    public void onWorldChange(World par1World)
    {
        this.blockRenderer = new RenderBlocks(par1World);
    }
}
