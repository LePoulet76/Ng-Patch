package net.ilexiconn.nationsgui.forge.client.gui.faction;

import com.google.gson.internal.LinkedTreeMap;
import cpw.mods.fml.common.network.PacketDispatcher;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import net.halalaboos.cfont.CFontRenderer;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarGeneric;
import net.ilexiconn.nationsgui.forge.client.gui.faction.WarGUI$1;
import net.ilexiconn.nationsgui.forge.client.gui.faction.WarGUI$2;
import net.ilexiconn.nationsgui.forge.client.gui.faction.WarGUI$3;
import net.ilexiconn.nationsgui.forge.client.gui.faction.WarGUI$4;
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
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class WarGUI extends TabbedFactionGUI
{
    public static HashMap<String, Integer> iconsConditionsRewards = new WarGUI$1();
    public static HashMap<String, Integer> iconsAgreements = new WarGUI$2();
    public static boolean loaded = false;
    public static ArrayList<TreeMap<String, Object>> factionsWarInfos;
    public static HashMap<String, Integer> btnForumY = new WarGUI$3();
    public String displayMode = "infos";
    private GuiScrollBarGeneric scrollBarMissiles;
    private GuiScrollBarGeneric scrollBarAssaults;
    public static int selectedAgreementId = -1;
    public ArrayList<String> agreementsAvailableConditions = new ArrayList(Arrays.asList(new String[] {"dollars", "power", "claims"}));
    public static HashMap<String, Integer> progressBarY = new WarGUI$4();
    private String hoveredCountry = "";
    public static int currentWarIndex = -1;
    private RenderItem itemRenderer = new RenderItem();
    private ArrayList<HashMap<String, Object>> cachedAssaults = new ArrayList();
    private GuiScrollBarGeneric scrollBarConditions;
    private CFontRenderer cFontLabel;
    private CFontRenderer cFontCondtionsRewards;
    public ArrayList<String> agreementsAvailableTypes = new ArrayList(Arrays.asList(new String[] {"peace", "no_assault", "no_missile"}));
    public List<String> availableConditions = Arrays.asList(new String[] {"kill", "victory", "missile_point", "assault_point", "anti", "red"});
    public ArrayList<String> availableRewards = new ArrayList(Arrays.asList(new String[] {"dollars", "power", "claims", "peace"}));
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

    public WarGUI()
    {
        factionsWarInfos = new ArrayList();
        this.cachedAssaults.clear();
        loaded = false;
        currentWarIndex = 0;
        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionWarDataPacket((String)FactionGUI.factionInfos.get("name"))));
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        this.scrollBarMissiles = new GuiScrollBarGeneric((float)(this.guiLeft + 138), (float)(this.guiTop + 113), 69, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
        this.scrollBarAssaults = new GuiScrollBarGeneric((float)(this.guiLeft + 247), (float)(this.guiTop + 113), 69, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
        this.scrollBarConditions = new GuiScrollBarGeneric((float)(this.guiLeft + 445), (float)(this.guiTop + 168), 52, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
        this.scrollBarAgreementsActive = new GuiScrollBarGeneric((float)(this.guiLeft + 131), (float)(this.guiTop + 125), 91, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
        this.scrollBarAgreementsWaiting = new GuiScrollBarGeneric((float)(this.guiLeft + 229), (float)(this.guiTop + 125), 91, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
        this.cFontLabel = ModernGui.getCustomFont("georamaMedium", Integer.valueOf(26));
        this.cFontCondtionsRewards = ModernGui.getCustomFont("georamaMedium", Integer.valueOf(28));
        this.cFontLabelHeader = ModernGui.getCustomFont("georamaMedium", Integer.valueOf(25));
        this.cFontSemiBold28 = ModernGui.getCustomFont("georamaSemiBold", Integer.valueOf(28));
        int index = 0;
        Iterator var2;
        String condition;
        CustomInputFieldGUI guiTextField;
        String data;

        for (var2 = this.availableConditions.iterator(); var2.hasNext(); ++index)
        {
            condition = (String)var2.next();
            guiTextField = new CustomInputFieldGUI(this.guiLeft + 106, this.guiTop + 125 + index * 12, 20, 12, "georamaMedium", 25);
            data = "0";

            if (this.conditionsInput.containsKey(condition))
            {
                data = ((GuiTextField)this.conditionsInput.get(condition)).getText();
            }

            guiTextField.setMaxStringLength(5);
            guiTextField.setText(data);
            this.conditionsInput.put(condition, guiTextField);
        }

        index = 0;

        for (var2 = this.availableRewards.iterator(); var2.hasNext(); ++index)
        {
            condition = (String)var2.next();
            guiTextField = new CustomInputFieldGUI(this.guiLeft + 199, this.guiTop + 125 + index * 12, 20, 12, "georamaMedium", 25);
            data = "0";

            if (this.rewardsInput.containsKey(condition))
            {
                data = ((GuiTextField)this.rewardsInput.get(condition)).getText();
            }

            guiTextField.setMaxStringLength(6);
            guiTextField.setText(data);
            this.rewardsInput.put(condition, guiTextField);
        }

        index = 0;

        for (var2 = this.agreementsAvailableConditions.iterator(); var2.hasNext(); ++index)
        {
            condition = (String)var2.next();
            guiTextField = new CustomInputFieldGUI(this.guiLeft + 321, this.guiTop + 125 + index * 11, 20, 12, "georamaMedium", 25);
            data = "0";

            if (this.agreement_myConditionsInput.containsKey(condition))
            {
                data = ((GuiTextField)this.agreement_myConditionsInput.get(condition)).getText();
            }

            guiTextField.setMaxStringLength(5);
            guiTextField.setText(data);
            this.agreement_myConditionsInput.put(condition, guiTextField);
            guiTextField = new CustomInputFieldGUI(this.guiLeft + 420, this.guiTop + 125 + index * 11, 20, 12, "georamaMedium", 25);
            data = "0";

            if (this.agreement_otherConditionsInput.containsKey(condition))
            {
                data = ((GuiTextField)this.agreement_otherConditionsInput.get(condition)).getText();
            }

            guiTextField.setMaxStringLength(5);
            guiTextField.setText(data);
            this.agreement_otherConditionsInput.put(condition, guiTextField);
        }

        this.agreement_form_duration = new CustomInputFieldGUI(this.guiLeft + 361, this.guiTop + 182, 20, 12, "georamaMedium", 25);
        this.agreement_form_duration.setMaxStringLength(3);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTick)
    {
        this.drawDefaultBackground();
        tooltipToDraw.clear();
        this.hoveredAction = "";
        this.hoveredCountry = "";
        ClientEventHandler.STYLE.bindTexture("faction_global_2");
        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 30), (float)(this.guiTop + 0), (float)(0 * GUI_SCALE), (float)(0 * GUI_SCALE), (this.xSize - 30) * GUI_SCALE, this.ySize * GUI_SCALE, this.xSize - 30, this.ySize, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);

        if (loaded)
        {
            if (factionsWarInfos.size() > 0 && currentWarIndex != -1)
            {
                if (FactionGUI.factionInfos.get("banners") != null && ((Map)FactionGUI.factionInfos.get("banners")).containsKey("wars"))
                {
                    ModernGui.bindRemoteTexture((String)((Map)FactionGUI.factionInfos.get("banners")).get("wars"));
                }
                else
                {
                    ModernGui.bindRemoteTexture("https://static.nationsglory.fr/N33y222_3N.png");
                }

                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 30 + 154), (float)(this.guiTop + 0), 0.0F, 0.0F, 279 * GUI_SCALE, 110 * GUI_SCALE, 279, 89, (float)(279 * GUI_SCALE), (float)(110 * GUI_SCALE), false);
                ClientEventHandler.STYLE.bindTexture("faction_war");
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 30), (float)(this.guiTop + 0), (float)(0 * GUI_SCALE), (float)(282 * GUI_SCALE), 433 * GUI_SCALE, 89 * GUI_SCALE, 433, 89, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                TreeMap currentWar = (TreeMap)factionsWarInfos.get(currentWarIndex);
                LinkedTreeMap currentWarRequest = (LinkedTreeMap)((TreeMap)factionsWarInfos.get(currentWarIndex)).get("warRequest");
                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.war.title_against") + " " + (String)currentWar.get("name"), (float)(this.guiLeft + 43), (float)(this.guiTop + 16), 16777215, 0.75F, "left", false, "georamaSemiBold", 32);
                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.war.label.war_status") + ":", (float)(this.guiLeft + 43), (float)(this.guiTop + 32), ((Integer)FactionGUI.textColor.get(FactionGUI.factionInfos.get("actualRelation"))).intValue(), 0.5F, "left", false, "georamaMedium", 25);
                String agreement;

                if (currentWar.get("status").equals("in_progress") && currentWar.get("startTime") != null)
                {
                    Date buttonOffsetX = new Date(((Double)currentWar.get("startTime")).longValue());
                    SimpleDateFormat index = new SimpleDateFormat("dd/MM/yyyy");
                    agreement = index.format(buttonOffsetX);
                    ModernGui.drawScaledStringCustomFont(I18n.getString("faction.war.status.in_progress") + " " + agreement, (float)(this.guiLeft + 43), (float)(this.guiTop + 40), 10395075, 0.5F, "left", false, "georamaMedium", 25);
                }
                else
                {
                    ModernGui.drawScaledStringCustomFont(I18n.getString("faction.war.status." + currentWar.get("status")).replace("#factionATT#", (String)currentWar.get("factionATTName")).replace("#factionDEF#", (String)currentWar.get("factionDEFName")).replace("#sender#", (String)currentWar.get("sender")), (float)(this.guiLeft + 43), (float)(this.guiTop + 40), 10395075, 0.5F, "left", false, "georamaMedium", 25);
                }

                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.enemy.reason") + ":", (float)(this.guiLeft + 43), (float)(this.guiTop + 48), ((Integer)FactionGUI.textColor.get(FactionGUI.factionInfos.get("actualRelation"))).intValue(), 0.5F, "left", false, "georamaMedium", 25);
                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.enemy.reason." + currentWarRequest.get("reason")) + (currentWarRequest.get("playerTarget") != null && !currentWarRequest.get("playerTarget").equals("") ? " (" + ((String)currentWarRequest.get("playerTarget")).split(" ")[1] + ")" : ""), (float)(this.guiLeft + 45 + (int)this.cFontLabelHeader.getStringWidth(I18n.getString("faction.enemy.reason") + ":") / 2), (float)(this.guiTop + 48), 10395075, 0.5F, "left", false, "georamaMedium", 25);
                ClientProxy.loadCountryFlag((String)currentWar.get("name"));
                ClientEventHandler.STYLE.bindTexture("faction_war");
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 292), (float)(this.guiTop - 15), (float)(0 * GUI_SCALE), (float)(173 * GUI_SCALE), 130 * GUI_SCALE, 104 * GUI_SCALE, 130, 104, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);

                if (ClientProxy.flagsTexture.containsKey((String)currentWar.get("name")))
                {
                    GL11.glBindTexture(GL11.GL_TEXTURE_2D, ((DynamicTexture)ClientProxy.flagsTexture.get((String)currentWar.get("name"))).getGlTextureId());
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 261), (float)(this.guiTop + 13), 0.0F, 0.0F, 156, 78, 59, 36, 156.0F, 78.0F, false);
                }

                int var21 = 43;

                if (currentWarRequest.get("status").equals("in_progress"))
                {
                    ClientEventHandler.STYLE.bindTexture("faction_war");

                    if (mouseX >= this.guiLeft + var21 && mouseX <= this.guiLeft + var21 + 55 && mouseY >= this.guiTop + 58 && mouseY <= this.guiTop + 58 + 13)
                    {
                        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + var21), (float)(this.guiTop + 58), (float)(285 * GUI_SCALE), (float)(160 * GUI_SCALE), 55 * GUI_SCALE, 13 * GUI_SCALE, 55, 13, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                        this.hoveredAction = "open_accords";
                    }
                    else
                    {
                        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + var21), (float)(this.guiTop + 58), (float)(285 * GUI_SCALE), (float)(142 * GUI_SCALE), 55 * GUI_SCALE, 13 * GUI_SCALE, 55, 13, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                    }

                    ModernGui.drawScaledStringCustomFont(I18n.getString("faction.war.warAgreementBtn"), (float)(this.guiLeft + var21 + 28), (float)(this.guiTop + 61), 2826561, 0.5F, "center", false, "georamaSemiBold", 28);
                    var21 += 61;
                }

                if (!((String)currentWarRequest.get("linkForum")).isEmpty())
                {
                    ClientEventHandler.STYLE.bindTexture("faction_war");

                    if (mouseX >= this.guiLeft + var21 && mouseX <= this.guiLeft + var21 + 83 && mouseY >= this.guiTop + 58 && mouseY <= this.guiTop + 58 + 13)
                    {
                        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + var21), (float)(this.guiTop + 58), (float)(134 * GUI_SCALE), (float)(118 * GUI_SCALE), 83 * GUI_SCALE, 13 * GUI_SCALE, 83, 13, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                        ModernGui.drawScaledStringCustomFont(I18n.getString("faction.enemy.linkForum"), (float)(this.guiLeft + var21 + 42), (float)(this.guiTop + 61), 2826561, 0.5F, "center", false, "georamaSemiBold", 28);
                        this.hoveredAction = "open_forum";
                    }
                    else
                    {
                        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + var21), (float)(this.guiTop + 58), (float)(134 * GUI_SCALE), (float)(((Integer)btnForumY.get(FactionGUI.factionInfos.get("actualRelation"))).intValue() * GUI_SCALE), 83 * GUI_SCALE, 13 * GUI_SCALE, 83, 13, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                        ModernGui.drawScaledStringCustomFont(I18n.getString("faction.enemy.linkForum"), (float)(this.guiLeft + var21 + 42), (float)(this.guiTop + 61), 16777215, 0.5F, "center", false, "georamaSemiBold", 28);
                    }

                    var21 += 89;
                }

                if (currentWarRequest.get("status").equals("in_progress") && ((Boolean)FactionGUI.factionInfos.get("isInCountry")).booleanValue() && FactionGUI.hasPermissions("wars"))
                {
                    ClientEventHandler.STYLE.bindTexture("faction_war");

                    if (mouseX >= this.guiLeft + var21 && mouseX <= this.guiLeft + var21 + 83 && mouseY >= this.guiTop + 58 && mouseY <= this.guiTop + 58 + 13)
                    {
                        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + var21), (float)(this.guiTop + 58), (float)(435 * GUI_SCALE), (float)(159 * GUI_SCALE), 56 * GUI_SCALE, 13 * GUI_SCALE, 56, 13, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                        ModernGui.drawScaledStringCustomFont(I18n.getString("faction.enemy.surrender"), (float)(this.guiLeft + var21 + 28), (float)(this.guiTop + 61), 2234425, 0.5F, "center", false, "georamaSemiBold", 28);
                        this.hoveredAction = "surrender";
                    }
                    else
                    {
                        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + var21), (float)(this.guiTop + 58), (float)(435 * GUI_SCALE), (float)(142 * GUI_SCALE), 56 * GUI_SCALE, 13 * GUI_SCALE, 56, 13, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                        ModernGui.drawScaledStringCustomFont(I18n.getString("faction.enemy.surrender"), (float)(this.guiLeft + var21 + 28), (float)(this.guiTop + 61), 15017020, 0.5F, "center", false, "georamaSemiBold", 28);
                    }

                    var21 += 62;
                }

                if (factionsWarInfos.size() > 6)
                {
                    ClientEventHandler.STYLE.bindTexture("faction_war");

                    if (this.tabsOffset > 0)
                    {
                        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 34), (float)(this.guiTop + 78), (float)(247 * GUI_SCALE), (float)(141 * GUI_SCALE), 5 * GUI_SCALE, 9 * GUI_SCALE, 5, 9, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);

                        if (mouseX >= this.guiLeft + 34 && mouseX <= this.guiLeft + 34 + 5 && mouseY >= this.guiTop + 78 && mouseY <= this.guiTop + 78 + 9)
                        {
                            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 34), (float)(this.guiTop + 78), (float)(247 * GUI_SCALE), (float)(151 * GUI_SCALE), 5 * GUI_SCALE, 9 * GUI_SCALE, 5, 9, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                            this.hoveredAction = "previousTab";
                        }
                    }

                    if (factionsWarInfos.size() > this.tabsOffset + 6)
                    {
                        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 441), (float)(this.guiTop + 78), (float)(254 * GUI_SCALE), (float)(141 * GUI_SCALE), 5 * GUI_SCALE, 9 * GUI_SCALE, 5, 9, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);

                        if (mouseX >= this.guiLeft + 441 && mouseX <= this.guiLeft + 441 + 5 && mouseY >= this.guiTop + 78 && mouseY <= this.guiTop + 78 + 9)
                        {
                            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 441), (float)(this.guiTop + 78), (float)(254 * GUI_SCALE), (float)(151 * GUI_SCALE), 5 * GUI_SCALE, 9 * GUI_SCALE, 5, 9, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                            this.hoveredAction = "nextTab";
                        }
                    }
                }

                int var22 = 0;
                int var23;

                for (var23 = this.tabsOffset; var23 < Math.min(6 + this.tabsOffset, factionsWarInfos.size()); ++var23)
                {
                    ClientEventHandler.STYLE.bindTexture("faction_war");
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 43 + var22 * 66), (float)(this.guiTop + 77), (float)((((TreeMap)factionsWarInfos.get(var23)).get("name").equals(((TreeMap)factionsWarInfos.get(currentWarIndex)).get("name")) ? 0 : 69) * GUI_SCALE), (float)(131 * GUI_SCALE), 65 * GUI_SCALE, 12 * GUI_SCALE, 65, 12, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);

                    if ((((TreeMap)factionsWarInfos.get(var23)).get("status").equals("waiting_conditions_att") || ((TreeMap)factionsWarInfos.get(var23)).get("status").equals("waiting_conditions_att_second")) && ((LinkedTreeMap)((TreeMap)factionsWarInfos.get(var23)).get("warRequest")).get("factionATT").equals(((LinkedTreeMap)((TreeMap)factionsWarInfos.get(var23)).get("warRequest")).get("playerFaction")) && ((Boolean)((LinkedTreeMap)((TreeMap)factionsWarInfos.get(var23)).get("warRequest")).get("hasWarPermInOwnCountry")).booleanValue() || ((TreeMap)factionsWarInfos.get(var23)).get("status").equals("waiting_conditions_def") && ((LinkedTreeMap)((TreeMap)factionsWarInfos.get(var23)).get("warRequest")).get("factionDEF").equals(((LinkedTreeMap)((TreeMap)factionsWarInfos.get(var23)).get("warRequest")).get("playerFaction")) && ((Boolean)((LinkedTreeMap)((TreeMap)factionsWarInfos.get(var23)).get("warRequest")).get("hasWarPermInOwnCountry")).booleanValue())
                    {
                        ClientEventHandler.STYLE.bindTexture("faction_global");
                        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 41 + var22 * 66 + 61), (float)(this.guiTop + 74), (float)(234 * GUI_SCALE), (float)(52 * GUI_SCALE), 6 * GUI_SCALE, 6 * GUI_SCALE, 6, 6, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                    }

                    ModernGui.drawScaledStringCustomFont(((TreeMap)factionsWarInfos.get(var23)).get("name") + "", (float)(this.guiLeft + 43 + var22 * 66 + 32), (float)(this.guiTop + 80), ((TreeMap)factionsWarInfos.get(var23)).get("name").equals(((TreeMap)factionsWarInfos.get(currentWarIndex)).get("name")) ? ((Integer)FactionGUI.textColor.get(FactionGUI.factionInfos.get("actualRelation"))).intValue() : 14803951, 0.5F, "center", false, "georamaSemiBold", 25);

                    if (mouseX >= this.guiLeft + 43 + var22 * 66 && mouseX <= this.guiLeft + 43 + var22 * 66 + 64 && mouseY >= this.guiTop + 77 && mouseY <= this.guiTop + 77 + 12)
                    {
                        this.hoveredAction = "switch_war#" + var23;
                    }

                    ++var22;
                }

                int dateFormated;
                int conditionsOTHER;
                String agreementCondition;
                String delay;
                Iterator var25;
                String var26;
                int var30;
                String var34;
                String var37;
                String var38;
                String var40;

                if (!this.agreementMode)
                {
                    SimpleDateFormat agreementType;
                    String date;
                    byte simpleDateFormat;
                    Date var24;

                    if (!currentWar.get("status").equals("waiting_validation") && !currentWar.get("status").equals("refused") && !currentWar.get("status").equals("cancelled") && (currentWar.get("status").equals("in_progress") || currentWar.get("status").equals("finished") || !currentWar.get("status").equals("waiting_conditions_att") || currentWarRequest.get("factionATT").equals(currentWarRequest.get("playerFaction")) && ((Boolean)currentWarRequest.get("hasWarPermInOwnCountry")).booleanValue()))
                    {
                        int var29;
                        int var43;

                        if (!currentWar.get("status").equals("waiting_conditions_att") && !currentWar.get("status").equals("waiting_conditions_def") && !currentWar.get("status").equals("waiting_conditions_att_second"))
                        {
                            if (currentWar.get("status").equals("in_progress") || currentWar.get("status").equals("finished"))
                            {
                                ClientEventHandler.STYLE.bindTexture("faction_war");
                                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 43), (float)(this.guiTop + 96), (float)(0 * GUI_SCALE), (float)(0 * GUI_SCALE), 103 * GUI_SCALE, 93 * GUI_SCALE, 103, 93, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.war.missile_history"), (float)(this.guiLeft + 48), (float)(this.guiTop + 100), 16514302, 0.5F, "left", false, "georamaSemiBold", 28);
                                ClientEventHandler.STYLE.bindTexture("faction_war");
                                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 138), (float)(this.guiTop + 113), (float)(456 * GUI_SCALE), (float)(0 * GUI_SCALE), 2 * GUI_SCALE, 69 * GUI_SCALE, 2, 69, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                                Float offsetY;
                                String label;
                                int var55;

                                if (currentWar.containsKey("missiles") && ((ArrayList)currentWar.get("missiles")).size() > 0)
                                {
                                    GUIUtils.startGLScissor(this.guiLeft + 43, this.guiTop + 113, 95, 69);

                                    for (var23 = 0; var23 < ((ArrayList)currentWar.get("missiles")).size(); ++var23)
                                    {
                                        var26 = (String)((ArrayList)currentWar.get("missiles")).get(var23);
                                        long var36 = Long.parseLong(var26.split("#")[0]);
                                        var34 = var26.split("#")[1];
                                        var37 = var26.split("#")[2];
                                        var40 = var26.split("#")[3];
                                        var43 = Integer.parseInt(var26.split("#")[4]);
                                        delay = var26.split("#")[5];
                                        var55 = this.guiLeft + 43;
                                        offsetY = Float.valueOf((float)(this.guiTop + 113 + 3 + var23 * 12) + this.getSlideMissiles());
                                        ClientProxy.loadCountryFlag(delay);

                                        if (ClientProxy.flagsTexture.containsKey(delay))
                                        {
                                            GL11.glBindTexture(GL11.GL_TEXTURE_2D, ((DynamicTexture)ClientProxy.flagsTexture.get(delay)).getGlTextureId());
                                            ModernGui.drawScaledCustomSizeModalRect((float)(var55 + 5), (float)offsetY.intValue(), 0.0F, 0.0F, 156, 78, 12, 7, 156.0F, 78.0F, false);
                                        }

                                        ModernGui.drawScaledStringCustomFont(var37.split(" ")[0], (float)(var55 + 20), (float)(offsetY.intValue() + 0), 16514302, 0.5F, "left", false, "georamaMedium", 26);
                                        label = ModernGui.formatDelayTime(Long.valueOf(var36));
                                        ModernGui.drawScaledStringCustomFont(label, (float)(var55 + 92), (float)(offsetY.intValue() + 0), 10395075, 0.5F, "right", false, "georamaMedium", 26);
                                        ClientEventHandler.STYLE.bindTexture("faction_global");
                                        ModernGui.drawScaledCustomSizeModalRect((float)(var55 + 20 + (int)this.cFontLabel.getStringWidth(var37.split(" ")[0]) / 2 + 2), (float)(offsetY.intValue() - 1), (float)(94 * GUI_SCALE), (float)(5 * GUI_SCALE), 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);

                                        if (mouseX >= this.guiLeft + 43 && mouseX <= this.guiLeft + 43 + 95 && mouseY >= this.guiTop + 113 && mouseY <= this.guiTop + 113 + 69 && mouseX >= var55 + 20 + (int)this.cFontLabel.getStringWidth(var37.split(" ")[0]) / 2 + 2 && mouseX <= var55 + 20 + (int)this.cFontLabel.getStringWidth(var37.split(" ")[0]) / 2 + 2 + 8 && mouseY >= offsetY.intValue() - 1 && mouseY <= offsetY.intValue() - 1 + 8)
                                        {
                                            tooltipToDraw.add(I18n.getString("faction.war.missile_firedby") + " " + var34);
                                        }
                                    }

                                    GUIUtils.endGLScissor();

                                    if (mouseX >= this.guiLeft + 43 && mouseX <= this.guiLeft + 43 + 103 && mouseY >= this.guiTop + 96 && mouseY <= this.guiTop + 96 + 93)
                                    {
                                        this.scrollBarMissiles.draw(mouseX, mouseY);
                                    }
                                }

                                ClientEventHandler.STYLE.bindTexture("faction_war");
                                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 152), (float)(this.guiTop + 96), (float)(0 * GUI_SCALE), (float)(0 * GUI_SCALE), 103 * GUI_SCALE, 93 * GUI_SCALE, 103, 93, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.war.assault_history"), (float)(this.guiLeft + 157), (float)(this.guiTop + 100), 16514302, 0.5F, "left", false, "georamaSemiBold", 28);
                                ClientEventHandler.STYLE.bindTexture("faction_war");
                                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 247), (float)(this.guiTop + 113), (float)(456 * GUI_SCALE), (float)(0 * GUI_SCALE), 2 * GUI_SCALE, 69 * GUI_SCALE, 2, 69, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                                String valueType;
                                HashMap var32;
                                int var61;

                                if (this.cachedAssaults.size() == 0 && currentWar.containsKey("assaults") && ((ArrayList)currentWar.get("assaults")).size() > 0)
                                {
                                    for (var23 = 0; var23 < ((ArrayList)currentWar.get("assaults")).size(); ++var23)
                                    {
                                        var32 = new HashMap();
                                        date = (String)((ArrayList)currentWar.get("assaults")).get(var23);
                                        long var44 = Long.parseLong(date.split("#")[0]);
                                        boolean var39 = date.split("#")[1].contains("win");
                                        var40 = date.split("#")[1].split(",").length > 1 ? date.split("#")[1].split(",")[1] : "25";
                                        ArrayList var51 = new ArrayList();
                                        String[] var53 = date.split("#")[2].split(",");
                                        var55 = var53.length;

                                        for (var61 = 0; var61 < var55; ++var61)
                                        {
                                            label = var53[var61];

                                            if (!label.contains("Helper"))
                                            {
                                                var51.add(label);
                                            }
                                        }

                                        ArrayList var56 = new ArrayList();
                                        String[] var58 = date.split("#")[3].split(",");
                                        var61 = var58.length;

                                        for (int var62 = 0; var62 < var61; ++var62)
                                        {
                                            valueType = var58[var62];

                                            if (!valueType.contains("Helper"))
                                            {
                                                var56.add(valueType);
                                            }
                                        }

                                        var32.put("time", Long.valueOf(var44));
                                        var32.put("date", ModernGui.formatDelayTime(Long.valueOf(var44)));
                                        var32.put("win", Boolean.valueOf(var39));
                                        var32.put("mmr", var40);
                                        var32.put("winnerHelpers", var51);
                                        var32.put("looserHelpers", var56);

                                        if (date.split("#").length > 4)
                                        {
                                            var32.put("rollback", Boolean.valueOf(true));
                                        }

                                        this.cachedAssaults.add(var32);
                                    }
                                }

                                if (this.cachedAssaults.size() > 0)
                                {
                                    GUIUtils.startGLScissor(this.guiLeft + 152, this.guiTop + 113, 95, 69);

                                    for (var23 = 0; var23 < this.cachedAssaults.size(); ++var23)
                                    {
                                        var32 = (HashMap)this.cachedAssaults.get(var23);
                                        var29 = this.guiLeft + 152;
                                        Float var46 = Float.valueOf((float)(this.guiTop + 113 + 3 + var23 * 12) + this.getSlideAssaults());
                                        ModernGui.drawScaledStringCustomFont((((Boolean)var32.get("win")).booleanValue() ? "+" : "-") + var32.get("mmr"), (float)(var29 + 6), (float)(var46.intValue() + 0), ((Boolean)var32.get("win")).booleanValue() ? 8046943 : 15017020, 0.5F, "left", false, "georamaMedium", 26);
                                        var34 = ((Boolean)var32.get("win")).booleanValue() ? I18n.getString("faction.common.victory") : I18n.getString("faction.common.defeat");
                                        ModernGui.drawScaledStringCustomFont(var34, (float)(var29 + 22), (float)(var46.intValue() + 0), 14342893, 0.5F, "left", false, "georamaMedium", 26);
                                        ModernGui.drawScaledStringCustomFont((String)var32.get("date"), (float)(var29 + 92), (float)(var46.intValue() + 0), 10395075, 0.5F, "right", false, "georamaMedium", 26);
                                        ClientEventHandler.STYLE.bindTexture("faction_global");
                                        ModernGui.drawScaledCustomSizeModalRect((float)(var29 + 20 + (int)this.cFontLabel.getStringWidth(var34) / 2 + 4), (float)(var46.intValue() - 1), (float)(94 * GUI_SCALE), (float)(5 * GUI_SCALE), 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);

                                        if (mouseX >= this.guiLeft + 152 && mouseX <= this.guiLeft + 152 + 95 && mouseY >= this.guiTop + 113 && mouseY <= this.guiTop + 113 + 69 && mouseX >= var29 + 20 + (int)this.cFontLabel.getStringWidth(var34) / 2 + 4 && mouseX <= var29 + 20 + (int)this.cFontLabel.getStringWidth(var34) / 2 + 4 + 8 && mouseY >= var46.intValue() - 1 && mouseY <= var46.intValue() - 1 + 8)
                                        {
                                            tooltipToDraw.add("\u00a72" + I18n.getString("faction.war.assaults.helpers.ally") + ":");
                                            Iterator var41 = (((Boolean)var32.get("win")).booleanValue() ? (List)var32.get("winnerHelpers") : (List)var32.get("looserHelpers")).iterator();

                                            while (var41.hasNext())
                                            {
                                                var40 = (String)var41.next();
                                                tooltipToDraw.add("\u00a77- " + var40);
                                            }

                                            tooltipToDraw.add("");
                                            tooltipToDraw.add("\u00a7c" + I18n.getString("faction.war.assaults.helpers.ennemy") + ":");
                                            var41 = (((Boolean)var32.get("win")).booleanValue() ? (List)var32.get("looserHelpers") : (List)var32.get("winnerHelpers")).iterator();

                                            while (var41.hasNext())
                                            {
                                                var40 = (String)var41.next();
                                                tooltipToDraw.add("\u00a77- " + var40);
                                            }
                                        }

                                        if (var32.containsKey("rollback"))
                                        {
                                            ClientEventHandler.STYLE.bindTexture("faction_global");
                                            ModernGui.drawScaledCustomSizeModalRect((float)(var29 + 20 + (int)this.cFontLabel.getStringWidth(var34) / 2 + 4 + 5 + 3), (float)(var46.intValue() - 1), (float)(102 * GUI_SCALE), (float)(5 * GUI_SCALE), 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);

                                            if (mouseX >= var29 + 20 + (int)this.cFontLabel.getStringWidth(var34) / 2 + 4 + 5 + 3 && mouseX <= var29 + 20 + (int)this.cFontLabel.getStringWidth(var34) / 2 + 4 + 5 + 3 + 8 && mouseY >= var46.intValue() - 1 && mouseY <= var46.intValue() - 1 + 8)
                                            {
                                                tooltipToDraw.add("\u00a7a" + I18n.getString("faction.war.assaults.rollback"));
                                                this.hoveredAction = "rollback_assault#" + var32.get("time");
                                                System.out.println(this.hoveredAction);
                                            }
                                        }
                                    }

                                    GUIUtils.endGLScissor();

                                    if (mouseX >= this.guiLeft + 152 && mouseX <= this.guiLeft + 152 + 103 && mouseY >= this.guiTop + 96 && mouseY <= this.guiTop + 96 + 93)
                                    {
                                        this.scrollBarAssaults.draw(mouseX, mouseY);
                                    }
                                }

                                ClientEventHandler.STYLE.bindTexture("faction_war");
                                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 271), (float)(this.guiTop + 96), (float)(248 * GUI_SCALE), (float)(0 * GUI_SCALE), 179 * GUI_SCALE, 63 * GUI_SCALE, 179, 63, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);

                                if (currentWarRequest.get("status").equals("finished"))
                                {
                                    ClientEventHandler.STYLE.bindTexture("faction_war");

                                    if (FactionGUI.factionInfos.get("id").equals(currentWarRequest.get("factionATTId")))
                                    {
                                        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 271), (float)(this.guiTop + 96), (float)(440 * GUI_SCALE), (float)((currentWarRequest.get("factionATTId").equals(currentWarRequest.get("winner")) ? 371 : 282) * GUI_SCALE), 45 * GUI_SCALE, 63 * GUI_SCALE, 45, 63, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                                    }

                                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 276), (float)(this.guiTop + 124), (float)(248 * GUI_SCALE), (float)(97 * GUI_SCALE), 35 * GUI_SCALE, 32 * GUI_SCALE, 35, 32, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 286), (float)(this.guiTop + 128), (float)((currentWarRequest.get("factionATTId").equals(currentWarRequest.get("winner")) ? 305 : 285) * GUI_SCALE), (float)(184 * GUI_SCALE), 18 * GUI_SCALE, 16 * GUI_SCALE, 18, 16, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                                    ModernGui.drawScaledStringCustomFont(currentWarRequest.get("factionATTId").equals(currentWarRequest.get("winner")) ? I18n.getString("faction.common.victory") : I18n.getString("faction.common.defeat"), (float)(this.guiLeft + 277 + 17), (float)(this.guiTop + 147), 10395075, 0.5F, "center", false, "georamaMedium", 22);
                                    ClientEventHandler.STYLE.bindTexture("faction_war");

                                    if (FactionGUI.factionInfos.get("id").equals(currentWarRequest.get("factionDEFId")))
                                    {
                                        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 405), (float)(this.guiTop + 96), (float)(440 * GUI_SCALE), (float)((currentWarRequest.get("factionDEFId").equals(currentWarRequest.get("winner")) ? 371 : 282) * GUI_SCALE), 45 * GUI_SCALE, 63 * GUI_SCALE, 45, 63, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                                    }

                                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 410), (float)(this.guiTop + 124), (float)(248 * GUI_SCALE), (float)(97 * GUI_SCALE), 35 * GUI_SCALE, 32 * GUI_SCALE, 35, 32, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 420), (float)(this.guiTop + 128), (float)((currentWarRequest.get("factionDEFId").equals(currentWarRequest.get("winner")) ? 305 : 285) * GUI_SCALE), (float)(184 * GUI_SCALE), 18 * GUI_SCALE, 16 * GUI_SCALE, 18, 16, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                                    ModernGui.drawScaledStringCustomFont(currentWarRequest.get("factionDEFId").equals(currentWarRequest.get("winner")) ? I18n.getString("faction.common.victory") : I18n.getString("faction.common.defeat"), (float)(this.guiLeft + 411 + 17), (float)(this.guiTop + 147), 10395075, 0.5F, "center", false, "georamaMedium", 22);
                                }

                                ClientProxy.loadCountryFlag((String)currentWar.get("factionATTName"));
                                ClientProxy.loadCountryFlag((String)currentWar.get("factionDEFName"));

                                if (ClientProxy.flagsTexture.containsKey(currentWar.get("factionATTName")))
                                {
                                    GL11.glBindTexture(GL11.GL_TEXTURE_2D, ((DynamicTexture)ClientProxy.flagsTexture.get(currentWar.get("factionATTName"))).getGlTextureId());
                                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 275), (float)(this.guiTop + 101), 0.0F, 0.0F, 156, 78, 36, 21, 156.0F, 78.0F, false);
                                }

                                if (ClientProxy.flagsTexture.containsKey(currentWar.get("factionDEFName")))
                                {
                                    GL11.glBindTexture(GL11.GL_TEXTURE_2D, ((DynamicTexture)ClientProxy.flagsTexture.get(currentWar.get("factionDEFName"))).getGlTextureId());
                                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 410), (float)(this.guiTop + 101), 0.0F, 0.0F, 156, 78, 36, 21, 156.0F, 78.0F, false);
                                }

                                ClientEventHandler.STYLE.bindTexture("faction_war");
                                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 318), (float)(this.guiTop + 109), (float)(132 * GUI_SCALE), (float)(0 * GUI_SCALE), 86 * GUI_SCALE, 6 * GUI_SCALE, 86, 6, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                                agreement = "att";
                                float var35 = 0.0F;
                                date = "";

                                if (((Double)currentWarRequest.get("progressATT")).doubleValue() != 1.0D && (currentWarRequest.get("winner") == null || !currentWarRequest.get("winner").equals(currentWarRequest.get("factionATTId"))))
                                {
                                    if (((Double)currentWarRequest.get("progressATT")).doubleValue() != 1.0D && (currentWarRequest.get("winner") == null || !currentWarRequest.get("winner").equals(currentWarRequest.get("factionDEFId"))))
                                    {
                                        if (((Double)currentWarRequest.get("progressATT")).doubleValue() >= ((Double)currentWarRequest.get("progressDEF")).doubleValue())
                                        {
                                            var35 = (float)(((Double)currentWarRequest.get("progressATT")).doubleValue() - ((Double)currentWarRequest.get("progressDEF")).doubleValue());
                                            agreement = "att";
                                        }
                                        else
                                        {
                                            var35 = (float)(((Double)currentWarRequest.get("progressDEF")).doubleValue() - ((Double)currentWarRequest.get("progressATT")).doubleValue());
                                            agreement = "def";
                                        }
                                    }
                                    else
                                    {
                                        date = (String)currentWarRequest.get("factionDEF");
                                        var35 = -1.0F;
                                    }
                                }
                                else
                                {
                                    date = (String)currentWarRequest.get("factionATT");
                                    var35 = 1.0F;
                                }

                                if (agreement.equals("att"))
                                {
                                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 318), (float)(this.guiTop + 109), (float)(132 * GUI_SCALE), (float)(((Integer)progressBarY.get(FactionGUI.factionInfos.get("actualRelation"))).intValue() * GUI_SCALE), (43 + (int)(43.0F * var35)) * GUI_SCALE, 6 * GUI_SCALE, 43 + (int)(43.0F * var35), 6, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                                }
                                else if (agreement.equals("def"))
                                {
                                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 318 + 86 - (43 + (int)(43.0F * var35))), (float)(this.guiTop + 109), (float)((218 - (43 + (int)(43.0F * var35))) * GUI_SCALE), (float)(((Integer)progressBarY.get(FactionGUI.factionInfos.get("actualRelation"))).intValue() * GUI_SCALE), (43 + (int)(43.0F * var35)) * GUI_SCALE, 6 * GUI_SCALE, 43 + (int)(43.0F * var35), 6, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                                }

                                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.common.victory"), (float)(this.guiLeft + 361), (float)(this.guiTop + 100), 10395075, 0.5F, "center", false, "georamaSemiBold", 24);
                                ModernGui.drawScaledStringCustomFont(String.format("%.0f", new Object[] {Double.valueOf(((Double)currentWarRequest.get("progressATT")).doubleValue() * 100.0D)}) + "%", (float)(this.guiLeft + 318), (float)(this.guiTop + 100), 16514302, 0.5F, "left", false, "georamaSemiBold", 24);
                                ModernGui.drawScaledStringCustomFont(String.format("%.0f", new Object[] {Double.valueOf(((Double)currentWarRequest.get("progressDEF")).doubleValue() * 100.0D)}) + "%", (float)(this.guiLeft + 318 + 86), (float)(this.guiTop + 100), 16514302, 0.5F, "right", false, "georamaSemiBold", 24);
                                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.enemy.inactivity"), (float)(this.guiLeft + 361), (float)(this.guiTop + 118), 10395075, 0.5F, "center", false, "georamaSemiBold", 24);
                                ModernGui.drawScaledStringCustomFont((currentWarRequest.get("inactivity_ATT") != null ? currentWarRequest.get("inactivity_ATT") : Integer.valueOf(0)) + "%", (float)(this.guiLeft + 318), (float)(this.guiTop + 118), 16514302, 0.5F, "left", false, "georamaSemiBold", 24);
                                ModernGui.drawScaledStringCustomFont((currentWarRequest.get("inactivity_DEF") != null ? currentWarRequest.get("inactivity_DEF") : Integer.valueOf(0)) + "%", (float)(this.guiLeft + 318 + 86), (float)(this.guiTop + 118), 16514302, 0.5F, "right", false, "georamaSemiBold", 24);
                                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.war.label.missile_points"), (float)(this.guiLeft + 361), (float)(this.guiTop + 128), 10395075, 0.5F, "center", false, "georamaMedium", 22);
                                ModernGui.drawScaledStringCustomFont(String.format("%.0f", new Object[] {(Double)currentWar.get("totalMissilePointsAtt")}), (float)(this.guiLeft + 318), (float)(this.guiTop + 128), 16514302, 0.5F, "left", false, "georamaMedium", 22);
                                ModernGui.drawScaledStringCustomFont(String.format("%.0f", new Object[] {(Double)currentWar.get("totalMissilePointsDef")}), (float)(this.guiLeft + 318 + 86), (float)(this.guiTop + 128), 16514302, 0.5F, "right", false, "georamaMedium", 22);
                                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.war.label.assaults_win"), (float)(this.guiLeft + 361), (float)(this.guiTop + 136), 10395075, 0.5F, "center", false, "georamaMedium", 22);
                                ModernGui.drawScaledStringCustomFont(String.format("%.0f", new Object[] {(Double)currentWar.get("totalAssaultsWinAtt")}), (float)(this.guiLeft + 318), (float)(this.guiTop + 136), 16514302, 0.5F, "left", false, "georamaMedium", 22);
                                ModernGui.drawScaledStringCustomFont(String.format("%.0f", new Object[] {(Double)currentWar.get("totalAssaultsWinDef")}), (float)(this.guiLeft + 318 + 86), (float)(this.guiTop + 136), 16514302, 0.5F, "right", false, "georamaMedium", 22);
                                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.war.label.kills"), (float)(this.guiLeft + 361), (float)(this.guiTop + 144), 10395075, 0.5F, "center", false, "georamaMedium", 22);
                                ModernGui.drawScaledStringCustomFont(String.format("%.0f", new Object[] {(Double)currentWar.get("totalKillsAtt")}), (float)(this.guiLeft + 318), (float)(this.guiTop + 144), 16514302, 0.5F, "left", false, "georamaMedium", 22);
                                ModernGui.drawScaledStringCustomFont(String.format("%.0f", new Object[] {(Double)currentWar.get("totalKillsDef")}), (float)(this.guiLeft + 318 + 86), (float)(this.guiTop + 144), 16514302, 0.5F, "right", false, "georamaMedium", 22);
                                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.war.label.mmr"), (float)(this.guiLeft + 361), (float)(this.guiTop + 152), 10395075, 0.5F, "center", false, "georamaMedium", 22);
                                ModernGui.drawScaledStringCustomFont(String.format("%.0f", new Object[] {(Double)currentWar.get("totalMMRAtt")}), (float)(this.guiLeft + 318), (float)(this.guiTop + 152), 16514302, 0.5F, "left", false, "georamaMedium", 22);
                                ModernGui.drawScaledStringCustomFont(String.format("%.0f", new Object[] {(Double)currentWar.get("totalMMRDef")}), (float)(this.guiLeft + 318 + 86), (float)(this.guiTop + 152), 16514302, 0.5F, "right", false, "georamaMedium", 22);

                                if (!currentWarRequest.get("status").equals("finished"))
                                {
                                    ClientEventHandler.STYLE.bindTexture("faction_war");
                                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 277), (float)(this.guiTop + 124), (float)(248 * GUI_SCALE), (float)(97 * GUI_SCALE), 35 * GUI_SCALE, 32 * GUI_SCALE, 35, 32, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 285), (float)(this.guiTop + 126), (float)((((Double)currentWar.get("remainingPointsATT")).doubleValue() > 0.0D ? 309 : 290) * GUI_SCALE), (float)(103 * GUI_SCALE), 18 * GUI_SCALE, 18 * GUI_SCALE, 18, 18, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                                    ModernGui.drawScaledStringCustomFont(String.format("%.0f", new Object[] {(Double)currentWar.get("remainingPointsATT")}) + " point(s)", (float)(this.guiLeft + 277 + 17), (float)(this.guiTop + 147), 10395075, 0.5F, "center", false, "georamaMedium", 22);
                                    ClientEventHandler.STYLE.bindTexture("faction_war");
                                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 409), (float)(this.guiTop + 124), (float)(248 * GUI_SCALE), (float)(97 * GUI_SCALE), 35 * GUI_SCALE, 32 * GUI_SCALE, 35, 32, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 417), (float)(this.guiTop + 126), (float)((((Double)currentWar.get("remainingPointsDEF")).doubleValue() > 0.0D ? 346 : 327) * GUI_SCALE), (float)(103 * GUI_SCALE), 18 * GUI_SCALE, 18 * GUI_SCALE, 18, 18, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                                    ModernGui.drawScaledStringCustomFont(String.format("%.0f", new Object[] {(Double)currentWar.get("remainingPointsDEF")}) + " point(s)", (float)(this.guiLeft + 409 + 17), (float)(this.guiTop + 147), 10395075, 0.5F, "center", false, "georamaMedium", 22);
                                }

                                ClientEventHandler.STYLE.bindTexture("faction_war");
                                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 271), (float)(this.guiTop + 162), (float)(248 * GUI_SCALE), (float)(0 * GUI_SCALE), 179 * GUI_SCALE, 63 * GUI_SCALE, 179, 63, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.enemy.contitions_with_mode").replaceAll("#mode#", I18n.getString("faction.enemy.conditionsType." + currentWarRequest.get("conditionsType"))), (float)(this.guiLeft + 278), (float)(this.guiTop + 166), 16514302, 0.5F, "left", false, "georamaSemiBold", 28);
                                ClientEventHandler.STYLE.bindTexture("faction_war");
                                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 445), (float)(this.guiTop + 168), (float)(460 * GUI_SCALE), (float)(0 * GUI_SCALE), 2 * GUI_SCALE, 52 * GUI_SCALE, 2, 52, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                                String[] var50;

                                if (currentWarRequest.get("conditions") != null)
                                {
                                    GUIUtils.startGLScissor(this.guiLeft + 271, this.guiTop + 177, 179, 44);
                                    var22 = 0;
                                    var50 = ((String)currentWarRequest.get("conditions")).split(",");
                                    dateFormated = var50.length;

                                    for (conditionsOTHER = 0; conditionsOTHER < dateFormated; ++conditionsOTHER)
                                    {
                                        var40 = var50[conditionsOTHER];

                                        if (!var40.isEmpty() && var40.split("#").length == 2)
                                        {
                                            agreementCondition = var40.split("#")[0];
                                            delay = var40.split("#")[1];
                                            var55 = this.guiLeft + 271;
                                            offsetY = Float.valueOf((float)(this.guiTop + 177 + var22 * 9) + this.getSlideConditions());
                                            label = (((LinkedTreeMap)currentWarRequest.get("data_ATT")).containsKey(agreementCondition) ? String.format("%.0f", new Object[] {((LinkedTreeMap)currentWarRequest.get("data_ATT")).get(agreementCondition)}): "0") + "/" + delay + " " + I18n.getString("faction.enemy.conditions." + agreementCondition);

                                            if (((LinkedTreeMap)currentWarRequest.get("data_ATT")).containsKey(agreementCondition) && ((Double)((LinkedTreeMap)currentWarRequest.get("data_ATT")).get(agreementCondition)).doubleValue() >= (double)Integer.parseInt(delay))
                                            {
                                                label = "\u00a7a" + label;
                                            }

                                            ModernGui.drawScaledStringCustomFont(label, (float)(var55 + 7), (float)(offsetY.intValue() + 0), 10395075, 0.5F, "left", false, "georamaMedium", 28);
                                            label = (((LinkedTreeMap)currentWarRequest.get("data_DEF")).containsKey(agreementCondition) ? String.format("%.0f", new Object[] {((LinkedTreeMap)currentWarRequest.get("data_DEF")).get(agreementCondition)}): "0") + "/" + delay + " " + I18n.getString("faction.enemy.conditions." + agreementCondition);

                                            if (((LinkedTreeMap)currentWarRequest.get("data_DEF")).containsKey(agreementCondition) && ((Double)((LinkedTreeMap)currentWarRequest.get("data_DEF")).get(agreementCondition)).doubleValue() >= (double)Integer.parseInt(delay))
                                            {
                                                label = "\u00a7a" + label;
                                            }

                                            ModernGui.drawScaledStringCustomFont(label, (float)(var55 + 179 - 7), (float)(offsetY.intValue() + 0), 10395075, 0.5F, "right", false, "georamaMedium", 28);
                                            ClientEventHandler.STYLE.bindTexture("faction_war");
                                            ModernGui.drawScaledCustomSizeModalRect((float)(var55 + 87), (float)(offsetY.intValue() - 1), (float)(((Integer)iconsConditionsRewards.get(agreementCondition)).intValue() * GUI_SCALE), (float)(106 * GUI_SCALE), 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                                            ++var22;
                                        }
                                    }

                                    GUIUtils.endGLScissor();

                                    if (mouseX >= this.guiLeft + 271 && mouseX <= this.guiLeft + 271 + 179 && mouseY >= this.guiTop + 162 && mouseY <= this.guiTop + 162 + 63)
                                    {
                                        this.scrollBarConditions.draw(mouseX, mouseY);
                                    }
                                }

                                ClientEventHandler.STYLE.bindTexture("faction_war");
                                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 43), (float)(this.guiTop + 192), (float)(235 * GUI_SCALE), (float)(64 * GUI_SCALE), 211 * GUI_SCALE, 32 * GUI_SCALE, 211, 32, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.enemy.rewards"), (float)(this.guiLeft + 50), (float)(this.guiTop + 196), 16514302, 0.5F, "left", false, "georamaSemiBold", 28);

                                if (currentWarRequest.get("rewards") != null)
                                {
                                    var22 = 0;
                                    var50 = ((String)currentWarRequest.get("rewards")).split(",");
                                    dateFormated = var50.length;

                                    for (conditionsOTHER = 0; conditionsOTHER < dateFormated; ++conditionsOTHER)
                                    {
                                        var40 = var50[conditionsOTHER];

                                        if (!var40.isEmpty() && var40.split("#").length == 2)
                                        {
                                            agreementCondition = var40.split("#")[0];
                                            delay = var40.split("#")[1];
                                            var55 = this.guiLeft + 50;

                                            if (var22 >= 2)
                                            {
                                                var55 = this.guiLeft + 43 + 211;
                                            }

                                            var61 = this.guiTop + 205 + var22 % 2 * 9;
                                            label = "";

                                            if (Integer.parseInt(delay) > 0)
                                            {
                                                valueType = "";

                                                if (agreementCondition.equals("peace"))
                                                {
                                                    valueType = I18n.getString("faction.enemy.rewards.valueType.day");
                                                }

                                                label = delay + valueType + " " + I18n.getString("faction.enemy.rewards." + agreementCondition);
                                            }
                                            else
                                            {
                                                label = I18n.getString("faction.enemy.rewards." + agreementCondition);
                                            }

                                            ClientEventHandler.STYLE.bindTexture("faction_war");
                                            ModernGui.drawScaledCustomSizeModalRect(var22 < 2 ? (float)var55 : (float)(var55 - 15), (float)var61, (float)(((Integer)iconsConditionsRewards.get(agreementCondition)).intValue() * GUI_SCALE), (float)(106 * GUI_SCALE), 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                                            ModernGui.drawScaledStringCustomFont(label, var22 < 2 ? (float)(var55 + 12) : (float)(var55 - 17), (float)var61, 10395075, 0.5F, var22 < 2 ? "left" : "right", false, "georamaMedium", 28);
                                        }

                                        ++var22;
                                    }
                                }
                            }
                        }
                        else
                        {
                            if (!currentWar.get("status").equals("waiting_conditions_att") && !this.defenserCounterPropositionMode)
                            {
                                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.war.label.proposition").replaceAll("#country#", (String)currentWar.get(currentWar.get("status").equals("waiting_conditions_def") ? "factionATTName" : "factionDEFName")), (float)(this.guiLeft + 43), (float)(this.guiTop + 97), 16514302, 0.5F, "left", false, "georamaSemiBold", 28);
                                ClientEventHandler.STYLE.bindTexture("faction_war_2");
                                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 43), (float)(this.guiTop + 108), (float)(0 * GUI_SCALE), (float)(199 * GUI_SCALE), 187 * GUI_SCALE, 54 * GUI_SCALE, 187, 54, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.enemy.contitions_with_mode").replaceAll("#mode#", I18n.getString("faction.enemy.conditionsType." + currentWarRequest.get(currentWar.get("status").equals("waiting_conditions_def") ? "conditionsType_ATT" : "conditionsType_DEF"))), (float)(this.guiLeft + 48), (float)(this.guiTop + 112), 16514302, 0.5F, "left", false, "georamaSemiBold", 32);
                                var22 = 0;
                                agreement = (String)currentWarRequest.get(currentWar.get("status").equals("waiting_conditions_def") ? "conditions_ATT" : "conditions_DEF");
                                int status;
                                int var33;

                                if (agreement != null)
                                {
                                    String[] var27 = agreement.split(",");
                                    var29 = var27.length;

                                    for (var33 = 0; var33 < var29; ++var33)
                                    {
                                        var34 = var27[var33];

                                        if (!var34.isEmpty())
                                        {
                                            conditionsOTHER = this.guiLeft + 48 + var22 / 3 * 96;
                                            status = this.guiTop + 126 + var22 % 3 * 11;
                                            agreementCondition = var34.split("#")[0];
                                            delay = var34.split("#")[1];
                                            ClientEventHandler.STYLE.bindTexture("faction_war");
                                            ModernGui.drawScaledCustomSizeModalRect((float)conditionsOTHER, (float)status, (float)(((Integer)iconsConditionsRewards.get(agreementCondition)).intValue() * GUI_SCALE), (float)(106 * GUI_SCALE), 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                                            ModernGui.drawScaledStringCustomFont(delay + " " + I18n.getString("faction.enemy.conditions." + agreementCondition), (float)(conditionsOTHER + 10), (float)(status + 1), 10395075, 0.5F, "left", false, "georamaMedium", 28);
                                            ++var22;
                                        }
                                    }
                                }

                                ClientEventHandler.STYLE.bindTexture("faction_war_2");
                                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 43), (float)(this.guiTop + 165), (float)(0 * GUI_SCALE), (float)(368 * GUI_SCALE), 187 * GUI_SCALE, 45 * GUI_SCALE, 187, 45, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.enemy.rewards"), (float)(this.guiLeft + 48), (float)(this.guiTop + 170), 16514302, 0.5F, "left", false, "georamaSemiBold", 32);
                                var22 = 0;
                                var26 = (String)currentWarRequest.get(currentWar.get("status").equals("waiting_conditions_def") ? "rewards_ATT" : "rewards_DEF");

                                if (var26 != null)
                                {
                                    String[] var31 = var26.split(",");
                                    var33 = var31.length;

                                    for (dateFormated = 0; dateFormated < var33; ++dateFormated)
                                    {
                                        var37 = var31[dateFormated];

                                        if (!var37.isEmpty())
                                        {
                                            status = this.guiLeft + 48 + var22 / 2 * 96;
                                            var43 = this.guiTop + 185 + var22 % 2 * 11;
                                            delay = var37.split("#")[0];
                                            String offSetX = var37.split("#")[1];
                                            ClientEventHandler.STYLE.bindTexture("faction_war");
                                            ModernGui.drawScaledCustomSizeModalRect((float)status, (float)var43, (float)(((Integer)iconsConditionsRewards.get(delay)).intValue() * GUI_SCALE), (float)(106 * GUI_SCALE), 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                                            ModernGui.drawScaledStringCustomFont(offSetX + " " + I18n.getString("faction.enemy.rewards." + delay), (float)(status + 10), (float)(var43 + 1), 10395075, 0.5F, "left", false, "georamaMedium", 28);
                                            ++var22;
                                        }
                                    }
                                }

                                if (currentWar.get("status").equals("waiting_conditions_def") && currentWarRequest.get("factionDEF").equals(currentWarRequest.get("playerFaction")) && ((Boolean)currentWarRequest.get("hasWarPermInOwnCountry")).booleanValue() || (currentWar.get("status").equals("waiting_conditions_att") || currentWar.get("status").equals("waiting_conditions_att_second")) && currentWarRequest.get("factionATT").equals(currentWarRequest.get("playerFaction")) && ((Boolean)currentWarRequest.get("hasWarPermInOwnCountry")).booleanValue())
                                {
                                    if (!currentWar.get("status").equals("waiting_conditions_def") || agreement != null)
                                    {
                                        ClientEventHandler.STYLE.bindTexture("faction_war_2");
                                        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 43), (float)(this.guiTop + 214), (float)(191 * GUI_SCALE), (float)(160 * GUI_SCALE), 53 * GUI_SCALE, 13 * GUI_SCALE, 53, 13, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);

                                        if (mouseX >= this.guiLeft + 43 && mouseX <= this.guiLeft + 43 + 53 && mouseY >= this.guiTop + 214 && mouseY <= this.guiTop + 214 + 13)
                                        {
                                            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 43), (float)(this.guiTop + 214), (float)(191 * GUI_SCALE), (float)(142 * GUI_SCALE), 53 * GUI_SCALE, 13 * GUI_SCALE, 53, 13, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                                            this.hoveredAction = currentWar.get("status").equals("waiting_conditions_def") ? "accept_conditions_def" : "accept_conditions_att_second";
                                        }

                                        ModernGui.drawScaledStringCustomFont(I18n.getString("faction.enemy.accept_conditions_def"), (float)(this.guiLeft + 43 + 26), (float)(this.guiTop + 217), 2234425, 0.5F, "center", false, "georamaSemiBold", 28);
                                    }

                                    ClientEventHandler.STYLE.bindTexture("faction_war_2");

                                    if (mouseX >= this.guiLeft + 119 && mouseX <= this.guiLeft + 119 + 110 && mouseY >= this.guiTop + 214 && mouseY <= this.guiTop + 214 + 13)
                                    {
                                        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 119), (float)(this.guiTop + 214), (float)(248 * GUI_SCALE), (float)(160 * GUI_SCALE), 111 * GUI_SCALE, 13 * GUI_SCALE, 111, 13, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                                        ModernGui.drawScaledStringCustomFont(I18n.getString(currentWar.get("status").equals("waiting_conditions_def") ? "faction.enemy.refuse_conditions_def" : "faction.enemy.refuse_conditions_att_second"), (float)(this.guiLeft + 119 + 55), (float)(this.guiTop + 217), 2234425, 0.5F, "center", false, "georamaSemiBold", 28);
                                        this.hoveredAction = currentWar.get("status").equals("waiting_conditions_def") ? "refuse_conditions_def" : "refuse_conditions_att_second";
                                    }
                                    else
                                    {
                                        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 119), (float)(this.guiTop + 214), (float)(248 * GUI_SCALE), (float)(142 * GUI_SCALE), 111 * GUI_SCALE, 13 * GUI_SCALE, 111, 13, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                                        ModernGui.drawScaledStringCustomFont(I18n.getString(currentWar.get("status").equals("waiting_conditions_def") ? "faction.enemy.refuse_conditions_def" : "faction.enemy.refuse_conditions_att_second"), (float)(this.guiLeft + 119 + 55), (float)(this.guiTop + 217), 14803951, 0.5F, "center", false, "georamaSemiBold", 28);
                                    }
                                }
                            }
                            else
                            {
                                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.war.title.your_proposal").replaceAll("#country#", this.defenserCounterPropositionMode ? (String)currentWar.get("factionDEFName") : (String)currentWar.get("factionATTName")), (float)(this.guiLeft + 43), (float)(this.guiTop + 97), 16514302, 0.5F, "left", false, "georamaSemiBold", 28);
                                ClientEventHandler.STYLE.bindTexture("faction_war_2");
                                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 42), (float)(this.guiTop + 108), (float)(0 * GUI_SCALE), (float)(0 * GUI_SCALE), 188 * GUI_SCALE, 119 * GUI_SCALE, 188, 119, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.enemy.contitions"), (float)(this.guiLeft + 48), (float)(this.guiTop + 114), 16514302, 0.5F, "left", false, "georamaSemiBold", 32);
                                var22 = 0;

                                for (var25 = this.availableConditions.iterator(); var25.hasNext(); ++var22)
                                {
                                    var26 = (String)var25.next();
                                    ClientEventHandler.STYLE.bindTexture("faction_war");

                                    if (this.conditionsInput.containsKey(var26))
                                    {
                                        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 49), (float)(this.guiTop + 128 - 1 + var22 * 12), (float)(((Integer)iconsConditionsRewards.get(var26)).intValue() * GUI_SCALE), (float)((FactionGUI.isNumeric(((GuiTextField)this.conditionsInput.get(var26)).getText(), false) ? 114 : 106) * GUI_SCALE), 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                                        ModernGui.drawScaledStringCustomFont(I18n.getString("faction.enemy.conditions." + var26), (float)(this.guiLeft + 59), (float)(this.guiTop + 128 + var22 * 12), FactionGUI.isNumeric(((GuiTextField)this.conditionsInput.get(var26)).getText(), false) ? 7239406 : 16514302, 0.5F, "left", false, "georamaSemiBold", 25);
                                        ((GuiTextField)this.conditionsInput.get(var26)).drawTextBox();
                                        ClientEventHandler.STYLE.bindTexture("faction_war_2");
                                        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 108), (float)(this.guiTop + 127 + var22 * 12), (float)(191 * GUI_SCALE), (float)(176 * GUI_SCALE), 22 * GUI_SCALE, 9 * GUI_SCALE, 22, 9, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                                    }
                                }

                                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.enemy.contitions_added"), (float)(this.guiLeft + 48), (float)(this.guiTop + 202), 16514302, 0.5F, "left", false, "georamaSemiBold", 32);
                                ClientEventHandler.STYLE.bindTexture("faction_global");
                                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 49), (float)(this.guiTop + 214), (float)(321 * GUI_SCALE), (float)((this.conditionsCumulative ? 181 : 190) * GUI_SCALE), 6 * GUI_SCALE, 6 * GUI_SCALE, 6, 6, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.common.true"), (float)(this.guiLeft + 57), (float)(this.guiTop + 214), this.conditionsCumulative ? 7239406 : 10395075, 0.5F, "left", false, "georamaSemiBold", 25);

                                if (mouseX >= this.guiLeft + 49 && mouseX <= this.guiLeft + 49 + 6 && mouseY >= this.guiTop + 214 && mouseY <= this.guiTop + 214 + 6)
                                {
                                    this.hoveredAction = "conditions_cumulative#true";
                                }

                                ClientEventHandler.STYLE.bindTexture("faction_global");
                                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 90), (float)(this.guiTop + 214), (float)(321 * GUI_SCALE), (float)((!this.conditionsCumulative ? 181 : 190) * GUI_SCALE), 6 * GUI_SCALE, 6 * GUI_SCALE, 6, 6, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.common.false"), (float)(this.guiLeft + 98), (float)(this.guiTop + 214), !this.conditionsCumulative ? 7239406 : 10395075, 0.5F, "left", false, "georamaSemiBold", 25);

                                if (mouseX >= this.guiLeft + 90 && mouseX <= this.guiLeft + 90 + 6 && mouseY >= this.guiTop + 214 && mouseY <= this.guiTop + 214 + 6)
                                {
                                    this.hoveredAction = "conditions_cumulative#false";
                                }

                                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.enemy.rewards"), (float)(this.guiLeft + 141), (float)(this.guiTop + 114), 16514302, 0.5F, "left", false, "georamaSemiBold", 32);
                                var22 = 0;

                                for (var25 = this.availableRewards.iterator(); var25.hasNext(); ++var22)
                                {
                                    var26 = (String)var25.next();

                                    if (this.rewardsInput.containsKey(var26))
                                    {
                                        var29 = FactionGUI.isNumeric(((GuiTextField)this.rewardsInput.get(var26)).getText(), false) ? 7239406 : 16514302;
                                        var29 = !((GuiTextField)this.rewardsInput.get(var26)).getText().isEmpty() && (!FactionGUI.isNumeric(((GuiTextField)this.rewardsInput.get(var26)).getText(), true) || var26.equals("peace") && Integer.parseInt(((GuiTextField)this.rewardsInput.get(var26)).getText()) > 120) ? 15017020 : var29;
                                        ClientEventHandler.STYLE.bindTexture("faction_war");
                                        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 142), (float)(this.guiTop + 128 - 1 + var22 * 12), (float)(((Integer)iconsConditionsRewards.get(var26)).intValue() * GUI_SCALE), (float)((var29 == 7239406 ? 114 : (var29 == 15017020 ? 130 : 106)) * GUI_SCALE), 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                                        ModernGui.drawScaledStringCustomFont(I18n.getString("faction.enemy.rewards." + var26), (float)(this.guiLeft + 152), (float)(this.guiTop + 128 + var22 * 12), var29, 0.5F, "left", false, "georamaSemiBold", 25);
                                        ((GuiTextField)this.rewardsInput.get(var26)).drawTextBox();
                                        ClientEventHandler.STYLE.bindTexture("faction_war_2");
                                        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 201), (float)(this.guiTop + 127 + var22 * 12), (float)(191 * GUI_SCALE), (float)(176 * GUI_SCALE), 22 * GUI_SCALE, 9 * GUI_SCALE, 26, 9, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                                    }
                                }

                                if (currentWar.get("status").equals("waiting_conditions_def"))
                                {
                                    ClientEventHandler.STYLE.bindTexture("faction_war_2");

                                    if (mouseX >= this.guiLeft + 142 && mouseX <= this.guiLeft + 142 + 39 && mouseY >= this.guiTop + 211 && mouseY <= this.guiTop + 211 + 11)
                                    {
                                        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 142), (float)(this.guiTop + 211), (float)(191 * GUI_SCALE), (float)(202 * GUI_SCALE), 39 * GUI_SCALE, 11 * GUI_SCALE, 39, 11, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                                        ModernGui.drawScaledStringCustomFont(I18n.getString("faction.common.back"), (float)(this.guiLeft + 142 + 19), (float)(this.guiTop + 213), 2234425, 0.5F, "center", false, "georamaSemiBold", 25);
                                        this.hoveredAction = "return_counter_def";
                                    }
                                    else
                                    {
                                        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 142), (float)(this.guiTop + 211), (float)(237 * GUI_SCALE), (float)(202 * GUI_SCALE), 39 * GUI_SCALE, 11 * GUI_SCALE, 39, 11, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                                        ModernGui.drawScaledStringCustomFont(I18n.getString("faction.common.back"), (float)(this.guiLeft + 142 + 19), (float)(this.guiTop + 213), 14803951, 0.5F, "center", false, "georamaSemiBold", 25);
                                    }
                                }

                                ClientEventHandler.STYLE.bindTexture("faction_war_2");
                                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 185), (float)(this.guiTop + 211), (float)(191 * GUI_SCALE), (float)(188 * GUI_SCALE), 39 * GUI_SCALE, 11 * GUI_SCALE, 39, 11, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);

                                if (mouseX >= this.guiLeft + 185 && mouseX <= this.guiLeft + 185 + 39 && mouseY >= this.guiTop + 211 && mouseY <= this.guiTop + 211 + 11)
                                {
                                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 185), (float)(this.guiTop + 211), (float)(191 * GUI_SCALE), (float)(202 * GUI_SCALE), 39 * GUI_SCALE, 11 * GUI_SCALE, 39, 11, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                                    this.hoveredAction = this.defenserCounterPropositionMode ? "send_conditions_def" : "send_conditions_att";
                                }

                                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.enemy.send_request"), (float)(this.guiLeft + 185 + 19), (float)(this.guiTop + 213), 2234425, 0.5F, "center", false, "georamaSemiBold", 25);
                            }

                            Gui.drawRect(this.guiLeft + 241, this.guiTop + 89, this.guiLeft + this.xSize, this.guiTop + this.ySize, -14279619);

                            if (this.defenserCounterPropositionMode)
                            {
                                ModernGui.drawScaledStringCustomFont(I18n.getString(currentWar.get("status").equals("waiting_conditions_def") ? "faction.war.label.received_proposition" : "faction.war.label.my_proposition").replaceAll("#country#", this.defenserCounterPropositionMode ? (String)currentWar.get("factionATTName") : (String)currentWar.get("factionDEFName")), (float)(this.guiLeft + 254), (float)(this.guiTop + 97), 16514302, 0.5F, "left", false, "georamaSemiBold", 28);
                                ClientEventHandler.STYLE.bindTexture("faction_war_2");
                                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 254), (float)(this.guiTop + 108), (float)(0 * GUI_SCALE), (float)(199 * GUI_SCALE), 187 * GUI_SCALE, 54 * GUI_SCALE, 187, 54, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.enemy.contitions_with_mode").replaceAll("#mode#", I18n.getString("faction.enemy.conditionsType." + currentWarRequest.get(currentWar.get("status").equals("waiting_conditions_def") ? "conditionsType_ATT" : "conditionsType_DEF"))), (float)(this.guiLeft + 254 + 5), (float)(this.guiTop + 112), 16514302, 0.5F, "left", false, "georamaSemiBold", 32);
                                var22 = 0;
                                String[] var28;

                                if (currentWarRequest.get(currentWar.get("status").equals("waiting_conditions_def") ? "conditions_ATT" : "conditions_DEF") != null)
                                {
                                    var28 = ((String)currentWarRequest.get(currentWar.get("status").equals("waiting_conditions_def") ? "conditions_ATT" : "conditions_DEF")).split(",");
                                    var30 = var28.length;

                                    for (var29 = 0; var29 < var30; ++var29)
                                    {
                                        var38 = var28[var29];
                                        dateFormated = this.guiLeft + 254 + 5 + var22 / 3 * 96;
                                        conditionsOTHER = this.guiTop + 126 + var22 % 3 * 11;
                                        var40 = var38.split("#")[0];
                                        agreementCondition = var38.split("#")[1];
                                        ClientEventHandler.STYLE.bindTexture("faction_war");
                                        ModernGui.drawScaledCustomSizeModalRect((float)dateFormated, (float)conditionsOTHER, (float)(((Integer)iconsConditionsRewards.get(var40)).intValue() * GUI_SCALE), (float)(106 * GUI_SCALE), 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                                        ModernGui.drawScaledStringCustomFont(agreementCondition + " " + I18n.getString("faction.enemy.conditions." + var40), (float)(dateFormated + 10), (float)(conditionsOTHER + 1), 10395075, 0.5F, "left", false, "georamaMedium", 28);
                                        ++var22;
                                    }
                                }

                                ClientEventHandler.STYLE.bindTexture("faction_war_2");
                                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 254), (float)(this.guiTop + 165), (float)(0 * GUI_SCALE), (float)(368 * GUI_SCALE), 187 * GUI_SCALE, 45 * GUI_SCALE, 187, 45, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.enemy.rewards"), (float)(this.guiLeft + 254 + 5), (float)(this.guiTop + 170), 16514302, 0.5F, "left", false, "georamaSemiBold", 32);
                                var22 = 0;

                                if (currentWarRequest.get(currentWar.get("status").equals("waiting_conditions_def") ? "rewards_ATT" : "rewards_DEF") != null)
                                {
                                    var28 = ((String)currentWarRequest.get(currentWar.get("status").equals("waiting_conditions_def") ? "rewards_ATT" : "rewards_DEF")).split(",");
                                    var30 = var28.length;

                                    for (var29 = 0; var29 < var30; ++var29)
                                    {
                                        var38 = var28[var29];
                                        dateFormated = this.guiLeft + 254 + 5 + var22 / 2 * 96;
                                        conditionsOTHER = this.guiTop + 185 + var22 % 2 * 11;
                                        var40 = var38.split("#")[0];
                                        agreementCondition = var38.length() > 1 ? var38.split("#")[1] : "0";
                                        ClientEventHandler.STYLE.bindTexture("faction_war");
                                        ModernGui.drawScaledCustomSizeModalRect((float)dateFormated, (float)conditionsOTHER, (float)(((Integer)iconsConditionsRewards.get(var40)).intValue() * GUI_SCALE), (float)(106 * GUI_SCALE), 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                                        ModernGui.drawScaledStringCustomFont(agreementCondition + " " + I18n.getString("faction.enemy.rewards." + var40), (float)(dateFormated + 10), (float)(conditionsOTHER + 1), 10395075, 0.5F, "left", false, "georamaMedium", 28);
                                        ++var22;
                                    }
                                }
                            }
                            else
                            {
                                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.war.title.request_informations"), (float)(this.guiLeft + 254), (float)(this.guiTop + 97), 16514302, 0.5F, "left", false, "georamaSemiBold", 28);
                                ClientEventHandler.STYLE.bindTexture("faction_war_2");
                                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 254), (float)(this.guiTop + 108), (float)(191 * GUI_SCALE), (float)(0 * GUI_SCALE), 196 * GUI_SCALE, 43 * GUI_SCALE, 196, 43, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.war.label.war_for") + " " + I18n.getString("faction.enemy.reason." + currentWarRequest.get("reason")), (float)(this.guiLeft + 261), (float)(this.guiTop + 115), 14803951, 0.5F, "left", false, "georamaSemiBold", 30);
                                ModernGui.drawSectionStringCustomFont(I18n.getString("faction.enemy.reason.desc." + currentWarRequest.get("reason")), (float)(this.guiLeft + 261), (float)(this.guiTop + 125), 10395075, 0.5F, "left", false, "georamaMedium", 26, 8, 360);
                                var24 = new Date(((Double)currentWarRequest.get("creationTime")).longValue());
                                agreementType = new SimpleDateFormat("dd/MM/yyyy");
                                date = agreementType.format(var24);
                                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.war.label.initiate_by_at").replaceAll("#date#", date).replaceAll("#player#", (String)currentWar.get("sender")), (float)(this.guiLeft + 261), (float)(this.guiTop + 142), 10395075, 0.5F, "left", false, "georamaMedium", 24);
                                simpleDateFormat = 1;

                                if (currentWar.get("status").equals("waiting_conditions_def"))
                                {
                                    simpleDateFormat = 2;
                                }
                                else if (currentWar.get("status").equals("waiting_conditions_att_second"))
                                {
                                    simpleDateFormat = 3;
                                }

                                for (dateFormated = 1; dateFormated <= 3; ++dateFormated)
                                {
                                    ClientEventHandler.STYLE.bindTexture("faction_war_2");
                                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 254 + (dateFormated - 1) * 67), (float)(this.guiTop + 154), (float)((dateFormated < simpleDateFormat ? 191 : (dateFormated == simpleDateFormat ? 258 : 325)) * GUI_SCALE), (float)(46 * GUI_SCALE), 63 * GUI_SCALE, 73 * GUI_SCALE, 63, 73, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                                    ModernGui.drawScaledStringCustomFont(dateFormated + "", (float)(this.guiLeft + 254 + (dateFormated - 1) * 67 + 32), (float)(this.guiTop + 154 + 4), dateFormated > simpleDateFormat ? 10395075 : 2234425, 0.75F, "center", false, "georamaBold", 28);
                                    ModernGui.drawSectionStringCustomFont(I18n.getString("faction.war.waiting_validation.step." + dateFormated).replaceAll("#factionATT#", (String)currentWar.get("factionATTName")).replaceAll("#factionDEF#", (String)currentWar.get("factionDEFName")), (float)(this.guiLeft + 254 + (dateFormated - 1) * 67 + 32), (float)(this.guiTop + 177), dateFormated < simpleDateFormat ? 16514302 : (dateFormated == simpleDateFormat ? 2234425 : 10395075), 0.5F, "center", false, "georamaMedium", 22, 8, 105);
                                }
                            }
                        }
                    }
                    else
                    {
                        ModernGui.drawScaledStringCustomFont(I18n.getString("faction.war.status." + currentWar.get("status")).replaceAll("#factionATT#", (String)currentWar.get("factionATTName")).replaceAll("#factionDEF#", (String)currentWar.get("factionDEFName")), (float)(this.guiLeft + 43), (float)(this.guiTop + 97), 16514302, 0.5F, "left", false, "georamaSemiBold", 28);
                        ClientEventHandler.STYLE.bindTexture("faction_war_2");
                        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 43), (float)(this.guiTop + 108), (float)(0 * GUI_SCALE), (float)(307 * GUI_SCALE), 403 * GUI_SCALE, 43 * GUI_SCALE, 403, 43, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                        ModernGui.drawScaledStringCustomFont(I18n.getString("faction.war.label.war_for") + " " + I18n.getString("faction.enemy.reason." + currentWarRequest.get("reason")), (float)(this.guiLeft + 50), (float)(this.guiTop + 115), 14803951, 0.5F, "left", false, "georamaSemiBold", 30);
                        ModernGui.drawSectionStringCustomFont(I18n.getString("faction.enemy.reason.desc." + currentWarRequest.get("reason")), (float)(this.guiLeft + 50), (float)(this.guiTop + 125), 10395075, 0.5F, "left", false, "georamaMedium", 26, 8, 400);
                        var24 = new Date(((Double)currentWarRequest.get("creationTime")).longValue());
                        agreementType = new SimpleDateFormat("dd/MM/yyyy");
                        date = agreementType.format(var24);
                        ModernGui.drawScaledStringCustomFont(I18n.getString("faction.war.label.initiate_by_at").replaceAll("#date#", date).replaceAll("#player#", (String)currentWarRequest.get("sender")), (float)(this.guiLeft + 50), (float)(this.guiTop + 142), 10395075, 0.5F, "left", false, "georamaMedium", 24);
                        Gui.drawRect(this.guiLeft + 106, this.guiTop + 192, this.guiLeft + 387, this.guiTop + 194, -13950655);
                        simpleDateFormat = 0;

                        if (currentWar.get("status").equals("waiting_conditions_att"))
                        {
                            simpleDateFormat = 1;
                        }
                        else if (currentWar.get("status").equals("waiting_conditions_def"))
                        {
                            simpleDateFormat = 2;
                        }
                        else if (currentWar.get("status").equals("waiting_conditions_att_second"))
                        {
                            simpleDateFormat = 3;
                        }

                        for (dateFormated = 0; dateFormated <= 4; ++dateFormated)
                        {
                            ClientEventHandler.STYLE.bindTexture("faction_war_2");

                            if (dateFormated == simpleDateFormat && (currentWar.get("status").equals("refused") || currentWar.get("status").equals("cancelled")))
                            {
                                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 43 + dateFormated * 85), (float)(this.guiTop + 154), (float)(449 * GUI_SCALE), (float)(136 * GUI_SCALE), 63 * GUI_SCALE, 73 * GUI_SCALE, 63, 73, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                            }
                            else
                            {
                                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 43 + dateFormated * 85), (float)(this.guiTop + 154), (float)((dateFormated < simpleDateFormat ? 191 : (dateFormated == simpleDateFormat ? 258 : 325)) * GUI_SCALE), (float)(46 * GUI_SCALE), 63 * GUI_SCALE, 73 * GUI_SCALE, 63, 73, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                            }

                            ModernGui.drawScaledStringCustomFont(dateFormated + "", (float)(this.guiLeft + 43 + dateFormated * 85 + 32), (float)(this.guiTop + 158), dateFormated > simpleDateFormat ? 10395075 : 2234425, 0.75F, "center", false, "georamaBold", 28);
                            ModernGui.drawSectionStringCustomFont(I18n.getString("faction.war.waiting_validation.step." + dateFormated).replaceAll("#factionATT#", (String)currentWar.get("factionATTName")).replaceAll("#factionDEF#", (String)currentWar.get("factionDEFName")), (float)(this.guiLeft + 43 + dateFormated * 85 + 32), (float)(this.guiTop + 177), dateFormated < simpleDateFormat ? 16514302 : (dateFormated == simpleDateFormat ? 2234425 : 10395075), 0.5F, "center", false, "georamaMedium", 22, 8, 105);
                        }
                    }
                }
                else
                {
                    ModernGui.drawScaledStringCustomFont(I18n.getString("faction.war.label.list_agreements"), (float)(this.guiLeft + 43), (float)(this.guiTop + 97), 16514302, 0.5F, "left", false, "georamaSemiBold", 28);

                    if (FactionGUI.hasPermissions("wars"))
                    {
                        ClientEventHandler.STYLE.bindTexture("faction_war_2");

                        if (mouseX >= this.guiLeft + 48 + (int)this.cFontSemiBold28.getStringWidth(I18n.getString("faction.war.label.list_agreements")) / 2 && mouseX <= this.guiLeft + 48 + (int)this.cFontSemiBold28.getStringWidth(I18n.getString("faction.war.label.list_agreements")) / 2 + 8 && mouseY >= this.guiTop + 97 && mouseY <= this.guiTop + 97 + 8)
                        {
                            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 48 + (int)this.cFontSemiBold28.getStringWidth(I18n.getString("faction.war.label.list_agreements")) / 2), (float)(this.guiTop + 97), (float)(248 * GUI_SCALE), (float)(188 * GUI_SCALE), 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                            this.hoveredAction = "add_agreement";
                        }
                        else
                        {
                            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 48 + (int)this.cFontSemiBold28.getStringWidth(I18n.getString("faction.war.label.list_agreements")) / 2), (float)(this.guiTop + 97), (float)(237 * GUI_SCALE), (float)(188 * GUI_SCALE), 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                        }
                    }

                    ClientEventHandler.STYLE.bindTexture("faction_war_2");
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 42), (float)(this.guiTop + 108), (float)(418 * GUI_SCALE), (float)(0 * GUI_SCALE), 94 * GUI_SCALE, 15 * GUI_SCALE, 94, 15, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 42), (float)(this.guiTop + 123), (float)(418 * GUI_SCALE), (float)(15 * GUI_SCALE), 94 * GUI_SCALE, 96 * GUI_SCALE, 94, 96, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                    ModernGui.drawScaledStringCustomFont(I18n.getString("faction.war.label.agreements_active"), (float)(this.guiLeft + 47), (float)(this.guiTop + 112), 2234425, 0.5F, "left", false, "georamaSemiBold", 30);
                    ModernGui.drawScaledStringCustomFont(((ArrayList)currentWar.get("agreements_active")).size() + "", (float)(this.guiLeft + 131), (float)(this.guiTop + 112), 2234425, 0.5F, "right", false, "georamaSemiBold", 30);
                    ClientEventHandler.STYLE.bindTexture("faction_war_2");
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 131), (float)(this.guiTop + 125), (float)(362 * GUI_SCALE), (float)(142 * GUI_SCALE), 2 * GUI_SCALE, 91 * GUI_SCALE, 2, 91, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                    GUIUtils.startGLScissor(this.guiLeft + 42, this.guiTop + 123, 89, 96);
                    Float var42;
                    Integer var52;

                    for (var23 = 0; var23 < ((ArrayList)currentWar.get("agreements_active")).size(); ++var23)
                    {
                        var30 = this.guiLeft + 42;
                        var42 = Float.valueOf((float)(this.guiTop + 123 + 4 + var23 * 13) + this.getSlideActiveAgreements());
                        var52 = Integer.valueOf(((Double)((LinkedTreeMap)((ArrayList)currentWar.get("agreements_active")).get(var23)).get("id")).intValue());
                        var34 = (String)((LinkedTreeMap)((ArrayList)currentWar.get("agreements_active")).get(var23)).get("factionSender");
                        var37 = (String)((LinkedTreeMap)((ArrayList)currentWar.get("agreements_active")).get(var23)).get("type");
                        Long var54 = Long.valueOf(((Double)((LinkedTreeMap)((ArrayList)currentWar.get("agreements_active")).get(var23)).get("creationTime")).longValue());
                        ClientProxy.loadCountryFlag(var34);

                        if (ClientProxy.flagsTexture.containsKey(var34))
                        {
                            GL11.glBindTexture(GL11.GL_TEXTURE_2D, ((DynamicTexture)ClientProxy.flagsTexture.get(var34)).getGlTextureId());
                            ModernGui.drawScaledCustomSizeModalRect((float)(var30 + 5), (float)(var42.intValue() + 0), 0.0F, 0.0F, 156, 78, 17, 10, 156.0F, 78.0F, false);
                        }

                        ModernGui.drawScaledStringCustomFont(I18n.getString("faction.enemy.agreement.type.short." + var37), (float)(var30 + 26), (float)(var42.intValue() + 2), var52.intValue() == selectedAgreementId ? 16514302 : 10395075, 0.5F, "left", false, "georamaMedium", 28);
                        agreementCondition = ModernGui.formatDelayTime(var54);
                        ModernGui.drawScaledStringCustomFont(agreementCondition, (float)(var30 + 89 - 2), (float)(var42.intValue() + 2), var52.intValue() == selectedAgreementId ? 16514302 : 10395075, 0.5F, "right", false, "georamaMedium", 28);

                        if (mouseX >= var30 && mouseX <= var30 + 89 && mouseY >= var42.intValue() && mouseY <= var42.intValue() + 13)
                        {
                            this.hoveredAction = "open_agreement#" + var52;
                        }
                    }

                    GUIUtils.endGLScissor();

                    if (mouseX >= this.guiLeft + 42 && mouseX <= this.guiLeft + 42 + 89 && mouseY >= this.guiTop + 123 && mouseY <= this.guiTop + 123 + 96)
                    {
                        this.scrollBarAgreementsActive.draw(mouseX, mouseY);
                    }

                    ClientEventHandler.STYLE.bindTexture("faction_war_2");
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 140), (float)(this.guiTop + 108), (float)(418 * GUI_SCALE), (float)(0 * GUI_SCALE), 94 * GUI_SCALE, 15 * GUI_SCALE, 94, 15, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 140), (float)(this.guiTop + 123), (float)(418 * GUI_SCALE), (float)(15 * GUI_SCALE), 94 * GUI_SCALE, 96 * GUI_SCALE, 94, 96, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                    ModernGui.drawScaledStringCustomFont(I18n.getString("faction.war.label.agreements_waiting"), (float)(this.guiLeft + 145), (float)(this.guiTop + 112), 2234425, 0.5F, "left", false, "georamaSemiBold", 30);
                    ModernGui.drawScaledStringCustomFont(((ArrayList)currentWar.get("agreements_waiting")).size() + "", (float)(this.guiLeft + 229), (float)(this.guiTop + 112), 2234425, 0.5F, "right", false, "georamaSemiBold", 30);
                    ClientEventHandler.STYLE.bindTexture("faction_war_2");
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 229), (float)(this.guiTop + 125), (float)(362 * GUI_SCALE), (float)(142 * GUI_SCALE), 2 * GUI_SCALE, 91 * GUI_SCALE, 2, 91, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                    GUIUtils.startGLScissor(this.guiLeft + 140, this.guiTop + 123, 89, 96);

                    for (var23 = 0; var23 < ((ArrayList)currentWar.get("agreements_waiting")).size(); ++var23)
                    {
                        var30 = this.guiLeft + 140;
                        var42 = Float.valueOf((float)(this.guiTop + 123 + 4 + var23 * 13) + this.getSlideWaitingAgreements());
                        var52 = Integer.valueOf(((Double)((LinkedTreeMap)((ArrayList)currentWar.get("agreements_waiting")).get(var23)).get("id")).intValue());
                        var34 = (String)((LinkedTreeMap)((ArrayList)currentWar.get("agreements_waiting")).get(var23)).get("factionSender");
                        var37 = (String)((LinkedTreeMap)((ArrayList)currentWar.get("agreements_waiting")).get(var23)).get("type");
                        var40 = (String)((LinkedTreeMap)((ArrayList)currentWar.get("agreements_waiting")).get(var23)).get("status");
                        Long var57 = Long.valueOf(((Double)((LinkedTreeMap)((ArrayList)currentWar.get("agreements_waiting")).get(var23)).get("creationTime")).longValue());
                        ClientProxy.loadCountryFlag(var34);

                        if (ClientProxy.flagsTexture.containsKey(var34))
                        {
                            GL11.glBindTexture(GL11.GL_TEXTURE_2D, ((DynamicTexture)ClientProxy.flagsTexture.get(var34)).getGlTextureId());
                            ModernGui.drawScaledCustomSizeModalRect((float)(var30 + 5), (float)(var42.intValue() + 0), 0.0F, 0.0F, 156, 78, 17, 10, 156.0F, 78.0F, false);
                        }

                        ModernGui.drawScaledStringCustomFont((var52.intValue() != selectedAgreementId && var40.equals("refused") ? "\u00a7c" : (var52.intValue() != selectedAgreementId && var40.equals("expired") ? "\u00a7n" : "")) + I18n.getString("faction.enemy.agreement.type.short." + var37), (float)(var30 + 26), (float)(var42.intValue() + 2), var52.intValue() == selectedAgreementId ? 16514302 : 10395075, 0.5F, "left", false, "georamaMedium", 28);
                        delay = ModernGui.formatDelayTime(var57);
                        ModernGui.drawScaledStringCustomFont((var52.intValue() != selectedAgreementId && var40.equals("refused") ? "\u00a7c" : (var52.intValue() != selectedAgreementId && var40.equals("expired") ? "\u00a7n" : "")) + delay, (float)(var30 + 89 - 2), (float)(var42.intValue() + 2), var52.intValue() == selectedAgreementId ? 16514302 : 10395075, 0.5F, "right", false, "georamaMedium", 28);

                        if (mouseX >= var30 && mouseX <= var30 + 89 && mouseY >= var42.intValue() && mouseY <= var42.intValue() + 13)
                        {
                            this.hoveredAction = "open_agreement#" + var52;
                        }
                    }

                    GUIUtils.endGLScissor();

                    if (mouseX >= this.guiLeft + 140 && mouseX <= this.guiLeft + 140 + 89 && mouseY >= this.guiTop + 123 && mouseY <= this.guiTop + 123 + 96)
                    {
                        this.scrollBarAgreementsWaiting.draw(mouseX, mouseY);
                    }

                    Gui.drawRect(this.guiLeft + 241, this.guiTop + 89, this.guiLeft + this.xSize, this.guiTop + this.ySize, -14279619);

                    if (this.agreementCreationMode || selectedAgreementId != -1)
                    {
                        ClientEventHandler.STYLE.bindTexture("faction_war_2");
                        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 258), (float)(this.guiTop + 108), (float)(197 * GUI_SCALE), (float)(426 * GUI_SCALE), 95 * GUI_SCALE, 55 * GUI_SCALE, 95, 55, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 357), (float)(this.guiTop + 108), (float)(197 * GUI_SCALE), (float)(426 * GUI_SCALE), 95 * GUI_SCALE, 55 * GUI_SCALE, 95, 55, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                        ModernGui.drawScaledStringCustomFont(I18n.getString("faction.war.label.agreements.my_side"), (float)(this.guiLeft + 264), (float)(this.guiTop + 115), 16514302, 0.5F, "left", false, "georamaSemiBold", 30);
                        ModernGui.drawScaledStringCustomFont(I18n.getString("faction.war.label.agreements.other_side"), (float)(this.guiLeft + 363), (float)(this.guiTop + 115), 16514302, 0.5F, "left", false, "georamaSemiBold", 30);
                        ClientEventHandler.STYLE.bindTexture("faction_war_2");
                        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 258), (float)(this.guiTop + 167), (float)(0 * GUI_SCALE), (float)(428 * GUI_SCALE), 194 * GUI_SCALE, 52 * GUI_SCALE, 194, 52, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                    }

                    if (!this.agreementCreationMode)
                    {
                        if (((LinkedTreeMap)currentWar.get("agreements")).containsKey(selectedAgreementId + ""))
                        {
                            LinkedTreeMap var47 = (LinkedTreeMap)((LinkedTreeMap)currentWar.get("agreements")).get(selectedAgreementId + "");
                            ModernGui.drawScaledStringCustomFont(I18n.getString("faction.war.label.details_agreements"), (float)(this.guiLeft + 258), (float)(this.guiTop + 97), 16514302, 0.5F, "left", false, "georamaSemiBold", 28);
                            List var45 = Arrays.asList(((String)var47.get("conditions")).split(","));
                            var22 = 0;

                            for (Iterator var48 = this.agreementsAvailableConditions.iterator(); var48.hasNext(); ++var22)
                            {
                                var38 = (String)var48.next();
                                dateFormated = 0;
                                conditionsOTHER = 0;
                                Iterator var59 = var45.iterator();

                                while (var59.hasNext())
                                {
                                    agreementCondition = (String)var59.next();

                                    if (agreementCondition.contains(var38))
                                    {
                                        if (agreementCondition.contains((String)FactionGUI.factionInfos.get("name")))
                                        {
                                            dateFormated = Integer.parseInt(agreementCondition.split("#")[1]);
                                        }
                                        else
                                        {
                                            conditionsOTHER = Integer.parseInt(agreementCondition.split("#")[1]);
                                        }
                                    }
                                }

                                ClientEventHandler.STYLE.bindTexture("faction_war");
                                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 265), (float)(this.guiTop + 127 + 11 * var22), (float)(((Integer)iconsConditionsRewards.get(var38)).intValue() * GUI_SCALE), (float)((dateFormated != 0 ? 114 : 106) * GUI_SCALE), 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.enemy.agreement.conditions." + var38), (float)(this.guiLeft + 265 + 12), (float)(this.guiTop + 127 + 11 * var22), dateFormated != 0 ? 7239406 : 10395075, 0.5F, "left", false, "georamaSemiBold", 28);
                                ModernGui.drawScaledStringCustomFont(dateFormated + "", (float)(this.guiLeft + 323), (float)(this.guiTop + 127 + 11 * var22), 16514302, 0.5F, "left", false, "georamaSemiBold", 28);
                                ClientEventHandler.STYLE.bindTexture("faction_war");
                                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 364), (float)(this.guiTop + 127 + 11 * var22), (float)(((Integer)iconsConditionsRewards.get(var38)).intValue() * GUI_SCALE), (float)((conditionsOTHER != 0 ? 130 : 106) * GUI_SCALE), 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.enemy.agreement.conditions." + var38), (float)(this.guiLeft + 364 + 12), (float)(this.guiTop + 127 + 11 * var22), conditionsOTHER != 0 ? 15017020 : 10395075, 0.5F, "left", false, "georamaSemiBold", 28);
                                ModernGui.drawScaledStringCustomFont(conditionsOTHER + "", (float)(this.guiLeft + 421), (float)(this.guiTop + 127 + 11 * var22), 16514302, 0.5F, "left", false, "georamaSemiBold", 28);
                            }

                            ClientEventHandler.STYLE.bindTexture("faction_war_2");
                            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 280), (float)(this.guiTop + 170), (float)(((Integer)iconsAgreements.get(var47.get("type"))).intValue() * GUI_SCALE), (float)(430 * GUI_SCALE), 45 * GUI_SCALE, 45 * GUI_SCALE, 45, 45, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                            ModernGui.drawScaledStringCustomFont(I18n.getString("faction.war.label.agreements.informations"), (float)(this.guiLeft + 357), (float)(this.guiTop + 173), 16514302, 0.5F, "left", false, "georamaSemiBold", 30);
                            ModernGui.drawScaledStringCustomFont(I18n.getString("faction.enemy.agreement.status.short." + var47.get("status")), (float)(this.guiLeft + 357), (float)(this.guiTop + 183), 10395075, 0.5F, "left", false, "georamaSemiBold", 24);
                            ModernGui.drawScaledStringCustomFont(I18n.getString("faction.enemy.agreement.type." + var47.get("type")) + " (" + String.format("%.0f", new Object[] {(Double)var47.get("value")}) + I18n.getString("faction.common.days.short") + ")", (float)(this.guiLeft + 357), (float)(this.guiTop + 190), 10395075, 0.5F, "left", false, "georamaSemiBold", 24);
                            Date var49 = new Date(((Double)var47.get("creationTime")).longValue());

                            if (var47.get("status").equals("active"))
                            {
                                var49 = new Date(((Double)var47.get("signatureTime")).longValue());
                            }

                            SimpleDateFormat var60 = new SimpleDateFormat("dd/MM/yyyy");
                            var34 = var60.format(var49);
                            ModernGui.drawScaledStringCustomFont(I18n.getString(var47.get("status").equals("active") ? "faction.war.label.agreements.signed_at" : "faction.war.label.agreements.received_at").replaceAll("#date#", var34), (float)(this.guiLeft + 357), (float)(this.guiTop + 197), 10395075, 0.5F, "left", false, "georamaSemiBold", 24);

                            if (FactionGUI.hasPermissions("wars") && var47.get("status").equals("waiting") && FactionGUI.hasPermissions("wars") && !FactionGUI.factionInfos.get("id").equals(var47.get("factionSender")))
                            {
                                ClientEventHandler.STYLE.bindTexture("faction_war_2");

                                if (mouseX >= this.guiLeft + 357 && mouseX <= this.guiLeft + 357 + 39 && mouseY >= this.guiTop + 206 && mouseY <= this.guiTop + 206 + 11)
                                {
                                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 357), (float)(this.guiTop + 206), (float)(191 * GUI_SCALE), (float)(202 * GUI_SCALE), 39 * GUI_SCALE, 11 * GUI_SCALE, 39, 11, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                                    this.hoveredAction = "agreement_accept#" + ((Double)var47.get("id")).intValue();
                                }
                                else
                                {
                                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 357), (float)(this.guiTop + 206), (float)(191 * GUI_SCALE), (float)(188 * GUI_SCALE), 39 * GUI_SCALE, 11 * GUI_SCALE, 39, 11, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                                }

                                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.enemy.agreement.accept"), (float)(this.guiLeft + 357 + 20), (float)(this.guiTop + 208), 2234425, 0.5F, "center", false, "georamaSemiBold", 25);
                                ClientEventHandler.STYLE.bindTexture("faction_war_2");

                                if (mouseX >= this.guiLeft + 400 && mouseX <= this.guiLeft + 400 + 39 && mouseY >= this.guiTop + 206 && mouseY <= this.guiTop + 206 + 11)
                                {
                                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 400), (float)(this.guiTop + 206), (float)(191 * GUI_SCALE), (float)(202 * GUI_SCALE), 39 * GUI_SCALE, 11 * GUI_SCALE, 39, 11, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                                    ModernGui.drawScaledStringCustomFont(I18n.getString("faction.enemy.agreement.refuse"), (float)(this.guiLeft + 400 + 20), (float)(this.guiTop + 208), 2234425, 0.5F, "center", false, "georamaSemiBold", 25);
                                    this.hoveredAction = "agreement_refuse#" + ((Double)var47.get("id")).intValue();
                                }
                                else
                                {
                                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 400), (float)(this.guiTop + 206), (float)(237 * GUI_SCALE), (float)(202 * GUI_SCALE), 39 * GUI_SCALE, 11 * GUI_SCALE, 39, 11, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                                    ModernGui.drawScaledStringCustomFont(I18n.getString("faction.enemy.agreement.refuse"), (float)(this.guiLeft + 400 + 20), (float)(this.guiTop + 208), 14803951, 0.5F, "center", false, "georamaSemiBold", 25);
                                }
                            }
                            else if (FactionGUI.hasPermissions("wars") && var47.get("status").equals("waiting") && FactionGUI.hasPermissions("wars") && FactionGUI.factionInfos.get("id").equals(var47.get("factionSender")))
                            {
                                ClientEventHandler.STYLE.bindTexture("faction_war_2");

                                if (mouseX >= this.guiLeft + 400 && mouseX <= this.guiLeft + 400 + 39 && mouseY >= this.guiTop + 206 && mouseY <= this.guiTop + 206 + 11)
                                {
                                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 400), (float)(this.guiTop + 206), (float)(191 * GUI_SCALE), (float)(202 * GUI_SCALE), 39 * GUI_SCALE, 11 * GUI_SCALE, 39, 11, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                                    ModernGui.drawScaledStringCustomFont(I18n.getString("faction.enemy.agreement.cancel"), (float)(this.guiLeft + 400 + 20), (float)(this.guiTop + 208), 2234425, 0.5F, "center", false, "georamaSemiBold", 25);
                                    this.hoveredAction = "agreement_cancel#" + ((Double)var47.get("id")).intValue();
                                }
                                else
                                {
                                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 400), (float)(this.guiTop + 206), (float)(237 * GUI_SCALE), (float)(202 * GUI_SCALE), 39 * GUI_SCALE, 11 * GUI_SCALE, 39, 11, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                                    ModernGui.drawScaledStringCustomFont(I18n.getString("faction.enemy.agreement.cancel"), (float)(this.guiLeft + 400 + 20), (float)(this.guiTop + 208), 14803951, 0.5F, "center", false, "georamaSemiBold", 25);
                                }
                            }
                            else if (var47.get("status").equals("active"))
                            {
                                var49 = new Date(((Double)var47.get("signatureTime")).longValue() + ((Double)var47.get("value")).longValue() * 86400000L);
                                var60 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                                var34 = var60.format(var49);
                                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.war.label.agreements.end_at").replaceAll("#date#", var34), (float)(this.guiLeft + 357), (float)(this.guiTop + 210), 14803951, 0.5F, "left", false, "georamaSemiBold", 25);
                            }
                        }
                    }
                    else if (FactionGUI.hasPermissions("wars"))
                    {
                        var22 = 0;

                        for (var25 = this.agreementsAvailableConditions.iterator(); var25.hasNext(); ++var22)
                        {
                            var26 = (String)var25.next();
                            ClientEventHandler.STYLE.bindTexture("faction_war");

                            if (this.agreement_myConditionsInput.containsKey(var26))
                            {
                                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 265), (float)(this.guiTop + 127 + 11 * var22), (float)(((Integer)iconsConditionsRewards.get(var26)).intValue() * GUI_SCALE), (float)((FactionGUI.isNumeric(((GuiTextField)this.agreement_myConditionsInput.get(var26)).getText(), false) ? 114 : 106) * GUI_SCALE), 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.enemy.agreement.conditions." + var26), (float)(this.guiLeft + 265 + 12), (float)(this.guiTop + 127 + 11 * var22), FactionGUI.isNumeric(((GuiTextField)this.agreement_myConditionsInput.get(var26)).getText(), false) ? 7239406 : 10395075, 0.5F, "left", false, "georamaSemiBold", 28);
                                ((GuiTextField)this.agreement_myConditionsInput.get(var26)).drawTextBox();
                                ClientEventHandler.STYLE.bindTexture("faction_war_2");
                                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 323), (float)(this.guiTop + 127 + var22 * 11), (float)(191 * GUI_SCALE), (float)(176 * GUI_SCALE), 22 * GUI_SCALE, 9 * GUI_SCALE, 22, 9, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                            }

                            if (this.agreement_otherConditionsInput.containsKey(var26))
                            {
                                ClientEventHandler.STYLE.bindTexture("faction_war");
                                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 364), (float)(this.guiTop + 127 + 11 * var22), (float)(((Integer)iconsConditionsRewards.get(var26)).intValue() * GUI_SCALE), (float)((FactionGUI.isNumeric(((GuiTextField)this.agreement_otherConditionsInput.get(var26)).getText(), false) ? 130 : 106) * GUI_SCALE), 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.enemy.agreement.conditions." + var26), (float)(this.guiLeft + 364 + 12), (float)(this.guiTop + 127 + 11 * var22), FactionGUI.isNumeric(((GuiTextField)this.agreement_otherConditionsInput.get(var26)).getText(), false) ? 15017020 : 10395075, 0.5F, "left", false, "georamaSemiBold", 28);
                                ((GuiTextField)this.agreement_otherConditionsInput.get(var26)).drawTextBox();
                                ClientEventHandler.STYLE.bindTexture("faction_war_2");
                                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 422), (float)(this.guiTop + 127 + var22 * 11), (float)(191 * GUI_SCALE), (float)(176 * GUI_SCALE), 22 * GUI_SCALE, 9 * GUI_SCALE, 22, 9, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                            }
                        }

                        ModernGui.drawScaledStringCustomFont(I18n.getString("faction.war.label.agreements.type"), (float)(this.guiLeft + 264), (float)(this.guiTop + 174), 16514302, 0.5F, "left", false, "georamaSemiBold", 30);
                        var22 = 0;

                        for (var25 = this.agreementsAvailableTypes.iterator(); var25.hasNext(); ++var22)
                        {
                            var26 = (String)var25.next();
                            ClientEventHandler.STYLE.bindTexture("faction_global");
                            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 265), (float)(this.guiTop + 186 + 9 * var22), (float)(321 * GUI_SCALE), (float)((this.agreement_form_type.equals(var26) ? 181 : 190) * GUI_SCALE), 6 * GUI_SCALE, 6 * GUI_SCALE, 6, 6, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);

                            if (mouseX >= this.guiLeft + 265 && mouseX <= this.guiLeft + 265 + 6 && mouseY >= this.guiTop + 186 + 9 * var22 && mouseY <= this.guiTop + 186 + 9 * var22 + 6)
                            {
                                this.hoveredAction = "agreement_select_type#" + var26;
                            }

                            ModernGui.drawScaledStringCustomFont(I18n.getString("faction.enemy.agreement.type." + var26), (float)(this.guiLeft + 265 + 10), (float)(this.guiTop + 185 + 9 * var22), 10395075, 0.5F, "left", false, "georamaSemiBold", 28);
                        }

                        ModernGui.drawScaledStringCustomFont(I18n.getString("faction.war.label.agreements.duration"), (float)(this.guiLeft + 363), (float)(this.guiTop + 174), 16514302, 0.5F, "left", false, "georamaSemiBold", 30);
                        ClientEventHandler.STYLE.bindTexture("faction_war_2");
                        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 363), (float)(this.guiTop + 184), (float)(191 * GUI_SCALE), (float)(176 * GUI_SCALE), 22 * GUI_SCALE, 9 * GUI_SCALE, 22, 9, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                        this.agreement_form_duration.drawTextBox();
                        ModernGui.drawScaledStringCustomFont(I18n.getString("faction.enemy.rewards.valueType.dayLong") + " (max. 120)", (float)(this.guiLeft + 388), (float)(this.guiTop + 185), 10395075, 0.5F, "left", false, "georamaSemiBold", 28);

                        if (FactionGUI.hasPermissions("wars"))
                        {
                            ClientEventHandler.STYLE.bindTexture("faction_war_2");

                            if (mouseX >= this.guiLeft + 363 && mouseX <= this.guiLeft + 363 + 39 && mouseY >= this.guiTop + 206 && mouseY <= this.guiTop + 206 + 11)
                            {
                                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 363), (float)(this.guiTop + 206), (float)(191 * GUI_SCALE), (float)(202 * GUI_SCALE), 39 * GUI_SCALE, 11 * GUI_SCALE, 39, 11, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                                this.hoveredAction = "agreement_create";
                            }
                            else
                            {
                                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 363), (float)(this.guiTop + 206), (float)(191 * GUI_SCALE), (float)(188 * GUI_SCALE), 39 * GUI_SCALE, 11 * GUI_SCALE, 39, 11, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                            }

                            ModernGui.drawScaledStringCustomFont(I18n.getString("faction.enemy.agreement.submit"), (float)(this.guiLeft + 363 + 20), (float)(this.guiTop + 208), 2234425, 0.5F, "center", false, "georamaSemiBold", 25);
                            ClientEventHandler.STYLE.bindTexture("faction_war_2");

                            if (mouseX >= this.guiLeft + 406 && mouseX <= this.guiLeft + 406 + 39 && mouseY >= this.guiTop + 206 && mouseY <= this.guiTop + 206 + 11)
                            {
                                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 406), (float)(this.guiTop + 206), (float)(191 * GUI_SCALE), (float)(202 * GUI_SCALE), 39 * GUI_SCALE, 11 * GUI_SCALE, 39, 11, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.common.back"), (float)(this.guiLeft + 406 + 20), (float)(this.guiTop + 208), 2234425, 0.5F, "center", false, "georamaSemiBold", 25);
                                this.hoveredAction = "agreement_return_form";
                            }
                            else
                            {
                                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 406), (float)(this.guiTop + 206), (float)(237 * GUI_SCALE), (float)(202 * GUI_SCALE), 39 * GUI_SCALE, 11 * GUI_SCALE, 39, 11, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.common.back"), (float)(this.guiLeft + 406 + 20), (float)(this.guiTop + 208), 14803951, 0.5F, "center", false, "georamaSemiBold", 25);
                            }
                        }
                    }
                }
            }
            else
            {
                ModernGui.bindRemoteTexture("https://static.nationsglory.fr/N33y222_3N.png");
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 30 + 154), (float)(this.guiTop + 0), 0.0F, 0.0F, 279 * GUI_SCALE, 110 * GUI_SCALE, 279, 89, (float)(279 * GUI_SCALE), (float)(110 * GUI_SCALE), false);
                ClientEventHandler.STYLE.bindTexture("faction_global");
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 30), (float)(this.guiTop + 0), (float)(33 * GUI_SCALE), (float)(280 * GUI_SCALE), 433 * GUI_SCALE, 89 * GUI_SCALE, 433, 89, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                ClientEventHandler.STYLE.bindTexture("faction_war");
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 292), (float)(this.guiTop - 15), (float)(130 * GUI_SCALE), (float)(173 * GUI_SCALE), 130 * GUI_SCALE, 104 * GUI_SCALE, 130, 104, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.war.title"), (float)(this.guiLeft + 43), (float)(this.guiTop + 16), 16777215, 0.75F, "left", false, "georamaSemiBold", 32);
                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.war.label.no_war_in_progress"), (float)(this.guiLeft + 43), (float)(this.guiTop + 105), 16514302, 0.5F, "left", false, "georamaSemiBold", 32);
            }

            ModernGui.drawScaledStringCustomFont((String)FactionGUI.factionInfos.get("name"), (float)(this.guiLeft + 43), (float)(this.guiTop + 6), 10395075, 0.5F, "left", false, "georamaMedium", 32);
        }

        super.drawScreen(mouseX, mouseY, partialTick);
    }

    public void drawScreen(int mouseX, int mouseY) {}

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        Iterator it = this.conditionsInput.entrySet().iterator();
        Entry pair;

        while (it.hasNext())
        {
            pair = (Entry)it.next();
            ((GuiTextField)pair.getValue()).updateCursorCounter();
        }

        it = this.rewardsInput.entrySet().iterator();

        while (it.hasNext())
        {
            pair = (Entry)it.next();
            ((GuiTextField)pair.getValue()).updateCursorCounter();
        }

        it = this.agreement_myConditionsInput.entrySet().iterator();

        while (it.hasNext())
        {
            pair = (Entry)it.next();
            ((GuiTextField)pair.getValue()).updateCursorCounter();
        }

        it = this.agreement_otherConditionsInput.entrySet().iterator();

        while (it.hasNext())
        {
            pair = (Entry)it.next();
            ((GuiTextField)pair.getValue()).updateCursorCounter();
        }

        this.agreement_form_duration.updateCursorCounter();
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char typedChar, int keyCode)
    {
        Iterator it = this.conditionsInput.entrySet().iterator();
        Entry pair;

        while (it.hasNext())
        {
            pair = (Entry)it.next();
            ((GuiTextField)pair.getValue()).textboxKeyTyped(typedChar, keyCode);
        }

        it = this.rewardsInput.entrySet().iterator();

        while (it.hasNext())
        {
            pair = (Entry)it.next();

            if (loaded && currentWarIndex != -1 && factionsWarInfos.size() > currentWarIndex && ((String)pair.getKey()).equals("claims"))
            {
                LinkedTreeMap currentWarRequest = (LinkedTreeMap)((TreeMap)factionsWarInfos.get(currentWarIndex)).get("warRequest");

                if (!((String)currentWarRequest.get("reason")).equals("territorial_expansion"))
                {
                    continue;
                }
            }

            ((GuiTextField)pair.getValue()).textboxKeyTyped(typedChar, keyCode);
        }

        it = this.agreement_myConditionsInput.entrySet().iterator();

        while (it.hasNext())
        {
            pair = (Entry)it.next();
            ((GuiTextField)pair.getValue()).textboxKeyTyped(typedChar, keyCode);
        }

        it = this.agreement_otherConditionsInput.entrySet().iterator();

        while (it.hasNext())
        {
            pair = (Entry)it.next();
            ((GuiTextField)pair.getValue()).textboxKeyTyped(typedChar, keyCode);
        }

        this.agreement_form_duration.textboxKeyTyped(typedChar, keyCode);
        super.keyTyped(typedChar, keyCode);
    }

    private float getSlideMissiles()
    {
        return ((ArrayList)((TreeMap)factionsWarInfos.get(currentWarIndex)).get("missiles")).size() > 6 ? (float)(-(((ArrayList)((TreeMap)factionsWarInfos.get(currentWarIndex)).get("missiles")).size() - 6) * 12) * this.scrollBarMissiles.getSliderValue() : 0.0F;
    }

    private float getSlideAssaults()
    {
        return ((ArrayList)((TreeMap)factionsWarInfos.get(currentWarIndex)).get("assaults")).size() > 6 ? (float)(-(((ArrayList)((TreeMap)factionsWarInfos.get(currentWarIndex)).get("assaults")).size() - 6) * 12) * this.scrollBarAssaults.getSliderValue() : 0.0F;
    }

    private float getSlideConditions()
    {
        List conditions = Arrays.asList(((String)((LinkedTreeMap)((TreeMap)factionsWarInfos.get(currentWarIndex)).get("warRequest")).get("conditions")).split(","));
        return conditions.size() > 5 ? (float)(-(conditions.size() - 5) * 9) * this.scrollBarConditions.getSliderValue() : 0.0F;
    }

    private float getSlideActiveAgreements()
    {
        return ((ArrayList)((TreeMap)factionsWarInfos.get(currentWarIndex)).get("agreements_active")).size() > 7 ? (float)(-(((ArrayList)((TreeMap)factionsWarInfos.get(currentWarIndex)).get("agreements_active")).size() - 7) * 13) * this.scrollBarAgreementsActive.getSliderValue() : 0.0F;
    }

    private float getSlideWaitingAgreements()
    {
        return ((ArrayList)((TreeMap)factionsWarInfos.get(currentWarIndex)).get("agreements_waiting")).size() > 7 ? (float)(-(((ArrayList)((TreeMap)factionsWarInfos.get(currentWarIndex)).get("agreements_waiting")).size() - 7) * 13) * this.scrollBarAgreementsWaiting.getSliderValue() : 0.0F;
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        Entry pair;

        if (mouseButton == 0 && !this.hoveredAction.isEmpty())
        {
            this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);

            if (this.hoveredAction.equals("edit_photo"))
            {
                ClientData.lastCaptureScreenshot.put("wars", Long.valueOf(System.currentTimeMillis()));
                Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
                Minecraft.getMinecraft().thePlayer.sendChatToPlayer(ChatMessageComponent.createFromText(I18n.getString("faction.take_picture")));
            }
            else if (this.hoveredAction.contains("switch_war"))
            {
                currentWarIndex = Integer.parseInt(this.hoveredAction.replace("switch_war#", ""));
                this.defenserCounterPropositionMode = false;
                this.agreementMode = false;
                this.cachedAssaults.clear();

                if (((LinkedTreeMap)((TreeMap)factionsWarInfos.get(currentWarIndex)).get("agreements")).size() > 0)
                {
                    LinkedTreeMap it = (LinkedTreeMap)((TreeMap)factionsWarInfos.get(currentWarIndex)).get("agreements");
                    pair = (Entry)it.entrySet().iterator().next();
                    selectedAgreementId = Integer.parseInt((String)pair.getKey());
                }
            }
            else if (this.hoveredAction.contains("conditions_cumulative"))
            {
                this.conditionsCumulative = this.hoveredAction.contains("true");
            }
            else if (this.hoveredAction.contains("open_accords"))
            {
                this.agreementMode = !this.agreementMode;
            }
            else if (this.hoveredAction.equals("accept_conditions_def"))
            {
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionEnemyRequestUpdateStatusPacket(Integer.valueOf(((Double)((TreeMap)factionsWarInfos.get(currentWarIndex)).get("warId")).intValue()), "in_progress", (String)((TreeMap)factionsWarInfos.get(currentWarIndex)).get("status"), (String)((LinkedTreeMap)((TreeMap)factionsWarInfos.get(currentWarIndex)).get("warRequest")).get("factionATT"), (String)((LinkedTreeMap)((TreeMap)factionsWarInfos.get(currentWarIndex)).get("warRequest")).get("factionDEF"))));
                this.mc.displayGuiScreen(new WarGUI());
            }
            else if (this.hoveredAction.equals("refuse_conditions_def"))
            {
                this.defenserCounterPropositionMode = true;
            }
            else if (this.hoveredAction.equals("return_counter_def"))
            {
                this.defenserCounterPropositionMode = false;
            }
            else if (this.hoveredAction.equals("accept_conditions_att_second"))
            {
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionEnemyRequestUpdateStatusPacket(Integer.valueOf(((Double)((TreeMap)factionsWarInfos.get(currentWarIndex)).get("warId")).intValue()), "in_progress", (String)((TreeMap)factionsWarInfos.get(currentWarIndex)).get("status"), (String)((LinkedTreeMap)((TreeMap)factionsWarInfos.get(currentWarIndex)).get("warRequest")).get("factionATT"), (String)((LinkedTreeMap)((TreeMap)factionsWarInfos.get(currentWarIndex)).get("warRequest")).get("factionDEF"))));
                this.mc.displayGuiScreen(new WarGUI());
            }
            else if (this.hoveredAction.equals("refuse_conditions_att_second"))
            {
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionEnemyRequestGenerateConditionsPacket(Integer.valueOf(((Double)((TreeMap)factionsWarInfos.get(currentWarIndex)).get("warId")).intValue()))));
            }
            else if (this.hoveredAction.equals("nextTab"))
            {
                ++this.tabsOffset;
            }
            else if (this.hoveredAction.equals("previousTab"))
            {
                --this.tabsOffset;
            }
            else if (this.hoveredAction.contains("open_agreement"))
            {
                selectedAgreementId = Integer.parseInt(this.hoveredAction.replaceAll("open_agreement#", ""));
                this.agreementCreationMode = false;
            }
            else if (this.hoveredAction.contains("add_agreement"))
            {
                this.agreementCreationMode = true;
            }
            else if (this.hoveredAction.contains("agreement_select_type"))
            {
                this.agreement_form_type = this.hoveredAction.replaceAll("agreement_select_type#", "");
            }
            else if (this.hoveredAction.contains("agreement_return_form"))
            {
                this.agreementCreationMode = false;
            }
            else if (this.hoveredAction.contains("agreement_cancel"))
            {
                int var9 = Integer.parseInt(this.hoveredAction.replaceAll("agreement_cancel#", ""));
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionEnemyAgreementCancelPacket(Integer.valueOf(var9))));
                selectedAgreementId = -1;
            }
            else
            {
                Iterator var6;
                String reward;
                String var10;
                String var11;

                if (this.hoveredAction.equals("agreement_create"))
                {
                    if (!this.agreement_form_duration.getText().isEmpty() && Integer.parseInt(this.agreement_form_duration.getText()) > 120)
                    {
                        return;
                    }

                    var10 = "";

                    if (FactionGUI.factionInfos.get("name").equals(((TreeMap)factionsWarInfos.get(currentWarIndex)).get("factionATTName")))
                    {
                        var10 = (String)((TreeMap)factionsWarInfos.get(currentWarIndex)).get("factionDEFName");
                    }
                    else
                    {
                        var10 = (String)((TreeMap)factionsWarInfos.get(currentWarIndex)).get("factionATTName");
                    }

                    var11 = "";
                    var6 = this.agreementsAvailableConditions.iterator();

                    while (var6.hasNext())
                    {
                        reward = (String)var6.next();

                        if (this.agreement_myConditionsInput.containsKey(reward) && FactionGUI.isNumeric(((GuiTextField)this.agreement_myConditionsInput.get(reward)).getText(), false))
                        {
                            var11 = var11 + reward + "#" + ((GuiTextField)this.agreement_myConditionsInput.get(reward)).getText() + "#" + FactionGUI.factionInfos.get("name") + ",";
                        }
                        else if (this.agreement_otherConditionsInput.containsKey(reward) && FactionGUI.isNumeric(((GuiTextField)this.agreement_otherConditionsInput.get(reward)).getText(), false))
                        {
                            var11 = var11 + reward + "#" + ((GuiTextField)this.agreement_otherConditionsInput.get(reward)).getText() + "#" + var10 + ",";
                        }
                    }

                    PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionEnemyAgreementCreatePacket(Integer.valueOf(((Double)((TreeMap)factionsWarInfos.get(currentWarIndex)).get("warId")).intValue()), this.agreement_form_type, Integer.parseInt(this.agreement_form_duration.getText()), var11)));
                    this.agreementCreationMode = false;
                    this.agreement_form_duration.setText("0");
                    var6 = this.agreementsAvailableConditions.iterator();

                    while (var6.hasNext())
                    {
                        reward = (String)var6.next();
                        ((GuiTextField)this.agreement_myConditionsInput.get(reward)).setText("0");
                        ((GuiTextField)this.agreement_otherConditionsInput.get(reward)).setText("0");
                    }

                    this.mc.displayGuiScreen(new WarGUI());
                }
                else if (!this.hoveredAction.equals("send_conditions_att") && !this.hoveredAction.equals("send_conditions_def"))
                {
                    if (this.hoveredAction.contains("open_forum"))
                    {
                        try
                        {
                            Class var12 = Class.forName("java.awt.Desktop");
                            Object var14 = var12.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
                            var12.getMethod("browse", new Class[] {URI.class}).invoke(var14, new Object[] {URI.create((String)((LinkedTreeMap)((TreeMap)factionsWarInfos.get(currentWarIndex)).get("warRequest")).get("linkForum"))});
                        }
                        catch (Throwable var8)
                        {
                            var8.printStackTrace();
                        }
                    }
                    else if (!this.hoveredAction.contains("agreement_accept") && !this.hoveredAction.contains("agreement_refuse"))
                    {
                        if (this.hoveredAction.contains("surrender"))
                        {
                            this.mc.displayGuiScreen(new SurrenderConfirmGui(this, ((Double)((TreeMap)factionsWarInfos.get(currentWarIndex)).get("warId")).intValue(), (String)FactionGUI.factionInfos.get("name")));
                        }
                        else if (this.hoveredAction.contains("rollback_assault"))
                        {
                            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new RollbackAssaultDataPacket(this.hoveredAction.replaceAll("rollback_assault#", ""), (String)((TreeMap)factionsWarInfos.get(currentWarIndex)).get("name"))));
                            Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
                        }
                    }
                    else
                    {
                        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionEnemyAgreementUpdateStatusPacket(Integer.valueOf(Integer.parseInt(this.hoveredAction.replaceAll("[^\\d.]", ""))), this.hoveredAction.contains("accept") ? "active" : "refused")));
                        selectedAgreementId = -1;
                        this.scrollBarAgreementsWaiting.reset();
                        this.scrollBarAgreementsActive.reset();
                    }
                }
                else
                {
                    var10 = "";
                    var11 = "";
                    var6 = this.availableConditions.iterator();

                    while (var6.hasNext())
                    {
                        reward = (String)var6.next();

                        if (this.conditionsInput.containsKey(reward) && FactionGUI.isNumeric(((GuiTextField)this.conditionsInput.get(reward)).getText(), false))
                        {
                            var10 = var10 + reward + "#" + Integer.parseInt(((GuiTextField)this.conditionsInput.get(reward)).getText()) + ",";
                        }
                    }

                    var6 = this.availableRewards.iterator();

                    while (var6.hasNext())
                    {
                        reward = (String)var6.next();

                        if (this.rewardsInput.containsKey(reward) && FactionGUI.isNumeric(((GuiTextField)this.rewardsInput.get(reward)).getText(), false))
                        {
                            var11 = var11 + reward + "#" + Integer.parseInt(((GuiTextField)this.rewardsInput.get(reward)).getText()) + ",";

                            if (reward.equals("peace") && Integer.parseInt(((GuiTextField)this.rewardsInput.get(reward)).getText()) > 120)
                            {
                                return;
                            }
                        }
                    }

                    var10 = var10.replaceAll(",$", "");
                    var11 = var11.replaceAll(",$", "");

                    if (var10.isEmpty())
                    {
                        return;
                    }

                    PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionEnemyRequestUpdateConditionsPacket(Integer.valueOf(((Double)((TreeMap)factionsWarInfos.get(currentWarIndex)).get("warId")).intValue()), this.hoveredAction.replace("send_conditions_", ""), this.conditionsCumulative ? "and" : "or", var10, var11)));
                    this.mc.displayGuiScreen(new WarGUI());
                }
            }

            this.hoveredAction = "";
        }

        Iterator var13 = this.conditionsInput.entrySet().iterator();

        while (var13.hasNext())
        {
            pair = (Entry)var13.next();
            ((GuiTextField)pair.getValue()).mouseClicked(mouseX, mouseY, mouseButton);
        }

        var13 = this.rewardsInput.entrySet().iterator();

        while (var13.hasNext())
        {
            pair = (Entry)var13.next();
            ((GuiTextField)pair.getValue()).mouseClicked(mouseX, mouseY, mouseButton);
        }

        var13 = this.agreement_myConditionsInput.entrySet().iterator();

        while (var13.hasNext())
        {
            pair = (Entry)var13.next();
            ((GuiTextField)pair.getValue()).mouseClicked(mouseX, mouseY, mouseButton);
        }

        var13 = this.agreement_otherConditionsInput.entrySet().iterator();

        while (var13.hasNext())
        {
            pair = (Entry)var13.next();
            ((GuiTextField)pair.getValue()).mouseClicked(mouseX, mouseY, mouseButton);
        }

        this.agreement_form_duration.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    private String convertDate(String time)
    {
        String date = "";
        long diff = System.currentTimeMillis() - Long.parseLong(time);
        long days = diff / 86400000L;
        long hours = 0L;
        long minutes = 0L;
        long seconds = 0L;

        if (days == 0L)
        {
            hours = diff / 3600000L;

            if (hours == 0L)
            {
                minutes = diff / 60000L;

                if (minutes == 0L)
                {
                    seconds = diff / 1000L;
                    date = date + " " + seconds + " " + I18n.getString("faction.common.seconds");
                }
                else
                {
                    date = date + " " + minutes + " " + I18n.getString("faction.common.minutes");
                }
            }
            else
            {
                date = date + " " + hours + " " + I18n.getString("faction.common.hours");
            }
        }
        else
        {
            date = date + " " + days + " " + I18n.getString("faction.common.days");
        }

        return date;
    }
}
