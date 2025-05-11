/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.Player
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayerMP
 *  net.minecraft.server.MinecraftServer
 */
package net.ilexiconn.nationsgui.forge.server.voices.networking;

import cpw.mods.fml.common.network.Player;
import java.util.List;
import net.ilexiconn.nationsgui.forge.server.voices.VoiceChatServer;
import net.ilexiconn.nationsgui.forge.server.voices.networking.DataManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class ServerNetwork {
    VoiceChatServer voiceChat;
    protected final DataManager dataManager;

    public ServerNetwork(VoiceChatServer voiceChat) {
        this.voiceChat = voiceChat;
        this.dataManager = new DataManager(voiceChat);
    }

    public void init() {
        this.dataManager.init();
    }

    public String[] getPlayerIPs() {
        List players = MinecraftServer.func_71276_C().func_71203_ab().field_72404_b;
        String[] ips = new String[players.size()];
        for (int i = 0; i < players.size(); ++i) {
            EntityPlayerMP p = (EntityPlayerMP)players.get(i);
            ips[i] = p.func_71114_r();
        }
        return ips;
    }

    public Player[] getPlayers() {
        List pl = MinecraftServer.func_71276_C().func_71203_ab().field_72404_b;
        Player[] players = pl.toArray(new Player[pl.size()]);
        return players;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Entity getEntityByID(int id) {
        MinecraftServer mc;
        MinecraftServer minecraftServer = mc = MinecraftServer.func_71276_C();
        synchronized (minecraftServer) {
            List players = mc.func_71203_ab().field_72404_b;
            for (int i = 0; i < players.size(); ++i) {
                Entity entity = (Entity)players.get(i);
                if (entity.field_70157_k != id) continue;
                return entity;
            }
            return null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean isAddressPlaying(String ip) {
        MinecraftServer mc;
        MinecraftServer minecraftServer = mc = MinecraftServer.func_71276_C();
        synchronized (minecraftServer) {
            List players = mc.func_71203_ab().field_72404_b;
            for (int i = 0; i < players.size(); ++i) {
                String address = ((EntityPlayerMP)players.get(i)).func_71114_r();
                if (!ip.equals(address)) continue;
                return true;
            }
            return false;
        }
    }

    public DataManager getDataManager() {
        return this.dataManager;
    }
}

