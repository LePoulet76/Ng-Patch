package net.ilexiconn.nationsgui.forge.client.gui.main.component;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.halalaboos.cfont.CFontRenderer;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;

@SideOnly(Side.CLIENT)
public class CustomInputFieldGUI extends GuiTextField {

   private final String customFont;
   private final int fontSize;
   private int width;
   private int height;
   private int cursorCounter;
   public int posX;
   public int posY;
   private CFontRenderer fontRenderer;


   public CustomInputFieldGUI(int x, int y, int width, int height, String customFont, int fontSize) {
      super(Minecraft.func_71410_x().field_71466_p, x, y, width, height);
      this.posX = x;
      this.posY = y;
      this.width = width;
      this.height = height;
      this.customFont = customFont;
      this.fontSize = fontSize;
      this.fontRenderer = ModernGui.getCustomFont(customFont, Integer.valueOf(fontSize));
   }

   public boolean func_73802_a(char par1, int par2) {
      return super.func_73802_a(par1, par2);
   }

   public void func_73780_a() {
      ++this.cursorCounter;
   }

   public void func_73795_f() {
      int x = 0;
      String line = "";
      String text = this.func_73781_b();
      char[] flag = text.toCharArray();
      int i = flag.length;

      for(int var6 = 0; var6 < i; ++var6) {
         char character = flag[var6];
         if(character != 13 && character != 10) {
            line = line + (char)character;
         } else {
            ModernGui.drawScaledStringCustomFont(line, (float)(this.posX + 4), (float)(this.posY + 4), 14737632, 0.5F, "left", false, this.customFont, this.fontSize);
            line = "";
            ++x;
         }
      }

      ModernGui.drawScaledStringCustomFont(line, (float)(this.posX + 4), (float)(this.posY + 4), 14737632, 0.5F, "left", false, this.customFont, this.fontSize);
      boolean var10 = this.func_73806_l() && this.cursorCounter / 6 % 2 == 0;
      i = 0;
      x = 0;
      line = "";
      if(var10 && 0 == text.length()) {
         ModernGui.drawScaledStringCustomFont("_", (float)(this.posX + 3 + (int)this.fontRenderer.getStringWidth(line) / 2), (float)(this.posY + this.fontRenderer.getHeight() / 2), 14737632, 0.5F, "left", false, this.customFont, this.fontSize);
      }

      char[] var11 = text.toCharArray();
      int var12 = var11.length;

      for(int var8 = 0; var8 < var12; ++var8) {
         char character1 = var11[var8];
         ++i;
         if(character1 != 13 && character1 != 10) {
            line = line + character1;
         } else {
            line = "";
            ++x;
         }

         if(var10 && i == text.length()) {
            ModernGui.drawScaledStringCustomFont("_", (float)(this.posX + 3 + (int)this.fontRenderer.getStringWidth(line) / 2), (float)(this.posY + this.fontRenderer.getHeight() / 2), 14737632, 0.5F, "left", false, this.customFont, this.fontSize);
         }
      }

   }

   public void func_73793_a(int par1, int par2, int par3) {
      boolean flag = par1 >= this.posX && par1 < this.posX + this.width && par2 >= this.posY && par2 < this.posY + this.height;
      this.func_73796_b(flag);
      if(this.func_73781_b().equals("0") && this.func_73806_l()) {
         this.func_73782_a("");
      }

      if(this.func_73806_l() && par3 == 0) {
         int l = par1 - this.posX;
         if(this.func_73783_i()) {
            l -= 4;
         }

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
}
