package net.ilexiconn.nationsgui.forge.server.event;

import net.ilexiconn.nationsgui.forge.server.event.PotionEffectEvent;
import net.ilexiconn.nationsgui.forge.server.event.PotionEffectEvent$1;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;

public class PotionEffectEvent$Finish extends PotionEffectEvent {

   public PotionEffectEvent$Finish(EntityLivingBase entity, PotionEffect effect) {
      super(entity, effect, (PotionEffectEvent$1)null);
   }
}
