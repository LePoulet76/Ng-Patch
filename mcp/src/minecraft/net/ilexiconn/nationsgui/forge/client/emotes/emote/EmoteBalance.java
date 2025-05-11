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

public class EmoteBalance
extends EmoteBase {
    public EmoteBalance(EntityPlayer player, ModelBiped model, ModelBiped armorModel, ModelBiped armorLegsModel) {
        super(player, model, armorModel, armorLegsModel);
    }

    @Override
    public Timeline getTimeline(EntityPlayer player, ModelBiped model) {
        Timeline timeline = Timeline.createSequence().beginParallel().push(Tween.to(model, 20, 2000.0f).target(-2.9415927f)).push(Tween.to(model, 14, 2000.0f).target(2.9415927f)).push(Tween.to(model, 26, 2000.0f).target(2.6179938f)).end().pushPause(2000.0f).beginParallel().push(Tween.to(model, 20, 500.0f).target(0.0f)).push(Tween.to(model, 14, 500.0f).target(0.0f)).push(Tween.to(model, 26, 500.0f).target(0.0f)).end();
        return timeline;
    }

    @Override
    public boolean usesBodyPart(int part) {
        return part == 0 || part == 18 || part == 12 || part == 30 || part == 24;
    }

    @Override
    protected void startSound() {
    }

    @Override
    protected boolean hasSound() {
        return false;
    }
}

