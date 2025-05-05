package net.ilexiconn.nationsgui.forge.server.container;

import net.ilexiconn.nationsgui.forge.server.container.IncubatorContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

class IncubatorContainer$2 extends Slot {

   // $FF: synthetic field
   final IncubatorContainer this$0;


   IncubatorContainer$2(IncubatorContainer this$0, IInventory x0, int x1, int x2, int x3) {
      super(x0, x1, x2, x3);
      this.this$0 = this$0;
   }

   public boolean func_75214_a(ItemStack itemStack) {
      return itemStack.func_77973_b().field_77779_bT == Item.field_77815_bC.field_77779_bT;
   }
}
