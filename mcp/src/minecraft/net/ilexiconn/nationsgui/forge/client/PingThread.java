package net.ilexiconn.nationsgui.forge.client;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import net.minecraft.client.Minecraft;

public class PingThread extends Thread
{
    public static int ping = 0;

    public void run()
    {
        try
        {
            InetSocketAddress e = (InetSocketAddress)Minecraft.getMinecraft().thePlayer.sendQueue.getNetManager().getSocketAddress();
            InetAddress inetAddress = e.getAddress();
            Ping p = new Ping(inetAddress.getHostAddress());
            ping = (int)p.run();
            Thread.sleep(5000L);
        }
        catch (InterruptedException var4)
        {
            var4.printStackTrace();
        }
    }
}
