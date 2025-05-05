package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class FactionGetImagePacket implements IPacket, IClientPacket
{
    public String image;
    public String name;

    public FactionGetImagePacket(String name)
    {
        this.name = name;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.name = data.readUTF();
        this.image = data.readUTF();
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.name);
    }

    public void handleClientPacket(EntityPlayer player)
    {
        ClientProxy.base64FlagsByFactionName.put(this.name, this.image);
    }
}
