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
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import org.lwjgl.opengl.GL11;

public class NotificationOverride extends Gui implements ElementOverride {

   public ElementType getType() {
      return ElementType.HOTBAR;
   }

   public ElementType[] getSubTypes() {
      return new ElementType[0];
   }

   public static boolean displaysNotification() {
      return !ClientData.notifications.isEmpty() && !((Notification)ClientData.notifications.iterator().next()).isExpired();
   }

   public void renderOverride(Minecraft client, ScaledResolution resolution, float partialTicks) {
      ClientEventHandler.STYLE.bindTexture("overlay_icons");
      if(ClientData.versusOverlayData.isEmpty()) {
         if(System.currentTimeMillis() - ClientEventHandler.lastPlayerDispayTAB.longValue() > 50L) {
            Iterator iterator = ClientData.notifications.iterator();
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, 0.0F, 100.0F);
            if(iterator.hasNext()) {
               Notification notification = (Notification)iterator.next();
               if(!notification.isExpired()) {
                  if(ClientProxy.clientConfig.displayNotifications) {
                     notification.render(partialTicks, resolution);
                  }
               } else {
                  iterator.remove();
               }
            }

            GL11.glPopMatrix();
         }

      }
   }

   static {
      ModernGui.getCustomFont("minecraftDungeons", Integer.valueOf(20));
      ModernGui.getCustomFont("minecraftDungeons", Integer.valueOf(23));
      ModernGui.getCustomFont("georamaSemiBold", Integer.valueOf(23));
   }
}
