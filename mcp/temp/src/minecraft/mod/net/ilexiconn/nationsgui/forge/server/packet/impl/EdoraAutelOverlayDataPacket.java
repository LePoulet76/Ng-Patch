package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EdoraAutelOverlayDataPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class EdoraAutelOverlayDataPacket implements IPacket, IClientPacket {

   public static long lastPacketReceived = 0L;
   public HashMap<String, Object> data;


   public void fromBytes(ByteArrayDataInput data) {
      this.data = (HashMap)(new Gson()).fromJson(data.readUTF(), (new EdoraAutelOverlayDataPacket$1(this)).getType());
   }

   public void toBytes(ByteArrayDataOutput data) {}

   public void handleClientPacket(EntityPlayer player) {
      if(!this.data.isEmpty()) {
         ClientData.autelOverlayData = this.data;
         lastPacketReceived = System.currentTimeMillis();
      } else {
         ClientData.autelOverlayData = new HashMap();
      }

   }

}
