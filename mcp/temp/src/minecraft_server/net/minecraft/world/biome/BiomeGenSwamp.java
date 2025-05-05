package net.minecraft.world.biome;

import java.util.Random;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.SpawnListEntry;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeGenSwamp extends BiomeGenBase {

   protected BiomeGenSwamp(int p_i1988_1_) {
      super(p_i1988_1_);
      this.field_76760_I.field_76832_z = 2;
      this.field_76760_I.field_76802_A = -999;
      this.field_76760_I.field_76804_C = 1;
      this.field_76760_I.field_76798_D = 8;
      this.field_76760_I.field_76799_E = 10;
      this.field_76760_I.field_76806_I = 1;
      this.field_76760_I.field_76833_y = 4;
      this.field_76759_H = 14745518;
      this.field_76761_J.add(new SpawnListEntry(EntitySlime.class, 1, 1, 1));
   }

   public WorldGenerator func_76740_a(Random p_76740_1_) {
      return this.field_76763_Q;
   }
}
