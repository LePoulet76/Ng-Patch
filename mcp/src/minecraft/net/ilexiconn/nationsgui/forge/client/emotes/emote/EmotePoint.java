package net.ilexiconn.nationsgui.forge.client.emotes.emote;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import net.ilexiconn.nationsgui.forge.client.emotes.emote.base.EmoteBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;

public class EmotePoint extends EmoteBase
{
    public EmotePoint(EntityPlayer player, ModelBiped model, ModelBiped armorModel, ModelBiped armorLegsModel)
    {
        super(player, model, armorModel, armorLegsModel);
    }

    public Timeline getTimeline(EntityPlayer player, ModelBiped model)
    {
        Timeline timeline = Timeline.createSequence().push((Tween)Tween.to(model, 12, 400.0F).target(-((float)Math.PI / 2F)).repeatYoyo(1, 1000.0F));
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
