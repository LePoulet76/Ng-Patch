package net.ilexiconn.nationsgui.forge.client.emotes.emote;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import net.ilexiconn.nationsgui.forge.client.emotes.emote.base.EmoteBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;

public class EmoteWave extends EmoteBase
{
    public EmoteWave(EntityPlayer player, ModelBiped model, ModelBiped armorModel, ModelBiped armorLegsModel)
    {
        super(player, model, armorModel, armorLegsModel);
    }

    public Timeline getTimeline(EntityPlayer player, ModelBiped model)
    {
        Timeline timeline = Timeline.createSequence().push(Tween.to(model, 12, 200.0F).target(-2.8274333F)).push((Tween)Tween.to(model, 14, 300.0F).target(-0.9424779F).repeatYoyo(5, 0.0F)).push(Tween.to(model, 12, 200.0F).target(0.0F));
        return timeline;
    }

    public boolean usesBodyPart(int part)
    {
        return part == 12;
    }

    protected void startSound() {}

    protected boolean hasSound()
    {
        return false;
    }
}
