package net.ilexiconn.nationsgui.forge.client.gui.override;

import fr.nationsglory.ngvehicles.client.render.utils.GlStateManagerHelper;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.imageio.ImageIO;
import net.halalaboos.cfont.CFontRenderer;
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
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import sun.misc.BASE64Decoder;

public class AssaultOverride extends Gui implements ElementOverride {

   private HashMap<String, DynamicTexture> flagTextures = new HashMap();
   public static CFontRenderer dg25 = ModernGui.getCustomFont("minecraftDungeons", Integer.valueOf(25));
   public static CFontRenderer georamaBold25 = ModernGui.getCustomFont("georamaBold", Integer.valueOf(25));
   public List<String> tooltipToDraw = new ArrayList();


   public ElementType getType() {
      return ElementType.HOTBAR;
   }

   public ElementType[] getSubTypes() {
      return new ElementType[0];
   }

   public void renderOverride(Minecraft client, ScaledResolution resolution, float partialTicks) {
      this.tooltipToDraw = new ArrayList();
      if(System.currentTimeMillis() - ClientEventHandler.lastPlayerDispayTAB.longValue() >= 50L) {
         if(!GenericOverride.displaysCountryEntry()) {
            if(!NotificationOverride.displaysNotification()) {
               if(!ClientData.currentAssault.isEmpty() && ClientData.currentWarzone.isEmpty()) {
                  ModernGui.bindTextureOverlayMain();
                  float progress = Float.parseFloat((String)ClientData.currentAssault.get("progressTime"));
                  ModernGui.drawScaledCustomSizeModalRect((float)(resolution.func_78326_a() / 2) - 42.0F, 46.0F, 1218.0F, 837.0F, (int)(195.0F * progress), 5, (int)(78.0F * progress), 2, 1920.0F, 1033.0F, false);
                  ModernGui.bindTextureOverlayMain();
                  ModernGui.drawScaledCustomSizeModalRect((float)(resolution.func_78326_a() / 2) - 228.4F, 0.0F, 770.0F, 536.0F, 1142, 167, 456, 66, 1920.0F, 1033.0F, true);
                  ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("overlay.assault.state." + (String)ClientData.currentAssault.get("currentState")), (float)(resolution.func_78326_a() / 2), 15.0F, 16000586, 0.5F, "center", true, "minecraftDungeons", 20);
                  ModernGui.drawScaledStringCustomFont((String)ClientData.currentAssault.get("remainingTimeInCurrentState"), (float)(resolution.func_78326_a() / 2), 22.0F, 16777215, 0.5F, "center", true, "minecraftDungeons", 27);
                  int currentStateNumber = Integer.parseInt((String)ClientData.currentAssault.get("currentStateNumber"));
                  if(currentStateNumber >= 1) {
                     ModernGui.bindTextureOverlayMain();

                     for(int hasAttackerHandicap = 0; hasAttackerHandicap < currentStateNumber; ++hasAttackerHandicap) {
                        boolean attackerName = hasAttackerHandicap == 1 || hasAttackerHandicap == 3 || hasAttackerHandicap == 4;
                        ModernGui.drawScaledCustomSizeModalRect((float)(resolution.func_78326_a() / 2) - 46.0F + (float)(hasAttackerHandicap * 18), 36.5F, attackerName?1268.0F:1218.0F, 779.0F, 50, 50, 20, 20, 1920.0F, 1033.0F, false);
                     }
                  }

                  ModernGui.bindTextureOverlayMain();
                  boolean var15 = Float.parseFloat((String)ClientData.currentAssault.get("multiRatioAttackersByDefenders")) > 1.0F;
                  byte mouseX;
                  int mouseY;
                  if(var15) {
                     ModernGui.bindTextureOverlayMain();
                     int var16 = resolution.func_78326_a() / 2 - 135;
                     byte hasDefenderHandicap = 11;
                     byte defenderName = 7;
                     mouseX = 8;
                     ModernGui.drawScaledCustomSizeModalRect((float)var16, (float)hasDefenderHandicap, 1318.0F, 729.0F, 18, 21, defenderName, mouseX, 1920.0F, 1033.0F, false);
                     mouseY = Mouse.getX() * resolution.func_78326_a() / client.field_71443_c;
                     int iconHeight = resolution.func_78328_b() - Mouse.getY() * resolution.func_78328_b() / client.field_71440_d - 1;
                     if(mouseY >= var16 && mouseY <= var16 + defenderName && iconHeight >= hasDefenderHandicap && iconHeight <= hasDefenderHandicap + mouseX) {
                        this.generateHandicapTooltip();
                     }
                  }

                  String var17 = ((String)ClientData.currentAssault.get("attackerFactionName")).replaceAll("Empire", "Emp");
                  ModernGui.drawScaledStringCustomFont(var17.length() < 14?var17.toUpperCase():var17.substring(0, 13).toUpperCase() + "..", (float)(resolution.func_78326_a() / 2 - 135 + (var15?10:0)), 10.0F, 16000586, 0.4F, "left", true, "minecraftDungeons", 30);
                  ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("overlay.assault.attacker"), (float)(resolution.func_78326_a() / 2 - 135), 22.0F, 16777215, 0.5F, "left", true, "georamaExtraBold", 23);
                  ModernGui.drawScaledStringCustomFont((String)ClientData.currentAssault.get("attackerScore") + " Points", (float)(resolution.func_78326_a() / 2 - 135), 29.0F, 16777215, 0.5F, "left", true, "georamaSemiBold", 23);
                  ModernGui.drawScaledStringCustomFont((String)ClientData.currentAssault.get("attackerPlayersCount") + " " + I18n.func_135053_a("main.players"), (float)(resolution.func_78326_a() / 2 - 135), 36.0F, 16777215, 0.5F, "left", true, "georamaSemiBold", 23);
                  ModernGui.bindTextureOverlayMain();
                  boolean var18 = Float.parseFloat((String)ClientData.currentAssault.get("multiRatioAttackersByDefenders")) < 1.0F;
                  if(var18) {
                     ModernGui.bindTextureOverlayMain();
                     int var19 = resolution.func_78326_a() / 2 + 135 - 7;
                     mouseX = 11;
                     byte var22 = 7;
                     byte var23 = 8;
                     ModernGui.drawScaledCustomSizeModalRect((float)var19, (float)mouseX, 1318.0F, 729.0F, 18, 21, var22, var23, 1920.0F, 1033.0F, false);
                     int mouseX1 = Mouse.getX() * resolution.func_78326_a() / client.field_71443_c;
                     int mouseY1 = resolution.func_78328_b() - Mouse.getY() * resolution.func_78328_b() / client.field_71440_d - 1;
                     if(mouseX1 >= var19 && mouseX1 <= var19 + var22 && mouseY1 >= mouseX && mouseY1 <= mouseX + var23) {
                        this.generateHandicapTooltip();
                     }
                  }

                  String var20 = ((String)ClientData.currentAssault.get("defenderFactionName")).replaceAll("Empire", "Emp");
                  ModernGui.drawScaledStringCustomFont(var20.length() < 14?var20.toUpperCase():var20.substring(0, 13).toUpperCase() + "..", (float)(resolution.func_78326_a() / 2 + 135 - (var18?9:0)), 10.0F, 16000586, 0.4F, "right", true, "minecraftDungeons", 30);
                  ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("overlay.assault.defender"), (float)(resolution.func_78326_a() / 2 + 135), 22.0F, 16777215, 0.5F, "right", true, "georamaExtraBold", 23);
                  ModernGui.drawScaledStringCustomFont((String)ClientData.currentAssault.get("defenderScore") + " Points", (float)(resolution.func_78326_a() / 2 + 135), 29.0F, 16777215, 0.5F, "right", true, "georamaSemiBold", 23);
                  ModernGui.drawScaledStringCustomFont((String)ClientData.currentAssault.get("defenderPlayersCount") + " " + I18n.func_135053_a("main.players"), (float)(resolution.func_78326_a() / 2 + 135), 36.0F, 16777215, 0.5F, "right", true, "georamaSemiBold", 23);
                  if(!this.tooltipToDraw.isEmpty()) {
                     int var21 = Mouse.getX() * resolution.func_78326_a() / client.field_71443_c;
                     mouseY = resolution.func_78328_b() - Mouse.getY() * resolution.func_78328_b() / client.field_71440_d - 1;
                     this.drawCustomHoveringText(this.tooltipToDraw, var21, mouseY, client.field_71466_p, resolution);
                  }
               }

            }
         }
      }
   }

   public void generateHandicapTooltip() {
      float ratio = Float.parseFloat((String)ClientData.currentAssault.get("multiRatioAttackersByDefenders"));
      int totalPlayers = Integer.parseInt((String)ClientData.currentAssault.get("attackerPlayersCount")) + Integer.parseInt((String)ClientData.currentAssault.get("defenderPlayersCount"));
      this.tooltipToDraw.add("\u00a74Ratio ATT/DEF: \u00a7c" + ratio);
      this.tooltipToDraw.add("");
      if(ratio != 1.0F) {
         this.tooltipToDraw.addAll(Arrays.asList(I18n.func_135053_a("overlay.assault.handicap.potions").replaceAll("<ratio>", String.format("%.2f", new Object[]{Float.valueOf(ratio)})).split("##")));
      }

      if((ratio > 2.0F || (double)ratio < 0.5D) && totalPlayers >= 8) {
         this.tooltipToDraw.addAll(Arrays.asList(I18n.func_135053_a("overlay.assault.handicap.conditions_divided").split("##")));
         this.tooltipToDraw.addAll(Arrays.asList(I18n.func_135053_a("overlay.assault.handicap.no_allies").split("##")));
         this.tooltipToDraw.addAll(Arrays.asList(I18n.func_135053_a("overlay.assault.handicap.no_missiles").split("##")));
         this.tooltipToDraw.addAll(Arrays.asList(I18n.func_135053_a("overlay.assault.handicap.damage_reduction").split("##")));
         if(ratio > 2.0F) {
            this.tooltipToDraw.addAll(Arrays.asList(I18n.func_135053_a("overlay.assault.handicap.no_kill_package").split("##")));
         }

         if((double)ratio < 0.5D) {
            this.tooltipToDraw.addAll(Arrays.asList(I18n.func_135053_a("overlay.assault.handicap.no_point_package").split("##")));
            this.tooltipToDraw.addAll(Arrays.asList(I18n.func_135053_a("overlay.assault.handicap.no_protection_r&d").split("##")));
         }
      }

   }

   private void drawCustomHoveringText(List<String> textLines, int x, int y, FontRenderer font, ScaledResolution resolution) {
      if(!textLines.isEmpty()) {
         GlStateManagerHelper.disableRescaleNormal();
         RenderHelper.func_74518_a();
         GlStateManagerHelper.disableLighting();
         GlStateManagerHelper.disableDepth();
         int tooltipWidth = 0;
         Iterator tooltipX = textLines.iterator();

         int tooltipHeight;
         while(tooltipX.hasNext()) {
            String tooltipY = (String)tooltipX.next();
            tooltipHeight = font.func_78256_a(tooltipY);
            if(tooltipHeight > tooltipWidth) {
               tooltipWidth = tooltipHeight;
            }
         }

         int var15 = x + 12;
         int var16 = y - 12;
         tooltipHeight = 8;
         if(textLines.size() > 1) {
            tooltipHeight += 2 + (textLines.size() - 1) * 10;
         }

         if(var15 + tooltipWidth > resolution.func_78326_a()) {
            var15 -= 28 + tooltipWidth;
         }

         if(var16 + tooltipHeight + 6 > resolution.func_78328_b()) {
            var16 = resolution.func_78328_b() - tooltipHeight - 6;
         }

         int backgroundColor = -267386864;
         func_73734_a(var15 - 3, var16 - 4, var15 + tooltipWidth + 3, var16 - 3, -267386864);
         func_73734_a(var15 - 3, var16 + tooltipHeight + 3, var15 + tooltipWidth + 3, var16 + tooltipHeight + 4, -267386864);
         func_73734_a(var15 - 3, var16 - 3, var15 + tooltipWidth + 3, var16 + tooltipHeight + 3, -267386864);
         func_73734_a(var15 - 4, var16 - 3, var15 - 3, var16 + tooltipHeight + 3, -267386864);
         func_73734_a(var15 + tooltipWidth + 3, var16 - 3, var15 + tooltipWidth + 4, var16 + tooltipHeight + 3, -267386864);
         int borderColorStart = 1347420415;
         int borderColorEnd = 1344798847;
         this.func_73733_a(var15 - 3, var16 - 3 + 1, var15 - 3 + 1, var16 + tooltipHeight + 3 - 1, 1347420415, 1344798847);
         this.func_73733_a(var15 + tooltipWidth + 2, var16 - 3 + 1, var15 + tooltipWidth + 3, var16 + tooltipHeight + 3 - 1, 1347420415, 1344798847);
         this.func_73733_a(var15 - 3, var16 - 3, var15 + tooltipWidth + 3, var16 - 3 + 1, 1347420415, 1347420415);
         this.func_73733_a(var15 - 3, var16 + tooltipHeight + 2, var15 + tooltipWidth + 3, var16 + tooltipHeight + 3, 1344798847, 1344798847);

         for(int lineNumber = 0; lineNumber < textLines.size(); ++lineNumber) {
            String line = (String)textLines.get(lineNumber);
            font.func_78261_a(line, var15, var16, -1);
            if(lineNumber == 0) {
               var16 += 2;
            }

            var16 += 10;
         }

         GlStateManagerHelper.enableLighting();
         GlStateManagerHelper.enableDepth();
         RenderHelper.func_74519_b();
         GlStateManagerHelper.enableRescaleNormal();
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
