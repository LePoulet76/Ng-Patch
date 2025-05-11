/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.AbstractClientPlayer
 *  net.minecraft.client.gui.GuiTextField
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
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
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseContractActionPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseContractDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class EnterpriseContractGUI
extends TabbedEnterpriseGUI {
    public static ArrayList<HashMap<String, Object>> contractsInfos = new ArrayList();
    public Integer hoveredContractId = -1;
    public Integer openedContractId = -1;
    public String hoveredAction = "";
    public String hoveredStatus = "";
    public String selectedStatus = "";
    public boolean expandStatus = false;
    public int lastInputX = 0;
    public int lastInputY = 0;
    private GuiTextField refundInput;
    private RenderItem itemRenderer = new RenderItem();
    private GuiScrollBarFaction scrollBarStatus;
    private GuiScrollBarFaction scrollBarContract;
    public static boolean loaded = false;
    private GuiTextField contractSearch;
    private Long lastAction = 0L;
    public static List<String> availableStatus = Arrays.asList("all", "waiting_enterprise", "in_progress", "done", "dispute_open_enterprise", "dispute_open_client", "dispute_close_enterprise", "dispute_close_client", "refused", "cancelled", "waiting_garant");

    @Override
    public void func_73866_w_() {
        super.func_73866_w_();
        loaded = false;
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new EnterpriseContractDataPacket((String)EnterpriseGui.enterpriseInfos.get("name"))));
        this.scrollBarStatus = new GuiScrollBarFaction(this.guiLeft + 379, this.guiTop + 54, 90);
        this.scrollBarContract = new GuiScrollBarFaction(this.guiLeft + 389, this.guiTop + 59, 174);
        this.contractSearch = new GuiTextField(this.field_73886_k, this.guiLeft + 152, this.guiTop + 36, 97, 10);
        this.contractSearch.func_73786_a(false);
        this.contractSearch.func_73804_f(20);
        this.selectedStatus = availableStatus.get(0);
        this.refundInput = new GuiTextField(this.field_73886_k, this.guiLeft, this.guiTop, 51, 10);
        this.refundInput.func_73786_a(false);
        this.refundInput.func_73804_f(7);
    }

    public void func_73876_c() {
        this.contractSearch.func_73780_a();
        this.refundInput.func_73780_a();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        this.hoveredAction = "";
        this.hoveredContractId = -1;
        this.hoveredStatus = "";
        ClientEventHandler.STYLE.bindTexture("enterprise_contract");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512.0f, 512.0f, false);
        this.drawScaledString(I18n.func_135053_a((String)"enterprise.contract.title"), this.guiLeft + 131, this.guiTop + 15, 0x191919, 1.4f, false, false);
        this.contractSearch.func_73795_f();
        if (contractsInfos.size() > 0) {
            String tooltipToDraw = "";
            GUIUtils.startGLScissor(this.guiLeft + 131, this.guiTop + 59, 253, 174);
            int yOffset = 0;
            for (HashMap<String, Object> contractInfos : contractsInfos) {
                String pattern;
                if (!this.contractSearch.func_73781_b().isEmpty() && !((String)contractInfos.get("clientName")).toLowerCase().contains(this.contractSearch.func_73781_b().toLowerCase()) || !this.selectedStatus.equals("all") && !((String)contractInfos.get("status")).equals(this.selectedStatus)) continue;
                int offsetX = this.guiLeft + 131;
                Float offsetY = Float.valueOf((float)(this.guiTop + 59 + yOffset) + this.getSlideContracts());
                if (this.openedContractId != -1 && this.openedContractId.intValue() == ((Double)contractInfos.get("id")).intValue()) {
                    ClientEventHandler.STYLE.bindTexture("enterprise_contract");
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.floatValue(), 0, 385, 253, 104, 512.0f, 512.0f, false);
                    yOffset += 105;
                    if (mouseX >= offsetX && mouseX <= offsetX + 253 && (float)mouseY >= offsetY.floatValue() && (float)mouseY <= offsetY.floatValue() + 104.0f) {
                        this.hoveredContractId = ((Double)contractInfos.get("id")).intValue();
                    }
                } else {
                    ClientEventHandler.STYLE.bindTexture("enterprise_contract");
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.floatValue(), 0, 350, 253, 34, 512.0f, 512.0f, false);
                    yOffset += 35;
                    if (mouseX >= offsetX && mouseX <= offsetX + 253 && (float)mouseY >= offsetY.floatValue() && (float)mouseY <= offsetY.floatValue() + 34.0f) {
                        this.hoveredContractId = ((Double)contractInfos.get("id")).intValue();
                    }
                }
                try {
                    ResourceLocation resourceLocation = AbstractClientPlayer.field_110314_b;
                    resourceLocation = AbstractClientPlayer.func_110311_f((String)((String)contractInfos.get("clientName")));
                    AbstractClientPlayer.func_110304_a((ResourceLocation)resourceLocation, (String)((String)contractInfos.get("clientName")));
                    Minecraft.func_71410_x().field_71446_o.func_110577_a(resourceLocation);
                    this.field_73882_e.func_110434_K().func_110577_a(resourceLocation);
                    GUIUtils.drawScaledCustomSizeModalRect(offsetX + 10 + 3, offsetY.intValue() + 10 + 3, 8.0f, 16.0f, 8, -8, -10, -10, 64.0f, 64.0f);
                    GUIUtils.drawScaledCustomSizeModalRect(offsetX + 10 + 3, offsetY.intValue() + 10 + 3, 40.0f, 16.0f, 8, -8, -10, -10, 64.0f, 64.0f);
                }
                catch (Exception resourceLocation) {
                    // empty catch block
                }
                ClientEventHandler.STYLE.bindTexture("enterprise_contract");
                this.drawScaledString((String)contractInfos.get("clientName"), offsetX + 15, offsetY.intValue() + 5, 0xFFFFFF, 1.0f, false, false);
                this.drawScaledString("\u00a72" + ((Double)contractInfos.get("price")).intValue() + "$", offsetX + 126, offsetY.intValue() + 5, 0xFFFFFF, 1.0f, true, false);
                this.drawScaledString((String)contractInfos.get("creationDate"), offsetX + 249 - this.field_73886_k.func_78256_a((String)contractInfos.get("creationDate")), offsetY.intValue() + 5, 0xFFFFFF, 1.0f, false, false);
                this.drawScaledString(I18n.func_135053_a((String)("enterprise.contract.status." + contractInfos.get("status"))), offsetX + 4, offsetY.intValue() + 24, 0xFFFFFF, 1.0f, false, false);
                String diffText = "";
                boolean expired = false;
                if ((Double)contractInfos.get("deadlineTime") > 0.0) {
                    long diff = ((Double)contractInfos.get("deadlineTime")).longValue() - System.currentTimeMillis();
                    if (diff > 0L) {
                        diffText = this.formatDiff(diff);
                        diffText = I18n.func_135053_a((String)"enterprise.contract.ends_in") + diffText;
                    } else {
                        diffText = I18n.func_135053_a((String)"enterprise.contract.expired");
                        expired = true;
                    }
                    diffText = diffText.trim();
                } else if ((Double)contractInfos.get("deadlineTime") == 0.0) {
                    diffText = I18n.func_135053_a((String)"enterprise.contract.at_end_assault");
                } else if ((Double)contractInfos.get("deadlineTime") == -10.0) {
                    diffText = "-";
                }
                this.drawScaledString("\u00a78" + diffText, offsetX + 249 - this.field_73886_k.func_78256_a(diffText), offsetY.intValue() + 24, 0xFFFFFF, 1.0f, false, false);
                if (this.openedContractId == -1 || this.openedContractId.intValue() != ((Double)contractInfos.get("id")).intValue()) continue;
                String description = (String)contractInfos.get("description");
                if (description.matches("PVP##.*")) {
                    pattern = I18n.func_135053_a((String)"enterprise.create.text_description.pvp");
                    pattern = pattern.replace("<playerFaction>", description.split("##")[1]);
                    pattern = pattern.replace("<factionHelpers>", description.split("##")[2]);
                    pattern = pattern.replace("<opponentFaction>", description.split("##")[3]);
                    pattern = pattern.replace("<opponentHelpers>", description.split("##")[4]);
                    pattern = pattern.replace("<price>", description.split("##")[5]);
                    description = pattern = pattern.replace("<condition>", I18n.func_135053_a((String)("enterprise.contract.condition." + description.split("##")[6])));
                } else if (description.matches("REPAIR##.*")) {
                    pattern = I18n.func_135053_a((String)"enterprise.create.text_description.repair");
                    pattern = pattern.replace("<factionName>", description.split("##")[1]);
                    pattern = pattern.replace("<price>", description.split("##")[2]);
                    description = pattern = pattern.replace("<amount>", description.split("##")[3]);
                } else if (description.contains("LOAN##")) {
                    pattern = I18n.func_135053_a((String)"enterprise.create.text_description.loan");
                    pattern = pattern.replace("<amount>", description.split("##")[1]);
                    pattern = pattern.replace("<duration>", description.split("##")[3]);
                    pattern = pattern.replace("<rate>", description.split("##")[4]);
                    pattern = pattern.replace("<totalDue>", description.split("##")[2]);
                    pattern = pattern.replace("<waranty>", I18n.func_135053_a((String)("enterprise.contract.loan.type." + description.split("##")[5])));
                    description = pattern = pattern.replace("<warant>", description.split("##")[5].equalsIgnoreCase("garant") ? "(" + description.split("##")[6] + ")" : "");
                }
                String[] descriptionWords = description.replaceAll("\u00a7[0-9a-z]{1}", "").split(" ");
                String line = "";
                int lineNumber = 0;
                for (String descWord : descriptionWords) {
                    StringBuilder stringBuilder = new StringBuilder();
                    if ((double)this.field_73886_k.func_78256_a(stringBuilder.append(line).append(descWord).toString()) * 0.9 <= 245.0) {
                        if (!line.equals("")) {
                            line = line + " ";
                        }
                        line = line + descWord;
                        continue;
                    }
                    this.drawScaledString(line, offsetX + 4, offsetY.intValue() + 38 + lineNumber * 10, 0xFFFFFF, 0.9f, false, false);
                    ++lineNumber;
                    line = descWord;
                }
                this.drawScaledString(line, offsetX + 4, offsetY.intValue() + 38 + lineNumber * 10, 0xFFFFFF, 0.9f, false, false);
                if (contractInfos.containsKey("items")) {
                    int itemOffset = 0;
                    for (String itemString : (ArrayList)contractInfos.get("items")) {
                        String[] itemInfos = itemString.split("#");
                        ItemStack stack = new ItemStack(Integer.parseInt(itemInfos[0]), Integer.parseInt(itemInfos[2]), Integer.parseInt(itemInfos[1]));
                        GL11.glEnable((int)2929);
                        RenderHelper.func_74520_c();
                        this.itemRenderer.func_82406_b(this.field_73886_k, Minecraft.func_71410_x().func_110434_K(), stack, offsetX + 4 + 18 * itemOffset, offsetY.intValue() + 38 + lineNumber * 10 + 10);
                        this.itemRenderer.func_94148_a(this.field_73886_k, Minecraft.func_71410_x().func_110434_K(), stack, offsetX + 4 + 18 * itemOffset, offsetY.intValue() + 38 + lineNumber * 10 + 10, stack.field_77994_a + "");
                        RenderHelper.func_74518_a();
                        GL11.glDisable((int)2896);
                        if (mouseX >= offsetX + 4 + 18 * itemOffset && mouseX <= offsetX + 4 + 18 * itemOffset + 18 && mouseY >= offsetY.intValue() + 38 + lineNumber * 10 + 10 - 18 && mouseY >= offsetY.intValue() + 38 + lineNumber * 10 + 10) {
                            tooltipToDraw = stack.func_82833_r();
                        }
                        ++itemOffset;
                    }
                }
                String status = (String)contractInfos.get("status");
                String clientName = (String)contractInfos.get("clientName");
                boolean validButton = false;
                boolean invalidButton = false;
                ClientEventHandler.STYLE.bindTexture("enterprise_contract");
                if (status.contains("dispute_open") && ((Boolean)EnterpriseGui.enterpriseInfos.get("canManageLitige")).booleanValue()) {
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 140, offsetY.intValue() + 82, 121, 270, 55, 18, 512.0f, 512.0f, false);
                    this.drawScaledString(I18n.func_135053_a((String)"enterprise.contract.btn_label.dispute_for_client"), offsetX + 168, offsetY.intValue() + 87, 0xFFFFFF, 1.0f, true, false);
                    validButton = true;
                } else if (status.equals("waiting_enterprise") && EnterpriseGui.hasPermission("contracts") && ((Boolean)EnterpriseGui.enterpriseInfos.get("isInEnterprise")).booleanValue()) {
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 140, offsetY.intValue() + 82, 121, 270, 55, 18, 512.0f, 512.0f, false);
                    this.drawScaledString(I18n.func_135053_a((String)"enterprise.contract.btn_label.accept"), offsetX + 168, offsetY.intValue() + 87, 0xFFFFFF, 1.0f, true, false);
                    validButton = true;
                } else if (status.equals("in_progress") && Minecraft.func_71410_x().field_71439_g.field_71092_bJ.equals(clientName)) {
                    if (!(EnterpriseGui.enterpriseInfos.get("type").equals("trader") || EnterpriseGui.enterpriseInfos.get("type").equals("repair") || EnterpriseGui.enterpriseInfos.get("type").equals("loan"))) {
                        ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 140, offsetY.intValue() + 82, 121, 270, 55, 18, 512.0f, 512.0f, false);
                        this.drawScaledString(I18n.func_135053_a((String)"enterprise.contract.btn_label.validate"), offsetX + 168, offsetY.intValue() + 87, 0xFFFFFF, 1.0f, true, false);
                        validButton = true;
                    } else if (EnterpriseGui.enterpriseInfos.get("type").equals("loan")) {
                        ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 140, offsetY.intValue() + 82, 121, 270, 55, 18, 512.0f, 512.0f, false);
                        this.drawScaledString(I18n.func_135053_a((String)"enterprise.contract.btn_label.refund"), offsetX + 168, offsetY.intValue() + 87, 0xFFFFFF, 1.0f, true, false);
                        validButton = true;
                        ModernGui.drawNGBlackSquare(offsetX + 196, offsetY.intValue() + 82, 55, 18);
                        if (this.lastInputX != offsetX + 196 + 3 || this.lastInputY != offsetY.intValue() + 82 + 6) {
                            this.lastInputX = offsetX + 196 + 3;
                            this.lastInputY = offsetY.intValue() + 82 + 6;
                            this.refundInput = new GuiTextField(this.field_73886_k, offsetX + 196 + 3, offsetY.intValue() + 82 + 6, 54, 10);
                            this.refundInput.func_73786_a(false);
                            this.refundInput.func_73804_f(7);
                            this.refundInput.func_73782_a("0");
                        }
                        this.refundInput.func_73795_f();
                        if (mouseX >= offsetX + 196 && mouseX <= offsetX + 196 + 55 && mouseY >= offsetY.intValue() + 82 && mouseY <= offsetY.intValue() + 82 + 18) {
                            this.hoveredContractId = -1;
                        }
                    }
                } else if (status.equals("waiting_garant") && Minecraft.func_71410_x().field_71439_g.field_71092_bJ.equals(contractInfos.get("garant"))) {
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 140, offsetY.intValue() + 82, 121, 270, 55, 18, 512.0f, 512.0f, false);
                    this.drawScaledString(I18n.func_135053_a((String)"enterprise.contract.btn_label.sign"), offsetX + 168, offsetY.intValue() + 87, 0xFFFFFF, 1.0f, true, false);
                    validButton = true;
                    this.drawScaledString("\u00a7c" + I18n.func_135053_a((String)"enterprise.contract.label.garant_warning") + " \u00a74" + String.format("%.0f", (Double)contractInfos.get("price") / 2.0) + "$", offsetX + 4, offsetY.intValue() + 87, 0xFFFFFF, 1.0f, false, false);
                }
                ClientEventHandler.STYLE.bindTexture("enterprise_contract");
                if (status.contains("dispute_open") && ((Boolean)EnterpriseGui.enterpriseInfos.get("canManageLitige")).booleanValue()) {
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 196, offsetY.intValue() + 82, 121, 251, 55, 18, 512.0f, 512.0f, false);
                    this.drawScaledString(I18n.func_135053_a((String)"enterprise.contract.btn_label.dispute_for_enterprise"), offsetX + 224, offsetY.intValue() + 87, 0xFFFFFF, 1.0f, true, false);
                    invalidButton = true;
                } else if (status.equals("waiting_enterprise") && EnterpriseGui.hasPermission("contracts") && ((Boolean)EnterpriseGui.enterpriseInfos.get("isInEnterprise")).booleanValue()) {
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 196, offsetY.intValue() + 82, 121, 251, 55, 18, 512.0f, 512.0f, false);
                    this.drawScaledString(I18n.func_135053_a((String)"enterprise.contract.btn_label.decline"), offsetX + 224, offsetY.intValue() + 87, 0xFFFFFF, 1.0f, true, false);
                    invalidButton = true;
                } else if ((status.equals("waiting_enterprise") || status.equals("waiting_garant")) && Minecraft.func_71410_x().field_71439_g.field_71092_bJ.equals(clientName)) {
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 196, offsetY.intValue() + 82, 121, 251, 55, 18, 512.0f, 512.0f, false);
                    this.drawScaledString(I18n.func_135053_a((String)"enterprise.contract.btn_label.cancel"), offsetX + 224, offsetY.intValue() + 87, 0xFFFFFF, 1.0f, true, false);
                    invalidButton = true;
                } else if (status.equals("in_progress") && !EnterpriseGui.enterpriseInfos.get("type").equals("trader") && !EnterpriseGui.enterpriseInfos.get("type").equals("repair") && (!EnterpriseGui.enterpriseInfos.get("type").equals("loan") || expired && ((Boolean)EnterpriseGui.enterpriseInfos.get("isInEnterprise")).booleanValue() && EnterpriseGui.hasPermission("contracts")) && (Minecraft.func_71410_x().field_71439_g.field_71092_bJ.equals(clientName) || EnterpriseGui.hasPermission("contracts"))) {
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 196, offsetY.intValue() + 82, 121, 251, 55, 18, 512.0f, 512.0f, false);
                    this.drawScaledString(I18n.func_135053_a((String)"enterprise.contract.btn_label.dispute"), offsetX + 224, offsetY.intValue() + 87, 0xFFFFFF, 1.0f, true, false);
                    invalidButton = true;
                } else if (status.equals("dispute_open_enterprise") && EnterpriseGui.hasPermission("contracts")) {
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 196, offsetY.intValue() + 82, 121, 251, 55, 18, 512.0f, 512.0f, false);
                    this.drawScaledString(I18n.func_135053_a((String)"enterprise.contract.btn_label.cancel"), offsetX + 224, offsetY.intValue() + 87, 0xFFFFFF, 1.0f, true, false);
                    invalidButton = true;
                } else if (status.equals("dispute_open_client") && Minecraft.func_71410_x().field_71439_g.field_71092_bJ.equals(clientName)) {
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 196, offsetY.intValue() + 82, 121, 251, 55, 18, 512.0f, 512.0f, false);
                    this.drawScaledString(I18n.func_135053_a((String)"enterprise.contract.btn_label.cancel"), offsetX + 224, offsetY.intValue() + 87, 0xFFFFFF, 1.0f, true, false);
                    invalidButton = true;
                } else if (status.equals("in_progress") && EnterpriseGui.enterpriseInfos.get("type").equals("trader") && Minecraft.func_71410_x().field_71439_g.field_71092_bJ.equals(clientName)) {
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 196, offsetY.intValue() + 82, 121, 251, 55, 18, 512.0f, 512.0f, false);
                    this.drawScaledString(I18n.func_135053_a((String)"enterprise.contract.btn_label.retire"), offsetX + 224, offsetY.intValue() + 87, 0xFFFFFF, 1.0f, true, false);
                    invalidButton = true;
                } else if (status.equals("in_progress") && EnterpriseGui.enterpriseInfos.get("type").equals("repair") && Minecraft.func_71410_x().field_71439_g.field_71092_bJ.equals(clientName)) {
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 196, offsetY.intValue() + 82, 121, 251, 55, 18, 512.0f, 512.0f, false);
                    this.drawScaledString(I18n.func_135053_a((String)"enterprise.contract.btn_label.cancel"), offsetX + 224, offsetY.intValue() + 87, 0xFFFFFF, 1.0f, true, false);
                    invalidButton = true;
                } else if (status.equals("in_progress") && EnterpriseGui.enterpriseInfos.get("type").equals("trader") && EnterpriseGui.hasPermission("contracts")) {
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 196, offsetY.intValue() + 82, 121, 251, 55, 18, 512.0f, 512.0f, false);
                    this.drawScaledString(I18n.func_135053_a((String)"enterprise.contract.btn_label.cancel"), offsetX + 224, offsetY.intValue() + 87, 0xFFFFFF, 1.0f, true, false);
                    invalidButton = true;
                } else if (status.equals("in_progress") && EnterpriseGui.enterpriseInfos.get("type").equals("repair") && EnterpriseGui.hasPermission("contracts")) {
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 196, offsetY.intValue() + 82, 121, 251, 55, 18, 512.0f, 512.0f, false);
                    this.drawScaledString(I18n.func_135053_a((String)"enterprise.contract.btn_label.cancel"), offsetX + 224, offsetY.intValue() + 87, 0xFFFFFF, 1.0f, true, false);
                    invalidButton = true;
                } else if (status.equals("waiting_garant") && Minecraft.func_71410_x().field_71439_g.field_71092_bJ.equals(contractInfos.get("garant"))) {
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 196, offsetY.intValue() + 82, 121, 251, 55, 18, 512.0f, 512.0f, false);
                    this.drawScaledString(I18n.func_135053_a((String)"enterprise.contract.btn_label.decline"), offsetX + 224, offsetY.intValue() + 87, 0xFFFFFF, 1.0f, true, false);
                    invalidButton = true;
                } else if ((status.equals("refused") || status.equals("done")) && Minecraft.func_71410_x().field_71439_g.field_71092_bJ.equals(clientName) && contractInfos.containsKey("items") && ((ArrayList)contractInfos.get("items")).size() > 0) {
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 196, offsetY.intValue() + 82, 121, 251, 55, 18, 512.0f, 512.0f, false);
                    this.drawScaledString(I18n.func_135053_a((String)"enterprise.contract.btn_label.get_items"), offsetX + 224, offsetY.intValue() + 87, 0xFFFFFF, 1.0f, true, false);
                    invalidButton = true;
                }
                if (validButton && mouseX >= offsetX + 140 && mouseX <= offsetX + 140 + 55 && mouseY >= offsetY.intValue() + 82 && mouseY <= offsetY.intValue() + 82 + 18) {
                    this.hoveredAction = "valid";
                    continue;
                }
                if (!invalidButton || mouseX < offsetX + 196 || mouseX > offsetX + 196 + 55 || mouseY < offsetY.intValue() + 82 || mouseY > offsetY.intValue() + 82 + 18) continue;
                this.hoveredAction = "invalid";
            }
            GUIUtils.endGLScissor();
            if (!this.expandStatus) {
                this.scrollBarContract.draw(mouseX, mouseY);
            }
            this.drawScaledString(I18n.func_135053_a((String)("enterprise.contract.status." + this.selectedStatus)), this.guiLeft + 278, this.guiTop + 36, 0xFFFFFF, 1.0f, false, false);
            if (this.expandStatus) {
                ClientEventHandler.STYLE.bindTexture("enterprise_contract");
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 274, this.guiTop + 49, 10, 251, 110, 99, 512.0f, 512.0f, false);
                GUIUtils.startGLScissor(this.guiLeft + 275, this.guiTop + 50, 104, 97);
                for (int i = 0; i < availableStatus.size(); ++i) {
                    int offsetX = this.guiLeft + 275;
                    Float offsetY = Float.valueOf((float)(this.guiTop + 50 + i * 20) + this.getSlideStatus());
                    ClientEventHandler.STYLE.bindTexture("enterprise_contract");
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.intValue(), 11, 252, 104, 20, 512.0f, 512.0f, false);
                    this.drawScaledString(I18n.func_135053_a((String)("enterprise.contract.status." + availableStatus.get(i))), offsetX + 3, offsetY.intValue() + 5, 0xFFFFFF, 1.0f, false, false);
                    if (mouseX <= offsetX || mouseX >= offsetX + 104 || !((float)mouseY > offsetY.floatValue()) || !((float)mouseY < offsetY.floatValue() + 20.0f)) continue;
                    this.hoveredStatus = availableStatus.get(i);
                }
                GUIUtils.endGLScissor();
                this.scrollBarStatus.draw(mouseX, mouseY);
            }
            if (tooltipToDraw != "") {
                this.drawHoveringText(Arrays.asList(tooltipToDraw), mouseX, mouseY, this.field_73886_k);
            }
        }
    }

    private float getSlideStatus() {
        return availableStatus.size() > 5 ? (float)(-(availableStatus.size() - 5) * 20) * this.scrollBarStatus.getSliderValue() : 0.0f;
    }

    private float getSlideContracts() {
        int offsetOpened = this.openedContractId != -1 ? 70 : 0;
        return this.openedContractId != -1 && contractsInfos.size() > 2 || contractsInfos.size() > 5 ? (float)(-((contractsInfos.size() - 5) * 35 + offsetOpened)) * this.scrollBarContract.getSliderValue() : 0.0f;
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
            if (!this.hoveredAction.isEmpty() && this.openedContractId != -1 && System.currentTimeMillis() - this.lastAction > 800L) {
                this.lastAction = System.currentTimeMillis();
                int refundAmount = 0;
                if (!this.refundInput.func_73781_b().isEmpty()) {
                    if (!this.isNumeric(this.refundInput.func_73781_b())) {
                        return;
                    }
                    refundAmount = Integer.parseInt(this.refundInput.func_73781_b());
                }
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new EnterpriseContractActionPacket((String)EnterpriseGui.enterpriseInfos.get("name"), this.openedContractId, this.hoveredAction, refundAmount)));
            }
            if (!this.expandStatus && this.hoveredContractId != -1) {
                this.openedContractId = this.openedContractId != this.hoveredContractId ? this.hoveredContractId : Integer.valueOf(-1);
                this.hoveredContractId = -1;
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
            }
            if (mouseX >= this.guiLeft + 365 && mouseX <= this.guiLeft + 365 + 19 && mouseY >= this.guiTop + 30 && mouseY <= this.guiTop + 30 + 20) {
                boolean bl = this.expandStatus = !this.expandStatus;
            }
            if (this.hoveredStatus != null && !this.hoveredStatus.isEmpty()) {
                this.selectedStatus = this.hoveredStatus;
                this.hoveredStatus = "";
                this.expandStatus = false;
                this.scrollBarContract.reset();
            }
        }
        this.contractSearch.func_73793_a(mouseX, mouseY, mouseButton);
        this.refundInput.func_73793_a(mouseX, mouseY, mouseButton);
    }

    protected void func_73869_a(char typedChar, int keyCode) {
        if (this.contractSearch.func_73802_a(typedChar, keyCode)) {
            this.openedContractId = -1;
        }
        this.refundInput.func_73802_a(typedChar, keyCode);
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

