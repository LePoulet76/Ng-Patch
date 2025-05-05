package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class WorldNameSyncPacket implements IPacket, IClientPacket
{
    private String worldName;

    public WorldNameSyncPacket(String worldName)
    {
        this.worldName = worldName;
    }

    public void handleClientPacket(EntityPlayer player)
    {
        ClientData.worldName = this.worldName;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.worldName = data.readUTF();
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.worldName);
    }
}
