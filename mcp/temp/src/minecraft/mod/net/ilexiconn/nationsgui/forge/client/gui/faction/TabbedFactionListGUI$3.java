package net.ilexiconn.nationsgui.forge.client.gui.faction;

import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionListGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

final class TabbedFactionListGUI$3 implements GuiScreenTab {

   public Class<? extends GuiScreen> getClassReferent() {
      return FactionListGUI.class;
   }

   public void call() {
      Minecraft.func_71410_x().func_71373_a(new FactionListGUI());
   }
}
