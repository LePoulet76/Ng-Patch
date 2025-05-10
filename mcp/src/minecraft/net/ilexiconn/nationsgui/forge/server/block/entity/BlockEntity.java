package net.ilexiconn.nationsgui.forge.server.block.entity;

import cpw.mods.fml.common.network.PacketDispatcher;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.BlockEntityPacket;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

public abstract class BlockEntity extends TileEntity
{
    private NBTTagCompound lastCompound;
    private int trackingUpdateTimer = 0;

    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    public final void updateEntity()
    {
        int trackingUpdateFrequency = this.getTrackingUpdateTime();

        if (this.trackingUpdateTimer < trackingUpdateFrequency)
        {
            ++this.trackingUpdateTimer;
        }

        if (this.trackingUpdateTimer >= trackingUpdateFrequency)
        {
            this.trackingUpdateTimer = 0;
            NBTTagCompound compound = new NBTTagCompound();
            this.saveTrackingSensitiveData(compound);

            if (!compound.equals(this.lastCompound))
            {
                if (!this.worldObj.isRemote)
                {
                    this.onSync();
                    PacketDispatcher.sendPacketToAllPlayers(PacketRegistry.INSTANCE.generatePacket(new BlockEntityPacket(this)));
                }

                this.lastCompound = compound;
            }
        }

        this.onUpdate();
    }

    /**
     * Overriden in a sign to provide the text.
     */
    public Packet getDescriptionPacket()
    {
        NBTTagCompound compound = new NBTTagCompound();
        this.writeToNBT(compound);
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 0, compound);
    }

    public void onDataPacket(INetworkManager networkManager, Packet132TileEntityData packet)
    {
        this.readFromNBT(packet.data);
    }

    /**
     * Reads a tile entity from NBT.
     */
    public final void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.loadNBTData(compound);
    }

    /**
     * Writes a tile entity to NBT.
     */
    public final void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        this.saveNBTData(compound);
    }

    public void saveTrackingSensitiveData(NBTTagCompound compound)
    {
        this.saveNBTData(compound);
    }

    public void loadTrackingSensitiveData(NBTTagCompound compound)
    {
        this.loadNBTData(compound);
    }

    public abstract void saveNBTData(NBTTagCompound var1);

    public abstract void loadNBTData(NBTTagCompound var1);

    public void onUpdate() {}

    public void onSync() {}

    public int getTrackingUpdateTime()
    {
        return 0;
    }
}
