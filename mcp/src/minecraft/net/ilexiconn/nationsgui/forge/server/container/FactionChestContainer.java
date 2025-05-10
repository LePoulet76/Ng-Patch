package net.ilexiconn.nationsgui.forge.server.container;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import net.ilexiconn.nationsgui.forge.server.inventory.FactionChestInventory;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionChestSaveDataPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class FactionChestContainer extends Container
{
    private ItemStack[] oldInventory;
    public FactionChestInventory factionChestInventory;
    private int numRows;
    private boolean canTake;
    private boolean canDeposit;
    private String factionName;
    private String playerName;
    private ArrayList<String> itemsMoved = new ArrayList();

    public FactionChestContainer(InventoryPlayer inventoryPlayer, FactionChestInventory chestInventory, boolean canTake, boolean canDeposit, String factionName)
    {
        this.canTake = canTake;
        this.canDeposit = canDeposit;
        this.factionChestInventory = chestInventory;
        this.oldInventory = (ItemStack[])chestInventory.itemStacks.clone();
        this.factionName = factionName;
        this.playerName = inventoryPlayer.player.username;
        this.numRows = chestInventory.getSizeInventory() / 9;
        this.factionChestInventory.openChest();
        int w;
        int z;

        for (w = 0; w < this.numRows; ++w)
        {
            for (z = 0; z < 9; ++z)
            {
                this.addSlotToContainer(new Slot(chestInventory, z + w * 9, 51 + z * 18, 44 + w * 18));
            }
        }

        for (w = 0; w < 3; ++w)
        {
            for (z = 0; z < 9; ++z)
            {
                this.addSlotToContainer(new Slot(inventoryPlayer, z + w * 9 + 9, 46 + z * 18, 240 + w * 18));

                if (w == 0)
                {
                    this.addSlotToContainer(new Slot(inventoryPlayer, z, 46 + z * 18, 298));
                }
            }
        }
    }

    public void writeToNBT()
    {
        this.factionChestInventory.writeToNBT();
    }

    public boolean canInteractWith(EntityPlayer entityplayer)
    {
        return this.factionChestInventory.isUseableByPlayer(entityplayer);
    }

    /**
     * Called when the container is closed.
     */
    public void onContainerClosed(EntityPlayer par1EntityPlayer)
    {
        if (par1EntityPlayer.worldObj.isRemote)
        {
            HashMap data = new HashMap();
            ArrayList difference = this.getDifference(Arrays.asList(this.oldInventory), Arrays.asList(this.factionChestInventory.itemStacks));
            data.put("logs", difference);
            data.put("factionName", this.factionName);
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionChestSaveDataPacket(data)));
        }

        this.writeToNBT();
        super.onContainerClosed(par1EntityPlayer);
    }

    public ItemStack slotClick(int slotId, int par2, int par3, EntityPlayer par4EntityPlayer)
    {
        InventoryPlayer inventoryplayer = par4EntityPlayer.inventory;

        if (slotId >= 0 && slotId < this.numRows * 9)
        {
            if (!this.canTake && ((Slot)this.inventorySlots.get(slotId)).getHasStack())
            {
                return null;
            }

            if (slotId >= this.factionChestInventory.chestLevel * 9 && inventoryplayer.getItemStack() != null)
            {
                return null;
            }
        }
        else if (slotId >= this.numRows * 9 && !this.canDeposit && (inventoryplayer.getItemStack() == null || ((Slot)this.inventorySlots.get(slotId)).getHasStack()) && (inventoryplayer.getItemStack() == null || inventoryplayer.getItemStack().itemID != ((Slot)this.inventorySlots.get(slotId)).getStack().itemID || inventoryplayer.getItemStack().getItemDamage() != ((Slot)this.inventorySlots.get(slotId)).getStack().getItemDamage()))
        {
            return null;
        }

        return super.slotClick(slotId, par2, par3, par4EntityPlayer);
    }

    /**
     * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
     */
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(par2);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (par2 < this.numRows * 9)
            {
                if (!this.canTake)
                {
                    return null;
                }

                if (!this.mergeItemStack(itemstack1, this.numRows * 9, this.inventorySlots.size(), true))
                {
                    return null;
                }

                this.itemsMoved.add(this.convertItemstackToString(itemstack, "removed"));
            }
            else
            {
                if (!this.canDeposit)
                {
                    return null;
                }

                if (!this.mergeItemStack(itemstack1, 0, this.factionChestInventory.chestLevel * 9, false))
                {
                    return null;
                }

                this.itemsMoved.add(this.convertItemstackToString(itemstack, "added"));
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }

    /**
     * merges provided ItemStack with the first avaliable one in the container/player inventory
     */
    protected boolean mergeItemStack(ItemStack par1ItemStack, int par2, int par3, boolean par4)
    {
        boolean flag1 = false;
        int k = par2;

        if (par4)
        {
            k = par3 - 1;
        }

        Slot slot;
        ItemStack itemstack1;

        if (par1ItemStack.isStackable())
        {
            while (par1ItemStack.stackSize > 0 && (!par4 && k < par3 || par4 && k >= par2))
            {
                slot = (Slot)this.inventorySlots.get(k);
                itemstack1 = slot.getStack();

                if (itemstack1 != null && itemstack1.itemID == par1ItemStack.itemID && (!par1ItemStack.getHasSubtypes() || par1ItemStack.getItemDamage() == itemstack1.getItemDamage()) && ItemStack.areItemStackTagsEqual(par1ItemStack, itemstack1))
                {
                    int l = itemstack1.stackSize + par1ItemStack.stackSize;

                    if (l <= par1ItemStack.getMaxStackSize())
                    {
                        par1ItemStack.stackSize = 0;
                        itemstack1.stackSize = l;
                        slot.onSlotChanged();
                        flag1 = true;
                    }
                    else if (itemstack1.stackSize < par1ItemStack.getMaxStackSize())
                    {
                        par1ItemStack.stackSize -= par1ItemStack.getMaxStackSize() - itemstack1.stackSize;
                        itemstack1.stackSize = par1ItemStack.getMaxStackSize();
                        slot.onSlotChanged();
                        flag1 = true;
                    }
                }

                if (par4)
                {
                    --k;
                }
                else
                {
                    ++k;
                }
            }
        }

        if (par1ItemStack.stackSize > 0)
        {
            if (par4)
            {
                k = par3 - 1;
            }
            else
            {
                k = par2;
            }

            while (!par4 && k < par3 || par4 && k >= par2)
            {
                slot = (Slot)this.inventorySlots.get(k);
                itemstack1 = slot.getStack();

                if (itemstack1 == null)
                {
                    slot.putStack(par1ItemStack.copy());
                    slot.onSlotChanged();
                    par1ItemStack.stackSize = 0;
                    flag1 = true;
                    break;
                }

                if (par4)
                {
                    --k;
                }
                else
                {
                    ++k;
                }
            }
        }

        return flag1;
    }

    public ArrayList<String> getDifference(List<ItemStack> oldInv, List<ItemStack> newInv)
    {
        HashMap oldInventoryItems = new HashMap();
        HashMap newInventoryItems = new HashMap();
        HashMap diffItems = new HashMap();
        ArrayList finalDiffItems = new ArrayList();
        Iterator it = oldInv.iterator();
        ItemStack it2;

        while (it.hasNext())
        {
            it2 = (ItemStack)it.next();

            if (it2 != null)
            {
                if (oldInventoryItems.containsKey(it2.itemID + "##" + it2.getItemDamage()))
                {
                    oldInventoryItems.put(it2.itemID + "##" + it2.getItemDamage(), Integer.valueOf(((Integer)oldInventoryItems.get(it2.itemID + "##" + it2.getItemDamage())).intValue() + it2.stackSize));
                }
                else
                {
                    oldInventoryItems.put(it2.itemID + "##" + it2.getItemDamage(), Integer.valueOf(it2.stackSize));
                }
            }
        }

        it = newInv.iterator();

        while (it.hasNext())
        {
            it2 = (ItemStack)it.next();

            if (it2 != null)
            {
                if (newInventoryItems.containsKey(it2.itemID + "##" + it2.getItemDamage()))
                {
                    newInventoryItems.put(it2.itemID + "##" + it2.getItemDamage(), Integer.valueOf(((Integer)newInventoryItems.get(it2.itemID + "##" + it2.getItemDamage())).intValue() + it2.stackSize));
                }
                else
                {
                    newInventoryItems.put(it2.itemID + "##" + it2.getItemDamage(), Integer.valueOf(it2.stackSize));
                }
            }
        }

        it = oldInventoryItems.entrySet().iterator();

        while (it.hasNext())
        {
            Entry it21 = (Entry)it.next();
            String it3 = (String)it21.getKey();
            Integer pair = (Integer)it21.getValue();

            if (!newInventoryItems.containsKey(it3))
            {
                diffItems.put(it3, Integer.valueOf(pair.intValue() * -1));
            }
            else
            {
                diffItems.put(it3, Integer.valueOf(((Integer)newInventoryItems.get(it3)).intValue() - pair.intValue()));
            }
        }

        Iterator it22 = newInventoryItems.entrySet().iterator();

        while (it22.hasNext())
        {
            Entry it31 = (Entry)it22.next();
            String pair1 = (String)it31.getKey();
            Integer itemInfos = (Integer)it31.getValue();

            if (!oldInventoryItems.containsKey(pair1))
            {
                diffItems.put(pair1, itemInfos);
            }
        }

        Iterator it32 = diffItems.entrySet().iterator();

        while (it32.hasNext())
        {
            Entry pair2 = (Entry)it32.next();
            String itemInfos1 = (String)pair2.getKey();
            Integer qte = (Integer)pair2.getValue();
            String type = "added";

            if (qte.intValue() < 0)
            {
                type = "removed";
                qte = Integer.valueOf(qte.intValue() * -1);
            }

            if (qte.intValue() != 0)
            {
                finalDiffItems.add(itemInfos1 + "##" + qte + "##" + this.playerName + "##" + System.currentTimeMillis() + "##" + type);
            }
        }

        return finalDiffItems;
    }

    private String convertItemstackToString(ItemStack itemStack, String type)
    {
        return itemStack.itemID + "##" + itemStack.getItemDamage() + "##" + itemStack.stackSize + "##" + this.playerName + "##" + System.currentTimeMillis() + "##" + type;
    }

    private static ItemStack findSimilarItemStack(Collection<ItemStack> items, ItemStack item)
    {
        Iterator var2 = items.iterator();
        ItemStack citem;

        do
        {
            if (!var2.hasNext())
            {
                return null;
            }

            citem = (ItemStack)var2.next();
        }
        while (item == null || citem == null || !item.isItemEqual(citem));

        return citem;
    }
}
