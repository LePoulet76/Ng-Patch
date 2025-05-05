package net.ilexiconn.nationsgui.forge.client.gui;

import net.ilexiconn.nationsgui.forge.client.gui.AbstractFirstConnectionGui;
import net.ilexiconn.nationsgui.forge.client.gui.FirstConnectionGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

class FirstConnectionGui$Button extends GuiButton {

   // $FF: synthetic field
   final FirstConnectionGui this$0;


   public FirstConnectionGui$Button(FirstConnectionGui var1, int x, int y) {
      super(0, x, y, 17, 40, ">");
      this.this$0 = var1;
   }

   public void func_73737_a(Minecraft par1Minecraft, int par2, int par3) {
      GL11.glPushMatrix();
      this.field_82253_i = par2 >= this.field_73746_c && par3 >= this.field_73743_d && par2 < this.field_73746_c + this.field_73747_a && par3 < this.field_73743_d + this.field_73745_b;
      if(this.field_82253_i) {
         float color = 0.7F;
         GL11.glColor3f(color, color, color);
      }

      GL11.glTranslatef((float)this.field_73746_c, (float)this.field_73743_d, 0.0F);
      GL11.glScalef(0.75F, 0.75F, 0.75F);
      FirstConnectionGui.access$200(this.this$0).func_110434_K().func_110577_a(AbstractFirstConnectionGui.BACKGROUND);
      this.func_73729_b(0, 0, 0, 12, 22, 53);
      GL11.glPushMatrix();
      GL11.glTranslatef((float)(11 - FirstConnectionGui.access$300(this.this$0).func_78256_a(this.field_73744_e) + 1), 19.0F, 0.0F);
      GL11.glScalef(2.0F, 2.0F, 0.0F);
      FirstConnectionGui.access$400(this.this$0).func_78276_b(this.field_73744_e, 0, 0, -1);
      GL11.glPopMatrix();
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      GL11.glPopMatrix();
   }
}
