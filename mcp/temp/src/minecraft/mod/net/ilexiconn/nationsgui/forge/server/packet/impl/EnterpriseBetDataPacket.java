package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseBetGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseBetDataPacket$1;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseBetDataPacket$2;
import net.minecraft.entity.player.EntityPlayer;

public class EnterpriseBetDataPacket implements IPacket, IClientPacket {

   public ArrayList<HashMap<String, Object>> bets = new ArrayList();
   public HashMap<String, String> betInfos = new HashMap();
   public String target;


   public EnterpriseBetDataPacket(String targetName) {
      this.target = targetName;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.betInfos = (HashMap)(new Gson()).fromJson(data.readUTF(), (new EnterpriseBetDataPacket$1(this)).getType());
      this.bets = (ArrayList)(new Gson()).fromJson(data.readUTF(), (new EnterpriseBetDataPacket$2(this)).getType());
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.target);
   }

   public void handleClientPacket(EntityPlayer player) {
      EnterpriseBetGUI.betsInfos = this.betInfos;
      EnterpriseBetGUI.bets = this.bets;
      EnterpriseBetGUI.loaded = true;
   }
}
