package net.ilexiconn.nationsgui.forge.server.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.ilexiconn.nationsgui.forge.server.asm.NationsGUITransformer;
import net.minecraft.command.CommandException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.storage.SaveHandler;

public class CommandUtils
{
    public static void writePlayerData(SaveHandler saveHandler, String username, NBTTagCompound playerData) throws IOException
    {
        File playersDirectory;

        try
        {
            Field file1 = SaveHandler.class.getDeclaredField(NationsGUITransformer.inDevelopment ? "playersDirectory" : "playersDirectory");
            file1.setAccessible(true);
            playersDirectory = (File)file1.get(saveHandler);
        }
        catch (Exception var6)
        {
            var6.printStackTrace();
            throw new CommandException("Unable to get save directory", new Object[0]);
        }

        File file11 = new File(playersDirectory, username + ".dat.tmp");
        File file2 = new File(playersDirectory, username + ".dat");
        CompressedStreamTools.writeCompressed(playerData, new FileOutputStream(file11));

        if (file2.exists())
        {
            file2.delete();
        }

        file11.renameTo(file2);
    }

    public static boolean isPlayerOnline(String username)
    {
        return Arrays.asList(MinecraftServer.getServer().getAllUsernames()).contains(username);
    }

    public static boolean isPlayerOp(String player)
    {
        return getAllOps().contains(player);
    }

    public static List<String> getAllOps()
    {
        ArrayList ops = new ArrayList();
        String[] var1 = MinecraftServer.getServer().getAllUsernames();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3)
        {
            String p = var1[var3];

            if (MinecraftServer.getServer().getConfigurationManager().isPlayerOpped(p))
            {
                ops.add(p);
            }
        }

        return ops;
    }

    public static List<EntityPlayer> getAllPlayers()
    {
        ArrayList players = new ArrayList();
        String[] var1 = MinecraftServer.getServer().getAllUsernames();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3)
        {
            String p = var1[var3];
            players.add(getPlayer(p));
        }

        return players;
    }

    public static EntityPlayer getPlayer(String player)
    {
        return MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(player);
    }
}
