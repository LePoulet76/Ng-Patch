/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.inventory.Container
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.inventory.Slot
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 */
package net.ilexiconn.nationsgui.forge.server.container;

import net.ilexiconn.nationsgui.forge.server.block.entity.IncubatorBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class IncubatorContainer
extends Container {
    private IncubatorBlockEntity blockEntity;

    public IncubatorContainer(InventoryPlayer inventoryPlayer, IncubatorBlockEntity blockEntity) {
        this.blockEntity = blockEntity;
        this.func_75146_a(new Slot(blockEntity, 0, 134, 38){

            public boolean func_75214_a(ItemStack itemStack) {
                return itemStack.func_77973_b().field_77779_bT == Block.field_111034_cE.field_71990_ca;
            }
        });
        this.func_75146_a(new Slot(blockEntity, 1, 29, 34){

            public boolean func_75214_a(ItemStack itemStack) {
                return itemStack.func_77973_b().field_77779_bT == Item.field_77815_bC.field_77779_bT;
            }
        });
        for (int x = 0; x < 3; ++x) {
            for (int y = 0; y < 9; ++y) {
                this.func_75146_a(new Slot((IInventory)inventoryPlayer, y + x * 9 + 9, 8 + y * 18, 84 + x * 18));
                if (x != 0) continue;
                this.func_75146_a(new Slot((IInventory)inventoryPlayer, y, 8 + y * 18, 142));
            }
        }
    }

    public IncubatorBlockEntity getBlockEntity() {
        return this.blockEntity;
    }

    public boolean func_75145_c(EntityPlayer entityPlayer) {
        return this.blockEntity.func_70300_a(entityPlayer);
    }

    public ItemStack func_82846_b(EntityPlayer entityPlayer, int slot) {
        return null;
    }
}

