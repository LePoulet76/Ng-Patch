package net.ilexiconn.nationsgui.forge.server.packet.impl;

import java.util.TimerTask;
import net.ilexiconn.nationsgui.forge.client.gui.BonusesGui;
import net.ilexiconn.nationsgui.forge.server.packet.impl.BonusDataPacket;
import net.minecraft.client.Minecraft;

class BonusDataPacket$1 extends TimerTask {

   // $FF: synthetic field
   final BonusDataPacket this$0;


   BonusDataPacket$1(BonusDataPacket this$0) {
      this.this$0 = this$0;
   }

   public void run() {
      Minecraft.func_71410_x().func_71373_a(new BonusesGui());
   }
}
