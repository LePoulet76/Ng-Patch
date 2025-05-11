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
import java.util.Map;
import java.util.TreeMap;
import net.ilexiconn.nationsgui.forge.client.gui.faction.MembersGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class FactionMembersDataPacket
implements IPacket,
IClientPacket {
    public TreeMap<String, Object> data = new TreeMap();
    public String targetFactionId;

    public FactionMembersDataPacket(String targetName) {
        MembersGUI.factionMembersInfos.clear();
        this.targetFactionId = targetName;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.data = (TreeMap)new Gson().fromJson(data.readUTF(), new TypeToken<TreeMap<String, Object>>(){}.getType());
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.targetFactionId);
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        for (Map.Entry<String, Object> entry : this.data.entrySet()) {
            MembersGUI.factionMembersInfos.put(entry.getKey(), entry.getValue());
        }
        MembersGUI.loaded = true;
    }
}

