package net.ilexiconn.nationsgui.forge.server.container;

import net.ilexiconn.nationsgui.forge.server.container.PlayerContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

class PlayerContainer$1 extends Slot {

   // $FF: synthetic field
   final int val$finalY;
   // $FF: synthetic field
   final EntityPlayer val$player;
   // $FF: synthetic field
   final PlayerContainer this$0;


   PlayerContainer$1(PlayerContainer this$0, IInventory x0, int x1, int x2, int x3, int var6, EntityPlayer var7) {
      super(x0, x1, x2, x3);
      this.this$0 = this$0;
      this.val$finalY = var6;
      this.val$player = var7;
   }

   public int func_75219_a() {
      return 1;
   }

   public boolean func_75214_a(ItemStack stack) {
      return stack != null && stack.func_77973_b().isValidArmor(stack, this.val$finalY, this.val$player);
   }
}
