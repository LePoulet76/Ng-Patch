package net.ilexiconn.nationsgui.forge.client.gui.hdv;

import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBar;
import net.ilexiconn.nationsgui.forge.client.gui.hdv.MarketGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;

class MarketGui$7 extends GuiScrollBar {

   // $FF: synthetic field
   final MarketGui this$0;


   MarketGui$7(MarketGui this$0, float x, float y, int height) {
      super(x, y, height);
      this.this$0 = this$0;
   }

   protected void drawScroller() {
      MarketGui.access$000(this.this$0).func_110434_K().func_110577_a(MarketGui.BACKGROUND);
      ModernGui.drawModalRectWithCustomSizedTexture((float)((int)this.x), (float)((int)(this.y + (float)(this.height - 15) * this.sliderValue)), 343, 70, 9, 14, 372.0F, 400.0F, false);
   }
}
