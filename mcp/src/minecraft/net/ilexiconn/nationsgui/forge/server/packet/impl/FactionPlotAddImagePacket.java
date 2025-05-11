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

public class FactionPlotAddImagePacket
implements IPacket {
    public String imageLink;
    private int plotId;

    public FactionPlotAddImagePacket(int plotId, String imageLink) {
        this.plotId = plotId;
        this.imageLink = imageLink;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.plotId = data.readInt();
        this.imageLink = data.readUTF();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeInt(this.plotId);
        data.writeUTF(this.imageLink);
    }
}

