package net.ilexiconn.nationsgui.forge.server.packet.impl;

import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseFarmGUI;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseMainDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

class EnterpriseMainDataPacket$8 implements GuiScreenTab {

   // $FF: synthetic field
   final EnterpriseMainDataPacket this$0;


   EnterpriseMainDataPacket$8(EnterpriseMainDataPacket this$0) {
      this.this$0 = this$0;
   }

   public Class<? extends GuiScreen> getClassReferent() {
      return EnterpriseFarmGUI.class;
   }

   public void call() {
      Minecraft.func_71410_x().func_71373_a(new EnterpriseFarmGUI());
   }
}
