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
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class DateTimeSyncPacket
implements IPacket,
IClientPacket {
    private Long serverTime;

    public DateTimeSyncPacket(Long serverTime) {
        this.serverTime = serverTime;
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        ClientData.serverTime = this.serverTime;
        ClientData.clientTimeWhenServerTimeReceived = System.currentTimeMillis();
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.serverTime = data.readLong();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeLong(this.serverTime.longValue());
    }
}

