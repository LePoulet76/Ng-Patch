package net.ilexiconn.nationsgui.forge.client.emotes.emote;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import net.ilexiconn.nationsgui.forge.client.emotes.emote.base.EmoteBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;

public class EmoteGangnamStyle extends EmoteBase
{
    public EmoteGangnamStyle(EntityPlayer player, ModelBiped model, ModelBiped armorModel, ModelBiped armorLegsModel)
    {
        super(player, model, armorModel, armorLegsModel);
    }

    public Timeline getTimeline(EntityPlayer player, ModelBiped model)
    {
        Timeline timeline = Timeline.createSequence().beginParallel().push(Tween.to(model, 12, 400.0F).target(-1.3707963F)).push(Tween.to(model, 18, 400.0F).target(-1.7707964F)).push(Tween.to(model, 13, 400.0F).target(-0.63539815F)).push(Tween.to(model, 19, 400.0F).target(0.63539815F)).push(Tween.to(model, 26, 200.0F).target(0.5235988F)).push(Tween.to(model, 32, 200.0F).target(-0.5235988F)).end().beginParallel().push((Tween)Tween.to(model, 12, 200.0F).target(-0.77079636F).repeatYoyo(16, 0.0F)).push((Tween)Tween.to(model, 18, 200.0F).target(-1.1707964F).repeatYoyo(16, 0.0F)).push((Tween)Tween.to(model, 26, 200.0F).target(0.92359877F).repeatYoyo(8, 200.0F)).push((Tween)((Tween)Tween.to(model, 32, 200.0F).delay(200.0F)).target(-0.92359877F).repeatYoyo(8, 200.0F)).end().beginParallel().push(Tween.to(model, 12, 400.0F).target(0.0F)).push(Tween.to(model, 18, 400.0F).target(0.0F)).push(Tween.to(model, 13, 400.0F).target(0.0F)).push(Tween.to(model, 19, 400.0F).target(0.0F)).push(Tween.to(model, 26, 200.0F).target(0.0F)).push(Tween.to(model, 32, 200.0F).target(0.0F)).end();
        return timeline;
    }

    public boolean usesBodyPart(int part)
    {
        return part == 18 || part == 12 || part == 30 || part == 24;
    }

    protected void startSound() {}

    protected boolean hasSound()
    {
        return false;
    }
}
