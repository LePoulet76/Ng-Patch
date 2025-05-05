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

      for(int i = 0; i < players.size(); ++i) {
         EntityPlayerMP p = (EntityPlayerMP)players.get(i);
         ips[i] = p.func_71114_r();
      }

      return ips;
   }

   public Player[] getPlayers() {
      List pl = MinecraftServer.func_71276_C().func_71203_ab().field_72404_b;
      Player[] players = (Player[])((Player[])pl.toArray(new Player[pl.size()]));
      return players;
   }

   public Entity getEntityByID(int id) {
      MinecraftServer mc = MinecraftServer.func_71276_C();
      synchronized(mc) {
         List players = mc.func_71203_ab().field_72404_b;

         for(int i = 0; i < players.size(); ++i) {
            Entity entity = (Entity)players.get(i);
            if(entity.field_70157_k == id) {
               return entity;
            }
         }

         return null;
      }
   }

   public boolean isAddressPlaying(String ip) {
      MinecraftServer mc = MinecraftServer.func_71276_C();
      synchronized(mc) {
         List players = mc.func_71203_ab().field_72404_b;

         for(int i = 0; i < players.size(); ++i) {
            String address = ((EntityPlayerMP)players.get(i)).func_71114_r();
            if(ip.equals(address)) {
               return true;
            }
         }

         return false;
      }
   }

   public DataManager getDataManager() {
      return this.dataManager;
   }
}
