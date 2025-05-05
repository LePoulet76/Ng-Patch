package net.ilexiconn.nationsgui.forge.client.emotes.emote;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import net.ilexiconn.nationsgui.forge.client.emotes.emote.base.EmoteBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;

public class EmoteStand extends EmoteBase {

   public EmoteStand(EntityPlayer player, ModelBiped model, ModelBiped armorModel, ModelBiped armorLegsModel) {
      super(player, model, armorModel, armorLegsModel);
   }

   public Timeline getTimeline(EntityPlayer player, ModelBiped model) {
      Timeline timeline = (Timeline)Timeline.createSequence().beginParallel().push(Tween.to(model, 12, 400.0F).target(-0.3F)).push(Tween.to(model, 13, 400.0F).target(0.0F)).push(Tween.to(model, 14, 400.0F).target(-1.1780972F)).push(Tween.to(model, 18, 400.0F).target(0.3F)).push(Tween.to(model, 19, 400.0F).target(0.0F)).push(Tween.to(model, 20, 400.0F).target(0.7853982F)).end().repeatYoyo(1, 2000.0F);
      return timeline;
   }

   public boolean usesBodyPart(int part) {
      return part == 18 || part == 12;
   }

   protected void startSound() {}

   protected boolean hasSound() {
      return false;
   }
}
