package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.minecraft.entity.player.EntityPlayer;

public class FactionProfilActionPacket implements IPacket, IClientPacket
{
    public String target;
    public String enterpriseName;
    public String action;

    public FactionProfilActionPacket(String targetName, String enterpriseName, String action)
    {
        this.target = targetName;
        this.enterpriseName = enterpriseName;
        this.action = action;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.target = data.readUTF();
        this.enterpriseName = data.readUTF();
        this.action = data.readUTF();
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.target);
        data.writeUTF(this.enterpriseName);
        data.writeUTF(this.action);
    }

    public void handleClientPacket(EntityPlayer player)
    {
        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionProfilDataPacket(this.target, this.enterpriseName)));
    }
}
