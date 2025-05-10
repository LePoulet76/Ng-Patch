package net.ilexiconn.nationsgui.forge.client.itemskin;

import net.ilexiconn.nationsgui.forge.client.model.entity.CosmeticModel;
import net.ilexiconn.nationsgui.forge.client.model.entity.GeckoCosmeticModel;
import net.ilexiconn.nationsgui.forge.client.model.entity.OBJCometicModel;
import net.ilexiconn.nationsgui.forge.client.util.Transform;
import org.json.simple.JSONObject;

public abstract class BodyPartSkin extends AbstractSkin
{
    private final CosmeticModel model;

    public BodyPartSkin(JSONObject object)
    {
        super(object);
        String modelType = object.containsKey("modelType") ? (String)object.get("modelType") : "obj";

        if (modelType.equals("gecko"))
        {
            this.model = new GeckoCosmeticModel(object);
        }
        else
        {
            this.model = new OBJCometicModel(object, this.getDomain(), this.getId());
        }
    }

    public void renderInGUI(int x, int y, float scale, float partialTick, Transform transform)
    {
        if (transform == null)
        {
            transform = new Transform();
        }

        this.model.updateModel(transform);
        super.renderInGUI(x, y, scale, partialTick, transform);
    }

    protected abstract String getDomain();

    protected void render(float partialTick)
    {
        this.model.render(partialTick);
    }

    public CosmeticModel getModel()
    {
        return this.model;
    }

    public void reload()
    {
        this.model.reload();
    }
}
