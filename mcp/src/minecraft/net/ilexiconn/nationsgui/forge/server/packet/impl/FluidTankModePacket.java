package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import fr.nationsglory.server.block.entity.GCFluidTankBlockEntity;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class FluidTankModePacket implements IPacket, IServerPacket
{
    private int posX;
    private int posY;
    private int posZ;
    private String mode;

    public FluidTankModePacket(int posX, int posY, int posZ, String mode)
    {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.mode = mode;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.posX = data.readInt();
        this.posY = data.readInt();
        this.posZ = data.readInt();
        this.mode = data.readUTF();
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeInt(this.posX);
        data.writeInt(this.posY);
        data.writeInt(this.posZ);
        data.writeUTF(this.mode);
    }

    public void handleServerPacket(EntityPlayer player)
    {
        TileEntity tileEntity = player.getEntityWorld().getBlockTileEntity(this.posX, this.posY, this.posZ);

        if (tileEntity instanceof GCFluidTankBlockEntity)
        {
            ((GCFluidTankBlockEntity)tileEntity).mode = this.mode;
        }
    }
}
