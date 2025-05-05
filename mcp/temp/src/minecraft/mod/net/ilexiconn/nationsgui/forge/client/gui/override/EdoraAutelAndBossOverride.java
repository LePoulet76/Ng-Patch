package net.ilexiconn.nationsgui.forge.client.gui.override;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import javax.imageio.ImageIO;
import net.halalaboos.cfont.CFontRenderer;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.gui.override.ElementOverride;
import net.ilexiconn.nationsgui.forge.client.gui.override.GenericOverride;
import net.ilexiconn.nationsgui.forge.client.gui.override.NotificationOverride;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EdoraAutelOverlayDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EdoraBossOverlayDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import org.lwjgl.opengl.GL11;
import sun.misc.BASE64Decoder;

public class EdoraAutelAndBossOverride extends Gui implements ElementOverride {

   public static CFontRenderer dg25 = ModernGui.getCustomFont("minecraftDungeons", Integer.valueOf(25));


   public static BufferedImage decodeToImage(String imageString) {
      BufferedImage image = null;

      try {
         BASE64Decoder e = new BASE64Decoder();
         byte[] imageByte = e.decodeBuffer(imageString);
         ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
         image = ImageIO.read(bis);
         bis.close();
      } catch (Exception var5) {
         var5.printStackTrace();
      }

      return image;
   }

   public ElementType getType() {
      return ElementType.HOTBAR;
   }

   public ElementType[] getSubTypes() {
      return new ElementType[0];
   }

