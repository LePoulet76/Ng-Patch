package net.ilexiconn.nationsgui.forge.client.gui.override;

import com.google.gson.internal.LinkedTreeMap;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import javax.imageio.ImageIO;
import net.halalaboos.cfont.CFontRenderer;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.gui.override.ElementOverride;
import net.ilexiconn.nationsgui.forge.client.gui.override.HotbarOverride;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import org.lwjgl.opengl.GL11;
import sun.misc.BASE64Decoder;

public class SpecVersusOverride extends Gui implements ElementOverride {

   public static CFontRenderer dg25 = ModernGui.getCustomFont("minecraftDungeons", Integer.valueOf(25));
   public static CFontRenderer georamaBold25 = ModernGui.getCustomFont("georamaBold", Integer.valueOf(25));


   public static String secondsToTimer(int totalSeconds) {
      int minutes = totalSeconds / 60;
      int seconds = totalSeconds % 60;
      return String.format("%02d:%02d", new Object[]{Integer.valueOf(minutes), Integer.valueOf(seconds)});
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

   public ElementType getType() {
      return ElementType.HOTBAR;
   }

   public ElementType[] getSubTypes() {
      return new ElementType[0];
   }

   public void renderOverride(Minecraft client, ScaledResolution resolution, float partialTicks) {
      if(System.currentTimeMillis() - ClientEventHandler.lastPlayerDispayTAB.longValue() >= 50L) {
         if(ClientProxy.currentServerName.equals("mmr")) {
            if(!ClientData.versusOverlayData.isEmpty()) {
               ClientEventHandler.STYLE.bindTexture("overlay_mmr");
               ModernGui.drawScaledCustomSizeModalRect((float)(resolution.func_78326_a() / 2) - 118.6F, 5.0F, 0.0F, 331.0F, 593, 171, 237, 68, 1920.0F, 1033.0F, true);
               ModernGui.drawScaledStringCustomFont(secondsToTimer(((Double)ClientData.versusOverlayData.get("timer")).intValue()), (float)(resolution.func_78326_a() / 2), 5.5F, 15463162, 0.6F, "center", false, "minecraftDungeons", 21);
               ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("overlay.versus.stage." + ClientData.versusOverlayData.get("stage")), (float)(resolution.func_78326_a() / 2), 63.0F, 855319, 0.5F, "center", false, "minecraftDungeons", 21);
               String teamName = ((String)((LinkedTreeMap)ClientData.versusOverlayData.get("team1")).get("name")).replaceAll("Empire", "E.");
               ModernGui.drawScaledStringCustomFont(teamName.length() < 11?teamName.toUpperCase():teamName.substring(0, 9).toUpperCase() + "..", (float)(resolution.func_78326_a() / 2) - 118.6F + 8.0F, 25.0F, 855319, 0.7F, "left", false, "minecraftDungeons", 21);
               ModernGui.drawScaledStringCustomFont(((String)((LinkedTreeMap)ClientData.versusOverlayData.get("team1")).get("color")).toUpperCase(), (float)(resolution.func_78326_a() / 2) - 118.6F + 8.0F, 37.0F, 855319, 0.5F, "left", false, "georamaBold", 21);
               int team1Score = ((Double)((LinkedTreeMap)ClientData.versusOverlayData.get("team1")).get("score")).intValue();

               int team2Score;
               for(team2Score = 0; team2Score <= 1; ++team2Score) {
                  ClientEventHandler.STYLE.bindTexture("overlay_mmr");
                  ModernGui.drawScaledCustomSizeModalRect((float)(resolution.func_78326_a() / 2) - 118.6F + 8.0F + (float)(team2Score * 10), 45.0F, team1Score > team2Score?612.0F:641.0F, 370.0F, 21, 21, 8, 8, 1920.0F, 1033.0F, true);
               }

               teamName = ((String)((LinkedTreeMap)ClientData.versusOverlayData.get("team2")).get("name")).replaceAll("Empire", "E.");
               ModernGui.drawScaledStringCustomFont(teamName.length() < 11?teamName.toUpperCase():teamName.substring(0, 9).toUpperCase(), (float)(resolution.func_78326_a() / 2) + 118.6F - 8.0F, 25.0F, 855319, 0.7F, "right", false, "minecraftDungeons", 21);
               ModernGui.drawScaledStringCustomFont(((String)((LinkedTreeMap)ClientData.versusOverlayData.get("team2")).get("color")).toUpperCase(), (float)(resolution.func_78326_a() / 2) + 118.6F - 8.0F, 37.0F, 855319, 0.5F, "right", false, "georamaBold", 21);
               team2Score = ((Double)((LinkedTreeMap)ClientData.versusOverlayData.get("team2")).get("score")).intValue();

               for(int playersTeam1 = 0; playersTeam1 <= 1; ++playersTeam1) {
                  ClientEventHandler.STYLE.bindTexture("overlay_mmr");
                  ModernGui.drawScaledCustomSizeModalRect((float)(resolution.func_78326_a() / 2) + 118.6F - 8.0F - 8.4F - (float)(playersTeam1 * 10), 45.0F, team2Score > playersTeam1?612.0F:641.0F, 370.0F, 21, 21, 8, 8, 1920.0F, 1033.0F, true);
               }

               if(!((ArrayList)ClientData.versusOverlayData.get("playersInMatch")).contains(Minecraft.func_71410_x().field_71439_g.field_71092_bJ)) {
                  ArrayList var19 = (ArrayList)((LinkedTreeMap)ClientData.versusOverlayData.get("team1")).get("players");
                  float offsetY = (float)(resolution.func_78328_b() / 2) - (float)var19.size() * 40.0F / 2.0F;

                  int playerEffects;
                  for(Iterator playersTeam2 = var19.iterator(); playersTeam2.hasNext(); offsetY += 40.0F) {
                     LinkedTreeMap player = (LinkedTreeMap)playersTeam2.next();
                     ClientEventHandler.STYLE.bindTexture("overlay_mmr");
                     ModernGui.drawScaledCustomSizeModalRect(5.0F, offsetY, 0.0F, ((Boolean)player.get("isDead")).booleanValue()?207.0F:102.0F, 249, 96, 99, 38, 1920.0F, 1033.0F, true);
                     if(!ClientProxy.cacheHeadPlayer.containsKey((String)player.get("name"))) {
                        try {
                           ResourceLocation player1 = AbstractClientPlayer.field_110314_b;
                           player1 = AbstractClientPlayer.func_110311_f((String)player.get("name"));
                           AbstractClientPlayer.func_110304_a(player1, (String)player.get("name"));
                           ClientProxy.cacheHeadPlayer.put((String)player.get("name"), player1);
                        } catch (Exception var18) {
                           System.out.println(var18.getMessage());
                        }
                     } else {
                        Minecraft.func_71410_x().field_71446_o.func_110577_a((ResourceLocation)ClientProxy.cacheHeadPlayer.get((String)player.get("name")));
                        Minecraft.func_71410_x().func_110434_K().func_110577_a((ResourceLocation)ClientProxy.cacheHeadPlayer.get((String)player.get("name")));
                        GUIUtils.drawScaledCustomSizeModalRect(30, (int)offsetY + 27, 8.0F, 16.0F, 8, -8, -16, -16, 64.0F, 64.0F);
                     }

                     ModernGui.drawScaledStringCustomFont((String)player.get("name"), 41.0F, offsetY + 9.0F, 15463162, 0.5F, "left", false, "minecraftDungeons", 21);
                     ModernGui.drawScaledStringCustomFont(String.format("%.0f", new Object[]{(Double)player.get("damage")}) + " d\u00e9g\u00e2ts", 41.0F, offsetY + 18.0F, 15463162, 0.5F, "left", false, "georamaSemiBold", 21);
                     Double var22 = (Double)player.get("health");

                     for(int playerHealth = 0; playerHealth < 10; ++playerHealth) {
                        ClientEventHandler.STYLE.bindTexture("overlay_mmr");
                        ModernGui.drawScaledCustomSizeModalRect((float)(41 + playerHealth * 6), offsetY + 26.0F, var22.doubleValue() >= (double)(playerHealth * 2)?329.0F:313.0F, 11.0F, 13, 11, 5, 4, 1920.0F, 1033.0F, true);
                     }

                     ArrayList var24 = (ArrayList)player.get("effects");

                     for(playerEffects = 0; playerEffects < var24.size(); ++playerEffects) {
                        Potion i = Potion.field_76425_a[((Double)var24.get(playerEffects)).intValue()];
                        if(i != null) {
                           ClientEventHandler.STYLE.bindTexture("overlay_mmr");
                           ModernGui.drawScaledCustomSizeModalRect((float)(105 + playerEffects / 2 * 14), offsetY + 5.5F + (float)(playerEffects % 2 * 14), 252.0F, 114.0F, 33, 34, 13, 13, 1920.0F, 1033.0F, true);
                           client.func_110434_K().func_110577_a(HotbarOverride.icons);
                           int potion = i.func_76392_e();
                           GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                           ModernGui.drawScaledCustomSizeModalRect(107.0F + (float)(playerEffects / 2 * 14), offsetY + 8.0F + (float)(playerEffects % 2 * 14), (float)(potion % 8 * 18), (float)(198 + potion / 8 * 18), 18, 18, 8, 8, 256.0F, 256.0F, true);
                        }
                     }
                  }

                  ArrayList var20 = (ArrayList)((LinkedTreeMap)ClientData.versusOverlayData.get("team2")).get("players");
                  offsetY = (float)(resolution.func_78328_b() / 2) - (float)var20.size() * 40.0F / 2.0F;

                  for(Iterator var21 = var20.iterator(); var21.hasNext(); offsetY += 40.0F) {
                     LinkedTreeMap var23 = (LinkedTreeMap)var21.next();
                     ClientEventHandler.STYLE.bindTexture("overlay_mmr");
                     ModernGui.drawScaledCustomSizeModalRect((float)(resolution.func_78326_a() - 5) - 99.6F, offsetY, 0.0F, ((Boolean)var23.get("isDead")).booleanValue()?750.0F:645.0F, 249, 96, 99, 38, 1920.0F, 1033.0F, true);
                     if(!ClientProxy.cacheHeadPlayer.containsKey((String)var23.get("name"))) {
                        try {
                           ResourceLocation var25 = AbstractClientPlayer.field_110314_b;
                           var25 = AbstractClientPlayer.func_110311_f((String)var23.get("name"));
                           AbstractClientPlayer.func_110304_a(var25, (String)var23.get("name"));
                           ClientProxy.cacheHeadPlayer.put((String)var23.get("name"), var25);
                        } catch (Exception var17) {
                           System.out.println(var17.getMessage());
                        }
                     } else {
                        Minecraft.func_71410_x().field_71446_o.func_110577_a((ResourceLocation)ClientProxy.cacheHeadPlayer.get((String)var23.get("name")));
                        Minecraft.func_71410_x().func_110434_K().func_110577_a((ResourceLocation)ClientProxy.cacheHeadPlayer.get((String)var23.get("name")));
                        GUIUtils.drawScaledCustomSizeModalRect(resolution.func_78326_a() - 15, (int)offsetY + 27, 8.0F, 16.0F, 8, -8, -16, -16, 64.0F, 64.0F);
                     }

                     ModernGui.drawScaledStringCustomFont((String)var23.get("name"), (float)(resolution.func_78326_a() - 41), offsetY + 9.0F, 15463162, 0.5F, "right", false, "minecraftDungeons", 21);
                     ModernGui.drawScaledStringCustomFont(String.format("%.0f", new Object[]{(Double)var23.get("damage")}) + " d\u00e9g\u00e2ts", (float)(resolution.func_78326_a() - 41), offsetY + 18.0F, 15463162, 0.5F, "right", false, "georamaSemiBold", 21);
                     Double var26 = (Double)var23.get("health");

                     for(playerEffects = 0; playerEffects < 10; ++playerEffects) {
                        ClientEventHandler.STYLE.bindTexture("overlay_mmr");
                        ModernGui.drawScaledCustomSizeModalRect((float)(resolution.func_78326_a() - 46 - playerEffects * 6), offsetY + 26.0F, var26.doubleValue() >= (double)(playerEffects * 2)?329.0F:313.0F, 11.0F, 13, 11, 5, 4, 1920.0F, 1033.0F, true);
                     }

                     ArrayList var27 = (ArrayList)var23.get("effects");

                     for(int var28 = 0; var28 < var27.size(); ++var28) {
                        Potion var29 = Potion.field_76425_a[((Double)var27.get(var28)).intValue()];
                        if(var29 != null) {
                           ClientEventHandler.STYLE.bindTexture("overlay_mmr");
                           ModernGui.drawScaledCustomSizeModalRect((float)(resolution.func_78326_a() - 118 - var28 / 2 * 14), offsetY + 5.5F + (float)(var28 % 2 * 14), 252.0F, 114.0F, 33, 34, 13, 13, 1920.0F, 1033.0F, true);
                           client.func_110434_K().func_110577_a(HotbarOverride.icons);
                           int iconIndex = var29.func_76392_e();
                           GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                           ModernGui.drawScaledCustomSizeModalRect((float)resolution.func_78326_a() - 116.0F - (float)(var28 / 2 * 14), offsetY + 8.0F + (float)(var28 % 2 * 14), (float)(iconIndex % 8 * 18), (float)(198 + iconIndex / 8 * 18), 18, 18, 8, 8, 256.0F, 256.0F, true);
                        }
                     }
                  }
               }
            } else {
               ClientEventHandler.STYLE.bindTexture("overlay_mmr");
               ModernGui.drawScaledCustomSizeModalRect((float)(resolution.func_78326_a() / 2) - 48.4F, 15.0F, 0.0F, 903.0F, 242, 119, 96, 47, 1920.0F, 1033.0F, true);
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
