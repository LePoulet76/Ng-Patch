package net.ilexiconn.nationsgui.forge.client.itemskin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.ilexiconn.nationsgui.forge.client.util.Transform;
import org.json.simple.JSONObject;
import org.lwjgl.opengl.GL11;

public abstract class AbstractSkin
{
    protected static AbstractSkin forcedRenderSkin = null;
    private final Map<String, Transform> transforms = new HashMap();
    private final String id;

    protected AbstractSkin(JSONObject object)
    {
        this.id = (String)object.get("id");
        this.loadTransforms(object);
    }

    public void loadTransforms(JSONObject object)
    {
        JSONObject transformList = (JSONObject)object.get("transforms");

        if (transformList != null)
        {
            Iterator var3 = transformList.entrySet().iterator();

            while (var3.hasNext())
            {
                Object o = var3.next();
                Entry entry = (Entry)o;
                this.transforms.put(entry.getKey(), new Transform((JSONObject)entry.getValue()));
            }
        }
    }

    public String getId()
    {
        return this.id;
    }

    public void renderInGUI(int x, int y, float scale, float partialTick)
    {
        this.renderInGUI(x, y, scale, partialTick, (Transform)null);
    }

    public void renderInGUI(int x, int y, float scale, float partialTick, Transform transform)
    {
        forcedRenderSkin = this;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x, (float)y, 80.0F);
        GL11.glScalef(-scale * 12.0F, scale * 12.0F, scale * 12.0F);
        GL11.glColor3f(1.0F, 1.0F, 1.0F);

        if (transform == null || transform.isNull())
        {
            transform = this.getTransform("gui");
        }

        transform.applyGL();
        this.render(0.0F);
        GL11.glPopMatrix();
        forcedRenderSkin = null;
    }

    protected abstract void render(float var1);

    public Transform getTransform(String key)
    {
        Transform transform = (Transform)this.transforms.get(key);

        if (transform == null)
        {
            transform = new Transform();
            this.transforms.put(key, transform);
        }

        return transform;
    }

    public void reload() {}
}
