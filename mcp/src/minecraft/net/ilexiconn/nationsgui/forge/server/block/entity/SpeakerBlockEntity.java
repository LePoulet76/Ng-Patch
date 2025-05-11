/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.NBTTagCompound
 */
package net.ilexiconn.nationsgui.forge.server.block.entity;

import net.ilexiconn.nationsgui.forge.server.block.entity.BlockEntity;
import net.minecraft.nbt.NBTTagCompound;

public class SpeakerBlockEntity
extends BlockEntity {
    public int radioX;
    public int radioY;
    public int radioZ;

    @Override
    public void saveNBTData(NBTTagCompound compound) {
        compound.func_74768_a("RadioX", this.radioX);
        compound.func_74768_a("RadioY", this.radioY);
        compound.func_74768_a("RadioZ", this.radioZ);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {
        this.radioX = compound.func_74762_e("RadioX");
        this.radioY = compound.func_74762_e("RadioY");
        this.radioZ = compound.func_74762_e("RadioZ");
    }
}

