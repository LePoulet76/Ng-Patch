package net.ilexiconn.nationsgui.forge.client;

import java.util.TimerTask;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientSocket;
import net.ilexiconn.nationsgui.forge.client.ClientTickHandler;

class ClientTickHandler$1 extends TimerTask {

   // $FF: synthetic field
   final String val$serverConnectOn;
   // $FF: synthetic field
   final ClientTickHandler this$0;


   ClientTickHandler$1(ClientTickHandler this$0, String var2) {
      this.this$0 = this$0;
      this.val$serverConnectOn = var2;
   }

   public void run() {
      ClientSocket.out.println("MESSAGE socket ADD_WAITINGLIST " + this.val$serverConnectOn);
      ClientData.waitingJoinTime = Long.valueOf(System.currentTimeMillis());
   }
}
