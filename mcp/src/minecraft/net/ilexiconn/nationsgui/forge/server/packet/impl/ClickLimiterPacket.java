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

public class ClickLimiterPacket
implements IPacket,
IClientPacket {
    public int limit;

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.limit = data.readInt();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeInt(this.limit);
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        ClientData.clickLimit = this.limit;
    }
}

