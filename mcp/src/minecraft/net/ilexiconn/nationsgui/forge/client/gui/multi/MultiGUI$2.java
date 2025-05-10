package net.ilexiconn.nationsgui.forge.client.gui.multi;

import java.io.IOException;
import net.minecraft.client.multiplayer.ServerData;

class MultiGUI$2 implements Runnable
{
    final int val$finalI;

    final MultiGUI this$0;

    MultiGUI$2(MultiGUI this$0, int var2)
    {
        this.this$0 = this$0;
        this.val$finalI = var2;
    }

    public void run()
    {
        ServerData serverData = this.this$0.multiServers.getServerData(this.val$finalI);

        try
        {
            long e = System.nanoTime();
            MultiGUI.access$000(serverData);
            long var2 = System.nanoTime();
            serverData.pingToServer = (var2 - e) / 1000000L;
        }
        catch (IOException var6)
        {
            var6.printStackTrace();
        }
    }
}
