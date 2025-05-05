package net.ilexiconn.nationsgui.forge.client;

import net.ilexiconn.nationsgui.forge.client.ClientProxy;

class ClientProxy$11 implements Runnable {

   // $FF: synthetic field
   final ClientProxy this$0;


   ClientProxy$11(ClientProxy this$0) {
      this.this$0 = this$0;
   }

   public void run() {
      long lastRefreshWiki = System.currentTimeMillis();

      while(true) {
         while(System.currentTimeMillis() - lastRefreshWiki <= 3000L) {
            ;
         }

         lastRefreshWiki = System.currentTimeMillis();
         ClientProxy.loadWiki();
      }
   }
}
