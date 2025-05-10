package net.ilexiconn.nationsgui.forge.server.packet;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.packet.Packet;

public enum PacketRegistry
{
    INSTANCE;
    public static final String CHANNEL = "nationsgui";
    public List < Class <? extends IPacket >> packetList = new ArrayList();

    public void registerPacket(Class <? extends IPacket > packetClass)
    {
        this.packetList.add(packetClass);
    }

    public Packet generatePacket(IPacket packet)
    {
        if (!this.packetList.contains(packet.getClass()))
        {
            throw new RuntimeException("Attempted to send an unregistered packet!");
        }
        else
        {
            ByteArrayDataOutput data = ByteStreams.newDataOutput();
            data.writeInt(this.packetList.indexOf(packet.getClass()));
            packet.toBytes(data);
            return PacketDispatcher.getPacket("nationsgui", data.toByteArray());
        }
    }
}
