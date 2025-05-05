package acs.tabbychat;

import net.minecraft.client.Minecraft;

public class TCSettingBool extends TCSetting
{
    protected int buttonOnColor;
    protected int buttonOffColor;
    protected Boolean value;
    protected Boolean tempValue;
    private static Minecraft mc;

    public TCSettingBool(String theLabel, int theID)
    {
        this(Boolean.valueOf(false), theLabel, theID);
    }

    public TCSettingBool(Boolean theSetting, String theLabel, int theID)
    {
        super(theID, 0, 0, "");
        this.buttonOnColor = -1146755100;
        this.buttonOffColor = -1728053248;
        this.type = "bool";
        mc = Minecraft.getMinecraft();
        this.value = theSetting;
        this.tempValue = new Boolean(this.value.booleanValue());
        this.description = theLabel;
        this.labelX = 0;
        this.width = 9;
        this.height = 9;
    }

    public void setValue(Boolean theVal)
    {
        this.value = theVal;
    }

    public void setTempValue(Boolean theVal)
    {
        this.tempValue = theVal;
    }

    public Boolean getValue()
    {
        return Boolean.valueOf(this.value.booleanValue());
    }

    public Boolean getTempValue()
    {
        return Boolean.valueOf(this.tempValue.booleanValue());
    }

    public void save()
    {
        this.value = Boolean.valueOf(this.tempValue.booleanValue());
    }

    public void reset()
    {
        this.tempValue = Boolean.valueOf(this.value.booleanValue());
    }

    public void toggle()
    {
        this.tempValue = Boolean.valueOf(!this.tempValue.booleanValue());
    }

    public void actionPerformed()
    {
        this.toggle();
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft par1, int cursorX, int cursorY)
    {
        int centerX = this.xPosition + this.width / 2;
        int centerY = this.yPosition + this.height / 2;
        byte tmpWidth = 9;
        byte tmpHeight = 9;
        int tmpX = centerX - 4;
        int tmpY = centerY - 4;
        int fgcolor = -1717526368;

        if (!this.enabled)
        {
            fgcolor = 1721802912;
        }
        else if (this.hovered(cursorX, cursorY).booleanValue())
        {
            fgcolor = -1711276128;
        }

        int labelColor = this.enabled ? 16777215 : 6710886;
        drawRect(tmpX + 1, tmpY, tmpX + tmpWidth - 1, tmpY + 1, fgcolor);
        drawRect(tmpX + 1, tmpY + tmpHeight - 1, tmpX + tmpWidth - 1, tmpY + tmpHeight, fgcolor);
        drawRect(tmpX, tmpY + 1, tmpX + 1, tmpY + tmpHeight - 1, fgcolor);
        drawRect(tmpX + tmpWidth - 1, tmpY + 1, tmpX + tmpWidth, tmpY + tmpHeight - 1, fgcolor);
        drawRect(tmpX + 1, tmpY + 1, tmpX + tmpWidth - 1, tmpY + tmpHeight - 1, -16777216);

        if (this.tempValue.booleanValue())
        {
            drawRect(centerX - 2, centerY, centerX - 1, centerY + 1, this.buttonOnColor);
            drawRect(centerX - 1, centerY + 1, centerX, centerY + 2, this.buttonOnColor);
            drawRect(centerX, centerY + 2, centerX + 1, centerY + 3, this.buttonOnColor);
            drawRect(centerX + 1, centerY + 2, centerX + 2, centerY, this.buttonOnColor);
            drawRect(centerX + 2, centerY, centerX + 3, centerY - 2, this.buttonOnColor);
            drawRect(centerX + 3, centerY - 2, centerX + 4, centerY - 4, this.buttonOnColor);
        }

        this.drawCenteredString(Minecraft.getMinecraft().fontRenderer, this.description, this.labelX + Minecraft.getMinecraft().fontRenderer.getStringWidth(this.description) / 2, this.yPosition + (this.height - 6) / 2, labelColor);
    }

    public Object getTempValue()
    {
        return this.getTempValue();
    }

    public Object getValue()
    {
        return this.getValue();
    }
}
