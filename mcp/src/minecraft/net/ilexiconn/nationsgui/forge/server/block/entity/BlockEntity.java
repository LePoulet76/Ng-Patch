/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.network.INetworkManager
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.network.packet.Packet132TileEntityData
 *  net.minecraft.tileentity.TileEntity
 */
package net.ilexiconn.nationsgui.forge.server.block.entity;

import cpw.mods.fml.common.network.PacketDispatcher;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.BlockEntityPacket;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

public abstract class BlockEntity
extends TileEntity {
    private NBTTagCompound lastCompound;
    private int trackingUpdateTimer = 0;

    public final void func_70316_g() {
        int trackingUpdateFrequency = this.getTrackingUpdateTime();
        if (this.trackingUpdateTimer < trackingUpdateFrequency) {
            ++this.trackingUpdateTimer;
        }
        if (this.trackingUpdateTimer >= trackingUpdateFrequency) {
            this.trackingUpdateTimer = 0;
            NBTTagCompound compound = new NBTTagCompound();
            this.saveTrackingSensitiveData(compound);
            if (!compound.equals((Object)this.lastCompound)) {
                if (!this.field_70331_k.field_72995_K) {
                    this.onSync();
                    PacketDispatcher.sendPacketToAllPlayers((Packet)PacketRegistry.INSTANCE.generatePacket(new BlockEntityPacket(this)));
                }
                this.lastCompound = compound;
            }
        }
        this.onUpdate();
    }

    public Packet func_70319_e() {
        NBTTagCompound compound = new NBTTagCompound();
        this.func_70310_b(compound);
        return new Packet132TileEntityData(this.field_70329_l, this.field_70330_m, this.field_70327_n, 0, compound);
    }

    public void onDataPacket(INetworkManager networkManager, Packet132TileEntityData packet) {
        this.func_70307_a(packet.field_73331_e);
    }

    public final void func_70307_a(NBTTagCompound compound) {
        super.func_70307_a(compound);
        this.loadNBTData(compound);
    }

    public final void func_70310_b(NBTTagCompound compound) {
        super.func_70310_b(compound);
        this.saveNBTData(compound);
    }

    public void saveTrackingSensitiveData(NBTTagCompound compound) {
        this.saveNBTData(compound);
    }

    public void loadTrackingSensitiveData(NBTTagCompound compound) {
        this.loadNBTData(compound);
    }

    public abstract void saveNBTData(NBTTagCompound var1);

    public abstract void loadNBTData(NBTTagCompound var1);

    public void onUpdate() {
    }

    public void onSync() {
    }

    public int getTrackingUpdateTime() {
        return 0;
    }
}

