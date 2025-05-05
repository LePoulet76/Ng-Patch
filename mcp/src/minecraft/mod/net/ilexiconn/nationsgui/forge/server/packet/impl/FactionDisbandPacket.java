package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class FactionDisbandPacket implements IPacket, IClientPacket
{
    public String factionId;

    public FactionDisbandPacket(String factionId)
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

    public void handleClientPacket(EntityPlayer player) {}
}
