package net.ilexiconn.nationsgui.forge.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

class ChatGUI$SimpleButton extends GuiButton
{
    final ChatGUI this$0;

    public ChatGUI$SimpleButton(ChatGUI var1, int par1, int par2, int par3, int par4, int par5, String par6Str)
    {
        super(par1, par2, par3, par4, par5, par6Str);
        this.this$0 = var1;
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft par1Minecraft, int par2, int par3)
    {
        this.field_82253_i = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, this.field_82253_i ? 2013265919 : 1442840575);
        ChatGUI.access$100(this.this$0).drawString(this.displayString, this.xPosition + this.width / 2 - ChatGUI.access$000(this.this$0).getStringWidth(this.displayString) / 2, this.yPosition + 4, 16777215);
    }
}
