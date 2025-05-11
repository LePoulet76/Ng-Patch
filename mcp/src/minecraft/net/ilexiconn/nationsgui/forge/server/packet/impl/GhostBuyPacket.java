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
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.minecraft.entity.player.EntityPlayer;

public class GhostBuyPacket
implements IPacket,
IServerPacket {
    int itemIndex;

    public GhostBuyPacket(int itemIndex) {
        this.itemIndex = itemIndex;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.itemIndex = data.readInt();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeInt(this.itemIndex);
    }

    @Override
    public void handleServerPacket(EntityPlayer player) {
    }
}

