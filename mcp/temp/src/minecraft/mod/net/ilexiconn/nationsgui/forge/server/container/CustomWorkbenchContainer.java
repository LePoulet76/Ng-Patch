package net.ilexiconn.nationsgui.forge.server.container;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.world.World;

public class CustomWorkbenchContainer extends Container {

   public InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
   public IInventory craftResult = new InventoryCraftResult();
   private World worldObj;
   private int posX;
   private int posY;
   private int posZ;


   public CustomWorkbenchContainer(InventoryPlayer par1InventoryPlayer, World par2World, int par3, int par4, int par5) {
      this.worldObj = par2World;
      this.posX = par3;
      this.posY = par4;
      this.posZ = par5;
      this.func_75146_a(new SlotCrafting(par1InventoryPlayer.field_70458_d, this.craftMatrix, this.craftResult, 0, 152, 103));

      int l;
      int i1;
      for(l = 0; l < 3; ++l) {
         for(i1 = 0; i1 < 3; ++i1) {
            this.func_75146_a(new Slot(this.craftMatrix, i1 + l * 3, i1 * 18 + 89, 67 + l * 18));
         }
      }

      for(l = 0; l < 3; ++l) {
         for(i1 = 0; i1 < 9; ++i1) {
            this.func_75146_a(new Slot(par1InventoryPlayer, i1 + l * 9 + 9, i1 * 18 + 206, 60 + l * 18));
         }
      }

      for(l = 0; l < 9; ++l) {
         this.func_75146_a(new Slot(par1InventoryPlayer, l, 25 + l * 18 + 181, 118));
      }

      this.func_75130_a(this.craftMatrix);
   }

   public void func_75130_a(IInventory par1IInventory) {
      this.craftResult.func_70299_a(0, CraftingManager.func_77594_a().func_82787_a(this.craftMatrix, this.worldObj));
   }

   public void func_75134_a(EntityPlayer par1EntityPlayer) {
      super.func_75134_a(par1EntityPlayer);
      if(!this.worldObj.field_72995_K) {
         for(int i = 0; i < 9; ++i) {
            ItemStack itemstack = this.craftMatrix.func_70304_b(i);
            if(itemstack != null) {
               par1EntityPlayer.func_71021_b(itemstack);
            }
         }
      }

   }

   public boolean func_75145_c(EntityPlayer par1EntityPlayer) {
      return this.worldObj.func_72798_a(this.posX, this.posY, this.posZ) != Block.field_72060_ay.field_71990_ca?false:par1EntityPlayer.func_70092_e((double)this.posX + 0.5D, (double)this.posY + 0.5D, (double)this.posZ + 0.5D) <= 64.0D;
   }

   public ItemStack func_82846_b(EntityPlayer par1EntityPlayer, int par2) {
      ItemStack itemstack = null;
      Slot slot = (Slot)this.field_75151_b.get(par2);
      if(slot != null && slot.func_75216_d()) {
         ItemStack itemstack1 = slot.func_75211_c();
         itemstack = itemstack1.func_77946_l();
         if(par2 == 0) {
            if(!this.func_75135_a(itemstack1, 10, 46, true)) {
               return null;
            }

            slot.func_75220_a(itemstack1, itemstack);
         } else if(par2 >= 10 && par2 < 37) {
            if(!this.func_75135_a(itemstack1, 37, 46, false)) {
               return null;
            }
         } else if(par2 >= 37 && par2 < 46) {
            if(!this.func_75135_a(itemstack1, 10, 37, false)) {
               return null;
            }
         } else if(!this.func_75135_a(itemstack1, 10, 46, false)) {
            return null;
         }

         if(itemstack1.field_77994_a == 0) {
            slot.func_75215_d((ItemStack)null);
         } else {
            slot.func_75218_e();
         }

         if(itemstack1.field_77994_a == itemstack.field_77994_a) {
            return null;
         }

         slot.func_82870_a(par1EntityPlayer, itemstack1);
      }

      return itemstack;
   }

   public boolean func_94530_a(ItemStack par1ItemStack, Slot par2Slot) {
      return par2Slot.field_75224_c != this.craftResult && super.func_94530_a(par1ItemStack, par2Slot);
   }
}
