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
package net.ilexiconn.nationsgui.forge.client.gui.warzone;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.gui.warzone.WarzonesLeaderboardGui_OLD;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.WarzoneTPPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.WarzonesDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class WarzonesGui_OLD
extends GuiScreen {
    protected int xSize = 289;
    protected int ySize = 172;
    private int guiLeft;
    private int guiTop;
    private RenderItem itemRenderer = new RenderItem();
    public static HashMap<String, String> bateauInfos = new HashMap();
    public static HashMap<String, String> petrolInfos = new HashMap();
    public static boolean loaded = false;
    public static String rank = "";
    public static int tpLeft = 0;

    public WarzonesGui_OLD() {
        loaded = false;
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new WarzonesDataPacket()));
        this.guiLeft = (this.field_73880_f - this.xSize) / 2;
        this.guiTop = (this.field_73881_g - this.ySize) / 2;
    }

    public void func_73863_a(int mouseX, int mouseY, float par3) {
        this.func_73873_v_();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        ClientEventHandler.STYLE.bindTexture("warzones");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512.0f, 512.0f, false);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(this.guiLeft + 14), (float)(this.guiTop + 148), (float)0.0f);
        GL11.glRotatef((float)-90.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        GL11.glTranslatef((float)(-(this.guiLeft + 14)), (float)(-(this.guiTop + 148)), (float)0.0f);
        this.drawScaledString(I18n.func_135053_a((String)"warzones.title"), this.guiLeft + 14, this.guiTop + 148, 0xFFFFFF, 1.5f, false, false);
        GL11.glPopMatrix();
        List<Object> tooltipToDraw = new ArrayList();
        if (mouseX >= this.guiLeft + 278 && mouseX <= this.guiLeft + 278 + 9 && mouseY >= this.guiTop - 8 && mouseY <= this.guiTop - 8 + 10) {
            ClientEventHandler.STYLE.bindTexture("warzones");
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 278, this.guiTop - 8, 110, 186, 9, 10, 512.0f, 512.0f, false);
        } else {
            ClientEventHandler.STYLE.bindTexture("warzones");
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 278, this.guiTop - 8, 110, 176, 9, 10, 512.0f, 512.0f, false);
        }
        ClientEventHandler.STYLE.bindTexture("warzones");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft - 25, this.guiTop + 30, 23, 213, 29, 30, 512.0f, 512.0f, false);
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft - 25 + 9, this.guiTop + 30 + 9, 2, 250, 13, 12, 512.0f, 512.0f, false);
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft - 23, this.guiTop + 61, 0, 213, 23, 30, 512.0f, 512.0f, false);
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft - 23 + 8, this.guiTop + 61 + 10, 24, 252, 9, 10, 512.0f, 512.0f, false);
        if (loaded && bateauInfos != null && petrolInfos != null) {
            ClientEventHandler.STYLE.bindTexture("warzones");
            this.drawScaledString("Warzone bateau", this.guiLeft + 57, this.guiTop + 80, 0xFFFFFF, 1.0f, false, true);
            String bonus = "Bonus " + bateauInfos.get("type");
            this.drawScaledString("Bonus " + bateauInfos.get("type"), this.guiLeft + 57, this.guiTop + 90, 0xB4B4B4, 1.0f, false, false);
            ClientEventHandler.STYLE.bindTexture("warzones");
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 57 + this.field_73886_k.func_78256_a(bonus) + 5, this.guiTop + 90, 96, 175, 10, 11, 512.0f, 512.0f, false);
            if (mouseX >= this.guiLeft + 57 + this.field_73886_k.func_78256_a(bonus) + 5 && mouseX <= this.guiLeft + 57 + this.field_73886_k.func_78256_a(bonus) + 5 + 10 && mouseY >= this.guiTop + 90 && mouseY <= this.guiTop + 90 + 11) {
                tooltipToDraw = Arrays.asList(I18n.func_135053_a((String)("warzones.infos." + bateauInfos.get("type") + "_1")), I18n.func_135053_a((String)("warzones.infos." + bateauInfos.get("type") + "_2")), I18n.func_135053_a((String)("warzones.infos." + bateauInfos.get("type") + "_3")));
            }
            this.drawScaledString(I18n.func_135053_a((String)"warzones.claim_by"), this.guiLeft + 57, this.guiTop + 105, 0xB4B4B4, 1.0f, false, false);
            this.drawScaledString(bateauInfos.get("factionName").replace('&', '\u00a7'), this.guiLeft + 57, this.guiTop + 115, 0xFFFFFF, 1.0f, false, false);
            Double progress = Double.parseDouble(bateauInfos.get("percent"));
            ClientEventHandler.STYLE.bindTexture("warzones");
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 56, this.guiTop + 127, 0, 207, (int)(94.0 * (progress / 100.0)), 4, 512.0f, 512.0f, false);
            if (mouseX >= this.guiLeft + 56 && mouseX <= this.guiLeft + 150 && mouseY >= this.guiTop + 127 && mouseY <= this.guiTop + 131) {
                tooltipToDraw = Arrays.asList("\u00a77" + bateauInfos.get("percent") + "%");
            }
            if (bateauInfos.get("tpLeft").equals("0")) {
                ClientEventHandler.STYLE.bindTexture("warzones");
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 56, this.guiTop + 133, 0, 190, 94, 15, 512.0f, 512.0f, false);
            }
            if (mouseX >= this.guiLeft + 56 && mouseX <= this.guiLeft + 150 && mouseY >= this.guiTop + 133 && mouseY <= this.guiTop + 148) {
                if (!bateauInfos.get("tpLeft").equals("0")) {
                    if (Integer.parseInt(bateauInfos.get("tpLeft")) <= 7) {
                        tooltipToDraw = Arrays.asList("\u00a7a" + bateauInfos.get("tpLeft") + " " + I18n.func_135053_a((String)"warzones.teleport_remaining"));
                    }
                } else {
                    tooltipToDraw = Arrays.asList("\u00a7c" + I18n.func_135053_a((String)"warzones.teleport_unavailable"));
                }
                ClientEventHandler.STYLE.bindTexture("warzones");
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 56, this.guiTop + 133, 0, 190, 94, 15, 512.0f, 512.0f, false);
            }
            this.drawScaledString(I18n.func_135053_a((String)"warzones.teleport"), this.guiLeft + 103, this.guiTop + 137, 0xFFFFFF, 1.0f, true, false);
            this.drawScaledString("Warzone petrol", this.guiLeft + 175, this.guiTop + 80, 0xFFFFFF, 1.0f, false, true);
            bonus = "Bonus " + petrolInfos.get("type");
            this.drawScaledString(bonus, this.guiLeft + 175, this.guiTop + 90, 0xB4B4B4, 1.0f, false, false);
            ClientEventHandler.STYLE.bindTexture("warzones");
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 175 + this.field_73886_k.func_78256_a(bonus) + 5, this.guiTop + 90, 96, 175, 10, 11, 512.0f, 512.0f, false);
            if (mouseX >= this.guiLeft + 175 + this.field_73886_k.func_78256_a(bonus) + 5 && mouseX <= this.guiLeft + 175 + this.field_73886_k.func_78256_a(bonus) + 5 + 10 && mouseY >= this.guiTop + 90 && mouseY <= this.guiTop + 90 + 11) {
                tooltipToDraw = Arrays.asList(I18n.func_135053_a((String)("warzones.infos." + petrolInfos.get("type") + "_1")), I18n.func_135053_a((String)("warzones.infos." + petrolInfos.get("type") + "_2")), I18n.func_135053_a((String)("warzones.infos." + petrolInfos.get("type") + "_3")));
            }
            this.drawScaledString(I18n.func_135053_a((String)"warzones.claim_by"), this.guiLeft + 175, this.guiTop + 105, 0xB4B4B4, 1.0f, false, false);
            this.drawScaledString(petrolInfos.get("factionName").replace('&', '\u00a7'), this.guiLeft + 175, this.guiTop + 115, 0xFFFFFF, 1.0f, false, false);
            progress = Double.parseDouble(petrolInfos.get("percent"));
            ClientEventHandler.STYLE.bindTexture("warzones");
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 174, this.guiTop + 127, 0, 207, (int)(94.0 * (progress / 100.0)), 4, 512.0f, 512.0f, false);
            if (mouseX >= this.guiLeft + 174 && mouseX <= this.guiLeft + 268 && mouseY >= this.guiTop + 127 && mouseY <= this.guiTop + 131) {
                tooltipToDraw = Arrays.asList("\u00a77" + petrolInfos.get("percent") + "%");
            }
            if (petrolInfos.get("tpLeft").equals("0")) {
                ClientEventHandler.STYLE.bindTexture("warzones");
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 174, this.guiTop + 133, 0, 190, 94, 15, 512.0f, 512.0f, false);
            }
            if (mouseX >= this.guiLeft + 174 && mouseX <= this.guiLeft + 268 && mouseY >= this.guiTop + 133 && mouseY <= this.guiTop + 148) {
                if (!petrolInfos.get("tpLeft").equals("0")) {
                    if (Integer.parseInt(petrolInfos.get("tpLeft")) <= 7) {
                        tooltipToDraw = Arrays.asList("\u00a7a" + petrolInfos.get("tpLeft") + " " + I18n.func_135053_a((String)"warzones.teleport_remaining"));
                    }
                } else {
                    tooltipToDraw = Arrays.asList("\u00a7c" + I18n.func_135053_a((String)"warzones.teleport_unavailable"));
                }
                ClientEventHandler.STYLE.bindTexture("warzones");
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 174, this.guiTop + 133, 0, 190, 94, 15, 512.0f, 512.0f, false);
            }
            this.drawScaledString(I18n.func_135053_a((String)"warzones.teleport"), this.guiLeft + 221, this.guiTop + 137, 0xFFFFFF, 1.0f, true, false);
        }
        if (!tooltipToDraw.isEmpty()) {
            this.drawHoveringText(tooltipToDraw, mouseX, mouseY, this.field_73886_k);
        }
        super.func_73863_a(mouseX, mouseY, par3);
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

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            if (mouseX > this.guiLeft + 278 && mouseX < this.guiLeft + 278 + 9 && mouseY > this.guiTop - 8 && mouseY < this.guiTop - 8 + 10) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a(null);
            }
            if (mouseX > this.guiLeft - 23 && mouseX < this.guiLeft - 23 + 23 && mouseY > this.guiTop + 61 && mouseY < this.guiTop + 61 + 30) {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new WarzonesLeaderboardGui_OLD());
            }
            if (bateauInfos != null && !bateauInfos.get("tpLeft").equals("0") && mouseX >= this.guiLeft + 56 && mouseX <= this.guiLeft + 150 && mouseY >= this.guiTop + 133 && mouseY <= this.guiTop + 148) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new WarzoneTPPacket("bateau")));
                Minecraft.func_71410_x().func_71373_a(null);
            } else if (petrolInfos != null && !petrolInfos.get("tpLeft").equals("0") && mouseX >= this.guiLeft + 174 && mouseX <= this.guiLeft + 268 && mouseY >= this.guiTop + 133 && mouseY <= this.guiTop + 148) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new WarzoneTPPacket("petrol")));
                Minecraft.func_71410_x().func_71373_a(null);
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
}

