/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  fr.nationsglory.ngupgrades.client.renderer.item.GenericItemGeckoRenderer
 *  fr.nationsglory.ngupgrades.common.item.GenericGeckoItem
 *  net.minecraft.client.Minecraft
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraftforge.client.IItemRenderer$ItemRenderType
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.render.item;

import fr.nationsglory.ngupgrades.client.renderer.item.GenericItemGeckoRenderer;
import fr.nationsglory.ngupgrades.common.item.GenericGeckoItem;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class Frozen2023ShieldRendererDebug
extends GenericItemGeckoRenderer {
    public Frozen2023ShieldRendererDebug(String name, String modId) {
        super(name, modId);
    }

    public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object ... data) {
        GL11.glEnable((int)32826);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glScalef((float)2.0f, (float)2.0f, (float)2.0f);
        GL11.glRotated((double)50.0, (double)0.0, (double)1.0, (double)0.0);
        if (type.equals((Object)IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON)) {
            GL11.glRotated((double)60.0, (double)0.0, (double)0.0, (double)1.0);
            GL11.glRotated((double)-90.0, (double)0.0, (double)1.0, (double)0.0);
            GL11.glTranslated((double)0.0, (double)-0.2, (double)-1.0);
        } else if (type.equals((Object)IItemRenderer.ItemRenderType.INVENTORY)) {
            GL11.glScalef((float)0.6f, (float)0.6f, (float)0.6f);
            GL11.glRotated((double)-45.0, (double)0.0, (double)1.0, (double)0.0);
            GL11.glRotated((double)30.0, (double)0.0, (double)0.0, (double)1.0);
            GL11.glTranslated((double)-0.2, (double)-0.5, (double)0.0);
        } else if (type.equals((Object)IItemRenderer.ItemRenderType.EQUIPPED)) {
            GL11.glTranslated((double)-0.5, (double)-0.3, (double)0.0);
        } else {
            GL11.glTranslated((double)-0.5, (double)-0.3, (double)0.0);
        }
        GL11.glPushMatrix();
        this.render((Item)((GenericGeckoItem)item.func_77973_b()), item, Minecraft.func_71410_x().field_71428_T.field_74281_c);
        GL11.glPopMatrix();
        GL11.glDisable((int)3042);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }
}

