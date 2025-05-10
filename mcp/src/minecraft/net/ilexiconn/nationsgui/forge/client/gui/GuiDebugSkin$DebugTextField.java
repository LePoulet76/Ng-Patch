package net.ilexiconn.nationsgui.forge.client.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;

class GuiDebugSkin$DebugTextField extends GuiTextField
{
    private final int x;
    private final int y;
    private final FontRenderer renderer;
    private final String name;

    public GuiDebugSkin$DebugTextField(FontRenderer par1FontRenderer, int par2, int par3, int par4, int par5, String name)
    {
        super(par1FontRenderer, par2, par3, par4, par5);
        this.x = par2;
        this.y = par3;
        this.renderer = par1FontRenderer;
        this.name = name;
    }

    /**
     * Draws the textbox
     */
    public void drawTextBox()
    {
        super.drawTextBox();
        this.renderer.drawStringWithShadow(this.name, this.x, this.y - 10, 16777215);
    }
}
