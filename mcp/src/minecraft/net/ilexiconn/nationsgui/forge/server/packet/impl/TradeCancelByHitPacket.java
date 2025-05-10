package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketCallbacks;
import net.ilexiconn.nationsgui.forge.server.trade.TradeManager;
import net.ilexiconn.nationsgui.forge.server.trade.enums.EnumPacketServer;
import net.minecraft.entity.player.EntityPlayer;

public class TradeCancelByHitPacket implements IPacket, IClientPacket, IServerPacket
{
    public TradeCancelByHitPacket() {}

    public TradeCancelByHitPacket(String trader) {}

    public void fromBytes(ByteArrayDataInput data) {}

    public void toBytes(ByteArrayDataOutput data) {}

    public void handleClientPacket(EntityPlayer player)
    {
        PacketCallbacks.MONEY.send(new String[0]);
        TradeManager.sendData(EnumPacketServer.TRADE_CANCEL, 0);
    }

    public void handleServerPacket(EntityPlayer player) {}
}
