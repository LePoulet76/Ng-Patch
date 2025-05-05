package net.ilexiconn.nationsgui.forge.client.gui.summary;

import net.ilexiconn.nationsgui.forge.client.gui.summary.SummaryGUI;
import net.ilexiconn.nationsgui.forge.server.permission.IPermissionCallback;
import net.minecraft.client.gui.GuiButton;

class SummaryGUI$1 implements IPermissionCallback {

   // $FF: synthetic field
   final GuiButton val$buttonShop;
   // $FF: synthetic field
   final SummaryGUI this$0;


   SummaryGUI$1(SummaryGUI this$0, GuiButton var2) {
      this.this$0 = this$0;
      this.val$buttonShop = var2;
   }

   public void call(String permission, boolean has) {
      this.val$buttonShop.field_73742_g = has;
   }
}
