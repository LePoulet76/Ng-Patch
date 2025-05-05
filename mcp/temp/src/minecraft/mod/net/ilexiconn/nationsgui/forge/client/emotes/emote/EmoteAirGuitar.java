package net.ilexiconn.nationsgui.forge.client.emotes.emote;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import net.ilexiconn.nationsgui.forge.client.emotes.emote.base.EmoteBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;

public class EmoteAirGuitar extends EmoteBase {

   public EmoteAirGuitar(EntityPlayer player, ModelBiped model, ModelBiped armorModel, ModelBiped armorLegsModel) {
      super(player, model, armorModel, armorLegsModel);
   }

   public Timeline getTimeline(EntityPlayer player, ModelBiped model) {
      Timeline timeline = Timeline.createSequence().beginParallel().push(Tween.to(model, 0, 400.0F).target(0.4F)).push(Tween.to(model, 1, 400.0F).target(0.0F)).push(Tween.to(model, 2, 400.0F).target(0.0F)).push(Tween.to(model, 18, 400.0F).target(-2.8274333F)).push(Tween.to(model, 19, 400.0F).target(-1.5707964F)).push(Tween.to(model, 12, 400.0F).target(-0.77079636F)).push(Tween.to(model, 13, 400.0F).target(-1.0707964F)).end().beginParallel().push((Tween)Tween.to(model, 0, 100.0F).target(0.2F).repeatYoyo(18, 0.0F)).push((Tween)Tween.to(model, 12, 100.0F).target(-1.3707963F).repeatYoyo(18, 0.0F)).end().beginParallel().push(Tween.to(model, 0, 400.0F).target(0.0F)).push(Tween.to(model, 18, 400.0F).target(0.0F)).push(Tween.to(model, 19, 400.0F).target(0.0F)).push(Tween.to(model, 12, 400.0F).target(0.0F)).push(Tween.to(model, 13, 400.0F).target(0.0F)).end();
      return timeline;
   }

   public boolean usesBodyPart(int part) {
      return part == 0 || part == 18 || part == 12;
   }

   protected void startSound() {}

   protected boolean hasSound() {
      return false;
   }
}
