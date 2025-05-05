package net.ilexiconn.nationsgui.forge.server.packet.impl;

import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseBetGUI;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseMainDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

class EnterpriseMainDataPacket$9 implements GuiScreenTab {

   // $FF: synthetic field
   final EnterpriseMainDataPacket this$0;


   EnterpriseMainDataPacket$9(EnterpriseMainDataPacket this$0) {
      this.this$0 = this$0;
   }

   public Class<? extends GuiScreen> getClassReferent() {
      return EnterpriseBetGUI.class;
   }

   public void call() {
      Minecraft.func_71410_x().func_71373_a(new EnterpriseBetGUI());
   }
}
