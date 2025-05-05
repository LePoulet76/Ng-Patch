package net.ilexiconn.nationsgui.forge.client.gui.enterprise;

import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseListGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

final class EnterpriseCreateGui$2 implements GuiScreenTab {

   public Class<? extends GuiScreen> getClassReferent() {
      return EnterpriseListGui.class;
   }

   public void call() {
      Minecraft.func_71410_x().func_71373_a(new EnterpriseListGui());
   }
}
