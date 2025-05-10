package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.TabbedFactionGUI_OLD;
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
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class CountrySkillsGUI_OLD extends TabbedFactionGUI_OLD
{
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
    public static List<String> skillsList = Arrays.asList(new String[] {"all", "miner", "lumberjack", "hunter", "farmer", "engineer", "builder"});

    public CountrySkillsGUI_OLD(EntityPlayer player)
    {
        super(player);
        dashboardData = new HashMap();
        countriesLeaderboard = new HashMap();
        playersLeaderboard = new HashMap();
        countryPositionPerSkill = new HashMap();
        playerPositionPerSkill = new HashMap();
        this.displayMode = "dashboard";
        this.selectedSkillLeaderboard = "all";
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        loaded_dashboard = false;
        loaded_countries_leaderboard = false;
        loaded_players_leaderboard = false;
        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionSkillsDashboardDataPacket((String)FactionGui_OLD.factionInfos.get("name"))));
        this.dashBoardButton = new GuiButton(0, this.guiLeft + 10, this.guiTop + 168, 100, 20, I18n.getString("faction.skills.button.dashboard"));
        this.playersLeaderboardButton = new GuiButton(1, this.guiLeft + 10, this.guiTop + 193, 100, 20, I18n.getString("faction.skills.button.players_leaderboard"));
        this.countriesLeaderboardButton = new GuiButton(2, this.guiLeft + 10, this.guiTop + 218, 100, 20, I18n.getString("faction.skills.button.countries_leaderboard"));
        this.scrollBarSkills = new GuiScrollBarFaction((float)(this.guiLeft + 379), (float)(this.guiTop + 56), 50);
        this.scrollBarCountries = new GuiScrollBarFaction((float)(this.guiLeft + 377), (float)(this.guiTop + 87), 130);
    }

    public void drawScreen(int mouseX, int mouseY)
    {
        this.hoveredSkillLeaderboard = "";
        ClientEventHandler.STYLE.bindTexture("faction_skills");
        ModernGui.drawModalRectWithCustomSizedTexture((float)this.guiLeft, (float)this.guiTop, 0, 0, this.xSize, this.ySize, 512.0F, 512.0F, false);
        this.dashBoardButton.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);
        this.playersLeaderboardButton.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);
        this.countriesLeaderboardButton.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);
        this.drawScaledString(I18n.getString("faction.skills.title"), this.guiLeft + 131, this.guiTop + 16, 1644825, 1.4F, false, false);
        String playerName;
        int offsetX1;
        int var24;

        if (this.displayMode.equalsIgnoreCase("dashboard") && loaded_dashboard)
        {
            Map var20 = (Map)dashboardData.get("skillsAverage");
            Map var21 = (Map)dashboardData.get("skillsCountLevel");
            ClientEventHandler.STYLE.bindTexture("faction_skills");
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 130), (float)(this.guiTop + 30), 257, 250, 255, 90, 512.0F, 512.0F, false);
            this.drawScaledString(I18n.getString("faction.skills.label.score"), this.guiLeft + 137, this.guiTop + 40, 16777215, 0.8F, false, false);
            this.drawScaledString(String.format("%.0f", new Object[] {(Double)dashboardData.get("skillsTotalLevels")}), this.guiLeft + 137, this.guiTop + 49, 6645093, 1.0F, false, false);
            this.drawScaledString(I18n.getString("faction.skills.label.leaderboard"), this.guiLeft + 197, this.guiTop + 40, 16777215, 0.8F, false, false);
            this.drawScaledString(((Double)dashboardData.get("skillsCountryPosition")).doubleValue() != 0.0D ? String.format("%.0f", new Object[] {(Double)dashboardData.get("skillsCountryPosition")}): "NC", this.guiLeft + 197, this.guiTop + 49, 6645093, 1.0F, false, false);
            this.drawScaledString(I18n.getString("faction.skills.label.global_note"), this.guiLeft + 137, this.guiTop + 70, 16777215, 0.8F, false, false);
            this.drawScaledString(String.format("%.1f", new Object[] {(Double)dashboardData.get("overallRating")}) + "/10", this.guiLeft + 137, this.guiTop + 79, ((Double)dashboardData.get("overallRating")).doubleValue() >= 7.0D ? 1423378 : (((Double)dashboardData.get("overallRating")).doubleValue() >= 4.0D ? 12092178 : 12063250), 1.5F, false, false);
            Entry var23 = (Entry)var20.entrySet().iterator().next();
            playerName = (String)var23.getKey();
            ClientEventHandler.STYLE.bindTexture("faction_skill_" + playerName);
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 292), (float)(this.guiTop + 5), 0.0F, 0.0F, 331, 400, 95, 115, 331.0F, 400.0F, false);
            this.drawScaledString(I18n.getString("faction.skills.label.country_type." + playerName), this.guiLeft + 137, this.guiTop + 103, 16777215, 1.0F, false, false);
            ClientEventHandler.STYLE.bindTexture("faction_skills");
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 141 + this.fontRenderer.getStringWidth(I18n.getString("faction.skills.label.country_type." + playerName))), (float)(this.guiTop + 99), getSkillIonX(playerName), 287, 15, 15, 512.0F, 512.0F, false);
            var24 = 0;

            for (Iterator var26 = var20.entrySet().iterator(); var26.hasNext(); ++var24)
            {
                Entry var27 = (Entry)var26.next();
                ClientEventHandler.STYLE.bindTexture("faction_skills");
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 132), (float)(this.guiTop + 125 + 19 * var24), getSkillIonX((String)var27.getKey()), 306, 15, 15, 512.0F, 512.0F, false);
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 152), (float)(this.guiTop + 127 + 19 * var24), 113, 361, 120, 10, 512.0F, 512.0F, false);
                double var29 = ((Double)var27.getValue()).doubleValue() / 10.0D;
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 152), (float)(this.guiTop + 127 + 19 * var24), 113, 374, (int)(120.0D * var29), 10, 512.0F, 512.0F, false);
                this.drawScaledString(String.format("%.1f", new Object[] {var27.getValue()}), this.guiLeft + 155, this.guiTop + 130 + 19 * var24, 0, 0.7F, false, false);
                this.drawScaledString("10", this.guiLeft + 152 + 120 - 10, this.guiTop + 130 + 19 * var24, 0, 0.7F, false, false);
            }

            ClientEventHandler.STYLE.bindTexture("faction_skills");
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 294), (float)(this.guiTop + 125), 410, 7, 91, 110, 512.0F, 512.0F, false);
            this.drawScaledString(I18n.getString("faction.skills.label.top_members"), this.guiLeft + 340, this.guiTop + 132, 0, 1.0F, true, false);
            offsetX1 = 0;

            for (Iterator var28 = ((ArrayList)dashboardData.get("top5Members")).iterator(); var28.hasNext(); ++offsetX1)
            {
                String var30 = (String)var28.next();
                String playerName1 = var30.split("##")[0];
                String playerAverageNote = var30.split("##")[1];

                if (!cacheHeadPlayer.containsKey(playerName1))
                {
                    try
                    {
                        ResourceLocation e1 = AbstractClientPlayer.locationStevePng;
                        e1 = AbstractClientPlayer.getLocationSkin(playerName1);
                        AbstractClientPlayer.getDownloadImageSkin(e1, playerName1);
                        cacheHeadPlayer.put(playerName1, e1);
                    }
                    catch (Exception var16)
                    {
                        System.out.println(var16.getMessage());
                    }
                }
                else
                {
                    Minecraft.getMinecraft().renderEngine.bindTexture((ResourceLocation)cacheHeadPlayer.get(playerName1));
                    this.mc.getTextureManager().bindTexture((ResourceLocation)cacheHeadPlayer.get(playerName1));
                    GUIUtils.drawScaledCustomSizeModalRect(this.guiLeft + 310, this.guiTop + 162 + 13 * offsetX1, 8.0F, 16.0F, 8, -8, -8, -8, 64.0F, 64.0F);
                }

                this.drawScaledString(playerName1.length() < 15 ? playerName1 : playerName1.substring(0, 14) + "..", this.guiLeft + 315, this.guiTop + 156 + 13 * offsetX1, 16777215, 0.8F, false, false);
            }
        }
        else if (this.displayMode.contains("leaderboard") && (loaded_countries_leaderboard || loaded_players_leaderboard))
        {
            this.drawScaledString(I18n.getString("faction.skills.title." + this.displayMode), this.guiLeft + 131, this.guiTop + 39, 1644825, 1.0F, false, false);
            ClientEventHandler.STYLE.bindTexture("faction_skills");
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 131), (float)(this.guiTop + 57), 259, 349, 253, 20, 512.0F, 512.0F, false);
            ClientEventHandler.STYLE.bindTexture("faction_skills");
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 131), (float)(this.guiTop + 82), 259, 372, 253, 140, 512.0F, 512.0F, false);
            int offsetX;
            String offsetY;
            int var18;

            if (this.displayMode.equalsIgnoreCase("countries_leaderboard") && loaded_countries_leaderboard)
            {
                ClientProxy.loadCountryFlag((String)FactionGui_OLD.factionInfos.get("name"));

                if (ClientProxy.flagsTexture.containsKey((String)FactionGui_OLD.factionInfos.get("name")))
                {
                    GL11.glBindTexture(GL11.GL_TEXTURE_2D, ((DynamicTexture)ClientProxy.flagsTexture.get((String)FactionGui_OLD.factionInfos.get("name"))).getGlTextureId());
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 136), (float)(this.guiTop + 61), 0.0F, 0.0F, 156, 78, 15, 9, 156.0F, 78.0F, false);
                }

                this.drawScaledString(((String)FactionGui_OLD.factionInfos.get("name")).toUpperCase(), this.guiLeft + 162, this.guiTop + 63, 16777215, 1.0F, false, false);
                this.drawScaledString(String.format("%.0f", new Object[] {countryTotalLevelPerSkill.get(this.selectedSkillLeaderboard)}), this.guiLeft + 270, this.guiTop + 63, 16777215, 1.0F, true, false);
                this.drawScaledString(String.format("%.0f", new Object[] {(Double)countryPositionPerSkill.get(this.selectedSkillLeaderboard)}) + "E", this.guiLeft + 380 - this.fontRenderer.getStringWidth(String.format("%.0f", new Object[] {(Double)countryPositionPerSkill.get(this.selectedSkillLeaderboard)}) + "E"), this.guiTop + 63, 16777215, 1.0F, false, false);
                GUIUtils.startGLScissor(this.guiLeft + 132, this.guiTop + 83, 253, 138);

                for (var18 = 0; var18 < ((List)countriesLeaderboard.get(this.selectedSkillLeaderboard)).size(); ++var18)
                {
                    String var19 = (String)((List)countriesLeaderboard.get(this.selectedSkillLeaderboard)).get(var18);
                    offsetY = var19.split("##")[0];
                    playerName = String.format("%.0f", new Object[] {Double.valueOf(Double.parseDouble(var19.split("##")[1]))});
                    var24 = this.guiLeft + 132;
                    Float var25 = Float.valueOf((float)(this.guiTop + 83 + var18 * 20) + this.getSlideCountries());
                    ClientEventHandler.STYLE.bindTexture("faction_skills");
                    ModernGui.drawModalRectWithCustomSizedTexture((float)var24, (float)var25.intValue(), 260, 473, 245, 20, 512.0F, 512.0F, false);
                    ClientProxy.loadCountryFlag(offsetY);

                    if (ClientProxy.flagsTexture.containsKey(offsetY))
                    {
                        GL11.glBindTexture(GL11.GL_TEXTURE_2D, ((DynamicTexture)ClientProxy.flagsTexture.get(offsetY)).getGlTextureId());
                        ModernGui.drawScaledCustomSizeModalRect((float)(var24 + 5), (float)(var25.intValue() + 3), 0.0F, 0.0F, 156, 78, 20, 12, 156.0F, 78.0F, false);
                    }

                    this.drawScaledString("\u00a77" + (var18 + 1) + ". \u00a7r" + offsetY, var24 + 30, var25.intValue() + 6, 16777215, 1.0F, false, false);
                    this.drawScaledString(playerName, var24 + 253 - this.fontRenderer.getStringWidth(playerName) - 15, var25.intValue() + 6, 16777215, 1.0F, false, false);
                }

                GUIUtils.endGLScissor();
            }
            else if (this.displayMode.equalsIgnoreCase("players_leaderboard") && loaded_players_leaderboard)
            {
                if (!cacheHeadPlayer.containsKey(Minecraft.getMinecraft().thePlayer.username))
                {
                    try
                    {
                        ResourceLocation i = AbstractClientPlayer.locationStevePng;
                        i = AbstractClientPlayer.getLocationSkin(Minecraft.getMinecraft().thePlayer.username);
                        AbstractClientPlayer.getDownloadImageSkin(i, Minecraft.getMinecraft().thePlayer.username);
                        cacheHeadPlayer.put(Minecraft.getMinecraft().thePlayer.username, i);
                    }
                    catch (Exception var15)
                    {
                        System.out.println(var15.getMessage());
                    }
                }
                else
                {
                    Minecraft.getMinecraft().renderEngine.bindTexture((ResourceLocation)cacheHeadPlayer.get(Minecraft.getMinecraft().thePlayer.username));
                    this.mc.getTextureManager().bindTexture((ResourceLocation)cacheHeadPlayer.get(Minecraft.getMinecraft().thePlayer.username));
                    GUIUtils.drawScaledCustomSizeModalRect(this.guiLeft + 148, this.guiTop + 73, 8.0F, 16.0F, 8, -8, -12, -12, 64.0F, 64.0F);
                }

                this.drawScaledString(Minecraft.getMinecraft().thePlayer.username.toUpperCase(), this.guiLeft + 155, this.guiTop + 63, 16777215, 1.0F, false, false);
                this.drawScaledString(String.format("%.0f", new Object[] {playerTotalLevelPerSkill.get(this.selectedSkillLeaderboard)}), this.guiLeft + 270, this.guiTop + 63, 16777215, 1.0F, true, false);
                String var17 = playerPositionPerSkill.containsKey(this.selectedSkillLeaderboard) ? String.format("%.0f", new Object[] {(Double)playerPositionPerSkill.get(this.selectedSkillLeaderboard)}) + "E": "NC";
                this.drawScaledString(var17, this.guiLeft + 380 - this.fontRenderer.getStringWidth(var17), this.guiTop + 63, 16777215, 1.0F, false, false);
                GUIUtils.startGLScissor(this.guiLeft + 132, this.guiTop + 83, 253, 138);

                for (offsetX = 0; offsetX < ((List)playersLeaderboard.get(this.selectedSkillLeaderboard)).size(); ++offsetX)
                {
                    offsetY = (String)((List)playersLeaderboard.get(this.selectedSkillLeaderboard)).get(offsetX);
                    playerName = offsetY.split("#")[0];
                    String score = offsetY.split("#")[1];
                    offsetX1 = this.guiLeft + 132;
                    Float offsetY1 = Float.valueOf((float)(this.guiTop + 83 + offsetX * 20) + this.getSlidePlayers());
                    ClientEventHandler.STYLE.bindTexture("faction_skills");
                    ModernGui.drawModalRectWithCustomSizedTexture((float)offsetX1, (float)offsetY1.intValue(), 260, 373, 245, 20, 512.0F, 512.0F, false);

                    if (!cacheHeadPlayer.containsKey(playerName))
                    {
                        try
                        {
                            ResourceLocation e = AbstractClientPlayer.locationStevePng;
                            e = AbstractClientPlayer.getLocationSkin(playerName);
                            AbstractClientPlayer.getDownloadImageSkin(e, playerName);
                            cacheHeadPlayer.put(playerName, e);
                        }
                        catch (Exception var14)
                        {
                            System.out.println(var14.getMessage());
                        }
                    }
                    else
                    {
                        Minecraft.getMinecraft().renderEngine.bindTexture((ResourceLocation)cacheHeadPlayer.get(playerName));
                        this.mc.getTextureManager().bindTexture((ResourceLocation)cacheHeadPlayer.get(playerName));
                        GUIUtils.drawScaledCustomSizeModalRect(offsetX1 + 16, offsetY1.intValue() + 14, 8.0F, 16.0F, 8, -8, -10, -10, 64.0F, 64.0F);
                    }

                    this.drawScaledString("\u00a77" + (offsetX + 1) + ". \u00a7r" + playerName, offsetX1 + 24, offsetY1.intValue() + 6, 16777215, 1.0F, false, false);
                    this.drawScaledString(String.format("%.0f", new Object[] {Double.valueOf(Double.parseDouble(score))}), offsetX1 + 253 - this.fontRenderer.getStringWidth(String.format("%.0f", new Object[] {Double.valueOf(Double.parseDouble(score))})) - 15, offsetY1.intValue() + 6, 16777215, 1.0F, false, false);
                }

                GUIUtils.endGLScissor();
                ClientEventHandler.STYLE.bindTexture("faction_skills");
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 131), (float)(this.guiTop + 227), this.onlyCountryFilter ? 0 : 10, 284, 10, 10, 512.0F, 512.0F, false);
                this.drawScaledString(I18n.getString("faction.skills.label.only_country"), this.guiLeft + 145, this.guiTop + 228, 1644825, 1.0F, false, false);
            }

            if (!this.skillSelectorOpen && mouseX >= this.guiLeft + 131 && mouseX <= this.guiLeft + 131 + 253 && mouseY >= this.guiTop + 82 && mouseY <= this.guiTop + 82 + 140)
            {
                this.scrollBarCountries.draw(mouseX, mouseY);
            }

            ModernGui.drawNGBlackSquare(this.guiLeft + 284, this.guiTop + 32, 82, 20);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            ClientEventHandler.STYLE.bindTexture("faction_skills");
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 365), (float)(this.guiTop + 32), 191, 252, 19, 20, 512.0F, 512.0F, false);
            this.drawScaledString(I18n.getString("skills.skill." + this.selectedSkillLeaderboard), this.guiLeft + 289, this.guiTop + 39, 16777215, 1.0F, false, false);

            if (this.skillSelectorOpen)
            {
                ClientEventHandler.STYLE.bindTexture("faction_skills");
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 284), (float)(this.guiTop + 51), 405, 121, 100, 59, 512.0F, 512.0F, false);
                GUIUtils.startGLScissor(this.guiLeft + 284, this.guiTop + 51, 100, 59);

                for (var18 = 0; var18 < skillsList.size(); ++var18)
                {
                    offsetX = this.guiLeft + 284;
                    Float var22 = Float.valueOf((float)(this.guiTop + 51 + var18 * 20) + this.getSlideSkills());
                    ClientEventHandler.STYLE.bindTexture("faction_skills");
                    ModernGui.drawModalRectWithCustomSizedTexture((float)offsetX, (float)var22.intValue(), 406, 122, 94, 20, 512.0F, 512.0F, false);
                    this.drawScaledString(I18n.getString("skills.skill." + (String)skillsList.get(var18)), offsetX + 5, var22.intValue() + 5, 16777215, 1.0F, false, false);

                    if (mouseX >= offsetX && mouseX <= offsetX + 94 && mouseY >= var22.intValue() && mouseY <= var22.intValue() + 20)
                    {
                        this.hoveredSkillLeaderboard = (String)skillsList.get(var18);
                    }
                }

                GUIUtils.endGLScissor();

                if (mouseX >= this.guiLeft + 284 && mouseX <= this.guiLeft + 284 + 100 && mouseY >= this.guiTop + 51 && mouseY <= this.guiTop + 51 + 59)
                {
                    this.scrollBarSkills.draw(mouseX, mouseY);
                }
            }
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (mouseButton == 0)
        {
            if (mouseX >= this.guiLeft + 10 && mouseX <= this.guiLeft + 10 + 100 && mouseY >= this.guiTop + 168 && mouseY <= this.guiTop + 168 + 20 && !this.displayMode.equals("dashboard"))
            {
                this.displayMode = "dashboard";
                this.skillSelectorOpen = false;
            }
            else if (mouseX >= this.guiLeft + 10 && mouseX <= this.guiLeft + 10 + 100 && mouseY >= this.guiTop + 193 && mouseY <= this.guiTop + 193 + 20 && !this.displayMode.equals("players_leaderboard"))
            {
                this.displayMode = "players_leaderboard";
                this.skillSelectorOpen = false;

                if (playerPositionPerSkill.isEmpty())
                {
                    this.onlyCountryFilter = false;
                    PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionSkillsPlayersLeaderboardDataPacket((String)FactionGui_OLD.factionInfos.get("name"), false)));
                }
            }
            else if (mouseX >= this.guiLeft + 10 && mouseX <= this.guiLeft + 10 + 100 && mouseY >= this.guiTop + 218 && mouseY <= this.guiTop + 218 + 20 && !this.displayMode.equals("countries_leaderboard"))
            {
                this.displayMode = "countries_leaderboard";
                this.skillSelectorOpen = false;

                if (countryPositionPerSkill.isEmpty())
                {
                    PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionSkillsCountriesLeaderboardDataPacket((String)FactionGui_OLD.factionInfos.get("name"))));
                }
            }
            else if (mouseX >= this.guiLeft + 284 && mouseX <= this.guiLeft + 284 + 100 && mouseY >= this.guiTop + 32 && mouseY <= this.guiTop + 32 + 20)
            {
                this.skillSelectorOpen = !this.skillSelectorOpen;
            }
            else if (!this.hoveredSkillLeaderboard.isEmpty())
            {
                this.skillSelectorOpen = false;
                this.selectedSkillLeaderboard = this.hoveredSkillLeaderboard;
            }
            else if (mouseX >= this.guiLeft + 131 && mouseX <= this.guiLeft + 131 + 10 && mouseY >= this.guiTop + 230 && mouseY <= this.guiTop + 230 + 10)
            {
                this.onlyCountryFilter = !this.onlyCountryFilter;
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionSkillsPlayersLeaderboardDataPacket((String)FactionGui_OLD.factionInfos.get("name"), this.onlyCountryFilter)));
            }
        }
    }

    private float getSlideSkills()
    {
        return skillsList.size() > 3 ? (float)(-(skillsList.size() - 3) * 20) * this.scrollBarSkills.getSliderValue() : 0.0F;
    }

    private float getSlideCountries()
    {
        return ((List)countriesLeaderboard.get(this.selectedSkillLeaderboard)).size() > 7 ? (float)(-(((List)countriesLeaderboard.get(this.selectedSkillLeaderboard)).size() - 7) * 20) * this.scrollBarCountries.getSliderValue() : 0.0F;
    }

    private float getSlidePlayers()
    {
        return ((List)playersLeaderboard.get(this.selectedSkillLeaderboard)).size() > 7 ? (float)(-(((List)playersLeaderboard.get(this.selectedSkillLeaderboard)).size() - 7) * 20) * this.scrollBarCountries.getSliderValue() : 0.0F;
    }

    public static int getSkillIonX(String skillName)
    {
        byte var2 = -1;

        switch (skillName.hashCode())
        {
            case -1281708189:
                if (skillName.equals("farmer"))
                {
                    var2 = 0;
                }

                break;

            case -1206091904:
                if (skillName.equals("hunter"))
                {
                    var2 = 3;
                }

                break;

            case 1496170:
                if (skillName.equals("lumberjack"))
                {
                    var2 = 1;
                }

                break;

            case 103900799:
                if (skillName.equals("miner"))
                {
                    var2 = 5;
                }

                break;

            case 230944667:
                if (skillName.equals("builder"))
                {
                    var2 = 2;
                }

                break;

            case 1820491375:
                if (skillName.equals("engineer"))
                {
                    var2 = 4;
                }
        }

        switch (var2)
        {
            case 0:
                return 127;

            case 1:
                return 148;

            case 2:
                return 167;

            case 3:
                return 187;

            case 4:
                return 207;

            case 5:
                return 226;

            default:
                return 0;
        }
    }
}
