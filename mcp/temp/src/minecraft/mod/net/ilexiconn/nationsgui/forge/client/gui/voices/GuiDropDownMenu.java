package net.ilexiconn.nationsgui.forge.client.gui.voices;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

public class GuiDropDownMenu extends GuiButton {

   String[] array;
   boolean[] mouseOn;
   private int prevHeight;
   private int amountOfItems = 1;
   public boolean dropDownMenu = false;
   public int selectedInteger;


   public GuiDropDownMenu(int par1, int par2, int par3, String par4Str, String[] array) {
      super(par1, par2, par3, par4Str);
      this.prevHeight = this.field_73745_b;
      this.array = array;
      this.amountOfItems = array.length;
      this.mouseOn = new boolean[this.amountOfItems];
   }

   public GuiDropDownMenu(int par1, int par2, int par3, int par4, int par5, String par6Str, String[] array) {
      super(par1, par2, par3, par4, par5, par6Str);
      this.prevHeight = this.field_73745_b;
      this.array = array;
      this.amountOfItems = array.length;
      this.mouseOn = new boolean[this.amountOfItems];
   }

   public void func_73737_a(Minecraft par1Minecraft, int par2, int par3) {
      if(this.field_73748_h) {
         if(this.dropDownMenu && this.array.length != 0) {
            this.field_73745_b = this.prevHeight * (this.amountOfItems + 1);
         } else {
            this.field_73745_b = this.prevHeight;
         }

         FontRenderer fontrenderer = par1Minecraft.field_71466_p;
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.field_82253_i = par2 >= this.field_73746_c && par3 >= this.field_73743_d && par2 < this.field_73746_c + this.field_73747_a && par3 < this.field_73743_d + this.field_73745_b;
         int k = this.func_73738_a(this.field_82253_i);
         this.func_73739_b(par1Minecraft, par2, par3);
         int l = 14737632;
         func_73734_a(this.field_73746_c, this.field_73743_d, this.field_73746_c + this.field_73747_a, this.field_73743_d + this.field_73745_b, -6250336);
         func_73734_a(this.field_73746_c + 1, this.field_73743_d + 1, this.field_73746_c + this.field_73747_a - 1, this.field_73743_d + this.field_73745_b - 1, -16777216);
         func_73734_a(this.field_73746_c, this.field_73743_d + this.prevHeight, this.field_73746_c + this.field_73747_a, this.field_73743_d + this.prevHeight, -6250336);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         boolean var10;
         if(this.dropDownMenu && this.array.length != 0) {
            var10 = true;
         } else {
            var10 = true;
         }

         if(!this.field_73742_g) {
            l = -6250336;
         }

         this.func_73732_a(fontrenderer, this.field_73744_e.substring(0, Math.min(this.field_73744_e.length(), 25)), this.field_73746_c + this.field_73747_a / 2, this.field_73743_d + (this.prevHeight - 8) / 2, l);
         GL11.glPushMatrix();
         if(this.dropDownMenu && this.array.length != 0) {
            for(int i = 0; i < this.amountOfItems; ++i) {
               this.mouseOn[i] = this.inBounds(par2, par3, this.field_73746_c, this.field_73743_d + this.prevHeight * (i + 1), this.field_73747_a, this.prevHeight);
               String s = this.array[i].substring(0, Math.min(this.array[i].length(), 26)) + "..";
               this.func_73732_a(fontrenderer, s, this.field_73746_c + this.field_73747_a / 2, this.field_73743_d + this.prevHeight * (i + 1) + 7, this.mouseOn[i]?16777120:14737632);
            }
         }

         GL11.glPopMatrix();
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      }

   }

   public boolean inBounds(int x, int y, int posX, int posY, int width, int height) {
      return this.field_73748_h && this.field_73742_g && x >= posX && y >= posY && x < posX + width && y < posY + height;
   }

   public void setDisplayString(String s) {
      this.field_73744_e = s;
   }

   public void setArray(String[] array) {
      this.array = array;
      this.amountOfItems = array.length;
      this.mouseOn = new boolean[this.amountOfItems];
   }

   public int getMouseOverInteger() {
      for(int i = 0; i < this.mouseOn.length; ++i) {
         if(this.mouseOn[i]) {
            return i;
         }
      }

      return -1;
   }
}
