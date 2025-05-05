package net.ilexiconn.nationsgui.forge.client.gui.faction;

import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

final class TabbedFactionGUI$3 implements GuiScreenTab {

   public Class<? extends GuiScreen> getClassReferent() {
      return FactionGUI.class;
   }

   public void call() {
      Minecraft.func_71410_x().func_71373_a(new FactionGUI((String)FactionGUI.factionInfos.get("name")));
   }
}
