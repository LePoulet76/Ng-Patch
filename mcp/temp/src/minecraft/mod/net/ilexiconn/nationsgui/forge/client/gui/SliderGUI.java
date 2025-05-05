package net.ilexiconn.nationsgui.forge.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.gui.SliderGUI$ISliderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class SliderGUI extends GuiButton {

   public float sliderValue = 1.0F;
   public String text;
   public SliderGUI$ISliderCallback callback;
   public boolean dragging;


   public SliderGUI(int id, int x, int y, String text, SliderGUI$ISliderCallback callback) {
      super(id, x, y, 150, 20, text + ": " + (int)(100.0F / callback.getMaxValue() * callback.getCurrentValue()) + "%");
      this.text = text;
      this.sliderValue = callback.getCurrentValue() / callback.getMaxValue();
      this.callback = callback;
   }

   protected int func_73738_a(boolean hover) {
      return 0;
   }

   protected void func_73739_b(Minecraft mc, int mouseX, int mouseY) {
      if(this.field_73748_h) {
         if(this.dragging) {
            this.sliderValue = (float)(mouseX - (this.field_73746_c + 4)) / (float)(this.field_73747_a - 8);
            if(this.sliderValue < 0.0F) {
               this.sliderValue = 0.0F;
            }

            if(this.sliderValue > 1.0F) {
               this.sliderValue = 1.0F;
            }

            this.callback.call(this.sliderValue * this.callback.getMaxValue());
            this.field_73744_e = this.text + ": " + (int)(100.0F / this.callback.getMaxValue() * this.callback.getCurrentValue()) + "%";
         }

         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.func_73729_b(this.field_73746_c + (int)(this.sliderValue * (float)(this.field_73747_a - 8)), this.field_73743_d, 0, 66, 4, 20);
         this.func_73729_b(this.field_73746_c + (int)(this.sliderValue * (float)(this.field_73747_a - 8)) + 4, this.field_73743_d, 196, 66, 4, 20);
      }

   }

   public boolean func_73736_c(Minecraft mc, int mouseX, int mouseY) {
      if(super.func_73736_c(mc, mouseX, mouseY)) {
         this.sliderValue = (float)(mouseX - (this.field_73746_c + 4)) / (float)(this.field_73747_a - 8);
         if(this.sliderValue < 0.0F) {
            this.sliderValue = 0.0F;
         }

         if(this.sliderValue > 1.0F) {
            this.sliderValue = 1.0F;
         }

         this.callback.call(this.sliderValue * this.callback.getMaxValue());
         this.field_73744_e = this.text + ": " + (int)(100.0F / this.callback.getMaxValue() * this.callback.getCurrentValue()) + "%";
         this.dragging = true;
         return true;
      } else {
         return false;
      }
   }

   public void func_73740_a(int mouseX, int mouseY) {
      this.dragging = false;
   }
}
