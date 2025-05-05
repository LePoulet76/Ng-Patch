package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.PlayerOverlayCombatArmorPacket$1;
import net.ilexiconn.nationsgui.forge.server.packet.impl.PlayerOverlayCombatArmorPacket$2;
import net.minecraft.entity.player.EntityPlayer;

public class PlayerOverlayCombatArmorPacket implements IPacket, IClientPacket {

   public ArrayList<String> itemsData = new ArrayList();
   public HashMap<String, String> data;


   public void fromBytes(ByteArrayDataInput data) {
      this.itemsData = (ArrayList)(new Gson()).fromJson(data.readUTF(), (new PlayerOverlayCombatArmorPacket$1(this)).getType());
      this.data = (HashMap)(new Gson()).fromJson(data.readUTF(), (new PlayerOverlayCombatArmorPacket$2(this)).getType());
   }

   public void toBytes(ByteArrayDataOutput data) {}

   public void handleClientPacket(EntityPlayer player) {
      if(!this.itemsData.isEmpty()) {
         ClientData.playerCombatArmorDurability = this.itemsData;
         ClientData.playerCombatArmorInfos = this.data;
      } else {
         ClientData.playerCombatArmorDurability = new ArrayList();
         ClientData.playerCombatArmorInfos = new HashMap();
      }

   }
}
