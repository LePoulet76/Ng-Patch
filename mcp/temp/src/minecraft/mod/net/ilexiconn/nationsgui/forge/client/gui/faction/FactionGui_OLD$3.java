package net.ilexiconn.nationsgui.forge.client.gui.faction;

import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.ilexiconn.nationsgui.forge.client.gui.faction.DiplomatieGUI_OLD;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

final class FactionGui_OLD$3 implements GuiScreenTab {

   public Class<? extends GuiScreen> getClassReferent() {
      return DiplomatieGUI_OLD.class;
   }

   public void call() {
      Minecraft.func_71410_x().func_71373_a(new DiplomatieGUI_OLD(Minecraft.func_71410_x().field_71439_g));
   }
}
