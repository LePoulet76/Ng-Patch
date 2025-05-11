/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.tileentity.TileEntity
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import net.ilexiconn.nationsgui.forge.server.block.entity.PortalBlockEntity;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;

public class IslandPortalPacket
implements IPacket,
IServerPacket,
IClientPacket {
    public String code;
    public int posX;
    public int posY;
    public int posZ;
    public int sync;

    public IslandPortalPacket(String code, int posX, int posY, int posZ) {
        this.code = code;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.sync = -1;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.code = data.readUTF();
        this.posX = data.readInt();
        this.posY = data.readInt();
        this.posZ = data.readInt();
        this.sync = data.readInt();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.code);
        data.writeInt(this.posX);
        data.writeInt(this.posY);
        data.writeInt(this.posZ);
        data.writeInt(this.sync);
    }

    @Override
    public void handleServerPacket(EntityPlayer player) {
        TileEntity tile1 = player.field_70170_p.func_72796_p(this.posX, this.posY, this.posZ);
        TileEntity tile2 = player.field_70170_p.func_72796_p(this.posX, this.posY + 1, this.posZ);
        if (tile1 != null && tile1 instanceof PortalBlockEntity && tile2 != null && tile2 instanceof PortalBlockEntity) {
            ((PortalBlockEntity)tile1).code = this.code;
            ((PortalBlockEntity)tile1).owner = player.getDisplayName();
            ((PortalBlockEntity)tile2).code = this.code;
            ((PortalBlockEntity)tile2).owner = player.getDisplayName();
            if (this.sync == 1) {
                ((PortalBlockEntity)tile1).active = true;
                ((PortalBlockEntity)tile2).active = true;
            } else {
                ((PortalBlockEntity)tile1).active = false;
                ((PortalBlockEntity)tile2).active = false;
            }
            player.field_70170_p.func_72902_n(this.posX, this.posY, this.posZ);
            player.field_70170_p.func_72902_n(this.posX, this.posY + 1, this.posZ);
        }
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        TileEntity tile1 = player.field_70170_p.func_72796_p(this.posX, this.posY, this.posZ);
        TileEntity tile2 = player.field_70170_p.func_72796_p(this.posX, this.posY + 1, this.posZ);
        if (tile1 != null && tile1 instanceof PortalBlockEntity && tile2 != null && tile2 instanceof PortalBlockEntity) {
            ((PortalBlockEntity)tile1).code = this.code;
            ((PortalBlockEntity)tile1).owner = player.getDisplayName();
            ((PortalBlockEntity)tile2).code = this.code;
            ((PortalBlockEntity)tile2).owner = player.getDisplayName();
            if (this.sync == 1) {
                ((PortalBlockEntity)tile1).active = true;
                ((PortalBlockEntity)tile2).active = true;
            } else {
                ((PortalBlockEntity)tile1).active = false;
                ((PortalBlockEntity)tile2).active = false;
            }
            player.field_70170_p.func_72902_n(this.posX, this.posY, this.posZ);
            player.field_70170_p.func_72902_n(this.posX, this.posY + 1, this.posZ);
        }
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(this));
    }
}

