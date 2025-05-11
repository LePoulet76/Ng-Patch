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
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseContractForm_Loan_Gui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class EnterpriseContractFormLoanPacket
implements IPacket,
IClientPacket {
    public HashMap<String, Object> infos = new HashMap();
    public String enterpriseName;

    public EnterpriseContractFormLoanPacket(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.infos = (HashMap)new Gson().fromJson(data.readUTF(), new TypeToken<HashMap<String, Object>>(){}.getType());
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.enterpriseName);
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        EnterpriseContractForm_Loan_Gui.data = this.infos;
        EnterpriseContractForm_Loan_Gui.loaded = true;
    }
}

