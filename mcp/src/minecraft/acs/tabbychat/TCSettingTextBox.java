/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiTextField
 */
package acs.tabbychat;

import acs.tabbychat.TCSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;

public class TCSettingTextBox
extends TCSetting {
    protected String value;
    protected GuiTextField textBox;
    protected int charLimit = 32;

    public TCSettingTextBox(String theLabel, int theID) {
        this("", theLabel, theID);
    }

    public TCSettingTextBox(String theSetting, String theLabel, int theID) {
        super(theID, 0, 0, "");
        this.type = "textbox";
        mc = Minecraft.func_71410_x();
        this.value = theSetting;
        this.description = theLabel;
        this.labelX = 0;
        this.field_73747_a = 50;
        this.field_73745_b = 11;
        this.textBox = new GuiTextField(TCSettingTextBox.mc.field_71466_p, 0, 0, this.field_73747_a, this.field_73745_b);
        this.textBox.func_73782_a(this.value);
    }

    private void reassignField() {
        String tmp = this.textBox.func_73781_b();
        this.textBox = new GuiTextField(TCSettingTextBox.mc.field_71466_p, this.field_73746_c, this.field_73743_d + 1, this.field_73747_a, this.field_73745_b + 1);
        this.textBox.func_73804_f(this.charLimit);
        this.textBox.func_73782_a(tmp);
    }

    public void setValue(String theVal) {
        this.value = theVal;
    }

    public void setTempValue(String theVal) {
        this.textBox.func_73782_a(theVal);
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public String getTempValue() {
        return this.textBox.func_73781_b().trim();
    }

    public void setCharLimit(int newLimit) {
        this.charLimit = newLimit;
        this.textBox.func_73804_f(newLimit);
    }

    @Override
    public void save() {
        this.value = this.textBox.func_73781_b().trim();
    }

    @Override
    public void reset() {
        this.textBox.func_73782_a(this.value);
    }

    @Override
    public void setButtonLoc(int bx, int by) {
        this.field_73746_c = bx;
        this.field_73743_d = by;
        this.reassignField();
    }

    @Override
    public void setButtonDims(int wide, int tall) {
        this.field_73747_a = wide;
        this.field_73745_b = tall;
        this.reassignField();
    }

    @Override
    public void enable() {
        this.field_73742_g = true;
        this.textBox.func_82265_c(true);
    }

    @Override
    public void disable() {
        this.field_73742_g = false;
        this.textBox.func_82265_c(false);
    }

    public void enabled(boolean val) {
        this.field_73742_g = val;
        this.textBox.func_82265_c(val);
    }

    protected void keyTyped(char par1, int par2) {
        this.textBox.func_73802_a(par1, par2);
    }

    @Override
    public void mouseClicked(int par1, int par2, int par3) {
        this.textBox.func_73793_a(par1, par2, par3);
    }

    @Override
    public void func_73737_a(Minecraft par1, int cursorX, int cursorY) {
        int labelColor = this.field_73742_g ? 0xFFFFFF : 0x666666;
        this.textBox.func_73795_f();
        this.func_73732_a(Minecraft.func_71410_x().field_71466_p, this.description, this.labelX + Minecraft.func_71410_x().field_71466_p.func_78256_a(this.description) / 2, this.field_73743_d + (this.field_73745_b - 6) / 2, labelColor);
    }
}

