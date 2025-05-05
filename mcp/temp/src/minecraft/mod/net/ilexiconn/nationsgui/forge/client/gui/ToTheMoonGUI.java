package net.ilexiconn.nationsgui.forge.client.gui;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.ToTheMoonGUI$ToTheMoonGuiButton;
import net.ilexiconn.nationsgui.forge.client.gui.ToTheMoonGUI$ToTheMoonModalGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import org.lwjgl.opengl.GL11;

public class ToTheMoonGUI extends GuiScreen {

   private ToTheMoonGUI$ToTheMoonGuiButton btn;
   private boolean isButtonActive;
   private double goal;
   private double actualMoney;
   private int guiLeft;
   private int guiTop;
   public static int xSize = 289;
   public static int ySize = 172;
   public boolean helpOpened = false;
   public int helpSectionOffsetX = 0;
   private List<String> donators;


   public ToTheMoonGUI(boolean goalAchieved, double goal, double actualMoney, List<String> donators) {
      this.isButtonActive = goalAchieved;
      this.goal = goal;
      this.actualMoney = actualMoney;
      this.donators = donators;
   }

   public void func_73866_w_() {
      this.guiLeft = (this.field_73880_f - xSize) / 2;
      this.guiTop = (this.field_73881_g - ySize) / 2;
      this.btn = new ToTheMoonGUI$ToTheMoonGuiButton(0, (this.field_73880_f - 289) / 2 + 174, (this.field_73881_g - 172) / 2 + 135, I18n.func_135053_a(this.isButtonActive?"moon.contribute":"moon.achieved"), this.isButtonActive);
      this.field_73887_h.add(this.btn);
   }

