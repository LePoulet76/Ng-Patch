package net.ilexiconn.nationsgui.forge.client.gui;

import net.ilexiconn.nationsgui.forge.client.gui.RecipeListGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

class RecipeListGUI$DisplayButton extends GuiButton {

   public boolean en;
   public boolean hovered;
   // $FF: synthetic field
   final RecipeListGUI this$0;


   public RecipeListGUI$DisplayButton(RecipeListGUI var1, int id, int posX, int posY) {
      super(id, posX, posY, 14, 14, "");
      this.this$0 = var1;
      this.en = false;
      this.hovered = false;
   }

   protected int func_73738_a(boolean par1) {
      return par1?0:18;
   }

   public void func_73737_a(Minecraft par1Minecraft, int par2, int par3) {
      if(this.field_73748_h) {
         RecipeListGUI.access$100(this.this$0).func_110434_K().func_110577_a(RecipeListGUI.access$000());
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.hovered = par2 >= this.field_73746_c && par3 >= this.field_73743_d && par2 < this.field_73746_c + this.field_73747_a && par3 < this.field_73743_d + this.field_73745_b;
         int k = this.en?0:14;
         this.func_73729_b(this.field_73746_c, this.field_73743_d, 206, k, 14, 14);
         this.func_73739_b(par1Minecraft, par2, par3);
      }

   }
}
