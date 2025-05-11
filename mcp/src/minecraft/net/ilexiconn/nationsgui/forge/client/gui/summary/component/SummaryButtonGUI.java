/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.summary.component;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.gui.summary.SummaryGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class SummaryButtonGUI
extends GuiButton {
    private Type type;

    public SummaryButtonGUI(int id, int x, int y, Type type) {
        super(id, x, y, 20, 20, "");
        this.type = type;
    }

    public void func_73737_a(Minecraft mc, int mouseX, int mouseY) {
        this.field_82253_i = mouseX >= this.field_73746_c && mouseY >= this.field_73743_d && mouseX < this.field_73746_c + this.field_73747_a && mouseY < this.field_73743_d + this.field_73745_b;
        int hoverState = this.func_73738_a(this.field_82253_i);
        mc.func_110434_K().func_110577_a(SummaryGUI.TEXTURE);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.func_73729_b(this.field_73746_c, this.field_73743_d, 151 + this.type.ordinal() * this.field_73747_a, hoverState * this.field_73745_b, this.field_73747_a, this.field_73745_b);
    }

    protected int func_73738_a(boolean hovered) {
        int state = 0;
        if (!this.field_73742_g) {
            state = 2;
        } else if (hovered) {
            state = 1;
        }
        return state;
    }

    public static enum Type {
        SETTINGS,
        DISCORD,
        TEAMSPEAK,
        LEAVE;

    }
}

