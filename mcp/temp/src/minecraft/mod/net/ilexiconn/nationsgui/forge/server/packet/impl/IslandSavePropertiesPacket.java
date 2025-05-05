package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class IslandSavePropertiesPacket implements IPacket, IClientPacket {

   public String id;
   public HashMap<String, Boolean> editedFlags;
   public String selectedTime;
   public String selectedWeather;
   public String selectedBiome;


   public IslandSavePropertiesPacket(String id, HashMap<String, Boolean> editedFlags, String selectedTime, String selectedWeather, String selectedBiome) {
      this.id = id;
      this.editedFlags = editedFlags;
      this.selectedTime = selectedTime;
      this.selectedWeather = selectedWeather;
      this.selectedBiome = selectedBiome;
   }

   public void fromBytes(ByteArrayDataInput data) {}

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.id);
      data.writeUTF((new Gson()).toJson(this.editedFlags));
      data.writeUTF(this.selectedTime);
      data.writeUTF(this.selectedWeather);
      data.writeUTF(this.selectedBiome);
   }

   public void handleClientPacket(EntityPlayer player) {}
}
