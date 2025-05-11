/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 */
package acs.tabbychat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class TCSetting
extends GuiButton {
    protected int labelX;
    protected String description;
    protected String type;
    protected Object value;
    protected Object tempValue;
    protected static Minecraft mc;

    public TCSetting(String theLabel, int theID) {
        super(theID, 0, 0, "");
    }

    public TCSetting(int theID, int theX, int theY, String theLabel) {
        super(theID, theX, theY, theLabel);
    }

    protected void setValue(Object updateVal) {
        this.value = updateVal;
    }

    protected void setTempValue(Object updateVal) {
        this.tempValue = updateVal;
    }

    protected Object getValue() {
        return this.value;
    }

    protected Object getTempValue() {
        return this.tempValue;
    }

    public void enable() {
        this.field_73742_g = true;
    }

    public void disable() {
        this.field_73742_g = false;
    }

    public void setButtonLoc(int bx, int by) {
        this.field_73746_c = bx;
        this.field_73743_d = by;
    }

    public void setButtonDims(int wide, int tall) {
        this.field_73747_a = wide;
        this.field_73745_b = tall;
    }

    public void save() {
    }

    public void reset() {
    }

    protected Boolean hovered(int cursorX, int cursorY) {
        return cursorX >= this.field_73746_c && cursorY >= this.field_73743_d && cursorX < this.field_73746_c + this.field_73747_a && cursorY < this.field_73743_d + this.field_73745_b;
    }

    public void mouseClicked(int par1, int par2, int par3) {
    }

    public void actionPerformed() {
    }

    public void func_73737_a(Minecraft mc, int cursorX, int cursorY) {
    }
}

