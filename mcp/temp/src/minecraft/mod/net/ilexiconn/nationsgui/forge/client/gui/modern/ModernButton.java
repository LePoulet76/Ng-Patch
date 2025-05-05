package net.ilexiconn.nationsgui.forge.client.gui.modern;

import net.halalaboos.cfont.CFontRenderer;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.FontManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ModernButton extends GuiButton {

   protected int color;
   protected static CFontRenderer cFontRenderer = null;
   protected ResourceLocation resourceIcon;


   public ModernButton(int id, int posX, int posY, int width, int height, String text, int color) {
      this(id, posX, posY, width, height, text, color, (ResourceLocation)null);
   }

   public ModernButton(int id, int posX, int posY, int width, int height, String text, int color, ResourceLocation icon) {
      super(id, posX, posY, width, height, text);
      this.color = color;
      if(cFontRenderer == null) {
         cFontRenderer = FontManager.createFont("nationsgui", "VisbyCF-Bold.otf");
         cFontRenderer.setFontSize(14.0F);
      }

      this.resourceIcon = icon;
   }

   public ModernButton(int id, int posX, int posY, String text, int color, ResourceLocation icon) {
      this(id, posX, posY, 0, 0, text, color, icon);
      if(!text.equals("")) {
         this.field_73747_a = (int)((float)this.field_73747_a + cFontRenderer.getStringWidth(text) + 8.0F);
      }

      if(icon != null) {
         this.field_73747_a += 12 + (!text.equals("")?0:2);
      }

      this.field_73745_b += cFontRenderer.getStringHeight(text) + 12;
   }

   public ModernButton(int id, int posX, int posY, String text, int color) {
      this(id, posX, posY, text, color, (ResourceLocation)null);
   }

   public ModernButton(int id, int posX, int posY, int color, ResourceLocation location) {
      this(id, posX, posY, "", color, location);
   }

   public void setIcon(ResourceLocation icon) {
      this.resourceIcon = icon;
   }

   public int getWidth() {
      return this.field_73747_a;
   }

   public int getHeight() {
      return this.field_73745_b;
   }

   public int getPosX() {
      return this.field_73746_c;
   }

   public int getPosY() {
      return this.field_73743_d;
   }

   public void setPos(int x, int y) {
      this.field_73746_c = x;
      this.field_73743_d = y;
   }

   public void func_73737_a(Minecraft minecraft, int i, int i1) {
      float r = (float)(this.color >> 24 & 255) / 255.0F;
      float g = (float)(this.color >> 16 & 255) / 255.0F;
      float b = (float)(this.color >> 8 & 255) / 255.0F;
      float a = (float)(this.color & 255) / 255.0F;
      this.field_82253_i = i >= this.field_73746_c && i1 >= this.field_73743_d && i < this.field_73746_c + this.field_73747_a && i1 < this.field_73743_d + this.field_73745_b;
      float modifier = this.field_82253_i && this.field_73742_g?0.1F:0.0F;
      GL11.glColor4f(r - modifier, g - modifier, b - modifier, a);
      ModernGui.drawRoundedRectangle((float)this.field_73746_c, (float)this.field_73743_d, this.field_73735_i, (float)this.field_73747_a, (float)this.field_73745_b);
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      float w = cFontRenderer.getStringWidth(this.field_73744_e);
      float h = (float)cFontRenderer.getStringHeight(this.field_73744_e);
      if(this.resourceIcon != null) {
         this.drawIcon(minecraft, w + (float)(!this.field_73744_e.equals("")?5:2), h);
      }

      this.drawText(minecraft, w, h);
   }

   protected void drawText(Minecraft minecraft, float w, float h) {
      cFontRenderer.drawString(this.field_73744_e, (float)this.field_73746_c + (float)this.field_73747_a / 2.0F - (w - (this.resourceIcon != null?10.0F:0.0F)) / 2.0F, (float)this.field_73743_d + (float)this.field_73745_b / 2.0F - h / 2.0F, 16777215);
   }

   protected void drawIcon(Minecraft minecraft, float w, float h) {
      minecraft.func_110434_K().func_110577_a(this.resourceIcon);
      ModernGui.drawModalRectWithCustomSizedTexture((float)this.field_73746_c + (float)this.field_73747_a / 2.0F - (w + 10.0F) / 2.0F, (float)this.field_73743_d + (float)this.field_73745_b / 2.0F - 6.0F, 0, 0, 12, 12, 12.0F, 12.0F, false);
   }

}
