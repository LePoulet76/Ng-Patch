/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiTextField
 */
package net.ilexiconn.nationsgui.forge.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.halalaboos.cfont.CFontRenderer;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;

@SideOnly(value=Side.CLIENT)
public class CustomTextAreaGUI
extends GuiTextField {
    private final int width;
    private final int height;
    private final String customFont;
    private final int fontSize;
    private final CFontRenderer fontRenderer;
    public int posX;
    public int posY;
    private int cursorCounter;

    public CustomTextAreaGUI(int x, int y, int width, String customFont, int fontSize, int height) {
        super(Minecraft.func_71410_x().field_71466_p, x, y, width, height);
        this.posX = x;
        this.posY = y;
        this.width = width;
        this.height = height;
        this.customFont = customFont;
        this.fontSize = fontSize;
        this.fontRenderer = ModernGui.getCustomFont(customFont, fontSize);
    }

    public void func_73780_a() {
        ++this.cursorCounter;
    }

    public void func_73793_a(int par1, int par2, int par3) {
        boolean flag = par1 >= this.posX && par1 < this.posX + this.width && par2 >= this.posY && par2 < this.posY + this.height;
        this.func_73796_b(flag);
        if (this.func_73806_l() && par3 == 0) {
            int l = par1 - this.posX;
            if (this.func_73783_i()) {
                l -= 4;
            }
            this.func_73791_e(this.trimStringToWidth(this.func_73781_b(), l += (par2 - this.posY - 4) / ((this.fontRenderer.getHeight() + 4) / 2) * this.width).length());
        }
    }

    public String trimStringToWidth(String str, int width) {
        String line = "";
        for (char character : this.func_73781_b().toCharArray()) {
            if (!(this.fontRenderer.getStringWidth(line = line + (char)character) / 2.0f >= (float)width)) continue;
            return line;
        }
        return line;
    }

    public void func_73795_f() {
        int x = 0;
        String line = "";
        for (char character : this.func_73781_b().toCharArray()) {
            if (character == '\r' || character == '\n') {
                ModernGui.drawScaledStringCustomFont(line, this.posX + 4, this.posY + 4 + x * (this.fontRenderer.getHeight() + 4) / 2, 0xE0E0E0, 0.5f, "left", false, this.customFont, this.fontSize);
                line = "";
                ++x;
                continue;
            }
            StringBuilder stringBuilder = new StringBuilder();
            if (this.fontRenderer.getStringWidth(stringBuilder.append(line).append(character).toString()) / 2.0f > (float)this.width) {
                ModernGui.drawScaledStringCustomFont(line, this.posX + 4, this.posY + 4 + x * (this.fontRenderer.getHeight() + 4) / 2, 0xE0E0E0, 0.5f, "left", false, this.customFont, this.fontSize);
                line = "";
                ++x;
            }
            line = line + (char)character;
        }
        ModernGui.drawScaledStringCustomFont(line, this.posX + 4, this.posY + 4 + x * (this.fontRenderer.getHeight() + 4) / 2, 0xE0E0E0, 0.5f, "left", false, this.customFont, this.fontSize);
        boolean flag = this.func_73806_l() && this.cursorCounter / 6 % 2 == 0;
        int i = 0;
        x = 0;
        line = "";
        if (flag && 0 == this.func_73781_b().length()) {
            ModernGui.drawScaledStringCustomFont("_", this.posX + 3 + (int)this.fontRenderer.getStringWidth(line) / 2, this.posY + 4 + x * (this.fontRenderer.getHeight() + 4) / 2, 0xE0E0E0, 0.5f, "left", false, this.customFont, this.fontSize);
        }
        for (char character : this.func_73781_b().toCharArray()) {
            ++i;
            if (character == '\r' || character == '\n') {
                line = "";
                ++x;
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                if (this.fontRenderer.getStringWidth(stringBuilder.append(line).append(character).toString()) / 2.0f > (float)this.width) {
                    line = "";
                    ++x;
                    line = line + character;
                } else {
                    line = line + character;
                }
            }
            if (!flag || i != this.func_73781_b().length()) continue;
            ModernGui.drawScaledStringCustomFont("_", this.posX + 3 + (int)this.fontRenderer.getStringWidth(line) / 2, this.posY + 4 + x * (this.fontRenderer.getHeight() + 4) / 2, 0xE0E0E0, 0.5f, "left", false, this.customFont, this.fontSize);
        }
    }
}

