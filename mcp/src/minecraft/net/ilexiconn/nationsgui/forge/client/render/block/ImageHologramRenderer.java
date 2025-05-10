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

public class ImageHologramRenderer extends TileEntitySpecialRenderer
{
    private RenderBlocks blockRenderer;

    public void renderTileEntityAt(TileEntity t, double x, double y, double z, float arg4)
    {
        ItemStack itemStack = Minecraft.getMinecraft().thePlayer.getHeldItem();

        if (itemStack != null && itemStack.getItem() instanceof ItemBlock && Block.blocksList[((ItemBlock)itemStack.getItem()).getBlockID()].getRenderType() == RenderTransparentBlock.renderID)
        {
            Tessellator tile = Tessellator.instance;
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

            tile.startDrawingQuads();
            tile.setTranslation((double)((float)x - (float)t.xCoord), (double)((float)y - (float)t.yCoord), (double)((float)z - (float)t.zCoord));
            tile.setColorRGBA_F(1.0F, 0.0F, 0.0F, 0.5F);
            TileEntityTransparent.rendering = true;
            this.blockRenderer.renderBlockByRenderType(t.blockType, t.xCoord, t.yCoord, t.zCoord);
            TileEntityTransparent.rendering = false;
            tile.setTranslation(0.0D, 0.0D, 0.0D);
            tile.setColorRGBA_F(1.0F, 0.0F, 0.0F, 0.5F);
            tile.draw();
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            RenderHelper.enableStandardItemLighting();
        }

        if (t instanceof ImageHologramBlockEntity)
        {
            ImageHologramBlockEntity tile1 = (ImageHologramBlockEntity)t;
            String url = tile1.getURL();

            if (!url.isEmpty())
            {
                GL11.glPushMatrix();
                GL11.glTranslated(x + 0.5D, y + 0.1D, z + 0.5D);
                GL11.glPushMatrix();
                float scale = 0.026666673F;
                GL11.glNormal3f(0.0F, 1.0F, 0.0F);
                GL11.glRotatef(-RenderManager.instance.playerViewY, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(RenderManager.instance.playerViewX, 1.0F, 0.0F, 0.0F);
                GL11.glScalef(-scale, -scale, scale);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glDisable(GL11.GL_LIGHTING);
                ModernGui.drawScaledModalRectWithCustomSizedRemoteTexture(-((int)((float)tile1.imgWidth * ((float)tile1.size / 100.0F))) / 2, -((int)((float)tile1.imgHeight * ((float)tile1.size / 100.0F))) / 2, 0, 0, tile1.imgWidth, tile1.imgHeight, (int)((float)tile1.imgWidth * ((float)tile1.size / 100.0F)), (int)((float)tile1.imgHeight * ((float)tile1.size / 100.0F)), (float)tile1.imgWidth, (float)tile1.imgHeight, false, url);
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glPopMatrix();
                GL11.glPopMatrix();
            }
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
