package net.ilexiconn.nationsgui.forge.server.container;

import net.ilexiconn.nationsgui.forge.server.container.RepairMachineContainer;
import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

class RepairMachineContainer$1 extends Slot {

   // $FF: synthetic field
   final RepairMachineContainer this$0;


   RepairMachineContainer$1(RepairMachineContainer this$0, IInventory x0, int x1, int x2, int x3) {
      super(x0, x1, x2, x3);
      this.this$0 = this$0;
   }

   public boolean func_75214_a(ItemStack itemStack) {
      return itemStack.func_77973_b().field_77779_bT == Block.field_111034_cE.field_71990_ca;
   }
}
