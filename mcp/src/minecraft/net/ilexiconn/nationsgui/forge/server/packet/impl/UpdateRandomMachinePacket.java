/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  fr.nationsglory.server.block.entity.GCRandomBlockEntity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.tileentity.TileEntity
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import fr.nationsglory.server.block.entity.GCRandomBlockEntity;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;

public class UpdateRandomMachinePacket
implements IPacket,
IServerPacket,
IClientPacket {
    public String enterpriseName;
    public int posX;
    public int posY;
    public int posZ;
    public int percent;
    public boolean hasAuthorization;
    public boolean bukkitDone;

    public UpdateRandomMachinePacket(String enterpriseName, int posX, int posY, int posZ, int percent) {
        this.enterpriseName = enterpriseName;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.percent = percent;
        this.hasAuthorization = false;
        this.bukkitDone = false;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.enterpriseName = data.readUTF();
        this.posX = data.readInt();
        this.posY = data.readInt();
        this.posZ = data.readInt();
        this.percent = data.readInt();
        this.hasAuthorization = data.readBoolean();
        this.bukkitDone = data.readBoolean();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.enterpriseName);
        data.writeInt(this.posX);
        data.writeInt(this.posY);
        data.writeInt(this.posZ);
        data.writeInt(this.percent);
        data.writeBoolean(this.hasAuthorization);
        data.writeBoolean(this.bukkitDone);
    }

    @Override
    public void handleServerPacket(EntityPlayer player) {
        TileEntity tileEntity;
        if (this.hasAuthorization && (tileEntity = player.func_130014_f_().func_72796_p(this.posX, this.posY, this.posZ)) instanceof GCRandomBlockEntity) {
            ((GCRandomBlockEntity)tileEntity).percent = this.percent;
        }
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        if (this.hasAuthorization) {
            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(this));
        }
    }
}

