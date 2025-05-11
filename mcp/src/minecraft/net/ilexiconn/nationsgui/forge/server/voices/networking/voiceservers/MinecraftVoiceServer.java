/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.common.network.Player
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.network.packet.Packet250CustomPayload
 */
package net.ilexiconn.nationsgui.forge.server.voices.networking.voiceservers;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import net.ilexiconn.nationsgui.forge.server.voices.VoiceChatServer;
import net.ilexiconn.nationsgui.forge.server.voices.networking.voiceservers.EnumVoiceNetworkType;
import net.ilexiconn.nationsgui.forge.server.voices.networking.voiceservers.VoiceServer;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;

public class MinecraftVoiceServer
extends VoiceServer {
    private VoiceChatServer voiceChat;

    public MinecraftVoiceServer(VoiceChatServer voiceChat, EnumVoiceNetworkType enumVoiceServer) {
        super(enumVoiceServer);
        this.voiceChat = voiceChat;
    }

    @Override
    public boolean start() {
        return false;
    }

    @Override
    public void handleVoiceData(Player player, byte[] data, int id, boolean end) {
        this.voiceChat.getServerNetwork().getDataManager().addQueue(player, data, id, end);
    }

    @Override
    public void sendVoiceData(Player player, int entityID, int global, byte[] samples) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(16);
        DataOutputStream outputStream = new DataOutputStream(bos);
        if (samples != null) {
            try {
                outputStream.writeInt(samples.length);
                for (int packet = 0; packet < samples.length; ++packet) {
                    outputStream.writeByte(samples[packet]);
                }
                outputStream.writeInt(entityID);
                outputStream.writeInt(global);
            }
            catch (Exception var8) {
                var8.printStackTrace();
            }
            Packet250CustomPayload var9 = new Packet250CustomPayload();
            var9.field_73630_a = "GVC-SMPL";
            var9.field_73629_c = bos.toByteArray();
            var9.field_73628_b = bos.size();
            PacketDispatcher.sendPacketToPlayer((Packet)var9, (Player)player);
        }
    }

    @Override
    public void sendChunkVoiceData(Player player, int entityID, int global, byte[] samples, int chunkSize) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(16);
        DataOutputStream outputStream = new DataOutputStream(bos);
        if (samples != null) {
            try {
                outputStream.writeInt(samples.length);
                for (int packet = 0; packet < samples.length; ++packet) {
                    outputStream.writeByte(samples[packet]);
                }
                outputStream.writeInt(chunkSize);
                outputStream.writeInt(entityID);
                outputStream.writeInt(global);
            }
            catch (Exception var9) {
                var9.printStackTrace();
            }
            Packet250CustomPayload var10 = new Packet250CustomPayload();
            var10.field_73630_a = "GVC-CHSMPL";
            var10.field_73629_c = bos.toByteArray();
            var10.field_73628_b = bos.size();
            PacketDispatcher.sendPacketToPlayer((Packet)var10, (Player)player);
        }
    }

    @Override
    public void sendVoiceEnd(Player player, int id) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(16);
        DataOutputStream outputStream = new DataOutputStream(bos);
        try {
            outputStream.writeInt(id);
        }
        catch (Exception var6) {
            var6.printStackTrace();
        }
        Packet250CustomPayload packet = new Packet250CustomPayload();
        packet.field_73630_a = "GVC-SMPLE";
        packet.field_73629_c = bos.toByteArray();
        packet.field_73628_b = bos.size();
        PacketDispatcher.sendPacketToPlayer((Packet)packet, (Player)player);
    }

    @Override
    public void sendEntityData(Player player, int entityID, String name, double x, double y, double z, double motX, double motY, double motZ) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(16);
        DataOutputStream outputStream = new DataOutputStream(bos);
        try {
            outputStream.writeInt(entityID);
            outputStream.writeUTF(name);
            outputStream.writeDouble(x);
            outputStream.writeDouble(y);
            outputStream.writeDouble(z);
            outputStream.writeDouble(motX);
            outputStream.writeDouble(motY);
            outputStream.writeDouble(motZ);
        }
        catch (Exception var19) {
            var19.printStackTrace();
        }
        Packet250CustomPayload packet = new Packet250CustomPayload();
        packet.field_73630_a = "GVC-ED";
        packet.field_73629_c = bos.toByteArray();
        packet.field_73628_b = bos.size();
        PacketDispatcher.sendPacketToPlayer((Packet)packet, (Player)player);
    }

    @Override
    public void sendEntityPosition(Player player, int entityID, double x, double y, double z, double motX, double motY, double motZ) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(16);
        DataOutputStream outputStream = new DataOutputStream(bos);
        try {
            outputStream.writeInt(entityID);
            outputStream.writeDouble(x);
            outputStream.writeDouble(y);
            outputStream.writeDouble(z);
            outputStream.writeDouble(motX);
            outputStream.writeDouble(motY);
            outputStream.writeDouble(motZ);
        }
        catch (Exception var18) {
            var18.printStackTrace();
        }
        Packet250CustomPayload packet = new Packet250CustomPayload();
        packet.field_73630_a = "GVC-E";
        packet.field_73629_c = bos.toByteArray();
        packet.field_73628_b = bos.size();
        PacketDispatcher.sendPacketToPlayer((Packet)packet, (Player)player);
    }
}

