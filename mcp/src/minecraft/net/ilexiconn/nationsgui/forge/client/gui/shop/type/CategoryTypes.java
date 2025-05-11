/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 */
package net.ilexiconn.nationsgui.forge.client.gui.shop.type;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.gui.shop.type.ICategoryType;
import net.ilexiconn.nationsgui.forge.client.gui.shop.type.ShopType;
import net.ilexiconn.nationsgui.forge.client.gui.shop.type.URLType;

@SideOnly(value=Side.CLIENT)
public enum CategoryTypes {
    SHOP(new ShopType()),
    URL(new URLType());

    private ICategoryType type;

    private CategoryTypes(ICategoryType type) {
        this.type = type;
    }

    public ICategoryType getType() {
        return this.type;
    }
}

