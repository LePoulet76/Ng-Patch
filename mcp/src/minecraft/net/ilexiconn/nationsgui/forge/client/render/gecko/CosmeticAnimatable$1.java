package net.ilexiconn.nationsgui.forge.client.render.gecko;

import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController.IAnimationPredicate;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

class CosmeticAnimatable$1 implements IAnimationPredicate<CosmeticAnimatable>
{
    final CosmeticAnimatable this$0;

    CosmeticAnimatable$1(CosmeticAnimatable this$0)
    {
        this.this$0 = this$0;
    }

    public PlayState test(AnimationEvent<CosmeticAnimatable> event)
    {
        event.getController().setAnimation((new AnimationBuilder()).addAnimation("idle", Boolean.valueOf(true)));
        return PlayState.CONTINUE;
    }
}
