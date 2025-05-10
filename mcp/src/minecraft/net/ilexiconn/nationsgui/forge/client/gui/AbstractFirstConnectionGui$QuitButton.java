package net.ilexiconn.nationsgui.forge.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

class AbstractFirstConnectionGui$QuitButton extends GuiButton
{
    final AbstractFirstConnectionGui this$0;

    public AbstractFirstConnectionGui$QuitButton(AbstractFirstConnectionGui var1, int x, int y)
    {
        super(-1, x, y, 9, 10, "");
        this.this$0 = var1;
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft par1Minecraft, int par2, int par3)
    {
        AbstractFirstConnectionGui.access$000(this.this$0).getTextureManager().bindTexture(AbstractFirstConnectionGui.SHOP_ASSET);
        this.field_82253_i = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
        this.drawTexturedModalRect(this.xPosition, this.yPosition, 204, this.field_82253_i ? 18 : 28, 9, 10);
    }
}
