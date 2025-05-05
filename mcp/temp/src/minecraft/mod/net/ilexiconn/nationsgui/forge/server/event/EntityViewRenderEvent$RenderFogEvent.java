package net.ilexiconn.nationsgui.forge.server.event;

import net.ilexiconn.nationsgui.forge.server.event.EntityViewRenderEvent;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.Event.HasResult;

@HasResult
public class EntityViewRenderEvent$RenderFogEvent extends EntityViewRenderEvent {

   public final int fogMode;
   public final float farPlaneDistance;


   public EntityViewRenderEvent$RenderFogEvent(EntityRenderer renderer, EntityLivingBase entity, int blockId, double renderPartialTicks, int fogMode, float farPlaneDistance) {
      super(renderer, entity, blockId, renderPartialTicks);
      this.fogMode = fogMode;
      this.farPlaneDistance = farPlaneDistance;
   }
}
