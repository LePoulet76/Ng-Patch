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
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionListGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class FactionListDetailsPacket
implements IPacket,
IClientPacket {
    public String targetFaction;
    public HashMap<String, String> countryData = new HashMap();

    public FactionListDetailsPacket(String targetFaction) {
        this.targetFaction = targetFaction;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.targetFaction = data.readUTF();
        this.countryData = (HashMap)new Gson().fromJson(data.readUTF(), new TypeToken<HashMap<String, String>>(){}.getType());
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.targetFaction);
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        FactionListGUI.countriesExtraData.put(this.targetFaction, this.countryData);
    }
}

