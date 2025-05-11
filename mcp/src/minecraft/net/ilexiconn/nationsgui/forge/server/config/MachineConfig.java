/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.common.Configuration
 */
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
    public List<Integer> repairMachineBlacklist = new ArrayList<Integer>();
    public List<Integer> spawnerBlacklist = new ArrayList<Integer>();

    public void loadConfig(Configuration config) {
        int[] blacklist2;
        String[] blacklist;
        config.load();
        this.incubatorID = config.get("incubator", "Block ID", 3333).getInt();
        this.incubatorCoalRate = config.get("incubator", "Coal Rate", 4, "Coal block rate per second").getInt();
        this.incubatorSpawnRate = config.get("incubator", "Spawn Rate", 12, "The amount of entity spawn per minute").getInt();
        this.repairMachineID = config.get("repair machine", "Block ID", 3334).getInt();
        this.repairMachineCoalRate = config.get("repair machine", "Coal Rate", 2, "Coal block rate per second").getInt();
        this.repairMachineRepairRate = config.get("repair machine", "Repair Rate", 2, "Repair rate per second").getInt();
        for (String id : blacklist = config.get("repair machine", "Blacklist", new String[0], "List of blacklisted item IDs").getStringList()) {
            System.out.println("Added item ID " + id + " to blacklist");
            this.repairMachineBlacklist.add(Integer.parseInt(id));
        }
        for (int id : blacklist2 = config.get("spawner", "Blacklist", new int[0], "List of blacklisted entity IDs").getIntList()) {
            this.spawnerBlacklist.add(id);
        }
        config.save();
    }
}

