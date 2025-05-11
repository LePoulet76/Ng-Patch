/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  cpw.mods.fml.common.ITickHandler
 *  cpw.mods.fml.common.TickType
 *  cpw.mods.fml.common.network.Player
 *  cpw.mods.fml.common.registry.TickRegistry
 *  cpw.mods.fml.relauncher.Side
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.EntityPlayerMP
 *  net.minecraft.server.MinecraftServer
 */
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
import java.io.Reader;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.server.voices.VoiceChatServer;
import net.ilexiconn.nationsgui.forge.server.voices.VoiceConfig;
import net.ilexiconn.nationsgui.forge.server.voices.networking.DataStream;
import net.ilexiconn.nationsgui.forge.server.voices.networking.ServerDatalet;
import net.ilexiconn.nationsgui.forge.server.voices.networking.ThreadDataQueue;
import net.ilexiconn.nationsgui.forge.server.voices.networking.ThreadKillDataStream;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class DataManager
implements ITickHandler {
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

    public DataManager(VoiceChatServer voiceChat) {
        this.voiceChat = voiceChat;
        TickRegistry.registerTickHandler((ITickHandler)this, (Side)Side.SERVER);
    }

    public void save() {
        VoiceConfig config = new VoiceConfig();
        config.setMutedPlayers(this.mutedPlayers);
        try {
            FileWriter fileWriter = new FileWriter(this.voiceConfigFile);
            this.gson.toJson((Object)config, VoiceConfig.class, (Appendable)fileWriter);
            fileWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void init() {
        this.mutedPlayers = new HashMap<String, Long>();
        if (this.voiceConfigFile.exists()) {
            try {
                VoiceConfig voiceConfig = (VoiceConfig)this.gson.fromJson((Reader)new FileReader(this.voiceConfigFile), VoiceConfig.class);
                this.mutedPlayers = voiceConfig.getMutedPlayers();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.dataQueue = new ConcurrentLinkedQueue();
        this.streaming = new ConcurrentHashMap();
        this.receivedEntityData = new HashMap();
        this.treadQueue = new Thread((Runnable)new ThreadDataQueue(this), "Stream Queue");
        this.treadQueue.start();
        this.threadKill = new Thread((Runnable)new ThreadKillDataStream(this), "Stream Kill");
        this.threadKill.start();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void addQueue(Player player, byte[] decoded_data, int id, boolean end) {
        EntityPlayerMP pl = (EntityPlayerMP)player;
        if (this.voiceChat.getServerSettings().isVoiceEnable() && !this.mutedPlayers.containsKey(pl.field_71092_bJ) && this.canTalk(pl.field_71092_bJ)) {
            this.dataQueue.offer(new ServerDatalet(player, id, decoded_data, end));
            Thread thread = this.treadQueue;
            synchronized (thread) {
                this.treadQueue.notify();
            }
        }
    }

    private boolean canTalk(String playerName) {
        return NationsGUI.canPlayerTalk(playerName);
    }

    public boolean newDatalet(ServerDatalet let) {
        return this.streaming.get(let.id) == null;
    }

    public void createStream(ServerDatalet data) {
        this.addStreamSafe(new DataStream(data.player, data.id, this.generateSource(data)));
        this.giveEntity(data);
        this.giveStream(data);
    }

    private void giveEntity(ServerDatalet data) {
        Player[] players = this.voiceChat.getServerNetwork().getPlayers();
        EntityPlayerMP sender = (EntityPlayerMP)data.player;
        for (int i = 0; i < players.length; ++i) {
            Player player = players[i];
            if (sender == (EntityPlayerMP)player) continue;
            this.voiceChat.getVoiceServer().sendEntityData(player, sender.field_70157_k, sender.func_70005_c_(), sender.field_70165_t, sender.field_70163_u, sender.field_70161_v, sender.field_70159_w, sender.field_70181_x, sender.field_70179_y);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void addStreamSafe(DataStream stream) {
        if (this.streaming.get(stream.id) == null) {
            this.streaming.put(stream.id, stream);
            Thread var2 = this.threadKill;
            Thread thread = this.threadKill;
            synchronized (thread) {
                this.threadKill.notify();
            }
        }
    }

    public void giveStream(ServerDatalet let) {
        Player sender = let.player;
        if (sender != null) {
            this.sendVoiceToWorldWithinDistance(let, this.voiceChat.getServerSettings().getSoundDistance());
        }
        if (let.end) {
            this.killStream(let.id);
        }
    }

    public void sendVoiceToWorldWithinDistance(ServerDatalet let, int distance) {
        EntityPlayer curPlayer = (EntityPlayer)let.player;
        List players = curPlayer.field_70170_p.field_73010_i;
        int tickRate = MinecraftServer.func_71276_C().func_71259_af();
        if (let.end) {
            for (int i = 0; i < players.size(); ++i) {
                double d6;
                double d5;
                double d4;
                EntityPlayer player = (EntityPlayer)players.get(i);
                if (player == curPlayer || !((d4 = curPlayer.field_70165_t - player.field_70165_t) * d4 + (d5 = curPlayer.field_70163_u - player.field_70163_u) * d5 + (d6 = curPlayer.field_70161_v - player.field_70161_v) * d6 < (double)(distance * distance))) continue;
                this.voiceChat.getVoiceServer().sendVoiceEnd((Player)player, let.id);
            }
        } else {
            for (int i = 0; i < players.size(); ++i) {
                double d6;
                double d5;
                double d4;
                EntityPlayer player = (EntityPlayer)players.get(i);
                if (player == curPlayer || !this.canHear(player, curPlayer) || !((d4 = curPlayer.field_70165_t - player.field_70165_t) * d4 + (d5 = curPlayer.field_70163_u - player.field_70163_u) * d5 + (d6 = curPlayer.field_70161_v - player.field_70161_v) * d6 < (double)(distance * distance))) continue;
                if (distance > 63) {
                    if (!this.hasEntityData(let.id, player.field_70157_k)) {
                        this.voiceChat.getVoiceServer().sendEntityData((Player)player, let.id, curPlayer.func_70005_c_(), curPlayer.field_70165_t, curPlayer.field_70163_u, curPlayer.field_70161_v, curPlayer.field_70159_w, curPlayer.field_70181_x, curPlayer.field_70179_y);
                        this.addReceivedEntityData(let.id, player.field_70157_k);
                    }
                    if (tickRate % 7 == 0) {
                        this.voiceChat.getVoiceServer().sendEntityPosition((Player)player, let.id, curPlayer.field_70165_t, curPlayer.field_70163_u, curPlayer.field_70161_v, curPlayer.field_70159_w, curPlayer.field_70181_x, curPlayer.field_70179_y);
                    }
                }
                this.voiceChat.getVoiceServer().sendVoiceData((Player)player, let.id, 1, let.data);
            }
        }
    }

    private boolean canHear(EntityPlayer player, EntityPlayer playerSpeaking) {
        return NationsGUI.canPlayerHear(player.field_71092_bJ, playerSpeaking.field_71092_bJ);
    }

    private String generateSource(ServerDatalet let) {
        return Integer.toString(let.id);
    }

    public void killStream(int id) {
        this.streaming.remove(id);
    }

    private void addReceivedEntityData(int id, int entityId) {
        List list = (List)this.receivedEntityData.get(id);
        if (list != null) {
            list.add(entityId);
        } else {
            ArrayList<Integer> list1 = new ArrayList<Integer>();
            list1.add(entityId);
            this.receivedEntityData.put(id, list1);
        }
    }

    public boolean hasEntityData(int entityID, int hasEntityID) {
        return this.receivedEntityData.containsKey(entityID) ? ((List)this.receivedEntityData.get(entityID)).contains(hasEntityID) : false;
    }

    public void playerDisconnect(EntityPlayer player) {
        List list = (List)this.receivedEntityData.get(player.field_70157_k);
        if (list != null) {
            this.receivedEntityData.remove(player.field_70157_k);
        }
        for (Map.Entry pairs : this.receivedEntityData.entrySet()) {
            List idList = (List)this.receivedEntityData.get(pairs.getKey());
            for (int i = 0; i < idList.size(); ++i) {
                if ((Integer)idList.get(i) != player.field_70157_k) continue;
                idList.remove(i);
            }
        }
    }

    public void tickStart(EnumSet<TickType> type, Object ... tickData) {
    }

    public void tickEnd(EnumSet<TickType> type, Object ... tickData) {
        if (type.equals(EnumSet.of(TickType.SERVER))) {
            if (this.tick >= 100) {
                if (this.mutedPlayers != null && !this.mutedPlayers.isEmpty()) {
                    HashMap<String, Long> newMap = new HashMap<String, Long>();
                    for (Map.Entry<String, Long> e : this.mutedPlayers.entrySet()) {
                        if (e.getValue() <= System.currentTimeMillis()) continue;
                        newMap.put(e.getKey(), e.getValue());
                    }
                    this.mutedPlayers = newMap;
                }
                this.tick = 0;
            }
            ++this.tick;
        }
    }

    public EnumSet<TickType> ticks() {
        return EnumSet.of(TickType.SERVER);
    }

    public String getLabel() {
        return "DataManagerUnmuter";
    }
}

