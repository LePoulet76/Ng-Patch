/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.model.ModelBiped
 *  net.minecraft.entity.player.EntityPlayer
 */
package net.ilexiconn.nationsgui.forge.client.emotes.emote;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import net.ilexiconn.nationsgui.forge.client.emotes.emote.base.EmoteBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;

public class EmoteClap
extends EmoteBase {
    public EmoteClap(EntityPlayer player, ModelBiped model, ModelBiped armorModel, ModelBiped armorLegsModel) {
        super(player, model, armorModel, armorLegsModel);
    }

    @Override
    public Timeline getTimeline(EntityPlayer player, ModelBiped model) {
        Timeline timeline = Timeline.createSequence().beginParallel().push(Tween.to(model, 12, 400.0f).target(-1.5707964f)).push(Tween.to(model, 18, 400.0f).target(-1.5707964f)).push(Tween.to(model, 13, 400.0f).target(-0.5353982f)).push(Tween.to(model, 19, 400.0f).target(0.5353982f)).end().beginParallel().push((Tween)Tween.to(model, 13, 100.0f).target(-0.38539818f).repeatYoyo(11, 0.0f)).push((Tween)Tween.to(model, 19, 100.0f).target(0.38539818f).repeatYoyo(11, 0.0f)).end().beginParallel().push(Tween.to(model, 12, 400.0f).target(0.0f)).push(Tween.to(model, 18, 400.0f).target(0.0f)).push(Tween.to(model, 13, 400.0f).target(0.0f)).push(Tween.to(model, 19, 400.0f).target(0.0f)).end();
        return timeline;
    }

    @Override
    public boolean usesBodyPart(int part) {
        return part == 12 || part == 18;
    }

    @Override
    protected void startSound() {
    }

    @Override
    protected boolean hasSound() {
        return false;
    }
}

