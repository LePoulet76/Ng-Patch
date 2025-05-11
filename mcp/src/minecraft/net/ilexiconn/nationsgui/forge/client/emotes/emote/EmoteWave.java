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

public class EmoteWave
extends EmoteBase {
    public EmoteWave(EntityPlayer player, ModelBiped model, ModelBiped armorModel, ModelBiped armorLegsModel) {
        super(player, model, armorModel, armorLegsModel);
    }

    @Override
    public Timeline getTimeline(EntityPlayer player, ModelBiped model) {
        Timeline timeline = Timeline.createSequence().push(Tween.to(model, 12, 200.0f).target(-2.8274333f)).push((Tween)Tween.to(model, 14, 300.0f).target(-0.9424779f).repeatYoyo(5, 0.0f)).push(Tween.to(model, 12, 200.0f).target(0.0f));
        return timeline;
    }

    @Override
    public boolean usesBodyPart(int part) {
        return part == 12;
    }

    @Override
    protected void startSound() {
    }

    @Override
    protected boolean hasSound() {
        return false;
    }
}

