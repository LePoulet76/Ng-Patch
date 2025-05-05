package net.ilexiconn.nationsgui.forge.client.data;

import java.util.List;
import net.minecraft.item.ItemStack;

public class RecipeData$RecipeItemStack {

   private List<ItemStack> itemStacks;
   private int displayIndex = -1;


   public RecipeData$RecipeItemStack(List<ItemStack> itemStacks) {
      this.itemStacks = itemStacks;
   }

   public ItemStack nextItem() {
      if(!this.itemStacks.isEmpty()) {
         ++this.displayIndex;
         if(this.displayIndex >= this.itemStacks.size()) {
            this.displayIndex = 0;
         }

         return ((ItemStack)this.itemStacks.get(this.displayIndex)).func_77946_l();
      } else {
         return null;
      }
   }

   public List<ItemStack> getItemStacks() {
      return this.itemStacks;
   }
}
