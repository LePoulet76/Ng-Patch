/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  com.google.gson.Gson
 *  com.google.gson.reflect.TypeToken
 *  net.minecraft.entity.player.EntityPlayer
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import net.ilexiconn.nationsgui.forge.client.gui.faction.PlayerListGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class PlayerListDataPacket
implements IPacket,
IClientPacket {
    public ArrayList<HashMap<String, String>> players = new ArrayList();
    public LinkedHashMap<String, ArrayList<String>> staffPlayers = new LinkedHashMap();
    public LinkedHashMap<String, ArrayList<String>> staffInterServerPlayers = new LinkedHashMap();

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.players = (ArrayList)new Gson().fromJson(data.readUTF(), new TypeToken<ArrayList<HashMap<String, String>>>(){}.getType());
        this.staffPlayers = (LinkedHashMap)new Gson().fromJson(data.readUTF(), new TypeToken<LinkedHashMap<String, ArrayList<String>>>(){}.getType());
        this.staffInterServerPlayers = (LinkedHashMap)new Gson().fromJson(data.readUTF(), new TypeToken<LinkedHashMap<String, ArrayList<String>>>(){}.getType());
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        if (!this.staffPlayers.isEmpty()) {
            PlayerListGUI.staffPlayers = this.staffPlayers;
        }
        if (!this.staffInterServerPlayers.isEmpty()) {
            PlayerListGUI.staffInterServerPlayers = this.staffInterServerPlayers;
        }
        if (!this.players.isEmpty()) {
            PlayerListGUI.playersData.addAll(this.players);
        }
        if (!PlayerListGUI.loaded && this.players.size() > 0) {
            PlayerListGUI.selectedPlayer = this.players.get(0);
        }
        PlayerListGUI.loaded = true;
    }
}

