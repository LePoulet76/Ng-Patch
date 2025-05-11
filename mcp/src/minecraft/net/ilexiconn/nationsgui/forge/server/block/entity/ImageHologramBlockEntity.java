/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.NBTTagCompound
 */
package net.ilexiconn.nationsgui.forge.server.block.entity;

import net.ilexiconn.nationsgui.forge.server.block.entity.BlockEntity;
import net.minecraft.nbt.NBTTagCompound;

public class ImageHologramBlockEntity
extends BlockEntity {
    public String url = "";
    public int imgWidth = 0;
    public int imgHeight = 0;
    public int size = 100;

    @Override
    public void saveNBTData(NBTTagCompound compound) {
        compound.func_74778_a("url", this.url);
        compound.func_74768_a("imgWidth", this.imgWidth);
        compound.func_74768_a("imgHeight", this.imgHeight);
        compound.func_74768_a("size", this.size);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {
        this.url = compound.func_74779_i("url");
        this.imgWidth = compound.func_74762_e("imgWidth");
        this.imgHeight = compound.func_74762_e("imgHeight");
        this.size = compound.func_74762_e("size");
    }

    public String getURL() {
        return this.url;
    }
}

