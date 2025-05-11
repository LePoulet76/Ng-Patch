/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiButton
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.voices;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

public class GuiCustomButton
extends GuiButton {
    public boolean allowed = true;

    public GuiCustomButton(int par1, int par2, int par3, int par4, int par5, String par6Str) {
        super(par1, par2, par3, par4, par5, par6Str);
    }

    protected int func_73738_a(boolean par1) {
        int b0 = 1;
        if (!this.field_73742_g) {
            b0 = 0;
        } else if (par1) {
            b0 = 2;
        }
        if (!this.allowed) {
            b0 = 1;
        }
        return b0;
    }

    public void func_73737_a(Minecraft par1Minecraft, int par2, int par3) {
        if (this.field_73748_h) {
            FontRenderer fontrenderer = par1Minecraft.field_71466_p;
            par1Minecraft.field_71446_o.func_110577_a(field_110332_a);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            this.field_82253_i = par2 >= this.field_73746_c && par3 >= this.field_73743_d && par2 < this.field_73746_c + this.field_73747_a && par3 < this.field_73743_d + this.field_73745_b;
            int k = this.func_73738_a(this.field_82253_i);
            this.func_73729_b(this.field_73746_c, this.field_73743_d, 0, 46 + k * 20, this.field_73747_a / 2, this.field_73745_b);
            this.func_73729_b(this.field_73746_c + this.field_73747_a / 2, this.field_73743_d, 200 - this.field_73747_a / 2, 46 + k * 20, this.field_73747_a / 2, this.field_73745_b);
            this.func_73739_b(par1Minecraft, par2, par3);
            int l = 0xE0E0E0;
            if (!this.field_73742_g) {
                l = -6250336;
            } else if (this.field_82253_i && this.allowed) {
                l = 0xFFFFA0;
            }
            this.func_73732_a(fontrenderer, this.field_73744_e, this.field_73746_c + this.field_73747_a / 2, this.field_73743_d + (this.field_73745_b - 8) / 2, l);
        }
    }

    public void setWidth(int width) {
        this.field_73747_a = width;
    }

    public void setHeight(int height) {
        this.field_73745_b = height;
    }
}

