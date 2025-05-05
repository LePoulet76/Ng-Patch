package net.ilexiconn.nationsgui.forge.server.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

class PlayerContainer$1 extends Slot
{
    final int val$finalY;

    final EntityPlayer val$player;

    final PlayerContainer this$0;

    PlayerContainer$1(PlayerContainer this$0, IInventory x0, int x1, int x2, int x3, int var6, EntityPlayer var7)
    {
        super(x0, x1, x2, x3);
        this.this$0 = this$0;
        this.val$finalY = var6;
        this.val$player = var7;
    }

    /**
     * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1 in the case
     * of armor slots)
     */
    public int getSlotStackLimit()
    {
        return 1;
    }

    /**
     * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
     */
    public boolean isItemValid(ItemStack stack)
    {
        return stack != null && stack.getItem().isValidArmor(stack, this.val$finalY, this.val$player);
    }
}
