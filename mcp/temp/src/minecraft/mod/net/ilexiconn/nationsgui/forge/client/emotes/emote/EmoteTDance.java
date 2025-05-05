package net.ilexiconn.nationsgui.forge.client.emotes.emote;

import aurelienribon.tweenengine.Timeline;
import net.ilexiconn.nationsgui.forge.client.emotes.emote.base.EmoteBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;

public class EmoteTDance extends EmoteBase {

   public EmoteTDance(EntityPlayer player, ModelBiped model, ModelBiped armorModel, ModelBiped armorLegsModel) {
      super(player, model, armorModel, armorLegsModel);
   }

   public Timeline getTimeline(EntityPlayer player, ModelBiped model) {
      return null;
   }

   public boolean usesBodyPart(int part) {
      return part == 0 || part == 12 || part == 18 || part == 6 || part == 30 || part == 24;
   }

   protected void startSound() {}

   protected boolean hasSound() {
      return false;
   }
}
