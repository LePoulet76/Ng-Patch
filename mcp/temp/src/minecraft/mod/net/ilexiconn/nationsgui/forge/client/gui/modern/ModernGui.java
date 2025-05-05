package net.ilexiconn.nationsgui.forge.client.gui.modern;

import fr.nationsglory.ngupgrades.common.CommonEventHandler;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import net.halalaboos.cfont.CFontRenderer;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.SliderHelpGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui$1;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernResourceLocation;
import net.ilexiconn.nationsgui.forge.client.gui.override.GenericOverride;
import net.ilexiconn.nationsgui.forge.client.render.texture.DownloadableTexture;
import net.ilexiconn.nationsgui.forge.client.util.FontManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import sun.misc.BASE64Decoder;

public class ModernGui extends Gui {

   public static final ResourceLocation INFO_ICON = new ModernResourceLocation("ic_info");
   public static final ResourceLocation CART = new ModernResourceLocation("ic_cart_white");
   public static final ResourceLocation NO_MUSIC = new ModernResourceLocation("ic_mute");
   public static final ResourceLocation MUSIC = new ModernResourceLocation("ic_music_white");
   public static HashMap<Long, String> cachedChronos = new HashMap();
   private static HashMap<Integer, CFontRenderer> georamaRegular;
   private static HashMap<Integer, CFontRenderer> georamaMedium;
   private static HashMap<Integer, CFontRenderer> georamaSemiBold;
   private static HashMap<Integer, CFontRenderer> georamaExtraBold;
   private static HashMap<Integer, CFontRenderer> georamaBold;
   private static HashMap<Integer, CFontRenderer> minecraftDungeons;
   public static String cachedOverlayMainTexture = "overlay_main";
   private static long lastResetCachedOverlayMainTexture = 0L;
   public static LinkedHashMap<String, Integer> ngColors = new ModernGui$1();
   public static RenderItem itemRenderer = new RenderItem();
   public static String hoveredCommonAction = "";


   public static CFontRenderer getCustomFont(String fontName, Integer fontSize) {
      try {
         if(ModernGui.class.getDeclaredField(fontName).get(ModernGui.class) != null && ((HashMap)ModernGui.class.getDeclaredField(fontName).get(ModernGui.class)).containsKey(fontSize)) {
            return (CFontRenderer)((HashMap)ModernGui.class.getDeclaredField(fontName).get(ModernGui.class)).get(fontSize);
         }

         CFontRenderer e = null;
         if(fontName.equalsIgnoreCase("georamabold")) {
            e = FontManager.createFont("nationsgui", fontName + ".otf");
         } else {
            e = FontManager.createFont("nationsgui", fontName + ".ttf");
         }

         e.setFontSize((float)fontSize.intValue());
         e.setAntiAlias(true);
         HashMap hashMap = new HashMap();
         if(ModernGui.class.getDeclaredField(fontName).get(ModernGui.class) != null) {
            hashMap = (HashMap)ModernGui.class.getDeclaredField(fontName).get(ModernGui.class);
         }

         hashMap.put(fontSize, e);
         ModernGui.class.getDeclaredField(fontName).set(ModernGui.class, hashMap);
         return e;
      } catch (NoSuchFieldException var4) {
         var4.printStackTrace();
      } catch (IllegalAccessException var5) {
         var5.printStackTrace();
      }

      return null;
   }

