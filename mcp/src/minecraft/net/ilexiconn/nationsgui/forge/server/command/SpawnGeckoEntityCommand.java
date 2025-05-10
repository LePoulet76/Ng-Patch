package net.ilexiconn.nationsgui.forge.server.command;

import java.util.List;
import net.ilexiconn.nationsgui.forge.server.ServerUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatMessageComponent;

public class SpawnGeckoEntityCommand extends CommandBase
{
    public String getCommandName()
    {
        return "spawngeckoentity";
    }

    public String getCommandUsage(ICommandSender icommandsender)
    {
        return "/spawngeckoentity <name> <stalk:true> <health:true>";
    }

    public void processCommand(ICommandSender icommandsender, String[] astring)
    {
        boolean isStalking = false;
        boolean hideHealthBar = false;

        if (astring.length > 0)
        {
            if (astring.length > 1)
            {
                isStalking = astring[1].equalsIgnoreCase("true");

                if (astring.length > 2)
                {
                    hideHealthBar = astring[2].equalsIgnoreCase("true");
                }
            }

            if (astring[0].contains("package"))
            {
                ServerUtils.spawnGeckoCarePackageAt(icommandsender.getPlayerCoordinates().posX, icommandsender.getPlayerCoordinates().posY, icommandsender.getPlayerCoordinates().posZ, icommandsender.getEntityWorld().getWorldInfo().getWorldName(), astring[0], 20.0D);
            }
            else
            {
                ServerUtils.spawnGeckoEntityAt(icommandsender.getPlayerCoordinates().posX, icommandsender.getPlayerCoordinates().posY, icommandsender.getPlayerCoordinates().posZ, icommandsender.getEntityWorld().getWorldInfo().getWorldName(), astring[0], 20.0D, isStalking, hideHealthBar);
            }
        }
        else
        {
            icommandsender.sendChatToPlayer(ChatMessageComponent.createFromText("\u00a7cUsage: /spawngeckoentity <nom> <stalk? true|false> <hidehealthbar? true|false>"));
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
