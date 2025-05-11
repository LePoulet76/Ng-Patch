/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.inventory.Container
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.inventory.InventoryBasic
 *  net.minecraft.inventory.Slot
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.crafting.CraftingManager
 *  net.minecraft.item.crafting.IRecipe
 *  org.apache.commons.lang3.StringUtils
 */
package net.ilexiconn.nationsgui.forge.server.container;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.RecipeWatcher;
import net.ilexiconn.nationsgui.forge.client.data.RecipeData;
import net.ilexiconn.nationsgui.forge.client.gui.CustomCraftingGUI;
import net.ilexiconn.nationsgui.forge.client.gui.RecipeListGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import org.apache.commons.lang3.StringUtils;

public class RecipeContainer
extends Container {
    IInventory inventory = new InventoryBasic("Recipe", false, 55);
    private Map<ItemStack, RecipeData> foundRecipes = new HashMap<ItemStack, RecipeData>();
    private List<ItemStack> foundResults = new ArrayList<ItemStack>();
    private int currentIndex = 0;
    private int ticks = 0;

    public RecipeContainer() {
        int x;
        int y;
        for (y = 0; y < 5; ++y) {
            for (x = 0; x < 9; ++x) {
                this.func_75146_a(new Slot(this.inventory, x + 9 * y, x * 18 + 10, y * 18 + 45));
            }
        }
        for (y = 0; y < 3; ++y) {
            for (x = 0; x < 3; ++x) {
                this.func_75146_a(new Slot(this.inventory, 45 + x + y * 3, x * 18 + 221, y * 18 + 53));
            }
        }
        this.func_75146_a(new Slot(this.inventory, 54, 315, 71));
    }

    public void next() {
        int index = this.currentIndex + 1;
        this.setFoundRecipe(index >= this.foundRecipes.size() ? 0 : index);
    }

    public void previous() {
        int index = this.currentIndex - 1;
        this.setFoundRecipe(index < 0 ? this.foundRecipes.size() - 1 : index);
    }

    private void setFoundRecipe(int index) {
        if (!this.foundRecipes.isEmpty()) {
            Map.Entry<ItemStack, RecipeData> entry = this.getRecipeFromIndex(index);
            int i = 0;
            for (RecipeData.RecipeItemStack itemStack : entry.getValue().getItemStacks()) {
                this.inventory.func_70299_a(45 + i, itemStack != null ? itemStack.nextItem() : null);
                ++i;
            }
            this.inventory.func_70299_a(54, entry.getKey());
            this.currentIndex = index;
        }
    }

    public void updateItemStackAnimation() {
        ++this.ticks;
        if (this.ticks >= 25 && !this.foundRecipes.isEmpty()) {
            Map.Entry<ItemStack, RecipeData> entry = this.getRecipeFromIndex(this.currentIndex);
            int i = 0;
            for (RecipeData.RecipeItemStack itemStack : entry.getValue().getItemStacks()) {
                if (itemStack != null) {
                    this.inventory.func_70299_a(45 + i, itemStack.nextItem());
                }
                ++i;
            }
            this.ticks = 0;
        }
    }

    public boolean hasMutipleResults() {
        return this.foundRecipes.size() > 1;
    }

    public int displayResults(int skip) {
        for (int y = 0; y < 5; ++y) {
            for (int x = 0; x < 9; ++x) {
                this.inventory.func_70299_a(x + 9 * y, null);
            }
        }
        int start = skip * 9;
        for (int i = 0; i < 45; ++i) {
            if (i + start >= this.foundResults.size()) continue;
            this.inventory.func_70299_a(i, this.foundResults.get(i + start));
        }
        return this.foundResults.size() / 9 - skip;
    }

    public void generateItemList(String filter, boolean displayAll) {
        this.foundResults.clear();
        for (Item item : Item.field_77698_e) {
            if (item == null) continue;
            ArrayList itemStackList = new ArrayList();
            item.func_77633_a(item.field_77779_bT, CreativeTabs.field_78027_g, itemStackList);
            block1: for (ItemStack itemStack : itemStackList) {
                if (!filter.equals("") && !this.idEqual(filter, itemStack) && !StringUtils.containsIgnoreCase((CharSequence)itemStack.func_82833_r(), (CharSequence)filter)) continue;
                for (Object object : CraftingManager.func_77594_a().func_77592_b()) {
                    IRecipe iRecipe = (IRecipe)object;
                    if (!displayAll && (iRecipe.func_77571_b() == null || !iRecipe.func_77571_b().func_77969_a(itemStack) || !ClientProxy.recipeWatcherMap.containsKey(iRecipe.getClass()))) continue;
                    this.foundResults.add(itemStack);
                    continue block1;
                }
            }
        }
    }

    private boolean idEqual(String filter, ItemStack itemStack) {
        try {
            int id = Integer.parseInt(filter);
            return itemStack.field_77993_c == id;
        }
        catch (NumberFormatException ignore) {
            return false;
        }
    }

    private Map.Entry<ItemStack, RecipeData> getRecipeFromIndex(int index) {
        int i = 0;
        for (Map.Entry<ItemStack, RecipeData> stackEntry : this.foundRecipes.entrySet()) {
            if (i == index) {
                return stackEntry;
            }
            ++i;
        }
        return null;
    }

    public ItemStack func_75144_a(int par1, int par2, int par3, EntityPlayer par4EntityPlayer) {
        if (par1 == 54 && this.inventory.func_70301_a(54) != null) {
            RecipeListGUI recipeListGUI = (RecipeListGUI)Minecraft.func_71410_x().field_71462_r;
            if (recipeListGUI.getPrev() instanceof CustomCraftingGUI) {
                CustomCraftingGUI craftingGUI = (CustomCraftingGUI)recipeListGUI.getPrev();
                for (int i = 0; i < 10; ++i) {
                    craftingGUI.selectedRecipe[i] = this.inventory.func_70301_a(45 + i);
                }
                Minecraft.func_71410_x().func_71373_a((GuiScreen)craftingGUI);
            }
        } else if (par1 != -999) {
            ItemStack target = this.inventory.func_70301_a(par1);
            if (target == null) {
                return null;
            }
            target = target.func_77946_l();
            boolean cleared = false;
            for (Object object : CraftingManager.func_77594_a().func_77592_b()) {
                IRecipe iRecipe = (IRecipe)object;
                if (iRecipe.func_77571_b() == null || !iRecipe.func_77571_b().func_77969_a(target) || !ClientProxy.recipeWatcherMap.containsKey(iRecipe.getClass())) continue;
                if (!cleared) {
                    this.foundRecipes.clear();
                    for (int i = 0; i < 9; ++i) {
                        this.inventory.func_70299_a(45 + i, null);
                    }
                    cleared = true;
                }
                RecipeWatcher recipeWatcher = ClientProxy.recipeWatcherMap.get(iRecipe.getClass());
                this.foundRecipes.put(iRecipe.func_77571_b(), recipeWatcher.watchRecipe(iRecipe));
            }
            if (cleared) {
                this.setFoundRecipe(0);
            }
        }
        return null;
    }

    public boolean func_75145_c(EntityPlayer entityplayer) {
        return false;
    }
}

