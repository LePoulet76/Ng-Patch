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

public class EmoteGangnamStyle
extends EmoteBase {
    public EmoteGangnamStyle(EntityPlayer player, ModelBiped model, ModelBiped armorModel, ModelBiped armorLegsModel) {
        super(player, model, armorModel, armorLegsModel);
    }

    @Override
    public Timeline getTimeline(EntityPlayer player, ModelBiped model) {
        Timeline timeline = Timeline.createSequence().beginParallel().push(Tween.to(model, 12, 400.0f).target(-1.3707963f)).push(Tween.to(model, 18, 400.0f).target(-1.7707964f)).push(Tween.to(model, 13, 400.0f).target(-0.63539815f)).push(Tween.to(model, 19, 400.0f).target(0.63539815f)).push(Tween.to(model, 26, 200.0f).target(0.5235988f)).push(Tween.to(model, 32, 200.0f).target(-0.5235988f)).end().beginParallel().push((Tween)Tween.to(model, 12, 200.0f).target(-0.77079636f).repeatYoyo(16, 0.0f)).push((Tween)Tween.to(model, 18, 200.0f).target(-1.1707964f).repeatYoyo(16, 0.0f)).push((Tween)Tween.to(model, 26, 200.0f).target(0.92359877f).repeatYoyo(8, 200.0f)).push((Tween)((Tween)Tween.to(model, 32, 200.0f).delay(200.0f)).target(-0.92359877f).repeatYoyo(8, 200.0f)).end().beginParallel().push(Tween.to(model, 12, 400.0f).target(0.0f)).push(Tween.to(model, 18, 400.0f).target(0.0f)).push(Tween.to(model, 13, 400.0f).target(0.0f)).push(Tween.to(model, 19, 400.0f).target(0.0f)).push(Tween.to(model, 26, 200.0f).target(0.0f)).push(Tween.to(model, 32, 200.0f).target(0.0f)).end();
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

