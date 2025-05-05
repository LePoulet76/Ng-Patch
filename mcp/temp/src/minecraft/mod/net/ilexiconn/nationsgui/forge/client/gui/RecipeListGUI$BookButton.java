package net.ilexiconn.nationsgui.forge.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RecipeListGUI$BookButton extends GuiButton {

   private static final ResourceLocation BACKGROUND = new ResourceLocation("nationsgui", "textures/gui/craftingtable.png");


   public RecipeListGUI$BookButton(int id, int posX, int posY) {
      super(id, posX, posY, 20, 18, "");
   }

   protected int func_73738_a(boolean par1) {
      return par1?0:18;
   }

   public void func_73737_a(Minecraft par1Minecraft, int par2, int par3) {
      if(this.field_73748_h) {
         Minecraft.func_71410_x().func_110434_K().func_110577_a(BACKGROUND);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.field_82253_i = par2 >= this.field_73746_c && par3 >= this.field_73743_d && par2 < this.field_73746_c + this.field_73747_a && par3 < this.field_73743_d + this.field_73745_b;
         int k = this.func_73738_a(this.field_82253_i);
         this.func_73729_b(this.field_73746_c, this.field_73743_d, 145, k, 20, 18);
         this.func_73739_b(par1Minecraft, par2, par3);
      }

   }

}
