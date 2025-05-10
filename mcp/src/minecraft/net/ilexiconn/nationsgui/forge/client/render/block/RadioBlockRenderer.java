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

public class RadioBlockRenderer extends TileEntitySpecialRenderer
{
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTicks)
    {
        float rotation = (float)(tileEntity.getBlockMetadata() * 360) / 16.0F;
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y + 1.5D, z + 0.5D);
        GL11.glRotatef(-rotation, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(RadioItemRenderer.TEXTURE);
        RadioItemRenderer.MODEL.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
        GL11.glPopMatrix();

        if (RenderManager.field_85095_o)
        {
            Iterator var10 = ((RadioBlockEntity)tileEntity).speakers.iterator();

            while (var10.hasNext())
            {
                ChunkCoordinates coordinates = (ChunkCoordinates)var10.next();
                GL11.glPushMatrix();
                GL11.glTranslated(x, y, z);
                GL11.glColor4d(1.0D, 1.0D, 1.0D, 1.0D);
                GL11.glDisable(GL11.GL_TEXTURE_2D);
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glDisable(GL11.GL_CULL_FACE);
                GL11.glDisable(GL11.GL_BLEND);
                Tessellator tessellator = Tessellator.instance;
                tessellator.startDrawing(1);
                tessellator.addVertex(0.5D, 0.5D, 0.5D);
                tessellator.addVertex((double)((float)(coordinates.posX - tileEntity.xCoord) + 0.5F), (double)((float)(coordinates.posY - tileEntity.yCoord) + 0.5F), (double)((float)(coordinates.posZ - tileEntity.zCoord) + 0.5F));
                tessellator.draw();
                GL11.glEnable(GL11.GL_TEXTURE_2D);
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glEnable(GL11.GL_CULL_FACE);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glPopMatrix();
            }
        }
    }
}
