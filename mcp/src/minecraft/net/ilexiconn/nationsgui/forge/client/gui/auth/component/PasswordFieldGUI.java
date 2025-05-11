/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.GuiTextField
 */
package net.ilexiconn.nationsgui.forge.client.gui.auth.component;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiTextField;

@SideOnly(value=Side.CLIENT)
public class PasswordFieldGUI
extends GuiTextField {
    private int posX;
    private int posY;
    private int width;
    private int height;
    private int cursorCounter;
    private FontRenderer fontRenderer;

    public PasswordFieldGUI(int x, int y, int width, int height) {
        super(Minecraft.func_71410_x().field_71466_p, x, y, width, height);
        this.posX = x;
        this.posY = y;
        this.width = width;
        this.height = height;
        this.fontRenderer = Minecraft.func_71410_x().field_71466_p;
        this.func_73804_f(38);
    }

    public boolean func_73802_a(char character, int key) {
        return character != ' ' && character != '\n' && super.func_73802_a(character, key);
    }

    public void func_73780_a() {
        ++this.cursorCounter;
    }

    public void func_73795_f() {
        Gui.func_73734_a((int)(this.posX - 1), (int)(this.posY - 1), (int)(this.posX + this.width + 1), (int)(this.posY + this.height + 1), (int)-6250336);
        Gui.func_73734_a((int)this.posX, (int)this.posY, (int)(this.posX + this.width), (int)(this.posY + this.height), (int)-16777216);
        int x = 0;
        String line = "";
        String text = this.func_73781_b().replaceAll(".", "*");
        for (char character : text.toCharArray()) {
            if (character == '\r' || character == '\n') {
                this.func_73731_b(this.fontRenderer, line, this.posX + 4, this.posY + 4 + x * this.fontRenderer.field_78288_b, 0xE0E0E0);
                line = "";
                ++x;
                continue;
            }
            if (this.fontRenderer.func_78256_a(line + (char)character) > 191) {
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
        if (flag && 0 == text.length()) {
            this.fontRenderer.func_78276_b("_", this.posX + 3 + this.fontRenderer.func_78256_a(line), this.posY + 4 + x * this.fontRenderer.field_78288_b, 0xE0E0E0);
        }
        for (char character : text.toCharArray()) {
            ++i;
            if (character == '\r' || character == '\n') {
                line = "";
                ++x;
            } else if (this.fontRenderer.func_78256_a(line + character) > 191) {
                line = "";
                ++x;
                line = line + character;
            } else {
                line = line + character;
            }
            if (!flag || i != text.length()) continue;
            this.fontRenderer.func_78276_b("_", this.posX + 3 + this.fontRenderer.func_78256_a(line), this.posY + 4 + x * this.fontRenderer.field_78288_b, 0xE0E0E0);
        }
    }
}

