/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  fr.nationsglory.nationsmap.NationsMap
 *  fr.nationsglory.nationsmap.Render
 *  fr.nationsglory.nationsmap.api.IMwChunkOverlay
 *  fr.nationsglory.nationsmap.api.IMwDataProvider
 *  fr.nationsglory.nationsmap.api.MwAPI
 *  fr.nationsglory.nationsmap.map.MapView
 *  fr.nationsglory.nationsmap.map.MapViewRequest
 *  fr.nationsglory.nationsmap.map.mapmode.MapMode
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.render;

import fr.nationsglory.nationsmap.NationsMap;
import fr.nationsglory.nationsmap.Render;
import fr.nationsglory.nationsmap.api.IMwChunkOverlay;
import fr.nationsglory.nationsmap.api.IMwDataProvider;
import fr.nationsglory.nationsmap.api.MwAPI;
import fr.nationsglory.nationsmap.map.MapView;
import fr.nationsglory.nationsmap.map.MapViewRequest;
import fr.nationsglory.nationsmap.map.mapmode.MapMode;
import java.awt.geom.Point2D;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import net.halalaboos.cfont.CFontRenderer;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientKeyHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.PingThread;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.gui.override.GenericOverride;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class MapRenderer {
    public Point2D.Double playerArrowScreenPos = new Point2D.Double(0.0, 0.0);
    private NationsMap mw;
    private MapMode mapMode;
    private MapView mapView;
    private ResourceLocation backgroundTexture = new ResourceLocation("mapwriter", "textures/map/background.png");
    private ResourceLocation roundMapTexture = new ResourceLocation("mapwriter", "textures/map/border_round.png");
    private ResourceLocation squareMapTexture = new ResourceLocation("mapwriter", "textures/map/border_square.png");
    private ResourceLocation playerArrowTexture = new ResourceLocation("mapwriter", "textures/map/arrow_player.png");
    private ResourceLocation northArrowTexture = new ResourceLocation("mapwriter", "textures/map/arrow_north.png");
    public static SimpleDateFormat formatterDate = new SimpleDateFormat("d/MM");
    public static SimpleDateFormat formatterTime = new SimpleDateFormat("HH:mm");
    public static CFontRenderer minecraftDungeons15 = ModernGui.getCustomFont("minecraftDungeons", 15);

    public MapRenderer(NationsMap mw, MapMode mapMode, MapView mapView) {
        this.mw = mw;
        this.mapMode = mapMode;
        this.mapView = mapView;
    }

    private static void paintChunk(MapMode mapMode, MapView mapView, IMwChunkOverlay overlay) {
        int chunkX = overlay.getCoordinates().x;
        int chunkZ = overlay.getCoordinates().y;
        float filling = overlay.getFilling();
        Point2D.Double topCorner = mapMode.blockXZtoScreenXY(mapView, (double)(chunkX << 4), (double)(chunkZ << 4));
        Point2D.Double botCorner = mapMode.blockXZtoScreenXY(mapView, (double)(chunkX + 1 << 4), (double)(chunkZ + 1 << 4));
        topCorner.x = Math.max((double)mapMode.x, topCorner.x);
        topCorner.x = Math.min((double)(mapMode.x + mapMode.w), topCorner.x);
        topCorner.y = Math.max((double)mapMode.y, topCorner.y);
        topCorner.y = Math.min((double)(mapMode.y + mapMode.h), topCorner.y);
        botCorner.x = Math.max((double)mapMode.x, botCorner.x);
        botCorner.x = Math.min((double)(mapMode.x + mapMode.w), botCorner.x);
        botCorner.y = Math.max((double)mapMode.y, botCorner.y);
        botCorner.y = Math.min((double)(mapMode.y + mapMode.h), botCorner.y);
        double sizeX = (botCorner.x - topCorner.x) * (double)filling;
        double sizeY = (botCorner.y - topCorner.y) * (double)filling;
        double offsetX = (botCorner.x - topCorner.x - sizeX) / 2.0;
        double offsetY = (botCorner.y - topCorner.y - sizeY) / 2.0;
        if (overlay.hasBorder()) {
            Render.setColour((int)overlay.getBorderColor());
            Render.drawRectBorder((double)(topCorner.x + 1.0), (double)(topCorner.y + 1.0), (double)(botCorner.x - topCorner.x - 1.0), (double)(botCorner.y - topCorner.y - 1.0), (double)overlay.getBorderWidth());
        }
        Render.setColour((int)overlay.getColor());
        Render.drawRect((double)(topCorner.x + offsetX + 1.0), (double)(topCorner.y + offsetY + 1.0), (double)(sizeX - 1.0), (double)(sizeY - 1.0));
    }

    private void drawMapTexture() {
        double h;
        double w;
        double v;
        double u;
        int regionZoomLevel = Math.max(0, this.mapView.getZoomLevel());
        double tSize = this.mw.textureSize;
        double zoomScale = 1 << regionZoomLevel;
        if (!this.mapMode.circular && this.mw.mapPixelSnapEnabled && this.mapView.getZoomLevel() >= 0) {
            u = (double)Math.round(this.mapView.getMinX() / zoomScale) / tSize % 1.0;
            v = (double)Math.round(this.mapView.getMinZ() / zoomScale) / tSize % 1.0;
            w = (double)Math.round(this.mapView.getWidth() / zoomScale) / tSize;
            h = (double)Math.round(this.mapView.getHeight() / zoomScale) / tSize;
        } else {
            double tSizeInBlocks = tSize * zoomScale;
            u = this.mapView.getMinX() / tSizeInBlocks % 1.0;
            v = this.mapView.getMinZ() / tSizeInBlocks % 1.0;
            w = this.mapView.getWidth() / tSizeInBlocks;
            h = this.mapView.getHeight() / tSizeInBlocks;
        }
        GL11.glPushMatrix();
        if (this.mapMode.rotate) {
            GL11.glRotated((double)this.mw.mapRotationDegrees, (double)0.0, (double)0.0, (double)1.0);
        }
        if (this.mapMode.circular) {
            Render.setCircularStencil((double)0.0, (double)0.0, (double)((double)this.mapMode.h / 2.0));
        }
        if (this.mapView.getUndergroundMode() && regionZoomLevel == 0) {
            this.mw.undergroundMapTexture.requestView(this.mapView);
            Render.setColourWithAlphaPercent((int)0, (int)this.mapMode.alphaPercent);
            Render.drawRect((double)this.mapMode.x, (double)this.mapMode.y, (double)this.mapMode.w, (double)this.mapMode.h);
            Render.setColourWithAlphaPercent((int)0xFFFFFF, (int)this.mapMode.alphaPercent);
            this.mw.undergroundMapTexture.bind();
            Render.drawTexturedRect((double)this.mapMode.x, (double)this.mapMode.y, (double)this.mapMode.w, (double)this.mapMode.h, (double)u, (double)v, (double)(u + w), (double)(v + h));
        } else {
            MapViewRequest req = new MapViewRequest(this.mapView);
            this.mw.mapTexture.requestView(req, this.mw.executor, this.mw.regionManager);
            if (this.mw.backgroundTextureMode > 0) {
                double bu1 = 0.0;
                double bu2 = 1.0;
                double bv1 = 0.0;
                double bv2 = 1.0;
                if (this.mw.backgroundTextureMode == 2) {
                    double bSize = tSize / 256.0;
                    bu1 = u * bSize;
                    bu2 = (u + w) * bSize;
                    bv1 = v * bSize;
                    bv2 = (v + h) * bSize;
                }
                this.mw.mc.field_71446_o.func_110577_a(this.backgroundTexture);
                Render.setColourWithAlphaPercent((int)0xFFFFFF, (int)this.mapMode.alphaPercent);
                Render.drawTexturedRect((double)this.mapMode.x, (double)this.mapMode.y, (double)this.mapMode.w, (double)this.mapMode.h, (double)bu1, (double)bv1, (double)bu2, (double)bv2);
            } else {
                Render.setColourWithAlphaPercent((int)0, (int)this.mapMode.alphaPercent);
                Render.drawRect((double)this.mapMode.x, (double)this.mapMode.y, (double)this.mapMode.w, (double)this.mapMode.h);
            }
            if (this.mw.mapTexture.isLoaded(req)) {
                this.mw.mapTexture.bind();
                Render.setColourWithAlphaPercent((int)0xFFFFFF, (int)this.mapMode.alphaPercent);
                Render.drawTexturedRect((double)this.mapMode.x, (double)this.mapMode.y, (double)this.mapMode.w, (double)this.mapMode.h, (double)u, (double)v, (double)(u + w), (double)(v + h));
            }
        }
        if (this.mapMode.circular) {
            Render.disableStencil();
        }
        GL11.glPopMatrix();
    }

    private void drawGenericOverlay() {
        if (this.mw.miniMap.smallMapMode.heightPercent >= 20) {
            ModernGui.bindTextureOverlayMain();
            if (ClientProxy.clientConfig.specialEnabled) {
                ModernGui.drawScaledCustomSizeModalRect((float)this.mapMode.x - 2.0f, (float)this.mapMode.y - 2.0f, 25 * GenericOverride.GUI_SCALE, 352 * GenericOverride.GUI_SCALE, 157 * GenericOverride.GUI_SCALE, 250 * GenericOverride.GUI_SCALE, this.mapMode.w + 5, this.mapMode.w + 60, 1920 * GenericOverride.GUI_SCALE, 1033 * GenericOverride.GUI_SCALE, false);
            } else {
                ModernGui.drawScaledCustomSizeModalRect((float)this.mapMode.x - 2.0f, (float)this.mapMode.y - 2.0f, 25 * GenericOverride.GUI_SCALE, 31 * GenericOverride.GUI_SCALE, 157 * GenericOverride.GUI_SCALE, 209 * GenericOverride.GUI_SCALE, this.mapMode.w + 5, this.mapMode.w + 35, 1920 * GenericOverride.GUI_SCALE, 1033 * GenericOverride.GUI_SCALE, false);
            }
            int offsetY = this.mapMode.y + this.mapMode.h + 2;
            if (!ClientData.currentWarzone.isEmpty()) {
                ModernGui.drawScaledStringCustomFont("WARZONE " + ClientData.currentWarzone.get("name").toUpperCase(), this.mapMode.x, offsetY, 0xFFFFFF, 0.35f, "left", false, "minecraftDungeons", 30);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("warzone.bonus." + ClientData.currentWarzone.get("type") + ".value")).toUpperCase(), this.mapMode.x, offsetY + 8, 0xFFFFFF, 0.35f, "left", false, "minecraftDungeons", 22);
                offsetY += 22;
            } else if (!ClientData.countryTitleInfos.isEmpty()) {
                String countryName;
                int offsetFlag = 0;
                if (!ClientData.countryTitleInfos.get("countryName").toLowerCase().contains("wilderness")) {
                    ClientProxy.loadCountryFlag(ClientData.countryTitleInfos.get("countryName"));
                    if (ClientProxy.flagsTexture.containsKey(ClientData.countryTitleInfos.get("countryName"))) {
                        GL11.glBindTexture((int)3553, (int)ClientProxy.flagsTexture.get(ClientData.countryTitleInfos.get("countryName")).func_110552_b());
                        ModernGui.drawScaledCustomSizeModalRect(this.mapMode.x, offsetY + 2, 0.0f, 0.0f, 156, 78, 19, 12, 156.0f, 78.0f, false);
                        offsetFlag = 21;
                    }
                }
                ModernGui.drawScaledStringCustomFont((countryName = ClientData.countryTitleInfos.get("countryName").toUpperCase().replaceAll("^\u00a7.{1}", "").replaceAll("EMPIRE", "EMP")).length() > 10 ? countryName.substring(0, 9) + ".." : countryName, this.mapMode.x + offsetFlag, offsetY + 1, 0xFFFFFF, 0.35f, "left", true, "minecraftDungeons", 24);
                ModernGui.glColorHex(GenericOverride.relationsColor.get(ClientData.countryTitleInfos.get("relation")), 1.0f);
                ModernGui.drawRectangle(this.mapMode.x + offsetFlag, offsetY + 8, 0.0f, 26.0f, 6.0f);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("overlay.faction.relation.short." + ClientData.countryTitleInfos.get("relation"))).toUpperCase(), this.mapMode.x + offsetFlag + 13, offsetY + 8, 0xFFFFFF, 0.3f, "center", false, "minecraftDungeons", 23);
                offsetY += 22;
            } else {
                ModernGui.drawScaledStringCustomFont(ClientProxy.currentServerName.toUpperCase(), this.mapMode.x, offsetY, 0xFFFFFF, 0.35f, "left", true, "minecraftDungeons", 30);
                offsetY += 22;
            }
            int colorTextDefault = 0xBABADA;
            if (ModernGui.cachedOverlayMainTexture.equalsIgnoreCase("overlay_main_spartan_white")) {
                colorTextDefault = 0;
            }
            ModernGui.drawScaledStringCustomFont(this.mw.coordsMode == 1 ? this.mw.playerXInt + " " + this.mw.playerYInt + " " + this.mw.playerZInt : "-", this.mapMode.x, offsetY, colorTextDefault, 0.5f, "left", false, "georamaBold", 24);
            ModernGui.drawScaledStringCustomFont(this.getOrientation(Minecraft.func_71410_x().field_71439_g.field_70177_z), this.mapMode.x + this.mapMode.w, offsetY, colorTextDefault, 0.5f, "right", false, "georamaBold", 24);
            offsetY += 10;
            if (ClientProxy.clientConfig.specialEnabled) {
                ModernGui.drawScaledStringCustomFont(PingThread.ping + " ms", this.mapMode.x, offsetY, colorTextDefault, 0.5f, "left", false, "georamaSemiBold", 25);
                ModernGui.drawScaledStringCustomFont(ClientEventHandler.lastClicks + " cps", this.mapMode.x, offsetY + 7, colorTextDefault, 0.5f, "left", false, "georamaSemiBold", 25);
                ModernGui.drawScaledStringCustomFont(Minecraft.func_71410_x().field_71426_K.split(",")[0], this.mapMode.x, offsetY + 14, colorTextDefault, 0.5f, "left", false, "georamaSemiBold", 25);
                ModernGui.drawScaledStringCustomFont("Sprint " + (ClientKeyHandler.toggleSprintEnabled ? "\u00a7aON" : "\u00a7cOFF"), this.mapMode.x + this.mapMode.w, offsetY, colorTextDefault, 0.5f, "right", false, "georamaSemiBold", 25);
                Date date = new Date(ClientData.serverTime + (System.currentTimeMillis() - ClientData.clientTimeWhenServerTimeReceived));
                ModernGui.drawScaledStringCustomFont(formatterDate.format(date), this.mapMode.x + this.mapMode.w, offsetY + 7, colorTextDefault, 0.5f, "right", false, "georamaSemiBold", 25);
                ModernGui.drawScaledStringCustomFont(formatterTime.format(date), this.mapMode.x + this.mapMode.w, offsetY + 14, colorTextDefault, 0.5f, "right", false, "georamaSemiBold", 25);
                offsetY += 25;
            }
            if (!ClientData.currentWarzone.isEmpty()) {
                ModernGui.bindTextureOverlayMain();
                ModernGui.drawScaledCustomSizeModalRect((float)this.mapMode.x - 2.0f, offsetY, 25 * GenericOverride.GUI_SCALE, 304 * GenericOverride.GUI_SCALE, 157 * GenericOverride.GUI_SCALE, 42 * GenericOverride.GUI_SCALE, this.mapMode.w + 5, 21, 1920 * GenericOverride.GUI_SCALE, 1033 * GenericOverride.GUI_SCALE, false);
                String label = I18n.func_135053_a((String)"warzone.captured");
                boolean isCapturing = false;
                if (ClientData.currentWarzone.get("factionCapturingId").equalsIgnoreCase("notcontested")) {
                    label = I18n.func_135053_a((String)"warzone.notcontested");
                } else if (ClientData.currentWarzone.get("factionCapturingId").equalsIgnoreCase("contested")) {
                    label = I18n.func_135053_a((String)"warzone.contested");
                } else if (!ClientData.currentWarzone.get("percent").equalsIgnoreCase("100") && !ClientData.currentWarzone.get("factionCapturingId").equals(ClientData.currentWarzone.get("factionId"))) {
                    label = I18n.func_135053_a((String)"warzone.capturing");
                    isCapturing = true;
                } else if (!ClientData.currentWarzone.get("percent").equalsIgnoreCase("100") && ClientData.currentWarzone.get("factionCapturingId").equals(ClientData.currentWarzone.get("factionId"))) {
                    label = I18n.func_135053_a((String)"warzone.securisation");
                }
                if (isCapturing) {
                    label = label + " (" + ClientData.currentWarzone.get("factionCapturingName") + ")";
                }
                ModernGui.drawScaledStringCustomFont("Status", (float)this.mapMode.x + 1.0f, offsetY + 3, 16000586, 0.5f, "left", true, "minecraftDungeons", 15);
                ModernGui.drawScaledStringCustomFont(label, (float)this.mapMode.x + 3.0f + minecraftDungeons15.getStringWidth("Status") / 2.0f, (float)offsetY + 3.5f, 0xFFFFFF, 0.5f, "left", true, "georamaSemiBold", 20);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"overlay.warzone.package"), (float)this.mapMode.x + 1.0f, offsetY + 11, 16000586, 0.5f, "left", true, "minecraftDungeons", 15);
                ModernGui.drawScaledStringCustomFont(ClientData.currentWarzone.get("nextTimeDropPackage"), (float)this.mapMode.x + 3.0f + minecraftDungeons15.getStringWidth(I18n.func_135053_a((String)"overlay.warzone.package")) / 2.0f, (float)offsetY + 11.5f, 0xFFFFFF, 0.5f, "left", true, "georamaSemiBold", 20);
                offsetY += 25;
            } else if (!(ClientData.currentAssault.isEmpty() || ClientData.currentAssault.get("areaX").equals("0") && ClientData.currentAssault.get("areaZ").equals("0") && ClientData.currentAssault.get("packageX").equals("0") && ClientData.currentAssault.get("packageZ").equals("0"))) {
                ModernGui.bindTextureOverlayMain();
                ModernGui.drawScaledCustomSizeModalRect((float)this.mapMode.x - 2.0f, offsetY, 25 * GenericOverride.GUI_SCALE, 304 * GenericOverride.GUI_SCALE, 157 * GenericOverride.GUI_SCALE, 42 * GenericOverride.GUI_SCALE, this.mapMode.w + 5, 21, 1920 * GenericOverride.GUI_SCALE, 1033 * GenericOverride.GUI_SCALE, false);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"overlay.assault.label.package"), (float)this.mapMode.x + 1.0f, offsetY + 3, 16000586, 0.5f, "left", true, "minecraftDungeons", 15);
                String packagePosition = !ClientData.currentAssault.get("packageX").equals("0") || !ClientData.currentAssault.get("packageZ").equals("0") ? "X: " + ClientData.currentAssault.get("packageX") + ", Z: " + ClientData.currentAssault.get("packageZ") : "-";
                ModernGui.drawScaledStringCustomFont(packagePosition, (float)this.mapMode.x + 3.0f + minecraftDungeons15.getStringWidth(I18n.func_135053_a((String)"overlay.assault.label.package")) / 2.0f, offsetY + 4, 0xFFFFFF, 0.5f, "left", true, "georamaSemiBold", 18);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"overlay.assault.label.area"), (float)this.mapMode.x + 1.0f, offsetY + 11, 16000586, 0.5f, "left", true, "minecraftDungeons", 15);
                String areaPosition = !ClientData.currentAssault.get("areaX").equals("0") || !ClientData.currentAssault.get("areaZ").equals("0") ? "X: " + ClientData.currentAssault.get("areaX") + ", Z: " + ClientData.currentAssault.get("areaZ") : "-";
                ModernGui.drawScaledStringCustomFont(areaPosition, (float)this.mapMode.x + 3.0f + minecraftDungeons15.getStringWidth(I18n.func_135053_a((String)"overlay.assault.label.area")) / 2.0f, offsetY + 12, 0xFFFFFF, 0.5f, "left", true, "georamaSemiBold", 18);
                offsetY += 25;
            }
            if (ClientData.isCombatTagged) {
                ModernGui.bindTextureOverlayMain();
                ModernGui.drawScaledCustomSizeModalRect((float)this.mapMode.x - 2.0f, offsetY, 25 * GenericOverride.GUI_SCALE, 612 * GenericOverride.GUI_SCALE, 157 * GenericOverride.GUI_SCALE, 26 * GenericOverride.GUI_SCALE, this.mapMode.w + 5, 13, 1920 * GenericOverride.GUI_SCALE, 1033 * GenericOverride.GUI_SCALE, false);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"overlay.tag_combat"), (float)this.mapMode.x - 2.0f + (float)((this.mapMode.w + 5) / 2), offsetY + 3, 16000586, 0.5f, "center", true, "minecraftDungeons", 15);
            }
            if (System.currentTimeMillis() > 1734166800000L && System.currentTimeMillis() < 1736722800000L) {
                ModernGui.bindTextureOverlayMain();
                ModernGui.drawScaledCustomSizeModalRect((float)this.mapMode.x - 30.0f, this.mapMode.y - 5, 1439 * GenericOverride.GUI_SCALE, 0 * GenericOverride.GUI_SCALE, 80 * GenericOverride.GUI_SCALE, 80 * GenericOverride.GUI_SCALE, 25, 25, 1920 * GenericOverride.GUI_SCALE, 1033 * GenericOverride.GUI_SCALE, false);
                ModernGui.drawScaledStringCustomFont(Keyboard.getKeyName((int)ClientKeyHandler.KEY_BONUS.field_74512_d), (float)this.mapMode.x - 30.0f + 18.5f, (float)(this.mapMode.y - 5) + 17.0f, 12588865, 0.5f, "center", false, "georamaBold", 13);
            } else if (System.currentTimeMillis() >= ClientData.bonusStartTime && System.currentTimeMillis() <= ClientData.bonusEndTime) {
                ModernGui.bindTextureOverlayMain();
                ModernGui.drawScaledCustomSizeModalRect((float)this.mapMode.x - 30.0f, this.mapMode.y - 5, 1519 * GenericOverride.GUI_SCALE, 0 * GenericOverride.GUI_SCALE, 80 * GenericOverride.GUI_SCALE, 80 * GenericOverride.GUI_SCALE, 25, 25, 1920 * GenericOverride.GUI_SCALE, 1033 * GenericOverride.GUI_SCALE, false);
                ModernGui.drawScaledStringCustomFont(Keyboard.getKeyName((int)ClientKeyHandler.KEY_BONUS.field_74512_d), (float)this.mapMode.x - 30.0f + 18.5f, (float)(this.mapMode.y - 5) + 17.0f, 15712091, 0.5f, "center", false, "georamaBold", 13);
            }
            if (ClientData.waitingServerName != null && !ClientData.waitingServerName.isEmpty() && !ClientData.waitingServerName.equalsIgnoreCase("null")) {
                ClientEventHandler.STYLE.bindTexture("overlay_hud");
                ModernGui.drawScaledCustomSizeModalRect((float)this.mapMode.x - 93.0f, this.mapMode.y - 2, 813.0f, 0.0f, 216, 114, 86, 45, 1920.0f, 1033.0f, false);
                ModernGui.drawScaledCustomSizeModalRect((float)this.mapMode.x - 93.0f - 7.0f, this.mapMode.y - 2, 1030.0f, 0.0f, 14, 14, 5, 5, 1920.0f, 1033.0f, false);
                ModernGui.drawScaledStringCustomFont(Keyboard.getKeyName((int)ClientKeyHandler.KEY_WAITING.field_74512_d), (float)this.mapMode.x - 93.0f - 7.0f + 2.0f, (float)(this.mapMode.y - 5) + 4.5f, 0x6E76EE, 0.5f, "center", false, "georamaBold", 13);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"waiting.server") + " " + ClientData.waitingServerName.toUpperCase(), (float)this.mapMode.x - 93.0f + 7.0f, this.mapMode.y - 2 + 5, 0x6E76EE, 0.5f, "left", false, "minecraftDungeons", 24);
                ModernGui.drawScaledStringCustomFont("\u00a7o" + (ClientData.waitingPriority ? I18n.func_135053_a((String)"waiting.list.priority") : I18n.func_135053_a((String)"waiting.list.classic")), (float)this.mapMode.x - 93.0f + 7.0f, this.mapMode.y - 2 + 16, 0xD9D9D9, 0.5f, "left", false, "georamaRegular", 24);
                long diffTime = System.currentTimeMillis() - ClientData.waitingJoinTime;
                diffTime = diffTime / 1000L / 60L;
                ModernGui.drawScaledStringCustomFont(ClientData.waitingPosition + " / \u00a77" + ClientData.waitingTotal, (float)this.mapMode.x - 93.0f + 7.0f, this.mapMode.y - 2 + 27, 0xFFFFFF, 0.5f, "left", false, "georamaSemiBold", 25);
                ModernGui.drawScaledStringCustomFont("\u00a7o" + diffTime + " minutes", (float)this.mapMode.x - 93.0f + 7.0f, this.mapMode.y - 2 + 35, 0xD9D9D9, 0.5f, "left", false, "georamaRegular", 20);
            }
        }
    }

    public String getOrientation(float yaw) {
        String[] directions = new String[]{"S", "SO", "O", "NO", "N", "NE", "E", "SE"};
        int index = Math.round(yaw / 45.0f) % 8;
        if (index < 0) {
            index = (index + 8) % 8;
        }
        return directions[index];
    }

    private void drawPlayerArrow() {
        GL11.glPushMatrix();
        double scale = this.mapView.getDimensionScaling(this.mw.playerDimension);
        Point2D.Double p = this.mapMode.getClampedScreenXY(this.mapView, this.mw.playerX * scale, this.mw.playerZ * scale);
        this.playerArrowScreenPos.setLocation(p.x + (double)this.mapMode.xTranslation, p.y + (double)this.mapMode.yTranslation);
        GL11.glTranslated((double)p.x, (double)p.y, (double)0.0);
        if (!this.mapMode.rotate) {
            GL11.glRotated((double)(-this.mw.mapRotationDegrees), (double)0.0, (double)0.0, (double)1.0);
        }
        double arrowSize = this.mapMode.playerArrowSize;
        Render.setColour((int)-1);
        this.mw.mc.field_71446_o.func_110577_a(this.playerArrowTexture);
        Render.drawTexturedRect((double)(-arrowSize), (double)(-arrowSize), (double)(arrowSize * 2.0), (double)(arrowSize * 2.0), (double)0.0, (double)0.0, (double)1.0, (double)1.0);
        GL11.glPopMatrix();
    }

    private void drawIcons() {
        GL11.glPushMatrix();
        if (this.mapMode.rotate) {
            GL11.glRotated((double)this.mw.mapRotationDegrees, (double)0.0, (double)0.0, (double)1.0);
        }
        this.mw.markerManager.drawMarkers(this.mapMode, this.mapView);
        if (this.mw.playerTrail.enabled) {
            this.mw.playerTrail.draw(this.mapMode, this.mapView);
        }
        if (this.mapMode.rotate) {
            double y = (double)this.mapMode.h / 2.0;
            double arrowSize = this.mapMode.playerArrowSize;
            Render.setColour((int)-1);
            this.mw.mc.field_71446_o.func_110577_a(this.northArrowTexture);
            Render.drawTexturedRect((double)(-arrowSize), (double)(-y - arrowSize * 2.0), (double)(arrowSize * 2.0), (double)(arrowSize * 2.0), (double)0.0, (double)0.0, (double)1.0, (double)1.0);
        }
        GL11.glPopMatrix();
        this.drawPlayerArrow();
    }

    private void drawCoords() {
        if (this.mapMode.coordsEnabled) {
            GL11.glPushMatrix();
            GL11.glTranslatef((float)this.mapMode.textX, (float)this.mapMode.textY, (float)0.0f);
            if (this.mw.coordsMode != 2) {
                GL11.glScalef((float)0.5f, (float)0.5f, (float)1.0f);
            }
            int offset = 0;
            if (this.mw.coordsMode > 0) {
                Render.drawCentredString((int)0, (int)0, (int)this.mapMode.textColour, (String)"%d, %d, %d", (Object[])new Object[]{this.mw.playerXInt, this.mw.playerYInt, this.mw.playerZInt});
                offset += 12;
            }
            if (this.mw.undergroundMode) {
                Render.drawCentredString((int)0, (int)offset, (int)this.mapMode.textColour, (String)"underground mode", (Object[])new Object[0]);
            }
            GL11.glPopMatrix();
        }
    }

    private IMwDataProvider drawOverlay() {
        ArrayList overlays;
        IMwDataProvider provider = MwAPI.getCurrentDataProvider();
        if (provider != null && (overlays = provider.getChunksOverlay(this.mapView.getDimension(), this.mapView.getX(), this.mapView.getZ(), this.mapView.getMinX(), this.mapView.getMinZ(), this.mapView.getMaxX(), this.mapView.getMaxZ())) != null) {
            for (IMwChunkOverlay overlay : overlays) {
                MapRenderer.paintChunk(this.mapMode, this.mapView, overlay);
            }
        }
        return provider;
    }

    public void draw() {
        if (ClientProxy.currentServerName.equals("mmr")) {
            return;
        }
        if (System.currentTimeMillis() - ClientEventHandler.lastPlayerDispayTAB > 50L) {
            this.mapMode.setScreenRes();
            this.mapView.setMapWH(this.mapMode);
            this.mapView.setTextureSize(this.mw.textureSize);
            GL11.glPushMatrix();
            GL11.glLoadIdentity();
            GL11.glTranslated((double)this.mapMode.xTranslation, (double)this.mapMode.yTranslation, (double)-2000.0);
            if (this.mapMode.borderMode > 0) {
                this.drawGenericOverlay();
            }
            this.drawMapTexture();
            this.drawIcons();
            IMwDataProvider provider = this.drawOverlay();
            GL11.glEnable((int)2929);
            GL11.glPopMatrix();
            if (provider != null) {
                GL11.glPushMatrix();
                provider.onDraw(this.mapView, this.mapMode);
                GL11.glPopMatrix();
            }
        }
    }
}

