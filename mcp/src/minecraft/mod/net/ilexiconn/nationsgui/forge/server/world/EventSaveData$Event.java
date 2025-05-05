package net.ilexiconn.nationsgui.forge.server.world;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;

public class EventSaveData$Event
{
    private String name;
    private int dimensionId;
    private ChunkCoordinates eventCoordinates = null;
    private float rotationPitch;
    private float rotationYaw;

    public ChunkCoordinates getEventCoordinates()
    {
        return this.eventCoordinates;
    }

    public void setEventCoordinates(ChunkCoordinates eventCoordinates)
    {
        this.eventCoordinates = eventCoordinates;
    }

    public float getRotationYaw()
    {
        return this.rotationYaw;
    }

    public void setRotationYaw(float rotationYaw)
    {
        this.rotationYaw = rotationYaw;
    }

    public float getRotationPitch()
    {
        return this.rotationPitch;
    }

    public void setRotationPitch(float rotationPitch)
    {
        this.rotationPitch = rotationPitch;
    }

    public int getDimensionId()
    {
        return this.dimensionId;
    }

    public void setDimensionId(int dimensionId)
    {
        this.dimensionId = dimensionId;
    }

    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        this.dimensionId = nbtTagCompound.getInteger("dimensionId");
        this.eventCoordinates = new ChunkCoordinates(nbtTagCompound.getInteger("posX"), nbtTagCompound.getInteger("posY"), nbtTagCompound.getInteger("posZ"));
        this.rotationYaw = nbtTagCompound.getFloat("rotationYaw");
        this.rotationPitch = nbtTagCompound.getFloat("rotationPitch");
        this.name = nbtTagCompound.getString("name");
    }

    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        nbtTagCompound.setInteger("dimensionId", this.dimensionId);
        nbtTagCompound.setInteger("posX", this.eventCoordinates.posX);
        nbtTagCompound.setInteger("posY", this.eventCoordinates.posY);
        nbtTagCompound.setInteger("posZ", this.eventCoordinates.posZ);
        nbtTagCompound.setFloat("rotationYaw", this.rotationYaw);
        nbtTagCompound.setFloat("rotationPitch", this.rotationPitch);
        nbtTagCompound.setString("name", this.name);
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
