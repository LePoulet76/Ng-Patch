package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.client.gui.trade.GuiTrade;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.trade.enums.EnumTradeState;
import net.minecraft.entity.player.EntityPlayer;

public class TradeStatePacket implements IPacket, IClientPacket
{
    private String trader;
    private int state;

    public TradeStatePacket(String trader, int state)
    {
        this.trader = trader;
        this.state = state;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.trader = data.readUTF();
        this.state = data.readInt();
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.trader);
        data.writeInt(this.state);
    }

    public void handleClientPacket(EntityPlayer player)
    {
        EntityPlayer trader = player.worldObj.getPlayerEntityByName(this.trader);

        if (trader != null && mc.currentScreen instanceof GuiTrade)
        {
            GuiTrade gui = (GuiTrade)mc.currentScreen;

            if (gui.trader == null)
            {
                gui.trader = trader;
            }

            gui.updateState(EnumTradeState.values()[this.state]);
        }
    }
}
