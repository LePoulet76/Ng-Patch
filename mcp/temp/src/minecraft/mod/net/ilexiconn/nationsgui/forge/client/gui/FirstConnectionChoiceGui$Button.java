package net.ilexiconn.nationsgui.forge.client.gui;

import net.ilexiconn.nationsgui.forge.client.gui.FirstConnectionChoiceGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

class FirstConnectionChoiceGui$Button extends GuiButton {

   // $FF: synthetic field
   final FirstConnectionChoiceGui this$0;


   public FirstConnectionChoiceGui$Button(FirstConnectionChoiceGui var1, int id, int x, int y, String text) {
      super(id, x, y, 150, 15, text);
      this.this$0 = var1;
   }

   public void func_73737_a(Minecraft par1Minecraft, int mouseX, int mouseY) {
      this.field_82253_i = mouseX >= this.field_73746_c && mouseY >= this.field_73743_d && mouseX < this.field_73746_c + this.field_73747_a && mouseY < this.field_73743_d + this.field_73745_b;
      if(this.field_82253_i) {
         float finale = 0.5F;
         GL11.glColor3f(finale, finale, finale);
      }

      this.this$0.drawGreyRectangle(this.field_73746_c, this.field_73743_d, this.field_73747_a, this.field_73745_b);
      String finale1 = this.field_73744_e + (this.field_73742_g?"":" (Indisponible)");
      FirstConnectionChoiceGui.access$300(this.this$0).field_71466_p.func_78276_b(finale1, this.field_73746_c + this.field_73747_a / 2 - FirstConnectionChoiceGui.access$200(this.this$0).field_71466_p.func_78256_a(finale1) / 2, this.field_73743_d + this.field_73745_b / 2 - 4, -1);
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
   }
}
