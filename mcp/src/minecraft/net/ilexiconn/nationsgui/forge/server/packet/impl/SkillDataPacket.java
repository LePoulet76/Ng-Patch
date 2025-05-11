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
import net.ilexiconn.nationsgui.forge.client.gui.SkillsGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class SkillDataPacket
implements IPacket,
IClientPacket {
    private String playerName;
    public HashMap<String, HashMap<String, Object>> skills = new HashMap();

    public SkillDataPacket(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.skills = (HashMap)new Gson().fromJson(data.readUTF(), new TypeToken<HashMap<String, HashMap<String, Object>>>(){}.getType());
        this.playerName = data.readUTF();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(new Gson().toJson(this.skills));
        data.writeUTF(this.playerName);
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        SkillsGui.skillsData = this.skills;
        Map.Entry<String, HashMap<String, Object>> entry = this.skills.entrySet().iterator().next();
        SkillsGui.selectedSkill = entry.getKey();
        SkillsGui.loaded = true;
    }
}

