package net.ilexiconn.nationsgui.forge.client.gui.multi;

import java.io.IOException;
import net.ilexiconn.nationsgui.forge.client.gui.multi.MultiGUI;
import net.minecraft.client.multiplayer.ServerData;

class MultiGUI$1 implements Runnable {

   // $FF: synthetic field
   final int val$finalI;
   // $FF: synthetic field
   final MultiGUI this$0;


   MultiGUI$1(MultiGUI this$0, int var2) {
      this.this$0 = this$0;
      this.val$finalI = var2;
   }

   public void run() {
      ServerData serverData = this.this$0.multiServers.func_78850_a(this.val$finalI);

      try {
         long e = System.nanoTime();
         MultiGUI.access$000(serverData);
         long var2 = System.nanoTime();
         serverData.field_78844_e = (var2 - e) / 1000000L;
      } catch (IOException var6) {
         var6.printStackTrace();
      }

   }
}
