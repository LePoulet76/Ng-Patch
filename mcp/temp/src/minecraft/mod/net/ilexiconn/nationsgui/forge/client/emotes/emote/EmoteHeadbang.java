package net.ilexiconn.nationsgui.forge.client.emotes.emote;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import net.ilexiconn.nationsgui.forge.client.emotes.emote.base.EmoteBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;

public class EmoteHeadbang extends EmoteBase {

   public EmoteHeadbang(EntityPlayer player, ModelBiped model, ModelBiped armorModel, ModelBiped armorLegsModel) {
      super(player, model, armorModel, armorLegsModel);
   }

   public Timeline getTimeline(EntityPlayer player, ModelBiped model) {
      Timeline timeline = Timeline.createSequence().beginParallel().push(Tween.to(model, 12, 400.0F).target(-3.1415927F)).push(Tween.to(model, 0, 400.0F).target(0.0F)).push(Tween.to(model, 1, 400.0F).target(0.0F)).push(Tween.to(model, 2, 400.0F).target(0.0F)).end().beginParallel().push((Tween)Tween.to(model, 12, 300.0F).target(-1.1415927F).repeatYoyo(11, 0.0F)).push((Tween)Tween.to(model, 0, 300.0F).target(1.1415927F).repeatYoyo(11, 0.0F)).end().push(Tween.to(model, 12, 600.0F).target(0.0F));
      return timeline;
   }

   public boolean usesBodyPart(int part) {
      return part == 12 || part == 0;
   }

   protected void startSound() {}

   protected boolean hasSound() {
      return false;
   }
}
