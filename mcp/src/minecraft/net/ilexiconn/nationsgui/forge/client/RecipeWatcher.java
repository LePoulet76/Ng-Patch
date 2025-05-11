/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.crafting.IRecipe
 */
package net.ilexiconn.nationsgui.forge.client;

import java.util.ArrayList;
import java.util.Collections;
import net.ilexiconn.nationsgui.forge.client.data.RecipeData;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

public abstract class RecipeWatcher<T extends IRecipe> {
    public abstract RecipeData watchRecipe(T var1);

    protected RecipeData.RecipeItemStack getDamagedList(ItemStack itemStack) {
        if (itemStack.func_77960_j() == Short.MAX_VALUE) {
            ArrayList<ItemStack> itemStackList = new ArrayList<ItemStack>();
            itemStack.func_77973_b().func_77633_a(itemStack.field_77993_c, CreativeTabs.field_78027_g, itemStackList);
            return new RecipeData.RecipeItemStack(itemStackList);
        }
        return new RecipeData.RecipeItemStack(Collections.singletonList(itemStack));
    }
}

