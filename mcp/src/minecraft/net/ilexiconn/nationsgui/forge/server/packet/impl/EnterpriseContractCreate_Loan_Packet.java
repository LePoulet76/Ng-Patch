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

public class EnterpriseContractCreate_Loan_Packet
implements IPacket {
    public String enterpriseName;
    public String content;
    public Integer price;
    public String type;

    public EnterpriseContractCreate_Loan_Packet(String enterpriseName, String content, Integer price, String type) {
        this.enterpriseName = enterpriseName;
        this.content = content;
        this.price = price;
        this.type = type;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.enterpriseName);
        data.writeUTF(this.content);
        data.writeInt(this.price.intValue());
        data.writeUTF(this.type);
    }
}

