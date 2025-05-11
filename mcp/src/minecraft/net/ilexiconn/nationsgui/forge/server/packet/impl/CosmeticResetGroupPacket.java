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

public class CosmeticResetGroupPacket
implements IPacket,
IClientPacket {
    private String playerTarget;
    private String groupId;
    private String categoryId;

    public CosmeticResetGroupPacket(String playerTarget, String groupId, String categoryId) {
        this.playerTarget = playerTarget;
        this.groupId = groupId;
        this.categoryId = categoryId;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.playerTarget = data.readUTF();
        this.groupId = data.readUTF();
        this.categoryId = data.readUTF();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.playerTarget);
        data.writeUTF(this.groupId);
        data.writeUTF(this.categoryId);
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        ClientProxy.SKIN_MANAGER.clearPlayerCachedSkins(this.playerTarget, null);
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new CosmeticCategoryDataPacket(this.categoryId, this.playerTarget)));
    }
}

