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
import java.util.HashMap;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionSkillsGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class FactionSkillsCountriesLeaderboardDataPacket
implements IPacket,
IClientPacket {
    public HashMap<String, List<String>> countriesLeaderboard = new HashMap();
    public HashMap<String, Double> countryPositionPerSkill = new HashMap();
    public HashMap<String, Double> countryTotalLevelPerSkill = new HashMap();
    private String target;

    public FactionSkillsCountriesLeaderboardDataPacket(String target) {
        this.target = target;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.countriesLeaderboard = (HashMap)new Gson().fromJson(data.readUTF(), new TypeToken<HashMap<String, List<String>>>(){}.getType());
        this.countryPositionPerSkill = (HashMap)new Gson().fromJson(data.readUTF(), new TypeToken<HashMap<String, Double>>(){}.getType());
        this.countryTotalLevelPerSkill = (HashMap)new Gson().fromJson(data.readUTF(), new TypeToken<HashMap<String, Double>>(){}.getType());
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.target);
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        FactionSkillsGUI.countriesLeaderboard = this.countriesLeaderboard;
        FactionSkillsGUI.countryPositionPerSkill = this.countryPositionPerSkill;
        FactionSkillsGUI.countryTotalLevelPerSkill = this.countryTotalLevelPerSkill;
        FactionSkillsGUI.loaded_countries_leaderboard = true;
    }
}

