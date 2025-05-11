/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.AbstractClientPlayer
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.util.ChatMessageComponent
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarGeneric;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.TabbedFactionGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionSkillsCountriesLeaderboardDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionSkillsDashboardDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionSkillsPlayersLeaderboardDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class FactionSkillsGUI
extends TabbedFactionGUI {
    public static boolean loaded_dashboard = false;
    public static boolean loaded_countries_leaderboard = false;
    public static boolean loaded_players_leaderboard = false;
    public static HashMap<String, Object> dashboardData = new HashMap();
    public static HashMap<String, List<String>> countriesLeaderboard = new HashMap();
    public static HashMap<String, List<String>> playersLeaderboard = new HashMap();
    public static HashMap<String, Double> countryPositionPerSkill = new HashMap();
    public static HashMap<String, Double> countryTotalLevelPerSkill = new HashMap();
    public static HashMap<String, Double> playerPositionPerSkill = new HashMap();
    public static HashMap<String, Double> playerTotalLevelPerSkill = new HashMap();
    private String displayMode = "dashboard";
    public String selectedSkillLeaderboard = "all";
    public String hoveredSkillLeaderboard = "";
    private boolean onlyCountryFilter = false;
    private GuiScrollBarGeneric scrollBarPlayers;
    private GuiScrollBarGeneric scrollBarCountries;
    public static List<String> skillsList = Arrays.asList("all", "miner", "lumberjack", "hunter", "farmer", "engineer", "builder");
    public static HashMap<String, Integer> blockStats = new HashMap<String, Integer>(){
        {
            this.put("neutral", 0);
            this.put("enemy", 122);
            this.put("ally", 244);
            this.put("colony", 366);
        }
    };
    public static HashMap<String, Integer> iconSkills = new HashMap<String, Integer>(){
        {
            this.put("farmer", 6);
            this.put("lumberjack", 24);
            this.put("builder", 44);
            this.put("hunter", 62);
            this.put("engineer", 81);
            this.put("miner", 101);
        }
    };
    public static HashMap<String, Integer> barSkills = new HashMap<String, Integer>(){
        {
            this.put("neutral", 9);
            this.put("enemy", 18);
            this.put("ally", 27);
            this.put("colony", 36);
        }
    };
    public static HashMap<String, Integer> checkboxFilters = new HashMap<String, Integer>(){
        {
            this.put("neutral", 321);
            this.put("enemy", 331);
            this.put("ally", 341);
            this.put("colony", 351);
        }
    };
    public static HashMap<String, Integer> dotFilters = new HashMap<String, Integer>(){
        {
            this.put("neutral", 321);
            this.put("enemy", 329);
            this.put("ally", 337);
            this.put("colony", 345);
        }
    };

    public FactionSkillsGUI() {
        dashboardData = new HashMap();
        countriesLeaderboard = new HashMap();
        playersLeaderboard = new HashMap();
        countryPositionPerSkill = new HashMap();
        playerPositionPerSkill = new HashMap();
        this.displayMode = "dashboard";
        this.selectedSkillLeaderboard = "all";
        loaded_dashboard = false;
        loaded_countries_leaderboard = false;
        loaded_players_leaderboard = false;
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionSkillsDashboardDataPacket((String)FactionGUI.factionInfos.get("name"))));
    }

    @Override
    public void func_73866_w_() {
        super.func_73866_w_();
        this.scrollBarPlayers = new GuiScrollBarGeneric(this.guiLeft + 218, this.guiTop + 113, 114, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
        this.scrollBarCountries = new GuiScrollBarGeneric(this.guiLeft + 218, this.guiTop + 113, 114, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
    }

    @Override
    public void func_73863_a(int mouseX, int mouseY, float partialTick) {
        this.func_73873_v_();
        tooltipToDraw.clear();
        this.hoveredAction = "";
        this.hoveredSkillLeaderboard = "";
        ClientEventHandler.STYLE.bindTexture("faction_global_2");
        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 30, this.guiTop + 0, 0 * GUI_SCALE, 0 * GUI_SCALE, (this.xSize - 30) * GUI_SCALE, this.ySize * GUI_SCALE, this.xSize - 30, this.ySize, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
        if (FactionGUI.loaded) {
            if (FactionGUI.factionInfos.get("banners") != null && ((Map)FactionGUI.factionInfos.get("banners")).containsKey("skills")) {
                ModernGui.bindRemoteTexture((String)((Map)FactionGUI.factionInfos.get("banners")).get("skills"));
            } else {
                ModernGui.bindRemoteTexture("https://static.nationsglory.fr/N3255yGyNN.png");
            }
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 30 + 154, this.guiTop + 0, 0.0f, 0.0f, 279 * GUI_SCALE, 110 * GUI_SCALE, 279, 89, 279 * GUI_SCALE, 110 * GUI_SCALE, false);
            ClientEventHandler.STYLE.bindTexture("faction_global");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 30, this.guiTop + 0, 33 * GUI_SCALE, 280 * GUI_SCALE, 433 * GUI_SCALE, 89 * GUI_SCALE, 433, 89, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
            ModernGui.drawScaledStringCustomFont((String)FactionGUI.factionInfos.get("name"), this.guiLeft + 43, this.guiTop + 6, 10395075, 0.5f, "left", false, "georamaMedium", 32);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.skills.title"), this.guiLeft + 43, this.guiTop + 16, 0xFFFFFF, 0.75f, "left", false, "georamaSemiBold", 32);
            ModernGui.drawSectionStringCustomFont(((String)FactionGUI.factionInfos.get("description")).replaceAll("\u00a7[0-9a-z]{1}", ""), this.guiLeft + 43, this.guiTop + 32, 10395075, 0.5f, "left", false, "georamaMedium", 25, 8, 350);
            ClientEventHandler.STYLE.bindTexture("faction_skills");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 42, this.guiTop + 77, 375 * GUI_SCALE, (this.displayMode.equals("dashboard") ? 0 : 15) * GUI_SCALE, 65 * GUI_SCALE, 12 * GUI_SCALE, 65, 12, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.skills.button.dashboard"), this.guiLeft + 42 + 33, this.guiTop + 80, this.displayMode.equals("dashboard") ? 0x6E76EE : 0xFFFFFF, 0.5f, "center", false, "georamaSemiBold", 24);
            if (mouseX > this.guiLeft + 42 && mouseX < this.guiLeft + 42 + 65 && mouseY >= this.guiTop + 77 && mouseY <= this.guiTop + 77 + 12) {
                this.hoveredAction = "dashboard";
            }
            ClientEventHandler.STYLE.bindTexture("faction_skills");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 108, this.guiTop + 77, 375 * GUI_SCALE, (this.displayMode.equals("players_leaderboard") ? 0 : 15) * GUI_SCALE, 65 * GUI_SCALE, 12 * GUI_SCALE, 65, 12, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.skills.button.players_leaderboard"), this.guiLeft + 108 + 33, this.guiTop + 80, this.displayMode.equals("players_leaderboard") ? 0x6E76EE : 0xFFFFFF, 0.5f, "center", false, "georamaSemiBold", 24);
            if (mouseX > this.guiLeft + 108 && mouseX < this.guiLeft + 108 + 65 && mouseY >= this.guiTop + 77 && mouseY <= this.guiTop + 77 + 12) {
                this.hoveredAction = "players_leaderboard";
            }
            ClientEventHandler.STYLE.bindTexture("faction_skills");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 174, this.guiTop + 77, 375 * GUI_SCALE, (this.displayMode.equals("countries_leaderboard") ? 0 : 15) * GUI_SCALE, 65 * GUI_SCALE, 12 * GUI_SCALE, 65, 12, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.skills.button.countries_leaderboard"), this.guiLeft + 174 + 33, this.guiTop + 80, this.displayMode.equals("countries_leaderboard") ? 0x6E76EE : 0xFFFFFF, 0.5f, "center", false, "georamaSemiBold", 24);
            if (mouseX > this.guiLeft + 174 && mouseX < this.guiLeft + 174 + 65 && mouseY >= this.guiTop + 77 && mouseY <= this.guiTop + 77 + 12) {
                this.hoveredAction = "countries_leaderboard";
            }
            ClientEventHandler.STYLE.bindTexture("faction_skills");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 246, this.guiTop + 103, 0 * GUI_SCALE, 0 * GUI_SCALE, 91 * GUI_SCALE, 116 * GUI_SCALE, 91, 116, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 355, this.guiTop + 103, 96 * GUI_SCALE, blockStats.get((String)FactionGUI.factionInfos.get("actualRelation")) * GUI_SCALE, 91 * GUI_SCALE, 116 * GUI_SCALE, 91, 116, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
            if (loaded_dashboard) {
                Map skillsAverage = (Map)dashboardData.get("skillsAverage");
                if (this.displayMode.equals("dashboard")) {
                    int skillIndex = 0;
                    for (Map.Entry entry : skillsAverage.entrySet()) {
                        ClientEventHandler.STYLE.bindTexture("faction_skills");
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 44, this.guiTop + 109 + 19 * skillIndex, 194 * GUI_SCALE, iconSkills.get(entry.getKey()) * GUI_SCALE, 12 * GUI_SCALE, 12 * GUI_SCALE, 12, 12, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 75, this.guiTop + 111 + 19 * skillIndex, 218 * GUI_SCALE, 0 * GUI_SCALE, 145 * GUI_SCALE, 6 * GUI_SCALE, 145, 6, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                        double value = (Double)entry.getValue();
                        double barProgress = value / 10.0;
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 75, this.guiTop + 111 + 19 * skillIndex, 218 * GUI_SCALE, barSkills.get((String)FactionGUI.factionInfos.get("actualRelation")) * GUI_SCALE, (int)(145.0 * barProgress) * GUI_SCALE, 6 * GUI_SCALE, (int)(145.0 * barProgress), 6, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                        ModernGui.drawScaledStringCustomFont(String.format("%.1f", value), this.guiLeft + 72, this.guiTop + 111 + 19 * skillIndex, 0xFFFFFF, 0.5f, "right", false, "georamaMedium", 26);
                        ModernGui.drawScaledStringCustomFont("10", this.guiLeft + 224, this.guiTop + 111 + 19 * skillIndex, 10395075, 0.5f, "left", false, "georamaMedium", 26);
                        ++skillIndex;
                    }
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.skills.label.top_members"), this.guiLeft + 253, this.guiTop + 111, 0xFFFFFF, 0.5f, "left", false, "georamaSemiBold", 30);
                    int playerIndex = 0;
                    for (String top5PlayerInfos : (ArrayList)dashboardData.get("top5Members")) {
                        String playerName = top5PlayerInfos.split("##")[0];
                        if (!ClientProxy.cacheHeadPlayer.containsKey(playerName)) {
                            try {
                                ResourceLocation resourceLocation = AbstractClientPlayer.field_110314_b;
                                resourceLocation = AbstractClientPlayer.func_110311_f((String)playerName);
                                AbstractClientPlayer.func_110304_a((ResourceLocation)resourceLocation, (String)playerName);
                                ClientProxy.cacheHeadPlayer.put(playerName, resourceLocation);
                            }
                            catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        } else {
                            Minecraft.func_71410_x().field_71446_o.func_110577_a(ClientProxy.cacheHeadPlayer.get(playerName));
                            this.field_73882_e.func_110434_K().func_110577_a(ClientProxy.cacheHeadPlayer.get(playerName));
                            GUIUtils.drawScaledCustomSizeModalRect(this.guiLeft + 253 + 10, this.guiTop + 126 + 13 * playerIndex + 10, 8.0f, 16.0f, 8, -8, -10, -10, 64.0f, 64.0f);
                        }
                        ModernGui.drawScaledStringCustomFont(playerName, this.guiLeft + 268, this.guiTop + 127 + 13 * playerIndex, 10395075, 0.5f, "left", false, "georamaMedium", 28);
                        ++playerIndex;
                    }
                } else if (this.displayMode.equals("players_leaderboard") && loaded_players_leaderboard) {
                    if (!ClientProxy.cacheHeadPlayer.containsKey(Minecraft.func_71410_x().field_71439_g.field_71092_bJ)) {
                        try {
                            ResourceLocation resourceLocation = AbstractClientPlayer.field_110314_b;
                            resourceLocation = AbstractClientPlayer.func_110311_f((String)Minecraft.func_71410_x().field_71439_g.field_71092_bJ);
                            AbstractClientPlayer.func_110304_a((ResourceLocation)resourceLocation, (String)Minecraft.func_71410_x().field_71439_g.field_71092_bJ);
                            ClientProxy.cacheHeadPlayer.put(Minecraft.func_71410_x().field_71439_g.field_71092_bJ, resourceLocation);
                        }
                        catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    } else {
                        Minecraft.func_71410_x().field_71446_o.func_110577_a(ClientProxy.cacheHeadPlayer.get(Minecraft.func_71410_x().field_71439_g.field_71092_bJ));
                        this.field_73882_e.func_110434_K().func_110577_a(ClientProxy.cacheHeadPlayer.get(Minecraft.func_71410_x().field_71439_g.field_71092_bJ));
                        GUIUtils.drawScaledCustomSizeModalRect(this.guiLeft + 42 + 10, this.guiTop + 94 + 10, 8.0f, 16.0f, 8, -8, -10, -10, 64.0f, 64.0f);
                    }
                    String playerPosition = playerPositionPerSkill.containsKey(this.selectedSkillLeaderboard) ? String.format("%.0f", playerPositionPerSkill.get(this.selectedSkillLeaderboard)) + "e" : "NC";
                    ModernGui.drawScaledStringCustomFont(playerPosition, this.guiLeft + 57, this.guiTop + 96, 0x6E76EE, 0.5f, "left", false, "georamaMedium", 28);
                    ModernGui.drawScaledStringCustomFont(Minecraft.func_71410_x().field_71439_g.field_71092_bJ, this.guiLeft + 87, this.guiTop + 96, 0x6E76EE, 0.5f, "left", false, "georamaMedium", 28);
                    ModernGui.drawScaledStringCustomFont(String.format("%.0f", playerTotalLevelPerSkill.get(this.selectedSkillLeaderboard)), this.guiLeft + 200, this.guiTop + 96, 0x6E76EE, 0.5f, "right", false, "georamaMedium", 28);
                    ClientEventHandler.STYLE.bindTexture("faction_skills");
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 218, this.guiTop + 113, 218 * GUI_SCALE, 63 * GUI_SCALE, 2 * GUI_SCALE, 114 * GUI_SCALE, 2, 114, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                    GUIUtils.startGLScissor(this.guiLeft + 42, this.guiTop + 115, 176, 114);
                    for (int i = 0; i < playersLeaderboard.get(this.selectedSkillLeaderboard).size(); ++i) {
                        String string = playersLeaderboard.get(this.selectedSkillLeaderboard).get(i);
                        String playerName = string.split("#")[0];
                        String score = string.split("#")[1];
                        int offsetX = this.guiLeft + 42;
                        Float offsetY = Float.valueOf((float)(this.guiTop + 115 + i * 13) + this.getSlidePlayers());
                        if (!ClientProxy.cacheHeadPlayer.containsKey(playerName)) {
                            try {
                                ResourceLocation resourceLocation = AbstractClientPlayer.field_110314_b;
                                resourceLocation = AbstractClientPlayer.func_110311_f((String)playerName);
                                AbstractClientPlayer.func_110304_a((ResourceLocation)resourceLocation, (String)playerName);
                                ClientProxy.cacheHeadPlayer.put(playerName, resourceLocation);
                            }
                            catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        } else {
                            Minecraft.func_71410_x().field_71446_o.func_110577_a(ClientProxy.cacheHeadPlayer.get(playerName));
                            this.field_73882_e.func_110434_K().func_110577_a(ClientProxy.cacheHeadPlayer.get(playerName));
                            GUIUtils.drawScaledCustomSizeModalRect(offsetX + 10, offsetY.intValue() + 10, 8.0f, 16.0f, 8, -8, -10, -10, 64.0f, 64.0f);
                        }
                        ModernGui.drawScaledStringCustomFont(i + 1 + "e", offsetX + 15, offsetY.intValue() + 2, 0xFFFFFF, 0.5f, "left", false, "georamaMedium", 28);
                        ModernGui.drawScaledStringCustomFont(playerName, offsetX + 45, offsetY.intValue() + 2, 0xFFFFFF, 0.5f, "left", false, "georamaMedium", 28);
                        ModernGui.drawScaledStringCustomFont(String.format("%.0f", Double.parseDouble(score)), offsetX + 158, offsetY.intValue() + 2, 10395075, 0.5f, "right", false, "georamaMedium", 28);
                    }
                    GUIUtils.endGLScissor();
                    if (mouseX > this.guiLeft + 42 && mouseX < this.guiLeft + 42 + 176 && mouseY >= this.guiTop + 115 && mouseY <= this.guiTop + 115 + 114) {
                        this.scrollBarPlayers.draw(mouseX, mouseY);
                    }
                } else if (this.displayMode.equals("countries_leaderboard") && loaded_countries_leaderboard) {
                    ClientProxy.loadCountryFlag((String)FactionGUI.factionInfos.get("name"));
                    if (ClientProxy.flagsTexture.containsKey((String)FactionGUI.factionInfos.get("name"))) {
                        GL11.glBindTexture((int)3553, (int)ClientProxy.flagsTexture.get((String)FactionGUI.factionInfos.get("name")).func_110552_b());
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 42, this.guiTop + 94, 0.0f, 0.0f, 156, 78, 15, 9, 156.0f, 78.0f, false);
                    }
                    ModernGui.drawScaledStringCustomFont(String.format("%.0f", countryPositionPerSkill.get(this.selectedSkillLeaderboard)) + "e", this.guiLeft + 62, this.guiTop + 96, 0x6E76EE, 0.5f, "left", false, "georamaMedium", 28);
                    ModernGui.drawScaledStringCustomFont((String)FactionGUI.factionInfos.get("name"), this.guiLeft + 92, this.guiTop + 96, 0x6E76EE, 0.5f, "left", false, "georamaMedium", 28);
                    ModernGui.drawScaledStringCustomFont(String.format("%.0f", countryTotalLevelPerSkill.get(this.selectedSkillLeaderboard)), this.guiLeft + 200, this.guiTop + 96, 0x6E76EE, 0.5f, "right", false, "georamaMedium", 28);
                    ClientEventHandler.STYLE.bindTexture("faction_skills");
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 218, this.guiTop + 113, 218 * GUI_SCALE, 63 * GUI_SCALE, 2 * GUI_SCALE, 114 * GUI_SCALE, 2, 114, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                    GUIUtils.startGLScissor(this.guiLeft + 42, this.guiTop + 115, 176, 114);
                    for (int i = 0; i < countriesLeaderboard.get(this.selectedSkillLeaderboard).size(); ++i) {
                        String countryInfos = countriesLeaderboard.get(this.selectedSkillLeaderboard).get(i);
                        String string = countryInfos.split("##")[0];
                        String score = String.format("%.0f", Double.parseDouble(countryInfos.split("##")[1]));
                        int offsetX = this.guiLeft + 42;
                        Float offsetY = Float.valueOf((float)(this.guiTop + 115 + i * 13) + this.getSlideCountries());
                        ClientProxy.loadCountryFlag(string);
                        if (ClientProxy.flagsTexture.containsKey(string)) {
                            GL11.glBindTexture((int)3553, (int)ClientProxy.flagsTexture.get(string).func_110552_b());
                            ModernGui.drawScaledCustomSizeModalRect(offsetX, offsetY.intValue() + 1, 0.0f, 0.0f, 156, 78, 15, 9, 156.0f, 78.0f, false);
                        }
                        ModernGui.drawScaledStringCustomFont(i + 1 + "e", offsetX + 20, offsetY.intValue() + 2, 0xFFFFFF, 0.5f, "left", false, "georamaMedium", 28);
                        ModernGui.drawScaledStringCustomFont(string, offsetX + 50, offsetY.intValue() + 2, 0xFFFFFF, 0.5f, "left", false, "georamaMedium", 28);
                        ModernGui.drawScaledStringCustomFont(score, offsetX + 158, offsetY.intValue() + 2, 10395075, 0.5f, "right", false, "georamaMedium", 28);
                    }
                    GUIUtils.endGLScissor();
                    if (mouseX > this.guiLeft + 42 && mouseX < this.guiLeft + 42 + 176 && mouseY >= this.guiTop + 115 && mouseY <= this.guiTop + 115 + 114) {
                        this.scrollBarCountries.draw(mouseX, mouseY);
                    }
                }
                if (this.displayMode.contains("leaderboard")) {
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.skills.label.filters"), this.guiLeft + 253, this.guiTop + 111, 0xFFFFFF, 0.5f, "left", false, "georamaSemiBold", 30);
                    for (int i = 0; i < skillsList.size(); ++i) {
                        int offsetX = this.guiLeft + 253;
                        int n = this.guiTop + 127 + i * 9;
                        ClientEventHandler.STYLE.bindTexture("faction_global");
                        ModernGui.drawScaledCustomSizeModalRect(offsetX, n, (this.selectedSkillLeaderboard.equals(skillsList.get(i)) ? dotFilters.get((String)FactionGUI.factionInfos.get("actualRelation")) : 321) * GUI_SCALE, (this.selectedSkillLeaderboard.equals(skillsList.get(i)) ? 181 : 190) * GUI_SCALE, 6 * GUI_SCALE, 6 * GUI_SCALE, 6, 6, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("skills.skill." + skillsList.get(i))), offsetX + 10, n + 0, this.selectedSkillLeaderboard.equals(skillsList.get(i)) ? 0xFFFFFF : 0xDADAED, 0.5f, "left", false, this.selectedSkillLeaderboard.equals(skillsList.get(i)) ? "georamaSemiBold" : "georamaMedium", 24);
                        if (mouseX < offsetX || mouseX > offsetX + 6 || mouseY < n || mouseY > n + 6) continue;
                        this.hoveredAction = "filter#" + skillsList.get(i);
                    }
                    if (this.displayMode.equals("players_leaderboard")) {
                        ClientEventHandler.STYLE.bindTexture("faction_global");
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 253, this.guiTop + 200, (this.onlyCountryFilter ? checkboxFilters.get((String)FactionGUI.factionInfos.get("actualRelation")) : 329) * GUI_SCALE, (this.onlyCountryFilter ? 199 : 189) * GUI_SCALE, 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                        ModernGui.drawSectionStringCustomFont(I18n.func_135053_a((String)"faction.skills.label.only_country"), this.guiLeft + 253 + 10, this.guiTop + 198, 0xDADAED, 0.5f, "left", false, "georamaMedium", 24, 7, 120);
                        if (mouseX >= this.guiLeft + 253 && mouseX <= this.guiLeft + 253 + 8 && mouseY >= this.guiTop + 200 && mouseY <= this.guiTop + 200 + 8) {
                            this.hoveredAction = "checkbox#only_country";
                        }
                    }
                }
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.skills.label.score"), this.guiLeft + 363, this.guiTop + 115, 0xFFFFFF, 0.5f, "left", false, "georamaMedium", 26);
                ModernGui.drawScaledStringCustomFont(String.format("%.0f", (Double)dashboardData.get("skillsTotalLevels")), this.guiLeft + 363, this.guiTop + 123, 0xFFFFFF, 0.5f, "left", false, "georamaBold", 32);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.skills.label.leaderboard"), this.guiLeft + 363, this.guiTop + 140, 0xFFFFFF, 0.5f, "left", false, "georamaMedium", 26);
                ModernGui.drawScaledStringCustomFont((Double)dashboardData.get("skillsCountryPosition") != 0.0 ? String.format("%.0f", (Double)dashboardData.get("skillsCountryPosition")) + "e" : "NC", this.guiLeft + 363, this.guiTop + 148, 0xFFFFFF, 0.5f, "left", false, "georamaBold", 32);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.skills.label.global_note"), this.guiLeft + 363, this.guiTop + 165, 0xFFFFFF, 0.5f, "left", false, "georamaMedium", 26);
                ModernGui.drawScaledStringCustomFont(String.format("%.2f", (Double)dashboardData.get("overallRating")) + "/10", this.guiLeft + 363, this.guiTop + 173, 0xFFFFFF, 0.5f, "left", false, "georamaBold", 32);
                Map.Entry firstEntry = skillsAverage.entrySet().iterator().next();
                String topSkillName = (String)firstEntry.getKey();
                ClientEventHandler.STYLE.bindTexture("faction_skill_" + topSkillName);
                GUIUtils.startGLScissor(this.guiLeft + 313, this.guiTop, 95, 89);
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 313, this.guiTop - 12, 0.0f, 0.0f, 331, 400, 95, 115, 331.0f, 400.0f, false);
                GUIUtils.endGLScissor();
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.skills.label.type"), this.guiLeft + 363, this.guiTop + 190, 0xFFFFFF, 0.5f, "left", false, "georamaMedium", 26);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("faction.skills.label.country_type." + topSkillName)), this.guiLeft + 363, this.guiTop + 198, 0xFFFFFF, 0.5f, "left", false, "georamaBold", 32);
            }
        }
        if (tooltipToDraw != null && !tooltipToDraw.isEmpty()) {
            this.drawHoveringText(tooltipToDraw, mouseX, mouseY, this.field_73886_k);
        }
        super.func_73863_a(mouseX, mouseY, partialTick);
    }

    private float getSlidePlayers() {
        return playersLeaderboard.get(this.selectedSkillLeaderboard).size() > 9 ? (float)(-(playersLeaderboard.get(this.selectedSkillLeaderboard).size() - 9) * 13) * this.scrollBarPlayers.getSliderValue() : 0.0f;
    }

    private float getSlideCountries() {
        return countriesLeaderboard.get(this.selectedSkillLeaderboard).size() > 9 ? (float)(-(countriesLeaderboard.get(this.selectedSkillLeaderboard).size() - 9) * 13) * this.scrollBarCountries.getSliderValue() : 0.0f;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
    }

    @Override
    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0 && !this.hoveredAction.isEmpty()) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
            if (!this.hoveredAction.isEmpty()) {
                if (this.hoveredAction.equals("edit_photo")) {
                    ClientData.lastCaptureScreenshot.put("skills", System.currentTimeMillis());
                    Minecraft.func_71410_x().func_71373_a(null);
                    Minecraft.func_71410_x().field_71439_g.func_70006_a(ChatMessageComponent.func_111066_d((String)I18n.func_135053_a((String)"faction.take_picture")));
                } else if (this.hoveredAction.contains("filter")) {
                    this.selectedSkillLeaderboard = this.hoveredAction.split("#")[1];
                } else if (this.hoveredAction.contains("checkbox")) {
                    this.onlyCountryFilter = !this.onlyCountryFilter;
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionSkillsPlayersLeaderboardDataPacket((String)FactionGUI.factionInfos.get("name"), this.onlyCountryFilter)));
                } else {
                    if (this.hoveredAction.equals("countries_leaderboard")) {
                        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionSkillsCountriesLeaderboardDataPacket((String)FactionGUI.factionInfos.get("name"))));
                    } else if (this.hoveredAction.equals("players_leaderboard")) {
                        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionSkillsPlayersLeaderboardDataPacket((String)FactionGUI.factionInfos.get("name"), this.onlyCountryFilter)));
                    }
                    this.displayMode = this.hoveredAction;
                }
            }
            this.hoveredAction = "";
        }
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    public boolean isNumeric(String str) {
        try {
            return Double.parseDouble(str) > 0.0;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }
}

