package net.ilexiconn.nationsgui.forge.server.packet.impl;

import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.ilexiconn.nationsgui.forge.client.gui.island.IslandMembersGui;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandMainDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

class IslandMainDataPacket$6 implements GuiScreenTab {

   // $FF: synthetic field
   final IslandMainDataPacket this$0;


   IslandMainDataPacket$6(IslandMainDataPacket this$0) {
      this.this$0 = this$0;
   }

   public Class<? extends GuiScreen> getClassReferent() {
      return IslandMembersGui.class;
   }

   public void call() {
      Minecraft.func_71410_x().func_71373_a(new IslandMembersGui());
   }
}
