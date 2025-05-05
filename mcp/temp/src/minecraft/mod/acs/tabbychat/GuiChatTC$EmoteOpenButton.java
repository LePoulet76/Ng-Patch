package acs.tabbychat;

import acs.tabbychat.GuiChatTC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

class GuiChatTC$EmoteOpenButton extends GuiButton {

   // $FF: synthetic field
   final GuiChatTC this$0;


   public GuiChatTC$EmoteOpenButton(GuiChatTC var1, int id, int posX, int posY, int width, int height) {
      super(id, posX, posY, width, height, "\u263a");
      this.this$0 = var1;
   }

   public void func_73737_a(Minecraft par1Minecraft, int par2, int par3) {
      this.field_82253_i = par2 >= this.field_73746_c && par3 >= this.field_73743_d && par2 < this.field_73746_c + this.field_73747_a && par3 < this.field_73743_d + this.field_73745_b;
      int l = 14737632;
      if(this.field_82253_i) {
         l = 16777120;
      }

      float _mult = GuiChatTC.access$000(this.this$0).field_71474_y.field_74357_r * 0.9F + 0.1F;
      int _opacity = (int)(255.0F * _mult);
      func_73734_a(this.field_73746_c, this.field_73743_d, this.field_73746_c + this.field_73747_a, this.field_73743_d + this.field_73745_b, _opacity / 2 << 24);
      int var10003 = this.field_73746_c + this.field_73747_a / 2;
      int var10004 = this.field_73743_d + (this.field_73745_b - 8) / 2;
      this.func_73732_a(Minecraft.func_71410_x().field_71466_p, this.field_73744_e, var10003, var10004, l);
   }
}
