/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.IPacketHandler
 *  cpw.mods.fml.common.network.Player
 *  net.minecraft.network.INetworkManager
 *  net.minecraft.network.packet.Packet250CustomPayload
 */
package net.ilexiconn.nationsgui.forge.client.voices.networking;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.net.InetSocketAddress;
import net.ilexiconn.nationsgui.forge.server.voices.VoiceChat;
import net.ilexiconn.nationsgui.forge.server.voices.networking.voiceservers.EnumVoiceNetworkType;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

public class ClientPacketHandler
implements IPacketHandler {
    private void executePacket(INetworkManager manager, String channel, Packet250CustomPayload packet, Player player) {
        if (channel.equals("GVC-SMPL")) {
            this.handleVoiceData(packet, player);
        } else if (channel.equals("GVC-SMPLE")) {
            this.handleVoiceEnd(packet, player);
        } else if (channel.equals("GVC-CHSMPL")) {
            this.handleChunkVoiceData(packet, player);
        } else if (channel.equals("GVC-VSA")) {
            this.handleVoiceServerAuthentication(manager, packet, player);
        } else if (channel.equals("GVC-VS")) {
            this.handleVoiceServer(manager, packet, player);
        } else if (channel.equals("GVC-E")) {
            this.handleEntityPosition(packet, player);
        } else if (channel.equals("GVC-ED")) {
            this.handleEntityData(packet, player);
        } else if (channel.equals("GVC-FORCEB")) {
            this.handleForceBandwidth(packet, player);
        }
    }

    private void handleForceBandwidth(Packet250CustomPayload packet, Player player) {
        ByteArrayInputStream bais = new ByteArrayInputStream(packet.field_73629_c);
        DataInputStream dis = new DataInputStream(bais);
        int x = -1;
        int x1 = -1;
        try {
            x = dis.readInt();
            x1 = dis.readInt();
        }
        catch (Exception var8) {
            var8.printStackTrace();
        }
        VoiceChat.getProxyInstance().getClientNetwork().setQuality(x, x1);
    }

    private void handleEntityData(Packet250CustomPayload packet, Player player) {
        ByteArrayInputStream bais = new ByteArrayInputStream(packet.field_73629_c);
        DataInputStream dis = new DataInputStream(bais);
        int entityID = -1;
        double x = -1.0;
        double y = -1.0;
        double z = -1.0;
        double motX = -1.0;
        double motY = -1.0;
        double motZ = -1.0;
        String name = null;
        try {
            entityID = dis.readInt();
            name = dis.readUTF();
            x = dis.readDouble();
            y = dis.readDouble();
            z = dis.readDouble();
            motX = dis.readDouble();
            motY = dis.readDouble();
            motZ = dis.readDouble();
        }
        catch (Exception var20) {
            var20.printStackTrace();
        }
        if (name != null && !name.isEmpty()) {
            VoiceChat.getProxyInstance().getClientNetwork().getVoiceClient().handleEntityData(entityID, name, x, y, z, motX, motY, motZ);
        }
    }

    private void handleEntityPosition(Packet250CustomPayload packet, Player player) {
        ByteArrayInputStream bais = new ByteArrayInputStream(packet.field_73629_c);
        DataInputStream dis = new DataInputStream(bais);
        int entityID = -1;
        double x = -1.0;
        double y = -1.0;
        double z = -1.0;
        double motX = -1.0;
        double motY = -1.0;
        double motZ = -1.0;
        try {
            entityID = dis.readInt();
            x = dis.readDouble();
            y = dis.readDouble();
            z = dis.readDouble();
            motX = dis.readDouble();
            motY = dis.readDouble();
            motZ = dis.readDouble();
        }
        catch (Exception var19) {
            var19.printStackTrace();
        }
        VoiceChat.getProxyInstance().getClientNetwork().getVoiceClient().handleEntityPosition(entityID, x, y, z, motX, motY, motZ);
    }

    private void handleVoiceData(Packet250CustomPayload packet, Player player) {
        ByteArrayInputStream bais = new ByteArrayInputStream(packet.field_73629_c);
        DataInputStream dis = new DataInputStream(bais);
        int entityID = -1;
        int global = 0;
        byte[] data = null;
        try {
            int e = dis.readInt();
            data = new byte[e];
            for (int i = 0; i < e; ++i) {
                data[i] = dis.readByte();
            }
            entityID = dis.readInt();
            global = dis.readInt();
        }
        catch (Exception var10) {
            var10.printStackTrace();
        }
        VoiceChat.getProxyInstance().getClientNetwork().getVoiceClient().handlePacket(entityID, data, data.length, global);
    }

    private void handleChunkVoiceData(Packet250CustomPayload packet, Player player) {
        ByteArrayInputStream bais = new ByteArrayInputStream(packet.field_73629_c);
        DataInputStream dis = new DataInputStream(bais);
        int entityID = -1;
        int chunkSize = -1;
        int global = 0;
        byte[] data = null;
        try {
            int e = dis.readInt();
            data = new byte[e];
            for (int i = 0; i < e; ++i) {
                data[i] = dis.readByte();
            }
            chunkSize = dis.readInt();
            entityID = dis.readInt();
            global = dis.readInt();
        }
        catch (Exception var11) {
            var11.printStackTrace();
        }
        VoiceChat.getProxyInstance().getClientNetwork().getVoiceClient().handlePacket(entityID, data, chunkSize, global);
    }

    private void handleVoiceServerAuthentication(INetworkManager manager, Packet250CustomPayload packet, Player player) {
        String serverAddress = null;
        if (manager.func_74430_c() instanceof InetSocketAddress) {
            InetSocketAddress bais = (InetSocketAddress)manager.func_74430_c();
            serverAddress = bais.getAddress().getHostAddress();
        }
        ByteArrayInputStream bais1 = new ByteArrayInputStream(packet.field_73629_c);
        DataInputStream dis = new DataInputStream(bais1);
        int type = 0;
        String hash = null;
        int udp = 0;
        int maxSoundDistance = 63;
        try {
            maxSoundDistance = dis.readInt();
            type = dis.readInt();
            hash = dis.readUTF();
            udp = dis.readInt();
        }
        catch (Exception var12) {
            var12.printStackTrace();
        }
        VoiceChat.getProxyInstance().getClientNetwork().startClientNetwork(EnumVoiceNetworkType.values()[type], hash, serverAddress, udp, maxSoundDistance);
    }

    private void handleVoiceServer(INetworkManager manager, Packet250CustomPayload packet, Player player) {
        ByteArrayInputStream bais = new ByteArrayInputStream(packet.field_73629_c);
        DataInputStream dis = new DataInputStream(bais);
        int type = 0;
        int maxSoundDistance = 63;
        try {
            maxSoundDistance = dis.readInt();
            type = dis.readInt();
        }
        catch (Exception var9) {
            var9.printStackTrace();
        }
        VoiceChat.getProxyInstance().getClientNetwork().startClientNetwork(EnumVoiceNetworkType.values()[type], null, null, 0, maxSoundDistance);
    }

    private void handleVoiceEnd(Packet250CustomPayload packet, Player player) {
        ByteArrayInputStream bais = new ByteArrayInputStream(packet.field_73629_c);
        DataInputStream dis = new DataInputStream(bais);
        int entityID = -1;
        try {
            entityID = dis.readInt();
        }
        catch (Exception var7) {
            var7.printStackTrace();
        }
        VoiceChat.getProxyInstance().getClientNetwork().getVoiceClient().handleEnd(entityID);
    }

    public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
        String channel = packet.field_73630_a;
        this.executePacket(manager, channel, packet, player);
    }
}

