package net.ilexiconn.nationsgui.forge.client.gui.cosmetic;

import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.ilexiconn.nationsgui.forge.client.gui.InventoryGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

final class CosmeticGUI_OLD$1 implements GuiScreenTab {

   public Class<? extends GuiScreen> getClassReferent() {
      return null;
   }

   public void call() {
      Minecraft.func_71410_x().func_71373_a(new InventoryGUI(Minecraft.func_71410_x().field_71439_g));
   }
}
