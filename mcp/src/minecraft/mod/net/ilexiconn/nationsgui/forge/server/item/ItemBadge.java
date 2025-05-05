package net.ilexiconn.nationsgui.forge.server.item;

import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemBadge extends Item
{
    public ItemBadge(int i)
    {
        super(i - 256);
    }

    public String getItemStackDisplayName(ItemStack par1ItemStack)
    {
        NBTTagCompound tagCompound = par1ItemStack.getTagCompound();

        if (tagCompound != null && tagCompound.hasKey("BadgeID"))
        {
            String badgeID = tagCompound.getString("BadgeID");
            return I18n.getStringParams("item.badge", new Object[] {NationsGUI.BADGES_NAMES.get(badgeID)});
        }
        else
        {
            return super.getItemStackDisplayName(par1ItemStack);
        }
    }

    public String getItemDisplayName(ItemStack par1ItemStack)
    {
        NBTTagCompound tagCompound = par1ItemStack.getTagCompound();

        if (tagCompound != null && tagCompound.hasKey("BadgeID"))
        {
            String badgeID = tagCompound.getString("BadgeID");
            return I18n.getStringParams("item.badge", new Object[] {NationsGUI.BADGES_NAMES.get(badgeID)});
        }
        else
        {
            return super.getItemDisplayName(par1ItemStack);
        }
    }
}
