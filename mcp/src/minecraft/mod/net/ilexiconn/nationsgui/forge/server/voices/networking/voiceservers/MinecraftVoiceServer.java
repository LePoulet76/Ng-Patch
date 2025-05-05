package net.ilexiconn.nationsgui.forge.server.voices.networking.voiceservers;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import net.ilexiconn.nationsgui.forge.server.voices.VoiceChatServer;
import net.minecraft.network.packet.Packet250CustomPayload;

public class MinecraftVoiceServer extends VoiceServer
{
    private VoiceChatServer voiceChat;

    public MinecraftVoiceServer(VoiceChatServer voiceChat, EnumVoiceNetworkType enumVoiceServer)
    {
        super(enumVoiceServer);
        this.voiceChat = voiceChat;
    }

    public boolean start()
    {
        return false;
    }

    public void handleVoiceData(Player player, byte[] data, int id, boolean end)
    {
        this.voiceChat.getServerNetwork().getDataManager().addQueue(player, data, id, end);
    }

    public void sendVoiceData(Player player, int entityID, int global, byte[] samples)
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(16);
        DataOutputStream outputStream = new DataOutputStream(bos);

        if (samples != null)
        {
            try
            {
                outputStream.writeInt(samples.length);

                for (int var9 = 0; var9 < samples.length; ++var9)
                {
                    outputStream.writeByte(samples[var9]);
                }

                outputStream.writeInt(entityID);
                outputStream.writeInt(global);
            }
            catch (Exception var8)
            {
                var8.printStackTrace();
            }

            Packet250CustomPayload var91 = new Packet250CustomPayload();
            var91.channel = "GVC-SMPL";
            var91.data = bos.toByteArray();
            var91.length = bos.size();
            PacketDispatcher.sendPacketToPlayer(var91, player);
        }
    }

    public void sendChunkVoiceData(Player player, int entityID, int global, byte[] samples, int chunkSize)
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(16);
        DataOutputStream outputStream = new DataOutputStream(bos);

        if (samples != null)
        {
            try
            {
                outputStream.writeInt(samples.length);

                for (int var10 = 0; var10 < samples.length; ++var10)
                {
                    outputStream.writeByte(samples[var10]);
                }

                outputStream.writeInt(chunkSize);
                outputStream.writeInt(entityID);
                outputStream.writeInt(global);
            }
            catch (Exception var9)
            {
                var9.printStackTrace();
            }

            Packet250CustomPayload var101 = new Packet250CustomPayload();
            var101.channel = "GVC-CHSMPL";
            var101.data = bos.toByteArray();
            var101.length = bos.size();
            PacketDispatcher.sendPacketToPlayer(var101, player);
        }
    }

    public void sendVoiceEnd(Player player, int id)
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(16);
        DataOutputStream outputStream = new DataOutputStream(bos);

        try
        {
            outputStream.writeInt(id);
        }
        catch (Exception var6)
        {
            var6.printStackTrace();
        }

        Packet250CustomPayload packet = new Packet250CustomPayload();
        packet.channel = "GVC-SMPLE";
        packet.data = bos.toByteArray();
        packet.length = bos.size();
        PacketDispatcher.sendPacketToPlayer(packet, player);
    }

    public void sendEntityData(Player player, int entityID, String name, double x, double y, double z, double motX, double motY, double motZ)
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(16);
        DataOutputStream outputStream = new DataOutputStream(bos);

        try
        {
            outputStream.writeInt(entityID);
            outputStream.writeUTF(name);
            outputStream.writeDouble(x);
            outputStream.writeDouble(y);
            outputStream.writeDouble(z);
            outputStream.writeDouble(motX);
            outputStream.writeDouble(motY);
            outputStream.writeDouble(motZ);
        }
        catch (Exception var19)
        {
            var19.printStackTrace();
        }

        Packet250CustomPayload packet = new Packet250CustomPayload();
        packet.channel = "GVC-ED";
        packet.data = bos.toByteArray();
        packet.length = bos.size();
        PacketDispatcher.sendPacketToPlayer(packet, player);
    }

    public void sendEntityPosition(Player player, int entityID, double x, double y, double z, double motX, double motY, double motZ)
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(16);
        DataOutputStream outputStream = new DataOutputStream(bos);

        try
        {
            outputStream.writeInt(entityID);
            outputStream.writeDouble(x);
            outputStream.writeDouble(y);
            outputStream.writeDouble(z);
            outputStream.writeDouble(motX);
            outputStream.writeDouble(motY);
            outputStream.writeDouble(motZ);
        }
        catch (Exception var18)
        {
            var18.printStackTrace();
        }

        Packet250CustomPayload packet = new Packet250CustomPayload();
        packet.channel = "GVC-E";
        packet.data = bos.toByteArray();
        packet.length = bos.size();
        PacketDispatcher.sendPacketToPlayer(packet, player);
    }
}
