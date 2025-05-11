/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui;

import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

public class TexturedCenteredButtonGUI
extends GuiButton {
    private final String texture;
    private final int u;
    private final int v;

    public TexturedCenteredButtonGUI(int id, int x, int y, int width, int height, String texture, int u, int v, String text) {
        super(id, x, y, width, height, text);
        this.texture = texture;
        this.u = u;
        this.v = v;
    }

    public void func_73737_a(Minecraft mc, int mouseX, int mouseY) {
        if (this.field_73748_h) {
            this.field_82253_i = mouseX >= this.field_73746_c && mouseY >= this.field_73743_d && mouseX < this.field_73746_c + this.field_73747_a && mouseY < this.field_73743_d + this.field_73745_b;
            int hoverState = this.func_73738_a(this.field_82253_i);
            ClientEventHandler.STYLE.bindTexture(this.texture);
            this.drawContinuousTexturedBox(this.field_73746_c, this.field_73743_d, this.u, this.v + hoverState * this.field_73745_b, this.field_73747_a, this.field_73745_b, this.field_73747_a, this.field_73745_b, 2, 3, 2, 2, this.field_73735_i);
            this.func_73731_b(mc.field_71466_p, this.field_73744_e, this.field_73746_c + this.field_73747_a / 2 - mc.field_71466_p.func_78256_a(this.field_73744_e) / 2, this.field_73743_d + (this.field_73745_b - 8) / 2, 0xE0E0E0);
        }
    }

    public void drawContinuousTexturedBox(int x, int y, int u, int v, int width, int height, int textureWidth, int textureHeight, int topBorder, int bottomBorder, int leftBorder, int rightBorder, float zLevel) {
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        int fillerWidth = textureWidth - leftBorder - rightBorder;
        int fillerHeight = textureHeight - topBorder - bottomBorder;
        int canvasWidth = width - leftBorder - rightBorder;
        int canvasHeight = height - topBorder - bottomBorder;
        int xPasses = canvasWidth / fillerWidth;
        int remainderWidth = canvasWidth % fillerWidth;
        int yPasses = canvasHeight / fillerHeight;
        int remainderHeight = canvasHeight % fillerHeight;
        this.func_73729_b(x, y, u, v, leftBorder, topBorder);
        this.func_73729_b(x + leftBorder + canvasWidth, y, u + leftBorder + fillerWidth, v, rightBorder, topBorder);
        this.func_73729_b(x, y + topBorder + canvasHeight, u, v + topBorder + fillerHeight, leftBorder, bottomBorder);
        this.func_73729_b(x + leftBorder + canvasWidth, y + topBorder + canvasHeight, u + leftBorder + fillerWidth, v + topBorder + fillerHeight, rightBorder, bottomBorder);
        for (int i = 0; i < xPasses + (remainderWidth > 0 ? 1 : 0); ++i) {
            this.func_73729_b(x + leftBorder + i * fillerWidth, y, u + leftBorder, v, i == xPasses ? remainderWidth : fillerWidth, topBorder);
            this.func_73729_b(x + leftBorder + i * fillerWidth, y + topBorder + canvasHeight, u + leftBorder, v + topBorder + fillerHeight, i == xPasses ? remainderWidth : fillerWidth, bottomBorder);
            for (int j = 0; j < yPasses + (remainderHeight > 0 ? 1 : 0); ++j) {
                this.func_73729_b(x + leftBorder + i * fillerWidth, y + topBorder + j * fillerHeight, u + leftBorder, v + topBorder, i == xPasses ? remainderWidth : fillerWidth, j == yPasses ? remainderHeight : fillerHeight);
            }
        }
        for (int j = 0; j < yPasses + (remainderHeight > 0 ? 1 : 0); ++j) {
            this.func_73729_b(x, y + topBorder + j * fillerHeight, u, v + topBorder, leftBorder, j == yPasses ? remainderHeight : fillerHeight);
            this.func_73729_b(x + leftBorder + canvasWidth, y + topBorder + j * fillerHeight, u + leftBorder + fillerWidth, v + topBorder, rightBorder, j == yPasses ? remainderHeight : fillerHeight);
        }
    }
}

