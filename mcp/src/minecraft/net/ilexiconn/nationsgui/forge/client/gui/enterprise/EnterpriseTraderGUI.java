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
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseTraderDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.ResourceLocation;

public class EnterpriseTraderGUI
extends TabbedEnterpriseGUI {
    public static boolean loaded = false;
    public static HashMap<String, Object> enterpriseTraderInfos;
    public static final ResourceLocation STATS_TEXTURE;

    @Override
    public void func_73866_w_() {
        super.func_73866_w_();
        loaded = false;
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new EnterpriseTraderDataPacket((String)EnterpriseGui.enterpriseInfos.get("name"))));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        String tooltipToDraw = "";
        ClientEventHandler.STYLE.bindTexture("enterprise_trader");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512.0f, 512.0f, false);
        if (loaded) {
            this.drawScaledString(I18n.func_135053_a((String)"enterprise.trader.title"), this.guiLeft + 131, this.guiTop + 15, 0x191919, 1.4f, false, false);
            Minecraft.func_71410_x().func_110434_K().func_110577_a(STATS_TEXTURE);
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 134, this.guiTop + 38, 0, 0, 248, 95, 248.0f, 95.0f, true);
            ClientEventHandler.STYLE.bindTexture("enterprise_trader");
            this.drawScaledString(I18n.func_135053_a((String)"enterprise.trader.label.my_investment"), this.guiLeft + 160, this.guiTop + 148, 0xB4B4B4, 1.0f, false, false);
            this.drawScaledString(String.format("%.2f", (Double)enterpriseTraderInfos.get("my_investment")) + "\u00a7a$", this.guiLeft + 138, this.guiTop + 168, 0xFFFFFF, 1.0f, false, false);
            this.drawScaledString(I18n.func_135053_a((String)"enterprise.trader.label.investors"), this.guiLeft + 288, this.guiTop + 148, 0xB4B4B4, 1.0f, false, false);
            this.drawScaledString(String.format("%.0f", (Double)enterpriseTraderInfos.get("investors")), this.guiLeft + 266, this.guiTop + 168, 0xFFFFFF, 1.0f, false, false);
            this.drawScaledString(I18n.func_135053_a((String)"enterprise.trader.label.sum_investment"), this.guiLeft + 160, this.guiTop + 199, 0xB4B4B4, 1.0f, false, false);
            this.drawScaledString(String.format("%.2f", (Double)enterpriseTraderInfos.get("sum_investment")) + "\u00a7a$", this.guiLeft + 138, this.guiTop + 219, 0xFFFFFF, 1.0f, false, false);
            this.drawScaledString(I18n.func_135053_a((String)"enterprise.trader.label.total_generated"), this.guiLeft + 288, this.guiTop + 199, 0xB4B4B4, 1.0f, false, false);
            this.drawScaledString(String.format("%.2f", (Double)enterpriseTraderInfos.get("total_generated")) + "\u00a7a$", this.guiLeft + 266, this.guiTop + 219, 0xFFFFFF, 1.0f, false, false);
            if (enterpriseTraderInfos.containsKey("today_percent")) {
                String todayPercent = "\u00a7a" + String.format("%.2f", (Double)enterpriseTraderInfos.get("today_percent")) + "%";
                if ((Double)enterpriseTraderInfos.get("today_percent") < 0.0) {
                    todayPercent = "\u00a7c" + String.format("%.2f", (Double)enterpriseTraderInfos.get("today_percent")) + "%";
                }
                this.drawScaledString(todayPercent, this.guiLeft + 51, this.guiTop + 172, 0xFFFFFF, 1.0f, true, false);
                if (mouseX > this.guiLeft + 10 && mouseX < this.guiLeft + 110 && mouseY > this.guiTop + 165 && mouseY < this.guiTop + 185) {
                    tooltipToDraw = I18n.func_135053_a((String)"enterprise.trader.tooltip.today_percent");
                }
            }
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
        STATS_TEXTURE = new ResourceLocation("nationsgui", "tmp/stats");
    }
}

