package net.ilexiconn.nationsgui.forge.client.gui.auction;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class AuctionSimpleButton extends GuiButton {

   public AuctionSimpleButton(int p_i1020_1_, int p_i1020_2_, int p_i1020_3_, String p_i1020_4_) {
      super(p_i1020_1_, p_i1020_2_, p_i1020_3_, p_i1020_4_);
   }

   public AuctionSimpleButton(int p_i1021_1_, int p_i1021_2_, int p_i1021_3_, int p_i1021_4_, int p_i1021_5_, String p_i1021_6_) {
      super(p_i1021_1_, p_i1021_2_, p_i1021_3_, p_i1021_4_, p_i1021_5_, p_i1021_6_);
   }

   public void func_73737_a(Minecraft par1Minecraft, int par2, int par3) {
      this.field_82253_i = par2 >= this.field_73746_c && par3 >= this.field_73743_d && par2 < this.field_73746_c + this.field_73747_a && par3 < this.field_73743_d + this.field_73745_b;
      int color = this.field_82253_i?-11119018:-14737633;
      func_73734_a(this.field_73746_c + 1, this.field_73743_d, this.field_73746_c + this.field_73747_a - 1, this.field_73743_d + this.field_73745_b, color);
      func_73734_a(this.field_73746_c, this.field_73743_d + 1, this.field_73746_c + 1, this.field_73743_d + this.field_73745_b - 1, color);
      func_73734_a(this.field_73746_c + this.field_73747_a - 1, this.field_73743_d + 1, this.field_73746_c + this.field_73747_a, this.field_73743_d + this.field_73745_b - 1, color);
      par1Minecraft.field_71466_p.func_78276_b(this.field_73744_e, this.field_73746_c + this.field_73747_a / 2 - par1Minecraft.field_71466_p.func_78256_a(this.field_73744_e) / 2, this.field_73743_d + this.field_73745_b / 2 - 3, 16777215);
   }
}
