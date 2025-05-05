package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.gui.faction.StatsGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.PlayerStatsDataPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class PlayerStatsDataPacket implements IPacket, IClientPacket {

   public HashMap<String, String> stats = new HashMap();
   private String playerName;


   public PlayerStatsDataPacket(String playerName) {
      this.playerName = playerName;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.stats = (HashMap)(new Gson()).fromJson(data.readUTF(), (new PlayerStatsDataPacket$1(this)).getType());
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.playerName);
   }

   public void handleClientPacket(EntityPlayer player) {
      if(this.stats != null) {
         StatsGUI.loaded = true;
         StatsGUI.statsInfos = this.stats;
      }

   }
}
