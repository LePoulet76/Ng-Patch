package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;

public class LotoGetItemsPacket implements IPacket
{
    public int lotteryId;

    public LotoGetItemsPacket(int lotteryId)
    {
        this.lotteryId = lotteryId;
    }

    public void fromBytes(ByteArrayDataInput data) {}

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeInt(this.lotteryId);
    }
}
