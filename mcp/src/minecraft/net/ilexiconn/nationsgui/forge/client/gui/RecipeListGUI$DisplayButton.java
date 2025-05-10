package net.ilexiconn.nationsgui.forge.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

class RecipeListGUI$DisplayButton extends GuiButton
{
    public boolean en;
    public boolean hovered;

    final RecipeListGUI this$0;

    public RecipeListGUI$DisplayButton(RecipeListGUI var1, int id, int posX, int posY)
    {
        super(id, posX, posY, 14, 14, "");
        this.this$0 = var1;
        this.en = false;
        this.hovered = false;
    }

    /**
     * Returns 0 if the button is disabled, 1 if the mouse is NOT hovering over this button and 2 if it IS hovering over
     * this button.
     */
    protected int getHoverState(boolean par1)
    {
        return par1 ? 0 : 18;
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft par1Minecraft, int par2, int par3)
    {
        if (this.drawButton)
        {
            RecipeListGUI.access$100(this.this$0).getTextureManager().bindTexture(RecipeListGUI.access$000());
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
            int k = this.en ? 0 : 14;
            this.drawTexturedModalRect(this.xPosition, this.yPosition, 206, k, 14, 14);
            this.mouseDragged(par1Minecraft, par2, par3);
        }
    }
}
