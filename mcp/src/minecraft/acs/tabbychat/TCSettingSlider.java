/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.input.Mouse
 */
package acs.tabbychat;

import acs.tabbychat.TCSetting;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class TCSettingSlider
extends TCSetting {
    protected Float value;
    protected Float tempValue;
    protected float minValue;
    protected float maxValue;
    protected float sliderValue;
    private int sliderX;
    protected int buttonOnColor = -1146755100;
    protected int buttonOffColor = 0x44FFFFFF;
    public String units = "%";
    private boolean dragging = false;

    public TCSettingSlider(String theLabel, int theID) {
        this(Float.valueOf(0.0f), theLabel, theID);
    }

    public TCSettingSlider(Float theSetting, String theLabel, int theID) {
        super(theID, 0, 0, "");
        this.type = "slider";
        mc = Minecraft.func_71410_x();
        this.value = theSetting;
        this.description = theLabel;
        this.labelX = 0;
        this.field_73747_a = 100;
        this.field_73745_b = 11;
        this.tempValue = this.value;
        this.sliderValue = (this.tempValue.floatValue() - this.minValue) / (this.maxValue - this.minValue);
    }

    public TCSettingSlider(Float theSetting, String theLabel, int theID, float minVal, float maxVal) {
        this(theSetting, theLabel, theID);
        this.minValue = minVal;
        this.maxValue = maxVal;
        this.sliderValue = (this.tempValue.floatValue() - this.minValue) / (this.maxValue - this.minValue);
    }

    @Override
    public void setButtonDims(int wide, int tall) {
        this.field_73747_a = wide;
        this.field_73745_b = tall;
    }

    public void setValue(Float theVal) {
        this.value = theVal;
    }

    public void setRange(Float theMin, Float theMax) {
        this.minValue = theMin.floatValue();
        this.maxValue = theMax.floatValue();
        this.sliderValue = (this.tempValue.floatValue() - this.minValue) / (this.maxValue - this.minValue);
    }

    public void setTempValue(Float theVal) {
        this.tempValue = theVal;
        this.sliderValue = (this.tempValue.floatValue() - this.minValue) / (this.maxValue - this.minValue);
    }

    @Override
    public Float getValue() {
        return this.value;
    }

    @Override
    public Float getTempValue() {
        this.tempValue = Float.valueOf(this.sliderValue * (this.maxValue - this.minValue) + this.minValue);
        return this.tempValue;
    }

    @Override
    public void save() {
        this.value = this.tempValue = Float.valueOf(this.sliderValue * (this.maxValue - this.minValue) + this.minValue);
    }

    @Override
    public void reset() {
        this.tempValue = this.value;
        this.sliderValue = (this.tempValue.floatValue() - this.minValue) / (this.maxValue - this.minValue);
    }

    public void handleMouseInput() {
        int mY;
        if (Minecraft.func_71410_x().field_71462_r == null) {
            return;
        }
        int mX = Mouse.getEventX() * Minecraft.func_71410_x().field_71462_r.field_73880_f / Minecraft.func_71410_x().field_71443_c;
        if (!this.hovered(mX, mY = Minecraft.func_71410_x().field_71462_r.field_73881_g - Mouse.getEventY() * Minecraft.func_71410_x().field_71462_r.field_73881_g / Minecraft.func_71410_x().field_71440_d - 1).booleanValue()) {
            return;
        }
        int var1 = Mouse.getEventDWheel();
        if (var1 != 0) {
            if (var1 > 1) {
                var1 = 3;
            }
            if (var1 < -1) {
                var1 = -3;
            }
            if (Keyboard.isKeyDown((int)42) || Keyboard.isKeyDown((int)54)) {
                var1 *= -7;
            }
        }
        this.sliderValue += (float)var1 / 100.0f;
        if (this.sliderValue < 0.0f) {
            this.sliderValue = 0.0f;
        } else if (this.sliderValue > 1.0f) {
            this.sliderValue = 1.0f;
        }
        this.tempValue = Float.valueOf(this.sliderValue * (this.maxValue - this.minValue) + this.minValue);
    }

    @Override
    public void mouseClicked(int par1, int par2, int par3) {
        if (par3 == 0 && this.hovered(par1, par2).booleanValue() && this.field_73742_g) {
            this.sliderX = par1 - 1;
            this.sliderValue = (float)(this.sliderX - (this.field_73746_c + 1)) / (float)(this.field_73747_a - 5);
            if (this.sliderValue < 0.0f) {
                this.sliderValue = 0.0f;
            } else if (this.sliderValue > 1.0f) {
                this.sliderValue = 1.0f;
            }
            if (!this.dragging) {
                this.dragging = true;
            }
        }
    }

    public void func_73740_a(int par1, int par2) {
        this.dragging = false;
    }

    @Override
    public void func_73737_a(Minecraft par1, int cursorX, int cursorY) {
        int fgcolor = -1717526368;
        if (!this.field_73742_g) {
            fgcolor = 0x66A0A0A0;
        } else if (this.hovered(cursorX, cursorY).booleanValue()) {
            fgcolor = -1711276128;
            if (this.dragging) {
                this.sliderX = cursorX - 1;
                this.sliderValue = (float)(this.sliderX - (this.field_73746_c + 1)) / (float)(this.field_73747_a - 5);
                if (this.sliderValue < 0.0f) {
                    this.sliderValue = 0.0f;
                } else if (this.sliderValue > 1.0f) {
                    this.sliderValue = 1.0f;
                }
            }
        }
        int labelColor = this.field_73742_g ? 0xFFFFFF : 0x666666;
        int buttonColor = this.field_73742_g ? this.buttonOnColor : this.buttonOffColor;
        TCSettingSlider.func_73734_a((int)this.field_73746_c, (int)(this.field_73743_d + 1), (int)(this.field_73746_c + 1), (int)(this.field_73743_d + this.field_73745_b - 1), (int)fgcolor);
        TCSettingSlider.func_73734_a((int)(this.field_73746_c + 1), (int)this.field_73743_d, (int)(this.field_73746_c + this.field_73747_a - 1), (int)(this.field_73743_d + 1), (int)fgcolor);
        TCSettingSlider.func_73734_a((int)(this.field_73746_c + 1), (int)(this.field_73743_d + this.field_73745_b - 1), (int)(this.field_73746_c + this.field_73747_a - 1), (int)(this.field_73743_d + this.field_73745_b), (int)fgcolor);
        TCSettingSlider.func_73734_a((int)(this.field_73746_c + this.field_73747_a - 1), (int)(this.field_73743_d + 1), (int)(this.field_73746_c + this.field_73747_a), (int)(this.field_73743_d + this.field_73745_b - 1), (int)fgcolor);
        TCSettingSlider.func_73734_a((int)(this.field_73746_c + 1), (int)(this.field_73743_d + 1), (int)(this.field_73746_c + this.field_73747_a - 1), (int)(this.field_73743_d + this.field_73745_b - 1), (int)-16777216);
        this.sliderX = Math.round(this.sliderValue * (float)(this.field_73747_a - 5)) + this.field_73746_c + 1;
        TCSettingSlider.func_73734_a((int)this.sliderX, (int)(this.field_73743_d + 1), (int)(this.sliderX + 1), (int)(this.field_73743_d + 2), (int)(buttonColor & 0x88FFFFFF));
        TCSettingSlider.func_73734_a((int)(this.sliderX + 1), (int)(this.field_73743_d + 1), (int)(this.sliderX + 2), (int)(this.field_73743_d + 2), (int)buttonColor);
        TCSettingSlider.func_73734_a((int)(this.sliderX + 2), (int)(this.field_73743_d + 1), (int)(this.sliderX + 3), (int)(this.field_73743_d + 2), (int)(buttonColor & 0x88FFFFFF));
        TCSettingSlider.func_73734_a((int)this.sliderX, (int)(this.field_73743_d + 2), (int)(this.sliderX + 1), (int)(this.field_73743_d + this.field_73745_b - 2), (int)buttonColor);
        TCSettingSlider.func_73734_a((int)(this.sliderX + 1), (int)(this.field_73743_d + 2), (int)(this.sliderX + 2), (int)(this.field_73743_d + this.field_73745_b - 2), (int)(buttonColor & 0x88FFFFFF));
        TCSettingSlider.func_73734_a((int)(this.sliderX + 2), (int)(this.field_73743_d + 2), (int)(this.sliderX + 3), (int)(this.field_73743_d + this.field_73745_b - 2), (int)buttonColor);
        TCSettingSlider.func_73734_a((int)this.sliderX, (int)(this.field_73743_d + this.field_73745_b - 2), (int)(this.sliderX + 1), (int)(this.field_73743_d + this.field_73745_b - 1), (int)(buttonColor & 0x88FFFFFF));
        TCSettingSlider.func_73734_a((int)(this.sliderX + 1), (int)(this.field_73743_d + this.field_73745_b - 2), (int)(this.sliderX + 2), (int)(this.field_73743_d + this.field_73745_b - 1), (int)buttonColor);
        TCSettingSlider.func_73734_a((int)(this.sliderX + 2), (int)(this.field_73743_d + this.field_73745_b - 2), (int)(this.sliderX + 3), (int)(this.field_73743_d + this.field_73745_b - 1), (int)(buttonColor & 0x88FFFFFF));
        int valCenter = 0;
        valCenter = this.sliderValue < 0.5f ? Math.round(0.7f * (float)this.field_73747_a) : Math.round(0.2f * (float)this.field_73747_a);
        String valLabel = Integer.toString(Math.round(this.sliderValue * (this.maxValue - this.minValue) + this.minValue)) + this.units;
        this.func_73732_a(Minecraft.func_71410_x().field_71466_p, valLabel, valCenter + this.field_73746_c, this.field_73743_d + 2, buttonColor);
        this.func_73732_a(Minecraft.func_71410_x().field_71466_p, this.description, this.labelX + Minecraft.func_71410_x().field_71466_p.func_78256_a(this.description) / 2, this.field_73743_d + (this.field_73745_b - 6) / 2, labelColor);
    }
}

