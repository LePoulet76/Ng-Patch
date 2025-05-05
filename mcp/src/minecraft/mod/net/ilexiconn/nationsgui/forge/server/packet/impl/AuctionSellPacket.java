package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;

public class AuctionSellPacket implements IPacket
{
    private int slotID;
    private int price;
    private int quantity;
    private long duration;
    private boolean pub;

    public AuctionSellPacket(int slotID, int price, int quantity, long duration, boolean pub)
    {
        this.slotID = slotID;
        this.price = price;
        this.quantity = quantity;
        this.duration = duration;
        this.pub = pub;
    }

    public void fromBytes(ByteArrayDataInput data) {}

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeInt(this.slotID);
        data.writeInt(this.price);
        data.writeInt(this.quantity);
        data.writeLong(this.duration);
        data.writeInt(this.pub ? 1 : 0);
    }
}
