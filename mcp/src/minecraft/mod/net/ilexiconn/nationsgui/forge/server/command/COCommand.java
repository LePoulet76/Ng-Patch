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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.storage.SaveHandler;

public class COCommand extends CommandBase
{
    public String getCommandName()
    {
        return "co";
    }

    public String getCommandUsage(ICommandSender sender)
    {
        return "/co <player>";
    }

    public void processCommand(ICommandSender sender, String[] args)
    {
        if (args.length != 1)
        {
            throw new CommandException(this.getCommandUsage(sender), new Object[0]);
        }
        else
        {
            String username = args[0];
            boolean totalCleared = false;
            StringBuilder logBuilder = (new StringBuilder()).append("Clearing inventory of player: ").append(username);
            int var12;

            if (Arrays.asList(MinecraftServer.getServer().getAllUsernames()).contains(username))
            {
                EntityPlayerMP saveHandler = CommandBase.getPlayer(sender, username);
                ItemStack[] playerData = saveHandler.inventory.mainInventory;
                int e = playerData.length;
                int i;

                for (i = 0; i < e; ++i)
                {
                    ItemStack item = playerData[i];

                    if (item != null)
                    {
                        logBuilder.append("\n").append("(x").append(item.stackSize).append(")").append(item.itemID).append(":").append(item.getItemDamage());
                    }
                }

                var12 = saveHandler.inventory.clearInventory(-1, -1);

                if (saveHandler.inventoryContainer instanceof ContainerPlayer)
                {
                    ContainerPlayer var14 = (ContainerPlayer)saveHandler.inventoryContainer;
                    InventoryCrafting var16 = var14.craftMatrix;

                    for (i = 0; i < var16.getSizeInventory(); ++i)
                    {
                        if (var16.getStackInSlot(i) != null)
                        {
                            ++var12;
                        }

                        var16.setInventorySlotContents(i, (ItemStack)null);
                    }

                    if (var14.craftResult.getStackInSlot(0) != null)
                    {
                        ++var12;
                        var14.craftResult.setInventorySlotContents(0, (ItemStack)null);
                    }
                }

                saveHandler.inventoryContainer.detectAndSendChanges();

                if (!saveHandler.capabilities.isCreativeMode)
                {
                    saveHandler.updateHeldItem();
                }
            }
            else
            {
                if (!(sender.getEntityWorld().getSaveHandler() instanceof SaveHandler))
                {
                    throw new CommandException("Using deprecated save system", new Object[0]);
                }

                SaveHandler var13 = (SaveHandler)sender.getEntityWorld().getSaveHandler();
                NBTTagCompound var15 = var13.getPlayerData(username);

                if (var15 == null)
                {
                    throw new CommandException("Player not found", new Object[0]);
                }

                var15.removeTag("Inventory");
                var15.setTag("Inventory", new NBTTagList("Inventory"));
                var12 = -1;

                try
                {
                    CommandUtils.writePlayerData(var13, username, var15);
                }
                catch (IOException var11)
                {
                    throw new CommandException("Unable to write player data", new Object[0]);
                }
            }

            MinecraftServer.getServer().logInfo(logBuilder.toString());
            CommandBase.notifyAdmins(sender, "commands.clear.success", new Object[] {username, Integer.valueOf(var12)});
        }
    }

    public int compareTo(Object o)
    {
        return this.compareTo((ICommand)o);
    }
}
