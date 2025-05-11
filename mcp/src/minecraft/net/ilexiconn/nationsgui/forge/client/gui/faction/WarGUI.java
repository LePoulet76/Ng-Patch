/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.internal.LinkedTreeMap
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiTextField
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.util.ChatMessageComponent
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.faction;

import com.google.gson.internal.LinkedTreeMap;
import cpw.mods.fml.common.network.PacketDispatcher;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import net.halalaboos.cfont.CFontRenderer;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarGeneric;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.SurrenderConfirmGui;
import net.ilexiconn.nationsgui.forge.client.gui.faction.TabbedFactionGUI;
import net.ilexiconn.nationsgui.forge.client.gui.main.component.CustomInputFieldGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionEnemyAgreementCancelPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionEnemyAgreementCreatePacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionEnemyAgreementUpdateStatusPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionEnemyRequestGenerateConditionsPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionEnemyRequestUpdateConditionsPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionEnemyRequestUpdateStatusPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionWarDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.RollbackAssaultDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class WarGUI
extends TabbedFactionGUI {
    public static HashMap<String, Integer> iconsConditionsRewards = new HashMap<String, Integer>(){
        {
            this.put("kill", 380);
            this.put("victory", 396);
            this.put("missile_point", 372);
            this.put("assault_point", 388);
            this.put("anti", 372);
            this.put("red", 372);
            this.put("dollars", 412);
            this.put("claims", 404);
            this.put("power", 420);
            this.put("peace", 428);
        }
    };
    public static HashMap<String, Integer> iconsAgreements = new HashMap<String, Integer>(){
        {
            this.put("peace", 308);
            this.put("no_missile", 353);
            this.put("no_assault", 398);
        }
    };
    public static boolean loaded = false;
    public static ArrayList<TreeMap<String, Object>> factionsWarInfos;
    public static HashMap<String, Integer> btnForumY;
    public String displayMode = "infos";
    private GuiScrollBarGeneric scrollBarMissiles;
    private GuiScrollBarGeneric scrollBarAssaults;
    public static int selectedAgreementId;
    public ArrayList<String> agreementsAvailableConditions = new ArrayList<String>(Arrays.asList("dollars", "power", "claims"));
    public static HashMap<String, Integer> progressBarY;
    private String hoveredCountry = "";
    public static int currentWarIndex;
    private RenderItem itemRenderer = new RenderItem();
    private ArrayList<HashMap<String, Object>> cachedAssaults = new ArrayList();
    private GuiScrollBarGeneric scrollBarConditions;
    private CFontRenderer cFontLabel;
    private CFontRenderer cFontCondtionsRewards;
    public ArrayList<String> agreementsAvailableTypes = new ArrayList<String>(Arrays.asList("peace", "no_assault", "no_missile"));
    public List<String> availableConditions = Arrays.asList("kill", "victory", "missile_point", "assault_point", "anti", "red");
    public ArrayList<String> availableRewards = new ArrayList<String>(Arrays.asList("dollars", "power", "claims", "peace"));
    private GuiScrollBarGeneric scrollBarAgreementsActive;
    private GuiScrollBarGeneric scrollBarAgreementsWaiting;
    private CFontRenderer cFontLabelHeader;
    private HashMap<String, GuiTextField> conditionsInput = new HashMap();
    private HashMap<String, GuiTextField> rewardsInput = new HashMap();
    private CFontRenderer cFontSemiBold28;
    private HashMap<String, GuiTextField> agreement_myConditionsInput = new HashMap();
    private boolean conditionsCumulative = true;
    private boolean defenserCounterPropositionMode = false;
    private HashMap<String, GuiTextField> agreement_otherConditionsInput = new HashMap();
    private boolean agreementMode = false;
    private int tabsOffset = 0;
    private boolean agreementCreationMode = false;
    private String agreement_form_type = "peace";
    private GuiTextField agreement_form_duration;

    public WarGUI() {
        factionsWarInfos = new ArrayList();
        this.cachedAssaults.clear();
        loaded = false;
        currentWarIndex = 0;
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionWarDataPacket((String)FactionGUI.factionInfos.get("name"))));
    }

    @Override
    public void func_73866_w_() {
        String data;
        CustomInputFieldGUI guiTextField;
        super.func_73866_w_();
        this.scrollBarMissiles = new GuiScrollBarGeneric(this.guiLeft + 138, this.guiTop + 113, 69, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
        this.scrollBarAssaults = new GuiScrollBarGeneric(this.guiLeft + 247, this.guiTop + 113, 69, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
        this.scrollBarConditions = new GuiScrollBarGeneric(this.guiLeft + 445, this.guiTop + 168, 52, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
        this.scrollBarAgreementsActive = new GuiScrollBarGeneric(this.guiLeft + 131, this.guiTop + 125, 91, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
        this.scrollBarAgreementsWaiting = new GuiScrollBarGeneric(this.guiLeft + 229, this.guiTop + 125, 91, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
        this.cFontLabel = ModernGui.getCustomFont("georamaMedium", 26);
        this.cFontCondtionsRewards = ModernGui.getCustomFont("georamaMedium", 28);
        this.cFontLabelHeader = ModernGui.getCustomFont("georamaMedium", 25);
        this.cFontSemiBold28 = ModernGui.getCustomFont("georamaSemiBold", 28);
        int index = 0;
        for (String condition : this.availableConditions) {
            guiTextField = new CustomInputFieldGUI(this.guiLeft + 106, this.guiTop + 125 + index * 12, 20, 12, "georamaMedium", 25);
            data = "0";
            if (this.conditionsInput.containsKey(condition)) {
                data = this.conditionsInput.get(condition).func_73781_b();
            }
            guiTextField.func_73804_f(5);
            guiTextField.func_73782_a(data);
            this.conditionsInput.put(condition, guiTextField);
            ++index;
        }
        index = 0;
        for (String reward : this.availableRewards) {
            guiTextField = new CustomInputFieldGUI(this.guiLeft + 199, this.guiTop + 125 + index * 12, 20, 12, "georamaMedium", 25);
            data = "0";
            if (this.rewardsInput.containsKey(reward)) {
                data = this.rewardsInput.get(reward).func_73781_b();
            }
            guiTextField.func_73804_f(6);
            guiTextField.func_73782_a(data);
            this.rewardsInput.put(reward, guiTextField);
            ++index;
        }
        index = 0;
        for (String condition : this.agreementsAvailableConditions) {
            guiTextField = new CustomInputFieldGUI(this.guiLeft + 321, this.guiTop + 125 + index * 11, 20, 12, "georamaMedium", 25);
            data = "0";
            if (this.agreement_myConditionsInput.containsKey(condition)) {
                data = this.agreement_myConditionsInput.get(condition).func_73781_b();
            }
            guiTextField.func_73804_f(5);
            guiTextField.func_73782_a(data);
            this.agreement_myConditionsInput.put(condition, guiTextField);
            guiTextField = new CustomInputFieldGUI(this.guiLeft + 420, this.guiTop + 125 + index * 11, 20, 12, "georamaMedium", 25);
            data = "0";
            if (this.agreement_otherConditionsInput.containsKey(condition)) {
                data = this.agreement_otherConditionsInput.get(condition).func_73781_b();
            }
            guiTextField.func_73804_f(5);
            guiTextField.func_73782_a(data);
            this.agreement_otherConditionsInput.put(condition, guiTextField);
            ++index;
        }
        this.agreement_form_duration = new CustomInputFieldGUI(this.guiLeft + 361, this.guiTop + 182, 20, 12, "georamaMedium", 25);
        this.agreement_form_duration.func_73804_f(3);
    }

    @Override
    public void func_73863_a(int mouseX, int mouseY, float partialTick) {
        this.func_73873_v_();
        tooltipToDraw.clear();
        this.hoveredAction = "";
        this.hoveredCountry = "";
        ClientEventHandler.STYLE.bindTexture("faction_global_2");
        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 30, this.guiTop + 0, 0 * GUI_SCALE, 0 * GUI_SCALE, (this.xSize - 30) * GUI_SCALE, this.ySize * GUI_SCALE, this.xSize - 30, this.ySize, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
        if (loaded) {
            if (factionsWarInfos.size() > 0 && currentWarIndex != -1) {
                int i;
                if (FactionGUI.factionInfos.get("banners") != null && ((Map)FactionGUI.factionInfos.get("banners")).containsKey("wars")) {
                    ModernGui.bindRemoteTexture((String)((Map)FactionGUI.factionInfos.get("banners")).get("wars"));
                } else {
                    ModernGui.bindRemoteTexture("https://static.nationsglory.fr/N33y222_3N.png");
                }
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 30 + 154, this.guiTop + 0, 0.0f, 0.0f, 279 * GUI_SCALE, 110 * GUI_SCALE, 279, 89, 279 * GUI_SCALE, 110 * GUI_SCALE, false);
                ClientEventHandler.STYLE.bindTexture("faction_war");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 30, this.guiTop + 0, 0 * GUI_SCALE, 282 * GUI_SCALE, 433 * GUI_SCALE, 89 * GUI_SCALE, 433, 89, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                TreeMap<String, Object> currentWar = factionsWarInfos.get(currentWarIndex);
                LinkedTreeMap currentWarRequest = (LinkedTreeMap)factionsWarInfos.get(currentWarIndex).get("warRequest");
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.war.title_against") + " " + (String)currentWar.get("name"), this.guiLeft + 43, this.guiTop + 16, 0xFFFFFF, 0.75f, "left", false, "georamaSemiBold", 32);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.war.label.war_status") + ":", this.guiLeft + 43, this.guiTop + 32, FactionGUI.textColor.get(FactionGUI.factionInfos.get("actualRelation")), 0.5f, "left", false, "georamaMedium", 25);
                if (currentWar.get("status").equals("in_progress") && currentWar.get("startTime") != null) {
                    Date date = new Date(((Double)currentWar.get("startTime")).longValue());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    String dateFormated = simpleDateFormat.format(date);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.war.status.in_progress") + " " + dateFormated, this.guiLeft + 43, this.guiTop + 40, 10395075, 0.5f, "left", false, "georamaMedium", 25);
                } else {
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("faction.war.status." + currentWar.get("status"))).replace("#factionATT#", (String)currentWar.get("factionATTName")).replace("#factionDEF#", (String)currentWar.get("factionDEFName")).replace("#sender#", (String)currentWar.get("sender")), this.guiLeft + 43, this.guiTop + 40, 10395075, 0.5f, "left", false, "georamaMedium", 25);
                }
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.enemy.reason") + ":", this.guiLeft + 43, this.guiTop + 48, FactionGUI.textColor.get(FactionGUI.factionInfos.get("actualRelation")), 0.5f, "left", false, "georamaMedium", 25);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("faction.enemy.reason." + currentWarRequest.get((Object)"reason"))) + (currentWarRequest.get((Object)"playerTarget") != null && !currentWarRequest.get((Object)"playerTarget").equals("") ? " (" + ((String)currentWarRequest.get((Object)"playerTarget")).split(" ")[1] + ")" : ""), this.guiLeft + 45 + (int)this.cFontLabelHeader.getStringWidth(I18n.func_135053_a((String)"faction.enemy.reason") + ":") / 2, this.guiTop + 48, 10395075, 0.5f, "left", false, "georamaMedium", 25);
                ClientProxy.loadCountryFlag((String)currentWar.get("name"));
                ClientEventHandler.STYLE.bindTexture("faction_war");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 292, this.guiTop - 15, 0 * GUI_SCALE, 173 * GUI_SCALE, 130 * GUI_SCALE, 104 * GUI_SCALE, 130, 104, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                if (ClientProxy.flagsTexture.containsKey((String)currentWar.get("name"))) {
                    GL11.glBindTexture((int)3553, (int)ClientProxy.flagsTexture.get((String)currentWar.get("name")).func_110552_b());
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 261, this.guiTop + 13, 0.0f, 0.0f, 156, 78, 59, 36, 156.0f, 78.0f, false);
                }
                int buttonOffsetX = 43;
                if (currentWarRequest.get((Object)"status").equals("in_progress")) {
                    ClientEventHandler.STYLE.bindTexture("faction_war");
                    if (mouseX >= this.guiLeft + buttonOffsetX && mouseX <= this.guiLeft + buttonOffsetX + 55 && mouseY >= this.guiTop + 58 && mouseY <= this.guiTop + 58 + 13) {
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + buttonOffsetX, this.guiTop + 58, 285 * GUI_SCALE, 160 * GUI_SCALE, 55 * GUI_SCALE, 13 * GUI_SCALE, 55, 13, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                        this.hoveredAction = "open_accords";
                    } else {
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + buttonOffsetX, this.guiTop + 58, 285 * GUI_SCALE, 142 * GUI_SCALE, 55 * GUI_SCALE, 13 * GUI_SCALE, 55, 13, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                    }
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.war.warAgreementBtn"), this.guiLeft + buttonOffsetX + 28, this.guiTop + 61, 2826561, 0.5f, "center", false, "georamaSemiBold", 28);
                    buttonOffsetX += 61;
                }
                if (!((String)currentWarRequest.get((Object)"linkForum")).isEmpty()) {
                    ClientEventHandler.STYLE.bindTexture("faction_war");
                    if (mouseX >= this.guiLeft + buttonOffsetX && mouseX <= this.guiLeft + buttonOffsetX + 83 && mouseY >= this.guiTop + 58 && mouseY <= this.guiTop + 58 + 13) {
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + buttonOffsetX, this.guiTop + 58, 134 * GUI_SCALE, 118 * GUI_SCALE, 83 * GUI_SCALE, 13 * GUI_SCALE, 83, 13, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.enemy.linkForum"), this.guiLeft + buttonOffsetX + 42, this.guiTop + 61, 2826561, 0.5f, "center", false, "georamaSemiBold", 28);
                        this.hoveredAction = "open_forum";
                    } else {
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + buttonOffsetX, this.guiTop + 58, 134 * GUI_SCALE, btnForumY.get(FactionGUI.factionInfos.get("actualRelation")) * GUI_SCALE, 83 * GUI_SCALE, 13 * GUI_SCALE, 83, 13, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.enemy.linkForum"), this.guiLeft + buttonOffsetX + 42, this.guiTop + 61, 0xFFFFFF, 0.5f, "center", false, "georamaSemiBold", 28);
                    }
                    buttonOffsetX += 89;
                }
                if (currentWarRequest.get((Object)"status").equals("in_progress") && ((Boolean)FactionGUI.factionInfos.get("isInCountry")).booleanValue() && FactionGUI.hasPermissions("wars")) {
                    ClientEventHandler.STYLE.bindTexture("faction_war");
                    if (mouseX >= this.guiLeft + buttonOffsetX && mouseX <= this.guiLeft + buttonOffsetX + 83 && mouseY >= this.guiTop + 58 && mouseY <= this.guiTop + 58 + 13) {
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + buttonOffsetX, this.guiTop + 58, 435 * GUI_SCALE, 159 * GUI_SCALE, 56 * GUI_SCALE, 13 * GUI_SCALE, 56, 13, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.enemy.surrender"), this.guiLeft + buttonOffsetX + 28, this.guiTop + 61, 2234425, 0.5f, "center", false, "georamaSemiBold", 28);
                        this.hoveredAction = "surrender";
                    } else {
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + buttonOffsetX, this.guiTop + 58, 435 * GUI_SCALE, 142 * GUI_SCALE, 56 * GUI_SCALE, 13 * GUI_SCALE, 56, 13, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.enemy.surrender"), this.guiLeft + buttonOffsetX + 28, this.guiTop + 61, 15017020, 0.5f, "center", false, "georamaSemiBold", 28);
                    }
                    buttonOffsetX += 62;
                }
                if (factionsWarInfos.size() > 6) {
                    ClientEventHandler.STYLE.bindTexture("faction_war");
                    if (this.tabsOffset > 0) {
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 34, this.guiTop + 78, 247 * GUI_SCALE, 141 * GUI_SCALE, 5 * GUI_SCALE, 9 * GUI_SCALE, 5, 9, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                        if (mouseX >= this.guiLeft + 34 && mouseX <= this.guiLeft + 34 + 5 && mouseY >= this.guiTop + 78 && mouseY <= this.guiTop + 78 + 9) {
                            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 34, this.guiTop + 78, 247 * GUI_SCALE, 151 * GUI_SCALE, 5 * GUI_SCALE, 9 * GUI_SCALE, 5, 9, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                            this.hoveredAction = "previousTab";
                        }
                    }
                    if (factionsWarInfos.size() > this.tabsOffset + 6) {
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 441, this.guiTop + 78, 254 * GUI_SCALE, 141 * GUI_SCALE, 5 * GUI_SCALE, 9 * GUI_SCALE, 5, 9, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                        if (mouseX >= this.guiLeft + 441 && mouseX <= this.guiLeft + 441 + 5 && mouseY >= this.guiTop + 78 && mouseY <= this.guiTop + 78 + 9) {
                            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 441, this.guiTop + 78, 254 * GUI_SCALE, 151 * GUI_SCALE, 5 * GUI_SCALE, 9 * GUI_SCALE, 5, 9, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                            this.hoveredAction = "nextTab";
                        }
                    }
                }
                int index = 0;
                for (i = this.tabsOffset; i < Math.min(6 + this.tabsOffset, factionsWarInfos.size()); ++i) {
                    ClientEventHandler.STYLE.bindTexture("faction_war");
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 43 + index * 66, this.guiTop + 77, (factionsWarInfos.get(i).get("name").equals(factionsWarInfos.get(currentWarIndex).get("name")) ? 0 : 69) * GUI_SCALE, 131 * GUI_SCALE, 65 * GUI_SCALE, 12 * GUI_SCALE, 65, 12, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                    if ((factionsWarInfos.get(i).get("status").equals("waiting_conditions_att") || factionsWarInfos.get(i).get("status").equals("waiting_conditions_att_second")) && ((LinkedTreeMap)factionsWarInfos.get(i).get("warRequest")).get((Object)"factionATT").equals(((LinkedTreeMap)factionsWarInfos.get(i).get("warRequest")).get((Object)"playerFaction")) && ((Boolean)((LinkedTreeMap)factionsWarInfos.get(i).get("warRequest")).get((Object)"hasWarPermInOwnCountry")).booleanValue() || factionsWarInfos.get(i).get("status").equals("waiting_conditions_def") && ((LinkedTreeMap)factionsWarInfos.get(i).get("warRequest")).get((Object)"factionDEF").equals(((LinkedTreeMap)factionsWarInfos.get(i).get("warRequest")).get((Object)"playerFaction")) && ((Boolean)((LinkedTreeMap)factionsWarInfos.get(i).get("warRequest")).get((Object)"hasWarPermInOwnCountry")).booleanValue()) {
                        ClientEventHandler.STYLE.bindTexture("faction_global");
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 41 + index * 66 + 61, this.guiTop + 74, 234 * GUI_SCALE, 52 * GUI_SCALE, 6 * GUI_SCALE, 6 * GUI_SCALE, 6, 6, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                    }
                    ModernGui.drawScaledStringCustomFont(factionsWarInfos.get(i).get("name") + "", this.guiLeft + 43 + index * 66 + 32, this.guiTop + 80, factionsWarInfos.get(i).get("name").equals(factionsWarInfos.get(currentWarIndex).get("name")) ? FactionGUI.textColor.get(FactionGUI.factionInfos.get("actualRelation")) : 14803951, 0.5f, "center", false, "georamaSemiBold", 25);
                    if (mouseX >= this.guiLeft + 43 + index * 66 && mouseX <= this.guiLeft + 43 + index * 66 + 64 && mouseY >= this.guiTop + 77 && mouseY <= this.guiTop + 77 + 12) {
                        this.hoveredAction = "switch_war#" + i;
                    }
                    ++index;
                }
                if (!this.agreementMode) {
                    if (currentWar.get("status").equals("waiting_validation") || currentWar.get("status").equals("refused") || currentWar.get("status").equals("cancelled") || !currentWar.get("status").equals("in_progress") && !currentWar.get("status").equals("finished") && currentWar.get("status").equals("waiting_conditions_att") && (!currentWarRequest.get((Object)"factionATT").equals(currentWarRequest.get((Object)"playerFaction")) || !((Boolean)currentWarRequest.get((Object)"hasWarPermInOwnCountry")).booleanValue())) {
                        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("faction.war.status." + currentWar.get("status"))).replaceAll("#factionATT#", (String)currentWar.get("factionATTName")).replaceAll("#factionDEF#", (String)currentWar.get("factionDEFName")), this.guiLeft + 43, this.guiTop + 97, 16514302, 0.5f, "left", false, "georamaSemiBold", 28);
                        ClientEventHandler.STYLE.bindTexture("faction_war_2");
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 43, this.guiTop + 108, 0 * GUI_SCALE, 307 * GUI_SCALE, 403 * GUI_SCALE, 43 * GUI_SCALE, 403, 43, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.war.label.war_for") + " " + I18n.func_135053_a((String)("faction.enemy.reason." + currentWarRequest.get((Object)"reason"))), this.guiLeft + 50, this.guiTop + 115, 14803951, 0.5f, "left", false, "georamaSemiBold", 30);
                        ModernGui.drawSectionStringCustomFont(I18n.func_135053_a((String)("faction.enemy.reason.desc." + currentWarRequest.get((Object)"reason"))), this.guiLeft + 50, this.guiTop + 125, 10395075, 0.5f, "left", false, "georamaMedium", 26, 8, 400);
                        Date date = new Date(((Double)currentWarRequest.get((Object)"creationTime")).longValue());
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        String dateFormated = simpleDateFormat.format(date);
                        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.war.label.initiate_by_at").replaceAll("#date#", dateFormated).replaceAll("#player#", (String)currentWarRequest.get((Object)"sender")), this.guiLeft + 50, this.guiTop + 142, 10395075, 0.5f, "left", false, "georamaMedium", 24);
                        Gui.func_73734_a((int)(this.guiLeft + 106), (int)(this.guiTop + 192), (int)(this.guiLeft + 387), (int)(this.guiTop + 194), (int)-13950655);
                        int stepInProgress = 0;
                        if (currentWar.get("status").equals("waiting_conditions_att")) {
                            stepInProgress = 1;
                        } else if (currentWar.get("status").equals("waiting_conditions_def")) {
                            stepInProgress = 2;
                        } else if (currentWar.get("status").equals("waiting_conditions_att_second")) {
                            stepInProgress = 3;
                        }
                        for (int i2 = 0; i2 <= 4; ++i2) {
                            ClientEventHandler.STYLE.bindTexture("faction_war_2");
                            if (i2 == stepInProgress && (currentWar.get("status").equals("refused") || currentWar.get("status").equals("cancelled"))) {
                                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 43 + i2 * 85, this.guiTop + 154, 449 * GUI_SCALE, 136 * GUI_SCALE, 63 * GUI_SCALE, 73 * GUI_SCALE, 63, 73, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                            } else {
                                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 43 + i2 * 85, this.guiTop + 154, (i2 < stepInProgress ? 191 : (i2 == stepInProgress ? 258 : 325)) * GUI_SCALE, 46 * GUI_SCALE, 63 * GUI_SCALE, 73 * GUI_SCALE, 63, 73, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                            }
                            ModernGui.drawScaledStringCustomFont(i2 + "", this.guiLeft + 43 + i2 * 85 + 32, this.guiTop + 158, i2 > stepInProgress ? 10395075 : 2234425, 0.75f, "center", false, "georamaBold", 28);
                            ModernGui.drawSectionStringCustomFont(I18n.func_135053_a((String)("faction.war.waiting_validation.step." + i2)).replaceAll("#factionATT#", (String)currentWar.get("factionATTName")).replaceAll("#factionDEF#", (String)currentWar.get("factionDEFName")), this.guiLeft + 43 + i2 * 85 + 32, this.guiTop + 177, i2 < stepInProgress ? 16514302 : (i2 == stepInProgress ? 2234425 : 10395075), 0.5f, "center", false, "georamaMedium", 22, 8, 105);
                        }
                    } else if (currentWar.get("status").equals("waiting_conditions_att") || currentWar.get("status").equals("waiting_conditions_def") || currentWar.get("status").equals("waiting_conditions_att_second")) {
                        if (currentWar.get("status").equals("waiting_conditions_att") || this.defenserCounterPropositionMode) {
                            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.war.title.your_proposal").replaceAll("#country#", this.defenserCounterPropositionMode ? (String)currentWar.get("factionDEFName") : (String)currentWar.get("factionATTName")), this.guiLeft + 43, this.guiTop + 97, 16514302, 0.5f, "left", false, "georamaSemiBold", 28);
                            ClientEventHandler.STYLE.bindTexture("faction_war_2");
                            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 42, this.guiTop + 108, 0 * GUI_SCALE, 0 * GUI_SCALE, 188 * GUI_SCALE, 119 * GUI_SCALE, 188, 119, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.enemy.contitions"), this.guiLeft + 48, this.guiTop + 114, 16514302, 0.5f, "left", false, "georamaSemiBold", 32);
                            index = 0;
                            for (String string : this.availableConditions) {
                                ClientEventHandler.STYLE.bindTexture("faction_war");
                                if (this.conditionsInput.containsKey(string)) {
                                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 49, this.guiTop + 128 - 1 + index * 12, iconsConditionsRewards.get(string) * GUI_SCALE, (FactionGUI.isNumeric(this.conditionsInput.get(string).func_73781_b(), false) ? 114 : 106) * GUI_SCALE, 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("faction.enemy.conditions." + string)), this.guiLeft + 59, this.guiTop + 128 + index * 12, FactionGUI.isNumeric(this.conditionsInput.get(string).func_73781_b(), false) ? 0x6E76EE : 16514302, 0.5f, "left", false, "georamaSemiBold", 25);
                                    this.conditionsInput.get(string).func_73795_f();
                                    ClientEventHandler.STYLE.bindTexture("faction_war_2");
                                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 108, this.guiTop + 127 + index * 12, 191 * GUI_SCALE, 176 * GUI_SCALE, 22 * GUI_SCALE, 9 * GUI_SCALE, 22, 9, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                                }
                                ++index;
                            }
                            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.enemy.contitions_added"), this.guiLeft + 48, this.guiTop + 202, 16514302, 0.5f, "left", false, "georamaSemiBold", 32);
                            ClientEventHandler.STYLE.bindTexture("faction_global");
                            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 49, this.guiTop + 214, 321 * GUI_SCALE, (this.conditionsCumulative ? 181 : 190) * GUI_SCALE, 6 * GUI_SCALE, 6 * GUI_SCALE, 6, 6, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.common.true"), this.guiLeft + 57, this.guiTop + 214, this.conditionsCumulative ? 0x6E76EE : 10395075, 0.5f, "left", false, "georamaSemiBold", 25);
                            if (mouseX >= this.guiLeft + 49 && mouseX <= this.guiLeft + 49 + 6 && mouseY >= this.guiTop + 214 && mouseY <= this.guiTop + 214 + 6) {
                                this.hoveredAction = "conditions_cumulative#true";
                            }
                            ClientEventHandler.STYLE.bindTexture("faction_global");
                            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 90, this.guiTop + 214, 321 * GUI_SCALE, (!this.conditionsCumulative ? 181 : 190) * GUI_SCALE, 6 * GUI_SCALE, 6 * GUI_SCALE, 6, 6, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.common.false"), this.guiLeft + 98, this.guiTop + 214, !this.conditionsCumulative ? 0x6E76EE : 10395075, 0.5f, "left", false, "georamaSemiBold", 25);
                            if (mouseX >= this.guiLeft + 90 && mouseX <= this.guiLeft + 90 + 6 && mouseY >= this.guiTop + 214 && mouseY <= this.guiTop + 214 + 6) {
                                this.hoveredAction = "conditions_cumulative#false";
                            }
                            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.enemy.rewards"), this.guiLeft + 141, this.guiTop + 114, 16514302, 0.5f, "left", false, "georamaSemiBold", 32);
                            index = 0;
                            for (String string : this.availableRewards) {
                                if (this.rewardsInput.containsKey(string)) {
                                    int labelColor = FactionGUI.isNumeric(this.rewardsInput.get(string).func_73781_b(), false) ? 0x6E76EE : 16514302;
                                    labelColor = !this.rewardsInput.get(string).func_73781_b().isEmpty() && (!FactionGUI.isNumeric(this.rewardsInput.get(string).func_73781_b(), true) || string.equals("peace") && Integer.parseInt(this.rewardsInput.get(string).func_73781_b()) > 120) ? 15017020 : labelColor;
                                    ClientEventHandler.STYLE.bindTexture("faction_war");
                                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 142, this.guiTop + 128 - 1 + index * 12, iconsConditionsRewards.get(string) * GUI_SCALE, (labelColor == 0x6E76EE ? 114 : (labelColor == 15017020 ? 130 : 106)) * GUI_SCALE, 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("faction.enemy.rewards." + string)), this.guiLeft + 152, this.guiTop + 128 + index * 12, labelColor, 0.5f, "left", false, "georamaSemiBold", 25);
                                    this.rewardsInput.get(string).func_73795_f();
                                    ClientEventHandler.STYLE.bindTexture("faction_war_2");
                                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 201, this.guiTop + 127 + index * 12, 191 * GUI_SCALE, 176 * GUI_SCALE, 22 * GUI_SCALE, 9 * GUI_SCALE, 26, 9, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                                }
                                ++index;
                            }
                            if (currentWar.get("status").equals("waiting_conditions_def")) {
                                ClientEventHandler.STYLE.bindTexture("faction_war_2");
                                if (mouseX >= this.guiLeft + 142 && mouseX <= this.guiLeft + 142 + 39 && mouseY >= this.guiTop + 211 && mouseY <= this.guiTop + 211 + 11) {
                                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 142, this.guiTop + 211, 191 * GUI_SCALE, 202 * GUI_SCALE, 39 * GUI_SCALE, 11 * GUI_SCALE, 39, 11, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.common.back"), this.guiLeft + 142 + 19, this.guiTop + 213, 2234425, 0.5f, "center", false, "georamaSemiBold", 25);
                                    this.hoveredAction = "return_counter_def";
                                } else {
                                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 142, this.guiTop + 211, 237 * GUI_SCALE, 202 * GUI_SCALE, 39 * GUI_SCALE, 11 * GUI_SCALE, 39, 11, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.common.back"), this.guiLeft + 142 + 19, this.guiTop + 213, 14803951, 0.5f, "center", false, "georamaSemiBold", 25);
                                }
                            }
                            ClientEventHandler.STYLE.bindTexture("faction_war_2");
                            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 185, this.guiTop + 211, 191 * GUI_SCALE, 188 * GUI_SCALE, 39 * GUI_SCALE, 11 * GUI_SCALE, 39, 11, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                            if (mouseX >= this.guiLeft + 185 && mouseX <= this.guiLeft + 185 + 39 && mouseY >= this.guiTop + 211 && mouseY <= this.guiTop + 211 + 11) {
                                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 185, this.guiTop + 211, 191 * GUI_SCALE, 202 * GUI_SCALE, 39 * GUI_SCALE, 11 * GUI_SCALE, 39, 11, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                                this.hoveredAction = this.defenserCounterPropositionMode ? "send_conditions_def" : "send_conditions_att";
                            }
                            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.enemy.send_request"), this.guiLeft + 185 + 19, this.guiTop + 213, 2234425, 0.5f, "center", false, "georamaSemiBold", 25);
                        } else {
                            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.war.label.proposition").replaceAll("#country#", (String)currentWar.get(currentWar.get("status").equals("waiting_conditions_def") ? "factionATTName" : "factionDEFName")), this.guiLeft + 43, this.guiTop + 97, 16514302, 0.5f, "left", false, "georamaSemiBold", 28);
                            ClientEventHandler.STYLE.bindTexture("faction_war_2");
                            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 43, this.guiTop + 108, 0 * GUI_SCALE, 199 * GUI_SCALE, 187 * GUI_SCALE, 54 * GUI_SCALE, 187, 54, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.enemy.contitions_with_mode").replaceAll("#mode#", I18n.func_135053_a((String)("faction.enemy.conditionsType." + currentWarRequest.get((Object)(currentWar.get("status").equals("waiting_conditions_def") ? "conditionsType_ATT" : "conditionsType_DEF"))))), this.guiLeft + 48, this.guiTop + 112, 16514302, 0.5f, "left", false, "georamaSemiBold", 32);
                            index = 0;
                            String[] conditions = (String[])currentWarRequest.get((Object)(currentWar.get("status").equals("waiting_conditions_def") ? "conditions_ATT" : "conditions_DEF"));
                            if (conditions != null) {
                                for (String condition : conditions.split(",")) {
                                    if (condition.isEmpty()) continue;
                                    int offsetX = this.guiLeft + 48 + index / 3 * 96;
                                    int offsetY = this.guiTop + 126 + index % 3 * 11;
                                    String conditionName = condition.split("#")[0];
                                    String conditionValue = condition.split("#")[1];
                                    ClientEventHandler.STYLE.bindTexture("faction_war");
                                    ModernGui.drawScaledCustomSizeModalRect(offsetX, offsetY, iconsConditionsRewards.get(conditionName) * GUI_SCALE, 106 * GUI_SCALE, 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                                    ModernGui.drawScaledStringCustomFont(conditionValue + " " + I18n.func_135053_a((String)("faction.enemy.conditions." + conditionName)), offsetX + 10, offsetY + 1, 10395075, 0.5f, "left", false, "georamaMedium", 28);
                                    ++index;
                                }
                            }
                            ClientEventHandler.STYLE.bindTexture("faction_war_2");
                            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 43, this.guiTop + 165, 0 * GUI_SCALE, 368 * GUI_SCALE, 187 * GUI_SCALE, 45 * GUI_SCALE, 187, 45, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.enemy.rewards"), this.guiLeft + 48, this.guiTop + 170, 16514302, 0.5f, "left", false, "georamaSemiBold", 32);
                            index = 0;
                            String string = (String)currentWarRequest.get((Object)(currentWar.get("status").equals("waiting_conditions_def") ? "rewards_ATT" : "rewards_DEF"));
                            if (string != null) {
                                for (String reward : string.split(",")) {
                                    if (reward.isEmpty()) continue;
                                    int offsetX = this.guiLeft + 48 + index / 2 * 96;
                                    int offsetY = this.guiTop + 185 + index % 2 * 11;
                                    String rewardName = reward.split("#")[0];
                                    String rewardValue = reward.split("#")[1];
                                    ClientEventHandler.STYLE.bindTexture("faction_war");
                                    ModernGui.drawScaledCustomSizeModalRect(offsetX, offsetY, iconsConditionsRewards.get(rewardName) * GUI_SCALE, 106 * GUI_SCALE, 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                                    ModernGui.drawScaledStringCustomFont(rewardValue + " " + I18n.func_135053_a((String)("faction.enemy.rewards." + rewardName)), offsetX + 10, offsetY + 1, 10395075, 0.5f, "left", false, "georamaMedium", 28);
                                    ++index;
                                }
                            }
                            if (currentWar.get("status").equals("waiting_conditions_def") && currentWarRequest.get((Object)"factionDEF").equals(currentWarRequest.get((Object)"playerFaction")) && ((Boolean)currentWarRequest.get((Object)"hasWarPermInOwnCountry")).booleanValue() || (currentWar.get("status").equals("waiting_conditions_att") || currentWar.get("status").equals("waiting_conditions_att_second")) && currentWarRequest.get((Object)"factionATT").equals(currentWarRequest.get((Object)"playerFaction")) && ((Boolean)currentWarRequest.get((Object)"hasWarPermInOwnCountry")).booleanValue()) {
                                if (!currentWar.get("status").equals("waiting_conditions_def") || conditions != null) {
                                    ClientEventHandler.STYLE.bindTexture("faction_war_2");
                                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 43, this.guiTop + 214, 191 * GUI_SCALE, 160 * GUI_SCALE, 53 * GUI_SCALE, 13 * GUI_SCALE, 53, 13, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                                    if (mouseX >= this.guiLeft + 43 && mouseX <= this.guiLeft + 43 + 53 && mouseY >= this.guiTop + 214 && mouseY <= this.guiTop + 214 + 13) {
                                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 43, this.guiTop + 214, 191 * GUI_SCALE, 142 * GUI_SCALE, 53 * GUI_SCALE, 13 * GUI_SCALE, 53, 13, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                                        this.hoveredAction = currentWar.get("status").equals("waiting_conditions_def") ? "accept_conditions_def" : "accept_conditions_att_second";
                                    }
                                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.enemy.accept_conditions_def"), this.guiLeft + 43 + 26, this.guiTop + 217, 2234425, 0.5f, "center", false, "georamaSemiBold", 28);
                                }
                                ClientEventHandler.STYLE.bindTexture("faction_war_2");
                                if (mouseX >= this.guiLeft + 119 && mouseX <= this.guiLeft + 119 + 110 && mouseY >= this.guiTop + 214 && mouseY <= this.guiTop + 214 + 13) {
                                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 119, this.guiTop + 214, 248 * GUI_SCALE, 160 * GUI_SCALE, 111 * GUI_SCALE, 13 * GUI_SCALE, 111, 13, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)(currentWar.get("status").equals("waiting_conditions_def") ? "faction.enemy.refuse_conditions_def" : "faction.enemy.refuse_conditions_att_second")), this.guiLeft + 119 + 55, this.guiTop + 217, 2234425, 0.5f, "center", false, "georamaSemiBold", 28);
                                    this.hoveredAction = currentWar.get("status").equals("waiting_conditions_def") ? "refuse_conditions_def" : "refuse_conditions_att_second";
                                } else {
                                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 119, this.guiTop + 214, 248 * GUI_SCALE, 142 * GUI_SCALE, 111 * GUI_SCALE, 13 * GUI_SCALE, 111, 13, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)(currentWar.get("status").equals("waiting_conditions_def") ? "faction.enemy.refuse_conditions_def" : "faction.enemy.refuse_conditions_att_second")), this.guiLeft + 119 + 55, this.guiTop + 217, 14803951, 0.5f, "center", false, "georamaSemiBold", 28);
                                }
                            }
                        }
                        Gui.func_73734_a((int)(this.guiLeft + 241), (int)(this.guiTop + 89), (int)(this.guiLeft + this.xSize), (int)(this.guiTop + this.ySize), (int)-14279619);
                        if (this.defenserCounterPropositionMode) {
                            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)(currentWar.get("status").equals("waiting_conditions_def") ? "faction.war.label.received_proposition" : "faction.war.label.my_proposition")).replaceAll("#country#", this.defenserCounterPropositionMode ? (String)currentWar.get("factionATTName") : (String)currentWar.get("factionDEFName")), this.guiLeft + 254, this.guiTop + 97, 16514302, 0.5f, "left", false, "georamaSemiBold", 28);
                            ClientEventHandler.STYLE.bindTexture("faction_war_2");
                            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 254, this.guiTop + 108, 0 * GUI_SCALE, 199 * GUI_SCALE, 187 * GUI_SCALE, 54 * GUI_SCALE, 187, 54, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.enemy.contitions_with_mode").replaceAll("#mode#", I18n.func_135053_a((String)("faction.enemy.conditionsType." + currentWarRequest.get((Object)(currentWar.get("status").equals("waiting_conditions_def") ? "conditionsType_ATT" : "conditionsType_DEF"))))), this.guiLeft + 254 + 5, this.guiTop + 112, 16514302, 0.5f, "left", false, "georamaSemiBold", 32);
                            index = 0;
                            if (currentWarRequest.get((Object)(currentWar.get("status").equals("waiting_conditions_def") ? "conditions_ATT" : "conditions_DEF")) != null) {
                                for (String condition : ((String)currentWarRequest.get((Object)(currentWar.get("status").equals("waiting_conditions_def") ? "conditions_ATT" : "conditions_DEF"))).split(",")) {
                                    int offsetX = this.guiLeft + 254 + 5 + index / 3 * 96;
                                    int offsetY = this.guiTop + 126 + index % 3 * 11;
                                    String conditionName = condition.split("#")[0];
                                    String conditionValue = condition.split("#")[1];
                                    ClientEventHandler.STYLE.bindTexture("faction_war");
                                    ModernGui.drawScaledCustomSizeModalRect(offsetX, offsetY, iconsConditionsRewards.get(conditionName) * GUI_SCALE, 106 * GUI_SCALE, 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                                    ModernGui.drawScaledStringCustomFont(conditionValue + " " + I18n.func_135053_a((String)("faction.enemy.conditions." + conditionName)), offsetX + 10, offsetY + 1, 10395075, 0.5f, "left", false, "georamaMedium", 28);
                                    ++index;
                                }
                            }
                            ClientEventHandler.STYLE.bindTexture("faction_war_2");
                            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 254, this.guiTop + 165, 0 * GUI_SCALE, 368 * GUI_SCALE, 187 * GUI_SCALE, 45 * GUI_SCALE, 187, 45, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.enemy.rewards"), this.guiLeft + 254 + 5, this.guiTop + 170, 16514302, 0.5f, "left", false, "georamaSemiBold", 32);
                            index = 0;
                            if (currentWarRequest.get((Object)(currentWar.get("status").equals("waiting_conditions_def") ? "rewards_ATT" : "rewards_DEF")) != null) {
                                for (String reward : ((String)currentWarRequest.get((Object)(currentWar.get("status").equals("waiting_conditions_def") ? "rewards_ATT" : "rewards_DEF"))).split(",")) {
                                    int offsetX = this.guiLeft + 254 + 5 + index / 2 * 96;
                                    int offsetY = this.guiTop + 185 + index % 2 * 11;
                                    String rewardName = reward.split("#")[0];
                                    String rewardValue = reward.length() > 1 ? reward.split("#")[1] : "0";
                                    ClientEventHandler.STYLE.bindTexture("faction_war");
                                    ModernGui.drawScaledCustomSizeModalRect(offsetX, offsetY, iconsConditionsRewards.get(rewardName) * GUI_SCALE, 106 * GUI_SCALE, 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                                    ModernGui.drawScaledStringCustomFont(rewardValue + " " + I18n.func_135053_a((String)("faction.enemy.rewards." + rewardName)), offsetX + 10, offsetY + 1, 10395075, 0.5f, "left", false, "georamaMedium", 28);
                                    ++index;
                                }
                            }
                        } else {
                            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.war.title.request_informations"), this.guiLeft + 254, this.guiTop + 97, 16514302, 0.5f, "left", false, "georamaSemiBold", 28);
                            ClientEventHandler.STYLE.bindTexture("faction_war_2");
                            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 254, this.guiTop + 108, 191 * GUI_SCALE, 0 * GUI_SCALE, 196 * GUI_SCALE, 43 * GUI_SCALE, 196, 43, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.war.label.war_for") + " " + I18n.func_135053_a((String)("faction.enemy.reason." + currentWarRequest.get((Object)"reason"))), this.guiLeft + 261, this.guiTop + 115, 14803951, 0.5f, "left", false, "georamaSemiBold", 30);
                            ModernGui.drawSectionStringCustomFont(I18n.func_135053_a((String)("faction.enemy.reason.desc." + currentWarRequest.get((Object)"reason"))), this.guiLeft + 261, this.guiTop + 125, 10395075, 0.5f, "left", false, "georamaMedium", 26, 8, 360);
                            Date date = new Date(((Double)currentWarRequest.get((Object)"creationTime")).longValue());
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                            String dateFormated = simpleDateFormat.format(date);
                            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.war.label.initiate_by_at").replaceAll("#date#", dateFormated).replaceAll("#player#", (String)currentWar.get("sender")), this.guiLeft + 261, this.guiTop + 142, 10395075, 0.5f, "left", false, "georamaMedium", 24);
                            int stepInProgress = 1;
                            if (currentWar.get("status").equals("waiting_conditions_def")) {
                                stepInProgress = 2;
                            } else if (currentWar.get("status").equals("waiting_conditions_att_second")) {
                                stepInProgress = 3;
                            }
                            for (int i3 = 1; i3 <= 3; ++i3) {
                                ClientEventHandler.STYLE.bindTexture("faction_war_2");
                                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 254 + (i3 - 1) * 67, this.guiTop + 154, (i3 < stepInProgress ? 191 : (i3 == stepInProgress ? 258 : 325)) * GUI_SCALE, 46 * GUI_SCALE, 63 * GUI_SCALE, 73 * GUI_SCALE, 63, 73, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                                ModernGui.drawScaledStringCustomFont(i3 + "", this.guiLeft + 254 + (i3 - 1) * 67 + 32, this.guiTop + 154 + 4, i3 > stepInProgress ? 10395075 : 2234425, 0.75f, "center", false, "georamaBold", 28);
                                ModernGui.drawSectionStringCustomFont(I18n.func_135053_a((String)("faction.war.waiting_validation.step." + i3)).replaceAll("#factionATT#", (String)currentWar.get("factionATTName")).replaceAll("#factionDEF#", (String)currentWar.get("factionDEFName")), this.guiLeft + 254 + (i3 - 1) * 67 + 32, this.guiTop + 177, i3 < stepInProgress ? 16514302 : (i3 == stepInProgress ? 2234425 : 10395075), 0.5f, "center", false, "georamaMedium", 22, 8, 105);
                            }
                        }
                    } else if (currentWar.get("status").equals("in_progress") || currentWar.get("status").equals("finished")) {
                        ClientEventHandler.STYLE.bindTexture("faction_war");
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 43, this.guiTop + 96, 0 * GUI_SCALE, 0 * GUI_SCALE, 103 * GUI_SCALE, 93 * GUI_SCALE, 103, 93, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.war.missile_history"), this.guiLeft + 48, this.guiTop + 100, 16514302, 0.5f, "left", false, "georamaSemiBold", 28);
                        ClientEventHandler.STYLE.bindTexture("faction_war");
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 138, this.guiTop + 113, 456 * GUI_SCALE, 0 * GUI_SCALE, 2 * GUI_SCALE, 69 * GUI_SCALE, 2, 69, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                        if (currentWar.containsKey("missiles") && ((ArrayList)currentWar.get("missiles")).size() > 0) {
                            GUIUtils.startGLScissor(this.guiLeft + 43, this.guiTop + 113, 95, 69);
                            for (i = 0; i < ((ArrayList)currentWar.get("missiles")).size(); ++i) {
                                String string = (String)((ArrayList)currentWar.get("missiles")).get(i);
                                long timeLaunched = Long.parseLong(string.split("#")[0]);
                                String senderName = string.split("#")[1];
                                String missileName = string.split("#")[2];
                                String cost = string.split("#")[3];
                                int missileID = Integer.parseInt(string.split("#")[4]);
                                String[] countrySenderId = string.split("#")[5];
                                int offsetX = this.guiLeft + 43;
                                Float offsetY = Float.valueOf((float)(this.guiTop + 113 + 3 + i * 12) + this.getSlideMissiles());
                                ClientProxy.loadCountryFlag((String)countrySenderId);
                                if (ClientProxy.flagsTexture.containsKey(countrySenderId)) {
                                    GL11.glBindTexture((int)3553, (int)ClientProxy.flagsTexture.get(countrySenderId).func_110552_b());
                                    ModernGui.drawScaledCustomSizeModalRect(offsetX + 5, offsetY.intValue(), 0.0f, 0.0f, 156, 78, 12, 7, 156.0f, 78.0f, false);
                                }
                                ModernGui.drawScaledStringCustomFont(missileName.split(" ")[0], offsetX + 20, offsetY.intValue() + 0, 16514302, 0.5f, "left", false, "georamaMedium", 26);
                                String date = ModernGui.formatDelayTime(timeLaunched);
                                ModernGui.drawScaledStringCustomFont(date, offsetX + 92, offsetY.intValue() + 0, 10395075, 0.5f, "right", false, "georamaMedium", 26);
                                ClientEventHandler.STYLE.bindTexture("faction_global");
                                ModernGui.drawScaledCustomSizeModalRect(offsetX + 20 + (int)this.cFontLabel.getStringWidth(missileName.split(" ")[0]) / 2 + 2, offsetY.intValue() - 1, 94 * GUI_SCALE, 5 * GUI_SCALE, 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                                if (mouseX < this.guiLeft + 43 || mouseX > this.guiLeft + 43 + 95 || mouseY < this.guiTop + 113 || mouseY > this.guiTop + 113 + 69 || mouseX < offsetX + 20 + (int)this.cFontLabel.getStringWidth(missileName.split(" ")[0]) / 2 + 2 || mouseX > offsetX + 20 + (int)this.cFontLabel.getStringWidth(missileName.split(" ")[0]) / 2 + 2 + 8 || mouseY < offsetY.intValue() - 1 || mouseY > offsetY.intValue() - 1 + 8) continue;
                                tooltipToDraw.add(I18n.func_135053_a((String)"faction.war.missile_firedby") + " " + senderName);
                            }
                            GUIUtils.endGLScissor();
                            if (mouseX >= this.guiLeft + 43 && mouseX <= this.guiLeft + 43 + 103 && mouseY >= this.guiTop + 96 && mouseY <= this.guiTop + 96 + 93) {
                                this.scrollBarMissiles.draw(mouseX, mouseY);
                            }
                        }
                        ClientEventHandler.STYLE.bindTexture("faction_war");
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 152, this.guiTop + 96, 0 * GUI_SCALE, 0 * GUI_SCALE, 103 * GUI_SCALE, 93 * GUI_SCALE, 103, 93, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.war.assault_history"), this.guiLeft + 157, this.guiTop + 100, 16514302, 0.5f, "left", false, "georamaSemiBold", 28);
                        ClientEventHandler.STYLE.bindTexture("faction_war");
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 247, this.guiTop + 113, 456 * GUI_SCALE, 0 * GUI_SCALE, 2 * GUI_SCALE, 69 * GUI_SCALE, 2, 69, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                        if (this.cachedAssaults.size() == 0 && currentWar.containsKey("assaults") && ((ArrayList)currentWar.get("assaults")).size() > 0) {
                            for (i = 0; i < ((ArrayList)currentWar.get("assaults")).size(); ++i) {
                                HashMap<String, Object> hashMap = new HashMap<String, Object>();
                                String assaultInfos = (String)((ArrayList)currentWar.get("assaults")).get(i);
                                long time = Long.parseLong(assaultInfos.split("#")[0]);
                                boolean win = assaultInfos.split("#")[1].contains("win");
                                String mmr = assaultInfos.split("#")[1].split(",").length > 1 ? assaultInfos.split("#")[1].split(",")[1] : "25";
                                ArrayList<String> winnerHelpers = new ArrayList<String>();
                                for (String helper : assaultInfos.split("#")[2].split(",")) {
                                    if (helper.contains("Helper")) continue;
                                    winnerHelpers.add(helper);
                                }
                                ArrayList<String> looserHelpers = new ArrayList<String>();
                                for (String helper : assaultInfos.split("#")[3].split(",")) {
                                    if (helper.contains("Helper")) continue;
                                    looserHelpers.add(helper);
                                }
                                hashMap.put("time", time);
                                hashMap.put("date", ModernGui.formatDelayTime(time));
                                hashMap.put("win", win);
                                hashMap.put("mmr", mmr);
                                hashMap.put("winnerHelpers", winnerHelpers);
                                hashMap.put("looserHelpers", looserHelpers);
                                if (assaultInfos.split("#").length > 4) {
                                    hashMap.put("rollback", true);
                                }
                                this.cachedAssaults.add(hashMap);
                            }
                        }
                        if (this.cachedAssaults.size() > 0) {
                            GUIUtils.startGLScissor(this.guiLeft + 152, this.guiTop + 113, 95, 69);
                            for (i = 0; i < this.cachedAssaults.size(); ++i) {
                                HashMap<String, Object> hashMap = this.cachedAssaults.get(i);
                                int offsetX = this.guiLeft + 152;
                                Float offsetY = Float.valueOf((float)(this.guiTop + 113 + 3 + i * 12) + this.getSlideAssaults());
                                ModernGui.drawScaledStringCustomFont(((Boolean)hashMap.get("win") != false ? "+" : "-") + hashMap.get("mmr"), offsetX + 6, offsetY.intValue() + 0, (Boolean)hashMap.get("win") != false ? 8046943 : 15017020, 0.5f, "left", false, "georamaMedium", 26);
                                String label = (Boolean)hashMap.get("win") != false ? I18n.func_135053_a((String)"faction.common.victory") : I18n.func_135053_a((String)"faction.common.defeat");
                                ModernGui.drawScaledStringCustomFont(label, offsetX + 22, offsetY.intValue() + 0, 0xDADAED, 0.5f, "left", false, "georamaMedium", 26);
                                ModernGui.drawScaledStringCustomFont((String)hashMap.get("date"), offsetX + 92, offsetY.intValue() + 0, 10395075, 0.5f, "right", false, "georamaMedium", 26);
                                ClientEventHandler.STYLE.bindTexture("faction_global");
                                ModernGui.drawScaledCustomSizeModalRect(offsetX + 20 + (int)this.cFontLabel.getStringWidth(label) / 2 + 4, offsetY.intValue() - 1, 94 * GUI_SCALE, 5 * GUI_SCALE, 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                                if (mouseX >= this.guiLeft + 152 && mouseX <= this.guiLeft + 152 + 95 && mouseY >= this.guiTop + 113 && mouseY <= this.guiTop + 113 + 69 && mouseX >= offsetX + 20 + (int)this.cFontLabel.getStringWidth(label) / 2 + 4 && mouseX <= offsetX + 20 + (int)this.cFontLabel.getStringWidth(label) / 2 + 4 + 8 && mouseY >= offsetY.intValue() - 1 && mouseY <= offsetY.intValue() - 1 + 8) {
                                    tooltipToDraw.add("\u00a72" + I18n.func_135053_a((String)"faction.war.assaults.helpers.ally") + ":");
                                    for (String helper : (Boolean)hashMap.get("win") != false ? (List)hashMap.get("winnerHelpers") : (List)hashMap.get("looserHelpers")) {
                                        tooltipToDraw.add("\u00a77- " + helper);
                                    }
                                    tooltipToDraw.add("");
                                    tooltipToDraw.add("\u00a7c" + I18n.func_135053_a((String)"faction.war.assaults.helpers.ennemy") + ":");
                                    for (String helper : (Boolean)hashMap.get("win") != false ? (List)hashMap.get("looserHelpers") : (List)hashMap.get("winnerHelpers")) {
                                        tooltipToDraw.add("\u00a77- " + helper);
                                    }
                                }
                                if (!hashMap.containsKey("rollback")) continue;
                                ClientEventHandler.STYLE.bindTexture("faction_global");
                                ModernGui.drawScaledCustomSizeModalRect(offsetX + 20 + (int)this.cFontLabel.getStringWidth(label) / 2 + 4 + 5 + 3, offsetY.intValue() - 1, 102 * GUI_SCALE, 5 * GUI_SCALE, 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                                if (mouseX < offsetX + 20 + (int)this.cFontLabel.getStringWidth(label) / 2 + 4 + 5 + 3 || mouseX > offsetX + 20 + (int)this.cFontLabel.getStringWidth(label) / 2 + 4 + 5 + 3 + 8 || mouseY < offsetY.intValue() - 1 || mouseY > offsetY.intValue() - 1 + 8) continue;
                                tooltipToDraw.add("\u00a7a" + I18n.func_135053_a((String)"faction.war.assaults.rollback"));
                                this.hoveredAction = "rollback_assault#" + hashMap.get("time");
                                System.out.println(this.hoveredAction);
                            }
                            GUIUtils.endGLScissor();
                            if (mouseX >= this.guiLeft + 152 && mouseX <= this.guiLeft + 152 + 103 && mouseY >= this.guiTop + 96 && mouseY <= this.guiTop + 96 + 93) {
                                this.scrollBarAssaults.draw(mouseX, mouseY);
                            }
                        }
                        ClientEventHandler.STYLE.bindTexture("faction_war");
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 271, this.guiTop + 96, 248 * GUI_SCALE, 0 * GUI_SCALE, 179 * GUI_SCALE, 63 * GUI_SCALE, 179, 63, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                        if (currentWarRequest.get((Object)"status").equals("finished")) {
                            ClientEventHandler.STYLE.bindTexture("faction_war");
                            if (FactionGUI.factionInfos.get("id").equals(currentWarRequest.get((Object)"factionATTId"))) {
                                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 271, this.guiTop + 96, 440 * GUI_SCALE, (currentWarRequest.get((Object)"factionATTId").equals(currentWarRequest.get((Object)"winner")) ? 371 : 282) * GUI_SCALE, 45 * GUI_SCALE, 63 * GUI_SCALE, 45, 63, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                            }
                            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 276, this.guiTop + 124, 248 * GUI_SCALE, 97 * GUI_SCALE, 35 * GUI_SCALE, 32 * GUI_SCALE, 35, 32, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 286, this.guiTop + 128, (currentWarRequest.get((Object)"factionATTId").equals(currentWarRequest.get((Object)"winner")) ? 305 : 285) * GUI_SCALE, 184 * GUI_SCALE, 18 * GUI_SCALE, 16 * GUI_SCALE, 18, 16, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                            ModernGui.drawScaledStringCustomFont(currentWarRequest.get((Object)"factionATTId").equals(currentWarRequest.get((Object)"winner")) ? I18n.func_135053_a((String)"faction.common.victory") : I18n.func_135053_a((String)"faction.common.defeat"), this.guiLeft + 277 + 17, this.guiTop + 147, 10395075, 0.5f, "center", false, "georamaMedium", 22);
                            ClientEventHandler.STYLE.bindTexture("faction_war");
                            if (FactionGUI.factionInfos.get("id").equals(currentWarRequest.get((Object)"factionDEFId"))) {
                                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 405, this.guiTop + 96, 440 * GUI_SCALE, (currentWarRequest.get((Object)"factionDEFId").equals(currentWarRequest.get((Object)"winner")) ? 371 : 282) * GUI_SCALE, 45 * GUI_SCALE, 63 * GUI_SCALE, 45, 63, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                            }
                            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 410, this.guiTop + 124, 248 * GUI_SCALE, 97 * GUI_SCALE, 35 * GUI_SCALE, 32 * GUI_SCALE, 35, 32, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 420, this.guiTop + 128, (currentWarRequest.get((Object)"factionDEFId").equals(currentWarRequest.get((Object)"winner")) ? 305 : 285) * GUI_SCALE, 184 * GUI_SCALE, 18 * GUI_SCALE, 16 * GUI_SCALE, 18, 16, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                            ModernGui.drawScaledStringCustomFont(currentWarRequest.get((Object)"factionDEFId").equals(currentWarRequest.get((Object)"winner")) ? I18n.func_135053_a((String)"faction.common.victory") : I18n.func_135053_a((String)"faction.common.defeat"), this.guiLeft + 411 + 17, this.guiTop + 147, 10395075, 0.5f, "center", false, "georamaMedium", 22);
                        }
                        ClientProxy.loadCountryFlag((String)currentWar.get("factionATTName"));
                        ClientProxy.loadCountryFlag((String)currentWar.get("factionDEFName"));
                        if (ClientProxy.flagsTexture.containsKey(currentWar.get("factionATTName"))) {
                            GL11.glBindTexture((int)3553, (int)ClientProxy.flagsTexture.get(currentWar.get("factionATTName")).func_110552_b());
                            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 275, this.guiTop + 101, 0.0f, 0.0f, 156, 78, 36, 21, 156.0f, 78.0f, false);
                        }
                        if (ClientProxy.flagsTexture.containsKey(currentWar.get("factionDEFName"))) {
                            GL11.glBindTexture((int)3553, (int)ClientProxy.flagsTexture.get(currentWar.get("factionDEFName")).func_110552_b());
                            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 410, this.guiTop + 101, 0.0f, 0.0f, 156, 78, 36, 21, 156.0f, 78.0f, false);
                        }
                        ClientEventHandler.STYLE.bindTexture("faction_war");
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 318, this.guiTop + 109, 132 * GUI_SCALE, 0 * GUI_SCALE, 86 * GUI_SCALE, 6 * GUI_SCALE, 86, 6, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                        String side = "att";
                        float f = 0.0f;
                        String winner = "";
                        if ((Double)currentWarRequest.get((Object)"progressATT") == 1.0 || currentWarRequest.get((Object)"winner") != null && currentWarRequest.get((Object)"winner").equals(currentWarRequest.get((Object)"factionATTId"))) {
                            winner = (String)currentWarRequest.get((Object)"factionATT");
                            f = 1.0f;
                        } else if ((Double)currentWarRequest.get((Object)"progressATT") == 1.0 || currentWarRequest.get((Object)"winner") != null && currentWarRequest.get((Object)"winner").equals(currentWarRequest.get((Object)"factionDEFId"))) {
                            winner = (String)currentWarRequest.get((Object)"factionDEF");
                            f = -1.0f;
                        } else if ((Double)currentWarRequest.get((Object)"progressATT") >= (Double)currentWarRequest.get((Object)"progressDEF")) {
                            f = (float)((Double)currentWarRequest.get((Object)"progressATT") - (Double)currentWarRequest.get((Object)"progressDEF"));
                            side = "att";
                        } else {
                            f = (float)((Double)currentWarRequest.get((Object)"progressDEF") - (Double)currentWarRequest.get((Object)"progressATT"));
                            side = "def";
                        }
                        if (side.equals("att")) {
                            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 318, this.guiTop + 109, 132 * GUI_SCALE, progressBarY.get(FactionGUI.factionInfos.get("actualRelation")) * GUI_SCALE, (43 + (int)(43.0f * f)) * GUI_SCALE, 6 * GUI_SCALE, 43 + (int)(43.0f * f), 6, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                        } else if (side.equals("def")) {
                            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 318 + 86 - (43 + (int)(43.0f * f)), this.guiTop + 109, (218 - (43 + (int)(43.0f * f))) * GUI_SCALE, progressBarY.get(FactionGUI.factionInfos.get("actualRelation")) * GUI_SCALE, (43 + (int)(43.0f * f)) * GUI_SCALE, 6 * GUI_SCALE, 43 + (int)(43.0f * f), 6, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                        }
                        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.common.victory"), this.guiLeft + 361, this.guiTop + 100, 10395075, 0.5f, "center", false, "georamaSemiBold", 24);
                        ModernGui.drawScaledStringCustomFont(String.format("%.0f", (Double)currentWarRequest.get((Object)"progressATT") * 100.0) + "%", this.guiLeft + 318, this.guiTop + 100, 16514302, 0.5f, "left", false, "georamaSemiBold", 24);
                        ModernGui.drawScaledStringCustomFont(String.format("%.0f", (Double)currentWarRequest.get((Object)"progressDEF") * 100.0) + "%", this.guiLeft + 318 + 86, this.guiTop + 100, 16514302, 0.5f, "right", false, "georamaSemiBold", 24);
                        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.enemy.inactivity"), this.guiLeft + 361, this.guiTop + 118, 10395075, 0.5f, "center", false, "georamaSemiBold", 24);
                        ModernGui.drawScaledStringCustomFont((currentWarRequest.get((Object)"inactivity_ATT") != null ? currentWarRequest.get((Object)"inactivity_ATT") : Integer.valueOf(0)) + "%", this.guiLeft + 318, this.guiTop + 118, 16514302, 0.5f, "left", false, "georamaSemiBold", 24);
                        ModernGui.drawScaledStringCustomFont((currentWarRequest.get((Object)"inactivity_DEF") != null ? currentWarRequest.get((Object)"inactivity_DEF") : Integer.valueOf(0)) + "%", this.guiLeft + 318 + 86, this.guiTop + 118, 16514302, 0.5f, "right", false, "georamaSemiBold", 24);
                        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.war.label.missile_points"), this.guiLeft + 361, this.guiTop + 128, 10395075, 0.5f, "center", false, "georamaMedium", 22);
                        ModernGui.drawScaledStringCustomFont(String.format("%.0f", (Double)currentWar.get("totalMissilePointsAtt")), this.guiLeft + 318, this.guiTop + 128, 16514302, 0.5f, "left", false, "georamaMedium", 22);
                        ModernGui.drawScaledStringCustomFont(String.format("%.0f", (Double)currentWar.get("totalMissilePointsDef")), this.guiLeft + 318 + 86, this.guiTop + 128, 16514302, 0.5f, "right", false, "georamaMedium", 22);
                        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.war.label.assaults_win"), this.guiLeft + 361, this.guiTop + 136, 10395075, 0.5f, "center", false, "georamaMedium", 22);
                        ModernGui.drawScaledStringCustomFont(String.format("%.0f", (Double)currentWar.get("totalAssaultsWinAtt")), this.guiLeft + 318, this.guiTop + 136, 16514302, 0.5f, "left", false, "georamaMedium", 22);
                        ModernGui.drawScaledStringCustomFont(String.format("%.0f", (Double)currentWar.get("totalAssaultsWinDef")), this.guiLeft + 318 + 86, this.guiTop + 136, 16514302, 0.5f, "right", false, "georamaMedium", 22);
                        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.war.label.kills"), this.guiLeft + 361, this.guiTop + 144, 10395075, 0.5f, "center", false, "georamaMedium", 22);
                        ModernGui.drawScaledStringCustomFont(String.format("%.0f", (Double)currentWar.get("totalKillsAtt")), this.guiLeft + 318, this.guiTop + 144, 16514302, 0.5f, "left", false, "georamaMedium", 22);
                        ModernGui.drawScaledStringCustomFont(String.format("%.0f", (Double)currentWar.get("totalKillsDef")), this.guiLeft + 318 + 86, this.guiTop + 144, 16514302, 0.5f, "right", false, "georamaMedium", 22);
                        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.war.label.mmr"), this.guiLeft + 361, this.guiTop + 152, 10395075, 0.5f, "center", false, "georamaMedium", 22);
                        ModernGui.drawScaledStringCustomFont(String.format("%.0f", (Double)currentWar.get("totalMMRAtt")), this.guiLeft + 318, this.guiTop + 152, 16514302, 0.5f, "left", false, "georamaMedium", 22);
                        ModernGui.drawScaledStringCustomFont(String.format("%.0f", (Double)currentWar.get("totalMMRDef")), this.guiLeft + 318 + 86, this.guiTop + 152, 16514302, 0.5f, "right", false, "georamaMedium", 22);
                        if (!currentWarRequest.get((Object)"status").equals("finished")) {
                            ClientEventHandler.STYLE.bindTexture("faction_war");
                            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 277, this.guiTop + 124, 248 * GUI_SCALE, 97 * GUI_SCALE, 35 * GUI_SCALE, 32 * GUI_SCALE, 35, 32, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 285, this.guiTop + 126, ((Double)currentWar.get("remainingPointsATT") > 0.0 ? 309 : 290) * GUI_SCALE, 103 * GUI_SCALE, 18 * GUI_SCALE, 18 * GUI_SCALE, 18, 18, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                            ModernGui.drawScaledStringCustomFont(String.format("%.0f", (Double)currentWar.get("remainingPointsATT")) + " point(s)", this.guiLeft + 277 + 17, this.guiTop + 147, 10395075, 0.5f, "center", false, "georamaMedium", 22);
                            ClientEventHandler.STYLE.bindTexture("faction_war");
                            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 409, this.guiTop + 124, 248 * GUI_SCALE, 97 * GUI_SCALE, 35 * GUI_SCALE, 32 * GUI_SCALE, 35, 32, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 417, this.guiTop + 126, ((Double)currentWar.get("remainingPointsDEF") > 0.0 ? 346 : 327) * GUI_SCALE, 103 * GUI_SCALE, 18 * GUI_SCALE, 18 * GUI_SCALE, 18, 18, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                            ModernGui.drawScaledStringCustomFont(String.format("%.0f", (Double)currentWar.get("remainingPointsDEF")) + " point(s)", this.guiLeft + 409 + 17, this.guiTop + 147, 10395075, 0.5f, "center", false, "georamaMedium", 22);
                        }
                        ClientEventHandler.STYLE.bindTexture("faction_war");
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 271, this.guiTop + 162, 248 * GUI_SCALE, 0 * GUI_SCALE, 179 * GUI_SCALE, 63 * GUI_SCALE, 179, 63, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.enemy.contitions_with_mode").replaceAll("#mode#", I18n.func_135053_a((String)("faction.enemy.conditionsType." + currentWarRequest.get((Object)"conditionsType")))), this.guiLeft + 278, this.guiTop + 166, 16514302, 0.5f, "left", false, "georamaSemiBold", 28);
                        ClientEventHandler.STYLE.bindTexture("faction_war");
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 445, this.guiTop + 168, 460 * GUI_SCALE, 0 * GUI_SCALE, 2 * GUI_SCALE, 52 * GUI_SCALE, 2, 52, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                        if (currentWarRequest.get((Object)"conditions") != null) {
                            GUIUtils.startGLScissor(this.guiLeft + 271, this.guiTop + 177, 179, 44);
                            index = 0;
                            for (String finishCondition : ((String)currentWarRequest.get((Object)"conditions")).split(",")) {
                                if (finishCondition.isEmpty() || finishCondition.split("#").length != 2) continue;
                                String conditionName = finishCondition.split("#")[0];
                                String conditionGoal = finishCondition.split("#")[1];
                                int offsetX = this.guiLeft + 271;
                                Float offsetY = Float.valueOf((float)(this.guiTop + 177 + index * 9) + this.getSlideConditions());
                                String line = (((LinkedTreeMap)currentWarRequest.get((Object)"data_ATT")).containsKey((Object)conditionName) ? String.format("%.0f", ((LinkedTreeMap)currentWarRequest.get((Object)"data_ATT")).get((Object)conditionName)) : "0") + "/" + conditionGoal + " " + I18n.func_135053_a((String)("faction.enemy.conditions." + conditionName));
                                if (((LinkedTreeMap)currentWarRequest.get((Object)"data_ATT")).containsKey((Object)conditionName) && (Double)((LinkedTreeMap)currentWarRequest.get((Object)"data_ATT")).get((Object)conditionName) >= (double)Integer.parseInt(conditionGoal)) {
                                    line = "\u00a7a" + line;
                                }
                                ModernGui.drawScaledStringCustomFont(line, offsetX + 7, offsetY.intValue() + 0, 10395075, 0.5f, "left", false, "georamaMedium", 28);
                                line = (((LinkedTreeMap)currentWarRequest.get((Object)"data_DEF")).containsKey((Object)conditionName) ? String.format("%.0f", ((LinkedTreeMap)currentWarRequest.get((Object)"data_DEF")).get((Object)conditionName)) : "0") + "/" + conditionGoal + " " + I18n.func_135053_a((String)("faction.enemy.conditions." + conditionName));
                                if (((LinkedTreeMap)currentWarRequest.get((Object)"data_DEF")).containsKey((Object)conditionName) && (Double)((LinkedTreeMap)currentWarRequest.get((Object)"data_DEF")).get((Object)conditionName) >= (double)Integer.parseInt(conditionGoal)) {
                                    line = "\u00a7a" + line;
                                }
                                ModernGui.drawScaledStringCustomFont(line, offsetX + 179 - 7, offsetY.intValue() + 0, 10395075, 0.5f, "right", false, "georamaMedium", 28);
                                ClientEventHandler.STYLE.bindTexture("faction_war");
                                ModernGui.drawScaledCustomSizeModalRect(offsetX + 87, offsetY.intValue() - 1, iconsConditionsRewards.get(conditionName) * GUI_SCALE, 106 * GUI_SCALE, 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                                ++index;
                            }
                            GUIUtils.endGLScissor();
                            if (mouseX >= this.guiLeft + 271 && mouseX <= this.guiLeft + 271 + 179 && mouseY >= this.guiTop + 162 && mouseY <= this.guiTop + 162 + 63) {
                                this.scrollBarConditions.draw(mouseX, mouseY);
                            }
                        }
                        ClientEventHandler.STYLE.bindTexture("faction_war");
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 43, this.guiTop + 192, 235 * GUI_SCALE, 64 * GUI_SCALE, 211 * GUI_SCALE, 32 * GUI_SCALE, 211, 32, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.enemy.rewards"), this.guiLeft + 50, this.guiTop + 196, 16514302, 0.5f, "left", false, "georamaSemiBold", 28);
                        if (currentWarRequest.get((Object)"rewards") != null) {
                            index = 0;
                            for (String finishReward : ((String)currentWarRequest.get((Object)"rewards")).split(",")) {
                                if (!finishReward.isEmpty() && finishReward.split("#").length == 2) {
                                    String rewardName = finishReward.split("#")[0];
                                    String rewardGoal = finishReward.split("#")[1];
                                    int offSetX = this.guiLeft + 50;
                                    if (index >= 2) {
                                        offSetX = this.guiLeft + 43 + 211;
                                    }
                                    int offsetY = this.guiTop + 205 + index % 2 * 9;
                                    String label = "";
                                    if (Integer.parseInt(rewardGoal) > 0) {
                                        String valueType = "";
                                        if (rewardName.equals("peace")) {
                                            valueType = I18n.func_135053_a((String)"faction.enemy.rewards.valueType.day");
                                        }
                                        label = rewardGoal + valueType + " " + I18n.func_135053_a((String)("faction.enemy.rewards." + rewardName));
                                    } else {
                                        label = I18n.func_135053_a((String)("faction.enemy.rewards." + rewardName));
                                    }
                                    ClientEventHandler.STYLE.bindTexture("faction_war");
                                    ModernGui.drawScaledCustomSizeModalRect(index < 2 ? (float)offSetX : (float)(offSetX - 15), offsetY, iconsConditionsRewards.get(rewardName) * GUI_SCALE, 106 * GUI_SCALE, 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                                    ModernGui.drawScaledStringCustomFont(label, index < 2 ? (float)(offSetX + 12) : (float)(offSetX - 17), offsetY, 10395075, 0.5f, index < 2 ? "left" : "right", false, "georamaMedium", 28);
                                }
                                ++index;
                            }
                        }
                    }
                } else {
                    String type;
                    String factionSenderId;
                    Integer agreementId;
                    Object offsetY;
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.war.label.list_agreements"), this.guiLeft + 43, this.guiTop + 97, 16514302, 0.5f, "left", false, "georamaSemiBold", 28);
                    if (FactionGUI.hasPermissions("wars")) {
                        ClientEventHandler.STYLE.bindTexture("faction_war_2");
                        if (mouseX >= this.guiLeft + 48 + (int)this.cFontSemiBold28.getStringWidth(I18n.func_135053_a((String)"faction.war.label.list_agreements")) / 2 && mouseX <= this.guiLeft + 48 + (int)this.cFontSemiBold28.getStringWidth(I18n.func_135053_a((String)"faction.war.label.list_agreements")) / 2 + 8 && mouseY >= this.guiTop + 97 && mouseY <= this.guiTop + 97 + 8) {
                            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 48 + (int)this.cFontSemiBold28.getStringWidth(I18n.func_135053_a((String)"faction.war.label.list_agreements")) / 2, this.guiTop + 97, 248 * GUI_SCALE, 188 * GUI_SCALE, 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                            this.hoveredAction = "add_agreement";
                        } else {
                            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 48 + (int)this.cFontSemiBold28.getStringWidth(I18n.func_135053_a((String)"faction.war.label.list_agreements")) / 2, this.guiTop + 97, 237 * GUI_SCALE, 188 * GUI_SCALE, 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                        }
                    }
                    ClientEventHandler.STYLE.bindTexture("faction_war_2");
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 42, this.guiTop + 108, 418 * GUI_SCALE, 0 * GUI_SCALE, 94 * GUI_SCALE, 15 * GUI_SCALE, 94, 15, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 42, this.guiTop + 123, 418 * GUI_SCALE, 15 * GUI_SCALE, 94 * GUI_SCALE, 96 * GUI_SCALE, 94, 96, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.war.label.agreements_active"), this.guiLeft + 47, this.guiTop + 112, 2234425, 0.5f, "left", false, "georamaSemiBold", 30);
                    ModernGui.drawScaledStringCustomFont(((ArrayList)currentWar.get("agreements_active")).size() + "", this.guiLeft + 131, this.guiTop + 112, 2234425, 0.5f, "right", false, "georamaSemiBold", 30);
                    ClientEventHandler.STYLE.bindTexture("faction_war_2");
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 131, this.guiTop + 125, 362 * GUI_SCALE, 142 * GUI_SCALE, 2 * GUI_SCALE, 91 * GUI_SCALE, 2, 91, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                    GUIUtils.startGLScissor(this.guiLeft + 42, this.guiTop + 123, 89, 96);
                    for (i = 0; i < ((ArrayList)currentWar.get("agreements_active")).size(); ++i) {
                        int n = this.guiLeft + 42;
                        offsetY = Float.valueOf((float)(this.guiTop + 123 + 4 + i * 13) + this.getSlideActiveAgreements());
                        agreementId = ((Double)((LinkedTreeMap)((ArrayList)currentWar.get("agreements_active")).get(i)).get((Object)"id")).intValue();
                        factionSenderId = (String)((LinkedTreeMap)((ArrayList)currentWar.get("agreements_active")).get(i)).get((Object)"factionSender");
                        type = (String)((LinkedTreeMap)((ArrayList)currentWar.get("agreements_active")).get(i)).get((Object)"type");
                        Long creationTime = ((Double)((LinkedTreeMap)((ArrayList)currentWar.get("agreements_active")).get(i)).get((Object)"creationTime")).longValue();
                        ClientProxy.loadCountryFlag(factionSenderId);
                        if (ClientProxy.flagsTexture.containsKey(factionSenderId)) {
                            GL11.glBindTexture((int)3553, (int)ClientProxy.flagsTexture.get(factionSenderId).func_110552_b());
                            ModernGui.drawScaledCustomSizeModalRect(n + 5, ((Float)offsetY).intValue() + 0, 0.0f, 0.0f, 156, 78, 17, 10, 156.0f, 78.0f, false);
                        }
                        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("faction.enemy.agreement.type.short." + type)), n + 26, ((Float)offsetY).intValue() + 2, agreementId == selectedAgreementId ? 16514302 : 10395075, 0.5f, "left", false, "georamaMedium", 28);
                        String delay = ModernGui.formatDelayTime(creationTime);
                        ModernGui.drawScaledStringCustomFont(delay, n + 89 - 2, ((Float)offsetY).intValue() + 2, agreementId == selectedAgreementId ? 16514302 : 10395075, 0.5f, "right", false, "georamaMedium", 28);
                        if (mouseX < n || mouseX > n + 89 || mouseY < ((Float)offsetY).intValue() || mouseY > ((Float)offsetY).intValue() + 13) continue;
                        this.hoveredAction = "open_agreement#" + agreementId;
                    }
                    GUIUtils.endGLScissor();
                    if (mouseX >= this.guiLeft + 42 && mouseX <= this.guiLeft + 42 + 89 && mouseY >= this.guiTop + 123 && mouseY <= this.guiTop + 123 + 96) {
                        this.scrollBarAgreementsActive.draw(mouseX, mouseY);
                    }
                    ClientEventHandler.STYLE.bindTexture("faction_war_2");
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 140, this.guiTop + 108, 418 * GUI_SCALE, 0 * GUI_SCALE, 94 * GUI_SCALE, 15 * GUI_SCALE, 94, 15, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 140, this.guiTop + 123, 418 * GUI_SCALE, 15 * GUI_SCALE, 94 * GUI_SCALE, 96 * GUI_SCALE, 94, 96, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.war.label.agreements_waiting"), this.guiLeft + 145, this.guiTop + 112, 2234425, 0.5f, "left", false, "georamaSemiBold", 30);
                    ModernGui.drawScaledStringCustomFont(((ArrayList)currentWar.get("agreements_waiting")).size() + "", this.guiLeft + 229, this.guiTop + 112, 2234425, 0.5f, "right", false, "georamaSemiBold", 30);
                    ClientEventHandler.STYLE.bindTexture("faction_war_2");
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 229, this.guiTop + 125, 362 * GUI_SCALE, 142 * GUI_SCALE, 2 * GUI_SCALE, 91 * GUI_SCALE, 2, 91, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                    GUIUtils.startGLScissor(this.guiLeft + 140, this.guiTop + 123, 89, 96);
                    for (i = 0; i < ((ArrayList)currentWar.get("agreements_waiting")).size(); ++i) {
                        int n = this.guiLeft + 140;
                        offsetY = Float.valueOf((float)(this.guiTop + 123 + 4 + i * 13) + this.getSlideWaitingAgreements());
                        agreementId = ((Double)((LinkedTreeMap)((ArrayList)currentWar.get("agreements_waiting")).get(i)).get((Object)"id")).intValue();
                        factionSenderId = (String)((LinkedTreeMap)((ArrayList)currentWar.get("agreements_waiting")).get(i)).get((Object)"factionSender");
                        type = (String)((LinkedTreeMap)((ArrayList)currentWar.get("agreements_waiting")).get(i)).get((Object)"type");
                        String status = (String)((LinkedTreeMap)((ArrayList)currentWar.get("agreements_waiting")).get(i)).get((Object)"status");
                        Long creationTime = ((Double)((LinkedTreeMap)((ArrayList)currentWar.get("agreements_waiting")).get(i)).get((Object)"creationTime")).longValue();
                        ClientProxy.loadCountryFlag(factionSenderId);
                        if (ClientProxy.flagsTexture.containsKey(factionSenderId)) {
                            GL11.glBindTexture((int)3553, (int)ClientProxy.flagsTexture.get(factionSenderId).func_110552_b());
                            ModernGui.drawScaledCustomSizeModalRect(n + 5, ((Float)offsetY).intValue() + 0, 0.0f, 0.0f, 156, 78, 17, 10, 156.0f, 78.0f, false);
                        }
                        ModernGui.drawScaledStringCustomFont((agreementId != selectedAgreementId && status.equals("refused") ? "\u00a7c" : (agreementId != selectedAgreementId && status.equals("expired") ? "\u00a7n" : "")) + I18n.func_135053_a((String)("faction.enemy.agreement.type.short." + type)), n + 26, ((Float)offsetY).intValue() + 2, agreementId == selectedAgreementId ? 16514302 : 10395075, 0.5f, "left", false, "georamaMedium", 28);
                        String delay = ModernGui.formatDelayTime(creationTime);
                        ModernGui.drawScaledStringCustomFont((agreementId != selectedAgreementId && status.equals("refused") ? "\u00a7c" : (agreementId != selectedAgreementId && status.equals("expired") ? "\u00a7n" : "")) + delay, n + 89 - 2, ((Float)offsetY).intValue() + 2, agreementId == selectedAgreementId ? 16514302 : 10395075, 0.5f, "right", false, "georamaMedium", 28);
                        if (mouseX < n || mouseX > n + 89 || mouseY < ((Float)offsetY).intValue() || mouseY > ((Float)offsetY).intValue() + 13) continue;
                        this.hoveredAction = "open_agreement#" + agreementId;
                    }
                    GUIUtils.endGLScissor();
                    if (mouseX >= this.guiLeft + 140 && mouseX <= this.guiLeft + 140 + 89 && mouseY >= this.guiTop + 123 && mouseY <= this.guiTop + 123 + 96) {
                        this.scrollBarAgreementsWaiting.draw(mouseX, mouseY);
                    }
                    Gui.func_73734_a((int)(this.guiLeft + 241), (int)(this.guiTop + 89), (int)(this.guiLeft + this.xSize), (int)(this.guiTop + this.ySize), (int)-14279619);
                    if (this.agreementCreationMode || selectedAgreementId != -1) {
                        ClientEventHandler.STYLE.bindTexture("faction_war_2");
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 258, this.guiTop + 108, 197 * GUI_SCALE, 426 * GUI_SCALE, 95 * GUI_SCALE, 55 * GUI_SCALE, 95, 55, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 357, this.guiTop + 108, 197 * GUI_SCALE, 426 * GUI_SCALE, 95 * GUI_SCALE, 55 * GUI_SCALE, 95, 55, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.war.label.agreements.my_side"), this.guiLeft + 264, this.guiTop + 115, 16514302, 0.5f, "left", false, "georamaSemiBold", 30);
                        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.war.label.agreements.other_side"), this.guiLeft + 363, this.guiTop + 115, 16514302, 0.5f, "left", false, "georamaSemiBold", 30);
                        ClientEventHandler.STYLE.bindTexture("faction_war_2");
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 258, this.guiTop + 167, 0 * GUI_SCALE, 428 * GUI_SCALE, 194 * GUI_SCALE, 52 * GUI_SCALE, 194, 52, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                    }
                    if (!this.agreementCreationMode) {
                        if (((LinkedTreeMap)currentWar.get("agreements")).containsKey((Object)(selectedAgreementId + ""))) {
                            LinkedTreeMap agreement = (LinkedTreeMap)((LinkedTreeMap)currentWar.get("agreements")).get((Object)(selectedAgreementId + ""));
                            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.war.label.details_agreements"), this.guiLeft + 258, this.guiTop + 97, 16514302, 0.5f, "left", false, "georamaSemiBold", 28);
                            List<String> list = Arrays.asList(((String)agreement.get((Object)"conditions")).split(","));
                            index = 0;
                            for (String condition : this.agreementsAvailableConditions) {
                                int conditionsMY = 0;
                                int conditionsOTHER = 0;
                                for (String agreementCondition : list) {
                                    if (!agreementCondition.contains(condition)) continue;
                                    if (agreementCondition.contains((String)FactionGUI.factionInfos.get("name"))) {
                                        conditionsMY = Integer.parseInt(agreementCondition.split("#")[1]);
                                        continue;
                                    }
                                    conditionsOTHER = Integer.parseInt(agreementCondition.split("#")[1]);
                                }
                                ClientEventHandler.STYLE.bindTexture("faction_war");
                                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 265, this.guiTop + 127 + 11 * index, iconsConditionsRewards.get(condition) * GUI_SCALE, (conditionsMY != 0 ? 114 : 106) * GUI_SCALE, 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("faction.enemy.agreement.conditions." + condition)), this.guiLeft + 265 + 12, this.guiTop + 127 + 11 * index, conditionsMY != 0 ? 0x6E76EE : 10395075, 0.5f, "left", false, "georamaSemiBold", 28);
                                ModernGui.drawScaledStringCustomFont(conditionsMY + "", this.guiLeft + 323, this.guiTop + 127 + 11 * index, 16514302, 0.5f, "left", false, "georamaSemiBold", 28);
                                ClientEventHandler.STYLE.bindTexture("faction_war");
                                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 364, this.guiTop + 127 + 11 * index, iconsConditionsRewards.get(condition) * GUI_SCALE, (conditionsOTHER != 0 ? 130 : 106) * GUI_SCALE, 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("faction.enemy.agreement.conditions." + condition)), this.guiLeft + 364 + 12, this.guiTop + 127 + 11 * index, conditionsOTHER != 0 ? 15017020 : 10395075, 0.5f, "left", false, "georamaSemiBold", 28);
                                ModernGui.drawScaledStringCustomFont(conditionsOTHER + "", this.guiLeft + 421, this.guiTop + 127 + 11 * index, 16514302, 0.5f, "left", false, "georamaSemiBold", 28);
                                ++index;
                            }
                            ClientEventHandler.STYLE.bindTexture("faction_war_2");
                            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 280, this.guiTop + 170, iconsAgreements.get(agreement.get((Object)"type")) * GUI_SCALE, 430 * GUI_SCALE, 45 * GUI_SCALE, 45 * GUI_SCALE, 45, 45, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.war.label.agreements.informations"), this.guiLeft + 357, this.guiTop + 173, 16514302, 0.5f, "left", false, "georamaSemiBold", 30);
                            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("faction.enemy.agreement.status.short." + agreement.get((Object)"status"))), this.guiLeft + 357, this.guiTop + 183, 10395075, 0.5f, "left", false, "georamaSemiBold", 24);
                            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("faction.enemy.agreement.type." + agreement.get((Object)"type"))) + " (" + String.format("%.0f", (Double)agreement.get((Object)"value")) + I18n.func_135053_a((String)"faction.common.days.short") + ")", this.guiLeft + 357, this.guiTop + 190, 10395075, 0.5f, "left", false, "georamaSemiBold", 24);
                            Date date = new Date(((Double)agreement.get((Object)"creationTime")).longValue());
                            if (agreement.get((Object)"status").equals("active")) {
                                date = new Date(((Double)agreement.get((Object)"signatureTime")).longValue());
                            }
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                            String dateFormated = simpleDateFormat.format(date);
                            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)(agreement.get((Object)"status").equals("active") ? "faction.war.label.agreements.signed_at" : "faction.war.label.agreements.received_at")).replaceAll("#date#", dateFormated), this.guiLeft + 357, this.guiTop + 197, 10395075, 0.5f, "left", false, "georamaSemiBold", 24);
                            if (FactionGUI.hasPermissions("wars") && agreement.get((Object)"status").equals("waiting") && FactionGUI.hasPermissions("wars") && !FactionGUI.factionInfos.get("id").equals(agreement.get((Object)"factionSender"))) {
                                ClientEventHandler.STYLE.bindTexture("faction_war_2");
                                if (mouseX >= this.guiLeft + 357 && mouseX <= this.guiLeft + 357 + 39 && mouseY >= this.guiTop + 206 && mouseY <= this.guiTop + 206 + 11) {
                                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 357, this.guiTop + 206, 191 * GUI_SCALE, 202 * GUI_SCALE, 39 * GUI_SCALE, 11 * GUI_SCALE, 39, 11, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                                    this.hoveredAction = "agreement_accept#" + ((Double)agreement.get((Object)"id")).intValue();
                                } else {
                                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 357, this.guiTop + 206, 191 * GUI_SCALE, 188 * GUI_SCALE, 39 * GUI_SCALE, 11 * GUI_SCALE, 39, 11, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                                }
                                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.enemy.agreement.accept"), this.guiLeft + 357 + 20, this.guiTop + 208, 2234425, 0.5f, "center", false, "georamaSemiBold", 25);
                                ClientEventHandler.STYLE.bindTexture("faction_war_2");
                                if (mouseX >= this.guiLeft + 400 && mouseX <= this.guiLeft + 400 + 39 && mouseY >= this.guiTop + 206 && mouseY <= this.guiTop + 206 + 11) {
                                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 400, this.guiTop + 206, 191 * GUI_SCALE, 202 * GUI_SCALE, 39 * GUI_SCALE, 11 * GUI_SCALE, 39, 11, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.enemy.agreement.refuse"), this.guiLeft + 400 + 20, this.guiTop + 208, 2234425, 0.5f, "center", false, "georamaSemiBold", 25);
                                    this.hoveredAction = "agreement_refuse#" + ((Double)agreement.get((Object)"id")).intValue();
                                } else {
                                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 400, this.guiTop + 206, 237 * GUI_SCALE, 202 * GUI_SCALE, 39 * GUI_SCALE, 11 * GUI_SCALE, 39, 11, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.enemy.agreement.refuse"), this.guiLeft + 400 + 20, this.guiTop + 208, 14803951, 0.5f, "center", false, "georamaSemiBold", 25);
                                }
                            } else if (FactionGUI.hasPermissions("wars") && agreement.get((Object)"status").equals("waiting") && FactionGUI.hasPermissions("wars") && FactionGUI.factionInfos.get("id").equals(agreement.get((Object)"factionSender"))) {
                                ClientEventHandler.STYLE.bindTexture("faction_war_2");
                                if (mouseX >= this.guiLeft + 400 && mouseX <= this.guiLeft + 400 + 39 && mouseY >= this.guiTop + 206 && mouseY <= this.guiTop + 206 + 11) {
                                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 400, this.guiTop + 206, 191 * GUI_SCALE, 202 * GUI_SCALE, 39 * GUI_SCALE, 11 * GUI_SCALE, 39, 11, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.enemy.agreement.cancel"), this.guiLeft + 400 + 20, this.guiTop + 208, 2234425, 0.5f, "center", false, "georamaSemiBold", 25);
                                    this.hoveredAction = "agreement_cancel#" + ((Double)agreement.get((Object)"id")).intValue();
                                } else {
                                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 400, this.guiTop + 206, 237 * GUI_SCALE, 202 * GUI_SCALE, 39 * GUI_SCALE, 11 * GUI_SCALE, 39, 11, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.enemy.agreement.cancel"), this.guiLeft + 400 + 20, this.guiTop + 208, 14803951, 0.5f, "center", false, "georamaSemiBold", 25);
                                }
                            } else if (agreement.get((Object)"status").equals("active")) {
                                date = new Date(((Double)agreement.get((Object)"signatureTime")).longValue() + ((Double)agreement.get((Object)"value")).longValue() * 86400000L);
                                simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                                dateFormated = simpleDateFormat.format(date);
                                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.war.label.agreements.end_at").replaceAll("#date#", dateFormated), this.guiLeft + 357, this.guiTop + 210, 14803951, 0.5f, "left", false, "georamaSemiBold", 25);
                            }
                        }
                    } else if (FactionGUI.hasPermissions("wars")) {
                        index = 0;
                        for (String string : this.agreementsAvailableConditions) {
                            ClientEventHandler.STYLE.bindTexture("faction_war");
                            if (this.agreement_myConditionsInput.containsKey(string)) {
                                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 265, this.guiTop + 127 + 11 * index, iconsConditionsRewards.get(string) * GUI_SCALE, (FactionGUI.isNumeric(this.agreement_myConditionsInput.get(string).func_73781_b(), false) ? 114 : 106) * GUI_SCALE, 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("faction.enemy.agreement.conditions." + string)), this.guiLeft + 265 + 12, this.guiTop + 127 + 11 * index, FactionGUI.isNumeric(this.agreement_myConditionsInput.get(string).func_73781_b(), false) ? 0x6E76EE : 10395075, 0.5f, "left", false, "georamaSemiBold", 28);
                                this.agreement_myConditionsInput.get(string).func_73795_f();
                                ClientEventHandler.STYLE.bindTexture("faction_war_2");
                                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 323, this.guiTop + 127 + index * 11, 191 * GUI_SCALE, 176 * GUI_SCALE, 22 * GUI_SCALE, 9 * GUI_SCALE, 22, 9, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                            }
                            if (this.agreement_otherConditionsInput.containsKey(string)) {
                                ClientEventHandler.STYLE.bindTexture("faction_war");
                                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 364, this.guiTop + 127 + 11 * index, iconsConditionsRewards.get(string) * GUI_SCALE, (FactionGUI.isNumeric(this.agreement_otherConditionsInput.get(string).func_73781_b(), false) ? 130 : 106) * GUI_SCALE, 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("faction.enemy.agreement.conditions." + string)), this.guiLeft + 364 + 12, this.guiTop + 127 + 11 * index, FactionGUI.isNumeric(this.agreement_otherConditionsInput.get(string).func_73781_b(), false) ? 15017020 : 10395075, 0.5f, "left", false, "georamaSemiBold", 28);
                                this.agreement_otherConditionsInput.get(string).func_73795_f();
                                ClientEventHandler.STYLE.bindTexture("faction_war_2");
                                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 422, this.guiTop + 127 + index * 11, 191 * GUI_SCALE, 176 * GUI_SCALE, 22 * GUI_SCALE, 9 * GUI_SCALE, 22, 9, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                            }
                            ++index;
                        }
                        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.war.label.agreements.type"), this.guiLeft + 264, this.guiTop + 174, 16514302, 0.5f, "left", false, "georamaSemiBold", 30);
                        index = 0;
                        for (String string : this.agreementsAvailableTypes) {
                            ClientEventHandler.STYLE.bindTexture("faction_global");
                            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 265, this.guiTop + 186 + 9 * index, 321 * GUI_SCALE, (this.agreement_form_type.equals(string) ? 181 : 190) * GUI_SCALE, 6 * GUI_SCALE, 6 * GUI_SCALE, 6, 6, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                            if (mouseX >= this.guiLeft + 265 && mouseX <= this.guiLeft + 265 + 6 && mouseY >= this.guiTop + 186 + 9 * index && mouseY <= this.guiTop + 186 + 9 * index + 6) {
                                this.hoveredAction = "agreement_select_type#" + string;
                            }
                            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("faction.enemy.agreement.type." + string)), this.guiLeft + 265 + 10, this.guiTop + 185 + 9 * index, 10395075, 0.5f, "left", false, "georamaSemiBold", 28);
                            ++index;
                        }
                        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.war.label.agreements.duration"), this.guiLeft + 363, this.guiTop + 174, 16514302, 0.5f, "left", false, "georamaSemiBold", 30);
                        ClientEventHandler.STYLE.bindTexture("faction_war_2");
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 363, this.guiTop + 184, 191 * GUI_SCALE, 176 * GUI_SCALE, 22 * GUI_SCALE, 9 * GUI_SCALE, 22, 9, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                        this.agreement_form_duration.func_73795_f();
                        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.enemy.rewards.valueType.dayLong") + " (max. 120)", this.guiLeft + 388, this.guiTop + 185, 10395075, 0.5f, "left", false, "georamaSemiBold", 28);
                        if (FactionGUI.hasPermissions("wars")) {
                            ClientEventHandler.STYLE.bindTexture("faction_war_2");
                            if (mouseX >= this.guiLeft + 363 && mouseX <= this.guiLeft + 363 + 39 && mouseY >= this.guiTop + 206 && mouseY <= this.guiTop + 206 + 11) {
                                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 363, this.guiTop + 206, 191 * GUI_SCALE, 202 * GUI_SCALE, 39 * GUI_SCALE, 11 * GUI_SCALE, 39, 11, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                                this.hoveredAction = "agreement_create";
                            } else {
                                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 363, this.guiTop + 206, 191 * GUI_SCALE, 188 * GUI_SCALE, 39 * GUI_SCALE, 11 * GUI_SCALE, 39, 11, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                            }
                            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.enemy.agreement.submit"), this.guiLeft + 363 + 20, this.guiTop + 208, 2234425, 0.5f, "center", false, "georamaSemiBold", 25);
                            ClientEventHandler.STYLE.bindTexture("faction_war_2");
                            if (mouseX >= this.guiLeft + 406 && mouseX <= this.guiLeft + 406 + 39 && mouseY >= this.guiTop + 206 && mouseY <= this.guiTop + 206 + 11) {
                                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 406, this.guiTop + 206, 191 * GUI_SCALE, 202 * GUI_SCALE, 39 * GUI_SCALE, 11 * GUI_SCALE, 39, 11, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.common.back"), this.guiLeft + 406 + 20, this.guiTop + 208, 2234425, 0.5f, "center", false, "georamaSemiBold", 25);
                                this.hoveredAction = "agreement_return_form";
                            } else {
                                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 406, this.guiTop + 206, 237 * GUI_SCALE, 202 * GUI_SCALE, 39 * GUI_SCALE, 11 * GUI_SCALE, 39, 11, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.common.back"), this.guiLeft + 406 + 20, this.guiTop + 208, 14803951, 0.5f, "center", false, "georamaSemiBold", 25);
                            }
                        }
                    }
                }
            } else {
                ModernGui.bindRemoteTexture("https://static.nationsglory.fr/N33y222_3N.png");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 30 + 154, this.guiTop + 0, 0.0f, 0.0f, 279 * GUI_SCALE, 110 * GUI_SCALE, 279, 89, 279 * GUI_SCALE, 110 * GUI_SCALE, false);
                ClientEventHandler.STYLE.bindTexture("faction_global");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 30, this.guiTop + 0, 33 * GUI_SCALE, 280 * GUI_SCALE, 433 * GUI_SCALE, 89 * GUI_SCALE, 433, 89, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                ClientEventHandler.STYLE.bindTexture("faction_war");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 292, this.guiTop - 15, 130 * GUI_SCALE, 173 * GUI_SCALE, 130 * GUI_SCALE, 104 * GUI_SCALE, 130, 104, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.war.title"), this.guiLeft + 43, this.guiTop + 16, 0xFFFFFF, 0.75f, "left", false, "georamaSemiBold", 32);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.war.label.no_war_in_progress"), this.guiLeft + 43, this.guiTop + 105, 16514302, 0.5f, "left", false, "georamaSemiBold", 32);
            }
            ModernGui.drawScaledStringCustomFont((String)FactionGUI.factionInfos.get("name"), this.guiLeft + 43, this.guiTop + 6, 10395075, 0.5f, "left", false, "georamaMedium", 32);
        }
        super.func_73863_a(mouseX, mouseY, partialTick);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
    }

    public void func_73876_c() {
        for (Map.Entry<String, GuiTextField> pair : this.conditionsInput.entrySet()) {
            pair.getValue().func_73780_a();
        }
        for (Map.Entry<String, GuiTextField> pair : this.rewardsInput.entrySet()) {
            pair.getValue().func_73780_a();
        }
        for (Map.Entry<String, GuiTextField> pair : this.agreement_myConditionsInput.entrySet()) {
            pair.getValue().func_73780_a();
        }
        for (Map.Entry<String, GuiTextField> pair : this.agreement_otherConditionsInput.entrySet()) {
            pair.getValue().func_73780_a();
        }
        this.agreement_form_duration.func_73780_a();
    }

    protected void func_73869_a(char typedChar, int keyCode) {
        for (Map.Entry<String, GuiTextField> pair : this.conditionsInput.entrySet()) {
            pair.getValue().func_73802_a(typedChar, keyCode);
        }
        for (Map.Entry<String, GuiTextField> pair : this.rewardsInput.entrySet()) {
            LinkedTreeMap currentWarRequest;
            if (loaded && currentWarIndex != -1 && factionsWarInfos.size() > currentWarIndex && pair.getKey().equals("claims") && !((String)(currentWarRequest = (LinkedTreeMap)factionsWarInfos.get(currentWarIndex).get("warRequest")).get((Object)"reason")).equals("territorial_expansion")) continue;
            pair.getValue().func_73802_a(typedChar, keyCode);
        }
        for (Map.Entry<String, GuiTextField> pair : this.agreement_myConditionsInput.entrySet()) {
            pair.getValue().func_73802_a(typedChar, keyCode);
        }
        for (Map.Entry<String, GuiTextField> pair : this.agreement_otherConditionsInput.entrySet()) {
            pair.getValue().func_73802_a(typedChar, keyCode);
        }
        this.agreement_form_duration.func_73802_a(typedChar, keyCode);
        super.func_73869_a(typedChar, keyCode);
    }

    private float getSlideMissiles() {
        return ((ArrayList)factionsWarInfos.get(currentWarIndex).get("missiles")).size() > 6 ? (float)(-(((ArrayList)factionsWarInfos.get(currentWarIndex).get("missiles")).size() - 6) * 12) * this.scrollBarMissiles.getSliderValue() : 0.0f;
    }

    private float getSlideAssaults() {
        return ((ArrayList)factionsWarInfos.get(currentWarIndex).get("assaults")).size() > 6 ? (float)(-(((ArrayList)factionsWarInfos.get(currentWarIndex).get("assaults")).size() - 6) * 12) * this.scrollBarAssaults.getSliderValue() : 0.0f;
    }

    private float getSlideConditions() {
        List<String> conditions = Arrays.asList(((String)((LinkedTreeMap)factionsWarInfos.get(currentWarIndex).get("warRequest")).get((Object)"conditions")).split(","));
        return conditions.size() > 5 ? (float)(-(conditions.size() - 5) * 9) * this.scrollBarConditions.getSliderValue() : 0.0f;
    }

    private float getSlideActiveAgreements() {
        return ((ArrayList)factionsWarInfos.get(currentWarIndex).get("agreements_active")).size() > 7 ? (float)(-(((ArrayList)factionsWarInfos.get(currentWarIndex).get("agreements_active")).size() - 7) * 13) * this.scrollBarAgreementsActive.getSliderValue() : 0.0f;
    }

    private float getSlideWaitingAgreements() {
        return ((ArrayList)factionsWarInfos.get(currentWarIndex).get("agreements_waiting")).size() > 7 ? (float)(-(((ArrayList)factionsWarInfos.get(currentWarIndex).get("agreements_waiting")).size() - 7) * 13) * this.scrollBarAgreementsWaiting.getSliderValue() : 0.0f;
    }

    @Override
    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0 && !this.hoveredAction.isEmpty()) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
            if (this.hoveredAction.equals("edit_photo")) {
                ClientData.lastCaptureScreenshot.put("wars", System.currentTimeMillis());
                Minecraft.func_71410_x().func_71373_a(null);
                Minecraft.func_71410_x().field_71439_g.func_70006_a(ChatMessageComponent.func_111066_d((String)I18n.func_135053_a((String)"faction.take_picture")));
            } else if (this.hoveredAction.contains("switch_war")) {
                currentWarIndex = Integer.parseInt(this.hoveredAction.replace("switch_war#", ""));
                this.defenserCounterPropositionMode = false;
                this.agreementMode = false;
                this.cachedAssaults.clear();
                if (((LinkedTreeMap)factionsWarInfos.get(currentWarIndex).get("agreements")).size() > 0) {
                    LinkedTreeMap agreements = (LinkedTreeMap)factionsWarInfos.get(currentWarIndex).get("agreements");
                    Map.Entry entry = (Map.Entry)agreements.entrySet().iterator().next();
                    selectedAgreementId = Integer.parseInt((String)entry.getKey());
                }
            } else if (this.hoveredAction.contains("conditions_cumulative")) {
                this.conditionsCumulative = this.hoveredAction.contains("true");
            } else if (this.hoveredAction.contains("open_accords")) {
                this.agreementMode = !this.agreementMode;
            } else if (this.hoveredAction.equals("accept_conditions_def")) {
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionEnemyRequestUpdateStatusPacket(((Double)factionsWarInfos.get(currentWarIndex).get("warId")).intValue(), "in_progress", (String)factionsWarInfos.get(currentWarIndex).get("status"), (String)((LinkedTreeMap)factionsWarInfos.get(currentWarIndex).get("warRequest")).get((Object)"factionATT"), (String)((LinkedTreeMap)factionsWarInfos.get(currentWarIndex).get("warRequest")).get((Object)"factionDEF"))));
                this.field_73882_e.func_71373_a((GuiScreen)new WarGUI());
            } else if (this.hoveredAction.equals("refuse_conditions_def")) {
                this.defenserCounterPropositionMode = true;
            } else if (this.hoveredAction.equals("return_counter_def")) {
                this.defenserCounterPropositionMode = false;
            } else if (this.hoveredAction.equals("accept_conditions_att_second")) {
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionEnemyRequestUpdateStatusPacket(((Double)factionsWarInfos.get(currentWarIndex).get("warId")).intValue(), "in_progress", (String)factionsWarInfos.get(currentWarIndex).get("status"), (String)((LinkedTreeMap)factionsWarInfos.get(currentWarIndex).get("warRequest")).get((Object)"factionATT"), (String)((LinkedTreeMap)factionsWarInfos.get(currentWarIndex).get("warRequest")).get((Object)"factionDEF"))));
                this.field_73882_e.func_71373_a((GuiScreen)new WarGUI());
            } else if (this.hoveredAction.equals("refuse_conditions_att_second")) {
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionEnemyRequestGenerateConditionsPacket(((Double)factionsWarInfos.get(currentWarIndex).get("warId")).intValue())));
            } else if (this.hoveredAction.equals("nextTab")) {
                ++this.tabsOffset;
            } else if (this.hoveredAction.equals("previousTab")) {
                --this.tabsOffset;
            } else if (this.hoveredAction.contains("open_agreement")) {
                selectedAgreementId = Integer.parseInt(this.hoveredAction.replaceAll("open_agreement#", ""));
                this.agreementCreationMode = false;
            } else if (this.hoveredAction.contains("add_agreement")) {
                this.agreementCreationMode = true;
            } else if (this.hoveredAction.contains("agreement_select_type")) {
                this.agreement_form_type = this.hoveredAction.replaceAll("agreement_select_type#", "");
            } else if (this.hoveredAction.contains("agreement_return_form")) {
                this.agreementCreationMode = false;
            } else if (this.hoveredAction.contains("agreement_cancel")) {
                int agreementId = Integer.parseInt(this.hoveredAction.replaceAll("agreement_cancel#", ""));
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionEnemyAgreementCancelPacket(agreementId)));
                selectedAgreementId = -1;
            } else if (this.hoveredAction.equals("agreement_create")) {
                if (!this.agreement_form_duration.func_73781_b().isEmpty() && Integer.parseInt(this.agreement_form_duration.func_73781_b()) > 120) {
                    return;
                }
                String otherCountryName = "";
                otherCountryName = FactionGUI.factionInfos.get("name").equals(factionsWarInfos.get(currentWarIndex).get("factionATTName")) ? (String)factionsWarInfos.get(currentWarIndex).get("factionDEFName") : (String)factionsWarInfos.get(currentWarIndex).get("factionATTName");
                String conditionsString = "";
                for (String condition : this.agreementsAvailableConditions) {
                    if (this.agreement_myConditionsInput.containsKey(condition) && FactionGUI.isNumeric(this.agreement_myConditionsInput.get(condition).func_73781_b(), false)) {
                        conditionsString = conditionsString + condition + "#" + this.agreement_myConditionsInput.get(condition).func_73781_b() + "#" + FactionGUI.factionInfos.get("name") + ",";
                        continue;
                    }
                    if (!this.agreement_otherConditionsInput.containsKey(condition) || !FactionGUI.isNumeric(this.agreement_otherConditionsInput.get(condition).func_73781_b(), false)) continue;
                    conditionsString = conditionsString + condition + "#" + this.agreement_otherConditionsInput.get(condition).func_73781_b() + "#" + otherCountryName + ",";
                }
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionEnemyAgreementCreatePacket(((Double)factionsWarInfos.get(currentWarIndex).get("warId")).intValue(), this.agreement_form_type, Integer.parseInt(this.agreement_form_duration.func_73781_b()), conditionsString)));
                this.agreementCreationMode = false;
                this.agreement_form_duration.func_73782_a("0");
                for (String condition : this.agreementsAvailableConditions) {
                    this.agreement_myConditionsInput.get(condition).func_73782_a("0");
                    this.agreement_otherConditionsInput.get(condition).func_73782_a("0");
                }
                this.field_73882_e.func_71373_a((GuiScreen)new WarGUI());
            } else if (this.hoveredAction.equals("send_conditions_att") || this.hoveredAction.equals("send_conditions_def")) {
                String conditionsString = "";
                String rewardsString = "";
                for (String condition : this.availableConditions) {
                    if (!this.conditionsInput.containsKey(condition) || !FactionGUI.isNumeric(this.conditionsInput.get(condition).func_73781_b(), false)) continue;
                    conditionsString = conditionsString + condition + "#" + Integer.parseInt(this.conditionsInput.get(condition).func_73781_b()) + ",";
                }
                for (String reward : this.availableRewards) {
                    if (!this.rewardsInput.containsKey(reward) || !FactionGUI.isNumeric(this.rewardsInput.get(reward).func_73781_b(), false)) continue;
                    rewardsString = rewardsString + reward + "#" + Integer.parseInt(this.rewardsInput.get(reward).func_73781_b()) + ",";
                    if (!reward.equals("peace") || Integer.parseInt(this.rewardsInput.get(reward).func_73781_b()) <= 120) continue;
                    return;
                }
                conditionsString = conditionsString.replaceAll(",$", "");
                rewardsString = rewardsString.replaceAll(",$", "");
                if (conditionsString.isEmpty()) {
                    return;
                }
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionEnemyRequestUpdateConditionsPacket(((Double)factionsWarInfos.get(currentWarIndex).get("warId")).intValue(), this.hoveredAction.replace("send_conditions_", ""), this.conditionsCumulative ? "and" : "or", conditionsString, rewardsString)));
                this.field_73882_e.func_71373_a((GuiScreen)new WarGUI());
            } else if (this.hoveredAction.contains("open_forum")) {
                try {
                    Class<?> desktop = Class.forName("java.awt.Desktop");
                    Object theDesktop = desktop.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
                    desktop.getMethod("browse", URI.class).invoke(theDesktop, URI.create((String)((LinkedTreeMap)factionsWarInfos.get(currentWarIndex).get("warRequest")).get((Object)"linkForum")));
                }
                catch (Throwable t) {
                    t.printStackTrace();
                }
            } else if (this.hoveredAction.contains("agreement_accept") || this.hoveredAction.contains("agreement_refuse")) {
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionEnemyAgreementUpdateStatusPacket(Integer.parseInt(this.hoveredAction.replaceAll("[^\\d.]", "")), this.hoveredAction.contains("accept") ? "active" : "refused")));
                selectedAgreementId = -1;
                this.scrollBarAgreementsWaiting.reset();
                this.scrollBarAgreementsActive.reset();
            } else if (this.hoveredAction.contains("surrender")) {
                this.field_73882_e.func_71373_a((GuiScreen)new SurrenderConfirmGui(this, ((Double)factionsWarInfos.get(currentWarIndex).get("warId")).intValue(), (String)FactionGUI.factionInfos.get("name")));
            } else if (this.hoveredAction.contains("rollback_assault")) {
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new RollbackAssaultDataPacket(this.hoveredAction.replaceAll("rollback_assault#", ""), (String)factionsWarInfos.get(currentWarIndex).get("name"))));
                Minecraft.func_71410_x().func_71373_a(null);
            }
            this.hoveredAction = "";
        }
        for (Map.Entry<String, GuiTextField> pair : this.conditionsInput.entrySet()) {
            pair.getValue().func_73793_a(mouseX, mouseY, mouseButton);
        }
        for (Map.Entry<String, GuiTextField> pair : this.rewardsInput.entrySet()) {
            pair.getValue().func_73793_a(mouseX, mouseY, mouseButton);
        }
        for (Map.Entry<String, GuiTextField> pair : this.agreement_myConditionsInput.entrySet()) {
            pair.getValue().func_73793_a(mouseX, mouseY, mouseButton);
        }
        for (Map.Entry<String, GuiTextField> pair : this.agreement_otherConditionsInput.entrySet()) {
            pair.getValue().func_73793_a(mouseX, mouseY, mouseButton);
        }
        this.agreement_form_duration.func_73793_a(mouseX, mouseY, mouseButton);
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    private String convertDate(String time) {
        String date = "";
        long diff = System.currentTimeMillis() - Long.parseLong(time);
        long days = diff / 86400000L;
        long hours = 0L;
        long minutes = 0L;
        long seconds = 0L;
        if (days == 0L) {
            hours = diff / 3600000L;
            if (hours == 0L) {
                minutes = diff / 60000L;
                if (minutes == 0L) {
                    seconds = diff / 1000L;
                    date = date + " " + seconds + " " + I18n.func_135053_a((String)"faction.common.seconds");
                } else {
                    date = date + " " + minutes + " " + I18n.func_135053_a((String)"faction.common.minutes");
                }
            } else {
                date = date + " " + hours + " " + I18n.func_135053_a((String)"faction.common.hours");
            }
        } else {
            date = date + " " + days + " " + I18n.func_135053_a((String)"faction.common.days");
        }
        return date;
    }

    static {
        btnForumY = new HashMap<String, Integer>(){
            {
                this.put("neutral", 50);
                this.put("enemy", 67);
                this.put("colony", 84);
                this.put("ally", 101);
            }
        };
        selectedAgreementId = -1;
        progressBarY = new HashMap<String, Integer>(){
            {
                this.put("neutral", 10);
                this.put("enemy", 20);
                this.put("colony", 30);
                this.put("ally", 40);
            }
        };
        currentWarIndex = -1;
    }
}

