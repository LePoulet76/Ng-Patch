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

public class EmoteHeadbang
extends EmoteBase {
    public EmoteHeadbang(EntityPlayer player, ModelBiped model, ModelBiped armorModel, ModelBiped armorLegsModel) {
        super(player, model, armorModel, armorLegsModel);
    }

    @Override
    public Timeline getTimeline(EntityPlayer player, ModelBiped model) {
        Timeline timeline = Timeline.createSequence().beginParallel().push(Tween.to(model, 12, 400.0f).target((float)(-Math.PI))).push(Tween.to(model, 0, 400.0f).target(0.0f)).push(Tween.to(model, 1, 400.0f).target(0.0f)).push(Tween.to(model, 2, 400.0f).target(0.0f)).end().beginParallel().push((Tween)Tween.to(model, 12, 300.0f).target(-1.1415927f).repeatYoyo(11, 0.0f)).push((Tween)Tween.to(model, 0, 300.0f).target(1.1415927f).repeatYoyo(11, 0.0f)).end().push(Tween.to(model, 12, 600.0f).target(0.0f));
        return timeline;
    }

    @Override
    public boolean usesBodyPart(int part) {
        return part == 12 || part == 0;
    }

    @Override
    protected void startSound() {
    }

    @Override
    protected boolean hasSound() {
        return false;
    }
}

