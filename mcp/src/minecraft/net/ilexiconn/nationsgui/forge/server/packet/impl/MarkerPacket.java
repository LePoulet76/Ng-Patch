/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  com.google.gson.Gson
 *  net.minecraft.entity.player.EntityPlayer
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class MarkerPacket
implements IPacket,
IClientPacket {
    private Map<String, Object> markerData;

    public MarkerPacket(Map<String, Object> markerData) {
        this.markerData = markerData;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.markerData = (Map)new Gson().fromJson(data.readUTF(), Map.class);
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(new Gson().toJson(this.markerData));
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        ArrayList<Map<String, Object>> newMarkersList = new ArrayList<Map<String, Object>>();
        boolean newMarkerAdded = false;
        for (Map<String, Object> marker : ClientData.markers) {
            if (marker.get("name").equals(this.markerData.get("name"))) {
                newMarkersList.add(this.markerData);
                newMarkerAdded = true;
                continue;
            }
            newMarkersList.add(marker);
        }
        if (!newMarkerAdded) {
            newMarkersList.add(this.markerData);
        }
        ClientData.markers = newMarkersList;
    }
}

