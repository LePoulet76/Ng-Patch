/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.AbstractClientPlayer
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiTextField
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.PlayerAbsenceRequestUpdateStatusPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class AbsenceRequestGUI
extends GuiScreen {
    public static HashMap<String, String> absenceInfos = new HashMap();
    public static boolean loaded = false;
    public static String playerName;
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
    public List<String> availableConditions = Arrays.asList("exams", "vacation", "other");
    public String selectedReward = "";
    public ArrayList<String> availableRewards = new ArrayList<String>(Arrays.asList("dollars", "power", "claims", "peace"));
    public HashMap<String, Integer> data_ATT = null;
    public HashMap<String, Integer> data_DEF = null;
    public String winner = null;
    protected int xSize = 319;
    protected int ySize = 248;
    private int guiLeft;
    private int guiTop;
    private RenderItem itemRenderer = new RenderItem();
    private GuiTextField linkForumInput;
    private GuiTextField conditionInput;
    private GuiTextField rewardInput;
    private GuiScrollBarFaction scrollBarAvailableConditions;
    private GuiScrollBarFaction scrollBarAvailableRewards;
    private GuiScrollBarFaction scrollBarConditions;
    private GuiScrollBarFaction scrollBarRewards;
    private GuiScrollBarFaction scrollBarFinishConditions;
    private GuiScrollBarFaction scrollBarFinishRewards;
    private GuiScreen guiFrom;

    public AbsenceRequestGUI(String playerName, HashMap<String, String> absenceInfos, GuiScreen guiFrom) {
        AbsenceRequestGUI.playerName = playerName;
        AbsenceRequestGUI.absenceInfos = absenceInfos;
        this.guiFrom = guiFrom;
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        loaded = false;
        this.guiLeft = (this.field_73880_f - this.xSize) / 2;
        this.guiTop = (this.field_73881_g - this.ySize) / 2;
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
        String absenceStartTime = absenceInfos.get("startTime");
        String absenceEndTime = absenceInfos.get("endTime");
        String tooltipToDraw = "";
        if (absenceInfos.size() > 0) {
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
            this.drawScaledString(I18n.func_135053_a((String)"faction.absence.title"), this.guiLeft + 14, this.guiTop + 210, 0xFFFFFF, 1.5f, false, false);
            GL11.glPopMatrix();
            ClientProxy.loadCountryFlag(absenceInfos.get("name"));
            if (ClientProxy.flagsTexture.containsKey(absenceInfos.get("name"))) {
                GL11.glBindTexture((int)3553, (int)ClientProxy.flagsTexture.get(absenceInfos.get("name")).func_110552_b());
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 57, this.guiTop + 27, 0.0f, 0.0f, 156, 78, 27, 15, 156.0f, 78.0f, false);
            }
            if (absenceInfos.get("name").contains("Empire")) {
                this.drawScaledString("Empire", this.guiLeft + 90, this.guiTop + 27, 0xFFFFFF, 1.0f, false, false);
                this.drawScaledString(absenceInfos.get("name").replace("Empire", ""), this.guiLeft + 90, this.guiTop + 37, 0xFFFFFF, 1.0f, false, false);
            } else {
                this.drawScaledString(absenceInfos.get("name"), this.guiLeft + 90, this.guiTop + 32, 0xFFFFFF, 1.0f, false, false);
            }
            if (!ClientProxy.cacheHeadPlayer.containsKey(absenceInfos.get("playerName"))) {
                try {
                    ResourceLocation resourceLocation = AbstractClientPlayer.field_110314_b;
                    resourceLocation = AbstractClientPlayer.func_110311_f((String)absenceInfos.get("playerName"));
                    AbstractClientPlayer.func_110304_a((ResourceLocation)resourceLocation, (String)absenceInfos.get("playerName"));
                    ClientProxy.cacheHeadPlayer.put(absenceInfos.get("playerName"), resourceLocation);
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } else {
                Minecraft.func_71410_x().field_71446_o.func_110577_a(ClientProxy.cacheHeadPlayer.get(absenceInfos.get("playerName")));
                this.field_73882_e.func_110434_K().func_110577_a(ClientProxy.cacheHeadPlayer.get(absenceInfos.get("playerName")));
                GUIUtils.drawScaledCustomSizeModalRect(this.guiLeft + 205 + 13, this.guiTop + 37 + 10, 8.0f, 16.0f, 8, -8, -27, -25, 64.0f, 64.0f);
            }
            this.drawScaledString(absenceInfos.get("playerName"), this.guiLeft + 225, this.guiTop + 32, 0xFFFFFF, 1.0f, false, false);
            this.drawScaledString(absenceInfos.get("playerRole").toLowerCase(), this.guiLeft + 245, this.guiTop + 58, 0xFFFFFF, 1.0f, true, false);
            Date startDate = new Date(Double.valueOf(absenceStartTime).longValue());
            SimpleDateFormat startDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date endDate = new Date(Double.valueOf(absenceEndTime).longValue());
            SimpleDateFormat endDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            this.drawScaledString("\u00a7b" + startDateFormat.format(startDate), this.guiLeft + 110 - this.field_73886_k.func_78256_a(startDateFormat.format(startDate)), this.guiTop + 125, 0xFFFFFF, 1.0f, false, false);
            this.drawScaledString("\u00a7b" + endDateFormat.format(endDate), this.guiLeft + 245 - this.field_73886_k.func_78256_a(endDateFormat.format(endDate)) + this.field_73886_k.func_78256_a(startDateFormat.format(startDate)), this.guiTop + 125, 0xFFFFFF, 1.0f, false, false);
            String status = I18n.func_135053_a((String)("faction.absence.status." + absenceInfos.get("status")));
            this.drawScaledString(status, this.guiLeft + 108 + 6, this.guiTop + 58, 0xFFFFFF, 1.0f, true, false);
            ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
            if (status.startsWith("\u00a72")) {
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 108 - this.field_73886_k.func_78256_a(status) / 2 - 8, this.guiTop + 56, 110, 251, 10, 11, 512.0f, 512.0f, false);
            } else if (status.startsWith("\u00a74")) {
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 108 - this.field_73886_k.func_78256_a(status) / 2 - 8, this.guiTop + 56, 120, 251, 10, 11, 512.0f, 512.0f, false);
            } else {
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 108 - this.field_73886_k.func_78256_a(status) / 2 - 8, this.guiTop + 56, 130, 251, 10, 11, 512.0f, 512.0f, false);
            }
            this.drawScaledString(I18n.func_135053_a((String)"faction.absence.reason"), this.guiLeft + 46, this.guiTop + 78, 0, 1.0f, false, false);
            this.drawScaledString(I18n.func_135053_a((String)("faction.absence.reason." + absenceInfos.get("reason"))), this.guiLeft + 50, this.guiTop + 94, 0xFFFFFF, 1.0f, false, false);
            this.drawScaledString(I18n.func_135053_a((String)"faction.enemy.creationDate"), this.guiLeft + 182, this.guiTop + 78, 0, 1.0f, false, false);
            Date date = new Date(Double.valueOf(absenceInfos.get("creationTime")).longValue());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyy HH:mm");
            this.drawScaledString(simpleDateFormat.format(date), this.guiLeft + 186, this.guiTop + 94, 0xFFFFFF, 1.0f, false, false);
            String[] descriptionWords = null;
            descriptionWords = I18n.func_135053_a((String)("faction.absence.status.details." + absenceInfos.get("status"))).split(" ");
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
            if (absenceInfos.get("status").equals("waiting_validation")) {
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
            } else if (absenceInfos.get("status").equals("accepted")) {
                ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 46, this.guiTop + 219, 0, 432, 127, 15, 512.0f, 512.0f, false);
                if (mouseX >= this.guiLeft + 46 && mouseX <= this.guiLeft + 46 + 127 && mouseY >= this.guiTop + 219 && mouseY <= this.guiTop + 219 + 15) {
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 46, this.guiTop + 219, 0, 447, 127, 15, 512.0f, 512.0f, false);
                    this.hoveredAction = "staff_cancel";
                }
                this.drawScaledString(I18n.func_135053_a((String)"faction.enemy.cancel_request"), this.guiLeft + 110, this.guiTop + 223, 0xFFFFFF, 1.0f, true, false);
            }
            if (absenceInfos.get("status").equals("in_progress") || absenceInfos.get("status").equals("validated")) {
                ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 46, this.guiTop + 219, 0, 432, 127, 15, 512.0f, 512.0f, false);
                if (mouseX >= this.guiLeft + 46 && mouseX <= this.guiLeft + 46 + 127 && mouseY >= this.guiTop + 219 && mouseY <= this.guiTop + 219 + 15) {
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 46, this.guiTop + 219, 0, 447, 127, 15, 512.0f, 512.0f, false);
                    this.hoveredAction = "cancel";
                }
                this.drawScaledString(I18n.func_135053_a((String)"faction.enemy.cancel_request"), this.guiLeft + 110, this.guiTop + 223, 0xFFFFFF, 1.0f, true, false);
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
        return absenceInfos.get("conditions").split(",").length > 3 ? (float)(-(absenceInfos.get("conditions").split(",").length - 3) * 20) * this.scrollBarFinishConditions.getSliderValue() : 0.0f;
    }

    private float getSlideFinishRewards() {
        return absenceInfos.get("rewards").split(",").length > 3 ? (float)(-(absenceInfos.get("rewards").split(",").length - 3) * 20) * this.scrollBarFinishRewards.getSliderValue() : 0.0f;
    }

    public void drawTooltip(String text, int mouseX, int mouseY) {
        int mouseXGui = mouseX - this.guiLeft;
        int mouseYGui = mouseY - this.guiTop;
        this.drawHoveringText(Arrays.asList(text), mouseX, mouseY, this.field_73886_k);
    }

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        super.func_73864_a(mouseX, mouseY, mouseButton);
        if (mouseButton == 0) {
            if (mouseX > this.guiLeft + 305 && mouseX < this.guiLeft + 305 + 9 && mouseY > this.guiTop - 6 && mouseY < this.guiTop - 6 + 10) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                this.field_73882_e.func_71373_a(this.guiFrom);
            }
            if (!this.hoveredAction.isEmpty()) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                if (this.hoveredAction.equals("cancel")) {
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new PlayerAbsenceRequestUpdateStatusPacket(absenceInfos.get("playerName"), "cancelled")));
                    this.field_73882_e.func_71373_a(null);
                } else if (this.hoveredAction.equals("staff_refuse")) {
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new PlayerAbsenceRequestUpdateStatusPacket(absenceInfos.get("playerName"), "refused")));
                    this.field_73882_e.func_71373_a(this.guiFrom);
                } else if (this.hoveredAction.equals("staff_accept") || this.hoveredAction.equals("accept")) {
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new PlayerAbsenceRequestUpdateStatusPacket(absenceInfos.get("playerName"), "accepted")));
                    this.field_73882_e.func_71373_a(this.guiFrom);
                } else if (this.hoveredAction.equals("staff_cancel")) {
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new PlayerAbsenceRequestUpdateStatusPacket(absenceInfos.get("playerName"), "cancelled")));
                    this.field_73882_e.func_71373_a(this.guiFrom);
                }
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
        return "faction_war_request_1";
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

