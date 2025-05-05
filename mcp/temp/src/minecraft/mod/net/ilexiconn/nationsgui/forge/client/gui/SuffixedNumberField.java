package net.ilexiconn.nationsgui.forge.client.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ChatAllowedCharacters;
import org.lwjgl.opengl.GL11;

public class SuffixedNumberField extends Gui {

   private final FontRenderer fontRenderer;
   private final int xPos;
   private final int yPos;
   private String suffix;
   private int min = 0;
   private int max = -1;
   private final int width;
   private final int height;
   private String text = "";
   private int maxStringLength = 8;
   private int cursorCounter;
   private boolean enableBackgroundDrawing = true;
   private boolean canLoseFocus = true;
   private boolean isFocused;
   private boolean isEnabled = true;
   private int lineScrollOffset;
   private int cursorPosition;
   private int selectionEnd;
   private int enabledColor = 14737632;
   private int disabledColor = 7368816;
   private boolean visible = true;


   public SuffixedNumberField(FontRenderer par1FontRenderer, int par2, int par3, int par4, int par5, String suffix) {
      this.fontRenderer = par1FontRenderer;
      this.xPos = par2;
      this.yPos = par3;
      this.width = par4;
      this.height = par5;
      this.suffix = suffix;
   }

   public void updateCursorCounter() {
      ++this.cursorCounter;
   }

   public void setText(String par1Str) {
      if(par1Str.length() > this.maxStringLength) {
         this.text = par1Str.substring(0, this.maxStringLength);
      } else {
         this.text = par1Str;
      }

      this.setCursorPositionEnd();
   }

   public String getText() {
      return this.text;
   }

   public String getSelectedtext() {
      int i = this.cursorPosition < this.selectionEnd?this.cursorPosition:this.selectionEnd;
      int j = this.cursorPosition < this.selectionEnd?this.selectionEnd:this.cursorPosition;
      return this.text.substring(i, j);
   }

   public void writeText(String par1Str) {
      String s1 = "";
      String s2 = ChatAllowedCharacters.func_71565_a(par1Str);
      int i = this.cursorPosition < this.selectionEnd?this.cursorPosition:this.selectionEnd;
      int j = this.cursorPosition < this.selectionEnd?this.selectionEnd:this.cursorPosition;
      int k = this.maxStringLength - this.text.length() - (i - this.selectionEnd);
      boolean flag = false;
      if(this.text.length() > 0) {
         s1 = s1 + this.text.substring(0, i);
      }

      int l;
      if(k < s2.length()) {
         s1 = s1 + s2.substring(0, k);
         l = k;
      } else {
         s1 = s1 + s2;
         l = s2.length();
      }

      if(this.text.length() > 0 && j < this.text.length()) {
         s1 = s1 + this.text.substring(j);
      }

      try {
         int number = Integer.parseInt(s1);
         if(this.max != -1 && number > this.max) {
            this.text = Integer.toString(this.max);
            this.moveCursorBy(i - this.selectionEnd + l);
            return;
         }
      } catch (NumberFormatException var11) {
         return;
      }

      this.text = s1;
      this.moveCursorBy(i - this.selectionEnd + l);
   }

   public void deleteWords(int par1) {
      if(this.text.length() != 0) {
         if(this.selectionEnd != this.cursorPosition) {
            this.writeText("");
         } else {
            this.deleteFromCursor(this.getNthWordFromCursor(par1) - this.cursorPosition);
         }
      }

   }

   public void deleteFromCursor(int par1) {
      if(this.text.length() != 0) {
         if(this.selectionEnd != this.cursorPosition) {
            this.writeText("");
         } else {
            boolean flag = par1 < 0;
            int j = flag?this.cursorPosition + par1:this.cursorPosition;
            int k = flag?this.cursorPosition:this.cursorPosition + par1;
            String s = "";
            if(j >= 0) {
               s = this.text.substring(0, j);
            }

            if(k < this.text.length()) {
               s = s + this.text.substring(k);
            }

            this.text = s;
            if(flag) {
               this.moveCursorBy(par1);
            }
         }
      }

   }

   public int getNthWordFromCursor(int par1) {
      return this.getNthWordFromPos(par1, this.getCursorPosition());
   }

   public int getNthWordFromPos(int par1, int par2) {
      return this.func_73798_a(par1, this.getCursorPosition(), true);
   }

   public int func_73798_a(int par1, int par2, boolean par3) {
      int k = par2;
      boolean flag1 = par1 < 0;
      int l = Math.abs(par1);

      for(int i1 = 0; i1 < l; ++i1) {
         if(flag1) {
            while(par3 && k > 0 && this.text.charAt(k - 1) == 32) {
               --k;
            }

            while(k > 0 && this.text.charAt(k - 1) != 32) {
               --k;
            }
         } else {
            int j1 = this.text.length();
            k = this.text.indexOf(32, k);
            if(k == -1) {
               k = j1;
            } else {
               while(par3 && k < j1 && this.text.charAt(k) == 32) {
                  ++k;
               }
            }
         }
      }

      return k;
   }

