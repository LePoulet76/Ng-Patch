package net.ilexiconn.nationsgui.forge.client.gui.main.component;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.halalaboos.cfont.CFontRenderer;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;

@SideOnly(Side.CLIENT)
public class CustomInputFieldGUI extends GuiTextField
{
    private final String customFont;
    private final int fontSize;
    private int width;
    private int height;
    private int cursorCounter;
    public int posX;
    public int posY;
    private CFontRenderer fontRenderer;

    public CustomInputFieldGUI(int x, int y, int width, int height, String customFont, int fontSize)
    {
        super(Minecraft.getMinecraft().fontRenderer, x, y, width, height);
        this.posX = x;
        this.posY = y;
        this.width = width;
        this.height = height;
        this.customFont = customFont;
        this.fontSize = fontSize;
        this.fontRenderer = ModernGui.getCustomFont(customFont, Integer.valueOf(fontSize));
    }

    /**
     * Call this method from you GuiScreen to process the keys into textbox.
     */
    public boolean textboxKeyTyped(char par1, int par2)
    {
        return super.textboxKeyTyped(par1, par2);
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
        String text = this.getText();
        char[] flag = text.toCharArray();
        int i = flag.length;

        for (int var6 = 0; var6 < i; ++var6)
        {
            char character = flag[var6];

            if (character != 13 && character != 10)
            {
                line = line + (char)character;
            }
            else
            {
                ModernGui.drawScaledStringCustomFont(line, (float)(this.posX + 4), (float)(this.posY + 4), 14737632, 0.5F, "left", false, this.customFont, this.fontSize);
                line = "";
                ++x;
            }
        }

        ModernGui.drawScaledStringCustomFont(line, (float)(this.posX + 4), (float)(this.posY + 4), 14737632, 0.5F, "left", false, this.customFont, this.fontSize);
        boolean var10 = this.isFocused() && this.cursorCounter / 6 % 2 == 0;
        i = 0;
        x = 0;
        line = "";

        if (var10 && 0 == text.length())
        {
            ModernGui.drawScaledStringCustomFont("_", (float)(this.posX + 3 + (int)this.fontRenderer.getStringWidth(line) / 2), (float)(this.posY + this.fontRenderer.getHeight() / 2), 14737632, 0.5F, "left", false, this.customFont, this.fontSize);
        }

        char[] var11 = text.toCharArray();
        int var12 = var11.length;

        for (int var8 = 0; var8 < var12; ++var8)
        {
            char character1 = var11[var8];
            ++i;

            if (character1 != 13 && character1 != 10)
            {
                line = line + character1;
            }
            else
            {
                line = "";
                ++x;
            }

            if (var10 && i == text.length())
            {
                ModernGui.drawScaledStringCustomFont("_", (float)(this.posX + 3 + (int)this.fontRenderer.getStringWidth(line) / 2), (float)(this.posY + this.fontRenderer.getHeight() / 2), 14737632, 0.5F, "left", false, this.customFont, this.fontSize);
            }
        }
    }

    /**
     * Args: x, y, buttonClicked
     */
    public void mouseClicked(int par1, int par2, int par3)
    {
        boolean flag = par1 >= this.posX && par1 < this.posX + this.width && par2 >= this.posY && par2 < this.posY + this.height;
        this.setFocused(flag);

        if (this.getText().equals("0") && this.isFocused())
        {
            this.setText("");
        }

        if (this.isFocused() && par3 == 0)
        {
            int l = par1 - this.posX;

            if (this.getEnableBackgroundDrawing())
            {
                l -= 4;
            }

            this.setCursorPosition(this.trimStringToWidth(this.getText(), l).length());
        }
    }

    public String trimStringToWidth(String str, int width)
    {
        String line = "";
        char[] var4 = this.getText().toCharArray();
        int var5 = var4.length;

        for (int var6 = 0; var6 < var5; ++var6)
        {
            char character = var4[var6];
            line = line + (char)character;

            if (this.fontRenderer.getStringWidth(line) / 2.0F >= (float)width)
            {
                return line;
            }
        }

        return line;
    }
}
