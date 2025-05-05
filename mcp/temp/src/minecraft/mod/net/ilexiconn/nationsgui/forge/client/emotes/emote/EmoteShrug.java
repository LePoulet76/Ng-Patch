package net.ilexiconn.nationsgui.forge.client.emotes.emote;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import net.ilexiconn.nationsgui.forge.client.emotes.emote.base.EmoteBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;

public class EmoteShrug extends EmoteBase {

   public EmoteShrug(EntityPlayer player, ModelBiped model, ModelBiped armorModel, ModelBiped armorLegsModel) {
      super(player, model, armorModel, armorLegsModel);
   }

   public Timeline getTimeline(EntityPlayer player, ModelBiped model) {
      Timeline timeline = Timeline.createParallel().push((Tween)Tween.to(model, 14, 400.0F).target(0.7F).repeatYoyo(1, 0.0F)).push((Tween)Tween.to(model, 20, 400.0F).target(-0.7F).repeatYoyo(1, 0.0F)).push((Tween)Tween.to(model, 12, 400.0F).target(-0.7F).repeatYoyo(1, 0.0F)).push((Tween)Tween.to(model, 18, 400.0F).target(-0.7F).repeatYoyo(1, 0.0F));
      return timeline;
   }

   public boolean usesBodyPart(int part) {
      return part == 12 || part == 18;
   }

   protected void startSound() {}

   protected boolean hasSound() {
      return false;
   }
}
