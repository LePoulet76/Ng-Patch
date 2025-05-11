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

public class EmoteThink
extends EmoteBase {
    public EmoteThink(EntityPlayer player, ModelBiped model, ModelBiped armorModel, ModelBiped armorLegsModel) {
        super(player, model, armorModel, armorLegsModel);
    }

    @Override
    public Timeline getTimeline(EntityPlayer player, ModelBiped model) {
        Timeline timeline = Timeline.createSequence().beginParallel().push(Tween.to(model, 0, 150.0f).target(0.0f)).push(Tween.to(model, 1, 150.0f).target(0.0f)).push(Tween.to(model, 2, 150.0f).target(0.0f)).push(Tween.to(model, 12, 400.0f).target(-2.3415928f)).push(Tween.to(model, 14, 400.0f).target(0.4f)).end().push((Tween)Tween.to(model, 14, 150.0f).target(0.3f).repeatYoyo(5, 0.0f)).beginParallel().push(Tween.to(model, 12, 300.0f).target(0.0f)).push(Tween.to(model, 14, 300.0f).target(0.0f)).end();
        return timeline;
    }

    @Override
    public boolean usesBodyPart(int part) {
        return part == 0 || part == 12;
    }

    @Override
    protected void startSound() {
    }

    @Override
    protected boolean hasSound() {
        return false;
    }
}

