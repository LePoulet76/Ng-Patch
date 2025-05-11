/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.AbstractClientPlayer
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiTextField
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import net.halalaboos.cfont.CFontRenderer;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarGeneric;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionCreateEditPlotsGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.TabbedFactionGUI;
import net.ilexiconn.nationsgui.forge.client.gui.main.component.CustomInputFieldGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionPlotsActionPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionPlotsDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class FactionPlotsGUI
extends TabbedFactionGUI {
    public static CFontRenderer dg22 = ModernGui.getCustomFont("minecraftDungeons", 22);
    public static String defaultImageList = "https://static.nationsglory.fr/N355G4y4_N.png";
    public static String defaultImageDetail = "https://static.nationsglory.fr/N4yG34N5NG.png";
    public static boolean loaded = false;
    public static boolean adminMode = false;
    public static List<HashMap<String, Object>> plots = new ArrayList<HashMap<String, Object>>();
    public static int countSell = 0;
    public static int countRent = 0;
    public static int countAvailable = 0;
    public static boolean canCreateNewPlot = false;
    public static HashMap<String, Object> selectedPlot = new HashMap();
    public static HashMap<String, Object> hoveredPlot = new HashMap();
    private GuiScrollBarGeneric scrollBarPlots;
    public static List<String> searchFilters = Arrays.asList("sell", "rent", "available");
    private GuiTextField inputSearchName;
    private ArrayList<String> selectedSearchFilters = new ArrayList();
    private GuiTextField inputName;
    private GuiTextField inputDescription;
    private GuiTextField inputRooms;
    private GuiTextField inputPrice;

    public FactionPlotsGUI() {
        plots = new ArrayList<HashMap<String, Object>>();
        loaded = false;
        this.selectedSearchFilters = new ArrayList<String>(Arrays.asList("sell", "rent"));
        selectedPlot.clear();
        countSell = 0;
        countRent = 0;
        countAvailable = 0;
        canCreateNewPlot = false;
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionPlotsDataPacket((String)FactionGUI.factionInfos.get("id"))));
    }

    @Override
    public void func_73866_w_() {
        super.func_73866_w_();
        this.scrollBarPlots = new GuiScrollBarGeneric(this.guiLeft + 450, this.guiTop + 47, 172, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_parrot.png"), 5, 28);
        this.inputSearchName = new CustomInputFieldGUI(this.guiLeft + 177, this.guiTop + 21, 90, 12, "georamaMedium", 28);
        this.inputSearchName.func_73804_f(24);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void func_73863_a(int mouseX, int mouseY, float partialTick) {
        this.func_73873_v_();
        tooltipToDraw = new ArrayList();
        this.hoveredAction = "";
        hoveredPlot.clear();
        GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
        ClientEventHandler.STYLE.bindTexture("faction_global_2");
        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 30, this.guiTop + 0, 0 * GUI_SCALE, 0 * GUI_SCALE, (this.xSize - 30) * GUI_SCALE, this.ySize * GUI_SCALE, this.xSize - 30, this.ySize, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
        ModernGui.drawScaledStringCustomFont((String)FactionGUI.factionInfos.get("name"), this.guiLeft + 43, this.guiTop + 6, 10395075, 0.5f, "left", false, "georamaMedium", 32);
        if (loaded) {
            if (selectedPlot.isEmpty()) {
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.plots.title"), this.guiLeft + 43, this.guiTop + 16, 0xFFFFFF, 0.75f, "left", false, "georamaSemiBold", 32);
                if (((Boolean)FactionGUI.factionInfos.get("isInCountry")).booleanValue() && FactionGUI.hasPermissions("locations")) {
                    ClientEventHandler.STYLE.bindTexture("faction_plots");
                    boolean hoveringCreate = mouseX >= this.guiLeft + 157 && mouseX <= this.guiLeft + 157 + 12 && mouseY >= this.guiTop + 22 && mouseY <= this.guiTop + 22 + 12;
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 157, this.guiTop + 22, (hoveringCreate && canCreateNewPlot ? 19 : 4) * GUI_SCALE, 101 * GUI_SCALE, 12 * GUI_SCALE, 12 * GUI_SCALE, 12, 12, 512 * GUI_SCALE, 512 * GUI_SCALE, hoveringCreate);
                    if (hoveringCreate) {
                        if (canCreateNewPlot) {
                            tooltipToDraw.add(I18n.func_135053_a((String)"faction.plots.create"));
                            this.hoveredAction = "create";
                        } else {
                            tooltipToDraw.addAll(Arrays.asList(I18n.func_135053_a((String)"faction.plots.create.max_plots").split("##")));
                        }
                    }
                }
                ClientEventHandler.STYLE.bindTexture("faction_plots");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 173, this.guiTop + 22, 280 * GUI_SCALE, 412 * GUI_SCALE, 105 * GUI_SCALE, 12 * GUI_SCALE, 105, 12, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                this.inputSearchName.func_73795_f();
                int indexFilters = 0;
                for (String string : searchFilters) {
                    void var7_12;
                    ClientEventHandler.STYLE.bindTexture("faction_plots");
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 282 + indexFilters * 54, this.guiTop + 22, (this.selectedSearchFilters.contains(string) ? 392 : 449) * GUI_SCALE, 412 * GUI_SCALE, 51 * GUI_SCALE, 12 * GUI_SCALE, 51, 12, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    String string2 = I18n.func_135053_a((String)("faction.plots.filter." + string));
                    if (string.equals("sell")) {
                        String string3 = string2 + " (" + countSell + ")";
                    } else if (string.equals("rent")) {
                        String string4 = string2 + " (" + countRent + ")";
                    } else if (string.equals("available")) {
                        String string5 = string2 + " (" + countAvailable + ")";
                    }
                    ModernGui.drawScaledStringCustomFont((String)var7_12, (float)(this.guiLeft + 282 + indexFilters * 54) + 25.5f, this.guiTop + 25, this.selectedSearchFilters.contains(string) ? 0xFFFFFF : 0xBABADA, 0.5f, "center", false, "georamaBold", 24);
                    if (mouseX >= this.guiLeft + 282 + indexFilters * 54 && mouseX <= this.guiLeft + 282 + indexFilters * 54 + 51 && mouseY >= this.guiTop + 22 && mouseY <= this.guiTop + 22 + 12) {
                        this.hoveredAction = "filter#" + string;
                    }
                    ++indexFilters;
                }
                GUIUtils.startGLScissor(this.guiLeft + 43, this.guiTop + 42, 428, 175);
                int indexPlot = 0;
                for (HashMap<String, Object> hashMap : plots) {
                    boolean hovered;
                    boolean isOwner;
                    int offsetX = this.guiLeft + 43 + indexPlot % 6 * 67;
                    Float offsetY = Float.valueOf((float)(this.guiTop + 45 + indexPlot / 6 * 104) + this.getSlidePlots());
                    boolean bl = isOwner = hashMap.get("owner") != null && hashMap.get("owner").equals(Minecraft.func_71410_x().field_71439_g.field_71092_bJ);
                    if (!this.inputSearchName.func_73781_b().isEmpty() && !((String)hashMap.get("name")).toLowerCase().contains(this.inputSearchName.func_73781_b().toLowerCase()) && (hashMap.get("owner") == null || !((String)hashMap.get("owner")).toLowerCase().contains(this.inputSearchName.func_73781_b().toLowerCase())) || !this.selectedSearchFilters.contains(hashMap.get("buyType")) || this.selectedSearchFilters.contains("available") && !((String)hashMap.get("owner")).isEmpty() && !isOwner) continue;
                    boolean bl2 = hovered = mouseX >= offsetX && mouseX <= offsetX + 63 && (float)mouseY >= offsetY.floatValue() && (float)mouseY <= offsetY.floatValue() + 100.0f;
                    if (hovered) {
                        hoveredPlot = (HashMap)hashMap.clone();
                    }
                    ClientEventHandler.STYLE.bindTexture("faction_plots");
                    int cardBackgroundTextureX = 248;
                    if (hovered) {
                        cardBackgroundTextureX = 449;
                    } else if (isOwner && ((String)hashMap.get("buyType")).equals("sell")) {
                        cardBackgroundTextureX = 382;
                    } else if (isOwner && ((String)hashMap.get("buyType")).equals("rent")) {
                        cardBackgroundTextureX = 315;
                    } else if (!((String)hashMap.get("owner")).isEmpty() && !hashMap.get("owner").equals(Minecraft.func_71410_x().field_71439_g.field_71092_bJ)) {
                        cardBackgroundTextureX = 181;
                    }
                    ModernGui.drawScaledCustomSizeModalRect(offsetX, offsetY.floatValue(), cardBackgroundTextureX * GUI_SCALE, 0 * GUI_SCALE, 63 * GUI_SCALE, 100 * GUI_SCALE, 63, 100, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    String img = hashMap.get("images") != null && !((ArrayList)hashMap.get("images")).isEmpty() ? (String)((ArrayList)hashMap.get("images")).get(0) : defaultImageList;
                    ModernGui.drawScaledModalRectWithCustomSizedRemoteTexture(offsetX + 4, offsetY.intValue() + 4, 61 * GUI_SCALE, 0, 116 * GUI_SCALE, 116 * GUI_SCALE, 55, 60, 225 * GUI_SCALE, 116 * GUI_SCALE, true, img);
                    int imageOverlayColor = -1;
                    if (hovered) {
                        imageOverlayColor = -2130706433;
                    } else if (isOwner && ((String)hashMap.get("buyType")).equals("sell")) {
                        imageOverlayColor = -2139436705;
                    } else if (isOwner && ((String)hashMap.get("buyType")).equals("rent")) {
                        imageOverlayColor = -2140244242;
                    } else if (!((String)hashMap.get("owner")).isEmpty() && !hashMap.get("owner").equals(Minecraft.func_71410_x().field_71439_g.field_71092_bJ)) {
                        imageOverlayColor = -1089400538;
                    }
                    if (imageOverlayColor != -1) {
                        ModernGui.glColorHex(imageOverlayColor, 1.0f);
                        ModernGui.drawRectangle(offsetX + 4, offsetY.floatValue() + 4.0f, this.field_73735_i, 55.0f, 60.0f);
                        GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
                    }
                    if (((String)hashMap.get("owner")).isEmpty()) {
                        ClientEventHandler.STYLE.bindTexture("faction_plots");
                        ModernGui.drawScaledCustomSizeModalRect(offsetX + 26, offsetY.floatValue() - 3.0f, 180 * GUI_SCALE, (hashMap.get("buyType").equals("sell") ? 106 : 119) * GUI_SCALE, 40 * GUI_SCALE, 11 * GUI_SCALE, 40, 11, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("faction.plots.label.to." + hashMap.get("buyType"))), offsetX + 26 + 20, offsetY.floatValue() + 0.5f, 0x252545, 0.5f, "center", false, "georamaBold", 20);
                        String plotName = (String)hashMap.get("name");
                        if (plotName.length() > 19) {
                            plotName = plotName.substring(0, 19) + ".";
                        }
                        ModernGui.drawScaledStringCustomFont(plotName, offsetX + 4, offsetY.floatValue() + 70.0f, hovered ? 1908021 : 0xF8F8FB, 0.5f, "left", false, "georamaSemiBold", 25);
                        ModernGui.drawScaledStringCustomFont(String.format("%.0f", (Double)hashMap.get("price")) + "$" + (hashMap.get("buyType").equals("rent") ? " " + I18n.func_135053_a((String)"faction.plots.label.per_day") : ""), offsetX + 4, offsetY.floatValue() + 82.0f, hovered ? 1908021 : (hashMap.get("buyType").equals("sell") ? 8046943 : 0x6E76EE), 0.5f, "left", false, "georamaRegular", 22);
                        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.plots.distance").replaceAll("XX", String.format("%.0f", (Double)hashMap.get("distance"))), offsetX + 4, offsetY.floatValue() + 90.0f, hovered ? 1908021 : 0x7979B7, 0.5f, "left", false, "georamaMedium", 23);
                    } else {
                        ArrayList<String> players = new ArrayList<String>(Arrays.asList((String)hashMap.get("owner")));
                        int indexHeadPlayers = 0;
                        for (String player : players) {
                            if (!ClientProxy.cacheHeadPlayer.containsKey(player)) {
                                try {
                                    ResourceLocation resourceLocation = AbstractClientPlayer.field_110314_b;
                                    resourceLocation = AbstractClientPlayer.func_110311_f((String)player);
                                    AbstractClientPlayer.func_110304_a((ResourceLocation)resourceLocation, (String)player);
                                    ClientProxy.cacheHeadPlayer.put(player, resourceLocation);
                                }
                                catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                                continue;
                            }
                            Minecraft.func_71410_x().field_71446_o.func_110577_a(ClientProxy.cacheHeadPlayer.get(player));
                            this.field_73882_e.func_110434_K().func_110577_a(ClientProxy.cacheHeadPlayer.get(player));
                            GUIUtils.drawScaledCustomSizeModalRect(offsetX + 13 + indexHeadPlayers * 11, offsetY.intValue() + 77, 8.0f, 16.0f, 8, -8, -9, -9, 64.0f, 64.0f);
                            ++indexHeadPlayers;
                        }
                        String ownerName = (String)hashMap.get("owner");
                        if (ownerName.length() > 15) {
                            ownerName = ownerName.substring(0, 15) + ".";
                        }
                        ModernGui.drawScaledStringCustomFont(ownerName, offsetX + 16, offsetY.floatValue() + 70.0f, hovered ? 1908021 : (!isOwner ? 0xF8F8FB : 0x111126), 0.5f, "left", false, "georamaSemiBold", 25);
                        String plotName = (String)hashMap.get("name");
                        ModernGui.drawScaledStringCustomFont(plotName, offsetX + 4, offsetY.floatValue() + 82.0f, hovered ? 1908021 : (!isOwner ? 0xBDBDBD : 0x111126), 0.5f, "left", false, "georamaRegular", 22);
                        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("faction.plots.label.owned." + hashMap.get("buyType"))), offsetX + 4, offsetY.floatValue() + 90.0f, hovered ? 1908021 : (isOwner ? 0x111126 : (hashMap.get("buyType").equals("rent") ? 0x6E76EE : 8046943)), 0.5f, "left", false, "georamaMedium", 23);
                    }
                    ++indexPlot;
                }
                if (indexPlot == 0) {
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.plots.no_plots"), this.guiLeft + 43 + 201, this.guiTop + 45 + 86, 0xFFFFFF, 0.5f, "center", false, "georamaSemiBold", 24);
                }
                GUIUtils.endGLScissor();
                ClientEventHandler.STYLE.bindTexture("faction_plots");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 450, this.guiTop + 47, 250 * GUI_SCALE, 340 * GUI_SCALE, 5 * GUI_SCALE, 172 * GUI_SCALE, 5, 172, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                this.scrollBarPlots.draw(mouseX, mouseY);
            } else {
                ClientEventHandler.STYLE.bindTexture("faction_plots");
                if (mouseX >= this.guiLeft + 403 && mouseX <= this.guiLeft + 403 + 40 && mouseY >= this.guiTop + 9 && mouseY <= this.guiTop + 9 + 6) {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 403, this.guiTop + 9, 48 * GUI_SCALE, 60 * GUI_SCALE, 6 * GUI_SCALE, 9 * GUI_SCALE, 6, 9, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.settings.label.back"), this.guiLeft + 412, this.guiTop + 10, 0xFFFFFF, 0.5f, "left", false, "georamaSemiBold", 30);
                    this.hoveredAction = "back";
                } else {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 403, this.guiTop + 9, 39 * GUI_SCALE, 60 * GUI_SCALE, 6 * GUI_SCALE, 9 * GUI_SCALE, 6, 9, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.settings.label.back"), this.guiLeft + 412, this.guiTop + 10, 10395075, 0.5f, "left", false, "georamaSemiBold", 30);
                }
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.plots.plot") + " " + selectedPlot.get("name"), this.guiLeft + 43, this.guiTop + 16, 0xFFFFFF, 0.75f, "left", false, "georamaSemiBold", 32);
                if (selectedPlot.get("images") == null || ((ArrayList)selectedPlot.get("images")).isEmpty()) {
                    ((ArrayList)selectedPlot.get("images")).add(defaultImageDetail);
                }
                if (selectedPlot.get("images") != null && !((ArrayList)selectedPlot.get("images")).isEmpty()) {
                    int indexImage = selectedPlot.containsKey("indexImage") && ((ArrayList)selectedPlot.get("images")).size() > (Integer)selectedPlot.get("indexImage") ? (Integer)selectedPlot.get("indexImage") : 0;
                    ModernGui.drawScaledModalRectWithCustomSizedRemoteTexture(this.guiLeft + 43, this.guiTop + 47, 0, 0, 225 * GUI_SCALE, 116 * GUI_SCALE, 225, 116, 225 * GUI_SCALE, 116 * GUI_SCALE, true, (String)((ArrayList)selectedPlot.get("images")).get(indexImage));
                    if (((ArrayList)selectedPlot.get("images")).size() > 1) {
                        ClientEventHandler.STYLE.bindTexture("faction_plots");
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 33, this.guiTop + 47 + 58 - 10, 6 * GUI_SCALE, 33 * GUI_SCALE, 21 * GUI_SCALE, 21 * GUI_SCALE, 21, 21, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                        if (mouseX >= this.guiLeft + 33 && mouseX <= this.guiLeft + 33 + 21 && mouseY >= this.guiTop + 47 + 58 - 10 && mouseY <= this.guiTop + 47 + 58 - 10 + 21) {
                            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 33, this.guiTop + 47 + 58 - 10, 33 * GUI_SCALE, 33 * GUI_SCALE, 21 * GUI_SCALE, 21 * GUI_SCALE, 21, 21, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                            this.hoveredAction = "carousel_previous";
                        }
                        ClientEventHandler.STYLE.bindTexture("faction_plots");
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 43 + 225 - 10, this.guiTop + 47 + 58 - 10, 6 * GUI_SCALE, 6 * GUI_SCALE, 21 * GUI_SCALE, 21 * GUI_SCALE, 21, 21, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                        if (mouseX >= this.guiLeft + 43 + 225 - 10 && mouseX <= this.guiLeft + 43 + 225 - 10 + 21 && mouseY >= this.guiTop + 47 + 58 - 10 && mouseY <= this.guiTop + 47 + 58 - 10 + 21) {
                            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 43 + 225 - 10, this.guiTop + 47 + 58 - 10, 33 * GUI_SCALE, 6 * GUI_SCALE, 21 * GUI_SCALE, 21 * GUI_SCALE, 21, 21, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                            this.hoveredAction = "carousel_next";
                        }
                    }
                } else {
                    ClientEventHandler.STYLE.bindTexture("faction_plots_2");
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 43, this.guiTop + 47, 0 * GUI_SCALE, 0 * GUI_SCALE, 0 * GUI_SCALE, 0 * GUI_SCALE, 225, 116, 0 * GUI_SCALE, 0 * GUI_SCALE, true);
                }
                if (selectedPlot.get("description") != null && !((String)selectedPlot.get("description")).isEmpty()) {
                    ModernGui.drawSectionStringCustomFont((String)selectedPlot.get("description"), this.guiLeft + 43, this.guiTop + 177, 0xBABADA, 0.5f, "left", false, "georamaMedium", 28, 7, 450);
                }
                ClientEventHandler.STYLE.bindTexture("faction_plots");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 282, this.guiTop + 47, 161 * GUI_SCALE, 218 * GUI_SCALE, 170 * GUI_SCALE, 116 * GUI_SCALE, 170, 116, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                int offsetYDetails = 57;
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.plots.type").toUpperCase() + ":", this.guiLeft + 296, this.guiTop + offsetYDetails, 0x7979B7, 0.5f, "left", false, "minecraftDungeons", 22);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("faction.plots.type." + selectedPlot.get("type"))), (float)(this.guiLeft + 296 + 5) + dg22.getStringWidth(I18n.func_135053_a((String)"faction.plots.type").toUpperCase()) / 2.0f, (float)(this.guiTop + offsetYDetails) + 0.5f, 0xDEDEED, 0.5f, "left", false, "georamaSemiBold", 30);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.plots.form.usage").toUpperCase() + ":", this.guiLeft + 296, this.guiTop + (offsetYDetails += 10), 0x7979B7, 0.5f, "left", false, "minecraftDungeons", 22);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("faction.plots.usage." + selectedPlot.get("usage"))), (float)(this.guiLeft + 296 + 5) + dg22.getStringWidth(I18n.func_135053_a((String)"faction.plots.form.usage").toUpperCase()) / 2.0f, (float)(this.guiTop + offsetYDetails) + 0.5f, 0xDEDEED, 0.5f, "left", false, "georamaSemiBold", 30);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.plots.size").toUpperCase() + ":", this.guiLeft + 296, this.guiTop + (offsetYDetails += 20), 0x7979B7, 0.5f, "left", false, "minecraftDungeons", 22);
                ModernGui.drawScaledStringCustomFont((String)selectedPlot.get("size") + " blocs", (float)(this.guiLeft + 296 + 5) + dg22.getStringWidth(I18n.func_135053_a((String)"faction.plots.size").toUpperCase()) / 2.0f, (float)(this.guiTop + offsetYDetails) + 0.5f, 0xDEDEED, 0.5f, "left", false, "georamaSemiBold", 30);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.plots.area").toUpperCase() + ":", this.guiLeft + 296, this.guiTop + (offsetYDetails += 10), 0x7979B7, 0.5f, "left", false, "minecraftDungeons", 22);
                ModernGui.drawScaledStringCustomFont(String.format("%.0f", (Double)selectedPlot.get("area")) + " blocs", (float)(this.guiLeft + 296 + 5) + dg22.getStringWidth(I18n.func_135053_a((String)"faction.plots.area").toUpperCase()) / 2.0f, (float)(this.guiTop + offsetYDetails) + 0.5f, 0xDEDEED, 0.5f, "left", false, "georamaSemiBold", 30);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.plots.price_blocs").toUpperCase() + ":", this.guiLeft + 296, this.guiTop + (offsetYDetails += 10), 0x7979B7, 0.5f, "left", false, "minecraftDungeons", 22);
                ModernGui.drawScaledStringCustomFont(String.format("%.5f", (Double)selectedPlot.get("price") / (Double)selectedPlot.get("area")) + "$/bloc", (float)(this.guiLeft + 296 + 5) + dg22.getStringWidth(I18n.func_135053_a((String)"faction.plots.price_blocs").toUpperCase()) / 2.0f, (float)(this.guiTop + offsetYDetails) + 0.5f, 0xDEDEED, 0.5f, "left", false, "georamaSemiBold", 30);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.plots.position").toUpperCase() + ":", this.guiLeft + 296, this.guiTop + (offsetYDetails += 20), 0x7979B7, 0.5f, "left", false, "minecraftDungeons", 22);
                ModernGui.drawScaledStringCustomFont(((String)selectedPlot.get("position")).replaceAll("#", ", ").replaceAll("world, ", ""), (float)(this.guiLeft + 296 + 5) + dg22.getStringWidth(I18n.func_135053_a((String)"faction.plots.position").toUpperCase()) / 2.0f, (float)(this.guiTop + offsetYDetails) + 0.5f, 0xDEDEED, 0.5f, "left", false, "georamaSemiBold", 30);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.plots.seller").toUpperCase() + ":", this.guiLeft + 296, this.guiTop + (offsetYDetails += 10), 0x7979B7, 0.5f, "left", false, "minecraftDungeons", 22);
                ModernGui.drawScaledStringCustomFont((String)FactionGUI.factionInfos.get("name"), (float)(this.guiLeft + 296 + 5) + dg22.getStringWidth(I18n.func_135053_a((String)"faction.plots.seller").toUpperCase()) / 2.0f, (float)(this.guiTop + offsetYDetails) + 0.5f, 0xDEDEED, 0.5f, "left", false, "georamaSemiBold", 30);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.plots.publish_date").toUpperCase() + ":", this.guiLeft + 296, this.guiTop + (offsetYDetails += 10), 0x7979B7, 0.5f, "left", false, "minecraftDungeons", 22);
                Date date = new Date(((Double)selectedPlot.get("publishTime")).longValue());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyy");
                ModernGui.drawScaledStringCustomFont((Double)selectedPlot.get("publishTime") > 0.0 ? simpleDateFormat.format(date) : "-", (float)(this.guiLeft + 296 + 5) + dg22.getStringWidth(I18n.func_135053_a((String)"faction.plots.publish_date").toUpperCase()) / 2.0f, (float)(this.guiTop + offsetYDetails) + 0.5f, 0xDEDEED, 0.5f, "left", false, "georamaSemiBold", 30);
                String string = (String)selectedPlot.get("owner");
                boolean isOwner = string != null && string.equals(Minecraft.func_71410_x().field_71439_g.field_71092_bJ);
                ClientEventHandler.STYLE.bindTexture("faction_plots");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 282, this.guiTop + 176, 333 * GUI_SCALE, 252 * GUI_SCALE, 170 * GUI_SCALE, 42 * GUI_SCALE, 170, 42, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                if (!string.isEmpty()) {
                    if (!ClientProxy.cacheHeadPlayer.containsKey(string)) {
                        try {
                            ResourceLocation resourceLocation = AbstractClientPlayer.field_110314_b;
                            resourceLocation = AbstractClientPlayer.func_110311_f((String)string);
                            AbstractClientPlayer.func_110304_a((ResourceLocation)resourceLocation, (String)string);
                            ClientProxy.cacheHeadPlayer.put(string, resourceLocation);
                        }
                        catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    } else {
                        Minecraft.func_71410_x().field_71446_o.func_110577_a(ClientProxy.cacheHeadPlayer.get(string));
                        this.field_73882_e.func_110434_K().func_110577_a(ClientProxy.cacheHeadPlayer.get(string));
                        GUIUtils.drawScaledCustomSizeModalRect(this.guiLeft + 322, this.guiTop + 210, 8.0f, 16.0f, 8, -8, -27, -27, 64.0f, 64.0f);
                    }
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.plots.occupant").toUpperCase() + ":", this.guiLeft + 330, this.guiTop + 183, 0x7979B7, 0.5f, "left", false, "minecraftDungeons", 22);
                    ModernGui.drawScaledStringCustomFont(string.isEmpty() ? I18n.func_135053_a((String)"faction.plots.none") : string, (float)(this.guiLeft + 332) + dg22.getStringWidth(I18n.func_135053_a((String)"faction.plots.occupant").toUpperCase() + ":") / 2.0f, (float)this.guiTop + 184.0f, 0xDEDEED, 0.5f, "left", false, "georamaSemiBold", 30);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.plots.from").toUpperCase() + ":", this.guiLeft + 330, this.guiTop + 193, 0x7979B7, 0.5f, "left", false, "minecraftDungeons", 22);
                    if ((Double)selectedPlot.get("transactionTime") != 0.0) {
                        Date transactionDate = new Date(((Double)selectedPlot.get("transactionTime")).longValue());
                        ModernGui.drawScaledStringCustomFont(simpleDateFormat.format(transactionDate), (float)(this.guiLeft + 332) + dg22.getStringWidth(I18n.func_135053_a((String)"faction.plots.from").toUpperCase() + ":") / 2.0f, (float)this.guiTop + 194.0f, 0xDEDEED, 0.5f, "left", false, "georamaSemiBold", 30);
                    } else {
                        ModernGui.drawScaledStringCustomFont("-", (float)(this.guiLeft + 332) + dg22.getStringWidth(I18n.func_135053_a((String)"faction.plots.from").toUpperCase() + ":") / 2.0f, (float)this.guiTop + 194.0f, 0xDEDEED, 0.5f, "left", false, "georamaSemiBold", 30);
                    }
                }
                if (isOwner || selectedPlot.get("owner") != null && !selectedPlot.get("owner").equals("") && selectedPlot.get("creator").equals(FactionGUI.factionInfos.get("id")) && FactionGUI.hasPermissions("locations")) {
                    ClientEventHandler.STYLE.bindTexture("faction_plots");
                    if (mouseX >= this.guiLeft + 420 && mouseX <= this.guiLeft + 440 && mouseY >= this.guiTop + 187 && mouseY <= this.guiTop + 207) {
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 420, this.guiTop + 187, 86 * GUI_SCALE, 53 * GUI_SCALE, 20 * GUI_SCALE, 20 * GUI_SCALE, 20, 20, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                        this.hoveredAction = "edit#" + selectedPlot.get("id");
                    } else {
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 420, this.guiTop + 187, 86 * GUI_SCALE, 77 * GUI_SCALE, 20 * GUI_SCALE, 20 * GUI_SCALE, 20, 20, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                    }
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.plots.total_sent").toUpperCase() + ":", this.guiLeft + 330, this.guiTop + 205, 0x7979B7, 0.5f, "left", false, "minecraftDungeons", 22);
                    ModernGui.drawScaledStringCustomFont(((String)selectedPlot.get("buyType")).equals("rent") ? (selectedPlot.containsKey("rent_total") ? String.format("%.0f", (Double)selectedPlot.get("rent_total")) : "0") : String.format("%.0f", (Double)selectedPlot.get("price")) + "$", (float)(this.guiLeft + 332) + dg22.getStringWidth(I18n.func_135053_a((String)"faction.plots.total_sent").toUpperCase() + ":") / 2.0f, (float)this.guiTop + 205.5f, 0xDEDEED, 0.5f, "left", false, "georamaSemiBold", 30);
                } else if (selectedPlot.get("creator").equals(FactionGUI.factionInfos.get("id")) && FactionGUI.hasPermissions("locations")) {
                    ClientEventHandler.STYLE.bindTexture("faction_plots");
                    boolean hoveredBtnBuy = mouseX >= this.guiLeft + 300 && mouseX <= this.guiLeft + 300 + 110 && mouseY >= this.guiTop + 187 && mouseY <= this.guiTop + 207;
                    ClientEventHandler.STYLE.bindTexture("faction_plots_3");
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 300, this.guiTop + 187, 0 * GUI_SCALE, (hoveredBtnBuy ? 367 : 343) * GUI_SCALE, 110 * GUI_SCALE, 20 * GUI_SCALE, 110, 20, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("faction.plots.label." + selectedPlot.get("buyType"))).toUpperCase() + " " + String.format("%.0f", (Double)selectedPlot.get("price")) + "$", this.guiLeft + 300 + 55, (float)this.guiTop + 191.5f, hoveredBtnBuy ? 0x6E76EE : 0xFFFFFF, 0.5f, "center", false, "minecraftDungeons", 26);
                    if (hoveredBtnBuy) {
                        this.hoveredAction = "buyRent#" + selectedPlot.get("id");
                    }
                    boolean hoveredBtnEdit = mouseX >= this.guiLeft + 415 && mouseX <= this.guiLeft + 415 + 20 && mouseY >= this.guiTop + 187 && mouseY <= this.guiTop + 187 + 20;
                    ClientEventHandler.STYLE.bindTexture("faction_plots");
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 415, this.guiTop + 187, 86 * GUI_SCALE, (hoveredBtnEdit ? 53 : 77) * GUI_SCALE, 20 * GUI_SCALE, 20 * GUI_SCALE, 20, 20, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    if (hoveredBtnEdit) {
                        this.hoveredAction = "edit#" + selectedPlot.get("id");
                        tooltipToDraw = Arrays.asList("\u00a7b" + I18n.func_135053_a((String)"faction.plots.edit"));
                    }
                } else if (string.isEmpty() && ((Boolean)FactionGUI.factionInfos.get("isInCountry")).booleanValue()) {
                    boolean hoveredBtnBuy = mouseX >= this.guiLeft + 300 && mouseX <= this.guiLeft + 300 + 138 && mouseY >= this.guiTop + 187 && mouseY <= this.guiTop + 187 + 20;
                    ClientEventHandler.STYLE.bindTexture("faction_plots_3");
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 300, this.guiTop + 187, 0 * GUI_SCALE, (hoveredBtnBuy ? 419 : 395) * GUI_SCALE, 138 * GUI_SCALE, 20 * GUI_SCALE, 138, 20, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("faction.plots.label." + selectedPlot.get("buyType"))).toUpperCase() + " " + String.format("%.0f", (Double)selectedPlot.get("price")) + "$", this.guiLeft + 300 + 69, (float)this.guiTop + 191.5f, hoveredBtnBuy ? 0x6E76EE : 0xFFFFFF, 0.5f, "center", false, "minecraftDungeons", 26);
                    if (hoveredBtnBuy) {
                        this.hoveredAction = "buyRent#" + selectedPlot.get("id");
                    }
                }
            }
        }
        super.func_73863_a(mouseX, mouseY, partialTick);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
    }

    private float getSlidePlots() {
        return plots.size() > 6 ? (float)(-(plots.size() - 6) * 35) * this.scrollBarPlots.getSliderValue() : 0.0f;
    }

    @Override
    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            this.inputSearchName.func_73793_a(mouseX, mouseY, mouseButton);
            if (!this.hoveredAction.isEmpty()) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                if (this.hoveredAction.contains("filter")) {
                    String filter = this.hoveredAction.replace("filter#", "");
                    if (this.selectedSearchFilters.contains(filter)) {
                        this.selectedSearchFilters.remove(filter);
                    } else {
                        this.selectedSearchFilters.add(filter);
                    }
                } else if (this.hoveredAction.equals("carousel_previous")) {
                    if (!selectedPlot.isEmpty()) {
                        int currentIndex = selectedPlot.containsKey("indexImage") ? (Integer)selectedPlot.get("indexImage") : 0;
                        int newIndex = currentIndex - 1 >= 0 ? currentIndex - 1 : ((ArrayList)selectedPlot.get("images")).size() - 1;
                        selectedPlot.put("indexImage", newIndex);
                    }
                } else if (this.hoveredAction.equals("carousel_next")) {
                    if (!selectedPlot.isEmpty()) {
                        int currentIndex = selectedPlot.containsKey("indexImage") ? (Integer)selectedPlot.get("indexImage") : 0;
                        int newIndex = currentIndex + 1 < ((ArrayList)selectedPlot.get("images")).size() ? currentIndex + 1 : 0;
                        selectedPlot.put("indexImage", newIndex);
                    }
                } else if (this.hoveredAction.equals("back")) {
                    selectedPlot.clear();
                } else if (this.hoveredAction.contains("remove#") || this.hoveredAction.contains("exclude#") || this.hoveredAction.contains("buyRent#")) {
                    String action = this.hoveredAction.split("#")[0];
                    int plotId = (int)Double.parseDouble(this.hoveredAction.split("#")[1]);
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionPlotsActionPacket(plotId, action, new HashMap<String, Object>())));
                    Minecraft.func_71410_x().func_71373_a((GuiScreen)new FactionPlotsGUI());
                } else if (this.hoveredAction.contains("edit#")) {
                    Minecraft.func_71410_x().func_71373_a((GuiScreen)new FactionCreateEditPlotsGUI(((Double)selectedPlot.get("id")).intValue()));
                } else if (this.hoveredAction.equals("create")) {
                    Minecraft.func_71410_x().func_71373_a((GuiScreen)new FactionCreateEditPlotsGUI(-1));
                }
            } else if (!hoveredPlot.isEmpty()) {
                selectedPlot = (HashMap)hoveredPlot.clone();
                hoveredPlot.clear();
            }
        }
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    public void func_73876_c() {
        this.inputSearchName.func_73780_a();
    }

    protected void func_73869_a(char par1, int par2) {
        this.inputSearchName.func_73802_a(par1, par2);
        super.func_73869_a(par1, par2);
    }
}

