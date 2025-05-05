package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.gui.faction.SettingsGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionSettingsDataPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class FactionSettingsDataPacket implements IPacket, IClientPacket {

   public HashMap<String, Object> settingsData = new HashMap();
   public String target;


   public FactionSettingsDataPacket(String targetName) {
      this.target = targetName;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.settingsData = (HashMap)(new Gson()).fromJson(data.readUTF(), (new FactionSettingsDataPacket$1(this)).getType());
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.target);
   }

   public void handleClientPacket(EntityPlayer player) {
      SettingsGUI.settingsData = this.settingsData;
      SettingsGUI.loaded = true;
      SettingsGUI.data_loaded = false;
      System.out.println(this.settingsData.toString());
   }
}
