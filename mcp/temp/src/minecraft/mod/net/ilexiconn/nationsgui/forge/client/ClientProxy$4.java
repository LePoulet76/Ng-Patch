package net.ilexiconn.nationsgui.forge.client;

import cpw.mods.fml.common.ICrashCallable;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.minecraft.client.Minecraft;

class ClientProxy$4 implements ICrashCallable {

   // $FF: synthetic field
   final ClientProxy this$0;


   ClientProxy$4(ClientProxy this$0) {
      this.this$0 = this$0;
   }

   public String getLabel() {
      return "Username";
   }

   public String call() throws Exception {
      return Minecraft.func_71410_x().func_110432_I().func_111285_a();
   }

   // $FF: synthetic method
   // $FF: bridge method
   public Object call() throws Exception {
      return this.call();
   }
}
