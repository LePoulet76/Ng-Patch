/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.item.ItemStack
 *  net.minecraftforge.client.IItemRenderer
 *  net.minecraftforge.client.IItemRenderer$ItemRenderType
 *  net.minecraftforge.client.IItemRenderer$ItemRendererHelper
 */
package net.ilexiconn.nationsgui.forge.client.render.item;

import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemBadgeRenderer
implements IItemRenderer {
    public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type) {
        return type == IItemRenderer.ItemRenderType.INVENTORY;
    }

    public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper) {
        return false;
    }

    public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object ... data) {
        if (item.func_77978_p() != null) {
            String badgeType = item.func_77978_p().func_74779_i("BadgeID");
            Tessellator tessellator = Tessellator.field_78398_a;
            int par5 = 16;
            int par4 = 16;
            Minecraft.func_71410_x().func_110434_K().func_110577_a(NationsGUI.BADGES_RESOURCES.get(badgeType));
            tessellator.func_78382_b();
            tessellator.func_78374_a(0.0, (double)par5, 0.0, 0.0, 1.0);
            tessellator.func_78374_a((double)par4, (double)par5, 0.0, 1.0, 1.0);
            tessellator.func_78374_a((double)par4, 0.0, 0.0, 1.0, 0.0);
            tessellator.func_78374_a(0.0, 0.0, 0.0, 0.0, 0.0);
            tessellator.func_78381_a();
        }
    }
}

