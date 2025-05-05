package net.ilexiconn.nationsgui.forge.client.gui;

import net.ilexiconn.nationsgui.forge.client.gui.CustomizationGUI;
import net.ilexiconn.nationsgui.forge.client.gui.GuiButtonOptionBoolean;
import net.minecraft.client.settings.EnumOptions;

class CustomizationGUI$12 extends GuiButtonOptionBoolean {

   // $FF: synthetic field
   final EnumOptions val$options;
   // $FF: synthetic field
   final CustomizationGUI this$0;


   CustomizationGUI$12(CustomizationGUI this$0, int x0, int x1, int x2, int x3, int x4, String x5, EnumOptions var8) {
      super(x0, x1, x2, x3, x4, x5);
      this.this$0 = this$0;
      this.val$options = var8;
   }

   public void setData(Boolean data) {
      CustomizationGUI.access$100(this.this$0).field_71474_y.func_74306_a(this.val$options, 1);
   }

   public Boolean getData() {
      return Boolean.valueOf(CustomizationGUI.access$200(this.this$0).field_71474_y.func_74308_b(this.val$options));
   }

   // $FF: synthetic method
   // $FF: bridge method
   public Object getData() {
      return this.getData();
   }

   // $FF: synthetic method
   // $FF: bridge method
   public void setData(Object var1) {
      this.setData((Boolean)var1);
   }
}
