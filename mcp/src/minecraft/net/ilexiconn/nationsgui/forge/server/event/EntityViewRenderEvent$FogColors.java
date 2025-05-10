package net.ilexiconn.nationsgui.forge.server.event;

import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.EntityLivingBase;

public class EntityViewRenderEvent$FogColors extends EntityViewRenderEvent
{
    public float red;
    public float green;
    public float blue;

    public EntityViewRenderEvent$FogColors(EntityRenderer renderer, EntityLivingBase entity, int blockId, double renderPartialTicks, float red, float green, float blue)
    {
        super(renderer, entity, blockId, renderPartialTicks);
        this.red = red;
        this.green = green;
        this.blue = blue;
    }
}
