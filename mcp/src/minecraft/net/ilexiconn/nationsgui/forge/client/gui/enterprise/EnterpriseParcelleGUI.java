/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  net.minecraft.client.gui.GuiTextField
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.network.packet.Packet
 */
package net.ilexiconn.nationsgui.forge.client.gui.enterprise;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseGui;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.TabbedEnterpriseGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseParcelleActionPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseParcelleDataPacket;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;

public class EnterpriseParcelleGUI
extends TabbedEnterpriseGUI {
    public static ArrayList<HashMap<String, Object>> parcellesInfos = new ArrayList();
    public static boolean canSeeCoords = false;
    public String hoveredParcelleName = "";
    public String openedParcelleName = "";
    public String hoveredAction = "";
    public String hoveredStatus = "";
    public String selectedStatus = "";
    public boolean expandStatus = false;
    private GuiScrollBarFaction scrollBarStatus;
    private GuiScrollBarFaction scrollBarParcelle;
    public static boolean loaded = false;
    private GuiTextField parcelleSearch;
    private Long lastAction = 0L;
    public static List<String> availableStatus = Arrays.asList("all", "to_rent", "to_sell", "rent", "free");

    @Override
    public void func_73866_w_() {
        super.func_73866_w_();
        loaded = false;
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new EnterpriseParcelleDataPacket((String)EnterpriseGui.enterpriseInfos.get("name"))));
        this.scrollBarStatus = new GuiScrollBarFaction(this.guiLeft + 379, this.guiTop + 54, 90);
        this.scrollBarParcelle = new GuiScrollBarFaction(this.guiLeft + 389, this.guiTop + 59, 174);
        this.parcelleSearch = new GuiTextField(this.field_73886_k, this.guiLeft + 152, this.guiTop + 36, 97, 10);
        this.parcelleSearch.func_73786_a(false);
        this.parcelleSearch.func_73804_f(20);
        this.selectedStatus = availableStatus.get(0);
    }

    public void func_73876_c() {
        this.parcelleSearch.func_73780_a();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        this.hoveredAction = "";
        this.hoveredParcelleName = "";
        this.hoveredStatus = "";
        ClientEventHandler.STYLE.bindTexture("enterprise_parcelle");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512.0f, 512.0f, false);
        this.drawScaledString(I18n.func_135053_a((String)"enterprise.parcelle.title"), this.guiLeft + 131, this.guiTop + 15, 0x191919, 1.4f, false, false);
        this.parcelleSearch.func_73795_f();
        if (parcellesInfos.size() > 0) {
            String tooltipToDraw = "";
            GUIUtils.startGLScissor(this.guiLeft + 131, this.guiTop + 59, 253, 174);
            int yOffset = 0;
            for (HashMap<String, Object> parcelleInfos : parcellesInfos) {
                if (!this.parcelleSearch.func_73781_b().isEmpty() && !((String)parcelleInfos.get("clientName")).toLowerCase().contains(this.parcelleSearch.func_73781_b().toLowerCase()) && !((String)parcelleInfos.get("name")).toLowerCase().contains(this.parcelleSearch.func_73781_b().toLowerCase()) || !this.selectedStatus.equals("all") && !((String)parcelleInfos.get("status")).startsWith(this.selectedStatus)) continue;
                int offsetX = this.guiLeft + 131;
                Float offsetY = Float.valueOf((float)(this.guiTop + 59 + yOffset) + this.getSlideContracts());
                if (this.openedParcelleName != "" && this.openedParcelleName == parcelleInfos.get("name")) {
                    ClientEventHandler.STYLE.bindTexture("enterprise_parcelle");
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.floatValue(), 0, 385, 253, 104, 512.0f, 512.0f, false);
                    yOffset += 57;
                    if (mouseX >= offsetX && mouseX <= offsetX + 253 && (float)mouseY >= offsetY.floatValue() && (float)mouseY <= offsetY.floatValue() + 104.0f) {
                        this.hoveredParcelleName = (String)parcelleInfos.get("name");
                    }
                } else {
                    ClientEventHandler.STYLE.bindTexture("enterprise_parcelle");
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.floatValue(), 0, 350, 253, 34, 512.0f, 512.0f, false);
                    yOffset += 35;
                    if (EnterpriseGui.hasPermission("locations") && (!parcelleInfos.get("status").equals("free") || !((Boolean)parcelleInfos.get("isStaff")).booleanValue()) && mouseX >= offsetX && mouseX <= offsetX + 253 && (float)mouseY >= offsetY.floatValue() && (float)mouseY <= offsetY.floatValue() + 34.0f) {
                        this.hoveredParcelleName = (String)parcelleInfos.get("name");
                    }
                }
                ClientEventHandler.STYLE.bindTexture("enterprise_parcelle");
                this.drawScaledString((String)parcelleInfos.get("clientName"), offsetX + 4, offsetY.intValue() + 5, 0xFFFFFF, 1.0f, false, false);
                this.drawScaledString("\u00a72" + parcelleInfos.get("price"), offsetX + 126, offsetY.intValue() + 5, 0xFFFFFF, 1.0f, true, false);
                this.drawScaledString((String)parcelleInfos.get("name"), offsetX + 249 - this.field_73886_k.func_78256_a((String)parcelleInfos.get("name")), offsetY.intValue() + 5, 0xFFFFFF, 1.0f, false, false);
                if (((String)parcelleInfos.get("status")).contains("rent#")) {
                    this.drawScaledString(I18n.func_135053_a((String)"enterprise.parcelle.status.rent") + " (" + ((String)parcelleInfos.get("status")).split("#")[1] + "j)", offsetX + 4, offsetY.intValue() + 24, 0xFFFFFF, 1.0f, false, false);
                } else {
                    this.drawScaledString(I18n.func_135053_a((String)("enterprise.parcelle.status." + parcelleInfos.get("status"))), offsetX + 4, offsetY.intValue() + 24, 0xFFFFFF, 1.0f, false, false);
                }
                String signCoords = "???";
                if (canSeeCoords) {
                    signCoords = (String)parcelleInfos.get("sign");
                }
                this.drawScaledString("\u00a78" + signCoords, offsetX + 249 - this.field_73886_k.func_78256_a(signCoords), offsetY.intValue() + 24, 0xFFFFFF, 1.0f, false, false);
                if (this.openedParcelleName == "" || this.openedParcelleName != parcelleInfos.get("name")) continue;
                ClientEventHandler.STYLE.bindTexture("enterprise_parcelle");
                ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 196, offsetY.intValue() + 36, 121, 251, 55, 18, 512.0f, 512.0f, false);
                String action = "cancel";
                if (!parcelleInfos.get("status").equals("free")) {
                    this.drawScaledString(I18n.func_135053_a((String)"enterprise.parcelle.btn_label.cancel"), offsetX + 224, offsetY.intValue() + 41, 0xFFFFFF, 1.0f, true, false);
                } else {
                    this.drawScaledString(I18n.func_135053_a((String)"enterprise.parcelle.btn_label.remove"), offsetX + 224, offsetY.intValue() + 41, 0xFFFFFF, 1.0f, true, false);
                    action = "remove";
                }
                if (mouseX < offsetX + 196 || mouseX > offsetX + 196 + 55 || mouseY < offsetY.intValue() + 36 || mouseY > offsetY.intValue() + 36 + 18) continue;
                this.hoveredAction = action;
            }
            GUIUtils.endGLScissor();
            if (!this.expandStatus) {
                this.scrollBarParcelle.draw(mouseX, mouseY);
            }
            this.drawScaledString(I18n.func_135053_a((String)("enterprise.parcelle.status." + this.selectedStatus)), this.guiLeft + 278, this.guiTop + 36, 0xFFFFFF, 1.0f, false, false);
            if (this.expandStatus) {
                ClientEventHandler.STYLE.bindTexture("enterprise_parcelle");
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 274, this.guiTop + 49, 10, 251, 110, 99, 512.0f, 512.0f, false);
                GUIUtils.startGLScissor(this.guiLeft + 275, this.guiTop + 50, 104, 97);
                for (int i = 0; i < availableStatus.size(); ++i) {
                    int offsetX = this.guiLeft + 275;
                    Float offsetY = Float.valueOf((float)(this.guiTop + 50 + i * 20) + this.getSlideStatus());
                    ClientEventHandler.STYLE.bindTexture("enterprise_parcelle");
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.intValue(), 11, 252, 104, 20, 512.0f, 512.0f, false);
                    this.drawScaledString(I18n.func_135053_a((String)("enterprise.parcelle.status." + availableStatus.get(i))), offsetX + 3, offsetY.intValue() + 5, 0xFFFFFF, 1.0f, false, false);
                    if (mouseX <= offsetX || mouseX >= offsetX + 104 || !((float)mouseY > offsetY.floatValue()) || !((float)mouseY < offsetY.floatValue() + 20.0f)) continue;
                    this.hoveredStatus = availableStatus.get(i);
                }
                GUIUtils.endGLScissor();
                this.scrollBarStatus.draw(mouseX, mouseY);
            }
        }
    }

    private float getSlideStatus() {
        return availableStatus.size() > 5 ? (float)(-(availableStatus.size() - 5) * 20) * this.scrollBarStatus.getSliderValue() : 0.0f;
    }

    private float getSlideContracts() {
        int offsetOpened = this.openedParcelleName != "" ? 22 : 0;
        return this.openedParcelleName != "" && parcellesInfos.size() > 2 || parcellesInfos.size() > 5 ? (float)(-((parcellesInfos.size() - 5) * 35 + offsetOpened)) * this.scrollBarParcelle.getSliderValue() : 0.0f;
    }

    public void drawTooltip(String text, int mouseX, int mouseY) {
        int mouseXGui = mouseX - this.guiLeft;
        int mouseYGui = mouseY - this.guiTop;
        this.drawHoveringText(Arrays.asList(text), mouseX, mouseY, this.field_73886_k);
    }

    @Override
    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        super.func_73864_a(mouseX, mouseY, mouseButton);
        if (mouseButton == 0) {
            if (!this.hoveredAction.isEmpty() && this.openedParcelleName != "" && EnterpriseGui.hasPermission("locations") && System.currentTimeMillis() - this.lastAction > 800L) {
                this.lastAction = System.currentTimeMillis();
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new EnterpriseParcelleActionPacket((String)EnterpriseGui.enterpriseInfos.get("name"), this.openedParcelleName, this.hoveredAction)));
                this.hoveredAction = "";
                this.openedParcelleName = "";
            }
            if (!this.expandStatus && this.hoveredParcelleName != "") {
                this.openedParcelleName = this.openedParcelleName != this.hoveredParcelleName ? this.hoveredParcelleName : "";
                this.hoveredParcelleName = "";
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
            }
            if (mouseX >= this.guiLeft + 365 && mouseX <= this.guiLeft + 365 + 19 && mouseY >= this.guiTop + 30 && mouseY <= this.guiTop + 30 + 20) {
                boolean bl = this.expandStatus = !this.expandStatus;
            }
            if (this.hoveredStatus != null && !this.hoveredStatus.isEmpty()) {
                this.selectedStatus = this.hoveredStatus;
                this.hoveredStatus = "";
                this.expandStatus = false;
            }
        }
        this.parcelleSearch.func_73793_a(mouseX, mouseY, mouseButton);
    }

    protected void func_73869_a(char typedChar, int keyCode) {
        if (this.parcelleSearch.func_73802_a(typedChar, keyCode)) {
            this.openedParcelleName = "";
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
}

