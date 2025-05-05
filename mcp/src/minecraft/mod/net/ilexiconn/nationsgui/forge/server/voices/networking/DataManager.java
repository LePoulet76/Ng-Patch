package net.ilexiconn.nationsgui.forge.server.voices.networking;

import com.google.gson.Gson;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.server.voices.VoiceChatServer;
import net.ilexiconn.nationsgui.forge.server.voices.VoiceConfig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class DataManager implements ITickHandler
{
    private final File voiceConfigFile = new File("config/voices.json");
    private final Gson gson = new Gson();
    public ConcurrentLinkedQueue dataQueue;
    public ConcurrentHashMap streaming;
    public Map<String, Long> mutedPlayers;
    Thread threadKill;
    Thread treadQueue;
    VoiceChatServer voiceChat;
    int tick;
    private HashMap receivedEntityData;

    public DataManager(VoiceChatServer voiceChat)
    {
        this.voiceChat = voiceChat;
        TickRegistry.registerTickHandler(this, Side.SERVER);
    }

    public void save()
    {
        VoiceConfig config = new VoiceConfig();
        config.setMutedPlayers(this.mutedPlayers);

        try
        {
            FileWriter e = new FileWriter(this.voiceConfigFile);
            this.gson.toJson(config, VoiceConfig.class, e);
            e.close();
        }
        catch (IOException var3)
        {
            var3.printStackTrace();
        }
    }

    public void init()
    {
        this.mutedPlayers = new HashMap();

        if (this.voiceConfigFile.exists())
        {
            try
            {
                VoiceConfig e = (VoiceConfig)this.gson.fromJson(new FileReader(this.voiceConfigFile), VoiceConfig.class);
                this.mutedPlayers = e.getMutedPlayers();
            }
            catch (Exception var2)
            {
                var2.printStackTrace();
            }
        }

        this.dataQueue = new ConcurrentLinkedQueue();
        this.streaming = new ConcurrentHashMap();
        this.receivedEntityData = new HashMap();
        this.treadQueue = new Thread(new ThreadDataQueue(this), "Stream Queue");
        this.treadQueue.start();
        this.threadKill = new Thread(new ThreadKillDataStream(this), "Stream Kill");
        this.threadKill.start();
    }

    public void addQueue(Player player, byte[] decoded_data, int id, boolean end)
    {
        EntityPlayerMP pl = (EntityPlayerMP)player;

        if (this.voiceChat.getServerSettings().isVoiceEnable() && !this.mutedPlayers.containsKey(pl.username) && this.canTalk(pl.username))
        {
            this.dataQueue.offer(new ServerDatalet(player, id, decoded_data, end));
            Thread var6 = this.treadQueue;

            synchronized (this.treadQueue)
            {
                this.treadQueue.notify();
            }
        }
    }

    private boolean canTalk(String playerName)
    {
        return NationsGUI.canPlayerTalk(playerName);
    }

    public boolean newDatalet(ServerDatalet let)
    {
        return this.streaming.get(Integer.valueOf(let.id)) == null;
    }

    public void createStream(ServerDatalet data)
    {
        this.addStreamSafe(new DataStream(data.player, data.id, this.generateSource(data)));
        this.giveEntity(data);
        this.giveStream(data);
    }

    private void giveEntity(ServerDatalet data)
    {
        Player[] players = this.voiceChat.getServerNetwork().getPlayers();
        EntityPlayerMP sender = (EntityPlayerMP)data.player;

        for (int i = 0; i < players.length; ++i)
        {
            Player player = players[i];

            if (sender != (EntityPlayerMP)player)
            {
                this.voiceChat.getVoiceServer().sendEntityData(player, sender.entityId, sender.getCommandSenderName(), sender.posX, sender.posY, sender.posZ, sender.motionX, sender.motionY, sender.motionZ);
            }
        }
    }

    private void addStreamSafe(DataStream stream)
    {
        if (this.streaming.get(Integer.valueOf(stream.id)) == null)
        {
            this.streaming.put(Integer.valueOf(stream.id), stream);
            Thread var2 = this.threadKill;
            Thread var3 = this.threadKill;

            synchronized (this.threadKill)
            {
                this.threadKill.notify();
            }
        }
    }

    public void giveStream(ServerDatalet let)
    {
        Player sender = let.player;

        if (sender != null)
        {
            this.sendVoiceToWorldWithinDistance(let, this.voiceChat.getServerSettings().getSoundDistance());
        }

        if (let.end)
        {
            this.killStream(let.id);
        }
    }

    public void sendVoiceToWorldWithinDistance(ServerDatalet let, int distance)
    {
        EntityPlayer curPlayer = (EntityPlayer)let.player;
        List players = curPlayer.worldObj.playerEntities;
        int tickRate = MinecraftServer.getServer().getTickCounter();
        int i;
        EntityPlayer player;
        double d4;
        double d5;
        double d6;

        if (let.end)
        {
            for (i = 0; i < players.size(); ++i)
            {
                player = (EntityPlayer)players.get(i);

                if (player != curPlayer)
                {
                    d4 = curPlayer.posX - player.posX;
                    d5 = curPlayer.posY - player.posY;
                    d6 = curPlayer.posZ - player.posZ;

                    if (d4 * d4 + d5 * d5 + d6 * d6 < (double)(distance * distance))
                    {
                        this.voiceChat.getVoiceServer().sendVoiceEnd((Player)player, let.id);
                    }
                }
            }
        }
        else
        {
            for (i = 0; i < players.size(); ++i)
            {
                player = (EntityPlayer)players.get(i);

                if (player != curPlayer && this.canHear(player, curPlayer))
                {
                    d4 = curPlayer.posX - player.posX;
                    d5 = curPlayer.posY - player.posY;
                    d6 = curPlayer.posZ - player.posZ;

                    if (d4 * d4 + d5 * d5 + d6 * d6 < (double)(distance * distance))
                    {
                        if (distance > 63)
                        {
                            if (!this.hasEntityData(let.id, player.entityId))
                            {
                                this.voiceChat.getVoiceServer().sendEntityData((Player)player, let.id, curPlayer.getCommandSenderName(), curPlayer.posX, curPlayer.posY, curPlayer.posZ, curPlayer.motionX, curPlayer.motionY, curPlayer.motionZ);
                                this.addReceivedEntityData(let.id, player.entityId);
                            }

                            if (tickRate % 7 == 0)
                            {
                                this.voiceChat.getVoiceServer().sendEntityPosition((Player)player, let.id, curPlayer.posX, curPlayer.posY, curPlayer.posZ, curPlayer.motionX, curPlayer.motionY, curPlayer.motionZ);
                            }
                        }

                        this.voiceChat.getVoiceServer().sendVoiceData((Player)player, let.id, 1, let.data);
                    }
                }
            }
        }
    }

    private boolean canHear(EntityPlayer player, EntityPlayer playerSpeaking)
    {
        return NationsGUI.canPlayerHear(player.username, playerSpeaking.username);
    }

    private String generateSource(ServerDatalet let)
    {
        return Integer.toString(let.id);
    }

    public void killStream(int id)
    {
        this.streaming.remove(Integer.valueOf(id));
    }

    private void addReceivedEntityData(int id, int entityId)
    {
        List list = (List)this.receivedEntityData.get(Integer.valueOf(id));

        if (list != null)
        {
            list.add(Integer.valueOf(entityId));
        }
        else
        {
            ArrayList list1 = new ArrayList();
            list1.add(Integer.valueOf(entityId));
            this.receivedEntityData.put(Integer.valueOf(id), list1);
        }
    }

    public boolean hasEntityData(int entityID, int hasEntityID)
    {
        return this.receivedEntityData.containsKey(Integer.valueOf(entityID)) ? ((List)this.receivedEntityData.get(Integer.valueOf(entityID))).contains(Integer.valueOf(hasEntityID)) : false;
    }

    public void playerDisconnect(EntityPlayer player)
    {
        List list = (List)this.receivedEntityData.get(Integer.valueOf(player.entityId));

        if (list != null)
        {
            this.receivedEntityData.remove(Integer.valueOf(player.entityId));
        }

        Iterator it = this.receivedEntityData.entrySet().iterator();

        while (it.hasNext())
        {
            Entry pairs = (Entry)it.next();
            List idList = (List)this.receivedEntityData.get(pairs.getKey());

            for (int i = 0; i < idList.size(); ++i)
            {
                if (((Integer)idList.get(i)).intValue() == player.entityId)
                {
                    idList.remove(i);
                }
            }
        }
    }

    public void tickStart(EnumSet<TickType> type, Object ... tickData) {}

    public void tickEnd(EnumSet<TickType> type, Object ... tickData)
    {
        if (type.equals(EnumSet.of(TickType.SERVER)))
        {
            if (this.tick >= 100)
            {
                if (this.mutedPlayers != null && !this.mutedPlayers.isEmpty())
                {
                    HashMap newMap = new HashMap();
                    Iterator var4 = this.mutedPlayers.entrySet().iterator();

                    while (var4.hasNext())
                    {
                        Entry e = (Entry)var4.next();

                        if (((Long)e.getValue()).longValue() > System.currentTimeMillis())
                        {
                            newMap.put(e.getKey(), e.getValue());
                        }
                    }

                    this.mutedPlayers = newMap;
                }

                this.tick = 0;
            }

            ++this.tick;
        }
    }

    public EnumSet<TickType> ticks()
    {
        return EnumSet.of(TickType.SERVER);
    }

    public String getLabel()
    {
        return "DataManagerUnmuter";
    }
}
