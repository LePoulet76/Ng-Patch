package net.ilexiconn.nationsgui.forge.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

final class InventoryGUI$1 implements GuiScreenTab
{
    public Class <? extends GuiScreen > getClassReferent()
    {
        return InventoryGUI.class;
    }

    public void call()
    {
        Minecraft.getMinecraft().displayGuiScreen(new InventoryGUI(Minecraft.getMinecraft().thePlayer));
    }
}
