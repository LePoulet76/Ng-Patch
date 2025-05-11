/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiTextField
 */
package net.ilexiconn.nationsgui.forge.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;

@SideOnly(value=Side.CLIENT)
public class TextAreaGUI
extends GuiTextField {
    private final int posX;
    private final int posY;
    private final int width;
    private final FontRenderer fontRenderer;
    private int cursorCounter;

    public TextAreaGUI(int x, int y, int width) {
        super(Minecraft.func_71410_x().field_71466_p, x, y, width, 73);
        this.posX = x;
        this.posY = y;
        this.width = width;
        this.fontRenderer = Minecraft.func_71410_x().field_71466_p;
    }

    public void func_73780_a() {
        ++this.cursorCounter;
    }

    public void func_73795_f() {
        int x = 0;
        String line = "";
        for (char character : this.func_73781_b().toCharArray()) {
            if (character == '\r' || character == '\n') {
                this.func_73731_b(this.fontRenderer, line, this.posX + 4, this.posY + 4 + x * this.fontRenderer.field_78288_b, 0xE0E0E0);
                line = "";
                ++x;
                continue;
            }
            if (this.fontRenderer.func_78256_a(line + (char)character) > this.width) {
                this.func_73731_b(this.fontRenderer, line, this.posX + 4, this.posY + 4 + x * this.fontRenderer.field_78288_b, 0xE0E0E0);
                line = "";
                ++x;
            }
            line = line + (char)character;
        }
        this.func_73731_b(this.fontRenderer, line, this.posX + 4, this.posY + 4 + x * this.fontRenderer.field_78288_b, 0xE0E0E0);
        boolean flag = this.func_73806_l() && this.cursorCounter / 6 % 2 == 0;
        int i = 0;
        x = 0;
        line = "";
        if (flag && 0 == this.func_73781_b().length()) {
            this.fontRenderer.func_78276_b("_", this.posX + 3 + this.fontRenderer.func_78256_a(line), this.posY + 4 + x * this.fontRenderer.field_78288_b, 0xE0E0E0);
        }
        for (char character : this.func_73781_b().toCharArray()) {
            ++i;
            if (character == '\r' || character == '\n') {
                line = "";
                ++x;
            } else if (this.fontRenderer.func_78256_a(line + character) > this.width) {
                line = "";
                ++x;
                line = line + character;
            } else {
                line = line + character;
            }
            if (!flag || i != this.func_73781_b().length()) continue;
            this.fontRenderer.func_78276_b("_", this.posX + 3 + this.fontRenderer.func_78256_a(line), this.posY + 4 + x * this.fontRenderer.field_78288_b, 0xE0E0E0);
        }
    }
}

