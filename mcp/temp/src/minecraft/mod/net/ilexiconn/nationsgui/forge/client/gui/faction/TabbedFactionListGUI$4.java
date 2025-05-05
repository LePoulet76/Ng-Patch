package net.ilexiconn.nationsgui.forge.client.gui.faction;

import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.ilexiconn.nationsgui.forge.client.gui.faction.PlayerListGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

final class TabbedFactionListGUI$4 implements GuiScreenTab {

   public Class<? extends GuiScreen> getClassReferent() {
      return PlayerListGUI.class;
   }

   public void call() {
      Minecraft.func_71410_x().func_71373_a(new PlayerListGUI());
   }
}
