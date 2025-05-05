package net.ilexiconn.nationsgui.forge.server.block.entity;

import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

public class RepairMachineBlockEntity extends TileEntity implements IInventory
{
    private ItemStack[] itemStacks = new ItemStack[2];
    private String customName;
    private int updateTick;
    private boolean active;
    public static final int SLOT_COAL = 0;
    public static final int SLOT_ITEM = 1;

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
        return this.isInvNameLocalized() ? this.customName : "tile.repair_machine.name";
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

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
     * this more of a set than a get?*
     */
    public int getInventoryStackLimit()
    {
        return 64;
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean isUseableByPlayer(EntityPlayer player)
    {
        return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
    }

    public void openChest() {}

    public void closeChest() {}

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
     */
    public boolean isItemValidForSlot(int slot, ItemStack itemStack)
    {
        return true;
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        NBTTagList itemsTag = compound.getTagList("Items");
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
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
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

        compound.setTag("Items", itemsTag);

        if (this.isInvNameLocalized())
        {
            compound.setString("CustomName", this.customName);
        }
    }

    /**
     * Overriden in a sign to provide the text.
     */
    public Packet getDescriptionPacket()
    {
        NBTTagCompound compound = new NBTTagCompound();
        this.writeToNBT(compound);
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 3, compound);
    }

    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    public void updateEntity()
    {
        ++this.updateTick;

        if (this.updateTick % 20 == 0)
        {
            if (this.getStackInSlot(0) != null && this.getStackInSlot(0).getItem().itemID == Block.coalBlock.blockID && this.getStackInSlot(1) != null)
            {
                if (this.getStackInSlot(0).stackSize >= NationsGUI.CONFIG.repairMachineCoalRate && this.getStackInSlot(1).isItemDamaged())
                {
                    this.active = true;
                    ItemStack var10000 = this.getStackInSlot(0);
                    var10000.stackSize -= NationsGUI.CONFIG.repairMachineCoalRate;
                    this.getStackInSlot(1).setItemDamage(this.getStackInSlot(1).getItemDamage() - NationsGUI.CONFIG.repairMachineRepairRate);
                }
                else
                {
                    this.active = false;
                }
            }
            else
            {
                this.active = false;
            }
        }
    }

    public boolean isActive()
    {
        return this.active;
    }
}
