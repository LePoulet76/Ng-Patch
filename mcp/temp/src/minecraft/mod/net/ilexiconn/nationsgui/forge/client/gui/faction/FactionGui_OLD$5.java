package net.ilexiconn.nationsgui.forge.client.gui.faction;

import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.ilexiconn.nationsgui.forge.client.gui.faction.PermGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

final class FactionGui_OLD$5 implements GuiScreenTab {

   public Class<? extends GuiScreen> getClassReferent() {
      return PermGUI.class;
   }

   public void call() {
      Minecraft.func_71410_x().func_71373_a(new PermGUI(Minecraft.func_71410_x().field_71439_g));
   }
}
