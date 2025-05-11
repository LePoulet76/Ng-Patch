/*
 * Decompiled with CFR 0.152.
 */
package fr.nationsglory.itemmanager.data;

import java.util.List;

public class RecipeData {
    private List<List<ItemStack>> recipe;
    private ItemStack output;

    public List<List<ItemStack>> getRecipe() {
        return this.recipe;
    }

    public ItemStack getOutput() {
        return this.output;
    }

    public class ItemStack {
        private int id = 0;
        private int count = 1;
        private int metadata = 0;

        public int getId() {
            return this.id;
        }

        public int getCount() {
            return this.count;
        }

        public int getMetadata() {
            return this.metadata;
        }
    }
}

