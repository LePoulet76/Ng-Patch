/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.EntityRenderer
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraftforge.event.Cancelable
 *  net.minecraftforge.event.Event
 *  net.minecraftforge.event.Event$HasResult
 */
package net.ilexiconn.nationsgui.forge.server.event;

import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.Cancelable;
import net.minecraftforge.event.Event;

public class EntityViewRenderEvent
extends Event {
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

    public static class FogColors
    extends EntityViewRenderEvent {
        public float red;
        public float green;
        public float blue;

        public FogColors(EntityRenderer renderer, EntityLivingBase entity, int blockId, double renderPartialTicks, float red, float green, float blue) {
            super(renderer, entity, blockId, renderPartialTicks);
            this.red = red;
            this.green = green;
            this.blue = blue;
        }
    }

    @Event.HasResult
    public static class RenderFogEvent
    extends EntityViewRenderEvent {
        public final int fogMode;
        public final float farPlaneDistance;

        public RenderFogEvent(EntityRenderer renderer, EntityLivingBase entity, int blockId, double renderPartialTicks, int fogMode, float farPlaneDistance) {
            super(renderer, entity, blockId, renderPartialTicks);
            this.fogMode = fogMode;
            this.farPlaneDistance = farPlaneDistance;
        }
    }

    @Cancelable
    public static class FogDensity
    extends EntityViewRenderEvent {
        public float density;

        public FogDensity(EntityRenderer renderer, EntityLivingBase entity, int blockId, double renderPartialTicks, float density) {
            super(renderer, entity, blockId, renderPartialTicks);
            this.density = density;
        }
    }
}

