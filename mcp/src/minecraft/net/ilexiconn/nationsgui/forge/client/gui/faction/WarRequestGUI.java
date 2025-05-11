/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiTextField
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.network.packet.Packet
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.io.Serializable;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionEnemyRequestGenerateConditionsPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionEnemyRequestPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionEnemyRequestUpdateConditionsPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionEnemyRequestUpdateForumPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionEnemyRequestUpdateStatusPacket;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;
import org.lwjgl.opengl.GL11;

public class WarRequestGUI
extends GuiScreen {
    protected int xSize = 319;
    protected int ySize = 248;
    private int guiLeft;
    private int guiTop;
    private RenderItem itemRenderer = new RenderItem();
    public static HashMap<String, Object> warInfos = new HashMap();
    public static boolean loaded = false;
    private GuiTextField linkForumInput;
    private GuiTextField conditionInput;
    private GuiTextField rewardInput;
    private GuiScrollBarFaction scrollBarAvailableConditions;
    private GuiScrollBarFaction scrollBarAvailableRewards;
    private GuiScrollBarFaction scrollBarConditions;
    private GuiScrollBarFaction scrollBarRewards;
    private GuiScrollBarFaction scrollBarFinishConditions;
    private GuiScrollBarFaction scrollBarFinishRewards;
    public static int warRequestId;
    private GuiScreen guiFrom;
    public String hoveredAction = "";
    public HashMap<String, Integer> conditions = new HashMap();
    public HashMap<String, Integer> rewards = new HashMap();
    public boolean conditionsTypeOpened = false;
    public boolean conditionsOpened = false;
    public boolean rewardsOpened = false;
    public String hoveredConditionsType = "";
    public String hoveredAvailableCondition = "";
    public String hoveredCondition = "";
    public String hoveredAvailableReward = "";
    public String hoveredReward = "";
    public String selectedConditionsType = "and";
    public String selectedCondition = "";
    public List<String> availableConditions = Arrays.asList("kill", "victory", "missile_point", "assault_point", "anti", "red");
    public String selectedReward = "";
    public ArrayList<String> availableRewards = new ArrayList<String>(Arrays.asList("dollars", "power", "claims", "peace"));
    public HashMap<String, Integer> data_ATT = null;
    public HashMap<String, Integer> data_DEF = null;
    public String winner = null;

    public WarRequestGUI(int warRequestId, GuiScreen guiFrom) {
        WarRequestGUI.warRequestId = warRequestId;
        this.guiFrom = guiFrom;
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        loaded = false;
        this.guiLeft = (this.field_73880_f - this.xSize) / 2;
        this.guiTop = (this.field_73881_g - this.ySize) / 2;
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionEnemyRequestPacket(warRequestId)));
        this.scrollBarAvailableConditions = new GuiScrollBarFaction(this.guiLeft + 280, this.guiTop + 100, 71);
        this.scrollBarAvailableRewards = new GuiScrollBarFaction(this.guiLeft + 280, this.guiTop + 172, 71);
        this.scrollBarConditions = new GuiScrollBarFaction(this.guiLeft + 302, this.guiTop + 104, 41);
        this.scrollBarRewards = new GuiScrollBarFaction(this.guiLeft + 302, this.guiTop + 175, 41);
        this.scrollBarFinishConditions = new GuiScrollBarFaction(this.guiLeft + 302, this.guiTop + 99, 55);
        this.scrollBarFinishRewards = new GuiScrollBarFaction(this.guiLeft + 302, this.guiTop + 174, 55);
        this.linkForumInput = new GuiTextField(this.field_73886_k, this.guiLeft + 48, this.guiTop + 126, 237, 10);
        this.linkForumInput.func_73786_a(false);
        this.linkForumInput.func_73804_f(200);
        this.conditionInput = new GuiTextField(this.field_73886_k, this.guiLeft + 153, this.guiTop + 85, 47, 10);
        this.conditionInput.func_73786_a(false);
        this.conditionInput.func_73804_f(3);
        this.conditionInput.func_73782_a("0");
        this.rewardInput = new GuiTextField(this.field_73886_k, this.guiLeft + 150, this.guiTop + 157, 50, 10);
        this.rewardInput.func_73786_a(false);
        this.rewardInput.func_73804_f(7);
        this.rewardInput.func_73782_a("0");
        this.selectedCondition = this.availableConditions.get(0);
        this.selectedReward = this.availableRewards.get(0);
        this.availableRewards = new ArrayList<String>(Arrays.asList("dollars", "power", "claims", "peace"));
    }

    public void func_73876_c() {
        this.linkForumInput.func_73780_a();
        this.conditionInput.func_73780_a();
        this.rewardInput.func_73780_a();
    }

    public void func_73863_a(int mouseX, int mouseY, float par3) {
        this.func_73873_v_();
        this.hoveredAction = "";
        this.hoveredConditionsType = "";
        this.hoveredAvailableCondition = "";
        this.hoveredCondition = "";
        this.hoveredReward = "";
        this.hoveredAvailableReward = "";
        String tooltipToDraw = "";
        if (loaded && warInfos.size() > 0) {
            if (warInfos.get("reason").equals("colony_refusal") && warInfos.get("status").equals("waiting_conditions_att") && !this.availableRewards.contains("colonisation")) {
                this.availableRewards.add("colonisation");
            }
            String MAIN_TEXTURE = this.bindTextureDependingOnStatus();
            ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512.0f, 512.0f, false);
            if (mouseX >= this.guiLeft + 305 && mouseX <= this.guiLeft + 305 + 9 && mouseY >= this.guiTop - 6 && mouseY <= this.guiTop - 6 + 10) {
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 305, this.guiTop - 6, 46, 258, 9, 10, 512.0f, 512.0f, false);
            } else {
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 305, this.guiTop - 6, 46, 248, 9, 10, 512.0f, 512.0f, false);
            }
            GL11.glPushMatrix();
            GL11.glTranslatef((float)(this.guiLeft + 14), (float)(this.guiTop + 210), (float)0.0f);
            GL11.glRotatef((float)-90.0f, (float)0.0f, (float)0.0f, (float)1.0f);
            GL11.glTranslatef((float)(-(this.guiLeft + 16)), (float)(-(this.guiTop + 210)), (float)0.0f);
            this.drawScaledString(I18n.func_135053_a((String)"faction.enemy.title"), this.guiLeft + 14, this.guiTop + 210, 0xFFFFFF, 1.5f, false, false);
            GL11.glPopMatrix();
            ClientProxy.loadCountryFlag((String)warInfos.get("factionATT"));
            ClientProxy.loadCountryFlag((String)warInfos.get("factionDEF"));
            if (ClientProxy.flagsTexture.containsKey((String)warInfos.get("factionATT"))) {
                GL11.glBindTexture((int)3553, (int)ClientProxy.flagsTexture.get((String)warInfos.get("factionATT")).func_110552_b());
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 57, this.guiTop + 27, 0.0f, 0.0f, 156, 78, 27, 15, 156.0f, 78.0f, false);
            }
            if (ClientProxy.flagsTexture.containsKey((String)warInfos.get("factionDEF"))) {
                GL11.glBindTexture((int)3553, (int)ClientProxy.flagsTexture.get((String)warInfos.get("factionDEF")).func_110552_b());
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 193, this.guiTop + 27, 0.0f, 0.0f, 156, 78, 27, 15, 156.0f, 78.0f, false);
            }
            if (((String)warInfos.get("factionATT")).contains("Empire")) {
                this.drawScaledString("Empire", this.guiLeft + 90, this.guiTop + 27, 0xFFFFFF, 1.0f, false, false);
                this.drawScaledString(((String)warInfos.get("factionATT")).replace("Empire", ""), this.guiLeft + 90, this.guiTop + 37, 0xFFFFFF, 1.0f, false, false);
            } else {
                this.drawScaledString((String)warInfos.get("factionATT"), this.guiLeft + 90, this.guiTop + 32, 0xFFFFFF, 1.0f, false, false);
            }
            if (((String)warInfos.get("factionDEF")).contains("Empire")) {
                this.drawScaledString("Empire", this.guiLeft + 226, this.guiTop + 27, 0xFFFFFF, 1.0f, false, false);
                this.drawScaledString(((String)warInfos.get("factionDEF")).replace("Empire", ""), this.guiLeft + 226, this.guiTop + 37, 0xFFFFFF, 1.0f, false, false);
            } else {
                this.drawScaledString((String)warInfos.get("factionDEF"), this.guiLeft + 226, this.guiTop + 32, 0xFFFFFF, 1.0f, false, false);
            }
            if (!warInfos.get("status").equals("in_progress") && !warInfos.get("status").equals("finished")) {
                String status = I18n.func_135053_a((String)("faction.enemy.status." + warInfos.get("status") + ".att"));
                this.drawScaledString(status, this.guiLeft + 108 + 6, this.guiTop + 58, 0xFFFFFF, 1.0f, true, false);
                ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                if (status.startsWith("\u00a72")) {
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 108 - this.field_73886_k.func_78256_a(status) / 2 - 8, this.guiTop + 56, 110, 251, 10, 11, 512.0f, 512.0f, false);
                } else if (status.startsWith("\u00a74")) {
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 108 - this.field_73886_k.func_78256_a(status) / 2 - 8, this.guiTop + 56, 120, 251, 10, 11, 512.0f, 512.0f, false);
                } else {
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 108 - this.field_73886_k.func_78256_a(status) / 2 - 8, this.guiTop + 56, 130, 251, 10, 11, 512.0f, 512.0f, false);
                }
                status = I18n.func_135053_a((String)("faction.enemy.status." + warInfos.get("status") + ".def"));
                this.drawScaledString(status, this.guiLeft + 244 + 6, this.guiTop + 58, 0xFFFFFF, 1.0f, true, false);
                ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                if (status.startsWith("\u00a72")) {
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 244 - this.field_73886_k.func_78256_a(status) / 2 - 8, this.guiTop + 56, 110, 251, 10, 11, 512.0f, 512.0f, false);
                } else if (status.startsWith("\u00a74")) {
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 244 - this.field_73886_k.func_78256_a(status) / 2 - 8, this.guiTop + 56, 120, 251, 10, 11, 512.0f, 512.0f, false);
                } else {
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 244 - this.field_73886_k.func_78256_a(status) / 2 - 8, this.guiTop + 56, 130, 251, 10, 11, 512.0f, 512.0f, false);
                }
            } else if (this.winner == null) {
                String inactivityString = I18n.func_135053_a((String)"faction.enemy.inactivity") + ": " + (warInfos.get("inactivity_ATT") != null ? warInfos.get("inactivity_ATT") : Integer.valueOf(0)) + "%";
                this.drawScaledString(inactivityString, this.guiLeft + 108 - 6, this.guiTop + 58, 0xFFFFFF, 1.0f, true, false);
                ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 108 - 6 + this.field_73886_k.func_78256_a(inactivityString) / 2 + 2, this.guiTop + 56, 71, 251, 10, 11, 512.0f, 512.0f, false);
                if (mouseX >= this.guiLeft + 108 - 6 + this.field_73886_k.func_78256_a(inactivityString) / 2 + 2 && mouseX <= this.guiLeft + 108 - 6 + this.field_73886_k.func_78256_a(inactivityString) / 2 + 2 + 10 && mouseY >= this.guiTop + 56 && mouseY <= this.guiTop + 56 + 11) {
                    tooltipToDraw = I18n.func_135053_a((String)"faction.enemy.tooltip.inactivity");
                }
                inactivityString = I18n.func_135053_a((String)"faction.enemy.inactivity") + ": " + (warInfos.get("inactivity_DEF") != null ? warInfos.get("inactivity_DEF") : Integer.valueOf(0)) + "%";
                this.drawScaledString(inactivityString, this.guiLeft + 244 - 6, this.guiTop + 58, 0xFFFFFF, 1.0f, true, false);
                ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 244 - 6 + this.field_73886_k.func_78256_a(inactivityString) / 2 + 2, this.guiTop + 56, 71, 251, 10, 11, 512.0f, 512.0f, false);
                if (mouseX >= this.guiLeft + 244 - 6 + this.field_73886_k.func_78256_a(inactivityString) / 2 + 2 && mouseX <= this.guiLeft + 244 - 6 + this.field_73886_k.func_78256_a(inactivityString) / 2 + 2 + 10 && mouseY >= this.guiTop + 56 && mouseY <= this.guiTop + 56 + 11) {
                    tooltipToDraw = I18n.func_135053_a((String)"faction.enemy.tooltip.inactivity");
                }
            } else if (this.winner.equals(warInfos.get("factionATT"))) {
                this.drawScaledString(I18n.func_135053_a((String)"faction.enemy.winner"), this.guiLeft + 108 - 6, this.guiTop + 58, 0xFFFFFF, 1.0f, true, false);
                ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 108 + this.field_73886_k.func_78256_a(I18n.func_135053_a((String)"faction.enemy.winner")) / 2 - 6 + 2, this.guiTop + 56, 330, 22, 11, 11, 512.0f, 512.0f, false);
                this.drawScaledString(I18n.func_135053_a((String)"faction.enemy.looser"), this.guiLeft + 244 - 9, this.guiTop + 58, 0xFFFFFF, 1.0f, true, false);
                ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 244 + this.field_73886_k.func_78256_a(I18n.func_135053_a((String)"faction.enemy.looser")) / 2 - 9 + 2, this.guiTop + 56, 329, 34, 16, 11, 512.0f, 512.0f, false);
            } else {
                this.drawScaledString(I18n.func_135053_a((String)"faction.enemy.looser"), this.guiLeft + 108 - 6, this.guiTop + 58, 0xFFFFFF, 1.0f, true, false);
                ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 108 + this.field_73886_k.func_78256_a(I18n.func_135053_a((String)"faction.enemy.looser")) / 2 - 6 + 2, this.guiTop + 56, 329, 34, 16, 11, 512.0f, 512.0f, false);
                this.drawScaledString(I18n.func_135053_a((String)"faction.enemy.winner"), this.guiLeft + 244 - 9, this.guiTop + 58, 0xFFFFFF, 1.0f, true, false);
                ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 244 + this.field_73886_k.func_78256_a(I18n.func_135053_a((String)"faction.enemy.winner")) / 2 - 9 + 2, this.guiTop + 56, 330, 22, 11, 11, 512.0f, 512.0f, false);
            }
            if (warInfos.get("status").equals("waiting_validation") || warInfos.get("status").equals("refused") || warInfos.get("status").equals("cancelled") || warInfos.get("status").equals("waiting_conditions_att") && (!warInfos.get("factionATT").equals(warInfos.get("playerFaction")) || !((Boolean)warInfos.get("hasWarPermInOwnCountry")).booleanValue()) || warInfos.get("status").equals("waiting_conditions_def") && (!warInfos.get("factionDEF").equals(warInfos.get("playerFaction")) || !((Boolean)warInfos.get("hasWarPermInOwnCountry")).booleanValue()) || warInfos.get("status").equals("waiting_conditions_att_second")) {
                this.drawScaledString(I18n.func_135053_a((String)"faction.enemy.reason"), this.guiLeft + 46, this.guiTop + 78, 0, 1.0f, false, false);
                this.drawScaledString(I18n.func_135053_a((String)("faction.enemy.reason." + warInfos.get("reason"))), this.guiLeft + 50, this.guiTop + 94, 0xFFFFFF, 1.0f, false, false);
                if (warInfos.get("playerTarget") != null && !((String)warInfos.get("playerTarget")).isEmpty()) {
                    ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 159, this.guiTop + 92, 71, 251, 10, 11, 512.0f, 512.0f, false);
                    if (mouseX >= this.guiLeft + 159 && mouseX <= this.guiLeft + 159 + 10 && mouseY >= this.guiTop + 92 && mouseY <= this.guiTop + 92 + 11) {
                        tooltipToDraw = I18n.func_135053_a((String)"faction.enemy.tooltip.reason").replace("#target#", (String)warInfos.get("playerTarget"));
                    }
                }
                this.drawScaledString(I18n.func_135053_a((String)"faction.enemy.creationDate"), this.guiLeft + 182, this.guiTop + 78, 0, 1.0f, false, false);
                Date date = new Date(((Double)warInfos.get("creationTime")).longValue());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyy HH:mm");
                this.drawScaledString(simpleDateFormat.format(date), this.guiLeft + 186, this.guiTop + 94, 0xFFFFFF, 1.0f, false, false);
                this.drawScaledString(I18n.func_135053_a((String)"faction.enemy.linkForum"), this.guiLeft + 46, this.guiTop + 110, 0, 1.0f, false, false);
                if (!warInfos.get("reason").equals("under_power")) {
                    this.linkForumInput.func_73795_f();
                    if (this.linkForumInput.func_73781_b().isEmpty() && !((String)warInfos.get("linkForum")).isEmpty()) {
                        this.linkForumInput.func_73782_a((String)warInfos.get("linkForum"));
                    }
                    ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                    GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                    if (!this.linkForumInput.func_73781_b().isEmpty()) {
                        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 286, this.guiTop + 121, 148, 250, 20, 16, 512.0f, 512.0f, false);
                    } else {
                        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 286, this.guiTop + 121, 169, 250, 20, 16, 512.0f, 512.0f, false);
                    }
                } else {
                    this.drawScaledString(I18n.func_135053_a((String)"faction.enemy.noLinkForum"), this.guiLeft + 50, this.guiTop + 126, 0xFFFFFF, 1.0f, false, false);
                }
                String[] descriptionWords = null;
                descriptionWords = warInfos.get("reason").equals("under_power") ? I18n.func_135053_a((String)"faction.enemy.status.details.special.under_power").split(" ") : (!warInfos.get("status").equals("waiting_conditions_att_second") ? I18n.func_135053_a((String)("faction.enemy.status.details." + warInfos.get("status"))).split(" ") : (warInfos.get("factionATT").equals(warInfos.get("playerFaction")) ? I18n.func_135053_a((String)("faction.enemy.status.details." + warInfos.get("status") + ".att")).split(" ") : I18n.func_135053_a((String)("faction.enemy.status.details." + warInfos.get("status") + ".def")).split(" ")));
                String line = "";
                int lineNumber = 0;
                for (String descWord : descriptionWords) {
                    StringBuilder stringBuilder = new StringBuilder();
                    if ((double)this.field_73886_k.func_78256_a(stringBuilder.append(line).append(descWord).toString()) * 0.9 <= 190.0) {
                        if (!line.equals("")) {
                            line = line + " ";
                        }
                        line = line + descWord;
                        continue;
                    }
                    this.drawScaledString(line, this.guiLeft + 50, this.guiTop + 150 + lineNumber * 10, 0xFFFFFF, 0.9f, false, false);
                    ++lineNumber;
                    line = descWord;
                }
                this.drawScaledString(line, this.guiLeft + 50, this.guiTop + 150 + lineNumber * 10, 0xFFFFFF, 0.9f, false, false);
                if (warInfos.get("status").equals("waiting_conditions_att_second") && warInfos.get("factionATT").equals(warInfos.get("playerFaction"))) {
                    ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 319, this.guiTop + 12, 319, 12, 102, 222, 512.0f, 512.0f, false);
                    this.drawScaledString("\u00a77\u00a7l" + I18n.func_135053_a((String)"faction.enemy.contitions") + " \u00a74\u00a7l" + I18n.func_135053_a((String)("faction.enemy.conditionsType." + warInfos.get("conditionsType_DEF"))), this.guiLeft + 326, this.guiTop + 42, 0xFFFFFF, 1.0f, false, false);
                    int index = 1;
                    if (warInfos.get("conditions_DEF") != null) {
                        for (String condition : ((String)warInfos.get("conditions_DEF")).split(",")) {
                            this.drawScaledString("\u00a7f\u25cf " + condition.split("#")[1] + " " + I18n.func_135053_a((String)("faction.enemy.conditions." + condition.split("#")[0])), this.guiLeft + 326, this.guiTop + 42 + 10 * index, 0xFFFFFF, 1.0f, false, false);
                            ++index;
                        }
                    } else {
                        this.drawScaledString("\u00a7c\u2716 " + I18n.func_135053_a((String)"faction.enemy.none"), this.guiLeft + 326, this.guiTop + 42 + 10, 0xFFFFFF, 1.0f, false, false);
                    }
                    int offset = index * 10;
                    index = 1;
                    this.drawScaledString("\u00a77\u00a7l" + I18n.func_135053_a((String)"faction.enemy.rewards"), this.guiLeft + 326, this.guiTop + 62 + offset, 0xFFFFFF, 1.0f, false, false);
                    if (warInfos.get("rewards_DEF") != null && !((String)warInfos.get("rewards_DEF")).isEmpty()) {
                        for (String reward : ((String)warInfos.get("rewards_DEF")).split(",")) {
                            if (Integer.parseInt(reward.split("#")[1]) > 0) {
                                String valueType = "";
                                if (reward.split("#")[0].equals("peace")) {
                                    valueType = I18n.func_135053_a((String)"faction.enemy.rewards.valueType.day");
                                }
                                this.drawScaledString("\u00a7f\u25cf " + reward.split("#")[1] + valueType + " " + I18n.func_135053_a((String)("faction.enemy.rewards." + reward.split("#")[0])), this.guiLeft + 326, this.guiTop + 62 + offset + 10 * index, 0xFFFFFF, 1.0f, false, false);
                            } else {
                                this.drawScaledString("\u00a7f\u25cf " + I18n.func_135053_a((String)("faction.enemy.rewards." + reward.split("#")[0])), this.guiLeft + 326, this.guiTop + 62 + offset + 10 * index, 0xFFFFFF, 1.0f, false, false);
                            }
                            ++index;
                        }
                    } else {
                        this.drawScaledString("\u00a7c\u2716 " + I18n.func_135053_a((String)"faction.enemy.none"), this.guiLeft + 326, this.guiTop + 62 + offset + 10 * index, 0xFFFFFF, 1.0f, false, false);
                    }
                    if (warInfos.get("factionATT").equals(warInfos.get("playerFaction")) && ((Boolean)warInfos.get("hasWarPermInOwnCountry")).booleanValue()) {
                        ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 323, this.guiTop + 209, 0, 466, 90, 15, 512.0f, 512.0f, false);
                        if (warInfos.get("conditions_DEF") == null || warInfos.get("rewards_DEF") == null || mouseX >= this.guiLeft + 323 && mouseX <= this.guiLeft + 323 + 90 && mouseY >= this.guiTop + 209 && mouseY <= this.guiTop + 209 + 15) {
                            ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 323, this.guiTop + 209, 0, 481, 90, 15, 512.0f, 512.0f, false);
                            if (warInfos.get("conditions_DEF") != null && warInfos.get("rewards_DEF") != null) {
                                this.hoveredAction = "accept_conditions_att_second";
                            }
                        }
                        this.drawScaledString(I18n.func_135053_a((String)"faction.enemy.accept_conditions_def"), this.guiLeft + 368, this.guiTop + 213, 0xFFFFFF, 1.0f, true, false);
                    }
                }
                if (warInfos.get("status").equals("waiting_validation") && warInfos.get("factionATT").equals(warInfos.get("playerFaction")) && ((Boolean)warInfos.get("hasWarPermInOwnCountry")).booleanValue()) {
                    ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 46, this.guiTop + 219, 0, 432, 127, 15, 512.0f, 512.0f, false);
                    if (mouseX >= this.guiLeft + 46 && mouseX <= this.guiLeft + 46 + 127 && mouseY >= this.guiTop + 219 && mouseY <= this.guiTop + 219 + 15) {
                        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 46, this.guiTop + 219, 0, 447, 127, 15, 512.0f, 512.0f, false);
                        this.hoveredAction = "cancel";
                    }
                    this.drawScaledString(I18n.func_135053_a((String)"faction.enemy.cancel_request"), this.guiLeft + 110, this.guiTop + 223, 0xFFFFFF, 1.0f, true, false);
                    ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 181, this.guiTop + 219, 0, 402, 127, 15, 512.0f, 512.0f, false);
                    if (mouseX >= this.guiLeft + 181 && mouseX <= this.guiLeft + 181 + 127 && mouseY >= this.guiTop + 219 && mouseY <= this.guiTop + 219 + 15) {
                        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 181, this.guiTop + 219, 0, 417, 127, 15, 512.0f, 512.0f, false);
                        this.hoveredAction = "save_forum";
                    }
                    this.drawScaledString(I18n.func_135053_a((String)"faction.enemy.save_request"), this.guiLeft + 245, this.guiTop + 223, 0xFFFFFF, 1.0f, true, false);
                } else if (warInfos.get("status").equals("waiting_validation") && ((Boolean)warInfos.get("hasStaffPerm")).booleanValue()) {
                    ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 46, this.guiTop + 219, 0, 432, 127, 15, 512.0f, 512.0f, false);
                    if (mouseX >= this.guiLeft + 46 && mouseX <= this.guiLeft + 46 + 127 && mouseY >= this.guiTop + 219 && mouseY <= this.guiTop + 219 + 15) {
                        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 46, this.guiTop + 219, 0, 447, 127, 15, 512.0f, 512.0f, false);
                        this.hoveredAction = "staff_refuse";
                    }
                    this.drawScaledString(I18n.func_135053_a((String)"faction.enemy.refuse_request"), this.guiLeft + 110, this.guiTop + 223, 0xFFFFFF, 1.0f, true, false);
                    ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 181, this.guiTop + 219, 0, 402, 127, 15, 512.0f, 512.0f, false);
                    if (mouseX >= this.guiLeft + 181 && mouseX <= this.guiLeft + 181 + 127 && mouseY >= this.guiTop + 219 && mouseY <= this.guiTop + 219 + 15) {
                        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 181, this.guiTop + 219, 0, 417, 127, 15, 512.0f, 512.0f, false);
                        this.hoveredAction = "staff_accept";
                    }
                    this.drawScaledString(I18n.func_135053_a((String)"faction.enemy.accept_request"), this.guiLeft + 245, this.guiTop + 223, 0xFFFFFF, 1.0f, true, false);
                } else if (warInfos.get("status").equals("waiting_conditions_att_second") && warInfos.get("factionATT").equals(warInfos.get("playerFaction")) && ((Boolean)warInfos.get("hasWarPermInOwnCountry")).booleanValue()) {
                    ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 46, this.guiTop + 219, 0, 353, 262, 15, 512.0f, 512.0f, false);
                    if (mouseX >= this.guiLeft + 46 && mouseX <= this.guiLeft + 46 + 262 && mouseY >= this.guiTop + 219 && mouseY <= this.guiTop + 219 + 15) {
                        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 46, this.guiTop + 219, 0, 368, 262, 15, 512.0f, 512.0f, false);
                        this.hoveredAction = "refuse_conditions_att_second";
                    }
                    this.drawScaledString(I18n.func_135053_a((String)"faction.enemy.refuse_conditions_att_second"), this.guiLeft + 177, this.guiTop + 223, 0xFFFFFF, 1.0f, true, false);
                }
            } else if (warInfos.get("status").equals("waiting_conditions_att") && warInfos.get("factionATT").equals(warInfos.get("playerFaction")) || warInfos.get("status").equals("waiting_conditions_def") && warInfos.get("factionDEF").equals(warInfos.get("playerFaction"))) {
                Float offsetY;
                int offsetX;
                this.drawScaledString(I18n.func_135053_a((String)"faction.enemy.contitions"), this.guiLeft + 46, this.guiTop + 84, 0, 1.0f, false, false);
                this.drawScaledString(I18n.func_135053_a((String)("faction.enemy.conditionsType." + this.selectedConditionsType)), this.guiLeft + 104, this.guiTop + 85, 0xFFFFFF, 1.0f, false, false);
                this.conditionInput.func_73795_f();
                this.drawScaledString(I18n.func_135053_a((String)("faction.enemy.conditions." + this.selectedCondition)), this.guiLeft + 209, this.guiTop + 85, 0xFFFFFF, 1.0f, false, false);
                if (mouseX >= this.guiLeft + 287 && mouseX <= this.guiLeft + 287 + 20 && mouseY >= this.guiTop + 78 && mouseY <= this.guiTop + 78 + 20) {
                    ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 287, this.guiTop + 78, 269, 328, 20, 20, 512.0f, 512.0f, false);
                    this.hoveredAction = "add_condition";
                    if (this.selectedCondition.equalsIgnoreCase("kill")) {
                        tooltipToDraw = "\u00a7cMin 10";
                    } else if (this.selectedCondition.equalsIgnoreCase("victory")) {
                        tooltipToDraw = "\u00a7cMin 5";
                    } else if (this.selectedCondition.equalsIgnoreCase("assault_point")) {
                        tooltipToDraw = "\u00a7cMin 500";
                    } else if (this.selectedCondition.equalsIgnoreCase("missile_point")) {
                        tooltipToDraw = "\u00a7cMin 20";
                    }
                }
                GUIUtils.startGLScissor(this.guiLeft + 47, this.guiTop + 101, 255, 45);
                int yOffset = 0;
                for (Map.Entry<String, Integer> pair : this.conditions.entrySet()) {
                    offsetX = this.guiLeft + 47;
                    offsetY = Float.valueOf((float)(this.guiTop + 101 + yOffset) + this.getSlideConditions());
                    ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.floatValue(), 47, 101, 255, 23, 512.0f, 512.0f, false);
                    this.drawScaledString(pair.getValue() + " " + I18n.func_135053_a((String)("faction.enemy.conditions." + pair.getKey())), offsetX + 2, offsetY.intValue() + 7, 0xFFFFFF, 1.0f, false, false);
                    ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                    if (!this.conditionsOpened && mouseX >= offsetX + 233 && mouseX <= offsetX + 233 + 20 && (float)mouseY >= offsetY.floatValue() + 3.0f && (float)mouseY <= offsetY.floatValue() + 3.0f + 16.0f) {
                        ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 233, offsetY.floatValue() + 3.0f, 298, 323, 20, 16, 512.0f, 512.0f, false);
                        this.hoveredCondition = pair.getKey();
                    } else {
                        ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 233, offsetY.floatValue() + 3.0f, 298, 307, 20, 16, 512.0f, 512.0f, false);
                    }
                    yOffset += 23;
                }
                GUIUtils.endGLScissor();
                if (!this.conditionsOpened && mouseX >= this.guiLeft + 46 && mouseX <= this.guiLeft + 46 + 261 && mouseY >= this.guiTop + 100 && mouseY <= this.guiTop + 100 + 47) {
                    this.scrollBarConditions.draw(mouseX, mouseY);
                }
                if (this.conditionsTypeOpened) {
                    ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 100, this.guiTop + 97, 464, 83, 48, 20, 512.0f, 512.0f, false);
                    this.drawScaledString(I18n.func_135053_a((String)"faction.enemy.conditionsType.and"), this.guiLeft + 104, this.guiTop + 104, 0xFFFFFF, 1.0f, false, false);
                    if (mouseX >= this.guiLeft + 100 && mouseX <= this.guiLeft + 100 + 48 && mouseY >= this.guiTop + 98 && mouseY <= this.guiTop + 98 + 19) {
                        this.hoveredConditionsType = "and";
                    }
                    ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 100, this.guiTop + 116, 464, 83, 48, 20, 512.0f, 512.0f, false);
                    this.drawScaledString(I18n.func_135053_a((String)"faction.enemy.conditionsType.or"), this.guiLeft + 104, this.guiTop + 123, 0xFFFFFF, 1.0f, false, false);
                    if (mouseX >= this.guiLeft + 100 && mouseX <= this.guiLeft + 100 + 48 && mouseY >= this.guiTop + 117 && mouseY <= this.guiTop + 117 + 19) {
                        this.hoveredConditionsType = "or";
                    }
                }
                this.drawScaledString(I18n.func_135053_a((String)"faction.enemy.rewards"), this.guiLeft + 46, this.guiTop + 156, 0, 1.0f, false, false);
                this.rewardInput.func_73795_f();
                this.drawScaledString(I18n.func_135053_a((String)("faction.enemy.rewards." + this.selectedReward)), this.guiLeft + 209, this.guiTop + 157, 0xFFFFFF, 1.0f, false, false);
                if (mouseX >= this.guiLeft + 287 && mouseX <= this.guiLeft + 287 + 20 && mouseY >= this.guiTop + 150 && mouseY <= this.guiTop + 150 + 20) {
                    ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 287, this.guiTop + 150, 269, 328, 20, 20, 512.0f, 512.0f, false);
                    this.hoveredAction = "add_reward";
                }
                GUIUtils.startGLScissor(this.guiLeft + 47, this.guiTop + 173, 255, 45);
                yOffset = 0;
                for (Map.Entry<String, Integer> pair : this.rewards.entrySet()) {
                    offsetX = this.guiLeft + 47;
                    offsetY = Float.valueOf((float)(this.guiTop + 173 + yOffset) + this.getSlideRewards());
                    ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.floatValue(), 47, 101, 255, 23, 512.0f, 512.0f, false);
                    if (pair.getValue() > 0) {
                        String valueType = "";
                        if (pair.getKey().equals("peace")) {
                            valueType = I18n.func_135053_a((String)"faction.enemy.rewards.valueType.day");
                        }
                        this.drawScaledString(pair.getValue() + valueType + " " + I18n.func_135053_a((String)("faction.enemy.rewards." + pair.getKey())), offsetX + 2, offsetY.intValue() + 7, 0xFFFFFF, 1.0f, false, false);
                    } else {
                        this.drawScaledString(I18n.func_135053_a((String)("faction.enemy.rewards." + pair.getKey())), offsetX + 2, offsetY.intValue() + 7, 0xFFFFFF, 1.0f, false, false);
                    }
                    ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                    if (!this.rewardsOpened && mouseX >= offsetX + 233 && mouseX <= offsetX + 233 + 20 && (float)mouseY >= offsetY.floatValue() + 3.0f && (float)mouseY <= offsetY.floatValue() + 3.0f + 16.0f) {
                        ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 233, offsetY.floatValue() + 3.0f, 298, 323, 20, 16, 512.0f, 512.0f, false);
                        this.hoveredReward = pair.getKey();
                    } else {
                        ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 233, offsetY.floatValue() + 3.0f, 298, 307, 20, 16, 512.0f, 512.0f, false);
                    }
                    yOffset += 23;
                }
                GUIUtils.endGLScissor();
                if (!this.rewardsOpened && mouseX >= this.guiLeft + 46 && mouseX <= this.guiLeft + 46 + 261 && mouseY >= this.guiTop + 172 && mouseY <= this.guiTop + 172 + 47) {
                    this.scrollBarRewards.draw(mouseX, mouseY);
                }
                ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                if (warInfos.get("status").equals("waiting_conditions_att")) {
                    if (mouseX >= this.guiLeft + 46 && mouseX <= this.guiLeft + 46 + 262 && mouseY >= this.guiTop + 221 && mouseY <= this.guiTop + 219 + 15) {
                        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 46, this.guiTop + 221, 0, 323, 262, 15, 512.0f, 512.0f, false);
                        this.hoveredAction = "send_conditions_att";
                    }
                    this.drawScaledString(I18n.func_135053_a((String)"faction.enemy.send_conditions_att"), this.guiLeft + 177, this.guiTop + 225, 0xFFFFFF, 1.0f, true, false);
                } else {
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 46, this.guiTop + 221, 0, 399, 262, 15, 512.0f, 512.0f, false);
                    if (mouseX >= this.guiLeft + 46 && mouseX <= this.guiLeft + 46 + 262 && mouseY >= this.guiTop + 221 && mouseY <= this.guiTop + 219 + 15) {
                        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 46, this.guiTop + 221, 0, 414, 262, 15, 512.0f, 512.0f, false);
                        this.hoveredAction = "send_conditions_def";
                    }
                    this.drawScaledString(I18n.func_135053_a((String)"faction.enemy.send_conditions_def"), this.guiLeft + 177, this.guiTop + 225, 0xFFFFFF, 1.0f, true, false);
                }
                if (warInfos.get("status").equals("waiting_conditions_def")) {
                    ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 319, this.guiTop + 12, 319, 12, 102, 222, 512.0f, 512.0f, false);
                    this.drawScaledString("\u00a77\u00a7l" + I18n.func_135053_a((String)"faction.enemy.contitions") + " \u00a74\u00a7l" + I18n.func_135053_a((String)("faction.enemy.conditionsType." + warInfos.get("conditionsType_ATT"))), this.guiLeft + 326, this.guiTop + 42, 0xFFFFFF, 1.0f, false, false);
                    int index = 1;
                    if (warInfos.get("conditions_ATT") != null) {
                        for (String condition : ((String)warInfos.get("conditions_ATT")).split(",")) {
                            this.drawScaledString("\u00a7f\u25cf " + condition.split("#")[1] + " " + I18n.func_135053_a((String)("faction.enemy.conditions." + condition.split("#")[0])), this.guiLeft + 326, this.guiTop + 42 + 10 * index, 0xFFFFFF, 1.0f, false, false);
                            ++index;
                        }
                    } else {
                        this.drawScaledString("\u00a7c\u2716 " + I18n.func_135053_a((String)"faction.enemy.none"), this.guiLeft + 326, this.guiTop + 42 + 10, 0xFFFFFF, 1.0f, false, false);
                    }
                    int offset = index * 10;
                    index = 1;
                    this.drawScaledString("\u00a77\u00a7l" + I18n.func_135053_a((String)"faction.enemy.rewards"), this.guiLeft + 326, this.guiTop + 62 + offset, 0xFFFFFF, 1.0f, false, false);
                    if (warInfos.get("rewards_ATT") != null && !((String)warInfos.get("rewards_ATT")).isEmpty()) {
                        for (String reward : ((String)warInfos.get("rewards_ATT")).split(",")) {
                            if (Integer.parseInt(reward.split("#")[1]) > 0) {
                                String valueType = "";
                                if (reward.split("#")[0].equals("peace")) {
                                    valueType = I18n.func_135053_a((String)"faction.enemy.rewards.valueType.day");
                                }
                                this.drawScaledString("\u00a7f\u25cf " + reward.split("#")[1] + valueType + " " + I18n.func_135053_a((String)("faction.enemy.rewards." + reward.split("#")[0])), this.guiLeft + 326, this.guiTop + 62 + offset + 10 * index, 0xFFFFFF, 1.0f, false, false);
                            } else {
                                this.drawScaledString("\u00a7f\u25cf " + I18n.func_135053_a((String)("faction.enemy.rewards." + reward.split("#")[0])), this.guiLeft + 326, this.guiTop + 62 + offset + 10 * index, 0xFFFFFF, 1.0f, false, false);
                            }
                            ++index;
                        }
                    } else {
                        this.drawScaledString("\u00a7c\u2716 " + I18n.func_135053_a((String)"faction.enemy.none"), this.guiLeft + 326, this.guiTop + 62 + offset + 10 * index, 0xFFFFFF, 1.0f, false, false);
                    }
                    if (warInfos.get("conditions_ATT") == null || warInfos.get("rewards_ATT") == null || mouseX >= this.guiLeft + 323 && mouseX <= this.guiLeft + 323 + 90 && mouseY >= this.guiTop + 209 && mouseY <= this.guiTop + 209 + 15) {
                        ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 323, this.guiTop + 209, 0, 369, 90, 15, 512.0f, 512.0f, false);
                        if (warInfos.get("conditions_ATT") != null && warInfos.get("rewards_ATT") != null) {
                            this.hoveredAction = "accept_conditions_def";
                        }
                    }
                    this.drawScaledString(I18n.func_135053_a((String)"faction.enemy.accept_conditions_def"), this.guiLeft + 368, this.guiTop + 213, 0xFFFFFF, 1.0f, true, false);
                }
                if (this.rewardsOpened) {
                    ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 205, this.guiTop + 169, 432, 0, 80, 77, 512.0f, 512.0f, false);
                    GUIUtils.startGLScissor(this.guiLeft + 206, this.guiTop + 170, 74, 75);
                    yOffset = 0;
                    for (String availableReward : this.availableRewards) {
                        int offsetX2 = this.guiLeft + 206;
                        Float offsetY2 = Float.valueOf((float)(this.guiTop + 170 + yOffset) + this.getSlideAvailableRewards());
                        ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                        ModernGui.drawModalRectWithCustomSizedTexture(offsetX2, offsetY2.floatValue(), 433, 1, 74, 19, 512.0f, 512.0f, false);
                        this.drawScaledString(I18n.func_135053_a((String)("faction.enemy.rewards." + availableReward)), offsetX2 + 2, offsetY2.intValue() + 5, 0xFFFFFF, 1.0f, false, false);
                        if (mouseX >= offsetX2 && mouseX <= offsetX2 + 74 && (float)mouseY >= offsetY2.floatValue() && (float)mouseY <= offsetY2.floatValue() + 19.0f) {
                            this.hoveredAvailableReward = availableReward;
                        }
                        yOffset += 19;
                    }
                    GUIUtils.endGLScissor();
                    this.scrollBarAvailableRewards.draw(mouseX, mouseY);
                }
                if (this.conditionsOpened) {
                    ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 205, this.guiTop + 97, 432, 0, 80, 77, 512.0f, 512.0f, false);
                    GUIUtils.startGLScissor(this.guiLeft + 206, this.guiTop + 98, 74, 75);
                    yOffset = 0;
                    for (String availableCondition : this.availableConditions) {
                        int offsetX3 = this.guiLeft + 206;
                        Float offsetY3 = Float.valueOf((float)(this.guiTop + 98 + yOffset) + this.getSlideAvailableConditions());
                        ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                        ModernGui.drawModalRectWithCustomSizedTexture(offsetX3, offsetY3.floatValue(), 433, 1, 74, 19, 512.0f, 512.0f, false);
                        this.drawScaledString(I18n.func_135053_a((String)("faction.enemy.conditions." + availableCondition)), offsetX3 + 2, offsetY3.intValue() + 5, 0xFFFFFF, 1.0f, false, false);
                        if (mouseX >= offsetX3 && mouseX <= offsetX3 + 74 && (float)mouseY >= offsetY3.floatValue() && (float)mouseY <= offsetY3.floatValue() + 19.0f) {
                            this.hoveredAvailableCondition = availableCondition;
                        }
                        yOffset += 19;
                    }
                    GUIUtils.endGLScissor();
                    this.scrollBarAvailableConditions.draw(mouseX, mouseY);
                }
            } else if (warInfos.get("status").equals("in_progress") || warInfos.get("status").equals("finished")) {
                Float offsetY;
                int offsetX;
                int yOffset;
                if (this.data_ATT == null) {
                    this.data_ATT = new HashMap();
                    if (warInfos.get("data_ATT") != null && !((String)warInfos.get("data_ATT")).isEmpty()) {
                        for (String data : ((String)warInfos.get("data_ATT")).split(",")) {
                            this.data_ATT.put(data.split("#")[0], Integer.parseInt(data.split("#")[1]));
                        }
                    }
                }
                if (this.data_DEF == null) {
                    this.data_DEF = new HashMap();
                    if (warInfos.get("data_DEF") != null && !((String)warInfos.get("data_DEF")).isEmpty()) {
                        for (String data : ((String)warInfos.get("data_DEF")).split(",")) {
                            this.data_DEF.put(data.split("#")[0], Integer.parseInt(data.split("#")[1]));
                        }
                    }
                }
                String side = "att";
                float progress = 0.0f;
                float progressATT = 0.0f;
                float progressDEF = 0.0f;
                if (warInfos.get("conditionsType").equals("or")) {
                    for (String condition : ((String)warInfos.get("conditions")).split(",")) {
                        if (this.data_ATT.containsKey(condition.split("#")[0])) {
                            progressATT = Math.max(progressATT, (float)this.data_ATT.get(condition.split("#")[0]).intValue() * 1.0f / (float)Integer.parseInt(condition.split("#")[1]));
                        }
                        if (!this.data_DEF.containsKey(condition.split("#")[0])) continue;
                        progressDEF = Math.max(progressDEF, (float)this.data_DEF.get(condition.split("#")[0]).intValue() * 1.0f / (float)Integer.parseInt(condition.split("#")[1]));
                    }
                } else {
                    for (String condition : ((String)warInfos.get("conditions")).split(",")) {
                        if (this.data_ATT.containsKey(condition.split("#")[0])) {
                            progressATT += (float)this.data_ATT.get(condition.split("#")[0]).intValue() * 1.0f / (float)Integer.parseInt(condition.split("#")[1]);
                        }
                        if (!this.data_DEF.containsKey(condition.split("#")[0])) continue;
                        progressDEF += (float)this.data_DEF.get(condition.split("#")[0]).intValue() * 1.0f / (float)Integer.parseInt(condition.split("#")[1]);
                    }
                    progressATT /= (float)((String)warInfos.get("conditions")).split(",").length;
                    progressDEF /= (float)((String)warInfos.get("conditions")).split(",").length;
                }
                if (progressATT == 1.0f || warInfos.get("winner") != null && warInfos.get("winner").equals(warInfos.get("factionATTId"))) {
                    this.winner = (String)warInfos.get("factionATT");
                    progress = 1.0f;
                } else if (progressDEF == 1.0f || warInfos.get("winner") != null && warInfos.get("winner").equals(warInfos.get("factionDEFId"))) {
                    this.winner = (String)warInfos.get("factionDEF");
                    progress = -1.0f;
                } else if (progressATT >= progressDEF) {
                    progress = progressATT - progressDEF;
                    side = "att";
                } else {
                    progress = progressDEF - progressATT;
                    side = "def";
                }
                ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                Float offset = Float.valueOf(130.0f * (side.equals("att") ? -progress : progress));
                Gui.func_73734_a((int)(this.guiLeft + 47), (int)(this.guiTop + 78), (int)(this.guiLeft + 47 + 130 + offset.intValue()), (int)(this.guiTop + 78 + 3), (int)-15352550);
                Gui.func_73734_a((int)(this.guiLeft + 177 + offset.intValue()), (int)(this.guiTop + 78), (int)(this.guiLeft + 306), (int)(this.guiTop + 78 + 3), (int)-3924707);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 176 + offset.intValue() - 5, this.guiTop + 73, 330, 22, 11, 10, 512.0f, 512.0f, false);
                this.drawScaledString(I18n.func_135053_a((String)"faction.enemy.contitions") + " \u00a74(" + I18n.func_135053_a((String)("faction.enemy.conditionsType." + warInfos.get("conditionsType"))) + ")", this.guiLeft + 46, this.guiTop + 86, 0, 1.0f, false, false);
                ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 48 + this.field_73886_k.func_78256_a(I18n.func_135053_a((String)"faction.enemy.contitions") + " \u00a74(" + I18n.func_135053_a((String)("faction.enemy.conditionsType." + warInfos.get("conditionsType"))) + ")"), this.guiTop + 84, 71, 251, 10, 11, 512.0f, 512.0f, false);
                if (mouseX >= this.guiLeft + 48 + this.field_73886_k.func_78256_a(I18n.func_135053_a((String)"faction.enemy.contitions") + " \u00a74(" + I18n.func_135053_a((String)("faction.enemy.conditionsType." + warInfos.get("conditionsType"))) + ")") && mouseX <= this.guiLeft + 48 + this.field_73886_k.func_78256_a(I18n.func_135053_a((String)"faction.enemy.contitions") + " \u00a74(" + I18n.func_135053_a((String)("faction.enemy.conditionsType." + warInfos.get("conditionsType"))) + ")") + 10 && mouseY >= this.guiTop + 84 && mouseY <= this.guiTop + 84 + 11) {
                    tooltipToDraw = I18n.func_135053_a((String)"faction.enemy.tooltip.conditions");
                }
                if (warInfos.get("conditions") != null) {
                    GUIUtils.startGLScissor(this.guiLeft + 47, this.guiTop + 96, 255, 60);
                    yOffset = 0;
                    String[] condition = ((String)warInfos.get("conditions")).split(",");
                    int condition2 = condition.length;
                    for (int valueType = 0; valueType < condition2; ++valueType) {
                        String finishCondition = condition[valueType];
                        if (finishCondition.isEmpty() || finishCondition.split("#").length != 2) continue;
                        String conditionName = finishCondition.split("#")[0];
                        String conditionGoal = finishCondition.split("#")[1];
                        offsetX = this.guiLeft + 47;
                        offsetY = Float.valueOf((float)(this.guiTop + 96 + yOffset) + this.getSlideFinishConditions());
                        ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                        ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.floatValue(), 47, 96, 255, 60, 512.0f, 512.0f, false);
                        String line = (this.data_ATT.containsKey(conditionName) ? (Serializable)this.data_ATT.get(conditionName) : "0") + "/" + conditionGoal + " " + I18n.func_135053_a((String)("faction.enemy.conditions." + conditionName));
                        if (this.data_ATT.containsKey(conditionName) && this.data_ATT.get(conditionName) >= Integer.parseInt(conditionGoal)) {
                            line = "\u00a7a" + line;
                        }
                        this.drawScaledString(line, offsetX + 4, offsetY.intValue() + 5, 0xFFFFFF, 1.0f, false, false);
                        line = (this.data_DEF.containsKey(conditionName) ? (Serializable)this.data_DEF.get(conditionName) : "0") + "/" + conditionGoal + " " + I18n.func_135053_a((String)("faction.enemy.conditions." + conditionName));
                        if (this.data_DEF.containsKey(conditionName) && this.data_DEF.get(conditionName) >= Integer.parseInt(conditionGoal)) {
                            line = "\u00a7a" + line;
                        }
                        this.drawScaledString(line, offsetX + 135, offsetY.intValue() + 5, 0xFFFFFF, 1.0f, false, false);
                        yOffset += 20;
                    }
                    GUIUtils.endGLScissor();
                    if (mouseX >= this.guiLeft + 46 && mouseX <= this.guiLeft + 46 + 261 && mouseY >= this.guiTop + 95 && mouseY <= this.guiTop + 95 + 62) {
                        this.scrollBarFinishConditions.draw(mouseX, mouseY);
                    }
                }
                this.drawScaledString(I18n.func_135053_a((String)"faction.enemy.rewards"), this.guiLeft + 182, this.guiTop + 160, 0, 1.0f, false, false);
                ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 184 + this.field_73886_k.func_78256_a(I18n.func_135053_a((String)"faction.enemy.rewards")), this.guiTop + 158, 71, 251, 10, 11, 512.0f, 512.0f, false);
                if (mouseX >= this.guiLeft + 184 + this.field_73886_k.func_78256_a(I18n.func_135053_a((String)"faction.enemy.rewards")) && mouseX <= this.guiLeft + 184 + this.field_73886_k.func_78256_a(I18n.func_135053_a((String)"faction.enemy.rewards")) + 10 && mouseY >= this.guiTop + 158 && mouseY <= this.guiTop + 158 + 11) {
                    tooltipToDraw = I18n.func_135053_a((String)"faction.enemy.tooltip.rewards");
                }
                if (warInfos.get("rewards") != null) {
                    GUIUtils.startGLScissor(this.guiLeft + 183, this.guiTop + 170, 119, 61);
                    yOffset = 0;
                    if (!((String)warInfos.get("rewards")).isEmpty()) {
                        for (String finishReward : ((String)warInfos.get("rewards")).split(",")) {
                            if (finishReward.isEmpty() || finishReward.split("#").length != 2) continue;
                            String rewardName = finishReward.split("#")[0];
                            String rewardGoal = finishReward.split("#")[1];
                            offsetX = this.guiLeft + 183;
                            offsetY = Float.valueOf((float)(this.guiTop + 170 + yOffset) + this.getSlideFinishRewards());
                            ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                            ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.floatValue(), 183, 170, 119, 20, 512.0f, 512.0f, false);
                            if (Integer.parseInt(rewardGoal) > 0) {
                                String valueType = "";
                                if (rewardName.equals("peace")) {
                                    valueType = I18n.func_135053_a((String)"faction.enemy.rewards.valueType.day");
                                }
                                this.drawScaledString(rewardGoal + valueType + " " + I18n.func_135053_a((String)("faction.enemy.rewards." + rewardName)), offsetX + 4, offsetY.intValue() + 6, 0xFFFFFF, 1.0f, false, false);
                            } else {
                                this.drawScaledString(I18n.func_135053_a((String)("faction.enemy.rewards." + rewardName)), offsetX + 4, offsetY.intValue() + 6, 0xFFFFFF, 1.0f, false, false);
                            }
                            yOffset += 20;
                        }
                    }
                    GUIUtils.endGLScissor();
                    if (mouseX >= this.guiLeft + 182 && mouseX <= this.guiLeft + 182 + 125 && mouseY >= this.guiTop + 169 && mouseY <= this.guiTop + 169 + 63) {
                        this.scrollBarFinishRewards.draw(mouseX, mouseY);
                    }
                }
                Date date = new Date(((Double)warInfos.get("enemyTime")).longValue());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyy");
                String dateString = simpleDateFormat.format(date);
                this.drawScaledString(dateString, this.guiLeft + 168 - this.field_73886_k.func_78256_a(dateString), this.guiTop + 173, 0xFFFFFF, 1.0f, false, false);
                if (warInfos.get("playerTarget") != null && !((String)warInfos.get("playerTarget")).isEmpty()) {
                    ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 159, this.guiTop + 187, 71, 251, 10, 11, 512.0f, 512.0f, false);
                    if (mouseX >= this.guiLeft + 159 && mouseX <= this.guiLeft + 159 + 10 && mouseY >= this.guiTop + 187 && mouseY <= this.guiTop + 187 + 11) {
                        tooltipToDraw = I18n.func_135053_a((String)"faction.enemy.tooltip.reason").replace("#target#", (String)warInfos.get("playerTarget"));
                    }
                    this.drawScaledString(I18n.func_135053_a((String)("faction.enemy.reason." + warInfos.get("reason"))), this.guiLeft + 168 - this.field_73886_k.func_78256_a(I18n.func_135053_a((String)("faction.enemy.reason." + warInfos.get("reason")))) - 14, this.guiTop + 189, 0xFFFFFF, 1.0f, false, false);
                } else {
                    this.drawScaledString(I18n.func_135053_a((String)("faction.enemy.reason." + warInfos.get("reason"))), this.guiLeft + 168 - this.field_73886_k.func_78256_a(I18n.func_135053_a((String)("faction.enemy.reason." + warInfos.get("reason")))), this.guiTop + 189, 0xFFFFFF, 1.0f, false, false);
                }
                int totalKills = 0;
                if (this.data_ATT.containsKey("kill")) {
                    totalKills += this.data_ATT.get("kill").intValue();
                }
                if (this.data_DEF.containsKey("kill")) {
                    totalKills += this.data_DEF.get("kill").intValue();
                }
                this.drawScaledString(totalKills + " kills", this.guiLeft + 168 - this.field_73886_k.func_78256_a(totalKills + " kills"), this.guiTop + 205, 0xFFFFFF, 1.0f, false, false);
                if (((String)warInfos.get("linkForum")).isEmpty() || mouseX >= this.guiLeft + 46 && mouseX <= this.guiLeft + 46 + 125 && mouseY >= this.guiTop + 217 && mouseY <= this.guiTop + 217 + 15) {
                    ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 46, this.guiTop + 217, 0, 302, 125, 15, 512.0f, 512.0f, false);
                    this.hoveredAction = "open_link_forum";
                }
                this.drawScaledString(I18n.func_135053_a((String)"faction.enemy.linkForum"), this.guiLeft + 109, this.guiTop + 221, 0, 1.0f, true, false);
                if (warInfos.get("status").equals("in_progress") && ((Boolean)warInfos.get("hasWarPermInOwnCountry")).booleanValue() && (warInfos.get("playerFaction").equals(warInfos.get("factionATT")) || warInfos.get("playerFaction").equals(warInfos.get("factionDEF")))) {
                    ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 319, this.guiTop + 166, 319, 166, 23, 64, 512.0f, 512.0f, false);
                    GL11.glPushMatrix();
                    GL11.glTranslatef((float)(this.guiLeft + 325), (float)(this.guiTop + 212), (float)0.0f);
                    GL11.glRotatef((float)-90.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                    GL11.glTranslatef((float)(-(this.guiLeft + 325)), (float)(-(this.guiTop + 212)), (float)0.0f);
                    this.drawScaledString(I18n.func_135053_a((String)"faction.enemy.surrender"), this.guiLeft + 325, this.guiTop + 212, 0, 1.0f, false, false);
                    GL11.glPopMatrix();
                    if (mouseX >= this.guiLeft + 319 && mouseX <= this.guiLeft + 319 + 23 && mouseY >= this.guiTop + 166 && mouseY <= this.guiTop + 166 + 64) {
                        if (warInfos.containsKey("enemyTime") && System.currentTimeMillis() - ((Double)warInfos.get("enemyTime")).longValue() < 604800000L) {
                            tooltipToDraw = I18n.func_135053_a((String)"faction.enemy.tooltip.surrender_delay");
                        } else {
                            this.hoveredAction = "surrender";
                        }
                    }
                }
            }
        }
        if (tooltipToDraw != "") {
            this.drawHoveringText(Arrays.asList(tooltipToDraw.split("##")), mouseX, mouseY, this.field_73886_k);
        }
    }

    private float getSlideAvailableConditions() {
        return this.availableConditions.size() > 4 ? (float)(-(this.availableConditions.size() - 4) * 19) * this.scrollBarAvailableConditions.getSliderValue() : 0.0f;
    }

    private float getSlideAvailableRewards() {
        return this.availableRewards.size() > 4 ? (float)(-(this.availableRewards.size() - 4) * 19) * this.scrollBarAvailableRewards.getSliderValue() : 0.0f;
    }

    private float getSlideConditions() {
        return this.conditions.size() > 2 ? (float)(-(this.conditions.size() - 2) * 23) * this.scrollBarConditions.getSliderValue() : 0.0f;
    }

    private float getSlideRewards() {
        return this.rewards.size() > 2 ? (float)(-(this.rewards.size() - 2) * 23) * this.scrollBarRewards.getSliderValue() : 0.0f;
    }

    private float getSlideFinishConditions() {
        return ((String)warInfos.get("conditions")).split(",").length > 3 ? (float)(-(((String)warInfos.get("conditions")).split(",").length - 3) * 20) * this.scrollBarFinishConditions.getSliderValue() : 0.0f;
    }

    private float getSlideFinishRewards() {
        return ((String)warInfos.get("rewards")).split(",").length > 3 ? (float)(-(((String)warInfos.get("rewards")).split(",").length - 3) * 20) * this.scrollBarFinishRewards.getSliderValue() : 0.0f;
    }

    public void drawTooltip(String text, int mouseX, int mouseY) {
        int mouseXGui = mouseX - this.guiLeft;
        int mouseYGui = mouseY - this.guiTop;
        this.drawHoveringText(Arrays.asList(text), mouseX, mouseY, this.field_73886_k);
    }

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        super.func_73864_a(mouseX, mouseY, mouseButton);
        if (mouseButton == 0) {
            Object theDesktop;
            Class<?> desktop;
            if (mouseX > this.guiLeft + 305 && mouseX < this.guiLeft + 305 + 9 && mouseY > this.guiTop - 6 && mouseY < this.guiTop - 6 + 10) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                this.field_73882_e.func_71373_a(this.guiFrom);
            }
            if (!this.hoveredAction.isEmpty()) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                if (this.hoveredAction.equals("cancel")) {
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionEnemyRequestUpdateStatusPacket(warRequestId, "cancelled", (String)warInfos.get("status"), (String)warInfos.get("factionATT"), (String)warInfos.get("factionDEF"))));
                    this.field_73882_e.func_71373_a(null);
                } else if (this.hoveredAction.equals("staff_refuse")) {
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionEnemyRequestUpdateStatusPacket(warRequestId, "refused", (String)warInfos.get("status"), (String)warInfos.get("factionATT"), (String)warInfos.get("factionDEF"))));
                    this.field_73882_e.func_71373_a(this.guiFrom);
                } else if (this.hoveredAction.equals("staff_accept") || this.hoveredAction.equals("accept")) {
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionEnemyRequestUpdateStatusPacket(warRequestId, "waiting_conditions_att", (String)warInfos.get("status"), (String)warInfos.get("factionATT"), (String)warInfos.get("factionDEF"))));
                    this.field_73882_e.func_71373_a(this.guiFrom);
                } else if (this.hoveredAction.equals("accept_conditions_def")) {
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionEnemyRequestUpdateStatusPacket(warRequestId, "in_progress", (String)warInfos.get("status"), (String)warInfos.get("factionATT"), (String)warInfos.get("factionDEF"))));
                    this.field_73882_e.func_71373_a(this.guiFrom);
                } else if (this.hoveredAction.equals("accept_conditions_att_second")) {
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionEnemyRequestUpdateStatusPacket(warRequestId, "in_progress", (String)warInfos.get("status"), (String)warInfos.get("factionATT"), (String)warInfos.get("factionDEF"))));
                    this.field_73882_e.func_71373_a(this.guiFrom);
                } else if (this.hoveredAction.equals("refuse_conditions_att_second")) {
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionEnemyRequestGenerateConditionsPacket(warRequestId)));
                    this.field_73882_e.func_71373_a(this.guiFrom);
                } else if (this.hoveredAction.equals("save_forum")) {
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionEnemyRequestUpdateForumPacket(warRequestId, this.linkForumInput.func_73781_b())));
                    this.field_73882_e.func_71373_a(this.guiFrom);
                } else if (this.hoveredAction.equals("open_link_forum")) {
                    if (warInfos != null && !((String)warInfos.get("linkForum")).isEmpty()) {
                        try {
                            desktop = Class.forName("java.awt.Desktop");
                            theDesktop = desktop.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
                            desktop.getMethod("browse", URI.class).invoke(theDesktop, URI.create((String)warInfos.get("linkForum")));
                        }
                        catch (Throwable t) {
                            t.printStackTrace();
                        }
                    }
                } else if (this.hoveredAction.equals("surrender")) {
                    // empty if block
                }
            }
            if (!this.linkForumInput.func_73781_b().isEmpty() && mouseX >= this.guiLeft + 286 && mouseX <= this.guiLeft + 286 + 20 && mouseY >= this.guiTop + 121 && mouseY <= this.guiTop + 121 + 16 && (this.linkForumInput.func_73781_b().contains("https://nationsglory.fr/forums") || this.linkForumInput.func_73781_b().contains("forum.nationsglory.fr"))) {
                try {
                    desktop = Class.forName("java.awt.Desktop");
                    theDesktop = desktop.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
                    desktop.getMethod("browse", URI.class).invoke(theDesktop, URI.create(this.linkForumInput.func_73781_b()));
                }
                catch (Throwable t) {
                    t.printStackTrace();
                }
            }
            if (mouseX >= this.guiLeft + 100 && mouseX <= this.guiLeft + 100 + 48 && mouseY >= this.guiTop + 78 && mouseY <= this.guiTop + 78 + 20) {
                boolean bl = this.conditionsTypeOpened = !this.conditionsTypeOpened;
            }
            if (!this.hoveredConditionsType.isEmpty()) {
                this.selectedConditionsType = this.hoveredConditionsType;
                this.hoveredConditionsType = "";
                this.conditionsTypeOpened = false;
            }
            if (mouseX >= this.guiLeft + 205 && mouseX <= this.guiLeft + 205 + 80 && mouseY >= this.guiTop + 78 && mouseY <= this.guiTop + 78 + 20) {
                this.conditionsOpened = !this.conditionsOpened;
                this.scrollBarAvailableConditions.reset();
            }
            if (!this.hoveredAvailableCondition.isEmpty()) {
                this.selectedCondition = this.hoveredAvailableCondition;
                this.hoveredAvailableCondition = "";
                this.conditionsOpened = false;
            }
            if (this.hoveredAction.equals("add_condition") && !this.conditionInput.func_73781_b().isEmpty() && this.isNumeric(this.conditionInput.func_73781_b())) {
                int value = Integer.parseInt(this.conditionInput.func_73781_b());
                if (this.selectedCondition.equalsIgnoreCase("kill") && value < 10) {
                    return;
                }
                if (this.selectedCondition.equalsIgnoreCase("victory") && value < 5) {
                    return;
                }
                if (this.selectedCondition.equalsIgnoreCase("assault_point") && value < 500) {
                    return;
                }
                if (this.selectedCondition.equalsIgnoreCase("missile_point") && value < 20) {
                    return;
                }
                this.conditions.put(this.selectedCondition, Integer.parseInt(this.conditionInput.func_73781_b()));
                this.conditionInput.func_73782_a("0");
                this.selectedCondition = this.availableConditions.get(0);
            }
            if (!this.hoveredCondition.isEmpty()) {
                this.conditions.remove(this.hoveredCondition);
                this.hoveredCondition = "";
                this.scrollBarConditions.reset();
            }
            if (!this.conditionsOpened && mouseX >= this.guiLeft + 205 && mouseX <= this.guiLeft + 205 + 80 && mouseY >= this.guiTop + 150 && mouseY <= this.guiTop + 150 + 20) {
                this.rewardsOpened = !this.rewardsOpened;
                this.scrollBarAvailableRewards.reset();
            }
            if (!this.hoveredAvailableReward.isEmpty()) {
                this.selectedReward = this.hoveredAvailableReward;
                this.hoveredAvailableReward = "";
                this.rewardsOpened = false;
            }
            if (this.hoveredAction.equals("add_reward") && (!this.rewardInput.func_73781_b().isEmpty() && this.isNumeric(this.rewardInput.func_73781_b()) || this.selectedReward.equals("colonisation"))) {
                if (this.isNumeric(this.rewardInput.func_73781_b())) {
                    int value = Integer.parseInt(this.rewardInput.func_73781_b());
                    if (this.selectedReward.equalsIgnoreCase("claims") && value > 150) {
                        return;
                    }
                    if (this.selectedReward.equalsIgnoreCase("power") && value > 300) {
                        return;
                    }
                }
                this.rewards.put(this.selectedReward, !this.selectedReward.equals("colonisation") ? Integer.parseInt(this.rewardInput.func_73781_b()) : 0);
                this.rewardInput.func_73782_a("0");
                this.selectedReward = this.availableRewards.get(0);
            }
            if (!this.hoveredReward.isEmpty()) {
                this.rewards.remove(this.hoveredReward);
                this.hoveredReward = "";
                this.scrollBarRewards.reset();
            }
            if ((this.hoveredAction.equals("send_conditions_att") || this.hoveredAction.equals("send_conditions_def")) && !this.conditions.isEmpty()) {
                String conditionsString = "";
                String rewardsString = "";
                for (Map.Entry<String, Integer> pair : this.conditions.entrySet()) {
                    conditionsString = conditionsString + pair.getKey() + "#" + pair.getValue() + ",";
                }
                for (Map.Entry<String, Integer> pair : this.rewards.entrySet()) {
                    rewardsString = rewardsString + pair.getKey() + "#" + pair.getValue() + ",";
                }
                conditionsString = conditionsString.replaceAll(",$", "");
                rewardsString = rewardsString.replaceAll(",$", "");
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionEnemyRequestUpdateConditionsPacket(warRequestId, this.hoveredAction.replace("send_conditions_", ""), this.selectedConditionsType, conditionsString, rewardsString)));
                this.field_73882_e.func_71373_a(this.guiFrom);
            }
        }
        this.linkForumInput.func_73793_a(mouseX, mouseY, mouseButton);
        this.conditionInput.func_73793_a(mouseX, mouseY, mouseButton);
        this.rewardInput.func_73793_a(mouseX, mouseY, mouseButton);
    }

    protected void func_73869_a(char typedChar, int keyCode) {
        this.linkForumInput.func_73802_a(typedChar, keyCode);
        if (this.conditionInput.func_73802_a(typedChar, keyCode)) {
            this.conditionInput.func_73782_a(this.conditionInput.func_73781_b().replaceAll("^0", ""));
        }
        if (this.rewardInput.func_73802_a(typedChar, keyCode)) {
            this.rewardInput.func_73782_a(this.rewardInput.func_73781_b().replaceAll("^0", ""));
        }
        super.func_73869_a(typedChar, keyCode);
    }

    private String formatDiff(long diff) {
        String date = "";
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
                    date = date + " " + seconds + " " + I18n.func_135053_a((String)"faction.common.seconds") + " " + I18n.func_135053_a((String)"faction.bank.date_2");
                } else {
                    date = date + " " + minutes + " " + I18n.func_135053_a((String)"faction.common.minutes") + " " + I18n.func_135053_a((String)"faction.bank.date_2");
                }
            } else {
                date = date + " " + hours + " " + I18n.func_135053_a((String)"faction.common.hours") + " " + I18n.func_135053_a((String)"faction.bank.date_2");
            }
        } else {
            date = date + " " + days + " " + I18n.func_135053_a((String)"faction.common.days") + " " + I18n.func_135053_a((String)"faction.bank.date_2");
        }
        return date;
    }

    public void drawScaledString(String text, int x, int y, int color, float scale, boolean centered, boolean shadow) {
        GL11.glPushMatrix();
        GL11.glScalef((float)scale, (float)scale, (float)scale);
        float newX = x;
        if (centered) {
            newX = (float)x - (float)this.field_73886_k.func_78256_a(text) * scale / 2.0f;
        }
        if (shadow) {
            this.field_73886_k.func_85187_a(text, (int)(newX / scale), (int)((float)(y + 1) / scale), (color & 0xFCFCFC) >> 2 | color & 0xFF000000, false);
        }
        this.field_73886_k.func_85187_a(text, (int)(newX / scale), (int)((float)y / scale), color, false);
        GL11.glPopMatrix();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    public boolean func_73868_f() {
        return false;
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

    public String bindTextureDependingOnStatus() {
        String textureToBind = "faction_war_request_1";
        if (loaded && warInfos.containsKey("status")) {
            switch ((String)warInfos.get("status")) {
                case "waiting_validation": 
                case "waiting_conditions_att_second": 
                case "refused": 
                case "cancelled": {
                    textureToBind = "faction_war_request_1";
                    break;
                }
                case "waiting_conditions_att": {
                    if (warInfos.get("factionATT").equals(warInfos.get("playerFaction")) && ((Boolean)warInfos.get("hasWarPermInOwnCountry")).booleanValue()) {
                        textureToBind = "faction_war_request_2";
                        break;
                    }
                    textureToBind = "faction_war_request_1";
                    break;
                }
                case "waiting_conditions_def": {
                    if (warInfos.get("factionDEF").equals(warInfos.get("playerFaction")) && ((Boolean)warInfos.get("hasWarPermInOwnCountry")).booleanValue()) {
                        textureToBind = "faction_war_request_2";
                        break;
                    }
                    textureToBind = "faction_war_request_1";
                    break;
                }
                case "in_progress": {
                    textureToBind = "faction_war_request_3";
                    break;
                }
                case "finished": {
                    textureToBind = "faction_war_request_3";
                }
            }
        }
        return textureToBind;
    }

    public boolean isNumeric(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (Character.isDigit(c)) continue;
            return false;
        }
        return Integer.parseInt(str) > 0;
    }
}

