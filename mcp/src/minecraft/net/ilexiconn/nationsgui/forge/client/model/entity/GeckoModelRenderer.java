/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.model.ModelBase
 *  net.minecraft.client.model.ModelRenderer
 *  org.lwjgl.opengl.GL11
 *  software.bernie.geckolib3.core.IAnimatable
 *  software.bernie.geckolib3.renderers.geo.GeoSimpleRenderer
 */
package net.ilexiconn.nationsgui.forge.client.model.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import org.lwjgl.opengl.GL11;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.renderers.geo.GeoSimpleRenderer;

public class GeckoModelRenderer
extends ModelRenderer {
    private final GeoSimpleRenderer<IAnimatable> renderer;
    private float scale;

    public GeckoModelRenderer(ModelBase par1ModelBase, GeoSimpleRenderer<IAnimatable> renderer) {
        super(par1ModelBase);
        this.renderer = renderer;
    }

    public void func_78785_a(float par1) {
        GL11.glPushMatrix();
        GL11.glScalef((float)this.scale, (float)this.scale, (float)this.scale);
        GL11.glRotatef((float)this.field_78795_f, (float)1.0f, (float)0.0f, (float)0.0f);
        GL11.glRotatef((float)this.field_78796_g, (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)this.field_78808_h, (float)0.0f, (float)0.0f, (float)1.0f);
        GL11.glTranslatef((float)this.field_82906_o, (float)this.field_82908_p, (float)this.field_82907_q);
        this.renderer.doRender(par1);
        GL11.glPopMatrix();
    }

    public float getScale() {
        return this.scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
}

