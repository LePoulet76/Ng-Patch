package net.ilexiconn.nationsgui.forge.client.emotes.emote;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import net.ilexiconn.nationsgui.forge.client.emotes.emote.base.EmoteBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;

public class EmoteDab extends EmoteBase
{
    public EmoteDab(EntityPlayer player, ModelBiped model, ModelBiped armorModel, ModelBiped armorLegsModel)
    {
        super(player, model, armorModel, armorLegsModel);
    }

    public Timeline getTimeline(EntityPlayer player, ModelBiped model)
    {
        Timeline timeline = Timeline.createSequence().beginParallel().push(Tween.to(model, 18, 420.0F).target(-1.9F)).push(Tween.to(model, 19, 420.0F).target(1.0F)).push(Tween.to(model, 12, 420.0F).target(0.1F)).push(Tween.to(model, 14, 420.0F).target(2.0F)).push(Tween.to(model, 2, 420.0F).target(0.0F)).push(Tween.to(model, 1, 420.0F).target(0.3F)).push(Tween.to(model, 0, 420.0F).target(0.2F)).pushPause(2500.0F).end();
        return timeline;
    }

    public boolean usesBodyPart(int part)
    {
        return part == 18 || part == 12 || part == 0;
    }

    protected void startSound() {}

    protected boolean hasSound()
    {
        return false;
    }
}
