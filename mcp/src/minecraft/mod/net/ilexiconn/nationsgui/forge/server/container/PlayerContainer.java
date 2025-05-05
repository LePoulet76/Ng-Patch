package net.ilexiconn.nationsgui.forge.server.container;

import net.ilexiconn.nationsgui.forge.server.container.PlayerContainer$1;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;

public class PlayerContainer extends ContainerPlayer
{
    public PlayerContainer(InventoryPlayer playerInventory, boolean localWorld, EntityPlayer player)
    {
        super(playerInventory, localWorld, player);
        super.addSlotToContainer(new SlotCrafting(playerInventory.player, this.craftMatrix, this.craftResult, 0, 155, 105));
        int x;
        int x1;

        for (x = 0; x < 2; ++x)
        {
            for (x1 = 0; x1 < 2; ++x1)
            {
                super.addSlotToContainer(new Slot(this.craftMatrix, x1 + x * 2, 110 + x1 * 18, 87 + x * 18));
            }
        }

        for (x = 0; x < 4; ++x)
        {
            super.addSlotToContainer(new PlayerContainer$1(this, playerInventory, 36 + (3 - x), 11, 51 + x * 18, x, player));
        }

        for (x = 0; x < 3; ++x)
        {
            for (x1 = 0; x1 < 9; ++x1)
            {
                super.addSlotToContainer(new Slot(playerInventory, x1 + (x + 1) * 9, 11 + x1 * 18, 141 + x * 18));
            }
        }

        for (x = 0; x < 9; ++x)
        {
            super.addSlotToContainer(new Slot(playerInventory, x, 11 + x * 18, 199));
        }

        this.onCraftMatrixChanged(this.craftMatrix);
    }

    /**
     * the slot is assumed empty
     */
    protected Slot addSlotToContainer(Slot slot)
    {
        return slot;
    }
}
