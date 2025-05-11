/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.command.CommandBase
 *  net.minecraft.command.CommandException
 *  net.minecraft.command.ICommand
 *  net.minecraft.command.ICommandSender
 *  net.minecraft.entity.player.EntityPlayerMP
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 */
package net.ilexiconn.nationsgui.forge.server.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class GiveSpecificCommand
extends CommandBase {
    public String func_71517_b() {
        return "givef";
    }

    public String func_71518_a(ICommandSender sender) {
        return "/givef <player> <id:meta> <amount> <specialdata:value>";
    }

    public void func_71515_b(ICommandSender sender, String[] args) {
        if (args.length < 3) {
            throw new CommandException(this.func_71518_a(sender), new Object[0]);
        }
        String target = args[0];
        String itemMeta = args[1];
        int amount = Integer.parseInt(args[2]);
        int itemID = Integer.parseInt(itemMeta.split(":")[0]);
        int meta = itemMeta.split(":").length > 1 ? Integer.parseInt(itemMeta.split(":")[1]) : 0;
        ItemStack itemStack = new ItemStack(itemID, amount, meta);
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        if (args.length > 3) {
            for (int i = 3; i < args.length; ++i) {
                String[] data = args[i].split(":");
                if (data.length <= 1) continue;
                if (data[0].equalsIgnoreCase("RocketFuel")) {
                    nbtTagCompound.func_74768_a("RocketFuel", Integer.parseInt(data[1]));
                    continue;
                }
                if (data[0].equalsIgnoreCase("RocketDurability")) {
                    nbtTagCompound.func_74768_a("RocketDurability", Integer.parseInt(data[1]));
                    continue;
                }
                if (data[0].equalsIgnoreCase("nuclearPower")) {
                    nbtTagCompound.func_74776_a("nuclearPower", Float.parseFloat(data[1]));
                    continue;
                }
                if (!data[0].equalsIgnoreCase("missileFuel")) continue;
                nbtTagCompound.func_74776_a("missileFuel", Float.parseFloat(data[1]));
            }
        }
        if (!nbtTagCompound.func_82582_d()) {
            itemStack.func_77982_d(nbtTagCompound);
        }
        EntityPlayerMP player = CommandBase.func_82359_c((ICommandSender)sender, (String)target);
        player.field_71071_by.func_70441_a(itemStack);
        player.field_71069_bz.func_75142_b();
        System.out.println("Gave " + target + " " + amount + " of " + itemID + ":" + meta);
    }

    public int compareTo(Object o) {
        return this.func_71525_a((ICommand)o);
    }
}

