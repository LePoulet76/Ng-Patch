package net.ilexiconn.nationsgui.forge.client.model.entity;

import net.ilexiconn.nationsgui.forge.client.render.gecko.CosmeticAnimatable;
import net.ilexiconn.nationsgui.forge.client.util.Transform;
import net.minecraft.client.model.ModelRenderer;
import org.json.simple.JSONObject;
import software.bernie.geckolib3.renderers.geo.GeoSimpleRenderer;

public class GeckoCosmeticModel extends CosmeticModel
{
    private final GeckoModel model;

    public GeckoCosmeticModel(JSONObject object)
    {
        super(object);
        CosmeticAnimatableGeoModel cosmeticAnimatableGeoModel = new CosmeticAnimatableGeoModel(object.get("modelName").toString(), object.get("textureName").toString());
        this.model = new GeckoModel(new GeoSimpleRenderer(new CosmeticAnimatable(cosmeticAnimatableGeoModel), cosmeticAnimatableGeoModel));
    }

    public void render(float partialTicks)
    {
        this.model.getModel().render(partialTicks);
    }

    public void updateModel(Transform transform)
    {
        GeckoModelRenderer modelRenderer = (GeckoModelRenderer)this.model.getModel();
        modelRenderer.offsetX = (float)transform.getOffsetX();
        modelRenderer.offsetY = (float)transform.getOffsetY();
        modelRenderer.offsetZ = (float)transform.getOffsetZ();
        modelRenderer.rotateAngleX = (float)transform.getRotateX();
        modelRenderer.rotateAngleY = (float)transform.getRotateY();
        modelRenderer.rotateAngleZ = (float)transform.getRotateZ();
        modelRenderer.setScale((float)transform.getScale());
    }

    public ModelRenderer getModel()
    {
        return this.model.getModel();
    }

    public void reload() {}

    public boolean isReady()
    {
        return true;
    }
}
