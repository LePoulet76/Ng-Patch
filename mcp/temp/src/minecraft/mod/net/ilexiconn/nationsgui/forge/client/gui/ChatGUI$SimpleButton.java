package net.ilexiconn.nationsgui.forge.client.gui;

import net.ilexiconn.nationsgui.forge.client.gui.ChatGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

class ChatGUI$SimpleButton extends GuiButton {

   // $FF: synthetic field
   final ChatGUI this$0;


   public ChatGUI$SimpleButton(ChatGUI var1, int p_i1021_1_, int p_i1021_2_, int p_i1021_3_, int p_i1021_4_, int p_i1021_5_, String p_i1021_6_) {
      super(p_i1021_1_, p_i1021_2_, p_i1021_3_, p_i1021_4_, p_i1021_5_, p_i1021_6_);
      this.this$0 = var1;
   }

   public void func_73737_a(Minecraft par1Minecraft, int par2, int par3) {
      this.field_82253_i = par2 >= this.field_73746_c && par3 >= this.field_73743_d && par2 < this.field_73746_c + this.field_73747_a && par3 < this.field_73743_d + this.field_73745_b;
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      func_73734_a(this.field_73746_c, this.field_73743_d, this.field_73746_c + this.field_73747_a, this.field_73743_d + this.field_73745_b, this.field_82253_i?2013265919:1442840575);
      ChatGUI.access$100(this.this$0).func_78276_b(this.field_73744_e, this.field_73746_c + this.field_73747_a / 2 - ChatGUI.access$000(this.this$0).func_78256_a(this.field_73744_e) / 2, this.field_73743_d + 4, 16777215);
   }
}
