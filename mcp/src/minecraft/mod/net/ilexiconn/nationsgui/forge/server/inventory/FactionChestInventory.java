package net.ilexiconn.nationsgui.forge.server.inventory;

import net.ilexiconn.nationsgui.forge.server.config.NBTConfig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class FactionChestInventory implements IInventory
{
    public ItemStack[] itemStacks;
    private String customName;
    private String owner;
    public ItemStack[] content;
    public int size = 54;
    public String factionId;
    public int chestLevel;

    public FactionChestInventory(String factionId, int chestLevel)
    {
        this.chestLevel = chestLevel;
        this.factionId = factionId;
        this.itemStacks = new ItemStack[this.size];
        this.readFromNBT(factionId);
    }

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory()
    {
        return this.itemStacks.length;
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getStackInSlot(int slot)
    {
        return this.itemStacks[slot];
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    public ItemStack decrStackSize(int slot, int stackSize)
    {
        if (this.itemStacks[slot] != null)
        {
            ItemStack itemstack;

            if (this.itemStacks[slot].stackSize <= stackSize)
            {
                itemstack = this.itemStacks[slot];
                this.itemStacks[slot] = null;
                return itemstack;
            }
            else
            {
                itemstack = this.itemStacks[slot].splitStack(stackSize);

                if (this.itemStacks[slot].stackSize == 0)
                {
                    this.itemStacks[slot] = null;
                }

                return itemstack;
            }
        }
        else
        {
            return null;
        }
    }

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    public ItemStack getStackInSlotOnClosing(int slot)
    {
        if (this.itemStacks[slot] != null)
        {
            ItemStack itemstack = this.itemStacks[slot];
            this.itemStacks[slot] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setInventorySlotContents(int slot, ItemStack itemStack)
    {
        this.itemStacks[slot] = itemStack;

        if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit())
        {
            itemStack.stackSize = this.getInventoryStackLimit();
        }
    }

    /**
     * Returns the name of the inventory.
     */
    public String getInvName()
    {
        return this.isInvNameLocalized() ? this.customName : this.factionId;
    }

    /**
     * If this returns false, the inventory name will be used as an unlocalized name, and translated into the player's
     * language. Otherwise it will be used directly.
     */
    public boolean isInvNameLocalized()
    {
        return this.customName != null && this.customName.length() > 0;
    }

    public void setCustomName(String customName)
    {
        this.customName = customName;
    }

    public String getOwner()
    {
        return this.owner;
    }

    public void setOwner(String owner)
    {
        this.owner = owner;
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
     * this more of a set than a get?*
     */
    public int getInventoryStackLimit()
    {
        return 64;
    }

    /**
     * Called when an the contents of an Inventory change, usually
     */
    public void onInventoryChanged() {}

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean isUseableByPlayer(EntityPlayer entityPlayer)
    {
        return true;
    }

    public void openChest() {}

    public void closeChest()
    {
        this.writeToNBT();
    }

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
     */
    public boolean isItemValidForSlot(int slot, ItemStack itemStack)
    {
        return true;
    }

    public void readFromNBT(String factionId)
    {
        NBTTagCompound compound = (NBTTagCompound)NBTConfig.CONFIG.getCompound().getTag("FactionChest");
        NBTTagList itemsTag = compound.getTagList(factionId);
        this.itemStacks = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < itemsTag.tagCount(); ++i)
        {
            NBTTagCompound itemTag = (NBTTagCompound)itemsTag.tagAt(i);
            byte slot = itemTag.getByte("Slot");

            if (slot >= 0 && slot < this.itemStacks.length)
            {
                this.itemStacks[slot] = ItemStack.loadItemStackFromNBT(itemTag);
            }
        }

        if (compound.hasKey("CustomName"))
        {
            this.customName = compound.getString("CustomName");
        }

        if (compound.hasKey("Owner"))
        {
            this.owner = compound.getString("Owner");
        }
    }

    public void writeToNBT()
    {
        NBTTagCompound compound = (NBTTagCompound)NBTConfig.CONFIG.getCompound().getTag("FactionChest");
        NBTTagList itemsTag = new NBTTagList();

        for (int i = 0; i < this.itemStacks.length; ++i)
        {
            if (this.itemStacks[i] != null)
            {
                NBTTagCompound itemTag = new NBTTagCompound();
                itemTag.setByte("Slot", (byte)i);
                this.itemStacks[i].writeToNBT(itemTag);
                itemsTag.appendTag(itemTag);
            }
        }

        compound.setTag(this.factionId, itemsTag);

        if (this.isInvNameLocalized())
        {
            compound.setString("CustomName", this.customName);
        }

        if (this.owner != null && this.owner.length() > 0)
        {
            compound.setString("Owner", this.owner);
        }

        NBTConfig.CONFIG.save();
    }
}
