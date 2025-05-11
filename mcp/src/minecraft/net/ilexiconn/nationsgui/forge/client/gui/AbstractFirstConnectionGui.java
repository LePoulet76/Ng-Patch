/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public abstract class AbstractFirstConnectionGui
extends GuiScreen {
    public static final ResourceLocation BACKGROUND = new ResourceLocation("nationsgui", "textures/gui/first_connection.png");
    public static final ResourceLocation SHOP_ASSET = new ResourceLocation("nationsgui", "textures/gui/shop.png");

    public void func_73866_w_() {
        this.field_73887_h.add(new QuitButton(this.field_73880_f / 2 + 82, this.field_73881_g / 2 - 58));
    }

    protected void func_73875_a(GuiButton par1GuiButton) {
        if (par1GuiButton.field_73741_f == -1) {
            this.field_73882_e.func_71373_a(null);
        }
    }

    public void func_73863_a(int par1, int par2, float par3) {
        this.field_73882_e.func_110434_K().func_110577_a(BACKGROUND);
        this.drawRectangleBox(this.field_73880_f / 2 - 100, this.field_73881_g / 2 - 64, 200, 128);
        GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
        this.drawRectangleWelcome(this.field_73880_f / 2 - 90, this.field_73881_g / 2 - 64, 110);
        super.func_73863_a(par1, par2, par3);
    }

    protected void drawRectangleBox(int x, int y, int width, int height) {
        GL11.glPushMatrix();
        GL11.glTranslated((double)x, (double)y, (double)0.0);
        this.func_73729_b(0, 0, 0, 0, 3, 3);
        GL11.glPushMatrix();
        GL11.glTranslated((double)3.0, (double)0.0, (double)0.0);
        GL11.glScalef((float)(width - 6), (float)1.0f, (float)1.0f);
        this.func_73729_b(0, 0, 3, 0, 1, 3);
        GL11.glPopMatrix();
        this.func_73729_b(width - 3, 0, 24, 0, 3, 3);
        GL11.glPushMatrix();
        GL11.glTranslated((double)0.0, (double)3.0, (double)0.0);
        GL11.glScalef((float)1.0f, (float)(height - 6), (float)1.0f);
        this.func_73729_b(0, 0, 0, 3, 3, 1);
        GL11.glPopMatrix();
        this.func_73729_b(0, height - 3, 0, 7, 3, 3);
        GL11.glPushMatrix();
        GL11.glTranslated((double)3.0, (double)0.0, (double)0.0);
        GL11.glScalef((float)(width - 6), (float)1.0f, (float)1.0f);
        this.func_73729_b(0, height - 3, 3, 7, 1, 3);
        GL11.glPopMatrix();
        this.func_73729_b(width - 3, height - 3, 24, 7, 3, 3);
        GL11.glPushMatrix();
        GL11.glTranslated((double)((float)width - 3.0f), (double)3.0, (double)0.0);
        GL11.glScalef((float)1.0f, (float)(height - 6), (float)1.0f);
        this.func_73729_b(0, 0, 24, 3, 3, 1);
        GL11.glPopMatrix();
        AbstractFirstConnectionGui.func_73734_a((int)3, (int)3, (int)(width - 3), (int)(height - 3), (int)-15000805);
        GL11.glPopMatrix();
    }

    protected void drawRectangleWelcome(int x, int y, int width) {
        this.func_73729_b(x, y, 28, 0, 3, 20);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(x + 3), (float)y, (float)0.0f);
        GL11.glScalef((float)(width - 6), (float)1.0f, (float)1.0f);
        this.func_73729_b(0, 0, 31, 0, 1, 20);
        GL11.glPopMatrix();
        this.func_73729_b(x + width - 3, y, 49, 0, 3, 20);
        GL11.glPushMatrix();
        String text = "Bienvenue";
        GL11.glTranslatef((float)((float)((double)(x + width / 2) - (double)this.field_73882_e.field_71466_p.func_78256_a(text) * 1.5 / 2.0)), (float)((float)((double)(y + 10) - 6.0)), (float)0.0f);
        GL11.glScalef((float)1.5f, (float)1.5f, (float)1.0f);
        this.field_73882_e.field_71466_p.func_78261_a(text, 0, 0, -1);
        GL11.glPopMatrix();
    }

    protected void drawGreyRectangle(int xPosition, int yPosition, int width, int height) {
        this.field_73882_e.func_110434_K().func_110577_a(BACKGROUND);
        this.func_73729_b(xPosition, yPosition, 25, 33, 2, 2);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(xPosition + 2), (float)yPosition, (float)0.0f);
        GL11.glScalef((float)(width - 4), (float)1.0f, (float)1.0f);
        this.func_73729_b(0, 0, 27, 33, 1, 2);
        GL11.glPopMatrix();
        this.func_73729_b(xPosition + width - 2, yPosition, 42, 33, 2, 2);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)xPosition, (float)(yPosition + 2), (float)0.0f);
        GL11.glScalef((float)1.0f, (float)(height - 4), (float)1.0f);
        this.func_73729_b(0, 0, 25, 35, 2, 1);
        GL11.glPopMatrix();
        this.func_73729_b(xPosition, yPosition + height - 2, 25, 40, 2, 2);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(xPosition + 2), (float)(yPosition + height - 2), (float)0.0f);
        GL11.glScalef((float)(width - 4), (float)1.0f, (float)1.0f);
        this.func_73729_b(0, 0, 27, 40, 1, 2);
        GL11.glPopMatrix();
        this.func_73729_b(xPosition + width - 2, yPosition + height - 2, 42, 40, 2, 2);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(xPosition + width - 2), (float)(yPosition + 2), (float)0.0f);
        GL11.glScalef((float)1.0f, (float)(height - 4), (float)1.0f);
        this.func_73729_b(0, 0, 42, 35, 2, 1);
        GL11.glPopMatrix();
        AbstractFirstConnectionGui.func_73734_a((int)(xPosition + 2), (int)(yPosition + 2), (int)(xPosition + width - 2), (int)(yPosition + height - 2), (int)-11645362);
    }

    private class QuitButton
    extends GuiButton {
        public QuitButton(int x, int y) {
            super(-1, x, y, 9, 10, "");
        }

        public void func_73737_a(Minecraft par1Minecraft, int par2, int par3) {
            AbstractFirstConnectionGui.this.field_73882_e.func_110434_K().func_110577_a(SHOP_ASSET);
            this.field_82253_i = par2 >= this.field_73746_c && par3 >= this.field_73743_d && par2 < this.field_73746_c + this.field_73747_a && par3 < this.field_73743_d + this.field_73745_b;
            this.func_73729_b(this.field_73746_c, this.field_73743_d, 204, this.field_82253_i ? 18 : 28, 9, 10);
        }
    }
}

