/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.common.network.Player
 *  fr.nationsglory.server.block.entity.GCElectricGeneratorBlockEntity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.tileentity.TileEntity
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import fr.nationsglory.server.block.entity.GCElectricGeneratorBlockEntity;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.ElectricGeneratorInfosMachinePacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;

public class ElectricGeneratorBuyPacket
implements IPacket,
IClientPacket,
IServerPacket {
    private String enterprise;
    private int amount;
    private int price;
    private int blockX;
    private int blockY;
    private int blockZ;
    private boolean valid;

    public ElectricGeneratorBuyPacket(String enterprise, int amount, int price, int blockX, int blockY, int blockZ, boolean valid) {
        this.enterprise = enterprise;
        this.amount = amount;
        this.price = price;
        this.blockX = blockX;
        this.blockY = blockY;
        this.blockZ = blockZ;
        this.valid = valid;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.enterprise = data.readUTF();
        this.amount = data.readInt();
        this.price = data.readInt();
        this.blockX = data.readInt();
        this.blockY = data.readInt();
        this.blockZ = data.readInt();
        this.valid = data.readBoolean();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.enterprise);
        data.writeInt(this.amount);
        data.writeInt(this.price);
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
            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new ElectricGeneratorInfosMachinePacket()));
        }
    }

    @Override
    public void handleServerPacket(EntityPlayer player) {
        if (this.valid) {
            TileEntity tileEntity = player.func_130014_f_().func_72796_p(this.blockX, this.blockY, this.blockZ);
            if (tileEntity instanceof GCElectricGeneratorBlockEntity) {
                float newEnergy = ((GCElectricGeneratorBlockEntity)tileEntity).getEnergyStored() + (float)(this.amount * 1000);
                ((GCElectricGeneratorBlockEntity)tileEntity).setEnergyStored(Math.min(((GCElectricGeneratorBlockEntity)tileEntity).getMaxEnergyStored(), newEnergy));
            }
            this.valid = false;
            PacketDispatcher.sendPacketToPlayer((Packet)PacketRegistry.INSTANCE.generatePacket(this), (Player)((Player)player));
        }
    }
}

