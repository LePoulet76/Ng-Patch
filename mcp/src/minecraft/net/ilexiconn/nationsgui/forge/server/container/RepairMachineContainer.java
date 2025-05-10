package net.ilexiconn.nationsgui.forge.server.container;

import net.ilexiconn.nationsgui.forge.server.block.entity.RepairMachineBlockEntity;
import net.ilexiconn.nationsgui.forge.server.container.RepairMachineContainer$1;
import net.ilexiconn.nationsgui.forge.server.container.RepairMachineContainer$2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class RepairMachineContainer extends Container
{
    private RepairMachineBlockEntity blockEntity;

    public RepairMachineContainer(InventoryPlayer inventoryPlayer, RepairMachineBlockEntity blockEntity)
    {
        this.blockEntity = blockEntity;
        this.addSlotToContainer(new RepairMachineContainer$1(this, blockEntity, 0, 116, 38));
        this.addSlotToContainer(new RepairMachineContainer$2(this, blockEntity, 1, 47, 34));

        for (int x = 0; x < 3; ++x)
        {
            for (int y = 0; y < 9; ++y)
            {
                this.addSlotToContainer(new Slot(inventoryPlayer, y + x * 9 + 9, 8 + y * 18, 84 + x * 18));

                if (x == 0)
                {
                    this.addSlotToContainer(new Slot(inventoryPlayer, y, 8 + y * 18, 142));
                }
            }
        }
    }

    public RepairMachineBlockEntity getBlockEntity()
    {
        return this.blockEntity;
    }

    public boolean canInteractWith(EntityPlayer entityPlayer)
    {
        return this.blockEntity.isUseableByPlayer(entityPlayer);
    }

    /**
     * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
     */
    public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int slot)
    {
        return null;
    }
}
