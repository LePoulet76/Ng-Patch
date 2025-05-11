/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.potion.PotionEffect
 */
package net.ilexiconn.nationsgui.forge.server.json.registry.armor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;

public class JSONEffect {
    private int id;
    private int amplifier;

    public void applyEffect(EntityPlayer entity) {
        entity.func_70690_d(new PotionEffect(this.id, 205, this.amplifier, true));
    }
}

