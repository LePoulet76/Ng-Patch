/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.util.ResourceLocation
 */
package net.ilexiconn.nationsgui.forge.client.gui.enterprise;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseGlobalMarketGui;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseGui;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.TabbedEnterpriseGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseFarmDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.ResourceLocation;

public class EnterpriseFarmGUI
extends TabbedEnterpriseGUI {
    public static boolean loaded = false;
    private GuiScrollBarFaction scrollBar;
    public static HashMap<String, Object> enterpriseFarmInfos;
    private GuiButton marketButton;
    public static final ResourceLocation STATS_TEXTURE;

    @Override
    public void func_73866_w_() {
        super.func_73866_w_();
        loaded = false;
        this.scrollBar = new GuiScrollBarFaction(this.guiLeft + 249, this.guiTop + 165, 65);
        this.marketButton = new GuiButton(0, this.guiLeft + 10, this.guiTop + 165, 100, 20, I18n.func_135053_a((String)"enterprise.farm.btnMarket"));
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new EnterpriseFarmDataPacket((String)EnterpriseGui.enterpriseInfos.get("name"))));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        String tooltipToDraw = "";
        ClientEventHandler.STYLE.bindTexture("enterprise_farm");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512.0f, 512.0f, false);
        this.marketButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
        if (loaded) {
            this.drawScaledString(I18n.func_135053_a((String)"enterprise.farm.title"), this.guiLeft + 131, this.guiTop + 15, 0x191919, 1.4f, false, false);
            Minecraft.func_71410_x().func_110434_K().func_110577_a(STATS_TEXTURE);
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 124, this.guiTop + 30, 0, 0, 268, 113, 268.0f, 115.0f, true);
            ClientEventHandler.STYLE.bindTexture("enterprise_farm");
            this.drawScaledString(I18n.func_135053_a((String)"enterprise.farm.label.today"), this.guiLeft + 286, this.guiTop + 149, 0xB4B4B4, 1.0f, false, false);
            this.drawScaledString(String.format("%.0f", (Double)enterpriseFarmInfos.get("today")) + " kg", this.guiLeft + 266, this.guiTop + 168, 0xFFFFFF, 1.0f, false, false);
            this.drawScaledString(I18n.func_135053_a((String)"enterprise.farm.label.total"), this.guiLeft + 286, this.guiTop + 199, 0xB4B4B4, 1.0f, false, false);
            this.drawScaledString(String.format("%.0f", (Double)enterpriseFarmInfos.get("total")) + " kg", this.guiLeft + 266, this.guiTop + 219, 0xFFFFFF, 1.0f, false, false);
            this.drawScaledString(I18n.func_135053_a((String)"enterprise.farm.label.history"), this.guiLeft + 158, this.guiTop + 149, 0xB4B4B4, 1.0f, false, false);
            GUIUtils.startGLScissor(this.guiLeft + 135, this.guiTop + 162, 114, 71);
            for (int i = 0; i < ((ArrayList)enterpriseFarmInfos.get("collected_top")).size(); ++i) {
                int offsetX = this.guiLeft + 135;
                Float offsetY = Float.valueOf((float)(this.guiTop + 162 + i * 20) + this.getSlide());
                ClientEventHandler.STYLE.bindTexture("enterprise_farm");
                ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.intValue(), 135, 162, 114, 20, 512.0f, 512.0f, false);
                String cerealName = I18n.func_135053_a((String)("cereal." + ((String)((ArrayList)enterpriseFarmInfos.get("collected_top")).get(i)).split("##")[0]));
                String value = "\u00a7a" + String.format("%.0f", Double.parseDouble(((String)((ArrayList)enterpriseFarmInfos.get("collected_top")).get(i)).split("##")[1]));
                this.drawScaledString(cerealName, offsetX + 2, offsetY.intValue() + 5, 0xFFFFFF, 1.0f, false, false);
                this.drawScaledString(value, offsetX + 112 - this.field_73886_k.func_78256_a(value), offsetY.intValue() + 5, 0xFFFFFF, 1.0f, false, false);
            }
            GUIUtils.endGLScissor();
            this.scrollBar.draw(mouseX, mouseY);
            if (!tooltipToDraw.isEmpty()) {
                this.drawTooltip(tooltipToDraw, mouseX, mouseY);
            }
        }
    }

    @Override
    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        super.func_73864_a(mouseX, mouseY, mouseButton);
        if (mouseButton == 0 && this.marketButton != null && mouseX >= this.guiLeft + 10 && mouseX <= this.guiLeft + 10 + 100 && mouseY >= this.guiTop + 165 && mouseY <= this.guiTop + 165 + 20) {
            Minecraft.func_71410_x().func_71373_a((GuiScreen)new EnterpriseGlobalMarketGui());
        }
    }

    private float getSlide() {
        return ((ArrayList)enterpriseFarmInfos.get("collected_top")).size() > 3 ? (float)(-(((ArrayList)enterpriseFarmInfos.get("collected_top")).size() - 3) * 20) * this.scrollBar.getSliderValue() : 0.0f;
    }

    public void drawTooltip(String text, int mouseX, int mouseY) {
        int mouseXGui = mouseX - this.guiLeft;
        int mouseYGui = mouseY - this.guiTop;
        this.drawHoveringText(Arrays.asList(text), mouseX, mouseY, this.field_73886_k);
    }

    static {
        STATS_TEXTURE = new ResourceLocation("nationsgui", "tmp/stats_farm");
    }
}

