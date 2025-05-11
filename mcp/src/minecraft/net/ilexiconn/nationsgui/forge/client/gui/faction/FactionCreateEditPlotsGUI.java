/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.AbstractClientPlayer
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiTextField
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.util.ChatMessageComponent
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import net.halalaboos.cfont.CFontRenderer;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.CustomTextAreaGUI;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarGeneric;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionPlotsGUI;
import net.ilexiconn.nationsgui.forge.client.gui.main.component.CustomInputFieldGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionPlotDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionPlotsActionPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class FactionCreateEditPlotsGUI
extends GuiScreen {
    public static int GUI_SCALE = 3;
    public String selectedType = "land";
    public static CFontRenderer dg22 = ModernGui.getCustomFont("minecraftDungeons", 22);
    public static CFontRenderer semiBold24 = ModernGui.getCustomFont("georamaSemiBold", 24);
    public static HashMap<String, Object> savedPlotForSelection = new HashMap();
    public static List<String> tooltipToDraw = new ArrayList<String>();
    public static List<String> types = Arrays.asList("house", "apartment", "land", "other");
    public static List<String> usages = Arrays.asList("all", "interact");
    public static List<String> modes = Arrays.asList("sell", "rent");
    public static HashMap<String, Object> editedPlot = new HashMap();
    public static boolean loaded = false;
    public String hoveredAction = "";
    public String selectedUsage = "all";
    public String selectedMode = "rent";
    private RenderItem itemRenderer = new RenderItem();
    public String hoveredType = "";
    public String hoveredUsage = "";
    public String hoveredMode = "";
    public String editionMode = "creator";
    public boolean allowCoOwner = false;
    public int rentDaysToAdd = 0;
    protected int xSize = 435;
    protected int ySize = 199;
    protected int guiLeft;
    protected int guiTop;
    private GuiTextField inputName;
    private GuiTextField inputDescription;
    private GuiTextField inputPrice;
    private GuiTextField inputCoOwner;
    private GuiScrollBarGeneric scrollBarCoowners;
    private boolean dataLoaded = false;

    public FactionCreateEditPlotsGUI(int plotId) {
        editedPlot = new HashMap();
        this.dataLoaded = false;
        if (plotId != -1) {
            loaded = false;
            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionPlotDataPacket(plotId)));
        } else {
            loaded = true;
        }
    }

    public static boolean isValidNonNegativeInteger(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            int number = Integer.parseInt(str);
            return number >= 0;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        this.guiLeft = (this.field_73880_f - this.xSize) / 2;
        this.guiTop = (this.field_73881_g - this.ySize) / 2;
        this.scrollBarCoowners = new GuiScrollBarGeneric(this.guiLeft + 95, this.guiTop + 125, 33, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_parrot.png"), 2, 8);
        this.inputName = new CustomInputFieldGUI(this.guiLeft + 12, this.guiTop + 47, 90, 12, "georamaMedium", 28);
        this.inputName.func_73804_f(24);
        this.inputDescription = new CustomTextAreaGUI(this.guiLeft + 12, this.guiTop + 80, 185, "georamaMedium", 22, 10);
        this.inputDescription.func_73804_f(200);
        this.inputPrice = new CustomInputFieldGUI(this.guiLeft + 113, this.guiTop + 47, 90, 12, "georamaMedium", 28);
        this.inputPrice.func_73804_f(8);
        this.inputPrice.func_73782_a("0");
        this.inputCoOwner = new CustomInputFieldGUI(this.guiLeft + 21, this.guiTop + 160, 64, 12, "georamaMedium", 28);
        this.inputCoOwner.func_73804_f(16);
    }

    private void updateInputValueWithData() {
        if (!this.dataLoaded && !editedPlot.isEmpty()) {
            this.inputName.func_73782_a(editedPlot.containsKey("name") ? (String)editedPlot.get("name") : "");
            this.inputDescription.func_73782_a(editedPlot.containsKey("description") ? (String)editedPlot.get("description") : "");
            this.inputPrice.func_73782_a(editedPlot.containsKey("price") ? ((Double)editedPlot.get("price")).intValue() + "" : "0");
            this.dataLoaded = true;
            this.selectedType = (String)editedPlot.get("type");
            this.selectedUsage = (String)editedPlot.get("usage");
            this.selectedMode = (String)editedPlot.get("buyType");
            this.allowCoOwner = (Boolean)editedPlot.get("canAddCoOwner");
            if (editedPlot.get("owner") != null && !editedPlot.get("owner").equals(Minecraft.func_71410_x().field_71439_g.field_71092_bJ) && FactionGUI.hasPermissions("locations")) {
                this.editionMode = "creator";
            } else if (editedPlot.get("owner") != null && editedPlot.get("owner").equals(Minecraft.func_71410_x().field_71439_g.field_71092_bJ)) {
                this.editionMode = "owner";
            }
        }
    }

    private float getSlidePlots() {
        if (editedPlot.isEmpty()) {
            return 0.0f;
        }
        List whitelist = (List)editedPlot.get("whitelisted_players");
        return whitelist.size() > 4 ? (float)(-(whitelist.size() - 4) * 9) * this.scrollBarCoowners.getSliderValue() : 0.0f;
    }

    public void func_73863_a(int mouseX, int mouseY, float partialTick) {
        String action;
        boolean hoveringBtnCancel;
        boolean bl;
        this.func_73873_v_();
        tooltipToDraw = new ArrayList<String>();
        this.hoveredAction = "";
        this.hoveredType = "";
        this.hoveredUsage = "";
        this.hoveredMode = "";
        this.updateInputValueWithData();
        Gui.func_73734_a((int)0, (int)0, (int)this.field_73880_f, (int)this.field_73881_g, (int)-2145049275);
        ClientEventHandler.STYLE.bindTexture("faction_plots_3");
        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft, this.guiTop + 25, 0 * GUI_SCALE, 0 * GUI_SCALE, this.xSize * GUI_SCALE, (this.ySize - 25) * GUI_SCALE, this.xSize, this.ySize - 25, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
        if (!editedPlot.isEmpty()) {
            ClientEventHandler.STYLE.bindTexture("faction_plots_3");
            boolean hoveringReturn = mouseX >= this.field_73880_f - 85 && mouseX < this.field_73880_f - 85 + 57 && mouseY >= 4 && mouseY < 20;
            ModernGui.drawScaledCustomSizeModalRect(this.field_73880_f - 85, 4.0f, 400 * GUI_SCALE, (hoveringReturn ? 235 : 215) * GUI_SCALE, 57 * GUI_SCALE, 16 * GUI_SCALE, 57, 16, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.plots.form.return"), this.field_73880_f - 85 + 21, 9.0f, hoveringReturn ? 3026518 : 0xFFFFFF, 0.5f, "left", false, "georamaSemiBold", 24);
            if (hoveringReturn) {
                this.hoveredAction = "return";
            }
        }
        ClientEventHandler.STYLE.bindTexture("faction_plots_3");
        boolean hoveringClose = mouseX >= this.field_73880_f - 22 && mouseX < this.field_73880_f - 22 + 16 && mouseY >= 4 && mouseY < 20;
        ModernGui.drawScaledCustomSizeModalRect(this.field_73880_f - 22, 4.0f, 465 * GUI_SCALE, (hoveringClose ? 235 : 215) * GUI_SCALE, 16 * GUI_SCALE, 16 * GUI_SCALE, 16, 16, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
        if (hoveringClose) {
            this.hoveredAction = "close";
        }
        ModernGui.drawScaledStringCustomFont(editedPlot.containsKey("name") ? I18n.func_135053_a((String)"faction.plots.plot") + " " + editedPlot.get("name") : I18n.func_135053_a((String)"faction.plots.new_plot"), this.guiLeft + 0, this.guiTop + 0, 0xFFFFFF, 1.0f, "left", false, "minecraftDungeons", 22);
        if (!editedPlot.isEmpty() && !editedPlot.get("owner").equals("") && FactionGUI.hasPermissions("locations")) {
            boolean hovering;
            ClientEventHandler.STYLE.bindTexture("faction_plots_3");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + this.xSize - 65, this.guiTop, 316 * GUI_SCALE, (this.editionMode.equals("owner") ? 235 : 215) * GUI_SCALE, 65 * GUI_SCALE, 16 * GUI_SCALE, 65, 16, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("faction.plots.form.editionMode." + this.editionMode)), this.guiLeft + this.xSize - 65 + (this.editionMode.equals("owner") ? 20 : 10), this.guiTop + 5, 0xFFFFFF, 0.5f, "left", false, "georamaSemiBold", 24);
            boolean bl2 = hovering = mouseX >= this.guiLeft + this.xSize - 65 && mouseX < this.guiLeft + this.xSize && mouseY >= this.guiTop && mouseY < this.guiTop + 16;
            if (hovering) {
                this.hoveredAction = "toggleEditionMode";
            }
        }
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.plots.form.name"), this.guiLeft + 12, this.guiTop + 38, 0xFFFFFF, 0.5f, "left", false, "georamaSemiBold", 24);
        this.inputName.func_73795_f();
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.plots.form.price"), this.guiLeft + 112, this.guiTop + 38, 0xFFFFFF, 0.5f, "left", false, "georamaSemiBold", 24);
        this.inputPrice.func_73795_f();
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.plots.form.description"), this.guiLeft + 12, this.guiTop + 73, 0xFFFFFF, 0.5f, "left", false, "georamaSemiBold", 24);
        this.inputDescription.func_73795_f();
        if (editedPlot.isEmpty() || FactionGUI.hasPermissions("locations") && this.editionMode.equals("creator")) {
            boolean hoveringToggleCoOwner;
            boolean hovering;
            ClientEventHandler.STYLE.bindTexture("faction_plots_3");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 12, this.guiTop + 117, 0 * GUI_SCALE, 190 * GUI_SCALE, 193 * GUI_SCALE, 66 * GUI_SCALE, 193, 66, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.plots.form.type"), this.guiLeft + 12, this.guiTop + 110, 0xFFFFFF, 0.5f, "left", false, "georamaSemiBold", 22);
            int index = 0;
            for (String string : types) {
                ClientEventHandler.STYLE.bindTexture("faction_plots_3");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 20 + index / 2 * 50, this.guiTop + 123 + index % 2 * 14, (this.selectedType.equals(string) ? 202 : 212) * GUI_SCALE, 187 * GUI_SCALE, 6 * GUI_SCALE, 6 * GUI_SCALE, 6, 6, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("faction.plots.type." + string)), this.guiLeft + 28 + index / 2 * 50, this.guiTop + 123 + 1 + index % 2 * 14, 0xFFFFFF, 0.5f, "left", false, "georamaSemiBold", 20);
                boolean bl3 = hovering = mouseX >= this.guiLeft + 20 + index / 2 * 50 && mouseX < this.guiLeft + 20 + index / 2 * 50 + 6 && mouseY >= this.guiTop + 123 + index % 2 * 14 && mouseY < this.guiTop + 123 + index % 2 * 14 + 6;
                if (hovering) {
                    this.hoveredType = string;
                }
                ++index;
            }
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.plots.form.usage"), this.guiLeft + 112, this.guiTop + 110, 0xFFFFFF, 0.5f, "left", false, "georamaSemiBold", 22);
            index = 0;
            for (String string : usages) {
                ClientEventHandler.STYLE.bindTexture("faction_plots_3");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 120, this.guiTop + 123 + index % 2 * 14, (this.selectedUsage.equals(string) ? 202 : 212) * GUI_SCALE, 187 * GUI_SCALE, 6 * GUI_SCALE, 6 * GUI_SCALE, 6, 6, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("faction.plots.usage." + string)), this.guiLeft + 120 + 8, this.guiTop + 123 + 1 + index % 2 * 14, 0xFFFFFF, 0.5f, "left", false, "georamaSemiBold", 20);
                boolean bl4 = hovering = mouseX >= this.guiLeft + 120 && mouseX < this.guiLeft + 120 + 6 && mouseY >= this.guiTop + 123 + index % 2 * 14 && mouseY < this.guiTop + 123 + index % 2 * 14 + 6;
                if (hovering) {
                    this.hoveredUsage = string;
                }
                ++index;
            }
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.plots.form.coowner"), this.guiLeft + 12, this.guiTop + 153, 0xFFFFFF, 0.5f, "left", false, "georamaSemiBold", 22);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.plots.label.allow_coowner"), this.guiLeft + 20, this.guiTop + 169, 0xFFFFFF, 0.5f, "left", false, "georamaSemiBold", 20);
            ClientEventHandler.STYLE.bindTexture("faction_plots_3");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 81, this.guiTop + 168, (this.allowCoOwner ? 288 : 271) * GUI_SCALE, 195 * GUI_SCALE, 15 * GUI_SCALE, 7 * GUI_SCALE, 15, 7, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
            boolean bl5 = hoveringToggleCoOwner = mouseX >= this.guiLeft + 81 && mouseX < this.guiLeft + 81 + 15 && mouseY >= this.guiTop + 168 && mouseY < this.guiTop + 168 + 7;
            if (hoveringToggleCoOwner) {
                this.hoveredAction = "toggleCoOwner";
            }
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.plots.form.mode"), this.guiLeft + 112, this.guiTop + 153, 0xFFFFFF, 0.5f, "left", false, "georamaSemiBold", 22);
            index = 0;
            for (String mode : modes) {
                boolean hovering2;
                ClientEventHandler.STYLE.bindTexture("faction_plots_3");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 120 + index * 50, this.guiTop + 168, (this.selectedMode.equals(mode) ? 202 : 212) * GUI_SCALE, 187 * GUI_SCALE, 6 * GUI_SCALE, 6 * GUI_SCALE, 6, 6, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("faction.plots.mode." + mode)), this.guiLeft + 120 + 8 + index * 50, this.guiTop + 168 + 1, 0xFFFFFF, 0.5f, "left", false, "georamaSemiBold", 20);
                boolean bl6 = hovering2 = mouseX >= this.guiLeft + 120 + index * 50 && mouseX < this.guiLeft + 120 + index * 50 + 6 && mouseY >= this.guiTop + 168 && mouseY < this.guiTop + 168 + 6;
                if (hovering2) {
                    this.hoveredMode = mode;
                }
                ++index;
            }
        } else if (!editedPlot.isEmpty()) {
            ClientEventHandler.STYLE.bindTexture("faction_plots_3");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 12, this.guiTop + 117, 0 * GUI_SCALE, 265 * GUI_SCALE, 93 * GUI_SCALE, 64 * GUI_SCALE, 93, 64, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.plots.form.coowners"), this.guiLeft + 12, this.guiTop + 109, (Boolean)editedPlot.get("canAddCoOwner") != false ? 0xFFFFFF : 0x111126, 0.5f, "left", false, "georamaSemiBold", 24);
            if (((Boolean)editedPlot.get("canAddCoOwner")).booleanValue()) {
                boolean bl7;
                GUIUtils.startGLScissor(this.guiLeft + 20, this.guiTop + 125, 72, 33);
                List whitelist = (List)editedPlot.get("whitelisted_players");
                int index = 0;
                for (String coOwner : whitelist) {
                    int offsetX = this.guiLeft + 20;
                    Float offsetY = Float.valueOf((float)(this.guiTop + 125 + index * 9) + this.getSlidePlots());
                    if (!ClientProxy.cacheHeadPlayer.containsKey(coOwner)) {
                        try {
                            ResourceLocation resourceLocation = AbstractClientPlayer.field_110314_b;
                            resourceLocation = AbstractClientPlayer.func_110311_f((String)coOwner);
                            AbstractClientPlayer.func_110304_a((ResourceLocation)resourceLocation, (String)coOwner);
                            ClientProxy.cacheHeadPlayer.put(coOwner, resourceLocation);
                        }
                        catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    } else {
                        Minecraft.func_71410_x().field_71446_o.func_110577_a(ClientProxy.cacheHeadPlayer.get(coOwner));
                        this.field_73882_e.func_110434_K().func_110577_a(ClientProxy.cacheHeadPlayer.get(coOwner));
                        GUIUtils.drawScaledCustomSizeModalRect(offsetX + 8, offsetY.intValue() + 6, 8.0f, 16.0f, 8, -8, -6, -6, 64.0f, 64.0f);
                    }
                    ModernGui.drawScaledStringCustomFont(coOwner, offsetX + 10, offsetY.floatValue(), 0xFFFFFF, 0.5f, "left", false, "georamaSemiBold", 20);
                    ClientEventHandler.STYLE.bindTexture("faction_plots_3");
                    boolean hoveringKick = mouseX >= offsetX + 65 && mouseX < offsetX + 65 + 6 && (float)mouseY >= offsetY.floatValue() && (float)mouseY < offsetY.floatValue() + 6.0f;
                    ModernGui.drawScaledCustomSizeModalRect(offsetX + 65, offsetY.floatValue(), (hoveringKick ? 262 : 254) * GUI_SCALE, 195 * GUI_SCALE, 6 * GUI_SCALE, 6 * GUI_SCALE, 6, 6, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    if (hoveringKick) {
                        this.hoveredAction = "kickCoowner#" + coOwner;
                    }
                    ++index;
                }
                GUIUtils.endGLScissor();
                this.scrollBarCoowners.draw(mouseX, mouseY);
                boolean bl8 = bl7 = (float)mouseX >= (float)this.guiLeft + 86.5f && (float)mouseX < (float)this.guiLeft + 86.5f + 11.0f && mouseY >= this.guiTop + 163 && mouseY < this.guiTop + 163 + 10;
                if (bl7) {
                    ClientEventHandler.STYLE.bindTexture("faction_plots_3");
                    ModernGui.drawScaledCustomSizeModalRect((float)this.guiLeft + 86.5f, this.guiTop + 163, 242 * GUI_SCALE, 196 * GUI_SCALE, 11 * GUI_SCALE, 10 * GUI_SCALE, 11, 10, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    this.hoveredAction = "addCoowner#" + this.inputCoOwner.func_73781_b();
                }
                this.inputCoOwner.func_73795_f();
            } else {
                ClientEventHandler.STYLE.bindTexture("faction_plots_3");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 12, this.guiTop + 117, 202 * GUI_SCALE, 394 * GUI_SCALE, 93 * GUI_SCALE, 64 * GUI_SCALE, 93, 64, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                if (mouseX >= this.guiLeft + 12 && mouseX < this.guiLeft + 12 + 93 && mouseY >= this.guiTop + 117 && mouseY < this.guiTop + 117 + 64) {
                    tooltipToDraw.add(I18n.func_135053_a((String)"faction.plots.tooltip.coowner_disabled"));
                }
            }
            if (editedPlot.get("buyType").equals("rent")) {
                boolean hoveringPlusBtn;
                boolean hoveringMinusBtn;
                ClientEventHandler.STYLE.bindTexture("faction_plots_3");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 112, this.guiTop + 117, 100 * GUI_SCALE, 265 * GUI_SCALE, 93 * GUI_SCALE, 64 * GUI_SCALE, 93, 64, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                ModernGui.drawScaledStringCustomFont(this.rentDaysToAdd + " " + I18n.func_135053_a((String)"faction.plots.label.days"), this.guiLeft + 159, this.guiTop + 133, 0xFFFFFF, 0.5f, "center", false, "georamaSemiBold", 22);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.plots.label.rent_duration"), this.guiLeft + 120, this.guiTop + 150, 0x6E76EE, 0.5f, "left", false, "georamaSemiBold", 24);
                ModernGui.drawScaledStringCustomFont(String.format("%.0f", (Double)editedPlot.get("rent_left_days")) + " " + I18n.func_135053_a((String)"faction.plots.label.days"), (float)(this.guiLeft + 120 + 3) + semiBold24.getStringWidth(I18n.func_135053_a((String)"faction.plots.label.rent_duration")) / 2.0f, this.guiTop + 150, 0xDEDEED, 0.5f, "left", false, "georamaSemiBold", 24);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.plots.label.rent_price"), this.guiLeft + 120, this.guiTop + 159, 0x6E76EE, 0.5f, "left", false, "georamaSemiBold", 24);
                ModernGui.drawScaledStringCustomFont(String.format("%.0f", (Double)editedPlot.get("price")) + "$", (float)(this.guiLeft + 120 + 3) + semiBold24.getStringWidth(I18n.func_135053_a((String)"faction.plots.label.rent_price")) / 2.0f, this.guiTop + 159, 0xDEDEED, 0.5f, "left", false, "georamaSemiBold", 24);
                if (this.rentDaysToAdd > 0) {
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.plots.label.rent_extend_price"), this.guiLeft + 120, this.guiTop + 168, 16000586, 0.5f, "left", false, "georamaSemiBold", 24);
                    ModernGui.drawScaledStringCustomFont(String.format("%.0f", (Double)editedPlot.get("price") * (double)this.rentDaysToAdd) + "$", (float)(this.guiLeft + 120 + 3) + semiBold24.getStringWidth(I18n.func_135053_a((String)"faction.plots.label.rent_extend_price")) / 2.0f, this.guiTop + 168, 0xDEDEED, 0.5f, "left", false, "georamaSemiBold", 24);
                }
                boolean bl9 = hoveringMinusBtn = mouseX >= this.guiLeft + 120 && mouseX < this.guiLeft + 120 + 16 && mouseY >= this.guiTop + 124 && mouseY < this.guiTop + 124 + 16;
                if (hoveringMinusBtn) {
                    ClientEventHandler.STYLE.bindTexture("faction_plots_3");
                    ModernGui.drawScaledCustomSizeModalRect((float)this.guiLeft + 120.5f, this.guiTop + 128, 222 * GUI_SCALE, 196 * GUI_SCALE, 17 * GUI_SCALE, 16 * GUI_SCALE, 17, 16, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    this.hoveredAction = "rentMinus";
                }
                boolean bl10 = hoveringPlusBtn = mouseX >= this.guiLeft + 181 && mouseX < this.guiLeft + 181 + 16 && mouseY >= this.guiTop + 124 && mouseY < this.guiTop + 124 + 16;
                if (hoveringPlusBtn) {
                    ClientEventHandler.STYLE.bindTexture("faction_plots_3");
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 181, this.guiTop + 128, 202 * GUI_SCALE, 196 * GUI_SCALE, 17 * GUI_SCALE, 16 * GUI_SCALE, 17, 16, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    this.hoveredAction = "rentPlus";
                }
            }
        }
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.plots.size") + ":", this.guiLeft + 245, this.guiTop + 45, 0x7979B7, 0.5f, "left", false, "georamaSemiBold", 25);
        ModernGui.drawScaledStringCustomFont(editedPlot.containsKey("size") ? (String)editedPlot.get("size") + " blocs" : "-", (float)(this.guiLeft + 245 + 5) + semiBold24.getStringWidth(I18n.func_135053_a((String)"faction.plots.size")) / 2.0f, this.guiTop + 45, 0xDEDEED, 0.5f, "left", false, "georamaSemiBold", 25);
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.plots.area") + ":", this.guiLeft + 245, this.guiTop + 55, 0x7979B7, 0.5f, "left", false, "georamaSemiBold", 25);
        ModernGui.drawScaledStringCustomFont(editedPlot.containsKey("area") ? String.format("%.0f", (Double)editedPlot.get("area")) + " blocs" : "-", (float)(this.guiLeft + 245 + 5) + semiBold24.getStringWidth(I18n.func_135053_a((String)"faction.plots.area")) / 2.0f, this.guiTop + 55, 0xDEDEED, 0.5f, "left", false, "georamaSemiBold", 25);
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.plots.price_blocs") + ":", this.guiLeft + 245, this.guiTop + 65, 0x7979B7, 0.5f, "left", false, "georamaSemiBold", 25);
        ModernGui.drawScaledStringCustomFont(editedPlot.containsKey("price") && editedPlot.containsKey("area") ? String.format("%.5f", (Double)editedPlot.get("price") / (Double)editedPlot.get("area")) + "$/bloc" : "-", (float)(this.guiLeft + 245 + 5) + semiBold24.getStringWidth(I18n.func_135053_a((String)"faction.plots.price_blocs")) / 2.0f, this.guiTop + 65, 0xDEDEED, 0.5f, "left", false, "georamaSemiBold", 25);
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.plots.position") + ":", this.guiLeft + 245, this.guiTop + 75, 0x7979B7, 0.5f, "left", false, "georamaSemiBold", 25);
        ModernGui.drawScaledStringCustomFont(editedPlot.containsKey("position") ? ((String)editedPlot.get("position")).replaceAll("#", ", ").replaceAll("world, ", "") : "-", (float)(this.guiLeft + 245 + 5) + semiBold24.getStringWidth(I18n.func_135053_a((String)"faction.plots.position")) / 2.0f, this.guiTop + 75, 0xDEDEED, 0.5f, "left", false, "georamaSemiBold", 25);
        boolean hoveringBtnprevisualize = mouseX >= this.guiLeft + 245 && mouseX < this.guiLeft + 245 + 81 && mouseY >= this.guiTop + 90 && mouseY < this.guiTop + 90 + 17;
        ClientEventHandler.STYLE.bindTexture("faction_plots_3");
        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 245, this.guiTop + 90, (editedPlot.isEmpty() ? 248 : (hoveringBtnprevisualize ? 202 : 291)) * GUI_SCALE, (editedPlot.isEmpty() ? 276 : (hoveringBtnprevisualize ? 368 : 344)) * GUI_SCALE, 81 * GUI_SCALE, 17 * GUI_SCALE, 81, 17, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)(editedPlot.containsKey("playerHasPrevisualization") && (Boolean)editedPlot.get("playerHasPrevisualization") != false ? "faction.plots.stop_previsualize" : "faction.plots.previsualize")), this.guiLeft + 245 + 40, this.guiTop + 95, editedPlot.isEmpty() ? 0 : (hoveringBtnprevisualize ? 0x6E76EE : 0xFFFFFF), 0.5f, "center", false, "georamaSemiBold", 25);
        if (hoveringBtnprevisualize && !editedPlot.isEmpty()) {
            this.hoveredAction = "previsualize";
        }
        if (this.editionMode.equals("creator")) {
            boolean hoveringBtnDefine = mouseX >= this.guiLeft + 334 && mouseX < this.guiLeft + 334 + 81 && mouseY >= this.guiTop + 90 && mouseY < this.guiTop + 90 + 17;
            ClientEventHandler.STYLE.bindTexture("faction_plots_3");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 334, this.guiTop + 90, (!hoveringBtnDefine || this.isFormValid() ? 202 : 248) * GUI_SCALE, (hoveringBtnDefine ? (this.isFormValid() ? 368 : 276) : 344) * GUI_SCALE, 81 * GUI_SCALE, 17 * GUI_SCALE, 81, 17, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.plots.define"), this.guiLeft + 334 + 40, this.guiTop + 95, hoveringBtnDefine ? (this.isFormValid() ? 0x6E76EE : 0) : 0xFFFFFF, 0.5f, "center", false, "georamaSemiBold", 25);
            if (hoveringBtnDefine) {
                if (this.isFormValid()) {
                    this.hoveredAction = "define";
                } else {
                    tooltipToDraw.add(I18n.func_135053_a((String)"faction.plots.form.error"));
                }
            }
        }
        if (editedPlot.get("images") != null && !((ArrayList)editedPlot.get("images")).isEmpty()) {
            for (int i = 0; i < Math.min(3, ((ArrayList)editedPlot.get("images")).size()); ++i) {
                boolean hoveringImage;
                Float f = Float.valueOf((float)(this.guiLeft + 237) + (float)i * 47.5f);
                ModernGui.drawScaledModalRectWithCustomSizedRemoteTexture(f.intValue(), this.guiTop + 123, 54 * GUI_SCALE, 0, 116 * GUI_SCALE, 116 * GUI_SCALE, 43, 39, 225 * GUI_SCALE, 116 * GUI_SCALE, true, (String)((ArrayList)editedPlot.get("images")).get(i));
                boolean bl11 = hoveringImage = (float)mouseX >= f.floatValue() && (float)mouseX < f.floatValue() + 43.0f && mouseY >= this.guiTop + 123 && mouseY < this.guiTop + 123 + 39;
                if (!hoveringImage) continue;
                Gui.func_73734_a((int)f.intValue(), (int)(this.guiTop + 123), (int)(f.intValue() + 43), (int)(this.guiTop + 123 + 39), (int)-2131483062);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                ClientEventHandler.STYLE.bindTexture("faction_plots_3");
                ModernGui.drawScaledCustomSizeModalRect(f.intValue() + 15, this.guiTop + 123 + 12, 318 * GUI_SCALE, 190 * GUI_SCALE, 14 * GUI_SCALE, 15 * GUI_SCALE, 14, 15, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                this.hoveredAction = "removeImage#" + i;
            }
        }
        boolean isHoveringAddImageBtn = mouseX >= this.guiLeft + 380 && (float)mouseX < (float)(this.guiLeft + 380) + 43.0f && mouseY >= this.guiTop + 123 && mouseY < this.guiTop + 123 + 39;
        ClientEventHandler.STYLE.bindTexture("faction_plots_3");
        if (isHoveringAddImageBtn) {
            if (editedPlot.containsKey("images") && ((ArrayList)editedPlot.get("images")).size() < 3) {
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 380, this.guiTop + 123, 202 * GUI_SCALE, 255 * GUI_SCALE, 47 * GUI_SCALE, 39 * GUI_SCALE, 47, 39, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                this.hoveredAction = "addImage";
            } else {
                tooltipToDraw.add(I18n.func_135053_a((String)"faction.plots.max_images"));
            }
        }
        boolean bl12 = bl = mouseX >= this.guiLeft + 225 && mouseX < this.guiLeft + 225 + 102 && mouseY >= this.guiTop + 182 && mouseY < this.guiTop + 182 + 17;
        if (bl) {
            if (this.isFormValid()) {
                ClientEventHandler.STYLE.bindTexture("faction_plots_3");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 225, this.guiTop + 182, 202 * GUI_SCALE, 215 * GUI_SCALE, 102 * GUI_SCALE, 17 * GUI_SCALE, 102, 17, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                this.hoveredAction = "validate";
            } else {
                ClientEventHandler.STYLE.bindTexture("faction_plots_3");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 225, this.guiTop + 182, 248 * GUI_SCALE, 255 * GUI_SCALE, 102 * GUI_SCALE, 17 * GUI_SCALE, 102, 17, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                tooltipToDraw.add(I18n.func_135053_a((String)"faction.plots.form.error"));
            }
        }
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)(this.rentDaysToAdd > 0 ? "faction.plots.validate_and_pay" : "faction.plots.validate")), this.guiLeft + 225 + 51, this.guiTop + 186, bl ? (this.isFormValid() ? 0x6E76EE : 0) : 0xFFFFFF, 0.5f, "center", false, "minecraftDungeons", 21);
        boolean bl13 = hoveringBtnCancel = mouseX >= this.guiLeft + 333 && mouseX < this.guiLeft + 333 + 102 && mouseY >= this.guiTop + 182 && mouseY < this.guiTop + 182 + 17;
        String string = editedPlot.isEmpty() ? "cancel" : (action = !editedPlot.get("owner").equals("") ? "stop" : "delete");
        if (hoveringBtnCancel) {
            ClientEventHandler.STYLE.bindTexture("faction_plots_3");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 333, this.guiTop + 182, 202 * GUI_SCALE, 235 * GUI_SCALE, 102 * GUI_SCALE, 17 * GUI_SCALE, 102, 17, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
            this.hoveredAction = action;
        }
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("faction.plots.btn." + action)), this.guiLeft + 333 + 51, this.guiTop + 186, hoveringBtnCancel ? 0xFFFFFF : 16000586, 0.5f, "center", false, "minecraftDungeons", 21);
        if (tooltipToDraw != null && !tooltipToDraw.isEmpty()) {
            this.drawHoveringText(tooltipToDraw, mouseX, mouseY, this.field_73886_k);
        }
        super.func_73863_a(mouseX, mouseY, partialTick);
    }

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            if (this.editionMode.equals("creator")) {
                this.inputName.func_73793_a(mouseX, mouseY, mouseButton);
                this.inputPrice.func_73793_a(mouseX, mouseY, mouseButton);
            }
            if (editedPlot.get("owner") != null && editedPlot.get("owner").equals(Minecraft.func_71410_x().field_71439_g.field_71092_bJ)) {
                this.inputCoOwner.func_73793_a(mouseX, mouseY, mouseButton);
            }
            this.inputDescription.func_73793_a(mouseX, mouseY, mouseButton);
            if (!this.hoveredAction.isEmpty()) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                if (this.hoveredAction.equals("toggleEditionMode")) {
                    this.editionMode = this.editionMode.equals("owner") ? "creator" : "owner";
                } else if (this.hoveredAction.equals("close")) {
                    this.field_73882_e.func_71373_a(null);
                } else if (this.hoveredAction.equals("return")) {
                    this.field_73882_e.func_71373_a((GuiScreen)new FactionPlotsGUI());
                } else if (this.hoveredAction.equals("toggleCoOwner")) {
                    this.allowCoOwner = !this.allowCoOwner;
                } else if (this.hoveredAction.startsWith("removeImage")) {
                    int index = Integer.parseInt(this.hoveredAction.split("#")[1]);
                    ((ArrayList)editedPlot.get("images")).remove(index);
                } else if (this.hoveredAction.equals("validate")) {
                    if (this.isFormValid()) {
                        HashMap<String, Object> data = new HashMap<String, Object>();
                        data.put("name", this.inputName.func_73781_b());
                        data.put("description", this.inputDescription.func_73781_b());
                        data.put("price", Double.parseDouble(this.inputPrice.func_73781_b()));
                        data.put("type", this.selectedType);
                        data.put("usage", this.selectedUsage);
                        data.put("buyType", this.selectedMode);
                        data.put("canAddCoOwner", this.allowCoOwner);
                        data.put("whitelisted_players", editedPlot.containsKey("whitelisted_players") ? editedPlot.get("whitelisted_players") : new ArrayList());
                        data.put("images", editedPlot.containsKey("images") ? editedPlot.get("images") : new ArrayList());
                        data.put("rentDaysToAdd", this.rentDaysToAdd);
                        if (editedPlot.isEmpty()) {
                            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionPlotsActionPacket(-1, "create", data)));
                            this.field_73882_e.func_71373_a(null);
                        } else {
                            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionPlotsActionPacket(((Double)editedPlot.get("id")).intValue(), "edit", data)));
                            this.field_73882_e.func_71373_a((GuiScreen)new FactionPlotsGUI());
                        }
                    }
                } else if (this.hoveredAction.equals("cancel")) {
                    this.field_73882_e.func_71373_a((GuiScreen)new FactionPlotsGUI());
                } else if (this.hoveredAction.equals("stop") || this.hoveredAction.equals("delete")) {
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionPlotsActionPacket(((Double)editedPlot.get("id")).intValue(), this.hoveredAction, new HashMap<String, Object>())));
                    this.field_73882_e.func_71373_a((GuiScreen)new FactionPlotsGUI());
                } else if (this.hoveredAction.equals("define")) {
                    if (this.isFormValid()) {
                        HashMap<String, Object> data = new HashMap<String, Object>();
                        data.put("id", !editedPlot.isEmpty() ? ((Double)editedPlot.get("id")).intValue() : -1);
                        data.put("name", this.inputName.func_73781_b());
                        data.put("description", this.inputDescription.func_73781_b());
                        data.put("price", Double.parseDouble(this.inputPrice.func_73781_b()));
                        data.put("type", this.selectedType);
                        data.put("usage", this.selectedUsage);
                        data.put("buyType", this.selectedMode);
                        data.put("canAddCoOwner", this.allowCoOwner);
                        data.put("whitelisted_players", editedPlot.containsKey("whitelisted_players") ? editedPlot.get("whitelisted_players") : new ArrayList());
                        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionPlotsActionPacket(editedPlot.containsKey("id") ? ((Double)editedPlot.get("id")).intValue() : -1, "define", data)));
                        this.field_73882_e.func_71373_a(null);
                    }
                } else if (this.hoveredAction.equals("previsualize")) {
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionPlotsActionPacket(((Double)editedPlot.get("id")).intValue(), "previsualize", new HashMap<String, Object>())));
                    if (!editedPlot.containsKey("playerHasPrevisualization") || !((Boolean)editedPlot.get("playerHasPrevisualization")).booleanValue()) {
                        this.field_73882_e.func_71373_a(null);
                    }
                } else if (this.hoveredAction.contains("addCoowner")) {
                    if (editedPlot.get("owner") != null && editedPlot.get("owner").equals(Minecraft.func_71410_x().field_71439_g.field_71092_bJ) && !this.inputCoOwner.func_73781_b().isEmpty()) {
                        String coOwner = this.hoveredAction.split("#")[1];
                        if (!((List)editedPlot.get("whitelisted_players")).contains(coOwner)) {
                            ((List)editedPlot.get("whitelisted_players")).add(coOwner);
                        }
                        this.inputCoOwner.func_73782_a("");
                    }
                } else if (this.hoveredAction.contains("kickCoowner")) {
                    String coOwner = this.hoveredAction.split("#")[1];
                    ((List)editedPlot.get("whitelisted_players")).remove(coOwner);
                } else if (this.hoveredAction.equals("rentMinus")) {
                    this.rentDaysToAdd = Math.max(0, this.rentDaysToAdd - 1);
                } else if (this.hoveredAction.equals("rentPlus")) {
                    ++this.rentDaysToAdd;
                } else if (this.hoveredAction.equals("addImage")) {
                    ClientData.lastCaptureScreenshot.put("plot#" + ((Double)editedPlot.get("id")).intValue(), System.currentTimeMillis());
                    Minecraft.func_71410_x().func_71373_a(null);
                    Minecraft.func_71410_x().field_71439_g.func_70006_a(ChatMessageComponent.func_111066_d((String)I18n.func_135053_a((String)"faction.plots.take_image")));
                }
            } else if (!this.hoveredType.isEmpty()) {
                this.selectedType = this.hoveredType;
            } else if (!this.hoveredUsage.isEmpty()) {
                this.selectedUsage = this.hoveredUsage;
            } else if (!this.hoveredMode.isEmpty()) {
                this.selectedMode = this.hoveredMode;
            }
        }
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    public void func_73876_c() {
        this.inputName.func_73780_a();
        this.inputPrice.func_73780_a();
        this.inputDescription.func_73780_a();
        this.inputCoOwner.func_73780_a();
    }

    protected void func_73869_a(char par1, int par2) {
        this.inputName.func_73802_a(par1, par2);
        this.inputPrice.func_73802_a(par1, par2);
        this.inputDescription.func_73802_a(par1, par2);
        this.inputCoOwner.func_73802_a(par1, par2);
        super.func_73869_a(par1, par2);
    }

    public boolean isFormValid() {
        if (this.inputName.func_73781_b().isEmpty()) {
            return false;
        }
        if (this.inputPrice.func_73781_b().isEmpty() || !FactionCreateEditPlotsGUI.isValidNonNegativeInteger(this.inputPrice.func_73781_b())) {
            return false;
        }
        if (this.selectedType.isEmpty()) {
            return false;
        }
        if (this.selectedUsage.isEmpty()) {
            return false;
        }
        return !this.selectedMode.isEmpty();
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

