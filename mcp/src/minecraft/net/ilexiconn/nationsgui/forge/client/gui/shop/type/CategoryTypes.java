package net.ilexiconn.nationsgui.forge.client.gui.shop.type;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public enum CategoryTypes
{
    SHOP(new ShopType()),
    URL(new URLType());
    private ICategoryType type;

    private CategoryTypes(ICategoryType type)
    {
        this.type = type;
    }

    public ICategoryType getType()
    {
        return this.type;
    }
}
