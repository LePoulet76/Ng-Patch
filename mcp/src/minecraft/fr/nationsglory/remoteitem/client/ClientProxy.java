/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.item.Item
 */
package fr.nationsglory.remoteitem.client;

import fr.nationsglory.remoteitem.RemoteItem;
import fr.nationsglory.remoteitem.client.creativetab.CustomCreativeTab;
import fr.nationsglory.remoteitem.common.CommonProxy;
import fr.nationsglory.remoteitem.common.data.ItemData;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ClientProxy
extends CommonProxy {
    @Override
    public void setCreativeTabItem(Item item, ItemData data) {
        if (!data.getCreativeTab().equals("")) {
            boolean found = false;
            for (CreativeTabs creativeTabs : RemoteItem.creativeTabsMap) {
                if (!creativeTabs.func_78013_b().equals(data.getCreativeTab())) continue;
                item.func_77637_a(creativeTabs);
                found = true;
            }
            if (!found) {
                CustomCreativeTab creativeTab = new CustomCreativeTab(data.getCreativeTab());
                RemoteItem.creativeTabsMap.add(creativeTab);
                item.func_77637_a((CreativeTabs)creativeTab);
            }
        }
    }
}

