/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
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
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseBonusGui;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseCapitalGui;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseGui;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseSalaryGui;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.TabbedEnterpriseGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseBankDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;

public class EnterpriseBankGUI
extends TabbedEnterpriseGUI {
    public static boolean loaded = false;
    public static HashMap<String, Object> enterpriseBankInfos;
    private ArrayList<HashMap<String, String>> cachedLogs = new ArrayList();
    private GuiScrollBarFaction scrollBarLogs;
    private GuiButton salaryButton;
    private GuiButton bonusButton;
    private GuiButton capitalButton;

    @Override
    public void func_73866_w_() {
        super.func_73866_w_();
        loaded = false;
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new EnterpriseBankDataPacket((String)EnterpriseGui.enterpriseInfos.get("name"))));
        this.scrollBarLogs = new GuiScrollBarFaction(this.guiLeft + 377, this.guiTop + 145, 80);
        this.cachedLogs = new ArrayList();
        this.salaryButton = new GuiButton(0, this.guiLeft + 10, this.guiTop + 165, 100, 20, I18n.func_135053_a((String)"enterprise.bank.btn.salary"));
        this.bonusButton = new GuiButton(1, this.guiLeft + 10, this.guiTop + 188, 100, 20, I18n.func_135053_a((String)"enterprise.bank.btn.bonus"));
        this.capitalButton = new GuiButton(2, this.guiLeft + 10, this.guiTop + 211, 100, 20, I18n.func_135053_a((String)"enterprise.bank.btn.capital"));
        if (!EnterpriseGui.hasPermission("salary")) {
            this.salaryButton.field_73742_g = false;
            this.bonusButton.field_73742_g = false;
        }
        if (!EnterpriseGui.hasPermission("capital")) {
            this.capitalButton.field_73742_g = false;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        String tooltipToDraw = "";
        ClientEventHandler.STYLE.bindTexture("enterprise_bank");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512.0f, 512.0f, false);
        this.salaryButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
        this.bonusButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
        this.capitalButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
        if (!this.salaryButton.field_73742_g) {
            if (mouseX > this.guiLeft + 10 && mouseX < this.guiLeft + 10 + 100 && mouseY > this.guiTop + 165 && mouseY < this.guiTop + 165 + 20) {
                tooltipToDraw = I18n.func_135053_a((String)"enterprise.bank.btn_disabled");
            } else if (mouseX > this.guiLeft + 10 && mouseX < this.guiLeft + 10 + 100 && mouseY > this.guiTop + 188 && mouseY < this.guiTop + 188 + 20) {
                tooltipToDraw = I18n.func_135053_a((String)"enterprise.bank.btn_disabled");
            }
        }
        if (!this.capitalButton.field_73742_g && mouseX > this.guiLeft + 10 && mouseX < this.guiLeft + 10 + 100 && mouseY > this.guiTop + 211 && mouseY < this.guiTop + 211 + 20) {
            tooltipToDraw = I18n.func_135053_a((String)"enterprise.bank.btn_disabled");
        }
        if (loaded) {
            this.drawScaledString(I18n.func_135053_a((String)"enterprise.bank.title") + " " + EnterpriseGui.enterpriseInfos.get("name"), this.guiLeft + 259, this.guiTop + 24, 3818599, 1.7f, true, false);
            this.drawScaledString(enterpriseBankInfos.get("bank") + "$", this.guiLeft + 257, this.guiTop + 107, 0xFFFFFF, 1.5f, true, false);
            this.drawScaledString(I18n.func_135053_a((String)"faction.bank.transactions"), this.guiLeft + 259, this.guiTop + 132, 3818599, 0.8f, false, false);
            if (mouseX >= this.guiLeft + 134 && mouseX <= this.guiLeft + 134 + 58 && mouseY >= this.guiTop + 140 && mouseY <= this.guiTop + 140 + 43) {
                tooltipToDraw = I18n.func_135053_a((String)"enterprise.bank.flux");
            } else if (mouseX >= this.guiLeft + 196 && mouseX <= this.guiLeft + 196 + 58 && mouseY >= this.guiTop + 140 && mouseY <= this.guiTop + 140 + 43) {
                tooltipToDraw = I18n.func_135053_a((String)"enterprise.bank.waiting_money");
            } else if (mouseX >= this.guiLeft + 134 && mouseX <= this.guiLeft + 134 + 58 && mouseY >= this.guiTop + 187 && mouseY <= this.guiTop + 187 + 43) {
                tooltipToDraw = I18n.func_135053_a((String)"enterprise.bank.salaries");
            } else if (mouseX >= this.guiLeft + 196 && mouseX <= this.guiLeft + 196 + 58 && mouseY >= this.guiTop + 187 && mouseY <= this.guiTop + 187 + 43) {
                tooltipToDraw = I18n.func_135053_a((String)"enterprise.bank.taxes");
            }
            String flux = (String)enterpriseBankInfos.get("flux");
            flux = flux.contains("-") ? "\u00a7c" + flux : "\u00a72+" + flux;
            String waiting_money = (String)enterpriseBankInfos.get("waiting_money");
            waiting_money = "\u00a72" + waiting_money;
            String salaries = (String)enterpriseBankInfos.get("salaries");
            salaries = salaries.contains("-") ? "\u00a7c" + salaries : "\u00a72+" + salaries;
            String taxes = (String)enterpriseBankInfos.get("taxes");
            taxes = taxes.contains("-") ? "\u00a7c" + taxes : "\u00a72+" + taxes;
            this.drawScaledString(flux + "$", this.guiLeft + 163, this.guiTop + 168, 0xFFFFFF, 1.0f, true, false);
            this.drawScaledString(waiting_money + "$", this.guiLeft + 225, this.guiTop + 168, 0xFFFFFF, 1.0f, true, false);
            this.drawScaledString(salaries + "$", this.guiLeft + 163, this.guiTop + 215, 0xFFFFFF, 1.0f, true, false);
            this.drawScaledString(taxes + "$", this.guiLeft + 225, this.guiTop + 215, 0xFFFFFF, 1.0f, true, false);
            ClientEventHandler.STYLE.bindTexture("enterprise_bank");
            if (enterpriseBankInfos.get("logs") != null && ((ArrayList)enterpriseBankInfos.get("logs")).size() > 0) {
                if (this.cachedLogs.size() == 0) {
                    for (int i = 0; i < ((ArrayList)enterpriseBankInfos.get("logs")).size(); ++i) {
                        HashMap<String, String> cachedLog = new HashMap<String, String>();
                        String line = (String)((ArrayList)enterpriseBankInfos.get("logs")).get(i);
                        if (line.split("##").length != 3) continue;
                        String amount = line.split("##")[1];
                        amount = amount.contains("-") ? amount.replace("-", "\u00a74-\u00a77") : "\u00a7a+\u00a77" + amount;
                        amount = amount + "$";
                        Long time = Long.parseLong(line.split("##")[2]);
                        long now = System.currentTimeMillis();
                        long diff = now - time;
                        String date = "\u00a78" + I18n.func_135053_a((String)"faction.bank.date_1");
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
                        String type = line.split("##")[0];
                        cachedLog.put("amount", amount);
                        cachedLog.put("date", date);
                        cachedLog.put("type", type);
                        this.cachedLogs.add(cachedLog);
                    }
                }
                if (EnterpriseGui.hasPermission("bank_log")) {
                    GUIUtils.startGLScissor(this.guiLeft + 261, this.guiTop + 142, 116, 86);
                    for (int j = 0; j < this.cachedLogs.size(); ++j) {
                        int offsetX = this.guiLeft + 261;
                        Float offsetY = Float.valueOf((float)(this.guiTop + 142 + j * 20) + this.getSlideLogs());
                        ClientEventHandler.STYLE.bindTexture("enterprise_bank");
                        ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.intValue(), 261, 142, 116, 20, 512.0f, 512.0f, false);
                        this.drawScaledString(this.cachedLogs.get(j).get("amount"), offsetX + 3, offsetY.intValue() + 3, 0xB4B4B4, 0.85f, false, false);
                        this.drawScaledString(this.cachedLogs.get(j).get("date"), offsetX + 3, offsetY.intValue() + 12, 0x666666, 0.65f, false, false);
                        ClientEventHandler.STYLE.bindTexture("enterprise_bank");
                        ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 104, offsetY.intValue() + 5, 148, 250, 10, 11, 512.0f, 512.0f, false);
                        if (mouseX <= offsetX + 104 || mouseX >= offsetX + 104 + 10 || !((float)mouseY > offsetY.floatValue() + 5.0f) || !((float)mouseY < offsetY.floatValue() + 5.0f + 11.0f)) continue;
                        String logType = this.cachedLogs.get(j).get("type");
                        tooltipToDraw = logType.contains("#") ? I18n.func_135053_a((String)("enterprise.bank.transaction.type." + logType.split("#")[0])).replace("<player>", logType.split("#")[1]) : I18n.func_135053_a((String)("enterprise.bank.transaction.type." + logType));
                    }
                    GUIUtils.endGLScissor();
                }
                if (mouseX > this.guiLeft + 259 && mouseX < this.guiLeft + 384 && mouseY > this.guiTop + 139 && mouseY < this.guiTop + 229) {
                    this.scrollBarLogs.draw(mouseX, mouseY);
                }
            }
            if (!tooltipToDraw.isEmpty()) {
                this.drawTooltip(tooltipToDraw, mouseX, mouseY);
            }
        }
    }

    @Override
    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0 && EnterpriseGui.enterpriseInfos != null) {
            if (this.salaryButton.field_73742_g && EnterpriseGui.hasPermission("salary") && mouseX >= this.guiLeft + 10 && mouseX <= this.guiLeft + 10 + 100 && mouseY >= this.guiTop + 165 && mouseY <= this.guiTop + 165 + 20) {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new EnterpriseSalaryGui(this));
            }
            if (this.bonusButton.field_73742_g && mouseX >= this.guiLeft + 10 && mouseX <= this.guiLeft + 10 + 100 && mouseY >= this.guiTop + 188 && mouseY <= this.guiTop + 188 + 20) {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new EnterpriseBonusGui(this));
            }
            if (this.capitalButton.field_73742_g && mouseX >= this.guiLeft + 10 && mouseX <= this.guiLeft + 10 + 100 && mouseY >= this.guiTop + 211 && mouseY <= this.guiTop + 211 + 20) {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new EnterpriseCapitalGui(this));
            }
        }
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    private float getSlideLogs() {
        return ((ArrayList)enterpriseBankInfos.get("logs")).size() > 4 ? (float)(-(((ArrayList)enterpriseBankInfos.get("logs")).size() - 4) * 20) * this.scrollBarLogs.getSliderValue() : 0.0f;
    }

    public void drawTooltip(String text, int mouseX, int mouseY) {
        int mouseXGui = mouseX - this.guiLeft;
        int mouseYGui = mouseY - this.guiTop;
        this.drawHoveringText(Arrays.asList(text), mouseX, mouseY, this.field_73886_k);
    }
}

