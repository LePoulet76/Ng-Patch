package acs.tabbychat;

import acs.tabbychat.TCSetting;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class TCSettingSlider extends TCSetting {

   protected Float value;
   protected Float tempValue;
   protected float minValue;
   protected float maxValue;
   protected float sliderValue;
   private int sliderX;
   protected int buttonOnColor;
   protected int buttonOffColor;
   public String units;
   private boolean dragging;


   public TCSettingSlider(String theLabel, int theID) {
      this(Float.valueOf(0.0F), theLabel, theID);
   }

   public TCSettingSlider(Float theSetting, String theLabel, int theID) {
      super(theID, 0, 0, "");
      this.buttonOnColor = -1146755100;
      this.buttonOffColor = 1157627903;
      this.units = "%";
      this.dragging = false;
      this.type = "slider";
      mc = Minecraft.func_71410_x();
      this.value = theSetting;
      this.description = theLabel;
      this.labelX = 0;
      this.field_73747_a = 100;
      this.field_73745_b = 11;
      this.tempValue = this.value;
      this.sliderValue = (this.tempValue.floatValue() - this.minValue) / (this.maxValue - this.minValue);
   }

   public TCSettingSlider(Float theSetting, String theLabel, int theID, float minVal, float maxVal) {
      this(theSetting, theLabel, theID);
      this.minValue = minVal;
      this.maxValue = maxVal;
      this.sliderValue = (this.tempValue.floatValue() - this.minValue) / (this.maxValue - this.minValue);
   }

   public void setButtonDims(int wide, int tall) {
      this.field_73747_a = wide;
      this.field_73745_b = tall;
   }

   public void setValue(Float theVal) {
      this.value = theVal;
   }

   public void setRange(Float theMin, Float theMax) {
      this.minValue = theMin.floatValue();
      this.maxValue = theMax.floatValue();
      this.sliderValue = (this.tempValue.floatValue() - this.minValue) / (this.maxValue - this.minValue);
   }

   public void setTempValue(Float theVal) {
      this.tempValue = theVal;
      this.sliderValue = (this.tempValue.floatValue() - this.minValue) / (this.maxValue - this.minValue);
   }

   public Float getValue() {
      return this.value;
   }

   public Float getTempValue() {
      this.tempValue = Float.valueOf(this.sliderValue * (this.maxValue - this.minValue) + this.minValue);
      return this.tempValue;
   }

   public void save() {
      this.tempValue = Float.valueOf(this.sliderValue * (this.maxValue - this.minValue) + this.minValue);
      this.value = this.tempValue;
   }

   public void reset() {
      this.tempValue = this.value;
      this.sliderValue = (this.tempValue.floatValue() - this.minValue) / (this.maxValue - this.minValue);
   }

   public void handleMouseInput() {
      if(Minecraft.func_71410_x().field_71462_r != null) {
         int mX = Mouse.getEventX() * Minecraft.func_71410_x().field_71462_r.field_73880_f / Minecraft.func_71410_x().field_71443_c;
         int mY = Minecraft.func_71410_x().field_71462_r.field_73881_g - Mouse.getEventY() * Minecraft.func_71410_x().field_71462_r.field_73881_g / Minecraft.func_71410_x().field_71440_d - 1;
         if(this.hovered(mX, mY).booleanValue()) {
            int var1 = Mouse.getEventDWheel();
            if(var1 != 0) {
               if(var1 > 1) {
                  var1 = 3;
               }

               if(var1 < -1) {
                  var1 = -3;
               }

               if(Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54)) {
                  var1 *= -7;
               }
            }

            this.sliderValue += (float)var1 / 100.0F;
            if(this.sliderValue < 0.0F) {
               this.sliderValue = 0.0F;
            } else if(this.sliderValue > 1.0F) {
               this.sliderValue = 1.0F;
            }

            this.tempValue = Float.valueOf(this.sliderValue * (this.maxValue - this.minValue) + this.minValue);
         }
      }
   }

   public void mouseClicked(int par1, int par2, int par3) {
      if(par3 == 0 && this.hovered(par1, par2).booleanValue() && this.field_73742_g) {
         this.sliderX = par1 - 1;
         this.sliderValue = (float)(this.sliderX - (this.field_73746_c + 1)) / (float)(this.field_73747_a - 5);
         if(this.sliderValue < 0.0F) {
            this.sliderValue = 0.0F;
         } else if(this.sliderValue > 1.0F) {
            this.sliderValue = 1.0F;
         }

         if(!this.dragging) {
            this.dragging = true;
         }
      }

   }

   public void func_73740_a(int par1, int par2) {
      this.dragging = false;
   }

   public void func_73737_a(Minecraft par1, int cursorX, int cursorY) {
      int fgcolor = -1717526368;
      if(!this.field_73742_g) {
         fgcolor = 1721802912;
      } else if(this.hovered(cursorX, cursorY).booleanValue()) {
         fgcolor = -1711276128;
         if(this.dragging) {
            this.sliderX = cursorX - 1;
            this.sliderValue = (float)(this.sliderX - (this.field_73746_c + 1)) / (float)(this.field_73747_a - 5);
            if(this.sliderValue < 0.0F) {
               this.sliderValue = 0.0F;
            } else if(this.sliderValue > 1.0F) {
               this.sliderValue = 1.0F;
            }
         }
      }

      int labelColor = this.field_73742_g?16777215:6710886;
      int buttonColor = this.field_73742_g?this.buttonOnColor:this.buttonOffColor;
      func_73734_a(this.field_73746_c, this.field_73743_d + 1, this.field_73746_c + 1, this.field_73743_d + this.field_73745_b - 1, fgcolor);
      func_73734_a(this.field_73746_c + 1, this.field_73743_d, this.field_73746_c + this.field_73747_a - 1, this.field_73743_d + 1, fgcolor);
      func_73734_a(this.field_73746_c + 1, this.field_73743_d + this.field_73745_b - 1, this.field_73746_c + this.field_73747_a - 1, this.field_73743_d + this.field_73745_b, fgcolor);
      func_73734_a(this.field_73746_c + this.field_73747_a - 1, this.field_73743_d + 1, this.field_73746_c + this.field_73747_a, this.field_73743_d + this.field_73745_b - 1, fgcolor);
      func_73734_a(this.field_73746_c + 1, this.field_73743_d + 1, this.field_73746_c + this.field_73747_a - 1, this.field_73743_d + this.field_73745_b - 1, -16777216);
      this.sliderX = Math.round(this.sliderValue * (float)(this.field_73747_a - 5)) + this.field_73746_c + 1;
      func_73734_a(this.sliderX, this.field_73743_d + 1, this.sliderX + 1, this.field_73743_d + 2, buttonColor & -1996488705);
      func_73734_a(this.sliderX + 1, this.field_73743_d + 1, this.sliderX + 2, this.field_73743_d + 2, buttonColor);
      func_73734_a(this.sliderX + 2, this.field_73743_d + 1, this.sliderX + 3, this.field_73743_d + 2, buttonColor & -1996488705);
      func_73734_a(this.sliderX, this.field_73743_d + 2, this.sliderX + 1, this.field_73743_d + this.field_73745_b - 2, buttonColor);
      func_73734_a(this.sliderX + 1, this.field_73743_d + 2, this.sliderX + 2, this.field_73743_d + this.field_73745_b - 2, buttonColor & -1996488705);
      func_73734_a(this.sliderX + 2, this.field_73743_d + 2, this.sliderX + 3, this.field_73743_d + this.field_73745_b - 2, buttonColor);
      func_73734_a(this.sliderX, this.field_73743_d + this.field_73745_b - 2, this.sliderX + 1, this.field_73743_d + this.field_73745_b - 1, buttonColor & -1996488705);
      func_73734_a(this.sliderX + 1, this.field_73743_d + this.field_73745_b - 2, this.sliderX + 2, this.field_73743_d + this.field_73745_b - 1, buttonColor);
      func_73734_a(this.sliderX + 2, this.field_73743_d + this.field_73745_b - 2, this.sliderX + 3, this.field_73743_d + this.field_73745_b - 1, buttonColor & -1996488705);
      boolean valCenter = false;
      int valCenter1;
      if(this.sliderValue < 0.5F) {
         valCenter1 = Math.round(0.7F * (float)this.field_73747_a);
      } else {
         valCenter1 = Math.round(0.2F * (float)this.field_73747_a);
      }

      String valLabel = Integer.toString(Math.round(this.sliderValue * (this.maxValue - this.minValue) + this.minValue)) + this.units;
      int var10003 = valCenter1 + this.field_73746_c;
      int var10004 = this.field_73743_d + 2;
      this.func_73732_a(Minecraft.func_71410_x().field_71466_p, valLabel, var10003, var10004, buttonColor);
      this.func_73732_a(Minecraft.func_71410_x().field_71466_p, this.description, this.labelX + Minecraft.func_71410_x().field_71466_p.func_78256_a(this.description) / 2, this.field_73743_d + (this.field_73745_b - 6) / 2, labelColor);
   }

   // $FF: synthetic method
   // $FF: bridge method
   public Object getTempValue() {
      return this.getTempValue();
   }

   // $FF: synthetic method
   // $FF: bridge method
   public Object getValue() {
      return this.getValue();
   }
}
