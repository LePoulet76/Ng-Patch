/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.common.network.Player
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.packet.Packet
 *  org.bukkit.Bukkit
 *  org.bukkit.plugin.Plugin
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.TradeTraderIsReady;
import net.ilexiconn.nationsgui.forge.server.packet.impl.TradeUpdateMoneyPacket;
import net.ilexiconn.nationsgui.forge.server.trade.ContainerTrade;
import net.ilexiconn.nationsgui.forge.server.trade.TradeData;
import net.ilexiconn.nationsgui.forge.server.trade.TradeManager;
import net.ilexiconn.nationsgui.forge.server.trade.enums.EnumTradeState;
import net.ilexiconn.nationsgui.forge.server.util.Translation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class TradeCompletePacket
implements IPacket,
IServerPacket {
    private int money;

    public TradeCompletePacket(int money) {
        this.money = money;
    }

    @Override
    public void handleServerPacket(EntityPlayer player) {
        TradeData data = TradeData.get(player);
        if (!data.isTrading()) {
            data.closeTrade();
            return;
        }
        if (data.state == EnumTradeState.DONE) {
            return;
        }
        if (Bukkit.getServer() != null) {
            Plugin pl = Bukkit.getPluginManager().getPlugin("NationsGUI");
            if (!this.hasMoney(pl, player.field_71092_bJ, this.money)) {
                return;
            }
        } else {
            return;
        }
        data.setMoney(this.money);
        EntityPlayer trader = data.tradePlayer;
        TradeData traderdata = TradeData.get(trader);
        if (data.state == EnumTradeState.TRADER_ACCEPTED) {
            traderdata.setState(EnumTradeState.DONE);
            data.setState(EnumTradeState.DONE);
            ContainerTrade container1 = (ContainerTrade)player.field_71070_bA;
            ContainerTrade container2 = (ContainerTrade)trader.field_71070_bA;
            if (Bukkit.getServer() != null) {
                Plugin pl = Bukkit.getPluginManager().getPlugin("NationsGUI");
                if (!this.hasMoney(pl, player.field_71092_bJ, data.getMoney()) || !this.hasMoney(pl, trader.field_71092_bJ, traderdata.getMoney())) {
                    data.closeTrade();
                    TradeManager.sendClose(player);
                    TradeManager.sendClose(trader);
                    player.func_71035_c(Translation.get("Action impossible, pas assez d'argent."));
                    trader.func_71035_c(Translation.get("Action impossible, pas assez d'argent."));
                    return;
                }
                if (this.hasMoney(pl, player.field_71092_bJ, data.getMoney())) {
                    this.addMoney(pl, player.field_71092_bJ, -data.getMoney());
                    this.addMoney(pl, trader.field_71092_bJ, data.getMoney());
                }
                if (this.hasMoney(pl, trader.field_71092_bJ, traderdata.getMoney())) {
                    this.addMoney(pl, trader.field_71092_bJ, -traderdata.getMoney());
                    this.addMoney(pl, player.field_71092_bJ, traderdata.getMoney());
                }
            }
            for (int i = 0; i < 14; ++i) {
                ItemStack item = container1.craftMatrix.func_70301_a(i);
                if (container1.craftMatrix.func_70301_a(i) != null) {
                    System.out.println("[TRADE] " + player.getDisplayName() + " GIVE " + container1.craftMatrix.func_70301_a((int)i).field_77993_c + ":" + container1.craftMatrix.func_70301_a(i).func_77960_j() + " x" + container1.craftMatrix.func_70301_a((int)i).field_77994_a + " TO " + trader.getDisplayName());
                }
                if (container2.craftMatrix.func_70301_a(i) != null) {
                    System.out.println("[TRADE] " + trader.getDisplayName() + " GIVE " + container2.craftMatrix.func_70301_a((int)i).field_77993_c + ":" + container2.craftMatrix.func_70301_a(i).func_77960_j() + " x" + container2.craftMatrix.func_70301_a((int)i).field_77994_a + " TO " + player.getDisplayName());
                }
                container1.craftMatrix.func_70299_a(i, container2.craftMatrix.func_70301_a(i));
                container2.craftMatrix.func_70299_a(i, item);
            }
            traderdata.setMoney(0);
            data.setMoney(0);
            TradeManager.sendClose(player);
            TradeManager.sendClose(trader);
        } else {
            traderdata.setState(EnumTradeState.TRADER_ACCEPTED);
            data.setState(EnumTradeState.YOU_ACCEPTED);
            PacketDispatcher.sendPacketToPlayer((Packet)PacketRegistry.INSTANCE.generatePacket(new TradeTraderIsReady()), (Player)((Player)data.tradePlayer));
            PacketDispatcher.sendPacketToPlayer((Packet)PacketRegistry.INSTANCE.generatePacket(new TradeUpdateMoneyPacket(this.money, "", false)), (Player)((Player)data.tradePlayer));
        }
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.money = data.readInt();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeInt(this.money);
    }

    public void addMoney(Plugin pl, String player, int money) {
        try {
            Method m = pl.getClass().getDeclaredMethod("addMoney", String.class, Integer.TYPE);
            m.invoke(pl, player, money);
            if (money != 0) {
                System.out.println("[TRADE] " + player + " " + money);
            }
        }
        catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public boolean hasMoney(Plugin pl, String player, int money) {
        try {
            Method m = pl.getClass().getDeclaredMethod("hasMoney", String.class, Integer.TYPE);
            return (Boolean)m.invoke(pl, player, money);
        }
        catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
            return false;
        }
    }
}

