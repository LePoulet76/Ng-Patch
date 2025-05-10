package net.ilexiconn.nationsgui.forge.client.gui;

import net.minecraft.client.gui.GuiScreen;

public class SwitchGUI extends GuiScreen
{
    private GuiScreen target;

    public SwitchGUI(GuiScreen target)
    {
        this.target = target;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        this.mc.displayGuiScreen(this.target);
    }
}
