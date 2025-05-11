/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.nbt.CompressedStreamTools
 *  net.minecraft.nbt.NBTTagCompound
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.ilexiconn.nationsgui.forge.server.block.entity.BlockEntity;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

public class BlockEntityPacket
implements IPacket,
IClientPacket {
    private int x;
    private int y;
    private int z;
    private NBTTagCompound compound;

    public BlockEntityPacket(BlockEntity entity) {
        this.x = entity.field_70329_l;
        this.y = entity.field_70330_m;
        this.z = entity.field_70327_n;
        this.compound = new NBTTagCompound();
        entity.saveTrackingSensitiveData(this.compound);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void handleClientPacket(EntityPlayer player) {
        BlockEntity blockEntity = (BlockEntity)player.field_70170_p.func_72796_p(this.x, this.y, this.z);
        if (blockEntity != null) {
            blockEntity.loadTrackingSensitiveData(this.compound);
        }
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.x = data.readInt();
        this.y = data.readInt();
        this.z = data.readInt();
        try {
            this.compound = CompressedStreamTools.func_74794_a((DataInput)data);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeInt(this.x);
        data.writeInt(this.y);
        data.writeInt(this.z);
        try {
            CompressedStreamTools.func_74800_a((NBTTagCompound)this.compound, (DataOutput)data);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

