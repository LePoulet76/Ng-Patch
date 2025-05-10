package net.ilexiconn.nationsgui.forge.client.gui;

class ChatGUI$ScrollBar extends GuiScrollBar
{
    final ChatGUI this$0;

    public ChatGUI$ScrollBar(ChatGUI var1, float x, float y, int height)
    {
        super(x, y, height);
        this.this$0 = var1;
    }

    protected void drawScroller()
    {
        drawRect((int)this.x, (int)this.y, (int)(this.x + 9.0F), (int)(this.y + (float)this.height), 587202559);
        int yP = (int)(this.y + (float)(this.height - 20) * this.sliderValue);
        drawRect((int)this.x, yP, (int)(this.x + 9.0F), yP + 20, 1442840575);
    }
}
