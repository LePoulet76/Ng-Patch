package net.ilexiconn.nationsgui.forge.server.command;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.world.storage.SaveHandler;

public class ClearPlayerDataCommand extends CommandBase
{
    public String getCommandName()
    {
        return "clearplayerdata";
    }

    public String getCommandUsage(ICommandSender iCommandSender)
    {
        return "[potions] [player]";
    }

    public void processCommand(ICommandSender iCommandSender, String[] strings)
    {
        if (strings.length < 2)
        {
            throw new CommandException("wrongFormat", new Object[0]);
        }
        else
        {
            SaveHandler saveHandler = (SaveHandler)MinecraftServer.getServer().getEntityWorld().getSaveHandler();
            NBTTagCompound nbtTagCompound = saveHandler.getPlayerData(strings[1]);

            if (nbtTagCompound == null)
            {
                iCommandSender.sendChatToPlayer(ChatMessageComponent.createFromText("Player not found"));
            }
            else
            {
                if (strings[0].equals("potions"))
                {
                    nbtTagCompound.setTag("ActiveEffects", new NBTTagList());
                }

                this.writePlayerData(new File(saveHandler.getWorldDirectory(), "players"), nbtTagCompound, strings[1]);
            }
        }
    }

    public int compareTo(Object o)
    {
        return 0;
    }

    /**
     * Return whether the specified command parameter index is a username parameter.
     */
    public boolean isUsernameIndex(String[] strings, int i)
    {
        return i == 1;
    }

    private void writePlayerData(File playersDirectory, NBTTagCompound nbttagcompound, String playerName)
    {
        try
        {
            File exception = new File(playersDirectory, playerName + ".dat.tmp");
            File file2 = new File(playersDirectory, playerName + ".dat");
            CompressedStreamTools.writeCompressed(nbttagcompound, Files.newOutputStream(exception.toPath(), new OpenOption[0]));

            if (file2.exists())
            {
                file2.delete();
            }

            exception.renameTo(file2);
        }
        catch (Exception var6)
        {
            var6.printStackTrace();
            MinecraftServer.getServer().getLogAgent().logWarning("Failed to save player data for " + playerName);
        }
    }
}
