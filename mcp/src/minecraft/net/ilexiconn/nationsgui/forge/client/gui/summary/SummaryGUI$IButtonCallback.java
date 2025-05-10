package net.ilexiconn.nationsgui.forge.client.gui.summary;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public interface SummaryGUI$IButtonCallback
{
    void call(Minecraft var1);

    int getSeconds();

    GuiButton getButton();
}
