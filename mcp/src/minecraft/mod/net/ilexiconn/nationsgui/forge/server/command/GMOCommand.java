package net.ilexiconn.nationsgui.forge.server.command;

import java.io.IOException;
import java.util.List;
import net.ilexiconn.nationsgui.forge.server.util.CommandUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.world.EnumGameType;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.storage.SaveHandler;

public class GMOCommand extends CommandBase
{
    public String getCommandName()
    {
        return "gmo";
    }

    public String getCommandUsage(ICommandSender sender)
    {
        return "/gmo <gamemode> <player>";
    }

    public void processCommand(ICommandSender sender, String[] args)
    {
        if (args.length != 2)
        {
            throw new CommandException(this.getCommandUsage(sender), new Object[0]);
        }
        else
        {
            EnumGameType gameType = this.getGameModeFromCommand(sender, args[0]);
            String username = args[1];

            if (CommandUtils.isPlayerOnline(username))
            {
                EntityPlayerMP saveHandler = CommandBase.getPlayer(sender, username);
                saveHandler.setGameType(gameType);
                saveHandler.fallDistance = 0.0F;
            }
            else
            {
                if (!(sender.getEntityWorld().getSaveHandler() instanceof SaveHandler))
                {
                    throw new CommandException("Using deprecated save system", new Object[0]);
                }

                SaveHandler saveHandler1 = (SaveHandler)sender.getEntityWorld().getSaveHandler();
                NBTTagCompound playerData = saveHandler1.getPlayerData(username);

                if (playerData == null)
                {
                    throw new CommandException("Player not found", new Object[0]);
                }

                playerData.setInteger("playerGameType", gameType.getID());

                try
                {
                    CommandUtils.writePlayerData(saveHandler1, username, playerData);
                }
                catch (IOException var8)
                {
                    throw new CommandException("Unable to write player data", new Object[0]);
                }
            }

            CommandBase.notifyAdmins(sender, 1, "commands.gamemode.success.other", new Object[] {username, ChatMessageComponent.createFromTranslationKey("gameMode." + gameType.getName())});
        }
    }

    private EnumGameType getGameModeFromCommand(ICommandSender sender, String arg)
    {
        return !arg.equalsIgnoreCase(EnumGameType.SURVIVAL.getName()) && !arg.equalsIgnoreCase("s") ? (!arg.equalsIgnoreCase(EnumGameType.CREATIVE.getName()) && !arg.equalsIgnoreCase("c") ? (!arg.equalsIgnoreCase(EnumGameType.ADVENTURE.getName()) && !arg.equalsIgnoreCase("a") ? WorldSettings.getGameTypeById(CommandBase.parseIntBounded(sender, arg, 0, EnumGameType.values().length - 2)) : EnumGameType.ADVENTURE) : EnumGameType.CREATIVE) : EnumGameType.SURVIVAL;
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    public List addTabCompletionOptions(ICommandSender sender, String[] args)
    {
        return args.length == 1 ? CommandBase.getListOfStringsMatchingLastWord(args, new String[] {"survival", "creative", "adventure"}): null;
    }

    public int compareTo(Object o)
    {
        return this.compareTo((ICommand)o);
    }
}
