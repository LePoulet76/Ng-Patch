/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.player.EntityPlayer
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.util.SoundStreamer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class MusicPacket
implements IPacket,
IClientPacket {
    public String filename = "";

    @Override
    public void handleClientPacket(EntityPlayer player) {
        if (ClientProxy.commandPlayer != null && ClientProxy.commandPlayer.isPlaying()) {
            ClientProxy.commandPlayer.forceClose();
        }
        ClientProxy.commandPlayer = new SoundStreamer("https://static.nationsglory.fr/" + this.filename);
        ClientProxy.commandPlayer.setVolume(Minecraft.func_71410_x().field_71474_y.field_74340_b * 0.25f);
        new Thread(ClientProxy.commandPlayer).start();
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.filename = data.readUTF();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.filename);
    }
}

