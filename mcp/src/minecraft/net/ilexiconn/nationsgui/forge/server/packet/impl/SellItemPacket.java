package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;

public class SellItemPacket implements IPacket
{
    private int slotID;
    private int price;
    private int quantity;
    private boolean pub;

    public SellItemPacket(int slotID, int price, int quantity, boolean pub)
    {
        this.slotID = slotID;
        this.price = price;
        this.quantity = quantity;
        this.pub = pub;
    }

    public SellItemPacket() {}

    public void fromBytes(ByteArrayDataInput data) {}

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeInt(this.slotID);
        data.writeInt(this.price);
        data.writeInt(this.quantity);
        data.writeInt(this.pub ? 1 : 0);
    }
}
