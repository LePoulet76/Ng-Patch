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

public class EmoteAirGuitar
extends EmoteBase {
    public EmoteAirGuitar(EntityPlayer player, ModelBiped model, ModelBiped armorModel, ModelBiped armorLegsModel) {
        super(player, model, armorModel, armorLegsModel);
    }

    @Override
    public Timeline getTimeline(EntityPlayer player, ModelBiped model) {
        Timeline timeline = Timeline.createSequence().beginParallel().push(Tween.to(model, 0, 400.0f).target(0.4f)).push(Tween.to(model, 1, 400.0f).target(0.0f)).push(Tween.to(model, 2, 400.0f).target(0.0f)).push(Tween.to(model, 18, 400.0f).target(-2.8274333f)).push(Tween.to(model, 19, 400.0f).target(-1.5707964f)).push(Tween.to(model, 12, 400.0f).target(-0.77079636f)).push(Tween.to(model, 13, 400.0f).target(-1.0707964f)).end().beginParallel().push((Tween)Tween.to(model, 0, 100.0f).target(0.2f).repeatYoyo(18, 0.0f)).push((Tween)Tween.to(model, 12, 100.0f).target(-1.3707963f).repeatYoyo(18, 0.0f)).end().beginParallel().push(Tween.to(model, 0, 400.0f).target(0.0f)).push(Tween.to(model, 18, 400.0f).target(0.0f)).push(Tween.to(model, 19, 400.0f).target(0.0f)).push(Tween.to(model, 12, 400.0f).target(0.0f)).push(Tween.to(model, 13, 400.0f).target(0.0f)).end();
        return timeline;
    }

    @Override
    public boolean usesBodyPart(int part) {
        return part == 0 || part == 18 || part == 12;
    }

    @Override
    protected void startSound() {
    }

    @Override
    protected boolean hasSound() {
        return false;
    }
}

