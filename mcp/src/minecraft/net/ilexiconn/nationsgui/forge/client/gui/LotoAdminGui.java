/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.internal.LinkedTreeMap
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiTextField
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui;

import com.google.gson.internal.LinkedTreeMap;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarGeneric;
import net.ilexiconn.nationsgui.forge.client.gui.LotoGui;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI;
import net.ilexiconn.nationsgui.forge.client.gui.main.component.CustomInputFieldGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.LotoCreatePacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.LotoDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.LotoDeletePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class LotoAdminGui
extends GuiScreen {
    public static int GUI_SCALE = 3;
    public static HashMap<String, Object> data = new HashMap();
    public static boolean loaded = false;
    public int hoveredLotteryId = -1;
    public LinkedTreeMap<String, Object> dataToEdit = new LinkedTreeMap();
    public String hoveredAction = "";
    public String displayMode = "list";
    public boolean checkbox_donation = false;
    public boolean checkbox_pool = false;
    public String itemsPriceAction = "none";
    public String selected_color = "yellow";
    protected int xSize = 380;
    protected int ySize = 214;
    private int guiLeft;
    private int guiTop;
    private RenderItem itemRenderer = new RenderItem();
    private GuiScrollBarGeneric scrollBar;
    private GuiTextField startDateDateInput;
    private GuiTextField startDateTimeInput;
    private GuiTextField endDateDateInput;
    private GuiTextField endDateTimeInput;
    private GuiTextField limitGlobalInput;
    private GuiTextField limitPlayerInput;
    private GuiTextField repetitionInput;
    private GuiTextField ticketPriceInput;
    private GuiTextField customLogoInput;
    private GuiTextField cashPriceInput;
    private GuiTextField winnersCountInput;
    private List<ItemStack> itemsPrice = new ArrayList<ItemStack>();

    public LotoAdminGui() {
        loaded = false;
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new LotoDataPacket(true)));
        this.guiLeft = (this.field_73880_f - this.xSize) / 2;
        this.guiTop = (this.field_73881_g - this.ySize) / 2;
        this.scrollBar = new GuiScrollBarGeneric(this.guiLeft + 360, this.guiTop + 45, 160, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
        this.startDateDateInput = new CustomInputFieldGUI(this.guiLeft + 101, this.guiTop + 61, 29, 12, "georamaMedium", 25);
        this.startDateDateInput.func_73804_f(8);
        if (this.dataToEdit.containsKey((Object)"startTime")) {
            this.startDateDateInput.func_73782_a(LotoGui.dateFormat.format(new Date(((Double)this.dataToEdit.get((Object)"startTime")).longValue())));
        } else {
            this.startDateDateInput.func_73782_a("01/01/22");
        }
        this.startDateTimeInput = new CustomInputFieldGUI(this.guiLeft + 134, this.guiTop + 61, 29, 12, "georamaMedium", 25);
        this.startDateTimeInput.func_73804_f(5);
        if (this.dataToEdit.containsKey((Object)"startTime")) {
            this.startDateTimeInput.func_73782_a(LotoGui.timeFormat.format(new Date(((Double)this.dataToEdit.get((Object)"startTime")).longValue())));
        } else {
            this.startDateTimeInput.func_73782_a("00:00");
        }
        this.endDateDateInput = new CustomInputFieldGUI(this.guiLeft + 101, this.guiTop + 75, 29, 12, "georamaMedium", 25);
        this.endDateDateInput.func_73804_f(8);
        if (this.dataToEdit.containsKey((Object)"endTime")) {
            this.endDateDateInput.func_73782_a(LotoGui.dateFormat.format(new Date(((Double)this.dataToEdit.get((Object)"endTime")).longValue())));
        } else {
            this.endDateDateInput.func_73782_a("01/01/22");
        }
        this.endDateTimeInput = new CustomInputFieldGUI(this.guiLeft + 134, this.guiTop + 75, 29, 12, "georamaMedium", 25);
        this.endDateTimeInput.func_73804_f(5);
        if (this.dataToEdit.containsKey((Object)"startTime")) {
            this.endDateTimeInput.func_73782_a(LotoGui.timeFormat.format(new Date(((Double)this.dataToEdit.get((Object)"endTime")).longValue())));
        } else {
            this.endDateTimeInput.func_73782_a("00:00");
        }
        this.limitGlobalInput = new CustomInputFieldGUI(this.guiLeft + 101, this.guiTop + 89, 29, 12, "georamaMedium", 25);
        this.limitGlobalInput.func_73804_f(5);
        if (this.dataToEdit.containsKey((Object)"maxTicketsGlobal")) {
            this.limitGlobalInput.func_73782_a(((Double)this.dataToEdit.get((Object)"maxTicketsGlobal")).intValue() + "");
        } else {
            this.limitGlobalInput.func_73782_a("0");
        }
        this.limitPlayerInput = new CustomInputFieldGUI(this.guiLeft + 101, this.guiTop + 103, 29, 12, "georamaMedium", 25);
        this.limitPlayerInput.func_73804_f(5);
        if (this.dataToEdit.containsKey((Object)"maxTicketsGlobal")) {
            this.limitPlayerInput.func_73782_a(((Double)this.dataToEdit.get((Object)"maxTicketsPlayer")).intValue() + "");
        } else {
            this.limitPlayerInput.func_73782_a("0");
        }
        this.repetitionInput = new CustomInputFieldGUI(this.guiLeft + 101, this.guiTop + 131, 29, 12, "georamaMedium", 25);
        this.repetitionInput.func_73804_f(2);
        if (this.dataToEdit.containsKey((Object)"repetitionDays")) {
            this.repetitionInput.func_73782_a(((Double)this.dataToEdit.get((Object)"repetitionDays")).intValue() + "");
        } else {
            this.repetitionInput.func_73782_a("0");
        }
        this.ticketPriceInput = new CustomInputFieldGUI(this.guiLeft + 101, this.guiTop + 145, 29, 12, "georamaMedium", 25);
        this.ticketPriceInput.func_73804_f(6);
        if (this.dataToEdit.containsKey((Object)"ticketPrice")) {
            this.ticketPriceInput.func_73782_a(((Double)this.dataToEdit.get((Object)"ticketPrice")).intValue() + "");
        } else {
            this.ticketPriceInput.func_73782_a("0");
        }
        this.customLogoInput = new CustomInputFieldGUI(this.guiLeft + 101, this.guiTop + 183, 75, 12, "georamaMedium", 25);
        this.customLogoInput.func_73804_f(50);
        if (this.dataToEdit.containsKey((Object)"designLogo")) {
            this.customLogoInput.func_73782_a((String)this.dataToEdit.get((Object)"designLogo"));
        } else {
            this.customLogoInput.func_73782_a("");
        }
        this.cashPriceInput = new CustomInputFieldGUI(this.guiLeft + 273, this.guiTop + 55, 29, 12, "georamaMedium", 25);
        this.cashPriceInput.func_73804_f(7);
        if (this.dataToEdit.containsKey((Object)"cashPrice")) {
            this.cashPriceInput.func_73782_a(((Double)this.dataToEdit.get((Object)"cashPrice")).intValue() + "");
        } else {
            this.cashPriceInput.func_73782_a("0");
        }
        this.winnersCountInput = new CustomInputFieldGUI(this.guiLeft + 273, this.guiTop + 112, 29, 12, "georamaMedium", 25);
        this.winnersCountInput.func_73804_f(2);
        if (this.dataToEdit.containsKey((Object)"winnersCount")) {
            this.winnersCountInput.func_73782_a(((Double)this.dataToEdit.get((Object)"winnersCount")).intValue() + "");
        } else {
            this.winnersCountInput.func_73782_a("1");
        }
        if (this.dataToEdit.containsKey((Object)"allowDonation")) {
            this.checkbox_donation = this.dataToEdit.get((Object)"allowDonation").equals("true");
        }
        if (this.dataToEdit.containsKey((Object)"poolMode")) {
            this.checkbox_pool = this.dataToEdit.get((Object)"poolMode").equals("true");
        }
        this.itemsPrice = this.dataToEdit.containsKey((Object)"itemsPrice") && !((String)this.dataToEdit.get((Object)"itemsPrice")).isEmpty() ? LotoGui.stringToItemstacks((String)this.dataToEdit.get((Object)"itemsPrice")) : new ArrayList<ItemStack>();
        if (this.dataToEdit.containsKey((Object)"designColor")) {
            this.selected_color = (String)this.dataToEdit.get((Object)"designColor");
        }
        this.itemsPriceAction = "none";
    }

    public void func_73863_a(int mouseX, int mouseY, float par3) {
        ArrayList tooltipToDraw = new ArrayList();
        this.hoveredAction = "";
        this.hoveredLotteryId = -1;
        this.func_73873_v_();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        ClientEventHandler.STYLE.bindTexture("loto_staff");
        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 19, this.guiTop, 0.0f, (this.displayMode.equals("list") ? 0 : 298) * GUI_SCALE, (this.xSize - 19) * GUI_SCALE, this.ySize * GUI_SCALE, this.xSize - 19, this.ySize, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
        ClientEventHandler.STYLE.bindTexture("loto");
        if (mouseX >= this.guiLeft + 367 && mouseX <= this.guiLeft + 367 + 9 && mouseY >= this.guiTop + 4 && mouseY <= this.guiTop + 4 + 9) {
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 367, this.guiTop + 4, 442 * GUI_SCALE, 53 * GUI_SCALE, 9 * GUI_SCALE, 9 * GUI_SCALE, 9, 9, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
            this.hoveredAction = "close";
        } else {
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 367, this.guiTop + 4, 431 * GUI_SCALE, 53 * GUI_SCALE, 9 * GUI_SCALE, 9 * GUI_SCALE, 9, 9, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
        }
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"lottery.admin.title"), this.guiLeft + 43, this.guiTop + 13, 10395075, 0.5f, "left", false, "georamaMedium", 32);
        if (loaded) {
            if (this.displayMode.equals("list")) {
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"lottery.admin.to_come"), this.guiLeft + 43, this.guiTop + 23, 0xFFFFFF, 0.75f, "left", false, "georamaSemiBold", 32);
                ClientEventHandler.STYLE.bindTexture("loto_staff");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft, this.guiTop, 385 * GUI_SCALE, 0 * GUI_SCALE, 19 * GUI_SCALE, 37 * GUI_SCALE, 19, 37, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft, this.guiTop, 385 * GUI_SCALE, 40 * GUI_SCALE, 19 * GUI_SCALE, 19 * GUI_SCALE, 19, 19, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                if (mouseX >= this.guiLeft && mouseX <= this.guiLeft + 19 && mouseY >= this.guiTop + 19 && mouseY <= this.guiTop + 19 + 18) {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft, this.guiTop + 19, 404 * GUI_SCALE, 59 * GUI_SCALE, 19 * GUI_SCALE, 19 * GUI_SCALE, 19, 19, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    this.hoveredAction = "admin_create";
                } else {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft, this.guiTop + 19, 366 * GUI_SCALE, 59 * GUI_SCALE, 19 * GUI_SCALE, 19 * GUI_SCALE, 19, 19, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                }
                int index = 0;
                for (LinkedTreeMap lottery : (ArrayList)data.get("lotteries")) {
                    GUIUtils.startGLScissor(this.guiLeft + 42, this.guiTop + 45, 315, 160);
                    int offsetX = this.guiLeft + 42;
                    Float offsetY = Float.valueOf((float)(this.guiTop + 45 + index * 39) + this.getSlideLotteries());
                    ClientEventHandler.STYLE.bindTexture("loto_staff");
                    ModernGui.drawScaledCustomSizeModalRect(offsetX, offsetY.intValue(), 0 * GUI_SCALE, 219 * GUI_SCALE, 245 * GUI_SCALE, 35 * GUI_SCALE, 245, 35, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    ModernGui.glColorHex(LotoGui.colors.get(lottery.get((Object)"designColor")), 1.0f);
                    ModernGui.drawRoundedRectangle((float)offsetX + 234.0f, offsetY.floatValue(), this.field_73735_i, 81.0f, 35.0f);
                    GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                    ClientEventHandler.STYLE.bindTexture("loto");
                    ModernGui.drawScaledCustomSizeModalRect(offsetX + 2, offsetY.intValue() + 1, LotoGui.iconsX.get("calendar") * GUI_SCALE, LotoGui.getElementYByColor(16, (String)lottery.get((Object)"designColor")) * GUI_SCALE, 11 * GUI_SCALE, 11 * GUI_SCALE, 11, 11, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                    String date = "";
                    date = System.currentTimeMillis() > ((Double)lottery.get((Object)"startTime")).longValue() ? I18n.func_135053_a((String)"lottery.label.in_progress") + " " + LotoGui.dateFormat.format(new Date(((Double)lottery.get((Object)"endTime")).longValue())) : I18n.func_135053_a((String)"lottery.label.incoming") + " " + LotoGui.dateFormat.format(new Date(((Double)lottery.get((Object)"startTime")).longValue()));
                    ModernGui.drawScaledStringCustomFont(date, offsetX + 15, offsetY.intValue() + 4, 16514302, 0.5f, "left", false, "georamaMedium", 26);
                    ClientEventHandler.STYLE.bindTexture("loto");
                    ModernGui.drawScaledCustomSizeModalRect(offsetX + 2, offsetY.intValue() + 12, LotoGui.iconsX.get("people") * GUI_SCALE, LotoGui.getElementYByColor(16, (String)lottery.get((Object)"designColor")) * GUI_SCALE, 11 * GUI_SCALE, 11 * GUI_SCALE, 11, 11, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                    ModernGui.drawScaledStringCustomFont((((String)lottery.get((Object)"players")).isEmpty() ? "0" : Integer.valueOf(((String)lottery.get((Object)"players")).split(",").length)) + " " + I18n.func_135053_a((String)"lottery.label.players"), offsetX + 15, offsetY.intValue() + 15, 16514302, 0.5f, "left", false, "georamaMedium", 26);
                    ClientEventHandler.STYLE.bindTexture("loto");
                    ModernGui.drawScaledCustomSizeModalRect(offsetX + 2, offsetY.intValue() + 23, LotoGui.iconsX.get("trophee") * GUI_SCALE, LotoGui.getElementYByColor(16, (String)lottery.get((Object)"designColor")) * GUI_SCALE, 11 * GUI_SCALE, 11 * GUI_SCALE, 11, 11, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                    ModernGui.drawScaledStringCustomFont(String.format("%.0f", (Double)lottery.get((Object)"winnersCount")) + " " + I18n.func_135053_a((String)"lottery.label.winners"), offsetX + 15, offsetY.intValue() + 26, 16514302, 0.5f, "left", false, "georamaMedium", 26);
                    ModernGui.drawScaledStringCustomFont("ID: " + String.format("%.0f", (Double)lottery.get((Object)"id")), offsetX + 125, offsetY.intValue() + 3, 15463162, 0.5f, "left", false, "georamaMedium", 25);
                    ModernGui.drawScaledStringCustomFont(String.format("%.0f", (Double)lottery.get((Object)"cashPrice")) + "$", offsetX + 125, offsetY.intValue() + 10, 15463162, 0.75f, "left", false, "georamaSemiBold", 28);
                    ModernGui.drawScaledStringCustomFont((((String)lottery.get((Object)"itemsPrice")).isEmpty() ? "0" : Integer.valueOf(((String)lottery.get((Object)"itemsPrice")).split(",").length)) + " item(s)", offsetX + 125, offsetY.intValue() + 22, 15463162, 0.5f, "left", false, "georamaSemiBold", 28);
                    if (((String)lottery.get((Object)"designLogo")).isEmpty() || !((String)lottery.get((Object)"designLogo")).matches("https://static.nationsglory.fr/.*\\.png")) {
                        ClientEventHandler.STYLE.bindTexture("loto");
                        ModernGui.drawScaledCustomSizeModalRect(offsetX + 245, offsetY.intValue() + 5, 471 * GUI_SCALE, 411 * GUI_SCALE, 120 * GUI_SCALE, 50 * GUI_SCALE, 60, 25, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                    } else {
                        ModernGui.bindRemoteTexture((String)lottery.get((Object)"designLogo"));
                        ModernGui.drawScaledCustomSizeModalRect(offsetX + 245, offsetY.intValue() + 5, 0 * GUI_SCALE, 0 * GUI_SCALE, 600, 250, 60, 25, 600.0f, 250.0f, false);
                    }
                    GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                    if (mouseX >= offsetX && mouseX <= offsetX + 315 && mouseY >= offsetY.intValue() && mouseY <= offsetY.intValue() + 35) {
                        this.hoveredAction = "admin_edit";
                        this.hoveredLotteryId = index;
                    }
                    GUIUtils.endGLScissor();
                    this.scrollBar.draw(mouseX, mouseY);
                    ++index;
                }
            } else if (this.displayMode.equals("edit")) {
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"lottery.admin.create"), this.guiLeft + 43, this.guiTop + 23, 0xFFFFFF, 0.75f, "left", false, "georamaSemiBold", 32);
                ClientEventHandler.STYLE.bindTexture("loto_staff");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft, this.guiTop, 365 * GUI_SCALE, 0 * GUI_SCALE, 19 * GUI_SCALE, 37 * GUI_SCALE, 19, 37, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft, this.guiTop + 19, 385 * GUI_SCALE, 59 * GUI_SCALE, 19 * GUI_SCALE, 19 * GUI_SCALE, 19, 19, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                if (mouseX >= this.guiLeft && mouseX <= this.guiLeft + 19 && mouseY >= this.guiTop && mouseY <= this.guiTop + 18) {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft, this.guiTop, 404 * GUI_SCALE, 40 * GUI_SCALE, 19 * GUI_SCALE, 19 * GUI_SCALE, 19, 19, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    this.hoveredAction = "admin_list";
                } else {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft, this.guiTop, 366 * GUI_SCALE, 40 * GUI_SCALE, 19 * GUI_SCALE, 19 * GUI_SCALE, 19, 19, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                }
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"lottery.admin.a_propos"), this.guiLeft + 42, this.guiTop + 44, 14803951, 0.5f, "left", false, "georamaSemiBold", 28);
                int fieldOffsetY = 65;
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"lottery.admin.start_date"), this.guiLeft + 48, this.guiTop + fieldOffsetY, 14803951, 0.5f, "left", false, "georamaMedium", 24);
                ClientEventHandler.STYLE.bindTexture("loto_staff");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 103, this.guiTop + fieldOffsetY - 2, 249 * GUI_SCALE, 268 * GUI_SCALE, 30 * GUI_SCALE, 9 * GUI_SCALE, 30, 9, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                this.startDateDateInput.func_73795_f();
                ClientEventHandler.STYLE.bindTexture("loto_staff");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 137, this.guiTop + fieldOffsetY - 2, 249 * GUI_SCALE, 268 * GUI_SCALE, 30 * GUI_SCALE, 9 * GUI_SCALE, 30, 9, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                this.startDateTimeInput.func_73795_f();
                fieldOffsetY = 79;
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"lottery.admin.end_date"), this.guiLeft + 48, this.guiTop + fieldOffsetY, 14803951, 0.5f, "left", false, "georamaMedium", 24);
                ClientEventHandler.STYLE.bindTexture("loto_staff");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 103, this.guiTop + fieldOffsetY - 2, 249 * GUI_SCALE, 268 * GUI_SCALE, 30 * GUI_SCALE, 9 * GUI_SCALE, 30, 9, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                this.endDateDateInput.func_73795_f();
                ClientEventHandler.STYLE.bindTexture("loto_staff");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 137, this.guiTop + fieldOffsetY - 2, 249 * GUI_SCALE, 268 * GUI_SCALE, 30 * GUI_SCALE, 9 * GUI_SCALE, 30, 9, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                this.endDateTimeInput.func_73795_f();
                fieldOffsetY = 93;
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"lottery.admin.limit_global"), this.guiLeft + 48, this.guiTop + fieldOffsetY, 14803951, 0.5f, "left", false, "georamaMedium", 24);
                ClientEventHandler.STYLE.bindTexture("loto_staff");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 103, this.guiTop + fieldOffsetY - 2, 249 * GUI_SCALE, 268 * GUI_SCALE, 30 * GUI_SCALE, 9 * GUI_SCALE, 30, 9, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                this.limitGlobalInput.func_73795_f();
                fieldOffsetY = 107;
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"lottery.admin.limit_player"), this.guiLeft + 48, this.guiTop + fieldOffsetY, 14803951, 0.5f, "left", false, "georamaMedium", 24);
                ClientEventHandler.STYLE.bindTexture("loto_staff");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 103, this.guiTop + fieldOffsetY - 2, 249 * GUI_SCALE, 268 * GUI_SCALE, 30 * GUI_SCALE, 9 * GUI_SCALE, 30, 9, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                this.limitPlayerInput.func_73795_f();
                ClientEventHandler.STYLE.bindTexture("loto_staff");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 48, this.guiTop + fieldOffsetY + 12, (this.checkbox_donation ? 295 : 285) * GUI_SCALE, 282 * GUI_SCALE, 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                if (mouseX >= this.guiLeft + 48 && mouseX <= this.guiLeft + 48 + 8 && mouseY >= this.guiTop + fieldOffsetY + 12 && mouseY <= this.guiTop + fieldOffsetY + 12 + 9) {
                    this.hoveredAction = "checkbox_donation";
                }
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"lottery.admin.allow_donation"), this.guiLeft + 59, this.guiTop + fieldOffsetY + 12 + 1, 14803951, 0.5f, "left", false, "georamaMedium", 24);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"lottery.admin.repeat"), this.guiLeft + 48, this.guiTop + 135, 14803951, 0.5f, "left", false, "georamaMedium", 24);
                ClientEventHandler.STYLE.bindTexture("loto_staff");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 103, this.guiTop + 135 - 2, 249 * GUI_SCALE, 268 * GUI_SCALE, 30 * GUI_SCALE, 9 * GUI_SCALE, 30, 9, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                this.repetitionInput.func_73795_f();
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"lottery.admin.ticket_price"), this.guiLeft + 48, this.guiTop + 149, 14803951, 0.5f, "left", false, "georamaMedium", 24);
                ClientEventHandler.STYLE.bindTexture("loto_staff");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 103, this.guiTop + 149 - 2, 249 * GUI_SCALE, 268 * GUI_SCALE, 30 * GUI_SCALE, 9 * GUI_SCALE, 30, 9, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                this.ticketPriceInput.func_73795_f();
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"lottery.admin.color_design"), this.guiLeft + 48, this.guiTop + 162, 14803951, 0.5f, "left", false, "georamaMedium", 24);
                ClientEventHandler.STYLE.bindTexture("loto_staff");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 48, this.guiTop + 169, 369 * GUI_SCALE, 268 * GUI_SCALE, 141 * GUI_SCALE, 12 * GUI_SCALE, 141, 12, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                int index = 0;
                for (Map.Entry<String, Integer> pair : LotoGui.colors.entrySet()) {
                    Gui.func_73734_a((int)(this.guiLeft + 52 + index * 14), (int)(this.guiTop + 171), (int)(this.guiLeft + 52 + index * 14 + 8), (int)(this.guiTop + 171 + 8), (int)pair.getValue());
                    GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                    if (this.selected_color.equals(pair.getKey())) {
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 52 + index * 14, this.guiTop + 171, 285 * GUI_SCALE, 282 * GUI_SCALE, 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    }
                    if (mouseX >= this.guiLeft + 52 + index * 14 && mouseX <= this.guiLeft + 52 + index * 14 + 8 && mouseY >= this.guiTop + 171 && mouseY <= this.guiTop + 171 + 8) {
                        this.hoveredAction = "select_color#" + pair.getKey();
                    }
                    ++index;
                }
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"lottery.admin.logo_design"), this.guiLeft + 48, this.guiTop + 187, 14803951, 0.5f, "left", false, "georamaMedium", 24);
                ClientEventHandler.STYLE.bindTexture("loto_staff");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 103, this.guiTop + 187 - 2, 369 * GUI_SCALE, 284 * GUI_SCALE, 80 * GUI_SCALE, 9 * GUI_SCALE, 80, 9, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                this.customLogoInput.func_73795_f();
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"lottery.admin.rewards"), this.guiLeft + 205, this.guiTop + 44, 14803951, 0.5f, "left", false, "georamaSemiBold", 28);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"lottery.admin.cashprice"), this.guiLeft + 214, this.guiTop + 59, 14803951, 0.5f, "left", false, "georamaMedium", 24);
                ClientEventHandler.STYLE.bindTexture("loto_staff");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 275, this.guiTop + 59 - 2, 249 * GUI_SCALE, 268 * GUI_SCALE, 30 * GUI_SCALE, 9 * GUI_SCALE, 30, 9, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                this.cashPriceInput.func_73795_f();
                ClientEventHandler.STYLE.bindTexture("loto_staff");
                for (int i = 0; i < 9; ++i) {
                    ClientEventHandler.STYLE.bindTexture("loto_staff");
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 214 + 12 * i, this.guiTop + 72, 285 * GUI_SCALE, 268 * GUI_SCALE, 10 * GUI_SCALE, 10 * GUI_SCALE, 10, 10, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    if (this.itemsPrice.size() <= i) continue;
                    ItemStack itemStack = this.itemsPrice.get(i);
                    GL11.glPushMatrix();
                    GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
                    this.itemRenderer.func_82406_b(this.field_73886_k, Minecraft.func_71410_x().func_110434_K(), itemStack, (this.guiLeft + 215 + 12 * i) * 2, (this.guiTop + 73) * 2);
                    GL11.glPopMatrix();
                    GL11.glDisable((int)2896);
                    GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                }
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                ClientEventHandler.STYLE.bindTexture("loto_staff");
                if (mouseX >= this.guiLeft + 214 && mouseX <= this.guiLeft + 214 + 62 && mouseY >= this.guiTop + 85 && mouseY <= this.guiTop + 85 + 10) {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 214, this.guiTop + 85, 299 * GUI_SCALE, 234 * GUI_SCALE, 62 * GUI_SCALE, 10 * GUI_SCALE, 62, 10, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    this.hoveredAction = "add_items";
                } else {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 214, this.guiTop + 85, 299 * GUI_SCALE, 219 * GUI_SCALE, 62 * GUI_SCALE, 10 * GUI_SCALE, 62, 10, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                }
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)(!this.itemsPrice.isEmpty() ? "lottery.admin.remove_items" : "lottery.admin.add_items")), this.guiLeft + 214 + 31, this.guiTop + 87, 2234425, 0.5f, "center", false, "georamaSemiBold", 24);
                ClientEventHandler.STYLE.bindTexture("loto_staff");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 214, this.guiTop + 100, (this.checkbox_pool ? 295 : 285) * GUI_SCALE, 282 * GUI_SCALE, 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                if (mouseX >= this.guiLeft + 214 && mouseX <= this.guiLeft + 214 + 8 && mouseY >= this.guiTop + 100 && mouseY <= this.guiTop + 100 + 9) {
                    this.hoveredAction = "checkbox_poll";
                }
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"lottery.admin.mode_poll"), this.guiLeft + 225, this.guiTop + 101, 14803951, 0.5f, "left", false, "georamaMedium", 24);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"lottery.admin.count_winners"), this.guiLeft + 214, this.guiTop + 116, 14803951, 0.5f, "left", false, "georamaMedium", 24);
                ClientEventHandler.STYLE.bindTexture("loto_staff");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 275, this.guiTop + 116 - 2, 249 * GUI_SCALE, 268 * GUI_SCALE, 30 * GUI_SCALE, 9 * GUI_SCALE, 30, 9, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                this.winnersCountInput.func_73795_f();
                ModernGui.glColorHex(LotoGui.colors.get(this.selected_color), 1.0f);
                ModernGui.drawRoundedRectangle((float)this.guiLeft + 205.0f, (float)this.guiTop + 145.0f, this.field_73735_i, 152.0f, 43.0f);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                if (this.customLogoInput.func_73781_b().isEmpty() || !this.customLogoInput.func_73781_b().matches("https://static.nationsglory.fr/.*\\.png")) {
                    ClientEventHandler.STYLE.bindTexture("loto");
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 251, this.guiTop + 154, 471 * GUI_SCALE, 411 * GUI_SCALE, 120 * GUI_SCALE, 50 * GUI_SCALE, 60, 25, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                } else {
                    ModernGui.bindRemoteTexture(this.customLogoInput.func_73781_b());
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 251, this.guiTop + 154, 0 * GUI_SCALE, 0 * GUI_SCALE, 600, 250, 60, 25, 600.0f, 250.0f, false);
                }
                ClientEventHandler.STYLE.bindTexture("loto_staff");
                if (mouseX >= this.guiLeft + 205 && mouseX <= this.guiLeft + 205 + 44 && mouseY >= this.guiTop + 194 && mouseY <= this.guiTop + 194 + 10) {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 205, this.guiTop + 194, 249 * GUI_SCALE, 249 * GUI_SCALE, 44 * GUI_SCALE, 11 * GUI_SCALE, 44, 11, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    this.hoveredAction = "create_valid";
                } else {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 205, this.guiTop + 194, 249 * GUI_SCALE, 219 * GUI_SCALE, 44 * GUI_SCALE, 11 * GUI_SCALE, 44, 11, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                }
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"lottery.admin.validate"), this.guiLeft + 205 + 22, this.guiTop + 196, 2234425, 0.5f, "center", false, "georamaSemiBold", 26);
                ClientEventHandler.STYLE.bindTexture("loto_staff");
                if (mouseX >= this.guiLeft + 252 && mouseX <= this.guiLeft + 252 + 62 && mouseY >= this.guiTop + 194 && mouseY <= this.guiTop + 194 + 10) {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 252, this.guiTop + 194, 249 * GUI_SCALE, 249 * GUI_SCALE, 44 * GUI_SCALE, 11 * GUI_SCALE, 44, 11, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    this.hoveredAction = "create_cancel";
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"lottery.admin.cancel"), this.guiLeft + 252 + 22, this.guiTop + 196, 2234425, 0.5f, "center", false, "georamaSemiBold", 26);
                } else {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 252, this.guiTop + 194, 249 * GUI_SCALE, 234 * GUI_SCALE, 44 * GUI_SCALE, 11 * GUI_SCALE, 44, 11, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"lottery.admin.cancel"), this.guiLeft + 252 + 22, this.guiTop + 196, 14803951, 0.5f, "center", false, "georamaSemiBold", 26);
                }
                if (!this.dataToEdit.isEmpty()) {
                    ClientEventHandler.STYLE.bindTexture("loto_staff");
                    if (mouseX >= this.guiLeft + 299 && mouseX <= this.guiLeft + 299 + 62 && mouseY >= this.guiTop + 194 && mouseY <= this.guiTop + 194 + 10) {
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 299, this.guiTop + 194, 249 * GUI_SCALE, 249 * GUI_SCALE, 44 * GUI_SCALE, 11 * GUI_SCALE, 44, 11, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                        this.hoveredAction = "delete";
                        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"lottery.admin.delete"), this.guiLeft + 299 + 22, this.guiTop + 196, 2234425, 0.5f, "center", false, "georamaSemiBold", 26);
                    } else {
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 299, this.guiTop + 194, 367 * GUI_SCALE, 219 * GUI_SCALE, 44 * GUI_SCALE, 11 * GUI_SCALE, 44, 11, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"lottery.admin.delete"), this.guiLeft + 299 + 22, this.guiTop + 196, 15463162, 0.5f, "center", false, "georamaSemiBold", 26);
                    }
                }
            }
        }
        if (!tooltipToDraw.isEmpty()) {
            this.drawHoveringText(tooltipToDraw, mouseX, mouseY, this.field_73886_k);
        }
        super.func_73863_a(mouseX, mouseY, par3);
        GL11.glEnable((int)2896);
        RenderHelper.func_74519_b();
    }

    private float getSlideLotteries() {
        return ((ArrayList)data.get("lotteries")).size() > 4 ? (float)(-(((ArrayList)data.get("lotteries")).size() - 4) * 39) * this.scrollBar.getSliderValue() : 0.0f;
    }

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            if (this.displayMode.equals("edit")) {
                this.startDateDateInput.func_73793_a(mouseX, mouseY, mouseButton);
                this.startDateTimeInput.func_73793_a(mouseX, mouseY, mouseButton);
                this.endDateDateInput.func_73793_a(mouseX, mouseY, mouseButton);
                this.endDateTimeInput.func_73793_a(mouseX, mouseY, mouseButton);
                this.limitGlobalInput.func_73793_a(mouseX, mouseY, mouseButton);
                this.limitPlayerInput.func_73793_a(mouseX, mouseY, mouseButton);
                this.repetitionInput.func_73793_a(mouseX, mouseY, mouseButton);
                this.ticketPriceInput.func_73793_a(mouseX, mouseY, mouseButton);
                this.customLogoInput.func_73793_a(mouseX, mouseY, mouseButton);
                this.cashPriceInput.func_73793_a(mouseX, mouseY, mouseButton);
                this.winnersCountInput.func_73793_a(mouseX, mouseY, mouseButton);
            }
            if (this.hoveredAction.equals("close")) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a(null);
            } else if (this.hoveredAction.equals("admin_create")) {
                this.displayMode = "edit";
                this.func_73866_w_();
            } else if (this.hoveredAction.equals("admin_edit")) {
                this.dataToEdit = (LinkedTreeMap)((ArrayList)data.get("lotteries")).get(this.hoveredLotteryId);
                this.displayMode = "edit";
                this.func_73866_w_();
            } else if (this.hoveredAction.equals("admin_list")) {
                this.dataToEdit.clear();
                this.func_73866_w_();
                this.displayMode = "list";
            } else if (this.hoveredAction.equals("checkbox_donation")) {
                this.checkbox_donation = !this.checkbox_donation;
            } else if (this.hoveredAction.equals("checkbox_poll")) {
                this.checkbox_pool = !this.checkbox_pool;
            } else if (this.hoveredAction.equals("add_items")) {
                if (this.itemsPrice.isEmpty()) {
                    this.itemsPriceAction = "update";
                    for (int i = 0; i < 9; ++i) {
                        ItemStack itemStack = Minecraft.func_71410_x().field_71439_g.field_71071_by.func_70301_a(i);
                        if (itemStack == null) continue;
                        this.itemsPrice.add(itemStack);
                    }
                } else {
                    this.itemsPriceAction = "clear";
                    this.itemsPrice.clear();
                }
            } else if (this.hoveredAction.contains("select_color")) {
                this.selected_color = this.hoveredAction.replace("select_color#", "");
            } else if (this.hoveredAction.contains("create_cancel")) {
                this.dataToEdit.clear();
                this.displayMode = "list";
            } else if (this.hoveredAction.contains("delete")) {
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new LotoDeletePacket(((Double)this.dataToEdit.get((Object)"id")).intValue())));
                this.dataToEdit.clear();
                this.displayMode = "list";
            } else if (this.hoveredAction.contains("create_valid")) {
                try {
                    HashMap<String, String> data = new HashMap<String, String>();
                    if (!this.startDateDateInput.func_73781_b().matches("\\d{2}/\\d{2}/\\d{2}")) {
                        Minecraft.func_71410_x().field_71439_g.func_71035_c(I18n.func_135053_a((String)"lotery.error.wrong_data"));
                        return;
                    }
                    if (!this.startDateTimeInput.func_73781_b().matches("\\d{2}:\\d{2}")) {
                        Minecraft.func_71410_x().field_71439_g.func_71035_c(I18n.func_135053_a((String)"lotery.error.wrong_data"));
                        return;
                    }
                    if (!(FactionGUI.isNumeric(this.limitGlobalInput.func_73781_b(), true) && FactionGUI.isNumeric(this.limitPlayerInput.func_73781_b(), true) && FactionGUI.isNumeric(this.repetitionInput.func_73781_b(), true) && FactionGUI.isNumeric(this.ticketPriceInput.func_73781_b(), false) && FactionGUI.isNumeric(this.cashPriceInput.func_73781_b(), true) && FactionGUI.isNumeric(this.winnersCountInput.func_73781_b(), false))) {
                        Minecraft.func_71410_x().field_71439_g.func_71035_c(I18n.func_135053_a((String)"lotery.error.wrong_data"));
                        return;
                    }
                    Date startDate = new SimpleDateFormat("dd/MM/yy HH:mm").parse(this.startDateDateInput.func_73781_b() + " " + this.startDateTimeInput.func_73781_b());
                    data.put("startTime", startDate.getTime() + "");
                    Date endDate = new SimpleDateFormat("dd/MM/yy HH:mm").parse(this.endDateDateInput.func_73781_b() + " " + this.endDateTimeInput.func_73781_b());
                    if (endDate.getTime() < System.currentTimeMillis()) {
                        Minecraft.func_71410_x().field_71439_g.func_71035_c(I18n.func_135053_a((String)"lotery.error.end_date_future"));
                        return;
                    }
                    data.put("endTime", endDate.getTime() + "");
                    data.put("maxTicketsGlobal", this.limitGlobalInput.func_73781_b());
                    data.put("maxTicketsPlayer", this.limitPlayerInput.func_73781_b());
                    data.put("allowDonation", this.checkbox_donation + "");
                    data.put("repetitionDays", this.repetitionInput.func_73781_b());
                    data.put("ticketPrice", this.ticketPriceInput.func_73781_b());
                    data.put("designColor", this.selected_color);
                    data.put("designLogo", this.customLogoInput.func_73781_b());
                    data.put("cashPrice", this.cashPriceInput.func_73781_b());
                    data.put("poolMode", this.checkbox_pool + "");
                    data.put("winnersCount", this.winnersCountInput.func_73781_b());
                    data.put("itemsPrice", this.itemsPriceAction);
                    data.put("lotteryIdForEdit", this.dataToEdit.containsKey((Object)"id") ? ((Double)this.dataToEdit.get((Object)"id")).intValue() + "" : "-1");
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new LotoCreatePacket(data)));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    public void func_73876_c() {
        this.startDateDateInput.func_73780_a();
        this.startDateTimeInput.func_73780_a();
        this.endDateDateInput.func_73780_a();
        this.endDateTimeInput.func_73780_a();
        this.limitGlobalInput.func_73780_a();
        this.limitPlayerInput.func_73780_a();
        this.repetitionInput.func_73780_a();
        this.ticketPriceInput.func_73780_a();
        this.customLogoInput.func_73780_a();
        this.cashPriceInput.func_73780_a();
        this.winnersCountInput.func_73780_a();
    }

    protected void func_73869_a(char par1, int par2) {
        this.startDateDateInput.func_73802_a(par1, par2);
        this.startDateTimeInput.func_73802_a(par1, par2);
        this.endDateDateInput.func_73802_a(par1, par2);
        this.endDateTimeInput.func_73802_a(par1, par2);
        this.limitGlobalInput.func_73802_a(par1, par2);
        this.limitPlayerInput.func_73802_a(par1, par2);
        this.repetitionInput.func_73802_a(par1, par2);
        this.ticketPriceInput.func_73802_a(par1, par2);
        this.customLogoInput.func_73802_a(par1, par2);
        this.cashPriceInput.func_73802_a(par1, par2);
        this.winnersCountInput.func_73802_a(par1, par2);
        super.func_73869_a(par1, par2);
    }

    public boolean func_73868_f() {
        return false;
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
            int j1 = par3;
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

    private void drawItemStack(ItemStack par1ItemStack, int par2, int par3, String par4Str) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)0.0f, (float)0.0f, (float)32.0f);
        RenderHelper.func_74520_c();
        GL11.glDisable((int)2896);
        GL11.glEnable((int)32826);
        GL11.glEnable((int)2903);
        GL11.glEnable((int)2896);
        this.field_73735_i = 200.0f;
        this.itemRenderer.field_77023_b = 200.0f;
        FontRenderer font = null;
        if (par1ItemStack != null) {
            font = par1ItemStack.func_77973_b().getFontRenderer(par1ItemStack);
        }
        if (font == null) {
            font = this.field_73886_k;
        }
        this.itemRenderer.func_82406_b(font, this.field_73882_e.func_110434_K(), par1ItemStack, par2, par3);
        this.itemRenderer.func_94148_a(font, this.field_73882_e.func_110434_K(), par1ItemStack, par2, par3, par4Str);
        this.field_73735_i = 0.0f;
        this.itemRenderer.field_77023_b = 0.0f;
        GL11.glPopMatrix();
        GL11.glEnable((int)2896);
        GL11.glEnable((int)2929);
        RenderHelper.func_74519_b();
    }
}

