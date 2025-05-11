/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteStreams
 *  com.google.gson.internal.UnsafeAllocator
 *  cpw.mods.fml.common.network.IPacketHandler
 *  cpw.mods.fml.common.network.Player
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.network.INetworkManager
 *  net.minecraft.network.packet.Packet250CustomPayload
 */
package net.ilexiconn.nationsgui.forge.server.packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.google.gson.internal.UnsafeAllocator;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketCallbacks;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

public class PacketHandler
implements IPacketHandler {
    public void onPacketData(INetworkManager manager, Packet250CustomPayload payload, Player player) {
        EntityPlayer entityPlayer = (EntityPlayer)player;
        ByteArrayDataInput data = ByteStreams.newDataInput((byte[])payload.field_73629_c);
        int packetID = data.readInt();
        try {
            if (packetID < -50) {
                PacketCallbacks callback = PacketCallbacks.values()[packetID + 100];
                callback.handleCallback((EntityPlayer)player, data);
            } else if (packetID >= 0) {
                if (PacketRegistry.INSTANCE.packetList.size() <= packetID) {
                    System.err.println("[NationsGUI] Invalid packet received! THIS IS A CRITICAL ERROR! ID: " + packetID);
                    return;
                }
                IPacket packet = (IPacket)UnsafeAllocator.create().newInstance(PacketRegistry.INSTANCE.packetList.get(packetID));
                NationsGUI.PROXY.handlePacket(data, packet, entityPlayer);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

