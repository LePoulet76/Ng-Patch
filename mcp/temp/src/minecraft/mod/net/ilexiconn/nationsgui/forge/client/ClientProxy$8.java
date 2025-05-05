package net.ilexiconn.nationsgui.forge.client;

import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.chat.IChatFallback;
import net.ilexiconn.nationsgui.forge.client.gui.FirstConnectionGui;
import net.minecraft.client.Minecraft;

class ClientProxy$8 implements IChatFallback {

   // $FF: synthetic field
   final ClientProxy this$0;


   ClientProxy$8(ClientProxy this$0) {
      this.this$0 = this$0;
   }

   public void call() {
      Minecraft.func_71410_x().field_71462_r = null;
      Minecraft.func_71410_x().func_71381_h();
      Minecraft.func_71410_x().field_71416_A.func_82461_f();
      if(ClientProxy.openFirstConnectionGuiAfterAuthMe && ClientProxy.serverType.equals("ng")) {
         Minecraft.func_71410_x().func_71373_a(new FirstConnectionGui(ClientProxy.currentServerName));
         ClientProxy.openFirstConnectionGuiAfterAuthMe = false;
      }

   }
}
