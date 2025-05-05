package acs.tabbychat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

class GuiChatTC$Button extends GuiButton
{
    final GuiChatTC this$0;

    public GuiChatTC$Button(GuiChatTC var1, int id, int posX, int posY, int width, int height)
    {
        super(id, posX, posY, width, height, "");
        this.this$0 = var1;
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft par1Minecraft, int par2, int par3)
    {
        this.field_82253_i = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
    }
}
