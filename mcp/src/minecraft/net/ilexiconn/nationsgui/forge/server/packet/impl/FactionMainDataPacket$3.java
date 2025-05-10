package net.ilexiconn.nationsgui.forge.server.packet.impl;

import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.ilexiconn.nationsgui.forge.client.gui.faction.SettingsGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

class FactionMainDataPacket$3 implements GuiScreenTab
{
    final FactionMainDataPacket this$0;

    FactionMainDataPacket$3(FactionMainDataPacket this$0)
    {
        this.this$0 = this$0;
    }

    public Class <? extends GuiScreen > getClassReferent()
    {
        return SettingsGUI.class;
    }

    public void call()
    {
        Minecraft.getMinecraft().displayGuiScreen(new SettingsGUI());
    }
}
