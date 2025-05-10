package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class EnterpriseLeavePacket implements IPacket, IClientPacket
{
    String enterpriseName;

    public EnterpriseLeavePacket(String enterpriseName)
    {
        this.enterpriseName = enterpriseName;
    }

    public void fromBytes(ByteArrayDataInput data) {}

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.enterpriseName);
    }

    public void handleClientPacket(EntityPlayer player) {}
}
