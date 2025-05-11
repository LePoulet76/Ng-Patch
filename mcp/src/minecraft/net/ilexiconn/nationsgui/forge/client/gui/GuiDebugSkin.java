/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiTextField
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui;

import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.itemskin.AbstractSkin;
import net.ilexiconn.nationsgui.forge.client.itemskin.BadgeSkin;
import net.ilexiconn.nationsgui.forge.client.itemskin.CapeSkin;
import net.ilexiconn.nationsgui.forge.client.itemskin.SkinType;
import net.ilexiconn.nationsgui.forge.client.util.Transform;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.opengl.GL11;

public class GuiDebugSkin
extends GuiScreen {
    public static final int COLLUMNS = 30;
    private static final int TILE_SIZE = 25;
    private static final int TILE_OFFSET = 2;
    private GuiTextField fieldX;
    private GuiTextField fieldY;
    private GuiTextField fieldZ;
    private GuiTextField rotateX;
    private GuiTextField rotateY;
    private GuiTextField rotateZ;
    private GuiTextField scale;
    private Transform transform;
    private String selectedSkin;

    public void func_73866_w_() {
        this.fieldX = new DebugTextField(this.field_73886_k, 25, this.field_73881_g - 25, 50, 15, "oX");
        this.fieldX.func_73782_a("0.0");
        this.fieldY = new DebugTextField(this.field_73886_k, 85, this.field_73881_g - 25, 50, 15, "oY");
        this.fieldY.func_73782_a("0.0");
        this.fieldZ = new DebugTextField(this.field_73886_k, 145, this.field_73881_g - 25, 50, 15, "oZ");
        this.fieldZ.func_73782_a("0.0");
        this.rotateX = new DebugTextField(this.field_73886_k, 205, this.field_73881_g - 25, 50, 15, "rX");
        this.rotateX.func_73782_a("0.0");
        this.rotateY = new DebugTextField(this.field_73886_k, 265, this.field_73881_g - 25, 50, 15, "rY");
        this.rotateY.func_73782_a("0.0");
        this.rotateZ = new DebugTextField(this.field_73886_k, 325, this.field_73881_g - 25, 50, 15, "rZ");
        this.rotateZ.func_73782_a("0.0");
        this.scale = new DebugTextField(this.field_73886_k, 385, this.field_73881_g - 25, 50, 15, "scale");
        this.scale.func_73782_a("1.0");
    }

    public void func_73863_a(int par1, int par2, float par3) {
        int i = 0;
        for (SkinType value : SkinType.values()) {
            for (AbstractSkin skin : value.getSkins()) {
                if (skin instanceof BadgeSkin || skin instanceof CapeSkin) continue;
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                boolean active = skin.getTransform("gui") == this.transform;
                int rX = i % 30 * 27;
                int rY = 25 + i / 30 * 27;
                GL11.glPushMatrix();
                GL11.glTranslatef((float)0.0f, (float)0.0f, (float)-50.0f);
                GuiDebugSkin.func_73734_a((int)rX, (int)rY, (int)(rX + 25), (int)(rY + 25), (int)(active ? -65451 : 0x50FF0000));
                GL11.glPopMatrix();
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                skin.renderInGUI(i % 30 * 27, 25 + i / 30 * 27, 1.0f, par3);
                ++i;
            }
        }
        GL11.glPushMatrix();
        GL11.glTranslatef((float)0.0f, (float)0.0f, (float)500.0f);
        this.fieldX.func_73795_f();
        this.fieldY.func_73795_f();
        this.fieldZ.func_73795_f();
        this.rotateX.func_73795_f();
        this.rotateY.func_73795_f();
        this.rotateZ.func_73795_f();
        this.scale.func_73795_f();
        GL11.glPopMatrix();
        if (this.selectedSkin != null) {
            ModernGui.drawScaledString(this.selectedSkin, 445, this.field_73881_g - 25, 0xFFFFFF, 1.0f, false, true);
        }
    }

    protected void func_73869_a(char par1, int par2) {
        super.func_73869_a(par1, par2);
        this.fieldX.func_73802_a(par1, par2);
        this.fieldY.func_73802_a(par1, par2);
        this.fieldZ.func_73802_a(par1, par2);
        this.rotateX.func_73802_a(par1, par2);
        this.rotateY.func_73802_a(par1, par2);
        this.rotateZ.func_73802_a(par1, par2);
        this.scale.func_73802_a(par1, par2);
        if (this.transform == null) {
            return;
        }
        try {
            this.transform.setOffsetX(Double.parseDouble(this.fieldX.func_73781_b()));
            this.transform.setOffsetY(Double.parseDouble(this.fieldY.func_73781_b()));
            this.transform.setOffsetZ(Double.parseDouble(this.fieldZ.func_73781_b()));
            this.transform.setRotateX(Double.parseDouble(this.rotateX.func_73781_b()));
            this.transform.setRotateY(Double.parseDouble(this.rotateY.func_73781_b()));
            this.transform.setRotateZ(Double.parseDouble(this.rotateZ.func_73781_b()));
            this.transform.setScale(Double.parseDouble(this.scale.func_73781_b()));
        }
        catch (NumberFormatException numberFormatException) {
            // empty catch block
        }
    }

    protected void func_73864_a(int par1, int par2, int par3) {
        super.func_73864_a(par1, par2, par3);
        this.fieldX.func_73793_a(par1, par2, par3);
        this.fieldY.func_73793_a(par1, par2, par3);
        this.fieldZ.func_73793_a(par1, par2, par3);
        this.rotateX.func_73793_a(par1, par2, par3);
        this.rotateY.func_73793_a(par1, par2, par3);
        this.rotateZ.func_73793_a(par1, par2, par3);
        this.scale.func_73793_a(par1, par2, par3);
        int i = 0;
        for (SkinType value : SkinType.values()) {
            for (AbstractSkin skin : value.getSkins()) {
                if (skin instanceof BadgeSkin || skin instanceof CapeSkin) continue;
                int x = i % 30 * 27;
                int y = 25 + i / 30 * 27;
                if (par1 >= x && par1 <= x + 25 && par2 >= y && par2 <= y + 25 && par2 < this.field_73881_g - 25) {
                    this.transform = skin.getTransform("gui");
                    this.fieldX.func_73782_a(Double.toString(this.transform.getOffsetX()));
                    this.fieldY.func_73782_a(Double.toString(this.transform.getOffsetY()));
                    this.fieldZ.func_73782_a(Double.toString(this.transform.getOffsetZ()));
                    this.rotateX.func_73782_a(Double.toString(this.transform.getRotateX()));
                    this.rotateY.func_73782_a(Double.toString(this.transform.getRotateY()));
                    this.rotateZ.func_73782_a(Double.toString(this.transform.getRotateZ()));
                    this.scale.func_73782_a(Double.toString(this.transform.getScale()));
                    this.selectedSkin = skin.getId();
                }
                ++i;
            }
        }
    }

    private static class DebugTextField
    extends GuiTextField {
        private final int x;
        private final int y;
        private final FontRenderer renderer;
        private final String name;

        public DebugTextField(FontRenderer p_i1032_1_, int p_i1032_2_, int p_i1032_3_, int p_i1032_4_, int p_i1032_5_, String name) {
            super(p_i1032_1_, p_i1032_2_, p_i1032_3_, p_i1032_4_, p_i1032_5_);
            this.x = p_i1032_2_;
            this.y = p_i1032_3_;
            this.renderer = p_i1032_1_;
            this.name = name;
        }

        public void func_73795_f() {
            super.func_73795_f();
            this.renderer.func_78261_a(this.name, this.x, this.y - 10, 0xFFFFFF);
        }
    }
}

