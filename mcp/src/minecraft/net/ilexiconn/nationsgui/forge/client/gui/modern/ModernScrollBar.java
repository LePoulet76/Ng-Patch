/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.modern;

import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class ModernScrollBar
extends ModernGui {
    private float x;
    private float y;
    private int width;
    private int height;
    private int steps;
    private boolean dragging;
    private float sliderValue = 0.0f;
    private boolean selected;
    private boolean isPressed = false;

    public ModernScrollBar(float x, float y, int width, int height, int steps) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.steps = steps;
    }

    public void draw(int mouseX, int mouseY) {
        GL11.glColor4f((float)0.0f, (float)0.0f, (float)0.0f, (float)0.25f);
        ModernGui.drawExtendedCircle(this.x, this.y, this.width, this.height);
        GL11.glColor4f((float)0.0f, (float)0.0f, (float)0.0f, (float)0.5f);
        ModernGui.drawExtendedCircle(this.x, this.y + this.sliderValue * (float)(this.height - this.height / this.steps), this.width, this.height / this.steps);
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
                this.sliderValue += (float)l2 * 0.05f;
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
        if ((float)mouseX >= this.x - 2.0f && (float)mouseX <= this.x + (float)this.width + 2.0f && (float)mouseY >= this.y && (float)mouseY <= this.y + (float)this.height) {
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
}

