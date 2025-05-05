package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import fr.nationsglory.nationsmap.NationsMap;
import fr.nationsglory.nationsmap.map.Marker;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.RefreshServerMarkersMapPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class RefreshServerMarkersMapPacket implements IPacket, IClientPacket {

   public List<String> markers;


   public void fromBytes(ByteArrayDataInput data) {
      this.markers = (List)(new Gson()).fromJson(data.readUTF(), (new RefreshServerMarkersMapPacket$1(this)).getType());
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF((new Gson()).toJson(this.markers));
   }

   public void handleClientPacket(EntityPlayer player) {
      ArrayList markersToDelete = new ArrayList();
      Iterator var3 = NationsMap.instance.markerManager.markerList.iterator();

      Marker marker;
      while(var3.hasNext()) {
         marker = (Marker)var3.next();
         if(!marker.groupName.equalsIgnoreCase("pays") && !marker.groupName.equalsIgnoreCase("morts") && !marker.groupName.equalsIgnoreCase("perso") && !marker.groupName.equalsIgnoreCase("ressources")) {
            markersToDelete.add(marker);
         }
      }

      var3 = markersToDelete.iterator();

      while(var3.hasNext()) {
         marker = (Marker)var3.next();
         NationsMap.instance.markerManager.delMarker(marker);
      }

      String name;
      String group;
      String x;
      String y;
      String z;
      int colorInt;
      for(var3 = this.markers.iterator(); var3.hasNext(); NationsMap.instance.markerManager.addMarker(name, group, Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(z), 0, colorInt)) {
         String marker1 = (String)var3.next();
         name = marker1.split("##")[0];
         group = marker1.split("##")[1];
         x = marker1.split("##")[2];
         y = marker1.split("##")[3];
         z = marker1.split("##")[4];
         String color = marker1.split("##")[5];
         colorInt = 16711680;
         byte var13 = -1;
         switch(color.hashCode()) {
         case -1008851410:
            if(color.equals("orange")) {
               var13 = 6;
            }
            break;
         case -976943172:
            if(color.equals("purple")) {
               var13 = 7;
            }
            break;
         case -734239628:
            if(color.equals("yellow")) {
               var13 = 3;
            }
            break;
         case 112785:
            if(color.equals("red")) {
               var13 = 0;
            }
            break;
         case 3027034:
            if(color.equals("blue")) {
               var13 = 2;
            }
            break;
         case 3068707:
            if(color.equals("cyan")) {
               var13 = 5;
            }
            break;
         case 3441014:
            if(color.equals("pink")) {
               var13 = 4;
            }
            break;
         case 93818879:
            if(color.equals("black")) {
               var13 = 9;
            }
            break;
         case 98619139:
            if(color.equals("green")) {
               var13 = 1;
            }
            break;
         case 113101865:
            if(color.equals("white")) {
               var13 = 8;
            }
         }

         switch(var13) {
         case 0:
            colorInt = -65536;
            break;
         case 1:
            colorInt = -16711936;
            break;
         case 2:
            colorInt = -16776961;
            break;
         case 3:
            colorInt = -256;
            break;
         case 4:
            colorInt = -65281;
            break;
         case 5:
            colorInt = -16711681;
            break;
         case 6:
            colorInt = -32768;
            break;
         case 7:
            colorInt = -8388353;
            break;
         case 8:
            colorInt = -1;
            break;
         case 9:
            colorInt = -16777216;
         }

         if(!NationsMap.instance.markerManager.groupList.contains(group)) {
            NationsMap.instance.markerManager.groupList.add(group);
         }
      }

      NationsMap.instance.markerManager.update();
   }
}
