package net.ilexiconn.nationsgui.forge.client;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.TimerTask;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.RefreshChannelPacket;

class ClientEventHandler$4 extends TimerTask
{
    final ClientEventHandler this$0;

    ClientEventHandler$4(ClientEventHandler this$0)
    {
        this.this$0 = this$0;
    }

    public void run()
    {
        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new RefreshChannelPacket()));
    }
}
