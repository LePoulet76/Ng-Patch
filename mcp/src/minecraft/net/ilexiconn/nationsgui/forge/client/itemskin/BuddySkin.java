/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.itemskin;

import net.ilexiconn.nationsgui.forge.client.itemskin.AbstractSkin;
import net.ilexiconn.nationsgui.forge.client.util.Transform;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.json.simple.JSONObject;
import org.lwjgl.opengl.GL11;

public class BuddySkin
extends AbstractSkin {
    private RenderItem itemRenderer = new RenderItem();

    public BuddySkin(JSONObject object) {
        super(object);
    }

    @Override
    public void renderInGUI(int x, int y, float scale, float partialTick, Transform transform) {
        forcedRenderSkin = this;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x, (float)y, (float)50.0f);
        GL11.glScalef((float)scale, (float)scale, (float)scale);
        this.render(partialTick);
        GL11.glPopMatrix();
        forcedRenderSkin = null;
    }

    @Override
    protected void render(float partialTick) {
        String alias = this.getId().split("_")[1];
        NBTTagCompound compound = new NBTTagCompound();
        compound.func_74778_a("SkullOwner", alias);
        ItemStack stack = new ItemStack(397, 1, 3);
        stack.field_77990_d = compound;
        GL11.glEnable((int)2929);
        RenderHelper.func_74520_c();
        this.itemRenderer.func_82406_b(Minecraft.func_71410_x().field_71466_p, Minecraft.func_71410_x().func_110434_K(), stack, 5, 3);
        this.itemRenderer.func_94148_a(Minecraft.func_71410_x().field_71466_p, Minecraft.func_71410_x().func_110434_K(), stack, 5, 3, "");
        RenderHelper.func_74518_a();
        GL11.glDisable((int)2896);
    }
}

