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
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class IslandDataPacket
implements IPacket,
IClientPacket {
    public HashMap<String, String> islandInfos = new HashMap();

    public IslandDataPacket(HashMap<String, String> islandInfos) {
        this.islandInfos = islandInfos;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.islandInfos = (HashMap)new Gson().fromJson(data.readUTF(), new TypeToken<HashMap<String, String>>(){}.getType());
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        if (!this.islandInfos.isEmpty()) {
            if (!(ClientData.currentIsland.containsKey("id") && this.islandInfos.containsKey("id") && ClientData.currentIsland.get("id").equals(this.islandInfos.get("id")))) {
                ClientData.currentJumpStartTime = -1L;
                ClientData.currentJumpLocation = "";
                ClientData.currentJumpRecord = "";
            }
            ClientData.setCurrentIsland(this.islandInfos);
        } else {
            ClientData.setCurrentIsland(new HashMap<String, String>());
        }
    }
}

