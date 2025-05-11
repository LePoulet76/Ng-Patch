/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;

public class EnterpriseElectricPricePacket
implements IPacket {
    public String enterpriseName;
    public Integer amount;
    public boolean allowAll;
    public boolean allowCountry;
    public boolean allowAlly;

    public EnterpriseElectricPricePacket(String enterpriseName, Integer amount, boolean allowAll, boolean allowCountry, boolean allowAlly) {
        this.enterpriseName = enterpriseName;
        this.amount = amount;
        this.allowAll = allowAll;
        this.allowCountry = allowCountry;
        this.allowAlly = allowAlly;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.enterpriseName = data.readUTF();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.enterpriseName);
        data.writeInt(this.amount.intValue());
        data.writeBoolean(this.allowAll);
        data.writeBoolean(this.allowCountry);
        data.writeBoolean(this.allowAlly);
    }
}

