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

import java.util.HashMap;
import net.halalaboos.cfont.CFontRenderer;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.gui.override.ElementOverride;
import net.ilexiconn.nationsgui.forge.client.gui.override.NotificationOverride;
import net.ilexiconn.nationsgui.forge.client.voices.keybindings.KeyManager;
import net.ilexiconn.nationsgui.forge.server.voices.VoiceChat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.lwjgl.opengl.GL11;

public class GenericOverride
extends Gui
implements ElementOverride {
    public static long COUNTRY_ENTRY_DISPLAY_TIME = 3000L;
    public static int GUI_SCALE = 3;
    public static CFontRenderer dg30 = ModernGui.getCustomFont("minecraftDungeons", 30);
    public static CFontRenderer georamaBold20 = ModernGui.getCustomFont("georamaBold", 20);
    public static CFontRenderer georamaSemiBold30 = ModernGui.getCustomFont("georamaSemiBold", 30);
    public static HashMap<String, Integer> relationsColor = new HashMap<String, Integer>(){
        {
            this.put("member", -8730273);
            this.put("neutral", -9537810);
            this.put("enemy", -1760196);
            this.put("ally", -5345554);
            this.put("colony", -667574);
            this.put("truce", -9537810);
        }
    };

    @Override
    public RenderGameOverlayEvent.ElementType getType() {
        return RenderGameOverlayEvent.ElementType.HOTBAR;
    }

    @Override
    public RenderGameOverlayEvent.ElementType[] getSubTypes() {
        return new RenderGameOverlayEvent.ElementType[0];
    }

    public static boolean displaysCountryEntry() {
        return ClientData.countryTitleInfos != null && !ClientData.countryTitleInfos.isEmpty() && System.currentTimeMillis() - Long.parseLong(ClientData.countryTitleInfos.get("displayTime")) < COUNTRY_ENTRY_DISPLAY_TIME;
    }

    @Override
    public void renderOverride(Minecraft client, ScaledResolution resolution, float partialTicks) {
        if (ClientData.playerInfos == null) {
            return;
        }
        if (System.currentTimeMillis() - ClientEventHandler.lastPlayerDispayTAB < 50L) {
            return;
        }
        if (!ClientData.versusOverlayData.isEmpty()) {
            return;
        }
        int sizeY = resolution.func_78328_b();
        int sizeX = resolution.func_78326_a();
        int centerX = sizeX / 2;
        if (!ClientData.playerInfos.isEmpty() && ClientData.currentAssault.isEmpty() && ClientData.currentWarzone.isEmpty()) {
            int offsetX = 0;
            if (KeyManager.getInstance().isKeyMuted() || VoiceChat.getProxyInstance().isRecorderActive()) {
                offsetX = 40;
            }
            ModernGui.drawScaledStringCustomFont(ClientData.playerInfos.get("money").toUpperCase(), offsetX + 5, 6.0f, 0xFFFFFF, 0.4f, "left", true, "minecraftDungeons", 30);
            ModernGui.bindTextureOverlayMain();
            ModernGui.drawScaledCustomSizeModalRect(offsetX + 5 + (int)(dg30.getStringWidth(ClientData.playerInfos.get("money").toUpperCase()) * 0.4f) + 2, 7.5f, 226.0f, 95.0f, 19, 20, 8, 9, 1920.0f, 1033.0f, true);
            ModernGui.drawScaledStringCustomFont(ClientData.playerInfos.get("orbs").toUpperCase(), offsetX + 5 + (int)(dg30.getStringWidth(ClientData.playerInfos.get("money").toUpperCase()) * 0.4f) + 2 + 8 + 5, 6.0f, 0xFFFFFF, 0.4f, "left", true, "minecraftDungeons", 30);
            ModernGui.bindTextureOverlayMain();
            ModernGui.drawScaledCustomSizeModalRect(offsetX + 5 + (int)(dg30.getStringWidth(ClientData.playerInfos.get("money").toUpperCase()) * 0.4f) + 2 + 8 + 5 + (int)(dg30.getStringWidth(ClientData.playerInfos.get("orbs").toUpperCase()) * 0.4f) + 2, 7.5f, 326.0f, 95.0f, 19, 20, 8, 9, 1920.0f, 1033.0f, true);
        }
        if (GenericOverride.displaysCountryEntry() && !NotificationOverride.displaysNotification()) {
            ModernGui.drawScaledStringCustomFont(ClientData.countryTitleInfos.get("countryName").toUpperCase().replaceAll("^\u00a7.{1}", ""), centerX, 10.0f, 0xFFFFFF, 0.75f, "center", true, "minecraftDungeons", 30);
            if (ClientData.countryTitleInfos.containsKey("relation")) {
                ModernGui.glColorHex(relationsColor.get(ClientData.countryTitleInfos.get("relation")), 1.0f);
                ModernGui.drawRectangle(centerX - 25, 30.0f, this.field_73735_i, 50.0f, 12.0f);
                ModernGui.glColorHex(-14869195, 1.0f);
                ModernGui.drawRectangle(centerX - 25, 42.0f, this.field_73735_i, 50.0f, 1.0f);
            }
            ModernGui.drawScaledStringCustomFont(ClientData.countryTitleInfos.get("countryName").startsWith("\u00a7") ? I18n.func_135053_a((String)"overlay.faction.relation.free") : I18n.func_135053_a((String)("overlay.faction.relation.short." + ClientData.countryTitleInfos.get("relation"))).toUpperCase(), centerX, 32.0f, 0xFFFFFF, 0.45f, "center", false, "minecraftDungeons", 23);
            if (ClientData.countryTitleInfos.containsKey("message") && !ClientData.countryTitleInfos.get("message").isEmpty()) {
                ModernGui.drawSectionStringCustomFont(ClientData.countryTitleInfos.get("message"), centerX, 47.0f, 0xFFFFFF, 0.5f, "center", true, "georamaSemiBold", 25, 7, 400);
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

