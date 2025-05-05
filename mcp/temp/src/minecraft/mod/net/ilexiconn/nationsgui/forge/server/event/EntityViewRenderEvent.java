package net.ilexiconn.nationsgui.forge.server.event;

import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.Event;

public class EntityViewRenderEvent extends Event {

   public final EntityRenderer renderer;
   public final EntityLivingBase entity;
   public final int blockId;
   public final double renderPartialTicks;


   public EntityViewRenderEvent(EntityRenderer renderer, EntityLivingBase entity, int blockId, double renderPartialTicks) {
      this.renderer = renderer;
      this.entity = entity;
      this.blockId = blockId;
      this.renderPartialTicks = renderPartialTicks;
   }
}
