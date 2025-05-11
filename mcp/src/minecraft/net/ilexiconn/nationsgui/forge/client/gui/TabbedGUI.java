/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.entity.player.EntityPlayer
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.ilexiconn.nationsgui.forge.client.gui.InventoryGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionCreateGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

public abstract class TabbedGUI
extends GuiScreen {
    protected final EntityPlayer player;
    protected int xSize = 182;
    protected int ySize = 223;
    protected int guiLeft;
    protected int guiTop;
    private RenderItem itemRenderer = new RenderItem();
    private final Map<Class<? extends GuiScreen>, Integer> tabAlerts = new HashMap<Class<? extends GuiScreen>, Integer>();

    public TabbedGUI(EntityPlayer player) {
        this.player = player;
        this.calculateAlerts();
    }

    protected void calculateAlerts() {
        this.tabAlerts.clear();
    }

    public void func_73866_w_() {
        this.guiLeft = (this.field_73880_f - this.xSize) / 2;
        this.guiTop = (this.field_73881_g - this.ySize) / 2;
    }

    public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
        int i;
        int y;
        int x;
        GuiScreenTab type;
        int i2;
        this.func_73873_v_();
        this.drawScreen(mouseX, mouseY);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        ClientEventHandler.STYLE.bindTexture("inventory");
        for (i2 = 0; i2 <= 4; ++i2) {
            type = InventoryGUI.TABS.get(i2);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            x = i2 % 3;
            y = i2 / 3;
            if (((Object)((Object)this)).getClass() == type.getClassReferent()) {
                this.func_73729_b(this.guiLeft - 23, this.guiTop + 55 + i2 * 31, 23, 223, 29, 30);
                this.func_73729_b(this.guiLeft - 23 + 3, this.guiTop + 55 + i2 * 31 + 5, 182 + x * 20, y * 20, 20, 20);
                continue;
            }
            this.func_73729_b(this.guiLeft - 20, this.guiTop + 55 + i2 * 31, 0, 223, 23, 30);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glEnable((int)3042);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)0.75f);
            this.func_73729_b(this.guiLeft - 20 + 3, this.guiTop + 55 + i2 * 31 + 5, 182 + x * 20, y * 20, 20, 20);
            GL11.glDisable((int)3042);
        }
        for (i2 = 5; i2 < InventoryGUI.TABS.size(); ++i2) {
            type = InventoryGUI.TABS.get(i2);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            x = i2 % 3;
            y = i2 / 3;
            if (((Object)((Object)this)).getClass() == type.getClassReferent()) {
                this.func_73729_b(this.guiLeft + 178, this.guiTop + 55 + (i2 - 4) * 31, 85, 223, 29, 30);
                this.func_73729_b(this.guiLeft + 175, this.guiTop + 55 + (i2 - 4) * 31 + 5, 182 + x * 20, y * 20, 20, 20);
                continue;
            }
            this.func_73729_b(this.guiLeft + 178, this.guiTop + 55 + (i2 - 4) * 31, 114, 223, 23, 30);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glEnable((int)3042);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)0.75f);
            this.func_73729_b(this.guiLeft + 179, this.guiTop + 55 + (i2 - 4) * 31 + 5, 182 + x * 20, y * 20, 20, 20);
            GL11.glDisable((int)3042);
        }
        if (ClientData.currentFaction != null) {
            ClientEventHandler.STYLE.bindTexture("faction_main");
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 7, this.guiTop + 223, 0, 451, 169, 23, 512.0f, 512.0f, false);
            ClientEventHandler.STYLE.bindTexture("inventory");
            if (!ClientData.currentFaction.contains("Wilderness")) {
                Double stringWidth = (double)this.field_73886_k.func_78256_a(I18n.func_135053_a((String)"gui.inventory.tab.country")) * 1.2 / 2.0;
                this.func_73729_b(this.guiLeft + 91 - stringWidth.intValue() - 17, this.guiTop + 225, 201, 107, 12, 17);
                this.drawScaledString(I18n.func_135053_a((String)"gui.inventory.tab.country"), this.guiLeft + 93 - stringWidth.intValue(), this.guiTop + 230, 0xFFFFFF, 1.2f, false, true);
            } else {
                Double stringWidth = (double)this.field_73886_k.func_78256_a(I18n.func_135053_a((String)"gui.inventory.tab.create")) * 1.2 / 2.0;
                this.func_73729_b(this.guiLeft + 91 - stringWidth.intValue() - 17, this.guiTop + 225, 185, 107, 12, 17);
                this.drawScaledString(I18n.func_135053_a((String)"gui.inventory.tab.create"), this.guiLeft + 93 - stringWidth.intValue(), this.guiTop + 230, 0xFFFFFF, 1.2f, false, true);
            }
        }
        this.drawScaledString(this.player.getDisplayName(), this.guiLeft + 90, this.guiTop + 10, 0xFFFFFF, 1.7f, true, true);
        this.drawScaledString(ClientData.currentFaction, this.guiLeft + 90, this.guiTop + 28, 0xC6C6C6, 1.0f, true, true);
        super.func_73863_a(mouseX, mouseY, partialTicks);
        for (i = 0; i <= 4; ++i) {
            if (mouseX < this.guiLeft - (((Object)((Object)this)).getClass() == InventoryGUI.TABS.get(i).getClassReferent() ? 23 : 20) || mouseX > this.guiLeft + 3 || mouseY < this.guiTop + 55 + i * 31 || mouseY > this.guiTop + 85 + i * 31) continue;
            this.drawHoveringText(Collections.singletonList(I18n.func_135053_a((String)("gui.inventory.tab." + i))), mouseX, mouseY, this.field_73886_k);
        }
        for (i = 5; i < InventoryGUI.TABS.size(); ++i) {
            if (i == 5 && InventoryGUI.isInAssault || mouseX < this.guiLeft + 178 || mouseX > this.guiLeft + 201 || mouseY < this.guiTop + 55 + (i - 4) * 31 || mouseY > this.guiTop + 55 + 31 + (i - 4) * 31) continue;
            this.drawHoveringText(Collections.singletonList(I18n.func_135053_a((String)("gui.inventory.tab." + i))), mouseX, mouseY, this.field_73886_k);
        }
        this.drawTooltip(mouseX, mouseY);
        GL11.glEnable((int)2896);
        RenderHelper.func_74519_b();
    }

    public abstract void drawScreen(int var1, int var2);

    public abstract void drawTooltip(int var1, int var2);

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            GuiScreenTab type;
            int i;
            for (i = 0; i <= 4; ++i) {
                type = InventoryGUI.TABS.get(i);
                if (mouseX < this.guiLeft - 20 || mouseX > this.guiLeft + 3 || mouseY < this.guiTop + 55 + i * 31 || mouseY > this.guiTop + 85 + i * 31) continue;
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                if (((Object)((Object)this)).getClass() == type.getClassReferent()) continue;
                try {
                    this.field_73882_e.field_71439_g.func_71053_j();
                    type.call();
                    continue;
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            for (i = 5; i < InventoryGUI.TABS.size(); ++i) {
                if (i == 5 && InventoryGUI.isInAssault) continue;
                type = InventoryGUI.TABS.get(i);
                if (mouseX < this.guiLeft + 178 || mouseX > this.guiLeft + 201 || mouseY < this.guiTop + 55 + (i - 4) * 31 || mouseY > this.guiTop + 55 + 31 + (i - 4) * 31) continue;
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                if (((Object)((Object)this)).getClass() == type.getClassReferent()) continue;
                try {
                    this.field_73882_e.field_71439_g.func_71053_j();
                    type.call();
                    continue;
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (mouseX >= this.guiLeft + 7 && mouseX <= this.guiLeft + 7 + 169 && mouseY >= this.guiTop + 223 && mouseY <= this.guiTop + 223 + 23 && ClientData.currentFaction != null) {
                if (!ClientData.currentFaction.contains("Wilderness")) {
                    Minecraft.func_71410_x().func_71373_a((GuiScreen)new FactionGUI(ClientData.currentFaction));
                } else {
                    Minecraft.func_71410_x().func_71373_a((GuiScreen)new FactionCreateGUI());
                }
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

