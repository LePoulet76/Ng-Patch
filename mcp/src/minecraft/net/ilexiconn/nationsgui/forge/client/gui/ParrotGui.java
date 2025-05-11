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
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import net.halalaboos.cfont.CFontRenderer;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarGeneric;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.ParrotDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.PlayerExecCmdPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class ParrotGui
extends GuiScreen {
    public static int GUI_SCALE = 3;
    public static CFontRenderer georamaBold27 = ModernGui.getCustomFont("georamaBold", 27);
    public static HashMap<String, Object> data = new HashMap();
    public static boolean hasNGPrime = false;
    public static boolean loaded = false;
    public static List<String> TABS = Arrays.asList("world", "warps", "homes", "warps_spawn");
    public String hoveredAction = "";
    protected int xSize = 505;
    protected int ySize = 235;
    private RenderItem itemRenderer = new RenderItem();
    private GuiScrollBarGeneric scrollBar;
    private String selectedTab;
    private int guiLeft;
    private int guiTop;

    public ParrotGui(String tabToOpen) {
        loaded = false;
        this.selectedTab = tabToOpen;
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new ParrotDataPacket()));
        this.guiLeft = (this.field_73880_f - this.xSize) / 2;
        this.guiTop = (this.field_73881_g - this.ySize) / 2;
        this.scrollBar = new GuiScrollBarGeneric(this.guiLeft + 344, this.guiTop + 48, 175, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_parrot.png"), 5, 28);
    }

    public void func_73863_a(int mouseX, int mouseY, float par3) {
        this.func_73873_v_();
        this.hoveredAction = "";
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        ClientEventHandler.STYLE.bindTexture("parrot_main");
        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft, this.guiTop, 9 * GUI_SCALE, 0 * GUI_SCALE, 499 * GUI_SCALE, 235 * GUI_SCALE, 499, 235, 1204 * GUI_SCALE, 1024 * GUI_SCALE, false);
        if (mouseX >= this.guiLeft + 505 && mouseX <= this.guiLeft + 515 && mouseY >= this.guiTop && mouseY <= this.guiTop + 10) {
            this.hoveredAction = "close";
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 505, this.guiTop, 560 * GUI_SCALE, 43 * GUI_SCALE, 10 * GUI_SCALE, 10 * GUI_SCALE, 10, 10, 1204 * GUI_SCALE, 1024 * GUI_SCALE, false);
        } else {
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 505, this.guiTop, 517 * GUI_SCALE, 0 * GUI_SCALE, 10 * GUI_SCALE, 10 * GUI_SCALE, 10, 10, 1204 * GUI_SCALE, 1024 * GUI_SCALE, false);
        }
        for (String tab : TABS) {
            if (this.selectedTab.equals(tab)) {
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft - 6, this.guiTop + TABS.indexOf(this.selectedTab) * 40, (this.selectedTab.equals("warps_spawn") ? 603 : 560) * GUI_SCALE, 0 * GUI_SCALE, 41 * GUI_SCALE, 35 * GUI_SCALE, 41, 35, 1204 * GUI_SCALE, 1024 * GUI_SCALE, false);
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft - 4, this.guiTop + TABS.indexOf(this.selectedTab) * 40, 9 * GUI_SCALE, (252 + TABS.indexOf(this.selectedTab) * 35) * GUI_SCALE, 35 * GUI_SCALE, 35 * GUI_SCALE, 35, 35, 1204 * GUI_SCALE, 1024 * GUI_SCALE, false);
                continue;
            }
            if (mouseX > this.guiLeft && mouseX < this.guiLeft + 35 && mouseY > this.guiTop + TABS.indexOf(tab) * 40 && mouseY < this.guiTop + TABS.indexOf(tab) * 40 + 35) {
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft, this.guiTop + TABS.indexOf(tab) * 40, 9 * GUI_SCALE, (427 + TABS.indexOf(tab) * 35) * GUI_SCALE, 35 * GUI_SCALE, 35 * GUI_SCALE, 35, 35, 1204 * GUI_SCALE, 1024 * GUI_SCALE, false);
                this.hoveredAction = "tab#" + tab;
                continue;
            }
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft, this.guiTop + TABS.indexOf(tab) * 40, 9 * GUI_SCALE, (602 + TABS.indexOf(tab) * 35) * GUI_SCALE, 35 * GUI_SCALE, 35 * GUI_SCALE, 35, 35, 1204 * GUI_SCALE, 1024 * GUI_SCALE, false);
        }
        ClientEventHandler.STYLE.bindTexture("parrot_main");
        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + (this.selectedTab.equals("warps_spawn") ? 15 : 50), this.guiTop + 10, 237 * GUI_SCALE, (this.selectedTab.equals("warps_spawn") ? 706 : 665) * GUI_SCALE, 176 * GUI_SCALE, 30 * GUI_SCALE, 176, 30, 1204 * GUI_SCALE, 1024 * GUI_SCALE, false);
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("gui.parrot." + this.selectedTab)), this.guiLeft + (this.selectedTab.equals("warps_spawn") ? 50 : 83), this.guiTop + 32, this.selectedTab.equals("warps_spawn") ? 16171012 : 7258350, 0.5f, "left", true, "georamaSemiBold", 25);
        if (this.selectedTab.equals("world")) {
            ClientEventHandler.STYLE.bindTexture("parrot_main");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 49, this.guiTop + 47, 72 * GUI_SCALE, 243 * GUI_SCALE, 436 * GUI_SCALE, 173 * GUI_SCALE, 436, 173, 1204 * GUI_SCALE, 1024 * GUI_SCALE, false);
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 350, this.guiTop + 15, 74 * GUI_SCALE, 647 * GUI_SCALE, 145 * GUI_SCALE, 134 * GUI_SCALE, 145, 134, 1204 * GUI_SCALE, 1024 * GUI_SCALE, false);
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 57, this.guiTop + 60, 622 * GUI_SCALE, 766 * GUI_SCALE, 285 * GUI_SCALE, 152 * GUI_SCALE, 285, 152, 1204 * GUI_SCALE, 1024 * GUI_SCALE, false);
            if (mouseX < this.guiLeft + 148 || mouseX > this.guiLeft + 172 || mouseY < this.guiTop + 110 || mouseY > this.guiTop + 135) {
                if (mouseX >= this.guiLeft + 57 && mouseX <= this.guiLeft + 178 && mouseY >= this.guiTop + 60 && mouseY <= this.guiTop + 145) {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 57, this.guiTop + 60, 919 * GUI_SCALE, 766 * GUI_SCALE, 285 * GUI_SCALE, 152 * GUI_SCALE, 285, 152, 1204 * GUI_SCALE, 1024 * GUI_SCALE, false);
                    this.hoveredAction = "world#america_north";
                } else if (mouseX >= this.guiLeft + 57 && mouseX <= this.guiLeft + 178 && mouseY >= this.guiTop + 145 && mouseY <= this.guiTop + 200) {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 57, this.guiTop + 60, 919 * GUI_SCALE, 307 * GUI_SCALE, 285 * GUI_SCALE, 152 * GUI_SCALE, 285, 152, 1204 * GUI_SCALE, 1024 * GUI_SCALE, false);
                    this.hoveredAction = "world#america_south";
                } else if (mouseX >= this.guiLeft + 178 && mouseX <= this.guiLeft + 239 && mouseY >= this.guiTop + 60 && mouseY <= this.guiTop + 129) {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 57, this.guiTop + 60, 919 * GUI_SCALE, 613 * GUI_SCALE, 285 * GUI_SCALE, 152 * GUI_SCALE, 285, 152, 1204 * GUI_SCALE, 1024 * GUI_SCALE, false);
                    this.hoveredAction = "world#europe";
                } else if (mouseX >= this.guiLeft + 178 && mouseX <= this.guiLeft + 239 && mouseY >= this.guiTop + 129 && mouseY <= this.guiTop + 200) {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 57, this.guiTop + 60, 919 * GUI_SCALE, 154 * GUI_SCALE, 285 * GUI_SCALE, 152 * GUI_SCALE, 285, 152, 1204 * GUI_SCALE, 1024 * GUI_SCALE, false);
                    this.hoveredAction = "world#africa";
                } else if (mouseX >= this.guiLeft + 239 && mouseX <= this.guiLeft + 342 && mouseY >= this.guiTop + 60 && mouseY <= this.guiTop + 152) {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 57, this.guiTop + 60, 919 * GUI_SCALE, 460 * GUI_SCALE, 285 * GUI_SCALE, 152 * GUI_SCALE, 285, 152, 1204 * GUI_SCALE, 1024 * GUI_SCALE, false);
                    this.hoveredAction = "world#asia";
                } else if (mouseX >= this.guiLeft + 264 && mouseX <= this.guiLeft + 342 && mouseY >= this.guiTop + 152 && mouseY <= this.guiTop + 200) {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 57, this.guiTop + 60, 919 * GUI_SCALE, 1 * GUI_SCALE, 285 * GUI_SCALE, 152 * GUI_SCALE, 285, 152, 1204 * GUI_SCALE, 1024 * GUI_SCALE, false);
                    this.hoveredAction = "world#oceania";
                } else if (mouseX >= this.guiLeft + 120 && mouseX <= this.guiLeft + 208 && mouseY >= this.guiTop + 200 && mouseY <= this.guiTop + 215) {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 57, this.guiTop + 60, 622 * GUI_SCALE, 460 * GUI_SCALE, 285 * GUI_SCALE, 152 * GUI_SCALE, 285, 152, 1204 * GUI_SCALE, 1024 * GUI_SCALE, false);
                    this.hoveredAction = "world#antarctica_west";
                } else if (mouseX >= this.guiLeft + 208 && mouseX <= this.guiLeft + 295 && mouseY >= this.guiTop + 200 && mouseY <= this.guiTop + 215) {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 57, this.guiTop + 60, 622 * GUI_SCALE, 613 * GUI_SCALE, 285 * GUI_SCALE, 152 * GUI_SCALE, 285, 152, 1204 * GUI_SCALE, 1024 * GUI_SCALE, false);
                    this.hoveredAction = "world#antarctica_east";
                }
            }
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 57, this.guiTop + 60, 325 * GUI_SCALE, 766 * GUI_SCALE, 285 * GUI_SCALE, 152 * GUI_SCALE, 285, 152, 1204 * GUI_SCALE, 1024 * GUI_SCALE, false);
        } else if ((this.selectedTab.equals("warps") || this.selectedTab.equals("homes") || this.selectedTab.equals("warps_spawn")) && loaded) {
            ClientEventHandler.STYLE.bindTexture("parrot_main");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 362, this.guiTop + 47, 385 * GUI_SCALE, 243 * GUI_SCALE, 123 * GUI_SCALE, 173 * GUI_SCALE, 123, 173, 1204 * GUI_SCALE, 1024 * GUI_SCALE, false);
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 350, this.guiTop + 15, 74 * GUI_SCALE, (this.selectedTab.equals("warps_spawn") ? 781 : 647) * GUI_SCALE, 145 * GUI_SCALE, 134 * GUI_SCALE, 145, 134, 1204 * GUI_SCALE, 1024 * GUI_SCALE, false);
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 344, this.guiTop + 48, 560 * GUI_SCALE, 316 * GUI_SCALE, 5 * GUI_SCALE, 175 * GUI_SCALE, 5, 175, 1204 * GUI_SCALE, 1024 * GUI_SCALE, false);
            ArrayList elements = new ArrayList();
            if (this.selectedTab.equals("warps")) {
                elements = (ArrayList)data.get("warps");
            } else if (this.selectedTab.equals("warps_spawn")) {
                elements = (ArrayList)data.get("warps_spawn");
            } else if (this.selectedTab.equals("homes")) {
                elements = (ArrayList)data.get("homes");
            }
            GUIUtils.startGLScissor(this.guiLeft + 49, this.guiTop + 47, 285, 176);
            int index = 0;
            for (String element : elements) {
                int offsetX = this.guiLeft + 49 + index % 3 * 96;
                Float offsetY = Float.valueOf((float)(this.guiTop + 47 + index / 3 * 37) + this.getSlide());
                boolean isHovered = mouseX >= offsetX && mouseX <= offsetX + 92 && (float)mouseY >= offsetY.floatValue() && (float)mouseY <= offsetY.floatValue() + 28.0f;
                ClientEventHandler.STYLE.bindTexture("parrot_main");
                ModernGui.drawScaledCustomSizeModalRect(offsetX, offsetY.floatValue(), (this.selectedTab.equals("warps_spawn") && isHovered ? 662 : 560) * GUI_SCALE, (this.selectedTab.equals("warps_spawn") && isHovered ? 205 : (isHovered ? 243 : 280)) * GUI_SCALE, 92 * GUI_SCALE, 28 * GUI_SCALE, 92, 28, 1204 * GUI_SCALE, 1024 * GUI_SCALE, false);
                ModernGui.drawScaledStringCustomFont(element.split("#")[0].replaceAll("capitale_", "").toUpperCase(), offsetX + 46, offsetY.floatValue() + (float)(this.selectedTab.contains("warps") ? 6 : 9), isHovered ? 1908021 : 0xBABADA, 0.55f, "center", false, "minecraftDungeons", 23);
                if (this.selectedTab.contains("warps")) {
                    ModernGui.drawScaledStringCustomFont(element.split("#")[1].equals("0") ? "\u00a7o" + I18n.func_135053_a((String)"gui.parrot.warps.free") : "\u00a7o" + element.split("#")[1] + "$", offsetX + 46, offsetY.floatValue() + 17.0f, isHovered ? 1908021 : 0xBABADA, 0.5f, "center", false, "georamaSemiBold", 23);
                }
                if (isHovered && this.selectedTab.contains("warps")) {
                    this.hoveredAction = "warp#" + element;
                } else if (isHovered && this.selectedTab.equals("homes")) {
                    this.hoveredAction = "home#" + element.split("#")[0];
                }
                ++index;
            }
            GUIUtils.endGLScissor();
            this.scrollBar.draw(mouseX, mouseY);
        }
        if (!this.selectedTab.equals("map")) {
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"gui.parrot.tickets.title").toUpperCase(), this.guiLeft + 374, this.guiTop + 160, 0xBABADA, 0.5f, "left", true, "minecraftDungeons", 23);
            ClientEventHandler.STYLE.bindTexture("parrot_main");
            if (loaded) {
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 374, this.guiTop + 172, 560 * GUI_SCALE, (hasNGPrime ? 99 : 145) * GUI_SCALE, 98 * GUI_SCALE, 18 * GUI_SCALE, 98, 18, 1204 * GUI_SCALE, 1024 * GUI_SCALE, false);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"gui.parrot.tickets.1er"), this.guiLeft + 400, this.guiTop + 178, hasNGPrime ? 1908021 : 0xAE6EEE, 0.5f, "left", false, "georamaBold", 27);
                ModernGui.drawScaledStringCustomFont("\u00a7o" + I18n.func_135053_a((String)"gui.parrot.tickets.free"), (float)(this.guiLeft + 400) + georamaBold27.getStringWidth(I18n.func_135053_a((String)"gui.parrot.tickets.1er")) / 2.0f + 5.0f, (float)this.guiTop + 177.5f, hasNGPrime ? 1908021 : 0xAE6EEE, 0.5f, "left", false, "georamaMedium", 25);
                ClientEventHandler.STYLE.bindTexture("parrot_main");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 374, this.guiTop + 194, 560 * GUI_SCALE, (hasNGPrime ? 122 : (this.selectedTab.equals("warps_spawn") ? 168 : 76)) * GUI_SCALE, 98 * GUI_SCALE, 18 * GUI_SCALE, 98, 18, 1204 * GUI_SCALE, 1024 * GUI_SCALE, false);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"gui.parrot.tickets.2nd"), this.guiLeft + 400, this.guiTop + 200, hasNGPrime ? 0x4A4A88 : 1908021, 0.5f, "left", false, "georamaBold", 27);
                if (!hasNGPrime) {
                    ModernGui.drawScaledStringCustomFont(this.selectedTab.equals("world") ? String.format("%.0f", (Double)data.get("price")) + "$" : I18n.func_135053_a((String)"gui.parrot.tickets.variable"), (float)(this.guiLeft + 400) + georamaBold27.getStringWidth(I18n.func_135053_a((String)"gui.parrot.tickets.2nd")) / 2.0f + 5.0f, (float)this.guiTop + 199.5f, 1908021, 0.5f, "left", false, "georamaSemiBold", 25);
                }
            }
        }
        super.func_73863_a(mouseX, mouseY, par3);
    }

    public boolean func_73868_f() {
        return false;
    }

    private float getSlide() {
        ArrayList elements = new ArrayList();
        if (this.selectedTab.equals("warps")) {
            elements = (ArrayList)data.get("warps");
        } else if (this.selectedTab.equals("warps_spawn")) {
            elements = (ArrayList)data.get("warps_spawn");
        } else if (this.selectedTab.equals("homes")) {
            elements = (ArrayList)data.get("homes");
        }
        return elements.size() > 15 ? (float)(-(elements.size() - 15) * 14) * this.scrollBar.getSliderValue() : 0.0f;
    }

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            if (this.hoveredAction.equals("close")) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a(null);
            } else if (this.hoveredAction.contains("tab#")) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                this.selectedTab = this.hoveredAction.split("#")[1];
            } else if (this.hoveredAction.contains("world#")) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a(null);
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new PlayerExecCmdPacket("warp " + this.hoveredAction.split("#")[1], hasNGPrime ? 0 : ((Double)data.get("price")).intValue())));
            } else if (this.hoveredAction.contains("warp#")) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a(null);
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new PlayerExecCmdPacket("warp " + this.hoveredAction.split("#")[1], hasNGPrime ? 0 : Integer.parseInt(this.hoveredAction.split("#")[2]))));
            } else if (this.hoveredAction.contains("home#")) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a(null);
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new PlayerExecCmdPacket("home " + this.hoveredAction.split("#")[1], 0)));
            }
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

