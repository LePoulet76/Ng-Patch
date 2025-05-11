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
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseBankGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class EnterpriseBankDataPacket
implements IPacket,
IClientPacket {
    public HashMap<String, Object> bankInfos = new HashMap();
    public String enterpriseName;

    public EnterpriseBankDataPacket(String targetName) {
        this.enterpriseName = targetName;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.bankInfos = (HashMap)new Gson().fromJson(data.readUTF(), new TypeToken<HashMap<String, Object>>(){}.getType());
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.enterpriseName);
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        EnterpriseBankGUI.enterpriseBankInfos = this.bankInfos;
        EnterpriseBankGUI.loaded = true;
    }
}

