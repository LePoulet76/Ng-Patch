package net.ilexiconn.nationsgui.forge.client;

import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;

class ClientEventHandler$2 extends Thread {

   // $FF: synthetic field
   final ClientEventHandler this$0;


   ClientEventHandler$2(ClientEventHandler this$0) {
      this.this$0 = this$0;
   }

   public void run() {
      while(this.isAlive()) {
         ClientEventHandler.lastClicks = ClientEventHandler.currentClicks;
         ClientEventHandler.currentClicks = 0;

         try {
            Thread.sleep(1000L);
         } catch (InterruptedException var2) {
            ;
         }
      }

   }
}
