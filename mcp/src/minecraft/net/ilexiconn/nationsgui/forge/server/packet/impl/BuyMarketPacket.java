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

public class BuyMarketPacket
implements IPacket {
    private String uuid;
    private int quantity;

    public BuyMarketPacket(String uuid, int quantity) {
        this.uuid = uuid;
        this.quantity = quantity;
    }

    public BuyMarketPacket() {
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.uuid);
        data.writeInt(this.quantity);
    }
}

