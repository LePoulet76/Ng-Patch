/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.warzone;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarGeneric;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.WarzoneTPPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.WarzonesDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.WarzonesLeaderboardDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class WarzonesGui
extends GuiScreen {
    public static int GUI_SCALE = 3;
    public static HashMap<String, Object> data = new HashMap();
    public static boolean loaded = false;
    public static boolean loadedRanking = false;
    private RenderItem itemRenderer = new RenderItem();
    public static String displayMode = "bateau";
    public static String rankingMode = "daily";
    public static int dollarsDailyLimit;
    public static int maxPowerboost;
    public static int maxSkillboost;
    private GuiScrollBarGeneric scrollBar;
    public static HashMap<String, String> bateauInfos;
    public static HashMap<String, String> petrolInfos;
    public static HashMap<String, String> mineInfos;
    public static HashMap<String, String> scoreInfos;
    public static HashMap<String, Object> rankingAllInfos;
    public static LinkedHashMap<String, Integer> bgOffsetY;
    public static LinkedHashMap<String, Integer> renderOffsetY;
    public static HashMap<String, String> warzoneInfos;
    public static LinkedHashMap<String, Integer> rankingPanelOffsetX;
    public List<String> WARZONES = Arrays.asList("bateau", "petrol", "mine");
    public String hoveredAction = "";
    protected int xSize = 463;
    protected int ySize = 235;
    private int guiLeft;
    private int guiTop;

    public WarzonesGui() {
        loaded = false;
        loadedRanking = false;
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new WarzonesDataPacket()));
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new WarzonesLeaderboardDataPacket()));
        this.guiLeft = (this.field_73880_f - this.xSize) / 2;
        this.guiTop = (this.field_73881_g - this.ySize) / 2;
        this.scrollBar = new GuiScrollBarGeneric(this.guiLeft + 448, this.guiTop + 104, 113, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
    }

    public void func_73863_a(int mouseX, int mouseY, float par3) {
        ArrayList<String> warzones = new ArrayList<String>(Arrays.asList("bateau", "petrol", "mine"));
        ArrayList<String> rankingModes = new ArrayList<String>(Arrays.asList("daily", "weekly"));
        LinkedHashMap<String, Integer> rewardPanelOffsetX = new LinkedHashMap<String, Integer>(){
            {
                this.put("bateau", 339);
                this.put("petrol", 376);
                this.put("mine", 412);
            }
        };
        warzones.remove(displayMode);
        rankingModes.remove(rankingMode);
        ArrayList<String> tooltipToDraw = new ArrayList<String>();
        this.hoveredAction = "";
        String factionName = ClientData.currentFaction;
        switch (displayMode) {
            case "bateau": {
                warzoneInfos = bateauInfos;
                break;
            }
            case "petrol": {
                warzoneInfos = petrolInfos;
                break;
            }
            case "mine": {
                warzoneInfos = mineInfos == null ? petrolInfos : mineInfos;
            }
        }
        boolean unknownWarzone = warzoneInfos.size() == 0;
        this.func_73873_v_();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        ClientEventHandler.STYLE.bindTexture("warzone");
        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft, this.guiTop, 0.0f, bgOffsetY.get(displayMode) * GUI_SCALE, 465 * GUI_SCALE, 235 * GUI_SCALE, 465, 235, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
        GUIUtils.startGLScissor(this.guiLeft + 14, this.guiTop, 179, 179);
        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 12, this.guiTop - 35, 483 * GUI_SCALE, renderOffsetY.get(displayMode) * GUI_SCALE, 179 * GUI_SCALE, 179 * GUI_SCALE, 179, 179, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
        GUIUtils.endGLScissor();
        if (mouseX >= this.guiLeft + 444 && mouseX <= this.guiLeft + 444 + 10 && mouseY >= this.guiTop + 13 && mouseY <= this.guiTop + 13 + 10) {
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 444, this.guiTop + 13, 169 * GUI_SCALE, 747 * GUI_SCALE, 10 * GUI_SCALE, 10 * GUI_SCALE, 10, 10, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
            this.hoveredAction = "close";
        }
        ModernGui.drawScaledStringCustomFont("WARZONE", this.guiLeft + 12, this.guiTop + 14, 0xFFFFFF, 1.0f, "left", false, "georamaSemiBold", 30);
        ClientEventHandler.STYLE.bindTexture("warzone");
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("warzone.name." + displayMode)), this.guiLeft + 205, this.guiTop + 49, 0xFFFFFF, 0.75f, "left", false, "georamaSemiBold", 25);
        ClientEventHandler.STYLE.bindTexture("warzone");
        if (loaded) {
            String ownerFactionName = unknownWarzone || warzoneInfos.get("factionName").equals("Neutre") ? I18n.func_135053_a((String)"warzone.zone.unowned") : warzoneInfos.get("factionName").replace('&', '\u00a7');
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 205, this.guiTop + 66, 0.0f, 940 * GUI_SCALE, 101 * GUI_SCALE, 20 * GUI_SCALE, 101, 20, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
            ClientProxy.loadCountryFlag(ownerFactionName);
            if (mouseX >= this.guiLeft + 205 && mouseX <= this.guiLeft + 205 + 67 && mouseY >= this.guiTop + 121 && mouseY <= this.guiTop + 121 + 12) {
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 205, this.guiTop + 121, 0.0f, 765 * GUI_SCALE, 67 * GUI_SCALE, 12 * GUI_SCALE, 67, 12, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"warzone.button.tp"), this.guiLeft + 239, this.guiTop + 124, 0, 0.5f, "center", false, "georamaSemiBold", 25);
                this.hoveredAction = "teleport";
            } else {
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"warzone.button.tp"), this.guiLeft + 239, this.guiTop + 124, 0xFFFFFF, 0.5f, "center", false, "georamaSemiBold", 25);
            }
            ClientEventHandler.STYLE.bindTexture("warzone");
            if (ownerFactionName.equals(I18n.func_135053_a((String)"warzone.zone.unowned"))) {
                Gui.func_73734_a((int)(this.guiLeft + 210), (int)(this.guiTop + 70), (int)(this.guiLeft + 210 + 18), (int)(this.guiTop + 70 + 12), (int)-1);
            } else if (ClientProxy.flagsTexture.containsKey(ownerFactionName)) {
                GL11.glBindTexture((int)3553, (int)ClientProxy.flagsTexture.get(ownerFactionName).func_110552_b());
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 210, this.guiTop + 70, 0.0f, 0.0f, 156, 78, 18, 12, 156.0f, 78.0f, false);
            }
            ClientEventHandler.STYLE.bindTexture("warzone");
            ModernGui.drawScaledStringCustomFont(ownerFactionName.equals(I18n.func_135053_a((String)"warzone.zone.unowned")) ? ownerFactionName : this.factionNameShortener(ownerFactionName), this.guiLeft + 233, this.guiTop + 69, 0xFFFFFF, 0.5f, "left", false, "georamaMedium", 30);
            long lastTimeOwned = unknownWarzone ? 0L : Long.parseLong(warzoneInfos.get("lastTimeOwned"));
            ModernGui.drawScaledStringCustomFont((warzoneInfos.containsKey("percent") ? warzoneInfos.get("percent") : "0") + "% - " + I18n.func_135053_a((String)"warzone.age.msg") + " " + ModernGui.formatDelayTime(lastTimeOwned), this.guiLeft + 233, this.guiTop + 77, 10395075, 0.5f, "left", false, "georamaMedium", 24);
            ModernGui.drawSectionStringCustomFont(I18n.func_135053_a((String)("warzone.desc." + displayMode)), this.guiLeft + 205, this.guiTop + 88, 10395075, 0.5f, "left", false, "georamaMedium", 25, 7, 200);
            for (int i = 0; i < warzones.size(); ++i) {
                long lastTimeOtherZoneOwned;
                int offsetX = i * 160;
                HashMap<String, String> infos = warzones.get(i).equals("bateau") ? bateauInfos : (warzones.get(i).equals("petrol") ? petrolInfos : mineInfos);
                String percent = infos.containsKey("percent") ? infos.get("percent") : "0";
                String ownerOtherZoneFactionName = I18n.func_135053_a((String)"warzone.zone.unowned");
                long l = lastTimeOtherZoneOwned = infos.get("lastTimeOwned") == null ? System.currentTimeMillis() : Long.parseLong(infos.get("lastTimeOwned"));
                if (infos.get("factionName") != null) {
                    ownerOtherZoneFactionName = infos.get("factionName").equals("Neutre") ? I18n.func_135053_a((String)"warzone.zone.unowned") : infos.get("factionName").replace('&', '\u00a7');
                }
                ClientEventHandler.STYLE.bindTexture("warzone");
                if (mouseX >= this.guiLeft + 14 + offsetX && mouseX <= this.guiLeft + 14 + offsetX + 158 && mouseY >= this.guiTop + 153 && mouseY <= this.guiTop + 153 + 68) {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 14 + offsetX, this.guiTop + 153, 0.0f, 786 * GUI_SCALE, 158 * GUI_SCALE, 68 * GUI_SCALE, 158, 68, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                    ModernGui.drawScaledStringCustomFont(ownerOtherZoneFactionName.equals(I18n.func_135053_a((String)"warzone.zone.unowned")) ? I18n.func_135053_a((String)"warzone.zone.unowned") : this.factionNameShortener(ownerOtherZoneFactionName), this.guiLeft + 50 + offsetX, this.guiTop + 199, 0, 0.5f, "left", false, "georamaMedium", 27);
                    ModernGui.drawScaledStringCustomFont(percent + "% - " + I18n.func_135053_a((String)"warzone.age.msg") + " " + ModernGui.formatDelayTime(lastTimeOtherZoneOwned), this.guiLeft + 50 + offsetX, this.guiTop + 207, 0, 0.5f, "left", false, "georamaMedium", 23);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("warzone.name." + warzones.get(i))), this.guiLeft + 19 + offsetX, this.guiTop + 160, 0, 0.5f, "left", false, "georamaSemiBold", 30);
                    ModernGui.drawSectionStringCustomFont(I18n.func_135053_a((String)("warzone.desc." + warzones.get(i))), this.guiLeft + 19 + offsetX, this.guiTop + 171, 0, 0.5f, "left", false, "georamaSemiBold", 20, 6, 200);
                    this.hoveredAction = warzones.get(i);
                } else {
                    ModernGui.drawScaledStringCustomFont(ownerOtherZoneFactionName.equals(I18n.func_135053_a((String)"warzone.zone.unowned")) ? I18n.func_135053_a((String)"warzone.zone.unowned") : this.factionNameShortener(ownerOtherZoneFactionName), this.guiLeft + 50 + offsetX, this.guiTop + 199, 0xFFFFFF, 0.5f, "left", false, "georamaMedium", 27);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("warzone.name." + warzones.get(i))), this.guiLeft + 19 + offsetX, this.guiTop + 160, 0xFFFFFF, 0.5f, "left", false, "georamaSemiBold", 30);
                    ModernGui.drawScaledStringCustomFont(percent + "% - " + I18n.func_135053_a((String)"warzone.age.msg") + " " + ModernGui.formatDelayTime(lastTimeOtherZoneOwned), this.guiLeft + 50 + offsetX, this.guiTop + 207, 10395075, 0.5f, "left", false, "georamaMedium", 23);
                    ModernGui.drawSectionStringCustomFont(I18n.func_135053_a((String)("warzone.desc." + warzones.get(i))), this.guiLeft + 19 + offsetX, this.guiTop + 171, 10395075, 0.5f, "left", false, "georamaSemiBold", 20, 6, 200);
                }
                if (ownerOtherZoneFactionName.equals(I18n.func_135053_a((String)"warzone.zone.unowned"))) {
                    Gui.func_73734_a((int)(this.guiLeft + 23 + offsetX), (int)(this.guiTop + 199), (int)(this.guiLeft + 23 + offsetX + 23), (int)(this.guiTop + 199 + 14), (int)-1);
                    continue;
                }
                ClientProxy.loadCountryFlag(ownerOtherZoneFactionName);
                if (ClientProxy.flagsTexture.containsKey(ownerOtherZoneFactionName)) {
                    GL11.glBindTexture((int)3553, (int)ClientProxy.flagsTexture.get(ownerOtherZoneFactionName).func_110552_b());
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 23 + offsetX, this.guiTop + 199, 0.0f, 0.0f, 156, 78, 23, 14, 156.0f, 78.0f, false);
                }
                ClientEventHandler.STYLE.bindTexture("warzone");
            }
            if (loadedRanking) {
                Double rank;
                GUIUtils.startGLScissor(this.guiLeft + 340, this.guiTop + 106, 112, 112);
                ArrayList daily = (ArrayList)rankingAllInfos.get("daily");
                ArrayList weekly = (ArrayList)rankingAllInfos.get("weekly");
                ArrayList rankingInfos = rankingMode.equals("daily") ? daily : weekly;
                int position = 1;
                for (int i = 0; i < rankingInfos.size(); ++i) {
                    int offsetX = this.guiLeft + 340;
                    Float offsetY = Float.valueOf((float)(this.guiTop + 108 + i * 11) + this.getSlide(rankingInfos));
                    ArrayList<String> lineInfos = new ArrayList<String>(Arrays.asList(((String)rankingInfos.get(i)).split("##")));
                    String targetFactionName = lineInfos.get(0);
                    String totalScore = lineInfos.get(1);
                    ModernGui.drawScaledStringCustomFont(String.valueOf(position), offsetX + 5, offsetY.intValue(), 0xFFFFFF, 0.5f, "center", false, "georamaSemiBold", 30);
                    ClientProxy.loadCountryFlag(targetFactionName);
                    if (ClientProxy.flagsTexture.containsKey(targetFactionName)) {
                        GL11.glBindTexture((int)3553, (int)ClientProxy.flagsTexture.get(targetFactionName).func_110552_b());
                        ModernGui.drawScaledCustomSizeModalRect(offsetX + 12, offsetY.intValue(), 0.0f, 0.0f, 156, 78, 13, 8, 156.0f, 78.0f, false);
                    }
                    ModernGui.drawScaledStringCustomFont(this.factionNameShortener(targetFactionName), offsetX + 28, offsetY.intValue(), 0xFFFFFF, 0.5f, "left", false, "georamaSemiBold", 30);
                    ModernGui.drawScaledStringCustomFont(totalScore, offsetX + 103, offsetY.intValue(), 10395075, 0.5f, "right", false, "georamaMedium", 30);
                    double scoreWidth = (double)ModernGui.getCustomFont("georamaMedium", 30).getStringWidth(totalScore) * 0.5;
                    if ((double)mouseX >= (double)(offsetX + 103) - scoreWidth && mouseX <= offsetX + 103 && mouseY >= offsetY.intValue() && mouseY <= offsetY.intValue() + 10) {
                        tooltipToDraw.add("\u00a77" + I18n.func_135053_a((String)"warzone.bateau") + ": \u00a7r" + lineInfos.get(2));
                        tooltipToDraw.add("\u00a77" + I18n.func_135053_a((String)"warzone.petrol") + ": \u00a7r" + lineInfos.get(3));
                        tooltipToDraw.add("\u00a77" + I18n.func_135053_a((String)"warzone.mine") + ": \u00a7r" + lineInfos.get(4));
                    }
                    ++position;
                }
                GUIUtils.endGLScissor();
                this.scrollBar.draw(mouseX, mouseY);
                ClientProxy.loadCountryFlag(factionName);
                if (ClientProxy.flagsTexture.containsKey(factionName)) {
                    GL11.glBindTexture((int)3553, (int)ClientProxy.flagsTexture.get(factionName).func_110552_b());
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 340, this.guiTop + 45, 0.0f, 0.0f, 156, 78, 30, 19, 156.0f, 78.0f, false);
                }
                ModernGui.drawScaledStringCustomFont(this.factionNameShortener(factionName), this.guiLeft + 375, this.guiTop + 45, 0xFFFFFF, 0.75f, "left", false, "georamaSemiBold", 22);
                Double d = rank = rankingMode.equals("daily") ? (Double)rankingAllInfos.get("dailyCountryPosition") : (Double)rankingAllInfos.get("weeklyCountryPosition");
                if (rank != 0.0) {
                    ModernGui.drawScaledStringCustomFont(rank.intValue() + (rank == 1.0 ? I18n.func_135053_a((String)"warzone.ranking.msg_first") : I18n.func_135053_a((String)"warzone.ranking.msg_general")), this.guiLeft + 375, this.guiTop + 55, 10395075, 0.5f, "left", false, "georamaSemiBold", 26);
                } else {
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"warzone.ranking.msg_unranked"), this.guiLeft + 375, this.guiTop + 55, 10395075, 0.5f, "left", false, "georamaSemiBold", 26);
                }
                ClientEventHandler.STYLE.bindTexture("warzone");
                for (Map.Entry pair : rewardPanelOffsetX.entrySet()) {
                    String wzName = (String)pair.getKey();
                    int offsetX = (Integer)pair.getValue();
                    if (wzName.equals(displayMode)) {
                        ClientEventHandler.STYLE.bindTexture("warzone");
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + offsetX, this.guiTop + 67, 126 * GUI_SCALE, 748 * GUI_SCALE, 35 * GUI_SCALE, 8 * GUI_SCALE, 35, 8, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                    }
                    ModernGui.drawScaledStringCustomFont(scoreInfos == null ? (wzName.equals("bateau") ? "0$" : (wzName.equals("petrol") ? "0 POWER" : "0% SKILL")) : (wzName.equals("bateau") ? scoreInfos.get("bateau") + " $$$" : (wzName.equals("petrol") ? scoreInfos.get("petrol") + " POWER" : (scoreInfos.get("mine") == null ? Integer.valueOf(0) : (Serializable)((Object)scoreInfos.get("mine"))) + "% SKILL")), this.guiLeft + offsetX + 18, this.guiTop + 69, 0xFFFFFF, 0.5f, "center", false, "georamaSemiBold", 20);
                    if (mouseX < this.guiLeft + offsetX || mouseX > this.guiLeft + offsetX + 35 || mouseY < this.guiTop + 67 || mouseY > this.guiTop + 67 + 8) continue;
                    String tooltip = "\u00a760/0";
                    if (scoreInfos.get(wzName) != null) {
                        tooltip = "\u00a76" + scoreInfos.get(wzName) + "/" + (wzName.equals("bateau") ? dollarsDailyLimit : (wzName.equals("petrol") ? maxPowerboost : maxSkillboost));
                    }
                    tooltipToDraw.add(tooltip);
                }
                ClientEventHandler.STYLE.bindTexture("warzone");
                if (mouseX >= this.guiLeft + 334 && mouseX <= this.guiLeft + 334 + 59 && mouseY >= this.guiTop + 88 && mouseY <= this.guiTop + 88 + 10) {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 334, this.guiTop + 88, 0 * GUI_SCALE, 746 * GUI_SCALE, 59 * GUI_SCALE, 10 * GUI_SCALE, 59, 10, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"warzone.ranking.daily"), this.guiLeft + 363, this.guiTop + 88 + 3, 0xFFFFFF, 0.5f, "center", false, "georamaSemiBold", 24);
                    ClientEventHandler.STYLE.bindTexture("warzone");
                    this.hoveredAction = "daily";
                } else if (rankingMode == "daily") {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 334, this.guiTop + 88, 0 * GUI_SCALE, 746 * GUI_SCALE, 59 * GUI_SCALE, 10 * GUI_SCALE, 59, 10, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"warzone.ranking.daily"), this.guiLeft + 363, this.guiTop + 88 + 3, 0xFFFFFF, 0.5f, "center", false, "georamaSemiBold", 24);
                } else {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 334, this.guiTop + 88, 63 * GUI_SCALE, 746 * GUI_SCALE, 59 * GUI_SCALE, 10 * GUI_SCALE, 59, 10, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"warzone.ranking.daily"), this.guiLeft + 363, this.guiTop + 88 + 3, 2499659, 0.5f, "center", false, "georamaSemiBold", 24);
                }
                ClientEventHandler.STYLE.bindTexture("warzone");
                if (mouseX >= this.guiLeft + 393 && mouseX <= this.guiLeft + 393 + 59 && mouseY >= this.guiTop + 88 && mouseY <= this.guiTop + 88 + 10) {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 393, this.guiTop + 88, 0 * GUI_SCALE, 746 * GUI_SCALE, 59 * GUI_SCALE, 10 * GUI_SCALE, 59, 10, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"warzone.ranking.weekly"), this.guiLeft + 363 + 59, this.guiTop + 88 + 3, 0xFFFFFF, 0.5f, "center", false, "georamaSemiBold", 24);
                    this.hoveredAction = "weekly";
                } else if (rankingMode == "weekly") {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 393, this.guiTop + 88, 0 * GUI_SCALE, 746 * GUI_SCALE, 59 * GUI_SCALE, 10 * GUI_SCALE, 59, 10, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"warzone.ranking.weekly"), this.guiLeft + 363 + 59, this.guiTop + 88 + 3, 0xFFFFFF, 0.5f, "center", false, "georamaSemiBold", 24);
                } else {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + rankingPanelOffsetX.get(rankingModes.get(0)), this.guiTop + 88, 63 * GUI_SCALE, 746 * GUI_SCALE, 59 * GUI_SCALE, 10 * GUI_SCALE, 59, 10, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"warzone.ranking.weekly"), this.guiLeft + 363 + 59, this.guiTop + 88 + 3, 2499659, 0.5f, "center", false, "georamaSemiBold", 24);
                }
            }
            if (!tooltipToDraw.isEmpty()) {
                this.drawHoveringText(tooltipToDraw, mouseX, mouseY, this.field_73886_k);
            }
        }
        super.func_73863_a(mouseX, mouseY, par3);
    }

    public boolean func_73868_f() {
        return false;
    }

    private String factionNameShortener(String factionName) {
        String res = factionName;
        if (factionName.length() > 12) {
            if (factionName.contains("Empire")) {
                res = factionName.replace("Empire", "Emp");
            }
            if (res.length() > 12) {
                res = res.substring(0, 9) + "..";
            }
        }
        return res;
    }

    private float getSlide(ArrayList<String> rankingInfos) {
        return rankingInfos.size() > 10 ? (float)(-(rankingInfos.size() - 10) * 11) * this.scrollBar.getSliderValue() : 0.0f;
    }

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            if (this.hoveredAction.equals("close")) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a(null);
            } else if (this.hoveredAction.equals("petrol")) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                displayMode = "petrol";
            } else if (this.hoveredAction.equals("daily")) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                rankingMode = "daily";
            } else if (this.hoveredAction.equals("weekly")) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                rankingMode = "weekly";
            } else if (this.hoveredAction.equals("mine")) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                displayMode = "mine";
            } else if (this.hoveredAction.equals("bateau")) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                displayMode = "bateau";
            } else if (this.hoveredAction.equals("teleport") && warzoneInfos != null && warzoneInfos.containsKey("tpLeft") && !warzoneInfos.get("tpLeft").equals("0")) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new WarzoneTPPacket(displayMode)));
                Minecraft.func_71410_x().func_71373_a(null);
            }
        }
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    protected void drawHoveringText(List par1List, int par2, int par3, FontRenderer font) {
        if (!par1List.isEmpty()) {
            GL11.glDisable((int)32826);
            RenderHelper.func_74518_a();
            GL11.glDisable((int)2896);
            GL11.glDisable((int)2929);
            int k = 0;
            for (String s : par1List) {
                int l = font.func_78256_a(s);
                if (l <= k) continue;
                k = l;
            }
            int i1 = par2 + 12;
            int j1 = par3 - 12;
            int k1 = 8;
            if (par1List.size() > 1) {
                k1 += 2 + (par1List.size() - 1) * 10;
            }
            if (i1 + k > this.field_73880_f) {
                i1 -= 28 + k;
            }
            if (j1 + k1 + 6 > this.field_73881_g) {
                j1 = this.field_73881_g - k1 - 6;
            }
            this.field_73735_i = 300.0f;
            this.itemRenderer.field_77023_b = 300.0f;
            int l1 = -267386864;
            this.func_73733_a(i1 - 3, j1 - 4, i1 + k + 3, j1 - 3, l1, l1);
            this.func_73733_a(i1 - 3, j1 + k1 + 3, i1 + k + 3, j1 + k1 + 4, l1, l1);
            this.func_73733_a(i1 - 3, j1 - 3, i1 + k + 3, j1 + k1 + 3, l1, l1);
            this.func_73733_a(i1 - 4, j1 - 3, i1 - 3, j1 + k1 + 3, l1, l1);
            this.func_73733_a(i1 + k + 3, j1 - 3, i1 + k + 4, j1 + k1 + 3, l1, l1);
            int i2 = 0x505000FF;
            int j2 = (i2 & 0xFEFEFE) >> 1 | i2 & 0xFF000000;
            this.func_73733_a(i1 - 3, j1 - 3 + 1, i1 - 3 + 1, j1 + k1 + 3 - 1, i2, j2);
            this.func_73733_a(i1 + k + 2, j1 - 3 + 1, i1 + k + 3, j1 + k1 + 3 - 1, i2, j2);
            this.func_73733_a(i1 - 3, j1 - 3, i1 + k + 3, j1 - 3 + 1, i2, i2);
            this.func_73733_a(i1 - 3, j1 + k1 + 2, i1 + k + 3, j1 + k1 + 3, j2, j2);
            for (int k2 = 0; k2 < par1List.size(); ++k2) {
                String s1 = (String)par1List.get(k2);
                font.func_78261_a(s1, i1, j1, -1);
                if (k2 == 0) {
                    j1 += 2;
                }
                j1 += 10;
            }
            this.field_73735_i = 0.0f;
            this.itemRenderer.field_77023_b = 0.0f;
            GL11.glDisable((int)2896);
            GL11.glDisable((int)2929);
            GL11.glEnable((int)32826);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        }
    }

    static {
        bateauInfos = new HashMap();
        petrolInfos = new HashMap();
        mineInfos = new HashMap();
        scoreInfos = new HashMap();
        rankingAllInfos = new HashMap();
        bgOffsetY = new LinkedHashMap<String, Integer>(){
            {
                this.put("bateau", 0);
                this.put("petrol", 244);
                this.put("mine", 488);
            }
        };
        renderOffsetY = new LinkedHashMap<String, Integer>(){
            {
                this.put("bateau", 56);
                this.put("petrol", 300);
                this.put("mine", 544);
            }
        };
        warzoneInfos = new HashMap();
        rankingPanelOffsetX = new LinkedHashMap<String, Integer>(){
            {
                this.put("daily", 334);
                this.put("weekly", 393);
            }
        };
    }
}

