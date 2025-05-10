package net.ilexiconn.nationsgui.forge.server.block.entity;

import net.minecraft.nbt.NBTTagCompound;

public class SpeakerBlockEntity extends BlockEntity
{
    public int radioX;
    public int radioY;
    public int radioZ;

    public void saveNBTData(NBTTagCompound compound)
    {
        compound.setInteger("RadioX", this.radioX);
        compound.setInteger("RadioY", this.radioY);
        compound.setInteger("RadioZ", this.radioZ);
    }

    public void loadNBTData(NBTTagCompound compound)
    {
        this.radioX = compound.getInteger("RadioX");
        this.radioY = compound.getInteger("RadioY");
        this.radioZ = compound.getInteger("RadioZ");
    }
}
