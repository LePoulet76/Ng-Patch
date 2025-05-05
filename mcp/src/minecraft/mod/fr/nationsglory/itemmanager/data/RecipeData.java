package fr.nationsglory.itemmanager.data;

import fr.nationsglory.itemmanager.data.RecipeData$ItemStack;
import java.util.List;

public class RecipeData
{
    private List<List<RecipeData$ItemStack>> recipe;
    private RecipeData$ItemStack output;

    public List<List<RecipeData$ItemStack>> getRecipe()
    {
        return this.recipe;
    }

    public RecipeData$ItemStack getOutput()
    {
        return this.output;
    }
}
