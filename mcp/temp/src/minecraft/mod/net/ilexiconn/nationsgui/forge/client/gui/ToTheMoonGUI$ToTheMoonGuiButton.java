package net.ilexiconn.nationsgui.forge.client.gui;

import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class ToTheMoonGUI$ToTheMoonGuiButton extends GuiButton {

   private boolean isActive;


   public ToTheMoonGUI$ToTheMoonGuiButton(int par1, int par2, int par3, String par4Str, boolean isActive) {
      super(par1, par2, par3, par4Str);
      this.isActive = isActive;
      this.field_73747_a = 94;
      this.field_73745_b = 15;
   }

   public void func_73737_a(Minecraft mc, int mouseX, int mouseY) {
      ClientEventHandler.STYLE.bindTexture("to_the_moon");
      if(this.isActive) {
         if(mouseX >= this.field_73746_c && mouseY >= this.field_73743_d && mouseX < this.field_73746_c + this.field_73747_a && mouseY < this.field_73743_d + this.field_73745_b) {
            ModernGui.drawModalRectWithCustomSizedTexture((float)this.field_73746_c, (float)this.field_73743_d, 0, 190, this.field_73747_a, this.field_73745_b, 512.0F, 512.0F, false);
         } else {
            ModernGui.drawModalRectWithCustomSizedTexture((float)this.field_73746_c, (float)this.field_73743_d, 0, 175, this.field_73747_a, this.field_73745_b, 512.0F, 512.0F, false);
         }
      } else {
         ModernGui.drawModalRectWithCustomSizedTexture((float)this.field_73746_c, (float)this.field_73743_d, 0, 190, this.field_73747_a, this.field_73745_b, 512.0F, 512.0F, false);
      }

      this.func_73732_a(mc.field_71466_p, this.field_73744_e, this.field_73746_c + this.field_73747_a / 2, this.field_73743_d + 4, 16777215);
   }
}
