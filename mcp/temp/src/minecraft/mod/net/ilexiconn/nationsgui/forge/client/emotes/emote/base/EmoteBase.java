package net.ilexiconn.nationsgui.forge.client.emotes.emote.base;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.TweenManager;
import net.ilexiconn.nationsgui.forge.client.emotes.ClientEmotesHandler;
import net.ilexiconn.nationsgui.forge.client.emotes.emote.base.EmoteBase$1;
import net.ilexiconn.nationsgui.forge.client.emotes.emote.base.EmoteBase$FinishCallback;
import net.ilexiconn.nationsgui.forge.client.emotes.emote.base.EmoteState;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;

public abstract class EmoteBase {

   public static final float pi = 3.1415927F;
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
      if(callback) {
         timeline.setCallback(new EmoteBase$FinishCallback(this, (EmoteBase$1)null));
      }

      if(this.hasSound()) {
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
      if(doUpdate) {
         this.emoteManager.update(ClientEmotesHandler.delta);
         this.state.save(this.model);
      }

   }

   public boolean isDone() {
      return this.done;
   }

   // $FF: synthetic method
   static boolean access$102(EmoteBase x0, boolean x1) {
      return x0.done = x1;
   }
}
