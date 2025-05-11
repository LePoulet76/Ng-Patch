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

public class FactionUpdateBannerPacket
implements IPacket {
    private String bannerName;
    public String imageLink;

    public FactionUpdateBannerPacket(String bannerName, String imageLink) {
        this.bannerName = bannerName;
        this.imageLink = imageLink;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.bannerName = data.readUTF();
        this.imageLink = data.readUTF();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.bannerName);
        data.writeUTF(this.imageLink);
    }
}

