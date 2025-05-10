package net.ilexiconn.nationsgui.forge.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RecipeListGUI$BookButton extends GuiButton
{
    private static final ResourceLocation BACKGROUND = new ResourceLocation("nationsgui", "textures/gui/craftingtable.png");

    public RecipeListGUI$BookButton(int id, int posX, int posY)
    {
        super(id, posX, posY, 20, 18, "");
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
            Minecraft.getMinecraft().getTextureManager().bindTexture(BACKGROUND);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.field_82253_i = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
            int k = this.getHoverState(this.field_82253_i);
            this.drawTexturedModalRect(this.xPosition, this.yPosition, 145, k, 20, 18);
            this.mouseDragged(par1Minecraft, par2, par3);
        }
    }
}
