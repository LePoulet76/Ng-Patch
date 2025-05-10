package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.ilexiconn.nationsgui.forge.server.trade.TradeData;
import net.ilexiconn.nationsgui.forge.server.trade.enums.EnumTradeState;
import net.minecraft.entity.player.EntityPlayer;

public class TradeAcceptPacket implements IPacket, IServerPacket
{
    public void handleServerPacket(EntityPlayer player)
    {
        TradeData data = TradeData.get(player);

        if (!data.isTrading())
        {
            data.closeTrade();
        }
        else
        {
            data.setState(EnumTradeState.STARTED);
            TradeData datatrader = TradeData.get(data.tradePlayer);
            datatrader.setState(EnumTradeState.STARTED);
            player.openGui(NationsGUI.INSTANCE, 777, player.worldObj, 0, 0, 0);
            data.tradePlayer.openGui(NationsGUI.INSTANCE, 777, player.worldObj, 0, 0, 0);
        }
    }

    public void fromBytes(ByteArrayDataInput data) {}

    public void toBytes(ByteArrayDataOutput data) {}
}
