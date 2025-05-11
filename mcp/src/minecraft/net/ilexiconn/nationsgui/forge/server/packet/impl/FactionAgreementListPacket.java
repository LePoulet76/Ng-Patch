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
import net.ilexiconn.nationsgui.forge.client.gui.faction.WarAgreementListGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class FactionAgreementListPacket
implements IPacket,
IClientPacket {
    public ArrayList<HashMap<String, Object>> agreements = new ArrayList();
    public int warId;
    public boolean canCreateAgreement;
    public String playerFactionId;

    public FactionAgreementListPacket(int warId) {
        this.warId = warId;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.agreements = (ArrayList)new Gson().fromJson(data.readUTF(), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
        this.canCreateAgreement = data.readBoolean();
        this.playerFactionId = data.readUTF();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeInt(this.warId);
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        WarAgreementListGui.agreements = this.agreements;
        WarAgreementListGui.canCreateAgreement = this.canCreateAgreement;
        WarAgreementListGui.playerFactionId = this.playerFactionId;
        WarAgreementListGui.loaded = true;
    }
}

