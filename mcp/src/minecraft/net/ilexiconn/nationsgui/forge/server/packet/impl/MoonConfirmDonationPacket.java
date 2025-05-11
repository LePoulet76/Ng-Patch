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

public class MoonConfirmDonationPacket
implements IPacket {
    private double value;

    public MoonConfirmDonationPacket(double d) {
        this.value = d;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.value = data.readDouble();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeDouble(this.value);
    }
}

