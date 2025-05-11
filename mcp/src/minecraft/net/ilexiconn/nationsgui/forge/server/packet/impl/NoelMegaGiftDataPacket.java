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
import net.ilexiconn.nationsgui.forge.client.gui.NoelMegaGiftGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class NoelMegaGiftDataPacket
implements IPacket,
IClientPacket {
    public ArrayList<String> history = new ArrayList();
    public boolean megaGiftAround = false;

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.history = (ArrayList)new Gson().fromJson(data.readUTF(), new TypeToken<ArrayList<String>>(){}.getType());
        this.megaGiftAround = data.readBoolean();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        NoelMegaGiftGui.history.addAll(this.history);
        NoelMegaGiftGui.megaGiftAround = this.megaGiftAround;
    }
}

