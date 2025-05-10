package net.ilexiconn.nationsgui.forge.client.gui;

import com.google.gson.internal.LinkedTreeMap;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
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
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

@SideOnly(Side.CLIENT)
public class LotoAdminGui extends GuiScreen
{
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
    private List<ItemStack> itemsPrice = new ArrayList();

    public LotoAdminGui()
    {
        loaded = false;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new LotoDataPacket(true)));
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        this.scrollBar = new GuiScrollBarGeneric((float)(this.guiLeft + 360), (float)(this.guiTop + 45), 160, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
        this.startDateDateInput = new CustomInputFieldGUI(this.guiLeft + 101, this.guiTop + 61, 29, 12, "georamaMedium", 25);
        this.startDateDateInput.setMaxStringLength(8);

        if (this.dataToEdit.containsKey("startTime"))
        {
            this.startDateDateInput.setText(LotoGui.dateFormat.format(new Date(((Double)this.dataToEdit.get("startTime")).longValue())));
        }
        else
        {
            this.startDateDateInput.setText("01/01/22");
        }

        this.startDateTimeInput = new CustomInputFieldGUI(this.guiLeft + 134, this.guiTop + 61, 29, 12, "georamaMedium", 25);
        this.startDateTimeInput.setMaxStringLength(5);

        if (this.dataToEdit.containsKey("startTime"))
        {
            this.startDateTimeInput.setText(LotoGui.timeFormat.format(new Date(((Double)this.dataToEdit.get("startTime")).longValue())));
        }
        else
        {
            this.startDateTimeInput.setText("00:00");
        }

        this.endDateDateInput = new CustomInputFieldGUI(this.guiLeft + 101, this.guiTop + 75, 29, 12, "georamaMedium", 25);
        this.endDateDateInput.setMaxStringLength(8);

        if (this.dataToEdit.containsKey("endTime"))
        {
            this.endDateDateInput.setText(LotoGui.dateFormat.format(new Date(((Double)this.dataToEdit.get("endTime")).longValue())));
        }
        else
        {
            this.endDateDateInput.setText("01/01/22");
        }

        this.endDateTimeInput = new CustomInputFieldGUI(this.guiLeft + 134, this.guiTop + 75, 29, 12, "georamaMedium", 25);
        this.endDateTimeInput.setMaxStringLength(5);

        if (this.dataToEdit.containsKey("startTime"))
        {
            this.endDateTimeInput.setText(LotoGui.timeFormat.format(new Date(((Double)this.dataToEdit.get("endTime")).longValue())));
        }
        else
        {
            this.endDateTimeInput.setText("00:00");
        }

        this.limitGlobalInput = new CustomInputFieldGUI(this.guiLeft + 101, this.guiTop + 89, 29, 12, "georamaMedium", 25);
        this.limitGlobalInput.setMaxStringLength(5);

        if (this.dataToEdit.containsKey("maxTicketsGlobal"))
        {
            this.limitGlobalInput.setText(((Double)this.dataToEdit.get("maxTicketsGlobal")).intValue() + "");
        }
        else
        {
            this.limitGlobalInput.setText("0");
        }

        this.limitPlayerInput = new CustomInputFieldGUI(this.guiLeft + 101, this.guiTop + 103, 29, 12, "georamaMedium", 25);
        this.limitPlayerInput.setMaxStringLength(5);

        if (this.dataToEdit.containsKey("maxTicketsGlobal"))
        {
            this.limitPlayerInput.setText(((Double)this.dataToEdit.get("maxTicketsPlayer")).intValue() + "");
        }
        else
        {
            this.limitPlayerInput.setText("0");
        }

        this.repetitionInput = new CustomInputFieldGUI(this.guiLeft + 101, this.guiTop + 131, 29, 12, "georamaMedium", 25);
        this.repetitionInput.setMaxStringLength(2);

        if (this.dataToEdit.containsKey("repetitionDays"))
        {
            this.repetitionInput.setText(((Double)this.dataToEdit.get("repetitionDays")).intValue() + "");
        }
        else
        {
            this.repetitionInput.setText("0");
        }

        this.ticketPriceInput = new CustomInputFieldGUI(this.guiLeft + 101, this.guiTop + 145, 29, 12, "georamaMedium", 25);
        this.ticketPriceInput.setMaxStringLength(6);

        if (this.dataToEdit.containsKey("ticketPrice"))
        {
            this.ticketPriceInput.setText(((Double)this.dataToEdit.get("ticketPrice")).intValue() + "");
        }
        else
        {
            this.ticketPriceInput.setText("0");
        }

        this.customLogoInput = new CustomInputFieldGUI(this.guiLeft + 101, this.guiTop + 183, 75, 12, "georamaMedium", 25);
        this.customLogoInput.setMaxStringLength(50);

        if (this.dataToEdit.containsKey("designLogo"))
        {
            this.customLogoInput.setText((String)this.dataToEdit.get("designLogo"));
        }
        else
        {
            this.customLogoInput.setText("");
        }

        this.cashPriceInput = new CustomInputFieldGUI(this.guiLeft + 273, this.guiTop + 55, 29, 12, "georamaMedium", 25);
        this.cashPriceInput.setMaxStringLength(7);

        if (this.dataToEdit.containsKey("cashPrice"))
        {
            this.cashPriceInput.setText(((Double)this.dataToEdit.get("cashPrice")).intValue() + "");
        }
        else
        {
            this.cashPriceInput.setText("0");
        }

        this.winnersCountInput = new CustomInputFieldGUI(this.guiLeft + 273, this.guiTop + 112, 29, 12, "georamaMedium", 25);
        this.winnersCountInput.setMaxStringLength(2);

        if (this.dataToEdit.containsKey("winnersCount"))
        {
            this.winnersCountInput.setText(((Double)this.dataToEdit.get("winnersCount")).intValue() + "");
        }
        else
        {
            this.winnersCountInput.setText("1");
        }

        if (this.dataToEdit.containsKey("allowDonation"))
        {
            this.checkbox_donation = this.dataToEdit.get("allowDonation").equals("true");
        }

        if (this.dataToEdit.containsKey("poolMode"))
        {
            this.checkbox_pool = this.dataToEdit.get("poolMode").equals("true");
        }

        if (this.dataToEdit.containsKey("itemsPrice") && !((String)this.dataToEdit.get("itemsPrice")).isEmpty())
        {
            this.itemsPrice = LotoGui.stringToItemstacks((String)this.dataToEdit.get("itemsPrice"));
        }
        else
        {
            this.itemsPrice = new ArrayList();
        }

        if (this.dataToEdit.containsKey("designColor"))
        {
            this.selected_color = (String)this.dataToEdit.get("designColor");
        }

        this.itemsPriceAction = "none";
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float par3)
    {
        ArrayList tooltipToDraw = new ArrayList();
        this.hoveredAction = "";
        this.hoveredLotteryId = -1;
        this.drawDefaultBackground();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        ClientEventHandler.STYLE.bindTexture("loto_staff");
        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 19), (float)this.guiTop, 0.0F, (float)((this.displayMode.equals("list") ? 0 : 298) * GUI_SCALE), (this.xSize - 19) * GUI_SCALE, this.ySize * GUI_SCALE, this.xSize - 19, this.ySize, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
        ClientEventHandler.STYLE.bindTexture("loto");

        if (mouseX >= this.guiLeft + 367 && mouseX <= this.guiLeft + 367 + 9 && mouseY >= this.guiTop + 4 && mouseY <= this.guiTop + 4 + 9)
        {
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 367), (float)(this.guiTop + 4), (float)(442 * GUI_SCALE), (float)(53 * GUI_SCALE), 9 * GUI_SCALE, 9 * GUI_SCALE, 9, 9, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
            this.hoveredAction = "close";
        }
        else
        {
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 367), (float)(this.guiTop + 4), (float)(431 * GUI_SCALE), (float)(53 * GUI_SCALE), 9 * GUI_SCALE, 9 * GUI_SCALE, 9, 9, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
        }

        ModernGui.drawScaledStringCustomFont(I18n.getString("lottery.admin.title"), (float)(this.guiLeft + 43), (float)(this.guiTop + 13), 10395075, 0.5F, "left", false, "georamaMedium", 32);

        if (loaded)
        {
            int i;

            if (this.displayMode.equals("list"))
            {
                ModernGui.drawScaledStringCustomFont(I18n.getString("lottery.admin.to_come"), (float)(this.guiLeft + 43), (float)(this.guiTop + 23), 16777215, 0.75F, "left", false, "georamaSemiBold", 32);
                ClientEventHandler.STYLE.bindTexture("loto_staff");
                ModernGui.drawScaledCustomSizeModalRect((float)this.guiLeft, (float)this.guiTop, (float)(385 * GUI_SCALE), (float)(0 * GUI_SCALE), 19 * GUI_SCALE, 37 * GUI_SCALE, 19, 37, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                ModernGui.drawScaledCustomSizeModalRect((float)this.guiLeft, (float)this.guiTop, (float)(385 * GUI_SCALE), (float)(40 * GUI_SCALE), 19 * GUI_SCALE, 19 * GUI_SCALE, 19, 19, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);

                if (mouseX >= this.guiLeft && mouseX <= this.guiLeft + 19 && mouseY >= this.guiTop + 19 && mouseY <= this.guiTop + 19 + 18)
                {
                    ModernGui.drawScaledCustomSizeModalRect((float)this.guiLeft, (float)(this.guiTop + 19), (float)(404 * GUI_SCALE), (float)(59 * GUI_SCALE), 19 * GUI_SCALE, 19 * GUI_SCALE, 19, 19, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                    this.hoveredAction = "admin_create";
                }
                else
                {
                    ModernGui.drawScaledCustomSizeModalRect((float)this.guiLeft, (float)(this.guiTop + 19), (float)(366 * GUI_SCALE), (float)(59 * GUI_SCALE), 19 * GUI_SCALE, 19 * GUI_SCALE, 19, 19, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                }

                int fieldOffsetY = 0;

                for (Iterator index = ((ArrayList)data.get("lotteries")).iterator(); index.hasNext(); ++fieldOffsetY)
                {
                    LinkedTreeMap it = (LinkedTreeMap)index.next();
                    GUIUtils.startGLScissor(this.guiLeft + 42, this.guiTop + 45, 315, 160);
                    i = this.guiLeft + 42;
                    Float itemStack = Float.valueOf((float)(this.guiTop + 45 + fieldOffsetY * 39) + this.getSlideLotteries());
                    ClientEventHandler.STYLE.bindTexture("loto_staff");
                    ModernGui.drawScaledCustomSizeModalRect((float)i, (float)itemStack.intValue(), (float)(0 * GUI_SCALE), (float)(219 * GUI_SCALE), 245 * GUI_SCALE, 35 * GUI_SCALE, 245, 35, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                    ModernGui.glColorHex(((Integer)LotoGui.colors.get(it.get("designColor"))).intValue(), 1.0F);
                    ModernGui.drawRoundedRectangle((float)i + 234.0F, itemStack.floatValue(), this.zLevel, 81.0F, 35.0F);
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    ClientEventHandler.STYLE.bindTexture("loto");
                    ModernGui.drawScaledCustomSizeModalRect((float)(i + 2), (float)(itemStack.intValue() + 1), (float)(((Integer)LotoGui.iconsX.get("calendar")).intValue() * GUI_SCALE), (float)(LotoGui.getElementYByColor(16, (String)it.get("designColor")) * GUI_SCALE), 11 * GUI_SCALE, 11 * GUI_SCALE, 11, 11, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
                    String date = "";

                    if (System.currentTimeMillis() > ((Double)it.get("startTime")).longValue())
                    {
                        date = I18n.getString("lottery.label.in_progress") + " " + LotoGui.dateFormat.format(new Date(((Double)it.get("endTime")).longValue()));
                    }
                    else
                    {
                        date = I18n.getString("lottery.label.incoming") + " " + LotoGui.dateFormat.format(new Date(((Double)it.get("startTime")).longValue()));
                    }

                    ModernGui.drawScaledStringCustomFont(date, (float)(i + 15), (float)(itemStack.intValue() + 4), 16514302, 0.5F, "left", false, "georamaMedium", 26);
                    ClientEventHandler.STYLE.bindTexture("loto");
                    ModernGui.drawScaledCustomSizeModalRect((float)(i + 2), (float)(itemStack.intValue() + 12), (float)(((Integer)LotoGui.iconsX.get("people")).intValue() * GUI_SCALE), (float)(LotoGui.getElementYByColor(16, (String)it.get("designColor")) * GUI_SCALE), 11 * GUI_SCALE, 11 * GUI_SCALE, 11, 11, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
                    ModernGui.drawScaledStringCustomFont((((String)it.get("players")).isEmpty() ? "0" : Integer.valueOf(((String)it.get("players")).split(",").length)) + " " + I18n.getString("lottery.label.players"), (float)(i + 15), (float)(itemStack.intValue() + 15), 16514302, 0.5F, "left", false, "georamaMedium", 26);
                    ClientEventHandler.STYLE.bindTexture("loto");
                    ModernGui.drawScaledCustomSizeModalRect((float)(i + 2), (float)(itemStack.intValue() + 23), (float)(((Integer)LotoGui.iconsX.get("trophee")).intValue() * GUI_SCALE), (float)(LotoGui.getElementYByColor(16, (String)it.get("designColor")) * GUI_SCALE), 11 * GUI_SCALE, 11 * GUI_SCALE, 11, 11, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
                    ModernGui.drawScaledStringCustomFont(String.format("%.0f", new Object[] {(Double)it.get("winnersCount")}) + " " + I18n.getString("lottery.label.winners"), (float)(i + 15), (float)(itemStack.intValue() + 26), 16514302, 0.5F, "left", false, "georamaMedium", 26);
                    ModernGui.drawScaledStringCustomFont("ID: " + String.format("%.0f", new Object[] {(Double)it.get("id")}), (float)(i + 125), (float)(itemStack.intValue() + 3), 15463162, 0.5F, "left", false, "georamaMedium", 25);
                    ModernGui.drawScaledStringCustomFont(String.format("%.0f", new Object[] {(Double)it.get("cashPrice")}) + "$", (float)(i + 125), (float)(itemStack.intValue() + 10), 15463162, 0.75F, "left", false, "georamaSemiBold", 28);
                    ModernGui.drawScaledStringCustomFont((((String)it.get("itemsPrice")).isEmpty() ? "0" : Integer.valueOf(((String)it.get("itemsPrice")).split(",").length)) + " item(s)", (float)(i + 125), (float)(itemStack.intValue() + 22), 15463162, 0.5F, "left", false, "georamaSemiBold", 28);

                    if (!((String)it.get("designLogo")).isEmpty() && ((String)it.get("designLogo")).matches("https://static.nationsglory.fr/.*\\.png"))
                    {
                        ModernGui.bindRemoteTexture((String)it.get("designLogo"));
                        ModernGui.drawScaledCustomSizeModalRect((float)(i + 245), (float)(itemStack.intValue() + 5), (float)(0 * GUI_SCALE), (float)(0 * GUI_SCALE), 600, 250, 60, 25, 600.0F, 250.0F, false);
                    }
                    else
                    {
                        ClientEventHandler.STYLE.bindTexture("loto");
                        ModernGui.drawScaledCustomSizeModalRect((float)(i + 245), (float)(itemStack.intValue() + 5), (float)(471 * GUI_SCALE), (float)(411 * GUI_SCALE), 120 * GUI_SCALE, 50 * GUI_SCALE, 60, 25, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
                    }

                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

                    if (mouseX >= i && mouseX <= i + 315 && mouseY >= itemStack.intValue() && mouseY <= itemStack.intValue() + 35)
                    {
                        this.hoveredAction = "admin_edit";
                        this.hoveredLotteryId = fieldOffsetY;
                    }

                    GUIUtils.endGLScissor();
                    this.scrollBar.draw(mouseX, mouseY);
                }
            }
            else if (this.displayMode.equals("edit"))
            {
                ModernGui.drawScaledStringCustomFont(I18n.getString("lottery.admin.create"), (float)(this.guiLeft + 43), (float)(this.guiTop + 23), 16777215, 0.75F, "left", false, "georamaSemiBold", 32);
                ClientEventHandler.STYLE.bindTexture("loto_staff");
                ModernGui.drawScaledCustomSizeModalRect((float)this.guiLeft, (float)this.guiTop, (float)(365 * GUI_SCALE), (float)(0 * GUI_SCALE), 19 * GUI_SCALE, 37 * GUI_SCALE, 19, 37, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                ModernGui.drawScaledCustomSizeModalRect((float)this.guiLeft, (float)(this.guiTop + 19), (float)(385 * GUI_SCALE), (float)(59 * GUI_SCALE), 19 * GUI_SCALE, 19 * GUI_SCALE, 19, 19, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);

                if (mouseX >= this.guiLeft && mouseX <= this.guiLeft + 19 && mouseY >= this.guiTop && mouseY <= this.guiTop + 18)
                {
                    ModernGui.drawScaledCustomSizeModalRect((float)this.guiLeft, (float)this.guiTop, (float)(404 * GUI_SCALE), (float)(40 * GUI_SCALE), 19 * GUI_SCALE, 19 * GUI_SCALE, 19, 19, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                    this.hoveredAction = "admin_list";
                }
                else
                {
                    ModernGui.drawScaledCustomSizeModalRect((float)this.guiLeft, (float)this.guiTop, (float)(366 * GUI_SCALE), (float)(40 * GUI_SCALE), 19 * GUI_SCALE, 19 * GUI_SCALE, 19, 19, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                }

                ModernGui.drawScaledStringCustomFont(I18n.getString("lottery.admin.a_propos"), (float)(this.guiLeft + 42), (float)(this.guiTop + 44), 14803951, 0.5F, "left", false, "georamaSemiBold", 28);
                byte var11 = 65;
                ModernGui.drawScaledStringCustomFont(I18n.getString("lottery.admin.start_date"), (float)(this.guiLeft + 48), (float)(this.guiTop + var11), 14803951, 0.5F, "left", false, "georamaMedium", 24);
                ClientEventHandler.STYLE.bindTexture("loto_staff");
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 103), (float)(this.guiTop + var11 - 2), (float)(249 * GUI_SCALE), (float)(268 * GUI_SCALE), 30 * GUI_SCALE, 9 * GUI_SCALE, 30, 9, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                this.startDateDateInput.drawTextBox();
                ClientEventHandler.STYLE.bindTexture("loto_staff");
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 137), (float)(this.guiTop + var11 - 2), (float)(249 * GUI_SCALE), (float)(268 * GUI_SCALE), 30 * GUI_SCALE, 9 * GUI_SCALE, 30, 9, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                this.startDateTimeInput.drawTextBox();
                var11 = 79;
                ModernGui.drawScaledStringCustomFont(I18n.getString("lottery.admin.end_date"), (float)(this.guiLeft + 48), (float)(this.guiTop + var11), 14803951, 0.5F, "left", false, "georamaMedium", 24);
                ClientEventHandler.STYLE.bindTexture("loto_staff");
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 103), (float)(this.guiTop + var11 - 2), (float)(249 * GUI_SCALE), (float)(268 * GUI_SCALE), 30 * GUI_SCALE, 9 * GUI_SCALE, 30, 9, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                this.endDateDateInput.drawTextBox();
                ClientEventHandler.STYLE.bindTexture("loto_staff");
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 137), (float)(this.guiTop + var11 - 2), (float)(249 * GUI_SCALE), (float)(268 * GUI_SCALE), 30 * GUI_SCALE, 9 * GUI_SCALE, 30, 9, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                this.endDateTimeInput.drawTextBox();
                var11 = 93;
                ModernGui.drawScaledStringCustomFont(I18n.getString("lottery.admin.limit_global"), (float)(this.guiLeft + 48), (float)(this.guiTop + var11), 14803951, 0.5F, "left", false, "georamaMedium", 24);
                ClientEventHandler.STYLE.bindTexture("loto_staff");
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 103), (float)(this.guiTop + var11 - 2), (float)(249 * GUI_SCALE), (float)(268 * GUI_SCALE), 30 * GUI_SCALE, 9 * GUI_SCALE, 30, 9, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                this.limitGlobalInput.drawTextBox();
                var11 = 107;
                ModernGui.drawScaledStringCustomFont(I18n.getString("lottery.admin.limit_player"), (float)(this.guiLeft + 48), (float)(this.guiTop + var11), 14803951, 0.5F, "left", false, "georamaMedium", 24);
                ClientEventHandler.STYLE.bindTexture("loto_staff");
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 103), (float)(this.guiTop + var11 - 2), (float)(249 * GUI_SCALE), (float)(268 * GUI_SCALE), 30 * GUI_SCALE, 9 * GUI_SCALE, 30, 9, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                this.limitPlayerInput.drawTextBox();
                ClientEventHandler.STYLE.bindTexture("loto_staff");
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 48), (float)(this.guiTop + var11 + 12), (float)((this.checkbox_donation ? 295 : 285) * GUI_SCALE), (float)(282 * GUI_SCALE), 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);

                if (mouseX >= this.guiLeft + 48 && mouseX <= this.guiLeft + 48 + 8 && mouseY >= this.guiTop + var11 + 12 && mouseY <= this.guiTop + var11 + 12 + 9)
                {
                    this.hoveredAction = "checkbox_donation";
                }

                ModernGui.drawScaledStringCustomFont(I18n.getString("lottery.admin.allow_donation"), (float)(this.guiLeft + 59), (float)(this.guiTop + var11 + 12 + 1), 14803951, 0.5F, "left", false, "georamaMedium", 24);
                ModernGui.drawScaledStringCustomFont(I18n.getString("lottery.admin.repeat"), (float)(this.guiLeft + 48), (float)(this.guiTop + 135), 14803951, 0.5F, "left", false, "georamaMedium", 24);
                ClientEventHandler.STYLE.bindTexture("loto_staff");
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 103), (float)(this.guiTop + 135 - 2), (float)(249 * GUI_SCALE), (float)(268 * GUI_SCALE), 30 * GUI_SCALE, 9 * GUI_SCALE, 30, 9, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                this.repetitionInput.drawTextBox();
                ModernGui.drawScaledStringCustomFont(I18n.getString("lottery.admin.ticket_price"), (float)(this.guiLeft + 48), (float)(this.guiTop + 149), 14803951, 0.5F, "left", false, "georamaMedium", 24);
                ClientEventHandler.STYLE.bindTexture("loto_staff");
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 103), (float)(this.guiTop + 149 - 2), (float)(249 * GUI_SCALE), (float)(268 * GUI_SCALE), 30 * GUI_SCALE, 9 * GUI_SCALE, 30, 9, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                this.ticketPriceInput.drawTextBox();
                ModernGui.drawScaledStringCustomFont(I18n.getString("lottery.admin.color_design"), (float)(this.guiLeft + 48), (float)(this.guiTop + 162), 14803951, 0.5F, "left", false, "georamaMedium", 24);
                ClientEventHandler.STYLE.bindTexture("loto_staff");
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 48), (float)(this.guiTop + 169), (float)(369 * GUI_SCALE), (float)(268 * GUI_SCALE), 141 * GUI_SCALE, 12 * GUI_SCALE, 141, 12, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                int var12 = 0;

                for (Iterator var13 = LotoGui.colors.entrySet().iterator(); var13.hasNext(); ++var12)
                {
                    Entry var14 = (Entry)var13.next();
                    Gui.drawRect(this.guiLeft + 52 + var12 * 14, this.guiTop + 171, this.guiLeft + 52 + var12 * 14 + 8, this.guiTop + 171 + 8, ((Integer)var14.getValue()).intValue());
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

                    if (this.selected_color.equals(var14.getKey()))
                    {
                        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 52 + var12 * 14), (float)(this.guiTop + 171), (float)(285 * GUI_SCALE), (float)(282 * GUI_SCALE), 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                    }

                    if (mouseX >= this.guiLeft + 52 + var12 * 14 && mouseX <= this.guiLeft + 52 + var12 * 14 + 8 && mouseY >= this.guiTop + 171 && mouseY <= this.guiTop + 171 + 8)
                    {
                        this.hoveredAction = "select_color#" + var14.getKey();
                    }
                }

                ModernGui.drawScaledStringCustomFont(I18n.getString("lottery.admin.logo_design"), (float)(this.guiLeft + 48), (float)(this.guiTop + 187), 14803951, 0.5F, "left", false, "georamaMedium", 24);
                ClientEventHandler.STYLE.bindTexture("loto_staff");
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 103), (float)(this.guiTop + 187 - 2), (float)(369 * GUI_SCALE), (float)(284 * GUI_SCALE), 80 * GUI_SCALE, 9 * GUI_SCALE, 80, 9, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                this.customLogoInput.drawTextBox();
                ModernGui.drawScaledStringCustomFont(I18n.getString("lottery.admin.rewards"), (float)(this.guiLeft + 205), (float)(this.guiTop + 44), 14803951, 0.5F, "left", false, "georamaSemiBold", 28);
                ModernGui.drawScaledStringCustomFont(I18n.getString("lottery.admin.cashprice"), (float)(this.guiLeft + 214), (float)(this.guiTop + 59), 14803951, 0.5F, "left", false, "georamaMedium", 24);
                ClientEventHandler.STYLE.bindTexture("loto_staff");
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 275), (float)(this.guiTop + 59 - 2), (float)(249 * GUI_SCALE), (float)(268 * GUI_SCALE), 30 * GUI_SCALE, 9 * GUI_SCALE, 30, 9, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                this.cashPriceInput.drawTextBox();
                ClientEventHandler.STYLE.bindTexture("loto_staff");

                for (i = 0; i < 9; ++i)
                {
                    ClientEventHandler.STYLE.bindTexture("loto_staff");
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 214 + 12 * i), (float)(this.guiTop + 72), (float)(285 * GUI_SCALE), (float)(268 * GUI_SCALE), 10 * GUI_SCALE, 10 * GUI_SCALE, 10, 10, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);

                    if (this.itemsPrice.size() > i)
                    {
                        ItemStack var15 = (ItemStack)this.itemsPrice.get(i);
                        GL11.glPushMatrix();
                        GL11.glScalef(0.5F, 0.5F, 0.5F);
                        this.itemRenderer.renderItemAndEffectIntoGUI(this.fontRenderer, Minecraft.getMinecraft().getTextureManager(), var15, (this.guiLeft + 215 + 12 * i) * 2, (this.guiTop + 73) * 2);
                        GL11.glPopMatrix();
                        GL11.glDisable(GL11.GL_LIGHTING);
                        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    }
                }

                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                ClientEventHandler.STYLE.bindTexture("loto_staff");

                if (mouseX >= this.guiLeft + 214 && mouseX <= this.guiLeft + 214 + 62 && mouseY >= this.guiTop + 85 && mouseY <= this.guiTop + 85 + 10)
                {
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 214), (float)(this.guiTop + 85), (float)(299 * GUI_SCALE), (float)(234 * GUI_SCALE), 62 * GUI_SCALE, 10 * GUI_SCALE, 62, 10, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                    this.hoveredAction = "add_items";
                }
                else
                {
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 214), (float)(this.guiTop + 85), (float)(299 * GUI_SCALE), (float)(219 * GUI_SCALE), 62 * GUI_SCALE, 10 * GUI_SCALE, 62, 10, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                }

                ModernGui.drawScaledStringCustomFont(I18n.getString(!this.itemsPrice.isEmpty() ? "lottery.admin.remove_items" : "lottery.admin.add_items"), (float)(this.guiLeft + 214 + 31), (float)(this.guiTop + 87), 2234425, 0.5F, "center", false, "georamaSemiBold", 24);
                ClientEventHandler.STYLE.bindTexture("loto_staff");
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 214), (float)(this.guiTop + 100), (float)((this.checkbox_pool ? 295 : 285) * GUI_SCALE), (float)(282 * GUI_SCALE), 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);

                if (mouseX >= this.guiLeft + 214 && mouseX <= this.guiLeft + 214 + 8 && mouseY >= this.guiTop + 100 && mouseY <= this.guiTop + 100 + 9)
                {
                    this.hoveredAction = "checkbox_poll";
                }

                ModernGui.drawScaledStringCustomFont(I18n.getString("lottery.admin.mode_poll"), (float)(this.guiLeft + 225), (float)(this.guiTop + 101), 14803951, 0.5F, "left", false, "georamaMedium", 24);
                ModernGui.drawScaledStringCustomFont(I18n.getString("lottery.admin.count_winners"), (float)(this.guiLeft + 214), (float)(this.guiTop + 116), 14803951, 0.5F, "left", false, "georamaMedium", 24);
                ClientEventHandler.STYLE.bindTexture("loto_staff");
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 275), (float)(this.guiTop + 116 - 2), (float)(249 * GUI_SCALE), (float)(268 * GUI_SCALE), 30 * GUI_SCALE, 9 * GUI_SCALE, 30, 9, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                this.winnersCountInput.drawTextBox();
                ModernGui.glColorHex(((Integer)LotoGui.colors.get(this.selected_color)).intValue(), 1.0F);
                ModernGui.drawRoundedRectangle((float)this.guiLeft + 205.0F, (float)this.guiTop + 145.0F, this.zLevel, 152.0F, 43.0F);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

                if (!this.customLogoInput.getText().isEmpty() && this.customLogoInput.getText().matches("https://static.nationsglory.fr/.*\\.png"))
                {
                    ModernGui.bindRemoteTexture(this.customLogoInput.getText());
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 251), (float)(this.guiTop + 154), (float)(0 * GUI_SCALE), (float)(0 * GUI_SCALE), 600, 250, 60, 25, 600.0F, 250.0F, false);
                }
                else
                {
                    ClientEventHandler.STYLE.bindTexture("loto");
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 251), (float)(this.guiTop + 154), (float)(471 * GUI_SCALE), (float)(411 * GUI_SCALE), 120 * GUI_SCALE, 50 * GUI_SCALE, 60, 25, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
                }

                ClientEventHandler.STYLE.bindTexture("loto_staff");

                if (mouseX >= this.guiLeft + 205 && mouseX <= this.guiLeft + 205 + 44 && mouseY >= this.guiTop + 194 && mouseY <= this.guiTop + 194 + 10)
                {
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 205), (float)(this.guiTop + 194), (float)(249 * GUI_SCALE), (float)(249 * GUI_SCALE), 44 * GUI_SCALE, 11 * GUI_SCALE, 44, 11, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                    this.hoveredAction = "create_valid";
                }
                else
                {
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 205), (float)(this.guiTop + 194), (float)(249 * GUI_SCALE), (float)(219 * GUI_SCALE), 44 * GUI_SCALE, 11 * GUI_SCALE, 44, 11, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                }

                ModernGui.drawScaledStringCustomFont(I18n.getString("lottery.admin.validate"), (float)(this.guiLeft + 205 + 22), (float)(this.guiTop + 196), 2234425, 0.5F, "center", false, "georamaSemiBold", 26);
                ClientEventHandler.STYLE.bindTexture("loto_staff");

                if (mouseX >= this.guiLeft + 252 && mouseX <= this.guiLeft + 252 + 62 && mouseY >= this.guiTop + 194 && mouseY <= this.guiTop + 194 + 10)
                {
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 252), (float)(this.guiTop + 194), (float)(249 * GUI_SCALE), (float)(249 * GUI_SCALE), 44 * GUI_SCALE, 11 * GUI_SCALE, 44, 11, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                    this.hoveredAction = "create_cancel";
                    ModernGui.drawScaledStringCustomFont(I18n.getString("lottery.admin.cancel"), (float)(this.guiLeft + 252 + 22), (float)(this.guiTop + 196), 2234425, 0.5F, "center", false, "georamaSemiBold", 26);
                }
                else
                {
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 252), (float)(this.guiTop + 194), (float)(249 * GUI_SCALE), (float)(234 * GUI_SCALE), 44 * GUI_SCALE, 11 * GUI_SCALE, 44, 11, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                    ModernGui.drawScaledStringCustomFont(I18n.getString("lottery.admin.cancel"), (float)(this.guiLeft + 252 + 22), (float)(this.guiTop + 196), 14803951, 0.5F, "center", false, "georamaSemiBold", 26);
                }

                if (!this.dataToEdit.isEmpty())
                {
                    ClientEventHandler.STYLE.bindTexture("loto_staff");

                    if (mouseX >= this.guiLeft + 299 && mouseX <= this.guiLeft + 299 + 62 && mouseY >= this.guiTop + 194 && mouseY <= this.guiTop + 194 + 10)
                    {
                        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 299), (float)(this.guiTop + 194), (float)(249 * GUI_SCALE), (float)(249 * GUI_SCALE), 44 * GUI_SCALE, 11 * GUI_SCALE, 44, 11, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                        this.hoveredAction = "delete";
                        ModernGui.drawScaledStringCustomFont(I18n.getString("lottery.admin.delete"), (float)(this.guiLeft + 299 + 22), (float)(this.guiTop + 196), 2234425, 0.5F, "center", false, "georamaSemiBold", 26);
                    }
                    else
                    {
                        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 299), (float)(this.guiTop + 194), (float)(367 * GUI_SCALE), (float)(219 * GUI_SCALE), 44 * GUI_SCALE, 11 * GUI_SCALE, 44, 11, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                        ModernGui.drawScaledStringCustomFont(I18n.getString("lottery.admin.delete"), (float)(this.guiLeft + 299 + 22), (float)(this.guiTop + 196), 15463162, 0.5F, "center", false, "georamaSemiBold", 26);
                    }
                }
            }
        }

        if (!tooltipToDraw.isEmpty())
        {
            this.drawHoveringText(tooltipToDraw, mouseX, mouseY, this.fontRenderer);
        }

        super.drawScreen(mouseX, mouseY, par3);
        GL11.glEnable(GL11.GL_LIGHTING);
        RenderHelper.enableStandardItemLighting();
    }

    private float getSlideLotteries()
    {
        return ((ArrayList)data.get("lotteries")).size() > 4 ? (float)(-(((ArrayList)data.get("lotteries")).size() - 4) * 39) * this.scrollBar.getSliderValue() : 0.0F;
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (mouseButton == 0)
        {
            if (this.displayMode.equals("edit"))
            {
                this.startDateDateInput.mouseClicked(mouseX, mouseY, mouseButton);
                this.startDateTimeInput.mouseClicked(mouseX, mouseY, mouseButton);
                this.endDateDateInput.mouseClicked(mouseX, mouseY, mouseButton);
                this.endDateTimeInput.mouseClicked(mouseX, mouseY, mouseButton);
                this.limitGlobalInput.mouseClicked(mouseX, mouseY, mouseButton);
                this.limitPlayerInput.mouseClicked(mouseX, mouseY, mouseButton);
                this.repetitionInput.mouseClicked(mouseX, mouseY, mouseButton);
                this.ticketPriceInput.mouseClicked(mouseX, mouseY, mouseButton);
                this.customLogoInput.mouseClicked(mouseX, mouseY, mouseButton);
                this.cashPriceInput.mouseClicked(mouseX, mouseY, mouseButton);
                this.winnersCountInput.mouseClicked(mouseX, mouseY, mouseButton);
            }

            if (this.hoveredAction.equals("close"))
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
            }
            else if (this.hoveredAction.equals("admin_create"))
            {
                this.displayMode = "edit";
                this.initGui();
            }
            else if (this.hoveredAction.equals("admin_edit"))
            {
                this.dataToEdit = (LinkedTreeMap)((ArrayList)data.get("lotteries")).get(this.hoveredLotteryId);
                this.displayMode = "edit";
                this.initGui();
            }
            else if (this.hoveredAction.equals("admin_list"))
            {
                this.dataToEdit.clear();
                this.initGui();
                this.displayMode = "list";
            }
            else if (this.hoveredAction.equals("checkbox_donation"))
            {
                this.checkbox_donation = !this.checkbox_donation;
            }
            else if (this.hoveredAction.equals("checkbox_poll"))
            {
                this.checkbox_pool = !this.checkbox_pool;
            }
            else if (this.hoveredAction.equals("add_items"))
            {
                if (this.itemsPrice.isEmpty())
                {
                    this.itemsPriceAction = "update";

                    for (int e = 0; e < 9; ++e)
                    {
                        ItemStack startDate = Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(e);

                        if (startDate != null)
                        {
                            this.itemsPrice.add(startDate);
                        }
                    }
                }
                else
                {
                    this.itemsPriceAction = "clear";
                    this.itemsPrice.clear();
                }
            }
            else if (this.hoveredAction.contains("select_color"))
            {
                this.selected_color = this.hoveredAction.replace("select_color#", "");
            }
            else if (this.hoveredAction.contains("create_cancel"))
            {
                this.dataToEdit.clear();
                this.displayMode = "list";
            }
            else if (this.hoveredAction.contains("delete"))
            {
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new LotoDeletePacket(((Double)this.dataToEdit.get("id")).intValue())));
                this.dataToEdit.clear();
                this.displayMode = "list";
            }
            else if (this.hoveredAction.contains("create_valid"))
            {
                try
                {
                    HashMap var8 = new HashMap();

                    if (!this.startDateDateInput.getText().matches("\\d{2}/\\d{2}/\\d{2}"))
                    {
                        Minecraft.getMinecraft().thePlayer.addChatMessage(I18n.getString("lotery.error.wrong_data"));
                        return;
                    }

                    if (!this.startDateTimeInput.getText().matches("\\d{2}:\\d{2}"))
                    {
                        Minecraft.getMinecraft().thePlayer.addChatMessage(I18n.getString("lotery.error.wrong_data"));
                        return;
                    }

                    if (!FactionGUI.isNumeric(this.limitGlobalInput.getText(), true) || !FactionGUI.isNumeric(this.limitPlayerInput.getText(), true) || !FactionGUI.isNumeric(this.repetitionInput.getText(), true) || !FactionGUI.isNumeric(this.ticketPriceInput.getText(), false) || !FactionGUI.isNumeric(this.cashPriceInput.getText(), true) || !FactionGUI.isNumeric(this.winnersCountInput.getText(), false))
                    {
                        Minecraft.getMinecraft().thePlayer.addChatMessage(I18n.getString("lotery.error.wrong_data"));
                        return;
                    }

                    Date var9 = (new SimpleDateFormat("dd/MM/yy HH:mm")).parse(this.startDateDateInput.getText() + " " + this.startDateTimeInput.getText());
                    var8.put("startTime", var9.getTime() + "");
                    Date endDate = (new SimpleDateFormat("dd/MM/yy HH:mm")).parse(this.endDateDateInput.getText() + " " + this.endDateTimeInput.getText());

                    if (endDate.getTime() < System.currentTimeMillis())
                    {
                        Minecraft.getMinecraft().thePlayer.addChatMessage(I18n.getString("lotery.error.end_date_future"));
                        return;
                    }

                    var8.put("endTime", endDate.getTime() + "");
                    var8.put("maxTicketsGlobal", this.limitGlobalInput.getText());
                    var8.put("maxTicketsPlayer", this.limitPlayerInput.getText());
                    var8.put("allowDonation", this.checkbox_donation + "");
                    var8.put("repetitionDays", this.repetitionInput.getText());
                    var8.put("ticketPrice", this.ticketPriceInput.getText());
                    var8.put("designColor", this.selected_color);
                    var8.put("designLogo", this.customLogoInput.getText());
                    var8.put("cashPrice", this.cashPriceInput.getText());
                    var8.put("poolMode", this.checkbox_pool + "");
                    var8.put("winnersCount", this.winnersCountInput.getText());
                    var8.put("itemsPrice", this.itemsPriceAction);
                    var8.put("lotteryIdForEdit", this.dataToEdit.containsKey("id") ? ((Double)this.dataToEdit.get("id")).intValue() + "" : "-1");
                    PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new LotoCreatePacket(var8)));
                }
                catch (Exception var7)
                {
                    var7.printStackTrace();
                }
            }
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.startDateDateInput.updateCursorCounter();
        this.startDateTimeInput.updateCursorCounter();
        this.endDateDateInput.updateCursorCounter();
        this.endDateTimeInput.updateCursorCounter();
        this.limitGlobalInput.updateCursorCounter();
        this.limitPlayerInput.updateCursorCounter();
        this.repetitionInput.updateCursorCounter();
        this.ticketPriceInput.updateCursorCounter();
        this.customLogoInput.updateCursorCounter();
        this.cashPriceInput.updateCursorCounter();
        this.winnersCountInput.updateCursorCounter();
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2)
    {
        this.startDateDateInput.textboxKeyTyped(par1, par2);
        this.startDateTimeInput.textboxKeyTyped(par1, par2);
        this.endDateDateInput.textboxKeyTyped(par1, par2);
        this.endDateTimeInput.textboxKeyTyped(par1, par2);
        this.limitGlobalInput.textboxKeyTyped(par1, par2);
        this.limitPlayerInput.textboxKeyTyped(par1, par2);
        this.repetitionInput.textboxKeyTyped(par1, par2);
        this.ticketPriceInput.textboxKeyTyped(par1, par2);
        this.customLogoInput.textboxKeyTyped(par1, par2);
        this.cashPriceInput.textboxKeyTyped(par1, par2);
        this.winnersCountInput.textboxKeyTyped(par1, par2);
        super.keyTyped(par1, par2);
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    protected void drawHoveringText(List par1List, int par2, int par3, FontRenderer font)
    {
        if (!par1List.isEmpty())
        {
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            int k = 0;
            Iterator iterator = par1List.iterator();
            int j1;

            while (iterator.hasNext())
            {
                String i1 = (String)iterator.next();
                j1 = font.getStringWidth(i1);

                if (j1 > k)
                {
                    k = j1;
                }
            }

            int var15 = par2 + 12;
            j1 = par3;
            int k1 = 8;

            if (par1List.size() > 1)
            {
                k1 += 2 + (par1List.size() - 1) * 10;
            }

            if (var15 + k > this.width)
            {
                var15 -= 28 + k;
            }

            if (par3 + k1 + 6 > this.height)
            {
                j1 = this.height - k1 - 6;
            }

            this.zLevel = 300.0F;
            this.itemRenderer.zLevel = 300.0F;
            int l1 = -267386864;
            this.drawGradientRect(var15 - 3, j1 - 4, var15 + k + 3, j1 - 3, l1, l1);
            this.drawGradientRect(var15 - 3, j1 + k1 + 3, var15 + k + 3, j1 + k1 + 4, l1, l1);
            this.drawGradientRect(var15 - 3, j1 - 3, var15 + k + 3, j1 + k1 + 3, l1, l1);
            this.drawGradientRect(var15 - 4, j1 - 3, var15 - 3, j1 + k1 + 3, l1, l1);
            this.drawGradientRect(var15 + k + 3, j1 - 3, var15 + k + 4, j1 + k1 + 3, l1, l1);
            int i2 = 1347420415;
            int j2 = (i2 & 16711422) >> 1 | i2 & -16777216;
            this.drawGradientRect(var15 - 3, j1 - 3 + 1, var15 - 3 + 1, j1 + k1 + 3 - 1, i2, j2);
            this.drawGradientRect(var15 + k + 2, j1 - 3 + 1, var15 + k + 3, j1 + k1 + 3 - 1, i2, j2);
            this.drawGradientRect(var15 - 3, j1 - 3, var15 + k + 3, j1 - 3 + 1, i2, i2);
            this.drawGradientRect(var15 - 3, j1 + k1 + 2, var15 + k + 3, j1 + k1 + 3, j2, j2);

            for (int k2 = 0; k2 < par1List.size(); ++k2)
            {
                String s1 = (String)par1List.get(k2);
                font.drawStringWithShadow(s1, var15, j1, -1);

                if (k2 == 0)
                {
                    j1 += 2;
                }

                j1 += 10;
            }

            this.zLevel = 0.0F;
            this.itemRenderer.zLevel = 0.0F;
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    private void drawItemStack(ItemStack par1ItemStack, int par2, int par3, String par4Str)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, 0.0F, 32.0F);
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        GL11.glEnable(GL11.GL_LIGHTING);
        this.zLevel = 200.0F;
        this.itemRenderer.zLevel = 200.0F;
        FontRenderer font = null;

        if (par1ItemStack != null)
        {
            font = par1ItemStack.getItem().getFontRenderer(par1ItemStack);
        }

        if (font == null)
        {
            font = this.fontRenderer;
        }

        this.itemRenderer.renderItemAndEffectIntoGUI(font, this.mc.getTextureManager(), par1ItemStack, par2, par3);
        this.itemRenderer.renderItemOverlayIntoGUI(font, this.mc.getTextureManager(), par1ItemStack, par2, par3, par4Str);
        this.zLevel = 0.0F;
        this.itemRenderer.zLevel = 0.0F;
        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        RenderHelper.enableStandardItemLighting();
    }
}
