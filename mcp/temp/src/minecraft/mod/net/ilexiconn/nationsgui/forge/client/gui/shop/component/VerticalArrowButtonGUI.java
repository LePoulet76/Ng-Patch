package net.ilexiconn.nationsgui.forge.client.gui.shop.component;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.gui.shop.ShopGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class VerticalArrowButtonGUI extends GuiButton {

   private boolean up;


   public VerticalArrowButtonGUI(int id, int x, int y, boolean up) {
      super(id, x, y, 21, 10, "");
      this.up = up;
   }

   public void func_73737_a(Minecraft mc, int mouseX, int mouseY) {
      this.field_82253_i = mouseX >= this.field_73746_c && mouseY >= this.field_73743_d && mouseX < this.field_73746_c + this.field_73747_a && mouseY < this.field_73743_d + this.field_73745_b;
      int state = this.func_73738_a(this.field_82253_i);
      mc.func_110434_K().func_110577_a(ShopGUI.TEXTURE);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.func_73729_b(this.field_73746_c, this.field_73743_d, this.up?113:134, 38 + state * 10, this.field_73747_a, this.field_73745_b);
   }
}
