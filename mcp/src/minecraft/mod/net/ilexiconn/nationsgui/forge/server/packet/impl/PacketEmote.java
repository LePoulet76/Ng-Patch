package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class PacketEmote implements IPacket, IClientPacket
{
    public String emoteName;
    public String playerName;

    public PacketEmote(String name, String player)
    {
        this.emoteName = name;
        this.playerName = player;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.emoteName = data.readUTF();
        this.playerName = data.readUTF();
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.emoteName);
        data.writeUTF(this.playerName);
    }

    public void handleClientPacket(EntityPlayer player)
    {
        ClientProxy.getEmotes().handlePacket(this.emoteName, this.playerName);
    }
}
