/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.crafting.ShapelessRecipes
 */
package net.ilexiconn.nationsgui.forge.client.recipes.watchers;

import net.ilexiconn.nationsgui.forge.client.RecipeWatcher;
import net.ilexiconn.nationsgui.forge.client.data.RecipeData;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapelessRecipes;

public class ShapelessRecipesWatcher
extends RecipeWatcher<ShapelessRecipes> {
    @Override
    public RecipeData watchRecipe(ShapelessRecipes recipe) {
        RecipeData recipeData = new RecipeData();
        int i = 0;
        for (ItemStack itemStack : recipe.field_77579_b) {
            recipeData.getItemStacks()[i] = this.getDamagedList(itemStack);
            ++i;
        }
        return recipeData;
    }
}

