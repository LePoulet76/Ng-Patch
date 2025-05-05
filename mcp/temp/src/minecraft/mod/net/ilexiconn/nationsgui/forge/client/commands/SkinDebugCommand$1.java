package net.ilexiconn.nationsgui.forge.client.commands;

import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.commands.SkinDebugCommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatMessageComponent;
import org.json.simple.parser.ParseException;

class SkinDebugCommand$1 implements Runnable {

   // $FF: synthetic field
   final ICommandSender val$icommandsender;
   // $FF: synthetic field
   final SkinDebugCommand this$0;


   SkinDebugCommand$1(SkinDebugCommand this$0, ICommandSender var2) {
      this.this$0 = this$0;
      this.val$icommandsender = var2;
   }

   public void run() {
      try {
         ClientProxy.SKIN_MANAGER.loadSkins();
         this.val$icommandsender.func_70006_a(ChatMessageComponent.func_111066_d("Skins reloaded"));
      } catch (ParseException var2) {
         var2.printStackTrace();
         this.val$icommandsender.func_70006_a(ChatMessageComponent.func_111066_d("Skins reload failed"));
      }

   }
}
