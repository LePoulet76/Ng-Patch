package net.ilexiconn.nationsgui.forge.server.command;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.JoinWaitingPacket;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;

public class JoinWaitingCommand extends CommandBase
{
    public String getCommandName()
    {
        return "joinwaiting";
    }

    public String getCommandUsage(ICommandSender icommandsender)
    {
        return "/joinwaiting <Server> <IP> <Port>";
    }

    public void processCommand(ICommandSender icommandsender, String[] astring)
    {
        if (astring.length == 3)
        {
            EntityPlayerMP target = getPlayer(icommandsender, icommandsender.getCommandSenderName());
            PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new JoinWaitingPacket(astring[0], astring[1], Integer.parseInt(astring[2]))), (Player)target);
        }
    }

    public int compareTo(Object o)
    {
        return 0;
    }

    /**
     * Return whether the specified command parameter index is a username parameter.
     */
    public boolean isUsernameIndex(String[] par1ArrayOfStr, int par2)
    {
        return par2 == 0;
    }
}
