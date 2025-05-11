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
import net.ilexiconn.nationsgui.forge.client.gui.island.IslandListGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class IslandGetImagePacket
implements IPacket,
IClientPacket {
    public String image;
    public String id;
    public String serverNumber;

    public IslandGetImagePacket(String id, String serverNumber) {
        this.id = id;
        this.serverNumber = serverNumber;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.id = data.readUTF();
        this.serverNumber = data.readUTF();
        this.image = data.readUTF();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.id);
        data.writeUTF(this.serverNumber);
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        IslandListGui.base64ImagesByIslandId.put(this.id + "#" + this.serverNumber, this.image);
    }
}

