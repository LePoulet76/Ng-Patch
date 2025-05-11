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

public class MinimapFactionsPacket
implements IPacket,
IClientPacket {
    @Override
    public void handleClientPacket(EntityPlayer player) {
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        ClientData.mapFactions = new String[data.readInt()];
        for (int i = 0; i < ClientData.mapFactions.length; ++i) {
            ClientData.mapFactions[i] = data.readUTF();
        }
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
    }
}

