package net.ilexiconn.nationsgui.forge.client.emotes.emote;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import net.ilexiconn.nationsgui.forge.client.emotes.emote.base.EmoteBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;

public class EmoteClap extends EmoteBase
{
    public EmoteClap(EntityPlayer player, ModelBiped model, ModelBiped armorModel, ModelBiped armorLegsModel)
    {
        super(player, model, armorModel, armorLegsModel);
    }

    public Timeline getTimeline(EntityPlayer player, ModelBiped model)
    {
        Timeline timeline = Timeline.createSequence().beginParallel().push(Tween.to(model, 12, 400.0F).target(-((float)Math.PI / 2F))).push(Tween.to(model, 18, 400.0F).target(-((float)Math.PI / 2F))).push(Tween.to(model, 13, 400.0F).target(-0.5353982F)).push(Tween.to(model, 19, 400.0F).target(0.5353982F)).end().beginParallel().push((Tween)Tween.to(model, 13, 100.0F).target(-0.38539818F).repeatYoyo(11, 0.0F)).push((Tween)Tween.to(model, 19, 100.0F).target(0.38539818F).repeatYoyo(11, 0.0F)).end().beginParallel().push(Tween.to(model, 12, 400.0F).target(0.0F)).push(Tween.to(model, 18, 400.0F).target(0.0F)).push(Tween.to(model, 13, 400.0F).target(0.0F)).push(Tween.to(model, 19, 400.0F).target(0.0F)).end();
        return timeline;
    }

    public boolean usesBodyPart(int part)
    {
        return part == 12 || part == 18;
    }

    protected void startSound() {}

    protected boolean hasSound()
    {
        return false;
    }
}
