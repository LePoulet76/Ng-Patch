package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.GetGroupAndPrimeOthersPacket$1;
import net.ilexiconn.nationsgui.forge.server.packet.impl.GetGroupAndPrimeOthersPacket$2;
import net.ilexiconn.nationsgui.forge.server.packet.impl.GetGroupAndPrimePacket;
import net.minecraft.entity.player.EntityPlayer;

public class GetGroupAndPrimeOthersPacket implements IPacket, IClientPacket {

   public HashMap<String, String> badges = new HashMap();
   public List<String> ngprimePlayers = new ArrayList();


   public void fromBytes(ByteArrayDataInput data) {
      this.badges = (HashMap)(new Gson()).fromJson(data.readUTF(), (new GetGroupAndPrimeOthersPacket$1(this)).getType());
      this.ngprimePlayers = (List)(new Gson()).fromJson(data.readUTF(), (new GetGroupAndPrimeOthersPacket$2(this)).getType());
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF((new Gson()).toJson(this.badges));
      data.writeUTF((new Gson()).toJson(this.ngprimePlayers));
   }

   public void handleClientPacket(EntityPlayer player) {
      for(Iterator it = this.badges.entrySet().iterator(); it.hasNext(); it.remove()) {
         Entry pair = (Entry)it.next();
         String playerName = (String)pair.getKey();
         String badgeName = (String)pair.getValue();
         if(!badgeName.equals("none") && NationsGUI.BADGES_RESOURCES.containsKey(badgeName.toLowerCase())) {
            GetGroupAndPrimePacket.GRP_BADGES_PLAYERS.put(playerName, badgeName.toLowerCase());
         } else {
            GetGroupAndPrimePacket.GRP_BADGES_PLAYERS.remove(playerName);
         }
      }

      GetGroupAndPrimePacket.NGPRIME_PLAYERS = this.ngprimePlayers;
   }
}
