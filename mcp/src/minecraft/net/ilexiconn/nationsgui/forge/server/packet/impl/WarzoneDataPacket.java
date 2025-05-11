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

public class WarzoneDataPacket
implements IPacket,
IClientPacket {
    public HashMap<String, String> warzoneInfos = new HashMap();

    public WarzoneDataPacket(HashMap<String, String> warzoneInfos) {
        this.warzoneInfos = warzoneInfos;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.warzoneInfos = (HashMap)new Gson().fromJson(data.readUTF(), new TypeToken<HashMap<String, String>>(){}.getType());
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        if (!this.warzoneInfos.isEmpty()) {
            ClientData.setCurrentWarzone(this.warzoneInfos);
        } else {
            ClientData.setCurrentWarzone(new HashMap<String, String>());
        }
    }
}

