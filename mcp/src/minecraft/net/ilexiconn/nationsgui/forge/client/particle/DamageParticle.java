/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.particle.EntityFX
 *  net.minecraft.client.renderer.OpenGlHelper
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.util.MathHelper
 *  net.minecraft.world.World
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class DamageParticle
extends EntityFX {
    protected static final float SIZE = 3.0f;
    protected boolean grow = true;
    private float damage;
    private String text;
    protected boolean shouldOnTop = true;
    protected float scale = 1.0f;

    public DamageParticle(World p_i1219_1_, double p_i1219_2_, double p_i1219_4_, double p_i1219_6_, double p_i1219_8_, double p_i1219_10_, double p_i1219_12_, float damage) {
        super(p_i1219_1_, p_i1219_2_, p_i1219_4_, p_i1219_6_, p_i1219_8_, p_i1219_10_, p_i1219_12_);
        this.damage = damage;
        this.text = Integer.toString((int)Math.abs(damage));
    }

    public void func_70539_a(Tessellator tessellator, float x, float y, float z, float dX, float dY, float dZ) {
        this.scale = 1.0f;
        float rotationYaw = -Minecraft.func_71410_x().field_71439_g.field_70177_z;
        float rotationPitch = Minecraft.func_71410_x().field_71439_g.field_70125_A;
        float locX = (float)(this.field_70169_q + (this.field_70165_t - this.field_70169_q) * (double)x - field_70556_an);
        float locY = (float)(this.field_70167_r + (this.field_70163_u - this.field_70167_r) * (double)y - field_70554_ao);
        float locZ = (float)(this.field_70166_s + (this.field_70161_v - this.field_70166_s) * (double)z - field_70555_ap);
        GL11.glPushMatrix();
        if (this.shouldOnTop) {
            GL11.glDepthFunc((int)519);
        } else {
            GL11.glDepthFunc((int)515);
        }
        GL11.glTranslatef((float)locX, (float)locY, (float)locZ);
        GL11.glRotatef((float)rotationYaw, (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)rotationPitch, (float)1.0f, (float)0.0f, (float)0.0f);
        GL11.glScalef((float)-1.0f, (float)-1.0f, (float)1.0f);
        GL11.glScaled((double)((double)this.field_70544_f * 0.008), (double)((double)this.field_70544_f * 0.008), (double)((double)this.field_70544_f * 0.008));
        GL11.glScaled((double)this.scale, (double)this.scale, (double)this.scale);
        OpenGlHelper.func_77475_a((int)OpenGlHelper.field_77476_b, (float)240.0f, (float)0.003662109f);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDepthMask((boolean)true);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDisable((int)2896);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)3042);
        GL11.glEnable((int)3008);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        int color = 0xFF0000;
        if (this.damage < 0.0f) {
            color = 65280;
        }
        FontRenderer fontRenderer = Minecraft.func_71410_x().field_71466_p;
        fontRenderer.func_78261_a(this.text, -MathHelper.func_76141_d((float)((float)fontRenderer.func_78256_a(this.text) / 2.0f)) + 1, -MathHelper.func_76141_d((float)((float)fontRenderer.field_78288_b / 2.0f)) + 1, color);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glDepthFunc((int)515);
        GL11.glPopMatrix();
        if (this.grow) {
            this.field_70544_f *= 1.08f;
            if ((double)this.field_70544_f > 9.0) {
                this.grow = false;
            }
        } else {
            this.field_70544_f *= 0.96f;
        }
    }

    public int func_70537_b() {
        return 3;
    }
}

