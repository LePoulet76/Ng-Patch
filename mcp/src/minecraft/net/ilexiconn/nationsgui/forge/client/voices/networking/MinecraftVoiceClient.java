package net.ilexiconn.nationsgui.forge.client.voices.networking;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import net.ilexiconn.nationsgui.forge.client.voices.VoiceChatClient;
import net.ilexiconn.nationsgui.forge.server.voices.EntityVector;
import net.ilexiconn.nationsgui.forge.server.voices.networking.voiceservers.EnumVoiceNetworkType;
import net.minecraft.network.packet.Packet250CustomPayload;

public class MinecraftVoiceClient extends VoiceClient
{
    public MinecraftVoiceClient(EnumVoiceNetworkType enumVoiceServer)
    {
        super(enumVoiceServer);
    }

    public void start() {}

    public void handleEnd(int id)
    {
        VoiceChatClient.getSoundManager().alertEnd(id);
    }

    public void handlePacket(int entityID, byte[] data, int chunkSize, int global)
    {
        VoiceChatClient.getSoundManager().getSoundPreProcessor().process(entityID, data, chunkSize, global);
    }

    public void sendVoiceData(byte[] samples, boolean end)
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(16);
        DataOutputStream outputStream = new DataOutputStream(bos);

        if (samples != null)
        {
            try
            {
                if (!end)
                {
                    outputStream.writeInt(samples.length);

                    for (int var7 = 0; var7 < samples.length; ++var7)
                    {
                        outputStream.writeByte(samples[var7]);
                    }
                }
            }
            catch (Exception var6)
            {
                var6.printStackTrace();
            }
        }

        Packet250CustomPayload var71 = new Packet250CustomPayload();
        var71.channel = "GVC-SMPL" + (end ? "E" : "");
        var71.data = bos.toByteArray();
        var71.length = bos.size();
        PacketDispatcher.sendPacketToServer(var71);
    }

    public void stop() {}

    public void handleEntityData(int entityID, String name, double x, double y, double z, double motX, double motY, double motZ)
    {
        VoiceChatClient.getSoundManager().entityData.put(Integer.valueOf(entityID), new EntityVector(entityID, name, x, y, z, motX, motY, motZ));
    }

    public void handleEntityPosition(int entityID, double x, double y, double z, double motX, double motY, double motZ)
    {
        EntityVector entityVector = (EntityVector)VoiceChatClient.getSoundManager().entityData.get(Integer.valueOf(entityID));
        entityVector.setPosition(x, y, z);
        entityVector.setVelocity(motX, motY, motZ);
    }
}
