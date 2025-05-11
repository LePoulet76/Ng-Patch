/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.resources.I18n
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$ElementType
 *  org.lwjgl.opengl.GL11
 */
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
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.lwjgl.opengl.GL11;
import sun.misc.BASE64Decoder;

public class EdoraAutelAndBossOverride
extends Gui
implements ElementOverride {
    public static CFontRenderer dg25 = ModernGui.getCustomFont("minecraftDungeons", 25);

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

    @Override
    public RenderGameOverlayEvent.ElementType getType() {
        return RenderGameOverlayEvent.ElementType.HOTBAR;
    }

    @Override
    public RenderGameOverlayEvent.ElementType[] getSubTypes() {
        return new RenderGameOverlayEvent.ElementType[0];
    }

    @Override
    public void renderOverride(Minecraft client, ScaledResolution resolution, float partialTicks) {
        if (System.currentTimeMillis() - ClientEventHandler.lastPlayerDispayTAB < 50L) {
            return;
        }
        if (GenericOverride.displaysCountryEntry()) {
            return;
        }
        if (NotificationOverride.displaysNotification()) {
            return;
        }
        if (ClientData.currentWarzone.isEmpty()) {
            if (!ClientData.autelOverlayData.isEmpty() && System.currentTimeMillis() - EdoraAutelOverlayDataPacket.lastPacketReceived < 5000L) {
                ClientEventHandler.STYLE.bindTexture("overlay_edora");
                ModernGui.drawScaledCustomSizeModalRect((float)(resolution.func_78326_a() / 2) - 136.0f, 0.0f, 0.0f, 1032.0f, 2040, 444, 272, 59, 5760.0f, 3099.0f, true);
                for (int i = 0; i < 10; ++i) {
                    if ((Double)ClientData.autelOverlayData.get("countCapturedZones") > (double)i) {
                        ModernGui.drawScaledCustomSizeModalRect((float)(resolution.func_78326_a() / 2) - 136.0f + 42.0f - 2.0f + (float)(i * 49) / 2.5f, 39.4f, 5535.0f, 132.0f, 120, 120, 16, 16, 5760.0f, 3099.0f, true);
                        continue;
                    }
                    ModernGui.drawScaledCustomSizeModalRect((float)(resolution.func_78326_a() / 2) - 136.0f + 42.0f - 2.0f + (float)(i * 49) / 2.5f, 39.4f, 5535.0f, 12.0f, 120, 120, 16, 16, 5760.0f, 3099.0f, true);
                }
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"overlay.autel.title"), resolution.func_78326_a() / 2, 16.0f, 10544870, 0.5f, "center", true, "minecraftDungeons", 21);
                ModernGui.drawScaledStringCustomFont(ModernGui.chronoTimeToStr(3600000L - (((Double)ClientData.autelOverlayData.get("currentServerTime")).longValue() - ((Double)ClientData.autelOverlayData.get("oldestLastCapture")).longValue()), false), resolution.func_78326_a() / 2, 23.0f, 0xFFFFFF, 0.5f, "center", true, "minecraftDungeons", 27);
            } else if (!ClientData.bossOverlayData.isEmpty()) {
                if (ClientData.bossOverlayData.containsKey("healthPercent")) {
                    if (System.currentTimeMillis() - EdoraBossOverlayDataPacket.lastPacketReceived > 5000L) {
                        return;
                    }
                    ClientEventHandler.STYLE.bindTexture("overlay_edora");
                    ModernGui.drawScaledCustomSizeModalRect((float)(resolution.func_78326_a() / 2) - 136.0f, 0.0f, 0.0f, 2049.0f, 2040, 444, 272, 59, 5760.0f, 3099.0f, true);
                    ModernGui.drawScaledCustomSizeModalRect((float)(resolution.func_78326_a() / 2) - dg25.getStringWidth("BOSS EDORA") / 2.0f / 2.0f - 10.8f - 3.0f, 8.0f, 4644.0f, 219.0f, 81, 75, 10, 10, 5760.0f, 3099.0f, true);
                    ModernGui.drawScaledCustomSizeModalRect((float)(resolution.func_78326_a() / 2) + dg25.getStringWidth("BOSS EDORA") / 2.0f / 2.0f + 3.0f, 8.0f, 4824.0f, 219.0f, 81, 75, 10, 10, 5760.0f, 3099.0f, true);
                    ModernGui.drawScaledStringCustomFont("BOSS EDORA", resolution.func_78326_a() / 2, 8.0f, 0xFFFFFF, 0.5f, "center", true, "minecraftDungeons", 25);
                    ClientEventHandler.STYLE.bindTexture("overlay_edora");
                    ModernGui.drawScaledCustomSizeModalRect((float)(resolution.func_78326_a() / 2) - 163.0f, 24.0f, 0.0f, 909.0f, 2445, 54, 326, 7, 5760.0f, 3099.0f, true);
                    ModernGui.drawScaledCustomSizeModalRect((float)(resolution.func_78326_a() / 2) - 163.0f, 24.0f, 0.0f, 786.0f, (int)((Double)ClientData.bossOverlayData.get("healthPercent") * 815.0 * 3.0), 54, (int)((Double)ClientData.bossOverlayData.get("healthPercent") * 815.0 / 2.5), 7, 5760.0f, 3099.0f, true);
                    ModernGui.drawScaledStringCustomFont(ModernGui.chronoTimeToStr(((Double)ClientData.bossOverlayData.get("despawnTime")).longValue(), false), resolution.func_78326_a() / 2, 35.0f, 0xFFFFFF, 0.5f, "center", true, "minecraftDungeons", 23);
                } else {
                    long delayFromPacketReceived = System.currentTimeMillis() - EdoraBossOverlayDataPacket.lastPacketReceived;
                    if (((Double)ClientData.bossOverlayData.get("endPortalTime")).longValue() - (((Double)ClientData.bossOverlayData.get("currentServerTime")).longValue() + delayFromPacketReceived) < 0L) {
                        return;
                    }
                    ClientEventHandler.STYLE.bindTexture("overlay_edora");
                    ModernGui.drawScaledCustomSizeModalRect((float)(resolution.func_78326_a() / 2) - 136.0f, 0.0f, 0.0f, 1545.0f, 2040, 444, 272, 59, 5760.0f, 3099.0f, true);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"overlay.portal.closing"), resolution.func_78326_a() / 2, 16.0f, 10544870, 0.5f, "center", true, "minecraftDungeons", 21);
                    ModernGui.drawScaledStringCustomFont(ModernGui.chronoTimeToStr(((Double)ClientData.bossOverlayData.get("endPortalTime")).longValue() - (((Double)ClientData.bossOverlayData.get("currentServerTime")).longValue() + delayFromPacketReceived), false), resolution.func_78326_a() / 2, 23.0f, 0xFFFFFF, 0.5f, "center", true, "minecraftDungeons", 27);
                }
            }
        }
    }

    private void drawSmallString(FontRenderer fontRenderer, String string, int posX, int posY, int color) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)posX, (float)posY, (float)0.0f);
        GL11.glScalef((float)0.95f, (float)0.95f, (float)0.95f);
        this.func_73731_b(fontRenderer, string, 0, 0, 0xFFFFFF);
        GL11.glPopMatrix();
    }

    private void drawVerySmallString(FontRenderer fontRenderer, String string, int posX, int posY, int color) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)posX, (float)posY, (float)0.0f);
        GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
        this.func_73731_b(fontRenderer, string, 0, 0, 0xFFFFFF);
        GL11.glPopMatrix();
    }

    public void drawScaledString(FontRenderer fontRenderer, String text, int x, int y, int color, float scale, boolean centered, boolean shadow) {
        GL11.glPushMatrix();
        GL11.glScalef((float)scale, (float)scale, (float)scale);
        float newX = x;
        if (centered) {
            newX = (float)x - (float)fontRenderer.func_78256_a(text) * scale / 2.0f;
        }
        if (shadow) {
            fontRenderer.func_85187_a(text, (int)(newX / scale), (int)((float)(y + 1) / scale), (color & 0xFCFCFC) >> 2 | color & 0xFF000000, false);
        }
        fontRenderer.func_85187_a(text, (int)(newX / scale), (int)((float)y / scale), color, false);
        GL11.glPopMatrix();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }
}

