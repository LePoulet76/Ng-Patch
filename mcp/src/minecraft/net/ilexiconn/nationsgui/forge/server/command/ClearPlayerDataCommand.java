/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.command.CommandBase
 *  net.minecraft.command.CommandException
 *  net.minecraft.command.ICommandSender
 *  net.minecraft.nbt.CompressedStreamTools
 *  net.minecraft.nbt.NBTBase
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.nbt.NBTTagList
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.util.ChatMessageComponent
 *  net.minecraft.world.storage.SaveHandler
 */
package net.ilexiconn.nationsgui.forge.server.command;

import java.io.File;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.world.storage.SaveHandler;

public class ClearPlayerDataCommand
extends CommandBase {
    public String func_71517_b() {
        return "clearplayerdata";
    }

    public String func_71518_a(ICommandSender iCommandSender) {
        return "[potions] [player]";
    }

    public void func_71515_b(ICommandSender iCommandSender, String[] strings) {
        if (strings.length < 2) {
            throw new CommandException("wrongFormat", new Object[0]);
        }
        SaveHandler saveHandler = (SaveHandler)MinecraftServer.func_71276_C().func_130014_f_().func_72860_G();
        NBTTagCompound nbtTagCompound = saveHandler.func_75764_a(strings[1]);
        if (nbtTagCompound == null) {
            iCommandSender.func_70006_a(ChatMessageComponent.func_111066_d((String)"Player not found"));
            return;
        }
        if (strings[0].equals("potions")) {
            nbtTagCompound.func_74782_a("ActiveEffects", (NBTBase)new NBTTagList());
        }
        this.writePlayerData(new File(saveHandler.func_75765_b(), "players"), nbtTagCompound, strings[1]);
    }

    public int compareTo(Object o) {
        return 0;
    }

    public boolean func_82358_a(String[] strings, int i) {
        return i == 1;
    }

    private void writePlayerData(File playersDirectory, NBTTagCompound nbttagcompound, String playerName) {
        try {
            File file1 = new File(playersDirectory, playerName + ".dat.tmp");
            File file2 = new File(playersDirectory, playerName + ".dat");
            CompressedStreamTools.func_74799_a((NBTTagCompound)nbttagcompound, (OutputStream)Files.newOutputStream(file1.toPath(), new OpenOption[0]));
            if (file2.exists()) {
                file2.delete();
            }
            file1.renameTo(file2);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            MinecraftServer.func_71276_C().func_98033_al().func_98236_b("Failed to save player data for " + playerName);
        }
    }
}

