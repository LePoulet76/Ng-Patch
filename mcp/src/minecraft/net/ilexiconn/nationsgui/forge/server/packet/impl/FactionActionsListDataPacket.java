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
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.gui.faction.ActionsListGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class FactionActionsListDataPacket
implements IPacket,
IClientPacket {
    public ArrayList<HashMap<String, String>> actions = new ArrayList();
    public String factionTarget;

    public FactionActionsListDataPacket(String factionTarget) {
        this.factionTarget = factionTarget;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.actions = (ArrayList)new Gson().fromJson(data.readUTF(), new TypeToken<ArrayList<HashMap<String, String>>>(){}.getType());
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.factionTarget);
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        ActionsListGui.actions = this.actions;
        ActionsListGui.loaded = true;
    }
}

