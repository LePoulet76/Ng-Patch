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
import java.util.List;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class RefreshServerMarkersMapPacket
implements IPacket,
IClientPacket {
    public List<String> markers;

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.markers = (List)new Gson().fromJson(data.readUTF(), new TypeToken<List<String>>(){}.getType());
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(new Gson().toJson(this.markers));
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        ArrayList<Marker> markersToDelete = new ArrayList<Marker>();
        for (Marker marker : NationsMap.instance.markerManager.markerList) {
            if (marker.groupName.equalsIgnoreCase("pays") || marker.groupName.equalsIgnoreCase("morts") || marker.groupName.equalsIgnoreCase("perso") || marker.groupName.equalsIgnoreCase("ressources")) continue;
            markersToDelete.add(marker);
        }
        for (Marker marker : markersToDelete) {
            NationsMap.instance.markerManager.delMarker(marker);
        }
        for (String string : this.markers) {
            String name = string.split("##")[0];
            String group = string.split("##")[1];
            String x = string.split("##")[2];
            String y = string.split("##")[3];
            String z = string.split("##")[4];
            String color = string.split("##")[5];
            int colorInt = 0xFF0000;
            switch (color) {
                case "red": {
                    colorInt = -65536;
                    break;
                }
                case "green": {
                    colorInt = -16711936;
                    break;
                }
                case "blue": {
                    colorInt = -16776961;
                    break;
                }
                case "yellow": {
                    colorInt = -256;
                    break;
                }
                case "pink": {
                    colorInt = -65281;
                    break;
                }
                case "cyan": {
                    colorInt = -16711681;
                    break;
                }
                case "orange": {
                    colorInt = Short.MIN_VALUE;
                    break;
                }
                case "purple": {
                    colorInt = -8388353;
                    break;
                }
                case "white": {
                    colorInt = -1;
                    break;
                }
                case "black": {
                    colorInt = -16777216;
                }
            }
            if (!NationsMap.instance.markerManager.groupList.contains(group)) {
                NationsMap.instance.markerManager.groupList.add(group);
            }
            NationsMap.instance.markerManager.addMarker(name, group, Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(z), 0, colorInt);
        }
        NationsMap.instance.markerManager.update();
    }
}

