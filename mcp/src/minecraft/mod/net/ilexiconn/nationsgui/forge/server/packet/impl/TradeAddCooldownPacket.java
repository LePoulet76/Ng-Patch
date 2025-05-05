package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.client.gui.trade.GuiTrade;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class TradeAddCooldownPacket implements IPacket, IClientPacket
{
    private long time;

    public TradeAddCooldownPacket(long l)
    {
        this.time = l;
    }

    public void handleClientPacket(EntityPlayer player)
    {
        GuiTrade.addCooldown(this.time);
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.time = data.readLong();
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeLong(this.time);
    }
}
