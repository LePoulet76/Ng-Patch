/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import net.halalaboos.cfont.CFontRenderer;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.MinimapRenderer;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarGeneric;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionListGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.TabbedFactionListGUI;
import net.ilexiconn.nationsgui.forge.client.gui.main.component.CustomInputFieldGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionCreateDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionCreatePacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.MinimapRequestPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class FactionCreateGUI
extends TabbedFactionListGUI {
    public static ArrayList<HashMap<String, String>> availableCountries = new ArrayList();
    public static HashMap<String, String> selectedCountry = new HashMap();
    public static HashMap<String, String> hoveredCountry = new HashMap();
    public MinimapRenderer minimapRenderer = new MinimapRenderer(8, 8);
    public Long lastMapLoading = 0L;
    public static boolean loaded = false;
    public boolean announceCreation = false;
    private GuiScrollBarGeneric scrollBar;
    private GuiTextField searchInput;
    private CFontRenderer cFontSemiBold28;
    public int countCountriesBySearch = -1;

    public FactionCreateGUI() {
        FactionCreateGUI.initTabs();
    }

    @Override
    public void func_73866_w_() {
        super.func_73866_w_();
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionCreateDataPacket()));
        this.cFontSemiBold28 = ModernGui.getCustomFont("georamaSemiBold", 28);
        this.scrollBar = new GuiScrollBarGeneric(this.guiLeft + 161, this.guiTop + 78, 135, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
        this.searchInput = new CustomInputFieldGUI(this.guiLeft + 62, this.guiTop + 42, 94, 12, "georamaSemiBold", 28);
        this.searchInput.func_73786_a(false);
        this.searchInput.func_73804_f(15);
        this.countCountriesBySearch = -1;
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
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.list.create_country"), this.guiLeft + 43, this.guiTop + 16, 0xFFFFFF, 0.75f, "left", false, "georamaSemiBold", 32);
        ClientEventHandler.STYLE.bindTexture("faction_list");
        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 42, this.guiTop + 36, 276 * GUI_SCALE, 157 * GUI_SCALE, 129 * GUI_SCALE, 27 * GUI_SCALE, 129, 27, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
        this.searchInput.func_73795_f();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        ClientEventHandler.STYLE.bindTexture("faction_list");
        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 42, this.guiTop + 69, 276 * GUI_SCALE, 0 * GUI_SCALE, 129 * GUI_SCALE, 152 * GUI_SCALE, 129, 152, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
        GUIUtils.startGLScissor(this.guiLeft + 42, this.guiTop + 69, 119, 152);
        if (availableCountries != null && availableCountries.size() > 0) {
            int index = 0;
            for (HashMap<String, String> country : availableCountries) {
                if (!this.searchInput.func_73781_b().isEmpty() && !country.get("name").toLowerCase().contains(this.searchInput.func_73781_b().toLowerCase())) continue;
                int offsetX = this.guiLeft + 42;
                Float offsetY = Float.valueOf((float)(this.guiTop + 69 + index * 12) + this.getSlideCountries());
                String continent = FactionCreateGUI.getContientByCoords(Integer.parseInt(selectedCountry.get("x")), Integer.parseInt(selectedCountry.get("z")), country.get("name"));
                ModernGui.drawScaledStringCustomFont(country.get("name"), offsetX + 11, offsetY.intValue() + 12, country.get("name").equals(selectedCountry.get("name")) ? 0x6E76EE : 16514302, 0.5f, "left", false, "georamaBold", 28);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("zone.name.zone_" + continent)), offsetX + 70, offsetY.intValue() + 11, 10395075, 0.5f, "left", false, "georamaMedium", 28);
                if (mouseX >= offsetX && mouseX <= offsetX + 154 && (float)mouseY >= offsetY.floatValue() + 12.0f && (float)mouseY <= offsetY.floatValue() + 12.0f + 12.0f) {
                    hoveredCountry = country;
                }
                ++index;
            }
            if (this.countCountriesBySearch == -1) {
                this.countCountriesBySearch = index + 1;
            }
        } else {
            ModernGui.drawSectionStringCustomFont(I18n.func_135053_a((String)"faction.create.unavailable_1"), this.guiLeft + 50, this.guiTop + 80, 10395075, 0.5f, "left", false, "georamaSemiBold", 26, 8, 200);
            ModernGui.drawSectionStringCustomFont(I18n.func_135053_a((String)"faction.create.unavailable_2"), this.guiLeft + 50, this.guiTop + 100, 10395075, 0.5f, "left", false, "georamaSemiBold", 26, 8, 200);
        }
        GUIUtils.endGLScissor();
        if (mouseX >= this.guiLeft + 42 && mouseX <= this.guiLeft + 42 + 129 && mouseY >= this.guiTop + 69 && mouseY <= this.guiTop + 69 + 152) {
            this.scrollBar.draw(mouseX, mouseY);
        }
        if (availableCountries == null || availableCountries.size() == 0) {
            ClientEventHandler.STYLE.bindTexture("faction_create");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 187, this.guiTop + 36, 0 * GUI_SCALE, 0 * GUI_SCALE, 272 * GUI_SCALE, 185 * GUI_SCALE, 272, 185, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.create.label.join_country"), this.guiLeft + 195, this.guiTop + 45, 16514302, 0.75f, "left", false, "georamaSemiBold", 30);
            ClientEventHandler.STYLE.bindTexture("faction_list");
            if (mouseX >= this.guiLeft + 195 && mouseX <= this.guiLeft + 195 + 59 && mouseY >= this.guiTop + 63 && mouseY <= this.guiTop + 63 + 13) {
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 195, this.guiTop + 63, 409 * GUI_SCALE, 156 * GUI_SCALE, 59 * GUI_SCALE, 13 * GUI_SCALE, 59, 13, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                this.hoveredAction = "country_list";
            } else {
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 195, this.guiTop + 63, 409 * GUI_SCALE, 122 * GUI_SCALE, 59 * GUI_SCALE, 13 * GUI_SCALE, 59, 13, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
            }
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.list.see_list"), this.guiLeft + 195 + 29, this.guiTop + 66, 2234425, 0.5f, "center", false, "georamaSemiBold", 28);
        } else if (selectedCountry != null && !selectedCountry.isEmpty()) {
            ClientEventHandler.STYLE.bindTexture("faction_list");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 187, this.guiTop + 36, 0 * GUI_SCALE, 0 * GUI_SCALE, 266 * GUI_SCALE, 185 * GUI_SCALE, 266, 185, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
            ModernGui.drawScaledStringCustomFont(selectedCountry.get("name"), this.guiLeft + 196, this.guiTop + 49, 16514302, 0.5f, "left", false, "georamaSemiBold", 32);
            GUIUtils.startGLScissor(this.guiLeft + 195, this.guiTop + 69, 121, 112);
            if (System.currentTimeMillis() - this.lastMapLoading > 2500L) {
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new MinimapRequestPacket(Integer.parseInt(selectedCountry.get("x")), Integer.parseInt(selectedCountry.get("z")), 8, 8)));
            }
            GL11.glDisable((int)2929);
            this.minimapRenderer.renderMap(this.guiLeft + 195, this.guiTop + 69, mouseX, mouseY, false);
            GL11.glEnable((int)2929);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GUIUtils.endGLScissor();
            String continent = FactionCreateGUI.getContientByCoords(Integer.parseInt(selectedCountry.get("x")), Integer.parseInt(selectedCountry.get("z")), selectedCountry.get("name"));
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.list.localisation"), this.guiLeft + 330, this.guiTop + 75, 0x6E76EE, 0.5f, "left", false, "georamaMedium", 26);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("zone.name.zone_" + continent)), this.guiLeft + 330, this.guiTop + 83, 16514302, 0.5f, "left", false, "georamaSemiBold", 28);
            ModernGui.drawScaledStringCustomFont("(" + selectedCountry.get("x") + ", " + selectedCountry.get("z") + ")", this.guiLeft + 333 + (int)this.cFontSemiBold28.getStringWidth(I18n.func_135053_a((String)("zone.name.zone_" + continent))) / 2, this.guiTop + 84, 10395075, 0.5f, "left", false, "georamaSemiBold", 22);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.list.resources"), this.guiLeft + 330, this.guiTop + 97, 0x6E76EE, 0.5f, "left", false, "georamaMedium", 26);
            int offsetY = 7;
            boolean index = false;
            for (String line : I18n.func_135053_a((String)("zone.resources.zone_" + continent)).split("##")) {
                ModernGui.drawScaledStringCustomFont(line, this.guiLeft + 330, this.guiTop + 97 + offsetY, 16514302, 0.5f, "left", false, "georamaSemiBold", 28);
                offsetY += 8;
            }
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.list.culture"), this.guiLeft + 330, this.guiTop + 97 + (offsetY += 6), 0x6E76EE, 0.5f, "left", false, "georamaMedium", 26);
            offsetY += 7;
            for (String line : I18n.func_135053_a((String)("zone.cereals.zone_" + continent)).split("##")) {
                ModernGui.drawScaledStringCustomFont(line, this.guiLeft + 330, this.guiTop + 97 + offsetY, 16514302, 0.5f, "left", false, "georamaSemiBold", 28);
                offsetY += 8;
            }
            ClientEventHandler.STYLE.bindTexture("faction_global");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 323, this.guiTop + 185, (this.announceCreation ? 321 : 329) * GUI_SCALE, (this.announceCreation ? 199 : 189) * GUI_SCALE, 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.create.announce"), this.guiLeft + 335, this.guiTop + 185, 16514302, 0.5f, "left", false, "georamaSemiBold", 28);
            if (mouseX >= this.guiLeft + 323 && mouseX <= this.guiLeft + 323 + 8 && mouseY >= this.guiTop + 185 && mouseY <= this.guiTop + 185 + 8) {
                this.hoveredAction = "checkbox_announce";
            }
            ClientEventHandler.STYLE.bindTexture("faction_list");
            if (mouseX >= this.guiLeft + 385 && mouseX <= this.guiLeft + 385 + 59 && mouseY >= this.guiTop + 199 && mouseY <= this.guiTop + 199 + 13) {
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 385, this.guiTop + 199, 409 * GUI_SCALE, 156 * GUI_SCALE, 59 * GUI_SCALE, 13 * GUI_SCALE, 59, 13, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                this.hoveredAction = "country_create";
            } else {
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 385, this.guiTop + 199, 409 * GUI_SCALE, 122 * GUI_SCALE, 59 * GUI_SCALE, 13 * GUI_SCALE, 59, 13, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
            }
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.list.create"), this.guiLeft + 385 + 29, this.guiTop + 202, 2234425, 0.5f, "center", false, "georamaSemiBold", 28);
        }
        super.func_73863_a(mouseX, mouseY, partialTick);
    }

    private float getSlideCountries() {
        return this.countCountriesBySearch > 10 ? (float)(-(this.countCountriesBySearch - 10) * 12) * this.scrollBar.getSliderValue() : 0.0f;
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
                if (this.hoveredAction.equals("checkbox_announce")) {
                    this.announceCreation = !this.announceCreation;
                } else if (this.hoveredAction.equals("country_create")) {
                    ClientProxy.clientConfig.currentServerTime = System.currentTimeMillis();
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionCreatePacket(selectedCountry.get("name"), this.announceCreation, true)));
                    Minecraft.func_71410_x().func_71373_a(null);
                    try {
                        ClientProxy.saveConfig();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (this.hoveredAction.equals("country_list")) {
                    Minecraft.func_71410_x().func_71373_a((GuiScreen)new FactionListGUI());
                }
            } else if (hoveredCountry != null && !hoveredCountry.isEmpty()) {
                selectedCountry = hoveredCountry;
            }
        }
        this.searchInput.func_73793_a(mouseX, mouseY, mouseButton);
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    public static String getContientByCoords(int x, int z, String country) {
        if (country.equals("Ouzbekistan") || country.equals("Turkmenistan")) {
            return "asia";
        }
        if (x >= -8195 && x <= -3000 && z >= -4225 && z <= -1000) {
            return "america_north";
        }
        if (x >= -8195 && x <= -3000 && z >= -1000 && z <= 300) {
            return "america_middle";
        }
        if (x >= -8195 && x <= -3000 && z >= 300 && z <= 3800) {
            return "america_south";
        }
        if (x >= -3000 && x <= -2200 && z >= -4225 && z <= 3800) {
            return "atlantic";
        }
        if (x >= -2200 && x <= 1800 && z >= -4225 && z <= -1600) {
            return "europe";
        }
        if (x >= -2200 && x <= 1800 && z >= -1600 && z <= 3800) {
            return "africa";
        }
        if (x >= 1800 && x <= 8320 && z >= -4225 && z <= -1600) {
            return "asia";
        }
        if (x >= 1800 && x <= 8320 && z >= -1600 && z <= 3800) {
            return "oceania";
        }
        if (x >= -8195 && x <= 8320 && z >= 3800 && z <= 5400) {
            return "antarctica";
        }
        return "unknow";
    }
}

