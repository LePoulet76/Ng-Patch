package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class IslandTeamKickPlayerPacket implements IPacket, IClientPacket
{
    public String islandId;
    public String teamId;
    public String playerName;

    public IslandTeamKickPlayerPacket(String islandId, String teamId, String playerName)
    {
        this.islandId = islandId;
        this.teamId = teamId;
        this.playerName = playerName;
    }

    public void fromBytes(ByteArrayDataInput data) {}

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.islandId);
        data.writeUTF(this.teamId);
        data.writeUTF(this.playerName);
    }

    public void handleClientPacket(EntityPlayer player) {}
}
