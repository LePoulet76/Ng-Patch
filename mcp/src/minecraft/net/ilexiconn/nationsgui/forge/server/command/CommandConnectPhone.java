package net.ilexiconn.nationsgui.forge.server.command;

import joptsimple.internal.Strings;
import net.ilexiconn.nationsgui.forge.server.ServerProxy;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.network.packet.Packet201PlayerInfo;
import net.minecraft.server.MinecraftServer;

public class CommandConnectPhone extends CommandBase
{
    public String getCommandName()
    {
        return "connectphone";
    }

    public String getCommandUsage(ICommandSender icommandsender)
    {
        return null;
    }

    public void processCommand(ICommandSender icommandsender, String[] astring)
    {
        String name = Strings.join(astring, " ");
        ServerProxy.mobilePlayers.add(name);
        MinecraftServer.getServerConfigurationManager(MinecraftServer.getServer()).sendPacketToAllPlayers(new Packet201PlayerInfo(name, true, -42));
    }

    public int compareTo(Object o)
    {
        return 0;
    }
}
