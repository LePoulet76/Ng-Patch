package net.ilexiconn.nationsgui.forge.client.gui.override;

import java.util.HashMap;
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

public class NoelMegaGiftOverride extends Gui implements ElementOverride {

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
               if(ClientData.currentAssault.isEmpty() && ClientData.currentWarzone.isEmpty() && System.currentTimeMillis() - ClientData.noelMegaGiftTimeSpawn.longValue() < 70000L) {
                  int remainingTimeSec = Math.max(0, 60 - (int)((System.currentTimeMillis() - ClientData.noelMegaGiftTimeSpawn.longValue()) / 1000L));
                  ClientEventHandler.STYLE.bindTexture("overlay_hud");
                  ModernGui.drawScaledCustomSizeModalRect((float)(resolution.func_78326_a() / 2) - 148.0F, 0.0F, 0.0F, 739.0F, 740, 153, 296, 61, 1920.0F, 1033.0F, true);
                  ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("gui.overlay.mega_gift.title"), (float)(resolution.func_78326_a() / 2), 15.0F, 14984509, 0.5F, "center", true, "minecraftDungeons", 18);
                  ModernGui.drawScaledStringCustomFont(remainingTimeSec > 0?remainingTimeSec + "":I18n.func_135053_a("gui.overlay.mega_gift.opening"), (float)(resolution.func_78326_a() / 2), 22.0F, 16777215, 0.5F, "center", true, "minecraftDungeons", 25);
               }

            }
         }
      }
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
