/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.network.packet.Packet
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.enterprise;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.Arrays;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.MinimapRenderer;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.MinimapRequestPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;
import org.lwjgl.opengl.GL11;

public abstract class TabbedEnterpriseGUI
extends GuiScreen {
    protected int xSize = 400;
    protected int ySize = 250;
    protected int guiLeft;
    protected int guiTop;
    public static boolean mapLoaded;
    private RenderItem itemRenderer = new RenderItem();
    public MinimapRenderer minimapRenderer = new MinimapRenderer(6, 6);

    public TabbedEnterpriseGUI() {
        mapLoaded = false;
    }

    public void func_73866_w_() {
        this.guiLeft = (this.field_73880_f - this.xSize) / 2;
        this.guiTop = (this.field_73881_g - this.ySize) / 2;
    }

    public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
        this.func_73873_v_();
        this.drawScreen(mouseX, mouseY);
        if (EnterpriseGui.enterpriseInfos != null) {
            int i;
            if (!mapLoaded && EnterpriseGui.enterpriseInfos.size() > 0 && EnterpriseGui.enterpriseInfos.get("position") != null && !((String)EnterpriseGui.enterpriseInfos.get("position")).isEmpty()) {
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new MinimapRequestPacket(Integer.parseInt(((String)EnterpriseGui.enterpriseInfos.get("position")).split("##")[0]), Integer.parseInt(((String)EnterpriseGui.enterpriseInfos.get("position")).split("##")[1]), 6, 6)));
                mapLoaded = true;
            }
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            ClientEventHandler.STYLE.bindTexture("enterprise_main");
            if (mouseX > this.guiLeft + 385 && mouseX < this.guiLeft + 385 + 9 && mouseY > this.guiTop - 6 && mouseY < this.guiTop - 6 + 10) {
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 385, this.guiTop - 6, 138, 261, 9, 10, 512.0f, 512.0f, false);
            } else {
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 385, this.guiTop - 6, 138, 251, 9, 10, 512.0f, 512.0f, false);
            }
            if (((String)EnterpriseGui.enterpriseInfos.get("name")).length() <= 9) {
                this.drawScaledString((String)EnterpriseGui.enterpriseInfos.get("name"), this.guiLeft + 60, this.guiTop + 25, 0xFFFFFF, 1.8f, true, true);
            } else {
                this.drawScaledString((String)EnterpriseGui.enterpriseInfos.get("name"), this.guiLeft + 60, this.guiTop + 25, 0xFFFFFF, 1.0f, true, true);
            }
            this.drawScaledString(I18n.func_135053_a((String)"faction.common.age_1") + " " + EnterpriseGui.enterpriseInfos.get("age") + " " + I18n.func_135053_a((String)"faction.common.age_2"), this.guiLeft + 60, this.guiTop + 43, 0xB4B4B4, 0.65f, true, false);
            ClientEventHandler.STYLE.bindTexture("enterprise_main");
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 12, this.guiTop + 61, EnterpriseGui.getTypeOffsetX((String)EnterpriseGui.enterpriseInfos.get("type")), 442, 16, 16, 512.0f, 512.0f, false);
            this.drawScaledString(I18n.func_135053_a((String)("enterprise.type." + ((String)EnterpriseGui.enterpriseInfos.get("type")).toLowerCase())), this.guiLeft + 32, this.guiTop + 66, 0xFFFFFF, 1.0f, false, false);
            ClientEventHandler.STYLE.bindTexture("enterprise_main");
            for (i = 0; i < EnterpriseGui.TABS.size(); ++i) {
                GuiScreenTab type = EnterpriseGui.TABS.get(i);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                int x = EnterpriseGui.getTabIndex(EnterpriseGui.TABS.get(i));
                if (((Object)((Object)this)).getClass() == type.getClassReferent()) {
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft - 23, this.guiTop + 20 + i * 31, 23, 250, 29, 31, 512.0f, 512.0f, false);
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft - 23 + 4, this.guiTop + 20 + i * 31 + 5, x * 20, 301, 20, 20, 512.0f, 512.0f, false);
                    continue;
                }
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft - 23, this.guiTop + 20 + i * 31, 0, 250, 23, 31, 512.0f, 512.0f, false);
                GL11.glBlendFunc((int)770, (int)771);
                GL11.glEnable((int)3042);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)0.75f);
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft - 23 + 4, this.guiTop + 20 + i * 31 + 5, x * 20, 301, 20, 20, 512.0f, 512.0f, false);
                GL11.glDisable((int)3042);
            }
            if (mapLoaded) {
                GUIUtils.startGLScissor(this.guiLeft + 11, this.guiTop + 83, 180, 78);
                GL11.glDisable((int)2929);
                this.minimapRenderer.renderMap(this.guiLeft + 12, this.guiTop + 74, mouseX, mouseY, true);
                GL11.glEnable((int)2929);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                ClientEventHandler.STYLE.bindTexture("enterprise_main");
                ModernGui.drawModalRectWithCustomSizedTextureWithTransparency(this.guiLeft + 11, this.guiTop + 83, 0, 362, 98, 78, 512.0f, 512.0f, false);
                GUIUtils.endGLScissor();
            }
            for (i = 0; i < EnterpriseGui.TABS.size(); ++i) {
                int x = EnterpriseGui.getTabIndex(EnterpriseGui.TABS.get(i));
                if (mouseX < this.guiLeft - 23 || mouseX > this.guiLeft - 23 + 29 || mouseY < this.guiTop + 20 + i * 31 || mouseY > this.guiTop + 20 + 30 + i * 31) continue;
                this.drawHoveringText(Arrays.asList(I18n.func_135053_a((String)("enterprise.tab." + x))), mouseX, mouseY, this.field_73886_k);
            }
        }
        super.func_73863_a(mouseX, mouseY, partialTicks);
        GL11.glEnable((int)2896);
        RenderHelper.func_74519_b();
    }

    public abstract void drawScreen(int var1, int var2);

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            for (int i = 0; i < EnterpriseGui.TABS.size(); ++i) {
                GuiScreenTab type = EnterpriseGui.TABS.get(i);
                if (mouseX < this.guiLeft - 20 || mouseX > this.guiLeft + 3 || mouseY < this.guiTop + 20 + i * 31 || mouseY > this.guiTop + 50 + i * 31) continue;
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                if (((Object)((Object)this)).getClass() == type.getClassReferent()) continue;
                try {
                    type.call();
                    continue;
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (mouseX > this.guiLeft + 385 && mouseX < this.guiLeft + 385 + 9 && mouseY > this.guiTop - 6 && mouseY < this.guiTop - 6 + 10) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a(null);
            }
        }
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    public boolean func_73868_f() {
        return false;
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

