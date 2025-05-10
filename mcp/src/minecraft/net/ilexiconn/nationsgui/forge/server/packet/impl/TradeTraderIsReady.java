package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.client.gui.trade.GuiTrade;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class TradeTraderIsReady implements IPacket, IClientPacket
{
    public void handleClientPacket(EntityPlayer player)
    {
        if (mc.currentScreen instanceof GuiTrade)
        {
            GuiTrade gui = (GuiTrade)mc.currentScreen;
            gui.traderIsReady = true;
            gui.lastInteraction = System.currentTimeMillis();
        }
    }

    public void fromBytes(ByteArrayDataInput data) {}

    public void toBytes(ByteArrayDataOutput data) {}
}
