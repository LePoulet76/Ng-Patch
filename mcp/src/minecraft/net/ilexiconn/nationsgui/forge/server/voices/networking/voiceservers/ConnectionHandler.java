package net.ilexiconn.nationsgui.forge.server.voices.networking.voiceservers;

import cpw.mods.fml.common.network.IConnectionHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map.Entry;
import net.ilexiconn.nationsgui.forge.server.voices.VoiceChatServer;
import net.ilexiconn.nationsgui.forge.server.voices.networking.DataStream;
import net.ilexiconn.nationsgui.forge.server.voices.networking.EntityInfo;
import net.ilexiconn.nationsgui.forge.server.voices.networking.PacketManager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.server.MinecraftServer;
import org.apache.commons.lang3.RandomStringUtils;

public class ConnectionHandler implements IConnectionHandler
{
    VoiceChatServer voiceChat;

    public ConnectionHandler(VoiceChatServer vc)
    {
        this.voiceChat = vc;
    }

    public void playerLoggedIn(Player player, NetHandler netHandler, INetworkManager manager)
    {
        if (this.voiceChat.getVoiceServer() instanceof VoiceAuthenticatedServer)
        {
            VoiceAuthenticatedServer it1 = (VoiceAuthenticatedServer)this.voiceChat.getVoiceServer();
            String pairs1 = null;

            while (pairs1 == null)
            {
                try
                {
                    pairs1 = this.sha256(RandomStringUtils.random(64));
                }
                catch (NoSuchAlgorithmException var8)
                {
                    var8.printStackTrace();
                }
            }

            EntityPlayerMP stream1 = (EntityPlayerMP)player;
            it1.addHash(new EntityInfo(stream1.getPlayerIP(), stream1.entityId), pairs1);
            PacketDispatcher.sendPacketToPlayer(PacketManager.getVoiceServerAutheticationPacket(this.voiceChat, it1.type, pairs1), player);
        }
        else
        {
            PacketDispatcher.sendPacketToPlayer(PacketManager.getVoiceServerPacket(this.voiceChat, this.voiceChat.getVoiceServer().type), player);
        }

        Iterator it11 = this.voiceChat.getServerNetwork().getDataManager().streaming.entrySet().iterator();

        while (it11.hasNext())
        {
            Entry pairs11 = (Entry)it11.next();
            DataStream stream11 = (DataStream)pairs11.getValue();
            EntityPlayerMP entity = (EntityPlayerMP)stream11.player;
            this.voiceChat.getVoiceServer().sendEntityData(player, stream11.id, entity.username, entity.posX, entity.posY, entity.posZ, entity.motionX, entity.motionY, entity.motionZ);
        }
    }

    public String connectionReceived(NetLoginHandler netHandler, INetworkManager manager)
    {
        return null;
    }

    public void connectionOpened(NetHandler netClientHandler, String server, int port, INetworkManager manager) {}

    public void connectionOpened(NetHandler netClientHandler, MinecraftServer server, INetworkManager manager) {}

    public void connectionClosed(INetworkManager manager) {}

    public void clientLoggedIn(NetHandler clientHandler, INetworkManager manager, Packet1Login login) {}

    public String sha256(String s) throws NoSuchAlgorithmException
    {
        byte[] hash = null;

        try
        {
            MessageDigest var7 = MessageDigest.getInstance("SHA-256");
            hash = var7.digest(s.getBytes());
        }
        catch (NoSuchAlgorithmException var6)
        {
            var6.printStackTrace();
        }

        StringBuilder var71 = new StringBuilder();

        for (int i = 0; i < hash.length; ++i)
        {
            String hex = Integer.toHexString(hash[i]);

            if (hex.length() == 1)
            {
                var71.append(0);
                var71.append(hex.charAt(hex.length() - 1));
            }
            else
            {
                var71.append(hex.substring(hex.length() - 2));
            }
        }

        return var71.toString();
    }
}
