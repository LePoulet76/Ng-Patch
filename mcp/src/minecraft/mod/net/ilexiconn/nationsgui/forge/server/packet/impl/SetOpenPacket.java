package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.server.block.entity.RadioBlockEntity;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class SetOpenPacket implements IPacket, IServerPacket
{
    private int x;
    private int y;
    private int z;
    private boolean canOpen;

    public SetOpenPacket(RadioBlockEntity blockEntity)
    {
        this.x = blockEntity.xCoord;
        this.y = blockEntity.yCoord;
        this.z = blockEntity.zCoord;
        this.canOpen = blockEntity.canOpen;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.x = data.readInt();
        this.y = data.readInt();
        this.z = data.readInt();
        this.canOpen = data.readBoolean();
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeInt(this.x);
        data.writeInt(this.y);
        data.writeInt(this.z);
        data.writeBoolean(this.canOpen);
    }

    public void handleServerPacket(EntityPlayer player)
    {
        TileEntity tileEntity = player.worldObj.getBlockTileEntity(this.x, this.y, this.z);

        if (tileEntity != null)
        {
            ((RadioBlockEntity)tileEntity).canOpen = this.canOpen;
        }
    }
}
