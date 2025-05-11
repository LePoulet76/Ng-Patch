/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.AbstractClientPlayer
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiTextField
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import net.halalaboos.cfont.CFontRenderer;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarGeneric;
import net.ilexiconn.nationsgui.forge.client.gui.faction.BankGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.TabbedFactionListGUI;
import net.ilexiconn.nationsgui.forge.client.gui.main.component.CustomInputFieldGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionAskToJoinPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionListDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionListDetailsPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionMainDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class FactionListGUI
extends TabbedFactionListGUI {
    public static HashMap<String, HashMap<String, String>> countriesExtraData = new HashMap();
    public static ArrayList<HashMap<String, String>> countriesData = new ArrayList();
    public static HashMap<String, String> selectedCountry = new HashMap();
    public static HashMap<String, String> hoveredCountry = new HashMap();
    public static boolean loaded = false;
    public static Long lastClick = 0L;
    private GuiScrollBarGeneric scrollBar;
    private GuiTextField searchInput;
    public int countCountriesBySearch = -1;
    public static Long lastExtraDataPacket = 0L;
    private CFontRenderer cFontSemiBold28;
    private TAB activeTab = TAB.LIST;
    public boolean foundReferent = false;
    public static HashMap<String, Integer> iconsXPerRelation = new HashMap<String, Integer>(){
        {
            this.put("neutral", 392);
            this.put("enemy", 482);
            this.put("ally", 452);
            this.put("colony", 422);
        }
    };
    public static HashMap<String, Integer> iconsYByName = new HashMap<String, Integer>(){
        {
            this.put("stats", 193);
            this.put("bank", 222);
            this.put("people", 251);
            this.put("timer", 280);
            this.put("farmer", 309);
            this.put("lumberjack", 338);
            this.put("builder", 367);
            this.put("hunter", 396);
            this.put("engineer", 425);
            this.put("miner", 454);
            this.put("relation", 483);
        }
    };

    public FactionListGUI() {
        countriesData.clear();
        countriesExtraData.clear();
        loaded = false;
        FactionListGUI.initTabs();
    }

    @Override
    public void func_73866_w_() {
        super.func_73866_w_();
        if (countriesData.isEmpty()) {
            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionListDataPacket()));
        }
        this.scrollBar = new GuiScrollBarGeneric(this.guiLeft + 297, this.guiTop + 70, 144, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
        this.scrollBar.setScrollIncrement(0.005f);
        this.searchInput = new CustomInputFieldGUI(this.guiLeft + 343, this.guiTop + 54, 94, 12, "georamaSemiBold", 28);
        this.searchInput.func_73786_a(false);
        this.searchInput.func_73804_f(15);
        this.countCountriesBySearch = -1;
        this.cFontSemiBold28 = ModernGui.getCustomFont("georamaSemiBold", 28);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
    }

    @Override
    public void func_73863_a(int mouseX, int mouseY, float partialTick) {
        ModernGui.drawDefaultBackground(this, this.field_73880_f, this.field_73881_g, mouseX, mouseY);
        tooltipToDraw.clear();
        this.hoveredAction = "";
        hoveredCountry = new HashMap();
        ClientEventHandler.STYLE.bindTexture("faction_global_2");
        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 30, this.guiTop + 0, 0 * GUI_SCALE, 0 * GUI_SCALE, (this.xSize - 30) * GUI_SCALE, this.ySize * GUI_SCALE, this.xSize - 30, this.ySize, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.list.manage_countries"), this.guiLeft + 43, this.guiTop + 6, 10395075, 0.5f, "left", false, "georamaMedium", 32);
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.list.list_countries"), this.guiLeft + 43, this.guiTop + 16, 0xFFFFFF, 0.75f, "left", false, "georamaSemiBold", 32);
        ClientEventHandler.STYLE.bindTexture("faction_list");
        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 324, this.guiTop + 48, 276 * GUI_SCALE, 157 * GUI_SCALE, 129 * GUI_SCALE, 27 * GUI_SCALE, 129, 27, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
        this.searchInput.func_73795_f();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        int index = 0;
        for (TAB tab : TAB.values()) {
            ClientEventHandler.STYLE.bindTexture("faction_list");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 42 + 55 * index, this.guiTop + 36, 409 * GUI_SCALE, (this.activeTab.equals((Object)tab) ? 103 : 89) * GUI_SCALE, 52 * GUI_SCALE, 12 * GUI_SCALE, 52, 12, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("faction.list.tab." + tab.name().toLowerCase())), this.guiLeft + 42 + 55 * index + 26, this.guiTop + 39, this.activeTab.equals((Object)tab) ? 0x6E76EE : 0xDADAED, 0.5f, "center", false, "georamaSemiBold", 26);
            if (mouseX >= this.guiLeft + 42 + 55 * index && mouseX <= this.guiLeft + 42 + 55 * index + 52 && mouseY >= this.guiTop + 36 && mouseY <= this.guiTop + 36 + 12) {
                this.hoveredAction = "switch_tab#" + tab.name();
            }
            ++index;
        }
        ClientEventHandler.STYLE.bindTexture("faction_list");
        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 42, this.guiTop + 48, 0 * GUI_SCALE, 191 * GUI_SCALE, 266 * GUI_SCALE, 173 * GUI_SCALE, 266, 173, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
        ModernGui.drawScaledStringCustomFont("\u00a7o" + I18n.func_135053_a((String)"faction.list.label.countries"), this.guiLeft + 50, this.guiTop + 57, 10395075, 0.5f, "left", false, "georamaMedium", 28);
        ModernGui.drawScaledStringCustomFont("\u00a7o" + I18n.func_135053_a((String)"faction.list.label.rank"), this.guiLeft + 147, this.guiTop + 57, 10395075, 0.5f, "left", false, "georamaMedium", 28);
        ModernGui.drawScaledStringCustomFont("\u00a7o" + I18n.func_135053_a((String)"faction.list.label.level"), this.guiLeft + 179, this.guiTop + 57, 10395075, 0.5f, "left", false, "georamaMedium", 28);
        ModernGui.drawScaledStringCustomFont("\u00a7o" + I18n.func_135053_a((String)("faction.list.label.column4." + this.activeTab.name().toLowerCase())), this.guiLeft + 222, this.guiTop + 57, 10395075, 0.5f, "left", false, "georamaMedium", 28);
        this.foundReferent = false;
        boolean addSeparatorForNonReferentCountries = false;
        boolean addSeparatorForReferentCountries = false;
        GUIUtils.startGLScissor(this.guiLeft + 50, this.guiTop + 70, 247, 151);
        if (countriesData.size() > 0) {
            index = 0;
            for (HashMap<String, String> country : countriesData) {
                if (!this.searchInput.func_73781_b().isEmpty() && !country.get("name").toLowerCase().contains(this.searchInput.func_73781_b().toLowerCase()) || this.activeTab.equals((Object)TAB.BUY) && country.get("price").equals("-1") || this.activeTab.equals((Object)TAB.JOIN) && (!country.get("recruitment").equals("true") || country.get("recruter_online").equals("true")) || this.activeTab.equals((Object)TAB.ACTIONS) && country.get("nb_actions").equals("0")) continue;
                int offsetX = this.guiLeft + 50;
                Float offsetY = Float.valueOf((float)(this.guiTop + 70 + index * 16) + this.getSlideCountries());
                if (this.activeTab.equals((Object)TAB.JOIN) || this.activeTab.equals((Object)TAB.LIST)) {
                    if (country.get("isReferent").equals("true")) {
                        this.foundReferent = true;
                        if (!addSeparatorForReferentCountries) {
                            addSeparatorForReferentCountries = true;
                            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.list.label.label.countries_referent"), offsetX, offsetY.intValue() + 2, 16109642, 0.5f, "left", false, "georamaSemiBold", 28);
                            ClientEventHandler.STYLE.bindTexture("faction_global");
                            ModernGui.drawScaledCustomSizeModalRect(offsetX + (int)this.cFontSemiBold28.getStringWidth(I18n.func_135053_a((String)"faction.list.label.label.countries_referent")) / 2 + 2, offsetY.intValue() + 2, 86 * GUI_SCALE, 5 * GUI_SCALE, 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                            if (mouseX >= offsetX + (int)this.cFontSemiBold28.getStringWidth(I18n.func_135053_a((String)"faction.list.label.label.countries_referent")) / 2 + 2 && mouseX <= offsetX + (int)this.cFontSemiBold28.getStringWidth(I18n.func_135053_a((String)"faction.list.label.label.countries_referent")) / 2 + 2 + 8 && mouseY >= offsetY.intValue() + 2 && mouseY <= offsetY.intValue() + 2 + 8) {
                                tooltipToDraw.addAll(Arrays.asList(I18n.func_135053_a((String)"faction.common.badge.referent").split("##")));
                            }
                            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                        }
                        offsetY = Float.valueOf(offsetY.floatValue() + 16.0f);
                    }
                    if (this.foundReferent && !country.get("isReferent").equals("true")) {
                        offsetY = Float.valueOf(offsetY.floatValue() + 20.0f);
                        if (!addSeparatorForNonReferentCountries) {
                            addSeparatorForNonReferentCountries = true;
                            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.list.label.label.countries_classic"), offsetX, offsetY.intValue() + 2, 10395075, 0.5f, "left", false, "georamaSemiBold", 28);
                            Gui.func_73734_a((int)(offsetX + (int)this.cFontSemiBold28.getStringWidth(I18n.func_135053_a((String)"faction.list.label.label.countries_classic")) / 2 + 5), (int)(offsetY.intValue() + 7), (int)(offsetX + 260 - ((int)this.cFontSemiBold28.getStringWidth(I18n.func_135053_a((String)"faction.list.label.label.countries_classic")) / 2 + 5)), (int)(offsetY.intValue() + 8), (int)-6382141);
                            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                        }
                        offsetY = Float.valueOf(offsetY.floatValue() + 16.0f);
                    }
                }
                if (offsetY.intValue() >= this.guiTop + 60 && offsetY.intValue() <= this.guiTop + 70 + 160) {
                    ClientProxy.loadCountryFlag(country.get("name"));
                    if (ClientProxy.flagsTexture.containsKey(country.get("name"))) {
                        GL11.glBindTexture((int)3553, (int)ClientProxy.flagsTexture.get(country.get("name")).func_110552_b());
                        ModernGui.drawScaledCustomSizeModalRect(offsetX + 0, offsetY.intValue() + 0, 0.0f, 0.0f, 156, 78, 17, 10, 156.0f, 78.0f, false);
                    }
                }
                if (country.get("isReferent").equals("true")) {
                    ClientEventHandler.STYLE.bindTexture("faction_list");
                    ModernGui.drawScaledCustomSizeModalRect(offsetX + 21, offsetY.intValue() + 1, 463 * GUI_SCALE, 15 * GUI_SCALE, 9 * GUI_SCALE, 9 * GUI_SCALE, 9, 9, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    ModernGui.drawScaledStringCustomFont(country.get("name"), offsetX + 31, offsetY.intValue() + 2, 16109642, 0.5f, "left", false, "georamaSemiBold", 28);
                    if (mouseX >= offsetX + 21 && mouseX <= offsetX + 21 + 13 && mouseY >= offsetY.intValue() + 1 && mouseY <= offsetY.intValue() + 1 + 13) {
                        tooltipToDraw.addAll(Arrays.asList(I18n.func_135053_a((String)"faction.common.badge.referent").split("##")));
                    }
                } else if (country.containsKey("isInPillage") && country.get("isInPillage").equals("true")) {
                    ClientEventHandler.STYLE.bindTexture("faction_list");
                    ModernGui.drawScaledCustomSizeModalRect(offsetX + 21, offsetY.intValue() + 1, 463 * GUI_SCALE, 46 * GUI_SCALE, 9 * GUI_SCALE, 9 * GUI_SCALE, 9, 9, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    ModernGui.drawScaledStringCustomFont(country.get("name"), offsetX + 31, offsetY.intValue() + 2, 14900238, 0.5f, "left", false, "georamaSemiBold", 28);
                    if (mouseX >= offsetX + 21 && mouseX <= offsetX + 21 + 13 && mouseY >= offsetY.intValue() + 1 && mouseY <= offsetY.intValue() + 1 + 13) {
                        tooltipToDraw.addAll(Arrays.asList(I18n.func_135053_a((String)"faction.common.badge.pillage").split("##")));
                    }
                } else {
                    ModernGui.drawScaledStringCustomFont(country.get("name"), offsetX + 21, offsetY.intValue() + 2, country.get("relation_my_country").equals("neutral") ? (country.get("name").equals(selectedCountry.get("name")) ? 0x6E76EE : 16514302) : FactionGUI.textColor.get(country.get("relation_my_country")), 0.5f, "left", false, "georamaSemiBold", 28);
                }
                ModernGui.drawScaledStringCustomFont(country.get("position"), offsetX + 98, offsetY.intValue() + 2, selectedCountry.containsKey("name") && country.get("name").equals(selectedCountry.get("name")) ? FactionGUI.textColor.get(country.get("relation_my_country")) : 16514302, 0.5f, "left", false, "georamaSemiBold", 28);
                ModernGui.drawScaledStringCustomFont(country.get("level"), offsetX + 130, offsetY.intValue() + 2, selectedCountry.containsKey("name") && country.get("name").equals(selectedCountry.get("name")) ? FactionGUI.textColor.get(country.get("relation_my_country")) : 16514302, 0.5f, "left", false, "georamaSemiBold", 28);
                String data = country.get("players");
                if (this.activeTab.equals((Object)TAB.BUY)) {
                    data = country.get("price") + "$";
                } else if (this.activeTab.equals((Object)TAB.ACTIONS)) {
                    data = 10000 + Integer.parseInt(country.get("level")) * Integer.parseInt(country.get("level")) * 100 + "$";
                }
                ModernGui.drawScaledStringCustomFont(data, offsetX + 173, offsetY.intValue() + 2, selectedCountry.containsKey("name") && country.get("name").equals(selectedCountry.get("name")) ? FactionGUI.textColor.get(country.get("relation_my_country")) : 16514302, 0.5f, "left", false, "georamaSemiBold", 28);
                if (mouseX >= offsetX && mouseX <= offsetX + 247 && (float)mouseY >= offsetY.floatValue() && (float)mouseY <= offsetY.floatValue() + 16.0f) {
                    hoveredCountry = country;
                }
                ++index;
            }
            if (this.countCountriesBySearch == -1) {
                this.countCountriesBySearch = index + 1;
            }
        }
        GUIUtils.endGLScissor();
        if (mouseX >= this.guiLeft + 42 && mouseX <= this.guiLeft + 42 + 266 && mouseY >= this.guiTop + 48 && mouseY <= this.guiTop + 48 + 173) {
            this.scrollBar.draw(mouseX, mouseY);
        }
        ClientEventHandler.STYLE.bindTexture("faction_list");
        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 324, this.guiTop + 81, 137 * GUI_SCALE, 372 * GUI_SCALE, 129 * GUI_SCALE, 140 * GUI_SCALE, 129, 140, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
        if (selectedCountry != null && !selectedCountry.isEmpty()) {
            if (!countriesExtraData.containsKey(selectedCountry.get("name"))) {
                if (System.currentTimeMillis() - lastExtraDataPacket > 500L) {
                    lastExtraDataPacket = System.currentTimeMillis();
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionListDetailsPacket(selectedCountry.get("name"))));
                }
            } else {
                HashMap<String, String> countryExtraData = countriesExtraData.get(selectedCountry.get("name"));
                if (selectedCountry.get("isReferent").equals("true")) {
                    ClientEventHandler.STYLE.bindTexture("faction_list");
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 444, this.guiTop + 77, 461 * GUI_SCALE, 0 * GUI_SCALE, 13 * GUI_SCALE, 13 * GUI_SCALE, 13, 13, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    if (mouseX >= this.guiLeft + 444 && mouseX <= this.guiLeft + 444 + 13 && mouseY >= this.guiTop + 77 && mouseY <= this.guiTop + 77 + 13) {
                        tooltipToDraw.addAll(Arrays.asList(I18n.func_135053_a((String)"faction.common.badge.referent").split("##")));
                    }
                }
                ClientProxy.loadCountryFlag(selectedCountry.get("name"));
                if (ClientProxy.flagsTexture.containsKey(selectedCountry.get("name"))) {
                    GL11.glBindTexture((int)3553, (int)ClientProxy.flagsTexture.get(selectedCountry.get("name")).func_110552_b());
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 328, this.guiTop + 85, 0.0f, 0.0f, 156, 78, 43, 25, 156.0f, 78.0f, false);
                }
                ModernGui.drawScaledStringCustomFont(selectedCountry.get("name"), this.guiLeft + 378, this.guiTop + 88, FactionGUI.textColor.get(selectedCountry.get("relation_my_country")), 0.75f, "left", false, "georamaSemiBold", 28);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.list.label.since").replaceAll("#day#", countryExtraData.get("age")), this.guiLeft + 378, this.guiTop + 100, 10395075, 0.5f, "left", false, "georamaMedium", 26);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.list.label.country_leader"), this.guiLeft + 353, this.guiTop + 126, FactionGUI.textColor.get(selectedCountry.get("relation_my_country")), 0.5f, "left", false, "georamaMedium", 22);
                ModernGui.drawScaledStringCustomFont(countryExtraData.get("leader"), this.guiLeft + 353, this.guiTop + 132, 16514302, 0.5f, "left", false, "georamaSemiBold", 28);
                if (countryExtraData.get("leader_online").equals("true")) {
                    ModernGui.drawScaledStringCustomFont("(" + I18n.func_135053_a((String)"faction.list.label.online") + ")", this.guiLeft + 355 + (int)this.cFontSemiBold28.getStringWidth(countryExtraData.get("leader")) / 2, this.guiTop + 133, 10395075, 0.5f, "left", false, "georamaMedium", 24);
                }
                if (!ClientProxy.cacheHeadPlayer.containsKey(countryExtraData.get("leader"))) {
                    try {
                        ResourceLocation resourceLocation = AbstractClientPlayer.field_110314_b;
                        resourceLocation = AbstractClientPlayer.func_110311_f((String)countryExtraData.get("leader"));
                        AbstractClientPlayer.func_110304_a((ResourceLocation)resourceLocation, (String)countryExtraData.get("leader"));
                        ClientProxy.cacheHeadPlayer.put(countryExtraData.get("leader"), resourceLocation);
                    }
                    catch (Exception resourceLocation) {}
                } else {
                    Minecraft.func_71410_x().field_71446_o.func_110577_a(ClientProxy.cacheHeadPlayer.get(countryExtraData.get("leader")));
                    this.field_73882_e.func_110434_K().func_110577_a(ClientProxy.cacheHeadPlayer.get(countryExtraData.get("leader")));
                    GUIUtils.drawScaledCustomSizeModalRect(this.guiLeft + 346, this.guiTop + 139, 8.0f, 16.0f, 8, -8, -13, -13, 64.0f, 64.0f);
                    ClientEventHandler.STYLE.bindTexture("faction_list");
                    if (countryExtraData.get("leader_online").equals("true")) {
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 343, this.guiTop + 124, 481 * GUI_SCALE, 2 * GUI_SCALE, 6 * GUI_SCALE, 6 * GUI_SCALE, 6, 6, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    } else {
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 343, this.guiTop + 124, 491 * GUI_SCALE, 2 * GUI_SCALE, 6 * GUI_SCALE, 6 * GUI_SCALE, 6, 6, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    }
                }
                if (this.activeTab.equals((Object)TAB.LIST)) {
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.list.label.relation_my_country"), this.guiLeft + 353, this.guiTop + 151, FactionGUI.textColor.get(selectedCountry.get("relation_my_country")), 0.5f, "left", false, "georamaMedium", 22);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("faction.common." + selectedCountry.get("relation_my_country") + ".nocolor")), this.guiLeft + 353, this.guiTop + 157, 16514302, 0.5f, "left", false, "georamaSemiBold", 28);
                    ClientEventHandler.STYLE.bindTexture("faction_list");
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 325, this.guiTop + 144, iconsXPerRelation.get(selectedCountry.get("relation_my_country")) * GUI_SCALE, iconsYByName.get("relation") * GUI_SCALE, 30 * GUI_SCALE, 29 * GUI_SCALE, 30, 29, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.list.label.recruitment"), this.guiLeft + 353, this.guiTop + 176, FactionGUI.textColor.get(selectedCountry.get("relation_my_country")), 0.5f, "left", false, "georamaMedium", 22);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("faction.list.label.recruitment." + selectedCountry.get("recruitment"))), this.guiLeft + 353, this.guiTop + 182, 16514302, 0.5f, "left", false, "georamaSemiBold", 28);
                    ClientEventHandler.STYLE.bindTexture("faction_list");
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 325, this.guiTop + 169, iconsXPerRelation.get(selectedCountry.get("relation_my_country")) * GUI_SCALE, iconsYByName.get("people") * GUI_SCALE, 30 * GUI_SCALE, 29 * GUI_SCALE, 30, 29, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                } else if (this.activeTab.equals((Object)TAB.JOIN)) {
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.list.label.recruitment"), this.guiLeft + 353, this.guiTop + 151, FactionGUI.textColor.get(selectedCountry.get("relation_my_country")), 0.5f, "left", false, "georamaMedium", 22);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("faction.list.label.recruitment." + selectedCountry.get("recruitment"))), this.guiLeft + 353, this.guiTop + 157, 16514302, 0.5f, "left", false, "georamaSemiBold", 28);
                    ClientEventHandler.STYLE.bindTexture("faction_list");
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 325, this.guiTop + 144, iconsXPerRelation.get(selectedCountry.get("relation_my_country")) * GUI_SCALE, iconsYByName.get("people") * GUI_SCALE, 30 * GUI_SCALE, 29 * GUI_SCALE, 30, 29, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.list.label.recruitmentThisWeek"), this.guiLeft + 353, this.guiTop + 176, FactionGUI.textColor.get(selectedCountry.get("relation_my_country")), 0.5f, "left", false, "georamaMedium", 22);
                    ModernGui.drawScaledStringCustomFont(countryExtraData.get("recruitmentThisWeek"), this.guiLeft + 353, this.guiTop + 182, 16514302, 0.5f, "left", false, "georamaSemiBold", 28);
                    ClientEventHandler.STYLE.bindTexture("faction_list");
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 325, this.guiTop + 169, iconsXPerRelation.get(selectedCountry.get("relation_my_country")) * GUI_SCALE, iconsYByName.get("stats") * GUI_SCALE, 30 * GUI_SCALE, 29 * GUI_SCALE, 30, 29, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                } else if (this.activeTab.equals((Object)TAB.BUY)) {
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.list.label.members_online"), this.guiLeft + 353, this.guiTop + 151, FactionGUI.textColor.get(selectedCountry.get("relation_my_country")), 0.5f, "left", false, "georamaMedium", 22);
                    ModernGui.drawScaledStringCustomFont(selectedCountry.get("players"), this.guiLeft + 353, this.guiTop + 157, 16514302, 0.5f, "left", false, "georamaSemiBold", 28);
                    ClientEventHandler.STYLE.bindTexture("faction_list");
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 325, this.guiTop + 144, iconsXPerRelation.get(selectedCountry.get("relation_my_country")) * GUI_SCALE, iconsYByName.get("people") * GUI_SCALE, 30 * GUI_SCALE, 29 * GUI_SCALE, 30, 29, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.list.label.sell_country"), this.guiLeft + 353, this.guiTop + 176, FactionGUI.textColor.get(selectedCountry.get("relation_my_country")), 0.5f, "left", false, "georamaMedium", 22);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.list.label.price"), this.guiLeft + 353, this.guiTop + 182, 0xDADAED, 0.5f, "left", false, "georamaSemiBold", 28);
                    ModernGui.drawScaledStringCustomFont(selectedCountry.get("price") + "$", this.guiLeft + 355 + (int)this.cFontSemiBold28.getStringWidth(I18n.func_135053_a((String)"faction.list.label.price")) / 2, this.guiTop + 182, 14803951, 0.5f, "left", false, "georamaSemiBold", 28);
                    ClientEventHandler.STYLE.bindTexture("faction_list");
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 325, this.guiTop + 169, iconsXPerRelation.get(selectedCountry.get("relation_my_country")) * GUI_SCALE, iconsYByName.get("bank") * GUI_SCALE, 30 * GUI_SCALE, 29 * GUI_SCALE, 30, 29, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                } else if (this.activeTab.equals((Object)TAB.ACTIONS)) {
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.list.label.members_online"), this.guiLeft + 353, this.guiTop + 151, FactionGUI.textColor.get(selectedCountry.get("relation_my_country")), 0.5f, "left", false, "georamaMedium", 22);
                    ModernGui.drawScaledStringCustomFont(selectedCountry.get("players"), this.guiLeft + 353, this.guiTop + 157, 16514302, 0.5f, "left", false, "georamaSemiBold", 28);
                    ClientEventHandler.STYLE.bindTexture("faction_list");
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 325, this.guiTop + 144, iconsXPerRelation.get(selectedCountry.get("relation_my_country")) * GUI_SCALE, iconsYByName.get("people") * GUI_SCALE, 30 * GUI_SCALE, 29 * GUI_SCALE, 30, 29, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.list.label.sell_actions"), this.guiLeft + 353, this.guiTop + 176, FactionGUI.textColor.get(selectedCountry.get("relation_my_country")), 0.5f, "left", false, "georamaMedium", 22);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.list.label.price"), this.guiLeft + 353, this.guiTop + 182, 0xDADAED, 0.5f, "left", false, "georamaSemiBold", 28);
                    ModernGui.drawScaledStringCustomFont(10000 + Integer.parseInt(selectedCountry.get("level")) * Integer.parseInt(selectedCountry.get("level")) * 100 + "$", this.guiLeft + 355 + (int)this.cFontSemiBold28.getStringWidth(I18n.func_135053_a((String)"faction.list.label.price")) / 2, this.guiTop + 182, 14803951, 0.5f, "left", false, "georamaSemiBold", 28);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.list.label.number"), this.guiLeft + 353, this.guiTop + 189, 0xDADAED, 0.5f, "left", false, "georamaSemiBold", 28);
                    ModernGui.drawScaledStringCustomFont(selectedCountry.get("nb_actions"), this.guiLeft + 355 + (int)this.cFontSemiBold28.getStringWidth(I18n.func_135053_a((String)"faction.list.label.number")) / 2, this.guiTop + 189, 14803951, 0.5f, "left", false, "georamaSemiBold", 28);
                    ClientEventHandler.STYLE.bindTexture("faction_list");
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 325, this.guiTop + 169, iconsXPerRelation.get(selectedCountry.get("relation_my_country")) * GUI_SCALE, iconsYByName.get("bank") * GUI_SCALE, 30 * GUI_SCALE, 29 * GUI_SCALE, 30, 29, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                }
                ClientEventHandler.STYLE.bindTexture("faction_list");
                String action = "see_country";
                if (this.activeTab.equals((Object)TAB.LIST) || countryExtraData.get("playerFaction").equals(selectedCountry.get("name"))) {
                    action = "see_country";
                } else if (this.activeTab.equals((Object)TAB.JOIN) && !countryExtraData.get("playerFaction").isEmpty()) {
                    action = "join_country";
                } else if (this.activeTab.equals((Object)TAB.BUY)) {
                    action = "buy_country";
                } else if (this.activeTab.equals((Object)TAB.ACTIONS)) {
                    action = "see_actions";
                }
                if (mouseX >= this.guiLeft + 337 && mouseX <= this.guiLeft + 337 + 103 && mouseY >= this.guiTop + 204 && mouseY <= this.guiTop + 204 + 13) {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 337, this.guiTop + 204, 276 * GUI_SCALE, 222 * GUI_SCALE, 103 * GUI_SCALE, 13 * GUI_SCALE, 103, 13, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    this.hoveredAction = action;
                } else {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 337, this.guiTop + 204, 276 * GUI_SCALE, 204 * GUI_SCALE, 103 * GUI_SCALE, 13 * GUI_SCALE, 103, 13, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                }
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("faction.list.label." + action)), this.guiLeft + 337 + 52, this.guiTop + 207, 2234425, 0.5f, "center", false, "georamaSemiBold", 28);
            }
        }
        super.func_73863_a(mouseX, mouseY, partialTick);
    }

    private float getSlideCountries() {
        return this.countCountriesBySearch > 10 ? (float)(-((this.countCountriesBySearch - 10) * 16 + (this.foundReferent ? 36 : 0))) * this.scrollBar.getSliderValue() : 0.0f;
    }

    public void func_73876_c() {
        this.searchInput.func_73780_a();
    }

    protected void func_73869_a(char typedChar, int keyCode) {
        this.searchInput.func_73802_a(typedChar, keyCode);
        this.countCountriesBySearch = -1;
        super.func_73869_a(typedChar, keyCode);
    }

    @Override
    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            if (!this.hoveredAction.isEmpty()) {
                if ((this.hoveredAction.equals("see_country") || this.hoveredAction.equals("buy_country")) && selectedCountry != null && !selectedCountry.isEmpty()) {
                    Minecraft.func_71410_x().func_71373_a((GuiScreen)new FactionGUI(selectedCountry.get("name")));
                } else if (this.hoveredAction.equals("see_actions") && selectedCountry != null && !selectedCountry.isEmpty()) {
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionMainDataPacket(selectedCountry.get("name"), false)));
                    if (selectedCountry.containsKey("id")) {
                        FactionGUI.factionInfos.put("id", selectedCountry.get("id"));
                    }
                    Minecraft.func_71410_x().func_71373_a((GuiScreen)new BankGUI());
                } else if (this.hoveredAction.equals("join_country") && selectedCountry != null && !selectedCountry.isEmpty()) {
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionAskToJoinPacket(selectedCountry.get("name"))));
                } else if (this.hoveredAction.contains("switch_tab")) {
                    this.activeTab = TAB.valueOf(this.hoveredAction.replace("switch_tab#", ""));
                }
            } else if (hoveredCountry != null && !hoveredCountry.isEmpty()) {
                if (selectedCountry.equals(hoveredCountry) && System.currentTimeMillis() - lastClick < 200L) {
                    Minecraft.func_71410_x().func_71373_a((GuiScreen)new FactionGUI(selectedCountry.get("name")));
                } else {
                    selectedCountry = hoveredCountry;
                }
            }
            lastClick = System.currentTimeMillis();
        }
        this.searchInput.func_73793_a(mouseX, mouseY, mouseButton);
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    static enum TAB {
        LIST,
        JOIN,
        BUY,
        ACTIONS;

    }
}

