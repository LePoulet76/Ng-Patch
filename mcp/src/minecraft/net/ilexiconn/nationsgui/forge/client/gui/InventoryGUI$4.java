package net.ilexiconn.nationsgui.forge.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

final class InventoryGUI$4 implements GuiScreenTab
{
    public Class <? extends GuiScreen > getClassReferent()
    {
        return null;
    }

    public void call()
    {
        Minecraft.getMinecraft().displayGuiScreen(new QuestGUI(Minecraft.getMinecraft().thePlayer));
    }
}
