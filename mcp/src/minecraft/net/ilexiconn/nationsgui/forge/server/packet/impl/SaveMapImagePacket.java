/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  net.minecraft.entity.player.EntityPlayer
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.minecraft.entity.player.EntityPlayer;

public class SaveMapImagePacket
implements IPacket,
IServerPacket {
    String fileName;
    String base64;
    boolean lastPart;

    public SaveMapImagePacket(String fileName, String base64, boolean lastPart) {
        this.fileName = fileName;
        this.base64 = base64;
        this.lastPart = lastPart;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.fileName = data.readUTF();
        this.base64 = data.readUTF();
        this.lastPart = data.readBoolean();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.fileName);
        data.writeUTF(this.base64);
        data.writeBoolean(this.lastPart);
    }

    @Override
    public void handleServerPacket(EntityPlayer player) {
    }
}

