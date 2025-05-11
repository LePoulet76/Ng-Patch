/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.tileentity.TileEntity
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.server.block.entity.RadioBlockEntity;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;

public class SetSourcePacket
implements IPacket,
IServerPacket {
    private int x;
    private int y;
    private int z;
    private String source;

    public SetSourcePacket(RadioBlockEntity blockEntity) {
        this.x = blockEntity.field_70329_l;
        this.y = blockEntity.field_70330_m;
        this.z = blockEntity.field_70327_n;
        this.source = blockEntity.source;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.x = data.readInt();
        this.y = data.readInt();
        this.z = data.readInt();
        this.source = data.readUTF();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeInt(this.x);
        data.writeInt(this.y);
        data.writeInt(this.z);
        data.writeUTF(this.source);
    }

    @Override
    public void handleServerPacket(EntityPlayer player) {
        TileEntity tileEntity = player.field_70170_p.func_72796_p(this.x, this.y, this.z);
        if (tileEntity != null && MinecraftServer.func_71276_C().func_71203_ab().func_72353_e(player.field_71092_bJ)) {
            ((RadioBlockEntity)tileEntity).source = this.source;
        }
    }
}

