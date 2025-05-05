package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.server.block.entity.ImageHologramBlockEntity;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;

public class ImageHologramSavePacket implements IPacket, IServerPacket
{
    private int x;
    private int y;
    private int z;
    private String url;
    private int imgWidth;
    private int imgHeight;
    private int size;

    public ImageHologramSavePacket(ImageHologramBlockEntity blockEntity)
    {
        this.x = blockEntity.xCoord;
        this.y = blockEntity.yCoord;
        this.z = blockEntity.zCoord;
        this.url = blockEntity.url;
        this.imgWidth = blockEntity.imgWidth;
        this.imgHeight = blockEntity.imgHeight;
        this.size = blockEntity.size;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.x = data.readInt();
        this.y = data.readInt();
        this.z = data.readInt();
        this.url = data.readUTF();
        this.imgWidth = data.readInt();
        this.imgHeight = data.readInt();
        this.size = data.readInt();
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeInt(this.x);
        data.writeInt(this.y);
        data.writeInt(this.z);
        data.writeUTF(this.url);
        data.writeInt(this.imgWidth);
        data.writeInt(this.imgHeight);
        data.writeInt(this.size);
    }

    public void handleServerPacket(EntityPlayer player)
    {
        if (MinecraftServer.getServer().getConfigurationManager().isPlayerOpped(player.username))
        {
            TileEntity tileEntity = player.worldObj.getBlockTileEntity(this.x, this.y, this.z);

            if (tileEntity != null)
            {
                ((ImageHologramBlockEntity)tileEntity).url = this.url;
                ((ImageHologramBlockEntity)tileEntity).imgWidth = this.imgWidth;
                ((ImageHologramBlockEntity)tileEntity).imgHeight = this.imgHeight;
                ((ImageHologramBlockEntity)tileEntity).size = this.size;
            }
        }
    }
}
