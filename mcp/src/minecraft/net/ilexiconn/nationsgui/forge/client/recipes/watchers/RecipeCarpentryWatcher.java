package net.ilexiconn.nationsgui.forge.client.recipes.watchers;

import net.ilexiconn.nationsgui.forge.client.RecipeWatcher;
import net.ilexiconn.nationsgui.forge.client.data.RecipeData;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import noppes.npcs.controllers.RecipeCarpentry;

public class RecipeCarpentryWatcher extends RecipeWatcher<RecipeCarpentry>
{
    public RecipeData watchRecipe(RecipeCarpentry recipe)
    {
        int width = recipe.recipeWidth;
        int height = recipe.recipeHeight;
        RecipeData recipeData = new RecipeData();

        for (int y = 0; y < 3; ++y)
        {
            if (y > height - 1)
            {
                return recipeData;
            }

            for (int x = 0; x < 3; ++x)
            {
                if (x > width - 1)
                {
                    recipeData.getItemStacks()[x + y * 3] = null;
                }
                else if (recipe.getCraftingItem(x + y * width) != null)
                {
                    ItemStack itemStack = recipe.getCraftingItem(x + y * width).copy();
                    itemStack.stackSize = 1;
                    recipeData.getItemStacks()[x + y * 3] = this.getDamagedList(itemStack);
                }
                else
                {
                    recipeData.getItemStacks()[x + y * 3] = null;
                }
            }
        }

        return recipeData;
    }

    public RecipeData watchRecipe(IRecipe var1)
    {
        return this.watchRecipe((RecipeCarpentry)var1);
    }
}
