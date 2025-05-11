/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 */
package net.ilexiconn.nationsgui.forge.client.data;

import java.util.List;
import net.minecraft.item.ItemStack;

public class RecipeData {
    private RecipeItemStack[] itemStacks = new RecipeItemStack[9];

    public RecipeItemStack[] getItemStacks() {
        return this.itemStacks;
    }

    public static class RecipeItemStack {
        private List<ItemStack> itemStacks;
        private int displayIndex = -1;

        public RecipeItemStack(List<ItemStack> itemStacks) {
            this.itemStacks = itemStacks;
        }

        public ItemStack nextItem() {
            if (!this.itemStacks.isEmpty()) {
                ++this.displayIndex;
                if (this.displayIndex >= this.itemStacks.size()) {
                    this.displayIndex = 0;
                }
                return this.itemStacks.get(this.displayIndex).func_77946_l();
            }
            return null;
        }

        public List<ItemStack> getItemStacks() {
            return this.itemStacks;
        }
    }
}

