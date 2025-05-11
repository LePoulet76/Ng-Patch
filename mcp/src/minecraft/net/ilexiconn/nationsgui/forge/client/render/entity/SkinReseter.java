/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.model.ModelBase
 *  net.minecraft.client.model.ModelRenderer
 *  net.minecraft.util.ResourceLocation
 */
package net.ilexiconn.nationsgui.forge.client.render.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;

public class SkinReseter
extends ModelRenderer {
    private ResourceLocation resource;

    public SkinReseter(ModelBase model, ResourceLocation texture) {
        super(model, "SkinReseter");
        this.resource = texture;
    }

    public void func_78785_a(float par1) {
        Minecraft.func_71410_x().func_110434_K().func_110577_a(this.resource);
    }
}

