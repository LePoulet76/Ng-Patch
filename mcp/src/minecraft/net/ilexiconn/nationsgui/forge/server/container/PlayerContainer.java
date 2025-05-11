/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.inventory.ContainerPlayer
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.inventory.Slot
 *  net.minecraft.inventory.SlotCrafting
 *  net.minecraft.item.ItemStack
 */
package net.ilexiconn.nationsgui.forge.server.container;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;

public class PlayerContainer
extends ContainerPlayer {
    public PlayerContainer(InventoryPlayer playerInventory, boolean localWorld, final EntityPlayer player) {
        super(playerInventory, localWorld, player);
        int x;
        int y;
        super.func_75146_a((Slot)new SlotCrafting(playerInventory.field_70458_d, (IInventory)this.field_75181_e, this.field_75179_f, 0, 155, 105));
        for (y = 0; y < 2; ++y) {
            for (x = 0; x < 2; ++x) {
                super.func_75146_a(new Slot((IInventory)this.field_75181_e, x + y * 2, 110 + x * 18, 87 + y * 18));
            }
        }
        y = 0;
        while (y < 4) {
            final int finalY = y++;
            super.func_75146_a(new Slot((IInventory)playerInventory, 36 + (3 - finalY), 11, 51 + finalY * 18){

                public int func_75219_a() {
                    return 1;
                }

                public boolean func_75214_a(ItemStack stack) {
                    return stack != null && stack.func_77973_b().isValidArmor(stack, finalY, (Entity)player);
                }
            });
        }
        for (y = 0; y < 3; ++y) {
            for (x = 0; x < 9; ++x) {
                super.func_75146_a(new Slot((IInventory)playerInventory, x + (y + 1) * 9, 11 + x * 18, 141 + y * 18));
            }
        }
        for (int x2 = 0; x2 < 9; ++x2) {
            super.func_75146_a(new Slot((IInventory)playerInventory, x2, 11 + x2 * 18, 199));
        }
        this.func_75130_a((IInventory)this.field_75181_e);
    }

    protected Slot func_75146_a(Slot slot) {
        return slot;
    }
}

