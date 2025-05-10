package net.ilexiconn.nationsgui.forge.client.gui.auction;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class AuctionSimpleButton extends GuiButton
{
    public AuctionSimpleButton(int par1, int par2, int par3, String par4Str)
    {
        super(par1, par2, par3, par4Str);
    }

    public AuctionSimpleButton(int par1, int par2, int par3, int par4, int par5, String par6Str)
    {
        super(par1, par2, par3, par4, par5, par6Str);
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft par1Minecraft, int par2, int par3)
    {
        this.field_82253_i = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
        int color = this.field_82253_i ? -11119018 : -14737633;
        drawRect(this.xPosition + 1, this.yPosition, this.xPosition + this.width - 1, this.yPosition + this.height, color);
        drawRect(this.xPosition, this.yPosition + 1, this.xPosition + 1, this.yPosition + this.height - 1, color);
        drawRect(this.xPosition + this.width - 1, this.yPosition + 1, this.xPosition + this.width, this.yPosition + this.height - 1, color);
        par1Minecraft.fontRenderer.drawString(this.displayString, this.xPosition + this.width / 2 - par1Minecraft.fontRenderer.getStringWidth(this.displayString) / 2, this.yPosition + this.height / 2 - 3, 16777215);
    }
}
