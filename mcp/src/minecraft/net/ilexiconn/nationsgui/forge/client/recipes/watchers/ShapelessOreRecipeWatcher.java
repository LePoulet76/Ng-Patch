/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 *  net.minecraftforge.oredict.ShapelessOreRecipe
 */
package net.ilexiconn.nationsgui.forge.client.recipes.watchers;

import java.util.ArrayList;
import net.ilexiconn.nationsgui.forge.client.RecipeWatcher;
import net.ilexiconn.nationsgui.forge.client.data.RecipeData;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ShapelessOreRecipeWatcher
extends RecipeWatcher<ShapelessOreRecipe> {
    @Override
    public RecipeData watchRecipe(ShapelessOreRecipe recipe) {
        RecipeData recipeData = new RecipeData();
        int i = 0;
        for (Object object : recipe.getInput()) {
            if (object instanceof ItemStack) {
                recipeData.getItemStacks()[i] = this.getDamagedList((ItemStack)object);
            } else if (object instanceof ArrayList) {
                ArrayList<ItemStack> itemStackList = new ArrayList<ItemStack>();
                for (ItemStack itemStack : (ArrayList)object) {
                    itemStackList.addAll(new ArrayList<ItemStack>(this.getDamagedList(itemStack).getItemStacks()));
                }
                recipeData.getItemStacks()[i] = new RecipeData.RecipeItemStack(itemStackList);
            }
            ++i;
        }
        return recipeData;
    }
}

