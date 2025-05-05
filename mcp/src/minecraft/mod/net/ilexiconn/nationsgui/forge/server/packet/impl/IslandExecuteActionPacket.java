package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class IslandExecuteActionPacket implements IPacket, IClientPacket
{
    public String id;
    public String action;

    public IslandExecuteActionPacket(String id, String action)
    {
        this.id = id;
        this.action = action;
    }

    public void fromBytes(ByteArrayDataInput data) {}

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.id);
        data.writeUTF(this.action);
    }

    public void handleClientPacket(EntityPlayer player) {}
}
