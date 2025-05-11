/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
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

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import net.halalaboos.cfont.CFontRenderer;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.RollbackAssaultStartPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class RollbackAssaultGUI
extends GuiScreen {
    public static int GUI_SCALE = 3;
    public static HashMap<String, Object> data = new HashMap();
    public static CFontRenderer semiBold40 = ModernGui.getCustomFont("georamaSemiBold", 40);
    public static CFontRenderer dungeons18 = ModernGui.getCustomFont("minecraftDungeons", 18);
    public static CFontRenderer dungeons24 = ModernGui.getCustomFont("minecraftDungeons", 24);
    public static boolean loaded = false;
    public String hoveredAction = "";
    public int xSize = 357;
    public int ySize = 180;
    public RenderItem itemRenderer = new RenderItem();
    public int guiLeft;
    public int guiTop;
    public List<String> tooltipToDraw = new ArrayList<String>();
    private double repairPercent = 0.01;
    private double dollarsPercent = 0.5;

    public RollbackAssaultGUI() {
        loaded = false;
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        this.guiLeft = (this.field_73880_f - this.xSize) / 2;
        this.guiTop = (this.field_73881_g - this.ySize) / 2;
    }

    public void func_73863_a(int mouseX, int mouseY, float par3) {
        this.func_73873_v_();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.tooltipToDraw.clear();
        this.hoveredAction = "";
        ClientEventHandler.STYLE.bindTexture("faction_rollback");
        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft, this.guiTop, 0 * GUI_SCALE, 0 * GUI_SCALE, this.xSize * GUI_SCALE, this.ySize * GUI_SCALE, this.xSize, this.ySize, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
        ClientEventHandler.STYLE.bindTexture("faction_rollback");
        if (mouseX >= this.guiLeft + 336 && mouseX <= this.guiLeft + 336 + 10 && mouseY >= this.guiTop + 12 && mouseY <= this.guiTop + 12 + 10) {
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 336, this.guiTop + 12, 379 * GUI_SCALE, 19 * GUI_SCALE, 10 * GUI_SCALE, 10 * GUI_SCALE, 10, 10, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
            this.hoveredAction = "close";
        } else {
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 336, this.guiTop + 12, 379 * GUI_SCALE, 8 * GUI_SCALE, 10 * GUI_SCALE, 10 * GUI_SCALE, 10, 10, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
        }
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.rollback.title").toUpperCase(), this.guiLeft + 13, this.guiTop + 10, 0xF8F8FB, 0.75f, "left", false, "minecraftDungeons", 23);
        ModernGui.drawSectionStringCustomFont(I18n.func_135053_a((String)"faction.rollback.desc"), this.guiLeft + 13, this.guiTop + 25, 0xBABADA, 0.5f, "left", false, "georamaMedium", 24, 8, 500);
        if (loaded) {
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.rollback.informations").toUpperCase(), this.guiLeft + 22, this.guiTop + 62, 0xF8F8FB, 0.5f, "left", false, "minecraftDungeons", 24);
            Date date = new Date(Long.parseLong((String)data.get("time")));
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM HH:mm");
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.rollback.assault_date").toUpperCase() + " :", this.guiLeft + 22, this.guiTop + 75, 0x7979B7, 0.5f, "left", false, "minecraftDungeons", 18);
            ModernGui.drawScaledStringCustomFont(sdf.format(date), (float)(this.guiLeft + 22) + dungeons18.getStringWidth(I18n.func_135053_a((String)"faction.rollback.assault_date").toUpperCase() + " :") / 2.0f + 3.0f, (float)this.guiTop + 75.5f, 0xDEDEED, 0.5f, "left", false, "georamaSemiBold", 24);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.rollback.country_enemy").toUpperCase() + " :", this.guiLeft + 22, this.guiTop + 85, 0x7979B7, 0.5f, "left", false, "minecraftDungeons", 18);
            ModernGui.drawScaledStringCustomFont((String)data.get("enemyName"), (float)(this.guiLeft + 22) + dungeons18.getStringWidth(I18n.func_135053_a((String)"faction.rollback.country_enemy").toUpperCase() + " :") / 2.0f + 3.0f, (float)this.guiTop + 85.5f, 0xDEDEED, 0.5f, "left", false, "georamaSemiBold", 24);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.rollback.block_count").toUpperCase() + " :", this.guiLeft + 22, this.guiTop + 105, 0x7979B7, 0.5f, "left", false, "minecraftDungeons", 18);
            ModernGui.drawScaledStringCustomFont(String.format("%.0f", (Double)data.get("blocksCount")), (float)(this.guiLeft + 22) + dungeons18.getStringWidth(I18n.func_135053_a((String)"faction.rollback.block_count").toUpperCase() + " :") / 2.0f + 3.0f, (float)this.guiTop + 105.5f, 0xDEDEED, 0.5f, "left", false, "georamaSemiBold", 24);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.rollback.price").toUpperCase() + " :", this.guiLeft + 22, this.guiTop + 115, 0x7979B7, 0.5f, "left", false, "minecraftDungeons", 18);
            ModernGui.drawScaledStringCustomFont("10$/bloc - 0.006 power/bloc", (float)(this.guiLeft + 22) + dungeons18.getStringWidth(I18n.func_135053_a((String)"faction.rollback.price").toUpperCase() + " :") / 2.0f + 3.0f, this.guiTop + 115, 0xDEDEED, 0.5f, "left", false, "georamaSemiBold", 24);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.rollback.remaining_time").toUpperCase() + " :", this.guiLeft + 22, this.guiTop + 125, 0x7979B7, 0.5f, "left", false, "minecraftDungeons", 18);
            long endTime = Long.parseLong((String)data.get("time")) + 3600000L;
            ModernGui.drawScaledStringCustomFont(endTime > System.currentTimeMillis() ? ModernGui.getFormattedTimeDiff(endTime, System.currentTimeMillis()) : I18n.func_135053_a((String)"faction.rollback.expired"), (float)(this.guiLeft + 22) + dungeons18.getStringWidth(I18n.func_135053_a((String)"faction.rollback.remaining_time").toUpperCase() + " :") / 2.0f + 3.0f, this.guiTop + 125, 0xDEDEED, 0.5f, "left", false, "georamaSemiBold", 24);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.rollback.malus").toUpperCase() + " :", this.guiLeft + 22, this.guiTop + 145, 0x7979B7, 0.5f, "left", false, "minecraftDungeons", 18);
            double malus = data.get("countRepair") != null ? (Double)data.get("countRepair") / 1000.0 : 0.0;
            ModernGui.drawScaledStringCustomFont("+" + String.format("%.0f", malus) + "% (+1%/1000 blocs)", (float)(this.guiLeft + 22) + dungeons18.getStringWidth(I18n.func_135053_a((String)"faction.rollback.malus").toUpperCase() + " :") / 2.0f + 3.0f, (float)this.guiTop + 145.5f, 0xDEDEED, 0.5f, "left", false, "georamaSemiBold", 24);
            ModernGui.drawScaledStringCustomFont(String.format("%.0f", this.repairPercent * 100.0) + "%", this.guiLeft + 189, this.guiTop + 61, 0xF8F8FB, 0.5f, "left", false, "minecraftDungeons", 24);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.rollback.repair_percent"), (float)(this.guiLeft + 190) + dungeons24.getStringWidth(String.format("%.0f", this.repairPercent * 100.0) + "%") / 2.0f + 2.0f, this.guiTop + 64, 0x7979B7, 0.5f, "left", false, "minecraftDungeons", 16);
            if ((Double)data.get("maxRepairPercent") < 1.0) {
                ClientEventHandler.STYLE.bindTexture("faction_rollback");
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 203) + 122.0f * ((Double)data.get("maxRepairPercent")).floatValue(), this.guiTop + 81, (367.0f + 122.0f * ((Double)data.get("maxRepairPercent")).floatValue()) * (float)GUI_SCALE, 81 * GUI_SCALE, (int)(122.0f * (1.0f - ((Double)data.get("maxRepairPercent")).floatValue()) * (float)GUI_SCALE), 3 * GUI_SCALE, (int)(122.0f * (1.0f - ((Double)data.get("maxRepairPercent")).floatValue())), 3, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
            }
            double cursorPercentX = (double)(this.guiLeft + 203) + 122.0 * this.repairPercent - 3.0;
            ClientEventHandler.STYLE.bindTexture("faction_rollback");
            ModernGui.drawScaledCustomSizeModalRect((float)cursorPercentX, this.guiTop + 75, 367 * GUI_SCALE, 51 * GUI_SCALE, 6 * GUI_SCALE, 15 * GUI_SCALE, 6, 15, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
            if (mouseX >= this.guiLeft + 200 && mouseX <= this.guiLeft + 200 + 126 && mouseY >= this.guiTop + 75 && mouseY <= this.guiTop + 90) {
                this.hoveredAction = "cursorRepair";
            }
            double totalDollarsPrice = (Double)data.get("blocksCount") * this.repairPercent * 10.0 * (1.0 + malus / 100.0);
            double priceDollars = totalDollarsPrice * this.dollarsPercent;
            double pricePower = totalDollarsPrice * (1.0 - this.dollarsPercent) / 10.0 * 0.006;
            double cursorDollarsX = (double)(this.guiLeft + 203) + 122.0 * this.dollarsPercent - 3.0;
            ClientEventHandler.STYLE.bindTexture("faction_rollback");
            ModernGui.drawScaledCustomSizeModalRect((float)cursorDollarsX, this.guiTop + 117, 367 * GUI_SCALE, 51 * GUI_SCALE, 6 * GUI_SCALE, 15 * GUI_SCALE, 6, 15, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
            if (mouseX >= this.guiLeft + 200 && mouseX <= this.guiLeft + 200 + 126 && mouseY >= this.guiTop + 117 && mouseY <= this.guiTop + 117 + 15) {
                this.hoveredAction = "cursorDollars";
            }
            ModernGui.drawScaledStringCustomFont(String.format("%.0f", pricePower) + " Pow + " + String.format("%.0f", priceDollars) + "$", this.guiLeft + 189, this.guiTop + 103, 0xF8F8FB, 0.5f, "left", false, "minecraftDungeons", 24);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.rollback.repair_cost"), (float)(this.guiLeft + 190) + dungeons24.getStringWidth(String.format("%.0f", pricePower) + " Pow + " + String.format("%.0f", priceDollars) + "$") / 2.0f + 2.0f, this.guiTop + 106, 0x7979B7, 0.5f, "left", false, "minecraftDungeons", 16);
            if (mouseX >= this.guiLeft + 184 && mouseX <= this.guiLeft + 184 + 160 && mouseY >= this.guiTop + 152 && mouseY <= this.guiTop + 152 + 17) {
                this.hoveredAction = "rollback";
                ClientEventHandler.STYLE.bindTexture("faction_rollback");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 184, this.guiTop + 152, 184 * GUI_SCALE, 211 * GUI_SCALE, 160 * GUI_SCALE, 17 * GUI_SCALE, 160, 17, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
            }
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.rollback.repair_button").toUpperCase(), this.guiLeft + 184 + 80, this.guiTop + 156, 0x252545, 0.5f, "center", false, "minecraftDungeons", 21);
        }
        super.func_73863_a(mouseX, mouseY, par3);
        if (!this.tooltipToDraw.isEmpty()) {
            this.drawHoveringText(this.tooltipToDraw, mouseX, mouseY, this.field_73886_k);
        }
        GL11.glEnable((int)2896);
        RenderHelper.func_74519_b();
    }

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            if (this.hoveredAction.equals("close")) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a(null);
            } else if (this.hoveredAction.equals("rollback")) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                data.put("repairPercent", this.repairPercent);
                data.put("dollarsPercent", this.dollarsPercent);
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new RollbackAssaultStartPacket(data)));
                Minecraft.func_71410_x().func_71373_a(null);
            }
        }
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    protected void func_85041_a(int mouseX, int par2, int par3, long par4) {
        if (this.hoveredAction.equals("cursorRepair")) {
            int min = this.guiLeft + 203;
            int max = this.guiLeft + 203 + 122;
            float percent = (float)(mouseX - min) / (float)(max - min);
            this.repairPercent = Math.min((Double)data.get("maxRepairPercent"), (double)Math.max(0.0f, percent));
        } else if (this.hoveredAction.equals("cursorDollars")) {
            int min = this.guiLeft + 203;
            int max = this.guiLeft + 203 + 122;
            float percent = (float)(mouseX - min) / (float)(max - min);
            this.dollarsPercent = Math.min(100.0f, Math.max(0.0f, percent));
        }
        super.func_85041_a(mouseX, par2, par3, par4);
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

