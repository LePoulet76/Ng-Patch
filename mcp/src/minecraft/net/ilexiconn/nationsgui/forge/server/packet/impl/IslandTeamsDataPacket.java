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
import net.ilexiconn.nationsgui.forge.client.gui.island.IslandMembersGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class IslandTeamsDataPacket
implements IPacket,
IClientPacket {
    public ArrayList<HashMap<String, Object>> teamsInfos = new ArrayList();
    public ArrayList<String> teamFlags = new ArrayList();
    public boolean isCreation;

    public IslandTeamsDataPacket(boolean isCreation) {
        this.isCreation = isCreation;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.teamsInfos = (ArrayList)new Gson().fromJson(data.readUTF(), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
        this.teamFlags = (ArrayList)new Gson().fromJson(data.readUTF(), new TypeToken<ArrayList<String>>(){}.getType());
        this.isCreation = data.readBoolean();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeBoolean(this.isCreation);
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        IslandMembersGui.teamsInfos = new ArrayList();
        IslandMembersGui.teamFlags = this.teamFlags;
        IslandMembersGui.needUpdate = true;
        if (this.teamsInfos.size() > 0) {
            for (HashMap<String, Object> teamInfos : this.teamsInfos) {
                teamInfos.put("flags", new Gson().fromJson((String)teamInfos.get("flags"), new TypeToken<HashMap<String, Boolean>>(){}.getType()));
                IslandMembersGui.teamsInfos.add(teamInfos);
            }
            IslandMembersGui.selectedTeamInfos = !this.isCreation ? this.teamsInfos.get(0) : this.teamsInfos.get(this.teamsInfos.size() - 1);
        }
    }
}

