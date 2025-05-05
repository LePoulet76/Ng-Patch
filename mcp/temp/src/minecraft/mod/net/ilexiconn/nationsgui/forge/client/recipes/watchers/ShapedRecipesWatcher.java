package net.ilexiconn.nationsgui.forge.client.recipes.watchers;

import net.ilexiconn.nationsgui.forge.client.RecipeWatcher;
import net.ilexiconn.nationsgui.forge.client.data.RecipeData;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;

public class ShapedRecipesWatcher extends RecipeWatcher<ShapedRecipes> {

   public RecipeData watchRecipe(ShapedRecipes recipe) {
      int width = recipe.field_77576_b;
      int height = recipe.field_77577_c;
      RecipeData recipeData = new RecipeData();

      for(int y = 0; y < 3; ++y) {
         if(y > height - 1) {
            return recipeData;
         }

         for(int x = 0; x < 3; ++x) {
            if(x > width - 1) {
               recipeData.getItemStacks()[x + y * 3] = null;
            } else if(recipe.field_77574_d[x + y * width] != null) {
               ItemStack itemStack = recipe.field_77574_d[x + y * width].func_77946_l();
               itemStack.field_77994_a = 1;
               recipeData.getItemStacks()[x + y * 3] = this.getDamagedList(itemStack);
            } else {
               recipeData.getItemStacks()[x + y * 3] = null;
            }
         }
      }

      return recipeData;
   }

   // $FF: synthetic method
   // $FF: bridge method
   public RecipeData watchRecipe(IRecipe var1) {
      return this.watchRecipe((ShapedRecipes)var1);
   }
}
