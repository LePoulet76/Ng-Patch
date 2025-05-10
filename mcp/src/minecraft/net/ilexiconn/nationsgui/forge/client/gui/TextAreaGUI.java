package net.ilexiconn.nationsgui.forge.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;

@SideOnly(Side.CLIENT)
public class TextAreaGUI extends GuiTextField
{
    private final int posX;
    private final int posY;
    private final int width;
    private final FontRenderer fontRenderer;
    private int cursorCounter;

    public TextAreaGUI(int x, int y, int width)
    {
        super(Minecraft.getMinecraft().fontRenderer, x, y, width, 73);
        this.posX = x;
        this.posY = y;
        this.width = width;
        this.fontRenderer = Minecraft.getMinecraft().fontRenderer;
    }

    /**
     * Increments the cursor counter
     */
    public void updateCursorCounter()
    {
        ++this.cursorCounter;
    }

    /**
     * Draws the textbox
     */
    public void drawTextBox()
    {
        int x = 0;
        String line = "";
        char[] flag = this.getText().toCharArray();
        int i = flag.length;

        for (int var5 = 0; var5 < i; ++var5)
        {
            char character = flag[var5];

            if (character != 13 && character != 10)
            {
                if (this.fontRenderer.getStringWidth(line + (char)character) > this.width)
                {
                    this.drawString(this.fontRenderer, line, this.posX + 4, this.posY + 4 + x * this.fontRenderer.FONT_HEIGHT, 14737632);
                    line = "";
                    ++x;
                }

                line = line + (char)character;
            }
            else
            {
                this.drawString(this.fontRenderer, line, this.posX + 4, this.posY + 4 + x * this.fontRenderer.FONT_HEIGHT, 14737632);
                line = "";
                ++x;
            }
        }

        this.drawString(this.fontRenderer, line, this.posX + 4, this.posY + 4 + x * this.fontRenderer.FONT_HEIGHT, 14737632);
        boolean var9 = this.isFocused() && this.cursorCounter / 6 % 2 == 0;
        i = 0;
        x = 0;
        line = "";

        if (var9 && 0 == this.getText().length())
        {
            this.fontRenderer.drawString("_", this.posX + 3 + this.fontRenderer.getStringWidth(line), this.posY + 4 + x * this.fontRenderer.FONT_HEIGHT, 14737632);
        }

        char[] var10 = this.getText().toCharArray();
        int var11 = var10.length;

        for (int var7 = 0; var7 < var11; ++var7)
        {
            char character1 = var10[var7];
            ++i;

            if (character1 != 13 && character1 != 10)
            {
                if (this.fontRenderer.getStringWidth(line + character1) > this.width)
                {
                    line = "";
                    ++x;
                    line = line + character1;
                }
                else
                {
                    line = line + character1;
                }
            }
            else
            {
                line = "";
                ++x;
            }

            if (var9 && i == this.getText().length())
            {
                this.fontRenderer.drawString("_", this.posX + 3 + this.fontRenderer.getStringWidth(line), this.posY + 4 + x * this.fontRenderer.FONT_HEIGHT, 14737632);
            }
        }
    }
}
