package net.ilexiconn.nationsgui.forge.client.model.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import org.lwjgl.opengl.GL11;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.renderers.geo.GeoSimpleRenderer;

public class GeckoModelRenderer extends ModelRenderer
{
    private final GeoSimpleRenderer<IAnimatable> renderer;
    private float scale;

    public GeckoModelRenderer(ModelBase par1ModelBase, GeoSimpleRenderer<IAnimatable> renderer)
    {
        super(par1ModelBase);
        this.renderer = renderer;
    }

    public void render(float par1)
    {
        GL11.glPushMatrix();
        GL11.glScalef(this.scale, this.scale, this.scale);
        GL11.glRotatef(this.rotateAngleX, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(this.rotateAngleY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(this.rotateAngleZ, 0.0F, 0.0F, 1.0F);
        GL11.glTranslatef(this.offsetX, this.offsetY, this.offsetZ);
        this.renderer.doRender(par1);
        GL11.glPopMatrix();
    }

    public float getScale()
    {
        return this.scale;
    }

    public void setScale(float scale)
    {
        this.scale = scale;
    }
}
