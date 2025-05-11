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
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseBetGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class EnterpriseBetDataPacket
implements IPacket,
IClientPacket {
    public ArrayList<HashMap<String, Object>> bets = new ArrayList();
    public HashMap<String, String> betInfos = new HashMap();
    public String target;

    public EnterpriseBetDataPacket(String targetName) {
        this.target = targetName;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.betInfos = (HashMap)new Gson().fromJson(data.readUTF(), new TypeToken<HashMap<String, String>>(){}.getType());
        this.bets = (ArrayList)new Gson().fromJson(data.readUTF(), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.target);
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        EnterpriseBetGUI.betsInfos = this.betInfos;
        EnterpriseBetGUI.bets = this.bets;
        EnterpriseBetGUI.loaded = true;
    }
}

