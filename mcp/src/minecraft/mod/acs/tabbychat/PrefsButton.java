package acs.tabbychat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;

public class PrefsButton extends GuiButton
{
    protected int bgcolor = -587202560;
    protected boolean hasControlCodes = false;
    protected String type;

    public PrefsButton()
    {
        super(9999, 0, 0, 1, 1, "");
    }

    public PrefsButton(int _id, int _x, int _y, int _w, int _h, String _title)
    {
        super(_id, _x, _y, _w, _h, _title);
    }

    public PrefsButton(int _id, int _x, int _y, int _w, int _h, String _title, int _bgcolor)
    {
        super(_id, _x, _y, _w, _h, _title);
        this.bgcolor = _bgcolor;
    }

    protected void title(String newtitle)
    {
        this.displayString = newtitle;
    }

    protected String title()
    {
        return this.displayString;
    }

    protected int width()
    {
        return this.width;
    }

    protected void width(int _w)
    {
        this.width = _w;
    }

    protected int height()
    {
        return this.height;
    }

    protected void height(int _h)
    {
        this.height = _h;
    }

    protected int adjustWidthForControlCodes()
    {
        String cleaned = this.displayString.replaceAll("(?i)\u00a7[0-9A-FK-OR]", "");
        boolean bold = this.displayString.replaceAll("(?i)\u00a7L", "").length() != this.displayString.length();
        int badWidth = Minecraft.getMinecraft().fontRenderer.getStringWidth(this.displayString);
        int goodWidth = Minecraft.getMinecraft().fontRenderer.getStringWidth(cleaned);

        if (bold)
        {
            goodWidth += cleaned.length();
        }

        return badWidth > goodWidth ? badWidth - goodWidth : 0;
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft mc, int cursorX, int cursorY)
    {
        if (this.drawButton)
        {
            FontRenderer fr = mc.fontRenderer;
            drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, this.bgcolor);
            boolean hovered = cursorX >= this.xPosition && cursorY >= this.yPosition && cursorX < this.xPosition + this.width && cursorY < this.yPosition + this.height;
            int var7 = 10526880;

            if (!this.enabled)
            {
                var7 = -6250336;
            }
            else if (hovered)
            {
                var7 = 16777120;
            }

            if (this.hasControlCodes)
            {
                int offset = this.adjustWidthForControlCodes();
                this.drawCenteredString(fr, this.displayString, this.xPosition + (this.width + offset) / 2, this.yPosition + (this.height - 8) / 2, var7);
            }
            else
            {
                this.drawCenteredString(fr, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, var7);
            }
        }
    }
}
