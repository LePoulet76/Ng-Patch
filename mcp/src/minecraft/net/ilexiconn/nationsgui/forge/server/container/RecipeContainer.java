package net.ilexiconn.nationsgui.forge.server.container;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.RecipeWatcher;
import net.ilexiconn.nationsgui.forge.client.data.RecipeData;
import net.ilexiconn.nationsgui.forge.client.data.RecipeData$RecipeItemStack;
import net.ilexiconn.nationsgui.forge.client.gui.CustomCraftingGUI;
import net.ilexiconn.nationsgui.forge.client.gui.RecipeListGUI;
import net.minecraft.client.Minecraft;
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

public class RecipeContainer extends Container
{
    IInventory inventory = new InventoryBasic("Recipe", false, 55);
    private Map<ItemStack, RecipeData> foundRecipes = new HashMap();
    private List<ItemStack> foundResults = new ArrayList();
    private int currentIndex = 0;
    private int ticks = 0;

    public RecipeContainer()
    {
        int y;
        int x;

        for (y = 0; y < 5; ++y)
        {
            for (x = 0; x < 9; ++x)
            {
                this.addSlotToContainer(new Slot(this.inventory, x + 9 * y, x * 18 + 10, y * 18 + 45));
            }
        }

        for (y = 0; y < 3; ++y)
        {
            for (x = 0; x < 3; ++x)
            {
                this.addSlotToContainer(new Slot(this.inventory, 45 + x + y * 3, x * 18 + 221, y * 18 + 53));
            }
        }

        this.addSlotToContainer(new Slot(this.inventory, 54, 315, 71));
    }

    public void next()
    {
        int index = this.currentIndex + 1;
        this.setFoundRecipe(index >= this.foundRecipes.size() ? 0 : index);
    }

    public void previous()
    {
        int index = this.currentIndex - 1;
        this.setFoundRecipe(index < 0 ? this.foundRecipes.size() - 1 : index);
    }

    private void setFoundRecipe(int index)
    {
        if (!this.foundRecipes.isEmpty())
        {
            Entry entry = this.getRecipeFromIndex(index);
            int i = 0;
            RecipeData$RecipeItemStack[] var4 = ((RecipeData)entry.getValue()).getItemStacks();
            int var5 = var4.length;

            for (int var6 = 0; var6 < var5; ++var6)
            {
                RecipeData$RecipeItemStack itemStack = var4[var6];
                this.inventory.setInventorySlotContents(45 + i, itemStack != null ? itemStack.nextItem() : null);
                ++i;
            }

            this.inventory.setInventorySlotContents(54, (ItemStack)entry.getKey());
            this.currentIndex = index;
        }
    }

    public void updateItemStackAnimation()
    {
        ++this.ticks;

        if (this.ticks >= 25 && !this.foundRecipes.isEmpty())
        {
            Entry entry = this.getRecipeFromIndex(this.currentIndex);
            int i = 0;
            RecipeData$RecipeItemStack[] var3 = ((RecipeData)entry.getValue()).getItemStacks();
            int var4 = var3.length;

            for (int var5 = 0; var5 < var4; ++var5)
            {
                RecipeData$RecipeItemStack itemStack = var3[var5];

                if (itemStack != null)
                {
                    this.inventory.setInventorySlotContents(45 + i, itemStack.nextItem());
                }

                ++i;
            }

            this.ticks = 0;
        }
    }

    public boolean hasMutipleResults()
    {
        return this.foundRecipes.size() > 1;
    }

    public int displayResults(int skip)
    {
        int start;
        int i;

        for (start = 0; start < 5; ++start)
        {
            for (i = 0; i < 9; ++i)
            {
                this.inventory.setInventorySlotContents(i + 9 * start, (ItemStack)null);
            }
        }

        start = skip * 9;

        for (i = 0; i < 45; ++i)
        {
            if (i + start < this.foundResults.size())
            {
                this.inventory.setInventorySlotContents(i, (ItemStack)this.foundResults.get(i + start));
            }
        }

        return this.foundResults.size() / 9 - skip;
    }

