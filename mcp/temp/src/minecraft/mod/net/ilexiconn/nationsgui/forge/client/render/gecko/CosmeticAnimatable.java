package net.ilexiconn.nationsgui.forge.client.render.gecko;

import java.io.InputStream;
import net.ilexiconn.nationsgui.forge.client.model.entity.CosmeticAnimatableGeoModel;
import net.ilexiconn.nationsgui.forge.client.render.gecko.CosmeticAnimatable$1;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimatableModel;
import software.bernie.geckolib3.core.SimpleAnimatable;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class CosmeticAnimatable extends SimpleAnimatable {

   private final AnimationFactory factory = new AnimationFactory(this);
   private final CosmeticAnimatableGeoModel model;


   public CosmeticAnimatable(CosmeticAnimatableGeoModel model) {
      this.model = model;
   }

   public void registerControllers(AnimationData animationData) {
      InputStream stream = this.getClass().getClassLoader().getResourceAsStream("assets/nationsgui/animations/cosmetics/" + this.model.getModelName() + ".animation.json");
      if(stream != null) {
         AnimationController controller = new AnimationController(this, "controller", 0.0F, new CosmeticAnimatable$1(this));
         animationData.addAnimationController(controller);
      }

   }

   public AnimationFactory getFactory() {
      return this.factory;
   }

   public IAnimatableModel<IAnimatable> getModelProvider() {
      return this.model;
   }
}
