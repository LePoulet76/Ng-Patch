/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  fr.nationsglory.ngupgrades.common.CommonEventHandler
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import sun.misc.BASE64Decoder;

public class ModernGui
extends Gui {
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
    public static String cachedOverlayMainTexture;
    private static long lastResetCachedOverlayMainTexture;
    public static LinkedHashMap<String, Integer> ngColors;
    public static RenderItem itemRenderer;
    public static String hoveredCommonAction;

    public static CFontRenderer getCustomFont(String fontName, Integer fontSize) {
        try {
            if (ModernGui.class.getDeclaredField(fontName).get(ModernGui.class) == null || !((HashMap)ModernGui.class.getDeclaredField(fontName).get(ModernGui.class)).containsKey(fontSize)) {
                CFontRenderer cFontRenderer = null;
                cFontRenderer = fontName.equalsIgnoreCase("georamabold") ? FontManager.createFont("nationsgui", fontName + ".otf") : FontManager.createFont("nationsgui", fontName + ".ttf");
                cFontRenderer.setFontSize(fontSize.intValue());
                cFontRenderer.setAntiAlias(true);
                HashMap hashMap = new HashMap();
                if (ModernGui.class.getDeclaredField(fontName).get(ModernGui.class) != null) {
                    hashMap = (HashMap)ModernGui.class.getDeclaredField(fontName).get(ModernGui.class);
                }
                hashMap.put(fontSize, cFontRenderer);
                ModernGui.class.getDeclaredField(fontName).set(ModernGui.class, hashMap);
                return cFontRenderer;
            }
            return (CFontRenderer)((HashMap)ModernGui.class.getDeclaredField(fontName).get(ModernGui.class)).get(fontSize);
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void drawSectionStringCustomFont(String text, float x, float y, int color, float scale, String align, boolean shadow, String customFont, int fontSize, int interLign, int maxWidth) {
        CFontRenderer cFontRenderer = ModernGui.getCustomFont(customFont, fontSize);
        String[] words = text.split(" ");
        String line = "";
        int lineNumber = 0;
        int offsetOnLine = 0;
        for (String word : words) {
            if (word.equals("##")) {
                ModernGui.drawScaledStringCustomFont(line, x + (float)offsetOnLine, y + (float)(lineNumber * interLign), color, scale, align, shadow, customFont, fontSize);
                lineNumber += 2;
                line = "";
                offsetOnLine = 0;
                continue;
            }
            if (word.equals("#")) {
                ModernGui.drawScaledStringCustomFont(line, x + (float)offsetOnLine, y + (float)(lineNumber * interLign), color, scale, align, shadow, customFont, fontSize);
                ++lineNumber;
                line = "";
                offsetOnLine = 0;
                continue;
            }
            StringBuilder stringBuilder = new StringBuilder();
            if (cFontRenderer.getStringWidth(stringBuilder.append(line).append(word).toString()) + (float)offsetOnLine / scale > (float)maxWidth) {
                ModernGui.drawScaledStringCustomFont(line, x + (float)offsetOnLine, y + (float)(lineNumber * interLign), color, scale, align, shadow, customFont, fontSize);
                ++lineNumber;
                offsetOnLine = 0;
                line = word;
                if (!word.matches("#.*#")) continue;
                ModernGui.drawScaledStringCustomFont(word.replaceAll("#", ""), x + (float)offsetOnLine, y + (float)(lineNumber * interLign), 0x6E76EE, scale, align, shadow, customFont, fontSize);
                offsetOnLine = (int)((float)offsetOnLine + cFontRenderer.getStringWidth(word.replaceAll("#", "")) * scale);
                line = "";
                continue;
            }
            if (word.matches("#.*#")) {
                ModernGui.drawScaledStringCustomFont(line, x + (float)offsetOnLine, y + (float)(lineNumber * interLign), color, scale, align, shadow, customFont, fontSize);
                ModernGui.drawScaledStringCustomFont(" " + word.replaceAll("#", ""), x + (float)offsetOnLine + (float)((int)(cFontRenderer.getStringWidth(line) * scale)), y + (float)(lineNumber * interLign), 0x6E76EE, scale, align, shadow, customFont, fontSize);
                offsetOnLine = (int)((float)offsetOnLine + cFontRenderer.getStringWidth(line + " " + word.replaceAll("#", "")) * scale);
                line = "";
                continue;
            }
            line = line + " " + word;
            if (offsetOnLine != 0) continue;
            line = line.trim();
        }
        ModernGui.drawScaledStringCustomFont(line, x + (float)offsetOnLine, y + (float)(lineNumber * interLign), color, scale, align, shadow, customFont, fontSize);
    }

    public static void drawScaledStringCustomFont(String text, float x, float y, int color, float scale, String align, boolean shadow, String customFont, int fontSize) {
        CFontRenderer cFontRenderer = ModernGui.getCustomFont(customFont, fontSize);
        GL11.glPushMatrix();
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDisable((int)2896);
        GL11.glEnable((int)3553);
        GL11.glScalef((float)scale, (float)scale, (float)scale);
        if (cFontRenderer != null) {
            float newX = x;
            if (align.equals("center")) {
                newX = x - cFontRenderer.getStringWidth(text) * scale / 2.0f;
            } else if (align.equals("right")) {
                newX = x - cFontRenderer.getStringWidth(text) * scale;
            }
            if (shadow) {
                cFontRenderer.drawString(text.replaceAll("\u00a7.{1}", ""), (int)(newX / scale), (int)((y + 1.0f) / scale), 1908021, false);
            }
            cFontRenderer.drawString(text, (int)(newX / scale), (int)(y / scale), color, false);
        } else {
            float newX = x;
            if (align.equals("center")) {
                newX = x - (float)Minecraft.func_71410_x().field_71466_p.func_78256_a(text) * scale / 2.0f;
            } else if (align.equals("right")) {
                newX = x - (float)Minecraft.func_71410_x().field_71466_p.func_78256_a(text) * scale;
            }
            if (shadow) {
                Minecraft.func_71410_x().field_71466_p.func_85187_a(text, (int)(newX / scale), (int)((y + 1.0f) / scale), (color & 0xFCFCFC) >> 2 | color & 0xFF000000, false);
            }
            Minecraft.func_71410_x().field_71466_p.func_85187_a(text, (int)(newX / scale), (int)(y / scale), color, false);
        }
        GL11.glPopMatrix();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    public static void drawModalRectWithCustomSizedTexture(float x, float y, int u, int v, int width, int height, float textureWidth, float textureHeight, boolean filter) {
        textureWidth = 1.0f / textureWidth;
        textureHeight = 1.0f / textureHeight;
        Tessellator tessellator = Tessellator.field_78398_a;
        tessellator.func_78382_b();
        tessellator.func_78374_a((double)x, (double)(y + (float)height), 0.0, (double)((float)u * textureWidth), (double)((float)(v + height) * textureHeight));
        tessellator.func_78374_a((double)(x + (float)width), (double)(y + (float)height), 0.0, (double)((float)(u + width) * textureWidth), (double)((float)(v + height) * textureHeight));
        tessellator.func_78374_a((double)(x + (float)width), (double)y, 0.0, (double)((float)(u + width) * textureWidth), (double)((float)v * textureHeight));
        tessellator.func_78374_a((double)x, (double)y, 0.0, (double)((float)u * textureWidth), (double)((float)v * textureHeight));
        tessellator.func_78381_a();
    }

    public static void drawModalRectWithCustomSizedTextureWithTransparency(float x, float y, int u, int v, int width, int height, float textureWidth, float textureHeight, boolean filter) {
        GL11.glEnable((int)3042);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glDisable((int)3008);
        ModernGui.drawModalRectWithCustomSizedTexture(x, y, u, v, width, height, textureWidth, textureHeight, filter);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glEnable((int)3008);
    }

    public static void drawExtendedCircle(float x, float y, float width, float height) {
        GL11.glDisable((int)3553);
        GL11.glEnable((int)3042);
        float radius = width / 2.0f;
        ModernGui.drawFilledCircle(x + width / 2.0f, y + radius, radius, 180, 180);
        Tessellator tessellator = Tessellator.field_78398_a;
        tessellator.func_78382_b();
        tessellator.func_78377_a((double)x, (double)(y + radius), 0.0);
        tessellator.func_78377_a((double)(x + width), (double)(y + radius), 0.0);
        tessellator.func_78377_a((double)(x + width), (double)(y + height - radius), 0.0);
        tessellator.func_78377_a((double)x, (double)(y + height - radius), 0.0);
        tessellator.func_78381_a();
        ModernGui.drawFilledCircle(x + width / 2.0f, y + height - radius, radius, 0, 180);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3553);
    }

    public static void drawFilledCircle(float x, float y, float radius, int startAngle, int angle) {
        GL11.glShadeModel((int)7425);
        GL11.glDisable((int)2884);
        GL11.glBegin((int)6);
        float y1 = y;
        float x1 = x;
        for (int i = 0; i <= angle; ++i) {
            float degInRad = (float)(i + startAngle) * (float)Math.PI / 180.0f;
            float x2 = x + (float)Math.cos(degInRad) * radius;
            float y2 = y + (float)Math.sin(degInRad) * radius;
            GL11.glVertex2f((float)x, (float)y);
            GL11.glVertex2f((float)x1, (float)y1);
            GL11.glVertex2f((float)x2, (float)y2);
            y1 = y2;
            x1 = x2;
        }
        GL11.glEnd();
        GL11.glEnable((int)32826);
    }

    public static void drawRoundedRectangle(float xPosition, float yPosition, float zLevel, float width, float height) {
        Tessellator tessellator = Tessellator.field_78398_a;
        GL11.glDisable((int)3553);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        float radius = 2.0f;
        ModernGui.drawFilledCircle(xPosition + radius, yPosition + radius, radius, 180, 90);
        ModernGui.drawFilledCircle(xPosition + width - radius, yPosition + radius, radius, 270, 90);
        ModernGui.drawFilledCircle(xPosition + width - radius, yPosition - radius + height, radius, 0, 90);
        ModernGui.drawFilledCircle(xPosition + radius, yPosition - radius + height, radius, 90, 90);
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
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3553);
    }

    public static void drawRectangle(float xPosition, float yPosition, float zLevel, float width, float height) {
        Tessellator tessellator = Tessellator.field_78398_a;
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDisable((int)3553);
        tessellator.func_78371_b(7);
        tessellator.func_78377_a((double)xPosition, (double)yPosition, (double)zLevel);
        tessellator.func_78377_a((double)xPosition, (double)(yPosition + height), (double)zLevel);
        tessellator.func_78377_a((double)(xPosition + width), (double)(yPosition + height), (double)zLevel);
        tessellator.func_78377_a((double)(xPosition + width), (double)yPosition, (double)zLevel);
        tessellator.func_78381_a();
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3553);
    }

    public static void drawScaledCustomSizeModalRect(float x, float y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight, boolean transparency) {
        if (transparency) {
            GL11.glDisable((int)2929);
            GL11.glDepthMask((boolean)false);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glDisable((int)3008);
        }
        GL11.glEnable((int)3042);
        float f = 1.0f / tileWidth;
        float f1 = 1.0f / tileHeight;
        Tessellator tessellator = Tessellator.field_78398_a;
        tessellator.func_78371_b(7);
        tessellator.func_78374_a((double)x, (double)(y + (float)height), 0.0, (double)(u * f), (double)((v + (float)vHeight) * f1));
        tessellator.func_78374_a((double)(x + (float)width), (double)(y + (float)height), 0.0, (double)((u + (float)uWidth) * f), (double)((v + (float)vHeight) * f1));
        tessellator.func_78374_a((double)(x + (float)width), (double)y, 0.0, (double)((u + (float)uWidth) * f), (double)(v * f1));
        tessellator.func_78374_a((double)x, (double)y, 0.0, (double)(u * f), (double)(v * f1));
        tessellator.func_78381_a();
        GL11.glDisable((int)3042);
        if (transparency) {
            GL11.glEnable((int)2929);
            GL11.glDepthMask((boolean)true);
            GL11.glEnable((int)3008);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        }
    }

    public static void drawScaledString(String text, int x, int y, int color, float scale, boolean centered, boolean shadow) {
        GL11.glPushMatrix();
        GL11.glScalef((float)scale, (float)scale, (float)scale);
        float newX = x;
        if (centered) {
            newX = (float)x - (float)Minecraft.func_71410_x().field_71466_p.func_78256_a(text) * scale / 2.0f;
        }
        if (shadow) {
            Minecraft.func_71410_x().field_71466_p.func_85187_a(text, (int)(newX / scale), (int)((float)(y + 1) / scale), (color & 0xFCFCFC) >> 2 | color & 0xFF000000, false);
        }
        Minecraft.func_71410_x().field_71466_p.func_85187_a(text, (int)(newX / scale), (int)((float)y / scale), color, false);
        GL11.glPopMatrix();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    public static void drawNGBlackSquare(int posX, int posY, int width, int height) {
        Gui.func_73734_a((int)posX, (int)posY, (int)(posX + width), (int)(posY + height), (int)-16777216);
        Gui.func_73734_a((int)(posX + 1), (int)(posY + 1), (int)(posX + width - 1), (int)(posY + height - 1), (int)-15132391);
    }

    public static void drawScaledModalRectWithCustomSizedRemoteTexture(int x, int y, int u, int v, int uWidth, int vHeight, int width, int height, float textureWidth, float textureHeight, boolean filter, String url) {
        DownloadableTexture downloadableTexture = ClientProxy.getRemoteResource(url);
        if (downloadableTexture != null && downloadableTexture.isTextureUploaded()) {
            Minecraft.func_71410_x().func_110434_K().func_110577_a(ClientProxy.getLocationRemoteResource(url));
            ModernGui.drawScaledCustomSizeModalRect(x, y, u, v, uWidth, vHeight, width, height, textureWidth, textureHeight, filter);
        }
    }

    public static void bindRemoteTexture(String url) {
        DownloadableTexture downloadableTexture = ClientProxy.getRemoteResource(url);
        if (downloadableTexture != null && downloadableTexture.isTextureUploaded()) {
            Minecraft.func_71410_x().func_110434_K().func_110577_a(ClientProxy.getLocationRemoteResource(url));
        } else {
            GL11.glBindTexture((int)3553, (int)0);
        }
    }

    public static boolean isRemoteTextureLoaded(String url) {
        DownloadableTexture downloadableTexture = ClientProxy.getRemoteResource(url);
        return downloadableTexture != null && downloadableTexture.isTextureUploaded();
    }

    public static BufferedImage decodeToImage(String imageString) {
        BufferedImage image = null;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] imageByte = decoder.decodeBuffer(imageString);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    public static String formatDelayTime(Long time) {
        long now = System.currentTimeMillis();
        long diff = now - time;
        String date = "";
        long days = diff / 86400000L;
        long hours = 0L;
        long minutes = 0L;
        long seconds = 0L;
        if (days == 0L) {
            hours = diff / 3600000L;
            if (hours == 0L) {
                minutes = diff / 60000L;
                if (minutes == 0L) {
                    seconds = diff / 1000L;
                    date = date + " " + seconds + I18n.func_135053_a((String)"faction.common.seconds.short");
                } else {
                    date = date + " " + minutes + I18n.func_135053_a((String)"faction.common.minutes.short");
                }
            } else {
                date = date + " " + hours + I18n.func_135053_a((String)"faction.common.hours.short");
            }
        } else {
            date = date + " " + days + I18n.func_135053_a((String)"faction.common.days.short");
        }
        return date.trim();
    }

    public static String formatMillisToDaysHoursMinutesSeconds(Long time) {
        String date = "";
        long days = time / 86400000L;
        long hours = 0L;
        long minutes = 0L;
        long seconds = 0L;
        if (days == 0L) {
            hours = time / 3600000L;
            if (hours == 0L) {
                minutes = time / 60000L;
                if (minutes == 0L) {
                    seconds = time / 1000L;
                    date = date + " " + seconds + I18n.func_135053_a((String)"faction.common.seconds.short");
                } else {
                    date = date + " " + minutes + I18n.func_135053_a((String)"faction.common.minutes.short");
                }
            } else {
                date = date + " " + hours + I18n.func_135053_a((String)"faction.common.hours.short");
            }
        } else {
            date = date + " " + days + I18n.func_135053_a((String)"faction.common.days.short");
        }
        return date.trim();
    }

    public static String formatMillisToChrono(Long timeInMillis, boolean millis) {
        StringBuilder formattedTime = new StringBuilder();
        long hours = timeInMillis / 3600000L;
        timeInMillis = timeInMillis % 3600000L;
        long minutes = timeInMillis / 60000L;
        timeInMillis = timeInMillis % 60000L;
        long seconds = timeInMillis / 1000L;
        timeInMillis = timeInMillis % 1000L;
        long milliseconds = timeInMillis;
        formattedTime.append(String.format("%02d", hours)).append(":").append(String.format("%02d", minutes)).append(":").append(String.format("%02d", seconds));
        if (millis) {
            formattedTime.append(":").append(String.format("%03d", milliseconds));
        }
        return formattedTime.toString();
    }

    public static String formatDuration(Long duration) {
        String date = "";
        long days = duration / 86400L;
        long hours = 0L;
        long minutes = 0L;
        long seconds = 0L;
        if (days == 0L) {
            hours = duration / 3600L;
            date = date + " " + hours + " " + I18n.func_135053_a((String)"faction.common.hours");
        } else {
            date = date + " " + days + " " + I18n.func_135053_a((String)"faction.common.days");
        }
        return date;
    }

    public static String getRankColor(String rank) {
        String res = "\u00a7f";
        switch (rank.toLowerCase()) {
            case "heros": {
                res = "\u00a77";
                break;
            }
            case "legende": {
                res = "\u00a73";
                break;
            }
            case "premium": {
                res = "\u00a76";
                break;
            }
            case "moderateur": 
            case "moderateur_plus": 
            case "moderateur_test": {
                res = "\u00a7a";
                break;
            }
            case "supermodo": {
                res = "\u00a79";
                break;
            }
            case "admin": {
                res = "\u00a7c";
                break;
            }
            case "respadmin": {
                res = "\u00a74";
                break;
            }
            case "fondateur": 
            case "co-fonda": {
                res = "\u00a7b";
            }
        }
        return res;
    }

    public static void glColorHex(int hex, float coeffBrightness) {
        float red = (float)(hex >> 16 & 0xFF) / 255.0f;
        float blue = (float)(hex >> 8 & 0xFF) / 255.0f;
        float green = (float)(hex & 0xFF) / 255.0f;
        float alpha = (float)(hex >> 24 & 0xFF) / 255.0f;
        GL11.glColor4f((float)(red * coeffBrightness), (float)(blue * coeffBrightness), (float)(green * coeffBrightness), (float)alpha);
    }

    public static void glColorHexFromNgColor(String color, float coeffBrightness) {
        int hex = ngColors.containsKey(color) ? ngColors.get(color) : -1;
        float red = (float)(hex >> 16 & 0xFF) / 255.0f;
        float blue = (float)(hex >> 8 & 0xFF) / 255.0f;
        float green = (float)(hex & 0xFF) / 255.0f;
        float alpha = (float)(hex >> 24 & 0xFF) / 255.0f;
        GL11.glColor4f((float)(red * coeffBrightness), (float)(blue * coeffBrightness), (float)(green * coeffBrightness), (float)alpha);
    }

    public static String formatIntToDevise(int value) {
        String res = "";
        for (int i = new StringBuilder().append(value).append("").toString().length() - 1; i >= 0; --i) {
            char c = (value + "").charAt(i);
            if (res.length() != 0 && res.replaceAll("\\.", "").length() % 3 == 0) {
                res = "." + res;
            }
            res = c + res;
        }
        return res;
    }

    public static String chronoTimeToStr(Long chrono, boolean millis) {
        if (chrono == null || chrono < 0L) {
            return "00:00";
        }
        long hours = TimeUnit.MILLISECONDS.toHours(chrono);
        chrono = chrono - TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(chrono);
        chrono = chrono - TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(chrono);
        chrono = chrono - TimeUnit.SECONDS.toMillis(seconds);
        long left = chrono / 10L;
        String chronoStr = "";
        if (hours > 0L) {
            chronoStr = chronoStr + (hours < 10L ? "0" + hours : Long.valueOf(hours));
        }
        chronoStr = chronoStr + (!chronoStr.isEmpty() ? ":" : "") + (minutes < 10L ? "0" + minutes : Long.valueOf(minutes));
        chronoStr = chronoStr + (!chronoStr.isEmpty() ? ":" : "") + (seconds < 10L ? "0" + seconds : Long.valueOf(seconds));
        if (millis) {
            chronoStr = chronoStr + (!chronoStr.isEmpty() ? ":" : "") + (left < 10L ? "0" + left : Long.valueOf(left));
        }
        return chronoStr;
    }

    public static String getFormattedTimeDiff(long timestamp1, long timestamp2) {
        long diffInMilliseconds = Math.max(0L, timestamp1 - timestamp2);
        long hours = TimeUnit.MILLISECONDS.toHours(diffInMilliseconds);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(diffInMilliseconds) - TimeUnit.HOURS.toMinutes(hours);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(diffInMilliseconds) - TimeUnit.MINUTES.toSeconds(minutes) - TimeUnit.HOURS.toSeconds(hours);
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public static void drawDefaultBackground(GuiScreen gui, int width, int height, int mouseX, int mouseY) {
        hoveredCommonAction = "";
        String GUIClass = gui.getClass().getSimpleName();
        Gui.func_73734_a((int)0, (int)0, (int)width, (int)height, (int)-435023335);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        ModernGui.bindTextureOverlayMain();
        if (ClientData.GUIWithHelp.containsKey(GUIClass) && !ClientData.GUIWithHelp.get(GUIClass).isEmpty()) {
            ModernGui.bindTextureOverlayMain();
            boolean hoveringReturn = mouseX >= width - 82 && mouseX < width - 82 + 54 && mouseY >= 10 && mouseY < 24;
            ModernGui.drawScaledCustomSizeModalRect(width - 82, 10.0f, 1727 * GenericOverride.GUI_SCALE, (hoveringReturn ? 215 : 153) * GenericOverride.GUI_SCALE, 189 * GenericOverride.GUI_SCALE, 52 * GenericOverride.GUI_SCALE, 54, 14, 1920 * GenericOverride.GUI_SCALE, 1033 * GenericOverride.GUI_SCALE, false);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"gui.common.help"), width - 82 + 24, 14.0f, hoveringReturn ? 0 : 0xFFFFFF, 0.5f, "left", false, "georamaSemiBold", 24);
            if (hoveringReturn) {
                hoveredCommonAction = "help";
            }
        }
        ModernGui.bindTextureOverlayMain();
        boolean hoveringClose = mouseX >= width - 25 && mouseX < width - 25 + 14 && mouseY >= 10 && mouseY < 24;
        ModernGui.drawScaledCustomSizeModalRect(width - 25, 10.0f, 1658 * GenericOverride.GUI_SCALE, (hoveringClose ? 215 : 153) * GenericOverride.GUI_SCALE, 52 * GenericOverride.GUI_SCALE, 52 * GenericOverride.GUI_SCALE, 14, 14, 1920 * GenericOverride.GUI_SCALE, 1033 * GenericOverride.GUI_SCALE, false);
        if (hoveringClose) {
            hoveredCommonAction = "close";
        }
    }

    public static void mouseClickedCommon(GuiScreen gui, int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            if (hoveredCommonAction.equals("close")) {
                Minecraft.func_71410_x().func_71373_a(null);
            } else if (hoveredCommonAction.equals("help")) {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new SliderHelpGui(ClientData.GUIWithHelp.get(gui.getClass().getSimpleName()), gui));
            }
        }
    }

    public static void bindTextureOverlayMain() {
        if (System.currentTimeMillis() - lastResetCachedOverlayMainTexture > 1000L) {
            cachedOverlayMainTexture = CommonEventHandler.isWearingFullSpartanWhiteArmor((EntityPlayer)Minecraft.func_71410_x().field_71439_g) ? "overlay_main_spartan_white" : "overlay_main";
            lastResetCachedOverlayMainTexture = System.currentTimeMillis();
        }
        ClientEventHandler.STYLE.bindTexture(cachedOverlayMainTexture);
    }

    static {
        cachedOverlayMainTexture = "overlay_main";
        lastResetCachedOverlayMainTexture = 0L;
        ngColors = new LinkedHashMap<String, Integer>(){
            {
                this.put("yellow", -407550);
                this.put("red", -1760196);
                this.put("orange", -2323131);
                this.put("green", -8730273);
                this.put("dark_green", -15175913);
                this.put("blue", -9537810);
                this.put("cyan", -9518866);
                this.put("purple", -5345554);
                this.put("pink", -1867845);
                this.put("white", -1314054);
                this.put("blue_grey", -10453363);
                this.put("brown_light", -6452109);
            }
        };
        itemRenderer = new RenderItem();
        hoveredCommonAction = "";
    }
}

