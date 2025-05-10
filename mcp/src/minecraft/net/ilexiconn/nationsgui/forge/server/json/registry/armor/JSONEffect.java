package net.ilexiconn.nationsgui.forge.server.json.registry.armor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;

public class JSONEffect
{
    private int id;
    private int amplifier;

    public void applyEffect(EntityPlayer entity)
    {
        entity.addPotionEffect(new PotionEffect(this.id, 205, this.amplifier, true));
    }
}
