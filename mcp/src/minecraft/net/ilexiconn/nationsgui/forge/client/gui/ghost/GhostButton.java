/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.renderer.Tessellator
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.ghost;

import net.ilexiconn.nationsgui.forge.client.gui.ghost.GhostGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;

public class GhostButton
extends GuiButton {
    private boolean icon;

    public GhostButton(int id, int posX, int posY, String label, boolean icon) {
        this(id, posX, posY, icon ? 94 : 85, 15, label, icon);
    }

    public GhostButton(int id, int posX, int posY, int width, int height, String label, boolean icon) {
        super(id, posX, posY, width, height, label);
        this.icon = icon;
    }

    protected int func_73738_a(boolean par1) {
        return this.field_73742_g && !par1 ? 0 : 1;
    }

    public void func_73737_a(Minecraft par1Minecraft, int par2, int par3) {
        if (this.field_73748_h) {
            FontRenderer fontrenderer = par1Minecraft.field_71466_p;
            par1Minecraft.func_110434_K().func_110577_a(GhostGUI.TEXTURE);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            this.field_82253_i = par2 >= this.field_73746_c && par3 >= this.field_73743_d && par2 < this.field_73746_c + this.field_73747_a && par3 < this.field_73743_d + this.field_73745_b;
            int k = this.func_73738_a(this.field_82253_i);
            this.func_73729_b(this.field_73746_c, this.field_73743_d, this.icon ? 0 : 132, 214 + k * 15, this.field_73747_a / 2, this.field_73745_b);
            this.func_73729_b(this.field_73746_c + this.field_73747_a / 2, this.field_73743_d, (this.icon ? 94 : 217) - this.field_73747_a / 2, 214 + k * 15, this.field_73747_a / 2, this.field_73745_b);
            this.func_73739_b(par1Minecraft, par2, par3);
            int l = 0xE0E0E0;
            if (!this.field_73742_g) {
                l = -6250336;
            } else if (this.field_82253_i) {
                l = 0xFFFFA0;
            }
            this.func_73732_a(fontrenderer, this.field_73744_e, this.field_73746_c + this.field_73747_a / 2, this.field_73743_d + (this.field_73745_b - 8) / 2, l);
        }
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

