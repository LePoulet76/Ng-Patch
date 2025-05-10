package net.ilexiconn.nationsgui.forge.server.block.entity;

import net.minecraft.nbt.NBTTagCompound;

public class URLBlockEntity extends BlockEntity
{
    public String url = "";

    public void saveNBTData(NBTTagCompound compound)
    {
        compound.setString("url", this.url);
    }

    public void loadNBTData(NBTTagCompound compound)
    {
        this.url = compound.getString("url");
    }

    public String getURL()
    {
        return this.url;
    }
}
