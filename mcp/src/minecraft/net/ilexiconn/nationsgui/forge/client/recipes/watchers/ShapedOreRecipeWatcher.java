package net.ilexiconn.nationsgui.forge.client.recipes.watchers;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import net.ilexiconn.nationsgui.forge.client.RecipeWatcher;
import net.ilexiconn.nationsgui.forge.client.data.RecipeData;
import net.ilexiconn.nationsgui.forge.client.data.RecipeData$RecipeItemStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ShapedOreRecipeWatcher extends RecipeWatcher<ShapedOreRecipe>
{
    public RecipeData watchRecipe(ShapedOreRecipe recipe)
    {
        int width;
        int height;

        try
        {
            Field recipeData = recipe.getClass().getDeclaredField("width");
            recipeData.setAccessible(true);
            width = recipeData.getInt(recipe);
            recipeData = recipe.getClass().getDeclaredField("height");
            recipeData.setAccessible(true);
            height = recipeData.getInt(recipe);
        }
        catch (IllegalAccessException var11)
        {
            var11.printStackTrace();
            return new RecipeData();
        }

        RecipeData var12 = new RecipeData();

        for (int y = 0; y < 3; ++y)
        {
            if (y > height - 1)
            {
                return var12;
            }

            for (int x = 0; x < 3; ++x)
            {
                if (x > width - 1)
                {
                    var12.getItemStacks()[x + y * 3] = null;
                }
                else
                {
                    Object object = recipe.getInput()[x + y * width];

                    if (object != null)
                    {
                        if (object instanceof ItemStack)
                        {
                            ItemStack itemStackList = ((ItemStack)object).copy();
                            itemStackList.stackSize = 1;
                            var12.getItemStacks()[x + y * 3] = this.getDamagedList(itemStackList);
                        }
                        else if (object instanceof ArrayList)
                        {
                            ArrayList var13 = new ArrayList();
                            Iterator var9 = ((ArrayList)object).iterator();

                            while (var9.hasNext())
                            {
                                ItemStack itemStack = (ItemStack)var9.next();
                                var13.addAll(new ArrayList(this.getDamagedList(itemStack).getItemStacks()));
                            }

                            var12.getItemStacks()[x + y * 3] = new RecipeData$RecipeItemStack(var13);
                        }
                    }
                }
            }
        }

        return var12;
    }

    public RecipeData watchRecipe(IRecipe var1)
    {
        return this.watchRecipe((ShapedOreRecipe)var1);
    }
}
