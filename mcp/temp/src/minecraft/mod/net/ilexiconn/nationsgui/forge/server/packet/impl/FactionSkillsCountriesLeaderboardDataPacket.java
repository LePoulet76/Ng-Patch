package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionSkillsGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionSkillsCountriesLeaderboardDataPacket$1;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionSkillsCountriesLeaderboardDataPacket$2;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionSkillsCountriesLeaderboardDataPacket$3;
import net.minecraft.entity.player.EntityPlayer;

public class FactionSkillsCountriesLeaderboardDataPacket implements IPacket, IClientPacket {

   public HashMap<String, List<String>> countriesLeaderboard = new HashMap();
   public HashMap<String, Double> countryPositionPerSkill = new HashMap();
   public HashMap<String, Double> countryTotalLevelPerSkill = new HashMap();
   private String target;


   public FactionSkillsCountriesLeaderboardDataPacket(String target) {
      this.target = target;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.countriesLeaderboard = (HashMap)(new Gson()).fromJson(data.readUTF(), (new FactionSkillsCountriesLeaderboardDataPacket$1(this)).getType());
      this.countryPositionPerSkill = (HashMap)(new Gson()).fromJson(data.readUTF(), (new FactionSkillsCountriesLeaderboardDataPacket$2(this)).getType());
      this.countryTotalLevelPerSkill = (HashMap)(new Gson()).fromJson(data.readUTF(), (new FactionSkillsCountriesLeaderboardDataPacket$3(this)).getType());
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.target);
   }

   public void handleClientPacket(EntityPlayer player) {
      FactionSkillsGUI.countriesLeaderboard = this.countriesLeaderboard;
      FactionSkillsGUI.countryPositionPerSkill = this.countryPositionPerSkill;
      FactionSkillsGUI.countryTotalLevelPerSkill = this.countryTotalLevelPerSkill;
      FactionSkillsGUI.loaded_countries_leaderboard = true;
   }
}
