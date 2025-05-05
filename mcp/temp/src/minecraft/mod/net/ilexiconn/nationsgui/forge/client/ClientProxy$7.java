package net.ilexiconn.nationsgui.forge.client;

import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.chat.IChatFallback;
import net.ilexiconn.nationsgui.forge.client.gui.auth.AuthGUI;
import net.ilexiconn.nationsgui.forge.client.gui.auth.type.AuthTypes;
import net.minecraft.client.Minecraft;

class ClientProxy$7 implements IChatFallback {

   // $FF: synthetic field
   final ClientProxy this$0;


   ClientProxy$7(ClientProxy this$0) {
      this.this$0 = this$0;
   }

   public void call() {
      if(!(Minecraft.func_71410_x().field_71462_r instanceof AuthGUI) || ((AuthGUI)Minecraft.func_71410_x().field_71462_r).getType() != AuthTypes.REGISTER) {
         Minecraft.func_71410_x().func_71373_a(new AuthGUI(AuthTypes.REGISTER));
      }

   }
}
