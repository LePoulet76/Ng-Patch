package net.ilexiconn.nationsgui.forge.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;

@SideOnly(Side.CLIENT)
public class TextAreaGUI extends GuiTextField {

   private final int posX;
   private final int posY;
   private final int width;
   private final FontRenderer fontRenderer;
   private int cursorCounter;


   public TextAreaGUI(int x, int y, int width) {
      super(Minecraft.func_71410_x().field_71466_p, x, y, width, 73);
      this.posX = x;
      this.posY = y;
      this.width = width;
      this.fontRenderer = Minecraft.func_71410_x().field_71466_p;
   }

   public void func_73780_a() {
      ++this.cursorCounter;
   }

   public void func_73795_f() {
      int x = 0;
      String line = "";
      char[] flag = this.func_73781_b().toCharArray();
      int i = flag.length;

      for(int var5 = 0; var5 < i; ++var5) {
         char character = flag[var5];
         if(character != 13 && character != 10) {
            if(this.fontRenderer.func_78256_a(line + (char)character) > this.width) {
               this.func_73731_b(this.fontRenderer, line, this.posX + 4, this.posY + 4 + x * this.fontRenderer.field_78288_b, 14737632);
               line = "";
               ++x;
            }

            line = line + (char)character;
         } else {
            this.func_73731_b(this.fontRenderer, line, this.posX + 4, this.posY + 4 + x * this.fontRenderer.field_78288_b, 14737632);
            line = "";
            ++x;
         }
      }

      this.func_73731_b(this.fontRenderer, line, this.posX + 4, this.posY + 4 + x * this.fontRenderer.field_78288_b, 14737632);
      boolean var9 = this.func_73806_l() && this.cursorCounter / 6 % 2 == 0;
      i = 0;
      x = 0;
      line = "";
      if(var9 && 0 == this.func_73781_b().length()) {
         this.fontRenderer.func_78276_b("_", this.posX + 3 + this.fontRenderer.func_78256_a(line), this.posY + 4 + x * this.fontRenderer.field_78288_b, 14737632);
      }

      char[] var10 = this.func_73781_b().toCharArray();
      int var11 = var10.length;

      for(int var7 = 0; var7 < var11; ++var7) {
         char character1 = var10[var7];
         ++i;
         if(character1 != 13 && character1 != 10) {
            if(this.fontRenderer.func_78256_a(line + character1) > this.width) {
               line = "";
               ++x;
               line = line + character1;
            } else {
               line = line + character1;
            }
         } else {
            line = "";
            ++x;
         }

         if(var9 && i == this.func_73781_b().length()) {
            this.fontRenderer.func_78276_b("_", this.posX + 3 + this.fontRenderer.func_78256_a(line), this.posY + 4 + x * this.fontRenderer.field_78288_b, 14737632);
         }
      }

   }
}
