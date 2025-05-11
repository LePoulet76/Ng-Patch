/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  com.google.gson.Gson
 *  com.google.gson.reflect.TypeToken
 *  fr.nationsglory.nationsmap.NationsMap
 *  fr.nationsglory.nationsmap.map.Marker
 *  net.minecraft.entity.player.EntityPlayer
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fr.nationsglory.nationsmap.NationsMap;
import fr.nationsglory.nationsmap.map.Marker;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class RefreshfHomeLocationsMapPacket
implements IPacket,
IClientPacket {
    public HashMap<String, String> fHomeLocations;

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.fHomeLocations = (HashMap)new Gson().fromJson(data.readUTF(), new TypeToken<HashMap<String, String>>(){}.getType());
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(new Gson().toJson(this.fHomeLocations));
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        ArrayList<Marker> markersToDelete = new ArrayList<Marker>();
        for (Marker marker : NationsMap.instance.markerManager.markerList) {
            if (!marker.groupName.equalsIgnoreCase("pays")) continue;
            markersToDelete.add(marker);
        }
        for (Marker marker : markersToDelete) {
            NationsMap.instance.markerManager.delMarker(marker);
        }
        if (!NationsMap.instance.markerManager.groupList.contains("Pays")) {
            NationsMap.instance.markerManager.groupList.add("Pays");
        }
        Iterator<Map.Entry<String, String>> it = this.fHomeLocations.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> pair = it.next();
            String countryName = pair.getValue();
            String countryLocation = pair.getKey();
            NationsMap.instance.markerManager.addMarker(countryName, "Pays", Integer.parseInt(countryLocation.split("#")[0]), Integer.parseInt(countryLocation.split("#")[1]), Integer.parseInt(countryLocation.split("#")[2]), 0, -16777216);
            it.remove();
        }
    }
}

