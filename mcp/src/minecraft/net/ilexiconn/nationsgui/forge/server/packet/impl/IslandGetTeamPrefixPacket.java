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
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class IslandGetTeamPrefixPacket
implements IPacket,
IClientPacket {
    private String playerName;
    private String prefix;

    public IslandGetTeamPrefixPacket(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.playerName = data.readUTF();
        this.prefix = data.readUTF();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.playerName);
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        if (this.prefix != null) {
            ClientEventHandler.playersIslandTeamPrefix.put(this.playerName, this.prefix);
        }
    }
}

