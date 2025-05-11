/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.hdv;

import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RadioButton
extends Gui {
    private static final ResourceLocation BACKGROUND = new ResourceLocation("nationsgui", "textures/gui/hdv_sell.png");
    private static final int WIDTH = 10;
    private static final int HEIGHT = 8;
    private int posX;
    private int posY;
    private boolean state = false;

    public RadioButton(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public void draw(int mouseX, int mouseY) {
        Minecraft.func_71410_x().func_110434_K().func_110577_a(BACKGROUND);
        if (this.isHovered(mouseX, mouseY)) {
            GL11.glColor3f((float)0.8f, (float)0.8f, (float)0.8f);
        } else {
            GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
        }
        ModernGui.drawModalRectWithCustomSizedTexture(this.posX, this.posY, 46, this.state ? 127 : 118, 10, 8, 275.0f, 256.0f, false);
    }

    public void mousePressed(int mouseX, int mouseY, int button) {
        if (this.isHovered(mouseX, mouseY) && button == 0) {
            this.state = !this.state;
        }
    }

    public boolean getState() {
        return this.state;
    }

    private boolean isHovered(int mouseX, int mouseY) {
        return mouseX >= this.posX && mouseX <= this.posX + 10 && mouseY >= this.posY && mouseY <= this.posY + 8;
    }
}

