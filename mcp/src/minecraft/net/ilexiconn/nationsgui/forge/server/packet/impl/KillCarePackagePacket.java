/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  fr.nationsglory.ngcontent.server.entity.EntityCarePackage
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.network.packet.Packet
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import fr.nationsglory.ngcontent.server.entity.EntityCarePackage;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;

public class KillCarePackagePacket
implements IPacket,
IServerPacket,
IClientPacket {
    public static HashMap<String, EntityCarePackage> carePakagesAlive = new HashMap();
    public String defenserName;

    public KillCarePackagePacket(String defenserName) {
        this.defenserName = defenserName;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.defenserName = data.readUTF();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.defenserName);
    }

    @Override
    public void handleServerPacket(EntityPlayer player) {
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new KillCarePackagePacket(this.defenserName)));
    }
}

