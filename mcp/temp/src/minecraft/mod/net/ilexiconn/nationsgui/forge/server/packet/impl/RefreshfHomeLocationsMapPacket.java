package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import fr.nationsglory.nationsmap.NationsMap;
import fr.nationsglory.nationsmap.map.Marker;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.RefreshfHomeLocationsMapPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class RefreshfHomeLocationsMapPacket implements IPacket, IClientPacket {

   public HashMap<String, String> fHomeLocations;


   public void fromBytes(ByteArrayDataInput data) {
      this.fHomeLocations = (HashMap)(new Gson()).fromJson(data.readUTF(), (new RefreshfHomeLocationsMapPacket$1(this)).getType());
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF((new Gson()).toJson(this.fHomeLocations));
   }

   public void handleClientPacket(EntityPlayer player) {
      ArrayList markersToDelete = new ArrayList();
      Iterator it = NationsMap.instance.markerManager.markerList.iterator();

      Marker pair;
      while(it.hasNext()) {
         pair = (Marker)it.next();
         if(pair.groupName.equalsIgnoreCase("pays")) {
            markersToDelete.add(pair);
         }
      }

      it = markersToDelete.iterator();

      while(it.hasNext()) {
         pair = (Marker)it.next();
         NationsMap.instance.markerManager.delMarker(pair);
      }

      if(!NationsMap.instance.markerManager.groupList.contains("Pays")) {
         NationsMap.instance.markerManager.groupList.add("Pays");
      }

      it = this.fHomeLocations.entrySet().iterator();

      while(it.hasNext()) {
         Entry pair1 = (Entry)it.next();
         String countryName = (String)pair1.getValue();
         String countryLocation = (String)pair1.getKey();
         NationsMap.instance.markerManager.addMarker(countryName, "Pays", Integer.parseInt(countryLocation.split("#")[0]), Integer.parseInt(countryLocation.split("#")[1]), Integer.parseInt(countryLocation.split("#")[2]), 0, -16777216);
         it.remove();
      }

   }
}
