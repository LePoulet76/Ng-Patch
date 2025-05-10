package net.ilexiconn.nationsgui.forge.client.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import net.ilexiconn.nationsgui.forge.client.gui.hdv.MarketGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.MarketPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

final class InventoryGUI$6 implements GuiScreenTab
{
    public Class <? extends GuiScreen > getClassReferent()
    {
        return null;
    }

    public void call()
    {
        Minecraft.getMinecraft().displayGuiScreen(new MarketGui());
        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new MarketPacket("", "", 0, false)));
    }
}
