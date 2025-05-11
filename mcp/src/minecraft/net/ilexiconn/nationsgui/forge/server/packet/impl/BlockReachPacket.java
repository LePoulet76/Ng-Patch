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
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class BlockReachPacket
implements IPacket,
IClientPacket {
    public float reach;

    public BlockReachPacket(float reach) {
        this.reach = reach;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.reach = data.readFloat();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeFloat(this.reach);
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        ClientProxy.blockReach = this.reach;
    }
}

