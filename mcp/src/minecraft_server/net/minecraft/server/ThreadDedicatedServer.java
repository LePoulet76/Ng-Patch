package net.minecraft.server;

import net.minecraft.server.dedicated.DedicatedServer;

public final class ThreadDedicatedServer extends Thread
{
    final DedicatedServer connectedDedicatedServer;

    public ThreadDedicatedServer(DedicatedServer par1DedicatedServer)
    {
        this.connectedDedicatedServer = par1DedicatedServer;
    }

    public void run()
    {
        this.connectedDedicatedServer.stopServer();
    }
}
