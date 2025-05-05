package net.ilexiconn.nationsgui.forge.client.util;

import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class RenderUtils {

   public static void translateToWorldCoords(Entity entity, float frame) {
      double interpPosX = entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * (double)frame;
      double interpPosY = entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * (double)frame;
      double interpPosZ = entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * (double)frame;
      GL11.glTranslated(-interpPosX, -interpPosY, -interpPosZ);
   }
}
