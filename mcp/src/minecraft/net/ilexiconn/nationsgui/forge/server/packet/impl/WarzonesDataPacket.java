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
import net.ilexiconn.nationsgui.forge.client.gui.warzone.WarzonesGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class WarzonesDataPacket
implements IPacket,
IClientPacket {
    public HashMap<String, String> bateauInfos = new HashMap();
    public HashMap<String, String> petrolInfos = new HashMap();
    public HashMap<String, String> mineInfos = new HashMap();
    public HashMap<String, String> scoreInfos = new HashMap();
    public int dollarsDailyLimit;
    public int maxPowerboost;
    public int maxSkillboost;

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.bateauInfos = (HashMap)new Gson().fromJson(data.readUTF(), new TypeToken<HashMap<String, String>>(){}.getType());
        this.petrolInfos = (HashMap)new Gson().fromJson(data.readUTF(), new TypeToken<HashMap<String, String>>(){}.getType());
        this.mineInfos = (HashMap)new Gson().fromJson(data.readUTF(), new TypeToken<HashMap<String, String>>(){}.getType());
        this.scoreInfos = (HashMap)new Gson().fromJson(data.readUTF(), new TypeToken<HashMap<String, String>>(){}.getType());
        this.dollarsDailyLimit = data.readInt();
        this.maxPowerboost = data.readInt();
        this.maxSkillboost = data.readInt();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        WarzonesGui.bateauInfos = this.bateauInfos;
        WarzonesGui.petrolInfos = this.petrolInfos;
        WarzonesGui.mineInfos = this.mineInfos;
        WarzonesGui.scoreInfos = this.scoreInfos;
        WarzonesGui.dollarsDailyLimit = this.dollarsDailyLimit;
        WarzonesGui.maxPowerboost = this.maxPowerboost;
        WarzonesGui.maxSkillboost = this.maxSkillboost;
        WarzonesGui.loaded = true;
    }
}

