/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.potion.PotionEffect
 *  net.minecraftforge.event.Event
 */
package net.ilexiconn.nationsgui.forge.server.event;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.Event;

public class PotionEffectEvent
extends Event {
    public final EntityLivingBase entity;
    public final PotionEffect effect;

    private PotionEffectEvent(EntityLivingBase entity, PotionEffect effect) {
        this.entity = entity;
        this.effect = effect;
    }

    public static class Finish
    extends PotionEffectEvent {
        public Finish(EntityLivingBase entity, PotionEffect effect) {
            super(entity, effect);
        }
    }

    public static class Change
    extends PotionEffectEvent {
        public Change(EntityLivingBase entity, PotionEffect effect) {
            super(entity, effect);
        }
    }

    public static class Start
    extends PotionEffectEvent {
        public Start(EntityLivingBase entity, PotionEffect effect) {
            super(entity, effect);
        }
    }
}

