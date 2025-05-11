/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiButton
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.assistance;

import net.ilexiconn.nationsgui.forge.client.gui.assistance.AbstractAssistanceGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

public class AssistanceButtonIcon
extends GuiButton {
    private final int iconU;
    private final int iconV;

    public AssistanceButtonIcon(int id, int posX, int posY, int iconU, int iconV) {
        this(id, posX, posY, iconU, iconV, "");
    }

    public AssistanceButtonIcon(int id, int posX, int posY, int iconU, int iconV, String text) {
        this(id, posX, posY, 200, 20, iconU, iconV, text);
    }

    public AssistanceButtonIcon(int id, int posX, int posY, int width, int height, int iconU, int iconV) {
        this(id, posX, posY, width, height, iconU, iconV, "");
    }

    public AssistanceButtonIcon(int id, int posX, int posY, int width, int height, int iconU, int iconV, String text) {
        super(id, posX, posY, width, height, text);
        this.iconU = iconU;
        this.iconV = iconV;
    }

    public void func_73737_a(Minecraft par1Minecraft, int par2, int par3) {
        if (this.field_73748_h) {
            FontRenderer fontrenderer = par1Minecraft.field_71466_p;
            par1Minecraft.func_110434_K().func_110577_a(field_110332_a);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            this.field_82253_i = par2 >= this.field_73746_c && par3 >= this.field_73743_d && par2 < this.field_73746_c + this.field_73747_a && par3 < this.field_73743_d + this.field_73745_b;
            int k = this.func_73738_a(this.field_82253_i);
            this.func_73729_b(this.field_73746_c, this.field_73743_d, 0, 46 + k * 20, this.field_73747_a / 2, this.field_73745_b);
            this.func_73729_b(this.field_73746_c + this.field_73747_a / 2, this.field_73743_d, 200 - this.field_73747_a / 2, 46 + k * 20, this.field_73747_a / 2, this.field_73745_b);
            this.func_73739_b(par1Minecraft, par2, par3);
            int l = 0xE0E0E0;
            if (!this.field_73742_g) {
                l = -6250336;
            } else if (this.field_82253_i) {
                l = 0xFFFFA0;
            }
            par1Minecraft.func_110434_K().func_110577_a(AbstractAssistanceGUI.GUI_TEXTURE);
            ModernGui.drawModalRectWithCustomSizedTexture(this.field_73746_c + this.field_73747_a / 2 - fontrenderer.func_78256_a(this.field_73744_e) / 2 - 8, this.field_73743_d + (this.field_73745_b - 16) / 2, this.iconU, this.iconV, 16, 16, 512.0f, 512.0f, false);
            this.func_73731_b(fontrenderer, this.field_73744_e, this.field_73746_c + this.field_73747_a / 2 - fontrenderer.func_78256_a(this.field_73744_e) / 2 + 9, this.field_73743_d + (this.field_73745_b - 8) / 2, l);
        }
    }
}

