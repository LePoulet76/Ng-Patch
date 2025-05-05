package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import fr.nationsglory.client.gui.OGMMachineGUI;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.OGMRecordsInfosPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class OGMRecordsInfosPacket implements IPacket, IClientPacket {

   public HashMap<String, Integer> ogmRecords = new HashMap();


   public void fromBytes(ByteArrayDataInput data) {
      this.ogmRecords = (HashMap)(new Gson()).fromJson(data.readUTF(), (new OGMRecordsInfosPacket$1(this)).getType());
   }

   public void toBytes(ByteArrayDataOutput data) {}

   public void handleClientPacket(EntityPlayer player) {
      OGMMachineGUI.ogmRecords = this.ogmRecords;
   }
}
