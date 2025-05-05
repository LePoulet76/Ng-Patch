package net.ilexiconn.nationsgui.forge.server.block.entity;

import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class IncubatorBlockEntity extends TileEntity implements IInventory {

   private ItemStack[] itemStacks = new ItemStack[2];
   private String customName;
   private int updateTick;
   private boolean active;
   private int progress;
   private String owner;
   public static final int SLOT_COAL = 0;
   public static final int SLOT_SPAWN_EGG = 1;


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
      return this.func_94042_c()?this.customName:"tile.incubator.name";
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

   public boolean func_70300_a(EntityPlayer entityPlayer) {
      return this.field_70331_k.func_72796_p(this.field_70329_l, this.field_70330_m, this.field_70327_n) != this?false:entityPlayer.func_70092_e((double)this.field_70329_l + 0.5D, (double)this.field_70330_m + 0.5D, (double)this.field_70327_n + 0.5D) <= 64.0D && (this.getOwner() == null || this.getOwner().equals(entityPlayer.field_71092_bJ) || MinecraftServer.func_71276_C().func_71203_ab().func_72353_e(entityPlayer.field_71092_bJ));
   }

   public void func_70295_k_() {}

   public void func_70305_f() {}

   public boolean func_94041_b(int slot, ItemStack itemStack) {
      return true;
   }

   public void func_70307_a(NBTTagCompound compound) {
      super.func_70307_a(compound);
      NBTTagList itemsTag = compound.func_74761_m("Items");
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

   public void func_70310_b(NBTTagCompound compound) {
      super.func_70310_b(compound);
      NBTTagList itemsTag = new NBTTagList();

      for(int i = 0; i < this.itemStacks.length; ++i) {
         if(this.itemStacks[i] != null) {
            NBTTagCompound itemTag = new NBTTagCompound();
            itemTag.func_74774_a("Slot", (byte)i);
            this.itemStacks[i].func_77955_b(itemTag);
            itemsTag.func_74742_a(itemTag);
         }
      }

      compound.func_74782_a("Items", itemsTag);
      if(this.func_94042_c()) {
         compound.func_74778_a("CustomName", this.customName);
      }

      if(this.owner != null && this.owner.length() > 0) {
         compound.func_74778_a("Owner", this.owner);
      }

   }

   public Packet func_70319_e() {
      NBTTagCompound compound = new NBTTagCompound();
      this.func_70310_b(compound);
      return new Packet132TileEntityData(this.field_70329_l, this.field_70330_m, this.field_70327_n, 3, compound);
   }

   public void func_70316_g() {
      ++this.updateTick;
      if(this.updateTick % 20 == 0) {
         if(this.func_70301_a(0) != null && this.func_70301_a(0).func_77973_b().field_77779_bT == Block.field_111034_cE.field_71990_ca && this.func_70301_a(1) != null && this.func_70301_a(0).field_77994_a >= NationsGUI.CONFIG.incubatorCoalRate) {
            ItemStack var10000 = this.func_70301_a(0);
            var10000.field_77994_a -= NationsGUI.CONFIG.incubatorCoalRate;
            if(this.updateTick / 20 % (60 / NationsGUI.CONFIG.incubatorSpawnRate) == 0) {
               this.progress = 0;
               if(!this.field_70331_k.field_72995_K) {
                  int metadata = this.func_70301_a(1).func_77960_j();
                  Class entityClass = EntityList.func_90035_a(metadata);

                  try {
                     Entity e = (Entity)entityClass.getConstructor(new Class[]{World.class}).newInstance(new Object[]{this.field_70331_k});
                     metadata = this.field_70331_k.func_72805_g(this.field_70329_l, this.field_70330_m, this.field_70327_n);
                     switch(metadata) {
                     case 2:
                        e.func_70012_b((double)this.field_70329_l, (double)this.field_70330_m, (double)(this.field_70327_n - 1), this.field_70331_k.field_73012_v.nextFloat() * 360.0F, 0.0F);
                        break;
                     case 3:
                        e.func_70012_b((double)this.field_70329_l, (double)this.field_70330_m, (double)(this.field_70327_n + 1), this.field_70331_k.field_73012_v.nextFloat() * 360.0F, 0.0F);
                        break;
                     case 4:
                        e.func_70012_b((double)(this.field_70329_l - 1), (double)this.field_70330_m, (double)this.field_70327_n, this.field_70331_k.field_73012_v.nextFloat() * 360.0F, 0.0F);
                        break;
                     case 5:
                        e.func_70012_b((double)(this.field_70329_l + 1), (double)this.field_70330_m, (double)this.field_70327_n, this.field_70331_k.field_73012_v.nextFloat() * 360.0F, 0.0F);
                     }

                     this.field_70331_k.func_72838_d(e);
                  } catch (Exception var4) {
                     var4.printStackTrace();
                  }
               }
            }

            this.progress += 80 / (60 / NationsGUI.CONFIG.incubatorSpawnRate);
            this.active = true;
         } else {
            this.progress = 0;
            this.active = false;
         }
      }

   }

   public boolean isActive() {
      return this.active;
   }

   public int getProgress() {
      return this.progress;
   }
}
