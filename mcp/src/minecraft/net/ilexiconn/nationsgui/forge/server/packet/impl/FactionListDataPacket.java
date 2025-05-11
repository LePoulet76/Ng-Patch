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
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionListGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class FactionListDataPacket
implements IPacket,
IClientPacket {
    public ArrayList<HashMap<String, String>> countries = new ArrayList();

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.countries = (ArrayList)new Gson().fromJson(data.readUTF(), new TypeToken<ArrayList<HashMap<String, String>>>(){}.getType());
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        FactionListGUI.countriesData.addAll(this.countries);
        if (!FactionListGUI.loaded && this.countries.size() > 0) {
            FactionListGUI.selectedCountry = this.countries.get(0);
        }
        FactionListGUI.loaded = true;
    }
}

