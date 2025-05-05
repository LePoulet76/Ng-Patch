package net.ilexiconn.nationsgui.forge.server.command;

import cpw.mods.fml.relauncher.ReflectionHelper;
import java.util.Iterator;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.NetServerHandler;
import net.minecraft.network.NetworkListenThread;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerListenThread;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.dedicated.DedicatedServerListenThread;

public class SocketCommand extends CommandBase
{
    public String getCommandName()
    {
        return "socketdebug";
    }

    public String getCommandUsage(ICommandSender icommandsender)
    {
        return null;
    }

    public void processCommand(ICommandSender icommandsender, String[] astring)
    {
        DedicatedServer dedicatedServer = (DedicatedServer)MinecraftServer.getServer();
        List connections = (List)ReflectionHelper.getPrivateValue(NetworkListenThread.class, dedicatedServer.getNetworkThread(), new String[] {"connections", "connections", "c"});
        Iterator dedicatedServerListenThread = connections.iterator();

        while (dedicatedServerListenThread.hasNext())
        {
            NetServerHandler serverListenThread = (NetServerHandler)dedicatedServerListenThread.next();
            System.out.println("SocketDebug [NetServerHandler] " + serverListenThread.netManager.getSocketAddress());
            serverListenThread.netManager.networkShutdown("Server Closed", new Object[0]);
        }

        DedicatedServerListenThread dedicatedServerListenThread1 = (DedicatedServerListenThread)ReflectionHelper.getPrivateValue(DedicatedServer.class, dedicatedServer, new String[] {"networkThread", "networkThread", "s"});
        ServerListenThread serverListenThread1 = (ServerListenThread)ReflectionHelper.getPrivateValue(DedicatedServerListenThread.class, dedicatedServerListenThread1, new String[] {"theServerListenThread", "theServerListenThread", "b"});
        List list = (List)ReflectionHelper.getPrivateValue(ServerListenThread.class, serverListenThread1, new String[] {"pendingConnections", "pendingConnections", "a"});
        Iterator var8 = list.iterator();

        while (var8.hasNext())
        {
            NetLoginHandler netLoginHandler = (NetLoginHandler)var8.next();
            System.out.println("SocketDebug [NetLoginHandler] " + netLoginHandler.myTCPConnection.getSocketAddress());
            netLoginHandler.myTCPConnection.networkShutdown("Server Closed", new Object[0]);
        }
    }

    public int compareTo(Object o)
    {
        return 0;
    }

    /**
     * Returns true if the given command sender is allowed to use this command.
     */
    public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender instanceof DedicatedServer;
    }
}
