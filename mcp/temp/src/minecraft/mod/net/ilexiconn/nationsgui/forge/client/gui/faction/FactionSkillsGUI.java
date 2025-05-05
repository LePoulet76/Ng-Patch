package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarGeneric;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionSkillsGUI$1;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionSkillsGUI$2;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionSkillsGUI$3;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionSkillsGUI$4;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionSkillsGUI$5;
import net.ilexiconn.nationsgui.forge.client.gui.faction.TabbedFactionGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionSkillsCountriesLeaderboardDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionSkillsDashboardDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionSkillsPlayersLeaderboardDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class FactionSkillsGUI extends TabbedFactionGUI {

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
   public static List<String> skillsList = Arrays.asList(new String[]{"all", "miner", "lumberjack", "hunter", "farmer", "engineer", "builder"});
   public static HashMap<String, Integer> blockStats = new FactionSkillsGUI$1();
   public static HashMap<String, Integer> iconSkills = new FactionSkillsGUI$2();
   public static HashMap<String, Integer> barSkills = new FactionSkillsGUI$3();
   public static HashMap<String, Integer> checkboxFilters = new FactionSkillsGUI$4();
   public static HashMap<String, Integer> dotFilters = new FactionSkillsGUI$5();


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
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionSkillsDashboardDataPacket((String)FactionGUI.factionInfos.get("name"))));
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.scrollBarPlayers = new GuiScrollBarGeneric((float)(this.guiLeft + 218), (float)(this.guiTop + 113), 114, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
      this.scrollBarCountries = new GuiScrollBarGeneric((float)(this.guiLeft + 218), (float)(this.guiTop + 113), 114, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
   }

   public void func_73863_a(int mouseX, int mouseY, float partialTick) {
      this.func_73873_v_();
      tooltipToDraw.clear();
      this.hoveredAction = "";
      this.hoveredSkillLeaderboard = "";
      ClientEventHandler.STYLE.bindTexture("faction_global_2");
      ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 30), (float)(this.guiTop + 0), (float)(0 * GUI_SCALE), (float)(0 * GUI_SCALE), (this.xSize - 30) * GUI_SCALE, this.ySize * GUI_SCALE, this.xSize - 30, this.ySize, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
      if(FactionGUI.loaded) {
         if(FactionGUI.factionInfos.get("banners") != null && ((Map)FactionGUI.factionInfos.get("banners")).containsKey("skills")) {
            ModernGui.bindRemoteTexture((String)((Map)FactionGUI.factionInfos.get("banners")).get("skills"));
         } else {
            ModernGui.bindRemoteTexture("https://static.nationsglory.fr/N3255yGyNN.png");
         }

         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 30 + 154), (float)(this.guiTop + 0), 0.0F, 0.0F, 279 * GUI_SCALE, 110 * GUI_SCALE, 279, 89, (float)(279 * GUI_SCALE), (float)(110 * GUI_SCALE), false);
         ClientEventHandler.STYLE.bindTexture("faction_global");
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 30), (float)(this.guiTop + 0), (float)(33 * GUI_SCALE), (float)(280 * GUI_SCALE), 433 * GUI_SCALE, 89 * GUI_SCALE, 433, 89, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
         ModernGui.drawScaledStringCustomFont((String)FactionGUI.factionInfos.get("name"), (float)(this.guiLeft + 43), (float)(this.guiTop + 6), 10395075, 0.5F, "left", false, "georamaMedium", 32);
         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.skills.title"), (float)(this.guiLeft + 43), (float)(this.guiTop + 16), 16777215, 0.75F, "left", false, "georamaSemiBold", 32);
         ModernGui.drawSectionStringCustomFont(((String)FactionGUI.factionInfos.get("description")).replaceAll("\u00a7[0-9a-z]{1}", ""), (float)(this.guiLeft + 43), (float)(this.guiTop + 32), 10395075, 0.5F, "left", false, "georamaMedium", 25, 8, 350);
         ClientEventHandler.STYLE.bindTexture("faction_skills");
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 42), (float)(this.guiTop + 77), (float)(375 * GUI_SCALE), (float)((this.displayMode.equals("dashboard")?0:15) * GUI_SCALE), 65 * GUI_SCALE, 12 * GUI_SCALE, 65, 12, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.skills.button.dashboard"), (float)(this.guiLeft + 42 + 33), (float)(this.guiTop + 80), this.displayMode.equals("dashboard")?7239406:16777215, 0.5F, "center", false, "georamaSemiBold", 24);
         if(mouseX > this.guiLeft + 42 && mouseX < this.guiLeft + 42 + 65 && mouseY >= this.guiTop + 77 && mouseY <= this.guiTop + 77 + 12) {
            this.hoveredAction = "dashboard";
         }

         ClientEventHandler.STYLE.bindTexture("faction_skills");
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 108), (float)(this.guiTop + 77), (float)(375 * GUI_SCALE), (float)((this.displayMode.equals("players_leaderboard")?0:15) * GUI_SCALE), 65 * GUI_SCALE, 12 * GUI_SCALE, 65, 12, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.skills.button.players_leaderboard"), (float)(this.guiLeft + 108 + 33), (float)(this.guiTop + 80), this.displayMode.equals("players_leaderboard")?7239406:16777215, 0.5F, "center", false, "georamaSemiBold", 24);
         if(mouseX > this.guiLeft + 108 && mouseX < this.guiLeft + 108 + 65 && mouseY >= this.guiTop + 77 && mouseY <= this.guiTop + 77 + 12) {
            this.hoveredAction = "players_leaderboard";
         }

         ClientEventHandler.STYLE.bindTexture("faction_skills");
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 174), (float)(this.guiTop + 77), (float)(375 * GUI_SCALE), (float)((this.displayMode.equals("countries_leaderboard")?0:15) * GUI_SCALE), 65 * GUI_SCALE, 12 * GUI_SCALE, 65, 12, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.skills.button.countries_leaderboard"), (float)(this.guiLeft + 174 + 33), (float)(this.guiTop + 80), this.displayMode.equals("countries_leaderboard")?7239406:16777215, 0.5F, "center", false, "georamaSemiBold", 24);
         if(mouseX > this.guiLeft + 174 && mouseX < this.guiLeft + 174 + 65 && mouseY >= this.guiTop + 77 && mouseY <= this.guiTop + 77 + 12) {
            this.hoveredAction = "countries_leaderboard";
         }

         ClientEventHandler.STYLE.bindTexture("faction_skills");
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 246), (float)(this.guiTop + 103), (float)(0 * GUI_SCALE), (float)(0 * GUI_SCALE), 91 * GUI_SCALE, 116 * GUI_SCALE, 91, 116, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 355), (float)(this.guiTop + 103), (float)(96 * GUI_SCALE), (float)(((Integer)blockStats.get((String)FactionGUI.factionInfos.get("actualRelation"))).intValue() * GUI_SCALE), 91 * GUI_SCALE, 116 * GUI_SCALE, 91, 116, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
         if(loaded_dashboard) {
            Map skillsAverage = (Map)dashboardData.get("skillsAverage");
            int firstEntry;
            String offsetX;
            int var16;
            String var19;
            String var23;
            if(this.displayMode.equals("dashboard")) {
               firstEntry = 0;

               for(Iterator topSkillName = skillsAverage.entrySet().iterator(); topSkillName.hasNext(); ++firstEntry) {
                  Entry offsetY = (Entry)topSkillName.next();
                  ClientEventHandler.STYLE.bindTexture("faction_skills");
                  ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 44), (float)(this.guiTop + 109 + 19 * firstEntry), (float)(194 * GUI_SCALE), (float)(((Integer)iconSkills.get(offsetY.getKey())).intValue() * GUI_SCALE), 12 * GUI_SCALE, 12 * GUI_SCALE, 12, 12, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                  ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 75), (float)(this.guiTop + 111 + 19 * firstEntry), (float)(218 * GUI_SCALE), (float)(0 * GUI_SCALE), 145 * GUI_SCALE, 6 * GUI_SCALE, 145, 6, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                  double score = ((Double)offsetY.getValue()).doubleValue();
                  double offsetY1 = score / 10.0D;
                  ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 75), (float)(this.guiTop + 111 + 19 * firstEntry), (float)(218 * GUI_SCALE), (float)(((Integer)barSkills.get((String)FactionGUI.factionInfos.get("actualRelation"))).intValue() * GUI_SCALE), (int)(145.0D * offsetY1) * GUI_SCALE, 6 * GUI_SCALE, (int)(145.0D * offsetY1), 6, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                  ModernGui.drawScaledStringCustomFont(String.format("%.1f", new Object[]{Double.valueOf(score)}), (float)(this.guiLeft + 72), (float)(this.guiTop + 111 + 19 * firstEntry), 16777215, 0.5F, "right", false, "georamaMedium", 26);
                  ModernGui.drawScaledStringCustomFont("10", (float)(this.guiLeft + 224), (float)(this.guiTop + 111 + 19 * firstEntry), 10395075, 0.5F, "left", false, "georamaMedium", 26);
               }

               ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.skills.label.top_members"), (float)(this.guiLeft + 253), (float)(this.guiTop + 111), 16777215, 0.5F, "left", false, "georamaSemiBold", 30);
               var16 = 0;

               for(Iterator var20 = ((ArrayList)dashboardData.get("top5Members")).iterator(); var20.hasNext(); ++var16) {
                  var23 = (String)var20.next();
                  offsetX = var23.split("##")[0];
                  if(!ClientProxy.cacheHeadPlayer.containsKey(offsetX)) {
                     try {
                        ResourceLocation var26 = AbstractClientPlayer.field_110314_b;
                        var26 = AbstractClientPlayer.func_110311_f(offsetX);
                        AbstractClientPlayer.func_110304_a(var26, offsetX);
                        ClientProxy.cacheHeadPlayer.put(offsetX, var26);
                     } catch (Exception var15) {
                        System.out.println(var15.getMessage());
                     }
                  } else {
                     Minecraft.func_71410_x().field_71446_o.func_110577_a((ResourceLocation)ClientProxy.cacheHeadPlayer.get(offsetX));
                     this.field_73882_e.func_110434_K().func_110577_a((ResourceLocation)ClientProxy.cacheHeadPlayer.get(offsetX));
                     GUIUtils.drawScaledCustomSizeModalRect(this.guiLeft + 253 + 10, this.guiTop + 126 + 13 * var16 + 10, 8.0F, 16.0F, 8, -8, -10, -10, 64.0F, 64.0F);
                  }

                  ModernGui.drawScaledStringCustomFont(offsetX, (float)(this.guiLeft + 268), (float)(this.guiTop + 127 + 13 * var16), 10395075, 0.5F, "left", false, "georamaMedium", 28);
               }
            } else {
               String var21;
               if(this.displayMode.equals("players_leaderboard") && loaded_players_leaderboard) {
                  if(!ClientProxy.cacheHeadPlayer.containsKey(Minecraft.func_71410_x().field_71439_g.field_71092_bJ)) {
                     try {
                        ResourceLocation var17 = AbstractClientPlayer.field_110314_b;
                        var17 = AbstractClientPlayer.func_110311_f(Minecraft.func_71410_x().field_71439_g.field_71092_bJ);
                        AbstractClientPlayer.func_110304_a(var17, Minecraft.func_71410_x().field_71439_g.field_71092_bJ);
                        ClientProxy.cacheHeadPlayer.put(Minecraft.func_71410_x().field_71439_g.field_71092_bJ, var17);
                     } catch (Exception var14) {
                        System.out.println(var14.getMessage());
                     }
                  } else {
                     Minecraft.func_71410_x().field_71446_o.func_110577_a((ResourceLocation)ClientProxy.cacheHeadPlayer.get(Minecraft.func_71410_x().field_71439_g.field_71092_bJ));
                     this.field_73882_e.func_110434_K().func_110577_a((ResourceLocation)ClientProxy.cacheHeadPlayer.get(Minecraft.func_71410_x().field_71439_g.field_71092_bJ));
                     GUIUtils.drawScaledCustomSizeModalRect(this.guiLeft + 42 + 10, this.guiTop + 94 + 10, 8.0F, 16.0F, 8, -8, -10, -10, 64.0F, 64.0F);
                  }

                  String var18 = playerPositionPerSkill.containsKey(this.selectedSkillLeaderboard)?String.format("%.0f", new Object[]{(Double)playerPositionPerSkill.get(this.selectedSkillLeaderboard)}) + "e":"NC";
                  ModernGui.drawScaledStringCustomFont(var18, (float)(this.guiLeft + 57), (float)(this.guiTop + 96), 7239406, 0.5F, "left", false, "georamaMedium", 28);
                  float var10001 = (float)(this.guiLeft + 87);
                  float var10002 = (float)(this.guiTop + 96);
                  ModernGui.drawScaledStringCustomFont(Minecraft.func_71410_x().field_71439_g.field_71092_bJ, var10001, var10002, 7239406, 0.5F, "left", false, "georamaMedium", 28);
                  ModernGui.drawScaledStringCustomFont(String.format("%.0f", new Object[]{playerTotalLevelPerSkill.get(this.selectedSkillLeaderboard)}), (float)(this.guiLeft + 200), (float)(this.guiTop + 96), 7239406, 0.5F, "right", false, "georamaMedium", 28);
                  ClientEventHandler.STYLE.bindTexture("faction_skills");
                  ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 218), (float)(this.guiTop + 113), (float)(218 * GUI_SCALE), (float)(63 * GUI_SCALE), 2 * GUI_SCALE, 114 * GUI_SCALE, 2, 114, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                  GUIUtils.startGLScissor(this.guiLeft + 42, this.guiTop + 115, 176, 114);

                  for(var16 = 0; var16 < ((List)playersLeaderboard.get(this.selectedSkillLeaderboard)).size(); ++var16) {
                     var21 = (String)((List)playersLeaderboard.get(this.selectedSkillLeaderboard)).get(var16);
                     var23 = var21.split("#")[0];
                     offsetX = var21.split("#")[1];
                     int var28 = this.guiLeft + 42;
                     Float offsetY2 = Float.valueOf((float)(this.guiTop + 115 + var16 * 13) + this.getSlidePlayers());
                     if(!ClientProxy.cacheHeadPlayer.containsKey(var23)) {
                        try {
                           ResourceLocation e = AbstractClientPlayer.field_110314_b;
                           e = AbstractClientPlayer.func_110311_f(var23);
                           AbstractClientPlayer.func_110304_a(e, var23);
                           ClientProxy.cacheHeadPlayer.put(var23, e);
                        } catch (Exception var13) {
                           System.out.println(var13.getMessage());
                        }
                     } else {
                        Minecraft.func_71410_x().field_71446_o.func_110577_a((ResourceLocation)ClientProxy.cacheHeadPlayer.get(var23));
                        this.field_73882_e.func_110434_K().func_110577_a((ResourceLocation)ClientProxy.cacheHeadPlayer.get(var23));
                        GUIUtils.drawScaledCustomSizeModalRect(var28 + 10, offsetY2.intValue() + 10, 8.0F, 16.0F, 8, -8, -10, -10, 64.0F, 64.0F);
                     }

                     ModernGui.drawScaledStringCustomFont(var16 + 1 + "e", (float)(var28 + 15), (float)(offsetY2.intValue() + 2), 16777215, 0.5F, "left", false, "georamaMedium", 28);
                     ModernGui.drawScaledStringCustomFont(var23, (float)(var28 + 45), (float)(offsetY2.intValue() + 2), 16777215, 0.5F, "left", false, "georamaMedium", 28);
                     ModernGui.drawScaledStringCustomFont(String.format("%.0f", new Object[]{Double.valueOf(Double.parseDouble(offsetX))}), (float)(var28 + 158), (float)(offsetY2.intValue() + 2), 10395075, 0.5F, "right", false, "georamaMedium", 28);
                  }

                  GUIUtils.endGLScissor();
                  if(mouseX > this.guiLeft + 42 && mouseX < this.guiLeft + 42 + 176 && mouseY >= this.guiTop + 115 && mouseY <= this.guiTop + 115 + 114) {
                     this.scrollBarPlayers.draw(mouseX, mouseY);
                  }
               } else if(this.displayMode.equals("countries_leaderboard") && loaded_countries_leaderboard) {
                  ClientProxy.loadCountryFlag((String)FactionGUI.factionInfos.get("name"));
                  if(ClientProxy.flagsTexture.containsKey((String)FactionGUI.factionInfos.get("name"))) {
                     GL11.glBindTexture(3553, ((DynamicTexture)ClientProxy.flagsTexture.get((String)FactionGUI.factionInfos.get("name"))).func_110552_b());
                     ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 42), (float)(this.guiTop + 94), 0.0F, 0.0F, 156, 78, 15, 9, 156.0F, 78.0F, false);
                  }

                  ModernGui.drawScaledStringCustomFont(String.format("%.0f", new Object[]{(Double)countryPositionPerSkill.get(this.selectedSkillLeaderboard)}) + "e", (float)(this.guiLeft + 62), (float)(this.guiTop + 96), 7239406, 0.5F, "left", false, "georamaMedium", 28);
                  ModernGui.drawScaledStringCustomFont((String)FactionGUI.factionInfos.get("name"), (float)(this.guiLeft + 92), (float)(this.guiTop + 96), 7239406, 0.5F, "left", false, "georamaMedium", 28);
                  ModernGui.drawScaledStringCustomFont(String.format("%.0f", new Object[]{countryTotalLevelPerSkill.get(this.selectedSkillLeaderboard)}), (float)(this.guiLeft + 200), (float)(this.guiTop + 96), 7239406, 0.5F, "right", false, "georamaMedium", 28);
                  ClientEventHandler.STYLE.bindTexture("faction_skills");
                  ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 218), (float)(this.guiTop + 113), (float)(218 * GUI_SCALE), (float)(63 * GUI_SCALE), 2 * GUI_SCALE, 114 * GUI_SCALE, 2, 114, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                  GUIUtils.startGLScissor(this.guiLeft + 42, this.guiTop + 115, 176, 114);

                  for(firstEntry = 0; firstEntry < ((List)countriesLeaderboard.get(this.selectedSkillLeaderboard)).size(); ++firstEntry) {
                     var19 = (String)((List)countriesLeaderboard.get(this.selectedSkillLeaderboard)).get(firstEntry);
                     var21 = var19.split("##")[0];
                     var23 = String.format("%.0f", new Object[]{Double.valueOf(Double.parseDouble(var19.split("##")[1]))});
                     int var25 = this.guiLeft + 42;
                     Float var27 = Float.valueOf((float)(this.guiTop + 115 + firstEntry * 13) + this.getSlideCountries());
                     ClientProxy.loadCountryFlag(var21);
                     if(ClientProxy.flagsTexture.containsKey(var21)) {
                        GL11.glBindTexture(3553, ((DynamicTexture)ClientProxy.flagsTexture.get(var21)).func_110552_b());
                        ModernGui.drawScaledCustomSizeModalRect((float)var25, (float)(var27.intValue() + 1), 0.0F, 0.0F, 156, 78, 15, 9, 156.0F, 78.0F, false);
                     }

                     ModernGui.drawScaledStringCustomFont(firstEntry + 1 + "e", (float)(var25 + 20), (float)(var27.intValue() + 2), 16777215, 0.5F, "left", false, "georamaMedium", 28);
                     ModernGui.drawScaledStringCustomFont(var21, (float)(var25 + 50), (float)(var27.intValue() + 2), 16777215, 0.5F, "left", false, "georamaMedium", 28);
                     ModernGui.drawScaledStringCustomFont(var23, (float)(var25 + 158), (float)(var27.intValue() + 2), 10395075, 0.5F, "right", false, "georamaMedium", 28);
                  }

                  GUIUtils.endGLScissor();
                  if(mouseX > this.guiLeft + 42 && mouseX < this.guiLeft + 42 + 176 && mouseY >= this.guiTop + 115 && mouseY <= this.guiTop + 115 + 114) {
                     this.scrollBarCountries.draw(mouseX, mouseY);
                  }
               }
            }

            if(this.displayMode.contains("leaderboard")) {
               ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.skills.label.filters"), (float)(this.guiLeft + 253), (float)(this.guiTop + 111), 16777215, 0.5F, "left", false, "georamaSemiBold", 30);

               for(firstEntry = 0; firstEntry < skillsList.size(); ++firstEntry) {
                  var16 = this.guiLeft + 253;
                  int var24 = this.guiTop + 127 + firstEntry * 9;
                  ClientEventHandler.STYLE.bindTexture("faction_global");
                  ModernGui.drawScaledCustomSizeModalRect((float)var16, (float)var24, (float)((this.selectedSkillLeaderboard.equals(skillsList.get(firstEntry))?((Integer)dotFilters.get((String)FactionGUI.factionInfos.get("actualRelation"))).intValue():321) * GUI_SCALE), (float)((this.selectedSkillLeaderboard.equals(skillsList.get(firstEntry))?181:190) * GUI_SCALE), 6 * GUI_SCALE, 6 * GUI_SCALE, 6, 6, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                  ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("skills.skill." + (String)skillsList.get(firstEntry)), (float)(var16 + 10), (float)(var24 + 0), this.selectedSkillLeaderboard.equals(skillsList.get(firstEntry))?16777215:14342893, 0.5F, "left", false, this.selectedSkillLeaderboard.equals(skillsList.get(firstEntry))?"georamaSemiBold":"georamaMedium", 24);
                  if(mouseX >= var16 && mouseX <= var16 + 6 && mouseY >= var24 && mouseY <= var24 + 6) {
                     this.hoveredAction = "filter#" + (String)skillsList.get(firstEntry);
                  }
               }

               if(this.displayMode.equals("players_leaderboard")) {
                  ClientEventHandler.STYLE.bindTexture("faction_global");
                  ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 253), (float)(this.guiTop + 200), (float)((this.onlyCountryFilter?((Integer)checkboxFilters.get((String)FactionGUI.factionInfos.get("actualRelation"))).intValue():329) * GUI_SCALE), (float)((this.onlyCountryFilter?199:189) * GUI_SCALE), 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                  ModernGui.drawSectionStringCustomFont(I18n.func_135053_a("faction.skills.label.only_country"), (float)(this.guiLeft + 253 + 10), (float)(this.guiTop + 198), 14342893, 0.5F, "left", false, "georamaMedium", 24, 7, 120);
                  if(mouseX >= this.guiLeft + 253 && mouseX <= this.guiLeft + 253 + 8 && mouseY >= this.guiTop + 200 && mouseY <= this.guiTop + 200 + 8) {
                     this.hoveredAction = "checkbox#only_country";
                  }
               }
            }

            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.skills.label.score"), (float)(this.guiLeft + 363), (float)(this.guiTop + 115), 16777215, 0.5F, "left", false, "georamaMedium", 26);
            ModernGui.drawScaledStringCustomFont(String.format("%.0f", new Object[]{(Double)dashboardData.get("skillsTotalLevels")}), (float)(this.guiLeft + 363), (float)(this.guiTop + 123), 16777215, 0.5F, "left", false, "georamaBold", 32);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.skills.label.leaderboard"), (float)(this.guiLeft + 363), (float)(this.guiTop + 140), 16777215, 0.5F, "left", false, "georamaMedium", 26);
            ModernGui.drawScaledStringCustomFont(((Double)dashboardData.get("skillsCountryPosition")).doubleValue() != 0.0D?String.format("%.0f", new Object[]{(Double)dashboardData.get("skillsCountryPosition")}) + "e":"NC", (float)(this.guiLeft + 363), (float)(this.guiTop + 148), 16777215, 0.5F, "left", false, "georamaBold", 32);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.skills.label.global_note"), (float)(this.guiLeft + 363), (float)(this.guiTop + 165), 16777215, 0.5F, "left", false, "georamaMedium", 26);
            ModernGui.drawScaledStringCustomFont(String.format("%.2f", new Object[]{(Double)dashboardData.get("overallRating")}) + "/10", (float)(this.guiLeft + 363), (float)(this.guiTop + 173), 16777215, 0.5F, "left", false, "georamaBold", 32);
            Entry var22 = (Entry)skillsAverage.entrySet().iterator().next();
            var19 = (String)var22.getKey();
            ClientEventHandler.STYLE.bindTexture("faction_skill_" + var19);
            GUIUtils.startGLScissor(this.guiLeft + 313, this.guiTop, 95, 89);
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 313), (float)(this.guiTop - 12), 0.0F, 0.0F, 331, 400, 95, 115, 331.0F, 400.0F, false);
            GUIUtils.endGLScissor();
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.skills.label.type"), (float)(this.guiLeft + 363), (float)(this.guiTop + 190), 16777215, 0.5F, "left", false, "georamaMedium", 26);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.skills.label.country_type." + var19), (float)(this.guiLeft + 363), (float)(this.guiTop + 198), 16777215, 0.5F, "left", false, "georamaBold", 32);
         }
      }

      if(tooltipToDraw != null && !tooltipToDraw.isEmpty()) {
         this.drawHoveringText(tooltipToDraw, mouseX, mouseY, this.field_73886_k);
      }

      super.func_73863_a(mouseX, mouseY, partialTick);
   }

   private float getSlidePlayers() {
      return ((List)playersLeaderboard.get(this.selectedSkillLeaderboard)).size() > 9?(float)(-(((List)playersLeaderboard.get(this.selectedSkillLeaderboard)).size() - 9) * 13) * this.scrollBarPlayers.getSliderValue():0.0F;
   }

   private float getSlideCountries() {
      return ((List)countriesLeaderboard.get(this.selectedSkillLeaderboard)).size() > 9?(float)(-(((List)countriesLeaderboard.get(this.selectedSkillLeaderboard)).size() - 9) * 13) * this.scrollBarCountries.getSliderValue():0.0F;
   }

   public void drawScreen(int mouseX, int mouseY) {}

   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
      if(mouseButton == 0 && !this.hoveredAction.isEmpty()) {
         this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
         if(!this.hoveredAction.isEmpty()) {
            if(this.hoveredAction.equals("edit_photo")) {
               ClientData.lastCaptureScreenshot.put("skills", Long.valueOf(System.currentTimeMillis()));
               Minecraft.func_71410_x().func_71373_a((GuiScreen)null);
               Minecraft.func_71410_x().field_71439_g.func_70006_a(ChatMessageComponent.func_111066_d(I18n.func_135053_a("faction.take_picture")));
            } else if(this.hoveredAction.contains("filter")) {
               this.selectedSkillLeaderboard = this.hoveredAction.split("#")[1];
            } else if(this.hoveredAction.contains("checkbox")) {
               this.onlyCountryFilter = !this.onlyCountryFilter;
               PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionSkillsPlayersLeaderboardDataPacket((String)FactionGUI.factionInfos.get("name"), this.onlyCountryFilter)));
            } else {
               if(this.hoveredAction.equals("countries_leaderboard")) {
                  PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionSkillsCountriesLeaderboardDataPacket((String)FactionGUI.factionInfos.get("name"))));
               } else if(this.hoveredAction.equals("players_leaderboard")) {
                  PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionSkillsPlayersLeaderboardDataPacket((String)FactionGUI.factionInfos.get("name"), this.onlyCountryFilter)));
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
         return Double.parseDouble(str) > 0.0D;
      } catch (NumberFormatException var3) {
         return false;
      }
   }

}
