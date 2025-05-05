package net.ilexiconn.nationsgui.forge.server.voices.commands;

import java.util.List;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.server.command.utils.DateUtil;
import net.ilexiconn.nationsgui.forge.server.util.Translation;
import net.ilexiconn.nationsgui.forge.server.voices.VoiceChat;
import net.ilexiconn.nationsgui.forge.server.voices.networking.DataManager;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatMessageComponent;

public class VoiceGlobalMuteCommand extends CommandBase
{
    /**
     * Return the required permission level for this command.
     */
    public int getRequiredPermissionLevel()
    {
        return 2;
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    public List addTabCompletionOptions(ICommandSender par1iCommandSender, String[] par2ArrayOfStr)
    {
        return par2ArrayOfStr.length == 1 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getAllUsernames()) : null;
    }

    /**
     * Return whether the specified command parameter index is a username parameter.
     */
    public boolean isUsernameIndex(String[] par1ArrayOfStr, int par2)
    {
        return par2 == 0;
    }

    public String getCommandName()
    {
        return "vmute";
    }

    public String getCommandUsage(ICommandSender icommandsender)
    {
        return "Usage: /vmute <player> [datediff]";
    }

    public void processCommand(ICommandSender sender, String[] args)
    {
        if (args.length > 0 && args.length < 3 && args[0].length() > 0)
        {
            EntityPlayerMP player = getPlayer(sender, args[0]);
            long time = Long.MAX_VALUE;

            if (args.length == 2)
            {
                try
                {
                    time = DateUtil.parseDateDiff(args[1], true);
                }
                catch (Exception var9)
                {
                    sender.sendChatToPlayer(ChatMessageComponent.createFromText(this.getCommandUsage(sender)));
                    return;
                }
            }

            if (player != null)
            {
                DataManager dataManager = VoiceChat.getServerInstance().getServerNetwork().getDataManager();
                Map mutedPlayers = dataManager.mutedPlayers;

                if (mutedPlayers.containsKey(player.username))
                {
                    mutedPlayers.remove(player.username);
                    sender.sendChatToPlayer(ChatMessageComponent.createFromText(Translation.get("\u00a7aLe joueur \u00a72<player> \u00a7apeut de nouveau utiliser le chat vocal.").replaceAll("<player>", player.getDisplayName())));
                    player.addChatMessage(Translation.get("\u00a7aVous pouvez de nouveau utiliser le chat vocal."));
                }
                else
                {
                    mutedPlayers.put(player.username, Long.valueOf(time));

                    if (time == Long.MAX_VALUE)
                    {
                        player.addChatMessage(Translation.get("\u00a7cVous avez \u00e9t\u00e9 mute du chat vocal, vous ne pouvez plus l\'utiliser."));
                        sender.sendChatToPlayer(ChatMessageComponent.createFromText(Translation.get("\u00a7cLe joueur \u00a74<player> \u00a7cne peut plus parler en vocal.").replaceAll("<player>", player.getDisplayName())));
                    }
                    else
                    {
                        String s = DateUtil.formatDateDiff(time);
                        player.addChatMessage(Translation.get("\u00a7cVous avez \u00e9t\u00e9 mute du chat vocal, vous ne pouvez plus l\'utiliser pendant \u00a74<time>").replaceAll("<time>", s));
                        sender.sendChatToPlayer(ChatMessageComponent.createFromText(Translation.get("\u00a7cLe joueur \u00a74<player> \u00a7cne peut plus parler en vocal pendant \u00a74<time>").replaceAll("<player>", player.getDisplayName()).replaceAll("<time>", s)));
                    }
                }

                dataManager.save();
            }
            else
            {
                player.addChatMessage(Translation.get("\u00a7cLe joueur est introuvable"));
            }
        }
        else
        {
            throw new CommandException(this.getCommandUsage(sender), new Object[0]);
        }
    }

    public int compareTo(Object o)
    {
        return 0;
    }
}
