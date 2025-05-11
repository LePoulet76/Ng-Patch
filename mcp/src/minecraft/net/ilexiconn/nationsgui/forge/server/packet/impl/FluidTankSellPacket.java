/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.common.network.Player
 *  fr.nationsglory.server.block.entity.GCFluidTankBlockEntity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.tileentity.TileEntity
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import fr.nationsglory.server.block.entity.GCFluidTankBlockEntity;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FluidTankInfosMachinePacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;

public class FluidTankSellPacket
implements IPacket,
IClientPacket,
IServerPacket {
    private int amount;
    private int blockX;
    private int blockY;
    private int blockZ;
    private boolean valid;

    public FluidTankSellPacket(int amount, int blockX, int blockY, int blockZ, boolean valid) {
        this.amount = amount;
        this.blockX = blockX;
        this.blockY = blockY;
        this.blockZ = blockZ;
        this.valid = valid;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.amount = data.readInt();
        this.blockX = data.readInt();
        this.blockY = data.readInt();
        this.blockZ = data.readInt();
        this.valid = data.readBoolean();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeInt(this.amount);
        data.writeInt(this.blockX);
        data.writeInt(this.blockY);
        data.writeInt(this.blockZ);
        data.writeBoolean(this.valid);
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        if (this.valid) {
            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(this));
        } else {
            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FluidTankInfosMachinePacket()));
        }
    }

    @Override
    public void handleServerPacket(EntityPlayer player) {
        if (this.valid) {
            TileEntity tileEntity = player.func_130014_f_().func_72796_p(this.blockX, this.blockY, this.blockZ);
            if (tileEntity instanceof GCFluidTankBlockEntity) {
                float newEnergy = ((GCFluidTankBlockEntity)tileEntity).getFuelStored() - (float)this.amount;
                ((GCFluidTankBlockEntity)tileEntity).setFuelStored(Math.max(0.0f, newEnergy));
            }
            this.valid = false;
            PacketDispatcher.sendPacketToPlayer((Packet)PacketRegistry.INSTANCE.generatePacket(this), (Player)((Player)player));
        }
    }
}

