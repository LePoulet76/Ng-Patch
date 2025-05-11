/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  net.minecraft.entity.player.EntityPlayer
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.ilexiconn.nationsgui.forge.server.trade.TradeData;
import net.ilexiconn.nationsgui.forge.server.trade.enums.EnumTradeState;
import net.minecraft.entity.player.EntityPlayer;

public class TradeAcceptPacket
implements IPacket,
IServerPacket {
    @Override
    public void handleServerPacket(EntityPlayer player) {
        TradeData data = TradeData.get(player);
        if (!data.isTrading()) {
            data.closeTrade();
            return;
        }
        data.setState(EnumTradeState.STARTED);
        TradeData datatrader = TradeData.get(data.tradePlayer);
        datatrader.setState(EnumTradeState.STARTED);
        player.openGui((Object)NationsGUI.INSTANCE, 777, player.field_70170_p, 0, 0, 0);
        data.tradePlayer.openGui((Object)NationsGUI.INSTANCE, 777, player.field_70170_p, 0, 0, 0);
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
    }
}

