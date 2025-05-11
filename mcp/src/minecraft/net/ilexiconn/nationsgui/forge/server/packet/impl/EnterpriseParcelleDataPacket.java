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
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseParcelleGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class EnterpriseParcelleDataPacket
implements IPacket,
IClientPacket {
    public ArrayList<HashMap<String, Object>> parcellesInfos = new ArrayList();
    public String target;
    public boolean canSeeCoords;

    public EnterpriseParcelleDataPacket(String targetName) {
        this.target = targetName;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.parcellesInfos = (ArrayList)new Gson().fromJson(data.readUTF(), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
        this.canSeeCoords = data.readBoolean();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.target);
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        EnterpriseParcelleGUI.parcellesInfos = this.parcellesInfos;
        EnterpriseParcelleGUI.loaded = true;
        EnterpriseParcelleGUI.canSeeCoords = this.canSeeCoords;
    }
}

