/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  com.google.gson.Gson
 *  com.google.gson.reflect.TypeToken
 *  fr.nationsglory.client.gui.OGMMachineGUI
 *  net.minecraft.entity.player.EntityPlayer
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fr.nationsglory.client.gui.OGMMachineGUI;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class OGMRecordsInfosPacket
implements IPacket,
IClientPacket {
    public HashMap<String, Integer> ogmRecords = new HashMap();

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.ogmRecords = (HashMap)new Gson().fromJson(data.readUTF(), new TypeToken<HashMap<String, Integer>>(){}.getType());
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        OGMMachineGUI.ogmRecords = this.ogmRecords;
    }
}

