package net.ilexiconn.nationsgui.forge.server.voices.networking;

import cpw.mods.fml.common.network.Player;
import java.util.List;
import net.ilexiconn.nationsgui.forge.server.voices.VoiceChatServer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class ServerNetwork
{
    VoiceChatServer voiceChat;
    protected final DataManager dataManager;

    public ServerNetwork(VoiceChatServer voiceChat)
    {
        this.voiceChat = voiceChat;
        this.dataManager = new DataManager(voiceChat);
    }

    public void init()
    {
        this.dataManager.init();
    }

    public String[] getPlayerIPs()
    {
        List players = MinecraftServer.getServer().getConfigurationManager().playerEntityList;
        String[] ips = new String[players.size()];

        for (int i = 0; i < players.size(); ++i)
        {
            EntityPlayerMP p = (EntityPlayerMP)players.get(i);
            ips[i] = p.getPlayerIP();
        }

        return ips;
    }

    public Player[] getPlayers()
    {
        List pl = MinecraftServer.getServer().getConfigurationManager().playerEntityList;
        Player[] players = (Player[])((Player[])pl.toArray(new Player[pl.size()]));
        return players;
    }

    public Entity getEntityByID(int id)
    {
        MinecraftServer mc = MinecraftServer.getServer();

        synchronized (mc)
        {
            List players = mc.getConfigurationManager().playerEntityList;

            for (int i = 0; i < players.size(); ++i)
            {
                Entity entity = (Entity)players.get(i);

                if (entity.entityId == id)
                {
                    return entity;
                }
            }

            return null;
        }
    }

    public boolean isAddressPlaying(String ip)
    {
        MinecraftServer mc = MinecraftServer.getServer();

        synchronized (mc)
        {
            List players = mc.getConfigurationManager().playerEntityList;

            for (int i = 0; i < players.size(); ++i)
            {
                String address = ((EntityPlayerMP)players.get(i)).getPlayerIP();

                if (ip.equals(address))
                {
                    return true;
                }
            }

            return false;
        }
    }

    public DataManager getDataManager()
    {
        return this.dataManager;
    }
}
