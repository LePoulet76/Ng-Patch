/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.model.ModelRenderer
 */
package net.ilexiconn.nationsgui.forge.client.model.entity;

import net.ilexiconn.nationsgui.forge.client.util.Transform;
import net.minecraft.client.model.ModelRenderer;
import org.json.simple.JSONObject;

public abstract class CosmeticModel {
    public CosmeticModel(JSONObject object) {
    }

    public abstract void render(float var1);

    public abstract void updateModel(Transform var1);

    public abstract ModelRenderer getModel();

    public abstract void reload();

    public abstract boolean isReady();
}

