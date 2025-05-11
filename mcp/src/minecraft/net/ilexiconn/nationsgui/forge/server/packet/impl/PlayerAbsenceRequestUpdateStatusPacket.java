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
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class PlayerAbsenceRequestUpdateStatusPacket
implements IPacket,
IClientPacket {
    private String status;
    private String playerName;

    public PlayerAbsenceRequestUpdateStatusPacket(String playerName, String status) {
        this.playerName = playerName;
        this.status = status;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.status);
        data.writeUTF(this.playerName);
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
    }
}

