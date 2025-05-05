package net.minecraft.entity.boss;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IBossDisplayData
{
    float getMaxHealth();

    float getHealth();

    /**
     * Gets the username of the entity.
     */
    String getEntityName();
}
