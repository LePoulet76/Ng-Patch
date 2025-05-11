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
import net.ilexiconn.nationsgui.forge.client.gui.LotoAdminGui;
import net.ilexiconn.nationsgui.forge.client.gui.LotoGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class LotoDataPacket
implements IPacket,
IClientPacket {
    public boolean isAdmin = false;
    public HashMap<String, Object> data = new HashMap();

    public LotoDataPacket(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.data = (HashMap)new Gson().fromJson(data.readUTF(), new TypeToken<HashMap<String, Object>>(){}.getType());
        this.isAdmin = data.readBoolean();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeBoolean(this.isAdmin);
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        if (this.isAdmin) {
            LotoAdminGui.data = this.data;
            LotoAdminGui.loaded = true;
        } else {
            LotoGui.data = this.data;
            LotoGui.loaded = true;
        }
    }
}

