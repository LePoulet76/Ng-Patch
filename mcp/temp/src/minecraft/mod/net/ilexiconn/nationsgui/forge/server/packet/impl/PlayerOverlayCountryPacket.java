package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.PlayerOverlayCountryPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class PlayerOverlayCountryPacket implements IPacket, IClientPacket {

   public HashMap<String, String> dataCountry = new HashMap();


   public void fromBytes(ByteArrayDataInput data) {
      this.dataCountry = (HashMap)(new Gson()).fromJson(data.readUTF(), (new PlayerOverlayCountryPacket$1(this)).getType());
   }

   public void toBytes(ByteArrayDataOutput data) {}

   public void handleClientPacket(EntityPlayer player) {
      if(!this.dataCountry.isEmpty()) {
         ClientData.countryTitleInfos = this.dataCountry;
      } else {
         ClientData.countryTitleInfos = new HashMap();
      }

   }
}
