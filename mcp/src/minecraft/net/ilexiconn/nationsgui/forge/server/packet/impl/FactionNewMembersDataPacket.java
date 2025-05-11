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
import java.util.LinkedHashMap;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.gui.faction.MembersGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class FactionNewMembersDataPacket
implements IPacket,
IClientPacket {
    public LinkedHashMap<String, Object> data = new LinkedHashMap();
    public String targetFactionId;

    public FactionNewMembersDataPacket(String targetName) {
        MembersGUI.factionNewMembersInfos.clear();
        this.targetFactionId = targetName;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.data = (LinkedHashMap)new Gson().fromJson(data.readUTF(), new TypeToken<LinkedHashMap<String, Object>>(){}.getType());
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.targetFactionId);
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        for (Map.Entry<String, Object> entry : this.data.entrySet()) {
            MembersGUI.factionNewMembersInfos.put(entry.getKey(), entry.getValue());
        }
        MembersGUI.loaded_new_player = true;
    }
}

