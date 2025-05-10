package net.ilexiconn.nationsgui.forge.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.gui.ToggleGUI$ISliderCallback;
import net.ilexiconn.nationsgui.forge.client.gui.assistance.AbstractAssistanceGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class ToggleGUI extends GuiButton
{
    private ToggleGUI$ISliderCallback callback;
    private float sliderOffset = 10.0F;
    private boolean active;

    public ToggleGUI(int x, int y, ToggleGUI$ISliderCallback callback, boolean active)
    {
        super(-1, x, y, 27, 14, "");
        this.active = active;
        this.sliderOffset = active ? 0.0F : 10.0F;
        this.callback = callback;
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft mc, int mouseX, int mouseY)
    {
        mc.getTextureManager().bindTexture(AbstractAssistanceGUI.GUI_TEXTURE);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        ModernGui.drawModalRectWithCustomSizedTexture((float)this.xPosition, (float)this.yPosition, 384, 21, this.width, this.height, 512.0F, 512.0F, false);
        GL11.glPushMatrix();
        GL11.glTranslatef(this.sliderOffset, 0.0F, 0.0F);
        ModernGui.drawModalRectWithCustomSizedTexture((float)(this.xPosition + 1), (float)(this.yPosition - 1), 381, 0, 15, 17, 512.0F, 512.0F, false);
        GL11.glPopMatrix();
        GL11.glDisable(GL11.GL_BLEND);
        this.sliderOffset = GUIUtils.interpolate(this.sliderOffset, this.active ? 0.0F : 10.0F, 0.15F);
    }

    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
     * e).
     */
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
    {
        if (super.mousePressed(mc, mouseX, mouseY))
        {
            this.active = !this.active;

            if (this.callback != null)
            {
                this.callback.call(this.active);
            }

            return true;
        }
        else
        {
            return false;
        }
    }
}
