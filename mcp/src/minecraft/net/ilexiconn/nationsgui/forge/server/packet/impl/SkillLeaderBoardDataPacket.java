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
import net.ilexiconn.nationsgui.forge.client.gui.SkillsGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class SkillLeaderBoardDataPacket
implements IPacket,
IClientPacket {
    public HashMap<String, List<String>> skillsLeaderBoard = new HashMap();
    public HashMap<String, String> skillsTopInterServ = new HashMap();

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.skillsLeaderBoard = (HashMap)new Gson().fromJson(data.readUTF(), new TypeToken<HashMap<String, List<String>>>(){}.getType());
        this.skillsTopInterServ = (HashMap)new Gson().fromJson(data.readUTF(), new TypeToken<HashMap<String, String>>(){}.getType());
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(new Gson().toJson(this.skillsLeaderBoard));
        data.writeUTF(new Gson().toJson(this.skillsTopInterServ));
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        SkillsGui.skillsLeaderBoard = this.skillsLeaderBoard;
        SkillsGui.skillsTopInterServ = this.skillsTopInterServ;
        SkillsGui.displayMode = "leaderboard";
    }
}

