/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiTextField
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.renderer.texture.DynamicTexture
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.network.packet.Packet
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.faction.WarRequestGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionAdminEnemyListPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionGetImagePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;
import org.lwjgl.opengl.GL11;

public class AdminWarRequestListGUI
extends GuiScreen {
    protected int xSize = 319;
    protected int ySize = 248;
    private int guiLeft;
    private int guiTop;
    private RenderItem itemRenderer = new RenderItem();
    public static ArrayList<HashMap<String, Object>> warsInfos = new ArrayList();
    public int hoveredRequestId = -1;
    public String hoveredStatus = "";
    public String selectedStatus = "";
    public boolean expandStatus = false;
    private GuiScrollBarFaction scrollBarRequests;
    public static boolean loaded = false;
    private GuiTextField countrySearch;
    public static List<String> availableStatus = Arrays.asList("all", "waiting_validation", "refused", "waiting_conditions_att", "waiting_conditions_def", "in_progress", "finished", "cancelled");

    public void func_73866_w_() {
        super.func_73866_w_();
        loaded = false;
        this.guiLeft = (this.field_73880_f - this.xSize) / 2;
        this.guiTop = (this.field_73881_g - this.ySize) / 2;
        warsInfos.clear();
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionAdminEnemyListPacket()));
        this.scrollBarRequests = new GuiScrollBarFaction(this.guiLeft + 303, this.guiTop + 55, 172);
        this.countrySearch = new GuiTextField(this.field_73886_k, this.guiLeft + 209, this.guiTop + 23, 97, 10);
        this.countrySearch.func_73786_a(false);
        this.countrySearch.func_73804_f(25);
        this.selectedStatus = availableStatus.get(0);
    }

    public void func_73876_c() {
        this.countrySearch.func_73780_a();
    }

    public void func_73863_a(int mouseX, int mouseY, float par3) {
        this.func_73873_v_();
        this.hoveredRequestId = -1;
        this.hoveredStatus = "";
        ClientEventHandler.STYLE.bindTexture("faction_war_requests");
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
        this.drawScaledString(I18n.func_135053_a((String)"faction.enemy.requests.title"), this.guiLeft + 14, this.guiTop + 210, 0xFFFFFF, 1.5f, false, false);
        GL11.glPopMatrix();
        this.countrySearch.func_73795_f();
        if (warsInfos.size() > 0) {
            String tooltipToDraw = "";
            GUIUtils.startGLScissor(this.guiLeft + 47, this.guiTop + 53, 256, 176);
            int yOffset = 0;
            for (HashMap<String, Object> warInfos : warsInfos) {
                BufferedImage image;
                if (!this.countrySearch.func_73781_b().isEmpty() && !((String)warInfos.get("factionATTName")).toLowerCase().contains(this.countrySearch.func_73781_b().toLowerCase()) && !((String)warInfos.get("factionDEFName")).toLowerCase().contains(this.countrySearch.func_73781_b().toLowerCase()) || !this.selectedStatus.equals("all") && !((String)warInfos.get("status")).startsWith(this.selectedStatus)) continue;
                int offsetX = this.guiLeft + 47;
                Float offsetY = Float.valueOf((float)(this.guiTop + 53 + yOffset) + this.getSlideWars());
                ClientEventHandler.STYLE.bindTexture("faction_war_requests");
                ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.floatValue(), 47, 53, 256, 48, 512.0f, 512.0f, false);
                if (mouseX >= this.guiLeft + 46 && mouseX <= this.guiLeft + 46 + 262 && mouseY >= this.guiTop + 53 && mouseY <= this.guiTop + 53 + 178 && mouseX >= offsetX && mouseX <= offsetX + 256 && (float)mouseY >= offsetY.floatValue() && (float)mouseY <= offsetY.floatValue() + 48.0f) {
                    this.hoveredRequestId = ((Double)warInfos.get("id")).intValue();
                }
                ClientEventHandler.STYLE.bindTexture("faction_war_requests");
                if (!ClientProxy.flagsTexture.containsKey((String)warInfos.get("factionATTName"))) {
                    if (!ClientProxy.base64FlagsByFactionName.containsKey((String)warInfos.get("factionATTName"))) {
                        ClientProxy.base64FlagsByFactionName.put((String)warInfos.get("factionATTName"), "");
                        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionGetImagePacket((String)warInfos.get("factionATTName"))));
                    } else if (ClientProxy.base64FlagsByFactionName.containsKey((String)warInfos.get("factionATTName")) && !ClientProxy.base64FlagsByFactionName.get((String)warInfos.get("factionATTName")).isEmpty()) {
                        image = ClientProxy.decodeToImage(ClientProxy.base64FlagsByFactionName.get((String)warInfos.get("factionATTName")));
                        ClientProxy.flagsTexture.put((String)warInfos.get("factionATTName"), new DynamicTexture(image));
                    }
                }
                if (ClientProxy.flagsTexture.containsKey((String)warInfos.get("factionATTName"))) {
                    GL11.glBindTexture((int)3553, (int)ClientProxy.flagsTexture.get((String)warInfos.get("factionATTName")).func_110552_b());
                    ModernGui.drawScaledCustomSizeModalRect(offsetX + 7, offsetY.intValue() + 5, 0.0f, 0.0f, 156, 78, 18, 10, 156.0f, 78.0f, false);
                }
                this.drawScaledString((String)warInfos.get("factionATTName"), offsetX + 28, offsetY.intValue() + 7, 0xFFFFFF, 1.0f, false, false);
                this.drawScaledString(" \u00a7cvs", offsetX + 28 + this.field_73886_k.func_78256_a((String)warInfos.get("factionATTName")), offsetY.intValue() + 7, 0xFFFFFF, 1.0f, false, false);
                if (!ClientProxy.flagsTexture.containsKey((String)warInfos.get("factionDEFName"))) {
                    if (!ClientProxy.base64FlagsByFactionName.containsKey((String)warInfos.get("factionDEFName"))) {
                        ClientProxy.base64FlagsByFactionName.put((String)warInfos.get("factionDEFName"), "");
                        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionGetImagePacket((String)warInfos.get("factionDEFName"))));
                    } else if (ClientProxy.base64FlagsByFactionName.containsKey((String)warInfos.get("factionDEFName")) && !ClientProxy.base64FlagsByFactionName.get((String)warInfos.get("factionDEFName")).isEmpty()) {
                        image = ClientProxy.decodeToImage(ClientProxy.base64FlagsByFactionName.get((String)warInfos.get("factionDEFName")));
                        ClientProxy.flagsTexture.put((String)warInfos.get("factionDEFName"), new DynamicTexture(image));
                    }
                }
                if (ClientProxy.flagsTexture.containsKey((String)warInfos.get("factionDEFName"))) {
                    GL11.glBindTexture((int)3553, (int)ClientProxy.flagsTexture.get((String)warInfos.get("factionDEFName")).func_110552_b());
                    ModernGui.drawScaledCustomSizeModalRect(offsetX + 7, offsetY.intValue() + 17, 0.0f, 0.0f, 156, 78, 18, 10, 156.0f, 78.0f, false);
                }
                this.drawScaledString((String)warInfos.get("factionDEFName"), offsetX + 28, offsetY.intValue() + 19, 0xFFFFFF, 1.0f, false, false);
                this.drawScaledString(I18n.func_135053_a((String)("faction.enemy.status." + warInfos.get("status"))), offsetX + 253 - this.field_73886_k.func_78256_a(I18n.func_135053_a((String)("faction.enemy.status." + warInfos.get("status")))), offsetY.intValue() + 7, 0xFFFFFF, 1.0f, false, false);
                this.drawScaledString(I18n.func_135053_a((String)("faction.enemy.reason." + warInfos.get("reason"))), offsetX + 7, offsetY.intValue() + 33, 0xFFFFFF, 1.0f, false, false);
                Date date = new Date(((Double)warInfos.get("creationTime")).longValue());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyy HH:mm");
                this.drawScaledString("\u00a7b" + simpleDateFormat.format(date), offsetX + 253 - this.field_73886_k.func_78256_a(simpleDateFormat.format(date)), offsetY.intValue() + 33, 0xFFFFFF, 1.0f, false, false);
                yOffset += 48;
            }
            GUIUtils.endGLScissor();
            if (!this.expandStatus) {
                this.scrollBarRequests.draw(mouseX, mouseY);
            }
            this.drawScaledString(I18n.func_135053_a((String)("faction.enemy.status." + this.selectedStatus)), this.guiLeft + 50, this.guiTop + 23, 0xFFFFFF, 1.0f, false, false);
            if (this.expandStatus) {
                ClientEventHandler.STYLE.bindTexture("faction_war_requests");
                for (int i = 0; i < availableStatus.size(); ++i) {
                    ClientEventHandler.STYLE.bindTexture("faction_war_requests");
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 46, this.guiTop + 36 + 19 * i, 0, 311, 120, 20, 512.0f, 512.0f, false);
                    this.drawScaledString(I18n.func_135053_a((String)("faction.enemy.status." + availableStatus.get(i))), this.guiLeft + 50, this.guiTop + 36 + 19 * i + 6, 0xFFFFFF, 1.0f, false, false);
                    if (mouseX <= this.guiLeft + 46 || mouseX >= this.guiLeft + 46 + 120 || mouseY <= this.guiTop + 36 + 19 * i || mouseY >= this.guiTop + 36 + 19 * i + 20) continue;
                    this.hoveredStatus = availableStatus.get(i);
                }
            }
        }
    }

    private float getSlideWars() {
        int countWars = 0;
        for (HashMap<String, Object> warInfos : warsInfos) {
            if (!this.countrySearch.func_73781_b().isEmpty() && !((String)warInfos.get("factionATTName")).toLowerCase().contains(this.countrySearch.func_73781_b().toLowerCase()) && !((String)warInfos.get("factionDEFName")).toLowerCase().contains(this.countrySearch.func_73781_b().toLowerCase()) || !this.selectedStatus.equals("all") && !((String)warInfos.get("status")).startsWith(this.selectedStatus)) continue;
            ++countWars;
        }
        return countWars > 3 ? (float)(-(countWars - 3) * 48) * this.scrollBarRequests.getSliderValue() : 0.0f;
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
                this.field_73882_e.func_71373_a(null);
            }
            if (!this.expandStatus && this.hoveredRequestId != -1) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new WarRequestGUI(this.hoveredRequestId, this));
            }
            if (mouseX >= this.guiLeft + 46 && mouseX <= this.guiLeft + 46 + 120 && mouseY >= this.guiTop + 17 && mouseY <= this.guiTop + 17 + 20) {
                boolean bl = this.expandStatus = !this.expandStatus;
            }
            if (this.hoveredStatus != null && !this.hoveredStatus.isEmpty()) {
                this.selectedStatus = this.hoveredStatus;
                this.hoveredStatus = "";
                this.expandStatus = false;
                this.scrollBarRequests.reset();
            }
        }
        this.countrySearch.func_73793_a(mouseX, mouseY, mouseButton);
    }

    protected void func_73869_a(char typedChar, int keyCode) {
        if (this.countrySearch.func_73802_a(typedChar, keyCode)) {
            this.scrollBarRequests.reset();
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
}

