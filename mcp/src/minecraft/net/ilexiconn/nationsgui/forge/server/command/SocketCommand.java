/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.ReflectionHelper
 *  net.minecraft.command.CommandBase
 *  net.minecraft.command.ICommandSender
 *  net.minecraft.network.NetLoginHandler
 *  net.minecraft.network.NetServerHandler
 *  net.minecraft.network.NetworkListenThread
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.ServerListenThread
 *  net.minecraft.server.dedicated.DedicatedServer
 *  net.minecraft.server.dedicated.DedicatedServerListenThread
 */
package net.ilexiconn.nationsgui.forge.server.command;

import cpw.mods.fml.relauncher.ReflectionHelper;
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

public class SocketCommand
extends CommandBase {
    public String func_71517_b() {
        return "socketdebug";
    }

    public String func_71518_a(ICommandSender icommandsender) {
        return null;
    }

    public void func_71515_b(ICommandSender icommandsender, String[] astring) {
        DedicatedServer dedicatedServer = (DedicatedServer)MinecraftServer.func_71276_C();
        List connections = (List)ReflectionHelper.getPrivateValue(NetworkListenThread.class, (Object)dedicatedServer.func_71212_ac(), (String[])new String[]{"connections", "field_71748_d", "c"});
        for (NetServerHandler connection : connections) {
            System.out.println("SocketDebug [NetServerHandler] " + connection.field_72575_b.func_74430_c());
            connection.field_72575_b.func_74424_a("Server Closed", new Object[0]);
        }
        DedicatedServerListenThread dedicatedServerListenThread = (DedicatedServerListenThread)ReflectionHelper.getPrivateValue(DedicatedServer.class, (Object)dedicatedServer, (String[])new String[]{"networkThread", "field_71336_r", "s"});
        ServerListenThread serverListenThread = (ServerListenThread)ReflectionHelper.getPrivateValue(DedicatedServerListenThread.class, (Object)dedicatedServerListenThread, (String[])new String[]{"theServerListenThread", "field_71763_c", "b"});
        List list = (List)ReflectionHelper.getPrivateValue(ServerListenThread.class, (Object)serverListenThread, (String[])new String[]{"pendingConnections", "field_71775_b", "a"});
        for (NetLoginHandler netLoginHandler : list) {
            System.out.println("SocketDebug [NetLoginHandler] " + netLoginHandler.field_72538_b.func_74430_c());
            netLoginHandler.field_72538_b.func_74424_a("Server Closed", new Object[0]);
        }
    }

    public int compareTo(Object o) {
        return 0;
    }

    public boolean func_71519_b(ICommandSender par1ICommandSender) {
        return par1ICommandSender instanceof DedicatedServer;
    }
}

