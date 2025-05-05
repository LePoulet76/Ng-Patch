package net.ilexiconn.nationsgui.forge.server.packet.impl;

import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.ilexiconn.nationsgui.forge.client.gui.island.IslandSettingsGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

class IslandMainDataPacket$8 implements GuiScreenTab
{
    final IslandMainDataPacket this$0;

    IslandMainDataPacket$8(IslandMainDataPacket this$0)
    {
        this.this$0 = this$0;
    }

    public Class <? extends GuiScreen > getClassReferent()
    {
        return IslandSettingsGui.class;
    }

    public void call()
    {
        Minecraft.getMinecraft().displayGuiScreen(new IslandSettingsGui());
    }
}
