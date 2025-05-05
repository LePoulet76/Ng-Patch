package net.ilexiconn.nationsgui.forge.client.emotes.emote;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import net.ilexiconn.nationsgui.forge.client.emotes.emote.base.EmoteBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;

public class EmoteBalance extends EmoteBase {

   public EmoteBalance(EntityPlayer player, ModelBiped model, ModelBiped armorModel, ModelBiped armorLegsModel) {
      super(player, model, armorModel, armorLegsModel);
   }

   public Timeline getTimeline(EntityPlayer player, ModelBiped model) {
      Timeline timeline = Timeline.createSequence().beginParallel().push(Tween.to(model, 20, 2000.0F).target(-2.9415927F)).push(Tween.to(model, 14, 2000.0F).target(2.9415927F)).push(Tween.to(model, 26, 2000.0F).target(2.6179938F)).end().pushPause(2000.0F).beginParallel().push(Tween.to(model, 20, 500.0F).target(0.0F)).push(Tween.to(model, 14, 500.0F).target(0.0F)).push(Tween.to(model, 26, 500.0F).target(0.0F)).end();
      return timeline;
   }

   public boolean usesBodyPart(int part) {
      return part == 0 || part == 18 || part == 12 || part == 30 || part == 24;
   }

   protected void startSound() {}

   protected boolean hasSound() {
      return false;
   }
}
