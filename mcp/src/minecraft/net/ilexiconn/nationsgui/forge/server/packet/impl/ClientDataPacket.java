package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class ClientDataPacket implements IPacket, IClientPacket
{
    private boolean isOp = false;

    public void handleClientPacket(EntityPlayer player)
    {
        ClientData.isOp = this.isOp;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.isOp = data.readBoolean();
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeBoolean(this.isOp);
    }
}