   public void renderOverride(Minecraft client, ScaledResolution resolution, float partialTicks) {
      if(System.currentTimeMillis() - ClientEventHandler.lastPlayerDispayTAB.longValue() >= 50L) {
         if(!GenericOverride.displaysCountryEntry()) {
            if(!NotificationOverride.displaysNotification()) {
               if(ClientData.currentWarzone.isEmpty()) {
                  if(!ClientData.autelOverlayData.isEmpty() && System.currentTimeMillis() - EdoraAutelOverlayDataPacket.lastPacketReceived < 5000L) {
                     ClientEventHandler.STYLE.bindTexture("overlay_edora");
                     ModernGui.drawScaledCustomSizeModalRect((float)(resolution.func_78326_a() / 2) - 136.0F, 0.0F, 0.0F, 1032.0F, 2040, 444, 272, 59, 5760.0F, 3099.0F, true);

                     for(int var6 = 0; var6 < 10; ++var6) {
                        if(((Double)ClientData.autelOverlayData.get("countCapturedZones")).doubleValue() > (double)var6) {
                           ModernGui.drawScaledCustomSizeModalRect((float)(resolution.func_78326_a() / 2) - 136.0F + 42.0F - 2.0F + (float)(var6 * 49) / 2.5F, 39.4F, 5535.0F, 132.0F, 120, 120, 16, 16, 5760.0F, 3099.0F, true);
                        } else {
                           ModernGui.drawScaledCustomSizeModalRect((float)(resolution.func_78326_a() / 2) - 136.0F + 42.0F - 2.0F + (float)(var6 * 49) / 2.5F, 39.4F, 5535.0F, 12.0F, 120, 120, 16, 16, 5760.0F, 3099.0F, true);
                        }
                     }

                     ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("overlay.autel.title"), (float)(resolution.func_78326_a() / 2), 16.0F, 10544870, 0.5F, "center", true, "minecraftDungeons", 21);
                     ModernGui.drawScaledStringCustomFont(ModernGui.chronoTimeToStr(Long.valueOf(3600000L - (((Double)ClientData.autelOverlayData.get("currentServerTime")).longValue() - ((Double)ClientData.autelOverlayData.get("oldestLastCapture")).longValue())), false), (float)(resolution.func_78326_a() / 2), 23.0F, 16777215, 0.5F, "center", true, "minecraftDungeons", 27);
                  } else if(!ClientData.bossOverlayData.isEmpty()) {
                     if(ClientData.bossOverlayData.containsKey("healthPercent")) {
                        if(System.currentTimeMillis() - EdoraBossOverlayDataPacket.lastPacketReceived > 5000L) {
                           return;
                        }

                        ClientEventHandler.STYLE.bindTexture("overlay_edora");
                        ModernGui.drawScaledCustomSizeModalRect((float)(resolution.func_78326_a() / 2) - 136.0F, 0.0F, 0.0F, 2049.0F, 2040, 444, 272, 59, 5760.0F, 3099.0F, true);
                        ModernGui.drawScaledCustomSizeModalRect((float)(resolution.func_78326_a() / 2) - dg25.getStringWidth("BOSS EDORA") / 2.0F / 2.0F - 10.8F - 3.0F, 8.0F, 4644.0F, 219.0F, 81, 75, 10, 10, 5760.0F, 3099.0F, true);
                        ModernGui.drawScaledCustomSizeModalRect((float)(resolution.func_78326_a() / 2) + dg25.getStringWidth("BOSS EDORA") / 2.0F / 2.0F + 3.0F, 8.0F, 4824.0F, 219.0F, 81, 75, 10, 10, 5760.0F, 3099.0F, true);
                        ModernGui.drawScaledStringCustomFont("BOSS EDORA", (float)(resolution.func_78326_a() / 2), 8.0F, 16777215, 0.5F, "center", true, "minecraftDungeons", 25);
                        ClientEventHandler.STYLE.bindTexture("overlay_edora");
                        ModernGui.drawScaledCustomSizeModalRect((float)(resolution.func_78326_a() / 2) - 163.0F, 24.0F, 0.0F, 909.0F, 2445, 54, 326, 7, 5760.0F, 3099.0F, true);
                        ModernGui.drawScaledCustomSizeModalRect((float)(resolution.func_78326_a() / 2) - 163.0F, 24.0F, 0.0F, 786.0F, (int)(((Double)ClientData.bossOverlayData.get("healthPercent")).doubleValue() * 815.0D * 3.0D), 54, (int)(((Double)ClientData.bossOverlayData.get("healthPercent")).doubleValue() * 815.0D / 2.5D), 7, 5760.0F, 3099.0F, true);
                        ModernGui.drawScaledStringCustomFont(ModernGui.chronoTimeToStr(Long.valueOf(((Double)ClientData.bossOverlayData.get("despawnTime")).longValue()), false), (float)(resolution.func_78326_a() / 2), 35.0F, 16777215, 0.5F, "center", true, "minecraftDungeons", 23);
                     } else {
                        long delayFromPacketReceived = System.currentTimeMillis() - EdoraBossOverlayDataPacket.lastPacketReceived;
                        if(((Double)ClientData.bossOverlayData.get("endPortalTime")).longValue() - (((Double)ClientData.bossOverlayData.get("currentServerTime")).longValue() + delayFromPacketReceived) < 0L) {
                           return;
                        }

                        ClientEventHandler.STYLE.bindTexture("overlay_edora");
                        ModernGui.drawScaledCustomSizeModalRect((float)(resolution.func_78326_a() / 2) - 136.0F, 0.0F, 0.0F, 1545.0F, 2040, 444, 272, 59, 5760.0F, 3099.0F, true);
                        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("overlay.portal.closing"), (float)(resolution.func_78326_a() / 2), 16.0F, 10544870, 0.5F, "center", true, "minecraftDungeons", 21);
                        ModernGui.drawScaledStringCustomFont(ModernGui.chronoTimeToStr(Long.valueOf(((Double)ClientData.bossOverlayData.get("endPortalTime")).longValue() - (((Double)ClientData.bossOverlayData.get("currentServerTime")).longValue() + delayFromPacketReceived)), false), (float)(resolution.func_78326_a() / 2), 23.0F, 16777215, 0.5F, "center", true, "minecraftDungeons", 27);
                     }
                  }
               }

            }
         }
      }
   }

   private void drawSmallString(FontRenderer fontRenderer, String string, int posX, int posY, int color) {
      GL11.glPushMatrix();
      GL11.glTranslatef((float)posX, (float)posY, 0.0F);
      GL11.glScalef(0.95F, 0.95F, 0.95F);
      this.func_73731_b(fontRenderer, string, 0, 0, 16777215);
      GL11.glPopMatrix();
   }

   private void drawVerySmallString(FontRenderer fontRenderer, String string, int posX, int posY, int color) {
      GL11.glPushMatrix();
      GL11.glTranslatef((float)posX, (float)posY, 0.0F);
      GL11.glScalef(0.5F, 0.5F, 0.5F);
      this.func_73731_b(fontRenderer, string, 0, 0, 16777215);
      GL11.glPopMatrix();
   }

   public void drawScaledString(FontRenderer fontRenderer, String text, int x, int y, int color, float scale, boolean centered, boolean shadow) {
      GL11.glPushMatrix();
      GL11.glScalef(scale, scale, scale);
      float newX = (float)x;
      if(centered) {
         newX = (float)x - (float)fontRenderer.func_78256_a(text) * scale / 2.0F;
      }

      if(shadow) {
         fontRenderer.func_85187_a(text, (int)(newX / scale), (int)((float)(y + 1) / scale), (color & 16579836) >> 2 | color & -16777216, false);
      }

      fontRenderer.func_85187_a(text, (int)(newX / scale), (int)((float)y / scale), color, false);
      GL11.glPopMatrix();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

}
