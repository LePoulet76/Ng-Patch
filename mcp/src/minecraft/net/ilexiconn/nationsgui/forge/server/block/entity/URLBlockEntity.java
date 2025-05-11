/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.NBTTagCompound
 */
package net.ilexiconn.nationsgui.forge.server.block.entity;

import net.ilexiconn.nationsgui.forge.server.block.entity.BlockEntity;
import net.minecraft.nbt.NBTTagCompound;

public class URLBlockEntity
extends BlockEntity {
    public String url = "";

    @Override
    public void saveNBTData(NBTTagCompound compound) {
        compound.func_74778_a("url", this.url);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {
        this.url = compound.func_74779_i("url");
    }

    public String getURL() {
        return this.url;
    }
}

