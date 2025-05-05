package net.ilexiconn.nationsgui.forge.client;

import java.io.IOException;
import net.ilexiconn.nationsgui.forge.server.ServerProxy;

final class ClientProxy$3 implements Runnable {

   // $FF: synthetic field
   final String val$cape;


   ClientProxy$3(String var1) {
      this.val$cape = var1;
   }

   public void run() {
      try {
         ServerProxy.getCacheManager().downloadAndStockInCache("https://apiv2.nationsglory.fr/json/capes/capes/" + this.val$cape.replaceAll("cape_", "") + ".png", this.val$cape + ".png");
      } catch (IOException var2) {
         var2.printStackTrace();
      }

   }
}
