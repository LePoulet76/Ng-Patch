/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.model.ModelBiped
 *  net.minecraft.entity.player.EntityPlayer
 */
package net.ilexiconn.nationsgui.forge.client.emotes.emote.base;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import net.ilexiconn.nationsgui.forge.client.emotes.ClientEmotesHandler;
import net.ilexiconn.nationsgui.forge.client.emotes.emote.base.EmoteState;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;

public abstract class EmoteBase {
    public static final float pi = (float)Math.PI;
    public TweenManager emoteManager = new TweenManager();
    private ModelBiped model;
    private ModelBiped armorModel;
    private ModelBiped armorLegsModel;
    private EmoteState state = new EmoteState(this);
    private boolean done = false;

    public EmoteBase(EntityPlayer player, ModelBiped model, ModelBiped armorModel, ModelBiped armorLegsModel) {
        this.model = model;
        this.armorModel = armorModel;
        this.armorLegsModel = armorLegsModel;
        this.startTimeline(player, model, true);
        this.startTimeline(player, armorModel, false);
        this.startTimeline(player, armorLegsModel, false);
    }

    void startTimeline(EntityPlayer player, ModelBiped model, boolean callback) {
        Timeline timeline = (Timeline)this.getTimeline(player, model).start(this.emoteManager);
        if (callback) {
            timeline.setCallback(new FinishCallback());
        }
        if (this.hasSound()) {
            this.startSound();
        }
    }

    protected abstract void startSound();

    protected abstract boolean hasSound();

    public abstract Timeline getTimeline(EntityPlayer var1, ModelBiped var2);

    public abstract boolean usesBodyPart(int var1);

    public void update(boolean doUpdate) {
        this.state.load(this.model);
        this.state.load(this.armorModel);
        this.state.load(this.armorLegsModel);
        if (doUpdate) {
            this.emoteManager.update(ClientEmotesHandler.delta);
            this.state.save(this.model);
        }
    }

    public boolean isDone() {
        return this.done;
    }

    private class FinishCallback
    implements TweenCallback {
        private FinishCallback() {
        }

        @Override
        public void onEvent(int type, BaseTween<?> source) {
            if (type == 8) {
                EmoteBase.this.done = true;
            }
        }
    }
}

