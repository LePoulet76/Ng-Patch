/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$ElementType
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.override;

import java.util.Iterator;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.Notification;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.gui.override.ElementOverride;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.lwjgl.opengl.GL11;

public class NotificationOverride
extends Gui
implements ElementOverride {
    @Override
    public RenderGameOverlayEvent.ElementType getType() {
        return RenderGameOverlayEvent.ElementType.HOTBAR;
    }

    @Override
    public RenderGameOverlayEvent.ElementType[] getSubTypes() {
        return new RenderGameOverlayEvent.ElementType[0];
    }

    public static boolean displaysNotification() {
        return !ClientData.notifications.isEmpty() && !ClientData.notifications.iterator().next().isExpired();
    }

    @Override
    public void renderOverride(Minecraft client, ScaledResolution resolution, float partialTicks) {
        ClientEventHandler.STYLE.bindTexture("overlay_icons");
        if (!ClientData.versusOverlayData.isEmpty()) {
            return;
        }
        if (System.currentTimeMillis() - ClientEventHandler.lastPlayerDispayTAB > 50L) {
            Iterator<Notification> iterator = ClientData.notifications.iterator();
            GL11.glPushMatrix();
            GL11.glTranslatef((float)0.0f, (float)0.0f, (float)100.0f);
            if (iterator.hasNext()) {
                Notification notification = iterator.next();
                if (!notification.isExpired()) {
                    if (ClientProxy.clientConfig.displayNotifications) {
                        notification.render(partialTicks, resolution);
                    }
                } else {
                    iterator.remove();
                }
            }
            GL11.glPopMatrix();
        }
    }

    static {
        ModernGui.getCustomFont("minecraftDungeons", 20);
        ModernGui.getCustomFont("minecraftDungeons", 23);
        ModernGui.getCustomFont("georamaSemiBold", 23);
    }
}

