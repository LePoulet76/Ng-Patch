package net.ilexiconn.nationsgui.forge.server.packet.impl;

import java.util.TimerTask;
import net.ilexiconn.nationsgui.forge.client.gui.BonusesGui;
import net.minecraft.client.Minecraft;

class BonusDataPacket$1 extends TimerTask
{
    final BonusDataPacket this$0;

    BonusDataPacket$1(BonusDataPacket this$0)
    {
        this.this$0 = this$0;
    }

    public void run()
    {
        Minecraft.getMinecraft().displayGuiScreen(new BonusesGui());
    }
}
