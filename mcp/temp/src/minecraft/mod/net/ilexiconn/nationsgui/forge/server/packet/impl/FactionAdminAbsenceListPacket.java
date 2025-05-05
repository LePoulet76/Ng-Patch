package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.gui.faction.AdminAbsenceRequestListGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionAdminAbsenceListPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class FactionAdminAbsenceListPacket implements IPacket, IClientPacket {

   public ArrayList<HashMap<String, String>> absenceInfos = new ArrayList();


   public void fromBytes(ByteArrayDataInput data) {
      this.absenceInfos = (ArrayList)(new Gson()).fromJson(data.readUTF(), (new FactionAdminAbsenceListPacket$1(this)).getType());
   }

   public void toBytes(ByteArrayDataOutput data) {}

   public void handleClientPacket(EntityPlayer player) {
      AdminAbsenceRequestListGUI.absenceInfos.addAll(this.absenceInfos);
      AdminAbsenceRequestListGUI.loaded = true;
   }
}
