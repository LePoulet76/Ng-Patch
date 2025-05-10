package net.ilexiconn.nationsgui.forge.client.gui.summary.component;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.gui.summary.SummaryGUI;
import net.ilexiconn.nationsgui.forge.client.gui.summary.component.SummaryButtonGUI$Type;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class SummaryButtonGUI extends GuiButton
{
    private SummaryButtonGUI$Type type;

    public SummaryButtonGUI(int id, int x, int y, SummaryButtonGUI$Type type)
    {
        super(id, x, y, 20, 20, "");
        this.type = type;
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft mc, int mouseX, int mouseY)
    {
        this.field_82253_i = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
        int hoverState = this.getHoverState(this.field_82253_i);
        mc.getTextureManager().bindTexture(SummaryGUI.TEXTURE);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.drawTexturedModalRect(this.xPosition, this.yPosition, 151 + this.type.ordinal() * this.width, hoverState * this.height, this.width, this.height);
    }

    /**
     * Returns 0 if the button is disabled, 1 if the mouse is NOT hovering over this button and 2 if it IS hovering over
     * this button.
     */
    protected int getHoverState(boolean hovered)
    {
        byte state = 0;

        if (!this.enabled)
        {
            state = 2;
        }
        else if (hovered)
        {
            state = 1;
        }

        return state;
    }
}
