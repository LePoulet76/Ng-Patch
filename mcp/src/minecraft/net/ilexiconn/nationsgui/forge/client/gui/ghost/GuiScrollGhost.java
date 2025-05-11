/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.renderer.Tessellator
 *  org.lwjgl.input.Mouse
 */
package net.ilexiconn.nationsgui.forge.client.gui.ghost;

import net.ilexiconn.nationsgui.forge.client.gui.ghost.GhostGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.input.Mouse;

public class GuiScrollGhost
extends Gui {
    protected float x;
    protected float y;
    protected int width;
    protected int height;
    private boolean dragging;
    protected float sliderValue = 0.0f;
    private boolean selected;
    private boolean isPressed = false;
    private float increment = 0.05f;

    public GuiScrollGhost(float x, float y, int height) {
        this.x = x;
        this.y = y;
        this.width = 2;
        this.height = height;
    }

    public void setScrollIncrement(float i) {
        this.increment = i;
    }

    protected void drawScroller() {
        Minecraft.func_71410_x().func_110434_K().func_110577_a(GhostGUI.TEXTURE);
        this.func_73729_b((int)this.x, (int)(this.y + (float)(this.height - 8) * this.sliderValue), 223, 214, 2, 8);
    }

    public void draw(int mouseX, int mouseY) {
        this.drawScroller();
        if (!Mouse.isButtonDown((int)0)) {
            if (this.isPressed) {
                this.mouseReleased(mouseX, mouseY);
                this.isPressed = false;
            }
            while (!Minecraft.func_71410_x().field_71474_y.field_85185_A && Mouse.next()) {
                int l2 = Mouse.getEventDWheel();
                if (l2 == 0) continue;
                if (l2 > 0) {
                    l2 = -1;
                } else if (l2 < 0) {
                    l2 = 1;
                }
                this.sliderValue += (float)l2 * this.increment;
                if (this.sliderValue < 0.0f) {
                    this.sliderValue = 0.0f;
                }
                if (!(this.sliderValue > 1.0f)) continue;
                this.sliderValue = 1.0f;
            }
        } else if (!this.isPressed) {
            this.isPressed = true;
            this.mousePressed(Minecraft.func_71410_x(), mouseX, mouseY);
        }
        this.mouseDragged(Minecraft.func_71410_x(), mouseX, mouseY);
    }

    protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
        if (this.dragging) {
            this.sliderValue = ((float)mouseY - this.y) / (float)this.height;
            if (this.sliderValue < 0.0f) {
                this.sliderValue = 0.0f;
            }
            if (this.sliderValue > 1.0f) {
                this.sliderValue = 1.0f;
            }
        }
    }

    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        if ((float)mouseX >= this.x && (float)mouseX <= this.x + (float)this.width + 2.0f && (float)mouseY >= this.y && (float)mouseY <= this.y + (float)this.height) {
            this.selected = true;
            this.sliderValue = ((float)mouseY - this.y) / (float)this.height;
            if (this.sliderValue < 0.0f) {
                this.sliderValue = 0.0f;
            }
            if (this.sliderValue > 1.0f) {
                this.sliderValue = 1.0f;
            }
            this.dragging = true;
            return true;
        }
        return false;
    }

    public void mouseReleased(int mouseX, int mouseY) {
        if (this.selected) {
            this.selected = false;
            this.dragging = false;
        }
    }

    public float getSliderValue() {
        return this.sliderValue;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void reset() {
        this.sliderValue = 0.0f;
    }

    public void setSliderValue(float sliderValue) {
        this.sliderValue = sliderValue;
    }

    public void func_73729_b(int par1, int par2, int par3, int par4, int par5, int par6) {
        float f = 0.001953125f;
        float f1 = 0.001953125f;
        Tessellator tessellator = Tessellator.field_78398_a;
        tessellator.func_78382_b();
        tessellator.func_78374_a((double)(par1 + 0), (double)(par2 + par6), (double)this.field_73735_i, (double)((float)(par3 + 0) * f), (double)((float)(par4 + par6) * f1));
        tessellator.func_78374_a((double)(par1 + par5), (double)(par2 + par6), (double)this.field_73735_i, (double)((float)(par3 + par5) * f), (double)((float)(par4 + par6) * f1));
        tessellator.func_78374_a((double)(par1 + par5), (double)(par2 + 0), (double)this.field_73735_i, (double)((float)(par3 + par5) * f), (double)((float)(par4 + 0) * f1));
        tessellator.func_78374_a((double)(par1 + 0), (double)(par2 + 0), (double)this.field_73735_i, (double)((float)(par3 + 0) * f), (double)((float)(par4 + 0) * f1));
        tessellator.func_78381_a();
    }
}

