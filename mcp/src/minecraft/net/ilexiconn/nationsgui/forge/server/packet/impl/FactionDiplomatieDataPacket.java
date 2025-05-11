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
import java.util.TreeMap;
import net.ilexiconn.nationsgui.forge.client.gui.faction.DiplomatieGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class FactionDiplomatieDataPacket
implements IPacket,
IClientPacket {
    public TreeMap<String, Object> diplomatieInfos = new TreeMap();
    public String target;

    public FactionDiplomatieDataPacket(String targetName) {
        this.target = targetName;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.diplomatieInfos = (TreeMap)new Gson().fromJson(data.readUTF(), new TypeToken<TreeMap<String, Object>>(){}.getType());
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.target);
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        DiplomatieGUI.factionDiplomatieInfos = this.diplomatieInfos;
        DiplomatieGUI.loaded = true;
    }
}

