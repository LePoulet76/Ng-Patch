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

public class EmoteDab
extends EmoteBase {
    public EmoteDab(EntityPlayer player, ModelBiped model, ModelBiped armorModel, ModelBiped armorLegsModel) {
        super(player, model, armorModel, armorLegsModel);
    }

    @Override
    public Timeline getTimeline(EntityPlayer player, ModelBiped model) {
        Timeline timeline = Timeline.createSequence().beginParallel().push(Tween.to(model, 18, 420.0f).target(-1.9f)).push(Tween.to(model, 19, 420.0f).target(1.0f)).push(Tween.to(model, 12, 420.0f).target(0.1f)).push(Tween.to(model, 14, 420.0f).target(2.0f)).push(Tween.to(model, 2, 420.0f).target(0.0f)).push(Tween.to(model, 1, 420.0f).target(0.3f)).push(Tween.to(model, 0, 420.0f).target(0.2f)).pushPause(2500.0f).end();
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

