/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 *  org.lwjgl.input.Mouse
 */
package net.ilexiconn.nationsgui.forge.client.gui;

import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Mouse;

public class GuiScrollBar
extends Gui {
    protected float x;
    protected float y;
    protected int width;
    protected int height;
    private boolean dragging;
    public float sliderValue = 0.0f;
    private boolean selected;
    private boolean isPressed = false;
    private float increment = 0.05f;

    public GuiScrollBar(float x, float y, int height) {
        this.x = x;
        this.y = y;
        this.width = 9;
        this.height = height;
    }

    public void setScrollIncrement(float i) {
        this.increment = i;
    }

    protected void drawScroller() {
        ClientEventHandler.STYLE.bindTexture("mail");
        this.func_73729_b((int)this.x, (int)(this.y + (float)(this.height - 15) * this.sliderValue), 182, 0, 9, 15);
    }

    public void draw(int mouseX, int mouseY) {
        this.draw(mouseX, mouseY, true);
    }

    public void draw(int mouseX, int mouseY, boolean scrollActive) {
        this.drawScroller();
        if (!Mouse.isButtonDown((int)0)) {
            if (this.isPressed) {
                this.mouseReleased(mouseX, mouseY);
                this.isPressed = false;
            }
            while (scrollActive && !Minecraft.func_71410_x().field_71474_y.field_85185_A && Mouse.next()) {
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

    private void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
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

    private boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
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

    private void mouseReleased(int mouseX, int mouseY) {
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
}

