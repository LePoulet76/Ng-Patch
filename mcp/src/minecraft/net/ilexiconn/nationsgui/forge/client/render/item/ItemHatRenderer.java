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

import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.itemskin.HatSkin;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class ItemHatRenderer
implements IItemRenderer {
    public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type) {
        return true;
    }

    public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper) {
        return true;
    }

    public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object ... data) {
        switch (type) {
            case EQUIPPED_FIRST_PERSON: {
                GL11.glTranslatef((float)0.0f, (float)0.5f, (float)0.2f);
                GL11.glRotatef((float)180.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                GL11.glRotatef((float)180.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                this.renderHat(item);
                break;
            }
            case EQUIPPED: {
                GL11.glRotatef((float)180.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glTranslatef((float)0.8f, (float)0.0f, (float)-0.3f);
                GL11.glRotatef((float)90.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                this.renderHat(item);
                break;
            }
            case INVENTORY: {
                GL11.glRotatef((float)180.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glTranslatef((float)0.4f, (float)0.2f, (float)-0.3f);
                this.renderHat(item);
                break;
            }
            case ENTITY: {
                GL11.glRotatef((float)180.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                this.renderHat(item);
                break;
            }
        }
    }

    private void renderHat(ItemStack item) {
        if (item.func_77942_o() && item.field_77990_d.func_74764_b("HatID") && ClientProxy.SKIN_MANAGER.getSkinFromID(item.field_77990_d.func_74779_i("HatID")) != null) {
            HatSkin hatSkin = (HatSkin)ClientProxy.SKIN_MANAGER.getSkinFromID(item.field_77990_d.func_74779_i("HatID"));
            GL11.glEnable((int)3553);
            if (hatSkin != null) {
                hatSkin.getModel().updateModel(hatSkin.getTransform("entity"));
                hatSkin.getModel().render(0.0f);
            }
        }
    }
}

