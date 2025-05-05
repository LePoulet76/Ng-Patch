package net.ilexiconn.nationsgui.forge.server.block.entity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.util.AxisAlignedBB;

public class PortalBlockEntity extends BlockEntity
{
    public boolean lastActive = false;
    public boolean active = false;
    public String code = "";
    public String owner = "";

    public void onUpdate()
    {
        if (this.lastActive != this.active)
        {
            this.lastActive = this.active;
            this.worldObj.markBlockForRenderUpdate(this.xCoord, this.yCoord, this.zCoord);
        }
    }

    public void saveNBTData(NBTTagCompound compound)
    {
        compound.setBoolean("active", this.active);
        compound.setString("code", this.code);
        compound.setString("owner", this.owner);
    }

    public void loadNBTData(NBTTagCompound compound)
    {
        if (compound.hasKey("active"))
        {
            this.active = compound.getBoolean("active");
        }

        if (compound.hasKey("code"))
        {
            this.code = compound.getString("code");
        }

        if (compound.hasKey("owner"))
        {
            this.owner = compound.getString("owner");
        }
    }

    public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt)
    {
        this.active = pkt.data.getBoolean("active");
        this.code = pkt.data.getString("code");
        this.owner = pkt.data.getString("owner");
    }

    /**
     * Overriden in a sign to provide the text.
     */
    public Packet getDescriptionPacket()
    {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setBoolean("active", this.active);
        compound.setString("code", this.code);
        compound.setString("owner", this.owner);
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 3, compound);
    }

    public AxisAlignedBB getRenderBoundingBox()
    {
        return INFINITE_EXTENT_AABB;
    }
}
