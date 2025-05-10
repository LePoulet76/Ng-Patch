package net.ilexiconn.nationsgui.forge.client.emotes.emote;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import net.ilexiconn.nationsgui.forge.client.emotes.emote.base.EmoteBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;

public class EmoteShaftHeadTilt extends EmoteBase
{
    public EmoteShaftHeadTilt(EntityPlayer player, ModelBiped model, ModelBiped armorModel, ModelBiped armorLegsModel)
    {
        super(player, model, armorModel, armorLegsModel);
    }

    public Timeline getTimeline(EntityPlayer player, ModelBiped model)
    {
        Timeline timeline = (Timeline)Timeline.createParallel().push(Tween.to(model, 0, 2000.0F).target(-0.2617994F)).push(Tween.to(model, 1, 2000.0F).target(2.4870942F)).repeatYoyo(1, 1000.0F);
        return timeline;
    }

    public boolean usesBodyPart(int part)
    {
        return part == 0;
    }

    protected void startSound() {}

    protected boolean hasSound()
    {
        return false;
    }
}
