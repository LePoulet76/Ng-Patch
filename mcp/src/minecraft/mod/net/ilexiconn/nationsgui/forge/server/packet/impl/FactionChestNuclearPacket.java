package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.minecraft.entity.player.EntityPlayer;

public class FactionChestNuclearPacket implements IPacket, IClientPacket
{
    public String factionId;
    public boolean hasT4;
    public boolean hasRed;
    public boolean hasFusee;
    private boolean hasT5;

    public FactionChestNuclearPacket(String factionId, boolean hasT4, boolean hasRed, boolean hasFusee, boolean hasT5)
    {
        this.factionId = factionId;
        this.hasT4 = hasT4;
        this.hasRed = hasRed;
        this.hasFusee = hasFusee;
        this.hasT5 = hasT5;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.factionId = data.readUTF();
        this.hasT4 = data.readBoolean();
        this.hasRed = data.readBoolean();
        this.hasFusee = data.readBoolean();
        this.hasT5 = data.readBoolean();
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.factionId);
        data.writeBoolean(this.hasT4);
        data.writeBoolean(this.hasRed);
        data.writeBoolean(this.hasFusee);
        data.writeBoolean(this.hasT5);
    }

    public void handleClientPacket(EntityPlayer player)
    {
        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(this));
    }
}
