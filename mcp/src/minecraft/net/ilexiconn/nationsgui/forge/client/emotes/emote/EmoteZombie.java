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

public class EmoteZombie
extends EmoteBase {
    public EmoteZombie(EntityPlayer player, ModelBiped model, ModelBiped armorModel, ModelBiped armorLegsModel) {
        super(player, model, armorModel, armorLegsModel);
    }

    @Override
    public Timeline getTimeline(EntityPlayer player, ModelBiped model) {
        Timeline timeline = Timeline.createParallel().beginSequence().push(Tween.set(model, 0).target(0.0f)).push(Tween.set(model, 1).target(0.0f)).push(Tween.to(model, 2, 1000.0f).target(0.5235988f)).push((Tween)Tween.to(model, 2, 2000.0f).target(-0.5235988f).repeatYoyo(3, 0.0f)).push(Tween.to(model, 2, 500.0f).target(0.0f)).end().beginSequence().beginParallel().push(Tween.to(model, 18, 1000.0f).target(-1.2707963f)).push(Tween.to(model, 12, 1000.0f).target(-1.8707964f)).end().beginParallel().push((Tween)Tween.to(model, 18, 1000.0f).target(-1.8707964f).repeatYoyo(7, 0.0f)).push((Tween)Tween.to(model, 12, 1000.0f).target(-1.2707963f).repeatYoyo(7, 0.0f)).end().beginParallel().push(Tween.to(model, 18, 500.0f).target(0.0f)).push(Tween.to(model, 12, 500.0f).target(0.0f)).end().end();
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

