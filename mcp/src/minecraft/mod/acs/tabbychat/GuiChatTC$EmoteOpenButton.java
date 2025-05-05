package acs.tabbychat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

class GuiChatTC$EmoteOpenButton extends GuiButton
{
    final GuiChatTC this$0;

    public GuiChatTC$EmoteOpenButton(GuiChatTC var1, int id, int posX, int posY, int width, int height)
    {
        super(id, posX, posY, width, height, "\u263a");
        this.this$0 = var1;
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft par1Minecraft, int par2, int par3)
    {
        this.field_82253_i = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
        int l = 14737632;

        if (this.field_82253_i)
        {
            l = 16777120;
        }

        float _mult = GuiChatTC.access$000(this.this$0).gameSettings.chatOpacity * 0.9F + 0.1F;
        int _opacity = (int)(255.0F * _mult);
        drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, _opacity / 2 << 24);
        int var10003 = this.xPosition + this.width / 2;
        int var10004 = this.yPosition + (this.height - 8) / 2;
        this.drawCenteredString(Minecraft.getMinecraft().fontRenderer, this.displayString, var10003, var10004, l);
    }
}
