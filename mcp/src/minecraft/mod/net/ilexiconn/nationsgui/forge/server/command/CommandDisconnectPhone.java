package net.ilexiconn.nationsgui.forge.server.command;

import joptsimple.internal.Strings;
import net.ilexiconn.nationsgui.forge.server.ServerProxy;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.network.packet.Packet201PlayerInfo;
import net.minecraft.server.MinecraftServer;

public class CommandDisconnectPhone extends CommandBase
{
    public String getCommandName()
    {
        return "disconnectphone";
    }

    public String getCommandUsage(ICommandSender icommandsender)
    {
        return null;
    }

    public void processCommand(ICommandSender icommandsender, String[] astring)
    {
        String name = Strings.join(astring, " ");
        ServerProxy.mobilePlayers.remove(name);
        MinecraftServer.getServerConfigurationManager(MinecraftServer.getServer()).sendPacketToAllPlayers(new Packet201PlayerInfo(name, false, -42));
    }

    public int compareTo(Object o)
    {
        return 0;
    }
}
