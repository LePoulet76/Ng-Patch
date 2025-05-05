package net.ilexiconn.nationsgui.forge.server.packet.impl;

import acs.tabbychat.ChatChannel;
import acs.tabbychat.TabbyChat;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.RefreshChannelPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class RefreshChannelPacket implements IPacket, IClientPacket {

   ArrayList<String> authorizedChannels = new ArrayList();


   public void fromBytes(ByteArrayDataInput data) {
      this.authorizedChannels = (ArrayList)(new Gson()).fromJson(data.readUTF(), (new RefreshChannelPacket$1(this)).getType());
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF((new Gson()).toJson(this.authorizedChannels));
   }

   public void handleClientPacket(EntityPlayer player) {
      if(ClientProxy.serverType.equals("ng")) {
         Iterator channel = this.authorizedChannels.iterator();

         while(channel.hasNext()) {
            String it = (String)channel.next();
            byte var5 = -1;
            switch(it.hashCode()) {
            case -1419464905:
               if(it.equals("journal")) {
                  var5 = 2;
               }
               break;
            case -1405559108:
               if(it.equals("avocat")) {
                  var5 = 3;
               }
               break;
            case -982670050:
               if(it.equals("police")) {
                  var5 = 5;
               }
               break;
            case 3646:
               if(it.equals("rp")) {
                  var5 = 7;
               }
               break;
            case 3327407:
               if(it.equals("logs")) {
                  var5 = 8;
               }
               break;
            case 3357101:
               if(it.equals("modo")) {
                  var5 = 1;
               }
               break;
            case 92668751:
               if(it.equals("admin")) {
                  var5 = 0;
               }
               break;
            case 98712316:
               if(it.equals("guide")) {
                  var5 = 6;
               }
               break;
            case 103654890:
               if(it.equals("mafia")) {
                  var5 = 4;
               }
            }

            switch(var5) {
            case 0:
               TabbyChat.instance.channelMap.put("ADMIN", new ChatChannel("ADMIN"));
               break;
            case 1:
               TabbyChat.instance.channelMap.put("MODO", new ChatChannel("MODO"));
               break;
            case 2:
               TabbyChat.instance.channelMap.put("Journal", new ChatChannel("Journal"));
               break;
            case 3:
               TabbyChat.instance.channelMap.put("Avocat", new ChatChannel("Avocat"));
               break;
            case 4:
               TabbyChat.instance.channelMap.put("Mafia", new ChatChannel("Mafia"));
               break;
            case 5:
               TabbyChat.instance.channelMap.put("Police", new ChatChannel("Police"));
               break;
            case 6:
               TabbyChat.instance.channelMap.put("Guide", new ChatChannel("Guide"));
               break;
            case 7:
               TabbyChat.instance.channelMap.put("RP", new ChatChannel("RP"));
               break;
            case 8:
               TabbyChat.instance.channelMap.put("Logs", new ChatChannel("Logs"));
            }
         }
      } else {
         String channel1 = (String)this.authorizedChannels.get(this.authorizedChannels.size() - 1);
         Iterator it1 = TabbyChat.instance.channelMap.entrySet().iterator();

         while(it1.hasNext()) {
            Entry pair = (Entry)it1.next();
            if(!((String)pair.getKey()).contains("Global")) {
               it1.remove();
            }
         }

         if(channel1.matches("i[0-9]+")) {
            TabbyChat.instance.channelMap.put("Ile " + channel1.replace("i", ""), new ChatChannel("Ile " + channel1.replace("i", "")));
         }
      }

   }
}
