/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.network.packet.Packet250CustomPayload
 */
package net.ilexiconn.nationsgui.forge.client.voices.networking;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import net.ilexiconn.nationsgui.forge.client.voices.VoiceChatClient;
import net.ilexiconn.nationsgui.forge.client.voices.networking.VoiceClient;
import net.ilexiconn.nationsgui.forge.server.voices.EntityVector;
import net.ilexiconn.nationsgui.forge.server.voices.networking.voiceservers.EnumVoiceNetworkType;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;

public class MinecraftVoiceClient
extends VoiceClient {
    public MinecraftVoiceClient(EnumVoiceNetworkType enumVoiceServer) {
        super(enumVoiceServer);
    }

    @Override
    public void start() {
    }

    @Override
    public void handleEnd(int id) {
        VoiceChatClient.getSoundManager().alertEnd(id);
    }

    @Override
    public void handlePacket(int entityID, byte[] data, int chunkSize, int global) {
        VoiceChatClient.getSoundManager().getSoundPreProcessor().process(entityID, data, chunkSize, global);
    }

    @Override
    public void sendVoiceData(byte[] samples, boolean end) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(16);
        DataOutputStream outputStream = new DataOutputStream(bos);
        if (samples != null) {
            try {
                if (!end) {
                    outputStream.writeInt(samples.length);
                    for (int packet = 0; packet < samples.length; ++packet) {
                        outputStream.writeByte(samples[packet]);
                    }
                }
            }
            catch (Exception var6) {
                var6.printStackTrace();
            }
        }
        Packet250CustomPayload var7 = new Packet250CustomPayload();
        var7.field_73630_a = "GVC-SMPL" + (end ? "E" : "");
        var7.field_73629_c = bos.toByteArray();
        var7.field_73628_b = bos.size();
        PacketDispatcher.sendPacketToServer((Packet)var7);
    }

    @Override
    public void stop() {
    }

    @Override
    public void handleEntityData(int entityID, String name, double x, double y, double z, double motX, double motY, double motZ) {
        VoiceChatClient.getSoundManager().entityData.put(entityID, new EntityVector(entityID, name, x, y, z, motX, motY, motZ));
    }

    @Override
    public void handleEntityPosition(int entityID, double x, double y, double z, double motX, double motY, double motZ) {
        EntityVector entityVector = VoiceChatClient.getSoundManager().entityData.get(entityID);
        entityVector.setPosition(x, y, z);
        entityVector.setVelocity(motX, motY, motZ);
    }
}

