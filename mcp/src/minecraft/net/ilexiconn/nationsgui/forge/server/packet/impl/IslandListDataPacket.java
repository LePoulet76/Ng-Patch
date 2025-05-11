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
import net.ilexiconn.nationsgui.forge.client.gui.island.IslandListGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class IslandListDataPacket
implements IPacket,
IClientPacket {
    public ArrayList<HashMap<String, String>> islands = new ArrayList();
    public String filter;
    public boolean onlyMyIsland;
    public boolean isPremium;
    public boolean isOp;
    public String serverNumber;

    public IslandListDataPacket(String filter, boolean onlyMyIsland) {
        this.filter = filter;
        this.onlyMyIsland = onlyMyIsland;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.islands = (ArrayList)new Gson().fromJson(data.readUTF(), new TypeToken<ArrayList<HashMap<String, String>>>(){}.getType());
        this.isPremium = data.readBoolean();
        this.isOp = data.readBoolean();
        this.serverNumber = data.readUTF();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.filter);
        data.writeBoolean(this.onlyMyIsland);
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        if (!IslandListGui.loaded || IslandListGui.resetList) {
            IslandListGui.islands = this.islands;
            IslandListGui.loaded = true;
            IslandListGui.isPremium = this.isPremium;
            IslandListGui.isOp = this.isOp;
            IslandListGui.serverNumber = this.serverNumber;
            IslandListGui.resetList = false;
        } else {
            IslandListGui.islands.addAll(this.islands);
        }
    }
}

