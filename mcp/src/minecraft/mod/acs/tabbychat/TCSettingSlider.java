package acs.tabbychat;

import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class TCSettingSlider extends TCSetting
{
    protected Float value;
    protected Float tempValue;
    protected float minValue;
    protected float maxValue;
    protected float sliderValue;
    private int sliderX;
    protected int buttonOnColor;
    protected int buttonOffColor;
    public String units;
    private boolean dragging;

    public TCSettingSlider(String theLabel, int theID)
    {
        this(Float.valueOf(0.0F), theLabel, theID);
    }

    public TCSettingSlider(Float theSetting, String theLabel, int theID)
    {
        super(theID, 0, 0, "");
        this.buttonOnColor = -1146755100;
        this.buttonOffColor = 1157627903;
        this.units = "%";
        this.dragging = false;
        this.type = "slider";
        mc = Minecraft.getMinecraft();
        this.value = theSetting;
        this.description = theLabel;
        this.labelX = 0;
        this.width = 100;
        this.height = 11;
        this.tempValue = this.value;
        this.sliderValue = (this.tempValue.floatValue() - this.minValue) / (this.maxValue - this.minValue);
    }

    public TCSettingSlider(Float theSetting, String theLabel, int theID, float minVal, float maxVal)
    {
        this(theSetting, theLabel, theID);
        this.minValue = minVal;
        this.maxValue = maxVal;
        this.sliderValue = (this.tempValue.floatValue() - this.minValue) / (this.maxValue - this.minValue);
    }

    public void setButtonDims(int wide, int tall)
    {
        this.width = wide;
        this.height = tall;
    }

    public void setValue(Float theVal)
    {
        this.value = theVal;
    }

    public void setRange(Float theMin, Float theMax)
    {
        this.minValue = theMin.floatValue();
        this.maxValue = theMax.floatValue();
        this.sliderValue = (this.tempValue.floatValue() - this.minValue) / (this.maxValue - this.minValue);
    }

    public void setTempValue(Float theVal)
    {
        this.tempValue = theVal;
        this.sliderValue = (this.tempValue.floatValue() - this.minValue) / (this.maxValue - this.minValue);
    }

    public Float getValue()
    {
        return this.value;
    }

    public Float getTempValue()
    {
        this.tempValue = Float.valueOf(this.sliderValue * (this.maxValue - this.minValue) + this.minValue);
        return this.tempValue;
    }

    public void save()
    {
        this.tempValue = Float.valueOf(this.sliderValue * (this.maxValue - this.minValue) + this.minValue);
        this.value = this.tempValue;
    }

    public void reset()
    {
        this.tempValue = this.value;
        this.sliderValue = (this.tempValue.floatValue() - this.minValue) / (this.maxValue - this.minValue);
    }

    public void handleMouseInput()
    {
        if (Minecraft.getMinecraft().currentScreen != null)
        {
            int mX = Mouse.getEventX() * Minecraft.getMinecraft().currentScreen.width / Minecraft.getMinecraft().displayWidth;
            int mY = Minecraft.getMinecraft().currentScreen.height - Mouse.getEventY() * Minecraft.getMinecraft().currentScreen.height / Minecraft.getMinecraft().displayHeight - 1;

            if (this.hovered(mX, mY).booleanValue())
            {
                int var1 = Mouse.getEventDWheel();

                if (var1 != 0)
                {
                    if (var1 > 1)
                    {
                        var1 = 3;
                    }

                    if (var1 < -1)
                    {
                        var1 = -3;
                    }

                    if (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54))
                    {
                        var1 *= -7;
                    }
                }

                this.sliderValue += (float)var1 / 100.0F;

                if (this.sliderValue < 0.0F)
                {
                    this.sliderValue = 0.0F;
                }
                else if (this.sliderValue > 1.0F)
                {
                    this.sliderValue = 1.0F;
                }

                this.tempValue = Float.valueOf(this.sliderValue * (this.maxValue - this.minValue) + this.minValue);
            }
        }
    }

    public void mouseClicked(int par1, int par2, int par3)
    {
        if (par3 == 0 && this.hovered(par1, par2).booleanValue() && this.enabled)
        {
            this.sliderX = par1 - 1;
            this.sliderValue = (float)(this.sliderX - (this.xPosition + 1)) / (float)(this.width - 5);

            if (this.sliderValue < 0.0F)
            {
                this.sliderValue = 0.0F;
            }
            else if (this.sliderValue > 1.0F)
            {
                this.sliderValue = 1.0F;
            }

            if (!this.dragging)
            {
                this.dragging = true;
            }
        }
    }

    /**
     * Fired when the mouse button is released. Equivalent of MouseListener.mouseReleased(MouseEvent e).
     */
    public void mouseReleased(int par1, int par2)
    {
        this.dragging = false;
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft par1, int cursorX, int cursorY)
    {
        int fgcolor = -1717526368;

        if (!this.enabled)
        {
            fgcolor = 1721802912;
        }
        else if (this.hovered(cursorX, cursorY).booleanValue())
        {
            fgcolor = -1711276128;

            if (this.dragging)
            {
                this.sliderX = cursorX - 1;
                this.sliderValue = (float)(this.sliderX - (this.xPosition + 1)) / (float)(this.width - 5);

                if (this.sliderValue < 0.0F)
                {
                    this.sliderValue = 0.0F;
                }
                else if (this.sliderValue > 1.0F)
                {
                    this.sliderValue = 1.0F;
                }
            }
        }

        int labelColor = this.enabled ? 16777215 : 6710886;
        int buttonColor = this.enabled ? this.buttonOnColor : this.buttonOffColor;
        drawRect(this.xPosition, this.yPosition + 1, this.xPosition + 1, this.yPosition + this.height - 1, fgcolor);
        drawRect(this.xPosition + 1, this.yPosition, this.xPosition + this.width - 1, this.yPosition + 1, fgcolor);
        drawRect(this.xPosition + 1, this.yPosition + this.height - 1, this.xPosition + this.width - 1, this.yPosition + this.height, fgcolor);
        drawRect(this.xPosition + this.width - 1, this.yPosition + 1, this.xPosition + this.width, this.yPosition + this.height - 1, fgcolor);
        drawRect(this.xPosition + 1, this.yPosition + 1, this.xPosition + this.width - 1, this.yPosition + this.height - 1, -16777216);
        this.sliderX = Math.round(this.sliderValue * (float)(this.width - 5)) + this.xPosition + 1;
        drawRect(this.sliderX, this.yPosition + 1, this.sliderX + 1, this.yPosition + 2, buttonColor & -1996488705);
        drawRect(this.sliderX + 1, this.yPosition + 1, this.sliderX + 2, this.yPosition + 2, buttonColor);
        drawRect(this.sliderX + 2, this.yPosition + 1, this.sliderX + 3, this.yPosition + 2, buttonColor & -1996488705);
        drawRect(this.sliderX, this.yPosition + 2, this.sliderX + 1, this.yPosition + this.height - 2, buttonColor);
        drawRect(this.sliderX + 1, this.yPosition + 2, this.sliderX + 2, this.yPosition + this.height - 2, buttonColor & -1996488705);
        drawRect(this.sliderX + 2, this.yPosition + 2, this.sliderX + 3, this.yPosition + this.height - 2, buttonColor);
        drawRect(this.sliderX, this.yPosition + this.height - 2, this.sliderX + 1, this.yPosition + this.height - 1, buttonColor & -1996488705);
        drawRect(this.sliderX + 1, this.yPosition + this.height - 2, this.sliderX + 2, this.yPosition + this.height - 1, buttonColor);
        drawRect(this.sliderX + 2, this.yPosition + this.height - 2, this.sliderX + 3, this.yPosition + this.height - 1, buttonColor & -1996488705);
        boolean valCenter = false;
        int valCenter1;

        if (this.sliderValue < 0.5F)
        {
            valCenter1 = Math.round(0.7F * (float)this.width);
        }
        else
        {
            valCenter1 = Math.round(0.2F * (float)this.width);
        }

        String valLabel = Integer.toString(Math.round(this.sliderValue * (this.maxValue - this.minValue) + this.minValue)) + this.units;
        int var10003 = valCenter1 + this.xPosition;
        int var10004 = this.yPosition + 2;
        this.drawCenteredString(Minecraft.getMinecraft().fontRenderer, valLabel, var10003, var10004, buttonColor);
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
