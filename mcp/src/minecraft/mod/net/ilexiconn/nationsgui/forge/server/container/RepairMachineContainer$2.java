package net.ilexiconn.nationsgui.forge.server.container;

import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

class RepairMachineContainer$2 extends Slot
{
    final RepairMachineContainer this$0;

    RepairMachineContainer$2(RepairMachineContainer this$0, IInventory x0, int x1, int x2, int x3)
    {
        super(x0, x1, x2, x3);
        this.this$0 = this$0;
    }

    /**
     * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
     */
    public boolean isItemValid(ItemStack itemStack)
    {
        return itemStack.isItemDamaged() && !NationsGUI.CONFIG.repairMachineBlacklist.contains(Integer.valueOf(itemStack.getItem().itemID));
    }
}
