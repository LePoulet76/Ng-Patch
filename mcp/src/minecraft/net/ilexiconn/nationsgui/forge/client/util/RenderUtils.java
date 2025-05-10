package net.ilexiconn.nationsgui.forge.client.util;

import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class RenderUtils
{
    public static void translateToWorldCoords(Entity entity, float frame)
    {
        double interpPosX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)frame;
        double interpPosY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)frame;
        double interpPosZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)frame;
        GL11.glTranslated(-interpPosX, -interpPosY, -interpPosZ);
    }
}
