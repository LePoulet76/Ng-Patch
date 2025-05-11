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

public class EmoteFacepalm
extends EmoteBase {
    public EmoteFacepalm(EntityPlayer player, ModelBiped model, ModelBiped armorModel, ModelBiped armorLegsModel) {
        super(player, model, armorModel, armorLegsModel);
    }

    @Override
    public Timeline getTimeline(EntityPlayer player, ModelBiped model) {
        Timeline timeline = Timeline.createSequence().beginParallel().push(Tween.to(model, 0, 1000.0f).target(0.2f)).push(Tween.to(model, 1, 1000.0f).target(0.0f)).push(Tween.to(model, 2, 1000.0f).target(0.0f)).push(Tween.to(model, 18, 400.0f).target(-2.3415928f)).push(Tween.to(model, 20, 400.0f).target(-1.0f)).end().push(Tween.to(model, 1, 300.0f).target(0.05f)).push((Tween)Tween.to(model, 1, 300.0f).target(-0.05f).repeatYoyo(4, 0.0f)).push(Tween.to(model, 1, 300.0f).target(0.0f)).beginParallel().push(Tween.to(model, 0, 500.0f).target(0.0f)).push(Tween.to(model, 18, 600.0f).target(0.0f)).push(Tween.to(model, 20, 600.0f).target(0.0f)).end();
        return timeline;
    }

    @Override
    public boolean usesBodyPart(int part) {
        return part == 0 || part == 18;
    }

    @Override
    protected void startSound() {
    }

    @Override
    protected boolean hasSound() {
        return false;
    }
}

