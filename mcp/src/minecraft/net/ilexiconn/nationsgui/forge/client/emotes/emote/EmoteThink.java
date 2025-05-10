package net.ilexiconn.nationsgui.forge.client.emotes.emote;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import net.ilexiconn.nationsgui.forge.client.emotes.emote.base.EmoteBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;

public class EmoteThink extends EmoteBase
{
    public EmoteThink(EntityPlayer player, ModelBiped model, ModelBiped armorModel, ModelBiped armorLegsModel)
    {
        super(player, model, armorModel, armorLegsModel);
    }

    public Timeline getTimeline(EntityPlayer player, ModelBiped model)
    {
        Timeline timeline = Timeline.createSequence().beginParallel().push(Tween.to(model, 0, 150.0F).target(0.0F)).push(Tween.to(model, 1, 150.0F).target(0.0F)).push(Tween.to(model, 2, 150.0F).target(0.0F)).push(Tween.to(model, 12, 400.0F).target(-2.3415928F)).push(Tween.to(model, 14, 400.0F).target(0.4F)).end().push((Tween)Tween.to(model, 14, 150.0F).target(0.3F).repeatYoyo(5, 0.0F)).beginParallel().push(Tween.to(model, 12, 300.0F).target(0.0F)).push(Tween.to(model, 14, 300.0F).target(0.0F)).end();
        return timeline;
    }

    public boolean usesBodyPart(int part)
    {
        return part == 0 || part == 12;
    }

    protected void startSound() {}

    protected boolean hasSound()
    {
        return false;
    }
}
