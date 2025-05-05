package net.ilexiconn.nationsgui.forge.server.packet.impl;

import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.ilexiconn.nationsgui.forge.client.gui.island.IslandPermsGui;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandMainDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

class IslandMainDataPacket$7 implements GuiScreenTab {

   // $FF: synthetic field
   final IslandMainDataPacket this$0;


   IslandMainDataPacket$7(IslandMainDataPacket this$0) {
      this.this$0 = this$0;
   }

   public Class<? extends GuiScreen> getClassReferent() {
      return IslandPermsGui.class;
   }

   public void call() {
      Minecraft.func_71410_x().func_71373_a(new IslandPermsGui());
   }
}
