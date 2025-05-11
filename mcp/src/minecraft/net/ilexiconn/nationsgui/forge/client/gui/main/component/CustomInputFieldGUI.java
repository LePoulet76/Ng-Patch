/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiTextField
 */
package net.ilexiconn.nationsgui.forge.client.gui.main.component;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.halalaboos.cfont.CFontRenderer;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;

@SideOnly(value=Side.CLIENT)
public class CustomInputFieldGUI
extends GuiTextField {
    private final String customFont;
    private final int fontSize;
    private int width;
    private int height;
    private int cursorCounter;
    public int posX;
    public int posY;
    private CFontRenderer fontRenderer;

    public CustomInputFieldGUI(int x, int y, int width, int height, String customFont, int fontSize) {
        super(Minecraft.func_71410_x().field_71466_p, x, y, width, height);
        this.posX = x;
        this.posY = y;
        this.width = width;
        this.height = height;
        this.customFont = customFont;
        this.fontSize = fontSize;
        this.fontRenderer = ModernGui.getCustomFont(customFont, fontSize);
    }

    public boolean func_73802_a(char par1, int par2) {
        return super.func_73802_a(par1, par2);
    }

    public void func_73780_a() {
        ++this.cursorCounter;
    }

    public void func_73795_f() {
        int x = 0;
        String line = "";
        String text = this.func_73781_b();
        for (char character : text.toCharArray()) {
            if (character == '\r' || character == '\n') {
                ModernGui.drawScaledStringCustomFont(line, this.posX + 4, this.posY + 4, 0xE0E0E0, 0.5f, "left", false, this.customFont, this.fontSize);
                line = "";
                ++x;
                continue;
            }
            line = line + (char)character;
        }
        ModernGui.drawScaledStringCustomFont(line, this.posX + 4, this.posY + 4, 0xE0E0E0, 0.5f, "left", false, this.customFont, this.fontSize);
        boolean flag = this.func_73806_l() && this.cursorCounter / 6 % 2 == 0;
        int i = 0;
        x = 0;
        line = "";
        if (flag && 0 == text.length()) {
            ModernGui.drawScaledStringCustomFont("_", this.posX + 3 + (int)this.fontRenderer.getStringWidth(line) / 2, this.posY + this.fontRenderer.getHeight() / 2, 0xE0E0E0, 0.5f, "left", false, this.customFont, this.fontSize);
        }
        for (char character : text.toCharArray()) {
            ++i;
            if (character == '\r' || character == '\n') {
                line = "";
                ++x;
            } else {
                line = line + character;
            }
            if (!flag || i != text.length()) continue;
            ModernGui.drawScaledStringCustomFont("_", this.posX + 3 + (int)this.fontRenderer.getStringWidth(line) / 2, this.posY + this.fontRenderer.getHeight() / 2, 0xE0E0E0, 0.5f, "left", false, this.customFont, this.fontSize);
        }
    }

    public void func_73793_a(int par1, int par2, int par3) {
        boolean flag = par1 >= this.posX && par1 < this.posX + this.width && par2 >= this.posY && par2 < this.posY + this.height;
        this.func_73796_b(flag);
        if (this.func_73781_b().equals("0") && this.func_73806_l()) {
            this.func_73782_a("");
        }
        if (this.func_73806_l() && par3 == 0) {
            int l = par1 - this.posX;
            if (this.func_73783_i()) {
                l -= 4;
            }
            this.func_73791_e(this.trimStringToWidth(this.func_73781_b(), l).length());
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
}

