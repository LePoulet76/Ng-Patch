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
 *  fr.nationsglory.nationsmap.overlay.OverlayZone
 *  net.minecraft.entity.player.EntityPlayer
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fr.nationsglory.nationsmap.NationsMap;
import fr.nationsglory.nationsmap.map.Marker;
import fr.nationsglory.nationsmap.overlay.OverlayZone;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class RefreshZonesMapPacket
implements IPacket,
IClientPacket {
    public HashMap<String, String> zoneLocations;
    public ArrayList<String> zoneChunks;

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.zoneLocations = (HashMap)new Gson().fromJson(data.readUTF(), new TypeToken<HashMap<String, String>>(){}.getType());
        this.zoneChunks = (ArrayList)new Gson().fromJson(data.readUTF(), new TypeToken<ArrayList<String>>(){}.getType());
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(new Gson().toJson(this.zoneLocations));
        data.writeUTF(new Gson().toJson(this.zoneChunks));
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        System.out.println("handleClientPacket refreshzone");
        ArrayList<Marker> markersToDelete = new ArrayList<Marker>();
        for (Marker marker : NationsMap.instance.markerManager.markerList) {
            if (!marker.groupName.equalsIgnoreCase("ressources")) continue;
            markersToDelete.add(marker);
        }
        for (Marker marker : markersToDelete) {
            NationsMap.instance.markerManager.delMarker(marker);
        }
        if (!NationsMap.instance.markerManager.groupList.contains("Ressources")) {
            NationsMap.instance.markerManager.groupList.add("Ressources");
        }
        Iterator<Map.Entry<String, String>> it = this.zoneLocations.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> pair = it.next();
            String zoneName = pair.getValue();
            String zoneLocation = pair.getKey();
            NationsMap.instance.markerManager.addMarker(zoneName, "Ressources", Integer.parseInt(zoneLocation.split("#")[0]), 64, Integer.parseInt(zoneLocation.split("#")[1]), 0, -16711936);
            it.remove();
        }
        OverlayZone.zoneChunks = this.zoneChunks;
    }
}

