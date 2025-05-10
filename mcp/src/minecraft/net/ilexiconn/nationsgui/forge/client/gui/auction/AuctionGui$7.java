package net.ilexiconn.nationsgui.forge.client.gui.auction;

import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBar;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;

class AuctionGui$7 extends GuiScrollBar
{
    final AuctionGui this$0;

    AuctionGui$7(AuctionGui this$0, float x, float y, int height)
    {
        super(x, y, height);
        this.this$0 = this$0;
    }

    protected void drawScroller()
    {
        ClientEventHandler.STYLE.bindTexture("auction_house");
        ModernGui.drawModalRectWithCustomSizedTexture((float)((int)this.x), (float)((int)(this.y + (float)(this.height - 15) * this.sliderValue)), 343, 70, 9, 14, 372.0F, 400.0F, false);
    }
}