   public void moveCursorBy(int par1) {
      this.setCursorPosition(this.selectionEnd + par1);
   }

   public void setCursorPosition(int par1) {
      this.cursorPosition = par1;
      int j = this.text.length();
      if(this.cursorPosition < 0) {
         this.cursorPosition = 0;
      }

      if(this.cursorPosition > j) {
         this.cursorPosition = j;
      }

      this.setSelectionPos(this.cursorPosition);
   }

   public void setCursorPositionZero() {
      this.setCursorPosition(0);
   }

   public void setCursorPositionEnd() {
      this.setCursorPosition(this.text.length());
   }

   public boolean textboxKeyTyped(char par1, int par2) {
      if(this.isEnabled && this.isFocused) {
         switch(par1) {
         case 1:
            this.setCursorPositionEnd();
            this.setSelectionPos(0);
            return true;
         case 3:
            GuiScreen.func_73865_d(this.getSelectedtext());
            return true;
         case 22:
            this.writeText(GuiScreen.func_73870_l());
            return true;
         case 24:
            GuiScreen.func_73865_d(this.getSelectedtext());
            this.writeText("");
            return true;
         default:
            switch(par2) {
            case 14:
               if(GuiScreen.func_73861_o()) {
                  this.deleteWords(-1);
               } else {
                  this.deleteFromCursor(-1);
               }

               return true;
            case 199:
               if(GuiScreen.func_73877_p()) {
                  this.setSelectionPos(0);
               } else {
                  this.setCursorPositionZero();
               }

               return true;
            case 203:
               if(GuiScreen.func_73877_p()) {
                  if(GuiScreen.func_73861_o()) {
                     this.setSelectionPos(this.getNthWordFromPos(-1, this.getSelectionEnd()));
                  } else {
                     this.setSelectionPos(this.getSelectionEnd() - 1);
                  }
               } else if(GuiScreen.func_73861_o()) {
                  this.setCursorPosition(this.getNthWordFromCursor(-1));
               } else {
                  this.moveCursorBy(-1);
               }

               return true;
            case 205:
               if(GuiScreen.func_73877_p()) {
                  if(GuiScreen.func_73861_o()) {
                     this.setSelectionPos(this.getNthWordFromPos(1, this.getSelectionEnd()));
                  } else {
                     this.setSelectionPos(this.getSelectionEnd() + 1);
                  }
               } else if(GuiScreen.func_73861_o()) {
                  this.setCursorPosition(this.getNthWordFromCursor(1));
               } else {
                  this.moveCursorBy(1);
               }

               return true;
            case 207:
               if(GuiScreen.func_73877_p()) {
                  this.setSelectionPos(this.text.length());
               } else {
                  this.setCursorPositionEnd();
               }

               return true;
            case 211:
               if(GuiScreen.func_73861_o()) {
                  this.deleteWords(1);
               } else {
                  this.deleteFromCursor(1);
               }

               return true;
            default:
               if(ChatAllowedCharacters.func_71566_a(par1)) {
                  this.writeText(Character.toString(par1));
                  return true;
               } else {
                  return false;
               }
            }
         }
      } else {
         return false;
      }
   }

   public void mouseClicked(int par1, int par2, int par3) {
      boolean flag = par1 >= this.xPos && par1 < this.xPos + this.width && par2 >= this.yPos && par2 < this.yPos + this.height;
      if(this.canLoseFocus) {
         this.setFocused(this.isEnabled && flag);
      }

      if(this.isFocused && par3 == 0) {
         int l = par1 - this.xPos;
         if(this.enableBackgroundDrawing) {
            l -= 4;
         }

         String s = this.fontRenderer.func_78269_a(this.text.substring(this.lineScrollOffset), this.getWidth());
         this.setCursorPosition(this.fontRenderer.func_78269_a(s, l).length() + this.lineScrollOffset);
      }

   }

