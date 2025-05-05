package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;

public class EnterpriseDisbandPacket implements IPacket
{
    public String enterpriseName;

    public EnterpriseDisbandPacket(String factionId)
    {
        this.enterpriseName = factionId;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.enterpriseName = data.readUTF();
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.enterpriseName);
    }
}
