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

public class FactionSellCountryPacket
implements IPacket {
    public String factionId;
    private int amount;

    public FactionSellCountryPacket(String factionId, int amount) {
        this.factionId = factionId;
        this.amount = amount;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.factionId = data.readUTF();
        this.amount = data.readInt();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.factionId);
        data.writeInt(this.amount);
    }
}

