/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  software.bernie.geckolib3.core.IAnimatable
 *  software.bernie.geckolib3.core.IAnimatableModel
 *  software.bernie.geckolib3.core.PlayState
 *  software.bernie.geckolib3.core.SimpleAnimatable
 *  software.bernie.geckolib3.core.builder.AnimationBuilder
 *  software.bernie.geckolib3.core.controller.AnimationController
 *  software.bernie.geckolib3.core.controller.AnimationController$IAnimationPredicate
 *  software.bernie.geckolib3.core.event.predicate.AnimationEvent
 *  software.bernie.geckolib3.core.manager.AnimationData
 *  software.bernie.geckolib3.core.manager.AnimationFactory
 */
package net.ilexiconn.nationsgui.forge.client.render.gecko;

import java.io.InputStream;
import net.ilexiconn.nationsgui.forge.client.model.entity.CosmeticAnimatableGeoModel;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimatableModel;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.SimpleAnimatable;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class CosmeticAnimatable
extends SimpleAnimatable {
    private final AnimationFactory factory = new AnimationFactory((IAnimatable)this);
    private final CosmeticAnimatableGeoModel model;

    public CosmeticAnimatable(CosmeticAnimatableGeoModel model) {
        this.model = model;
    }

    public void registerControllers(AnimationData animationData) {
        InputStream stream = ((Object)((Object)this)).getClass().getClassLoader().getResourceAsStream("assets/nationsgui/animations/cosmetics/" + this.model.getModelName() + ".animation.json");
        if (stream != null) {
            AnimationController controller = new AnimationController((IAnimatable)this, "controller", 0.0f, (AnimationController.IAnimationPredicate)new AnimationController.IAnimationPredicate<CosmeticAnimatable>(){

                public PlayState test(AnimationEvent<CosmeticAnimatable> event) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", Boolean.valueOf(true)));
                    return PlayState.CONTINUE;
                }
            });
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

