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
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class PlayerEventPointsPacket
implements IPacket,
IClientPacket {
    private ArrayList<String> eventPoints;

    public PlayerEventPointsPacket(ArrayList<String> eventPoints) {
        this.eventPoints = eventPoints;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.eventPoints = (ArrayList)new Gson().fromJson(data.readUTF(), new TypeToken<List<String>>(){}.getType());
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(new Gson().toJson(this.eventPoints));
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        System.out.println("debug points event");
        System.out.println(this.eventPoints.toString());
        ClientData.eventPointsValidated.addAll(this.eventPoints);
    }
}

