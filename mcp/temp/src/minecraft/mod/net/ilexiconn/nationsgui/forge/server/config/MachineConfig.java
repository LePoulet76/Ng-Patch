package net.ilexiconn.nationsgui.forge.server.config;

import java.util.ArrayList;
import java.util.List;
import net.minecraftforge.common.Configuration;

public class MachineConfig {

   public int incubatorID = 3333;
   public int incubatorCoalRate = 4;
   public int incubatorSpawnRate = 12;
   public int repairMachineID = 3334;
   public int repairMachineCoalRate = 2;
   public int repairMachineRepairRate = 2;
   public List<Integer> repairMachineBlacklist = new ArrayList();
   public List<Integer> spawnerBlacklist = new ArrayList();


   public void loadConfig(Configuration config) {
      config.load();
      this.incubatorID = config.get("incubator", "Block ID", 3333).getInt();
      this.incubatorCoalRate = config.get("incubator", "Coal Rate", 4, "Coal block rate per second").getInt();
      this.incubatorSpawnRate = config.get("incubator", "Spawn Rate", 12, "The amount of entity spawn per minute").getInt();
      this.repairMachineID = config.get("repair machine", "Block ID", 3334).getInt();
      this.repairMachineCoalRate = config.get("repair machine", "Coal Rate", 2, "Coal block rate per second").getInt();
      this.repairMachineRepairRate = config.get("repair machine", "Repair Rate", 2, "Repair rate per second").getInt();
      String[] blacklist = config.get("repair machine", "Blacklist", new String[0], "List of blacklisted item IDs").getStringList();
      String[] blacklist2 = blacklist;
      int var4 = blacklist.length;

      int var5;
      for(var5 = 0; var5 < var4; ++var5) {
         String id = blacklist2[var5];
         System.out.println("Added item ID " + id + " to blacklist");
         this.repairMachineBlacklist.add(Integer.valueOf(Integer.parseInt(id)));
      }

      int[] var8 = config.get("spawner", "Blacklist", new int[0], "List of blacklisted entity IDs").getIntList();
      int[] var9 = var8;
      var5 = var8.length;

      for(int var10 = 0; var10 < var5; ++var10) {
         int id1 = var9[var10];
         this.spawnerBlacklist.add(Integer.valueOf(id1));
      }

      config.save();
   }
}
