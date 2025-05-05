package net.ilexiconn.nationsgui.forge.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.halalaboos.cfont.CFontRenderer;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;

@SideOnly(Side.CLIENT)
public class CustomTextAreaGUI extends GuiTextField {

   private final int width;
   private final int height;
   private final String customFont;
   private final int fontSize;
   private final CFontRenderer fontRenderer;
   public int posX;
   public int posY;
   private int cursorCounter;


   public CustomTextAreaGUI(int x, int y, int width, String customFont, int fontSize, int height) {
      super(Minecraft.func_71410_x().field_71466_p, x, y, width, height);
      this.posX = x;
      this.posY = y;
      this.width = width;
      this.height = height;
      this.customFont = customFont;
      this.fontSize = fontSize;
      this.fontRenderer = ModernGui.getCustomFont(customFont, Integer.valueOf(fontSize));
   }

   public void func_73780_a() {
      ++this.cursorCounter;
   }

   public void func_73793_a(int par1, int par2, int par3) {
      boolean flag = par1 >= this.posX && par1 < this.posX + this.width && par2 >= this.posY && par2 < this.posY + this.height;
      this.func_73796_b(flag);
      if(this.func_73806_l() && par3 == 0) {
         int l = par1 - this.posX;
         if(this.func_73783_i()) {
            l -= 4;
         }

         l += (par2 - this.posY - 4) / ((this.fontRenderer.getHeight() + 4) / 2) * this.width;
         this.func_73791_e(this.trimStringToWidth(this.func_73781_b(), l).length());
      }

   }

   public String trimStringToWidth(String str, int width) {
      String line = "";
      char[] var4 = this.func_73781_b().toCharArray();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         char character = var4[var6];
         line = line + (char)character;
         if(this.fontRenderer.getStringWidth(line) / 2.0F >= (float)width) {
            return line;
         }
      }

      return line;
   }

   public void func_73795_f() {
      int x = 0;
      String line = "";
      char[] flag = this.func_73781_b().toCharArray();
      int i = flag.length;

      for(int var5 = 0; var5 < i; ++var5) {
         char character = flag[var5];
         if(character != 13 && character != 10) {
            if(this.fontRenderer.getStringWidth(line + (char)character) / 2.0F > (float)this.width) {
               ModernGui.drawScaledStringCustomFont(line, (float)(this.posX + 4), (float)(this.posY + 4 + x * (this.fontRenderer.getHeight() + 4) / 2), 14737632, 0.5F, "left", false, this.customFont, this.fontSize);
               line = "";
               ++x;
            }

            line = line + (char)character;
         } else {
            ModernGui.drawScaledStringCustomFont(line, (float)(this.posX + 4), (float)(this.posY + 4 + x * (this.fontRenderer.getHeight() + 4) / 2), 14737632, 0.5F, "left", false, this.customFont, this.fontSize);
            line = "";
            ++x;
         }
      }

      ModernGui.drawScaledStringCustomFont(line, (float)(this.posX + 4), (float)(this.posY + 4 + x * (this.fontRenderer.getHeight() + 4) / 2), 14737632, 0.5F, "left", false, this.customFont, this.fontSize);
      boolean var9 = this.func_73806_l() && this.cursorCounter / 6 % 2 == 0;
      i = 0;
      x = 0;
      line = "";
      if(var9 && 0 == this.func_73781_b().length()) {
         ModernGui.drawScaledStringCustomFont("_", (float)(this.posX + 3 + (int)this.fontRenderer.getStringWidth(line) / 2), (float)(this.posY + 4 + x * (this.fontRenderer.getHeight() + 4) / 2), 14737632, 0.5F, "left", false, this.customFont, this.fontSize);
      }

      char[] var10 = this.func_73781_b().toCharArray();
      int var11 = var10.length;

      for(int var7 = 0; var7 < var11; ++var7) {
         char character1 = var10[var7];
         ++i;
         if(character1 != 13 && character1 != 10) {
            if(this.fontRenderer.getStringWidth(line + character1) / 2.0F > (float)this.width) {
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
            ModernGui.drawScaledStringCustomFont("_", (float)(this.posX + 3 + (int)this.fontRenderer.getStringWidth(line) / 2), (float)(this.posY + 4 + x * (this.fontRenderer.getHeight() + 4) / 2), 14737632, 0.5F, "left", false, this.customFont, this.fontSize);
         }
      }

   }
}
