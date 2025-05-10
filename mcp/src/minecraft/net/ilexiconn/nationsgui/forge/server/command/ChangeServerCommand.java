package net.ilexiconn.nationsgui.forge.server.command;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.ChangeServerPacket;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;

public class ChangeServerCommand extends CommandBase
{
    public String getCommandName()
    {
        return "changeserver";
    }

    public String getCommandUsage(ICommandSender icommandsender)
    {
        return "/changeserver <Player> <IP> <Port>";
    }

    public void processCommand(ICommandSender icommandsender, String[] astring)
    {
        if (astring.length == 3)
        {
            EntityPlayerMP target = getPlayer(icommandsender, astring[0]);
            PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new ChangeServerPacket(astring[1], Integer.parseInt(astring[2]))), (Player)target);
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
