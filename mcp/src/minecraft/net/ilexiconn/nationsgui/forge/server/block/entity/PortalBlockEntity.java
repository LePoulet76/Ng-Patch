/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.network.INetworkManager
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.network.packet.Packet132TileEntityData
 *  net.minecraft.util.AxisAlignedBB
 */
package net.ilexiconn.nationsgui.forge.server.block.entity;

import net.ilexiconn.nationsgui.forge.server.block.entity.BlockEntity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.util.AxisAlignedBB;

public class PortalBlockEntity
extends BlockEntity {
    public boolean lastActive = false;
    public boolean active = false;
    public String code = "";
    public String owner = "";

    @Override
    public void onUpdate() {
        if (this.lastActive != this.active) {
            this.lastActive = this.active;
            this.field_70331_k.func_72902_n(this.field_70329_l, this.field_70330_m, this.field_70327_n);
        }
    }

    @Override
    public void saveNBTData(NBTTagCompound compound) {
        compound.func_74757_a("active", this.active);
        compound.func_74778_a("code", this.code);
        compound.func_74778_a("owner", this.owner);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {
        if (compound.func_74764_b("active")) {
            this.active = compound.func_74767_n("active");
        }
        if (compound.func_74764_b("code")) {
            this.code = compound.func_74779_i("code");
        }
        if (compound.func_74764_b("owner")) {
            this.owner = compound.func_74779_i("owner");
        }
    }

    @Override
    public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
        this.active = pkt.field_73331_e.func_74767_n("active");
        this.code = pkt.field_73331_e.func_74779_i("code");
        this.owner = pkt.field_73331_e.func_74779_i("owner");
    }

    @Override
    public Packet func_70319_e() {
        NBTTagCompound compound = new NBTTagCompound();
        compound.func_74757_a("active", this.active);
        compound.func_74778_a("code", this.code);
        compound.func_74778_a("owner", this.owner);
        return new Packet132TileEntityData(this.field_70329_l, this.field_70330_m, this.field_70327_n, 3, compound);
    }

    public AxisAlignedBB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
    }
}

