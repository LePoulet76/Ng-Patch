package net.ilexiconn.nationsgui.forge.client.render.entity;

import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.server.entity.EntityPictureFrame;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderPictureFrame extends RenderEntity
{
    private static final ResourceLocation TEXTURE = new ResourceLocation("nationsgui", "textures/entity/pictureframeoff.png");

    private void renderEntityPicture(EntityPictureFrame par1EntityPainting, int par2, int par3)
    {
        Tessellator tessellator = Tessellator.instance;

        if ((par1EntityPainting.getDownloadImageSkin() == null || !par1EntityPainting.getDownloadImageSkin().isTextureUploaded()) && ClientProxy.clientConfig.displayPictureFrame)
        {
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            tessellator.startDrawingQuads();
            tessellator.setColorRGBA_F(1.0F, 1.0F, 1.0F, 1.0F);
            tessellator.setNormal(1.0F, 0.0F, 0.0F);
            tessellator.addVertex((double)(par2 / 2), (double)(par3 / 2), 0.0D);
            tessellator.addVertex((double)(par2 / 2), (double)(-par3 / 2), 0.0D);
            tessellator.addVertex((double)(-par2 / 2), (double)(-par3 / 2), 0.0D);
            tessellator.addVertex((double)(-par2 / 2), (double)(par3 / 2), 0.0D);
            tessellator.draw();
            GL11.glEnable(GL11.GL_TEXTURE_2D);
        }
        else
        {
            this.bindTexture(ClientProxy.clientConfig.displayPictureFrame ? par1EntityPainting.getLocationSkin() : TEXTURE);
            tessellator.startDrawingQuads();
            tessellator.setNormal(1.0F, 0.0F, 0.0F);
            tessellator.addVertexWithUV((double)(par2 / 2), (double)(par3 / 2), 0.0D, 0.0D, 0.0D);
            tessellator.addVertexWithUV((double)(par2 / 2), (double)(-par3 / 2), 0.0D, 0.0D, 1.0D);
            tessellator.addVertexWithUV((double)(-par2 / 2), (double)(-par3 / 2), 0.0D, 1.0D, 1.0D);
            tessellator.addVertexWithUV((double)(-par2 / 2), (double)(par3 / 2), 0.0D, 1.0D, 0.0D);
            tessellator.draw();
        }
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(par2, par4, par6);
        GL11.glRotatef(par8, 0.0F, 1.0F, 0.0F);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        float f2 = 0.0625F;
        GL11.glScalef(f2, f2, f2);
        EntityPictureFrame entityPictureFrame = (EntityPictureFrame)par1Entity;
        this.renderEntityPicture(entityPictureFrame, entityPictureFrame.getWidthPixels(), entityPictureFrame.getHeightPixels());
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return TEXTURE;
    }
}
