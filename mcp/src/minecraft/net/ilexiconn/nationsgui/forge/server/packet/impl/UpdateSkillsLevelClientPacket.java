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
import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class UpdateSkillsLevelClientPacket
implements IPacket,
IClientPacket {
    public HashMap<String, Integer> skillsLevel;
    public Map<String, String> topSkills;

    public UpdateSkillsLevelClientPacket(HashMap<String, Integer> skillsLevel, Map<String, String> topSkills) {
        this.skillsLevel = skillsLevel;
        this.topSkills = topSkills;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.skillsLevel = (HashMap)new Gson().fromJson(data.readUTF(), new TypeToken<HashMap<String, Integer>>(){}.getType());
        this.topSkills = (Map)new Gson().fromJson(data.readUTF(), new TypeToken<Map<String, String>>(){}.getType());
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(new Gson().toJson(this.skillsLevel));
        data.writeUTF(new Gson().toJson(this.topSkills));
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        if (!this.skillsLevel.isEmpty()) {
            ClientData.skillsLevel = this.skillsLevel;
        }
        if (!this.topSkills.isEmpty()) {
            for (Map.Entry<String, String> entry : this.topSkills.entrySet()) {
                ClientData.topPlayersSkills.put(entry.getValue().split("#")[0], entry.getKey());
            }
        }
    }
}

