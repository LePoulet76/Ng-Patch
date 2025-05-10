package net.ilexiconn.nationsgui.forge.client.gui;

import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionListGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

final class InventoryGUI$7 implements GuiScreenTab
{
    public Class <? extends GuiScreen > getClassReferent()
    {
        return null;
    }

    public void call()
    {
        Minecraft.getMinecraft().displayGuiScreen(new FactionListGUI());
    }
}
