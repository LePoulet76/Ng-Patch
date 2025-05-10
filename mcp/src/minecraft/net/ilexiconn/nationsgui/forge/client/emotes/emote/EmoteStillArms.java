package net.ilexiconn.nationsgui.forge.client.emotes.emote;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import net.ilexiconn.nationsgui.forge.client.emotes.emote.base.EmoteBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;

public class EmoteStillArms extends EmoteBase
{
    public EmoteStillArms(EntityPlayer player, ModelBiped model, ModelBiped armorModel, ModelBiped armorLegsModel)
    {
        super(player, model, armorModel, armorLegsModel);
    }

    public Timeline getTimeline(EntityPlayer player, ModelBiped model)
    {
        Timeline timeline = Timeline.createSequence().beginParallel().push(Tween.to(model, 12, 1000.0F).target(0.0F)).push(Tween.to(model, 18, 1000.0F).target(0.0F)).pushPause(7000.0F).end();
        return timeline;
    }

    public boolean usesBodyPart(int part)
    {
        return part == 18 || part == 12;
    }

    protected void startSound() {}

    protected boolean hasSound()
    {
        return false;
    }
}
