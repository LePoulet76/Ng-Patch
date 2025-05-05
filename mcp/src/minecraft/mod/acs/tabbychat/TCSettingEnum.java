package acs.tabbychat;

import net.minecraft.client.Minecraft;

public class TCSettingEnum extends TCSetting
{
    protected Enum value;
    protected Enum tempValue;

    public TCSettingEnum(String theLabel, int theID)
    {
        super(theLabel, theID);
        this.value = null;
        this.tempValue = null;
    }

    public TCSettingEnum(Enum theVar, String theLabel, int theID)
    {
        super(theLabel, theID);
        mc = Minecraft.getMinecraft();
        this.value = theVar;
        this.tempValue = theVar;
        this.description = theLabel;
        this.type = "enum";
        this.labelX = 0;
        this.width = 30;
        this.height = 11;
    }

    public void next()
    {
        Enum[] E = (Enum[])this.tempValue.getClass().getEnumConstants();
        Enum tmp;

        if (this.tempValue.ordinal() == E.length - 1)
        {
            tmp = Enum.valueOf(this.tempValue.getClass(), E[0].name());
        }
        else
        {
            tmp = Enum.valueOf(this.tempValue.getClass(), E[this.tempValue.ordinal() + 1].name());
        }

        this.tempValue = tmp;
    }

    public void previous()
    {
        Enum[] E = (Enum[])this.tempValue.getClass().getEnumConstants();

        if (this.tempValue.ordinal() == 0)
        {
            this.tempValue = Enum.valueOf(this.tempValue.getClass(), E[E.length - 1].name());
        }
        else
        {
            this.tempValue = Enum.valueOf(this.tempValue.getClass(), E[this.tempValue.ordinal() - 1].name());
        }
    }

    public void save()
    {
        this.value = Enum.valueOf(this.tempValue.getClass(), this.tempValue.name());
    }

    public void reset()
    {
        this.tempValue = Enum.valueOf(this.value.getClass(), this.value.name());
    }

    public void setValue(Enum theVal)
    {
        this.value = Enum.valueOf(theVal.getClass(), theVal.name());
    }

    public void setTempValue(Enum theVal)
    {
        this.tempValue = Enum.valueOf(theVal.getClass(), theVal.name());
    }

    public Enum getValue()
    {
        return this.value;
    }

    public Enum getTempValue()
    {
        return this.tempValue;
    }

    public void mouseClicked(int par1, int par2, int par3)
    {
        if (this.hovered(par1, par2).booleanValue() && this.enabled)
        {
            if (par3 == 1)
            {
                this.previous();
            }
            else if (par3 == 0)
            {
                this.next();
            }
        }
    }

    public void actionPerformed() {}

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft par1, int cursorX, int cursorY)
    {
        int centerX = this.xPosition + this.width / 2;
        int var10000 = this.yPosition + this.height / 2;
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
        drawRect(this.xPosition + 1, this.yPosition, this.xPosition + this.width - 1, this.yPosition + 1, fgcolor);
        drawRect(this.xPosition + 1, this.yPosition + this.height - 1, this.xPosition + this.width - 1, this.yPosition + this.height, fgcolor);
        drawRect(this.xPosition, this.yPosition + 1, this.xPosition + 1, this.yPosition + this.height - 1, fgcolor);
        drawRect(this.xPosition + this.width - 1, this.yPosition + 1, this.xPosition + this.width, this.yPosition + this.height - 1, fgcolor);
        drawRect(this.xPosition + 1, this.yPosition + 1, this.xPosition + this.width - 1, this.yPosition + this.height - 1, -16777216);
        this.drawCenteredString(Minecraft.getMinecraft().fontRenderer, this.tempValue.toString(), centerX, this.yPosition + 2, labelColor);
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
