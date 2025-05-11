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
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseGui;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseListGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class EnterpriseListDataPacket
implements IPacket,
IClientPacket {
    public ArrayList<HashMap<String, String>> enterprises = new ArrayList();
    public List<String> availableTypes = new ArrayList<String>();

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.enterprises = (ArrayList)new Gson().fromJson(data.readUTF(), new TypeToken<ArrayList<HashMap<String, String>>>(){}.getType());
        this.availableTypes = (List)new Gson().fromJson(data.readUTF(), new TypeToken<List<String>>(){}.getType());
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        EnterpriseListGui.enterprises.addAll(this.enterprises);
        EnterpriseListGui.loaded = true;
        EnterpriseGui.availableTypes.clear();
        EnterpriseGui.availableTypes.add("all");
        EnterpriseGui.availableTypes.addAll(this.availableTypes);
    }
}

