package net.ilexiconn.nationsgui.forge.server.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class GiveSpecificCommand extends CommandBase
{
    public String getCommandName()
    {
        return "givef";
    }

    public String getCommandUsage(ICommandSender sender)
    {
        return "/givef <player> <id:meta> <amount> <specialdata:value>";
    }

    public void processCommand(ICommandSender sender, String[] args)
    {
        if (args.length < 3)
        {
            throw new CommandException(this.getCommandUsage(sender), new Object[0]);
        }
        else
        {
            String target = args[0];
            String itemMeta = args[1];
            int amount = Integer.parseInt(args[2]);
            int itemID = Integer.parseInt(itemMeta.split(":")[0]);
            int meta = itemMeta.split(":").length > 1 ? Integer.parseInt(itemMeta.split(":")[1]) : 0;
            ItemStack itemStack = new ItemStack(itemID, amount, meta);
            NBTTagCompound nbtTagCompound = new NBTTagCompound();

            if (args.length > 3)
            {
                for (int player = 3; player < args.length; ++player)
                {
                    String[] data = args[player].split(":");

                    if (data.length > 1)
                    {
                        if (data[0].equalsIgnoreCase("RocketFuel"))
                        {
                            nbtTagCompound.setInteger("RocketFuel", Integer.parseInt(data[1]));
                        }
                        else if (data[0].equalsIgnoreCase("RocketDurability"))
                        {
                            nbtTagCompound.setInteger("RocketDurability", Integer.parseInt(data[1]));
                        }
                        else if (data[0].equalsIgnoreCase("nuclearPower"))
                        {
                            nbtTagCompound.setFloat("nuclearPower", Float.parseFloat(data[1]));
                        }
                        else if (data[0].equalsIgnoreCase("missileFuel"))
                        {
                            nbtTagCompound.setFloat("missileFuel", Float.parseFloat(data[1]));
                        }
                    }
                }
            }

            if (!nbtTagCompound.hasNoTags())
            {
                itemStack.setTagCompound(nbtTagCompound);
            }

            EntityPlayerMP var12 = CommandBase.getPlayer(sender, target);
            var12.inventory.addItemStackToInventory(itemStack);
            var12.inventoryContainer.detectAndSendChanges();
            System.out.println("Gave " + target + " " + amount + " of " + itemID + ":" + meta);
        }
    }

    public int compareTo(Object o)
    {
        return this.compareTo((ICommand)o);
    }
}
