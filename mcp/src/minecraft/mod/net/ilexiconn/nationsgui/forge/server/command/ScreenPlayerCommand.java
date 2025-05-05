package net.ilexiconn.nationsgui.forge.server.command;

import java.util.List;
import net.ilexiconn.nationsgui.forge.server.command.ScreenPlayerCommand$LinkRequester;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

public class ScreenPlayerCommand extends CommandBase
{
    public String getCommandName()
    {
        return "screen";
    }

    public String getCommandUsage(ICommandSender icommandsender)
    {
        return "\u00a7c/screen <player>";
    }

    public void processCommand(ICommandSender icommandsender, String[] args)
    {
        if (icommandsender instanceof EntityPlayer)
        {
            EntityPlayer sender = (EntityPlayer)icommandsender;
            EntityPlayer player = sender.getEntityWorld().getPlayerEntityByName(args[0]);

            if (args.length > 0 && player != null)
            {
                Thread t = new Thread(new ScreenPlayerCommand$LinkRequester(sender, player));
                t.setDaemon(true);
                t.start();
            }
        }
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        return par2ArrayOfStr.length == 1 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getAllUsernames()) : null;
    }

    public int compareTo(Object o)
    {
        return 0;
    }
}
