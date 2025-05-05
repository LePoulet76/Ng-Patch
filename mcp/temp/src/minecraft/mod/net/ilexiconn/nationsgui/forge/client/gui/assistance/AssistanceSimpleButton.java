package net.ilexiconn.nationsgui.forge.client.gui.assistance;

import net.ilexiconn.nationsgui.forge.client.gui.assistance.AbstractAssistanceGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

public class AssistanceSimpleButton extends GuiButton {

   private final int u;
   private final int v;


   public AssistanceSimpleButton(int id, int posX, int posY, int u, int v) {
      super(id, posX, posY, 20, 16, "");
      this.u = u;
      this.v = v;
   }

   public void func_73737_a(Minecraft par1Minecraft, int par2, int par3) {
      if(this.field_73748_h) {
         this.field_82253_i = par2 >= this.field_73746_c && par3 >= this.field_73743_d && par2 < this.field_73746_c + this.field_73747_a && par3 < this.field_73743_d + this.field_73745_b;
         Minecraft.func_71410_x().func_110434_K().func_110577_a(AbstractAssistanceGUI.GUI_TEXTURE);
         GL11.glColor3f(1.0F, 1.0F, 1.0F);
         ModernGui.drawModalRectWithCustomSizedTexture((float)this.field_73746_c, (float)this.field_73743_d, this.u + (this.field_82253_i?20:0), this.v, 20, 16, 512.0F, 512.0F, false);
      }

   }
}
