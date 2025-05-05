package net.ilexiconn.nationsgui.forge.server.packet.impl;

import java.util.TimerTask;
import net.ilexiconn.nationsgui.forge.client.gui.ServerSwitchExpressGui;
import net.ilexiconn.nationsgui.forge.server.packet.impl.ChangeServerPacket;
import net.minecraft.client.Minecraft;

class ChangeServerPacket$1 extends TimerTask {

   // $FF: synthetic field
   final String val$serverName;
   // $FF: synthetic field
   final ChangeServerPacket this$0;


   ChangeServerPacket$1(ChangeServerPacket this$0, String var2) {
      this.this$0 = this$0;
      this.val$serverName = var2;
   }

   public void run() {
      Minecraft.func_71410_x().func_71373_a(new ServerSwitchExpressGui(ChangeServerPacket.access$000(this.this$0), ChangeServerPacket.access$100(this.this$0), this.val$serverName));
   }
}
