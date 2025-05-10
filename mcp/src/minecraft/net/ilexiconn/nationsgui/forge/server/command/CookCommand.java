package net.ilexiconn.nationsgui.forge.server.command;

import java.util.List;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.server.util.Translation;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.ChatMessageComponent;

public class CookCommand extends CommandBase
{
    public String getCommandName()
    {
        return "cook";
    }

    public String getCommandUsage(ICommandSender icommandsender)
    {
        return "/cook";
    }

    public void processCommand(ICommandSender icommandsender, String[] astring)
    {
        if (icommandsender instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer)icommandsender;
            ItemStack[] playerInventory = player.inventory.mainInventory;
            Map recipes = FurnaceRecipes.smelting().getSmeltingList();
            int amountChanged = 0;

            for (int i = 0; i < playerInventory.length; ++i)
            {
                if (playerInventory[i] != null)
                {
                    int id = playerInventory[i].itemID;

                    if (recipes.containsKey(Integer.valueOf(id)) && ((ItemStack)recipes.get(Integer.valueOf(id))).itemID != 263)
                    {
                        playerInventory[i] = (ItemStack)recipes.get(Integer.valueOf(id));
                        ++amountChanged;
                    }
                }
            }

            if (amountChanged != 0)
            {
                player.inventory.inventoryChanged = true;
                player.sendChatToPlayer(ChatMessageComponent.createFromText(Translation.get("\u00a7aVous venez de faire cuire les objets de votre inventaire.")));
            }
            else
            {
                player.sendChatToPlayer(ChatMessageComponent.createFromText(Translation.get("\u00a7cVotre inventaire ne contient que des objets ne pouvant pas \u00eatre cuits.")));
            }
        }
    }

    public int compareTo(Object o)
    {
        return 0;
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        return null;
    }

    /**
     * Return whether the specified command parameter index is a username parameter.
     */
    public boolean isUsernameIndex(String[] par1ArrayOfStr, int par2)
    {
        return par2 == 0;
    }
}
