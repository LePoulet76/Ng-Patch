package net.ilexiconn.nationsgui.forge.client.gui.summary;

import net.ilexiconn.nationsgui.forge.server.permission.IPermissionCallback;
import net.minecraft.client.gui.GuiButton;

class SummaryGUI$1 implements IPermissionCallback
{
    final GuiButton val$buttonShop;

    final SummaryGUI this$0;

    SummaryGUI$1(SummaryGUI this$0, GuiButton var2)
    {
        this.this$0 = this$0;
        this.val$buttonShop = var2;
    }

    public void call(String permission, boolean has)
    {
        this.val$buttonShop.enabled = has;
    }
}
