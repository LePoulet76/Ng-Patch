/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.command.CommandBase
 *  net.minecraft.command.CommandException
 *  net.minecraft.command.ICommand
 *  net.minecraft.command.ICommandSender
 *  net.minecraft.entity.player.EntityPlayerMP
 *  net.minecraft.inventory.ContainerPlayer
 *  net.minecraft.inventory.InventoryCrafting
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTBase
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.nbt.NBTTagList
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.world.storage.SaveHandler
 */
package net.ilexiconn.nationsgui.forge.server.command;

import java.io.IOException;
import java.util.Arrays;
import net.ilexiconn.nationsgui.forge.server.util.CommandUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.storage.SaveHandler;

public class COCommand
extends CommandBase {
    public String func_71517_b() {
        return "co";
    }

    public String func_71518_a(ICommandSender sender) {
        return "/co <player>";
    }

    public void func_71515_b(ICommandSender sender, String[] args) {
        if (args.length != 1) {
            throw new CommandException(this.func_71518_a(sender), new Object[0]);
        }
        String username = args[0];
        int totalCleared = 0;
        StringBuilder logBuilder = new StringBuilder().append("Clearing inventory of player: ").append(username);
        if (Arrays.asList(MinecraftServer.func_71276_C().func_71213_z()).contains(username)) {
            EntityPlayerMP player = CommandBase.func_82359_c((ICommandSender)sender, (String)username);
            for (ItemStack item : player.field_71071_by.field_70462_a) {
                if (item == null) continue;
                logBuilder.append("\n").append("(x").append(item.field_77994_a).append(")").append(item.field_77993_c).append(":").append(item.func_77960_j());
            }
            totalCleared = player.field_71071_by.func_82347_b(-1, -1);
            if (player.field_71069_bz instanceof ContainerPlayer) {
                ContainerPlayer containerPlayer = (ContainerPlayer)player.field_71069_bz;
                InventoryCrafting inventoryCrafting = containerPlayer.field_75181_e;
                for (int i = 0; i < inventoryCrafting.func_70302_i_(); ++i) {
                    if (inventoryCrafting.func_70301_a(i) != null) {
                        ++totalCleared;
                    }
                    inventoryCrafting.func_70299_a(i, null);
                }
                if (containerPlayer.field_75179_f.func_70301_a(0) != null) {
                    ++totalCleared;
                    containerPlayer.field_75179_f.func_70299_a(0, null);
                }
            }
            player.field_71069_bz.func_75142_b();
            if (!player.field_71075_bZ.field_75098_d) {
                player.func_71113_k();
            }
        } else {
            if (!(sender.func_130014_f_().func_72860_G() instanceof SaveHandler)) {
                throw new CommandException("Using deprecated save system", new Object[0]);
            }
            SaveHandler saveHandler = (SaveHandler)sender.func_130014_f_().func_72860_G();
            NBTTagCompound playerData = saveHandler.func_75764_a(username);
            if (playerData == null) {
                throw new CommandException("Player not found", new Object[0]);
            }
            playerData.func_82580_o("Inventory");
            playerData.func_74782_a("Inventory", (NBTBase)new NBTTagList("Inventory"));
            totalCleared = -1;
            try {
                CommandUtils.writePlayerData(saveHandler, username, playerData);
            }
            catch (IOException e) {
                throw new CommandException("Unable to write player data", new Object[0]);
            }
        }
        MinecraftServer.func_71276_C().func_71244_g(logBuilder.toString());
        CommandBase.func_71522_a((ICommandSender)sender, (String)"commands.clear.success", (Object[])new Object[]{username, totalCleared});
    }

    public int compareTo(Object o) {
        return this.func_71525_a((ICommand)o);
    }
}

