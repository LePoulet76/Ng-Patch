package net.ilexiconn.nationsgui.forge.client.gui;

import net.minecraft.client.gui.GuiScreen;

public interface GuiScreenTab
{
    Class <? extends GuiScreen > getClassReferent();

    void call();
}
