package net.ilexiconn.nationsgui.forge.client.gui.override;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.HashMap;
import javax.imageio.ImageIO;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.gui.override.ElementOverride;
import net.ilexiconn.nationsgui.forge.client.gui.override.GenericOverride;
import net.ilexiconn.nationsgui.forge.client.gui.override.NotificationOverride;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import org.lwjgl.opengl.GL11;
import sun.misc.BASE64Decoder;

public class WarzoneOverride extends Gui implements ElementOverride {

   private HashMap<String, DynamicTexture> flagTextures = new HashMap();


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
               if(!ClientData.currentWarzone.isEmpty()) {
                  ModernGui.bindTextureOverlayMain();
                  ModernGui.drawScaledCustomSizeModalRect((float)(resolution.func_78326_a() / 2) - 148.0F, 0.0F, 1180.0F, 880.0F, 740, 153, 296, 61, 1920.0F, 1033.0F, true);
                  String warzoneName = "Warzone " + (String)ClientData.currentWarzone.get("name");
                  ModernGui.drawScaledStringCustomFont(warzoneName, (float)(resolution.func_78326_a() / 2), 15.0F, 16000586, 0.5F, "center", true, "minecraftDungeons", 18);
                  ModernGui.drawScaledStringCustomFont(!((String)ClientData.currentWarzone.get("factionId")).isEmpty()?((String)ClientData.currentWarzone.get("factionName")).replaceAll("Empire", "Emp"):I18n.func_135053_a("overlay.warzone.free"), (float)(resolution.func_78326_a() / 2), 22.0F, 16777215, 0.5F, "center", true, "minecraftDungeons", 25);
                  float progress = Float.parseFloat((String)ClientData.currentWarzone.get("percent")) / 100.0F;
                  ModernGui.bindTextureOverlayMain();
                  ModernGui.drawScaledCustomSizeModalRect((float)(resolution.func_78326_a() / 2) - 83.6F, 42.0F, 1502.0F, 807.0F, 418, 11, 167, 4, 1920.0F, 1033.0F, true);
                  ModernGui.drawScaledCustomSizeModalRect((float)(resolution.func_78326_a() / 2) - 83.6F, 42.0F, 1502.0F, 831.0F, (int)(418.0F * progress), 11, (int)((float)((int)(418.0F * progress)) / 2.5F), 4, 1920.0F, 1033.0F, true);
                  ModernGui.drawScaledStringCustomFont((String)ClientData.currentWarzone.get("percent") + "%", (float)(resolution.func_78326_a() / 2), 47.0F, 16777215, 0.5F, "center", true, "minecraftDungeons", 25);
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
}
