package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;

public class FactionBuyCountryPacket implements IPacket
{
    public String factionId;

    public FactionBuyCountryPacket(String factionId)
    {
        this.factionId = factionId;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.factionId = data.readUTF();
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.factionId);
    }
}
