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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.gui.NoelAventGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class NoelAventDataPacket
implements IPacket,
IClientPacket {
    public HashMap<Integer, String> gifts = new HashMap();
    public long serverTime = 0L;

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.gifts = (HashMap)new Gson().fromJson(data.readUTF(), new TypeToken<HashMap<Integer, String>>(){}.getType());
        this.serverTime = data.readLong();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        NoelAventGui.gifts = this.gifts;
        NoelAventGui.serverTime = this.serverTime;
        NoelAventGui.loaded = true;
        NoelAventGui.currentDay = new SimpleDateFormat("dd").format(new Date(this.serverTime));
        NoelAventGui.currentMonth = new SimpleDateFormat("MM").format(new Date(this.serverTime));
    }
}

