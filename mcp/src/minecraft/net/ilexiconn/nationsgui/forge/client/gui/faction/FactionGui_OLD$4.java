package net.ilexiconn.nationsgui.forge.client.gui.faction;

import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

final class FactionGui_OLD$4 implements GuiScreenTab
{
    public Class <? extends GuiScreen > getClassReferent()
    {
        return WarGUI.class;
    }

    public void call()
    {
        Minecraft.getMinecraft().displayGuiScreen(new WarGUI_OLD(Minecraft.getMinecraft().thePlayer));
    }
}
