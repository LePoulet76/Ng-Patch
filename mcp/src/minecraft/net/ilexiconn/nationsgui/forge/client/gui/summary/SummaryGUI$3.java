package net.ilexiconn.nationsgui.forge.client.gui.summary;

import java.util.ArrayList;
import java.util.Iterator;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.main.MainGUI;
import net.ilexiconn.nationsgui.forge.client.gui.summary.SummaryGUI$IButtonCallback;
import net.ilexiconn.nationsgui.forge.server.util.SoundStreamer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.stats.StatList;

class SummaryGUI$3 implements SummaryGUI$IButtonCallback
{
    final GuiButton val$button;

    final SummaryGUI this$0;

    SummaryGUI$3(SummaryGUI this$0, GuiButton var2)
    {
        this.this$0 = this$0;
        this.val$button = var2;
    }

    public void call(Minecraft mc)
    {
        ClientData.lastPlayerWantDisconnect = Long.valueOf(System.currentTimeMillis());
        mc.statFileWriter.readStat(StatList.leaveGameStat, 1);
        mc.theWorld.sendQuittingDisconnectingPacket();
        mc.loadWorld((WorldClient)null);
        mc.displayGuiScreen(new MainGUI());
        Iterator var2 = (new ArrayList(ClientProxy.STREAMER_LIST)).iterator();

        while (var2.hasNext())
        {
            SoundStreamer player = (SoundStreamer)var2.next();
            player.forceClose();
        }

        ClientProxy.STREAMER_LIST.clear();
    }

    public int getSeconds()
    {
        return 3;
    }

    public GuiButton getButton()
    {
        return this.val$button;
    }
}
