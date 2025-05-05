package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.minecraft.entity.player.EntityPlayer;

public class IslandTeamRemovePacket implements IPacket, IClientPacket
{
    private int teamId;

    public IslandTeamRemovePacket(int teamId)
    {
        this.teamId = teamId;
    }

    public void fromBytes(ByteArrayDataInput data) {}

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeInt(this.teamId);
    }

    public void handleClientPacket(EntityPlayer player)
    {
        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new IslandTeamsDataPacket(false)));
    }
}
