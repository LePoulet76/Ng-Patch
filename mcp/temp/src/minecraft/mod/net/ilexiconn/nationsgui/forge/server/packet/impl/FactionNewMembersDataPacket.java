package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import net.ilexiconn.nationsgui.forge.client.gui.faction.MembersGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionNewMembersDataPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class FactionNewMembersDataPacket implements IPacket, IClientPacket {

   public LinkedHashMap<String, Object> data = new LinkedHashMap();
   public String targetFactionId;


   public FactionNewMembersDataPacket(String targetName) {
      MembersGUI.factionNewMembersInfos.clear();
      this.targetFactionId = targetName;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.data = (LinkedHashMap)(new Gson()).fromJson(data.readUTF(), (new FactionNewMembersDataPacket$1(this)).getType());
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.targetFactionId);
   }

   public void handleClientPacket(EntityPlayer player) {
      Iterator var2 = this.data.entrySet().iterator();

      while(var2.hasNext()) {
         Entry entry = (Entry)var2.next();
         MembersGUI.factionNewMembersInfos.put(entry.getKey(), entry.getValue());
      }

      MembersGUI.loaded_new_player = true;
   }
}
