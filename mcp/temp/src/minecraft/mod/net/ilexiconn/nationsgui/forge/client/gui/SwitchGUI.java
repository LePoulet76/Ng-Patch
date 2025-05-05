package net.ilexiconn.nationsgui.forge.client.gui;

import net.minecraft.client.gui.GuiScreen;

public class SwitchGUI extends GuiScreen {

   private GuiScreen target;


   public SwitchGUI(GuiScreen target) {
      this.target = target;
   }

   public void func_73866_w_() {
      this.field_73882_e.func_71373_a(this.target);
   }
}