    public void generateItemList(String filter, boolean displayAll)
    {
        this.foundResults.clear();
        Item[] var3 = Item.itemsList;
        int var4 = var3.length;

        for (int var5 = 0; var5 < var4; ++var5)
        {
            Item item = var3[var5];

            if (item != null)
            {
                ArrayList itemStackList = new ArrayList();
                item.getSubItems(item.itemID, CreativeTabs.tabAllSearch, itemStackList);
                Iterator var8 = itemStackList.iterator();
                label45:

                while (var8.hasNext())
                {
                    ItemStack itemStack = (ItemStack)var8.next();

                    if (filter.equals("") || this.idEqual(filter, itemStack) || StringUtils.containsIgnoreCase(itemStack.getDisplayName(), filter))
                    {
                        Iterator var10 = CraftingManager.getInstance().getRecipeList().iterator();
                        IRecipe iRecipe;

                        do
                        {
                            if (!var10.hasNext())
                            {
                                continue label45;
                            }

                            Object object = var10.next();
                            iRecipe = (IRecipe)object;
                        }
                        while (!displayAll && (iRecipe.getRecipeOutput() == null || !iRecipe.getRecipeOutput().isItemEqual(itemStack) || !ClientProxy.recipeWatcherMap.containsKey(iRecipe.getClass())));

                        this.foundResults.add(itemStack);
                    }
                }
            }
        }
    }

    private boolean idEqual(String filter, ItemStack itemStack)
    {
        try
        {
            int ignore = Integer.parseInt(filter);
            return itemStack.itemID == ignore;
        }
        catch (NumberFormatException var4)
        {
            return false;
        }
    }

    private Entry<ItemStack, RecipeData> getRecipeFromIndex(int index)
    {
        int i = 0;

        for (Iterator var3 = this.foundRecipes.entrySet().iterator(); var3.hasNext(); ++i)
        {
            Entry stackEntry = (Entry)var3.next();

            if (i == index)
            {
                return stackEntry;
            }
        }

        return null;
    }

    public ItemStack slotClick(int par1, int par2, int par3, EntityPlayer par4EntityPlayer)
    {
        if (par1 == 54 && this.inventory.getStackInSlot(54) != null)
        {
            RecipeListGUI var11 = (RecipeListGUI)Minecraft.getMinecraft().currentScreen;

            if (var11.getPrev() instanceof CustomCraftingGUI)
            {
                CustomCraftingGUI var12 = (CustomCraftingGUI)var11.getPrev();

                for (int var13 = 0; var13 < 10; ++var13)
                {
                    var12.selectedRecipe[var13] = this.inventory.getStackInSlot(45 + var13);
                }

                Minecraft.getMinecraft().displayGuiScreen(var12);
            }
        }
        else if (par1 != -999)
        {
            ItemStack target = this.inventory.getStackInSlot(par1);

            if (target == null)
            {
                return null;
            }

            target = target.copy();
            boolean cleared = false;
            Iterator i = CraftingManager.getInstance().getRecipeList().iterator();

            while (i.hasNext())
            {
                Object object = i.next();
                IRecipe iRecipe = (IRecipe)object;

                if (iRecipe.getRecipeOutput() != null && iRecipe.getRecipeOutput().isItemEqual(target) && ClientProxy.recipeWatcherMap.containsKey(iRecipe.getClass()))
                {
                    if (!cleared)
                    {
                        this.foundRecipes.clear();

                        for (int recipeWatcher = 0; recipeWatcher < 9; ++recipeWatcher)
                        {
                            this.inventory.setInventorySlotContents(45 + recipeWatcher, (ItemStack)null);
                        }

                        cleared = true;
                    }

                    RecipeWatcher var14 = (RecipeWatcher)ClientProxy.recipeWatcherMap.get(iRecipe.getClass());
                    this.foundRecipes.put(iRecipe.getRecipeOutput(), var14.watchRecipe(iRecipe));
                }
            }

            if (cleared)
            {
                this.setFoundRecipe(0);
            }
        }

        return null;
    }

    public boolean canInteractWith(EntityPlayer entityplayer)
    {
        return false;
    }
}
