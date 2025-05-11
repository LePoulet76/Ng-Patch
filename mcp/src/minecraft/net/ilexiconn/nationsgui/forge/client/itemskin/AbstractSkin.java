/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.itemskin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.util.Transform;
import org.json.simple.JSONObject;
import org.lwjgl.opengl.GL11;

public abstract class AbstractSkin {
    protected static AbstractSkin forcedRenderSkin = null;
    private final Map<String, Transform> transforms = new HashMap<String, Transform>();
    private final String id;

    protected AbstractSkin(JSONObject object) {
        this.id = (String)object.get("id");
        this.loadTransforms(object);
    }

    public void loadTransforms(JSONObject object) {
        JSONObject transformList = (JSONObject)object.get("transforms");
        if (transformList == null) {
            return;
        }
        Iterator iterator = transformList.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry o;
            Map.Entry entry = o = iterator.next();
            this.transforms.put((String)entry.getKey(), new Transform((JSONObject)entry.getValue()));
        }
    }

    public String getId() {
        return this.id;
    }

    public void renderInGUI(int x, int y, float scale, float partialTick) {
        this.renderInGUI(x, y, scale, partialTick, null);
    }

    public void renderInGUI(int x, int y, float scale, float partialTick, Transform transform) {
        forcedRenderSkin = this;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x, (float)y, (float)80.0f);
        GL11.glScalef((float)(-scale * 12.0f), (float)(scale * 12.0f), (float)(scale * 12.0f));
        GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
        if (transform == null || transform.isNull()) {
            transform = this.getTransform("gui");
        }
        transform.applyGL();
        this.render(0.0f);
        GL11.glPopMatrix();
        forcedRenderSkin = null;
    }

    protected abstract void render(float var1);

    public Transform getTransform(String key) {
        Transform transform = this.transforms.get(key);
        if (transform == null) {
            transform = new Transform();
            this.transforms.put(key, transform);
        }
        return transform;
    }

    public void reload() {
    }
}

