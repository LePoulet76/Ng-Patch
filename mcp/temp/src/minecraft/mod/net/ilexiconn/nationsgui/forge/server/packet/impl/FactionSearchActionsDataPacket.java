package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.gui.faction.SearchActionsGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionSearchActionsDataPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class FactionSearchActionsDataPacket implements IPacket, IClientPacket {

   public ArrayList<HashMap<String, String>> countries = new ArrayList();


   public void fromBytes(ByteArrayDataInput data) {
      this.countries = (ArrayList)(new Gson()).fromJson(data.readUTF(), (new FactionSearchActionsDataPacket$1(this)).getType());
   }

   public void toBytes(ByteArrayDataOutput data) {}

   public void handleClientPacket(EntityPlayer player) {
      SearchActionsGui.countries = this.countries;
      SearchActionsGui.loaded = true;
   }
}
