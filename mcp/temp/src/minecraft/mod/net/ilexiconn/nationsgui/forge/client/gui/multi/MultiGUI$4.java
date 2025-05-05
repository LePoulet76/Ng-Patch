package net.ilexiconn.nationsgui.forge.client.gui.multi;

import java.io.IOException;
import net.ilexiconn.nationsgui.forge.client.gui.multi.MultiGUI;
import net.minecraft.client.multiplayer.ServerData;

class MultiGUI$4 implements Runnable {

   // $FF: synthetic field
   final ServerData val$serverDataFinal;
   // $FF: synthetic field
   final MultiGUI this$0;


   MultiGUI$4(MultiGUI this$0, ServerData var2) {
      this.this$0 = this$0;
      this.val$serverDataFinal = var2;
   }

   public void run() {
      try {
         long e = System.nanoTime();
         MultiGUI.access$000(this.val$serverDataFinal);
         long var2 = System.nanoTime();
         this.val$serverDataFinal.field_78844_e = (var2 - e) / 1000000L;
      } catch (IOException var5) {
         var5.printStackTrace();
      }

   }
}
