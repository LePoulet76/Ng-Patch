package net.ilexiconn.nationsgui.forge.server.container;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import net.ilexiconn.nationsgui.forge.server.inventory.FactionChestInventory;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionChestSaveDataPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class FactionChestContainer extends Container {

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

      int w;
      int z;
      for(w = 0; w < this.numRows; ++w) {
         for(z = 0; z < 9; ++z) {
            this.func_75146_a(new Slot(chestInventory, z + w * 9, 51 + z * 18, 44 + w * 18));
         }
      }

      for(w = 0; w < 3; ++w) {
         for(z = 0; z < 9; ++z) {
            this.func_75146_a(new Slot(inventoryPlayer, z + w * 9 + 9, 46 + z * 18, 240 + w * 18));
            if(w == 0) {
               this.func_75146_a(new Slot(inventoryPlayer, z, 46 + z * 18, 298));
            }
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
      if(par1EntityPlayer.field_70170_p.field_72995_K) {
         HashMap data = new HashMap();
         ArrayList difference = this.getDifference(Arrays.asList(this.oldInventory), Arrays.asList(this.factionChestInventory.itemStacks));
         data.put("logs", difference);
         data.put("factionName", this.factionName);
         PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionChestSaveDataPacket(data)));
      }

      this.writeToNBT();
      super.func_75134_a(par1EntityPlayer);
   }

   public ItemStack func_75144_a(int slotId, int par2, int par3, EntityPlayer par4EntityPlayer) {
      InventoryPlayer inventoryplayer = par4EntityPlayer.field_71071_by;
      if(slotId >= 0 && slotId < this.numRows * 9) {
         if(!this.canTake && ((Slot)this.field_75151_b.get(slotId)).func_75216_d()) {
            return null;
         }

         if(slotId >= this.factionChestInventory.chestLevel * 9 && inventoryplayer.func_70445_o() != null) {
            return null;
         }
      } else if(slotId >= this.numRows * 9 && !this.canDeposit && (inventoryplayer.func_70445_o() == null || ((Slot)this.field_75151_b.get(slotId)).func_75216_d()) && (inventoryplayer.func_70445_o() == null || inventoryplayer.func_70445_o().field_77993_c != ((Slot)this.field_75151_b.get(slotId)).func_75211_c().field_77993_c || inventoryplayer.func_70445_o().func_77960_j() != ((Slot)this.field_75151_b.get(slotId)).func_75211_c().func_77960_j())) {
         return null;
      }

      return super.func_75144_a(slotId, par2, par3, par4EntityPlayer);
   }

   public ItemStack func_82846_b(EntityPlayer par1EntityPlayer, int par2) {
      ItemStack itemstack = null;
      Slot slot = (Slot)this.field_75151_b.get(par2);
      if(slot != null && slot.func_75216_d()) {
         ItemStack itemstack1 = slot.func_75211_c();
         itemstack = itemstack1.func_77946_l();
         if(par2 < this.numRows * 9) {
            if(!this.canTake) {
               return null;
            }

            if(!this.func_75135_a(itemstack1, this.numRows * 9, this.field_75151_b.size(), true)) {
               return null;
            }

            this.itemsMoved.add(this.convertItemstackToString(itemstack, "removed"));
         } else {
            if(!this.canDeposit) {
               return null;
            }

            if(!this.func_75135_a(itemstack1, 0, this.factionChestInventory.chestLevel * 9, false)) {
               return null;
            }

            this.itemsMoved.add(this.convertItemstackToString(itemstack, "added"));
         }

         if(itemstack1.field_77994_a == 0) {
            slot.func_75215_d((ItemStack)null);
         } else {
            slot.func_75218_e();
         }
      }

      return itemstack;
   }

   protected boolean func_75135_a(ItemStack par1ItemStack, int par2, int par3, boolean par4) {
      boolean flag1 = false;
      int k = par2;
      if(par4) {
         k = par3 - 1;
      }

      Slot slot;
      ItemStack itemstack1;
      if(par1ItemStack.func_77985_e()) {
         while(par1ItemStack.field_77994_a > 0 && (!par4 && k < par3 || par4 && k >= par2)) {
            slot = (Slot)this.field_75151_b.get(k);
            itemstack1 = slot.func_75211_c();
            if(itemstack1 != null && itemstack1.field_77993_c == par1ItemStack.field_77993_c && (!par1ItemStack.func_77981_g() || par1ItemStack.func_77960_j() == itemstack1.func_77960_j()) && ItemStack.func_77970_a(par1ItemStack, itemstack1)) {
               int l = itemstack1.field_77994_a + par1ItemStack.field_77994_a;
               if(l <= par1ItemStack.func_77976_d()) {
                  par1ItemStack.field_77994_a = 0;
                  itemstack1.field_77994_a = l;
                  slot.func_75218_e();
                  flag1 = true;
               } else if(itemstack1.field_77994_a < par1ItemStack.func_77976_d()) {
                  par1ItemStack.field_77994_a -= par1ItemStack.func_77976_d() - itemstack1.field_77994_a;
                  itemstack1.field_77994_a = par1ItemStack.func_77976_d();
                  slot.func_75218_e();
                  flag1 = true;
               }
            }

            if(par4) {
               --k;
            } else {
               ++k;
            }
         }
      }

      if(par1ItemStack.field_77994_a > 0) {
         if(par4) {
            k = par3 - 1;
         } else {
            k = par2;
         }

         while(!par4 && k < par3 || par4 && k >= par2) {
            slot = (Slot)this.field_75151_b.get(k);
            itemstack1 = slot.func_75211_c();
            if(itemstack1 == null) {
               slot.func_75215_d(par1ItemStack.func_77946_l());
               slot.func_75218_e();
               par1ItemStack.field_77994_a = 0;
               flag1 = true;
               break;
            }

            if(par4) {
               --k;
            } else {
               ++k;
            }
         }
      }

      return flag1;
   }

   public ArrayList<String> getDifference(List<ItemStack> oldInv, List<ItemStack> newInv) {
      HashMap oldInventoryItems = new HashMap();
      HashMap newInventoryItems = new HashMap();
      HashMap diffItems = new HashMap();
      ArrayList finalDiffItems = new ArrayList();
      Iterator it = oldInv.iterator();

      ItemStack it2;
      while(it.hasNext()) {
         it2 = (ItemStack)it.next();
         if(it2 != null) {
            if(oldInventoryItems.containsKey(it2.field_77993_c + "##" + it2.func_77960_j())) {
               oldInventoryItems.put(it2.field_77993_c + "##" + it2.func_77960_j(), Integer.valueOf(((Integer)oldInventoryItems.get(it2.field_77993_c + "##" + it2.func_77960_j())).intValue() + it2.field_77994_a));
            } else {
               oldInventoryItems.put(it2.field_77993_c + "##" + it2.func_77960_j(), Integer.valueOf(it2.field_77994_a));
            }
         }
      }

      it = newInv.iterator();

      while(it.hasNext()) {
         it2 = (ItemStack)it.next();
         if(it2 != null) {
            if(newInventoryItems.containsKey(it2.field_77993_c + "##" + it2.func_77960_j())) {
               newInventoryItems.put(it2.field_77993_c + "##" + it2.func_77960_j(), Integer.valueOf(((Integer)newInventoryItems.get(it2.field_77993_c + "##" + it2.func_77960_j())).intValue() + it2.field_77994_a));
            } else {
               newInventoryItems.put(it2.field_77993_c + "##" + it2.func_77960_j(), Integer.valueOf(it2.field_77994_a));
            }
         }
      }

      it = oldInventoryItems.entrySet().iterator();

      while(it.hasNext()) {
         Entry it21 = (Entry)it.next();
         String it3 = (String)it21.getKey();
         Integer pair = (Integer)it21.getValue();
         if(!newInventoryItems.containsKey(it3)) {
            diffItems.put(it3, Integer.valueOf(pair.intValue() * -1));
         } else {
            diffItems.put(it3, Integer.valueOf(((Integer)newInventoryItems.get(it3)).intValue() - pair.intValue()));
         }
      }

      Iterator it22 = newInventoryItems.entrySet().iterator();

      while(it22.hasNext()) {
         Entry it31 = (Entry)it22.next();
         String pair1 = (String)it31.getKey();
         Integer itemInfos = (Integer)it31.getValue();
         if(!oldInventoryItems.containsKey(pair1)) {
            diffItems.put(pair1, itemInfos);
         }
      }

      Iterator it32 = diffItems.entrySet().iterator();

      while(it32.hasNext()) {
         Entry pair2 = (Entry)it32.next();
         String itemInfos1 = (String)pair2.getKey();
         Integer qte = (Integer)pair2.getValue();
         String type = "added";
         if(qte.intValue() < 0) {
            type = "removed";
            qte = Integer.valueOf(qte.intValue() * -1);
         }

         if(qte.intValue() != 0) {
            finalDiffItems.add(itemInfos1 + "##" + qte + "##" + this.playerName + "##" + System.currentTimeMillis() + "##" + type);
         }
      }

      return finalDiffItems;
   }

   private String convertItemstackToString(ItemStack itemStack, String type) {
      return itemStack.field_77993_c + "##" + itemStack.func_77960_j() + "##" + itemStack.field_77994_a + "##" + this.playerName + "##" + System.currentTimeMillis() + "##" + type;
   }

   private static ItemStack findSimilarItemStack(Collection<ItemStack> items, ItemStack item) {
      Iterator var2 = items.iterator();

      ItemStack citem;
      do {
         if(!var2.hasNext()) {
            return null;
         }

         citem = (ItemStack)var2.next();
      } while(item == null || citem == null || !item.func_77969_a(citem));

      return citem;
   }
}
