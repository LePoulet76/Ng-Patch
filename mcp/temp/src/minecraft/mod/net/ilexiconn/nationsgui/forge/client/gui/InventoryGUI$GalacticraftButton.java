package net.ilexiconn.nationsgui.forge.client.gui;

import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

public class InventoryGUI$GalacticraftButton extends GuiButton {

   public InventoryGUI$GalacticraftButton(int id, int posX, int posY) {
      super(id, posX, posY, 20, 18, "");
   }

   protected int func_73738_a(boolean par1) {
      return par1?220:238;
   }

   public void func_73737_a(Minecraft par1Minecraft, int par2, int par3) {
      if(this.field_73748_h) {
         ClientEventHandler.STYLE.bindTexture("inventory");
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.field_82253_i = par2 >= this.field_73746_c && par3 >= this.field_73743_d && par2 < this.field_73746_c + this.field_73747_a && par3 < this.field_73743_d + this.field_73745_b;
         int k = this.func_73738_a(this.field_82253_i);
         this.func_73729_b(this.field_73746_c, this.field_73743_d, 236, k, 20, 18);
         this.func_73739_b(par1Minecraft, par2, par3);
      }

   }
}
