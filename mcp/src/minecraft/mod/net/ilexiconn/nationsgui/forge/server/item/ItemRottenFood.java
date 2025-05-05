package net.ilexiconn.nationsgui.forge.server.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemRottenFood extends Item
{
    public ItemRottenFood()
    {
        super(4756);
        this.setUnlocalizedName("rottenfood");
        this.setTextureName("nationsgui:rotten_food");
        this.setCreativeTab(CreativeTabs.tabFood);
    }
}
