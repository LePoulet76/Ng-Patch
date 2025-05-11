/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.AbstractClientPlayer
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.network.packet.Packet
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
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.TabbedFactionGUI_OLD;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGui_OLD;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionSkillsCountriesLeaderboardDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionSkillsDashboardDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionSkillsPlayersLeaderboardDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class CountrySkillsGUI_OLD
extends TabbedFactionGUI_OLD {
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
    private RenderItem itemRenderer = new RenderItem();
    private GuiButton dashBoardButton;
    private GuiButton playersLeaderboardButton;
    private GuiButton countriesLeaderboardButton;
    private GuiScrollBarFaction scrollBarSkills;
    private GuiScrollBarFaction scrollBarCountries;
    private String displayMode = "dashboard";
    private boolean skillSelectorOpen = false;
    private boolean onlyCountryFilter = false;
    public String selectedSkillLeaderboard = "all";
    public String hoveredSkillLeaderboard = "";
    public static HashMap<String, ResourceLocation> cacheHeadPlayer = new HashMap();
    public static List<String> skillsList = Arrays.asList("all", "miner", "lumberjack", "hunter", "farmer", "engineer", "builder");

    public CountrySkillsGUI_OLD(EntityPlayer player) {
        super(player);
        dashboardData = new HashMap();
        countriesLeaderboard = new HashMap();
        playersLeaderboard = new HashMap();
        countryPositionPerSkill = new HashMap();
        playerPositionPerSkill = new HashMap();
        this.displayMode = "dashboard";
        this.selectedSkillLeaderboard = "all";
    }

    @Override
    public void func_73866_w_() {
        super.func_73866_w_();
        loaded_dashboard = false;
        loaded_countries_leaderboard = false;
        loaded_players_leaderboard = false;
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionSkillsDashboardDataPacket((String)FactionGui_OLD.factionInfos.get("name"))));
        this.dashBoardButton = new GuiButton(0, this.guiLeft + 10, this.guiTop + 168, 100, 20, I18n.func_135053_a((String)"faction.skills.button.dashboard"));
        this.playersLeaderboardButton = new GuiButton(1, this.guiLeft + 10, this.guiTop + 193, 100, 20, I18n.func_135053_a((String)"faction.skills.button.players_leaderboard"));
        this.countriesLeaderboardButton = new GuiButton(2, this.guiLeft + 10, this.guiTop + 218, 100, 20, I18n.func_135053_a((String)"faction.skills.button.countries_leaderboard"));
        this.scrollBarSkills = new GuiScrollBarFaction(this.guiLeft + 379, this.guiTop + 56, 50);
        this.scrollBarCountries = new GuiScrollBarFaction(this.guiLeft + 377, this.guiTop + 87, 130);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        this.hoveredSkillLeaderboard = "";
        ClientEventHandler.STYLE.bindTexture("faction_skills");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512.0f, 512.0f, false);
        this.dashBoardButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
        this.playersLeaderboardButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
        this.countriesLeaderboardButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
        this.drawScaledString(I18n.func_135053_a((String)"faction.skills.title"), this.guiLeft + 131, this.guiTop + 16, 0x191919, 1.4f, false, false);
        if (this.displayMode.equalsIgnoreCase("dashboard") && loaded_dashboard) {
            Map skillsAverage = (Map)dashboardData.get("skillsAverage");
            Map skillsCountLevel = (Map)dashboardData.get("skillsCountLevel");
            ClientEventHandler.STYLE.bindTexture("faction_skills");
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 130, this.guiTop + 30, 257, 250, 255, 90, 512.0f, 512.0f, false);
            this.drawScaledString(I18n.func_135053_a((String)"faction.skills.label.score"), this.guiLeft + 137, this.guiTop + 40, 0xFFFFFF, 0.8f, false, false);
            this.drawScaledString(String.format("%.0f", (Double)dashboardData.get("skillsTotalLevels")), this.guiLeft + 137, this.guiTop + 49, 0x656565, 1.0f, false, false);
            this.drawScaledString(I18n.func_135053_a((String)"faction.skills.label.leaderboard"), this.guiLeft + 197, this.guiTop + 40, 0xFFFFFF, 0.8f, false, false);
            this.drawScaledString((Double)dashboardData.get("skillsCountryPosition") != 0.0 ? String.format("%.0f", (Double)dashboardData.get("skillsCountryPosition")) : "NC", this.guiLeft + 197, this.guiTop + 49, 0x656565, 1.0f, false, false);
            this.drawScaledString(I18n.func_135053_a((String)"faction.skills.label.global_note"), this.guiLeft + 137, this.guiTop + 70, 0xFFFFFF, 0.8f, false, false);
            this.drawScaledString(String.format("%.1f", (Double)dashboardData.get("overallRating")) + "/10", this.guiLeft + 137, this.guiTop + 79, (Double)dashboardData.get("overallRating") >= 7.0 ? 1423378 : ((Double)dashboardData.get("overallRating") >= 4.0 ? 12092178 : 12063250), 1.5f, false, false);
            Map.Entry firstEntry = skillsAverage.entrySet().iterator().next();
            String topSkillName = (String)firstEntry.getKey();
            ClientEventHandler.STYLE.bindTexture("faction_skill_" + topSkillName);
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 292, this.guiTop + 5, 0.0f, 0.0f, 331, 400, 95, 115, 331.0f, 400.0f, false);
            this.drawScaledString(I18n.func_135053_a((String)("faction.skills.label.country_type." + topSkillName)), this.guiLeft + 137, this.guiTop + 103, 0xFFFFFF, 1.0f, false, false);
            ClientEventHandler.STYLE.bindTexture("faction_skills");
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 141 + this.field_73886_k.func_78256_a(I18n.func_135053_a((String)("faction.skills.label.country_type." + topSkillName))), this.guiTop + 99, CountrySkillsGUI_OLD.getSkillIonX(topSkillName), 287, 15, 15, 512.0f, 512.0f, false);
            int skillIndex = 0;
            for (Map.Entry entry : skillsAverage.entrySet()) {
                ClientEventHandler.STYLE.bindTexture("faction_skills");
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 132, this.guiTop + 125 + 19 * skillIndex, CountrySkillsGUI_OLD.getSkillIonX((String)entry.getKey()), 306, 15, 15, 512.0f, 512.0f, false);
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 152, this.guiTop + 127 + 19 * skillIndex, 113, 361, 120, 10, 512.0f, 512.0f, false);
                double barProgress = (Double)entry.getValue() / 10.0;
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 152, this.guiTop + 127 + 19 * skillIndex, 113, 374, (int)(120.0 * barProgress), 10, 512.0f, 512.0f, false);
                this.drawScaledString(String.format("%.1f", entry.getValue()), this.guiLeft + 155, this.guiTop + 130 + 19 * skillIndex, 0, 0.7f, false, false);
                this.drawScaledString("10", this.guiLeft + 152 + 120 - 10, this.guiTop + 130 + 19 * skillIndex, 0, 0.7f, false, false);
                ++skillIndex;
            }
            ClientEventHandler.STYLE.bindTexture("faction_skills");
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 294, this.guiTop + 125, 410, 7, 91, 110, 512.0f, 512.0f, false);
            this.drawScaledString(I18n.func_135053_a((String)"faction.skills.label.top_members"), this.guiLeft + 340, this.guiTop + 132, 0, 1.0f, true, false);
            int playerIndex = 0;
            for (String top5PlayerInfos : (ArrayList)dashboardData.get("top5Members")) {
                String playerName = top5PlayerInfos.split("##")[0];
                String playerAverageNote = top5PlayerInfos.split("##")[1];
                if (!cacheHeadPlayer.containsKey(playerName)) {
                    try {
                        ResourceLocation resourceLocation = AbstractClientPlayer.field_110314_b;
                        resourceLocation = AbstractClientPlayer.func_110311_f((String)playerName);
                        AbstractClientPlayer.func_110304_a((ResourceLocation)resourceLocation, (String)playerName);
                        cacheHeadPlayer.put(playerName, resourceLocation);
                    }
                    catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } else {
                    Minecraft.func_71410_x().field_71446_o.func_110577_a(cacheHeadPlayer.get(playerName));
                    this.field_73882_e.func_110434_K().func_110577_a(cacheHeadPlayer.get(playerName));
                    GUIUtils.drawScaledCustomSizeModalRect(this.guiLeft + 310, this.guiTop + 162 + 13 * playerIndex, 8.0f, 16.0f, 8, -8, -8, -8, 64.0f, 64.0f);
                }
                this.drawScaledString(playerName.length() < 15 ? playerName : playerName.substring(0, 14) + "..", this.guiLeft + 315, this.guiTop + 156 + 13 * playerIndex, 0xFFFFFF, 0.8f, false, false);
                ++playerIndex;
            }
        } else if (this.displayMode.contains("leaderboard") && (loaded_countries_leaderboard || loaded_players_leaderboard)) {
            this.drawScaledString(I18n.func_135053_a((String)("faction.skills.title." + this.displayMode)), this.guiLeft + 131, this.guiTop + 39, 0x191919, 1.0f, false, false);
            ClientEventHandler.STYLE.bindTexture("faction_skills");
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 131, this.guiTop + 57, 259, 349, 253, 20, 512.0f, 512.0f, false);
            ClientEventHandler.STYLE.bindTexture("faction_skills");
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 131, this.guiTop + 82, 259, 372, 253, 140, 512.0f, 512.0f, false);
            if (this.displayMode.equalsIgnoreCase("countries_leaderboard") && loaded_countries_leaderboard) {
                ClientProxy.loadCountryFlag((String)FactionGui_OLD.factionInfos.get("name"));
                if (ClientProxy.flagsTexture.containsKey((String)FactionGui_OLD.factionInfos.get("name"))) {
                    GL11.glBindTexture((int)3553, (int)ClientProxy.flagsTexture.get((String)FactionGui_OLD.factionInfos.get("name")).func_110552_b());
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 136, this.guiTop + 61, 0.0f, 0.0f, 156, 78, 15, 9, 156.0f, 78.0f, false);
                }
                this.drawScaledString(((String)FactionGui_OLD.factionInfos.get("name")).toUpperCase(), this.guiLeft + 162, this.guiTop + 63, 0xFFFFFF, 1.0f, false, false);
                this.drawScaledString(String.format("%.0f", countryTotalLevelPerSkill.get(this.selectedSkillLeaderboard)), this.guiLeft + 270, this.guiTop + 63, 0xFFFFFF, 1.0f, true, false);
                this.drawScaledString(String.format("%.0f", countryPositionPerSkill.get(this.selectedSkillLeaderboard)) + "E", this.guiLeft + 380 - this.field_73886_k.func_78256_a(String.format("%.0f", countryPositionPerSkill.get(this.selectedSkillLeaderboard)) + "E"), this.guiTop + 63, 0xFFFFFF, 1.0f, false, false);
                GUIUtils.startGLScissor(this.guiLeft + 132, this.guiTop + 83, 253, 138);
                for (int i = 0; i < countriesLeaderboard.get(this.selectedSkillLeaderboard).size(); ++i) {
                    String countryInfos = countriesLeaderboard.get(this.selectedSkillLeaderboard).get(i);
                    String countryName = countryInfos.split("##")[0];
                    String score = String.format("%.0f", Double.parseDouble(countryInfos.split("##")[1]));
                    int offsetX = this.guiLeft + 132;
                    Float offsetY = Float.valueOf((float)(this.guiTop + 83 + i * 20) + this.getSlideCountries());
                    ClientEventHandler.STYLE.bindTexture("faction_skills");
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.intValue(), 260, 473, 245, 20, 512.0f, 512.0f, false);
                    ClientProxy.loadCountryFlag(countryName);
                    if (ClientProxy.flagsTexture.containsKey(countryName)) {
                        GL11.glBindTexture((int)3553, (int)ClientProxy.flagsTexture.get(countryName).func_110552_b());
                        ModernGui.drawScaledCustomSizeModalRect(offsetX + 5, offsetY.intValue() + 3, 0.0f, 0.0f, 156, 78, 20, 12, 156.0f, 78.0f, false);
                    }
                    this.drawScaledString("\u00a77" + (i + 1) + ". \u00a7r" + countryName, offsetX + 30, offsetY.intValue() + 6, 0xFFFFFF, 1.0f, false, false);
                    this.drawScaledString(score, offsetX + 253 - this.field_73886_k.func_78256_a(score) - 15, offsetY.intValue() + 6, 0xFFFFFF, 1.0f, false, false);
                }
                GUIUtils.endGLScissor();
            } else if (this.displayMode.equalsIgnoreCase("players_leaderboard") && loaded_players_leaderboard) {
                if (!cacheHeadPlayer.containsKey(Minecraft.func_71410_x().field_71439_g.field_71092_bJ)) {
                    try {
                        ResourceLocation resourceLocation = AbstractClientPlayer.field_110314_b;
                        resourceLocation = AbstractClientPlayer.func_110311_f((String)Minecraft.func_71410_x().field_71439_g.field_71092_bJ);
                        AbstractClientPlayer.func_110304_a((ResourceLocation)resourceLocation, (String)Minecraft.func_71410_x().field_71439_g.field_71092_bJ);
                        cacheHeadPlayer.put(Minecraft.func_71410_x().field_71439_g.field_71092_bJ, resourceLocation);
                    }
                    catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } else {
                    Minecraft.func_71410_x().field_71446_o.func_110577_a(cacheHeadPlayer.get(Minecraft.func_71410_x().field_71439_g.field_71092_bJ));
                    this.field_73882_e.func_110434_K().func_110577_a(cacheHeadPlayer.get(Minecraft.func_71410_x().field_71439_g.field_71092_bJ));
                    GUIUtils.drawScaledCustomSizeModalRect(this.guiLeft + 148, this.guiTop + 73, 8.0f, 16.0f, 8, -8, -12, -12, 64.0f, 64.0f);
                }
                this.drawScaledString(Minecraft.func_71410_x().field_71439_g.field_71092_bJ.toUpperCase(), this.guiLeft + 155, this.guiTop + 63, 0xFFFFFF, 1.0f, false, false);
                this.drawScaledString(String.format("%.0f", playerTotalLevelPerSkill.get(this.selectedSkillLeaderboard)), this.guiLeft + 270, this.guiTop + 63, 0xFFFFFF, 1.0f, true, false);
                String playerPosition = playerPositionPerSkill.containsKey(this.selectedSkillLeaderboard) ? String.format("%.0f", playerPositionPerSkill.get(this.selectedSkillLeaderboard)) + "E" : "NC";
                this.drawScaledString(playerPosition, this.guiLeft + 380 - this.field_73886_k.func_78256_a(playerPosition), this.guiTop + 63, 0xFFFFFF, 1.0f, false, false);
                GUIUtils.startGLScissor(this.guiLeft + 132, this.guiTop + 83, 253, 138);
                for (int i = 0; i < playersLeaderboard.get(this.selectedSkillLeaderboard).size(); ++i) {
                    String playerInfos = playersLeaderboard.get(this.selectedSkillLeaderboard).get(i);
                    String playerName = playerInfos.split("#")[0];
                    String score = playerInfos.split("#")[1];
                    int offsetX = this.guiLeft + 132;
                    Float f = Float.valueOf((float)(this.guiTop + 83 + i * 20) + this.getSlidePlayers());
                    ClientEventHandler.STYLE.bindTexture("faction_skills");
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX, f.intValue(), 260, 373, 245, 20, 512.0f, 512.0f, false);
                    if (!cacheHeadPlayer.containsKey(playerName)) {
                        try {
                            ResourceLocation resourceLocation = AbstractClientPlayer.field_110314_b;
                            resourceLocation = AbstractClientPlayer.func_110311_f((String)playerName);
                            AbstractClientPlayer.func_110304_a((ResourceLocation)resourceLocation, (String)playerName);
                            cacheHeadPlayer.put(playerName, resourceLocation);
                        }
                        catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    } else {
                        Minecraft.func_71410_x().field_71446_o.func_110577_a(cacheHeadPlayer.get(playerName));
                        this.field_73882_e.func_110434_K().func_110577_a(cacheHeadPlayer.get(playerName));
                        GUIUtils.drawScaledCustomSizeModalRect(offsetX + 16, f.intValue() + 14, 8.0f, 16.0f, 8, -8, -10, -10, 64.0f, 64.0f);
                    }
                    this.drawScaledString("\u00a77" + (i + 1) + ". \u00a7r" + playerName, offsetX + 24, f.intValue() + 6, 0xFFFFFF, 1.0f, false, false);
                    this.drawScaledString(String.format("%.0f", Double.parseDouble(score)), offsetX + 253 - this.field_73886_k.func_78256_a(String.format("%.0f", Double.parseDouble(score))) - 15, f.intValue() + 6, 0xFFFFFF, 1.0f, false, false);
                }
                GUIUtils.endGLScissor();
                ClientEventHandler.STYLE.bindTexture("faction_skills");
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 131, this.guiTop + 227, this.onlyCountryFilter ? 0 : 10, 284, 10, 10, 512.0f, 512.0f, false);
                this.drawScaledString(I18n.func_135053_a((String)"faction.skills.label.only_country"), this.guiLeft + 145, this.guiTop + 228, 0x191919, 1.0f, false, false);
            }
            if (!this.skillSelectorOpen && mouseX >= this.guiLeft + 131 && mouseX <= this.guiLeft + 131 + 253 && mouseY >= this.guiTop + 82 && mouseY <= this.guiTop + 82 + 140) {
                this.scrollBarCountries.draw(mouseX, mouseY);
            }
            ModernGui.drawNGBlackSquare(this.guiLeft + 284, this.guiTop + 32, 82, 20);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            ClientEventHandler.STYLE.bindTexture("faction_skills");
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 365, this.guiTop + 32, 191, 252, 19, 20, 512.0f, 512.0f, false);
            this.drawScaledString(I18n.func_135053_a((String)("skills.skill." + this.selectedSkillLeaderboard)), this.guiLeft + 289, this.guiTop + 39, 0xFFFFFF, 1.0f, false, false);
            if (this.skillSelectorOpen) {
                ClientEventHandler.STYLE.bindTexture("faction_skills");
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 284, this.guiTop + 51, 405, 121, 100, 59, 512.0f, 512.0f, false);
                GUIUtils.startGLScissor(this.guiLeft + 284, this.guiTop + 51, 100, 59);
                for (int i = 0; i < skillsList.size(); ++i) {
                    int offsetX = this.guiLeft + 284;
                    Float offsetY = Float.valueOf((float)(this.guiTop + 51 + i * 20) + this.getSlideSkills());
                    ClientEventHandler.STYLE.bindTexture("faction_skills");
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.intValue(), 406, 122, 94, 20, 512.0f, 512.0f, false);
                    this.drawScaledString(I18n.func_135053_a((String)("skills.skill." + skillsList.get(i))), offsetX + 5, offsetY.intValue() + 5, 0xFFFFFF, 1.0f, false, false);
                    if (mouseX < offsetX || mouseX > offsetX + 94 || mouseY < offsetY.intValue() || mouseY > offsetY.intValue() + 20) continue;
                    this.hoveredSkillLeaderboard = skillsList.get(i);
                }
                GUIUtils.endGLScissor();
                if (mouseX >= this.guiLeft + 284 && mouseX <= this.guiLeft + 284 + 100 && mouseY >= this.guiTop + 51 && mouseY <= this.guiTop + 51 + 59) {
                    this.scrollBarSkills.draw(mouseX, mouseY);
                }
            }
        }
    }

    @Override
    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        super.func_73864_a(mouseX, mouseY, mouseButton);
        if (mouseButton == 0) {
            if (mouseX >= this.guiLeft + 10 && mouseX <= this.guiLeft + 10 + 100 && mouseY >= this.guiTop + 168 && mouseY <= this.guiTop + 168 + 20 && !this.displayMode.equals("dashboard")) {
                this.displayMode = "dashboard";
                this.skillSelectorOpen = false;
            } else if (mouseX >= this.guiLeft + 10 && mouseX <= this.guiLeft + 10 + 100 && mouseY >= this.guiTop + 193 && mouseY <= this.guiTop + 193 + 20 && !this.displayMode.equals("players_leaderboard")) {
                this.displayMode = "players_leaderboard";
                this.skillSelectorOpen = false;
                if (playerPositionPerSkill.isEmpty()) {
                    this.onlyCountryFilter = false;
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionSkillsPlayersLeaderboardDataPacket((String)FactionGui_OLD.factionInfos.get("name"), false)));
                }
            } else if (mouseX >= this.guiLeft + 10 && mouseX <= this.guiLeft + 10 + 100 && mouseY >= this.guiTop + 218 && mouseY <= this.guiTop + 218 + 20 && !this.displayMode.equals("countries_leaderboard")) {
                this.displayMode = "countries_leaderboard";
                this.skillSelectorOpen = false;
                if (countryPositionPerSkill.isEmpty()) {
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionSkillsCountriesLeaderboardDataPacket((String)FactionGui_OLD.factionInfos.get("name"))));
                }
            } else if (mouseX >= this.guiLeft + 284 && mouseX <= this.guiLeft + 284 + 100 && mouseY >= this.guiTop + 32 && mouseY <= this.guiTop + 32 + 20) {
                this.skillSelectorOpen = !this.skillSelectorOpen;
            } else if (!this.hoveredSkillLeaderboard.isEmpty()) {
                this.skillSelectorOpen = false;
                this.selectedSkillLeaderboard = this.hoveredSkillLeaderboard;
            } else if (mouseX >= this.guiLeft + 131 && mouseX <= this.guiLeft + 131 + 10 && mouseY >= this.guiTop + 230 && mouseY <= this.guiTop + 230 + 10) {
                this.onlyCountryFilter = !this.onlyCountryFilter;
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionSkillsPlayersLeaderboardDataPacket((String)FactionGui_OLD.factionInfos.get("name"), this.onlyCountryFilter)));
            }
        }
    }

    private float getSlideSkills() {
        return skillsList.size() > 3 ? (float)(-(skillsList.size() - 3) * 20) * this.scrollBarSkills.getSliderValue() : 0.0f;
    }

    private float getSlideCountries() {
        return countriesLeaderboard.get(this.selectedSkillLeaderboard).size() > 7 ? (float)(-(countriesLeaderboard.get(this.selectedSkillLeaderboard).size() - 7) * 20) * this.scrollBarCountries.getSliderValue() : 0.0f;
    }

    private float getSlidePlayers() {
        return playersLeaderboard.get(this.selectedSkillLeaderboard).size() > 7 ? (float)(-(playersLeaderboard.get(this.selectedSkillLeaderboard).size() - 7) * 20) * this.scrollBarCountries.getSliderValue() : 0.0f;
    }

    public static int getSkillIonX(String skillName) {
        switch (skillName) {
            case "farmer": {
                return 127;
            }
            case "lumberjack": {
                return 148;
            }
            case "builder": {
                return 167;
            }
            case "hunter": {
                return 187;
            }
            case "engineer": {
                return 207;
            }
            case "miner": {
                return 226;
            }
        }
        return 0;
    }
}

