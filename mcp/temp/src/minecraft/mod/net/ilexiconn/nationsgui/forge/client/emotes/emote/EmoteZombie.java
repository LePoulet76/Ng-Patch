package net.ilexiconn.nationsgui.forge.client.emotes.emote;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import net.ilexiconn.nationsgui.forge.client.emotes.emote.base.EmoteBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;

public class EmoteZombie extends EmoteBase {

   public EmoteZombie(EntityPlayer player, ModelBiped model, ModelBiped armorModel, ModelBiped armorLegsModel) {
      super(player, model, armorModel, armorLegsModel);
   }

   public Timeline getTimeline(EntityPlayer player, ModelBiped model) {
      Timeline timeline = Timeline.createParallel().beginSequence().push(Tween.set(model, 0).target(0.0F)).push(Tween.set(model, 1).target(0.0F)).push(Tween.to(model, 2, 1000.0F).target(0.5235988F)).push((Tween)Tween.to(model, 2, 2000.0F).target(-0.5235988F).repeatYoyo(3, 0.0F)).push(Tween.to(model, 2, 500.0F).target(0.0F)).end().beginSequence().beginParallel().push(Tween.to(model, 18, 1000.0F).target(-1.2707963F)).push(Tween.to(model, 12, 1000.0F).target(-1.8707964F)).end().beginParallel().push((Tween)Tween.to(model, 18, 1000.0F).target(-1.8707964F).repeatYoyo(7, 0.0F)).push((Tween)Tween.to(model, 12, 1000.0F).target(-1.2707963F).repeatYoyo(7, 0.0F)).end().beginParallel().push(Tween.to(model, 18, 500.0F).target(0.0F)).push(Tween.to(model, 12, 500.0F).target(0.0F)).end().end();
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
