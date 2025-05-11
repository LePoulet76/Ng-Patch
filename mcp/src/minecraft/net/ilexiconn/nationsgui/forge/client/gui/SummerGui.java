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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.SummerDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.SummerTradePacket;
import net.ilexiconn.nationsgui.forge.server.util.SoundStreamer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class SummerGui
extends GuiScreen {
    public static int GUI_SCALE = 3;
    public static boolean loaded = false;
    public static HashMap<String, Object> data = new HashMap();
    public String hoveredAction = "";
    public long lastMusicCheck = 0L;
    protected int xSize = 232;
    protected int ySize = 231;
    private int guiLeft;
    private int guiTop;
    private RenderItem itemRenderer = new RenderItem();

    public SummerGui() {
        loaded = false;
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new SummerDataPacket()));
        this.guiLeft = (this.field_73880_f - this.xSize) / 2;
        this.guiTop = (this.field_73881_g - this.ySize) / 2;
    }

    public void func_73863_a(int mouseX, int mouseY, float par3) {
        if (System.currentTimeMillis() - this.lastMusicCheck > 1000L) {
            this.lastMusicCheck = System.currentTimeMillis();
            if (ClientProxy.commandPlayer == null || !ClientProxy.commandPlayer.isPlaying()) {
                ClientProxy.commandPlayer = new SoundStreamer("https://static.nationsglory.fr/N4N66N3y2_.mp3");
                ClientProxy.commandPlayer.setVolume(Minecraft.func_71410_x().field_71474_y.field_74340_b * 0.35f);
                new Thread(ClientProxy.commandPlayer).start();
            }
        }
        this.func_73873_v_();
        this.hoveredAction = "";
        ArrayList tooltipToDraw = new ArrayList();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        ClientEventHandler.STYLE.bindTexture("summer");
        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft, this.guiTop, 0 * GUI_SCALE, 0 * GUI_SCALE, this.xSize * GUI_SCALE, this.ySize * GUI_SCALE, this.xSize, this.ySize, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"gui.summer.title"), this.guiLeft + 117, this.guiTop + 40, 7033896, 0.75f, "center", true, "minecraftDungeons", 23);
        ModernGui.drawSectionStringCustomFont(I18n.func_135053_a((String)"gui.summer.description"), this.guiLeft + 117, this.guiTop + 65, 7033896, 0.75f, "center", false, "georamaMedium", 23, 10, 210);
        ClientEventHandler.STYLE.bindTexture("summer");
        if (mouseX >= this.guiLeft + 235 && mouseX <= this.guiLeft + 235 + 16 && mouseY >= this.guiTop + 2 && mouseY <= this.guiTop + 2 + 16) {
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 235, this.guiTop + 2, 256 * GUI_SCALE, 106 * GUI_SCALE, 16 * GUI_SCALE, 16 * GUI_SCALE, 16, 16, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
            this.hoveredAction = "close";
        } else {
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 235, this.guiTop + 2, 235 * GUI_SCALE, 2 * GUI_SCALE, 16 * GUI_SCALE, 16 * GUI_SCALE, 16, 16, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
        }
        if (loaded) {
            boolean isButtonHovered;
            ClientEventHandler.STYLE.bindTexture("summer");
            int playerPoints = ((Double)data.get("playerPoints")).intValue();
            int playerTradedPoints = ((Double)data.get("playerTradedPoints")).intValue();
            float progress = (float)(playerPoints - playerTradedPoints) / 20.0f;
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 32, this.guiTop + 122, 256 * GUI_SCALE, 0 * GUI_SCALE, (int)(148.0f * progress) * GUI_SCALE, 7 * GUI_SCALE, (int)(148.0f * progress), 7, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
            if (progress >= 1.0f) {
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 180, this.guiTop + 115, 258 * GUI_SCALE, 16 * GUI_SCALE, 20 * GUI_SCALE, 20 * GUI_SCALE, 20, 20, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
            }
            ModernGui.drawScaledStringCustomFont(playerPoints + "/" + String.format("%.0f", (Double)data.get("totalPoints")), this.guiLeft + 112, this.guiTop + 136, 7033896, 0.5f, "center", false, "georamaSemiBold", 33);
            boolean bl = isButtonHovered = mouseX >= this.guiLeft + 32 && mouseX <= this.guiLeft + 32 + 168 && mouseY >= this.guiTop + 165 && mouseY <= this.guiTop + 165 + 20;
            if (progress >= 1.0f) {
                ClientEventHandler.STYLE.bindTexture("summer");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 32, this.guiTop + 165, 256 * GUI_SCALE, (isButtonHovered ? 76 : 46) * GUI_SCALE, 168 * GUI_SCALE, 20 * GUI_SCALE, 168, 20, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                if (isButtonHovered) {
                    this.hoveredAction = "get_lootbox";
                }
            }
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"gui.summer.get_lootbox"), this.guiLeft + 117, this.guiTop + 172, progress < 1.0f ? 11970120 : 0x445504, 0.75f, "center", false, "georamaMedium", 24);
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

    public void func_73874_b() {
        if (ClientProxy.commandPlayer != null && ClientProxy.commandPlayer.isPlaying()) {
            ClientProxy.commandPlayer.softClose();
        }
        super.func_73874_b();
    }

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            if (this.hoveredAction.equals("close")) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a(null);
            } else if (this.hoveredAction.equalsIgnoreCase("get_lootbox")) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new SummerTradePacket()));
                Minecraft.func_71410_x().func_71373_a(null);
            }
        }
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    public boolean func_73868_f() {
        return false;
    }
}

