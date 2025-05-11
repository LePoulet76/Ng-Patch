/*
 * Decompiled with CFR 0.152.
 */
package net.ilexiconn.nationsgui.forge.client.util;

import net.ilexiconn.nationsgui.forge.client.model.entity.CosmeticModel;
import net.ilexiconn.nationsgui.forge.client.model.entity.GeckoCosmeticModel;
import net.ilexiconn.nationsgui.forge.client.model.entity.OBJCometicModel;
import net.ilexiconn.nationsgui.forge.client.util.Transform;
import org.json.simple.JSONObject;

public class BodyPart {
    private final CosmeticModel model;
    private Transform transform;

    public BodyPart(JSONObject object, String domain) {
        String modelType = object.containsKey("modelType") ? (String)object.get("modelType") : "obj";
        this.model = modelType.equals("gecko") ? new GeckoCosmeticModel(object) : new OBJCometicModel(object, domain, (String)object.get("id"));
        this.loadTransforms(object);
    }

    public void loadTransforms(JSONObject object) {
        JSONObject transformRaw = (JSONObject)object.get("transform");
        this.transform = new Transform(transformRaw);
    }

    public CosmeticModel getModel() {
        return this.model;
    }

    public Transform getTransform() {
        return this.transform;
    }

    public void reload() {
        this.model.reload();
    }
}

