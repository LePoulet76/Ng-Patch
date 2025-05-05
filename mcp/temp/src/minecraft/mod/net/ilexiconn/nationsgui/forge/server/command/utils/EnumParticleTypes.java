package net.ilexiconn.nationsgui.forge.server.command.utils;

import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public enum EnumParticleTypes {

   EXPLOSION_NORMAL("EXPLOSION_NORMAL", 0, "explode", true),
   EXPLOSION_LARGE("EXPLOSION_LARGE", 1, "largeexplode", true),
   EXPLOSION_HUGE("EXPLOSION_HUGE", 2, "hugeexplosion", true),
   FIREWORKS_SPARK("FIREWORKS_SPARK", 3, "fireworksSpark", false),
   WATER_BUBBLE("WATER_BUBBLE", 4, "bubble", false),
   WATER_SPLASH("WATER_SPLASH", 5, "splash", false),
   SUSPENDED("SUSPENDED", 6, "suspended", false),
   SUSPENDED_DEPTH("SUSPENDED_DEPTH", 7, "depthsuspend", false),
   CRIT("CRIT", 8, "crit", false),
   CRIT_MAGIC("CRIT_MAGIC", 9, "magicCrit", false),
   SMOKE_NORMAL("SMOKE_NORMAL", 10, "smoke", false),
   SMOKE_LARGE("SMOKE_LARGE", 11, "largesmoke", false),
   SPELL("SPELL", 12, "spell", false),
   SPELL_INSTANT("SPELL_INSTANT", 13, "instantSpell", false),
   SPELL_MOB("SPELL_MOB", 14, "mobSpell", false),
   SPELL_MOB_AMBIENT("SPELL_MOB_AMBIENT", 15, "mobSpellAmbient", false),
   SPELL_WITCH("SPELL_WITCH", 16, "witchMagic", false),
   DRIP_WATER("DRIP_WATER", 17, "dripWater", false),
   DRIP_LAVA("DRIP_LAVA", 18, "dripLava", false),
   VILLAGER_ANGRY("VILLAGER_ANGRY", 19, "angryVillager", false),
   VILLAGER_HAPPY("VILLAGER_HAPPY", 20, "happyVillager", false),
   TOWN_AURA("TOWN_AURA", 21, "townaura", false),
   NOTE("NOTE", 22, "note", false),
   PORTAL("PORTAL", 23, "portal", false),
   ENCHANTMENT_TABLE("ENCHANTMENT_TABLE", 24, "enchantmenttable", false),
   FLAME("FLAME", 25, "flame", false),
   LAVA("LAVA", 26, "lava", false),
   FOOTSTEP("FOOTSTEP", 27, "footstep", false),
   CLOUD("CLOUD", 28, "cloud", false),
   REDSTONE("REDSTONE", 29, "reddust", false),
   SNOWBALL("SNOWBALL", 30, "snowballpoof", false),
   SNOW_SHOVEL("SNOW_SHOVEL", 31, "snowshovel", false),
   SLIME("SLIME", 32, "slime", false),
   HEART("HEART", 33, "heart", false);
   private final String particleName;
   private final boolean shouldIgnoreRange;
   private static final Map<String, EnumParticleTypes> PARTICLES = Maps.newHashMap();
   // $FF: synthetic field
   private static final EnumParticleTypes[] $VALUES = new EnumParticleTypes[]{EXPLOSION_NORMAL, EXPLOSION_LARGE, EXPLOSION_HUGE, FIREWORKS_SPARK, WATER_BUBBLE, WATER_SPLASH, SUSPENDED, SUSPENDED_DEPTH, CRIT, CRIT_MAGIC, SMOKE_NORMAL, SMOKE_LARGE, SPELL, SPELL_INSTANT, SPELL_MOB, SPELL_MOB_AMBIENT, SPELL_WITCH, DRIP_WATER, DRIP_LAVA, VILLAGER_ANGRY, VILLAGER_HAPPY, TOWN_AURA, NOTE, PORTAL, ENCHANTMENT_TABLE, FLAME, LAVA, FOOTSTEP, CLOUD, REDSTONE, SNOWBALL, SNOW_SHOVEL, SLIME, HEART};


   private EnumParticleTypes(String var1, int var2, String particleNameIn, boolean shouldIgnoreRangeIn) {
      this.particleName = particleNameIn;
      this.shouldIgnoreRange = shouldIgnoreRangeIn;
   }

   public static String[] getParticleNames() {
      Set s = PARTICLES.keySet();
      int n = s.size();
      String[] arr = new String[n];
      int i = 0;

      String x;
      for(Iterator var4 = s.iterator(); var4.hasNext(); arr[i++] = x) {
         x = (String)var4.next();
      }

      return arr;
   }

   public String getParticleName() {
      return this.particleName;
   }

   public boolean getShouldIgnoreRange() {
      return this.shouldIgnoreRange;
   }

   public static EnumParticleTypes getParticleFromId(String particleId) {
      return (EnumParticleTypes)PARTICLES.get(particleId);
   }

   static {
      EnumParticleTypes[] var0 = values();
      int var1 = var0.length;

      for(int var2 = 0; var2 < var1; ++var2) {
         EnumParticleTypes enumparticletypes = var0[var2];
         PARTICLES.put(enumparticletypes.getParticleName(), enumparticletypes);
      }

   }
}
