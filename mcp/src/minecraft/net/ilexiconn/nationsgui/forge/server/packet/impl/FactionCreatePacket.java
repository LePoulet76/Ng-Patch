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

public class FactionCreatePacket
implements IPacket,
IClientPacket {
    public String factionName;
    public boolean announce;
    public boolean regen;

    public FactionCreatePacket(String factionName, boolean announce, boolean regen) {
        this.factionName = factionName;
        this.announce = announce;
        this.regen = regen;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.factionName = data.readUTF();
        this.announce = data.readBoolean();
        this.regen = data.readBoolean();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.factionName);
        data.writeBoolean(this.announce);
        data.writeBoolean(this.regen);
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
    }
}

