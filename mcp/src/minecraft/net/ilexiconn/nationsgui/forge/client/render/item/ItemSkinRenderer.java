/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 *  net.minecraftforge.client.IItemRenderer
 *  net.minecraftforge.client.IItemRenderer$ItemRenderType
 *  net.minecraftforge.client.IItemRenderer$ItemRendererHelper
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.render.item;

import net.ilexiconn.nationsgui.forge.client.itemskin.ItemSkinModel;
import net.ilexiconn.nationsgui.forge.client.util.Transform;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class ItemSkinRenderer
implements IItemRenderer {
    private final ItemSkinModel itemSkinModel;

    public ItemSkinRenderer(ItemSkinModel itemSkinModel) {
        this.itemSkinModel = itemSkinModel;
    }

    public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type) {
        return type == IItemRenderer.ItemRenderType.EQUIPPED || type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON;
    }

    public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper) {
        return false;
    }

    public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object ... data) {
        if (this.itemSkinModel.getModel() != null) {
            Transform transform = type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON ? this.itemSkinModel.getTransformFirstPerson() : this.itemSkinModel.getTransformEntity();
            GL11.glPushMatrix();
            transform.applyGL();
            this.itemSkinModel.getModel().render();
            GL11.glPopMatrix();
        }
    }
}

