package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class NoelMegaGiftOverlayDataPacket implements IPacket, IClientPacket
{
    public long timeSpawn;

    public void fromBytes(ByteArrayDataInput data)
    {
        this.timeSpawn = data.readLong();
    }

    public void toBytes(ByteArrayDataOutput data) {}

    public void handleClientPacket(EntityPlayer player)
    {
        ClientData.noelMegaGiftTimeSpawn = Long.valueOf(this.timeSpawn);
    }
}
