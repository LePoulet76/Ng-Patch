/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.network.packet.Packet
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.LotoDataPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;

public class LotoBuyPacket
implements IPacket,
IClientPacket {
    public int lotteryId;
    public int tickets;
    public int donation;

    public LotoBuyPacket(int lotteryId, int tickets, int donation) {
        this.lotteryId = lotteryId;
        this.tickets = tickets;
        this.donation = donation;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeInt(this.lotteryId);
        data.writeInt(this.tickets);
        data.writeInt(this.donation);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void handleClientPacket(EntityPlayer player) {
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new LotoDataPacket(false)));
    }
}

