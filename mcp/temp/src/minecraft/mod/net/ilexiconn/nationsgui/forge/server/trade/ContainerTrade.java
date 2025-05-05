package net.ilexiconn.nationsgui.forge.server.trade;

import cpw.mods.fml.relauncher.ReflectionHelper;
import java.util.HashMap;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.server.trade.ContainerTrade$SlotTrader;
import net.ilexiconn.nationsgui.forge.server.trade.TradeData;
import net.ilexiconn.nationsgui.forge.server.trade.enums.EnumTradeState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.world.storage.IPlayerFileData;

public class ContainerTrade extends Container {

   private EntityPlayer player;
   public InventoryCrafting craftMatrix = new InventoryCrafting(this, 7, 2);
   public EnumTradeState state;


   public ContainerTrade(EntityPlayer player) {
      this.state = EnumTradeState.NONE;
      this.player = player;

      int j;
      int k;
      for(j = 0; j < 3; ++j) {
         for(k = 0; k < 9; ++k) {
            this.func_75146_a(new Slot(player.field_71071_by, k + j * 9 + 9, 230 + k * 18, 95 + j * 18));
            if(j == 0) {
               this.func_75146_a(new Slot(player.field_71071_by, k, 230 + k * 18, 153));
            }
         }
      }

      for(j = 0; j < 2; ++j) {
         for(k = 0; k < 7; ++k) {
            this.func_75146_a(new ContainerTrade$SlotTrader(this, this.craftMatrix, k + j * 7, 66 + k * 18, 57 + j * 18));
         }
      }

   }

   public ItemStack func_82846_b(EntityPlayer par1EntityPlayer, int slot) {
      return null;
   }

   public boolean func_75145_c(EntityPlayer player) {
      if(player.field_70170_p.field_72995_K) {
         return this.state != EnumTradeState.YOU_ACCEPTED;
      } else {
         TradeData data = TradeData.get(player);
         boolean trading = data.isTrading();
         if(!trading) {
            data.closeTrade();
         }

         return trading && this.state != EnumTradeState.YOU_ACCEPTED;
      }
   }

   public void func_75134_a(EntityPlayer player) {
      InventoryPlayer inventoryplayer = player.field_71071_by;
      if(inventoryplayer.func_70445_o() != null) {
         if(!player.field_71071_by.func_70441_a(inventoryplayer.func_70445_o())) {
            player.func_71021_b(inventoryplayer.func_70445_o());
         }

         inventoryplayer.func_70437_b((ItemStack)null);
      }

      if(!player.field_70170_p.field_72995_K) {
         for(int playerFileData = 0; playerFileData < this.craftMatrix.func_70302_i_(); ++playerFileData) {
            ItemStack itemstack = this.craftMatrix.func_70304_b(playerFileData);
            if(itemstack != null) {
               System.out.println("[TRADE] GIVE ITEM BACK TO " + player.getDisplayName() + " : " + itemstack.field_77993_c + " x" + itemstack.field_77994_a);
            }

            if(!player.field_71071_by.func_70441_a(itemstack) && itemstack != null) {
               System.out.println("[TRADE] FAIL (drop) GIVE ITEM BACK TO " + player.getDisplayName() + " : " + itemstack.field_77993_c + " x" + itemstack.field_77994_a + " - Location X:" + Math.floor(player.field_70165_t) + " Y:" + Math.floor(player.field_70163_u) + " Z:" + Math.floor(player.field_70161_v));
               player.func_71021_b(itemstack);
            }
         }

         if(player.field_70128_L) {
            IPlayerFileData var5 = (IPlayerFileData)ReflectionHelper.getPrivateValue(ServerConfigurationManager.class, MinecraftServer.func_71276_C().func_71203_ab(), new String[]{"field_72412_k", "playerNBTManagerObj"});
            var5.func_75753_a(player);
         }
      }

   }

   public NBTTagCompound itemsToComp() {
      NBTTagCompound compound = new NBTTagCompound();
      NBTTagList list = new NBTTagList();

      for(int i = 0; i < this.craftMatrix.func_70302_i_(); ++i) {
         ItemStack item = this.craftMatrix.func_70301_a(i);
         if(item != null) {
            NBTTagCompound c = new NBTTagCompound();
            c.func_74768_a("Slot", i);
            item.func_77955_b(c);
            list.func_74742_a(c);
         }
      }

      compound.func_74782_a("Items", list);
      return compound;
   }

   public static Map CompToItem(NBTTagCompound compound) {
      HashMap items = new HashMap();
      NBTTagList list = compound.func_74761_m("Items");

      for(int i = 0; i < list.func_74745_c(); ++i) {
         NBTTagCompound c = (NBTTagCompound)list.func_74743_b(i);
         int slot = c.func_74762_e("Slot");
         ItemStack item = ItemStack.func_77949_a(c);
         if(item != null) {
            items.put(Integer.valueOf(slot), item);
         }
      }

      return items;
   }

   // $FF: synthetic method
   static EntityPlayer access$000(ContainerTrade x0) {
      return x0.player;
   }
}
