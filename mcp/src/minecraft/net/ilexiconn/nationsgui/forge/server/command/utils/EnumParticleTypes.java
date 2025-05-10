package net.ilexiconn.nationsgui.forge.server.command.utils;

import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public enum EnumParticleTypes
{
    EXPLOSION_NORMAL("explode", true),
    EXPLOSION_LARGE("largeexplode", true),
    EXPLOSION_HUGE("hugeexplosion", true),
    FIREWORKS_SPARK("fireworksSpark", false),
    WATER_BUBBLE("bubble", false),
    WATER_SPLASH("splash", false),
    SUSPENDED("suspended", false),
    SUSPENDED_DEPTH("depthsuspend", false),
    CRIT("crit", false),
    CRIT_MAGIC("magicCrit", false),
    SMOKE_NORMAL("smoke", false),
    SMOKE_LARGE("largesmoke", false),
    SPELL("spell", false),
    SPELL_INSTANT("instantSpell", false),
    SPELL_MOB("mobSpell", false),
    SPELL_MOB_AMBIENT("mobSpellAmbient", false),
    SPELL_WITCH("witchMagic", false),
    DRIP_WATER("dripWater", false),
    DRIP_LAVA("dripLava", false),
    VILLAGER_ANGRY("angryVillager", false),
    VILLAGER_HAPPY("happyVillager", false),
    TOWN_AURA("townaura", false),
    NOTE("note", false),
    PORTAL("portal", false),
    ENCHANTMENT_TABLE("enchantmenttable", false),
    FLAME("flame", false),
    LAVA("lava", false),
    FOOTSTEP("footstep", false),
    CLOUD("cloud", false),
    REDSTONE("reddust", false),
    SNOWBALL("snowballpoof", false),
    SNOW_SHOVEL("snowshovel", false),
    SLIME("slime", false),
    HEART("heart", false);
    private final String particleName;
    private final boolean shouldIgnoreRange;
    private static final Map<String, EnumParticleTypes> PARTICLES = Maps.newHashMap();

    private EnumParticleTypes(String particleNameIn, boolean shouldIgnoreRangeIn)
    {
        this.particleName = particleNameIn;
        this.shouldIgnoreRange = shouldIgnoreRangeIn;
    }

    public static String[] getParticleNames()
    {
        Set s = PARTICLES.keySet();
        int n = s.size();
        String[] arr = new String[n];
        int i = 0;
        String x;

        for (Iterator var4 = s.iterator(); var4.hasNext(); arr[i++] = x)
        {
            x = (String)var4.next();
        }

        return arr;
    }

    public String getParticleName()
    {
        return this.particleName;
    }

    public boolean getShouldIgnoreRange()
    {
        return this.shouldIgnoreRange;
    }

    public static EnumParticleTypes getParticleFromId(String particleId)
    {
        return (EnumParticleTypes)PARTICLES.get(particleId);
    }

    static {
        EnumParticleTypes[] var0 = values();
        int var1 = var0.length;

        for (int var2 = 0; var2 < var1; ++var2)
        {
            EnumParticleTypes enumparticletypes = var0[var2];
            PARTICLES.put(enumparticletypes.getParticleName(), enumparticletypes);
        }
    }
}
