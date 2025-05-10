package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import fr.nationsglory.server.block.entity.GCElectricCollectorBlockEntity;
import fr.nationsglory.server.block.entity.GCRandomBlockEntity;
import fr.nationsglory.server.block.entity.GCTraderBlockEntity;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class SetEnterpriseMachinePacket implements IPacket, IServerPacket, IClientPacket
{
    public int posX;
    public int posY;
    public int posZ;
    public String enterpriseName;

    public SetEnterpriseMachinePacket(int posX, int posY, int posZ, String enterpriseName)
    {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.enterpriseName = enterpriseName;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.posX = data.readInt();
        this.posY = data.readInt();
        this.posZ = data.readInt();
        this.enterpriseName = data.readUTF();
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeInt(this.posX);
        data.writeInt(this.posY);
        data.writeInt(this.posZ);
        data.writeUTF(this.enterpriseName);
    }

    public void handleServerPacket(EntityPlayer player)
    {
        TileEntity tileEntity = player.getEntityWorld().getBlockTileEntity(this.posX, this.posY, this.posZ);

        if (tileEntity instanceof GCTraderBlockEntity)
        {
            ((GCTraderBlockEntity)tileEntity).enterpriseName = this.enterpriseName;
        }
        else if (tileEntity instanceof GCRandomBlockEntity)
        {
            ((GCRandomBlockEntity)tileEntity).enterpriseName = this.enterpriseName;
        }
        else if (tileEntity instanceof GCElectricCollectorBlockEntity)
        {
            ((GCElectricCollectorBlockEntity)tileEntity).enterpriseName = this.enterpriseName;
        }
    }

    public void handleClientPacket(EntityPlayer player)
    {
        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(this));
    }
}
