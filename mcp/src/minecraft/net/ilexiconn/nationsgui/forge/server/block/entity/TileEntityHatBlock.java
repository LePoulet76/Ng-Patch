package net.ilexiconn.nationsgui.forge.server.block.entity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

public class TileEntityHatBlock extends TileEntity
{
    private String hatID = "";

    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound par1nbtTagCompound)
    {
        if (!this.worldObj.isRemote && this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord) != 3590)
        {
            this.worldObj.removeBlockTileEntity(this.xCoord, this.yCoord, this.zCoord);
        }

        super.readFromNBT(par1nbtTagCompound);
        NBTTagCompound data = par1nbtTagCompound.getCompoundTag("data");

        if (data != null)
        {
            this.hatID = data.getString("HatID");
        }
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound par1nbtTagCompound)
    {
        super.writeToNBT(par1nbtTagCompound);
        NBTTagCompound data = new NBTTagCompound();
        data.setString("HatID", this.hatID);
        par1nbtTagCompound.setCompoundTag("data", data);
    }

    /**
     * Overriden in a sign to provide the text.
     */
    public Packet getDescriptionPacket()
    {
        NBTTagCompound data = new NBTTagCompound();
        this.writeToNBT(data);
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 3, data);
    }

    public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt)
    {
        this.readFromNBT(pkt.data);
    }

    public String getHatID()
    {
        return this.hatID;
    }

    public void setHatID(String hatID)
    {
        this.hatID = hatID;
    }
}
