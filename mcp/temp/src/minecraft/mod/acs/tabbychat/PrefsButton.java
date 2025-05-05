package acs.tabbychat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;

public class PrefsButton extends GuiButton {

   protected int bgcolor = -587202560;
   protected boolean hasControlCodes = false;
   protected String type;


   public PrefsButton() {
      super(9999, 0, 0, 1, 1, "");
   }

   public PrefsButton(int _id, int _x, int _y, int _w, int _h, String _title) {
      super(_id, _x, _y, _w, _h, _title);
   }

   public PrefsButton(int _id, int _x, int _y, int _w, int _h, String _title, int _bgcolor) {
      super(_id, _x, _y, _w, _h, _title);
      this.bgcolor = _bgcolor;
   }

   protected void title(String newtitle) {
      this.field_73744_e = newtitle;
   }

   protected String title() {
      return this.field_73744_e;
   }

   protected int width() {
      return this.field_73747_a;
   }

   protected void width(int _w) {
      this.field_73747_a = _w;
   }

   protected int height() {
      return this.field_73745_b;
   }

   protected void height(int _h) {
      this.field_73745_b = _h;
   }

   protected int adjustWidthForControlCodes() {
      String cleaned = this.field_73744_e.replaceAll("(?i)\u00a7[0-9A-FK-OR]", "");
      boolean bold = this.field_73744_e.replaceAll("(?i)\u00a7L", "").length() != this.field_73744_e.length();
      int badWidth = Minecraft.func_71410_x().field_71466_p.func_78256_a(this.field_73744_e);
      int goodWidth = Minecraft.func_71410_x().field_71466_p.func_78256_a(cleaned);
      if(bold) {
         goodWidth += cleaned.length();
      }

      return badWidth > goodWidth?badWidth - goodWidth:0;
   }

   public void func_73737_a(Minecraft mc, int cursorX, int cursorY) {
      if(this.field_73748_h) {
         FontRenderer fr = mc.field_71466_p;
         func_73734_a(this.field_73746_c, this.field_73743_d, this.field_73746_c + this.field_73747_a, this.field_73743_d + this.field_73745_b, this.bgcolor);
         boolean hovered = cursorX >= this.field_73746_c && cursorY >= this.field_73743_d && cursorX < this.field_73746_c + this.field_73747_a && cursorY < this.field_73743_d + this.field_73745_b;
         int var7 = 10526880;
         if(!this.field_73742_g) {
            var7 = -6250336;
         } else if(hovered) {
            var7 = 16777120;
         }

         if(this.hasControlCodes) {
            int offset = this.adjustWidthForControlCodes();
            this.func_73732_a(fr, this.field_73744_e, this.field_73746_c + (this.field_73747_a + offset) / 2, this.field_73743_d + (this.field_73745_b - 8) / 2, var7);
         } else {
            this.func_73732_a(fr, this.field_73744_e, this.field_73746_c + this.field_73747_a / 2, this.field_73743_d + (this.field_73745_b - 8) / 2, var7);
         }
      }

   }
}
