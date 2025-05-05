package net.ilexiconn.nationsgui.forge.server.event;

import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.Cancelable;

@Cancelable
public class EntityViewRenderEvent$FogDensity extends EntityViewRenderEvent
{
    public float density;

    public EntityViewRenderEvent$FogDensity(EntityRenderer renderer, EntityLivingBase entity, int blockId, double renderPartialTicks, float density)
    {
        super(renderer, entity, blockId, renderPartialTicks);
        this.density = density;
    }
}
