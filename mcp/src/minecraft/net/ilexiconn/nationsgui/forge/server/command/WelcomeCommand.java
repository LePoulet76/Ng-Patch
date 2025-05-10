package net.ilexiconn.nationsgui.forge.server.command;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import fr.nationsglory.itemmanager.CommonProxy;
import java.util.List;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FirstConnectionPacket;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;

public class WelcomeCommand extends CommandBase
{
    public String getCommandName()
    {
        return "welcome";
    }

    public String getCommandUsage(ICommandSender icommandsender)
    {
        return "/welcome <pseudo>";
    }

    public void processCommand(ICommandSender icommandsender, String[] astring)
    {
        if (astring.length > 0)
        {
            FirstConnectionPacket firstConnectionPacket = new FirstConnectionPacket();
            firstConnectionPacket.serverName = CommonProxy.localConfig.getServerName();
            firstConnectionPacket.forceOpen = true;
            firstConnectionPacket.serverType = CommonProxy.localConfig.getServerType();
            PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(firstConnectionPacket), (Player)getPlayer(icommandsender, astring[0]));
        }
        else
        {
            throw new WrongUsageException("/welcome <pseudo>", new Object[0]);
        }
    }

    public int compareTo(Object o)
    {
        return 0;
    }

    /**
     * Return the required permission level for this command.
     */
    public int getRequiredPermissionLevel()
    {
        return 4;
    }

    /**
     * Return whether the specified command parameter index is a username parameter.
     */
    public boolean isUsernameIndex(String[] par1ArrayOfStr, int par2)
    {
        return par2 == 0;
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        return par2ArrayOfStr.length == 1 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getAllUsernames()) : null;
    }
}
