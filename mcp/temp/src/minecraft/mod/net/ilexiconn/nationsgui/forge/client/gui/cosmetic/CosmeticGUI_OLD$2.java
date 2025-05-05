package net.ilexiconn.nationsgui.forge.client.gui.cosmetic;

import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.ilexiconn.nationsgui.forge.client.gui.cosmetic.CosmeticGUI_OLD;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

final class CosmeticGUI_OLD$2 implements GuiScreenTab {

   public Class<? extends GuiScreen> getClassReferent() {
      return CosmeticGUI_OLD.class;
   }

   public void call() {
      Minecraft.func_71410_x().func_71373_a(new CosmeticGUI_OLD());
   }
}
