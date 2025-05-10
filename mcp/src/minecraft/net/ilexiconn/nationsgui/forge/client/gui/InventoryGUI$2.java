package net.ilexiconn.nationsgui.forge.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

final class InventoryGUI$2 implements GuiScreenTab
{
    public Class <? extends GuiScreen > getClassReferent()
    {
        return MailGUI.class;
    }

    public void call()
    {
        Minecraft.getMinecraft().displayGuiScreen(new MailGUI(Minecraft.getMinecraft().thePlayer));
    }
}