   public static void drawSectionStringCustomFont(String text, float x, float y, int color, float scale, String align, boolean shadow, String customFont, int fontSize, int interLign, int maxWidth) {
      CFontRenderer cFontRenderer = getCustomFont(customFont, Integer.valueOf(fontSize));
      String[] words = text.split(" ");
      String line = "";
      int lineNumber = 0;
      int offsetOnLine = 0;
      String[] var16 = words;
      int var17 = words.length;

      for(int var18 = 0; var18 < var17; ++var18) {
         String word = var16[var18];
         if(word.equals("##")) {
            drawScaledStringCustomFont(line, x + (float)offsetOnLine, y + (float)(lineNumber * interLign), color, scale, align, shadow, customFont, fontSize);
            lineNumber += 2;
            line = "";
            offsetOnLine = 0;
         } else if(word.equals("#")) {
            drawScaledStringCustomFont(line, x + (float)offsetOnLine, y + (float)(lineNumber * interLign), color, scale, align, shadow, customFont, fontSize);
            ++lineNumber;
            line = "";
            offsetOnLine = 0;
         } else if(cFontRenderer.getStringWidth(line + word) + (float)offsetOnLine / scale > (float)maxWidth) {
            drawScaledStringCustomFont(line, x + (float)offsetOnLine, y + (float)(lineNumber * interLign), color, scale, align, shadow, customFont, fontSize);
            ++lineNumber;
            offsetOnLine = 0;
            line = word;
            if(word.matches("#.*#")) {
               drawScaledStringCustomFont(word.replaceAll("#", ""), x + (float)offsetOnLine, y + (float)(lineNumber * interLign), 7239406, scale, align, shadow, customFont, fontSize);
               offsetOnLine = (int)((float)offsetOnLine + cFontRenderer.getStringWidth(word.replaceAll("#", "")) * scale);
               line = "";
            }
         } else if(word.matches("#.*#")) {
            drawScaledStringCustomFont(line, x + (float)offsetOnLine, y + (float)(lineNumber * interLign), color, scale, align, shadow, customFont, fontSize);
            drawScaledStringCustomFont(" " + word.replaceAll("#", ""), x + (float)offsetOnLine + (float)((int)(cFontRenderer.getStringWidth(line) * scale)), y + (float)(lineNumber * interLign), 7239406, scale, align, shadow, customFont, fontSize);
            offsetOnLine = (int)((float)offsetOnLine + cFontRenderer.getStringWidth(line + " " + word.replaceAll("#", "")) * scale);
            line = "";
         } else {
            line = line + " " + word;
            if(offsetOnLine == 0) {
               line = line.trim();
            }
         }
      }

      drawScaledStringCustomFont(line, x + (float)offsetOnLine, y + (float)(lineNumber * interLign), color, scale, align, shadow, customFont, fontSize);
   }

