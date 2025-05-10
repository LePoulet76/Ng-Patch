package net.ilexiconn.nationsgui.forge.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

class ChatGUI$Button extends GuiButton
{
    final ChatGUI this$0;

    public ChatGUI$Button(ChatGUI var1, int id, int posX, int posY, int width, int height)
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

        if (this.field_82253_i)
        {
            drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, 1728053247);
        }
    }
}
