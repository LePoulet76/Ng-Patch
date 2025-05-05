package net.ilexiconn.nationsgui.forge.server.container;

import net.ilexiconn.nationsgui.forge.server.block.entity.RepairMachineBlockEntity;
import net.ilexiconn.nationsgui.forge.server.container.RepairMachineContainer$1;
import net.ilexiconn.nationsgui.forge.server.container.RepairMachineContainer$2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class RepairMachineContainer extends Container {

   private RepairMachineBlockEntity blockEntity;


   public RepairMachineContainer(InventoryPlayer inventoryPlayer, RepairMachineBlockEntity blockEntity) {
      this.blockEntity = blockEntity;
      this.func_75146_a(new RepairMachineContainer$1(this, blockEntity, 0, 116, 38));
      this.func_75146_a(new RepairMachineContainer$2(this, blockEntity, 1, 47, 34));

      for(int x = 0; x < 3; ++x) {
         for(int y = 0; y < 9; ++y) {
            this.func_75146_a(new Slot(inventoryPlayer, y + x * 9 + 9, 8 + y * 18, 84 + x * 18));
            if(x == 0) {
               this.func_75146_a(new Slot(inventoryPlayer, y, 8 + y * 18, 142));
            }
         }
      }

   }

   public RepairMachineBlockEntity getBlockEntity() {
      return this.blockEntity;
   }

   public boolean func_75145_c(EntityPlayer entityPlayer) {
      return this.blockEntity.func_70300_a(entityPlayer);
   }

   public ItemStack func_82846_b(EntityPlayer entityPlayer, int slot) {
      return null;
   }
}
