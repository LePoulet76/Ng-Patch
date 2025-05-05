package acs.tabbychat;

import acs.tabbychat.TCSetting;
import net.minecraft.client.Minecraft;

public class TCSettingBool extends TCSetting {

   protected int buttonOnColor;
   protected int buttonOffColor;
   protected Boolean value;
   protected Boolean tempValue;
   private static Minecraft mc;


   public TCSettingBool(String theLabel, int theID) {
      this(Boolean.valueOf(false), theLabel, theID);
   }

   public TCSettingBool(Boolean theSetting, String theLabel, int theID) {
      super(theID, 0, 0, "");
      this.buttonOnColor = -1146755100;
      this.buttonOffColor = -1728053248;
      this.type = "bool";
      mc = Minecraft.func_71410_x();
      this.value = theSetting;
      this.tempValue = new Boolean(this.value.booleanValue());
      this.description = theLabel;
      this.labelX = 0;
      this.field_73747_a = 9;
      this.field_73745_b = 9;
   }

   public void setValue(Boolean theVal) {
      this.value = theVal;
   }

   public void setTempValue(Boolean theVal) {
      this.tempValue = theVal;
   }

   public Boolean getValue() {
      return Boolean.valueOf(this.value.booleanValue());
   }

   public Boolean getTempValue() {
      return Boolean.valueOf(this.tempValue.booleanValue());
   }

   public void save() {
      this.value = Boolean.valueOf(this.tempValue.booleanValue());
   }

   public void reset() {
      this.tempValue = Boolean.valueOf(this.value.booleanValue());
   }

   public void toggle() {
      this.tempValue = Boolean.valueOf(!this.tempValue.booleanValue());
   }

   public void actionPerformed() {
      this.toggle();
   }

   public void func_73737_a(Minecraft par1, int cursorX, int cursorY) {
      int centerX = this.field_73746_c + this.field_73747_a / 2;
      int centerY = this.field_73743_d + this.field_73745_b / 2;
      byte tmpWidth = 9;
      byte tmpHeight = 9;
      int tmpX = centerX - 4;
      int tmpY = centerY - 4;
      int fgcolor = -1717526368;
      if(!this.field_73742_g) {
         fgcolor = 1721802912;
      } else if(this.hovered(cursorX, cursorY).booleanValue()) {
         fgcolor = -1711276128;
      }

      int labelColor = this.field_73742_g?16777215:6710886;
      func_73734_a(tmpX + 1, tmpY, tmpX + tmpWidth - 1, tmpY + 1, fgcolor);
      func_73734_a(tmpX + 1, tmpY + tmpHeight - 1, tmpX + tmpWidth - 1, tmpY + tmpHeight, fgcolor);
      func_73734_a(tmpX, tmpY + 1, tmpX + 1, tmpY + tmpHeight - 1, fgcolor);
      func_73734_a(tmpX + tmpWidth - 1, tmpY + 1, tmpX + tmpWidth, tmpY + tmpHeight - 1, fgcolor);
      func_73734_a(tmpX + 1, tmpY + 1, tmpX + tmpWidth - 1, tmpY + tmpHeight - 1, -16777216);
      if(this.tempValue.booleanValue()) {
         func_73734_a(centerX - 2, centerY, centerX - 1, centerY + 1, this.buttonOnColor);
         func_73734_a(centerX - 1, centerY + 1, centerX, centerY + 2, this.buttonOnColor);
         func_73734_a(centerX, centerY + 2, centerX + 1, centerY + 3, this.buttonOnColor);
         func_73734_a(centerX + 1, centerY + 2, centerX + 2, centerY, this.buttonOnColor);
         func_73734_a(centerX + 2, centerY, centerX + 3, centerY - 2, this.buttonOnColor);
         func_73734_a(centerX + 3, centerY - 2, centerX + 4, centerY - 4, this.buttonOnColor);
      }

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
