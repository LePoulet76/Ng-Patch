package net.ilexiconn.nationsgui.forge.client.gui.cosmetic;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.ilexiconn.nationsgui.forge.client.gui.InventoryGUI;
import net.ilexiconn.nationsgui.forge.client.gui.TexturedButtonGUI;
import net.ilexiconn.nationsgui.forge.client.gui.cosmetic.CosmeticGUI_OLD;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.entity.data.NGPlayerData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class EmotesGUI extends GuiScreen {

   public static final ResourceLocation TEXTURE = new ResourceLocation("nationsgui", "emotes/gui/cosmetic.png");
   private int guiLeft = 0;
   private int guiTop = 0;
   private int xSize = 256;
   private int ySize = 210;
   private List<String> ownedEmotes;
   private List<String> currentEmotes;
   private String selectedEmote;
   private String hoveredEmote;
   private int selectedSlot = -1;
   private GuiButton removeButton;
   public static boolean loaded = false;


   public void func_73866_w_() {
      super.func_73866_w_();
      loaded = false;
      this.selectedSlot = -1;
      this.guiLeft = this.field_73880_f / 2 - 128;
      this.guiTop = this.field_73881_g / 2 - 105;
      this.ownedEmotes = NGPlayerData.get(this.field_73882_e.field_71439_g).getEmotes();
      this.currentEmotes = NGPlayerData.get(this.field_73882_e.field_71439_g).getCurrentEmotes();
      this.selectedEmote = (String)this.ownedEmotes.get(0);
      this.removeButton = new TexturedButtonGUI(0, this.guiLeft + 165, this.guiTop + 175, 74, 19, "cosmetic_btns", 0, 0, "Retirer");
      this.field_73887_h.add(this.removeButton);
   }

   public void func_73863_a(int mouseX, int mouseY, float partialsTicks) {
      this.func_73873_v_();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.bindTexture(TEXTURE);
      this.func_73729_b(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
      String tooltipToDraw = "";
      ClientEventHandler.STYLE.bindTexture("cosmetic");

      int offsetX;
      GuiScreenTab offsetY;
      int i;
      int c;
      for(offsetX = 0; offsetX <= 3; ++offsetX) {
         offsetY = (GuiScreenTab)CosmeticGUI_OLD.TABS.get(offsetX);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         i = offsetX % 4;
         c = offsetX / 4;
         if(this.getClass() == offsetY.getClassReferent()) {
            this.func_73729_b(this.guiLeft - 23, this.guiTop + 50 + offsetX * 31, 125, 210, 29, 30);
            this.func_73729_b(this.guiLeft - 23 + 3, this.guiTop + 50 + offsetX * 31 + 5, 154 + i * 20, 210 + c * 20, 20, 20);
         } else {
            this.func_73729_b(this.guiLeft - 20, this.guiTop + 50 + offsetX * 31, 102, 210, 23, 30);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(3042);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.75F);
            this.func_73729_b(this.guiLeft - 20 + 3, this.guiTop + 50 + offsetX * 31 + 5, 154 + i * 20, 210 + c * 20, 20, 20);
            GL11.glDisable(3042);
         }
      }

      for(offsetX = 4; offsetX < CosmeticGUI_OLD.TABS.size(); ++offsetX) {
         offsetY = (GuiScreenTab)CosmeticGUI_OLD.TABS.get(offsetX);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         i = offsetX % 4;
         c = offsetX / 4;
         if(this.getClass() == offsetY.getClassReferent()) {
            this.func_73729_b(this.guiLeft + 250, this.guiTop + 50 + (offsetX - 4) * 31, 51, 210, 29, 30);
            this.func_73729_b(this.guiLeft + 250 + 3, this.guiTop + 50 + (offsetX - 4) * 31 + 5, 154 + i * 20, 210 + c * 20, 20, 20);
         } else {
            this.func_73729_b(this.guiLeft + 252, this.guiTop + 50 + (offsetX - 4) * 31, 80, 210, 23, 30);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(3042);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.75F);
            this.func_73729_b(this.guiLeft + 250 + 3, this.guiTop + 50 + (offsetX - 4) * 31 + 5, 154 + i * 20, 210 + c * 20, 20, 20);
            GL11.glDisable(3042);
         }
      }

      drawScaledString("Emotes", this.guiLeft + 20, this.guiTop + 15, 16777215, 2.0F, false);
      if(this.selectedSlot != -1 && !((String)this.currentEmotes.get(this.selectedSlot)).isEmpty()) {
         if(!this.field_73887_h.contains(this.removeButton)) {
            this.field_73887_h.add(this.removeButton);
         }
      } else if(this.field_73887_h.contains(this.removeButton)) {
         this.field_73887_h.remove(this.removeButton);
      }

      this.hoveredEmote = null;
      boolean var13 = false;
      boolean var14 = false;
      i = 0;

      int w;
      for(Iterator var16 = this.ownedEmotes.iterator(); var16.hasNext(); ++i) {
         String emote = (String)var16.next();
         int emote1 = i % 7;
         w = i / 7;
         offsetX = this.guiLeft + 11 + emote1 * 18;
         int var15 = this.guiTop + 53 + w * 18;
         this.drawIcon(emote, offsetX, var15, -1);
         if(this.selectedEmote != null && this.selectedEmote.equals(emote)) {
            this.bindTexture(TEXTURE);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.func_73729_b(offsetX - 4, var15 - 4, 9, 210, 24, 24);
            GL11.glDisable(3042);
         }

         if(mouseX >= offsetX && mouseX <= offsetX + 16 && mouseY >= var15 && mouseY <= var15 + 16) {
            this.hoveredEmote = emote;
            tooltipToDraw = I18n.func_135053_a("emotes." + this.hoveredEmote + ".name");
         }
      }

      c = 0;

      for(Iterator var17 = this.currentEmotes.iterator(); var17.hasNext(); ++c) {
         String var18 = (String)var17.next();
         switch(c) {
         case 0:
            this.drawIcon(var18, this.guiLeft + 180, this.guiTop + 76, 0);
            break;
         case 1:
            this.drawIcon(var18, this.guiLeft + 204, this.guiTop + 76, 1);
            break;
         case 2:
            this.drawIcon(var18, this.guiLeft + 166, this.guiTop + 98, 2);
            break;
         case 3:
            this.drawIcon(var18, this.guiLeft + 218, this.guiTop + 98, 3);
            break;
         case 4:
            this.drawIcon(var18, this.guiLeft + 180, this.guiTop + 120, 4);
            break;
         case 5:
            this.drawIcon(var18, this.guiLeft + 204, this.guiTop + 120, 5);
         }

         if(this.selectedSlot != -1 && this.selectedSlot == c && this.currentEmotes.get(this.selectedSlot) != null && !((String)this.currentEmotes.get(this.selectedSlot)).isEmpty()) {
            this.bindTexture(TEXTURE);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            w = this.guiLeft;
            int h = this.guiTop;
            switch(c) {
            case 0:
               w += 180;
               h += 76;
               break;
            case 1:
               w += 204;
               h += 76;
               break;
            case 2:
               w += 166;
               h += 98;
               break;
            case 3:
               w += 218;
               h += 98;
               break;
            case 4:
               w += 180;
               h += 120;
               break;
            case 5:
               w += 204;
               h += 120;
            }

            this.func_73729_b(w - 4, h - 4, 9, 210, 24, 24);
            GL11.glDisable(3042);
         }
      }

      if(this.selectedSlot != -1 && this.currentEmotes.get(this.selectedSlot) != null && !((String)this.currentEmotes.get(this.selectedSlot)).isEmpty()) {
         drawScaledString(I18n.func_135053_a("emotes." + (String)this.currentEmotes.get(this.selectedSlot) + ".name"), this.guiLeft + 23, this.guiTop + 180, 16777215, 1.2F, false);
      }

      super.func_73863_a(mouseX, mouseY, partialsTicks);
      if(mouseX >= this.guiLeft + 165 && mouseX <= this.guiLeft + 165 + 74 && mouseY >= this.guiTop + 175 && mouseY <= this.guiTop + 175 + 19 && this.selectedSlot != -1 && this.currentEmotes.get(this.selectedSlot) != "") {
         this.drawHoveringText(Arrays.asList(new String[]{I18n.func_135053_a("emotes.gui.label.remove")}), mouseX, mouseY, this.field_73886_k);
      }

      for(i = 0; i <= 3; ++i) {
         if(mouseX >= this.guiLeft - (this.getClass() == ((GuiScreenTab)InventoryGUI.TABS.get(i)).getClassReferent()?23:20) && mouseX <= this.guiLeft + 3 && mouseY >= this.guiTop + 50 + i * 31 && mouseY <= this.guiTop + 85 + i * 31) {
            this.drawHoveringText(Collections.singletonList(I18n.func_135053_a("gui.cosmetic.tab." + i)), mouseX, mouseY, this.field_73886_k);
         }
      }

      for(i = 4; i < CosmeticGUI_OLD.TABS.size(); ++i) {
         if(mouseX >= this.guiLeft + 252 && mouseX <= this.guiLeft + 278 && mouseY >= this.guiTop + 50 + (i - 4) * 31 && mouseY <= this.guiTop + 85 + (i - 4) * 31) {
            this.drawHoveringText(Collections.singletonList(I18n.func_135053_a("gui.cosmetic.tab." + i)), mouseX, mouseY, this.field_73886_k);
         }
      }

      if(!tooltipToDraw.isEmpty()) {
         this.drawHoveringText(Arrays.asList(new String[]{tooltipToDraw}), mouseX, mouseY, this.field_73886_k);
      }

   }

   private void drawIcon(String emote, int offsetX, int offsetY, int id) {
      ResourceLocation rl = new ResourceLocation("nationsgui", "emotes/icons/" + emote + ".png");
      if(this.iconExist(rl)) {
         this.bindTexture(rl);
         ModernGui.drawModalRectWithCustomSizedTexture((float)offsetX, (float)offsetY, 0, 0, 16, 16, 16.0F, 16.0F, false);
      } else {
         this.bindTexture(TEXTURE);
         this.func_73729_b(offsetX, offsetY, 11, 53, 16, 16);
         if(id > -1 && !((String)this.currentEmotes.get(id)).isEmpty()) {
            this.bindTexture(new ResourceLocation("nationsgui", "emotes/icons/custom.png"));
            ModernGui.drawModalRectWithCustomSizedTexture((float)offsetX, (float)offsetY, 0, 0, 16, 16, 16.0F, 16.0F, false);
         }

         if(id == -1) {
            this.bindTexture(new ResourceLocation("nationsgui", "emotes/icons/custom.png"));
            ModernGui.drawModalRectWithCustomSizedTexture((float)offsetX, (float)offsetY, 0, 0, 16, 16, 16.0F, 16.0F, false);
         }
      }

   }

   public void drawHoveringText(List<String> text, int mouseX, int mouseY, FontRenderer fontRenderer) {
      if(!text.isEmpty()) {
         GL11.glDisable('\u803a');
         GL11.glDisable(2896);
         GL11.glDisable(2929);
         int width = 0;

         int offsetY;
         for(Iterator posX = text.iterator(); posX.hasNext(); width = Math.max(width, offsetY)) {
            String posY = (String)posX.next();
            offsetY = fontRenderer.func_78256_a(posY);
         }

         int var14 = mouseX + 12;
         int var15 = mouseY - 12;
         offsetY = 8;
         if(text.size() > 1) {
            offsetY += 2 + (text.size() - 1) * 10;
         }

         if(var14 + width > this.field_73880_f) {
            var14 -= 28 + width;
         }

         if(var15 + offsetY + 6 > this.field_73881_g) {
            var15 = this.field_73881_g - offsetY - 6;
         }

         this.field_73735_i = 300.0F;
         int color1 = -267386864;
         this.func_73733_a(var14 - 3, var15 - 4, var14 + width + 3, var15 - 3, color1, color1);
         this.func_73733_a(var14 - 3, var15 + offsetY + 3, var14 + width + 3, var15 + offsetY + 4, color1, color1);
         this.func_73733_a(var14 - 3, var15 - 3, var14 + width + 3, var15 + offsetY + 3, color1, color1);
         this.func_73733_a(var14 - 4, var15 - 3, var14 - 3, var15 + offsetY + 3, color1, color1);
         this.func_73733_a(var14 + width + 3, var15 - 3, var14 + width + 4, var15 + offsetY + 3, color1, color1);
         int color2 = 1347420415;
         int color3 = (color2 & 16711422) >> 1 | color2 & -16777216;
         this.func_73733_a(var14 - 3, var15 - 3 + 1, var14 - 3 + 1, var15 + offsetY + 3 - 1, color2, color3);
         this.func_73733_a(var14 + width + 2, var15 - 3 + 1, var14 + width + 3, var15 + offsetY + 3 - 1, color2, color3);
         this.func_73733_a(var14 - 3, var15 - 3, var14 + width + 3, var15 - 3 + 1, color2, color2);
         this.func_73733_a(var14 - 3, var15 + offsetY + 2, var14 + width + 3, var15 + offsetY + 3, color3, color3);

         for(int i = 0; i < text.size(); ++i) {
            String line = (String)text.get(i);
            if(i == 0) {
               fontRenderer.func_78261_a(line, var14, var15, -1);
               var15 += 2;
            } else {
               fontRenderer.func_78261_a(EnumChatFormatting.GOLD + line, var14 + width - fontRenderer.func_78256_a(line), var15, 16777215);
            }

            var15 += 10;
         }

         this.field_73735_i = 0.0F;
         GL11.glDisable(2896);
         GL11.glDisable(2929);
         GL11.glEnable('\u803a');
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      }

   }

   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
      if(mouseButton == 0) {
         int slot;
         GuiScreenTab type;
         for(slot = 0; slot <= 3; ++slot) {
            type = (GuiScreenTab)CosmeticGUI_OLD.TABS.get(slot);
            if(mouseX >= this.guiLeft - 20 && mouseX <= this.guiLeft + 3 && mouseY >= this.guiTop + 50 + slot * 31 && mouseY <= this.guiTop + 85 + slot * 31) {
               this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
               if(this.getClass() != type.getClassReferent()) {
                  try {
                     type.call();
                  } catch (Exception var8) {
                     var8.printStackTrace();
                  }
               }
            }
         }

         for(slot = 4; slot < CosmeticGUI_OLD.TABS.size(); ++slot) {
            type = (GuiScreenTab)CosmeticGUI_OLD.TABS.get(slot);
            if(mouseX >= this.guiLeft + 252 && mouseX <= this.guiLeft + 278 && mouseY >= this.guiTop + 50 + (slot - 4) * 31 && mouseY <= this.guiTop + 85 + (slot - 4) * 31) {
               this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
               if(this.getClass() != type.getClassReferent()) {
                  try {
                     type.call();
                  } catch (Exception var7) {
                     var7.printStackTrace();
                  }
               }
            }
         }

         if(mouseX >= this.guiLeft + 236 && mouseX <= this.guiLeft + 236 + 9 && mouseY >= this.guiTop + 17 && mouseY <= this.guiTop + 17 + 10) {
            this.field_73882_e.func_71373_a((GuiScreen)null);
            this.field_73882_e.func_71381_h();
            this.field_73882_e.field_71416_A.func_82461_f();
         }

         if(this.hoveredEmote != null) {
            this.selectedEmote = this.hoveredEmote;
            this.selectedSlot = -1;
         }

         if(mouseX >= this.guiLeft + 154 && mouseX <= this.guiLeft + 154 + 92 && mouseY >= this.guiTop + 52 && mouseY <= this.guiTop + 52 + 107) {
            this.selectedSlot = -1;
         }

         boolean var9 = true;
         if((slot = this.mouseClickedInCurentSlots(mouseX, mouseY)) != -1) {
            this.selectedSlot = slot;
            if(this.selectedEmote != null) {
               if(!this.currentEmotes.contains(this.selectedEmote)) {
                  this.currentEmotes.set(this.selectedSlot, this.selectedEmote);
               }

               this.selectedEmote = null;
            }
         }
      }

      super.func_73864_a(mouseX, mouseY, mouseButton);
   }

   protected void func_73875_a(GuiButton button) {
      switch(button.field_73741_f) {
      case 0:
         if(this.selectedSlot != -1) {
            this.currentEmotes.set(this.selectedSlot, "");
            this.selectedSlot = -1;
         }
      default:
      }
   }

   private int mouseClickedInCurentSlots(int mouseX, int mouseY) {
      byte slotLenght = 16;
      int[][] slots = new int[][]{{180, 76}, {204, 76}, {166, 98}, {218, 98}, {180, 120}, {204, 120}};

      for(int i = 0; i < slots.length; ++i) {
         int[] slot = slots[i];
         if(mouseX >= this.guiLeft + slot[0] && mouseX <= this.guiLeft + slot[0] + slotLenght && mouseY >= this.guiTop + slot[1] && mouseY <= this.guiTop + slot[1] + slotLenght) {
            return i;
         }
      }

      return -1;
   }

   public void func_73874_b() {
      if(this.currentEmotes.size() < 7) {
         NGPlayerData.get(this.field_73882_e.field_71439_g).setCurrentEmotes(this.currentEmotes);
         System.out.println("GuiClosed + sending current emotes");
      }

   }

   private void bindTexture(ResourceLocation texture) {
      Minecraft.func_71410_x().field_71446_o.func_110577_a(texture);
   }

   private boolean iconExist(ResourceLocation rl) {
      return EmotesGUI.class.getResourceAsStream("/assets/" + rl.func_110624_b() + "/" + rl.func_110623_a()) != null;
   }

   public static void drawScaledString(String text, int x, int y, int color, float scale, boolean centered) {
      GL11.glPushMatrix();
      GL11.glScalef(scale, scale, scale);
      float newX = (float)x;
      if(centered) {
         newX = (float)x - (float)Minecraft.func_71410_x().field_71466_p.func_78256_a(text) * scale / 2.0F;
      }

      Minecraft.func_71410_x().field_71466_p.func_85187_a(text, (int)(newX / scale), (int)((float)(y + 1) / scale), (color & 16579836) >> 2 | color & -16777216, false);
      Minecraft.func_71410_x().field_71466_p.func_85187_a(text, (int)(newX / scale), (int)((float)y / scale), color, false);
      GL11.glPopMatrix();
   }

}
