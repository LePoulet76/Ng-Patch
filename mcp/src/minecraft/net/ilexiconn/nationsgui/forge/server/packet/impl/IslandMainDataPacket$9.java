package net.ilexiconn.nationsgui.forge.server.packet.impl;

import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.ilexiconn.nationsgui.forge.client.gui.island.IslandPropertiesGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

class IslandMainDataPacket$9 implements GuiScreenTab
{
    final IslandMainDataPacket this$0;

    IslandMainDataPacket$9(IslandMainDataPacket this$0)
    {
        this.this$0 = this$0;
    }

    public Class <? extends GuiScreen > getClassReferent()
    {
        return IslandPropertiesGui.class;
    }

    public void call()
    {
        Minecraft.getMinecraft().displayGuiScreen(new IslandPropertiesGui());
    }
}
