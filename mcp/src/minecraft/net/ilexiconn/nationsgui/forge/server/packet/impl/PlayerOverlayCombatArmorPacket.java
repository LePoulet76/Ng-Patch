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
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class PlayerOverlayCombatArmorPacket
implements IPacket,
IClientPacket {
    public ArrayList<String> itemsData = new ArrayList();
    public HashMap<String, String> data;

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.itemsData = (ArrayList)new Gson().fromJson(data.readUTF(), new TypeToken<ArrayList<String>>(){}.getType());
        this.data = (HashMap)new Gson().fromJson(data.readUTF(), new TypeToken<HashMap<String, String>>(){}.getType());
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        if (!this.itemsData.isEmpty()) {
            ClientData.playerCombatArmorDurability = this.itemsData;
            ClientData.playerCombatArmorInfos = this.data;
        } else {
            ClientData.playerCombatArmorDurability = new ArrayList();
            ClientData.playerCombatArmorInfos = new HashMap();
        }
    }
}

