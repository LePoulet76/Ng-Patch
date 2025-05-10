package net.ilexiconn.nationsgui.forge.server.command;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.PingPacket;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

public class PingCommand extends CommandBase
{
    public String getCommandName()
    {
        return "nping";
    }

    public String getCommandUsage(ICommandSender icommandsender)
    {
        return null;
    }

    public void processCommand(ICommandSender icommandsender, String[] astring)
    {
        PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new PingPacket(icommandsender.getCommandSenderName())), (Player)getPlayer(icommandsender, astring[0]));
    }

    public int compareTo(Object o)
    {
        return 0;
    }
}