   public void drawTextBox() {
      if(this.getVisible()) {
         if(this.getEnableBackgroundDrawing()) {
            func_73734_a(this.xPos - 1, this.yPos - 1, this.xPos + this.width + 1, this.yPos + this.height + 1, -6250336);
            func_73734_a(this.xPos, this.yPos, this.xPos + this.width, this.yPos + this.height, -16777216);
         }

         String t = this.text + this.suffix;
         int i = this.isEnabled?this.enabledColor:this.disabledColor;
         int j = this.cursorPosition - this.lineScrollOffset;
         int k = this.selectionEnd - this.lineScrollOffset;
         String s = this.fontRenderer.func_78269_a(t.substring(this.lineScrollOffset), this.getWidth());
         boolean flag = j >= 0 && j <= s.length();
         boolean flag1 = this.isFocused && this.cursorCounter / 6 % 2 == 0 && flag;
         int l = this.enableBackgroundDrawing?this.xPos + 4:this.xPos;
         int i1 = this.enableBackgroundDrawing?this.yPos + (this.height - 8) / 2:this.yPos;
         int j1 = l;
         if(k > s.length()) {
            k = s.length();
         }

         if(s.length() > 0) {
            String flag2 = flag?s.substring(0, j):s;
            j1 = this.fontRenderer.func_78261_a(flag2, l, i1, i);
         }

         boolean var14 = this.cursorPosition < t.length() || t.length() >= this.getMaxStringLength();
         int k1 = j1;
         if(!flag) {
            k1 = j > 0?l + this.width:l;
         } else if(var14) {
            k1 = j1 - 1;
            --j1;
         }

         if(s.length() > 0 && flag && j < s.length()) {
            this.fontRenderer.func_78261_a(s.substring(j), j1, i1, i);
         }

         if(flag1) {
            if(var14) {
               Gui.func_73734_a(k1, i1 - 1, k1 + 1, i1 + 1 + this.fontRenderer.field_78288_b, -3092272);
            } else {
               this.fontRenderer.func_78261_a("_", k1, i1, i);
            }
         }

         if(k != j) {
            int l1 = l + this.fontRenderer.func_78256_a(s.substring(0, k));
            this.drawCursorVertical(k1, i1 - 1, l1 - 1, i1 + 1 + this.fontRenderer.field_78288_b);
         }
      }

   }

   private void drawCursorVertical(int par1, int par2, int par3, int par4) {
      int i1;
      if(par1 < par3) {
         i1 = par1;
         par1 = par3;
         par3 = i1;
      }

      if(par2 < par4) {
         i1 = par2;
         par2 = par4;
         par4 = i1;
      }

      Tessellator tessellator = Tessellator.field_78398_a;
      GL11.glColor4f(0.0F, 0.0F, 255.0F, 255.0F);
      GL11.glDisable(3553);
      GL11.glEnable(3058);
      GL11.glLogicOp(5387);
      tessellator.func_78382_b();
      tessellator.func_78377_a((double)par1, (double)par4, 0.0D);
      tessellator.func_78377_a((double)par3, (double)par4, 0.0D);
      tessellator.func_78377_a((double)par3, (double)par2, 0.0D);
      tessellator.func_78377_a((double)par1, (double)par2, 0.0D);
      tessellator.func_78381_a();
      GL11.glDisable(3058);
      GL11.glEnable(3553);
   }

   public void setMaxStringLength(int par1) {
      this.maxStringLength = par1;
      if(this.text.length() > par1) {
         this.text = this.text.substring(0, par1);
      }

   }

   public int getMaxStringLength() {
      return this.maxStringLength;
   }

   public int getCursorPosition() {
      return this.cursorPosition;
   }

   public boolean getEnableBackgroundDrawing() {
      return this.enableBackgroundDrawing;
   }

   public void setEnableBackgroundDrawing(boolean par1) {
      this.enableBackgroundDrawing = par1;
   }

   public void setTextColor(int par1) {
      this.enabledColor = par1;
   }

   public void setDisabledTextColour(int par1) {
      this.disabledColor = par1;
   }

   public void setFocused(boolean par1) {
      if(par1 && !this.isFocused) {
         this.cursorCounter = 0;
      }

      this.isFocused = par1;
   }

   public boolean isFocused() {
      return this.isFocused;
   }

   public void setEnabled(boolean par1) {
      this.isEnabled = par1;
   }

   public int getSelectionEnd() {
      return this.selectionEnd;
   }

   public int getWidth() {
      return this.getEnableBackgroundDrawing()?this.width - 8:this.width;
   }

   public void setSelectionPos(int par1) {
      int j = this.text.length();
      if(par1 > j) {
         par1 = j;
      }

      if(par1 < 0) {
         par1 = 0;
      }

      this.selectionEnd = par1;
      if(this.fontRenderer != null) {
         if(this.lineScrollOffset > j) {
            this.lineScrollOffset = j;
         }

         int k = this.getWidth();
         String s = this.fontRenderer.func_78269_a(this.text.substring(this.lineScrollOffset), k);
         int l = s.length() + this.lineScrollOffset;
         if(par1 == this.lineScrollOffset) {
            this.lineScrollOffset -= this.fontRenderer.func_78262_a(this.text, k, true).length();
         }

         if(par1 > l) {
            this.lineScrollOffset += par1 - l;
         } else if(par1 <= this.lineScrollOffset) {
            this.lineScrollOffset -= this.lineScrollOffset - par1;
         }

         if(this.lineScrollOffset < 0) {
            this.lineScrollOffset = 0;
         }

         if(this.lineScrollOffset > j) {
            this.lineScrollOffset = j;
         }
      }

   }

   public void setCanLoseFocus(boolean par1) {
      this.canLoseFocus = par1;
   }

   public boolean getVisible() {
      return this.visible;
   }

   public void setVisible(boolean par1) {
      this.visible = par1;
   }

   public void setMin(int min) {
      this.min = min;
   }

   public void setMax(int max) {
      this.max = max;
   }

   public int getMax() {
      return this.max;
   }

   public int getMin() {
      return this.min;
   }
}
