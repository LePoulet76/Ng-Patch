/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package acs.tabbychat;

import acs.tabbychat.TCSetting;
import net.minecraft.client.Minecraft;

public class TCSettingBool
extends TCSetting {
    protected int buttonOnColor = -1146755100;
    protected int buttonOffColor = -1728053248;
    protected Boolean value;
    protected Boolean tempValue;
    private static Minecraft mc;

    public TCSettingBool(String theLabel, int theID) {
        this(false, theLabel, theID);
    }

    public TCSettingBool(Boolean theSetting, String theLabel, int theID) {
        super(theID, 0, 0, "");
        this.type = "bool";
        mc = Minecraft.func_71410_x();
        this.value = theSetting;
        this.tempValue = new Boolean(this.value);
        this.description = theLabel;
        this.labelX = 0;
        this.field_73747_a = 9;
        this.field_73745_b = 9;
    }

    public void setValue(Boolean theVal) {
        this.value = theVal;
    }

    public void setTempValue(Boolean theVal) {
        this.tempValue = theVal;
    }

    @Override
    public Boolean getValue() {
        return (boolean)this.value;
    }

    @Override
    public Boolean getTempValue() {
        return (boolean)this.tempValue;
    }

    @Override
    public void save() {
        this.value = (boolean)this.tempValue;
    }

    @Override
    public void reset() {
        this.tempValue = (boolean)this.value;
    }

    public void toggle() {
        this.tempValue = this.tempValue == false;
    }

    @Override
    public void actionPerformed() {
        this.toggle();
    }

    @Override
    public void func_73737_a(Minecraft par1, int cursorX, int cursorY) {
        int centerX = this.field_73746_c + this.field_73747_a / 2;
        int centerY = this.field_73743_d + this.field_73745_b / 2;
        int tmpWidth = 9;
        int tmpHeight = 9;
        int tmpX = centerX - 4;
        int tmpY = centerY - 4;
        int fgcolor = -1717526368;
        if (!this.field_73742_g) {
            fgcolor = 0x66A0A0A0;
        } else if (this.hovered(cursorX, cursorY).booleanValue()) {
            fgcolor = -1711276128;
        }
        int labelColor = this.field_73742_g ? 0xFFFFFF : 0x666666;
        TCSettingBool.func_73734_a((int)(tmpX + 1), (int)tmpY, (int)(tmpX + tmpWidth - 1), (int)(tmpY + 1), (int)fgcolor);
        TCSettingBool.func_73734_a((int)(tmpX + 1), (int)(tmpY + tmpHeight - 1), (int)(tmpX + tmpWidth - 1), (int)(tmpY + tmpHeight), (int)fgcolor);
        TCSettingBool.func_73734_a((int)tmpX, (int)(tmpY + 1), (int)(tmpX + 1), (int)(tmpY + tmpHeight - 1), (int)fgcolor);
        TCSettingBool.func_73734_a((int)(tmpX + tmpWidth - 1), (int)(tmpY + 1), (int)(tmpX + tmpWidth), (int)(tmpY + tmpHeight - 1), (int)fgcolor);
        TCSettingBool.func_73734_a((int)(tmpX + 1), (int)(tmpY + 1), (int)(tmpX + tmpWidth - 1), (int)(tmpY + tmpHeight - 1), (int)-16777216);
        if (this.tempValue.booleanValue()) {
            TCSettingBool.func_73734_a((int)(centerX - 2), (int)centerY, (int)(centerX - 1), (int)(centerY + 1), (int)this.buttonOnColor);
            TCSettingBool.func_73734_a((int)(centerX - 1), (int)(centerY + 1), (int)centerX, (int)(centerY + 2), (int)this.buttonOnColor);
            TCSettingBool.func_73734_a((int)centerX, (int)(centerY + 2), (int)(centerX + 1), (int)(centerY + 3), (int)this.buttonOnColor);
            TCSettingBool.func_73734_a((int)(centerX + 1), (int)(centerY + 2), (int)(centerX + 2), (int)centerY, (int)this.buttonOnColor);
            TCSettingBool.func_73734_a((int)(centerX + 2), (int)centerY, (int)(centerX + 3), (int)(centerY - 2), (int)this.buttonOnColor);
            TCSettingBool.func_73734_a((int)(centerX + 3), (int)(centerY - 2), (int)(centerX + 4), (int)(centerY - 4), (int)this.buttonOnColor);
        }
        this.func_73732_a(Minecraft.func_71410_x().field_71466_p, this.description, this.labelX + Minecraft.func_71410_x().field_71466_p.func_78256_a(this.description) / 2, this.field_73743_d + (this.field_73745_b - 6) / 2, labelColor);
    }
}

