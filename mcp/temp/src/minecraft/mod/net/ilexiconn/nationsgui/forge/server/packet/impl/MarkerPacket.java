package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class MarkerPacket implements IPacket, IClientPacket {

   private Map<String, Object> markerData;


   public MarkerPacket(Map<String, Object> markerData) {
      this.markerData = markerData;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.markerData = (Map)(new Gson()).fromJson(data.readUTF(), Map.class);
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF((new Gson()).toJson(this.markerData));
   }

   public void handleClientPacket(EntityPlayer player) {
      ArrayList newMarkersList = new ArrayList();
      boolean newMarkerAdded = false;
      Iterator var4 = ClientData.markers.iterator();

      while(var4.hasNext()) {
         Map marker = (Map)var4.next();
         if(marker.get("name").equals(this.markerData.get("name"))) {
            newMarkersList.add(this.markerData);
            newMarkerAdded = true;
         } else {
            newMarkersList.add(marker);
         }
      }

      if(!newMarkerAdded) {
         newMarkersList.add(this.markerData);
      }

      ClientData.markers = newMarkersList;
   }
}
