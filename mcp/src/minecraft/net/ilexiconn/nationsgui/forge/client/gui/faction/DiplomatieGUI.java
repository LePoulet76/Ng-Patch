/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.internal.LinkedTreeMap
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.util.ChatMessageComponent
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.faction;

import com.google.gson.internal.LinkedTreeMap;
import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarGeneric;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.RefuseColonyConfirmGui;
import net.ilexiconn.nationsgui.forge.client.gui.faction.TabbedFactionGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionDiplomatieDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionDiplomatieWishActionPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class DiplomatieGUI
extends TabbedFactionGUI {
    public static boolean loaded = false;
    public static TreeMap<String, Object> factionDiplomatieInfos;
    public String displayMode = "relations";
    private GuiScrollBarGeneric scrollBarAllies;
    private GuiScrollBarGeneric scrollBarEnnemies;
    private GuiScrollBarGeneric scrollBarColonies;
    private GuiScrollBarGeneric scrollBarReceived;
    private GuiScrollBarGeneric scrollBarSent;
    private String hoveredCountry = "";
    private String hoveredRelationType = "";

    public DiplomatieGUI() {
        loaded = false;
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionDiplomatieDataPacket((String)FactionGUI.factionInfos.get("name"))));
    }

    @Override
    public void func_73866_w_() {
        super.func_73866_w_();
        this.scrollBarAllies = new GuiScrollBarGeneric(this.guiLeft + 160, this.guiTop + 125, 96, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
        this.scrollBarEnnemies = new GuiScrollBarGeneric(this.guiLeft + 301, this.guiTop + 125, 96, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
        this.scrollBarColonies = new GuiScrollBarGeneric(this.guiLeft + 442, this.guiTop + 125, 96, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
        this.scrollBarReceived = new GuiScrollBarGeneric(this.guiLeft + 235, this.guiTop + 124, 96, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
        this.scrollBarSent = new GuiScrollBarGeneric(this.guiLeft + 441, this.guiTop + 124, 96, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
    }

    @Override
    public void func_73863_a(int mouseX, int mouseY, float partialTick) {
        this.func_73873_v_();
        tooltipToDraw.clear();
        this.hoveredAction = "";
        this.hoveredCountry = "";
        this.hoveredRelationType = "";
        ClientEventHandler.STYLE.bindTexture("faction_global_2");
        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 30, this.guiTop + 0, 0 * GUI_SCALE, 0 * GUI_SCALE, (this.xSize - 30) * GUI_SCALE, this.ySize * GUI_SCALE, this.xSize - 30, this.ySize, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
        if (loaded) {
            if (FactionGUI.factionInfos.get("banners") != null && ((Map)FactionGUI.factionInfos.get("banners")).containsKey("diplomatie")) {
                ModernGui.bindRemoteTexture((String)((Map)FactionGUI.factionInfos.get("banners")).get("diplomatie"));
            } else {
                ModernGui.bindRemoteTexture("https://static.nationsglory.fr/N33N_N33NN.png");
            }
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 30 + 154, this.guiTop + 0, 0.0f, 0.0f, 279 * GUI_SCALE, 110 * GUI_SCALE, 279, 89, 279 * GUI_SCALE, 110 * GUI_SCALE, false);
            ClientEventHandler.STYLE.bindTexture("faction_global");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 30, this.guiTop + 0, 33 * GUI_SCALE, 280 * GUI_SCALE, 433 * GUI_SCALE, 89 * GUI_SCALE, 433, 89, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
            ClientEventHandler.STYLE.bindTexture("faction_diplomatie");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 292, this.guiTop - 15, 382 * GUI_SCALE, 0 * GUI_SCALE, 130 * GUI_SCALE, 104 * GUI_SCALE, 130, 104, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
            ModernGui.drawScaledStringCustomFont((String)FactionGUI.factionInfos.get("name"), this.guiLeft + 43, this.guiTop + 6, 10395075, 0.5f, "left", false, "georamaMedium", 32);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.diplomatie.title"), this.guiLeft + 43, this.guiTop + 16, 0xFFFFFF, 0.75f, "left", false, "georamaSemiBold", 32);
            ModernGui.drawSectionStringCustomFont(((String)FactionGUI.factionInfos.get("description")).replaceAll("\u00a7[0-9a-z]{1}", ""), this.guiLeft + 43, this.guiTop + 32, 10395075, 0.5f, "left", false, "georamaMedium", 25, 8, 350);
            ArrayList sent = (ArrayList)factionDiplomatieInfos.get("sent");
            ArrayList received = (ArrayList)factionDiplomatieInfos.get("received");
            if (this.displayMode.equals("relations")) {
                String countryName;
                Float offsetY;
                int offsetX;
                int i;
                ArrayList allies = (ArrayList)factionDiplomatieInfos.get("allies");
                ArrayList ennemies = (ArrayList)factionDiplomatieInfos.get("ennemies");
                ArrayList colonies = (ArrayList)factionDiplomatieInfos.get("colonies");
                ClientEventHandler.STYLE.bindTexture("faction_diplomatie");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 42, this.guiTop + 77, 0 * GUI_SCALE, 145 * GUI_SCALE, 65 * GUI_SCALE, 12 * GUI_SCALE, 65, 12, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.diplomatie.label.relations"), this.guiLeft + 42 + 32, this.guiTop + 79, 0x6E76EE, 0.5f, "center", false, "georamaSemiBold", 28);
                if (mouseX >= this.guiLeft + 42 && mouseX <= this.guiLeft + 42 + 65 && mouseY >= this.guiTop + 77 && mouseY <= this.guiTop + 77 + 12) {
                    this.hoveredAction = "tab_relations";
                }
                ClientEventHandler.STYLE.bindTexture("faction_diplomatie");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 108, this.guiTop + 77, 68 * GUI_SCALE, 145 * GUI_SCALE, 65 * GUI_SCALE, 12 * GUI_SCALE, 65, 12, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.diplomatie.label.requests"), this.guiLeft + 108 + 32, this.guiTop + 79, 14803951, 0.5f, "center", false, "georamaSemiBold", 28);
                if (received.size() > 0) {
                    ClientEventHandler.STYLE.bindTexture("faction_diplomatie");
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 168, this.guiTop + 75, 134 * GUI_SCALE, 145 * GUI_SCALE, 6 * GUI_SCALE, 6 * GUI_SCALE, 6, 6, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                }
                if (mouseX >= this.guiLeft + 108 && mouseX <= this.guiLeft + 108 + 65 && mouseY >= this.guiTop + 77 && mouseY <= this.guiTop + 77 + 12) {
                    this.hoveredAction = "tab_requests";
                }
                ClientEventHandler.STYLE.bindTexture("faction_diplomatie");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 43, this.guiTop + 104, 0 * GUI_SCALE, 0 * GUI_SCALE, 122 * GUI_SCALE, 120 * GUI_SCALE, 122, 120, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 43, this.guiTop + 104, 126 * GUI_SCALE, 124 * GUI_SCALE, 122 * GUI_SCALE, 17 * GUI_SCALE, 122, 17, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.diplomatie.label.allies"), this.guiLeft + 48, this.guiTop + 109, 0, 0.5f, "left", false, "georamaSemiBold", 30);
                ModernGui.drawScaledStringCustomFont(allies.size() + "", this.guiLeft + 159, this.guiTop + 109, 0, 0.5f, "right", false, "georamaSemiBold", 30);
                if (allies.size() > 0) {
                    ClientEventHandler.STYLE.bindTexture("faction_diplomatie");
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 160, this.guiTop + 125, 0 * GUI_SCALE, 160 * GUI_SCALE, 2 * GUI_SCALE, 96 * GUI_SCALE, 2, 96, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                    GUIUtils.startGLScissor(this.guiLeft + 43, this.guiTop + 121, 117, 103);
                    for (i = 0; i < allies.size(); ++i) {
                        offsetX = this.guiLeft + 43;
                        offsetY = Float.valueOf((float)(this.guiTop + 121 + 5 + i * 14) + this.getSlideAllies());
                        countryName = (String)((LinkedTreeMap)allies.get(i)).get((Object)"factionName");
                        ClientProxy.loadCountryFlag(countryName);
                        if (ClientProxy.flagsTexture.containsKey(countryName)) {
                            GL11.glBindTexture((int)3553, (int)ClientProxy.flagsTexture.get(countryName).func_110552_b());
                            ModernGui.drawScaledCustomSizeModalRect(offsetX + 5, offsetY.intValue() + 0, 0.0f, 0.0f, 156, 78, 17, 10, 156.0f, 78.0f, false);
                        }
                        ModernGui.drawScaledStringCustomFont(countryName, offsetX + 26, offsetY.intValue() + 2, 16514302, 0.5f, "left", false, "georamaMedium", 28);
                        if (!((String)((LinkedTreeMap)allies.get(i)).get((Object)"relationTime")).isEmpty() && !((String)((LinkedTreeMap)allies.get(i)).get((Object)"relationTime")).equals("null")) {
                            ModernGui.drawScaledStringCustomFont(this.convertDate((String)((LinkedTreeMap)allies.get(i)).get((Object)"relationTime")), offsetX + 114, offsetY.intValue() + 2, 10395075, 0.5f, "right", false, "georamaMedium", 28);
                        }
                        if (!(offsetY.floatValue() >= (float)(this.guiTop + 110)) || !(offsetY.floatValue() <= (float)(this.guiTop + 225)) || mouseX < offsetX || mouseX > offsetX + 117 || mouseY < offsetY.intValue() || mouseY > offsetY.intValue() + 14) continue;
                        this.hoveredAction = "open_country";
                        this.hoveredCountry = countryName;
                    }
                    GUIUtils.endGLScissor();
                    if (mouseX >= this.guiLeft + 43 && mouseX <= this.guiLeft + 43 + 122 && mouseY >= this.guiTop + 104 && mouseY <= this.guiTop + 104 + 120) {
                        this.scrollBarAllies.draw(mouseX, mouseY);
                    }
                } else {
                    ModernGui.drawScaledStringCustomFont("\u00a7o" + I18n.func_135053_a((String)"faction.diplomatie.label.no_relation"), this.guiLeft + 43 + 5, this.guiTop + 104 + 24, 10395075, 0.5f, "left", false, "georamaMedium", 28);
                }
                ClientEventHandler.STYLE.bindTexture("faction_diplomatie");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 184, this.guiTop + 104, 0 * GUI_SCALE, 0 * GUI_SCALE, 122 * GUI_SCALE, 120 * GUI_SCALE, 122, 120, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 184, this.guiTop + 104, 252 * GUI_SCALE, 124 * GUI_SCALE, 122 * GUI_SCALE, 17 * GUI_SCALE, 122, 17, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.diplomatie.label.enemies"), this.guiLeft + 189, this.guiTop + 109, 0, 0.5f, "left", false, "georamaSemiBold", 30);
                ModernGui.drawScaledStringCustomFont(ennemies.size() + "", this.guiLeft + 300, this.guiTop + 109, 0, 0.5f, "right", false, "georamaSemiBold", 30);
                if (ennemies.size() > 0) {
                    ClientEventHandler.STYLE.bindTexture("faction_diplomatie");
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 301, this.guiTop + 125, 0 * GUI_SCALE, 160 * GUI_SCALE, 2 * GUI_SCALE, 96 * GUI_SCALE, 2, 96, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                    GUIUtils.startGLScissor(this.guiLeft + 184, this.guiTop + 121, 117, 103);
                    for (i = 0; i < ennemies.size(); ++i) {
                        offsetX = this.guiLeft + 184;
                        offsetY = Float.valueOf((float)(this.guiTop + 121 + 5 + i * 14) + this.getSlideEnnemies());
                        countryName = (String)((LinkedTreeMap)ennemies.get(i)).get((Object)"factionName");
                        ClientProxy.loadCountryFlag(countryName);
                        if (ClientProxy.flagsTexture.containsKey(countryName)) {
                            GL11.glBindTexture((int)3553, (int)ClientProxy.flagsTexture.get(countryName).func_110552_b());
                            ModernGui.drawScaledCustomSizeModalRect(offsetX + 5, offsetY.intValue() + 0, 0.0f, 0.0f, 156, 78, 17, 10, 156.0f, 78.0f, false);
                        }
                        ModernGui.drawScaledStringCustomFont(countryName, offsetX + 26, offsetY.intValue() + 2, 16514302, 0.5f, "left", false, "georamaMedium", 28);
                        if (!((String)((LinkedTreeMap)ennemies.get(i)).get((Object)"relationTime")).isEmpty() && !((String)((LinkedTreeMap)ennemies.get(i)).get((Object)"relationTime")).equals("null")) {
                            ModernGui.drawScaledStringCustomFont(this.convertDate((String)((LinkedTreeMap)ennemies.get(i)).get((Object)"relationTime")), offsetX + 114, offsetY.intValue() + 2, 10395075, 0.5f, "right", false, "georamaMedium", 28);
                        }
                        if (!(offsetY.floatValue() >= (float)(this.guiTop + 110)) || !(offsetY.floatValue() <= (float)(this.guiTop + 225)) || mouseX < offsetX || mouseX > offsetX + 117 || mouseY < offsetY.intValue() || mouseY > offsetY.intValue() + 14) continue;
                        this.hoveredAction = "open_country";
                        this.hoveredCountry = countryName;
                    }
                    GUIUtils.endGLScissor();
                    if (mouseX >= this.guiLeft + 184 && mouseX <= this.guiLeft + 184 + 122 && mouseY >= this.guiTop + 104 && mouseY <= this.guiTop + 104 + 120) {
                        this.scrollBarEnnemies.draw(mouseX, mouseY);
                    }
                } else {
                    ModernGui.drawScaledStringCustomFont("\u00a7o" + I18n.func_135053_a((String)"faction.diplomatie.label.no_relation"), this.guiLeft + 184 + 5, this.guiTop + 104 + 24, 10395075, 0.5f, "left", false, "georamaMedium", 28);
                }
                ClientEventHandler.STYLE.bindTexture("faction_diplomatie");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 325, this.guiTop + 104, 0 * GUI_SCALE, 0 * GUI_SCALE, 122 * GUI_SCALE, 120 * GUI_SCALE, 122, 120, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 325, this.guiTop + 104, 0 * GUI_SCALE, 124 * GUI_SCALE, 122 * GUI_SCALE, 17 * GUI_SCALE, 122, 17, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.diplomatie.label.colonies"), this.guiLeft + 330, this.guiTop + 109, 0, 0.5f, "left", false, "georamaSemiBold", 30);
                ModernGui.drawScaledStringCustomFont(colonies.size() + "", this.guiLeft + 441, this.guiTop + 109, 0, 0.5f, "right", false, "georamaSemiBold", 30);
                if (colonies.size() > 0) {
                    ClientEventHandler.STYLE.bindTexture("faction_diplomatie");
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 442, this.guiTop + 125, 0 * GUI_SCALE, 160 * GUI_SCALE, 2 * GUI_SCALE, 96 * GUI_SCALE, 2, 96, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                    GUIUtils.startGLScissor(this.guiLeft + 325, this.guiTop + 121, 117, 103);
                    for (i = 0; i < colonies.size(); ++i) {
                        offsetX = this.guiLeft + 325;
                        offsetY = Float.valueOf((float)(this.guiTop + 121 + 5 + i * 14) + this.getSlideColonies());
                        countryName = (String)((LinkedTreeMap)colonies.get(i)).get((Object)"factionName");
                        ClientProxy.loadCountryFlag(countryName);
                        if (ClientProxy.flagsTexture.containsKey(countryName)) {
                            GL11.glBindTexture((int)3553, (int)ClientProxy.flagsTexture.get(countryName).func_110552_b());
                            ModernGui.drawScaledCustomSizeModalRect(offsetX + 5, offsetY.intValue() + 0, 0.0f, 0.0f, 156, 78, 17, 10, 156.0f, 78.0f, false);
                        }
                        ModernGui.drawScaledStringCustomFont(countryName, offsetX + 26, offsetY.intValue() + 2, 16514302, 0.5f, "left", false, "georamaMedium", 28);
                        if (!((String)((LinkedTreeMap)colonies.get(i)).get((Object)"relationTime")).isEmpty() && !((String)((LinkedTreeMap)colonies.get(i)).get((Object)"relationTime")).equals("null")) {
                            ModernGui.drawScaledStringCustomFont(this.convertDate((String)((LinkedTreeMap)colonies.get(i)).get((Object)"relationTime")), offsetX + 114, offsetY.intValue() + 2, 10395075, 0.5f, "right", false, "georamaMedium", 28);
                        }
                        if (!(offsetY.floatValue() >= (float)(this.guiTop + 110)) || !(offsetY.floatValue() <= (float)(this.guiTop + 225)) || mouseX < offsetX || mouseX > offsetX + 117 || mouseY < offsetY.intValue() || mouseY > offsetY.intValue() + 14) continue;
                        this.hoveredAction = "open_country";
                        this.hoveredCountry = countryName;
                    }
                    GUIUtils.endGLScissor();
                    if (mouseX >= this.guiLeft + 325 && mouseX <= this.guiLeft + 325 + 122 && mouseY >= this.guiTop + 104 && mouseY <= this.guiTop + 104 + 120) {
                        this.scrollBarColonies.draw(mouseX, mouseY);
                    }
                } else {
                    ModernGui.drawScaledStringCustomFont("\u00a7o" + I18n.func_135053_a((String)"faction.diplomatie.label.no_relation"), this.guiLeft + 325 + 5, this.guiTop + 104 + 24, 10395075, 0.5f, "left", false, "georamaMedium", 28);
                }
            } else if (this.displayMode.equals("requests")) {
                String relationType;
                String countryName;
                Float offsetY;
                int offsetX;
                int i;
                ClientEventHandler.STYLE.bindTexture("faction_diplomatie");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 42, this.guiTop + 77, 68 * GUI_SCALE, 145 * GUI_SCALE, 65 * GUI_SCALE, 12 * GUI_SCALE, 65, 12, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.diplomatie.label.relations"), this.guiLeft + 42 + 32, this.guiTop + 79, 14803951, 0.5f, "center", false, "georamaSemiBold", 28);
                if (mouseX >= this.guiLeft + 42 && mouseX <= this.guiLeft + 42 + 65 && mouseY >= this.guiTop + 77 && mouseY <= this.guiTop + 77 + 12) {
                    this.hoveredAction = "tab_relations";
                }
                ClientEventHandler.STYLE.bindTexture("faction_diplomatie");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 108, this.guiTop + 77, 0 * GUI_SCALE, 145 * GUI_SCALE, 65 * GUI_SCALE, 12 * GUI_SCALE, 65, 12, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.diplomatie.label.requests"), this.guiLeft + 108 + 32, this.guiTop + 79, 0x6E76EE, 0.5f, "center", false, "georamaSemiBold", 28);
                if (received.size() > 0) {
                    ClientEventHandler.STYLE.bindTexture("faction_diplomatie");
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 168, this.guiTop + 75, 134 * GUI_SCALE, 145 * GUI_SCALE, 6 * GUI_SCALE, 6 * GUI_SCALE, 6, 6, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                }
                if (mouseX >= this.guiLeft + 108 && mouseX <= this.guiLeft + 108 + 65 && mouseY >= this.guiTop + 77 && mouseY <= this.guiTop + 77 + 12) {
                    this.hoveredAction = "tab_requests";
                }
                ClientEventHandler.STYLE.bindTexture("faction_diplomatie");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 43, this.guiTop + 103, 126 * GUI_SCALE, 0 * GUI_SCALE, 197 * GUI_SCALE, 120 * GUI_SCALE, 197, 120, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.diplomatie.label.received"), this.guiLeft + 48, this.guiTop + 108, 0, 0.5f, "left", false, "georamaSemiBold", 30);
                ModernGui.drawScaledStringCustomFont(received.size() + "", this.guiLeft + 232, this.guiTop + 108, 0, 0.5f, "right", false, "georamaSemiBold", 30);
                if (received.size() > 0) {
                    ClientEventHandler.STYLE.bindTexture("faction_diplomatie");
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 235, this.guiTop + 124, 0 * GUI_SCALE, 160 * GUI_SCALE, 2 * GUI_SCALE, 96 * GUI_SCALE, 2, 96, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                    GUIUtils.startGLScissor(this.guiLeft + 43, this.guiTop + 121, 192, 103);
                    for (i = 0; i < received.size(); ++i) {
                        offsetX = this.guiLeft + 43;
                        offsetY = Float.valueOf((float)(this.guiTop + 121 + 5 + i * 14) + this.getSlideReceived());
                        countryName = (String)((LinkedTreeMap)received.get(i)).get((Object)"factionName");
                        relationType = (String)((LinkedTreeMap)received.get(i)).get((Object)"relationType");
                        ClientProxy.loadCountryFlag(countryName);
                        if (ClientProxy.flagsTexture.containsKey(countryName)) {
                            GL11.glBindTexture((int)3553, (int)ClientProxy.flagsTexture.get(countryName).func_110552_b());
                            ModernGui.drawScaledCustomSizeModalRect(offsetX + 5, offsetY.intValue() + 0, 0.0f, 0.0f, 156, 78, 17, 10, 156.0f, 78.0f, false);
                        }
                        ModernGui.drawScaledStringCustomFont(countryName, offsetX + 26, offsetY.intValue() + 2, 16514302, 0.5f, "left", false, "georamaMedium", 28);
                        ModernGui.drawScaledStringCustomFont(relationType, offsetX + 104, offsetY.intValue() + 2, 10395075, 0.5f, "left", false, "georamaMedium", 28);
                        if (!FactionGUI.hasPermissions("relations")) continue;
                        ClientEventHandler.STYLE.bindTexture("faction_diplomatie");
                        if (offsetY.floatValue() >= (float)(this.guiTop + 110) && offsetY.floatValue() <= (float)(this.guiTop + 225) && mouseX >= offsetX + 169 && mouseX <= offsetX + 169 + 9 && mouseY >= offsetY.intValue() + 2 && mouseY <= offsetY.intValue() + 2 + 7) {
                            ModernGui.drawScaledCustomSizeModalRect(offsetX + 169, offsetY.intValue() + 2, 142 * GUI_SCALE, 155 * GUI_SCALE, 9 * GUI_SCALE, 7 * GUI_SCALE, 9, 7, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                            this.hoveredCountry = countryName;
                            this.hoveredAction = "relation_yes";
                            this.hoveredRelationType = relationType;
                        } else {
                            ModernGui.drawScaledCustomSizeModalRect(offsetX + 169, offsetY.intValue() + 2, 142 * GUI_SCALE, 145 * GUI_SCALE, 9 * GUI_SCALE, 7 * GUI_SCALE, 9, 7, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                        }
                        if (mouseX >= offsetX + 181 && mouseX <= offsetX + 181 + 8 && mouseY >= offsetY.intValue() + 2 && mouseY <= offsetY.intValue() + 2 + 8) {
                            ModernGui.drawScaledCustomSizeModalRect(offsetX + 181, offsetY.intValue() + 2, 155 * GUI_SCALE, 155 * GUI_SCALE, 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                            this.hoveredCountry = countryName;
                            this.hoveredAction = "relation_no";
                            this.hoveredRelationType = relationType;
                            continue;
                        }
                        ModernGui.drawScaledCustomSizeModalRect(offsetX + 181, offsetY.intValue() + 2, 155 * GUI_SCALE, 145 * GUI_SCALE, 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                    }
                    GUIUtils.endGLScissor();
                    if (mouseX >= this.guiLeft + 43 && mouseX <= this.guiLeft + 43 + 160 && mouseY >= this.guiTop + 104 && mouseY <= this.guiTop + 104 + 120) {
                        this.scrollBarReceived.draw(mouseX, mouseY);
                    }
                } else {
                    ModernGui.drawScaledStringCustomFont("\u00a7o" + I18n.func_135053_a((String)"faction.diplomatie.label.no_relation"), this.guiLeft + 43 + 5, this.guiTop + 104 + 24, 10395075, 0.5f, "left", false, "georamaMedium", 28);
                }
                ClientEventHandler.STYLE.bindTexture("faction_diplomatie");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 249, this.guiTop + 103, 126 * GUI_SCALE, 0 * GUI_SCALE, 197 * GUI_SCALE, 120 * GUI_SCALE, 197, 120, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.diplomatie.label.sent"), this.guiLeft + 254, this.guiTop + 108, 0, 0.5f, "left", false, "georamaSemiBold", 30);
                ModernGui.drawScaledStringCustomFont(sent.size() + "", this.guiLeft + 438, this.guiTop + 108, 0, 0.5f, "right", false, "georamaSemiBold", 30);
                if (sent.size() > 0) {
                    ClientEventHandler.STYLE.bindTexture("faction_diplomatie");
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 441, this.guiTop + 124, 0 * GUI_SCALE, 160 * GUI_SCALE, 2 * GUI_SCALE, 96 * GUI_SCALE, 2, 96, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                    GUIUtils.startGLScissor(this.guiLeft + 249, this.guiTop + 121, 192, 103);
                    for (i = 0; i < sent.size(); ++i) {
                        offsetX = this.guiLeft + 249;
                        offsetY = Float.valueOf((float)(this.guiTop + 121 + 5 + i * 14) + this.getSlideSent());
                        countryName = (String)((LinkedTreeMap)sent.get(i)).get((Object)"factionName");
                        relationType = (String)((LinkedTreeMap)sent.get(i)).get((Object)"relationType");
                        ClientProxy.loadCountryFlag(countryName);
                        if (ClientProxy.flagsTexture.containsKey(countryName)) {
                            GL11.glBindTexture((int)3553, (int)ClientProxy.flagsTexture.get(countryName).func_110552_b());
                            ModernGui.drawScaledCustomSizeModalRect(offsetX + 5, offsetY.intValue() + 0, 0.0f, 0.0f, 156, 78, 17, 10, 156.0f, 78.0f, false);
                        }
                        ModernGui.drawScaledStringCustomFont(countryName, offsetX + 26, offsetY.intValue() + 2, 16514302, 0.5f, "left", false, "georamaMedium", 28);
                        ModernGui.drawScaledStringCustomFont(relationType, offsetX + 104, offsetY.intValue() + 2, 10395075, 0.5f, "left", false, "georamaMedium", 28);
                        if (!FactionGUI.hasPermissions("relations")) continue;
                        ClientEventHandler.STYLE.bindTexture("faction_diplomatie");
                        if (offsetY.floatValue() >= (float)(this.guiTop + 110) && offsetY.floatValue() <= (float)(this.guiTop + 225) && mouseX >= offsetX + 181 && mouseX <= offsetX + 181 + 8 && mouseY >= offsetY.intValue() + 2 && mouseY <= offsetY.intValue() + 2 + 8) {
                            ModernGui.drawScaledCustomSizeModalRect(offsetX + 181, offsetY.intValue() + 2, 155 * GUI_SCALE, 155 * GUI_SCALE, 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                            this.hoveredCountry = countryName;
                            this.hoveredAction = "relation_cancel";
                            this.hoveredRelationType = relationType;
                            continue;
                        }
                        ModernGui.drawScaledCustomSizeModalRect(offsetX + 181, offsetY.intValue() + 2, 155 * GUI_SCALE, 145 * GUI_SCALE, 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                    }
                    GUIUtils.endGLScissor();
                    if (mouseX >= this.guiLeft + 249 && mouseX <= this.guiLeft + 249 + 160 && mouseY >= this.guiTop + 104 && mouseY <= this.guiTop + 104 + 120) {
                        this.scrollBarSent.draw(mouseX, mouseY);
                    }
                } else {
                    ModernGui.drawScaledStringCustomFont("\u00a7o" + I18n.func_135053_a((String)"faction.diplomatie.label.no_relation"), this.guiLeft + 249 + 5, this.guiTop + 104 + 24, 10395075, 0.5f, "left", false, "georamaMedium", 28);
                }
            }
        }
        super.func_73863_a(mouseX, mouseY, partialTick);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
    }

    private float getSlideAllies() {
        return ((ArrayList)factionDiplomatieInfos.get("allies")).size() > 7 ? (float)(-(((ArrayList)factionDiplomatieInfos.get("allies")).size() - 7) * 14) * this.scrollBarAllies.getSliderValue() : 0.0f;
    }

    private float getSlideEnnemies() {
        return ((ArrayList)factionDiplomatieInfos.get("ennemies")).size() > 8 ? (float)(-(((ArrayList)factionDiplomatieInfos.get("ennemies")).size() - 8) * 13) * this.scrollBarEnnemies.getSliderValue() : 0.0f;
    }

    private float getSlideColonies() {
        return ((ArrayList)factionDiplomatieInfos.get("colonies")).size() > 8 ? (float)(-(((ArrayList)factionDiplomatieInfos.get("colonies")).size() - 8) * 13) * this.scrollBarColonies.getSliderValue() : 0.0f;
    }

    private float getSlideReceived() {
        return ((ArrayList)factionDiplomatieInfos.get("received")).size() > 8 ? (float)(-(((ArrayList)factionDiplomatieInfos.get("received")).size() - 8) * 13) * this.scrollBarReceived.getSliderValue() : 0.0f;
    }

    private float getSlideSent() {
        return ((ArrayList)factionDiplomatieInfos.get("sent")).size() > 8 ? (float)(-(((ArrayList)factionDiplomatieInfos.get("sent")).size() - 8) * 13) * this.scrollBarSent.getSliderValue() : 0.0f;
    }

    @Override
    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0 && !this.hoveredAction.isEmpty()) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
            if (this.hoveredAction.equals("tab_relations")) {
                this.displayMode = "relations";
            } else if (this.hoveredAction.equals("tab_requests")) {
                this.displayMode = "requests";
            } else if (this.hoveredAction.equals("edit_photo")) {
                ClientData.lastCaptureScreenshot.put("diplomatie", System.currentTimeMillis());
                Minecraft.func_71410_x().func_71373_a(null);
                Minecraft.func_71410_x().field_71439_g.func_70006_a(ChatMessageComponent.func_111066_d((String)I18n.func_135053_a((String)"faction.take_picture")));
            } else if (this.hoveredAction.equalsIgnoreCase("open_country") && !this.hoveredCountry.isEmpty()) {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new FactionGUI(this.hoveredCountry));
            } else if (!this.hoveredAction.isEmpty() && !this.hoveredCountry.isEmpty()) {
                if (this.hoveredAction.equals("relation_no") && this.hoveredRelationType.equalsIgnoreCase("colony")) {
                    Minecraft.func_71410_x().func_71373_a((GuiScreen)new RefuseColonyConfirmGui(this, this.hoveredCountry, this.hoveredAction.replaceAll("relation_", ""), this.hoveredRelationType));
                } else {
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionDiplomatieWishActionPacket((String)FactionGUI.factionInfos.get("name"), this.hoveredCountry, this.hoveredAction.replaceAll("relation_", ""), this.hoveredRelationType)));
                }
            }
            this.hoveredAction = "";
        }
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    private String convertDate(String time) {
        String date = "";
        long diff = System.currentTimeMillis() - Long.parseLong(time);
        long days = diff / 86400000L;
        long hours = 0L;
        long minutes = 0L;
        long seconds = 0L;
        if (days == 0L) {
            hours = diff / 3600000L;
            if (hours == 0L) {
                minutes = diff / 60000L;
                if (minutes == 0L) {
                    seconds = diff / 1000L;
                    date = date + " " + seconds + " " + I18n.func_135053_a((String)"faction.common.seconds");
                } else {
                    date = date + " " + minutes + " " + I18n.func_135053_a((String)"faction.common.minutes");
                }
            } else {
                date = date + " " + hours + " " + I18n.func_135053_a((String)"faction.common.hours");
            }
        } else {
            date = date + " " + days + " " + I18n.func_135053_a((String)"faction.common.days");
        }
        return date;
    }
}

