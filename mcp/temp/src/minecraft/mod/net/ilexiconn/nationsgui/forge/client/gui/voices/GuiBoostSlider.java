package net.ilexiconn.nationsgui.forge.client.gui.voices;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiBoostSlider extends GuiButton {

   public float sliderValue = 1.0F;
   public boolean dragging;
   public String idValue;


   public GuiBoostSlider(int par1, int par2, int par3, String idValue, String par5Str, float par6) {
      super(par1, par2, par3, 150, 20, par5Str);
      this.idValue = idValue;
      this.sliderValue = par6;
   }

   protected int func_73738_a(boolean par1) {
      return 0;
   }

   protected void func_73739_b(Minecraft par1Minecraft, int par2, int par3) {
      if(this.field_73748_h) {
         if(this.dragging) {
            this.sliderValue = (float)(par2 - (this.field_73746_c + 4)) / (float)(this.field_73747_a - 8);
            if(this.sliderValue < 0.0F) {
               this.sliderValue = 0.0F;
            }

            if(this.sliderValue > 1.0F) {
               this.sliderValue = 1.0F;
            }

            this.field_73744_e = this.idValue;
         }

         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.func_73729_b(this.field_73746_c + (int)(this.sliderValue * (float)(this.field_73747_a - 8)), this.field_73743_d, 0, 66, 4, 20);
         this.func_73729_b(this.field_73746_c + (int)(this.sliderValue * (float)(this.field_73747_a - 8)) + 4, this.field_73743_d, 196, 66, 4, 20);
      }

   }

   public boolean func_73736_c(Minecraft par1Minecraft, int par2, int par3) {
      if(super.func_73736_c(par1Minecraft, par2, par3)) {
         this.sliderValue = (float)(par2 - (this.field_73746_c + 4)) / (float)(this.field_73747_a - 8);
         if(this.sliderValue < 0.0F) {
            this.sliderValue = 0.0F;
         }

         if(this.sliderValue > 1.0F) {
            this.sliderValue = 1.0F;
         }

         this.dragging = true;
         return true;
      } else {
         return false;
      }
   }

   public void func_73740_a(int par1, int par2) {
      this.dragging = false;
   }

   public void setDisplayString(String display) {
      this.idValue = display;
      this.field_73744_e = display;
   }
}
