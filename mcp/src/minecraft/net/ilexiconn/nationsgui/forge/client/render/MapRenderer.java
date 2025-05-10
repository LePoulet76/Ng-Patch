package net.ilexiconn.nationsgui.forge.client.render;

import fr.nationsglory.nationsmap.NationsMap;
import fr.nationsglory.nationsmap.Render;
import fr.nationsglory.nationsmap.api.IMwChunkOverlay;
import fr.nationsglory.nationsmap.api.IMwDataProvider;
import fr.nationsglory.nationsmap.api.MwAPI;
import fr.nationsglory.nationsmap.map.MapView;
import fr.nationsglory.nationsmap.map.MapViewRequest;
import fr.nationsglory.nationsmap.map.mapmode.MapMode;
import java.awt.geom.Point2D.Double;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import net.halalaboos.cfont.CFontRenderer;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientKeyHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.PingThread;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.gui.override.GenericOverride;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class MapRenderer
{
    public Double playerArrowScreenPos = new Double(0.0D, 0.0D);
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
    public static CFontRenderer minecraftDungeons15 = ModernGui.getCustomFont("minecraftDungeons", Integer.valueOf(15));

    public MapRenderer(NationsMap mw, MapMode mapMode, MapView mapView)
    {
        this.mw = mw;
        this.mapMode = mapMode;
        this.mapView = mapView;
    }

    private static void paintChunk(MapMode mapMode, MapView mapView, IMwChunkOverlay overlay)
    {
        int chunkX = overlay.getCoordinates().x;
        int chunkZ = overlay.getCoordinates().y;
        float filling = overlay.getFilling();
        Double topCorner = mapMode.blockXZtoScreenXY(mapView, (double)(chunkX << 4), (double)(chunkZ << 4));
        Double botCorner = mapMode.blockXZtoScreenXY(mapView, (double)(chunkX + 1 << 4), (double)(chunkZ + 1 << 4));
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
        double offsetX = (botCorner.x - topCorner.x - sizeX) / 2.0D;
        double offsetY = (botCorner.y - topCorner.y - sizeY) / 2.0D;

        if (overlay.hasBorder())
        {
            Render.setColour(overlay.getBorderColor());
            Render.drawRectBorder(topCorner.x + 1.0D, topCorner.y + 1.0D, botCorner.x - topCorner.x - 1.0D, botCorner.y - topCorner.y - 1.0D, (double)overlay.getBorderWidth());
        }

        Render.setColour(overlay.getColor());
        Render.drawRect(topCorner.x + offsetX + 1.0D, topCorner.y + offsetY + 1.0D, sizeX - 1.0D, sizeY - 1.0D);
    }

    private void drawMapTexture()
    {
        int regionZoomLevel = Math.max(0, this.mapView.getZoomLevel());
        double tSize = (double)this.mw.textureSize;
        double zoomScale = (double)(1 << regionZoomLevel);
        double u;
        double v;
        double w;
        double h;

        if (!this.mapMode.circular && this.mw.mapPixelSnapEnabled && this.mapView.getZoomLevel() >= 0)
        {
            u = (double)Math.round(this.mapView.getMinX() / zoomScale) / tSize % 1.0D;
            v = (double)Math.round(this.mapView.getMinZ() / zoomScale) / tSize % 1.0D;
            w = (double)Math.round(this.mapView.getWidth() / zoomScale) / tSize;
            h = (double)Math.round(this.mapView.getHeight() / zoomScale) / tSize;
        }
        else
        {
            double req = tSize * zoomScale;
            u = this.mapView.getMinX() / req % 1.0D;
            v = this.mapView.getMinZ() / req % 1.0D;
            w = this.mapView.getWidth() / req;
            h = this.mapView.getHeight() / req;
        }

        GL11.glPushMatrix();

        if (this.mapMode.rotate)
        {
            GL11.glRotated(this.mw.mapRotationDegrees, 0.0D, 0.0D, 1.0D);
        }

        if (this.mapMode.circular)
        {
            Render.setCircularStencil(0.0D, 0.0D, (double)this.mapMode.h / 2.0D);
        }

        if (this.mapView.getUndergroundMode() && regionZoomLevel == 0)
        {
            this.mw.undergroundMapTexture.requestView(this.mapView);
            Render.setColourWithAlphaPercent(0, this.mapMode.alphaPercent);
            Render.drawRect((double)this.mapMode.x, (double)this.mapMode.y, (double)this.mapMode.w, (double)this.mapMode.h);
            Render.setColourWithAlphaPercent(16777215, this.mapMode.alphaPercent);
            this.mw.undergroundMapTexture.bind();
            Render.drawTexturedRect((double)this.mapMode.x, (double)this.mapMode.y, (double)this.mapMode.w, (double)this.mapMode.h, u, v, u + w, v + h);
        }
        else
        {
            MapViewRequest req1 = new MapViewRequest(this.mapView);
            this.mw.mapTexture.requestView(req1, this.mw.executor, this.mw.regionManager);

            if (this.mw.backgroundTextureMode > 0)
            {
                double bu1 = 0.0D;
                double bu2 = 1.0D;
                double bv1 = 0.0D;
                double bv2 = 1.0D;

                if (this.mw.backgroundTextureMode == 2)
                {
                    double bSize = tSize / 256.0D;
                    bu1 = u * bSize;
                    bu2 = (u + w) * bSize;
                    bv1 = v * bSize;
                    bv2 = (v + h) * bSize;
                }

                this.mw.mc.renderEngine.bindTexture(this.backgroundTexture);
                Render.setColourWithAlphaPercent(16777215, this.mapMode.alphaPercent);
                Render.drawTexturedRect((double)this.mapMode.x, (double)this.mapMode.y, (double)this.mapMode.w, (double)this.mapMode.h, bu1, bv1, bu2, bv2);
            }
            else
            {
                Render.setColourWithAlphaPercent(0, this.mapMode.alphaPercent);
                Render.drawRect((double)this.mapMode.x, (double)this.mapMode.y, (double)this.mapMode.w, (double)this.mapMode.h);
            }

            if (this.mw.mapTexture.isLoaded(req1))
            {
                this.mw.mapTexture.bind();
                Render.setColourWithAlphaPercent(16777215, this.mapMode.alphaPercent);
                Render.drawTexturedRect((double)this.mapMode.x, (double)this.mapMode.y, (double)this.mapMode.w, (double)this.mapMode.h, u, v, u + w, v + h);
            }
        }

        if (this.mapMode.circular)
        {
            Render.disableStencil();
        }

        GL11.glPopMatrix();
    }

    private void drawGenericOverlay()
    {
        if (this.mw.miniMap.smallMapMode.heightPercent >= 20)
        {
            ModernGui.bindTextureOverlayMain();

            if (ClientProxy.clientConfig.specialEnabled)
            {
                ModernGui.drawScaledCustomSizeModalRect((float)this.mapMode.x - 2.0F, (float)this.mapMode.y - 2.0F, (float)(25 * GenericOverride.GUI_SCALE), (float)(352 * GenericOverride.GUI_SCALE), 157 * GenericOverride.GUI_SCALE, 250 * GenericOverride.GUI_SCALE, this.mapMode.w + 5, this.mapMode.w + 60, (float)(1920 * GenericOverride.GUI_SCALE), (float)(1033 * GenericOverride.GUI_SCALE), false);
            }
            else
            {
                ModernGui.drawScaledCustomSizeModalRect((float)this.mapMode.x - 2.0F, (float)this.mapMode.y - 2.0F, (float)(25 * GenericOverride.GUI_SCALE), (float)(31 * GenericOverride.GUI_SCALE), 157 * GenericOverride.GUI_SCALE, 209 * GenericOverride.GUI_SCALE, this.mapMode.w + 5, this.mapMode.w + 35, (float)(1920 * GenericOverride.GUI_SCALE), (float)(1033 * GenericOverride.GUI_SCALE), false);
            }

            int offsetY = this.mapMode.y + this.mapMode.h + 2;
            String diffTime;

            if (!ClientData.currentWarzone.isEmpty())
            {
                ModernGui.drawScaledStringCustomFont("WARZONE " + ((String)ClientData.currentWarzone.get("name")).toUpperCase(), (float)this.mapMode.x, (float)offsetY, 16777215, 0.35F, "left", false, "minecraftDungeons", 30);
                ModernGui.drawScaledStringCustomFont(I18n.getString("warzone.bonus." + (String)ClientData.currentWarzone.get("type") + ".value").toUpperCase(), (float)this.mapMode.x, (float)(offsetY + 8), 16777215, 0.35F, "left", false, "minecraftDungeons", 22);
                offsetY += 22;
            }
            else if (!ClientData.countryTitleInfos.isEmpty())
            {
                byte colorTextDefault = 0;

                if (!((String)ClientData.countryTitleInfos.get("countryName")).toLowerCase().contains("wilderness"))
                {
                    ClientProxy.loadCountryFlag((String)ClientData.countryTitleInfos.get("countryName"));

                    if (ClientProxy.flagsTexture.containsKey(ClientData.countryTitleInfos.get("countryName")))
                    {
                        GL11.glBindTexture(GL11.GL_TEXTURE_2D, ((DynamicTexture)ClientProxy.flagsTexture.get(ClientData.countryTitleInfos.get("countryName"))).getGlTextureId());
                        ModernGui.drawScaledCustomSizeModalRect((float)this.mapMode.x, (float)(offsetY + 2), 0.0F, 0.0F, 156, 78, 19, 12, 156.0F, 78.0F, false);
                        colorTextDefault = 21;
                    }
                }

                diffTime = ((String)ClientData.countryTitleInfos.get("countryName")).toUpperCase().replaceAll("^\u00a7.{1}", "").replaceAll("EMPIRE", "EMP");
                ModernGui.drawScaledStringCustomFont(diffTime.length() > 10 ? diffTime.substring(0, 9) + ".." : diffTime, (float)(this.mapMode.x + colorTextDefault), (float)(offsetY + 1), 16777215, 0.35F, "left", true, "minecraftDungeons", 24);
                ModernGui.glColorHex(((Integer)GenericOverride.relationsColor.get(ClientData.countryTitleInfos.get("relation"))).intValue(), 1.0F);
                ModernGui.drawRectangle((float)(this.mapMode.x + colorTextDefault), (float)(offsetY + 8), 0.0F, 26.0F, 6.0F);
                ModernGui.drawScaledStringCustomFont(I18n.getString("overlay.faction.relation.short." + (String)ClientData.countryTitleInfos.get("relation")).toUpperCase(), (float)(this.mapMode.x + colorTextDefault + 13), (float)(offsetY + 8), 16777215, 0.3F, "center", false, "minecraftDungeons", 23);
                offsetY += 22;
            }
            else
            {
                ModernGui.drawScaledStringCustomFont(ClientProxy.currentServerName.toUpperCase(), (float)this.mapMode.x, (float)offsetY, 16777215, 0.35F, "left", true, "minecraftDungeons", 30);
                offsetY += 22;
            }

            int colorTextDefault1 = 12237530;

            if (ModernGui.cachedOverlayMainTexture.equalsIgnoreCase("overlay_main_spartan_white"))
            {
                colorTextDefault1 = 0;
            }

            ModernGui.drawScaledStringCustomFont(this.mw.coordsMode == 1 ? this.mw.playerXInt + " " + this.mw.playerYInt + " " + this.mw.playerZInt : "-", (float)this.mapMode.x, (float)offsetY, colorTextDefault1, 0.5F, "left", false, "georamaBold", 24);
            ModernGui.drawScaledStringCustomFont(this.getOrientation(Minecraft.getMinecraft().thePlayer.rotationYaw), (float)(this.mapMode.x + this.mapMode.w), (float)offsetY, colorTextDefault1, 0.5F, "right", false, "georamaBold", 24);
            offsetY += 10;

            if (ClientProxy.clientConfig.specialEnabled)
            {
                ModernGui.drawScaledStringCustomFont(PingThread.ping + " ms", (float)this.mapMode.x, (float)offsetY, colorTextDefault1, 0.5F, "left", false, "georamaSemiBold", 25);
                ModernGui.drawScaledStringCustomFont(ClientEventHandler.lastClicks + " cps", (float)this.mapMode.x, (float)(offsetY + 7), colorTextDefault1, 0.5F, "left", false, "georamaSemiBold", 25);
                ModernGui.drawScaledStringCustomFont(Minecraft.getMinecraft().debug.split(",")[0], (float)this.mapMode.x, (float)(offsetY + 14), colorTextDefault1, 0.5F, "left", false, "georamaSemiBold", 25);
                ModernGui.drawScaledStringCustomFont("Sprint " + (ClientKeyHandler.toggleSprintEnabled ? "\u00a7aON" : "\u00a7cOFF"), (float)(this.mapMode.x + this.mapMode.w), (float)offsetY, colorTextDefault1, 0.5F, "right", false, "georamaSemiBold", 25);
                Date diffTime1 = new Date(ClientData.serverTime.longValue() + (System.currentTimeMillis() - ClientData.clientTimeWhenServerTimeReceived.longValue()));
                ModernGui.drawScaledStringCustomFont(formatterDate.format(diffTime1), (float)(this.mapMode.x + this.mapMode.w), (float)(offsetY + 7), colorTextDefault1, 0.5F, "right", false, "georamaSemiBold", 25);
                ModernGui.drawScaledStringCustomFont(formatterTime.format(diffTime1), (float)(this.mapMode.x + this.mapMode.w), (float)(offsetY + 14), colorTextDefault1, 0.5F, "right", false, "georamaSemiBold", 25);
                offsetY += 25;
            }

            if (!ClientData.currentWarzone.isEmpty())
            {
                ModernGui.bindTextureOverlayMain();
                ModernGui.drawScaledCustomSizeModalRect((float)this.mapMode.x - 2.0F, (float)offsetY, (float)(25 * GenericOverride.GUI_SCALE), (float)(304 * GenericOverride.GUI_SCALE), 157 * GenericOverride.GUI_SCALE, 42 * GenericOverride.GUI_SCALE, this.mapMode.w + 5, 21, (float)(1920 * GenericOverride.GUI_SCALE), (float)(1033 * GenericOverride.GUI_SCALE), false);
                diffTime = I18n.getString("warzone.captured");
                boolean areaPosition = false;

                if (((String)ClientData.currentWarzone.get("factionCapturingId")).equalsIgnoreCase("notcontested"))
                {
                    diffTime = I18n.getString("warzone.notcontested");
                }
                else if (((String)ClientData.currentWarzone.get("factionCapturingId")).equalsIgnoreCase("contested"))
                {
                    diffTime = I18n.getString("warzone.contested");
                }
                else if (!((String)ClientData.currentWarzone.get("percent")).equalsIgnoreCase("100") && !((String)ClientData.currentWarzone.get("factionCapturingId")).equals(ClientData.currentWarzone.get("factionId")))
                {
                    diffTime = I18n.getString("warzone.capturing");
                    areaPosition = true;
                }
                else if (!((String)ClientData.currentWarzone.get("percent")).equalsIgnoreCase("100") && ((String)ClientData.currentWarzone.get("factionCapturingId")).equals(ClientData.currentWarzone.get("factionId")))
                {
                    diffTime = I18n.getString("warzone.securisation");
                }

                if (areaPosition)
                {
                    diffTime = diffTime + " (" + (String)ClientData.currentWarzone.get("factionCapturingName") + ")";
                }

                ModernGui.drawScaledStringCustomFont("Status", (float)this.mapMode.x + 1.0F, (float)(offsetY + 3), 16000586, 0.5F, "left", true, "minecraftDungeons", 15);
                ModernGui.drawScaledStringCustomFont(diffTime, (float)this.mapMode.x + 3.0F + minecraftDungeons15.getStringWidth("Status") / 2.0F, (float)offsetY + 3.5F, 16777215, 0.5F, "left", true, "georamaSemiBold", 20);
                ModernGui.drawScaledStringCustomFont(I18n.getString("overlay.warzone.package"), (float)this.mapMode.x + 1.0F, (float)(offsetY + 11), 16000586, 0.5F, "left", true, "minecraftDungeons", 15);
                ModernGui.drawScaledStringCustomFont((String)ClientData.currentWarzone.get("nextTimeDropPackage"), (float)this.mapMode.x + 3.0F + minecraftDungeons15.getStringWidth(I18n.getString("overlay.warzone.package")) / 2.0F, (float)offsetY + 11.5F, 16777215, 0.5F, "left", true, "georamaSemiBold", 20);
                offsetY += 25;
            }
            else if (!ClientData.currentAssault.isEmpty() && (!((String)ClientData.currentAssault.get("areaX")).equals("0") || !((String)ClientData.currentAssault.get("areaZ")).equals("0") || !((String)ClientData.currentAssault.get("packageX")).equals("0") || !((String)ClientData.currentAssault.get("packageZ")).equals("0")))
            {
                ModernGui.bindTextureOverlayMain();
                ModernGui.drawScaledCustomSizeModalRect((float)this.mapMode.x - 2.0F, (float)offsetY, (float)(25 * GenericOverride.GUI_SCALE), (float)(304 * GenericOverride.GUI_SCALE), 157 * GenericOverride.GUI_SCALE, 42 * GenericOverride.GUI_SCALE, this.mapMode.w + 5, 21, (float)(1920 * GenericOverride.GUI_SCALE), (float)(1033 * GenericOverride.GUI_SCALE), false);
                ModernGui.drawScaledStringCustomFont(I18n.getString("overlay.assault.label.package"), (float)this.mapMode.x + 1.0F, (float)(offsetY + 3), 16000586, 0.5F, "left", true, "minecraftDungeons", 15);
                diffTime = ((String)ClientData.currentAssault.get("packageX")).equals("0") && ((String)ClientData.currentAssault.get("packageZ")).equals("0") ? "-" : "X: " + (String)ClientData.currentAssault.get("packageX") + ", Z: " + (String)ClientData.currentAssault.get("packageZ");
                ModernGui.drawScaledStringCustomFont(diffTime, (float)this.mapMode.x + 3.0F + minecraftDungeons15.getStringWidth(I18n.getString("overlay.assault.label.package")) / 2.0F, (float)(offsetY + 4), 16777215, 0.5F, "left", true, "georamaSemiBold", 18);
                ModernGui.drawScaledStringCustomFont(I18n.getString("overlay.assault.label.area"), (float)this.mapMode.x + 1.0F, (float)(offsetY + 11), 16000586, 0.5F, "left", true, "minecraftDungeons", 15);
                String areaPosition1 = ((String)ClientData.currentAssault.get("areaX")).equals("0") && ((String)ClientData.currentAssault.get("areaZ")).equals("0") ? "-" : "X: " + (String)ClientData.currentAssault.get("areaX") + ", Z: " + (String)ClientData.currentAssault.get("areaZ");
                ModernGui.drawScaledStringCustomFont(areaPosition1, (float)this.mapMode.x + 3.0F + minecraftDungeons15.getStringWidth(I18n.getString("overlay.assault.label.area")) / 2.0F, (float)(offsetY + 12), 16777215, 0.5F, "left", true, "georamaSemiBold", 18);
                offsetY += 25;
            }

            if (ClientData.isCombatTagged)
            {
                ModernGui.bindTextureOverlayMain();
                ModernGui.drawScaledCustomSizeModalRect((float)this.mapMode.x - 2.0F, (float)offsetY, (float)(25 * GenericOverride.GUI_SCALE), (float)(612 * GenericOverride.GUI_SCALE), 157 * GenericOverride.GUI_SCALE, 26 * GenericOverride.GUI_SCALE, this.mapMode.w + 5, 13, (float)(1920 * GenericOverride.GUI_SCALE), (float)(1033 * GenericOverride.GUI_SCALE), false);
                ModernGui.drawScaledStringCustomFont(I18n.getString("overlay.tag_combat"), (float)this.mapMode.x - 2.0F + (float)((this.mapMode.w + 5) / 2), (float)(offsetY + 3), 16000586, 0.5F, "center", true, "minecraftDungeons", 15);
            }

            if (System.currentTimeMillis() > 1734166800000L && System.currentTimeMillis() < 1736722800000L)
            {
                ModernGui.bindTextureOverlayMain();
                ModernGui.drawScaledCustomSizeModalRect((float)this.mapMode.x - 30.0F, (float)(this.mapMode.y - 5), (float)(1439 * GenericOverride.GUI_SCALE), (float)(0 * GenericOverride.GUI_SCALE), 80 * GenericOverride.GUI_SCALE, 80 * GenericOverride.GUI_SCALE, 25, 25, (float)(1920 * GenericOverride.GUI_SCALE), (float)(1033 * GenericOverride.GUI_SCALE), false);
                ModernGui.drawScaledStringCustomFont(Keyboard.getKeyName(ClientKeyHandler.KEY_BONUS.keyCode), (float)this.mapMode.x - 30.0F + 18.5F, (float)(this.mapMode.y - 5) + 17.0F, 12588865, 0.5F, "center", false, "georamaBold", 13);
            }
            else if (System.currentTimeMillis() >= ClientData.bonusStartTime.longValue() && System.currentTimeMillis() <= ClientData.bonusEndTime.longValue())
            {
                ModernGui.bindTextureOverlayMain();
                ModernGui.drawScaledCustomSizeModalRect((float)this.mapMode.x - 30.0F, (float)(this.mapMode.y - 5), (float)(1519 * GenericOverride.GUI_SCALE), (float)(0 * GenericOverride.GUI_SCALE), 80 * GenericOverride.GUI_SCALE, 80 * GenericOverride.GUI_SCALE, 25, 25, (float)(1920 * GenericOverride.GUI_SCALE), (float)(1033 * GenericOverride.GUI_SCALE), false);
                ModernGui.drawScaledStringCustomFont(Keyboard.getKeyName(ClientKeyHandler.KEY_BONUS.keyCode), (float)this.mapMode.x - 30.0F + 18.5F, (float)(this.mapMode.y - 5) + 17.0F, 15712091, 0.5F, "center", false, "georamaBold", 13);
            }

            if (ClientData.waitingServerName != null && !ClientData.waitingServerName.isEmpty() && !ClientData.waitingServerName.equalsIgnoreCase("null"))
            {
                ClientEventHandler.STYLE.bindTexture("overlay_hud");
                ModernGui.drawScaledCustomSizeModalRect((float)this.mapMode.x - 93.0F, (float)(this.mapMode.y - 2), 813.0F, 0.0F, 216, 114, 86, 45, 1920.0F, 1033.0F, false);
                ModernGui.drawScaledCustomSizeModalRect((float)this.mapMode.x - 93.0F - 7.0F, (float)(this.mapMode.y - 2), 1030.0F, 0.0F, 14, 14, 5, 5, 1920.0F, 1033.0F, false);
                ModernGui.drawScaledStringCustomFont(Keyboard.getKeyName(ClientKeyHandler.KEY_WAITING.keyCode), (float)this.mapMode.x - 93.0F - 7.0F + 2.0F, (float)(this.mapMode.y - 5) + 4.5F, 7239406, 0.5F, "center", false, "georamaBold", 13);
                ModernGui.drawScaledStringCustomFont(I18n.getString("waiting.server") + " " + ClientData.waitingServerName.toUpperCase(), (float)this.mapMode.x - 93.0F + 7.0F, (float)(this.mapMode.y - 2 + 5), 7239406, 0.5F, "left", false, "minecraftDungeons", 24);
                ModernGui.drawScaledStringCustomFont("\u00a7o" + (ClientData.waitingPriority ? I18n.getString("waiting.list.priority") : I18n.getString("waiting.list.classic")), (float)this.mapMode.x - 93.0F + 7.0F, (float)(this.mapMode.y - 2 + 16), 14277081, 0.5F, "left", false, "georamaRegular", 24);
                long diffTime2 = System.currentTimeMillis() - ClientData.waitingJoinTime.longValue();
                diffTime2 = diffTime2 / 1000L / 60L;
                ModernGui.drawScaledStringCustomFont(ClientData.waitingPosition + " / \u00a77" + ClientData.waitingTotal, (float)this.mapMode.x - 93.0F + 7.0F, (float)(this.mapMode.y - 2 + 27), 16777215, 0.5F, "left", false, "georamaSemiBold", 25);
                ModernGui.drawScaledStringCustomFont("\u00a7o" + diffTime2 + " minutes", (float)this.mapMode.x - 93.0F + 7.0F, (float)(this.mapMode.y - 2 + 35), 14277081, 0.5F, "left", false, "georamaRegular", 20);
            }
        }
    }

    public String getOrientation(float yaw)
    {
        String[] directions = new String[] {"S", "SO", "O", "NO", "N", "NE", "E", "SE"};
        int index = Math.round(yaw / 45.0F) % 8;

        if (index < 0)
        {
            index = (index + 8) % 8;
        }

        return directions[index];
    }

    private void drawPlayerArrow()
    {
        GL11.glPushMatrix();
        double scale = this.mapView.getDimensionScaling(this.mw.playerDimension);
        Double p = this.mapMode.getClampedScreenXY(this.mapView, this.mw.playerX * scale, this.mw.playerZ * scale);
        this.playerArrowScreenPos.setLocation(p.x + (double)this.mapMode.xTranslation, p.y + (double)this.mapMode.yTranslation);
        GL11.glTranslated(p.x, p.y, 0.0D);

        if (!this.mapMode.rotate)
        {
            GL11.glRotated(-this.mw.mapRotationDegrees, 0.0D, 0.0D, 1.0D);
        }

        double arrowSize = (double)this.mapMode.playerArrowSize;
        Render.setColour(-1);
        this.mw.mc.renderEngine.bindTexture(this.playerArrowTexture);
        Render.drawTexturedRect(-arrowSize, -arrowSize, arrowSize * 2.0D, arrowSize * 2.0D, 0.0D, 0.0D, 1.0D, 1.0D);
        GL11.glPopMatrix();
    }

    private void drawIcons()
    {
        GL11.glPushMatrix();

        if (this.mapMode.rotate)
        {
            GL11.glRotated(this.mw.mapRotationDegrees, 0.0D, 0.0D, 1.0D);
        }

        this.mw.markerManager.drawMarkers(this.mapMode, this.mapView);

        if (this.mw.playerTrail.enabled)
        {
            this.mw.playerTrail.draw(this.mapMode, this.mapView);
        }

        if (this.mapMode.rotate)
        {
            double y = (double)this.mapMode.h / 2.0D;
            double arrowSize = (double)this.mapMode.playerArrowSize;
            Render.setColour(-1);
            this.mw.mc.renderEngine.bindTexture(this.northArrowTexture);
            Render.drawTexturedRect(-arrowSize, -y - arrowSize * 2.0D, arrowSize * 2.0D, arrowSize * 2.0D, 0.0D, 0.0D, 1.0D, 1.0D);
        }

        GL11.glPopMatrix();
        this.drawPlayerArrow();
    }

    private void drawCoords()
    {
        if (this.mapMode.coordsEnabled)
        {
            GL11.glPushMatrix();
            GL11.glTranslatef((float)this.mapMode.textX, (float)this.mapMode.textY, 0.0F);

            if (this.mw.coordsMode != 2)
            {
                GL11.glScalef(0.5F, 0.5F, 1.0F);
            }

            int offset = 0;

            if (this.mw.coordsMode > 0)
            {
                Render.drawCentredString(0, 0, this.mapMode.textColour, "%d, %d, %d", new Object[] {Integer.valueOf(this.mw.playerXInt), Integer.valueOf(this.mw.playerYInt), Integer.valueOf(this.mw.playerZInt)});
                offset += 12;
            }

            if (this.mw.undergroundMode)
            {
                Render.drawCentredString(0, offset, this.mapMode.textColour, "underground mode", new Object[0]);
            }

            GL11.glPopMatrix();
        }
    }

    private IMwDataProvider drawOverlay()
    {
        IMwDataProvider provider = MwAPI.getCurrentDataProvider();

        if (provider != null)
        {
            ArrayList overlays = provider.getChunksOverlay(this.mapView.getDimension(), this.mapView.getX(), this.mapView.getZ(), this.mapView.getMinX(), this.mapView.getMinZ(), this.mapView.getMaxX(), this.mapView.getMaxZ());

            if (overlays != null)
            {
                Iterator var3 = overlays.iterator();

                while (var3.hasNext())
                {
                    IMwChunkOverlay overlay = (IMwChunkOverlay)var3.next();
                    paintChunk(this.mapMode, this.mapView, overlay);
                }
            }
        }

        return provider;
    }

    public void draw()
    {
        if (!ClientProxy.currentServerName.equals("mmr"))
        {
            if (System.currentTimeMillis() - ClientEventHandler.lastPlayerDispayTAB.longValue() > 50L)
            {
                this.mapMode.setScreenRes();
                this.mapView.setMapWH(this.mapMode);
                this.mapView.setTextureSize(this.mw.textureSize);
                GL11.glPushMatrix();
                GL11.glLoadIdentity();
                GL11.glTranslated((double)this.mapMode.xTranslation, (double)this.mapMode.yTranslation, -2000.0D);

                if (this.mapMode.borderMode > 0)
                {
                    this.drawGenericOverlay();
                }

                this.drawMapTexture();
                this.drawIcons();
                IMwDataProvider provider = this.drawOverlay();
                GL11.glEnable(GL11.GL_DEPTH_TEST);
                GL11.glPopMatrix();

                if (provider != null)
                {
                    GL11.glPushMatrix();
                    provider.onDraw(this.mapView, this.mapMode);
                    GL11.glPopMatrix();
                }
            }
        }
    }
}
