/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.hdv;

import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class StatsButton
extends GuiButton {
    private static final ResourceLocation BACKGROUND = new ResourceLocation("nationsgui", "textures/gui/market.png");

    public StatsButton(int id, int posX, int posY) {
        super(id, posX, posY, 71, 14, I18n.func_135053_a((String)"hdv.stats"));
    }

    public void func_73737_a(Minecraft par1Minecraft, int par2, int par3) {
        if (this.field_73748_h) {
            par1Minecraft.func_110434_K().func_110577_a(BACKGROUND);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            boolean bl = this.field_82253_i = par2 >= this.field_73746_c && par3 >= this.field_73743_d && par2 < this.field_73746_c + this.field_73747_a && par3 < this.field_73743_d + this.field_73745_b;
            if (this.field_82253_i) {
                GL11.glColor3f((float)0.75f, (float)0.75f, (float)0.75f);
            } else {
                GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
            }
            ModernGui.drawModalRectWithCustomSizedTexture(this.field_73746_c, this.field_73743_d, 36, 373, 71, 14, 372.0f, 400.0f, false);
            ModernGui.drawScaledString(this.field_73744_e, this.field_73746_c + 18, this.field_73743_d + 4, 0xFFFFFF, 1.0f, false, false);
            this.func_73739_b(par1Minecraft, par2, par3);
        }
    }
}

