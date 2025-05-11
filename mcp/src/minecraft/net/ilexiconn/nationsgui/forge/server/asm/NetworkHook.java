/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.NetLoginHandler
 *  net.minecraft.network.packet.NetHandler
 *  net.minecraft.network.packet.Packet2ClientProtocol
 *  net.minecraft.server.MinecraftServer
 */
package net.ilexiconn.nationsgui.forge.server.asm;

import fr.nationsglory.itemmanager.CommonProxy;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.PlayerListTextPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.SpawnTypePacket;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet2ClientProtocol;
import net.minecraft.server.MinecraftServer;

public class NetworkHook {
    public static void onProtocolPacket(Packet2ClientProtocol packet2ClientProtocol, NetHandler netHandler) {
        MinecraftServer minecraftServer = MinecraftServer.func_71276_C();
        NetLoginHandler netLoginHandler = (NetLoginHandler)netHandler;
        if (CommonProxy.localConfig.isMultipleRespawn()) {
            netLoginHandler.field_72538_b.func_74429_a(PacketRegistry.INSTANCE.generatePacket(new SpawnTypePacket()));
        }
        PlayerListTextPacket playerListTextPacket = new PlayerListTextPacket();
        playerListTextPacket.up = CommonProxy.localConfig.getPlayerListTopText();
        playerListTextPacket.bottom = CommonProxy.localConfig.getPlayerListBottomText();
        netLoginHandler.field_72538_b.func_74429_a(PacketRegistry.INSTANCE.generatePacket(playerListTextPacket));
        netHandler.func_72500_a(packet2ClientProtocol);
    }
}

