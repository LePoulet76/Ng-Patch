/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.internal.LinkedTreeMap
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.packet.Packet
 */
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
import net.ilexiconn.nationsgui.forge.client.gui.faction.TabbedFactionGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionResearchCollectItemPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionResearchDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionResearchStartPacket;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;

public class FactionResearchGUI
extends TabbedFactionGUI {
    public static boolean loaded = false;
    public static HashMap<String, Object> data = new HashMap();
    public static HashMap<String, Integer> domainIconTextureX = new HashMap<String, Integer>(){
        {
            this.put("resource", 193);
            this.put("military", 254);
            this.put("general", 315);
            this.put("industry", 376);
            this.put("technology", 437);
        }
    };
    public String selectedResearch;
    public int selectedResearchLevel = 1;

    public FactionResearchGUI() {
        loaded = false;
        this.selectedResearch = null;
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionResearchDataPacket((String)FactionGUI.factionInfos.get("name"))));
    }

    public static String minutesToHumanText(double minutes) {
        int totalMinutes = (int)minutes;
        int days = totalMinutes / 1440;
        int hours = totalMinutes % 1440 / 60;
        int mins = totalMinutes % 60;
        String time = "";
        if (days > 0) {
            time = time + days + "J ";
        }
        if (hours > 0) {
            time = time + hours + "H ";
        }
        if (mins > 0) {
            time = time + mins + "MIN";
        }
        return time.trim();
    }

    @Override
    public void func_73866_w_() {
        super.func_73866_w_();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
    }

    @Override
    public void func_73863_a(int mouseX, int mouseY, float partialTick) {
        this.func_73873_v_();
        tooltipToDraw = new ArrayList();
        this.hoveredAction = "";
        String hoveringText = "";
        ClientEventHandler.STYLE.bindTexture("faction_global_2");
        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 30, this.guiTop + 0, 0 * GUI_SCALE, 0 * GUI_SCALE, (this.xSize - 30) * GUI_SCALE, this.ySize * GUI_SCALE, this.xSize - 30, this.ySize, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
        ModernGui.drawScaledStringCustomFont((String)FactionGUI.factionInfos.get("name"), this.guiLeft + 43, this.guiTop + 6, 10395075, 0.5f, "left", false, "georamaMedium", 32);
        if (this.selectedResearch == null) {
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.research.title"), this.guiLeft + 43, this.guiTop + 16, 0xFFFFFF, 0.75f, "left", false, "georamaSemiBold", 32);
            ModernGui.drawSectionStringCustomFont(I18n.func_135053_a((String)"faction.research.desc"), this.guiLeft + 43, this.guiTop + 33, 10395075, 0.5f, "left", false, "georamaRegular", 26, 9, 500);
            if (loaded) {
                for (int i = 0; i < Math.min(5, ((LinkedTreeMap)data.get("researchesConfig")).size()); ++i) {
                    boolean isHovered = mouseX >= this.guiLeft + 43 + 85 * i && mouseX < this.guiLeft + 43 + 85 * i + 68 && mouseY >= this.guiTop + 79 && mouseY < this.guiTop + 79 + 93;
                    String domain = (String)((LinkedTreeMap)data.get("researchesConfig")).keySet().toArray()[i];
                    int factionLevelForSelectedResearch = ((LinkedTreeMap)data.get("researchesLevels")).containsKey((Object)domain) ? ((Double)((LinkedTreeMap)data.get("researchesLevels")).get((Object)domain)).intValue() : 0;
                    LinkedTreeMap research = (LinkedTreeMap)((LinkedTreeMap)data.get("researchesConfig")).get((Object)domain);
                    ClientEventHandler.STYLE.bindTexture("faction_research");
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 43 + 85 * i, this.guiTop + 79, (isHovered ? 72 : 0) * GUI_SCALE, 0 * GUI_SCALE, 68 * GUI_SCALE, 93 * GUI_SCALE, 68, 93, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 46 + 85 * i, this.guiTop + 82, (domainIconTextureX.containsKey(domain) ? domainIconTextureX.get(domain) : 193) * GUI_SCALE, 269 * GUI_SCALE, 61 * GUI_SCALE, 61 * GUI_SCALE, 61, 61, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    ModernGui.drawScaledStringCustomFont(((String)research.get((Object)"name")).toUpperCase(), this.guiLeft + 43 + 85 * i + 34, this.guiTop + 153, 0xFFFFFF, 0.5f, "center", false, "georamaSemiBold", 30);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.research.palier") + " " + factionLevelForSelectedResearch + "/" + String.format("%.0f", (Double)research.get((Object)"maxLevel")), this.guiLeft + 43 + 85 * i + 34, this.guiTop + 161, 3618661, 0.5f, "center", false, "georamaSemiBold", 24);
                    if (!isHovered) continue;
                    this.hoveredAction = "research#" + domain;
                }
                ClientEventHandler.STYLE.bindTexture("faction_research");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 43, this.guiTop + 184, 0 * GUI_SCALE, 97 * GUI_SCALE, 408 * GUI_SCALE, 30 * GUI_SCALE, 408, 30, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.research.last_research").toUpperCase(), this.guiLeft + 49, this.guiTop + 191, 0xFFFFFF, 0.5f, "left", false, "georamaSemiBold", 30);
                if (data.get("currentResearch") != null && !((String)data.get("currentResearch")).isEmpty()) {
                    String[] currentResearch = ((String)data.get("currentResearch")).split("#");
                    String domain = ((LinkedTreeMap)data.get("researchesConfig")).containsKey((Object)currentResearch[0]) ? (String)((LinkedTreeMap)((LinkedTreeMap)data.get("researchesConfig")).get((Object)currentResearch[0])).get((Object)"name") : "unknown";
                    ModernGui.drawScaledStringCustomFont(domain + " - " + I18n.func_135053_a((String)"faction.research.palier") + " " + currentResearch[1], this.guiLeft + 49, this.guiTop + 202, 0xBABADA, 0.5f, "left", false, "georamaMedium", 26);
                } else if (data.get("lastResearch") != null && !((String)data.get("lastResearch")).isEmpty()) {
                    String[] lastResearch = ((String)data.get("lastResearch")).split("#");
                    String domain = ((LinkedTreeMap)data.get("researchesConfig")).containsKey((Object)lastResearch[0]) ? (String)((LinkedTreeMap)((LinkedTreeMap)data.get("researchesConfig")).get((Object)lastResearch[0])).get((Object)"name") : "unknown";
                    Long time = Long.parseLong(lastResearch[2]);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    String formattedDate = sdf.format(time);
                    ModernGui.drawScaledStringCustomFont(domain + " - " + I18n.func_135053_a((String)"faction.research.palier") + " " + lastResearch[1] + " \u00a7o(" + formattedDate + ")", this.guiLeft + 49, this.guiTop + 202, 0xBABADA, 0.5f, "left", false, "georamaMedium", 26);
                } else {
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.research.last_research.none"), this.guiLeft + 49, this.guiTop + 202, 0xBABADA, 0.5f, "left", false, "georamaMedium", 26);
                }
                if (data.get("currentResearch") != null && !((String)data.get("currentResearch")).isEmpty()) {
                    String[] currentResearch = ((String)data.get("currentResearch")).split("#");
                    Long startTime = Long.parseLong(currentResearch[2]);
                    Long durationMinute = ((LinkedTreeMap)data.get("researchesConfig")).containsKey((Object)currentResearch[0]) ? ((Double)((LinkedTreeMap)((LinkedTreeMap)((LinkedTreeMap)((LinkedTreeMap)data.get("researchesConfig")).get((Object)currentResearch[0])).get((Object)"levels")).get((Object)currentResearch[1])).get((Object)"duration")).longValue() : 0L;
                    Long endTime = startTime + durationMinute * 60L * 1000L;
                    ModernGui.drawScaledStringCustomFont(System.currentTimeMillis() < endTime ? ModernGui.getFormattedTimeDiff(endTime, System.currentTimeMillis()) + " " + I18n.func_135053_a((String)"faction.research.last_research.remaining") : I18n.func_135053_a((String)"faction.research.last_research.ending"), this.guiLeft + 216, this.guiTop + 187, 0x6E76EE, 0.5f, "left", false, "georamaSemiBold", 26);
                    ClientEventHandler.STYLE.bindTexture("faction_research");
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 216, this.guiTop + 196, 0 * GUI_SCALE, 131 * GUI_SCALE, 223 * GUI_SCALE, 6 * GUI_SCALE, 223, 6, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    double progress = (float)(System.currentTimeMillis() - startTime) / (float)(endTime - startTime);
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 216, this.guiTop + 196, 0 * GUI_SCALE, 141 * GUI_SCALE, (int)(223.0 * progress * (double)GUI_SCALE), 6 * GUI_SCALE, (int)(223.0 * progress), 6, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    ModernGui.drawScaledStringCustomFont(String.format("%.0f", Math.min(100.0, progress * 100.0)) + "%", this.guiLeft + 216, this.guiTop + 205, 0xBABADA, 0.5f, "left", false, "georamaSemiBold", 26);
                    ModernGui.drawScaledStringCustomFont(this.formatMinutes(durationMinute.intValue()), this.guiLeft + 436, this.guiTop + 205, 0xBABADA, 0.5f, "right", false, "georamaSemiBold", 26);
                }
            }
        } else {
            ClientEventHandler.STYLE.bindTexture("faction_settings");
            if (mouseX >= this.guiLeft + 403 && mouseX <= this.guiLeft + 403 + 40 && mouseY >= this.guiTop + 9 && mouseY <= this.guiTop + 9 + 6) {
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 403, this.guiTop + 8, 206 * GUI_SCALE, 34 * GUI_SCALE, 6 * GUI_SCALE, 9 * GUI_SCALE, 6, 9, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.settings.label.back"), this.guiLeft + 412, this.guiTop + 9, 0xFFFFFF, 0.5f, "left", false, "georamaSemiBold", 30);
                this.hoveredAction = "back";
            } else {
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 403, this.guiTop + 8, 197 * GUI_SCALE, 34 * GUI_SCALE, 6 * GUI_SCALE, 9 * GUI_SCALE, 6, 9, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.settings.label.back"), this.guiLeft + 412, this.guiTop + 9, 10395075, 0.5f, "left", false, "georamaSemiBold", 30);
            }
            LinkedTreeMap research = (LinkedTreeMap)((LinkedTreeMap)data.get("researchesConfig")).get((Object)this.selectedResearch);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.research.selected.title") + " \"" + research.get((Object)"name") + "\"", this.guiLeft + 43, this.guiTop + 16, 0xFFFFFF, 0.75f, "left", false, "georamaSemiBold", 32);
            if (research.get((Object)"description") != null) {
                ModernGui.drawSectionStringCustomFont((String)research.get((Object)"description"), this.guiLeft + 43, this.guiTop + 33, 10395075, 0.5f, "left", false, "georamaRegular", 26, 9, 500);
            }
            int factionLevelForSelectedResearch = ((LinkedTreeMap)data.get("researchesLevels")).containsKey((Object)this.selectedResearch) ? ((Double)((LinkedTreeMap)data.get("researchesLevels")).get((Object)this.selectedResearch)).intValue() : 0;
            int maxLevel = ((Double)((LinkedTreeMap)((LinkedTreeMap)data.get("researchesConfig")).get((Object)this.selectedResearch)).get((Object)"maxLevel")).intValue();
            for (int i = -1; i < 3; ++i) {
                if (this.selectedResearchLevel + i > maxLevel) continue;
                ClientEventHandler.STYLE.bindTexture("faction_research");
                if (this.selectedResearchLevel + i < maxLevel) {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 160 + i * 95, this.guiTop + 85, 0 * GUI_SCALE, (factionLevelForSelectedResearch >= this.selectedResearchLevel + i ? 160 : 151) * GUI_SCALE, 99 * GUI_SCALE, 5 * GUI_SCALE, 99, 5, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                }
                boolean isHovered = this.selectedResearchLevel + i != 0 && i != 0 && mouseX >= this.guiLeft + 160 + i * 95 - (i == 0 ? 49 : 41) / 2 && mouseX <= this.guiLeft + 160 + i * 95 - (i == 0 ? 49 : 41) / 2 + (i == 0 ? 49 : 41) && mouseY >= this.guiTop + 87 - (i == 0 ? 54 : 46) / 2 && mouseY <= this.guiTop + 87 - (i == 0 ? 54 : 46) / 2 + (i == 0 ? 54 : 46);
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 160 + i * 95 - (i == 0 ? 49 : 41) / 2, this.guiTop + 87 - (i == 0 ? 54 : 46) / 2, (i == 0 ? 52 : (isHovered ? 112 : 0)) * GUI_SCALE, (factionLevelForSelectedResearch >= this.selectedResearchLevel + i && !isHovered ? 169 : (factionLevelForSelectedResearch == this.selectedResearchLevel + i - 1 || isHovered ? 288 : 229)) * GUI_SCALE, (i == 0 ? 49 : 41) * GUI_SCALE, (i == 0 ? 54 : 46) * GUI_SCALE, i == 0 ? 49 : 41, i == 0 ? 54 : 46, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                if (factionLevelForSelectedResearch == this.selectedResearchLevel + i - 1) {
                    ModernGui.drawScaledStringCustomFont(this.selectedResearchLevel + i + "", this.guiLeft + 161 + i * 95, this.guiTop + 80, 1381671, 0.75f, "center", false, "georamaBold", 45);
                } else if (factionLevelForSelectedResearch >= this.selectedResearchLevel + i && this.selectedResearchLevel + i != 0) {
                    ClientEventHandler.STYLE.bindTexture("faction_research");
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 160 + i * 95 - 24 + 14, this.guiTop + 87 - 27 + 20, 172 * GUI_SCALE, 37 * GUI_SCALE, 21 * GUI_SCALE, 15 * GUI_SCALE, 21, 15, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                } else {
                    ModernGui.drawScaledStringCustomFont(this.selectedResearchLevel + i + "", this.guiLeft + 161 + i * 95, this.guiTop + 81, 0x252545, 0.75f, "center", false, "georamaSemiBold", 35);
                }
                if (!isHovered) continue;
                this.hoveredAction = "level#" + (this.selectedResearchLevel + i);
            }
            ClientEventHandler.STYLE.bindTexture("faction_research");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 140, this.guiTop + 124, 200 * GUI_SCALE, 0 * GUI_SCALE, 41 * GUI_SCALE, 46 * GUI_SCALE, 41, 46, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 49, this.guiTop + 133, 0 * GUI_SCALE, 352 * GUI_SCALE, 401 * GUI_SCALE, 87 * GUI_SCALE, 401, 87, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.research.palier").toUpperCase() + " " + this.selectedResearchLevel, this.guiLeft + 55, this.guiTop + 143, 0xF8F8FB, 0.5f, "left", false, "georamaSemiBold", 30);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.research.palier.infos.conditions").toUpperCase(), this.guiLeft + 55, this.guiTop + 152, 0xF8F8FB, 0.5f, "left", false, "georamaMedium", 24);
            boolean allConditionsValidated = false;
            if (((LinkedTreeMap)((LinkedTreeMap)((LinkedTreeMap)data.get("researchesConfig")).get((Object)this.selectedResearch)).get((Object)"levels")).containsKey((Object)(this.selectedResearchLevel + ""))) {
                String conditionStringFormat;
                ItemStack stack;
                int itemMeta;
                int itemId;
                String[] itemIdMetaSplit;
                String itemIdMeta;
                String prefix;
                String conditionString;
                boolean validCondition;
                double countryConditionValue;
                int conditionValue;
                String conditionName;
                int y;
                int x;
                int i;
                allConditionsValidated = true;
                ArrayList conditions = (ArrayList)((LinkedTreeMap)((LinkedTreeMap)((LinkedTreeMap)((LinkedTreeMap)data.get("researchesConfig")).get((Object)this.selectedResearch)).get((Object)"levels")).get((Object)(this.selectedResearchLevel + ""))).get((Object)"conditions");
                Double duration = (Double)((LinkedTreeMap)((LinkedTreeMap)((LinkedTreeMap)((LinkedTreeMap)data.get("researchesConfig")).get((Object)this.selectedResearch)).get((Object)"levels")).get((Object)(this.selectedResearchLevel + ""))).get((Object)"duration");
                LinkedTreeMap countryConditionsValues = (LinkedTreeMap)data.get("researchesConditionsValues");
                for (i = 0; i < Math.min(4, conditions.size()); ++i) {
                    x = this.guiLeft + 55;
                    y = this.guiTop + 167 + i % 4 * 9;
                    conditionName = ((String)conditions.get(i)).split("#")[0];
                    conditionValue = ((String)conditions.get(i)).split("#").length > 1 ? Integer.parseInt(((String)conditions.get(i)).split("#")[1]) : 1;
                    countryConditionValue = countryConditionsValues.containsKey((Object)conditionName) ? (Double)countryConditionsValues.get((Object)conditionName) : 0.0;
                    ClientEventHandler.STYLE.bindTexture("faction_research");
                    boolean bl = validCondition = countryConditionValue >= (double)conditionValue;
                    if (conditionName.equals("notations")) {
                        boolean bl2 = validCondition = (double)conditionValue >= countryConditionValue && countryConditionValue > 0.0;
                    }
                    if (conditionName.matches("(priceitem)_[\\d]+:[\\d]+") && mouseX >= x && mouseX <= x + 7 && (float)mouseY >= (float)y - 0.5f && (float)mouseY <= (float)y + 7.5f && !validCondition && this.selectedResearchLevel == factionLevelForSelectedResearch + 1 && ((Boolean)FactionGUI.factionInfos.get("isInCountry")).booleanValue()) {
                        ModernGui.drawScaledCustomSizeModalRect(x, (float)y - 0.5f, 157 * GUI_SCALE, 27 * GUI_SCALE, 7 * GUI_SCALE, 7 * GUI_SCALE, 7, 7, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                        this.hoveredAction = "deposit#" + conditionName + ":" + (int)Math.max(0.0, (double)conditionValue - countryConditionValue);
                    } else {
                        ModernGui.drawScaledCustomSizeModalRect(x, (float)y - 0.5f, 157 * GUI_SCALE, (conditionName.equals("pay") ? 34 : (validCondition ? 13 : 20)) * GUI_SCALE, 7 * GUI_SCALE, 7 * GUI_SCALE, 7, 7, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                        if (conditionName.equals("pay") && mouseX >= x && mouseX <= x + 7 && (float)mouseY >= (float)y - 0.5f && (float)mouseY <= (float)y + 7.5f) {
                            tooltipToDraw = Arrays.asList(I18n.func_135053_a((String)"faction.research.tooltip.pay").split("##"));
                        }
                    }
                    conditionString = I18n.func_135053_a((String)("faction.research.conditions.label." + conditionName));
                    if (conditionName.matches("(craft|priceitem|blockbreak|crush|drop)_[\\d]+:[\\d]+")) {
                        prefix = conditionName.split("_")[0];
                        itemIdMeta = conditionName.split("_")[1];
                        itemIdMetaSplit = itemIdMeta.split(":");
                        itemId = Integer.parseInt(itemIdMetaSplit[0]);
                        itemMeta = itemIdMetaSplit.length > 1 ? Integer.parseInt(itemIdMetaSplit[1]) : 0;
                        stack = new ItemStack(itemId, 1, itemMeta);
                        conditionString = I18n.func_135053_a((String)("faction.research.conditions.label." + prefix)) + " " + stack.func_82833_r();
                    }
                    conditionStringFormat = conditionName.contains("notations") ? "%.2f" : "%.0f";
                    conditionString = conditionString.replaceAll("<value>", String.format(conditionStringFormat, countryConditionValue)).replaceAll("<condition>", conditionValue + "");
                    if (mouseX >= x + 9 && mouseX <= x + 9 + 70 && mouseY >= y && mouseY <= y + 9) {
                        hoveringText = conditionString;
                    }
                    if (conditionString.length() > 33) {
                        conditionString = conditionString.substring(0, 32) + "..";
                    }
                    ModernGui.drawScaledStringCustomFont(conditionString, x + 9, y, validCondition ? 0x6E76EE : 10395075, 0.5f, "left", false, "georamaMedium", 23);
                    if (validCondition) continue;
                    allConditionsValidated = false;
                }
                for (i = 4; i < conditions.size(); ++i) {
                    x = this.guiLeft + 155;
                    y = this.guiTop + (167 - Math.max(0, conditions.size() - 8) * 9) + (i - 4) * 9;
                    conditionName = ((String)conditions.get(i)).split("#")[0];
                    conditionValue = ((String)conditions.get(i)).split("#").length > 1 ? Integer.parseInt(((String)conditions.get(i)).split("#")[1]) : 1;
                    countryConditionValue = countryConditionsValues.containsKey((Object)conditionName) ? (Double)countryConditionsValues.get((Object)conditionName) : 0.0;
                    boolean bl = validCondition = countryConditionValue >= (double)conditionValue;
                    if (conditionName.equals("notations")) {
                        validCondition = (double)conditionValue >= countryConditionValue && countryConditionValue > 0.0;
                    }
                    ClientEventHandler.STYLE.bindTexture("faction_research");
                    if (conditionName.matches("(priceitem)_[\\d]+:[\\d]+") && mouseX >= x && mouseX <= x + 7 && (float)mouseY >= (float)y - 0.5f && (float)mouseY <= (float)y + 7.5f && !validCondition && this.selectedResearchLevel == factionLevelForSelectedResearch + 1 && ((Boolean)FactionGUI.factionInfos.get("isInCountry")).booleanValue()) {
                        ModernGui.drawScaledCustomSizeModalRect(x, (float)y - 0.5f, 157 * GUI_SCALE, 27 * GUI_SCALE, 7 * GUI_SCALE, 7 * GUI_SCALE, 7, 7, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                        this.hoveredAction = "deposit#" + conditionName + ":" + (int)Math.max(0.0, (double)conditionValue - countryConditionValue);
                    } else {
                        if (conditionName.equals("pay") && mouseX >= x && mouseX <= x + 7 && (float)mouseY >= (float)y - 0.5f && (float)mouseY <= (float)y + 7.5f) {
                            tooltipToDraw = Arrays.asList(I18n.func_135053_a((String)"faction.research.tooltip.pay").split("##"));
                        }
                        ModernGui.drawScaledCustomSizeModalRect(x, (float)y - 0.5f, 157 * GUI_SCALE, (conditionName.equals("pay") ? 34 : (validCondition ? 13 : 20)) * GUI_SCALE, 7 * GUI_SCALE, 7 * GUI_SCALE, 7, 7, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    }
                    conditionString = I18n.func_135053_a((String)("faction.research.conditions.label." + conditionName));
                    if (conditionName.matches("(craft|priceitem|blockbreak|crush|drop)_[\\d]+:[\\d]+")) {
                        prefix = conditionName.split("_")[0];
                        itemIdMeta = conditionName.split("_")[1];
                        itemIdMetaSplit = itemIdMeta.split(":");
                        itemId = Integer.parseInt(itemIdMetaSplit[0]);
                        itemMeta = itemIdMetaSplit.length > 1 ? Integer.parseInt(itemIdMetaSplit[1]) : 0;
                        stack = new ItemStack(itemId, 1, itemMeta);
                        conditionString = I18n.func_135053_a((String)("faction.research.conditions.label." + prefix)) + " " + stack.func_82833_r();
                    }
                    conditionStringFormat = conditionName.contains("notations") ? "%.1f" : "%.0f";
                    conditionString = conditionString.replaceAll("<value>", String.format(conditionStringFormat, countryConditionValue)).replaceAll("<condition>", conditionValue + "");
                    if (mouseX >= x + 9 && mouseX <= x + 9 + 70 && mouseY >= y && mouseY <= y + 9) {
                        hoveringText = conditionString;
                    }
                    if (conditionString.length() > 32) {
                        conditionString = conditionString.substring(0, 31) + "..";
                    }
                    ModernGui.drawScaledStringCustomFont(conditionString, x + 9, y, validCondition ? 0x6E76EE : 10395075, 0.5f, "left", false, "georamaMedium", 23);
                    if (validCondition) continue;
                    allConditionsValidated = false;
                }
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.research.palier.infos.search_time").replaceAll("<time>", FactionResearchGUI.minutesToHumanText(duration)).toUpperCase(), this.guiLeft + 270, this.guiTop + 143, 0xF8F8FB, 0.5f, "left", false, "georamaSemiBold", 30);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.research.palier.infos.rewards").toUpperCase(), this.guiLeft + 258, this.guiTop + 152, 0xF8F8FB, 0.5f, "left", false, "georamaMedium", 24);
                i = 0;
                for (String reward : I18n.func_135053_a((String)("faction.research.rewards." + this.selectedResearch + "." + this.selectedResearchLevel)).split("#")) {
                    int x2 = this.guiLeft + 258 + i / 4 * 100;
                    int y2 = this.guiTop + 167 + i % 4 * 9;
                    if (reward.length() > 35) {
                        reward = reward.substring(0, 34) + "..";
                    }
                    ModernGui.drawScaledStringCustomFont(reward, x2, y2, 10395075, 0.5f, "left", false, "georamaMedium", 23);
                    ++i;
                }
            }
            if (this.selectedResearchLevel > 1) {
                ClientEventHandler.STYLE.bindTexture("faction_research");
                if (mouseX >= this.guiLeft + 39 && mouseX <= this.guiLeft + 39 + 8 && mouseY >= this.guiTop + 166 && mouseY <= this.guiTop + 166 + 16) {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 39, this.guiTop + 166, 182 * GUI_SCALE, 18 * GUI_SCALE, 8 * GUI_SCALE, 16 * GUI_SCALE, 8, 16, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    this.hoveredAction = "levels_previous";
                } else {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 39, this.guiTop + 166, 172 * GUI_SCALE, 18 * GUI_SCALE, 8 * GUI_SCALE, 16 * GUI_SCALE, 8, 16, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                }
            }
            ClientEventHandler.STYLE.bindTexture("faction_research");
            if (mouseX >= this.guiLeft + 452 && mouseX <= this.guiLeft + 452 + 8 && mouseY >= this.guiTop + 167 && mouseY <= this.guiTop + 167 + 16) {
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 452, this.guiTop + 167, 182 * GUI_SCALE, 0 * GUI_SCALE, 8 * GUI_SCALE, 16 * GUI_SCALE, 8, 16, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                this.hoveredAction = "levels_next";
            } else {
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 452, this.guiTop + 167, 172 * GUI_SCALE, 0 * GUI_SCALE, 8 * GUI_SCALE, 16 * GUI_SCALE, 8, 16, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
            }
            if (this.selectedResearchLevel > factionLevelForSelectedResearch && ((Boolean)FactionGUI.factionInfos.get("isInCountry")).booleanValue() && ((Boolean)FactionGUI.factionInfos.get("isAtLeastOfficer")).booleanValue()) {
                boolean canStartNewResearch = true;
                boolean isHovered = false;
                if (mouseX >= this.guiLeft + 190 && mouseX <= this.guiLeft + 190 + 112 && mouseY >= this.guiTop + 204 && mouseY <= this.guiTop + 204 + 13) {
                    isHovered = true;
                    if (allConditionsValidated && canStartNewResearch) {
                        this.hoveredAction = "research";
                    }
                }
                if (this.selectedResearchLevel != factionLevelForSelectedResearch + 1) {
                    canStartNewResearch = false;
                    if (isHovered) {
                        hoveringText = I18n.func_135053_a((String)"faction.research.palier.wrong.level");
                    }
                } else if (!allConditionsValidated) {
                    canStartNewResearch = false;
                    if (isHovered) {
                        hoveringText = I18n.func_135053_a((String)"faction.research.palier.wrong.conditions");
                    }
                } else if (data.get("currentResearch") != null) {
                    canStartNewResearch = false;
                    if (isHovered) {
                        hoveringText = I18n.func_135053_a((String)"faction.research.palier.wrong.already");
                    }
                }
                ClientEventHandler.STYLE.bindTexture("faction_research");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 190, this.guiTop + 204, 0 * GUI_SCALE, (allConditionsValidated && canStartNewResearch && !isHovered ? 471 : 454) * GUI_SCALE, 112 * GUI_SCALE, 13 * GUI_SCALE, 112, 13, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.research.palier.infos.start_research"), this.guiLeft + 190 + 56, this.guiTop + 204 + 3, allConditionsValidated && canStartNewResearch && !isHovered ? 15463162 : 3026518, 0.5f, "center", false, "georamaSemiBold", 26);
            }
            if (!hoveringText.isEmpty()) {
                this.drawHoveringText(Arrays.asList(hoveringText), mouseX, mouseY, this.field_73886_k);
            }
        }
        super.func_73863_a(mouseX, mouseY, partialTick);
    }

    public String formatMinutes(int minutes) {
        long hours = TimeUnit.MINUTES.toHours(minutes);
        long remainingMinutes = (long)minutes - TimeUnit.HOURS.toMinutes(hours);
        long seconds = 0L;
        return String.format("%02d:%02d:%02d", hours, remainingMinutes, seconds);
    }

    @Override
    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0 && !this.hoveredAction.isEmpty()) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
            if (this.hoveredAction.contains("research#")) {
                this.selectedResearch = this.hoveredAction.replaceAll("research#", "");
                this.selectedResearchLevel = 1;
                if (((LinkedTreeMap)data.get("researchesLevels")).containsKey((Object)this.selectedResearch)) {
                    int maxLevel = ((Double)((LinkedTreeMap)((LinkedTreeMap)data.get("researchesConfig")).get((Object)this.selectedResearch)).get((Object)"maxLevel")).intValue();
                    this.selectedResearchLevel = Math.min(maxLevel, ((Double)((LinkedTreeMap)data.get("researchesLevels")).get((Object)this.selectedResearch)).intValue() + 1);
                }
            } else if (this.hoveredAction.equals("levels_previous")) {
                this.selectedResearchLevel = Math.max(1, this.selectedResearchLevel - 1);
            } else if (this.hoveredAction.equals("levels_next")) {
                int maxLevel = ((Double)((LinkedTreeMap)((LinkedTreeMap)data.get("researchesConfig")).get((Object)this.selectedResearch)).get((Object)"maxLevel")).intValue();
                this.selectedResearchLevel = Math.min(maxLevel, this.selectedResearchLevel + 1);
            } else if (this.hoveredAction.contains("level#")) {
                this.selectedResearchLevel = Integer.parseInt(this.hoveredAction.replaceAll("level#", ""));
            } else if (this.hoveredAction.equals("back")) {
                this.selectedResearch = null;
            } else if (this.hoveredAction.equals("research")) {
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionResearchStartPacket(this.selectedResearch, this.selectedResearchLevel)));
                this.selectedResearch = null;
            } else if (this.hoveredAction.contains("deposit#")) {
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionResearchCollectItemPacket(this.hoveredAction.replaceAll("deposit#", ""))));
            }
            this.hoveredAction = "";
        }
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }
}

