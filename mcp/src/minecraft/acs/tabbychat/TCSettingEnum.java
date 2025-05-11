/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package acs.tabbychat;

import acs.tabbychat.TCSetting;
import net.minecraft.client.Minecraft;

public class TCSettingEnum
extends TCSetting {
    protected Enum value;
    protected Enum tempValue;

    public TCSettingEnum(String theLabel, int theID) {
        super(theLabel, theID);
        this.value = null;
        this.tempValue = null;
    }

    public TCSettingEnum(Enum theVar, String theLabel, int theID) {
        super(theLabel, theID);
        mc = Minecraft.func_71410_x();
        this.value = theVar;
        this.tempValue = theVar;
        this.description = theLabel;
        this.type = "enum";
        this.labelX = 0;
        this.field_73747_a = 30;
        this.field_73745_b = 11;
    }

    public void next() {
        Enum[] E = (Enum[])this.tempValue.getClass().getEnumConstants();
        Object tmp = this.tempValue.ordinal() == E.length - 1 ? Enum.valueOf(this.tempValue.getClass(), E[0].name()) : Enum.valueOf(this.tempValue.getClass(), E[this.tempValue.ordinal() + 1].name());
        this.tempValue = tmp;
    }

    public void previous() {
        Enum[] E = (Enum[])this.tempValue.getClass().getEnumConstants();
        this.tempValue = this.tempValue.ordinal() == 0 ? Enum.valueOf(this.tempValue.getClass(), E[E.length - 1].name()) : Enum.valueOf(this.tempValue.getClass(), E[this.tempValue.ordinal() - 1].name());
    }

    @Override
    public void save() {
        this.value = Enum.valueOf(this.tempValue.getClass(), this.tempValue.name());
    }

    @Override
    public void reset() {
        this.tempValue = Enum.valueOf(this.value.getClass(), this.value.name());
    }

    public void setValue(Enum theVal) {
        this.value = Enum.valueOf(theVal.getClass(), theVal.name());
    }

    public void setTempValue(Enum theVal) {
        this.tempValue = Enum.valueOf(theVal.getClass(), theVal.name());
    }

    @Override
    public Enum getValue() {
        return this.value;
    }

    @Override
    public Enum getTempValue() {
        return this.tempValue;
    }

    @Override
    public void mouseClicked(int par1, int par2, int par3) {
        if (this.hovered(par1, par2).booleanValue() && this.field_73742_g) {
            if (par3 == 1) {
                this.previous();
            } else if (par3 == 0) {
                this.next();
            }
        }
    }

    @Override
    public void actionPerformed() {
    }

    @Override
    public void func_73737_a(Minecraft par1, int cursorX, int cursorY) {
        int centerX = this.field_73746_c + this.field_73747_a / 2;
        int centerY = this.field_73743_d + this.field_73745_b / 2;
        int fgcolor = -1717526368;
        if (!this.field_73742_g) {
            fgcolor = 0x66A0A0A0;
        } else if (this.hovered(cursorX, cursorY).booleanValue()) {
            fgcolor = -1711276128;
        }
        int labelColor = this.field_73742_g ? 0xFFFFFF : 0x666666;
        TCSettingEnum.func_73734_a((int)(this.field_73746_c + 1), (int)this.field_73743_d, (int)(this.field_73746_c + this.field_73747_a - 1), (int)(this.field_73743_d + 1), (int)fgcolor);
        TCSettingEnum.func_73734_a((int)(this.field_73746_c + 1), (int)(this.field_73743_d + this.field_73745_b - 1), (int)(this.field_73746_c + this.field_73747_a - 1), (int)(this.field_73743_d + this.field_73745_b), (int)fgcolor);
        TCSettingEnum.func_73734_a((int)this.field_73746_c, (int)(this.field_73743_d + 1), (int)(this.field_73746_c + 1), (int)(this.field_73743_d + this.field_73745_b - 1), (int)fgcolor);
        TCSettingEnum.func_73734_a((int)(this.field_73746_c + this.field_73747_a - 1), (int)(this.field_73743_d + 1), (int)(this.field_73746_c + this.field_73747_a), (int)(this.field_73743_d + this.field_73745_b - 1), (int)fgcolor);
        TCSettingEnum.func_73734_a((int)(this.field_73746_c + 1), (int)(this.field_73743_d + 1), (int)(this.field_73746_c + this.field_73747_a - 1), (int)(this.field_73743_d + this.field_73745_b - 1), (int)-16777216);
        this.func_73732_a(Minecraft.func_71410_x().field_71466_p, this.tempValue.toString(), centerX, this.field_73743_d + 2, labelColor);
        this.func_73732_a(Minecraft.func_71410_x().field_71466_p, this.description, this.labelX + Minecraft.func_71410_x().field_71466_p.func_78256_a(this.description) / 2, this.field_73743_d + (this.field_73745_b - 6) / 2, labelColor);
    }
}

