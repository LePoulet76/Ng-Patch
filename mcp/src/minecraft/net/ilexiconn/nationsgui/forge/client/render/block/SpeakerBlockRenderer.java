package net.ilexiconn.nationsgui.forge.client.render.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.render.item.SpeakerItemRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class SpeakerBlockRenderer extends TileEntitySpecialRenderer
{
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTicks)
    {
        GL11.glPushMatrix();
        float rotation = 0.0F;

        switch (tileEntity.getBlockMetadata())
        {
            case 2:
                GL11.glTranslated(x + 0.5D, y - 1.5D, z + 1.5D);
                rotation = 0.0F;
                break;

            case 3:
                GL11.glTranslated(x + 0.5D, y - 1.5D, z - 0.5D);
                rotation = 180.0F;
                break;

            case 4:
                GL11.glTranslated(x + 1.5D, y - 1.5D, z + 0.5D);
                rotation = 90.0F;
                break;

            case 5:
                GL11.glTranslated(x - 0.5D, y - 1.5D, z + 0.5D);
                rotation = 270.0F;
                break;

            default:
                GL11.glPopMatrix();
                return;
        }

        GL11.glRotatef(rotation, 0.0F, 1.0F, 0.0F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(SpeakerItemRenderer.TEXTURE);
        SpeakerItemRenderer.MODEL.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
        GL11.glPopMatrix();
    }
}
