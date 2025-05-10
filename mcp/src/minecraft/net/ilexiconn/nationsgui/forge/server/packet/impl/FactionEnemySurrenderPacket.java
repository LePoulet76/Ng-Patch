package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.minecraft.entity.player.EntityPlayer;

public class FactionEnemySurrenderPacket implements IPacket, IClientPacket
{
    private Integer requestID;
    private String factionNameToSurrend;

    public FactionEnemySurrenderPacket(Integer requestID, String factionNameToSurrend)
    {
        this.requestID = requestID;
        this.factionNameToSurrend = factionNameToSurrend;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.requestID = Integer.valueOf(data.readInt());
        this.factionNameToSurrend = data.readUTF();
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeInt(this.requestID.intValue());
        data.writeUTF(this.factionNameToSurrend);
    }

    public void handleClientPacket(EntityPlayer player)
    {
        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionWarDataPacket(this.factionNameToSurrend)));
    }
}
