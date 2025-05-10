package net.ilexiconn.nationsgui.forge.client.emotes.emote;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import net.ilexiconn.nationsgui.forge.client.emotes.emote.base.EmoteBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;

public class EmoteFacepalm extends EmoteBase
{
    public EmoteFacepalm(EntityPlayer player, ModelBiped model, ModelBiped armorModel, ModelBiped armorLegsModel)
    {
        super(player, model, armorModel, armorLegsModel);
    }

    public Timeline getTimeline(EntityPlayer player, ModelBiped model)
    {
        Timeline timeline = Timeline.createSequence().beginParallel().push(Tween.to(model, 0, 1000.0F).target(0.2F)).push(Tween.to(model, 1, 1000.0F).target(0.0F)).push(Tween.to(model, 2, 1000.0F).target(0.0F)).push(Tween.to(model, 18, 400.0F).target(-2.3415928F)).push(Tween.to(model, 20, 400.0F).target(-1.0F)).end().push(Tween.to(model, 1, 300.0F).target(0.05F)).push((Tween)Tween.to(model, 1, 300.0F).target(-0.05F).repeatYoyo(4, 0.0F)).push(Tween.to(model, 1, 300.0F).target(0.0F)).beginParallel().push(Tween.to(model, 0, 500.0F).target(0.0F)).push(Tween.to(model, 18, 600.0F).target(0.0F)).push(Tween.to(model, 20, 600.0F).target(0.0F)).end();
        return timeline;
    }

    public boolean usesBodyPart(int part)
    {
        return part == 0 || part == 18;
    }

    protected void startSound() {}

    protected boolean hasSound()
    {
        return false;
    }
}
