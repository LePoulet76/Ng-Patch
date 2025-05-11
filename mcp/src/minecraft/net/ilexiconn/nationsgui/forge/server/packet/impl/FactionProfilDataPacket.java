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
import net.ilexiconn.nationsgui.forge.client.gui.faction.ProfilGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class FactionProfilDataPacket
implements IPacket,
IClientPacket {
    public HashMap<String, Object> playerInfos = new HashMap();
    public String target;
    public String enterpriseName;

    public FactionProfilDataPacket(String targetName, String enterpriseName) {
        this.target = targetName;
        this.enterpriseName = enterpriseName;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.playerInfos = (HashMap)new Gson().fromJson(data.readUTF(), new TypeToken<HashMap<String, Object>>(){}.getType());
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.target);
        data.writeUTF(this.enterpriseName);
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        ProfilGui.playerInfos = this.playerInfos;
        ProfilGui.loaded = true;
    }
}

