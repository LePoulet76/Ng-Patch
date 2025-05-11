/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiTextField
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.network.packet.Packet
 */
package net.ilexiconn.nationsgui.forge.client.gui.enterprise;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseBetCreateGui;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseGui;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.TabbedEnterpriseGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseBetActionPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseBetDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;

public class EnterpriseBetGUI
extends TabbedEnterpriseGUI {
    public static ArrayList<HashMap<String, Object>> bets = new ArrayList();
    public static HashMap<String, String> betsInfos = new HashMap();
    public String hoveredBetId = "";
    public String openedBetId = "";
    public String hoveredAction = "";
    public int hoveredOption = 0;
    public double hoveredMinBet = 0.0;
    public int selectedOption = 0;
    public int lastInputX = 0;
    public int lastInputY = 0;
    private GuiScrollBarFaction scrollBarBet;
    public static boolean loaded = false;
    private GuiTextField betInput;
    private Long lastAction = 0L;
    private GuiButton createButton;

    public void func_73876_c() {
        this.betInput.func_73780_a();
    }

    @Override
    public void func_73866_w_() {
        super.func_73866_w_();
        loaded = false;
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new EnterpriseBetDataPacket((String)EnterpriseGui.enterpriseInfos.get("name"))));
        this.scrollBarBet = new GuiScrollBarFaction(this.guiLeft + 389, this.guiTop + 59, 174);
        this.createButton = new GuiButton(0, this.guiLeft + 10, this.guiTop + 165, 100, 20, I18n.func_135053_a((String)"enterprise.bet.btnCreate"));
        if (!EnterpriseGui.hasPermission("bets") || !((Boolean)EnterpriseGui.enterpriseInfos.get("isInEnterprise")).booleanValue()) {
            this.createButton.field_73742_g = false;
        }
        this.betInput = new GuiTextField(this.field_73886_k, this.guiLeft, this.guiTop, 54, 10);
        this.betInput.func_73786_a(false);
        this.betInput.func_73804_f(6);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        this.hoveredAction = "";
        this.hoveredBetId = "";
        this.hoveredOption = 0;
        String tooltipToDraw = "";
        ClientEventHandler.STYLE.bindTexture("enterprise_bet");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512.0f, 512.0f, false);
        this.createButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
        if (!this.createButton.field_73742_g && mouseX > this.guiLeft + 10 && mouseX < this.guiLeft + 10 + 100 && mouseY > this.guiTop + 165 && mouseY < this.guiTop + 165 + 20) {
            tooltipToDraw = I18n.func_135053_a((String)"enterprise.bet.btnCreate.disabled");
        }
        this.drawScaledString(I18n.func_135053_a((String)"enterprise.bet.title").replace("#enterprise#", (String)EnterpriseGui.enterpriseInfos.get("name")), this.guiLeft + 131, this.guiTop + 15, 0x191919, 1.4f, false, false);
        if (loaded) {
            this.drawScaledString(betsInfos.get("stats_active"), this.guiLeft + 203 - this.field_73886_k.func_78256_a(betsInfos.get("stats_active")), this.guiTop + 37, 0xFFFFFF, 1.0f, false, false);
            this.drawScaledString(betsInfos.get("stats_reward"), this.guiLeft + 292 - this.field_73886_k.func_78256_a(betsInfos.get("stats_reward")), this.guiTop + 37, 0xFFFFFF, 1.0f, false, false);
            this.drawScaledString(betsInfos.get("stats_total"), this.guiLeft + 381 - this.field_73886_k.func_78256_a(betsInfos.get("stats_total")), this.guiTop + 37, 0xFFFFFF, 1.0f, false, false);
            if (mouseX > this.guiLeft + 131 && mouseX < this.guiLeft + 131 + 75 && mouseY > this.guiTop + 30 && mouseY < this.guiTop + 30 + 20) {
                tooltipToDraw = I18n.func_135053_a((String)"enterprise.bet.tooltip.active");
            } else if (mouseX > this.guiLeft + 220 && mouseX < this.guiLeft + 220 + 75 && mouseY > this.guiTop + 30 && mouseY < this.guiTop + 30 + 20) {
                tooltipToDraw = I18n.func_135053_a((String)"enterprise.bet.tooltip.reward");
            } else if (mouseX > this.guiLeft + 309 && mouseX < this.guiLeft + 309 + 75 && mouseY > this.guiTop + 30 && mouseY < this.guiTop + 30 + 20) {
                tooltipToDraw = I18n.func_135053_a((String)"enterprise.bet.tooltip.total");
            }
            GUIUtils.startGLScissor(this.guiLeft + 131, this.guiTop + 59, 253, 174);
            int yOffset = 0;
            for (HashMap<String, Object> betInfos : bets) {
                int offsetX = this.guiLeft + 131;
                Float offsetY = Float.valueOf((float)(this.guiTop + 59 + yOffset) + this.getSlideBets());
                if (this.openedBetId != "" && this.openedBetId == betInfos.get("id")) {
                    ClientEventHandler.STYLE.bindTexture("enterprise_bet");
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.floatValue(), 0, 385, 253, 104, 512.0f, 512.0f, false);
                    yOffset += 86;
                    if (mouseX >= offsetX && mouseX <= offsetX + 253 && (float)mouseY >= offsetY.floatValue() && (float)mouseY <= offsetY.floatValue() + 104.0f) {
                        this.hoveredBetId = (String)betInfos.get("id");
                    }
                } else {
                    ClientEventHandler.STYLE.bindTexture("enterprise_bet");
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.floatValue(), 0, 350, 253, 34, 512.0f, 512.0f, false);
                    yOffset += 35;
                    if (mouseX >= offsetX && mouseX <= offsetX + 253 && (float)mouseY >= offsetY.floatValue() && (float)mouseY <= offsetY.floatValue() + 34.0f) {
                        this.hoveredBetId = (String)betInfos.get("id");
                    }
                }
                ClientEventHandler.STYLE.bindTexture("enterprise_bet");
                this.drawScaledString((String)betInfos.get("title"), offsetX + 4, offsetY.intValue() + 5, 0xFFFFFF, 1.0f, false, false);
                String playersLabel = String.format("%.0f", (Double)betInfos.get("countPlayerBet")) + " " + I18n.func_135053_a((String)"enterprise.bet.players");
                this.drawScaledString("\u00a75" + playersLabel, offsetX + 249 - this.field_73886_k.func_78256_a(playersLabel), offsetY.intValue() + 14, 0xFFFFFF, 1.0f, false, false);
                if (((String)betInfos.get("status")).contains("bet#")) {
                    this.drawScaledString(I18n.func_135053_a((String)"enterprise.bet.status.bet") + " (" + ((String)betInfos.get("status")).split("#")[1] + "$)", offsetX + 4, offsetY.intValue() + 24, 0xFFFFFF, 1.0f, false, false);
                } else {
                    String status = I18n.func_135053_a((String)("enterprise.bet.status." + betInfos.get("status")));
                    if (((String)betInfos.get("status")).equalsIgnoreCase("done") && !((String)betInfos.get("playerResult")).isEmpty() && !((String)betInfos.get("playerResult")).equals("0")) {
                        status = ((String)betInfos.get("playerResult")).contains("-") ? status + " \u00a74(" + betInfos.get("playerResult") + "$)" : status + " \u00a7a(+" + betInfos.get("playerResult") + "$)";
                    }
                    this.drawScaledString(status, offsetX + 4, offsetY.intValue() + 24, 0xFFFFFF, 1.0f, false, false);
                }
                String diffText = "-";
                boolean expired = false;
                if (!betInfos.get("status").equals("done") && (Double)betInfos.get("deadlineTime") > 0.0) {
                    long diff = ((Double)betInfos.get("deadlineTime")).longValue() - System.currentTimeMillis();
                    if (diff > 0L) {
                        diffText = this.formatDiff(diff);
                        diffText = I18n.func_135053_a((String)"enterprise.contract.ends_in") + diffText;
                    } else {
                        diffText = I18n.func_135053_a((String)"enterprise.contract.expired");
                        expired = true;
                    }
                    diffText = diffText.trim();
                }
                this.drawScaledString("\u00a78" + diffText, offsetX + 249 - this.field_73886_k.func_78256_a(diffText), offsetY.intValue() + 24, 0xFFFFFF, 1.0f, false, false);
                if (this.openedBetId == "" || this.openedBetId != betInfos.get("id")) continue;
                ClientEventHandler.STYLE.bindTexture("enterprise_bet");
                if (this.selectedOption == 1 || (!EnterpriseGui.hasPermission("bets") || !((Boolean)EnterpriseGui.enterpriseInfos.get("isInEnterprise")).booleanValue()) && (Double)betInfos.get("playerChoice") == 1.0) {
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 4, offsetY.intValue() + 38, 177, 251, 10, 10, 512.0f, 512.0f, false);
                } else {
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 4, offsetY.intValue() + 38, 187, 251, 10, 10, 512.0f, 512.0f, false);
                }
                if (this.selectedOption == 2 || (!EnterpriseGui.hasPermission("bets") || !((Boolean)EnterpriseGui.enterpriseInfos.get("isInEnterprise")).booleanValue()) && (Double)betInfos.get("playerChoice") == 2.0) {
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 4, offsetY.intValue() + 52, 177, 251, 10, 10, 512.0f, 512.0f, false);
                } else {
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 4, offsetY.intValue() + 52, 187, 251, 10, 10, 512.0f, 512.0f, false);
                }
                if (mouseX >= offsetX + 4 && mouseX <= offsetX + 4 + 10 && mouseY >= offsetY.intValue() + 38 && mouseY <= offsetY.intValue() + 52 + 10) {
                    this.hoveredBetId = "";
                    if ((betInfos.get("status").equals("to_bet") && !expired || !betInfos.get("status").equals("done") && EnterpriseGui.hasPermission("bets") && ((Boolean)EnterpriseGui.enterpriseInfos.get("isInEnterprise")).booleanValue()) && mouseY >= offsetY.intValue() + 38 && mouseY <= offsetY.intValue() + 38 + 10) {
                        this.hoveredOption = 1;
                    } else if ((betInfos.get("status").equals("to_bet") && !expired || !betInfos.get("status").equals("done") && EnterpriseGui.hasPermission("bets") && ((Boolean)EnterpriseGui.enterpriseInfos.get("isInEnterprise")).booleanValue()) && mouseY >= offsetY.intValue() + 52 && mouseY <= offsetY.intValue() + 52 + 10) {
                        this.hoveredOption = 2;
                    }
                }
                this.drawScaledString((String)betInfos.get("option1"), offsetX + 18, offsetY.intValue() + 39, 0xFFFFFF, 1.0f, false, false);
                this.drawScaledString((String)betInfos.get("option2"), offsetX + 18, offsetY.intValue() + 53, 0xFFFFFF, 1.0f, false, false);
                String action = "";
                String action2 = "";
                if (!betInfos.get("status").equals("done") && EnterpriseGui.hasPermission("bets") && ((Boolean)EnterpriseGui.enterpriseInfos.get("isInEnterprise")).booleanValue()) {
                    ClientEventHandler.STYLE.bindTexture("enterprise_bet");
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 196, offsetY.intValue() + 65, 121, 270, 55, 18, 512.0f, 512.0f, false);
                    this.drawScaledString(I18n.func_135053_a((String)"enterprise.bet.btn_label.end"), offsetX + 224, offsetY.intValue() + 71, 0xFFFFFF, 1.0f, true, false);
                    action = "end";
                    ClientEventHandler.STYLE.bindTexture("enterprise_bet");
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 140, offsetY.intValue() + 65, 121, 251, 55, 18, 512.0f, 512.0f, false);
                    this.drawScaledString(I18n.func_135053_a((String)"enterprise.bet.btn_label.cancel"), offsetX + 168, offsetY.intValue() + 71, 0xFFFFFF, 1.0f, true, false);
                    action2 = "cancel";
                } else if (betInfos.get("status").equals("bet")) {
                    this.drawScaledString("\u00a76" + I18n.func_135053_a((String)"enterprise.bet.mise") + " " + betInfos.get("playerBet") + "$", offsetX + 4, offsetY.intValue() + 73, 0xFFFFFF, 1.0f, false, false);
                } else if (!expired && betInfos.get("status").equals("to_bet")) {
                    action = "bet";
                    ClientEventHandler.STYLE.bindTexture("enterprise_bet");
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 196, offsetY.intValue() + 65, 121, 270, 55, 18, 512.0f, 512.0f, false);
                    this.drawScaledString(I18n.func_135053_a((String)"enterprise.bet.btn_label.bet"), offsetX + 224, offsetY.intValue() + 71, 0xFFFFFF, 1.0f, true, false);
                    ClientEventHandler.STYLE.bindTexture("enterprise_bet");
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 4, offsetY.intValue() + 65, 121, 289, 75, 18, 512.0f, 512.0f, false);
                    if (this.lastInputX != offsetX + 6 || this.lastInputY != offsetY.intValue() + 69) {
                        this.lastInputX = offsetX + 6;
                        this.lastInputY = offsetY.intValue() + 69;
                        this.betInput = new GuiTextField(this.field_73886_k, offsetX + 7, offsetY.intValue() + 71, 54, 14);
                        this.betInput.func_73786_a(false);
                        this.betInput.func_73804_f(6);
                        this.betInput.func_73782_a("0");
                    }
                    this.betInput.func_73795_f();
                    this.drawScaledString("\u00a7a$", offsetX + 71, offsetY.intValue() + 71, 0xFFFFFF, 1.3f, true, false);
                    this.drawScaledString(I18n.func_135053_a((String)"enterprise.bet.minBet") + ": " + String.format("%.0f", (Double)betInfos.get("minBet")) + "$", offsetX + 82, offsetY.intValue() + 71, 0xB4B4B4, 1.0f, false, false);
                    this.hoveredMinBet = (Double)betInfos.get("minBet");
                    if (mouseX >= offsetX + 4 && mouseX <= offsetX + 4 + 75 && mouseY >= offsetY.intValue() + 65 && mouseY <= offsetY.intValue() + 65 + 18) {
                        this.hoveredBetId = "";
                    }
                }
                if (mouseX >= offsetX + 196 && mouseX <= offsetX + 196 + 55 && mouseY >= offsetY.intValue() + 65 && mouseY <= offsetY.intValue() + 65 + 18) {
                    this.hoveredAction = action;
                    continue;
                }
                if (mouseX < offsetX + 140 || mouseX > offsetX + 140 + 55 || mouseY < offsetY.intValue() + 65 || mouseY > offsetY.intValue() + 65 + 18) continue;
                this.hoveredAction = action2;
            }
            GUIUtils.endGLScissor();
            if (mouseX > this.guiLeft + 121 && mouseX < this.guiLeft + 121 + 278 && mouseY > this.guiTop + 51 && mouseY < this.guiTop + 51 + 194) {
                this.scrollBarBet.draw(mouseX, mouseY);
            }
            if (!tooltipToDraw.isEmpty()) {
                this.drawTooltip(tooltipToDraw, mouseX, mouseY);
            }
        }
    }

    private float getSlideBets() {
        int offsetOpened = this.openedBetId != "" ? 51 : 0;
        return this.openedBetId != "" && bets.size() > 2 || bets.size() > 5 ? (float)(-((bets.size() - 5) * 35 + offsetOpened)) * this.scrollBarBet.getSliderValue() : 0.0f;
    }

    public void drawTooltip(String text, int mouseX, int mouseY) {
        int mouseXGui = mouseX - this.guiLeft;
        int mouseYGui = mouseY - this.guiTop;
        this.drawHoveringText(Arrays.asList(text), mouseX, mouseY, this.field_73886_k);
    }

    protected void func_73869_a(char typedChar, int keyCode) {
        this.betInput.func_73802_a(typedChar, keyCode);
        super.func_73869_a(typedChar, keyCode);
    }

    @Override
    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        super.func_73864_a(mouseX, mouseY, mouseButton);
        if (mouseButton == 0) {
            if (this.createButton.field_73742_g && EnterpriseGui.hasPermission("bets") && mouseX >= this.guiLeft + 10 && mouseX <= this.guiLeft + 10 + 100 && mouseY >= this.guiTop + 165 && mouseY <= this.guiTop + 165 + 20) {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new EnterpriseBetCreateGui(this));
            }
            if (!this.hoveredAction.isEmpty() && this.openedBetId != "") {
                if (!(this.betInput.func_73781_b().isEmpty() || this.isNumeric(this.betInput.func_73781_b()) && !((double)Integer.parseInt(this.betInput.func_73781_b()) < this.hoveredMinBet))) {
                    return;
                }
                if (System.currentTimeMillis() - this.lastAction > 800L) {
                    this.lastAction = System.currentTimeMillis();
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new EnterpriseBetActionPacket((String)EnterpriseGui.enterpriseInfos.get("name"), this.openedBetId, this.hoveredAction, !this.betInput.func_73781_b().isEmpty() ? Integer.parseInt(this.betInput.func_73781_b()) : 0, this.selectedOption)));
                    this.hoveredAction = "";
                    this.openedBetId = "";
                    this.selectedOption = 0;
                }
            }
            if (this.hoveredBetId != "") {
                this.openedBetId = this.openedBetId != this.hoveredBetId ? this.hoveredBetId : "";
                this.hoveredBetId = "";
                this.selectedOption = 0;
                this.lastInputX = 0;
                this.lastInputY = 0;
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
            }
            if (this.hoveredOption != 0) {
                this.selectedOption = this.selectedOption != this.hoveredOption ? this.hoveredOption : 0;
                this.hoveredOption = 0;
            }
        }
        this.betInput.func_73793_a(mouseX, mouseY, mouseButton);
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

    public boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }
}

