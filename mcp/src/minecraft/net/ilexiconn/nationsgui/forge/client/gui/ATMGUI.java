/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.network.packet.Packet
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui;

import com.google.gson.Gson;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import net.halalaboos.cfont.CFontRenderer;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.ATMConvertPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.ATMDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class ATMGUI
extends GuiScreen {
    public static int GUI_SCALE = 3;
    public static int COLOR_LIGHT_GRAY = 10395075;
    public static int COLOR_DARK_BLUE = 2499659;
    public static int COLOR_LIGHT_BLUE = 6249630;
    public static int COLOR_WHITE = 0xDADAED;
    public static int COLOR_PINK = 0xAE6EEE;
    public static HashMap<String, Object> data = new HashMap();
    public static CFontRenderer semiBold40 = ModernGui.getCustomFont("georamaSemiBold", 40);
    public static Gson gson = new Gson();
    public static boolean loaded = false;
    public String hoveredAction = "";
    public int xSize = 221;
    public int ySize = 187;
    public RenderItem itemRenderer = new RenderItem();
    public int guiLeft;
    public int guiTop;
    public List<String> tooltipToDraw = new ArrayList<String>();
    public static int lastBalanceDollars = -1;
    public static int lastBalanceOrbs = -1;
    public static int lastATMDollars = -1;
    public static int lastATMPlaytime = -1;
    public static long lastAmountAnimation = -1L;

    public ATMGUI() {
        loaded = false;
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new ATMDataPacket()));
        this.guiLeft = (this.field_73880_f - this.xSize) / 2;
        this.guiTop = (this.field_73881_g - this.ySize) / 2;
    }

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            if (this.hoveredAction.equals("close")) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a(null);
            } else if (this.hoveredAction.equals("convert_dollars")) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new ATMConvertPacket("dollars")));
            } else if (this.hoveredAction.equals("convert_orbs")) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a(null);
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new ATMConvertPacket("orbs_playtime")));
            }
        }
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    public boolean func_73868_f() {
        return false;
    }

    public void func_73863_a(int mouseX, int mouseY, float par3) {
        this.func_73873_v_();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.tooltipToDraw.clear();
        this.hoveredAction = "";
        ClientEventHandler.STYLE.bindTexture("atm");
        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft, this.guiTop, 0 * GUI_SCALE, 0 * GUI_SCALE, this.xSize * GUI_SCALE, this.ySize * GUI_SCALE, this.xSize, this.ySize, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
        ClientEventHandler.STYLE.bindTexture("atm");
        if (mouseX >= this.guiLeft + 225 && mouseX <= this.guiLeft + 225 + 10 && mouseY >= this.guiTop + 0 && mouseY <= this.guiTop + 0 + 10) {
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 225, this.guiTop + 0, 245 * GUI_SCALE, 0 * GUI_SCALE, 10 * GUI_SCALE, 10 * GUI_SCALE, 10, 10, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
            this.hoveredAction = "close";
        } else {
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 225, this.guiTop + 0, 225 * GUI_SCALE, 0 * GUI_SCALE, 10 * GUI_SCALE, 10 * GUI_SCALE, 10, 10, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
        }
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"gui.atm.title"), this.guiLeft + 9, this.guiTop + 3, COLOR_WHITE, 1.0f, "left", false, "georamaExtraBold", 28);
        if (loaded) {
            int balanceDollars = ((Double)data.get("player_dollars")).intValue();
            if (lastBalanceDollars != -1 && System.currentTimeMillis() - lastAmountAnimation < 1000L) {
                double gap = (Double)data.get("player_dollars") - (double)lastBalanceDollars;
                double progress = (double)(System.currentTimeMillis() - lastAmountAnimation) / 1000.0 * gap;
                balanceDollars = lastBalanceDollars + (int)progress;
            } else {
                lastBalanceDollars = balanceDollars;
            }
            ModernGui.drawScaledStringCustomFont(balanceDollars + "", this.guiLeft + 153, (float)this.guiTop + 9.5f, COLOR_LIGHT_GRAY, 0.5f, "right", false, "georamaSemiBold", 27);
            int balanceOrbs = ((Double)data.get("player_orbs")).intValue();
            if (lastBalanceOrbs != -1 && System.currentTimeMillis() - lastAmountAnimation < 1000L) {
                double gap = (Double)data.get("player_orbs") - (double)lastBalanceOrbs;
                double progress = (double)(System.currentTimeMillis() - lastAmountAnimation) / 1000.0 * gap;
                balanceOrbs = lastBalanceOrbs + (int)progress;
            } else {
                lastBalanceOrbs = balanceOrbs;
            }
            ModernGui.drawScaledStringCustomFont(balanceOrbs + "", this.guiLeft + 200, (float)this.guiTop + 9.5f, COLOR_LIGHT_GRAY, 0.5f, "right", false, "georamaSemiBold", 27);
            int playtime = ((Double)data.get("atm_minutes")).intValue();
            if (lastATMPlaytime != -1 && System.currentTimeMillis() - lastAmountAnimation < 1000L) {
                double gap = (Double)data.get("atm_minutes") - (double)lastATMPlaytime;
                double progress = (double)(System.currentTimeMillis() - lastAmountAnimation) / 1000.0 * gap;
                playtime = lastATMPlaytime + (int)progress;
            } else {
                lastATMPlaytime = playtime;
            }
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"gui.atm.label.playtime").toUpperCase(), this.guiLeft + 20, this.guiTop + 33, COLOR_LIGHT_BLUE, 0.5f, "left", false, "georamaSemiBold", 27);
            int hours = playtime / 60;
            int minutes = playtime % 60;
            String hoursStr = hours < 10 ? "0" + hours : "" + hours;
            String minutesStr = minutes < 10 ? "0" + minutes : "" + minutes;
            ModernGui.drawScaledStringCustomFont(hoursStr + "h " + minutesStr + "min", this.guiLeft + 110, this.guiTop + 58, COLOR_WHITE, 1.0f, "center", false, "georamaBold", 30);
            boolean isMouseOver = mouseX >= this.guiLeft + 20 && mouseX <= this.guiLeft + 20 + 180 && mouseY >= this.guiTop + 94 && mouseY <= this.guiTop + 94 + 35;
            ClientEventHandler.STYLE.bindTexture("atm");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 20, this.guiTop + 94, 96 * GUI_SCALE, (isMouseOver ? 295 : 207) * GUI_SCALE, 180 * GUI_SCALE, 35 * GUI_SCALE, 180, 35, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"gui.atm.label.convert"), this.guiLeft + 110, this.guiTop + 102, COLOR_DARK_BLUE, 0.5f, "center", false, "georamaSemiBold", 27);
            int atmDollars = ((Double)data.get("atm_dollars")).intValue();
            if (lastATMDollars != -1 && System.currentTimeMillis() - lastAmountAnimation < 1000L) {
                double gap = (Double)data.get("atm_dollars") - (double)lastATMDollars;
                double progress = (double)(System.currentTimeMillis() - lastAmountAnimation) / 1000.0 * gap;
                atmDollars = lastATMDollars + (int)progress;
            } else {
                lastATMDollars = playtime;
            }
            ModernGui.drawScaledStringCustomFont(atmDollars + "", this.guiLeft + 112 - 4, (float)this.guiTop + 110.5f, isMouseOver ? COLOR_DARK_BLUE : COLOR_WHITE, 0.5f, "center", false, "georamaSemiBold", 40);
            ClientEventHandler.STYLE.bindTexture("atm");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 112 - 4) + semiBold40.getStringWidth(atmDollars + "") / 2.0f / 2.0f + 2.0f, (float)this.guiTop + 111.5f, 325 * GUI_SCALE, (isMouseOver ? 194 : 233) * GUI_SCALE, 10 * GUI_SCALE, 10 * GUI_SCALE, 10, 10, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
            ClientEventHandler.STYLE.bindTexture("atm");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 190, this.guiTop + 120, 313 * GUI_SCALE, 246 * GUI_SCALE, 6 * GUI_SCALE, 6 * GUI_SCALE, 6, 6, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
            ModernGui.drawScaledStringCustomFont(String.format("%.0f", (Double)data.get("player_dollars_per_minute")) + "$/min", this.guiLeft + 188, this.guiTop + 120, COLOR_DARK_BLUE, 0.5f, "right", false, "georamaMedium", 24);
            if (mouseX >= this.guiLeft + 190 && mouseX <= this.guiLeft + 190 + 6 && mouseY >= this.guiTop + 120 && mouseY <= this.guiTop + 120 + 6) {
                this.tooltipToDraw.addAll(Arrays.asList(I18n.func_135053_a((String)"gui.atm.tooltip.dollars_per_minute").split("#")));
            }
            if (isMouseOver) {
                this.hoveredAction = "convert_dollars";
            }
            isMouseOver = mouseX >= this.guiLeft + 21 && mouseX <= this.guiLeft + 21 + 86 && mouseY >= this.guiTop + 136 && mouseY <= this.guiTop + 136 + 35;
            ClientEventHandler.STYLE.bindTexture("atm");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 21, this.guiTop + 136, 0 * GUI_SCALE, ((Boolean)data.get("ngprime") != false ? 383 : (isMouseOver ? 251 : 207)) * GUI_SCALE, 86 * GUI_SCALE, 35 * GUI_SCALE, 86, 35, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
            ModernGui.drawSectionStringCustomFont(I18n.func_135053_a((String)"gui.atm.label.convert_orb_to_playtime").replaceAll("<orbs>", balanceOrbs + ""), this.guiLeft + 64, this.guiTop + 144, (Boolean)data.get("ngprime") != false ? COLOR_LIGHT_BLUE : COLOR_DARK_BLUE, 0.5f, "center", false, "georamaSemiBold", 30, 7, 120);
            if (isMouseOver) {
                this.hoveredAction = "convert_orbs";
            }
        }
        super.func_73863_a(mouseX, mouseY, par3);
        if (!this.tooltipToDraw.isEmpty()) {
            this.drawHoveringText(this.tooltipToDraw, mouseX, mouseY, this.field_73886_k);
        }
        GL11.glEnable((int)2896);
        RenderHelper.func_74519_b();
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

