package net.ilexiconn.nationsgui.forge.client.gui.faction;

import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionSkillsGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

final class TabbedFactionGUI$6 implements GuiScreenTab {

   public Class<? extends GuiScreen> getClassReferent() {
      return FactionSkillsGUI.class;
   }

   public void call() {
      Minecraft.func_71410_x().func_71373_a(new FactionSkillsGUI());
   }
}
