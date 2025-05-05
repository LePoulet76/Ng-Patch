package net.ilexiconn.nationsgui.forge.client.gui.radio;

import net.ilexiconn.nationsgui.forge.client.gui.radio.RadioGUI;
import net.ilexiconn.nationsgui.forge.client.gui.radio.component.GearButtonGUI;
import net.ilexiconn.nationsgui.forge.server.permission.IPermissionCallback;

class RadioGUI$1 implements IPermissionCallback {

   // $FF: synthetic field
   final RadioGUI this$0;


   RadioGUI$1(RadioGUI this$0) {
      this.this$0 = this$0;
   }

   public void call(String permission, boolean has) {
      if(has) {
         int x = this.this$0.field_73880_f / 2 - 116;
         int y = this.this$0.field_73881_g / 2 - 82;
         RadioGUI.access$000(this.this$0).add(new GearButtonGUI(5, x + 192, y + 13));
      }

   }
}
