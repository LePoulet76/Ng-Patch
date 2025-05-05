package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import fr.nationsglory.nationsmap.NationsMap;
import fr.nationsglory.nationsmap.map.Marker;
import fr.nationsglory.nationsmap.overlay.OverlayZone;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.RefreshZonesMapPacket$1;
import net.ilexiconn.nationsgui.forge.server.packet.impl.RefreshZonesMapPacket$2;
import net.minecraft.entity.player.EntityPlayer;

public class RefreshZonesMapPacket implements IPacket, IClientPacket {

   public HashMap<String, String> zoneLocations;
   public ArrayList<String> zoneChunks;


   public void fromBytes(ByteArrayDataInput data) {
      this.zoneLocations = (HashMap)(new Gson()).fromJson(data.readUTF(), (new RefreshZonesMapPacket$1(this)).getType());
      this.zoneChunks = (ArrayList)(new Gson()).fromJson(data.readUTF(), (new RefreshZonesMapPacket$2(this)).getType());
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF((new Gson()).toJson(this.zoneLocations));
      data.writeUTF((new Gson()).toJson(this.zoneChunks));
   }

   public void handleClientPacket(EntityPlayer player) {
      System.out.println("handleClientPacket refreshzone");
      ArrayList markersToDelete = new ArrayList();
      Iterator it = NationsMap.instance.markerManager.markerList.iterator();

      Marker pair;
      while(it.hasNext()) {
         pair = (Marker)it.next();
         if(pair.groupName.equalsIgnoreCase("ressources")) {
            markersToDelete.add(pair);
         }
      }

      it = markersToDelete.iterator();

      while(it.hasNext()) {
         pair = (Marker)it.next();
         NationsMap.instance.markerManager.delMarker(pair);
      }

      if(!NationsMap.instance.markerManager.groupList.contains("Ressources")) {
         NationsMap.instance.markerManager.groupList.add("Ressources");
      }

      it = this.zoneLocations.entrySet().iterator();

      while(it.hasNext()) {
         Entry pair1 = (Entry)it.next();
         String zoneName = (String)pair1.getValue();
         String zoneLocation = (String)pair1.getKey();
         NationsMap.instance.markerManager.addMarker(zoneName, "Ressources", Integer.parseInt(zoneLocation.split("#")[0]), 64, Integer.parseInt(zoneLocation.split("#")[1]), 0, -16711936);
         it.remove();
      }

      OverlayZone.zoneChunks = this.zoneChunks;
   }
}
