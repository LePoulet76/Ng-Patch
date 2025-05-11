/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 *  net.minecraftforge.oredict.ShapedOreRecipe
 */
package net.ilexiconn.nationsgui.forge.client.recipes.watchers;

import java.lang.reflect.Field;
import java.util.ArrayList;
import net.ilexiconn.nationsgui.forge.client.RecipeWatcher;
import net.ilexiconn.nationsgui.forge.client.data.RecipeData;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ShapedOreRecipeWatcher
extends RecipeWatcher<ShapedOreRecipe> {
    @Override
    public RecipeData watchRecipe(ShapedOreRecipe recipe) {
        int height;
        int width;
        try {
            Field field = recipe.getClass().getDeclaredField("width");
            field.setAccessible(true);
            width = field.getInt(recipe);
            field = recipe.getClass().getDeclaredField("height");
            field.setAccessible(true);
            height = field.getInt(recipe);
        }
        catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
            return new RecipeData();
        }
        RecipeData recipeData = new RecipeData();
        for (int y = 0; y < 3; ++y) {
            if (y > height - 1) {
                return recipeData;
            }
            for (int x = 0; x < 3; ++x) {
                if (x > width - 1) {
                    recipeData.getItemStacks()[x + y * 3] = null;
                    continue;
                }
                Object object = recipe.getInput()[x + y * width];
                if (object == null) continue;
                if (object instanceof ItemStack) {
                    ItemStack itemStack = ((ItemStack)object).func_77946_l();
                    itemStack.field_77994_a = 1;
                    recipeData.getItemStacks()[x + y * 3] = this.getDamagedList(itemStack);
                    continue;
                }
                if (!(object instanceof ArrayList)) continue;
                ArrayList<ItemStack> itemStackList = new ArrayList<ItemStack>();
                for (ItemStack itemStack : (ArrayList)object) {
                    itemStackList.addAll(new ArrayList<ItemStack>(this.getDamagedList(itemStack).getItemStacks()));
                }
                recipeData.getItemStacks()[x + y * 3] = new RecipeData.RecipeItemStack(itemStackList);
            }
        }
        return recipeData;
    }
}

