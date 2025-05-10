package net.ilexiconn.nationsgui.forge.client.render.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.model.block.SkullModel;
import net.ilexiconn.nationsgui.forge.client.render.item.SkullItemRenderer;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

@SideOnly(Side.CLIENT)
public class SkullBlockRenderer extends TileEntitySkullRenderer
{
    public static final SkullBlockRenderer INSTANCE = new SkullBlockRenderer();

    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTicks)
    {
        TileEntitySkull skull = (TileEntitySkull)tileEntity;
        this.renderSkull((float)x, (float)y, (float)z, skull.getBlockMetadata() & 7, (float)(skull.func_82119_b() * 360) / 16.0F, skull.getSkullType(), skull.getExtraType(), false);
    }

    public void renderSkull(float x, float y, float z, int placement, float rotation, int type, String extraType, boolean wear)
    {
        SkullModel model = SkullItemRenderer.MODEL_SKULL;

        switch (type)
        {
            case 0:
            default:
                this.bindTexture(SkullItemRenderer.SKELETON_TEXTURE);
                break;

            case 1:
                this.bindTexture(SkullItemRenderer.WITHER_SKELETON_TEXTURE);
                break;

            case 2:
                this.bindTexture(SkullItemRenderer.ZOMBIE_TEXTURE);
                model = SkullItemRenderer.MODEL_SKULL_LARGE;
                break;

            case 3:
                ResourceLocation resourceLocation = AbstractClientPlayer.locationStevePng;

                if (extraType != null && extraType.length() > 0)
                {
                    resourceLocation = AbstractClientPlayer.getLocationSkull(extraType);
                    AbstractClientPlayer.getDownloadImageSkin(resourceLocation, extraType);
                }

                this.bindTexture(resourceLocation);
                model = SkullItemRenderer.MODEL_SKULL_LARGE;
                break;

            case 4:
                this.bindTexture(SkullItemRenderer.CREEPER_TEXTURE);
        }

        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_CULL_FACE);

        if (placement != 1)
        {
            switch (placement)
            {
                case 2:
                    GL11.glTranslated((double)(x + 0.5F), (double)(y + 0.25F), (double)(z + 0.74F));
                    break;

                case 3:
                    GL11.glTranslated((double)(x + 0.5F), (double)(y + 0.25F), (double)(z + 0.26F));
                    rotation = 180.0F;
                    break;

                case 4:
                    GL11.glTranslated((double)(x + 0.74F), (double)(y + 0.25F), (double)(z + 0.5F));
                    rotation = 270.0F;
                    break;

                default:
                    GL11.glTranslated((double)(x + 0.26F), (double)(y + 0.25F), (double)(z + 0.5F));
                    rotation = 90.0F;
            }
        }
        else
        {
            GL11.glTranslated((double)(x + 0.5F), (double)y, (double)(z + 0.5F));
        }

        if (wear)
        {
            GL11.glTranslated(0.0D, -0.029999999329447746D, 0.0D);
        }

        GL11.glEnable(GL12.GL_RESCALE_NORMAL);

        if (wear)
        {
            GL11.glScalef(-1.1F, -1.1F, 1.1F);
        }
        else
        {
            GL11.glScalef(-1.0F, -1.0F, 1.0F);
        }

        GL11.glEnable(GL11.GL_ALPHA_TEST);
        model.render((Entity)null, 0.0F, 0.0F, 0.0F, rotation, 0.0F, 0.0625F);
        GL11.glPopMatrix();
    }
}
