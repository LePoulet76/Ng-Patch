/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.inventory.Container
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.inventory.Slot
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.packet.Packet
 */
package net.ilexiconn.nationsgui.forge.server.container;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.server.inventory.FactionChestInventory;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionChestSaveDataPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;

public class FactionChestContainer
extends Container {
    private ItemStack[] oldInventory;
    public FactionChestInventory factionChestInventory;
    private int numRows;
    private boolean canTake;
    private boolean canDeposit;
    private String factionName;
    private String playerName;
    private ArrayList<String> itemsMoved = new ArrayList();

    public FactionChestContainer(InventoryPlayer inventoryPlayer, FactionChestInventory chestInventory, boolean canTake, boolean canDeposit, String factionName) {
        this.canTake = canTake;
        this.canDeposit = canDeposit;
        this.factionChestInventory = chestInventory;
        this.oldInventory = (ItemStack[])chestInventory.itemStacks.clone();
        this.factionName = factionName;
        this.playerName = inventoryPlayer.field_70458_d.field_71092_bJ;
        this.numRows = chestInventory.func_70302_i_() / 9;
        this.factionChestInventory.func_70295_k_();
        for (int x = 0; x < this.numRows; ++x) {
            for (int y = 0; y < 9; ++y) {
                this.func_75146_a(new Slot((IInventory)chestInventory, y + x * 9, 51 + y * 18, 44 + x * 18));
            }
        }
        for (int w = 0; w < 3; ++w) {
            for (int z = 0; z < 9; ++z) {
                this.func_75146_a(new Slot((IInventory)inventoryPlayer, z + w * 9 + 9, 46 + z * 18, 240 + w * 18));
                if (w != 0) continue;
                this.func_75146_a(new Slot((IInventory)inventoryPlayer, z, 46 + z * 18, 298));
            }
        }
    }

    public void writeToNBT() {
        this.factionChestInventory.writeToNBT();
    }

    public boolean func_75145_c(EntityPlayer entityplayer) {
        return this.factionChestInventory.func_70300_a(entityplayer);
    }

    public void func_75134_a(EntityPlayer par1EntityPlayer) {
        if (par1EntityPlayer.field_70170_p.field_72995_K) {
            HashMap<String, Object> data = new HashMap<String, Object>();
            ArrayList<String> difference = this.getDifference(Arrays.asList(this.oldInventory), Arrays.asList(this.factionChestInventory.itemStacks));
            data.put("logs", difference);
            data.put("factionName", this.factionName);
            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionChestSaveDataPacket(data)));
        }
        this.writeToNBT();
        super.func_75134_a(par1EntityPlayer);
    }

    public ItemStack func_75144_a(int slotId, int par2, int par3, EntityPlayer par4EntityPlayer) {
        InventoryPlayer inventoryplayer = par4EntityPlayer.field_71071_by;
        if (slotId >= 0 && slotId < this.numRows * 9) {
            if (!this.canTake && ((Slot)this.field_75151_b.get(slotId)).func_75216_d()) {
                return null;
            }
            if (slotId >= this.factionChestInventory.chestLevel * 9 && inventoryplayer.func_70445_o() != null) {
                return null;
            }
        } else if (!(slotId < this.numRows * 9 || this.canDeposit || inventoryplayer.func_70445_o() != null && !((Slot)this.field_75151_b.get(slotId)).func_75216_d() || inventoryplayer.func_70445_o() != null && inventoryplayer.func_70445_o().field_77993_c == ((Slot)this.field_75151_b.get((int)slotId)).func_75211_c().field_77993_c && inventoryplayer.func_70445_o().func_77960_j() == ((Slot)this.field_75151_b.get(slotId)).func_75211_c().func_77960_j())) {
            return null;
        }
        return super.func_75144_a(slotId, par2, par3, par4EntityPlayer);
    }

    public ItemStack func_82846_b(EntityPlayer par1EntityPlayer, int par2) {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.field_75151_b.get(par2);
        if (slot != null && slot.func_75216_d()) {
            ItemStack itemstack1 = slot.func_75211_c();
            itemstack = itemstack1.func_77946_l();
            if (par2 < this.numRows * 9) {
                if (!this.canTake) {
                    return null;
                }
                if (!this.func_75135_a(itemstack1, this.numRows * 9, this.field_75151_b.size(), true)) {
                    return null;
                }
                this.itemsMoved.add(this.convertItemstackToString(itemstack, "removed"));
            } else {
                if (!this.canDeposit) {
                    return null;
                }
                if (!this.func_75135_a(itemstack1, 0, this.factionChestInventory.chestLevel * 9, false)) {
                    return null;
                }
                this.itemsMoved.add(this.convertItemstackToString(itemstack, "added"));
            }
            if (itemstack1.field_77994_a == 0) {
                slot.func_75215_d((ItemStack)null);
            } else {
                slot.func_75218_e();
            }
        }
        return itemstack;
    }

    protected boolean func_75135_a(ItemStack par1ItemStack, int par2, int par3, boolean par4) {
        ItemStack itemstack1;
        Slot slot;
        boolean flag1 = false;
        int k = par2;
        if (par4) {
            k = par3 - 1;
        }
        if (par1ItemStack.func_77985_e()) {
            while (par1ItemStack.field_77994_a > 0 && (!par4 && k < par3 || par4 && k >= par2)) {
                slot = (Slot)this.field_75151_b.get(k);
                itemstack1 = slot.func_75211_c();
                if (itemstack1 != null && itemstack1.field_77993_c == par1ItemStack.field_77993_c && (!par1ItemStack.func_77981_g() || par1ItemStack.func_77960_j() == itemstack1.func_77960_j()) && ItemStack.func_77970_a((ItemStack)par1ItemStack, (ItemStack)itemstack1)) {
                    int l = itemstack1.field_77994_a + par1ItemStack.field_77994_a;
                    if (l <= par1ItemStack.func_77976_d()) {
                        par1ItemStack.field_77994_a = 0;
                        itemstack1.field_77994_a = l;
                        slot.func_75218_e();
                        flag1 = true;
                    } else if (itemstack1.field_77994_a < par1ItemStack.func_77976_d()) {
                        par1ItemStack.field_77994_a -= par1ItemStack.func_77976_d() - itemstack1.field_77994_a;
                        itemstack1.field_77994_a = par1ItemStack.func_77976_d();
                        slot.func_75218_e();
                        flag1 = true;
                    }
                }
                if (par4) {
                    --k;
                    continue;
                }
                ++k;
            }
        }
        if (par1ItemStack.field_77994_a > 0) {
            k = par4 ? par3 - 1 : par2;
            while (!par4 && k < par3 || par4 && k >= par2) {
                slot = (Slot)this.field_75151_b.get(k);
                itemstack1 = slot.func_75211_c();
                if (itemstack1 == null) {
                    slot.func_75215_d(par1ItemStack.func_77946_l());
                    slot.func_75218_e();
                    par1ItemStack.field_77994_a = 0;
                    flag1 = true;
                    break;
                }
                if (par4) {
                    --k;
                    continue;
                }
                ++k;
            }
        }
        return flag1;
    }

    public ArrayList<String> getDifference(List<ItemStack> oldInv, List<ItemStack> newInv) {
        HashMap<String, Integer> oldInventoryItems = new HashMap<String, Integer>();
        HashMap<String, Integer> newInventoryItems = new HashMap<String, Integer>();
        HashMap<String, Integer> diffItems = new HashMap<String, Integer>();
        ArrayList<String> finalDiffItems = new ArrayList<String>();
        for (ItemStack oldItem : oldInv) {
            if (oldItem == null) continue;
            if (oldInventoryItems.containsKey(oldItem.field_77993_c + "##" + oldItem.func_77960_j())) {
                oldInventoryItems.put(oldItem.field_77993_c + "##" + oldItem.func_77960_j(), (Integer)oldInventoryItems.get(oldItem.field_77993_c + "##" + oldItem.func_77960_j()) + oldItem.field_77994_a);
                continue;
            }
            oldInventoryItems.put(oldItem.field_77993_c + "##" + oldItem.func_77960_j(), oldItem.field_77994_a);
        }
        for (ItemStack newItem : newInv) {
            if (newItem == null) continue;
            if (newInventoryItems.containsKey(newItem.field_77993_c + "##" + newItem.func_77960_j())) {
                newInventoryItems.put(newItem.field_77993_c + "##" + newItem.func_77960_j(), (Integer)newInventoryItems.get(newItem.field_77993_c + "##" + newItem.func_77960_j()) + newItem.field_77994_a);
                continue;
            }
            newInventoryItems.put(newItem.field_77993_c + "##" + newItem.func_77960_j(), newItem.field_77994_a);
        }
        for (Map.Entry pair : oldInventoryItems.entrySet()) {
            String itemInfos = (String)pair.getKey();
            Integer qte = (Integer)pair.getValue();
            if (!newInventoryItems.containsKey(itemInfos)) {
                diffItems.put(itemInfos, qte * -1);
                continue;
            }
            diffItems.put(itemInfos, (Integer)newInventoryItems.get(itemInfos) - qte);
        }
        for (Map.Entry pair : newInventoryItems.entrySet()) {
            String itemInfos = (String)pair.getKey();
            Integer qte = (Integer)pair.getValue();
            if (oldInventoryItems.containsKey(itemInfos)) continue;
            diffItems.put(itemInfos, qte);
        }
        for (Map.Entry pair : diffItems.entrySet()) {
            String itemInfos = (String)pair.getKey();
            Integer qte = (Integer)pair.getValue();
            String type = "added";
            if (qte < 0) {
                type = "removed";
                qte = qte * -1;
            }
            if (qte == 0) continue;
            finalDiffItems.add(itemInfos + "##" + qte + "##" + this.playerName + "##" + System.currentTimeMillis() + "##" + type);
        }
        return finalDiffItems;
    }

    private String convertItemstackToString(ItemStack itemStack, String type) {
        return itemStack.field_77993_c + "##" + itemStack.func_77960_j() + "##" + itemStack.field_77994_a + "##" + this.playerName + "##" + System.currentTimeMillis() + "##" + type;
    }

    private static ItemStack findSimilarItemStack(Collection<ItemStack> items, ItemStack item) {
        for (ItemStack citem : items) {
            if (item == null || citem == null || !item.func_77969_a(citem)) continue;
            return citem;
        }
        return null;
    }
}

