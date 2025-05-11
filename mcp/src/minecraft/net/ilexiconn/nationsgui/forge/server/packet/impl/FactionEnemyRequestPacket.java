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
import net.ilexiconn.nationsgui.forge.client.gui.faction.WarRequestGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class FactionEnemyRequestPacket
implements IPacket,
IClientPacket {
    public HashMap<String, Object> warInfos = new HashMap();
    public int warRequestId;

    public FactionEnemyRequestPacket(int warRequestId) {
        this.warRequestId = warRequestId;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.warInfos = (HashMap)new Gson().fromJson(data.readUTF(), new TypeToken<HashMap<String, Object>>(){}.getType());
        this.warRequestId = data.readInt();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeInt(this.warRequestId);
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        WarRequestGUI.warInfos = this.warInfos;
        WarRequestGUI.warRequestId = this.warRequestId;
        WarRequestGUI.loaded = true;
    }
}

