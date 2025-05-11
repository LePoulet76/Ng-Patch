/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.util.ResourceLocation
 */
package net.ilexiconn.nationsgui.forge.client.gui.enterprise;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.Arrays;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseGui;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.TabbedEnterpriseGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseCasinoDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.ResourceLocation;

public class EnterpriseCasinoGUI
extends TabbedEnterpriseGUI {
    public static boolean loaded = false;
    public static HashMap<String, Object> enterpriseCasinoInfos;
    public static final ResourceLocation STATS_TEXTURE;

    @Override
    public void func_73866_w_() {
        super.func_73866_w_();
        loaded = false;
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new EnterpriseCasinoDataPacket((String)EnterpriseGui.enterpriseInfos.get("name"))));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        String tooltipToDraw = "";
        ClientEventHandler.STYLE.bindTexture("enterprise_casino");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512.0f, 512.0f, false);
        if (loaded) {
            this.drawScaledString(I18n.func_135053_a((String)"enterprise.casino.title"), this.guiLeft + 131, this.guiTop + 15, 0x191919, 1.4f, false, false);
            Minecraft.func_71410_x().func_110434_K().func_110577_a(STATS_TEXTURE);
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 134, this.guiTop + 38, 0, 0, 248, 95, 248.0f, 95.0f, true);
            ClientEventHandler.STYLE.bindTexture("enterprise_casino");
            this.drawScaledString(I18n.func_135053_a((String)"enterprise.casino.label.benef_moy"), this.guiLeft + 160, this.guiTop + 148, 0xB4B4B4, 1.0f, false, false);
            this.drawScaledString(String.format("%.2f", (Double)enterpriseCasinoInfos.get("benefMoy")) + "\u00a7a$", this.guiLeft + 138, this.guiTop + 168, 0xFFFFFF, 1.0f, false, false);
            this.drawScaledString(I18n.func_135053_a((String)"enterprise.casino.label.win_percent"), this.guiLeft + 288, this.guiTop + 148, 0xB4B4B4, 1.0f, false, false);
            this.drawScaledString(String.format("%.0f", (Double)enterpriseCasinoInfos.get("winPercent")) + "%", this.guiLeft + 266, this.guiTop + 168, 0xFFFFFF, 1.0f, false, false);
            this.drawScaledString(I18n.func_135053_a((String)"enterprise.casino.label.total_play"), this.guiLeft + 160, this.guiTop + 199, 0xB4B4B4, 1.0f, false, false);
            this.drawScaledString(String.format("%.2f", (Double)enterpriseCasinoInfos.get("totalPlay")) + "\u00a7a$", this.guiLeft + 138, this.guiTop + 219, 0xFFFFFF, 1.0f, false, false);
            this.drawScaledString(I18n.func_135053_a((String)"enterprise.casino.label.total_reward"), this.guiLeft + 288, this.guiTop + 199, 0xB4B4B4, 1.0f, false, false);
            this.drawScaledString(String.format("%.2f", (Double)enterpriseCasinoInfos.get("totalWin")) + "\u00a7a$", this.guiLeft + 266, this.guiTop + 219, 0xFFFFFF, 1.0f, false, false);
            if (!tooltipToDraw.isEmpty()) {
                this.drawTooltip(tooltipToDraw, mouseX, mouseY);
            }
        }
    }

    public void drawTooltip(String text, int mouseX, int mouseY) {
        int mouseXGui = mouseX - this.guiLeft;
        int mouseYGui = mouseY - this.guiTop;
        this.drawHoveringText(Arrays.asList(text), mouseX, mouseY, this.field_73886_k);
    }

    private void drawPriceChart(int x, int y, int width, int height) {
        ScaledResolution scaledresolution = new ScaledResolution(this.field_73882_e.field_71474_y, this.field_73882_e.field_71443_c, this.field_73882_e.field_71440_d);
        ModernGui.drawModalRectWithCustomSizedTexture(x *= scaledresolution.func_78325_e(), y *= scaledresolution.func_78325_e(), 0, 0, width *= scaledresolution.func_78325_e(), height *= scaledresolution.func_78325_e(), width, height, true);
    }

    static {
        STATS_TEXTURE = new ResourceLocation("nationsgui", "tmp/stats_casino");
    }
}

