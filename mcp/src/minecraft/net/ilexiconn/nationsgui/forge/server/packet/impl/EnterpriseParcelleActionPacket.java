package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.minecraft.entity.player.EntityPlayer;

public class EnterpriseParcelleActionPacket implements IPacket, IClientPacket
{
    public String enterpriseName;
    public String parcelleName;
    public String action;

    public EnterpriseParcelleActionPacket(String enterpriseName, String parcelleName, String action)
    {
        this.enterpriseName = enterpriseName;
        this.parcelleName = parcelleName;
        this.action = action;
    }

    public void fromBytes(ByteArrayDataInput data) {}

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.enterpriseName);
        data.writeUTF(this.parcelleName);
        data.writeUTF(this.action);
    }

    public void handleClientPacket(EntityPlayer player)
    {
        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new EnterpriseParcelleDataPacket((String)EnterpriseGui.enterpriseInfos.get("name"))));
    }
}
