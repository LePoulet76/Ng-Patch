package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.server.block.entity.RadioBlockEntity;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;

public class SetSourcePacket implements IPacket, IServerPacket
{
    private int x;
    private int y;
    private int z;
    private String source;

    public SetSourcePacket(RadioBlockEntity blockEntity)
    {
        this.x = blockEntity.xCoord;
        this.y = blockEntity.yCoord;
        this.z = blockEntity.zCoord;
        this.source = blockEntity.source;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.x = data.readInt();
        this.y = data.readInt();
        this.z = data.readInt();
        this.source = data.readUTF();
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeInt(this.x);
        data.writeInt(this.y);
        data.writeInt(this.z);
        data.writeUTF(this.source);
    }

    public void handleServerPacket(EntityPlayer player)
    {
        TileEntity tileEntity = player.worldObj.getBlockTileEntity(this.x, this.y, this.z);

        if (tileEntity != null && MinecraftServer.getServer().getConfigurationManager().isPlayerOpped(player.username))
        {
            ((RadioBlockEntity)tileEntity).source = this.source;
        }
    }
}
