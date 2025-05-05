package net.ilexiconn.nationsgui.forge.server.command;

import fr.nationsglory.server.item.tools.electric.IElectricTool;
import icbm.explosion.ICBMExplosion;
import icbm.explosion.items.ItemMissile;
import icbm.explosion.items.ItemRocketLauncher;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatMessageComponent;

public class CommandRefill extends CommandBase
{
    public String getCommandName()
    {
        return "refill";
    }

    public String getCommandUsage(ICommandSender icommandsender)
    {
        return null;
    }

    public void processCommand(ICommandSender icommandsender, String[] astring)
    {
        if (icommandsender instanceof EntityPlayer)
        {
            EntityPlayer entityPlayer = (EntityPlayer)icommandsender;
            ItemStack itemStack = entityPlayer.getHeldItem();

            if (itemStack == null)
            {
                entityPlayer.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("nationsgui.command.refill.refused"));
                return;
            }

            boolean done = false;

            if (itemStack.itemID == ICBMExplosion.itemMissile.itemID)
            {
                if (itemStack.hasTagCompound())
                {
                    itemStack.getTagCompound().setFloat("missileFuel", ItemMissile.getMaxFuelQuantity(itemStack));
                    done = true;
                }
            }
            else if (itemStack.itemID == ICBMExplosion.itemRocketLauncher.itemID)
            {
                if (itemStack.hasTagCompound())
                {
                    ItemRocketLauncher itemRocketLauncher = (ItemRocketLauncher)itemStack.getItem();
                    itemRocketLauncher.setEnergy(itemStack, itemRocketLauncher.getEnergyCapacity(itemStack));
                    done = true;
                }
            }
            else if (itemStack.getItem() instanceof IElectricTool)
            {
                ((IElectricTool)itemStack.getItem()).setCharge(itemStack, ((IElectricTool)itemStack.getItem()).getMaxCharge());
                done = true;
            }

            if (done)
            {
                entityPlayer.sendChatToPlayer(ChatMessageComponent.createFromTranslationWithSubstitutions("nationsgui.command.refill.done", new Object[] {itemStack.getDisplayName()}));
            }
            else
            {
                entityPlayer.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("nationsgui.command.refill.refused"));
            }
        }
    }

    public int compareTo(Object o)
    {
        return 0;
    }
}
