package net.ilexiconn.nationsgui.forge.client.data;

import net.ilexiconn.nationsgui.forge.client.data.RecipeData$RecipeItemStack;

public class RecipeData
{
    private RecipeData$RecipeItemStack[] itemStacks = new RecipeData$RecipeItemStack[9];

    public RecipeData$RecipeItemStack[] getItemStacks()
    {
        return this.itemStacks;
    }
}
