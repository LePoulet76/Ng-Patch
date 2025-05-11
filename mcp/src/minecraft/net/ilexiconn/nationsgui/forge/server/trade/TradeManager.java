/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.common.network.Player
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.network.packet.Packet
 */
package net.ilexiconn.nationsgui.forge.server.trade;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.PlayersInTradePacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.TradeAcceptPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.TradeAddCooldownPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.TradeCancelPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.TradeClosePacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.TradeCompletePacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.TradeIgnorePacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.TradeItemsPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.TradeRejectedPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.TradeRequestPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.TradeStatePacket;
import net.ilexiconn.nationsgui.forge.server.trade.TradeData;
import net.ilexiconn.nationsgui.forge.server.trade.enums.EnumPacketServer;
import net.ilexiconn.nationsgui.forge.server.trade.enums.EnumTradeState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;

public class TradeManager {
    public static void sendData(EnumPacketServer enu, int finalMoney) {
        IServerPacket packet = null;
        switch (enu) {
            case TRADE_ACCEPT: {
                packet = new TradeAcceptPacket();
                break;
            }
            case TRADE_CANCEL: {
                packet = new TradeCancelPacket();
                break;
            }
            case TRADE_COMPLETE: {
                packet = new TradeCompletePacket(finalMoney);
                break;
            }
            case TRADE_IGNORE: {
                packet = new TradeIgnorePacket();
                break;
            }
            case TRADE_REJECTED: {
                packet = new TradeRejectedPacket();
            }
        }
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket((IPacket)((Object)packet)));
    }

    public static void sendRequest(EntityPlayer player, String name) {
        PacketDispatcher.sendPacketToPlayer((Packet)PacketRegistry.INSTANCE.generatePacket(new TradeRequestPacket(name)), (Player)((Player)player));
    }

    public static void sendItems(EntityPlayer player, NBTTagCompound items, String username) {
        PacketDispatcher.sendPacketToPlayer((Packet)PacketRegistry.INSTANCE.generatePacket(new TradeItemsPacket(items, username)), (Player)((Player)player));
        PacketDispatcher.sendPacketToPlayer((Packet)PacketRegistry.INSTANCE.generatePacket(new TradeAddCooldownPacket(1500L)), (Player)((Player)player));
        PacketDispatcher.sendPacketToPlayer((Packet)PacketRegistry.INSTANCE.generatePacket(new TradeAddCooldownPacket(1500L)), (Player)((Player)TradeData.get((EntityPlayer)player).tradePlayer));
    }

    public static void sendCancel(EntityPlayer player, String trader) {
        PacketDispatcher.sendPacketToPlayer((Packet)PacketRegistry.INSTANCE.generatePacket(new TradeCancelPacket(trader)), (Player)((Player)player));
        PacketDispatcher.sendPacketToPlayer((Packet)PacketRegistry.INSTANCE.generatePacket(new PlayersInTradePacket(player.field_71092_bJ, trader, false)), (Player)((Player)player));
    }

    public static void sendClose(EntityPlayer player) {
        PacketDispatcher.sendPacketToPlayer((Packet)PacketRegistry.INSTANCE.generatePacket(new TradeClosePacket(player.field_71092_bJ)), (Player)((Player)player));
    }

    public static void sendState(EntityPlayer player, String trader, EnumTradeState state) {
        PacketDispatcher.sendPacketToPlayer((Packet)PacketRegistry.INSTANCE.generatePacket(new TradeStatePacket(trader, state.ordinal())), (Player)((Player)player));
    }
}

