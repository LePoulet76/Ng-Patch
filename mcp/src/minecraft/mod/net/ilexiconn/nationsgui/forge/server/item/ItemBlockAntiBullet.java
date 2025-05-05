package net.ilexiconn.nationsgui.forge.server.item;

import java.util.List;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemBlockAntiBullet extends Item
{
    public ItemBlockAntiBullet()
    {
        super(4754);
        this.maxStackSize = 64;
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.setUnlocalizedName("antibullet");
    }

    /**
     * allows items to add custom lines of information to the mouseover description
     */
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        par3List.add("\u00a77Bloque les balles");
    }

    public void registerIcons(IconRegister iconRegister)
    {
        this.itemIcon = iconRegister.registerIcon("nationsgui:antibullet");
    }
}
