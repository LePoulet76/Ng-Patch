package net.ilexiconn.nationsgui.forge.server.util;

import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class HatsHelper
{
    public static ItemStack generateHatItem(String hatID)
    {
        ItemStack itemHat = new ItemStack(NationsGUI.HATBLOCK.blockID, 1, 0);

        if (!itemHat.hasTagCompound())
        {
            itemHat.stackTagCompound = new NBTTagCompound();
        }

        itemHat.stackTagCompound.setString("HatID", hatID);
        return itemHat;
    }
}
