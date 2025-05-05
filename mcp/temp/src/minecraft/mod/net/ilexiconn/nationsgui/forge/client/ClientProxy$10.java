package net.ilexiconn.nationsgui.forge.client;

import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.chat.IChatFallback;
import net.ilexiconn.nationsgui.forge.client.gui.SnackbarGUI;
import net.minecraft.util.StatCollector;

class ClientProxy$10 implements IChatFallback {

   // $FF: synthetic field
   final ClientProxy this$0;


   ClientProxy$10(ClientProxy this$0) {
      this.this$0 = this$0;
   }

   public void call() {
      ClientProxy.SNACKBAR_LIST.add(new SnackbarGUI(StatCollector.func_74838_a("nationsgui.auth.invalid")));
   }
}
