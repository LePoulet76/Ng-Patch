/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.model.ModelBiped
 *  net.minecraft.entity.player.EntityPlayer
 */
package net.ilexiconn.nationsgui.forge.client.emotes.emote.jojo;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import net.ilexiconn.nationsgui.forge.client.emotes.emote.base.EmoteBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;

public class EmoteAwakenPose
extends EmoteBase {
    public EmoteAwakenPose(EntityPlayer player, ModelBiped model, ModelBiped armorModel, ModelBiped armorLegsModel) {
        super(player, model, armorModel, armorLegsModel);
    }

    @Override
    public Timeline getTimeline(EntityPlayer player, ModelBiped model) {
        Timeline timeline = Timeline.createSequence().beginParallel().push(Tween.to(model, 0, 200.0f).target(0.0f)).push(Tween.to(model, 1, 200.0f).target(0.0f)).push(Tween.to(model, 2, 200.0f).target(0.0f)).push(Tween.to(model, 3, 200.0f).target(0.0f)).push(Tween.to(model, 4, 200.0f).target(0.0f)).push(Tween.to(model, 5, 200.0f).target(0.0f)).push(Tween.to(model, 12, 200.0f).target(-3.04f)).push(Tween.to(model, 18, 200.0f).target(-3.04f)).push(Tween.to(model, 14, 200.0f).target(-0.9f)).push(Tween.to(model, 20, 200.0f).target(0.9f)).end().pushPause(600.0f).beginParallel().push(Tween.to(model, 0, 100.0f).target(-0.5f)).push(Tween.to(model, 12, 200.0f).target(-3.04f)).push(Tween.to(model, 18, 200.0f).target(-3.04f)).push(Tween.to(model, 14, 200.0f).target(-0.3f)).push(Tween.to(model, 20, 200.0f).target(0.3f)).end().pushPause(600.0f).beginParallel().push(Tween.to(model, 0, 200.0f).target(0.5f)).push(Tween.to(model, 4, 200.0f).target(0.3f)).push(Tween.to(model, 12, 200.0f).target(-0.5f)).push(Tween.to(model, 18, 200.0f).target(-0.5f)).push(Tween.to(model, 14, 200.0f).target(0.3f)).push(Tween.to(model, 20, 200.0f).target(-0.3f)).push(Tween.to(model, 16, 200.0f).target(0.3f)).push(Tween.to(model, 22, 200.0f).target(0.3f)).push(Tween.to(model, 10, 200.0f).target(0.3f)).push(Tween.to(model, 6, 200.0f).target(0.3f)).push(Tween.to(model, 30, 200.0f).target(-0.5f)).push(Tween.to(model, 35, 200.0f).target(0.3f)).push(Tween.to(model, 24, 200.0f).target(0.2f)).push(Tween.to(model, 29, 200.0f).target(0.3f)).end().pushPause(4000.0f).beginParallel().push(Tween.to(model, 0, 100.0f).target(0.0f)).push(Tween.to(model, 1, 100.0f).target(0.0f)).push(Tween.to(model, 1, 100.0f).target(0.0f)).push(Tween.to(model, 18, 100.0f).target(0.0f)).push(Tween.to(model, 19, 100.0f).target(0.0f)).push(Tween.to(model, 20, 100.0f).target(0.0f)).push(Tween.to(model, 12, 100.0f).target(0.0f)).push(Tween.to(model, 13, 100.0f).target(0.0f)).push(Tween.to(model, 14, 100.0f).target(0.0f)).push(Tween.to(model, 6, 100.0f).target(0.0f)).push(Tween.to(model, 7, 100.0f).target(0.0f)).push(Tween.to(model, 8, 100.0f).target(0.0f)).push(Tween.to(model, 30, 100.0f).target(0.0f)).push(Tween.to(model, 31, 100.0f).target(0.0f)).push(Tween.to(model, 32, 100.0f).target(0.0f)).push(Tween.to(model, 24, 100.0f).target(0.0f)).push(Tween.to(model, 25, 100.0f).target(0.0f)).push(Tween.to(model, 26, 100.0f).target(0.0f)).push(Tween.to(model, 3, 100.0f).target(0.0f)).push(Tween.to(model, 4, 100.0f).target(0.0f)).push(Tween.to(model, 5, 100.0f).target(0.0f)).push(Tween.to(model, 21, 100.0f).target(0.0f)).push(Tween.to(model, 22, 100.0f).target(0.0f)).push(Tween.to(model, 23, 100.0f).target(0.0f)).push(Tween.to(model, 15, 100.0f).target(0.0f)).push(Tween.to(model, 16, 100.0f).target(0.0f)).push(Tween.to(model, 17, 100.0f).target(0.0f)).push(Tween.to(model, 9, 100.0f).target(0.0f)).push(Tween.to(model, 10, 100.0f).target(0.0f)).push(Tween.to(model, 11, 100.0f).target(0.0f)).push(Tween.to(model, 33, 100.0f).target(0.0f)).push(Tween.to(model, 34, 100.0f).target(0.0f)).push(Tween.to(model, 35, 100.0f).target(0.0f)).push(Tween.to(model, 27, 100.0f).target(0.0f)).push(Tween.to(model, 28, 100.0f).target(0.0f)).push(Tween.to(model, 29, 100.0f).target(0.0f)).end();
        return timeline;
    }

    @Override
    public boolean usesBodyPart(int part) {
        return part == 0 || part == 12 || part == 18 || part == 6 || part == 30 || part == 24;
    }

    @Override
    protected void startSound() {
    }

    @Override
    protected boolean hasSound() {
        return false;
    }
}

