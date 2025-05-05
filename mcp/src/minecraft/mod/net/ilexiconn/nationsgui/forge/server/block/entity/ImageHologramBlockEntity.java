package net.ilexiconn.nationsgui.forge.server.block.entity;

import net.minecraft.nbt.NBTTagCompound;

public class ImageHologramBlockEntity extends BlockEntity
{
    public String url = "";
    public int imgWidth = 0;
    public int imgHeight = 0;
    public int size = 100;

    public void saveNBTData(NBTTagCompound compound)
    {
        compound.setString("url", this.url);
        compound.setInteger("imgWidth", this.imgWidth);
        compound.setInteger("imgHeight", this.imgHeight);
        compound.setInteger("size", this.size);
    }

    public void loadNBTData(NBTTagCompound compound)
    {
        this.url = compound.getString("url");
        this.imgWidth = compound.getInteger("imgWidth");
        this.imgHeight = compound.getInteger("imgHeight");
        this.size = compound.getInteger("size");
    }

    public String getURL()
    {
        return this.url;
    }
}
