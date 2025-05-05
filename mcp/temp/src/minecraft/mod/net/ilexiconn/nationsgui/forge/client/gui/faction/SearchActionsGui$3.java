package net.ilexiconn.nationsgui.forge.client.gui.faction;

import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.ilexiconn.nationsgui.forge.client.gui.faction.SearchActionsGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

final class SearchActionsGui$3 implements GuiScreenTab {

   public Class<? extends GuiScreen> getClassReferent() {
      return SearchActionsGui.class;
   }

   public void call() {
      Minecraft.func_71410_x().func_71373_a(new SearchActionsGui());
   }
}
