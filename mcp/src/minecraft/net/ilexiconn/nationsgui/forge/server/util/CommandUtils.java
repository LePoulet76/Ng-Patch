/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.command.CommandException
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.nbt.CompressedStreamTools
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.world.storage.SaveHandler
 */
package net.ilexiconn.nationsgui.forge.server.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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

public class CommandUtils {
    public static void writePlayerData(SaveHandler saveHandler, String username, NBTTagCompound playerData) throws IOException {
        File playersDirectory;
        try {
            Field field = SaveHandler.class.getDeclaredField(NationsGUITransformer.inDevelopment ? "playersDirectory" : "field_75771_c");
            field.setAccessible(true);
            playersDirectory = (File)field.get(saveHandler);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new CommandException("Unable to get save directory", new Object[0]);
        }
        File file1 = new File(playersDirectory, username + ".dat.tmp");
        File file2 = new File(playersDirectory, username + ".dat");
        CompressedStreamTools.func_74799_a((NBTTagCompound)playerData, (OutputStream)new FileOutputStream(file1));
        if (file2.exists()) {
            file2.delete();
        }
        file1.renameTo(file2);
    }

    public static boolean isPlayerOnline(String username) {
        return Arrays.asList(MinecraftServer.func_71276_C().func_71213_z()).contains(username);
    }

    public static boolean isPlayerOp(String player) {
        return CommandUtils.getAllOps().contains(player);
    }

    public static List<String> getAllOps() {
        ArrayList<String> ops = new ArrayList<String>();
        for (String p : MinecraftServer.func_71276_C().func_71213_z()) {
            if (!MinecraftServer.func_71276_C().func_71203_ab().func_72353_e(p)) continue;
            ops.add(p);
        }
        return ops;
    }

    public static List<EntityPlayer> getAllPlayers() {
        ArrayList<EntityPlayer> players = new ArrayList<EntityPlayer>();
        for (String p : MinecraftServer.func_71276_C().func_71213_z()) {
            players.add(CommandUtils.getPlayer(p));
        }
        return players;
    }

    public static EntityPlayer getPlayer(String player) {
        return MinecraftServer.func_71276_C().func_71203_ab().func_72361_f(player);
    }
}

