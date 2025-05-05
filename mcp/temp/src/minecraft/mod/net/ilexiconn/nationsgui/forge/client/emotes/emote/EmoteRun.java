package net.ilexiconn.nationsgui.forge.client.emotes.emote;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import net.ilexiconn.nationsgui.forge.client.emotes.emote.base.EmoteBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;

public class EmoteRun extends EmoteBase {

   public EmoteRun(EntityPlayer player, ModelBiped model, ModelBiped armorModel, ModelBiped armorLegsModel) {
      super(player, model, armorModel, armorLegsModel);
   }

   public Timeline getTimeline(EntityPlayer player, ModelBiped model) {
      Timeline timeline = Timeline.createSequence().beginParallel().push(Tween.to(model, 18, 100.0F).target(-0.7853982F)).push(Tween.to(model, 12, 100.0F).target(0.7853982F)).push(Tween.to(model, 30, 100.0F).target(0.7853982F)).push(Tween.to(model, 24, 100.0F).target(-0.7853982F)).end().beginParallel().push((Tween)Tween.to(model, 18, 200.0F).target(0.7853982F).repeatYoyo(10, 0.0F)).push((Tween)Tween.to(model, 12, 200.0F).target(-0.7853982F).repeatYoyo(10, 0.0F)).push((Tween)Tween.to(model, 30, 200.0F).target(-0.7853982F).repeatYoyo(10, 0.0F)).push((Tween)Tween.to(model, 24, 200.0F).target(0.7853982F).repeatYoyo(10, 0.0F)).end().beginParallel().push(Tween.to(model, 18, 100.0F).target(0.0F)).push(Tween.to(model, 12, 100.0F).target(0.0F)).push(Tween.to(model, 30, 100.0F).target(0.0F)).push(Tween.to(model, 24, 100.0F).target(0.0F)).end();
      return timeline;
   }

   public boolean usesBodyPart(int part) {
      return part == 18 || part == 12 || part == 30 || part == 24;
   }

   protected void startSound() {}

   protected boolean hasSound() {
      return false;
   }
}
