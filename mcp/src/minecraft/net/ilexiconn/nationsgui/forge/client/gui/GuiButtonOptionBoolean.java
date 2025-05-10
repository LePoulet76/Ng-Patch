package net.ilexiconn.nationsgui.forge.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;

public abstract class GuiButtonOptionBoolean extends GuiButton implements IButtonOption<Boolean>
{
    private String text;

    public GuiButtonOptionBoolean(int id, int posX, int posY, int width, int height, String text)
    {
        super(id, posX, posY, width, height, "");
        this.text = text;
        this.updateText();
    }

    protected void updateText()
    {
        this.displayString = this.text + " : " + (((Boolean)this.getData()).booleanValue() ? this.getEnabledStateText() : this.getDisabledStateText());
    }

    protected String getEnabledStateText()
    {
        return I18n.getString("options.on");
    }

    protected String getDisabledStateText()
    {
        return I18n.getString("options.off");
    }

    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
     * e).
     */
    public boolean mousePressed(Minecraft par1Minecraft, int par2, int par3)
    {
        if (super.mousePressed(par1Minecraft, par2, par3))
        {
            this.setData(Boolean.valueOf(!((Boolean)this.getData()).booleanValue()));
            this.updateText();
            return true;
        }
        else
        {
            return false;
        }
    }
}
