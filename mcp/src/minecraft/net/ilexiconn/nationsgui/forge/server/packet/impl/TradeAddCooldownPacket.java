/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  net.minecraft.entity.player.EntityPlayer
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.client.gui.trade.GuiTrade;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class TradeAddCooldownPacket
implements IPacket,
IClientPacket {
    private long time;

    public TradeAddCooldownPacket(long l) {
        this.time = l;
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        GuiTrade.addCooldown(this.time);
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.time = data.readLong();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeLong(this.time);
    }
}

