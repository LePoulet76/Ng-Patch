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

public class EmoteRun
extends EmoteBase {
    public EmoteRun(EntityPlayer player, ModelBiped model, ModelBiped armorModel, ModelBiped armorLegsModel) {
        super(player, model, armorModel, armorLegsModel);
    }

    @Override
    public Timeline getTimeline(EntityPlayer player, ModelBiped model) {
        Timeline timeline = Timeline.createSequence().beginParallel().push(Tween.to(model, 18, 100.0f).target(-0.7853982f)).push(Tween.to(model, 12, 100.0f).target(0.7853982f)).push(Tween.to(model, 30, 100.0f).target(0.7853982f)).push(Tween.to(model, 24, 100.0f).target(-0.7853982f)).end().beginParallel().push((Tween)Tween.to(model, 18, 200.0f).target(0.7853982f).repeatYoyo(10, 0.0f)).push((Tween)Tween.to(model, 12, 200.0f).target(-0.7853982f).repeatYoyo(10, 0.0f)).push((Tween)Tween.to(model, 30, 200.0f).target(-0.7853982f).repeatYoyo(10, 0.0f)).push((Tween)Tween.to(model, 24, 200.0f).target(0.7853982f).repeatYoyo(10, 0.0f)).end().beginParallel().push(Tween.to(model, 18, 100.0f).target(0.0f)).push(Tween.to(model, 12, 100.0f).target(0.0f)).push(Tween.to(model, 30, 100.0f).target(0.0f)).push(Tween.to(model, 24, 100.0f).target(0.0f)).end();
        return timeline;
    }

    @Override
    public boolean usesBodyPart(int part) {
        return part == 18 || part == 12 || part == 30 || part == 24;
    }

    @Override
    protected void startSound() {
    }

    @Override
    protected boolean hasSound() {
        return false;
    }
}

