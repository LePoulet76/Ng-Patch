/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.network.packet.Packet
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.CosmeticCategoryDataPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;

public class HatExportPacket
implements IPacket,
IClientPacket {
    private String hatIdentifier;

    public HatExportPacket(String hatIdentifier) {
        this.hatIdentifier = hatIdentifier;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.hatIdentifier = data.readUTF();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.hatIdentifier);
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        ClientProxy.SKIN_MANAGER.clearPlayerCachedSkins(player.field_71092_bJ, null);
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new CosmeticCategoryDataPacket("hats", player.field_71092_bJ)));
    }
}

