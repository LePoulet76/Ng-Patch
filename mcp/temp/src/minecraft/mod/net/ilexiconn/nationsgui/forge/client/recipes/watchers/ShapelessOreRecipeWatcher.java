package net.ilexiconn.nationsgui.forge.client.recipes.watchers;

import java.util.ArrayList;
import java.util.Iterator;
import net.ilexiconn.nationsgui.forge.client.RecipeWatcher;
import net.ilexiconn.nationsgui.forge.client.data.RecipeData;
import net.ilexiconn.nationsgui.forge.client.data.RecipeData$RecipeItemStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ShapelessOreRecipeWatcher extends RecipeWatcher<ShapelessOreRecipe> {

   public RecipeData watchRecipe(ShapelessOreRecipe recipe) {
      RecipeData recipeData = new RecipeData();
      int i = 0;

      for(Iterator var4 = recipe.getInput().iterator(); var4.hasNext(); ++i) {
         Object object = var4.next();
         if(object instanceof ItemStack) {
            recipeData.getItemStacks()[i] = this.getDamagedList((ItemStack)object);
         } else if(object instanceof ArrayList) {
            ArrayList itemStackList = new ArrayList();
            Iterator var7 = ((ArrayList)object).iterator();

            while(var7.hasNext()) {
               ItemStack itemStack = (ItemStack)var7.next();
               itemStackList.addAll(new ArrayList(this.getDamagedList(itemStack).getItemStacks()));
            }

            recipeData.getItemStacks()[i] = new RecipeData$RecipeItemStack(itemStackList);
         }
      }

      return recipeData;
   }

   // $FF: synthetic method
   // $FF: bridge method
   public RecipeData watchRecipe(IRecipe var1) {
      return this.watchRecipe((ShapelessOreRecipe)var1);
   }
}
