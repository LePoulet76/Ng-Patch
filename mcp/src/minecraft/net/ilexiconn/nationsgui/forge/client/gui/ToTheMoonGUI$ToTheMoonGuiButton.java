package net.ilexiconn.nationsgui.forge.client.gui;

import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class ToTheMoonGUI$ToTheMoonGuiButton extends GuiButton
{
    private boolean isActive;

    public ToTheMoonGUI$ToTheMoonGuiButton(int par1, int par2, int par3, String par4Str, boolean isActive)
    {
        super(par1, par2, par3, par4Str);
        this.isActive = isActive;
        this.width = 94;
        this.height = 15;
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft mc, int mouseX, int mouseY)
    {
        ClientEventHandler.STYLE.bindTexture("to_the_moon");

        if (this.isActive)
        {
            if (mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height)
            {
                ModernGui.drawModalRectWithCustomSizedTexture((float)this.xPosition, (float)this.yPosition, 0, 190, this.width, this.height, 512.0F, 512.0F, false);
            }
            else
            {
                ModernGui.drawModalRectWithCustomSizedTexture((float)this.xPosition, (float)this.yPosition, 0, 175, this.width, this.height, 512.0F, 512.0F, false);
            }
        }
        else
        {
            ModernGui.drawModalRectWithCustomSizedTexture((float)this.xPosition, (float)this.yPosition, 0, 190, this.width, this.height, 512.0F, 512.0F, false);
        }

        this.drawCenteredString(mc.fontRenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + 4, 16777215);
    }
}