   public void func_73863_a(int mouseX, int mouseY, float par3) {
      int x = (this.field_73880_f - xSize) / 2;
      int y = (this.field_73881_g - ySize) / 2;
      ClientEventHandler.STYLE.bindTexture("to_the_moon");
      if(!this.helpOpened) {
         this.helpSectionOffsetX = Math.max(this.helpSectionOffsetX - 2, 0);
      } else {
         this.helpSectionOffsetX = Math.min(this.helpSectionOffsetX + 2, 88);
      }

      ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 289 + this.helpSectionOffsetX), (float)(this.guiTop + 110), 106, 175, 23, 45, 512.0F, 512.0F, false);
      GL11.glPushMatrix();
      GL11.glTranslatef((float)(this.guiLeft + 296 + this.helpSectionOffsetX), (float)(this.guiTop + 147), 0.0F);
      GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
      GL11.glTranslatef((float)(-(this.guiLeft + 296 + this.helpSectionOffsetX)), (float)(-(this.guiTop + 147)), 0.0F);
      this.drawScaledString(I18n.func_135053_a("moon.label.leader"), this.guiLeft + 296 + this.helpSectionOffsetX, this.guiTop + 147, 0, 1.0F, false, false);
      GL11.glPopMatrix();
      ClientEventHandler.STYLE.bindTexture("to_the_moon");
      ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 289 - 88 + this.helpSectionOffsetX), (float)(this.guiTop + 8), 424, 0, 88, 156, 512.0F, 512.0F, false);
      this.drawScaledString(I18n.func_135053_a("moon.label.leader.title"), this.guiLeft + 286 - 88 + 44 + this.helpSectionOffsetX, this.guiTop + 17, 0, 1.0F, true, false);
      int percent;
      if(this.donators != null && !this.donators.isEmpty()) {
         for(percent = 0; percent < Math.min(this.donators.size(), 10); ++percent) {
            int progress = percent + 1;
            this.drawScaledString(progress + ". " + ((String)this.donators.get(percent)).split("#")[0], this.guiLeft + 294 - 88 + this.helpSectionOffsetX, this.guiTop + 30 + percent * 13, 0, 0.8F, false, false);
            this.drawScaledString("\u00a72" + ((String)this.donators.get(percent)).split("#")[1] + "$", this.guiLeft + 294 - 88 + this.helpSectionOffsetX + this.field_73886_k.func_78256_a(progress + ". ") - 2, this.guiTop + 37 + percent * 13, 0, 0.5F, false, false);
         }
      }

      ClientEventHandler.STYLE.bindTexture("to_the_moon");
      ModernGui.drawModalRectWithCustomSizedTexture((float)x, (float)y, 0, 0, xSize, ySize, 512.0F, 512.0F, false);
      super.func_73863_a(mouseX, mouseY, par3);
      GL11.glPushMatrix();
      GL11.glTranslatef((float)((this.field_73880_f - xSize) / 2 + 17), (float)((this.field_73881_g - ySize) / 2 + 145), 0.0F);
      GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
      GL11.glTranslatef((float)(-((this.field_73880_f - xSize) / 2 + 17)), (float)(-((this.field_73881_g - ySize) / 2 + 145)), 0.0F);
      this.drawScaledString(I18n.func_135053_a("moon.title"), (this.field_73880_f - xSize) / 2 + 17, (this.field_73881_g - ySize) / 2 + 145, 16777215, 1.2F, false, false);
      GL11.glPopMatrix();
      percent = (int)(this.actualMoney / this.goal * 100.0D);
      Double var18 = Double.valueOf(this.actualMoney / this.goal);
      Double barHeight = Double.valueOf(var18.doubleValue() * 115.0D);
      int barHeightInt = barHeight.intValue();
      int offsetHeight = 115 - barHeightInt;
      this.drawScaledString(percent + "%", (this.field_73880_f - xSize) / 2 + 147, (this.field_73881_g - ySize) / 2 + 141, 16777215, 0.8F, true, false);
      this.func_73732_a(this.field_73886_k, Math.round(this.goal) + "$", (this.field_73880_f - xSize) / 2 + 94, (this.field_73881_g - ySize) / 2 + 22, 16777215);
      ClientEventHandler.STYLE.bindTexture("to_the_moon");
      ModernGui.drawModalRectWithCustomSizedTexture((float)((this.field_73880_f - xSize) / 2 + 139), (float)((this.field_73881_g - ySize) / 2 + 22 + (117 - percent)), 0, 212 + (117 - percent), 13, 117 - percent, 512.0F, 512.0F, false);
      String[] descriptionWords = I18n.func_135053_a("moon.desc").split(" ");
      String line = "";
      int lineNumber = 0;
      String[] var14 = descriptionWords;
      int var15 = descriptionWords.length;

      for(int var16 = 0; var16 < var15; ++var16) {
         String descWord = var14[var16];
         if(this.field_73886_k.func_78256_a(line + descWord) <= 100) {
            if(!line.equals("")) {
               line = line + " ";
            }

            line = line + descWord;
         } else {
            this.drawScaledString(line, (this.field_73880_f - xSize) / 2 + 170, (this.field_73881_g - ySize) / 2 + 28 + lineNumber * 10, 16777215, 0.8F, false, true);
            ++lineNumber;
            line = descWord;
         }
      }

      this.drawScaledString(line, (this.field_73880_f - xSize) / 2 + 170, (this.field_73881_g - ySize) / 2 + 28 + lineNumber * 10, 16777215, 0.8F, false, true);
      if(mouseX >= (this.field_73880_f - xSize) / 2 + 233 && mouseX <= (this.field_73880_f - xSize) / 2 + 243 && mouseY >= (this.field_73881_g - ySize) / 2 + 115 && mouseY <= (this.field_73881_g - ySize) / 2 + 125) {
         this.drawHoveringText(Arrays.asList(I18n.func_135053_a("moon.info").split("##")), mouseX, mouseY, this.field_73886_k);
      }

   }

   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
      if(mouseButton == 0) {
         if(!this.helpOpened && mouseX > this.guiLeft + 289 && mouseX < this.guiLeft + 289 + 23 && mouseY > this.guiTop + 110 && mouseY < this.guiTop + 110 + 45) {
            this.helpOpened = true;
         } else if(this.helpOpened && mouseX > this.guiLeft + 289 + 88 && mouseX < this.guiLeft + 289 + 88 + 23 && mouseY > this.guiTop + 110 && mouseY < this.guiTop + 110 + 45) {
            this.helpOpened = false;
         }
      }

      super.func_73864_a(mouseX, mouseY, mouseButton);
   }

   protected void func_73875_a(GuiButton btn) {
      switch(btn.field_73741_f) {
      case 0:
         if(this.isButtonActive) {
            Minecraft.func_71410_x().func_71373_a(new ToTheMoonGUI$ToTheMoonModalGui(this));
         }
      default:
      }
   }

   public void drawScaledString(String text, int x, int y, int color, float scale, boolean centered, boolean shadow) {
      GL11.glPushMatrix();
      GL11.glScalef(scale, scale, scale);
      float newX = (float)x;
      if(centered) {
         newX = (float)x - (float)this.field_73886_k.func_78256_a(text) * scale / 2.0F;
      }

      if(shadow) {
         this.field_73886_k.func_85187_a(text, (int)(newX / scale), (int)((float)(y + 1) / scale), (color & 16579836) >> 2 | color & -16777216, false);
      }

      this.field_73886_k.func_85187_a(text, (int)(newX / scale), (int)((float)y / scale), color, false);
      GL11.glPopMatrix();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   protected void drawHoveringText(List par1List, int par2, int par3, FontRenderer font) {
      if(!par1List.isEmpty()) {
         GL11.glDisable('\u803a');
         RenderHelper.func_74518_a();
         GL11.glDisable(2896);
         GL11.glDisable(2929);
         int k = 0;
         Iterator iterator = par1List.iterator();

         int j1;
         while(iterator.hasNext()) {
            String i1 = (String)iterator.next();
            j1 = font.func_78256_a(i1);
            if(j1 > k) {
               k = j1;
            }
         }

         int var15 = par2 + 12;
         j1 = par3 - 12;
         int k1 = 8;
         if(par1List.size() > 1) {
            k1 += 2 + (par1List.size() - 1) * 10;
         }

         if(var15 + k > this.field_73880_f) {
            var15 -= 28 + k;
         }

         if(j1 + k1 + 6 > this.field_73881_g) {
            j1 = this.field_73881_g - k1 - 6;
         }

         this.field_73735_i = 300.0F;
         int l1 = -267386864;
         this.func_73733_a(var15 - 3, j1 - 4, var15 + k + 3, j1 - 3, l1, l1);
         this.func_73733_a(var15 - 3, j1 + k1 + 3, var15 + k + 3, j1 + k1 + 4, l1, l1);
         this.func_73733_a(var15 - 3, j1 - 3, var15 + k + 3, j1 + k1 + 3, l1, l1);
         this.func_73733_a(var15 - 4, j1 - 3, var15 - 3, j1 + k1 + 3, l1, l1);
         this.func_73733_a(var15 + k + 3, j1 - 3, var15 + k + 4, j1 + k1 + 3, l1, l1);
         int i2 = 1347420415;
         int j2 = (i2 & 16711422) >> 1 | i2 & -16777216;
         this.func_73733_a(var15 - 3, j1 - 3 + 1, var15 - 3 + 1, j1 + k1 + 3 - 1, i2, j2);
         this.func_73733_a(var15 + k + 2, j1 - 3 + 1, var15 + k + 3, j1 + k1 + 3 - 1, i2, j2);
         this.func_73733_a(var15 - 3, j1 - 3, var15 + k + 3, j1 - 3 + 1, i2, i2);
         this.func_73733_a(var15 - 3, j1 + k1 + 2, var15 + k + 3, j1 + k1 + 3, j2, j2);

         for(int k2 = 0; k2 < par1List.size(); ++k2) {
            String s1 = (String)par1List.get(k2);
            font.func_78261_a(s1, var15, j1, -1);
            if(k2 == 0) {
               j1 += 2;
            }

            j1 += 10;
         }

         this.field_73735_i = 0.0F;
         GL11.glDisable(2896);
         GL11.glDisable(2929);
         GL11.glEnable('\u803a');
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      }

   }

}
