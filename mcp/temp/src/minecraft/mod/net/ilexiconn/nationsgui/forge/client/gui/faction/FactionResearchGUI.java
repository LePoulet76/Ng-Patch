package net.ilexiconn.nationsgui.forge.client.gui.faction;

import com.google.gson.internal.LinkedTreeMap;
import cpw.mods.fml.common.network.PacketDispatcher;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionResearchGUI$1;
import net.ilexiconn.nationsgui.forge.client.gui.faction.TabbedFactionGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionResearchCollectItemPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionResearchDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionResearchStartPacket;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

public class FactionResearchGUI extends TabbedFactionGUI {

   public static boolean loaded = false;
   public static HashMap<String, Object> data = new HashMap();
   public static HashMap<String, Integer> domainIconTextureX = new FactionResearchGUI$1();
   public String selectedResearch;
   public int selectedResearchLevel = 1;


   public FactionResearchGUI() {
      loaded = false;
      this.selectedResearch = null;
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionResearchDataPacket((String)FactionGUI.factionInfos.get("name"))));
   }

   public static String minutesToHumanText(double minutes) {
      int totalMinutes = (int)minutes;
      int days = totalMinutes / 1440;
      int hours = totalMinutes % 1440 / 60;
      int mins = totalMinutes % 60;
      String time = "";
      if(days > 0) {
         time = time + days + "J ";
      }

      if(hours > 0) {
         time = time + hours + "H ";
      }

      if(mins > 0) {
         time = time + mins + "MIN";
      }

      return time.trim();
   }

   public void func_73866_w_() {
      super.func_73866_w_();
   }

   public void drawScreen(int mouseX, int mouseY) {}

   public void func_73863_a(int mouseX, int mouseY, float partialTick) {
      this.func_73873_v_();
      tooltipToDraw = new ArrayList();
      this.hoveredAction = "";
      String hoveringText = "";
      ClientEventHandler.STYLE.bindTexture("faction_global_2");
      ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 30), (float)(this.guiTop + 0), (float)(0 * GUI_SCALE), (float)(0 * GUI_SCALE), (this.xSize - 30) * GUI_SCALE, this.ySize * GUI_SCALE, this.xSize - 30, this.ySize, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
      ModernGui.drawScaledStringCustomFont((String)FactionGUI.factionInfos.get("name"), (float)(this.guiLeft + 43), (float)(this.guiTop + 6), 10395075, 0.5F, "left", false, "georamaMedium", 32);
      int allConditionsValidated;
      if(this.selectedResearch == null) {
         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.research.title"), (float)(this.guiLeft + 43), (float)(this.guiTop + 16), 16777215, 0.75F, "left", false, "georamaSemiBold", 32);
         ModernGui.drawSectionStringCustomFont(I18n.func_135053_a("faction.research.desc"), (float)(this.guiLeft + 43), (float)(this.guiTop + 33), 10395075, 0.5F, "left", false, "georamaRegular", 26, 9, 500);
         if(loaded) {
            for(int research = 0; research < Math.min(5, ((LinkedTreeMap)data.get("researchesConfig")).size()); ++research) {
               boolean factionLevelForSelectedResearch = mouseX >= this.guiLeft + 43 + 85 * research && mouseX < this.guiLeft + 43 + 85 * research + 68 && mouseY >= this.guiTop + 79 && mouseY < this.guiTop + 79 + 93;
               String maxLevel = (String)((LinkedTreeMap)data.get("researchesConfig")).keySet().toArray()[research];
               allConditionsValidated = ((LinkedTreeMap)data.get("researchesLevels")).containsKey(maxLevel)?((Double)((LinkedTreeMap)data.get("researchesLevels")).get(maxLevel)).intValue():0;
               LinkedTreeMap canStartNewResearch = (LinkedTreeMap)((LinkedTreeMap)data.get("researchesConfig")).get(maxLevel);
               ClientEventHandler.STYLE.bindTexture("faction_research");
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 43 + 85 * research), (float)(this.guiTop + 79), (float)((factionLevelForSelectedResearch?72:0) * GUI_SCALE), (float)(0 * GUI_SCALE), 68 * GUI_SCALE, 93 * GUI_SCALE, 68, 93, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 46 + 85 * research), (float)(this.guiTop + 82), (float)((domainIconTextureX.containsKey(maxLevel)?((Integer)domainIconTextureX.get(maxLevel)).intValue():193) * GUI_SCALE), (float)(269 * GUI_SCALE), 61 * GUI_SCALE, 61 * GUI_SCALE, 61, 61, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
               ModernGui.drawScaledStringCustomFont(((String)canStartNewResearch.get("name")).toUpperCase(), (float)(this.guiLeft + 43 + 85 * research + 34), (float)(this.guiTop + 153), 16777215, 0.5F, "center", false, "georamaSemiBold", 30);
               ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.research.palier") + " " + allConditionsValidated + "/" + String.format("%.0f", new Object[]{(Double)canStartNewResearch.get("maxLevel")}), (float)(this.guiLeft + 43 + 85 * research + 34), (float)(this.guiTop + 161), 3618661, 0.5F, "center", false, "georamaSemiBold", 24);
               if(factionLevelForSelectedResearch) {
                  this.hoveredAction = "research#" + maxLevel;
               }
            }

            ClientEventHandler.STYLE.bindTexture("faction_research");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 43), (float)(this.guiTop + 184), (float)(0 * GUI_SCALE), (float)(97 * GUI_SCALE), 408 * GUI_SCALE, 30 * GUI_SCALE, 408, 30, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.research.last_research").toUpperCase(), (float)(this.guiLeft + 49), (float)(this.guiTop + 191), 16777215, 0.5F, "left", false, "georamaSemiBold", 30);
            String[] var27;
            String var28;
            Long var32;
            if(data.get("currentResearch") != null && !((String)data.get("currentResearch")).isEmpty()) {
               var27 = ((String)data.get("currentResearch")).split("#");
               var28 = ((LinkedTreeMap)data.get("researchesConfig")).containsKey(var27[0])?(String)((LinkedTreeMap)((LinkedTreeMap)data.get("researchesConfig")).get(var27[0])).get("name"):"unknown";
               ModernGui.drawScaledStringCustomFont(var28 + " - " + I18n.func_135053_a("faction.research.palier") + " " + var27[1], (float)(this.guiLeft + 49), (float)(this.guiTop + 202), 12237530, 0.5F, "left", false, "georamaMedium", 26);
            } else if(data.get("lastResearch") != null && !((String)data.get("lastResearch")).isEmpty()) {
               var27 = ((String)data.get("lastResearch")).split("#");
               var28 = ((LinkedTreeMap)data.get("researchesConfig")).containsKey(var27[0])?(String)((LinkedTreeMap)((LinkedTreeMap)data.get("researchesConfig")).get(var27[0])).get("name"):"unknown";
               var32 = Long.valueOf(Long.parseLong(var27[2]));
               SimpleDateFormat var34 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
               String var36 = var34.format(var32);
               ModernGui.drawScaledStringCustomFont(var28 + " - " + I18n.func_135053_a("faction.research.palier") + " " + var27[1] + " \u00a7o(" + var36 + ")", (float)(this.guiLeft + 49), (float)(this.guiTop + 202), 12237530, 0.5F, "left", false, "georamaMedium", 26);
            } else {
               ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.research.last_research.none"), (float)(this.guiLeft + 49), (float)(this.guiTop + 202), 12237530, 0.5F, "left", false, "georamaMedium", 26);
            }

            if(data.get("currentResearch") != null && !((String)data.get("currentResearch")).isEmpty()) {
               var27 = ((String)data.get("currentResearch")).split("#");
               Long var30 = Long.valueOf(Long.parseLong(var27[2]));
               var32 = Long.valueOf(((LinkedTreeMap)data.get("researchesConfig")).containsKey(var27[0])?((Double)((LinkedTreeMap)((LinkedTreeMap)((LinkedTreeMap)((LinkedTreeMap)data.get("researchesConfig")).get(var27[0])).get("levels")).get(var27[1])).get("duration")).longValue():0L);
               Long var35 = Long.valueOf(var30.longValue() + var32.longValue() * 60L * 1000L);
               ModernGui.drawScaledStringCustomFont(System.currentTimeMillis() < var35.longValue()?ModernGui.getFormattedTimeDiff(var35.longValue(), System.currentTimeMillis()) + " " + I18n.func_135053_a("faction.research.last_research.remaining"):I18n.func_135053_a("faction.research.last_research.ending"), (float)(this.guiLeft + 216), (float)(this.guiTop + 187), 7239406, 0.5F, "left", false, "georamaSemiBold", 26);
               ClientEventHandler.STYLE.bindTexture("faction_research");
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 216), (float)(this.guiTop + 196), (float)(0 * GUI_SCALE), (float)(131 * GUI_SCALE), 223 * GUI_SCALE, 6 * GUI_SCALE, 223, 6, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
               double var38 = (double)((float)(System.currentTimeMillis() - var30.longValue()) / (float)(var35.longValue() - var30.longValue()));
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 216), (float)(this.guiTop + 196), (float)(0 * GUI_SCALE), (float)(141 * GUI_SCALE), (int)(223.0D * var38 * (double)GUI_SCALE), 6 * GUI_SCALE, (int)(223.0D * var38), 6, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
               ModernGui.drawScaledStringCustomFont(String.format("%.0f", new Object[]{Double.valueOf(Math.min(100.0D, var38 * 100.0D))}) + "%", (float)(this.guiLeft + 216), (float)(this.guiTop + 205), 12237530, 0.5F, "left", false, "georamaSemiBold", 26);
               ModernGui.drawScaledStringCustomFont(this.formatMinutes(var32.intValue()), (float)(this.guiLeft + 436), (float)(this.guiTop + 205), 12237530, 0.5F, "right", false, "georamaSemiBold", 26);
            }
         }
      } else {
         ClientEventHandler.STYLE.bindTexture("faction_settings");
         if(mouseX >= this.guiLeft + 403 && mouseX <= this.guiLeft + 403 + 40 && mouseY >= this.guiTop + 9 && mouseY <= this.guiTop + 9 + 6) {
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 403), (float)(this.guiTop + 8), (float)(206 * GUI_SCALE), (float)(34 * GUI_SCALE), 6 * GUI_SCALE, 9 * GUI_SCALE, 6, 9, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.settings.label.back"), (float)(this.guiLeft + 412), (float)(this.guiTop + 9), 16777215, 0.5F, "left", false, "georamaSemiBold", 30);
            this.hoveredAction = "back";
         } else {
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 403), (float)(this.guiTop + 8), (float)(197 * GUI_SCALE), (float)(34 * GUI_SCALE), 6 * GUI_SCALE, 9 * GUI_SCALE, 6, 9, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.settings.label.back"), (float)(this.guiLeft + 412), (float)(this.guiTop + 9), 10395075, 0.5F, "left", false, "georamaSemiBold", 30);
         }

         LinkedTreeMap var29 = (LinkedTreeMap)((LinkedTreeMap)data.get("researchesConfig")).get(this.selectedResearch);
         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.research.selected.title") + " \"" + var29.get("name") + "\"", (float)(this.guiLeft + 43), (float)(this.guiTop + 16), 16777215, 0.75F, "left", false, "georamaSemiBold", 32);
         if(var29.get("description") != null) {
            ModernGui.drawSectionStringCustomFont((String)var29.get("description"), (float)(this.guiLeft + 43), (float)(this.guiTop + 33), 10395075, 0.5F, "left", false, "georamaRegular", 26, 9, 500);
         }

         int var31 = ((LinkedTreeMap)data.get("researchesLevels")).containsKey(this.selectedResearch)?((Double)((LinkedTreeMap)data.get("researchesLevels")).get(this.selectedResearch)).intValue():0;
         int var33 = ((Double)((LinkedTreeMap)((LinkedTreeMap)data.get("researchesConfig")).get(this.selectedResearch)).get("maxLevel")).intValue();

         boolean var39;
         for(allConditionsValidated = -1; allConditionsValidated < 3; ++allConditionsValidated) {
            if(this.selectedResearchLevel + allConditionsValidated <= var33) {
               ClientEventHandler.STYLE.bindTexture("faction_research");
               if(this.selectedResearchLevel + allConditionsValidated < var33) {
                  ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 160 + allConditionsValidated * 95), (float)(this.guiTop + 85), (float)(0 * GUI_SCALE), (float)((var31 >= this.selectedResearchLevel + allConditionsValidated?160:151) * GUI_SCALE), 99 * GUI_SCALE, 5 * GUI_SCALE, 99, 5, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
               }

               var39 = this.selectedResearchLevel + allConditionsValidated != 0 && allConditionsValidated != 0 && mouseX >= this.guiLeft + 160 + allConditionsValidated * 95 - (allConditionsValidated == 0?49:41) / 2 && mouseX <= this.guiLeft + 160 + allConditionsValidated * 95 - (allConditionsValidated == 0?49:41) / 2 + (allConditionsValidated == 0?49:41) && mouseY >= this.guiTop + 87 - (allConditionsValidated == 0?54:46) / 2 && mouseY <= this.guiTop + 87 - (allConditionsValidated == 0?54:46) / 2 + (allConditionsValidated == 0?54:46);
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 160 + allConditionsValidated * 95 - (allConditionsValidated == 0?49:41) / 2), (float)(this.guiTop + 87 - (allConditionsValidated == 0?54:46) / 2), (float)((allConditionsValidated == 0?52:(var39?112:0)) * GUI_SCALE), (float)((var31 >= this.selectedResearchLevel + allConditionsValidated && !var39?169:(var31 != this.selectedResearchLevel + allConditionsValidated - 1 && !var39?229:288)) * GUI_SCALE), (allConditionsValidated == 0?49:41) * GUI_SCALE, (allConditionsValidated == 0?54:46) * GUI_SCALE, allConditionsValidated == 0?49:41, allConditionsValidated == 0?54:46, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
               if(var31 == this.selectedResearchLevel + allConditionsValidated - 1) {
                  ModernGui.drawScaledStringCustomFont(this.selectedResearchLevel + allConditionsValidated + "", (float)(this.guiLeft + 161 + allConditionsValidated * 95), (float)(this.guiTop + 80), 1381671, 0.75F, "center", false, "georamaBold", 45);
               } else if(var31 >= this.selectedResearchLevel + allConditionsValidated && this.selectedResearchLevel + allConditionsValidated != 0) {
                  ClientEventHandler.STYLE.bindTexture("faction_research");
                  ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 160 + allConditionsValidated * 95 - 24 + 14), (float)(this.guiTop + 87 - 27 + 20), (float)(172 * GUI_SCALE), (float)(37 * GUI_SCALE), 21 * GUI_SCALE, 15 * GUI_SCALE, 21, 15, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
               } else {
                  ModernGui.drawScaledStringCustomFont(this.selectedResearchLevel + allConditionsValidated + "", (float)(this.guiLeft + 161 + allConditionsValidated * 95), (float)(this.guiTop + 81), 2434373, 0.75F, "center", false, "georamaSemiBold", 35);
               }

               if(var39) {
                  this.hoveredAction = "level#" + (this.selectedResearchLevel + allConditionsValidated);
               }
            }
         }

         ClientEventHandler.STYLE.bindTexture("faction_research");
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 140), (float)(this.guiTop + 124), (float)(200 * GUI_SCALE), (float)(0 * GUI_SCALE), 41 * GUI_SCALE, 46 * GUI_SCALE, 41, 46, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 49), (float)(this.guiTop + 133), (float)(0 * GUI_SCALE), (float)(352 * GUI_SCALE), 401 * GUI_SCALE, 87 * GUI_SCALE, 401, 87, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.research.palier").toUpperCase() + " " + this.selectedResearchLevel, (float)(this.guiLeft + 55), (float)(this.guiTop + 143), 16316667, 0.5F, "left", false, "georamaSemiBold", 30);
         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.research.palier.infos.conditions").toUpperCase(), (float)(this.guiLeft + 55), (float)(this.guiTop + 152), 16316667, 0.5F, "left", false, "georamaMedium", 24);
         boolean var37 = false;
         if(((LinkedTreeMap)((LinkedTreeMap)((LinkedTreeMap)data.get("researchesConfig")).get(this.selectedResearch)).get("levels")).containsKey(this.selectedResearchLevel + "")) {
            var37 = true;
            ArrayList var40 = (ArrayList)((LinkedTreeMap)((LinkedTreeMap)((LinkedTreeMap)((LinkedTreeMap)data.get("researchesConfig")).get(this.selectedResearch)).get("levels")).get(this.selectedResearchLevel + "")).get("conditions");
            Double isHovered = (Double)((LinkedTreeMap)((LinkedTreeMap)((LinkedTreeMap)((LinkedTreeMap)data.get("researchesConfig")).get(this.selectedResearch)).get("levels")).get(this.selectedResearchLevel + "")).get("duration");
            LinkedTreeMap countryConditionsValues = (LinkedTreeMap)data.get("researchesConditionsValues");

            int i;
            int x;
            int y;
            String conditionName;
            int reward;
            double x1;
            boolean validCondition;
            String conditionString;
            String conditionStringFormat;
            String itemIdMeta;
            String[] itemIdMetaSplit;
            int itemId;
            int itemMeta;
            ItemStack stack;
            for(i = 0; i < Math.min(4, var40.size()); ++i) {
               x = this.guiLeft + 55;
               y = this.guiTop + 167 + i % 4 * 9;
               conditionName = ((String)var40.get(i)).split("#")[0];
               reward = ((String)var40.get(i)).split("#").length > 1?Integer.parseInt(((String)var40.get(i)).split("#")[1]):1;
               x1 = countryConditionsValues.containsKey(conditionName)?((Double)countryConditionsValues.get(conditionName)).doubleValue():0.0D;
               ClientEventHandler.STYLE.bindTexture("faction_research");
               validCondition = x1 >= (double)reward;
               if(conditionName.equals("notations")) {
                  validCondition = (double)reward >= x1 && x1 > 0.0D;
               }

               if(conditionName.matches("(priceitem)_[\\d]+:[\\d]+") && mouseX >= x && mouseX <= x + 7 && (float)mouseY >= (float)y - 0.5F && (float)mouseY <= (float)y + 7.5F && !validCondition && this.selectedResearchLevel == var31 + 1 && ((Boolean)FactionGUI.factionInfos.get("isInCountry")).booleanValue()) {
                  ModernGui.drawScaledCustomSizeModalRect((float)x, (float)y - 0.5F, (float)(157 * GUI_SCALE), (float)(27 * GUI_SCALE), 7 * GUI_SCALE, 7 * GUI_SCALE, 7, 7, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                  this.hoveredAction = "deposit#" + conditionName + ":" + (int)Math.max(0.0D, (double)reward - x1);
               } else {
                  ModernGui.drawScaledCustomSizeModalRect((float)x, (float)y - 0.5F, (float)(157 * GUI_SCALE), (float)((conditionName.equals("pay")?34:(validCondition?13:20)) * GUI_SCALE), 7 * GUI_SCALE, 7 * GUI_SCALE, 7, 7, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                  if(conditionName.equals("pay") && mouseX >= x && mouseX <= x + 7 && (float)mouseY >= (float)y - 0.5F && (float)mouseY <= (float)y + 7.5F) {
                     tooltipToDraw = Arrays.asList(I18n.func_135053_a("faction.research.tooltip.pay").split("##"));
                  }
               }

               conditionString = I18n.func_135053_a("faction.research.conditions.label." + conditionName);
               if(conditionName.matches("(craft|priceitem|blockbreak|crush|drop)_[\\d]+:[\\d]+")) {
                  conditionStringFormat = conditionName.split("_")[0];
                  itemIdMeta = conditionName.split("_")[1];
                  itemIdMetaSplit = itemIdMeta.split(":");
                  itemId = Integer.parseInt(itemIdMetaSplit[0]);
                  itemMeta = itemIdMetaSplit.length > 1?Integer.parseInt(itemIdMetaSplit[1]):0;
                  stack = new ItemStack(itemId, 1, itemMeta);
                  conditionString = I18n.func_135053_a("faction.research.conditions.label." + conditionStringFormat) + " " + stack.func_82833_r();
               }

               conditionStringFormat = conditionName.contains("notations")?"%.2f":"%.0f";
               conditionString = conditionString.replaceAll("<value>", String.format(conditionStringFormat, new Object[]{Double.valueOf(x1)})).replaceAll("<condition>", reward + "");
               if(mouseX >= x + 9 && mouseX <= x + 9 + 70 && mouseY >= y && mouseY <= y + 9) {
                  hoveringText = conditionString;
               }

               if(conditionString.length() > 33) {
                  conditionString = conditionString.substring(0, 32) + "..";
               }

               ModernGui.drawScaledStringCustomFont(conditionString, (float)(x + 9), (float)y, validCondition?7239406:10395075, 0.5F, "left", false, "georamaMedium", 23);
               if(!validCondition) {
                  var37 = false;
               }
            }

            for(i = 4; i < var40.size(); ++i) {
               x = this.guiLeft + 155;
               y = this.guiTop + (167 - Math.max(0, var40.size() - 8) * 9) + (i - 4) * 9;
               conditionName = ((String)var40.get(i)).split("#")[0];
               reward = ((String)var40.get(i)).split("#").length > 1?Integer.parseInt(((String)var40.get(i)).split("#")[1]):1;
               x1 = countryConditionsValues.containsKey(conditionName)?((Double)countryConditionsValues.get(conditionName)).doubleValue():0.0D;
               validCondition = x1 >= (double)reward;
               if(conditionName.equals("notations")) {
                  validCondition = (double)reward >= x1 && x1 > 0.0D;
               }

               ClientEventHandler.STYLE.bindTexture("faction_research");
               if(conditionName.matches("(priceitem)_[\\d]+:[\\d]+") && mouseX >= x && mouseX <= x + 7 && (float)mouseY >= (float)y - 0.5F && (float)mouseY <= (float)y + 7.5F && !validCondition && this.selectedResearchLevel == var31 + 1 && ((Boolean)FactionGUI.factionInfos.get("isInCountry")).booleanValue()) {
                  ModernGui.drawScaledCustomSizeModalRect((float)x, (float)y - 0.5F, (float)(157 * GUI_SCALE), (float)(27 * GUI_SCALE), 7 * GUI_SCALE, 7 * GUI_SCALE, 7, 7, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                  this.hoveredAction = "deposit#" + conditionName + ":" + (int)Math.max(0.0D, (double)reward - x1);
               } else {
                  if(conditionName.equals("pay") && mouseX >= x && mouseX <= x + 7 && (float)mouseY >= (float)y - 0.5F && (float)mouseY <= (float)y + 7.5F) {
                     tooltipToDraw = Arrays.asList(I18n.func_135053_a("faction.research.tooltip.pay").split("##"));
                  }

                  ModernGui.drawScaledCustomSizeModalRect((float)x, (float)y - 0.5F, (float)(157 * GUI_SCALE), (float)((conditionName.equals("pay")?34:(validCondition?13:20)) * GUI_SCALE), 7 * GUI_SCALE, 7 * GUI_SCALE, 7, 7, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
               }

               conditionString = I18n.func_135053_a("faction.research.conditions.label." + conditionName);
               if(conditionName.matches("(craft|priceitem|blockbreak|crush|drop)_[\\d]+:[\\d]+")) {
                  conditionStringFormat = conditionName.split("_")[0];
                  itemIdMeta = conditionName.split("_")[1];
                  itemIdMetaSplit = itemIdMeta.split(":");
                  itemId = Integer.parseInt(itemIdMetaSplit[0]);
                  itemMeta = itemIdMetaSplit.length > 1?Integer.parseInt(itemIdMetaSplit[1]):0;
                  stack = new ItemStack(itemId, 1, itemMeta);
                  conditionString = I18n.func_135053_a("faction.research.conditions.label." + conditionStringFormat) + " " + stack.func_82833_r();
               }

               conditionStringFormat = conditionName.contains("notations")?"%.1f":"%.0f";
               conditionString = conditionString.replaceAll("<value>", String.format(conditionStringFormat, new Object[]{Double.valueOf(x1)})).replaceAll("<condition>", reward + "");
               if(mouseX >= x + 9 && mouseX <= x + 9 + 70 && mouseY >= y && mouseY <= y + 9) {
                  hoveringText = conditionString;
               }

               if(conditionString.length() > 32) {
                  conditionString = conditionString.substring(0, 31) + "..";
               }

               ModernGui.drawScaledStringCustomFont(conditionString, (float)(x + 9), (float)y, validCondition?7239406:10395075, 0.5F, "left", false, "georamaMedium", 23);
               if(!validCondition) {
                  var37 = false;
               }
            }

            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.research.palier.infos.search_time").replaceAll("<time>", minutesToHumanText(isHovered.doubleValue())).toUpperCase(), (float)(this.guiLeft + 270), (float)(this.guiTop + 143), 16316667, 0.5F, "left", false, "georamaSemiBold", 30);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.research.palier.infos.rewards").toUpperCase(), (float)(this.guiLeft + 258), (float)(this.guiTop + 152), 16316667, 0.5F, "left", false, "georamaMedium", 24);
            i = 0;
            String[] var42 = I18n.func_135053_a("faction.research.rewards." + this.selectedResearch + "." + this.selectedResearchLevel).split("#");
            y = var42.length;

            for(int var43 = 0; var43 < y; ++var43) {
               String var44 = var42[var43];
               int var45 = this.guiLeft + 258 + i / 4 * 100;
               int y1 = this.guiTop + 167 + i % 4 * 9;
               if(var44.length() > 35) {
                  var44 = var44.substring(0, 34) + "..";
               }

               ModernGui.drawScaledStringCustomFont(var44, (float)var45, (float)y1, 10395075, 0.5F, "left", false, "georamaMedium", 23);
               ++i;
            }
         }

         if(this.selectedResearchLevel > 1) {
            ClientEventHandler.STYLE.bindTexture("faction_research");
            if(mouseX >= this.guiLeft + 39 && mouseX <= this.guiLeft + 39 + 8 && mouseY >= this.guiTop + 166 && mouseY <= this.guiTop + 166 + 16) {
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 39), (float)(this.guiTop + 166), (float)(182 * GUI_SCALE), (float)(18 * GUI_SCALE), 8 * GUI_SCALE, 16 * GUI_SCALE, 8, 16, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
               this.hoveredAction = "levels_previous";
            } else {
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 39), (float)(this.guiTop + 166), (float)(172 * GUI_SCALE), (float)(18 * GUI_SCALE), 8 * GUI_SCALE, 16 * GUI_SCALE, 8, 16, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
            }
         }

         ClientEventHandler.STYLE.bindTexture("faction_research");
         if(mouseX >= this.guiLeft + 452 && mouseX <= this.guiLeft + 452 + 8 && mouseY >= this.guiTop + 167 && mouseY <= this.guiTop + 167 + 16) {
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 452), (float)(this.guiTop + 167), (float)(182 * GUI_SCALE), (float)(0 * GUI_SCALE), 8 * GUI_SCALE, 16 * GUI_SCALE, 8, 16, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
            this.hoveredAction = "levels_next";
         } else {
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 452), (float)(this.guiTop + 167), (float)(172 * GUI_SCALE), (float)(0 * GUI_SCALE), 8 * GUI_SCALE, 16 * GUI_SCALE, 8, 16, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
         }

         if(this.selectedResearchLevel > var31 && ((Boolean)FactionGUI.factionInfos.get("isInCountry")).booleanValue() && ((Boolean)FactionGUI.factionInfos.get("isAtLeastOfficer")).booleanValue()) {
            var39 = true;
            boolean var41 = false;
            if(mouseX >= this.guiLeft + 190 && mouseX <= this.guiLeft + 190 + 112 && mouseY >= this.guiTop + 204 && mouseY <= this.guiTop + 204 + 13) {
               var41 = true;
               if(var37 && var39) {
                  this.hoveredAction = "research";
               }
            }

            if(this.selectedResearchLevel != var31 + 1) {
               var39 = false;
               if(var41) {
                  hoveringText = I18n.func_135053_a("faction.research.palier.wrong.level");
               }
            } else if(!var37) {
               var39 = false;
               if(var41) {
                  hoveringText = I18n.func_135053_a("faction.research.palier.wrong.conditions");
               }
            } else if(data.get("currentResearch") != null) {
               var39 = false;
               if(var41) {
                  hoveringText = I18n.func_135053_a("faction.research.palier.wrong.already");
               }
            }

            ClientEventHandler.STYLE.bindTexture("faction_research");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 190), (float)(this.guiTop + 204), (float)(0 * GUI_SCALE), (float)((var37 && var39 && !var41?471:454) * GUI_SCALE), 112 * GUI_SCALE, 13 * GUI_SCALE, 112, 13, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.research.palier.infos.start_research"), (float)(this.guiLeft + 190 + 56), (float)(this.guiTop + 204 + 3), var37 && var39 && !var41?15463162:3026518, 0.5F, "center", false, "georamaSemiBold", 26);
         }

         if(!hoveringText.isEmpty()) {
            this.drawHoveringText(Arrays.asList(new String[]{hoveringText}), mouseX, mouseY, this.field_73886_k);
         }
      }

      super.func_73863_a(mouseX, mouseY, partialTick);
   }

   public String formatMinutes(int minutes) {
      long hours = TimeUnit.MINUTES.toHours((long)minutes);
      long remainingMinutes = (long)minutes - TimeUnit.HOURS.toMinutes(hours);
      long seconds = 0L;
      return String.format("%02d:%02d:%02d", new Object[]{Long.valueOf(hours), Long.valueOf(remainingMinutes), Long.valueOf(seconds)});
   }

   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
      if(mouseButton == 0 && !this.hoveredAction.isEmpty()) {
         this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
         int maxLevel;
         if(this.hoveredAction.contains("research#")) {
            this.selectedResearch = this.hoveredAction.replaceAll("research#", "");
            this.selectedResearchLevel = 1;
            if(((LinkedTreeMap)data.get("researchesLevels")).containsKey(this.selectedResearch)) {
               maxLevel = ((Double)((LinkedTreeMap)((LinkedTreeMap)data.get("researchesConfig")).get(this.selectedResearch)).get("maxLevel")).intValue();
               this.selectedResearchLevel = Math.min(maxLevel, ((Double)((LinkedTreeMap)data.get("researchesLevels")).get(this.selectedResearch)).intValue() + 1);
            }
         } else if(this.hoveredAction.equals("levels_previous")) {
            this.selectedResearchLevel = Math.max(1, this.selectedResearchLevel - 1);
         } else if(this.hoveredAction.equals("levels_next")) {
            maxLevel = ((Double)((LinkedTreeMap)((LinkedTreeMap)data.get("researchesConfig")).get(this.selectedResearch)).get("maxLevel")).intValue();
            this.selectedResearchLevel = Math.min(maxLevel, this.selectedResearchLevel + 1);
         } else if(this.hoveredAction.contains("level#")) {
            this.selectedResearchLevel = Integer.parseInt(this.hoveredAction.replaceAll("level#", ""));
         } else if(this.hoveredAction.equals("back")) {
            this.selectedResearch = null;
         } else if(this.hoveredAction.equals("research")) {
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionResearchStartPacket(this.selectedResearch, this.selectedResearchLevel)));
            this.selectedResearch = null;
         } else if(this.hoveredAction.contains("deposit#")) {
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionResearchCollectItemPacket(this.hoveredAction.replaceAll("deposit#", ""))));
         }

         this.hoveredAction = "";
      }

      super.func_73864_a(mouseX, mouseY, mouseButton);
   }

}
