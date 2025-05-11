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

public class RemoveSalePacket
implements IPacket {
    private String uuid;
    private boolean drop;

    public RemoveSalePacket(String uuid, boolean drop) {
        this.uuid = uuid;
        this.drop = drop;
    }

    public RemoveSalePacket() {
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.uuid);
        data.writeInt(this.drop ? 1 : 0);
    }
}

