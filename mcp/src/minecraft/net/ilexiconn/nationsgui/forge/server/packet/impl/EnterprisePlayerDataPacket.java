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
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class EnterprisePlayerDataPacket
implements IPacket,
IClientPacket {
    public HashMap<String, Object> playerInfos = new HashMap();
    public String playerName;
    public String enterpriseName;

    public EnterprisePlayerDataPacket(String enterpriseName, String playerName) {
        this.enterpriseName = enterpriseName;
        this.playerName = playerName;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.playerName = data.readUTF();
        this.enterpriseName = data.readUTF();
        this.playerInfos = (HashMap)new Gson().fromJson(data.readUTF(), new TypeToken<HashMap<String, Object>>(){}.getType());
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.playerName);
        data.writeUTF(this.enterpriseName);
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        EnterpriseGui.playerTooltip.put(this.enterpriseName + "##" + this.playerName, this.playerInfos);
    }
}

