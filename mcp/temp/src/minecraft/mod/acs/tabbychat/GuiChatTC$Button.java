package acs.tabbychat;

import acs.tabbychat.GuiChatTC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

class GuiChatTC$Button extends GuiButton {

   // $FF: synthetic field
   final GuiChatTC this$0;


   public GuiChatTC$Button(GuiChatTC var1, int id, int posX, int posY, int width, int height) {
      super(id, posX, posY, width, height, "");
      this.this$0 = var1;
   }

   public void func_73737_a(Minecraft par1Minecraft, int par2, int par3) {
      this.field_82253_i = par2 >= this.field_73746_c && par3 >= this.field_73743_d && par2 < this.field_73746_c + this.field_73747_a && par3 < this.field_73743_d + this.field_73745_b;
   }
}
