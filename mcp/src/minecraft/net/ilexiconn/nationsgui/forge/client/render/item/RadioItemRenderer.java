/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.ResourceLocation
 *  net.minecraftforge.client.IItemRenderer
 *  net.minecraftforge.client.IItemRenderer$ItemRenderType
 *  net.minecraftforge.client.IItemRenderer$ItemRendererHelper
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.render.item;

import net.ilexiconn.nationsgui.forge.client.model.block.RadioModel;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class RadioItemRenderer
implements IItemRenderer {
    public static final ResourceLocation TEXTURE = new ResourceLocation("nationsgui", "textures/blocks/radio.png");
    public static final RadioModel MODEL = new RadioModel();

    public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type) {
        return type != IItemRenderer.ItemRenderType.FIRST_PERSON_MAP;
    }

    public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper) {
        return helper != IItemRenderer.ItemRendererHelper.BLOCK_3D;
    }

    public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object ... data) {
        switch (type) {
            case ENTITY: {
                this.renderBlock(0.0f, 1.5f, 0.0f);
                break;
            }
            case EQUIPPED: {
                GL11.glScalef((float)1.4f, (float)1.4f, (float)1.4f);
                GL11.glRotatef((float)30.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                GL11.glTranslatef((float)-0.6f, (float)0.2f, (float)0.2f);
                this.renderBlock(0.5f, 1.5f, 0.5f);
                break;
            }
            case EQUIPPED_FIRST_PERSON: {
                GL11.glScalef((float)1.4f, (float)1.4f, (float)1.4f);
                GL11.glRotatef((float)60.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                GL11.glTranslatef((float)-0.5f, (float)0.0f, (float)0.0f);
                this.renderBlock(0.5f, 1.5f, 0.5f);
                break;
            }
            case INVENTORY: {
                GL11.glScalef((float)1.4f, (float)1.4f, (float)1.4f);
                GL11.glTranslatef((float)0.0f, (float)0.15f, (float)0.0f);
                GL11.glRotatef((float)180.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                this.renderBlock(0.0f, 1.0f, 0.0f);
            }
        }
    }

    public void renderBlock(float x, float y, float z) {
        GL11.glPushMatrix();
        Minecraft.func_71410_x().field_71446_o.func_110577_a(TEXTURE);
        GL11.glTranslatef((float)x, (float)y, (float)z);
        GL11.glScalef((float)-1.0f, (float)-1.0f, (float)1.0f);
        MODEL.func_78088_a(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
        GL11.glPopMatrix();
    }
}

