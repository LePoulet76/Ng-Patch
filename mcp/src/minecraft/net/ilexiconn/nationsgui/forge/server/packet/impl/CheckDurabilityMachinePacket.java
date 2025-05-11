/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.common.network.Player
 *  micdoodle8.mods.galacticraft.core.tile.GCCoreTileEntityUniversalElectrical
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.tileentity.TileEntity
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import micdoodle8.mods.galacticraft.core.tile.GCCoreTileEntityUniversalElectrical;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;

public class CheckDurabilityMachinePacket
implements IPacket,
IServerPacket,
IClientPacket {
    private String playerName;
    public int posX;
    public int posY;
    public int posZ;
    public String target;

    public CheckDurabilityMachinePacket(String playerName, int posX, int posY, int posZ, String target) {
        this.playerName = playerName;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.target = target;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.playerName = data.readUTF();
        this.posX = data.readInt();
        this.posY = data.readInt();
        this.posZ = data.readInt();
        this.target = data.readUTF();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.playerName);
        data.writeInt(this.posX);
        data.writeInt(this.posY);
        data.writeInt(this.posZ);
        data.writeUTF(this.target);
    }

    @Override
    public void handleServerPacket(EntityPlayer player) {
        int dura;
        TileEntity tileEntity;
        if (this.target.equalsIgnoreCase("forge") && (tileEntity = player.func_130014_f_().func_72796_p(this.posX, this.posY, this.posZ)) instanceof GCCoreTileEntityUniversalElectrical && (dura = ((GCCoreTileEntityUniversalElectrical)tileEntity).durability) < 100) {
            this.target = "bukkit";
            PacketDispatcher.sendPacketToPlayer((Packet)PacketRegistry.INSTANCE.generatePacket(this), (Player)((Player)player));
        }
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(this));
    }
}

