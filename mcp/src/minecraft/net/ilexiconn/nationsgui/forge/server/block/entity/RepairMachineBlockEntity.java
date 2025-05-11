/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTBase
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.nbt.NBTTagList
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.network.packet.Packet132TileEntityData
 *  net.minecraft.tileentity.TileEntity
 */
package net.ilexiconn.nationsgui.forge.server.block.entity;

import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

public class RepairMachineBlockEntity
extends TileEntity
implements IInventory {
    private ItemStack[] itemStacks = new ItemStack[2];
    private String customName;
    private int updateTick;
    private boolean active;
    public static final int SLOT_COAL = 0;
    public static final int SLOT_ITEM = 1;

    public int func_70302_i_() {
        return this.itemStacks.length;
    }

    public ItemStack func_70301_a(int slot) {
        return this.itemStacks[slot];
    }

    public ItemStack func_70298_a(int slot, int stackSize) {
        if (this.itemStacks[slot] != null) {
            if (this.itemStacks[slot].field_77994_a <= stackSize) {
                ItemStack itemstack = this.itemStacks[slot];
                this.itemStacks[slot] = null;
                return itemstack;
            }
            ItemStack itemstack = this.itemStacks[slot].func_77979_a(stackSize);
            if (this.itemStacks[slot].field_77994_a == 0) {
                this.itemStacks[slot] = null;
            }
            return itemstack;
        }
        return null;
    }

    public ItemStack func_70304_b(int slot) {
        if (this.itemStacks[slot] != null) {
            ItemStack itemstack = this.itemStacks[slot];
            this.itemStacks[slot] = null;
            return itemstack;
        }
        return null;
    }

    public void func_70299_a(int slot, ItemStack itemStack) {
        this.itemStacks[slot] = itemStack;
        if (itemStack != null && itemStack.field_77994_a > this.func_70297_j_()) {
            itemStack.field_77994_a = this.func_70297_j_();
        }
    }

    public String func_70303_b() {
        return this.func_94042_c() ? this.customName : "tile.repair_machine.name";
    }

    public boolean func_94042_c() {
        return this.customName != null && this.customName.length() > 0;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public int func_70297_j_() {
        return 64;
    }

    public boolean func_70300_a(EntityPlayer player) {
        return this.field_70331_k.func_72796_p(this.field_70329_l, this.field_70330_m, this.field_70327_n) != this ? false : player.func_70092_e((double)this.field_70329_l + 0.5, (double)this.field_70330_m + 0.5, (double)this.field_70327_n + 0.5) <= 64.0;
    }

    public void func_70295_k_() {
    }

    public void func_70305_f() {
    }

    public boolean func_94041_b(int slot, ItemStack itemStack) {
        return true;
    }

    public void func_70307_a(NBTTagCompound compound) {
        super.func_70307_a(compound);
        NBTTagList itemsTag = compound.func_74761_m("Items");
        this.itemStacks = new ItemStack[this.func_70302_i_()];
        for (int i = 0; i < itemsTag.func_74745_c(); ++i) {
            NBTTagCompound itemTag = (NBTTagCompound)itemsTag.func_74743_b(i);
            byte slot = itemTag.func_74771_c("Slot");
            if (slot < 0 || slot >= this.itemStacks.length) continue;
            this.itemStacks[slot] = ItemStack.func_77949_a((NBTTagCompound)itemTag);
        }
        if (compound.func_74764_b("CustomName")) {
            this.customName = compound.func_74779_i("CustomName");
        }
    }

    public void func_70310_b(NBTTagCompound compound) {
        super.func_70310_b(compound);
        NBTTagList itemsTag = new NBTTagList();
        for (int i = 0; i < this.itemStacks.length; ++i) {
            if (this.itemStacks[i] == null) continue;
            NBTTagCompound itemTag = new NBTTagCompound();
            itemTag.func_74774_a("Slot", (byte)i);
            this.itemStacks[i].func_77955_b(itemTag);
            itemsTag.func_74742_a((NBTBase)itemTag);
        }
        compound.func_74782_a("Items", (NBTBase)itemsTag);
        if (this.func_94042_c()) {
            compound.func_74778_a("CustomName", this.customName);
        }
    }

    public Packet func_70319_e() {
        NBTTagCompound compound = new NBTTagCompound();
        this.func_70310_b(compound);
        return new Packet132TileEntityData(this.field_70329_l, this.field_70330_m, this.field_70327_n, 3, compound);
    }

    public void func_70316_g() {
        ++this.updateTick;
        if (this.updateTick % 20 == 0) {
            if (this.func_70301_a(0) != null && this.func_70301_a((int)0).func_77973_b().field_77779_bT == Block.field_111034_cE.field_71990_ca && this.func_70301_a(1) != null) {
                if (this.func_70301_a((int)0).field_77994_a >= NationsGUI.CONFIG.repairMachineCoalRate && this.func_70301_a(1).func_77951_h()) {
                    this.active = true;
                    this.func_70301_a((int)0).field_77994_a -= NationsGUI.CONFIG.repairMachineCoalRate;
                    this.func_70301_a(1).func_77964_b(this.func_70301_a(1).func_77960_j() - NationsGUI.CONFIG.repairMachineRepairRate);
                } else {
                    this.active = false;
                }
            } else {
                this.active = false;
            }
        }
    }

    public boolean isActive() {
        return this.active;
    }
}

