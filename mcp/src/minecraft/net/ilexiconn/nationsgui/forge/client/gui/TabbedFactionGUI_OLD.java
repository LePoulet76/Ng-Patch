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
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.network.packet.Packet
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.MinimapRenderer;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGui_OLD;
import net.ilexiconn.nationsgui.forge.client.gui.faction.SettingsGUI_OLD;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.MinimapRequestPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.RemoteOpenOwnFactionMainPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;
import org.lwjgl.opengl.GL11;

public abstract class TabbedFactionGUI_OLD
extends GuiScreen {
    protected final EntityPlayer player;
    protected int xSize = 400;
    protected int ySize = 250;
    protected int guiLeft;
    protected int guiTop;
    public static boolean mapLoaded;
    private RenderItem itemRenderer = new RenderItem();
    public MinimapRenderer minimapRenderer = new MinimapRenderer(6, 6);
    boolean relationExpanded = false;
    private GuiScrollBarFaction scrollBarRelations;
    protected String hoveredRelation = "";
    private final Map<Class<? extends GuiScreen>, Integer> tabAlerts = new HashMap<Class<? extends GuiScreen>, Integer>();

    public TabbedFactionGUI_OLD(EntityPlayer player) {
        this.player = player;
        mapLoaded = false;
    }

    public void func_73866_w_() {
        this.guiLeft = (this.field_73880_f - this.xSize) / 2;
        this.guiTop = (this.field_73881_g - this.ySize) / 2;
    }

    public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
        this.func_73873_v_();
        this.drawScreen(mouseX, mouseY);
        if (FactionGui_OLD.factionInfos != null) {
            if (!mapLoaded && FactionGui_OLD.factionInfos.size() > 0) {
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new MinimapRequestPacket(Integer.parseInt(((String)FactionGui_OLD.factionInfos.get("home")).split(",")[0]), Integer.parseInt(((String)FactionGui_OLD.factionInfos.get("home")).split(",")[1]), 6, 6)));
                mapLoaded = true;
            }
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            ClientEventHandler.STYLE.bindTexture("faction_main");
            if (mouseX > this.guiLeft + 385 && mouseX < this.guiLeft + 385 + 9 && mouseY > this.guiTop - 6 && mouseY < this.guiTop - 6 + 10) {
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 385, this.guiTop - 6, 138, 261, 9, 10, 512.0f, 512.0f, false);
            } else {
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 385, this.guiTop - 6, 138, 251, 9, 10, 512.0f, 512.0f, false);
            }
            if (!((Boolean)FactionGui_OLD.factionInfos.get("isInCountry")).booleanValue() && ((Boolean)FactionGui_OLD.factionInfos.get("playerHasFaction")).booleanValue()) {
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 8, this.guiTop + 250, 187, 251, 104, 19, 512.0f, 512.0f, false);
                this.drawScaledString(I18n.func_135053_a((String)"faction.home.button.own_country"), this.guiLeft + 31, this.guiTop + 254, 0xFFFFFF, 1.0f, false, false);
            }
            if (((String)FactionGui_OLD.factionInfos.get("name")).length() <= 9) {
                this.drawScaledString((String)FactionGui_OLD.factionInfos.get("name"), this.guiLeft + 60, this.guiTop + 25, 0xFFFFFF, 1.8f, true, true);
            } else {
                this.drawScaledString((String)FactionGui_OLD.factionInfos.get("name"), this.guiLeft + 60, this.guiTop + 25, 0xFFFFFF, 1.0f, true, true);
            }
            this.drawScaledString(I18n.func_135053_a((String)"faction.common.age_1") + " " + FactionGui_OLD.factionInfos.get("age") + " " + I18n.func_135053_a((String)"faction.common.age_2"), this.guiLeft + 60, this.guiTop + 43, 0xB4B4B4, 0.65f, true, false);
            this.drawScaledString(I18n.func_135053_a((String)("faction.common." + FactionGui_OLD.factionInfos.get("actualRelation"))), this.guiLeft + 58, this.guiTop + 66, 0x191919, 1.0f, true, false);
            ClientEventHandler.STYLE.bindTexture("faction_main");
            for (int i = 0; i < FactionGui_OLD.TABS.size(); ++i) {
                GuiScreenTab type = FactionGui_OLD.TABS.get(i);
                if (type.getClassReferent().equals(SettingsGUI_OLD.class)) {
                    if (((Object)((Object)this)).getClass() == type.getClassReferent()) {
                        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 398, this.guiTop + 200, 85, 251, 29, 30, 512.0f, 512.0f, false);
                        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 400 + 2, this.guiTop + 200 + 4, 40, 321, 20, 20, 512.0f, 512.0f, false);
                        continue;
                    }
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 400, this.guiTop + 200, 114, 251, 23, 30, 512.0f, 512.0f, false);
                    GL11.glBlendFunc((int)770, (int)771);
                    GL11.glEnable((int)3042);
                    GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)0.75f);
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 400 + 2, this.guiTop + 200 + 4, 40, 321, 20, 20, 512.0f, 512.0f, false);
                    GL11.glDisable((int)3042);
                    continue;
                }
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                int x = i % 5;
                int y = i / 5;
                if (((Object)((Object)this)).getClass() == type.getClassReferent()) {
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft - 23, this.guiTop + 20 + i * 31, 23, 251, 29, 31, 512.0f, 512.0f, false);
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft - 23 + 4, this.guiTop + 20 + i * 31 + 5, x * 20, 301 + y * 20, 20, 20, 512.0f, 512.0f, false);
                    continue;
                }
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft - 23, this.guiTop + 20 + i * 31, 0, 251, 23, 31, 512.0f, 512.0f, false);
                GL11.glBlendFunc((int)770, (int)771);
                GL11.glEnable((int)3042);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)0.75f);
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft - 23 + 4, this.guiTop + 20 + i * 31 + 5, x * 20, 301 + y * 20, 20, 20, 512.0f, 512.0f, false);
                GL11.glDisable((int)3042);
            }
            ClientEventHandler.STYLE.bindTexture("faction_main");
            int negativeOffsetX = 0;
            if (Integer.parseInt((String)FactionGui_OLD.factionInfos.get("age")) < 2) {
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 105, this.guiTop + 50, 149, 263, 10, 10, 512.0f, 512.0f, false);
                if (mouseX >= this.guiLeft + 105 && mouseX <= this.guiLeft + 105 + 10 && mouseY >= this.guiTop + 50 && mouseY <= this.guiTop + 50 + 10) {
                    this.drawHoveringText(Arrays.asList(I18n.func_135053_a((String)"faction.common.badge.young")), mouseX, mouseY, this.field_73886_k);
                }
                negativeOffsetX += 12;
            }
            if (((Boolean)FactionGui_OLD.factionInfos.get("isEmpire")).booleanValue()) {
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 105 - negativeOffsetX, this.guiTop + 50, 160, 251, 10, 10, 512.0f, 512.0f, false);
                if (mouseX >= this.guiLeft + 105 - negativeOffsetX && mouseX <= this.guiLeft + 105 - negativeOffsetX + 10 && mouseY >= this.guiTop + 50 && mouseY <= this.guiTop + 50 + 10) {
                    this.drawHoveringText(Arrays.asList(I18n.func_135053_a((String)"faction.common.badge.empire")), mouseX, mouseY, this.field_73886_k);
                }
            } else if (!((String)FactionGui_OLD.factionInfos.get("isColony")).isEmpty()) {
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 105 - negativeOffsetX, this.guiTop + 50, 172, 251, 10, 10, 512.0f, 512.0f, false);
                if (mouseX >= this.guiLeft + 105 - negativeOffsetX && mouseX <= this.guiLeft + 105 - negativeOffsetX + 10 && mouseY >= this.guiTop + 50 && mouseY <= this.guiTop + 50 + 10) {
                    this.drawHoveringText(Arrays.asList(I18n.func_135053_a((String)"faction.common.badge.colony") + " " + FactionGui_OLD.factionInfos.get("isColony")), mouseX, mouseY, this.field_73886_k);
                }
            }
            if (mapLoaded) {
                GUIUtils.startGLScissor(this.guiLeft + 11, this.guiTop + 83, 180, 78);
                GL11.glDisable((int)2929);
                this.minimapRenderer.renderMap(this.guiLeft + 12, this.guiTop + 74, mouseX, mouseY, true);
                GL11.glEnable((int)2929);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                ClientEventHandler.STYLE.bindTexture("faction_main");
                ModernGui.drawModalRectWithCustomSizedTextureWithTransparency(this.guiLeft + 11, this.guiTop + 83, 0, 362, 98, 78, 512.0f, 512.0f, false);
                GUIUtils.endGLScissor();
            }
            for (int i = 0; i < FactionGui_OLD.TABS.size(); ++i) {
                GuiScreenTab type = FactionGui_OLD.TABS.get(i);
                if (!type.getClassReferent().equals(SettingsGUI_OLD.class)) {
                    if (mouseX < this.guiLeft - 23 || mouseX > this.guiLeft - 23 + 29 || mouseY < this.guiTop + 20 + i * 31 || mouseY > this.guiTop + 20 + 30 + i * 31) continue;
                    this.drawHoveringText(Arrays.asList(I18n.func_135053_a((String)("faction.tab." + type.getClassReferent().getSimpleName()))), mouseX, mouseY, this.field_73886_k);
                    continue;
                }
                if (mouseX < this.guiLeft + 400 || mouseX > this.guiLeft + 400 + 23 || mouseY < this.guiTop + 200 || mouseY > this.guiTop + 200 + 30) continue;
                this.drawHoveringText(Arrays.asList(I18n.func_135053_a((String)("faction.tab." + type.getClassReferent().getSimpleName()))), mouseX, mouseY, this.field_73886_k);
            }
        }
        super.func_73863_a(mouseX, mouseY, partialTicks);
        GL11.glEnable((int)2896);
        RenderHelper.func_74519_b();
    }

    public abstract void drawScreen(int var1, int var2);

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            for (int i = 0; i < FactionGui_OLD.TABS.size(); ++i) {
                GuiScreenTab type = FactionGui_OLD.TABS.get(i);
                if (!type.getClassReferent().equals(SettingsGUI_OLD.class)) {
                    if (mouseX < this.guiLeft - 20 || mouseX > this.guiLeft + 3 || mouseY < this.guiTop + 20 + i * 31 || mouseY > this.guiTop + 50 + i * 31) continue;
                    this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                    if (((Object)((Object)this)).getClass() == type.getClassReferent()) continue;
                    try {
                        type.call();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    continue;
                }
                if (mouseX < this.guiLeft + 400 || mouseX > this.guiLeft + 400 + 23 || mouseY < this.guiTop + 200 || mouseY > this.guiTop + 200 + 30) continue;
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
            if (mouseX >= this.guiLeft + 8 && mouseX <= this.guiLeft + 8 + 104 && mouseY >= this.guiTop + 250 && mouseY <= this.guiTop + 250 + 19) {
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new RemoteOpenOwnFactionMainPacket()));
            }
        }
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    public boolean func_73868_f() {
        return false;
    }

    private float getSlideRelations() {
        return ((List)FactionGui_OLD.factionInfos.get("possibleRelations")).size() > 3 ? (float)(-(((List)FactionGui_OLD.factionInfos.get("possibleRelations")).size() - 3) * 20) * this.scrollBarRelations.getSliderValue() : 0.0f;
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

