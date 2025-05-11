/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.resources.I18n
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.HashMap;
import net.halalaboos.cfont.CFontRenderer;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class AssaultResultGUI
extends GuiScreen {
    public static int GUI_SCALE = 5;
    public static CFontRenderer dg25 = ModernGui.getCustomFont("minecraftDungeons", 25);
    protected int xSize = 244;
    private int guiLeft;
    private int guiTop;
    public HashMap<String, String> infos = new HashMap();
    protected int ySize = 205;
    private String hoveredAction = "";

    public AssaultResultGUI(HashMap<String, String> infos) {
        this.infos = infos;
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        this.guiLeft = (this.field_73880_f - this.xSize) / 2;
        this.guiTop = (this.field_73881_g - this.ySize) / 2;
    }

    public void func_73863_a(int mouseX, int mouseY, float par3) {
        this.hoveredAction = "";
        if (this.infos != null) {
            boolean isBtnHovered;
            boolean isHoveringClose;
            ClientEventHandler.STYLE.bindTexture("assault_resume");
            ModernGui.drawScaledCustomSizeModalRect(0.0f, 0.0f, 0 * GUI_SCALE, (this.infos.get("result").equals("victory") ? 221 : 597) * GUI_SCALE, 640 * GUI_SCALE, 360 * GUI_SCALE, this.field_73880_f, this.field_73881_g, 1024 * GUI_SCALE, 1024 * GUI_SCALE, true);
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft, this.guiTop, (this.infos.get("result").equals("victory") ? 0 : 260) * GUI_SCALE, 0 * GUI_SCALE, 244 * GUI_SCALE, 205 * GUI_SCALE, this.xSize, this.ySize, 1024 * GUI_SCALE, 1024 * GUI_SCALE, true);
            ModernGui.drawScaledCustomSizeModalRect(this.field_73880_f - 20, 6.0f, 519 * GUI_SCALE, 0 * GUI_SCALE, 14 * GUI_SCALE, 14 * GUI_SCALE, 14, 14, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
            boolean bl = isHoveringClose = mouseX >= this.field_73880_f - 20 && mouseX <= this.field_73880_f - 20 + 14 && mouseY >= 6 && mouseY <= 20;
            if (isHoveringClose) {
                ModernGui.drawScaledCustomSizeModalRect(this.field_73880_f - 20, 6.0f, (this.infos.get("result").equals("victory") ? 537 : 555) * GUI_SCALE, 0 * GUI_SCALE, 14 * GUI_SCALE, 14 * GUI_SCALE, 14, 14, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                this.hoveredAction = "close";
            }
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("assault.title." + this.infos.get("result"))), this.guiLeft + this.xSize / 2, this.guiTop + 0, this.infos.get("result").equals("victory") ? 16171012 : 16711772, 2.5f, "center", true, "minecraftDungeons", 20);
            ModernGui.drawScaledStringCustomFont("Score :", (float)(this.guiLeft + this.xSize / 2 - 10) - dg25.getStringWidth(this.infos.get("score_total")) / 2.0f - 3.0f, this.guiTop + 45, 0xBDBDBD, 0.5f, "right", true, "minecraftDungeons", 25);
            ModernGui.drawScaledStringCustomFont(this.infos.get("score_total"), this.guiLeft + this.xSize / 2 - 10, this.guiTop + 45, this.infos.get("result").equals("victory") ? 16171012 : 16711772, 0.5f, "right", true, "minecraftDungeons", 25);
            ModernGui.drawScaledStringCustomFont(!this.infos.get("result").equals("defeat") ? "+ " + this.infos.get("mmr") + " MMR" : "- " + this.infos.get("mmr") + " MMR", this.guiLeft + this.xSize / 2 + 10, this.guiTop + 45, this.infos.get("result").equals("victory") ? 16171012 : 16711772, 0.5f, "left", false, "minecraftDungeons", 25);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"assault.title.score"), this.guiLeft + 65, this.guiTop + 80, 0xBDBDBD, 0.5f, "center", false, "georamaSemiBold", 28);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"assault.label.score_mode"), this.guiLeft + 20, this.guiTop + 104, 0xBDBDBD, 0.5f, "left", false, "georamaSemiBold", 25);
            ModernGui.drawScaledStringCustomFont("\u00a7a+\u00a7f" + this.infos.get("score_mode"), this.guiLeft + 110, this.guiTop + 104, 0xBDBDBD, 0.5f, "right", false, "georamaSemiBold", 25);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"assault.label.score_missiles"), this.guiLeft + 20, this.guiTop + 115, 0xBDBDBD, 0.5f, "left", false, "georamaSemiBold", 25);
            ModernGui.drawScaledStringCustomFont("\u00a7a+\u00a7f" + this.infos.get("score_missile"), this.guiLeft + 110, this.guiTop + 115, 0xBDBDBD, 0.5f, "right", false, "georamaSemiBold", 25);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"assault.label.score_kills"), this.guiLeft + 20, this.guiTop + 126, 0xBDBDBD, 0.5f, "left", false, "georamaSemiBold", 25);
            ModernGui.drawScaledStringCustomFont("\u00a7a+\u00a7f" + this.infos.get("score_kill"), this.guiLeft + 110, this.guiTop + 126, 0xBDBDBD, 0.5f, "right", false, "georamaSemiBold", 25);
            ModernGui.drawScaledStringCustomFont("TOTAL", this.guiLeft + 20, this.guiTop + 148, 0xBDBDBD, 0.5f, "left", false, "georamaSemiBold", 25);
            ModernGui.drawScaledStringCustomFont(this.infos.get("score_total"), this.guiLeft + 110, this.guiTop + 148, this.infos.get("result").equals("victory") ? 16171012 : 16711772, 0.5f, "right", false, "georamaSemiBold", 25);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"assault.title.assault"), this.guiLeft + this.xSize - 65, this.guiTop + 80, 0xBDBDBD, 0.5f, "center", false, "georamaSemiBold", 28);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"assault.label.duration"), this.guiLeft + 134, this.guiTop + 104, 0xBDBDBD, 0.5f, "left", false, "georamaSemiBold", 25);
            ModernGui.drawScaledStringCustomFont(this.formatDurationMilliSeconds(Long.parseLong(this.infos.get("duration"))), this.guiLeft + 224, this.guiTop + 104, 0xBDBDBD, 0.5f, "right", false, "georamaSemiBold", 25);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"assault.label.kills"), this.guiLeft + 134, this.guiTop + 115, 0xBDBDBD, 0.5f, "left", false, "georamaSemiBold", 25);
            ModernGui.drawScaledStringCustomFont(this.infos.get("kills"), this.guiLeft + 224, this.guiTop + 115, 0xBDBDBD, 0.5f, "right", false, "georamaSemiBold", 25);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"assault.label.missile_blocks_destroyed"), this.guiLeft + 134, this.guiTop + 126, 0xBDBDBD, 0.5f, "left", false, "georamaSemiBold", 25);
            ModernGui.drawScaledStringCustomFont(this.infos.get("missile_blocks_destroyed"), this.guiLeft + 224, this.guiTop + 126, 0xBDBDBD, 0.5f, "right", false, "georamaSemiBold", 25);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"assault.label.missile_blocks_destroyed_own"), this.guiLeft + 134, this.guiTop + 137, 0xBDBDBD, 0.5f, "left", false, "georamaSemiBold", 25);
            ModernGui.drawScaledStringCustomFont(this.infos.get("missile_blocks_destroyed_own"), this.guiLeft + 224, this.guiTop + 137, 0xBDBDBD, 0.5f, "right", false, "georamaSemiBold", 25);
            boolean bl2 = isBtnHovered = mouseX >= this.guiLeft + 0 && mouseX <= this.guiLeft + 0 + this.xSize && mouseY >= this.guiTop + 183 && mouseY <= this.guiTop + 183 + 23;
            if (isBtnHovered) {
                ClientEventHandler.STYLE.bindTexture("assault_resume");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft, this.guiTop + 183, 519 * GUI_SCALE, (this.infos.get("result").equals("victory") ? 18 : 44) * GUI_SCALE, this.xSize * GUI_SCALE, 22 * GUI_SCALE, this.xSize, 22, 1024 * GUI_SCALE, 1024 * GUI_SCALE, true);
                this.hoveredAction = "close";
            }
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"assault.label.continue"), this.guiLeft + this.xSize / 2, this.guiTop + 190, 0xFFFFFF, 0.5f, "center", false, "georamaSemiBold", 28);
        }
    }

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0 && this.hoveredAction.equalsIgnoreCase("close")) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
            Minecraft.func_71410_x().func_71373_a(null);
        }
    }

    public String formatDurationMilliSeconds(Long time) {
        int seconds = time.intValue() / 1000;
        if (seconds < 60) {
            return this.twoDigitString(seconds % 60) + "s";
        }
        if (seconds < 3600) {
            return seconds % 3600 / 60 + "m" + this.twoDigitString(seconds % 60) + "s";
        }
        return seconds / 3600 + "h" + this.twoDigitString(seconds % 3600 / 60) + "m" + this.twoDigitString(seconds %= 60) + "s";
    }

    public String twoDigitString(int number) {
        if (number == 0) {
            return "00";
        }
        if (number / 10 == 0) {
            return "0" + number;
        }
        return String.valueOf(number);
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
}

