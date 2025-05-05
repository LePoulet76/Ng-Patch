package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.TreeMap;
import net.ilexiconn.nationsgui.forge.client.gui.faction.WarGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionWarDataPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class FactionWarDataPacket implements IPacket, IClientPacket {

   public ArrayList<TreeMap<String, Object>> factionWars = new ArrayList();
   public String target;


   public FactionWarDataPacket(String targetName) {
      this.target = targetName;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.factionWars = (ArrayList)(new Gson()).fromJson(data.readUTF(), (new FactionWarDataPacket$1(this)).getType());
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.target);
   }

   public void handleClientPacket(EntityPlayer player) {
      WarGUI.factionsWarInfos.addAll(this.factionWars);
      WarGUI.loaded = true;
   }
}
