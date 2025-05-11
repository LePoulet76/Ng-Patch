/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.model.ModelBiped
 *  net.minecraft.client.model.ModelRenderer
 *  net.minecraft.entity.Entity
 */
package net.ilexiconn.nationsgui.forge.client.itemskin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.itemskin.AbstractSkin;
import net.ilexiconn.nationsgui.forge.client.util.BodyPart;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.json.simple.JSONObject;

public class HandSkin
extends AbstractSkin {
    private final HashMap<String, HashMap<String, BodyPart>> items = new HashMap();
    private final ModelBiped modelBiped = new ModelBiped();

    public HandSkin(JSONObject object) {
        super(object);
        if (object.containsKey("sides")) {
            HashMap bodyPartHashMap = new HashMap();
            Iterator iterator = ((JSONObject)object.get("sides")).entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry o;
                Map.Entry entry = o = iterator.next();
                bodyPartHashMap.put(entry.getKey(), new BodyPart((JSONObject)entry.getValue(), "handskins"));
            }
            this.items.put((String)object.get("id"), bodyPartHashMap);
        }
        this.loadTransforms(object);
    }

    @Override
    public void loadTransforms(JSONObject object) {
        super.loadTransforms(object);
        if (this.items != null && !this.items.isEmpty() && object.containsKey("sides")) {
            Iterator iterator = ((JSONObject)object.get("sides")).entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry o;
                Map.Entry entry = o = iterator.next();
                HashMap<String, BodyPart> bodyPartHashMap = this.items.get(object.get("id"));
                if (bodyPartHashMap == null) continue;
                BodyPart bodyPart = bodyPartHashMap.get(entry.getKey());
                bodyPart.loadTransforms((JSONObject)entry.getValue());
            }
        }
    }

    @Override
    protected void render(float partialTick) {
        this.clearCubes(this.modelBiped.field_78115_e);
        this.clearCubes(this.modelBiped.field_78116_c);
        this.clearCubes(this.modelBiped.field_78114_d);
        this.clearCubes(this.modelBiped.field_78123_h);
        this.clearCubes(this.modelBiped.field_78124_i);
        this.clearCubes(this.modelBiped.field_78113_g);
        this.clearCubes(this.modelBiped.field_78112_f);
        for (HashMap<String, BodyPart> value : this.items.values()) {
            this.applyToBody(value, this.modelBiped);
        }
        this.modelBiped.func_78088_a((Entity)Minecraft.func_71410_x().field_71439_g, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
    }

    private void clearCubes(ModelRenderer model) {
        model.field_78804_l.clear();
    }

    public void applyToBody(String name, ModelBiped modelPlayer) {
        if (name == null || !this.items.containsKey(name)) {
            return;
        }
        this.applyToBody(this.items.get(name), modelPlayer);
    }

    public void applyToBody(HashMap<String, BodyPart> bodyPartHashMap, ModelBiped modelPlayer) {
        if (bodyPartHashMap == null) {
            return;
        }
        for (Map.Entry<String, BodyPart> stringBodyPartEntry : bodyPartHashMap.entrySet()) {
            BodyPart bodyPart = stringBodyPartEntry.getValue();
            if (!bodyPart.getModel().isReady()) continue;
            bodyPart.getModel().updateModel(bodyPart.getTransform());
            ModelRenderer model = this.getBodyPartFromName(stringBodyPartEntry.getKey(), modelPlayer);
            if (model == null) continue;
            model.func_78792_a(bodyPart.getModel().getModel());
        }
    }

    private ModelRenderer getBodyPartFromName(String name, ModelBiped modelBiped) {
        switch (name) {
            case "leftArm": {
                return modelBiped.field_78113_g;
            }
            case "rightArm": {
                return modelBiped.field_78112_f;
            }
        }
        return null;
    }

    @Override
    public void reload() {
        for (HashMap<String, BodyPart> value : this.items.values()) {
            for (BodyPart bodyPart : value.values()) {
                bodyPart.reload();
            }
        }
    }
}

