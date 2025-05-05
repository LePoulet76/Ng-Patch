package net.ilexiconn.nationsgui.forge.client.gui.override;

import java.util.HashMap;
import net.halalaboos.cfont.CFontRenderer;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.gui.override.ElementOverride;
import net.ilexiconn.nationsgui.forge.client.gui.override.GenericOverride$1;
import net.ilexiconn.nationsgui.forge.client.gui.override.NotificationOverride;
import net.ilexiconn.nationsgui.forge.client.voices.keybindings.KeyManager;
import net.ilexiconn.nationsgui.forge.server.voices.VoiceChat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import org.lwjgl.opengl.GL11;

public class GenericOverride extends Gui implements ElementOverride {

   public static long COUNTRY_ENTRY_DISPLAY_TIME = 3000L;
   public static int GUI_SCALE = 3;
   public static CFontRenderer dg30 = ModernGui.getCustomFont("minecraftDungeons", Integer.valueOf(30));
   public static CFontRenderer georamaBold20 = ModernGui.getCustomFont("georamaBold", Integer.valueOf(20));
   public static CFontRenderer georamaSemiBold30 = ModernGui.getCustomFont("georamaSemiBold", Integer.valueOf(30));
   public static HashMap<String, Integer> relationsColor = new GenericOverride$1();


   public ElementType getType() {
      return ElementType.HOTBAR;
   }

   public ElementType[] getSubTypes() {
      return new ElementType[0];
   }

   public static boolean displaysCountryEntry() {
      return ClientData.countryTitleInfos != null && !ClientData.countryTitleInfos.isEmpty() && System.currentTimeMillis() - Long.parseLong((String)ClientData.countryTitleInfos.get("displayTime")) < COUNTRY_ENTRY_DISPLAY_TIME;
   }

   public void renderOverride(Minecraft client, ScaledResolution resolution, float partialTicks) {
      if(ClientData.playerInfos != null) {
         if(System.currentTimeMillis() - ClientEventHandler.lastPlayerDispayTAB.longValue() >= 50L) {
            if(ClientData.versusOverlayData.isEmpty()) {
               int sizeY = resolution.func_78328_b();
               int sizeX = resolution.func_78326_a();
               int centerX = sizeX / 2;
               if(!ClientData.playerInfos.isEmpty() && ClientData.currentAssault.isEmpty() && ClientData.currentWarzone.isEmpty()) {
                  byte offsetX = 0;
                  if(KeyManager.getInstance().isKeyMuted() || VoiceChat.getProxyInstance().isRecorderActive()) {
                     offsetX = 40;
                  }

                  ModernGui.drawScaledStringCustomFont(((String)ClientData.playerInfos.get("money")).toUpperCase(), (float)(offsetX + 5), 6.0F, 16777215, 0.4F, "left", true, "minecraftDungeons", 30);
                  ModernGui.bindTextureOverlayMain();
                  ModernGui.drawScaledCustomSizeModalRect((float)(offsetX + 5 + (int)(dg30.getStringWidth(((String)ClientData.playerInfos.get("money")).toUpperCase()) * 0.4F) + 2), 7.5F, 226.0F, 95.0F, 19, 20, 8, 9, 1920.0F, 1033.0F, true);
                  ModernGui.drawScaledStringCustomFont(((String)ClientData.playerInfos.get("orbs")).toUpperCase(), (float)(offsetX + 5 + (int)(dg30.getStringWidth(((String)ClientData.playerInfos.get("money")).toUpperCase()) * 0.4F) + 2 + 8 + 5), 6.0F, 16777215, 0.4F, "left", true, "minecraftDungeons", 30);
                  ModernGui.bindTextureOverlayMain();
                  ModernGui.drawScaledCustomSizeModalRect((float)(offsetX + 5 + (int)(dg30.getStringWidth(((String)ClientData.playerInfos.get("money")).toUpperCase()) * 0.4F) + 2 + 8 + 5 + (int)(dg30.getStringWidth(((String)ClientData.playerInfos.get("orbs")).toUpperCase()) * 0.4F) + 2), 7.5F, 326.0F, 95.0F, 19, 20, 8, 9, 1920.0F, 1033.0F, true);
               }

               if(displaysCountryEntry() && !NotificationOverride.displaysNotification()) {
                  ModernGui.drawScaledStringCustomFont(((String)ClientData.countryTitleInfos.get("countryName")).toUpperCase().replaceAll("^\u00a7.{1}", ""), (float)centerX, 10.0F, 16777215, 0.75F, "center", true, "minecraftDungeons", 30);
                  if(ClientData.countryTitleInfos.containsKey("relation")) {
                     ModernGui.glColorHex(((Integer)relationsColor.get(ClientData.countryTitleInfos.get("relation"))).intValue(), 1.0F);
                     ModernGui.drawRectangle((float)(centerX - 25), 30.0F, this.field_73735_i, 50.0F, 12.0F);
                     ModernGui.glColorHex(-14869195, 1.0F);
                     ModernGui.drawRectangle((float)(centerX - 25), 42.0F, this.field_73735_i, 50.0F, 1.0F);
                  }

                  ModernGui.drawScaledStringCustomFont(((String)ClientData.countryTitleInfos.get("countryName")).startsWith("\u00a7")?I18n.func_135053_a("overlay.faction.relation.free"):I18n.func_135053_a("overlay.faction.relation.short." + (String)ClientData.countryTitleInfos.get("relation")).toUpperCase(), (float)centerX, 32.0F, 16777215, 0.45F, "center", false, "minecraftDungeons", 23);
                  if(ClientData.countryTitleInfos.containsKey("message") && !((String)ClientData.countryTitleInfos.get("message")).isEmpty()) {
                     ModernGui.drawSectionStringCustomFont((String)ClientData.countryTitleInfos.get("message"), (float)centerX, 47.0F, 16777215, 0.5F, "center", true, "georamaSemiBold", 25, 7, 400);
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
