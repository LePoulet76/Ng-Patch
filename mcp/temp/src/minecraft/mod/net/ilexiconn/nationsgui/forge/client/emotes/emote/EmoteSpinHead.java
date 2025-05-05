package net.ilexiconn.nationsgui.forge.client.emotes.emote;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.equations.Linear;
import aurelienribon.tweenengine.equations.Quart;
import net.ilexiconn.nationsgui.forge.client.emotes.emote.base.EmoteBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;

public class EmoteSpinHead extends EmoteBase {

   public EmoteSpinHead(EntityPlayer player, ModelBiped model, ModelBiped armorModel, ModelBiped armorLegsModel) {
      super(player, model, armorModel, armorLegsModel);
   }

   public Timeline getTimeline(EntityPlayer player, ModelBiped model) {
      Timeline timeline = Timeline.createSequence().beginParallel().push(Tween.to(model, 12, 500.0F).target(-2.75F)).push(Tween.to(model, 18, 500.0F).target(-3.75F)).pushPause(500.0F).end().beginParallel().push(Tween.to(model, 12, 500.0F).target(-3.75F)).push(Tween.to(model, 18, 500.0F).target(-2.75F)).push(Tween.to(model, 1, 500.0F).target(0.79F)).pushPause(500.0F).end().beginParallel().push(Tween.to(model, 14, 250.0F).target(-0.5F).ease(Quart.OUT)).push(Tween.to(model, 12, 250.0F).target(-3.75F).ease(Linear.INOUT)).push(Tween.to(model, 20, 250.0F).target(0.5F).ease(Quart.OUT)).push(Tween.to(model, 18, 250.0F).target(-2.75F).ease(Linear.INOUT)).end().beginParallel().push(Tween.to(model, 14, 250.0F).target(0.0F).ease(Quart.IN)).push(Tween.to(model, 12, 250.0F).target(-2.75F).ease(Linear.INOUT)).push(Tween.to(model, 20, 250.0F).target(0.0F).ease(Quart.IN)).push(Tween.to(model, 18, 250.0F).target(-3.75F).ease(Linear.INOUT)).end().beginParallel().push(Tween.to(model, 12, 500.0F).target(-2.75F)).push(Tween.to(model, 18, 500.0F).target(-3.75F)).pushPause(500.0F).end().beginParallel().push(Tween.to(model, 12, 500.0F).target(-3.75F)).push(Tween.to(model, 18, 500.0F).target(-2.75F)).push(Tween.to(model, 1, 500.0F).target(1.57F)).pushPause(500.0F).end().beginParallel().push(Tween.to(model, 14, 250.0F).target(-0.5F).ease(Quart.OUT)).push(Tween.to(model, 12, 250.0F).target(-3.75F).ease(Linear.INOUT)).push(Tween.to(model, 20, 250.0F).target(0.5F).ease(Quart.OUT)).push(Tween.to(model, 18, 250.0F).target(-2.75F).ease(Linear.INOUT)).end().beginParallel().push(Tween.to(model, 14, 250.0F).target(0.0F).ease(Quart.IN)).push(Tween.to(model, 12, 250.0F).target(-2.75F).ease(Linear.INOUT)).push(Tween.to(model, 20, 250.0F).target(0.0F).ease(Quart.IN)).push(Tween.to(model, 18, 250.0F).target(-3.75F).ease(Linear.INOUT)).end().beginParallel().push(Tween.to(model, 12, 500.0F).target(-2.75F)).push(Tween.to(model, 18, 500.0F).target(-3.75F)).pushPause(500.0F).end().beginParallel().push(Tween.to(model, 12, 500.0F).target(-3.75F)).push(Tween.to(model, 18, 500.0F).target(-2.75F)).push(Tween.to(model, 1, 500.0F).target(2.36F)).pushPause(500.0F).end().beginParallel().push(Tween.to(model, 14, 250.0F).target(-0.5F).ease(Quart.OUT)).push(Tween.to(model, 12, 250.0F).target(-3.75F).ease(Linear.INOUT)).push(Tween.to(model, 20, 250.0F).target(0.5F).ease(Quart.OUT)).push(Tween.to(model, 18, 250.0F).target(-2.75F).ease(Linear.INOUT)).end().beginParallel().push(Tween.to(model, 14, 250.0F).target(0.0F).ease(Quart.IN)).push(Tween.to(model, 12, 250.0F).target(-2.75F).ease(Linear.INOUT)).push(Tween.to(model, 20, 250.0F).target(0.0F).ease(Quart.IN)).push(Tween.to(model, 18, 250.0F).target(-3.75F).ease(Linear.INOUT)).end().beginParallel().push(Tween.to(model, 12, 500.0F).target(-2.75F)).push(Tween.to(model, 18, 500.0F).target(-3.75F)).pushPause(500.0F).end().beginParallel().push(Tween.to(model, 12, 500.0F).target(-3.75F)).push(Tween.to(model, 18, 500.0F).target(-2.75F)).push(Tween.to(model, 1, 500.0F).target(3.14F)).pushPause(500.0F).end().beginParallel().push(Tween.to(model, 14, 350.0F).target(-0.5F).ease(Quart.OUT)).push(Tween.to(model, 12, 350.0F).target(-0.75F).ease(Quart.OUT)).push(Tween.to(model, 20, 350.0F).target(0.5F).ease(Quart.OUT)).push(Tween.to(model, 18, 350.0F).target(-5.75F).ease(Quart.OUT)).end().beginParallel().push(Tween.to(model, 14, 50.0F).target(0.0F).ease(Quart.IN)).push(Tween.to(model, 12, 50.0F).target(-5.75F).ease(Linear.INOUT)).push(Tween.to(model, 20, 50.0F).target(0.0F).ease(Quart.IN)).push(Tween.to(model, 18, 50.0F).target(-0.75F).ease(Linear.INOUT)).push(Tween.to(model, 12, 300.0F).target(-6.25F).ease(Linear.INOUT)).push(Tween.to(model, 18, 300.0F).target(0.0F).ease(Linear.INOUT)).end().beginParallel().pushPause(150.0F).push(Tween.to(model, 1, 3000.0F).target(37.68F).ease(Quart.OUT)).pushPause(250.0F).end();
      return timeline;
   }

   public boolean usesBodyPart(int part) {
      return part == 18 || part == 12 || part == 0;
   }

   protected void startSound() {}

   protected boolean hasSound() {
      return false;
   }
}
