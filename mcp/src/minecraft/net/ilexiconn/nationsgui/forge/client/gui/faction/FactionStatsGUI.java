/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.util.ChatMessageComponent
 *  net.minecraft.util.ResourceLocation
 */
package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.HashMap;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.TabbedFactionGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionStatsDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.ResourceLocation;

public class FactionStatsGUI
extends TabbedFactionGUI {
    public static boolean loaded = false;
    public static HashMap<TAB, ResourceLocation> TEXTURES = new HashMap();
    public TAB activeTab = TAB.NOTATIONS;

    public FactionStatsGUI() {
        loaded = false;
        for (TAB tab : TAB.values()) {
            TEXTURES.put(tab, new ResourceLocation("nationsgui", "tmp/faction_stats_" + tab.name()));
        }
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionStatsDataPacket((String)FactionGUI.factionInfos.get("name"))));
    }

    @Override
    public void func_73866_w_() {
        super.func_73866_w_();
    }

    @Override
    public void func_73863_a(int mouseX, int mouseY, float partialTick) {
        this.func_73873_v_();
        tooltipToDraw.clear();
        this.hoveredAction = "";
        ClientEventHandler.STYLE.bindTexture("faction_global_2");
        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 30, this.guiTop + 0, 0 * GUI_SCALE, 0 * GUI_SCALE, (this.xSize - 30) * GUI_SCALE, this.ySize * GUI_SCALE, this.xSize - 30, this.ySize, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
        if (loaded) {
            if (FactionGUI.factionInfos.get("banners") != null && ((Map)FactionGUI.factionInfos.get("banners")).containsKey("stats")) {
                ModernGui.bindRemoteTexture((String)((Map)FactionGUI.factionInfos.get("banners")).get("stats"));
            } else {
                ModernGui.bindRemoteTexture("https://static.nationsglory.fr/N33N_N33NN.png");
            }
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 30 + 154, this.guiTop + 0, 0.0f, 0.0f, 279 * GUI_SCALE, 110 * GUI_SCALE, 279, 89, 279 * GUI_SCALE, 110 * GUI_SCALE, false);
            ClientEventHandler.STYLE.bindTexture("faction_global");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 30, this.guiTop + 0, 33 * GUI_SCALE, 280 * GUI_SCALE, 433 * GUI_SCALE, 89 * GUI_SCALE, 433, 89, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
            ClientEventHandler.STYLE.bindTexture("faction_diplomatie");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 292, this.guiTop - 15, 382 * GUI_SCALE, 0 * GUI_SCALE, 130 * GUI_SCALE, 104 * GUI_SCALE, 130, 104, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
            ModernGui.drawScaledStringCustomFont((String)FactionGUI.factionInfos.get("name"), this.guiLeft + 43, this.guiTop + 6, 10395075, 0.5f, "left", false, "georamaMedium", 32);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.stats.title"), this.guiLeft + 43, this.guiTop + 16, 0xFFFFFF, 0.75f, "left", false, "georamaSemiBold", 32);
            ModernGui.drawSectionStringCustomFont(((String)FactionGUI.factionInfos.get("description")).replaceAll("\u00a7[0-9a-z]{1}", ""), this.guiLeft + 43, this.guiTop + 32, 10395075, 0.5f, "left", false, "georamaMedium", 25, 8, 350);
            int index = 0;
            for (TAB tab : TAB.values()) {
                ClientEventHandler.STYLE.bindTexture("faction_stats");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 42 + 66 * index, this.guiTop + 77, 0 * GUI_SCALE, (this.activeTab.equals((Object)tab) ? 0 : 15) * GUI_SCALE, 65 * GUI_SCALE, 12 * GUI_SCALE, 65, 12, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("faction.stats.tab." + tab.name().toLowerCase())), this.guiLeft + 42 + 66 * index + 32, this.guiTop + 80, this.activeTab.equals((Object)tab) ? 0x6E76EE : 0xDADAED, 0.5f, "center", false, "georamaSemiBold", 26);
                if (mouseX >= this.guiLeft + 42 + 66 * index && mouseX <= this.guiLeft + 42 + 66 * index + 65 && mouseY >= this.guiTop + 77 && mouseY <= this.guiTop + 77 + 12) {
                    this.hoveredAction = "switch_tab#" + tab.name();
                }
                ++index;
            }
            ResourceLocation chartTexture = TEXTURES.get((Object)this.activeTab);
            Minecraft.func_71410_x().func_110434_K().func_110577_a(chartTexture);
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 43, this.guiTop + 102, 0, 0, 411, 125, 411.0f, 125.0f, true);
        }
        super.func_73863_a(mouseX, mouseY, partialTick);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
    }

    @Override
    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0 && !this.hoveredAction.isEmpty()) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
            if (this.hoveredAction.equals("edit_photo")) {
                ClientData.lastCaptureScreenshot.put("stats", System.currentTimeMillis());
                Minecraft.func_71410_x().func_71373_a(null);
                Minecraft.func_71410_x().field_71439_g.func_70006_a(ChatMessageComponent.func_111066_d((String)I18n.func_135053_a((String)"faction.take_picture")));
            } else if (this.hoveredAction.contains("switch_tab")) {
                this.activeTab = TAB.valueOf(this.hoveredAction.replace("switch_tab#", ""));
            }
            this.hoveredAction = "";
        }
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    public static enum TAB {
        NOTATIONS,
        POPULATION,
        TERRITORY,
        WARS,
        ECONOMY;

    }
}

