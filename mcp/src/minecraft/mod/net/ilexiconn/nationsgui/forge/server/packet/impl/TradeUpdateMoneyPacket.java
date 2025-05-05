package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import net.ilexiconn.nationsgui.forge.client.gui.trade.GuiTrade;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.trade.TradeData;
import net.ilexiconn.nationsgui.forge.server.trade.enums.EnumTradeState;
import net.minecraft.entity.player.EntityPlayer;

public class TradeUpdateMoneyPacket implements IPacket, IClientPacket, IServerPacket
{
    private int money;
    private String trader;
    private boolean force;

    public TradeUpdateMoneyPacket(int money, String trader, boolean force)
    {
        this.money = money;
        this.trader = trader;
        this.force = force;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.money = data.readInt();
        this.trader = data.readUTF();
        this.force = data.readBoolean();
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeInt(this.money);
        data.writeUTF(this.trader);
        data.writeBoolean(this.force);
    }

    public void handleClientPacket(EntityPlayer player)
    {
        GuiTrade gui = (GuiTrade)mc.currentScreen;

        if (gui != null)
        {
            if (this.trader.equals(player.username))
            {
                gui.moneyTrader = this.money;
            }

            if (gui.trader == null)
            {
                gui.trader = player.worldObj.getPlayerEntityByName(this.trader);
            }
        }
    }

    public void handleServerPacket(EntityPlayer player)
    {
        TradeData data = TradeData.get(player);

        if (data.state != EnumTradeState.DONE && data.state != EnumTradeState.WAITING && (data.getMoney() != this.money || this.force))
        {
            data.setMoney(this.money);
            data.setState(EnumTradeState.STARTED);
            TradeData dataTrader = TradeData.get(data.tradePlayer);
            dataTrader.setState(EnumTradeState.STARTED);
            PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new TradeAddCooldownPacket(1500L)), (Player)data.player);
            PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new TradeAddCooldownPacket(1500L)), (Player)data.tradePlayer);
        }
    }
}
