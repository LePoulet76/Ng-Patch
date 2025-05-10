package net.ilexiconn.nationsgui.forge.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.GuiScreenTemporaryResourcePackSelect;
import net.minecraft.client.resources.I18n;

public class GuiTextureRefused extends GuiScreen
{
    private GuiScreen previous;

    public GuiTextureRefused(GuiScreen previous)
    {
        this.previous = previous;
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.drawBackground(0);
        this.drawCenteredString(this.fontRenderer, I18n.getString("textureRefused.title"), this.width / 2, 25, 16777215);
        this.drawCenteredString(this.fontRenderer, I18n.getString("textureRefused.text"), this.width / 2, 75, 16777215);
        super.drawScreen(par1, par2, par3);
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height - 30, I18n.getString("textureRefused.button")));
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        if (par1GuiButton.id == 0)
        {
            this.mc.displayGuiScreen(new GuiScreenTemporaryResourcePackSelect(this.previous, this.mc.gameSettings));
        }
    }
}
