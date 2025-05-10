package net.ilexiconn.nationsgui.forge.client.gui.radio.component;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.gui.radio.RadioGUI;
import net.ilexiconn.nationsgui.forge.client.gui.summary.SummaryGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GearButtonGUI extends GuiButton
{
    private boolean invert;

    public GearButtonGUI(int id, int x, int y)
    {
        this(id, x, y, false);
    }

    public GearButtonGUI(int id, int x, int y, boolean invert)
    {
        super(id, x, y, 10, 11, "");
        this.invert = invert;
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
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        Minecraft.getMinecraft().getTextureManager().bindTexture(RadioGUI.TEXTURE);
        this.drawTexturedModalRect(this.xPosition, this.yPosition, 233, this.invert ? 70 - hoverState * this.height : 59 + hoverState * this.height, this.width, this.height);
        GL11.glDisable(GL11.GL_BLEND);
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
