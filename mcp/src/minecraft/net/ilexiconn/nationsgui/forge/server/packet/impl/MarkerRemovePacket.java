/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  net.minecraft.entity.player.EntityPlayer
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class MarkerRemovePacket
implements IPacket,
IClientPacket {
    private String markerName;

    public MarkerRemovePacket(String markerName) {
        this.markerName = markerName;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.markerName = data.readUTF();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.markerName);
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        for (Map<String, Object> marker : ClientData.markers) {
            if (!marker.get("name").equals(this.markerName)) continue;
            ClientData.markers.remove(marker);
            break;
        }
    }
}

