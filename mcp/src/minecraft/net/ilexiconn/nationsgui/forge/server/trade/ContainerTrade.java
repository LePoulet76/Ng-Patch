/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.ReflectionHelper
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.inventory.Container
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.inventory.InventoryCrafting
 *  net.minecraft.inventory.Slot
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTBase
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.nbt.NBTTagList
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.management.ServerConfigurationManager
 *  net.minecraft.world.storage.IPlayerFileData
 */
package net.ilexiconn.nationsgui.forge.server.trade;

import cpw.mods.fml.relauncher.ReflectionHelper;
import java.util.HashMap;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.server.packet.PacketCallbacks;
import net.ilexiconn.nationsgui.forge.server.trade.TradeData;
import net.ilexiconn.nationsgui.forge.server.trade.TradeManager;
import net.ilexiconn.nationsgui.forge.server.trade.enums.EnumTradeState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.world.storage.IPlayerFileData;

public class ContainerTrade
extends Container {
    private EntityPlayer player;
    public InventoryCrafting craftMatrix = new InventoryCrafting((Container)this, 7, 2);
    public EnumTradeState state = EnumTradeState.NONE;

    public ContainerTrade(EntityPlayer player) {
        int k;
        int j;
        this.player = player;
        for (j = 0; j < 3; ++j) {
            for (k = 0; k < 9; ++k) {
                this.func_75146_a(new Slot((IInventory)player.field_71071_by, k + j * 9 + 9, 230 + k * 18, 95 + j * 18));
                if (j != 0) continue;
                this.func_75146_a(new Slot((IInventory)player.field_71071_by, k, 230 + k * 18, 153));
            }
        }
        for (j = 0; j < 2; ++j) {
            for (k = 0; k < 7; ++k) {
                this.func_75146_a(new SlotTrader((IInventory)this.craftMatrix, k + j * 7, 66 + k * 18, 57 + j * 18));
            }
        }
    }

    public ItemStack func_82846_b(EntityPlayer par1EntityPlayer, int slot) {
        return null;
    }

    public boolean func_75145_c(EntityPlayer player) {
        if (player.field_70170_p.field_72995_K) {
            return this.state != EnumTradeState.YOU_ACCEPTED;
        }
        TradeData data = TradeData.get(player);
        boolean trading = data.isTrading();
        if (!trading) {
            data.closeTrade();
        }
        return trading && this.state != EnumTradeState.YOU_ACCEPTED;
    }

    public void func_75134_a(EntityPlayer player) {
        InventoryPlayer inventoryplayer = player.field_71071_by;
        if (inventoryplayer.func_70445_o() != null) {
            if (!player.field_71071_by.func_70441_a(inventoryplayer.func_70445_o())) {
                player.func_71021_b(inventoryplayer.func_70445_o());
            }
            inventoryplayer.func_70437_b((ItemStack)null);
        }
        if (!player.field_70170_p.field_72995_K) {
            for (int i = 0; i < this.craftMatrix.func_70302_i_(); ++i) {
                ItemStack itemstack = this.craftMatrix.func_70304_b(i);
                if (itemstack != null) {
                    System.out.println("[TRADE] GIVE ITEM BACK TO " + player.getDisplayName() + " : " + itemstack.field_77993_c + " x" + itemstack.field_77994_a);
                }
                if (player.field_71071_by.func_70441_a(itemstack) || itemstack == null) continue;
                System.out.println("[TRADE] FAIL (drop) GIVE ITEM BACK TO " + player.getDisplayName() + " : " + itemstack.field_77993_c + " x" + itemstack.field_77994_a + " - Location X:" + Math.floor(player.field_70165_t) + " Y:" + Math.floor(player.field_70163_u) + " Z:" + Math.floor(player.field_70161_v));
                player.func_71021_b(itemstack);
            }
            if (player.field_70128_L) {
                IPlayerFileData playerFileData = (IPlayerFileData)ReflectionHelper.getPrivateValue(ServerConfigurationManager.class, (Object)MinecraftServer.func_71276_C().func_71203_ab(), (String[])new String[]{"field_72412_k", "playerNBTManagerObj"});
                playerFileData.func_75753_a(player);
            }
        }
    }

    public NBTTagCompound itemsToComp() {
        NBTTagCompound compound = new NBTTagCompound();
        NBTTagList list = new NBTTagList();
        for (int i = 0; i < this.craftMatrix.func_70302_i_(); ++i) {
            ItemStack item = this.craftMatrix.func_70301_a(i);
            if (item == null) continue;
            NBTTagCompound c = new NBTTagCompound();
            c.func_74768_a("Slot", i);
            item.func_77955_b(c);
            list.func_74742_a((NBTBase)c);
        }
        compound.func_74782_a("Items", (NBTBase)list);
        return compound;
    }

    public static Map CompToItem(NBTTagCompound compound) {
        HashMap<Integer, ItemStack> items = new HashMap<Integer, ItemStack>();
        NBTTagList list = compound.func_74761_m("Items");
        for (int i = 0; i < list.func_74745_c(); ++i) {
            NBTTagCompound c = (NBTTagCompound)list.func_74743_b(i);
            int slot = c.func_74762_e("Slot");
            ItemStack item = ItemStack.func_77949_a((NBTTagCompound)c);
            if (item == null) continue;
            items.put(slot, item);
        }
        return items;
    }

    public class SlotTrader
    extends Slot {
        public SlotTrader(IInventory par1iInventory, int par2, int par3, int par4) {
            super(par1iInventory, par2, par3, par4);
        }

        public boolean func_75214_a(ItemStack par1ItemStack) {
            EnumTradeState state = ContainerTrade.this.state;
            if (!((ContainerTrade)ContainerTrade.this).player.field_70170_p.field_72995_K) {
                TradeData data = TradeData.get(ContainerTrade.this.player);
                state = data.state;
            }
            return state == EnumTradeState.STARTED || state == EnumTradeState.TRADER_ACCEPTED || state == EnumTradeState.YOU_ACCEPTED;
        }

        public void func_75218_e() {
            super.func_75218_e();
            if (!((ContainerTrade)ContainerTrade.this).player.field_70170_p.field_72995_K) {
                TradeData data = TradeData.get(ContainerTrade.this.player);
                if (data.state != EnumTradeState.DONE && data.state != EnumTradeState.WAITING) {
                    data.setState(EnumTradeState.STARTED);
                    TradeData data2 = TradeData.get(data.tradePlayer);
                    data2.setState(EnumTradeState.STARTED);
                }
                TradeManager.sendItems(data.tradePlayer, ContainerTrade.this.itemsToComp(), data.player.field_71092_bJ);
            } else {
                PacketCallbacks.MONEY.send(new String[0]);
            }
        }
    }
}

