package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;

public class FactionTeleportPacket implements IPacket
{
    public boolean createFaction;

    public void fromBytes(ByteArrayDataInput data)
    {
        this.createFaction = data.readBoolean();
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeBoolean(this.createFaction);
    }
}
