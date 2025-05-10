package net.ilexiconn.nationsgui.forge.client.item;

import net.ilexiconn.nationsgui.forge.client.itemskin.ItemSkinBow;
import net.ilexiconn.nationsgui.forge.client.itemskin.ItemSkinSimple;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;

public class FakeItemSkin extends Item
{
    public FakeItemSkin(int par1)
    {
        super(par1);
    }

    public void registerIcons(IconRegister par1IconRegister)
    {
        ItemSkinSimple.register(par1IconRegister);
        ItemSkinBow.register(par1IconRegister);
    }
}
