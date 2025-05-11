/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiButton
 */
package net.ilexiconn.nationsgui.forge.client.gui.shop.type;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.gui.shop.Category;
import net.ilexiconn.nationsgui.forge.client.gui.shop.ShopGUI;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;

@SideOnly(value=Side.CLIENT)
public interface ICategoryType {
    public void init(int var1, int var2, ShopGUI var3, Category var4, List<GuiButton> var5);

    public void render(int var1, int var2, int var3, int var4, ShopGUI var5, Category var6, FontRenderer var7);

    public void renderPost(int var1, int var2, int var3, int var4, ShopGUI var5, Category var6, FontRenderer var7);

    public void mouseClicked(int var1, int var2, int var3, int var4, int var5, ShopGUI var6, Category var7);

    public void actionPerformed(GuiButton var1, ShopGUI var2, Category var3);
}

