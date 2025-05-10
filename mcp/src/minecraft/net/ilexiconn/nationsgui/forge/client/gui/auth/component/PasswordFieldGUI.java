package net.ilexiconn.nationsgui.forge.client.gui.auth.component;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiTextField;

@SideOnly(Side.CLIENT)
public class PasswordFieldGUI extends GuiTextField
{
    private int posX;
    private int posY;
    private int width;
    private int height;
    private int cursorCounter;
    private FontRenderer fontRenderer;

    public PasswordFieldGUI(int x, int y, int width, int height)
    {
        super(Minecraft.getMinecraft().fontRenderer, x, y, width, height);
        this.posX = x;
        this.posY = y;
        this.width = width;
        this.height = height;
        this.fontRenderer = Minecraft.getMinecraft().fontRenderer;
        this.setMaxStringLength(38);
    }

    /**
     * Call this method from you GuiScreen to process the keys into textbox.
     */
    public boolean textboxKeyTyped(char character, int key)
    {
        return character != 32 && character != 10 && super.textboxKeyTyped(character, key);
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
        Gui.drawRect(this.posX - 1, this.posY - 1, this.posX + this.width + 1, this.posY + this.height + 1, -6250336);
        Gui.drawRect(this.posX, this.posY, this.posX + this.width, this.posY + this.height, -16777216);
        int x = 0;
        String line = "";
        String text = this.getText().replaceAll(".", "*");
        char[] flag = text.toCharArray();
        int i = flag.length;

        for (int var6 = 0; var6 < i; ++var6)
        {
            char character = flag[var6];

            if (character != 13 && character != 10)
            {
                if (this.fontRenderer.getStringWidth(line + (char)character) > 191)
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
        boolean var10 = this.isFocused() && this.cursorCounter / 6 % 2 == 0;
        i = 0;
        x = 0;
        line = "";

        if (var10 && 0 == text.length())
        {
            this.fontRenderer.drawString("_", this.posX + 3 + this.fontRenderer.getStringWidth(line), this.posY + 4 + x * this.fontRenderer.FONT_HEIGHT, 14737632);
        }

        char[] var11 = text.toCharArray();
        int var12 = var11.length;

        for (int var8 = 0; var8 < var12; ++var8)
        {
            char character1 = var11[var8];
            ++i;

            if (character1 != 13 && character1 != 10)
            {
                if (this.fontRenderer.getStringWidth(line + character1) > 191)
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

            if (var10 && i == text.length())
            {
                this.fontRenderer.drawString("_", this.posX + 3 + this.fontRenderer.getStringWidth(line), this.posY + 4 + x * this.fontRenderer.FONT_HEIGHT, 14737632);
            }
        }
    }
}
