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
import net.ilexiconn.nationsgui.forge.client.gui.faction.BankGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class FactionBankDataPacket
implements IPacket,
IClientPacket {
    public HashMap<String, Object> factionInfos = new HashMap();
    public String targetFactionId;

    public FactionBankDataPacket(String targetName) {
        this.targetFactionId = targetName;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.factionInfos = (HashMap)new Gson().fromJson(data.readUTF(), new TypeToken<HashMap<String, Object>>(){}.getType());
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.targetFactionId);
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        BankGUI.factionBankInfos = this.factionInfos;
        if (!BankGUI.loaded) {
            BankGUI.lastBalance = 0;
        }
        BankGUI.loaded = true;
        BankGUI.lastBalanceAnimation = System.currentTimeMillis();
        BankGUI.cachedLogs.clear();
    }
}

