/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.texture.DynamicTexture
 *  net.minecraft.client.resources.I18n
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$ElementType
 *  org.lwjgl.opengl.GL11
 */
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
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.lwjgl.opengl.GL11;
import sun.misc.BASE64Decoder;

public class WarzoneOverride
extends Gui
implements ElementOverride {
    private HashMap<String, DynamicTexture> flagTextures = new HashMap();

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
        if (!ClientData.currentWarzone.isEmpty()) {
            ModernGui.bindTextureOverlayMain();
            ModernGui.drawScaledCustomSizeModalRect((float)(resolution.func_78326_a() / 2) - 148.0f, 0.0f, 1180.0f, 880.0f, 740, 153, 296, 61, 1920.0f, 1033.0f, true);
            String warzoneName = "Warzone " + ClientData.currentWarzone.get("name");
            ModernGui.drawScaledStringCustomFont(warzoneName, resolution.func_78326_a() / 2, 15.0f, 16000586, 0.5f, "center", true, "minecraftDungeons", 18);
            ModernGui.drawScaledStringCustomFont(!ClientData.currentWarzone.get("factionId").isEmpty() ? ClientData.currentWarzone.get("factionName").replaceAll("Empire", "Emp") : I18n.func_135053_a((String)"overlay.warzone.free"), resolution.func_78326_a() / 2, 22.0f, 0xFFFFFF, 0.5f, "center", true, "minecraftDungeons", 25);
            float progress = Float.parseFloat(ClientData.currentWarzone.get("percent")) / 100.0f;
            ModernGui.bindTextureOverlayMain();
            ModernGui.drawScaledCustomSizeModalRect((float)(resolution.func_78326_a() / 2) - 83.6f, 42.0f, 1502.0f, 807.0f, 418, 11, 167, 4, 1920.0f, 1033.0f, true);
            ModernGui.drawScaledCustomSizeModalRect((float)(resolution.func_78326_a() / 2) - 83.6f, 42.0f, 1502.0f, 831.0f, (int)(418.0f * progress), 11, (int)((float)((int)(418.0f * progress)) / 2.5f), 4, 1920.0f, 1033.0f, true);
            ModernGui.drawScaledStringCustomFont(ClientData.currentWarzone.get("percent") + "%", resolution.func_78326_a() / 2, 47.0f, 0xFFFFFF, 0.5f, "center", true, "minecraftDungeons", 25);
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
}

