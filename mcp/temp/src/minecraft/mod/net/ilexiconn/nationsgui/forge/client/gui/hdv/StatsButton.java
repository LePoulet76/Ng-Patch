package net.ilexiconn.nationsgui.forge.client.gui.hdv;

import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class StatsButton extends GuiButton {

   private static final ResourceLocation BACKGROUND = new ResourceLocation("nationsgui", "textures/gui/market.png");


   public StatsButton(int id, int posX, int posY) {
      super(id, posX, posY, 71, 14, I18n.func_135053_a("hdv.stats"));
   }

   public void func_73737_a(Minecraft par1Minecraft, int par2, int par3) {
      if(this.field_73748_h) {
         par1Minecraft.func_110434_K().func_110577_a(BACKGROUND);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.field_82253_i = par2 >= this.field_73746_c && par3 >= this.field_73743_d && par2 < this.field_73746_c + this.field_73747_a && par3 < this.field_73743_d + this.field_73745_b;
         if(this.field_82253_i) {
            GL11.glColor3f(0.75F, 0.75F, 0.75F);
         } else {
            GL11.glColor3f(1.0F, 1.0F, 1.0F);
         }

         ModernGui.drawModalRectWithCustomSizedTexture((float)this.field_73746_c, (float)this.field_73743_d, 36, 373, 71, 14, 372.0F, 400.0F, false);
         ModernGui.drawScaledString(this.field_73744_e, this.field_73746_c + 18, this.field_73743_d + 4, 16777215, 1.0F, false, false);
         this.func_73739_b(par1Minecraft, par2, par3);
      }

   }

}
