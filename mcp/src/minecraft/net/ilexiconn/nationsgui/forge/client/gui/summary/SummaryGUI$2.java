package net.ilexiconn.nationsgui.forge.client.gui.summary;

import net.ilexiconn.nationsgui.forge.client.gui.summary.SummaryGUI$IButtonCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

class SummaryGUI$2 implements SummaryGUI$IButtonCallback
{
    final GuiButton val$button;

    final SummaryGUI this$0;

    SummaryGUI$2(SummaryGUI this$0, GuiButton var2)
    {
        this.this$0 = this$0;
        this.val$button = var2;
    }

    public void call(Minecraft mc) {}

    public int getSeconds()
    {
        return 5;
    }

    public GuiButton getButton()
    {
        return this.val$button;
    }
}
