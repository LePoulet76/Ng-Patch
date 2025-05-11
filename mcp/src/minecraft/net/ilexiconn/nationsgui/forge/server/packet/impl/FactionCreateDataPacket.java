/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Charsets
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  com.google.common.reflect.TypeToken
 *  com.google.gson.Gson
 *  net.minecraft.entity.player.EntityPlayer
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.base.Charsets;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionCreateGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class FactionCreateDataPacket
implements IPacket,
IClientPacket {
    public ArrayList<HashMap<String, String>> countries = new ArrayList();
    public String serverName;

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.serverName = data.readUTF();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        try {
            Type listType = new TypeToken<ArrayList<HashMap<String, String>>>(){}.getType();
            this.countries = this.serverName.equals("dev") ? (ArrayList)new Gson().fromJson("[{\"name\":\"AfriqueDuSud\",\"x\":\"100\",\"z\":\"2250\"},{\"name\":\"Altai\",\"x\":\"2122\",\"z\":\"-2692\"},{\"name\":\"Crete\",\"x\":\"-81\",\"z\":\"-1574\"},{\"name\":\"Guyana\",\"x\":\"-4320\",\"z\":\"120\"},{\"name\":\"Koweit\",\"x\":\"1033\",\"z\":\"-1224\"},{\"name\":\"Maroc\",\"x\":\"-1530\",\"z\":\"-1420\"},{\"name\":\"Tchad\",\"x\":\"-290\",\"z\":\"-370\"},{\"name\":\"TerreBooth\",\"x\":\"2100\",\"z\":\"4580\"},{\"name\":\"TerreLiard\",\"x\":\"820\",\"z\":\"4320\"},{\"name\":\"Tunisie\",\"x\":\"-830\",\"z\":\"-1560\"}]", listType) : (ArrayList)new Gson().fromJson((Reader)new InputStreamReader(new URL("https://apiv2.nationsglory.fr/mods/country_list/" + this.serverName).openStream(), Charsets.UTF_8), listType);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        FactionCreateGUI.availableCountries = this.countries;
        if (this.countries != null && this.countries.size() > 0) {
            FactionCreateGUI.selectedCountry = this.countries.get(0);
        }
        FactionCreateGUI.loaded = true;
    }
}

