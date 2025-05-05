package net.ilexiconn.nationsgui.forge.server.event;

import net.ilexiconn.nationsgui.forge.server.event.PotionEffectEvent$1;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.Event;

public class PotionEffectEvent extends Event
{
    public final EntityLivingBase entity;
    public final PotionEffect effect;

    private PotionEffectEvent(EntityLivingBase entity, PotionEffect effect)
    {
        this.entity = entity;
        this.effect = effect;
    }

    PotionEffectEvent(EntityLivingBase x0, PotionEffect x1, PotionEffectEvent$1 x2)
    {
        this(x0, x1);
    }
}
