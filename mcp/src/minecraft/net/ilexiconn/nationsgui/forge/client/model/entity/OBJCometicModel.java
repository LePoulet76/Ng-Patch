/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  fr.nationsglory.modelapi.ModelAPI
 *  fr.nationsglory.modelapi.ModelOBJ
 *  net.minecraft.client.model.ModelRenderer
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.model.entity;

import fr.nationsglory.modelapi.ModelAPI;
import fr.nationsglory.modelapi.ModelOBJ;
import net.ilexiconn.nationsgui.forge.client.model.entity.CosmeticModel;
import net.ilexiconn.nationsgui.forge.client.util.Transform;
import net.minecraft.client.model.ModelRenderer;
import org.json.simple.JSONObject;
import org.lwjgl.opengl.GL11;

public class OBJCometicModel
extends CosmeticModel {
    private final ModelOBJ modelOBJ;

    public OBJCometicModel(JSONObject object, String domain, String id) {
        super(object);
        this.modelOBJ = ModelAPI.registerModel((String)domain, (String)id, (String)((String)object.get("modelName")), (String)((String)object.get("textureName")));
    }

    @Override
    public void render(float partialTicks) {
        GL11.glEnable((int)32826);
        this.modelOBJ.render(partialTicks);
    }

    @Override
    public void updateModel(Transform transform) {
        this.modelOBJ.applyTexture();
        transform.applyToModel(this.modelOBJ.getModel());
    }

    @Override
    public ModelRenderer getModel() {
        return this.modelOBJ.getModel();
    }

    @Override
    public void reload() {
        this.modelOBJ.resetTexture();
        this.modelOBJ.resetModel(true);
    }

    @Override
    public boolean isReady() {
        return this.modelOBJ.getModel() != null && this.modelOBJ.loadTexture();
    }
}

