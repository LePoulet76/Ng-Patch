/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.IConnectionHandler
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.common.network.Player
 *  net.minecraft.entity.player.EntityPlayerMP
 *  net.minecraft.network.INetworkManager
 *  net.minecraft.network.NetLoginHandler
 *  net.minecraft.network.packet.NetHandler
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.network.packet.Packet1Login
 *  net.minecraft.server.MinecraftServer
 *  org.apache.commons.lang3.RandomStringUtils
 */
package net.ilexiconn.nationsgui.forge.server.voices.networking.voiceservers;

import cpw.mods.fml.common.network.IConnectionHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.server.voices.VoiceChatServer;
import net.ilexiconn.nationsgui.forge.server.voices.networking.DataStream;
import net.ilexiconn.nationsgui.forge.server.voices.networking.EntityInfo;
import net.ilexiconn.nationsgui.forge.server.voices.networking.PacketManager;
import net.ilexiconn.nationsgui.forge.server.voices.networking.voiceservers.VoiceAuthenticatedServer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.server.MinecraftServer;
import org.apache.commons.lang3.RandomStringUtils;

public class ConnectionHandler
implements IConnectionHandler {
    VoiceChatServer voiceChat;

    public ConnectionHandler(VoiceChatServer vc) {
        this.voiceChat = vc;
    }

    public void playerLoggedIn(Player player, NetHandler netHandler, INetworkManager manager) {
        if (this.voiceChat.getVoiceServer() instanceof VoiceAuthenticatedServer) {
            VoiceAuthenticatedServer it = (VoiceAuthenticatedServer)this.voiceChat.getVoiceServer();
            String pairs = null;
            while (pairs == null) {
                try {
                    pairs = this.sha256(RandomStringUtils.random((int)64));
                }
                catch (NoSuchAlgorithmException var8) {
                    var8.printStackTrace();
                }
            }
            EntityPlayerMP stream = (EntityPlayerMP)player;
            it.addHash(new EntityInfo(stream.func_71114_r(), stream.field_70157_k), pairs);
            PacketDispatcher.sendPacketToPlayer((Packet)PacketManager.getVoiceServerAutheticationPacket(this.voiceChat, it.type, pairs), (Player)player);
        } else {
            PacketDispatcher.sendPacketToPlayer((Packet)PacketManager.getVoiceServerPacket(this.voiceChat, this.voiceChat.getVoiceServer().type), (Player)player);
        }
        for (Map.Entry pairs1 : this.voiceChat.getServerNetwork().getDataManager().streaming.entrySet()) {
            DataStream stream1 = (DataStream)pairs1.getValue();
            EntityPlayerMP entity = (EntityPlayerMP)stream1.player;
            this.voiceChat.getVoiceServer().sendEntityData(player, stream1.id, entity.field_71092_bJ, entity.field_70165_t, entity.field_70163_u, entity.field_70161_v, entity.field_70159_w, entity.field_70181_x, entity.field_70179_y);
        }
    }

    public String connectionReceived(NetLoginHandler netHandler, INetworkManager manager) {
        return null;
    }

    public void connectionOpened(NetHandler netClientHandler, String server, int port, INetworkManager manager) {
    }

    public void connectionOpened(NetHandler netClientHandler, MinecraftServer server, INetworkManager manager) {
    }

    public void connectionClosed(INetworkManager manager) {
    }

    public void clientLoggedIn(NetHandler clientHandler, INetworkManager manager, Packet1Login login) {
    }

    public String sha256(String s) throws NoSuchAlgorithmException {
        byte[] hash = null;
        try {
            MessageDigest sb = MessageDigest.getInstance("SHA-256");
            hash = sb.digest(s.getBytes());
        }
        catch (NoSuchAlgorithmException var6) {
            var6.printStackTrace();
        }
        StringBuilder var7 = new StringBuilder();
        for (int i = 0; i < hash.length; ++i) {
            String hex = Integer.toHexString(hash[i]);
            if (hex.length() == 1) {
                var7.append(0);
                var7.append(hex.charAt(hex.length() - 1));
                continue;
            }
            var7.append(hex.substring(hex.length() - 2));
        }
        return var7.toString();
    }
}

