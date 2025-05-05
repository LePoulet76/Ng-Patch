package fr.nationsglory.itemmanager.client;

import fr.nationsglory.itemmanager.CommonProxy;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import net.ilexiconn.nationsgui.forge.server.asm.NationsGUITransformer;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class ClientProxy extends CommonProxy
{
    public void postInit()
    {
        super.postInit();
        Iterator var1 = NationsGUITransformer.config.getCreativeTabBlacklist().iterator();

        while (var1.hasNext())
        {
            String tabName = (String)var1.next();
            ArrayList creativeTabsList = new ArrayList();
            CreativeTabs tabs = null;
            boolean passed = false;
            CreativeTabs[] var6 = CreativeTabs.creativeTabArray;
            int var7 = var6.length;
            int var8;

            for (var8 = 0; var8 < var7; ++var8)
            {
                CreativeTabs item = var6[var8];

                if (!item.getTabLabel().equals(tabName))
                {
                    if (passed)
                    {
                        try
                        {
                            Field e = CreativeTabs.class.getDeclaredField(NationsGUITransformer.inDevelopment ? "tabIndex" : "tabIndex");
                            e.setAccessible(true);
                            e.set(item, Integer.valueOf(item.getTabIndex() - 1));
                        }
                        catch (NoSuchFieldException var11)
                        {
                            var11.printStackTrace();
                        }
                        catch (IllegalAccessException var12)
                        {
                            var12.printStackTrace();
                        }
                    }

                    creativeTabsList.add(item);
                }
                else
                {
                    tabs = item;
                    passed = true;
                }
            }

            if (tabs != null)
            {
                Item[] var13 = Item.itemsList;
                var7 = var13.length;

                for (var8 = 0; var8 < var7; ++var8)
                {
                    Item var14 = var13[var8];

                    if (var14 != null && var14.getCreativeTab() != null && var14.getCreativeTab().equals(tabs))
                    {
                        var14.setCreativeTab(CreativeTabs.tabMisc);

                        if (var14 instanceof ItemBlock)
                        {
                            Block.blocksList[var14.itemID].setCreativeTab(CreativeTabs.tabMisc);
                        }
                    }
                }

                CreativeTabs.creativeTabArray = (CreativeTabs[])creativeTabsList.toArray(new CreativeTabs[creativeTabsList.size()]);
            }
        }
    }
}
