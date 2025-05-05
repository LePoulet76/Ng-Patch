package net.ilexiconn.nationsgui.forge.client.gui.advanced;

import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBar;

class GuiScroller$Scrollbar extends GuiScrollBar {

   public GuiScroller$Scrollbar(float x, float y, int height) {
      super(x, y, height);
   }

   protected void drawScroller() {
      func_73734_a((int)this.x, (int)(this.y + (float)(this.height - 6) * this.sliderValue), (int)this.x + 2, (int)(this.y + (float)(this.height - 6) * this.sliderValue) + 6, -10592674);
   }
}
