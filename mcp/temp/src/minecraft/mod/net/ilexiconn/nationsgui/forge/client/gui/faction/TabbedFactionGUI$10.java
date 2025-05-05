package net.ilexiconn.nationsgui.forge.client.gui.faction;

import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionStatsGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

final class TabbedFactionGUI$10 implements GuiScreenTab {

   public Class<? extends GuiScreen> getClassReferent() {
      return FactionStatsGUI.class;
   }

   public void call() {
      Minecraft.func_71410_x().func_71373_a(new FactionStatsGUI());
   }
}
