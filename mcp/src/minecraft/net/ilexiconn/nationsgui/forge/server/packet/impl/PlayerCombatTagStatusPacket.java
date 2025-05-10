package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class PlayerCombatTagStatusPacket implements IPacket, IClientPacket
{
    private boolean isTagged;

    public void fromBytes(ByteArrayDataInput data)
    {
        this.isTagged = data.readBoolean();
    }

    public void toBytes(ByteArrayDataOutput data) {}

    public void handleClientPacket(EntityPlayer player)
    {
        ClientData.isCombatTagged = this.isTagged;
    }
}
