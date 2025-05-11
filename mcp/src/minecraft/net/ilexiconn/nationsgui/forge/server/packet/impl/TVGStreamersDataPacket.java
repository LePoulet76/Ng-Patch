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
import net.ilexiconn.nationsgui.forge.client.gui.TVGStreamersGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class TVGStreamersDataPacket
implements IPacket,
IClientPacket {
    private Integer totalCagnotte;
    private String targetStreamer;
    private String playerStreamer;
    private HashMap<String, String> streamerData;
    private Long playerTime;
    private Integer playerPosition;

    public TVGStreamersDataPacket(String targetStreamer) {
        this.targetStreamer = targetStreamer;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.playerStreamer = data.readUTF();
        this.streamerData = (HashMap)new Gson().fromJson(data.readUTF(), new TypeToken<HashMap<String, String>>(){}.getType());
        this.totalCagnotte = data.readInt();
        this.playerTime = data.readLong();
        this.playerPosition = data.readInt();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.targetStreamer);
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        TVGStreamersGui.playerStreamer = this.playerStreamer;
        if (TVGStreamersGui.selectedStreamer.isEmpty()) {
            TVGStreamersGui.selectedStreamer = this.playerStreamer;
        }
        TVGStreamersGui.streamerData = this.streamerData;
        TVGStreamersGui.totalCagnotte = this.totalCagnotte;
        TVGStreamersGui.playerPosition = this.playerPosition;
        TVGStreamersGui.playerTime = this.playerTime;
        TVGStreamersGui.loaded = true;
    }
}

