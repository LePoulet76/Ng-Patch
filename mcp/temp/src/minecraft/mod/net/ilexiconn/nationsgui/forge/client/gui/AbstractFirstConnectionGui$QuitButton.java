package net.ilexiconn.nationsgui.forge.client.gui;

import net.ilexiconn.nationsgui.forge.client.gui.AbstractFirstConnectionGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

class AbstractFirstConnectionGui$QuitButton extends GuiButton {

   // $FF: synthetic field
   final AbstractFirstConnectionGui this$0;


   public AbstractFirstConnectionGui$QuitButton(AbstractFirstConnectionGui var1, int x, int y) {
      super(-1, x, y, 9, 10, "");
      this.this$0 = var1;
   }

   public void func_73737_a(Minecraft par1Minecraft, int par2, int par3) {
      AbstractFirstConnectionGui.access$000(this.this$0).func_110434_K().func_110577_a(AbstractFirstConnectionGui.SHOP_ASSET);
      this.field_82253_i = par2 >= this.field_73746_c && par3 >= this.field_73743_d && par2 < this.field_73746_c + this.field_73747_a && par3 < this.field_73743_d + this.field_73745_b;
      this.func_73729_b(this.field_73746_c, this.field_73743_d, 204, this.field_82253_i?18:28, 9, 10);
   }
}
