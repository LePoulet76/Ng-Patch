/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.internal.LinkedTreeMap
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

import com.google.gson.internal.LinkedTreeMap;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.halalaboos.cfont.CFontRenderer;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.BossEdoraGUIDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class BossEdoraGui
extends GuiScreen {
    private static final double A = 0.00885;
    private static final double B = -0.00865;
    private static final double C = 135.25;
    private static final double D = 0.00515;
    private static final double E = 0.00515;
    private static final double F = 92.25;
    public static int GUI_SCALE = 3;
    public static CFontRenderer dg23 = ModernGui.getCustomFont("minecraftDungeons", 23);
    public static HashMap<String, Object> data;
    public String hoveredAction = "";
    public List<String> tooltipToDraw = new ArrayList<String>();
    public RenderItem itemRenderer = new RenderItem();
    protected int xSize = 460;
    protected int ySize = 202;
    private int guiLeft;
    private int guiTop;
    private boolean forceBoss;

    public BossEdoraGui(boolean forceBoss) {
        this.forceBoss = forceBoss;
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new BossEdoraGUIDataPacket()));
        data = new HashMap();
    }

    public static int[] minecraftCoordonatesToGuiPosition(int minecraftX, int minecraftZ) {
        int guiX = (int)(0.00885 * (double)minecraftX + -0.00865 * (double)minecraftZ + 135.25);
        int guiY = (int)(0.00515 * (double)minecraftX + 0.00515 * (double)minecraftZ + 92.25);
        return new int[]{guiX, guiY};
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        this.guiLeft = (this.field_73880_f - this.xSize) / 2;
        this.guiTop = (this.field_73881_g - this.ySize) / 2;
    }

    public void func_73863_a(int mouseX, int mouseY, float par3) {
        this.hoveredAction = "";
        this.tooltipToDraw = new ArrayList<String>();
        this.func_73873_v_();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        ClientEventHandler.STYLE.bindTexture("background_dark");
        ModernGui.drawScaledCustomSizeModalRect(0.0f, 0.0f, 0.0f, 0.0f, 1920 * GUI_SCALE, 1920 * GUI_SCALE, this.field_73880_f, this.field_73881_g, 1920 * GUI_SCALE, 1080 * GUI_SCALE, true);
        ClientEventHandler.STYLE.bindTexture("boss_edora");
        if (mouseX > this.field_73880_f - 14 - 5 && mouseX < this.field_73880_f - 14 - 5 + 14 && mouseY > 5 && mouseY < 19) {
            ModernGui.drawScaledCustomSizeModalRect(this.field_73880_f - 14 - 5, 5.0f, 498 * GUI_SCALE, 33 * GUI_SCALE, 14 * GUI_SCALE, 14 * GUI_SCALE, 14, 14, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
            this.hoveredAction = "close";
        } else {
            ModernGui.drawScaledCustomSizeModalRect(this.field_73880_f - 14 - 5, 5.0f, 483 * GUI_SCALE, 33 * GUI_SCALE, 14 * GUI_SCALE, 14 * GUI_SCALE, 14, 14, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
        }
        boolean isBossMode = data.containsKey("boss");
        ClientEventHandler.STYLE.bindTexture("boss_edora");
        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft, this.guiTop + 24, 0 * GUI_SCALE, (isBossMode ? 180 : 0) * GUI_SCALE, 460 * GUI_SCALE, 177 * GUI_SCALE, 460, 177, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"gui.boss.edora.orientation.north"), this.guiLeft + 190, this.guiTop + 55, 0x848486, 0.5f, "center", true, "minecraftDungeons", 23);
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"gui.boss.edora.orientation.south"), this.guiLeft + 78, this.guiTop + 132, 0x848486, 0.5f, "center", true, "minecraftDungeons", 23);
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"gui.boss.edora.orientation.west"), this.guiLeft + 80, this.guiTop + 52, 0x848486, 0.5f, "center", true, "minecraftDungeons", 23);
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"gui.boss.edora.orientation.east"), this.guiLeft + 195, this.guiTop + 132, 0x848486, 0.5f, "center", true, "minecraftDungeons", 23);
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"gui.boss.edora.title"), this.guiLeft + 135, this.guiTop + 3, 0xFFFFFF, 0.75f, "center", true, "minecraftDungeons", 23);
        ClientEventHandler.STYLE.bindTexture("boss_edora");
        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 135) - dg23.getStringWidth(I18n.func_135053_a((String)"gui.boss.edora.title")) * 0.75f / 2.0f - 10.0f, this.guiTop + 5, (isBossMode ? 503 : 508) * GUI_SCALE, 10 * GUI_SCALE, 4 * GUI_SCALE, 10 * GUI_SCALE, 4, 10, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 135) + dg23.getStringWidth(I18n.func_135053_a((String)"gui.boss.edora.title")) * 0.75f / 2.0f + 4.0f, this.guiTop + 5, (isBossMode ? 503 : 508) * GUI_SCALE, 0 * GUI_SCALE, 4 * GUI_SCALE, 10 * GUI_SCALE, 4, 10, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"gui.boss.edora.description"), this.guiLeft + 285, this.guiTop + 34, 0xFFFFFF, 0.5f, "left", false, "georamaSemiBold", 28);
        ModernGui.drawSectionStringCustomFont(I18n.func_135053_a((String)("gui.boss.edora.description." + (isBossMode ? "boss" : "autels"))), this.guiLeft + 285, this.guiTop + 48, 0x848486, 0.5f, "left", false, "georamaMedium", 24, 7, 320);
        int countNotCaptured = 0;
        int countCaptured = 0;
        int countInProgress = 0;
        if (!isBossMode && data.containsKey("autels")) {
            for (Map.Entry pair : ((LinkedTreeMap)data.get("autels")).entrySet()) {
                LinkedTreeMap autel = (LinkedTreeMap)pair.getValue();
                int[] guiCoords = BossEdoraGui.minecraftCoordonatesToGuiPosition(((Double)((ArrayList)autel.get((Object)"center")).get(0)).intValue(), ((Double)((ArrayList)autel.get((Object)"center")).get(1)).intValue());
                int capturePercent = ((Double)autel.get((Object)"capturePercent")).intValue();
                ClientEventHandler.STYLE.bindTexture("boss_edora");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + guiCoords[0] - 4, this.guiTop + guiCoords[1] - 4, (capturePercent == 0 ? 486 : (capturePercent == 100 ? 504 : 495)) * GUI_SCALE, 22 * GUI_SCALE, 8 * GUI_SCALE, 9 * GUI_SCALE, 8, 9, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                if (capturePercent < 100) {
                    ModernGui.drawScaledStringCustomFont((String)pair.getKey(), (float)(this.guiLeft + guiCoords[0]) + 0.5f, this.guiTop + guiCoords[1] - 3, 0x272627, 0.5f, "center", false, "minecraftDungeons", 16);
                }
                if (capturePercent == 100) {
                    ++countCaptured;
                } else if (capturePercent == 0) {
                    ++countNotCaptured;
                } else {
                    ++countInProgress;
                }
                if (mouseX < this.guiLeft + guiCoords[0] - 4 || mouseX > this.guiLeft + guiCoords[0] + 4 || mouseY < this.guiTop + guiCoords[1] - 4 || mouseY > this.guiTop + guiCoords[1] + 4) continue;
                if (!autel.containsKey((Object)"nextAvailableTime")) {
                    this.tooltipToDraw = Arrays.asList("\u00a7fAutel " + pair.getKey(), "\u00a77Capture: \u00a7f" + capturePercent + "%", "", "\u00a77X:\u00a7b" + ((Double)((ArrayList)autel.get((Object)"center")).get(0)).intValue() + " \u00a77Z:\u00a7b" + ((Double)((ArrayList)autel.get((Object)"center")).get(1)).intValue());
                    continue;
                }
                this.tooltipToDraw = Arrays.asList("\u00a7fAutel " + pair.getKey(), "\u00a77Disponible dans", "\u00a7f" + ModernGui.chronoTimeToStr(((Double)autel.get((Object)"nextAvailableTime")).longValue() - System.currentTimeMillis(), false), "", "\u00a77X:\u00a7b" + ((Double)((ArrayList)autel.get((Object)"center")).get(0)).intValue() + " \u00a77Z:\u00a7b" + ((Double)((ArrayList)autel.get((Object)"center")).get(1)).intValue());
            }
        } else if (isBossMode && data.containsKey("boss")) {
            LinkedTreeMap bossData = (LinkedTreeMap)data.get("boss");
            int[] guiCoords = BossEdoraGui.minecraftCoordonatesToGuiPosition(((Double)((ArrayList)bossData.get((Object)"activePosition")).get(0)).intValue(), ((Double)((ArrayList)bossData.get((Object)"activePosition")).get(1)).intValue());
            ClientEventHandler.STYLE.bindTexture("boss_edora");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + guiCoords[0] - 4, this.guiTop + guiCoords[1] - 4, 477 * GUI_SCALE, 22 * GUI_SCALE, 8 * GUI_SCALE, 9 * GUI_SCALE, 8, 9, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
            if (mouseX >= this.guiLeft + guiCoords[0] - 4 && mouseX <= this.guiLeft + guiCoords[0] + 4 && mouseY >= this.guiTop + guiCoords[1] - 4 && mouseY <= this.guiTop + guiCoords[1] + 4) {
                this.tooltipToDraw = Arrays.asList("\u00a77X:\u00a7c" + ((Double)((ArrayList)bossData.get((Object)"activePosition")).get(0)).intValue() + " \u00a77Z:\u00a7c" + ((Double)((ArrayList)bossData.get((Object)"activePosition")).get(1)).intValue());
            }
        }
        if (!isBossMode) {
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"gui.boss.edora.legend"), this.guiLeft + 285, this.guiTop + 125, 0xFFFFFF, 0.5f, "left", false, "georamaSemiBold", 28);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"gui.boss.edora.legend.not_captured"), this.guiLeft + 308, this.guiTop + 147, 0xFFFFFF, 0.5f, "left", false, "georamaSemiBold", 25);
            ModernGui.drawScaledStringCustomFont(countNotCaptured + "", this.guiLeft + 445, this.guiTop + 147, 0x848486, 0.5f, "right", false, "georamaSemiBold", 27);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"gui.boss.edora.legend.capture_inprogress"), this.guiLeft + 307, this.guiTop + 161, 0xFFFFFF, 0.5f, "left", false, "georamaSemiBold", 25);
            ModernGui.drawScaledStringCustomFont(countInProgress + "", this.guiLeft + 445, this.guiTop + 161, 0x848486, 0.5f, "right", false, "georamaSemiBold", 27);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"gui.boss.edora.legend.captured"), this.guiLeft + 307, this.guiTop + 175, 0xFFFFFF, 0.5f, "left", false, "georamaSemiBold", 25);
            ModernGui.drawScaledStringCustomFont(countCaptured + "", this.guiLeft + 445, this.guiTop + 175, 0x848486, 0.5f, "right", false, "georamaSemiBold", 27);
        } else {
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"gui.boss.edora.legend"), this.guiLeft + 286, this.guiTop + 149, 0xFFFFFF, 0.5f, "left", false, "georamaSemiBold", 28);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"gui.boss.edora.legend.boss"), this.guiLeft + 308, this.guiTop + 172, 0xFFFFFF, 0.5f, "left", false, "georamaSemiBold", 25);
        }
        if (!isBossMode) {
            float percent = (float)countCaptured / (float)(countCaptured + countInProgress + countNotCaptured);
            ClientEventHandler.STYLE.bindTexture("boss_edora");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 10, this.guiTop + 178, 11 * GUI_SCALE, 368 * GUI_SCALE, (int)(234.0f * percent) * GUI_SCALE, 8 * GUI_SCALE, (int)(234.0f * percent), 8, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
        }
        super.func_73863_a(mouseX, mouseY, par3);
        if (!this.tooltipToDraw.isEmpty()) {
            this.drawHoveringText(this.tooltipToDraw, mouseX, mouseY, this.field_73886_k);
        }
    }

    public boolean func_73868_f() {
        return false;
    }

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0 && this.hoveredAction.equals("close")) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
            Minecraft.func_71410_x().func_71373_a(null);
        }
        super.func_73864_a(mouseX, mouseY, mouseButton);
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

