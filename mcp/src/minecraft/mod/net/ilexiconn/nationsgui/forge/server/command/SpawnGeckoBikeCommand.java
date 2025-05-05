package net.ilexiconn.nationsgui.forge.server.command;

import java.util.List;
import net.ilexiconn.nationsgui.forge.server.ServerUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatMessageComponent;

public class SpawnGeckoBikeCommand extends CommandBase
{
    public String getCommandName()
    {
        return "spawngeckobike";
    }

    public String getCommandUsage(ICommandSender icommandsender)
    {
        return "/spawngeckobike <name> <flying?>";
    }

    public void processCommand(ICommandSender icommandsender, String[] astring)
    {
        if (icommandsender instanceof EntityPlayer)
        {
            if (astring.length > 0)
            {
                ServerUtils.spawnGeckoBike((EntityPlayer)icommandsender, icommandsender.getPlayerCoordinates().posX, icommandsender.getPlayerCoordinates().posY, icommandsender.getPlayerCoordinates().posZ, icommandsender.getEntityWorld().getWorldInfo().getWorldName(), astring[0], astring.length > 1 && astring[1].equalsIgnoreCase("true"));
            }
            else
            {
                icommandsender.sendChatToPlayer(ChatMessageComponent.createFromText("\u00a7cUsage: /spawngeckobike <nom> <flying?>"));
            }
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
