/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.common.network.Player
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.network.packet.Packet
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import java.net.URI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;

public class OpenUrlPacket
implements IPacket,
IClientPacket,
IServerPacket {
    public String url;

    public OpenUrlPacket(String url) {
        this.url = url;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.url = data.readUTF();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.url);
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        try {
            Class<?> desktop = Class.forName("java.awt.Desktop");
            Object theDesktop = desktop.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
            desktop.getMethod("browse", URI.class).invoke(theDesktop, new URI(this.url));
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @Override
    public void handleServerPacket(EntityPlayer player) {
        PacketDispatcher.sendPacketToPlayer((Packet)PacketRegistry.INSTANCE.generatePacket(this), (Player)((Player)player));
    }
}

