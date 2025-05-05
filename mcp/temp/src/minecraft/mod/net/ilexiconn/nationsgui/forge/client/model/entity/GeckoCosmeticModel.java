package net.ilexiconn.nationsgui.forge.client.model.entity;

import net.ilexiconn.nationsgui.forge.client.model.entity.CosmeticAnimatableGeoModel;
import net.ilexiconn.nationsgui.forge.client.model.entity.CosmeticModel;
import net.ilexiconn.nationsgui.forge.client.model.entity.GeckoModel;
import net.ilexiconn.nationsgui.forge.client.model.entity.GeckoModelRenderer;
import net.ilexiconn.nationsgui.forge.client.render.gecko.CosmeticAnimatable;
import net.ilexiconn.nationsgui.forge.client.util.Transform;
import net.minecraft.client.model.ModelRenderer;
import org.json.simple.JSONObject;
import software.bernie.geckolib3.renderers.geo.GeoSimpleRenderer;

public class GeckoCosmeticModel extends CosmeticModel {

   private final GeckoModel model;


   public GeckoCosmeticModel(JSONObject object) {
      super(object);
      CosmeticAnimatableGeoModel cosmeticAnimatableGeoModel = new CosmeticAnimatableGeoModel(object.get("modelName").toString(), object.get("textureName").toString());
      this.model = new GeckoModel(new GeoSimpleRenderer(new CosmeticAnimatable(cosmeticAnimatableGeoModel), cosmeticAnimatableGeoModel));
   }

   public void render(float partialTicks) {
      this.model.getModel().func_78785_a(partialTicks);
   }

   public void updateModel(Transform transform) {
      GeckoModelRenderer modelRenderer = (GeckoModelRenderer)this.model.getModel();
      modelRenderer.field_82906_o = (float)transform.getOffsetX();
      modelRenderer.field_82908_p = (float)transform.getOffsetY();
      modelRenderer.field_82907_q = (float)transform.getOffsetZ();
      modelRenderer.field_78795_f = (float)transform.getRotateX();
      modelRenderer.field_78796_g = (float)transform.getRotateY();
      modelRenderer.field_78808_h = (float)transform.getRotateZ();
      modelRenderer.setScale((float)transform.getScale());
   }

   public ModelRenderer getModel() {
      return this.model.getModel();
   }

   public void reload() {}

   public boolean isReady() {
      return true;
   }
}
