package net.ilexiconn.nationsgui.forge.server.trade;

import cpw.mods.fml.relauncher.ReflectionHelper;
import java.util.HashMap;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.server.trade.ContainerTrade$SlotTrader;
import net.ilexiconn.nationsgui.forge.server.trade.enums.EnumTradeState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.world.storage.IPlayerFileData;

public class ContainerTrade extends Container
{
    private EntityPlayer player;
    public InventoryCrafting craftMatrix = new InventoryCrafting(this, 7, 2);
    public EnumTradeState state;

    public ContainerTrade(EntityPlayer player)
    {
        this.state = EnumTradeState.NONE;
        this.player = player;
        int j;
        int k;

        for (j = 0; j < 3; ++j)
        {
            for (k = 0; k < 9; ++k)
            {
                this.addSlotToContainer(new Slot(player.inventory, k + j * 9 + 9, 230 + k * 18, 95 + j * 18));

                if (j == 0)
                {
                    this.addSlotToContainer(new Slot(player.inventory, k, 230 + k * 18, 153));
                }
            }
        }

        for (j = 0; j < 2; ++j)
        {
            for (k = 0; k < 7; ++k)
            {
                this.addSlotToContainer(new ContainerTrade$SlotTrader(this, this.craftMatrix, k + j * 7, 66 + k * 18, 57 + j * 18));
            }
        }
    }

    /**
     * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
     */
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int slot)
    {
        return null;
    }

    public boolean canInteractWith(EntityPlayer player)
    {
        if (player.worldObj.isRemote)
        {
            return this.state != EnumTradeState.YOU_ACCEPTED;
        }
        else
        {
            TradeData data = TradeData.get(player);
            boolean trading = data.isTrading();

            if (!trading)
            {
                data.closeTrade();
            }

            return trading && this.state != EnumTradeState.YOU_ACCEPTED;
        }
    }

    /**
     * Called when the container is closed.
     */
    public void onContainerClosed(EntityPlayer player)
    {
        InventoryPlayer inventoryplayer = player.inventory;

        if (inventoryplayer.getItemStack() != null)
        {
            if (!player.inventory.addItemStackToInventory(inventoryplayer.getItemStack()))
            {
                player.dropPlayerItem(inventoryplayer.getItemStack());
            }

            inventoryplayer.setItemStack((ItemStack)null);
        }

        if (!player.worldObj.isRemote)
        {
            for (int playerFileData = 0; playerFileData < this.craftMatrix.getSizeInventory(); ++playerFileData)
            {
                ItemStack itemstack = this.craftMatrix.getStackInSlotOnClosing(playerFileData);

                if (itemstack != null)
                {
                    System.out.println("[TRADE] GIVE ITEM BACK TO " + player.getDisplayName() + " : " + itemstack.itemID + " x" + itemstack.stackSize);
                }

                if (!player.inventory.addItemStackToInventory(itemstack) && itemstack != null)
                {
                    System.out.println("[TRADE] FAIL (drop) GIVE ITEM BACK TO " + player.getDisplayName() + " : " + itemstack.itemID + " x" + itemstack.stackSize + " - Location X:" + Math.floor(player.posX) + " Y:" + Math.floor(player.posY) + " Z:" + Math.floor(player.posZ));
                    player.dropPlayerItem(itemstack);
                }
            }

            if (player.isDead)
            {
                IPlayerFileData var5 = (IPlayerFileData)ReflectionHelper.getPrivateValue(ServerConfigurationManager.class, MinecraftServer.getServer().getConfigurationManager(), new String[] {"playerNBTManagerObj", "playerNBTManagerObj"});
                var5.writePlayerData(player);
            }
        }
    }

    public NBTTagCompound itemsToComp()
    {
        NBTTagCompound compound = new NBTTagCompound();
        NBTTagList list = new NBTTagList();

        for (int i = 0; i < this.craftMatrix.getSizeInventory(); ++i)
        {
            ItemStack item = this.craftMatrix.getStackInSlot(i);

            if (item != null)
            {
                NBTTagCompound c = new NBTTagCompound();
                c.setInteger("Slot", i);
                item.writeToNBT(c);
                list.appendTag(c);
            }
        }

        compound.setTag("Items", list);
        return compound;
    }

    public static Map CompToItem(NBTTagCompound compound)
    {
        HashMap items = new HashMap();
        NBTTagList list = compound.getTagList("Items");

        for (int i = 0; i < list.tagCount(); ++i)
        {
            NBTTagCompound c = (NBTTagCompound)list.tagAt(i);
            int slot = c.getInteger("Slot");
            ItemStack item = ItemStack.loadItemStackFromNBT(c);

            if (item != null)
            {
                items.put(Integer.valueOf(slot), item);
            }
        }

        return items;
    }

    static EntityPlayer access$000(ContainerTrade x0)
    {
        return x0.player;
    }
}
