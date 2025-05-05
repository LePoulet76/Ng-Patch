package net.ilexiconn.nationsgui.forge.server.command;

import java.io.IOException;
import net.ilexiconn.nationsgui.forge.server.util.CommandUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.storage.SaveHandler;

public class POCommand extends CommandBase
{
    public String getCommandName()
    {
        return "po";
    }

    public String getCommandUsage(ICommandSender sender)
    {
        return "/po <parachute> <player>";
    }

    public void processCommand(ICommandSender sender, String[] args)
    {
        if (args.length != 2)
        {
            throw new CommandException(this.getCommandUsage(sender), new Object[0]);
        }
        else
        {
            boolean parachute = CommandBase.func_110662_c(sender, args[0]);
            String username = args[1];

            if (!(sender.getEntityWorld().getSaveHandler() instanceof SaveHandler))
            {
                throw new CommandException("Using deprecated save system", new Object[0]);
            }
            else
            {
                SaveHandler saveHandler = (SaveHandler)sender.getEntityWorld().getSaveHandler();
                NBTTagCompound playerData = saveHandler.getPlayerData(username);

                if (playerData == null)
                {
                    throw new CommandException("Player not found", new Object[0]);
                }
                else
                {
                    playerData.setBoolean("usingParachute", parachute);

                    try
                    {
                        CommandUtils.writePlayerData(saveHandler, username, playerData);
                    }
                    catch (IOException var8)
                    {
                        throw new CommandException("Unable to write player data", new Object[0]);
                    }
                }
            }
        }
    }

    public int compareTo(Object o)
    {
        return 0;
    }
}
