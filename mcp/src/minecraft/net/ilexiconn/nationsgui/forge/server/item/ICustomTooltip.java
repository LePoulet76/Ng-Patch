package net.ilexiconn.nationsgui.forge.server.item;

import net.minecraft.item.ItemStack;

public interface ICustomTooltip
{
    int getTooltipColor(ItemStack var1);

    int getTooltipBackgroundColor(ItemStack var1);
}
