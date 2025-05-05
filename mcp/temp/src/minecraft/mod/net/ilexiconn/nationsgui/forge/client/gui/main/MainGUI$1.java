package net.ilexiconn.nationsgui.forge.client.gui.main;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.data.ServersData$Server;
import net.ilexiconn.nationsgui.forge.client.data.ServersData$ServerGroup;
import net.ilexiconn.nationsgui.forge.client.gui.main.MainGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.multiplayer.ServerData;

class MainGUI$1 extends Thread {

   // $FF: synthetic field
   final MainGUI this$0;


   MainGUI$1(MainGUI this$0) {
      this.this$0 = this$0;
   }

   public void run() {
      Method method = null;

      try {
         method = GuiMultiplayer.class.getDeclaredMethod("func_74017_b", new Class[]{ServerData.class});
         method.setAccessible(true);
      } catch (NoSuchMethodException var8) {
         var8.printStackTrace();
      }

      HashMap serverMap = new HashMap();
      Iterator var4 = ClientProxy.serversData.getPermanentServers().entrySet().iterator();

      while(var4.hasNext()) {
         Entry server = (Entry)var4.next();
         serverMap.putAll(((ServersData$ServerGroup)server.getValue()).getServerMap());
      }

      serverMap.putAll(ClientProxy.serversData.getTemporaryServers());
      var4 = serverMap.values().iterator();

      while(var4.hasNext()) {
         ServersData$Server server1 = (ServersData$Server)var4.next();
         if(server1.isVisible(Minecraft.func_71410_x()) && !server1.getIp().contains("event.nationsglory")) {
            ServerData serverData = new ServerData("Server", server1.getIp());

            try {
               method.invoke((Object)null, new Object[]{serverData});
               server1.setSlots(Integer.parseInt(MainGUI.access$000(this.this$0, serverData.field_78846_c)));
            } catch (InvocationTargetException var7) {
               server1.setSlots(0);
            }
         }
      }

   }
}
