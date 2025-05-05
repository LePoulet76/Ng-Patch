package acs.tabbychat;

import acs.tabbychat.TCSetting;
import net.minecraft.client.Minecraft;

public class TCSettingEnum extends TCSetting {

   protected Enum value;
   protected Enum tempValue;


   public TCSettingEnum(String theLabel, int theID) {
      super(theLabel, theID);
      this.value = null;
      this.tempValue = null;
   }

   public TCSettingEnum(Enum theVar, String theLabel, int theID) {
      super(theLabel, theID);
      mc = Minecraft.func_71410_x();
      this.value = theVar;
      this.tempValue = theVar;
      this.description = theLabel;
      this.type = "enum";
      this.labelX = 0;
      this.field_73747_a = 30;
      this.field_73745_b = 11;
   }

   public void next() {
      Enum[] E = (Enum[])this.tempValue.getClass().getEnumConstants();
      Enum tmp;
      if(this.tempValue.ordinal() == E.length - 1) {
         tmp = Enum.valueOf(this.tempValue.getClass(), E[0].name());
      } else {
         tmp = Enum.valueOf(this.tempValue.getClass(), E[this.tempValue.ordinal() + 1].name());
      }

      this.tempValue = tmp;
   }

   public void previous() {
      Enum[] E = (Enum[])this.tempValue.getClass().getEnumConstants();
      if(this.tempValue.ordinal() == 0) {
         this.tempValue = Enum.valueOf(this.tempValue.getClass(), E[E.length - 1].name());
      } else {
         this.tempValue = Enum.valueOf(this.tempValue.getClass(), E[this.tempValue.ordinal() - 1].name());
      }

   }

   public void save() {
      this.value = Enum.valueOf(this.tempValue.getClass(), this.tempValue.name());
   }

   public void reset() {
      this.tempValue = Enum.valueOf(this.value.getClass(), this.value.name());
   }

   public void setValue(Enum theVal) {
      this.value = Enum.valueOf(theVal.getClass(), theVal.name());
   }

   public void setTempValue(Enum theVal) {
      this.tempValue = Enum.valueOf(theVal.getClass(), theVal.name());
   }

   public Enum getValue() {
      return this.value;
   }

   public Enum getTempValue() {
      return this.tempValue;
   }

   public void mouseClicked(int par1, int par2, int par3) {
      if(this.hovered(par1, par2).booleanValue() && this.field_73742_g) {
         if(par3 == 1) {
            this.previous();
         } else if(par3 == 0) {
            this.next();
         }
      }

   }

   public void actionPerformed() {}

   public void func_73737_a(Minecraft par1, int cursorX, int cursorY) {
      int centerX = this.field_73746_c + this.field_73747_a / 2;
      int var10000 = this.field_73743_d + this.field_73745_b / 2;
      int fgcolor = -1717526368;
      if(!this.field_73742_g) {
         fgcolor = 1721802912;
      } else if(this.hovered(cursorX, cursorY).booleanValue()) {
         fgcolor = -1711276128;
      }

      int labelColor = this.field_73742_g?16777215:6710886;
      func_73734_a(this.field_73746_c + 1, this.field_73743_d, this.field_73746_c + this.field_73747_a - 1, this.field_73743_d + 1, fgcolor);
      func_73734_a(this.field_73746_c + 1, this.field_73743_d + this.field_73745_b - 1, this.field_73746_c + this.field_73747_a - 1, this.field_73743_d + this.field_73745_b, fgcolor);
      func_73734_a(this.field_73746_c, this.field_73743_d + 1, this.field_73746_c + 1, this.field_73743_d + this.field_73745_b - 1, fgcolor);
      func_73734_a(this.field_73746_c + this.field_73747_a - 1, this.field_73743_d + 1, this.field_73746_c + this.field_73747_a, this.field_73743_d + this.field_73745_b - 1, fgcolor);
      func_73734_a(this.field_73746_c + 1, this.field_73743_d + 1, this.field_73746_c + this.field_73747_a - 1, this.field_73743_d + this.field_73745_b - 1, -16777216);
      this.func_73732_a(Minecraft.func_71410_x().field_71466_p, this.tempValue.toString(), centerX, this.field_73743_d + 2, labelColor);
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
