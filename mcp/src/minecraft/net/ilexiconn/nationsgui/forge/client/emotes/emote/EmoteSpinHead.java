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
import aurelienribon.tweenengine.equations.Linear;
import aurelienribon.tweenengine.equations.Quart;
import net.ilexiconn.nationsgui.forge.client.emotes.emote.base.EmoteBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;

public class EmoteSpinHead
extends EmoteBase {
    public EmoteSpinHead(EntityPlayer player, ModelBiped model, ModelBiped armorModel, ModelBiped armorLegsModel) {
        super(player, model, armorModel, armorLegsModel);
    }

    @Override
    public Timeline getTimeline(EntityPlayer player, ModelBiped model) {
        Timeline timeline = Timeline.createSequence().beginParallel().push(Tween.to(model, 12, 500.0f).target(-2.75f)).push(Tween.to(model, 18, 500.0f).target(-3.75f)).pushPause(500.0f).end().beginParallel().push(Tween.to(model, 12, 500.0f).target(-3.75f)).push(Tween.to(model, 18, 500.0f).target(-2.75f)).push(Tween.to(model, 1, 500.0f).target(0.79f)).pushPause(500.0f).end().beginParallel().push(Tween.to(model, 14, 250.0f).target(-0.5f).ease(Quart.OUT)).push(Tween.to(model, 12, 250.0f).target(-3.75f).ease(Linear.INOUT)).push(Tween.to(model, 20, 250.0f).target(0.5f).ease(Quart.OUT)).push(Tween.to(model, 18, 250.0f).target(-2.75f).ease(Linear.INOUT)).end().beginParallel().push(Tween.to(model, 14, 250.0f).target(0.0f).ease(Quart.IN)).push(Tween.to(model, 12, 250.0f).target(-2.75f).ease(Linear.INOUT)).push(Tween.to(model, 20, 250.0f).target(0.0f).ease(Quart.IN)).push(Tween.to(model, 18, 250.0f).target(-3.75f).ease(Linear.INOUT)).end().beginParallel().push(Tween.to(model, 12, 500.0f).target(-2.75f)).push(Tween.to(model, 18, 500.0f).target(-3.75f)).pushPause(500.0f).end().beginParallel().push(Tween.to(model, 12, 500.0f).target(-3.75f)).push(Tween.to(model, 18, 500.0f).target(-2.75f)).push(Tween.to(model, 1, 500.0f).target(1.57f)).pushPause(500.0f).end().beginParallel().push(Tween.to(model, 14, 250.0f).target(-0.5f).ease(Quart.OUT)).push(Tween.to(model, 12, 250.0f).target(-3.75f).ease(Linear.INOUT)).push(Tween.to(model, 20, 250.0f).target(0.5f).ease(Quart.OUT)).push(Tween.to(model, 18, 250.0f).target(-2.75f).ease(Linear.INOUT)).end().beginParallel().push(Tween.to(model, 14, 250.0f).target(0.0f).ease(Quart.IN)).push(Tween.to(model, 12, 250.0f).target(-2.75f).ease(Linear.INOUT)).push(Tween.to(model, 20, 250.0f).target(0.0f).ease(Quart.IN)).push(Tween.to(model, 18, 250.0f).target(-3.75f).ease(Linear.INOUT)).end().beginParallel().push(Tween.to(model, 12, 500.0f).target(-2.75f)).push(Tween.to(model, 18, 500.0f).target(-3.75f)).pushPause(500.0f).end().beginParallel().push(Tween.to(model, 12, 500.0f).target(-3.75f)).push(Tween.to(model, 18, 500.0f).target(-2.75f)).push(Tween.to(model, 1, 500.0f).target(2.36f)).pushPause(500.0f).end().beginParallel().push(Tween.to(model, 14, 250.0f).target(-0.5f).ease(Quart.OUT)).push(Tween.to(model, 12, 250.0f).target(-3.75f).ease(Linear.INOUT)).push(Tween.to(model, 20, 250.0f).target(0.5f).ease(Quart.OUT)).push(Tween.to(model, 18, 250.0f).target(-2.75f).ease(Linear.INOUT)).end().beginParallel().push(Tween.to(model, 14, 250.0f).target(0.0f).ease(Quart.IN)).push(Tween.to(model, 12, 250.0f).target(-2.75f).ease(Linear.INOUT)).push(Tween.to(model, 20, 250.0f).target(0.0f).ease(Quart.IN)).push(Tween.to(model, 18, 250.0f).target(-3.75f).ease(Linear.INOUT)).end().beginParallel().push(Tween.to(model, 12, 500.0f).target(-2.75f)).push(Tween.to(model, 18, 500.0f).target(-3.75f)).pushPause(500.0f).end().beginParallel().push(Tween.to(model, 12, 500.0f).target(-3.75f)).push(Tween.to(model, 18, 500.0f).target(-2.75f)).push(Tween.to(model, 1, 500.0f).target(3.14f)).pushPause(500.0f).end().beginParallel().push(Tween.to(model, 14, 350.0f).target(-0.5f).ease(Quart.OUT)).push(Tween.to(model, 12, 350.0f).target(-0.75f).ease(Quart.OUT)).push(Tween.to(model, 20, 350.0f).target(0.5f).ease(Quart.OUT)).push(Tween.to(model, 18, 350.0f).target(-5.75f).ease(Quart.OUT)).end().beginParallel().push(Tween.to(model, 14, 50.0f).target(0.0f).ease(Quart.IN)).push(Tween.to(model, 12, 50.0f).target(-5.75f).ease(Linear.INOUT)).push(Tween.to(model, 20, 50.0f).target(0.0f).ease(Quart.IN)).push(Tween.to(model, 18, 50.0f).target(-0.75f).ease(Linear.INOUT)).push(Tween.to(model, 12, 300.0f).target(-6.25f).ease(Linear.INOUT)).push(Tween.to(model, 18, 300.0f).target(0.0f).ease(Linear.INOUT)).end().beginParallel().pushPause(150.0f).push(Tween.to(model, 1, 3000.0f).target(37.68f).ease(Quart.OUT)).pushPause(250.0f).end();
        return timeline;
    }

    @Override
    public boolean usesBodyPart(int part) {
        return part == 18 || part == 12 || part == 0;
    }

    @Override
    protected void startSound() {
    }

    @Override
    protected boolean hasSound() {
        return false;
    }
}

