package net.ilexiconn.nationsgui.forge.client.gui.auth.component;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiTextField;

@SideOnly(Side.CLIENT)
public class PasswordFieldGUI extends GuiTextField {

   private int posX;
   private int posY;
   private int width;
   private int height;
   private int cursorCounter;
   private FontRenderer fontRenderer;


   public PasswordFieldGUI(int x, int y, int width, int height) {
      super(Minecraft.func_71410_x().field_71466_p, x, y, width, height);
      this.posX = x;
      this.posY = y;
      this.width = width;
      this.height = height;
      this.fontRenderer = Minecraft.func_71410_x().field_71466_p;
      this.func_73804_f(38);
   }

   public boolean func_73802_a(char character, int key) {
      return character != 32 && character != 10 && super.func_73802_a(character, key);
   }

   public void func_73780_a() {
      ++this.cursorCounter;
   }

   public void func_73795_f() {
      Gui.func_73734_a(this.posX - 1, this.posY - 1, this.posX + this.width + 1, this.posY + this.height + 1, -6250336);
      Gui.func_73734_a(this.posX, this.posY, this.posX + this.width, this.posY + this.height, -16777216);
      int x = 0;
      String line = "";
      String text = this.func_73781_b().replaceAll(".", "*");
      char[] flag = text.toCharArray();
      int i = flag.length;

      for(int var6 = 0; var6 < i; ++var6) {
         char character = flag[var6];
         if(character != 13 && character != 10) {
            if(this.fontRenderer.func_78256_a(line + (char)character) > 191) {
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
      boolean var10 = this.func_73806_l() && this.cursorCounter / 6 % 2 == 0;
      i = 0;
      x = 0;
      line = "";
      if(var10 && 0 == text.length()) {
         this.fontRenderer.func_78276_b("_", this.posX + 3 + this.fontRenderer.func_78256_a(line), this.posY + 4 + x * this.fontRenderer.field_78288_b, 14737632);
      }

      char[] var11 = text.toCharArray();
      int var12 = var11.length;

      for(int var8 = 0; var8 < var12; ++var8) {
         char character1 = var11[var8];
         ++i;
         if(character1 != 13 && character1 != 10) {
            if(this.fontRenderer.func_78256_a(line + character1) > 191) {
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

         if(var10 && i == text.length()) {
            this.fontRenderer.func_78276_b("_", this.posX + 3 + this.fontRenderer.func_78256_a(line), this.posY + 4 + x * this.fontRenderer.field_78288_b, 14737632);
         }
      }

   }
}
