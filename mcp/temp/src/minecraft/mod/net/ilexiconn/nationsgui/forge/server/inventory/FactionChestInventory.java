package net.ilexiconn.nationsgui.forge.server.inventory;

import net.ilexiconn.nationsgui.forge.server.config.NBTConfig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class FactionChestInventory implements IInventory {

   public ItemStack[] itemStacks;
   private String customName;
   private String owner;
   public ItemStack[] content;
   public int size = 54;
   public String factionId;
   public int chestLevel;


   public FactionChestInventory(String factionId, int chestLevel) {
      this.chestLevel = chestLevel;
      this.factionId = factionId;
      this.itemStacks = new ItemStack[this.size];
      this.readFromNBT(factionId);
   }

   public int func_70302_i_() {
      return this.itemStacks.length;
   }

   public ItemStack func_70301_a(int slot) {
      return this.itemStacks[slot];
   }

   public ItemStack func_70298_a(int slot, int stackSize) {
      if(this.itemStacks[slot] != null) {
         ItemStack itemstack;
         if(this.itemStacks[slot].field_77994_a <= stackSize) {
            itemstack = this.itemStacks[slot];
            this.itemStacks[slot] = null;
            return itemstack;
         } else {
            itemstack = this.itemStacks[slot].func_77979_a(stackSize);
            if(this.itemStacks[slot].field_77994_a == 0) {
               this.itemStacks[slot] = null;
            }

            return itemstack;
         }
      } else {
         return null;
      }
   }

   public ItemStack func_70304_b(int slot) {
      if(this.itemStacks[slot] != null) {
         ItemStack itemstack = this.itemStacks[slot];
         this.itemStacks[slot] = null;
         return itemstack;
      } else {
         return null;
      }
   }

   public void func_70299_a(int slot, ItemStack itemStack) {
      this.itemStacks[slot] = itemStack;
      if(itemStack != null && itemStack.field_77994_a > this.func_70297_j_()) {
         itemStack.field_77994_a = this.func_70297_j_();
      }

   }

   public String func_70303_b() {
      return this.func_94042_c()?this.customName:this.factionId;
   }

   public boolean func_94042_c() {
      return this.customName != null && this.customName.length() > 0;
   }

   public void setCustomName(String customName) {
      this.customName = customName;
   }

   public String getOwner() {
      return this.owner;
   }

   public void setOwner(String owner) {
      this.owner = owner;
   }

   public int func_70297_j_() {
      return 64;
   }

   public void func_70296_d() {}

   public boolean func_70300_a(EntityPlayer entityPlayer) {
      return true;
   }

   public void func_70295_k_() {}

   public void func_70305_f() {
      this.writeToNBT();
   }

   public boolean func_94041_b(int slot, ItemStack itemStack) {
      return true;
   }

   public void readFromNBT(String factionId) {
      NBTTagCompound compound = (NBTTagCompound)NBTConfig.CONFIG.getCompound().func_74781_a("FactionChest");
      NBTTagList itemsTag = compound.func_74761_m(factionId);
      this.itemStacks = new ItemStack[this.func_70302_i_()];

      for(int i = 0; i < itemsTag.func_74745_c(); ++i) {
         NBTTagCompound itemTag = (NBTTagCompound)itemsTag.func_74743_b(i);
         byte slot = itemTag.func_74771_c("Slot");
         if(slot >= 0 && slot < this.itemStacks.length) {
            this.itemStacks[slot] = ItemStack.func_77949_a(itemTag);
         }
      }

      if(compound.func_74764_b("CustomName")) {
         this.customName = compound.func_74779_i("CustomName");
      }

      if(compound.func_74764_b("Owner")) {
         this.owner = compound.func_74779_i("Owner");
      }

   }

   public void writeToNBT() {
      NBTTagCompound compound = (NBTTagCompound)NBTConfig.CONFIG.getCompound().func_74781_a("FactionChest");
      NBTTagList itemsTag = new NBTTagList();

      for(int i = 0; i < this.itemStacks.length; ++i) {
         if(this.itemStacks[i] != null) {
            NBTTagCompound itemTag = new NBTTagCompound();
            itemTag.func_74774_a("Slot", (byte)i);
            this.itemStacks[i].func_77955_b(itemTag);
            itemsTag.func_74742_a(itemTag);
         }
      }

      compound.func_74782_a(this.factionId, itemsTag);
      if(this.func_94042_c()) {
         compound.func_74778_a("CustomName", this.customName);
      }

      if(this.owner != null && this.owner.length() > 0) {
         compound.func_74778_a("Owner", this.owner);
      }

      NBTConfig.CONFIG.save();
   }
}
