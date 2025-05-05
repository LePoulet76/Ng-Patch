package net.ilexiconn.nationsgui.forge.client.model.entity;

import net.ilexiconn.nationsgui.forge.client.model.entity.GeckoModelRenderer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.renderers.geo.GeoSimpleRenderer;

public class GeckoModel extends ModelBase {

   private final ModelRenderer model;


   public GeckoModel(GeoSimpleRenderer<IAnimatable> renderer) {
      this.model = new GeckoModelRenderer(this, renderer);
   }

   public ModelRenderer getModel() {
      return this.model;
   }
}
