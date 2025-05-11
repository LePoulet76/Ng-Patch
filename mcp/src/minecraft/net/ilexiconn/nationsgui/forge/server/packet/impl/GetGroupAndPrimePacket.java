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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class GetGroupAndPrimePacket
implements IPacket,
IClientPacket {
    public String mainBadge = "";
    public String playerName = "";
    public boolean isNGPrime = false;
    public static HashMap<String, String> GRP_BADGES_PLAYERS = new HashMap();
    public static List<String> NGPRIME_PLAYERS = new ArrayList<String>();

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.mainBadge = data.readUTF();
        this.playerName = data.readUTF();
        this.isNGPrime = data.readBoolean();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.mainBadge);
        data.writeUTF(this.playerName);
        data.writeBoolean(this.isNGPrime);
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        if (!this.mainBadge.equals("none") && NationsGUI.BADGES_RESOURCES.containsKey(this.mainBadge.toLowerCase())) {
            GRP_BADGES_PLAYERS.put(this.playerName, this.mainBadge.toLowerCase());
        } else if (GRP_BADGES_PLAYERS.containsKey(this.playerName)) {
            GRP_BADGES_PLAYERS.remove(this.playerName);
        }
        if (this.isNGPrime && !NGPRIME_PLAYERS.contains(this.playerName)) {
            NGPRIME_PLAYERS.add(this.playerName);
        } else if (!this.isNGPrime && NGPRIME_PLAYERS.contains(this.playerName)) {
            NGPRIME_PLAYERS.remove(this.playerName);
        }
    }
}

