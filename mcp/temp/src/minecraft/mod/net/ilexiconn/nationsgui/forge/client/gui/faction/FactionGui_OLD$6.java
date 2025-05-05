package net.ilexiconn.nationsgui.forge.client.gui.faction;

import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.ilexiconn.nationsgui.forge.client.gui.faction.ActionsGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

final class FactionGui_OLD$6 implements GuiScreenTab {

   public Class<? extends GuiScreen> getClassReferent() {
      return ActionsGUI.class;
   }

   public void call() {
      Minecraft.func_71410_x().func_71373_a(new ActionsGUI(Minecraft.func_71410_x().field_71439_g));
   }
}
