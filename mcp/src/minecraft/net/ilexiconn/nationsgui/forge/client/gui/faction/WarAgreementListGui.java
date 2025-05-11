/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.network.packet.Packet
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.faction.WarAgreementCreateGui;
import net.ilexiconn.nationsgui.forge.client.gui.faction.WarGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionAgreementListPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionEnemyAgreementCancelPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionEnemyAgreementUpdateStatusPacket;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class WarAgreementListGui
extends GuiScreen {
    public static ArrayList<HashMap<String, Object>> agreements = new ArrayList();
    public static boolean canCreateAgreement = false;
    public static String playerFactionId = "";
    public HashMap<String, Object> hoveredAgreement = null;
    public HashMap<String, Object> selectedAgreement = null;
    protected int xSize = 226;
    protected int ySize = 248;
    private int guiLeft;
    private int guiTop;
    private RenderItem itemRenderer = new RenderItem();
    public static boolean loaded = false;
    private GuiScrollBarFaction scrollBar;
    boolean statusExpanded = false;
    private String selectedStatus = "";
    private String hoveredStatus = "";
    private String hoveredAction = "";
    private EntityPlayer player;
    public static int warId;
    private String faction1;
    private String faction2;
    private List<String> availableStatus = Arrays.asList("all", "waiting", "active", "refused", "expired");

    public WarAgreementListGui(EntityPlayer player, int warId, String faction1, String faction2) {
        this.player = player;
        WarAgreementListGui.warId = warId;
        this.faction1 = faction1;
        this.faction2 = faction2;
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        this.guiLeft = (this.field_73880_f - this.xSize) / 2;
        this.guiTop = (this.field_73881_g - this.ySize) / 2;
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionAgreementListPacket(warId)));
        this.scrollBar = new GuiScrollBarFaction(this.guiLeft + 211, this.guiTop + 42, 173);
        this.selectedStatus = this.availableStatus.get(0);
    }

    public void func_73863_a(int mouseX, int mouseY, float par3) {
        this.hoveredStatus = "";
        this.hoveredAgreement = null;
        this.hoveredAction = "";
        this.func_73873_v_();
        List<String> tooltipToDraw = null;
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        ClientEventHandler.STYLE.bindTexture("faction_war_agreement_list");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512.0f, 512.0f, false);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(this.guiLeft + 12), (float)(this.guiTop + 190), (float)0.0f);
        GL11.glRotatef((float)-90.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        GL11.glTranslatef((float)(-(this.guiLeft + 14)), (float)(-(this.guiTop + 190)), (float)0.0f);
        this.drawScaledString(I18n.func_135053_a((String)"faction.enemy.agreement.title"), this.guiLeft + 12, this.guiTop + 190, 0xFFFFFF, 1.5f, false, false);
        GL11.glPopMatrix();
        ClientEventHandler.STYLE.bindTexture("faction_war_agreement_list");
        if (mouseX >= this.guiLeft + 214 && mouseX <= this.guiLeft + 214 + 9 && mouseY >= this.guiTop - 8 && mouseY <= this.guiTop - 8 + 10) {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 214, this.guiTop - 8, 46, 248, 9, 10, 512.0f, 512.0f, false);
        } else {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 214, this.guiTop - 8, 46, 258, 9, 10, 512.0f, 512.0f, false);
        }
        if (loaded) {
            if (!this.selectedStatus.isEmpty()) {
                this.drawScaledString(I18n.func_135053_a((String)("faction.enemy.agreement.status." + this.selectedStatus)), this.guiLeft + 49, this.guiTop + 23, 0xFFFFFF, 1.0f, false, false);
            }
            if (this.selectedAgreement == null) {
                GUIUtils.startGLScissor(this.guiLeft + 47, this.guiTop + 40, 164, 177);
                int yOffset = 0;
                for (HashMap<String, Object> agreement : agreements) {
                    if (!this.selectedStatus.equals("all") && !((String)agreement.get("status")).startsWith(this.selectedStatus)) continue;
                    int offsetX = this.guiLeft + 47;
                    Float offsetY = Float.valueOf((float)(this.guiTop + 40 + yOffset) + this.getSlideAgreements());
                    ClientEventHandler.STYLE.bindTexture("faction_war_agreement_list");
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.floatValue(), 47, 40, 164, 35, 512.0f, 512.0f, false);
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 4, offsetY.floatValue() + 4.0f, 37, 275, 24, 17, 512.0f, 512.0f, false);
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 10, offsetY.floatValue() + 7.0f, agreement.get("type").equals("no_missile") ? 100 : (agreement.get("type").equals("peace") ? 112 : 124), 251, 12, 12, 512.0f, 512.0f, false);
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 4, offsetY.floatValue() + 21.0f, 64, agreement.get("status").equals("refused") ? 275 : (agreement.get("status").equals("waiting") ? 284 : (agreement.get("status").equals("active") ? 293 : 302)), 24, 8, 512.0f, 512.0f, false);
                    this.drawScaledString(I18n.func_135053_a((String)("faction.enemy.agreement.type." + agreement.get("type"))), offsetX + 35, offsetY.intValue() + 8, 0xFFFFFF, 1.0f, false, false);
                    Date date = new Date(((Double)agreement.get("creationTime")).longValue());
                    SimpleDateFormat dateFormatDay = new SimpleDateFormat("dd/MM");
                    this.drawScaledString("\u00a77" + I18n.func_135053_a((String)"faction.enemy.agreement.send_date") + " " + dateFormatDay.format(date), offsetX + 35, offsetY.intValue() + 18, 0xFFFFFF, 1.0f, false, false);
                    ClientEventHandler.STYLE.bindTexture("faction_war_agreement_list");
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 128, offsetY.floatValue() + 9.0f, 0, 272, 32, 15, 512.0f, 512.0f, false);
                    if (!this.statusExpanded && mouseX >= offsetX + 128 && mouseX <= offsetX + 128 + 32 && (float)mouseY >= offsetY.floatValue() + 9.0f && (float)mouseY <= offsetY.floatValue() + 9.0f + 15.0f) {
                        ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 128, offsetY.floatValue() + 9.0f, 0, 287, 32, 15, 512.0f, 512.0f, false);
                        this.hoveredAgreement = agreement;
                    }
                    this.drawScaledString(I18n.func_135053_a((String)"faction.enemy.agreement.see"), offsetX + 145, offsetY.intValue() + 13, 0, 1.0f, true, false);
                    yOffset += 35;
                }
                GUIUtils.endGLScissor();
                if (!this.statusExpanded) {
                    this.scrollBar.draw(mouseX, mouseY);
                }
                if (canCreateAgreement) {
                    ClientEventHandler.STYLE.bindTexture("faction_war_agreement_list");
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 46, this.guiTop + 220, 0, 427, 170, 15, 512.0f, 512.0f, false);
                    if (mouseX >= this.guiLeft + 46 && mouseX <= this.guiLeft + 46 + 170 && mouseY >= this.guiTop + 220 && mouseY <= this.guiTop + 220 + 15) {
                        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 46, this.guiTop + 220, 0, 442, 170, 15, 512.0f, 512.0f, false);
                        this.hoveredAction = "create";
                    }
                    this.drawScaledString(I18n.func_135053_a((String)"faction.enemy.agreement.create"), this.guiLeft + 131, this.guiTop + 224, 0xFFFFFF, 1.0f, true, false);
                }
            } else {
                ModernGui.drawNGBlackSquare(this.guiLeft + 46, this.guiTop + 39, 170, 196);
                ClientEventHandler.STYLE.bindTexture("faction_war_agreement_list");
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                ClientEventHandler.STYLE.bindTexture("faction_war_agreement_list");
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 47, this.guiTop + 40, 47, 40, 164, 35, 512.0f, 512.0f, false);
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 51, this.guiTop + 44, 37, 275, 24, 17, 512.0f, 512.0f, false);
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 57, this.guiTop + 47, this.selectedAgreement.get("type").equals("no_missile") ? 100 : (this.selectedAgreement.get("type").equals("peace") ? 112 : 124), 251, 12, 12, 512.0f, 512.0f, false);
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 51, this.guiTop + 61, 64, this.selectedAgreement.get("status").equals("refused") ? 275 : (this.selectedAgreement.get("status").equals("waiting") ? 284 : (this.selectedAgreement.get("status").equals("active") ? 293 : 302)), 24, 8, 512.0f, 512.0f, false);
                this.drawScaledString(I18n.func_135053_a((String)("faction.enemy.agreement.type." + this.selectedAgreement.get("type"))), this.guiLeft + 82, this.guiTop + 48, 0xFFFFFF, 1.0f, false, false);
                Date date = new Date(((Double)this.selectedAgreement.get("creationTime")).longValue());
                SimpleDateFormat dateFormatDay = new SimpleDateFormat("dd/MM");
                this.drawScaledString("\u00a77" + I18n.func_135053_a((String)"faction.enemy.agreement.send_date") + " " + dateFormatDay.format(date), this.guiLeft + 82, this.guiTop + 58, 0xFFFFFF, 1.0f, false, false);
                ClientEventHandler.STYLE.bindTexture("faction_war_agreement_list");
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 175, this.guiTop + 49, 0, 272, 32, 15, 512.0f, 512.0f, false);
                if (!this.statusExpanded && mouseX >= this.guiLeft + 175 && mouseX <= this.guiLeft + 175 + 32 && mouseY >= this.guiTop + 49 && mouseY <= this.guiTop + 49 + 15) {
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 175, this.guiTop + 49, 0, 287, 32, 15, 512.0f, 512.0f, false);
                    this.hoveredAgreement = this.selectedAgreement;
                }
                this.drawScaledString(I18n.func_135053_a((String)"faction.enemy.agreement.back"), this.guiLeft + 192, this.guiTop + 53, 0, 1.0f, true, false);
                ClientEventHandler.STYLE.bindTexture("faction_war_agreement_list");
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 54, this.guiTop + 84, 95, 251, 3, 3, 512.0f, 512.0f, false);
                this.drawScaledString("\u00a77" + I18n.func_135053_a((String)"faction.enemy.agreement.status") + " \u00a77> " + I18n.func_135053_a((String)("faction.enemy.agreement.status.short." + this.selectedAgreement.get("status"))), this.guiLeft + 64, this.guiTop + 82, 0xFFFFFF, 1.0f, false, false);
                ClientEventHandler.STYLE.bindTexture("faction_war_agreement_list");
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 51, this.guiTop + 96, 61, 251, 9, 11, 512.0f, 512.0f, false);
                if (this.selectedAgreement.get("status").equals("active") && this.selectedAgreement.containsKey("signatureTime")) {
                    Date dateEnd = new Date(((Double)this.selectedAgreement.get("value")).longValue() * 86400000L + ((Double)this.selectedAgreement.get("signatureTime")).longValue());
                    SimpleDateFormat dateFormatEnd = new SimpleDateFormat("dd/MM/yyy HH:mm");
                    this.drawScaledString("\u00a77" + I18n.func_135053_a((String)"faction.enemy.agreement.end") + " \u00a77> \u00a7e" + dateFormatEnd.format(dateEnd), this.guiLeft + 64, this.guiTop + 99, 0xFFFFFF, 1.0f, false, false);
                } else {
                    this.drawScaledString("\u00a77" + I18n.func_135053_a((String)"faction.enemy.agreement.duration") + " \u00a77> \u00a7e" + ((Double)this.selectedAgreement.get("value")).intValue() + " " + I18n.func_135053_a((String)"faction.enemy.rewards.valueType.dayLong"), this.guiLeft + 64, this.guiTop + 99, 0xFFFFFF, 1.0f, false, false);
                }
                ClientEventHandler.STYLE.bindTexture("faction_war_agreement_list");
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 51, this.guiTop + 114, 61, 263, 10, 10, 512.0f, 512.0f, false);
                this.drawScaledString("\u00a77" + I18n.func_135053_a((String)"faction.enemy.agreement.conditions"), this.guiLeft + 64, this.guiTop + 116, 0xFFFFFF, 1.0f, false, false);
                int infoX = this.guiLeft + 64 + this.field_73886_k.func_78256_a(I18n.func_135053_a((String)"faction.enemy.agreement.conditions")) + 3;
                ClientEventHandler.STYLE.bindTexture("faction_war_agreement_list");
                ModernGui.drawModalRectWithCustomSizedTexture(infoX, this.guiTop + 114, 71, 251, 10, 11, 512.0f, 512.0f, false);
                if (mouseX >= infoX && mouseX <= infoX + 10 && mouseY >= this.guiTop + 114 && mouseY <= this.guiTop + 114 + 11) {
                    tooltipToDraw = Arrays.asList(I18n.func_135053_a((String)"faction.enemy.agreement.conditions.tooltip").split("##"));
                }
                if (this.selectedAgreement.get("conditions") != null && !((String)this.selectedAgreement.get("conditions")).isEmpty()) {
                    int index = 0;
                    for (String condition : ((String)this.selectedAgreement.get("conditions")).split(",")) {
                        ClientProxy.loadCountryFlag(condition.split("#")[2]);
                        if (ClientProxy.flagsTexture.containsKey(condition.split("#")[2])) {
                            this.drawScaledString("\u00a77>", this.guiLeft + 53, this.guiTop + 131 + 12 * index, 0xFFFFFF, 1.0f, false, false);
                            GL11.glBindTexture((int)3553, (int)ClientProxy.flagsTexture.get(condition.split("#")[2]).func_110552_b());
                            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 61, this.guiTop + 129 + 12 * index, 0.0f, 0.0f, 156, 78, 18, 10, 156.0f, 78.0f, false);
                            this.drawScaledString(condition.split("#")[1] + " " + I18n.func_135053_a((String)("faction.enemy.agreement.conditions." + condition.split("#")[0])), this.guiLeft + 83, this.guiTop + 131 + 12 * index, 0xFFFFFF, 1.0f, false, false);
                        }
                        ++index;
                    }
                } else {
                    this.drawScaledString("\u00a7c\u2716 " + I18n.func_135053_a((String)"faction.enemy.none"), this.guiLeft + 53, this.guiTop + 129, 0xFFFFFF, 1.0f, false, false);
                }
                if (canCreateAgreement && this.selectedAgreement.get("status").equals("waiting") && (playerFactionId.equals(this.selectedAgreement.get("factionATT")) || playerFactionId.equals(this.selectedAgreement.get("factionDEF"))) && !this.selectedAgreement.get("factionSender").equals(playerFactionId)) {
                    ClientEventHandler.STYLE.bindTexture("faction_war_agreement_list");
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 52, this.guiTop + 217, 360, 208, 73, 15, 512.0f, 512.0f, false);
                    if (mouseX >= this.guiLeft + 52 && mouseX <= this.guiLeft + 52 + 73 && mouseY >= this.guiTop + 217 && mouseY <= this.guiTop + 217 + 15) {
                        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 52, this.guiTop + 217, 360, 223, 73, 15, 512.0f, 512.0f, false);
                        this.hoveredAction = "accept";
                    }
                    this.drawScaledString(I18n.func_135053_a((String)"faction.enemy.agreement.accept"), this.guiLeft + 88, this.guiTop + 221, 0xFFFFFF, 1.0f, true, false);
                    ClientEventHandler.STYLE.bindTexture("faction_war_agreement_list");
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 137, this.guiTop + 217, 439, 208, 73, 15, 512.0f, 512.0f, false);
                    if (mouseX >= this.guiLeft + 137 && mouseX <= this.guiLeft + 137 + 73 && mouseY >= this.guiTop + 217 && mouseY <= this.guiTop + 217 + 15) {
                        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 137, this.guiTop + 217, 439, 223, 73, 15, 512.0f, 512.0f, false);
                        this.hoveredAction = "refuse";
                    }
                    this.drawScaledString(I18n.func_135053_a((String)"faction.enemy.agreement.refuse"), this.guiLeft + 173, this.guiTop + 221, 0xFFFFFF, 1.0f, true, false);
                } else if (canCreateAgreement && this.selectedAgreement.get("status").equals("waiting") && (playerFactionId.equals(this.selectedAgreement.get("factionATT")) || playerFactionId.equals(this.selectedAgreement.get("factionDEF"))) && this.selectedAgreement.get("factionSender").equals(playerFactionId)) {
                    ClientEventHandler.STYLE.bindTexture("faction_war_agreement_list");
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 52, this.guiTop + 217, 0, 457, 158, 15, 512.0f, 512.0f, false);
                    if (mouseX >= this.guiLeft + 52 && mouseX <= this.guiLeft + 52 + 158 && mouseY >= this.guiTop + 217 && mouseY <= this.guiTop + 217 + 15) {
                        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 52, this.guiTop + 217, 0, 472, 158, 15, 512.0f, 512.0f, false);
                        this.hoveredAction = "cancel";
                    }
                    this.drawScaledString(I18n.func_135053_a((String)"faction.enemy.agreement.cancel"), this.guiLeft + 131, this.guiTop + 221, 0xFFFFFF, 1.0f, true, false);
                }
            }
            if (this.statusExpanded) {
                for (int i = 0; i < this.availableStatus.size(); ++i) {
                    ClientEventHandler.STYLE.bindTexture("faction_war_agreement_list");
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 46, this.guiTop + 37 + i * 19, 0, 312, 170, 19, 512.0f, 512.0f, false);
                    this.drawScaledString(I18n.func_135053_a((String)("faction.enemy.agreement.status." + this.availableStatus.get(i))), this.guiLeft + 49, this.guiTop + 37 + i * 19 + 5, 0xFFFFFF, 1.0f, false, false);
                    if (mouseX < this.guiLeft + 46 || mouseX > this.guiLeft + 46 + 170 || mouseY < this.guiTop + 37 + i * 19 || mouseY > this.guiTop + 37 + i * 19 + 19) continue;
                    this.hoveredStatus = this.availableStatus.get(i);
                }
            }
        }
        if (tooltipToDraw != null && !tooltipToDraw.isEmpty()) {
            this.drawHoveringText(tooltipToDraw, mouseX, mouseY, this.field_73886_k);
        }
        GL11.glEnable((int)2896);
        RenderHelper.func_74519_b();
    }

    private float getSlideAgreements() {
        int agreementCount = 0;
        for (HashMap<String, Object> agreement : agreements) {
            if (!this.selectedStatus.equals("all") && !((String)agreement.get("status")).startsWith(this.selectedStatus)) continue;
            ++agreementCount;
        }
        return agreementCount > 5 ? (float)(-(agreementCount - 5) * 35) * this.scrollBar.getSliderValue() : 0.0f;
    }

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            if (mouseX >= this.guiLeft + 46 && mouseX <= this.guiLeft + 46 + 170 && mouseY >= this.guiTop + 17 && mouseY <= this.guiTop + 17 + 20) {
                boolean bl = this.statusExpanded = !this.statusExpanded;
            }
            if (mouseX > this.guiLeft + 214 && mouseX < this.guiLeft + 214 + 9 && mouseY > this.guiTop - 8 && mouseY < this.guiTop - 8 + 10) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                this.field_73882_e.func_71373_a((GuiScreen)new WarGUI());
            }
            if (!this.hoveredStatus.isEmpty()) {
                this.selectedAgreement = null;
                this.selectedStatus = this.hoveredStatus;
                this.hoveredStatus = "";
                this.statusExpanded = false;
                this.scrollBar.reset();
            }
            if (this.hoveredAgreement != null) {
                this.selectedAgreement = this.selectedAgreement != this.hoveredAgreement ? this.hoveredAgreement : null;
                this.hoveredAgreement = null;
            }
            if (canCreateAgreement && this.hoveredAction.equals("create")) {
                this.field_73882_e.func_71373_a((GuiScreen)new WarAgreementCreateGui(this.player, warId, this.faction1, this.faction2));
            }
            if (canCreateAgreement && this.hoveredAction.equals("cancel") && this.selectedAgreement != null) {
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionEnemyAgreementCancelPacket(((Double)this.selectedAgreement.get("id")).intValue())));
                this.selectedStatus = this.availableStatus.get(0);
                this.scrollBar.reset();
                this.selectedAgreement = null;
            }
            if (canCreateAgreement && this.selectedAgreement != null && (this.hoveredAction.equals("accept") || this.hoveredAction.equals("refuse"))) {
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionEnemyAgreementUpdateStatusPacket(((Double)this.selectedAgreement.get("id")).intValue(), this.hoveredAction.equals("accept") ? "active" : "refused")));
                this.selectedStatus = this.availableStatus.get(0);
                this.scrollBar.reset();
                this.selectedAgreement = null;
            }
        }
        super.func_73864_a(mouseX, mouseY, mouseButton);
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
}

