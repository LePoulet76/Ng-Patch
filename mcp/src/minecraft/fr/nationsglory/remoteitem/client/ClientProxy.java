package fr.nationsglory.remoteitem.client;

import fr.nationsglory.remoteitem.RemoteItem;
import fr.nationsglory.remoteitem.client.creativetab.CustomCreativeTab;
import fr.nationsglory.remoteitem.common.CommonProxy;
import fr.nationsglory.remoteitem.common.data.ItemData;
import java.util.Iterator;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ClientProxy extends CommonProxy
{
    public void setCreativeTabItem(Item item, ItemData data)
    {
        if (!data.getCreativeTab().equals(""))
        {
            boolean found = false;
            Iterator creativeTab = RemoteItem.creativeTabsMap.iterator();

            while (creativeTab.hasNext())
            {
                CreativeTabs creativeTabs = (CreativeTabs)creativeTab.next();

                if (creativeTabs.getTabLabel().equals(data.getCreativeTab()))
                {
                    item.setCreativeTab(creativeTabs);
                    found = true;
                }
            }

            if (!found)
            {
                CustomCreativeTab creativeTab1 = new CustomCreativeTab(data.getCreativeTab());
                RemoteItem.creativeTabsMap.add(creativeTab1);
                item.setCreativeTab(creativeTab1);
            }
        }
    }
}
