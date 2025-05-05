package net.ilexiconn.nationsgui.forge.server.voices.networking;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import net.ilexiconn.nationsgui.forge.server.voices.VoiceChatServer;
import net.ilexiconn.nationsgui.forge.server.voices.networking.voiceservers.EnumVoiceNetworkType;
import net.minecraft.network.packet.Packet250CustomPayload;

public class PacketManager
{
    public static Packet250CustomPayload getVoiceServerAutheticationPacket(VoiceChatServer voiceChat, EnumVoiceNetworkType voiceServerType, String auth)
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
        DataOutputStream outputStream = new DataOutputStream(bos);

        try
        {
            outputStream.writeInt(voiceChat.getServerSettings().getSoundDistance());
            outputStream.writeInt(voiceServerType.ordinal());
            outputStream.writeUTF(auth);
            outputStream.writeInt(5447);
        }
        catch (Exception var6)
        {
            var6.printStackTrace();
        }

        Packet250CustomPayload packet = new Packet250CustomPayload();
        packet.channel = "GVC-VSA";
        packet.data = bos.toByteArray();
        packet.length = bos.size();
        return packet;
    }

    public static Packet250CustomPayload getVoiceServerPacket(VoiceChatServer voiceChat, EnumVoiceNetworkType voiceServerType)
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
        DataOutputStream outputStream = new DataOutputStream(bos);

        try
        {
            outputStream.writeInt(voiceChat.getServerSettings().getSoundDistance());
            outputStream.writeInt(voiceServerType.ordinal());
        }
        catch (Exception var5)
        {
            var5.printStackTrace();
        }

        Packet250CustomPayload packet = new Packet250CustomPayload();
        packet.channel = "GVC-VS";
        packet.data = bos.toByteArray();
        packet.length = bos.size();
        return packet;
    }
}
