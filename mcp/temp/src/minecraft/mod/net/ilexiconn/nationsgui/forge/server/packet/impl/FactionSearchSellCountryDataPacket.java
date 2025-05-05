package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.gui.faction.SearchSellCountryGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionSearchSellCountryDataPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class FactionSearchSellCountryDataPacket implements IPacket, IClientPacket {

   public ArrayList<HashMap<String, String>> countries = new ArrayList();


   public void fromBytes(ByteArrayDataInput data) {
      this.countries = (ArrayList)(new Gson()).fromJson(data.readUTF(), (new FactionSearchSellCountryDataPacket$1(this)).getType());
   }

   public void toBytes(ByteArrayDataOutput data) {}

   public void handleClientPacket(EntityPlayer player) {
      SearchSellCountryGui.countries = this.countries;
      SearchSellCountryGui.loaded = true;
   }
}
