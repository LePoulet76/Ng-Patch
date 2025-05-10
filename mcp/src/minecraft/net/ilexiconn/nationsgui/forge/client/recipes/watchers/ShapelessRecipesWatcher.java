package net.ilexiconn.nationsgui.forge.client.recipes.watchers;

import java.util.Iterator;
import net.ilexiconn.nationsgui.forge.client.RecipeWatcher;
import net.ilexiconn.nationsgui.forge.client.data.RecipeData;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapelessRecipes;

public class ShapelessRecipesWatcher extends RecipeWatcher<ShapelessRecipes>
{
    public RecipeData watchRecipe(ShapelessRecipes recipe)
    {
        RecipeData recipeData = new RecipeData();
        int i = 0;

        for (Iterator var4 = recipe.recipeItems.iterator(); var4.hasNext(); ++i)
        {
            ItemStack itemStack = (ItemStack)var4.next();
            recipeData.getItemStacks()[i] = this.getDamagedList(itemStack);
        }

        return recipeData;
    }

    public RecipeData watchRecipe(IRecipe var1)
    {
        return this.watchRecipe((ShapelessRecipes)var1);
    }
}
