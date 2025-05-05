package net.ilexiconn.nationsgui.forge.client.particle;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.client.particle.EntityNoteFX;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class RadioParticle extends EntityNoteFX {

   public RadioParticle(World world, double x, double y, double z, float scale, Random random) {
      super(world, x, y, z, 0.0D, 0.0D, 0.0D);
      this.field_70552_h = (float)random.nextInt(11) / 10.0F;
      this.field_70553_i = (float)random.nextInt(11) / 10.0F;
      this.field_70551_j = (float)random.nextInt(11) / 10.0F;
      this.field_70544_f *= scale;
   }
}
