package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;

public class AuctionBidPacket implements IPacket
{
    private String uuid;
    private int bid;

    public AuctionBidPacket(String uuid, int bid)
    {
        this.uuid = uuid;
        this.bid = bid;
    }

    public void fromBytes(ByteArrayDataInput data) {}

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.uuid);
        data.writeInt(this.bid);
    }
}
