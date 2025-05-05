package acs.tabbychat;

import acs.tabbychat.GuiChatTC;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBar;

class GuiChatTC$ScrollBar extends GuiScrollBar {

   // $FF: synthetic field
   final GuiChatTC this$0;


   public GuiChatTC$ScrollBar(GuiChatTC var1, float x, float y, int height) {
      super(x, y, height);
      this.this$0 = var1;
   }

   protected void drawScroller() {
      func_73734_a((int)this.x, (int)this.y, (int)(this.x + 9.0F), (int)(this.y + (float)this.height), 587202559);
      int yP = (int)(this.y + (float)(this.height - 20) * this.sliderValue);
      func_73734_a((int)this.x, yP, (int)(this.x + 9.0F), yP + 20, 1442840575);
   }
}
