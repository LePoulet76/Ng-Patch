package net.ilexiconn.nationsgui.forge.client.emotes.emote;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import net.ilexiconn.nationsgui.forge.client.emotes.emote.base.EmoteBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;

public class EmoteExorcist extends EmoteBase {

   public EmoteExorcist(EntityPlayer player, ModelBiped model, ModelBiped armorModel, ModelBiped armorLegsModel) {
      super(player, model, armorModel, armorLegsModel);
   }

   public Timeline getTimeline(EntityPlayer player, ModelBiped model) {
      Timeline timeline = Timeline.createParallel().push(Tween.to(model, 1, 7500.0F).target(6.2831855F)).push((Tween)Tween.to(model, 0, 750.0F).target(-0.05F).repeatYoyo(9, 0.0F));
      return timeline;
   }

   public boolean usesBodyPart(int part) {
      return part == 0;
   }

   protected void startSound() {}

   protected boolean hasSound() {
      return false;
   }
}
