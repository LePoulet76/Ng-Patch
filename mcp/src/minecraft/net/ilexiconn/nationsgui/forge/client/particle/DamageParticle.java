package net.ilexiconn.nationsgui.forge.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class DamageParticle extends EntityFX
{
    protected static final float SIZE = 3.0F;
    protected boolean grow = true;
    private float damage;
    private String text;
    protected boolean shouldOnTop = true;
    protected float scale = 1.0F;

    public DamageParticle(World par1World, double par2, double par4, double par6, double par8, double par10, double par12, float damage)
    {
        super(par1World, par2, par4, par6, par8, par10, par12);
        this.damage = damage;
        this.text = Integer.toString((int)Math.abs(damage));
    }

    public void renderParticle(Tessellator tessellator, float x, float y, float z, float dX, float dY, float dZ)
    {
        this.scale = 1.0F;
        float rotationYaw = -Minecraft.getMinecraft().thePlayer.rotationYaw;
        float rotationPitch = Minecraft.getMinecraft().thePlayer.rotationPitch;
        float locX = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)x - interpPosX);
        float locY = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)y - interpPosY);
        float locZ = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)z - interpPosZ);
        GL11.glPushMatrix();

        if (this.shouldOnTop)
        {
            GL11.glDepthFunc(GL11.GL_ALWAYS);
        }
        else
        {
            GL11.glDepthFunc(GL11.GL_LEQUAL);
        }

        GL11.glTranslatef(locX, locY, locZ);
        GL11.glRotatef(rotationYaw, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(rotationPitch, 1.0F, 0.0F, 0.0F);
        GL11.glScalef(-1.0F, -1.0F, 1.0F);
        GL11.glScaled((double)this.particleScale * 0.008D, (double)this.particleScale * 0.008D, (double)this.particleScale * 0.008D);
        GL11.glScaled((double)this.scale, (double)this.scale, (double)this.scale);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 0.003662109F);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDepthMask(true);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int color = 16711680;

        if (this.damage < 0.0F)
        {
            color = 65280;
        }

        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
        fontRenderer.drawStringWithShadow(this.text, -MathHelper.floor_float((float)fontRenderer.getStringWidth(this.text) / 2.0F) + 1, -MathHelper.floor_float((float)fontRenderer.FONT_HEIGHT / 2.0F) + 1, color);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        GL11.glPopMatrix();

        if (this.grow)
        {
            this.particleScale *= 1.08F;

            if ((double)this.particleScale > 9.0D)
            {
                this.grow = false;
            }
        }
        else
        {
            this.particleScale *= 0.96F;
        }
    }

    public int getFXLayer()
    {
        return 3;
    }
}
