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
package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionCreateGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionListGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.PlayerListGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.GUIGetHelpPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;
import org.lwjgl.opengl.GL11;

public abstract class TabbedFactionListGUI
extends GuiScreen {
    public static final List<GuiScreenTab> TABS = new ArrayList<GuiScreenTab>();
    public static int GUI_SCALE = 3;
    protected int xSize = 463;
    protected int ySize = 235;
    protected int guiLeft;
    protected int guiTop;
    private RenderItem itemRenderer = new RenderItem();
    public static List<String> tooltipToDraw = new ArrayList<String>();
    protected String hoveredAction = "";
    public static HashMap<String, Integer> tabIconsPositionY = new HashMap<String, Integer>(){
        {
            this.put("FactionCreateGUI", 239);
            this.put("FactionListGUI", 257);
            this.put("PlayerListGUI", 276);
        }
    };

    public void func_73866_w_() {
        this.guiLeft = (this.field_73880_f - this.xSize) / 2;
        this.guiTop = (this.field_73881_g - this.ySize) / 2;
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new GUIGetHelpPacket(((Object)((Object)this)).getClass().getSimpleName())));
    }

    public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
        GuiScreenTab type;
        int i;
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        ClientEventHandler.STYLE.bindTexture("faction_global");
        ClientEventHandler.STYLE.bindTexture("faction_list");
        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 11, this.guiTop, 415 * GUI_SCALE, 0 * GUI_SCALE, 19 * GUI_SCALE, 55 * GUI_SCALE, 19, 55, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
        for (i = 0; i < TABS.size(); ++i) {
            type = TABS.get(i);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            int x = i % 9;
            int y = i / 9;
            if (((Object)((Object)this)).getClass() == type.getClassReferent()) {
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 11, this.guiTop + i * 18, 434 * GUI_SCALE, 0 * GUI_SCALE + i * 18 * GUI_SCALE, 19 * GUI_SCALE, 18 * GUI_SCALE, 19, 18, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 11, this.guiTop + i * 18 - 1, 312 * GUI_SCALE, tabIconsPositionY.get(type.getClassReferent().getSimpleName()) * GUI_SCALE, 19 * GUI_SCALE, 18 * GUI_SCALE, 19, 18, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                continue;
            }
            if (mouseX > this.guiLeft + 11 && mouseX < this.guiLeft + 11 + 19 && mouseY > this.guiTop + 1 + i * 18 && mouseY < this.guiTop + 1 + i * 18 + 19) {
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 11, this.guiTop + i * 18 - 1, 274 * GUI_SCALE, tabIconsPositionY.get(type.getClassReferent().getSimpleName()) * GUI_SCALE, 19 * GUI_SCALE, 18 * GUI_SCALE, 19, 18, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                continue;
            }
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 11, this.guiTop + i * 18 - 1, 293 * GUI_SCALE, tabIconsPositionY.get(type.getClassReferent().getSimpleName()) * GUI_SCALE, 19 * GUI_SCALE, 18 * GUI_SCALE, 19, 18, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
        }
        for (i = 0; i < TABS.size(); ++i) {
            type = TABS.get(i);
            if (mouseX <= this.guiLeft + 11 || mouseX >= this.guiLeft + 11 + 19 || mouseY <= this.guiTop + 1 + i * 19 || mouseY >= this.guiTop + 1 + i * 19 + 19) continue;
            this.drawHoveringText(Arrays.asList(I18n.func_135053_a((String)("faction.tab." + type.getClassReferent().getSimpleName()))), mouseX, mouseY, this.field_73886_k);
        }
        if (tooltipToDraw != null && !tooltipToDraw.isEmpty()) {
            this.drawHoveringText(tooltipToDraw, mouseX, mouseY, this.field_73886_k);
        }
        super.func_73863_a(mouseX, mouseY, partialTicks);
        GL11.glEnable((int)2896);
        RenderHelper.func_74519_b();
    }

    public abstract void drawScreen(int var1, int var2);

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        ModernGui.mouseClickedCommon(this, mouseX, mouseY, mouseButton);
        if (mouseButton == 0) {
            for (int i = 0; i < TABS.size(); ++i) {
                GuiScreenTab type = TABS.get(i);
                if (mouseX < this.guiLeft + 11 || mouseX > this.guiLeft + 11 + 19 || mouseY < this.guiTop + 1 + i * 19 || mouseY > this.guiTop + 1 + i * 19 + 19) continue;
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
            if (mouseX > this.guiLeft + 445 && mouseX < this.guiLeft + 445 + 8 && mouseY > this.guiTop + 9 && mouseY < this.guiTop + 9 + 8) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a(null);
            }
        }
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    public boolean func_73868_f() {
        return false;
    }

    public static void initTabs() {
        TABS.clear();
        TABS.add(new GuiScreenTab(){

            @Override
            public Class<? extends GuiScreen> getClassReferent() {
                return FactionCreateGUI.class;
            }

            @Override
            public void call() {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new FactionCreateGUI());
            }
        });
        TABS.add(new GuiScreenTab(){

            @Override
            public Class<? extends GuiScreen> getClassReferent() {
                return FactionListGUI.class;
            }

            @Override
            public void call() {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new FactionListGUI());
            }
        });
        TABS.add(new GuiScreenTab(){

            @Override
            public Class<? extends GuiScreen> getClassReferent() {
                return PlayerListGUI.class;
            }

            @Override
            public void call() {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new PlayerListGUI());
            }
        });
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