   public static void drawScaledStringCustomFont(String text, float x, float y, int color, float scale, String align, boolean shadow, String customFont, int fontSize) {
      CFontRenderer cFontRenderer = getCustomFont(customFont, Integer.valueOf(fontSize));
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(2896);
      GL11.glEnable(3553);
      GL11.glScalef(scale, scale, scale);
      float newX;
      if(cFontRenderer != null) {
         newX = x;
         if(align.equals("center")) {
            newX = x - cFontRenderer.getStringWidth(text) * scale / 2.0F;
         } else if(align.equals("right")) {
            newX = x - cFontRenderer.getStringWidth(text) * scale;
         }

         if(shadow) {
            cFontRenderer.drawString(text.replaceAll("\u00a7.{1}", ""), (double)((int)(newX / scale)), (double)((int)((y + 1.0F) / scale)), 1908021, false);
         }

         cFontRenderer.drawString(text, (double)((int)(newX / scale)), (double)((int)(y / scale)), color, false);
      } else {
         newX = x;
         if(align.equals("center")) {
            newX = x - (float)Minecraft.func_71410_x().field_71466_p.func_78256_a(text) * scale / 2.0F;
         } else if(align.equals("right")) {
            newX = x - (float)Minecraft.func_71410_x().field_71466_p.func_78256_a(text) * scale;
         }

         if(shadow) {
            Minecraft.func_71410_x().field_71466_p.func_85187_a(text, (int)(newX / scale), (int)((y + 1.0F) / scale), (color & 16579836) >> 2 | color & -16777216, false);
         }

         Minecraft.func_71410_x().field_71466_p.func_85187_a(text, (int)(newX / scale), (int)(y / scale), color, false);
      }

      GL11.glPopMatrix();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public static void drawModalRectWithCustomSizedTexture(float x, float y, int u, int v, int width, int height, float textureWidth, float textureHeight, boolean filter) {
      textureWidth = 1.0F / textureWidth;
      textureHeight = 1.0F / textureHeight;
      Tessellator tessellator = Tessellator.field_78398_a;
      tessellator.func_78382_b();
      tessellator.func_78374_a((double)x, (double)(y + (float)height), 0.0D, (double)((float)u * textureWidth), (double)((float)(v + height) * textureHeight));
      tessellator.func_78374_a((double)(x + (float)width), (double)(y + (float)height), 0.0D, (double)((float)(u + width) * textureWidth), (double)((float)(v + height) * textureHeight));
      tessellator.func_78374_a((double)(x + (float)width), (double)y, 0.0D, (double)((float)(u + width) * textureWidth), (double)((float)v * textureHeight));
      tessellator.func_78374_a((double)x, (double)y, 0.0D, (double)((float)u * textureWidth), (double)((float)v * textureHeight));
      tessellator.func_78381_a();
   }

   public static void drawModalRectWithCustomSizedTextureWithTransparency(float x, float y, int u, int v, int width, int height, float textureWidth, float textureHeight, boolean filter) {
      GL11.glEnable(3042);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      GL11.glBlendFunc(770, 771);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glDisable(3008);
      drawModalRectWithCustomSizedTexture(x, y, u, v, width, height, textureWidth, textureHeight, filter);
      GL11.glDisable(3042);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glEnable(3008);
   }

   public static void drawExtendedCircle(float x, float y, float width, float height) {
      GL11.glDisable(3553);
      GL11.glEnable(3042);
      float radius = width / 2.0F;
      drawFilledCircle(x + width / 2.0F, y + radius, radius, 180, 180);
      Tessellator tessellator = Tessellator.field_78398_a;
      tessellator.func_78382_b();
      tessellator.func_78377_a((double)x, (double)(y + radius), 0.0D);
      tessellator.func_78377_a((double)(x + width), (double)(y + radius), 0.0D);
      tessellator.func_78377_a((double)(x + width), (double)(y + height - radius), 0.0D);
      tessellator.func_78377_a((double)x, (double)(y + height - radius), 0.0D);
      tessellator.func_78381_a();
      drawFilledCircle(x + width / 2.0F, y + height - radius, radius, 0, 180);
      GL11.glDisable(3042);
      GL11.glEnable(3553);
   }

   public static void drawFilledCircle(float x, float y, float radius, int startAngle, int angle) {
      GL11.glShadeModel(7425);
      GL11.glDisable(2884);
      GL11.glBegin(6);
      float y1 = y;
      float x1 = x;

      for(int i = 0; i <= angle; ++i) {
         float degInRad = (float)(i + startAngle) * 3.1415927F / 180.0F;
         float x2 = x + (float)Math.cos((double)degInRad) * radius;
         float y2 = y + (float)Math.sin((double)degInRad) * radius;
         GL11.glVertex2f(x, y);
         GL11.glVertex2f(x1, y1);
         GL11.glVertex2f(x2, y2);
         y1 = y2;
         x1 = x2;
      }

      GL11.glEnd();
      GL11.glEnable('\u803a');
   }

   public static void drawRoundedRectangle(float xPosition, float yPosition, float zLevel, float width, float height) {
      Tessellator tessellator = Tessellator.field_78398_a;
      GL11.glDisable(3553);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      float radius = 2.0F;
      drawFilledCircle(xPosition + radius, yPosition + radius, radius, 180, 90);
      drawFilledCircle(xPosition + width - radius, yPosition + radius, radius, 270, 90);
      drawFilledCircle(xPosition + width - radius, yPosition - radius + height, radius, 0, 90);
      drawFilledCircle(xPosition + radius, yPosition - radius + height, radius, 90, 90);
      tessellator.func_78371_b(7);
      tessellator.func_78377_a((double)(xPosition + radius), (double)yPosition, (double)zLevel);
      tessellator.func_78377_a((double)(xPosition + radius), (double)(yPosition + height), (double)zLevel);
      tessellator.func_78377_a((double)(xPosition + width - radius), (double)(yPosition + height), (double)zLevel);
      tessellator.func_78377_a((double)(xPosition + width - radius), (double)yPosition, (double)zLevel);
      tessellator.func_78381_a();
      tessellator.func_78371_b(7);
      tessellator.func_78377_a((double)xPosition, (double)(yPosition + radius), (double)zLevel);
      tessellator.func_78377_a((double)xPosition, (double)(yPosition + height - radius), (double)zLevel);
      tessellator.func_78377_a((double)(xPosition + radius), (double)(yPosition + height - radius), (double)zLevel);
      tessellator.func_78377_a((double)(xPosition + radius), (double)(yPosition + radius), (double)zLevel);
      tessellator.func_78381_a();
      tessellator.func_78371_b(7);
      tessellator.func_78377_a((double)(xPosition + width - radius), (double)(yPosition + radius), (double)zLevel);
      tessellator.func_78377_a((double)(xPosition + width - radius), (double)(yPosition + height - radius), (double)zLevel);
      tessellator.func_78377_a((double)(xPosition + width), (double)(yPosition + height - radius), (double)zLevel);
      tessellator.func_78377_a((double)(xPosition + width), (double)(yPosition + radius), (double)zLevel);
      tessellator.func_78381_a();
      GL11.glDisable(3042);
      GL11.glEnable(3553);
   }

   public static void drawRectangle(float xPosition, float yPosition, float zLevel, float width, float height) {
      Tessellator tessellator = Tessellator.field_78398_a;
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(3553);
      tessellator.func_78371_b(7);
      tessellator.func_78377_a((double)xPosition, (double)yPosition, (double)zLevel);
      tessellator.func_78377_a((double)xPosition, (double)(yPosition + height), (double)zLevel);
      tessellator.func_78377_a((double)(xPosition + width), (double)(yPosition + height), (double)zLevel);
      tessellator.func_78377_a((double)(xPosition + width), (double)yPosition, (double)zLevel);
      tessellator.func_78381_a();
      GL11.glDisable(3042);
      GL11.glEnable(3553);
   }

   public static void drawScaledCustomSizeModalRect(float x, float y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight, boolean transparency) {
      if(transparency) {
         GL11.glDisable(2929);
         GL11.glDepthMask(false);
         GL11.glBlendFunc(770, 771);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glDisable(3008);
      }

      GL11.glEnable(3042);
      float f = 1.0F / tileWidth;
      float f1 = 1.0F / tileHeight;
      Tessellator tessellator = Tessellator.field_78398_a;
      tessellator.func_78371_b(7);
      tessellator.func_78374_a((double)x, (double)(y + (float)height), 0.0D, (double)(u * f), (double)((v + (float)vHeight) * f1));
      tessellator.func_78374_a((double)(x + (float)width), (double)(y + (float)height), 0.0D, (double)((u + (float)uWidth) * f), (double)((v + (float)vHeight) * f1));
      tessellator.func_78374_a((double)(x + (float)width), (double)y, 0.0D, (double)((u + (float)uWidth) * f), (double)(v * f1));
      tessellator.func_78374_a((double)x, (double)y, 0.0D, (double)(u * f), (double)(v * f1));
      tessellator.func_78381_a();
      GL11.glDisable(3042);
      if(transparency) {
         GL11.glEnable(2929);
         GL11.glDepthMask(true);
         GL11.glEnable(3008);
         GL11.glBlendFunc(770, 771);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      }

   }

   public static void drawScaledString(String text, int x, int y, int color, float scale, boolean centered, boolean shadow) {
      GL11.glPushMatrix();
      GL11.glScalef(scale, scale, scale);
      float newX = (float)x;
      if(centered) {
         newX = (float)x - (float)Minecraft.func_71410_x().field_71466_p.func_78256_a(text) * scale / 2.0F;
      }

      if(shadow) {
         Minecraft.func_71410_x().field_71466_p.func_85187_a(text, (int)(newX / scale), (int)((float)(y + 1) / scale), (color & 16579836) >> 2 | color & -16777216, false);
      }

      Minecraft.func_71410_x().field_71466_p.func_85187_a(text, (int)(newX / scale), (int)((float)y / scale), color, false);
      GL11.glPopMatrix();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public static void drawNGBlackSquare(int posX, int posY, int width, int height) {
      Gui.func_73734_a(posX, posY, posX + width, posY + height, -16777216);
      Gui.func_73734_a(posX + 1, posY + 1, posX + width - 1, posY + height - 1, -15132391);
   }

   public static void drawScaledModalRectWithCustomSizedRemoteTexture(int x, int y, int u, int v, int uWidth, int vHeight, int width, int height, float textureWidth, float textureHeight, boolean filter, String url) {
      DownloadableTexture downloadableTexture = ClientProxy.getRemoteResource(url);
      if(downloadableTexture != null && downloadableTexture.isTextureUploaded()) {
         Minecraft.func_71410_x().func_110434_K().func_110577_a(ClientProxy.getLocationRemoteResource(url));
         drawScaledCustomSizeModalRect((float)x, (float)y, (float)u, (float)v, uWidth, vHeight, width, height, textureWidth, textureHeight, filter);
      }

   }

   public static void bindRemoteTexture(String url) {
      DownloadableTexture downloadableTexture = ClientProxy.getRemoteResource(url);
      if(downloadableTexture != null && downloadableTexture.isTextureUploaded()) {
         Minecraft.func_71410_x().func_110434_K().func_110577_a(ClientProxy.getLocationRemoteResource(url));
      } else {
         GL11.glBindTexture(3553, 0);
      }

   }

   public static boolean isRemoteTextureLoaded(String url) {
      DownloadableTexture downloadableTexture = ClientProxy.getRemoteResource(url);
      return downloadableTexture != null && downloadableTexture.isTextureUploaded();
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

   public static String formatDelayTime(Long time) {
      long now = System.currentTimeMillis();
      long diff = now - time.longValue();
      String date = "";
      long days = diff / 86400000L;
      long hours = 0L;
      long minutes = 0L;
      long seconds = 0L;
      if(days == 0L) {
         hours = diff / 3600000L;
         if(hours == 0L) {
            minutes = diff / 60000L;
            if(minutes == 0L) {
               seconds = diff / 1000L;
               date = date + " " + seconds + I18n.func_135053_a("faction.common.seconds.short");
            } else {
               date = date + " " + minutes + I18n.func_135053_a("faction.common.minutes.short");
            }
         } else {
            date = date + " " + hours + I18n.func_135053_a("faction.common.hours.short");
         }
      } else {
         date = date + " " + days + I18n.func_135053_a("faction.common.days.short");
      }

      return date.trim();
   }

   public static String formatMillisToDaysHoursMinutesSeconds(Long time) {
      String date = "";
      long days = time.longValue() / 86400000L;
      long hours = 0L;
      long minutes = 0L;
      long seconds = 0L;
      if(days == 0L) {
         hours = time.longValue() / 3600000L;
         if(hours == 0L) {
            minutes = time.longValue() / 60000L;
            if(minutes == 0L) {
               seconds = time.longValue() / 1000L;
               date = date + " " + seconds + I18n.func_135053_a("faction.common.seconds.short");
            } else {
               date = date + " " + minutes + I18n.func_135053_a("faction.common.minutes.short");
            }
         } else {
            date = date + " " + hours + I18n.func_135053_a("faction.common.hours.short");
         }
      } else {
         date = date + " " + days + I18n.func_135053_a("faction.common.days.short");
      }

      return date.trim();
   }

   public static String formatMillisToChrono(Long timeInMillis, boolean millis) {
      StringBuilder formattedTime = new StringBuilder();
      long hours = timeInMillis.longValue() / 3600000L;
      timeInMillis = Long.valueOf(timeInMillis.longValue() % 3600000L);
      long minutes = timeInMillis.longValue() / 60000L;
      timeInMillis = Long.valueOf(timeInMillis.longValue() % 60000L);
      long seconds = timeInMillis.longValue() / 1000L;
      timeInMillis = Long.valueOf(timeInMillis.longValue() % 1000L);
      long milliseconds = timeInMillis.longValue();
      formattedTime.append(String.format("%02d", new Object[]{Long.valueOf(hours)})).append(":").append(String.format("%02d", new Object[]{Long.valueOf(minutes)})).append(":").append(String.format("%02d", new Object[]{Long.valueOf(seconds)}));
      if(millis) {
         formattedTime.append(":").append(String.format("%03d", new Object[]{Long.valueOf(milliseconds)}));
      }

      return formattedTime.toString();
   }

   public static String formatDuration(Long duration) {
      String date = "";
      long days = duration.longValue() / 86400L;
      long hours = 0L;
      long minutes = 0L;
      long seconds = 0L;
      if(days == 0L) {
         hours = duration.longValue() / 3600L;
         date = date + " " + hours + " " + I18n.func_135053_a("faction.common.hours");
      } else {
         date = date + " " + days + " " + I18n.func_135053_a("faction.common.days");
      }

      return date;
   }

   public static String getRankColor(String rank) {
      String res = "\u00a7f";
      String var2 = rank.toLowerCase();
      byte var3 = -1;
      switch(var2.hashCode()) {
      case -2016291104:
         if(var2.equals("moderateur")) {
            var3 = 3;
         }
         break;
      case -1996099632:
         if(var2.equals("fondateur")) {
            var3 = 9;
         }
         break;
      case -1973319297:
         if(var2.equals("respadmin")) {
            var3 = 8;
         }
         break;
      case -332106840:
         if(var2.equals("supermodo")) {
            var3 = 6;
         }
         break;
      case -318452137:
         if(var2.equals("premium")) {
            var3 = 2;
         }
         break;
      case 55934456:
         if(var2.equals("legende")) {
            var3 = 1;
         }
         break;
      case 92668751:
         if(var2.equals("admin")) {
            var3 = 7;
         }
         break;
      case 99168185:
         if(var2.equals("heros")) {
            var3 = 0;
         }
         break;
      case 1670429593:
         if(var2.equals("moderateur_plus")) {
            var3 = 4;
         }
         break;
      case 1670541969:
         if(var2.equals("moderateur_test")) {
            var3 = 5;
         }
         break;
      case 1854118753:
         if(var2.equals("co-fonda")) {
            var3 = 10;
         }
      }

      switch(var3) {
      case 0:
         res = "\u00a77";
         break;
      case 1:
         res = "\u00a73";
         break;
      case 2:
         res = "\u00a76";
         break;
      case 3:
      case 4:
      case 5:
         res = "\u00a7a";
         break;
      case 6:
         res = "\u00a79";
         break;
      case 7:
         res = "\u00a7c";
         break;
      case 8:
         res = "\u00a74";
         break;
      case 9:
      case 10:
         res = "\u00a7b";
      }

      return res;
   }

   public static void glColorHex(int hex, float coeffBrightness) {
      float red = (float)(hex >> 16 & 255) / 255.0F;
      float blue = (float)(hex >> 8 & 255) / 255.0F;
      float green = (float)(hex & 255) / 255.0F;
      float alpha = (float)(hex >> 24 & 255) / 255.0F;
      GL11.glColor4f(red * coeffBrightness, blue * coeffBrightness, green * coeffBrightness, alpha);
   }

   public static void glColorHexFromNgColor(String color, float coeffBrightness) {
      int hex = ngColors.containsKey(color)?((Integer)ngColors.get(color)).intValue():-1;
      float red = (float)(hex >> 16 & 255) / 255.0F;
      float blue = (float)(hex >> 8 & 255) / 255.0F;
      float green = (float)(hex & 255) / 255.0F;
      float alpha = (float)(hex >> 24 & 255) / 255.0F;
      GL11.glColor4f(red * coeffBrightness, blue * coeffBrightness, green * coeffBrightness, alpha);
   }

   public static String formatIntToDevise(int value) {
      String res = "";

      for(int i = (value + "").length() - 1; i >= 0; --i) {
         char c = (value + "").charAt(i);
         if(res.length() != 0 && res.replaceAll("\\.", "").length() % 3 == 0) {
            res = "." + res;
         }

         res = c + res;
      }

      return res;
   }

   public static String chronoTimeToStr(Long chrono, boolean millis) {
      if(chrono != null && chrono.longValue() >= 0L) {
         long hours = TimeUnit.MILLISECONDS.toHours(chrono.longValue());
         chrono = Long.valueOf(chrono.longValue() - TimeUnit.HOURS.toMillis(hours));
         long minutes = TimeUnit.MILLISECONDS.toMinutes(chrono.longValue());
         chrono = Long.valueOf(chrono.longValue() - TimeUnit.MINUTES.toMillis(minutes));
         long seconds = TimeUnit.MILLISECONDS.toSeconds(chrono.longValue());
         chrono = Long.valueOf(chrono.longValue() - TimeUnit.SECONDS.toMillis(seconds));
         long left = chrono.longValue() / 10L;
         String chronoStr = "";
         if(hours > 0L) {
            chronoStr = chronoStr + (hours < 10L?"0" + hours:Long.valueOf(hours));
         }

         chronoStr = chronoStr + (!chronoStr.isEmpty()?":":"") + (minutes < 10L?"0" + minutes:Long.valueOf(minutes));
         chronoStr = chronoStr + (!chronoStr.isEmpty()?":":"") + (seconds < 10L?"0" + seconds:Long.valueOf(seconds));
         if(millis) {
            chronoStr = chronoStr + (!chronoStr.isEmpty()?":":"") + (left < 10L?"0" + left:Long.valueOf(left));
         }

         return chronoStr;
      } else {
         return "00:00";
      }
   }

   public static String getFormattedTimeDiff(long timestamp1, long timestamp2) {
      long diffInMilliseconds = Math.max(0L, timestamp1 - timestamp2);
      long hours = TimeUnit.MILLISECONDS.toHours(diffInMilliseconds);
      long minutes = TimeUnit.MILLISECONDS.toMinutes(diffInMilliseconds) - TimeUnit.HOURS.toMinutes(hours);
      long seconds = TimeUnit.MILLISECONDS.toSeconds(diffInMilliseconds) - TimeUnit.MINUTES.toSeconds(minutes) - TimeUnit.HOURS.toSeconds(hours);
      return String.format("%02d:%02d:%02d", new Object[]{Long.valueOf(hours), Long.valueOf(minutes), Long.valueOf(seconds)});
   }

   public static void drawDefaultBackground(GuiScreen gui, int width, int height, int mouseX, int mouseY) {
      hoveredCommonAction = "";
      String GUIClass = gui.getClass().getSimpleName();
      Gui.func_73734_a(0, 0, width, height, -435023335);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      bindTextureOverlayMain();
      boolean hoveringClose;
      if(ClientData.GUIWithHelp.containsKey(GUIClass) && !((String)ClientData.GUIWithHelp.get(GUIClass)).isEmpty()) {
         bindTextureOverlayMain();
         hoveringClose = mouseX >= width - 82 && mouseX < width - 82 + 54 && mouseY >= 10 && mouseY < 24;
         drawScaledCustomSizeModalRect((float)(width - 82), 10.0F, (float)(1727 * GenericOverride.GUI_SCALE), (float)((hoveringClose?215:153) * GenericOverride.GUI_SCALE), 189 * GenericOverride.GUI_SCALE, 52 * GenericOverride.GUI_SCALE, 54, 14, (float)(1920 * GenericOverride.GUI_SCALE), (float)(1033 * GenericOverride.GUI_SCALE), false);
         drawScaledStringCustomFont(I18n.func_135053_a("gui.common.help"), (float)(width - 82 + 24), 14.0F, hoveringClose?0:16777215, 0.5F, "left", false, "georamaSemiBold", 24);
         if(hoveringClose) {
            hoveredCommonAction = "help";
         }
      }

      bindTextureOverlayMain();
      hoveringClose = mouseX >= width - 25 && mouseX < width - 25 + 14 && mouseY >= 10 && mouseY < 24;
      drawScaledCustomSizeModalRect((float)(width - 25), 10.0F, (float)(1658 * GenericOverride.GUI_SCALE), (float)((hoveringClose?215:153) * GenericOverride.GUI_SCALE), 52 * GenericOverride.GUI_SCALE, 52 * GenericOverride.GUI_SCALE, 14, 14, (float)(1920 * GenericOverride.GUI_SCALE), (float)(1033 * GenericOverride.GUI_SCALE), false);
      if(hoveringClose) {
         hoveredCommonAction = "close";
      }

   }

   public static void mouseClickedCommon(GuiScreen gui, int mouseX, int mouseY, int mouseButton) {
      if(mouseButton == 0) {
         if(hoveredCommonAction.equals("close")) {
            Minecraft.func_71410_x().func_71373_a((GuiScreen)null);
         } else if(hoveredCommonAction.equals("help")) {
            Minecraft.func_71410_x().func_71373_a(new SliderHelpGui((String)ClientData.GUIWithHelp.get(gui.getClass().getSimpleName()), gui));
         }
      }

   }

   public static void bindTextureOverlayMain() {
      if(System.currentTimeMillis() - lastResetCachedOverlayMainTexture > 1000L) {
         if(CommonEventHandler.isWearingFullSpartanWhiteArmor(Minecraft.func_71410_x().field_71439_g)) {
            cachedOverlayMainTexture = "overlay_main_spartan_white";
         } else {
            cachedOverlayMainTexture = "overlay_main";
         }

         lastResetCachedOverlayMainTexture = System.currentTimeMillis();
      }

      ClientEventHandler.STYLE.bindTexture(cachedOverlayMainTexture);
   }

}
