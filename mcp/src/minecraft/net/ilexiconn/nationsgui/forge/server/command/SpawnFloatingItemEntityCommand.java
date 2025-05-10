package net.ilexiconn.nationsgui.forge.server.command;

import java.util.List;
import net.ilexiconn.nationsgui.forge.server.ServerUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatMessageComponent;

public class SpawnFloatingItemEntityCommand extends CommandBase
{
    public String getCommandName()
    {
        return "spawnfloatingitem";
    }

    public String getCommandUsage(ICommandSender icommandsender)
    {
        return "/spawnfloatingitem <id> <meta>";
    }

    public void processCommand(ICommandSender icommandsender, String[] astring)
    {
        if (astring.length > 1)
        {
            ServerUtils.spawnFloatingItemAt(icommandsender.getPlayerCoordinates().posX, icommandsender.getPlayerCoordinates().posY, icommandsender.getPlayerCoordinates().posZ, icommandsender.getEntityWorld().getWorldInfo().getWorldName(), Integer.parseInt(astring[0]), Integer.parseInt(astring[1]), 4.0F, 0.0F, 0.0F, 0.0F, false);
        }
        else
        {
            icommandsender.sendChatToPlayer(ChatMessageComponent.createFromText("\u00a7cUsage: /spawnfloatingitem <id> <meta>"));
        }
    }

    public int compareTo(Object o)
    {
        return 0;
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        return null;
    }

    /**
     * Return whether the specified command parameter index is a username parameter.
     */
    public boolean isUsernameIndex(String[] par1ArrayOfStr, int par2)
    {
        return par2 == 0;
    }
}
