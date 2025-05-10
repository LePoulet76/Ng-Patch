package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import net.halalaboos.cfont.CFontRenderer;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.CustomTextAreaGUI;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarGeneric;
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
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class FactionCreateEditPlotsGUI extends GuiScreen
{
    public static int GUI_SCALE = 3;
    public String selectedType = "land";
    public static CFontRenderer dg22 = ModernGui.getCustomFont("minecraftDungeons", Integer.valueOf(22));
    public static CFontRenderer semiBold24 = ModernGui.getCustomFont("georamaSemiBold", Integer.valueOf(24));
    public static HashMap<String, Object> savedPlotForSelection = new HashMap();
    public static List<String> tooltipToDraw = new ArrayList();
    public static List<String> types = Arrays.asList(new String[] {"house", "apartment", "land", "other"});
    public static List<String> usages = Arrays.asList(new String[] {"all", "interact"});
    public static List<String> modes = Arrays.asList(new String[] {"sell", "rent"});
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

    public FactionCreateEditPlotsGUI(int plotId)
    {
        editedPlot = new HashMap();
        this.dataLoaded = false;

        if (plotId != -1)
        {
            loaded = false;
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionPlotDataPacket(plotId)));
        }
        else
        {
            loaded = true;
        }
    }

    public static boolean isValidNonNegativeInteger(String str)
    {
        if (str != null && !str.isEmpty())
        {
            try
            {
                int e = Integer.parseInt(str);
                return e >= 0;
            }
            catch (NumberFormatException var2)
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        this.scrollBarCoowners = new GuiScrollBarGeneric((float)(this.guiLeft + 95), (float)(this.guiTop + 125), 33, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_parrot.png"), 2, 8);
        this.inputName = new CustomInputFieldGUI(this.guiLeft + 12, this.guiTop + 47, 90, 12, "georamaMedium", 28);
        this.inputName.setMaxStringLength(24);
        this.inputDescription = new CustomTextAreaGUI(this.guiLeft + 12, this.guiTop + 80, 185, "georamaMedium", 22, 10);
        this.inputDescription.setMaxStringLength(200);
        this.inputPrice = new CustomInputFieldGUI(this.guiLeft + 113, this.guiTop + 47, 90, 12, "georamaMedium", 28);
        this.inputPrice.setMaxStringLength(8);
        this.inputPrice.setText("0");
        this.inputCoOwner = new CustomInputFieldGUI(this.guiLeft + 21, this.guiTop + 160, 64, 12, "georamaMedium", 28);
        this.inputCoOwner.setMaxStringLength(16);
    }

    private void updateInputValueWithData()
    {
        if (!this.dataLoaded && !editedPlot.isEmpty())
        {
            this.inputName.setText(editedPlot.containsKey("name") ? (String)editedPlot.get("name") : "");
            this.inputDescription.setText(editedPlot.containsKey("description") ? (String)editedPlot.get("description") : "");
            this.inputPrice.setText(editedPlot.containsKey("price") ? ((Double)editedPlot.get("price")).intValue() + "" : "0");
            this.dataLoaded = true;
            this.selectedType = (String)editedPlot.get("type");
            this.selectedUsage = (String)editedPlot.get("usage");
            this.selectedMode = (String)editedPlot.get("buyType");
            this.allowCoOwner = ((Boolean)editedPlot.get("canAddCoOwner")).booleanValue();

            if (editedPlot.get("owner") != null && !editedPlot.get("owner").equals(Minecraft.getMinecraft().thePlayer.username) && FactionGUI.hasPermissions("locations"))
            {
                this.editionMode = "creator";
            }
            else if (editedPlot.get("owner") != null && editedPlot.get("owner").equals(Minecraft.getMinecraft().thePlayer.username))
            {
                this.editionMode = "owner";
            }
        }
    }

    private float getSlidePlots()
    {
        if (editedPlot.isEmpty())
        {
            return 0.0F;
        }
        else
        {
            List whitelist = (List)editedPlot.get("whitelisted_players");
            return whitelist.size() > 4 ? (float)(-(whitelist.size() - 4) * 9) * this.scrollBarCoowners.getSliderValue() : 0.0F;
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTick)
    {
        this.drawDefaultBackground();
        tooltipToDraw = new ArrayList();
        this.hoveredAction = "";
        this.hoveredType = "";
        this.hoveredUsage = "";
        this.hoveredMode = "";
        this.updateInputValueWithData();
        Gui.drawRect(0, 0, this.width, this.height, -2145049275);
        ClientEventHandler.STYLE.bindTexture("faction_plots_3");
        ModernGui.drawScaledCustomSizeModalRect((float)this.guiLeft, (float)(this.guiTop + 25), (float)(0 * GUI_SCALE), (float)(0 * GUI_SCALE), this.xSize * GUI_SCALE, (this.ySize - 25) * GUI_SCALE, this.xSize, this.ySize - 25, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
        boolean hoveringClose;

        if (!editedPlot.isEmpty())
        {
            ClientEventHandler.STYLE.bindTexture("faction_plots_3");
            hoveringClose = mouseX >= this.width - 85 && mouseX < this.width - 85 + 57 && mouseY >= 4 && mouseY < 20;
            ModernGui.drawScaledCustomSizeModalRect((float)(this.width - 85), 4.0F, (float)(400 * GUI_SCALE), (float)((hoveringClose ? 235 : 215) * GUI_SCALE), 57 * GUI_SCALE, 16 * GUI_SCALE, 57, 16, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
            ModernGui.drawScaledStringCustomFont(I18n.getString("faction.plots.form.return"), (float)(this.width - 85 + 21), 9.0F, hoveringClose ? 3026518 : 16777215, 0.5F, "left", false, "georamaSemiBold", 24);

            if (hoveringClose)
            {
                this.hoveredAction = "return";
            }
        }

        ClientEventHandler.STYLE.bindTexture("faction_plots_3");
        hoveringClose = mouseX >= this.width - 22 && mouseX < this.width - 22 + 16 && mouseY >= 4 && mouseY < 20;
        ModernGui.drawScaledCustomSizeModalRect((float)(this.width - 22), 4.0F, (float)(465 * GUI_SCALE), (float)((hoveringClose ? 235 : 215) * GUI_SCALE), 16 * GUI_SCALE, 16 * GUI_SCALE, 16, 16, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);

        if (hoveringClose)
        {
            this.hoveredAction = "close";
        }

        ModernGui.drawScaledStringCustomFont(editedPlot.containsKey("name") ? I18n.getString("faction.plots.plot") + " " + editedPlot.get("name") : I18n.getString("faction.plots.new_plot"), (float)(this.guiLeft + 0), (float)(this.guiTop + 0), 16777215, 1.0F, "left", false, "minecraftDungeons", 22);
        boolean hoveringBtnprevisualize;

        if (!editedPlot.isEmpty() && !editedPlot.get("owner").equals("") && FactionGUI.hasPermissions("locations"))
        {
            ClientEventHandler.STYLE.bindTexture("faction_plots_3");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + this.xSize - 65), (float)this.guiTop, (float)(316 * GUI_SCALE), (float)((this.editionMode.equals("owner") ? 235 : 215) * GUI_SCALE), 65 * GUI_SCALE, 16 * GUI_SCALE, 65, 16, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
            ModernGui.drawScaledStringCustomFont(I18n.getString("faction.plots.form.editionMode." + this.editionMode), (float)(this.guiLeft + this.xSize - 65 + (this.editionMode.equals("owner") ? 20 : 10)), (float)(this.guiTop + 5), 16777215, 0.5F, "left", false, "georamaSemiBold", 24);
            hoveringBtnprevisualize = mouseX >= this.guiLeft + this.xSize - 65 && mouseX < this.guiLeft + this.xSize && mouseY >= this.guiTop && mouseY < this.guiTop + 16;

            if (hoveringBtnprevisualize)
            {
                this.hoveredAction = "toggleEditionMode";
            }
        }

        ModernGui.drawScaledStringCustomFont(I18n.getString("faction.plots.form.name"), (float)(this.guiLeft + 12), (float)(this.guiTop + 38), 16777215, 0.5F, "left", false, "georamaSemiBold", 24);
        this.inputName.drawTextBox();
        ModernGui.drawScaledStringCustomFont(I18n.getString("faction.plots.form.price"), (float)(this.guiLeft + 112), (float)(this.guiTop + 38), 16777215, 0.5F, "left", false, "georamaSemiBold", 24);
        this.inputPrice.drawTextBox();
        ModernGui.drawScaledStringCustomFont(I18n.getString("faction.plots.form.description"), (float)(this.guiLeft + 12), (float)(this.guiTop + 73), 16777215, 0.5F, "left", false, "georamaSemiBold", 24);
        this.inputDescription.drawTextBox();
        boolean hoveringBtnCancel;
        boolean var14;
        int var15;
        Iterator var17;
        boolean var18;
        String var20;

        if (!editedPlot.isEmpty() && (!FactionGUI.hasPermissions("locations") || !this.editionMode.equals("creator")))
        {
            if (!editedPlot.isEmpty())
            {
                ClientEventHandler.STYLE.bindTexture("faction_plots_3");
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 12), (float)(this.guiTop + 117), (float)(0 * GUI_SCALE), (float)(265 * GUI_SCALE), 93 * GUI_SCALE, 64 * GUI_SCALE, 93, 64, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.plots.form.coowners"), (float)(this.guiLeft + 12), (float)(this.guiTop + 109), ((Boolean)editedPlot.get("canAddCoOwner")).booleanValue() ? 16777215 : 1118502, 0.5F, "left", false, "georamaSemiBold", 24);

                if (((Boolean)editedPlot.get("canAddCoOwner")).booleanValue())
                {
                    GUIUtils.startGLScissor(this.guiLeft + 20, this.guiTop + 125, 72, 33);
                    List var16 = (List)editedPlot.get("whitelisted_players");
                    var15 = 0;

                    for (var17 = var16.iterator(); var17.hasNext(); ++var15)
                    {
                        var20 = (String)var17.next();
                        int var21 = this.guiLeft + 20;
                        Float offsetY = Float.valueOf((float)(this.guiTop + 125 + var15 * 9) + this.getSlidePlots());

                        if (!ClientProxy.cacheHeadPlayer.containsKey(var20))
                        {
                            try
                            {
                                ResourceLocation hoveringKick = AbstractClientPlayer.locationStevePng;
                                hoveringKick = AbstractClientPlayer.getLocationSkin(var20);
                                AbstractClientPlayer.getDownloadImageSkin(hoveringKick, var20);
                                ClientProxy.cacheHeadPlayer.put(var20, hoveringKick);
                            }
                            catch (Exception var12)
                            {
                                System.out.println(var12.getMessage());
                            }
                        }
                        else
                        {
                            Minecraft.getMinecraft().renderEngine.bindTexture((ResourceLocation)ClientProxy.cacheHeadPlayer.get(var20));
                            this.mc.getTextureManager().bindTexture((ResourceLocation)ClientProxy.cacheHeadPlayer.get(var20));
                            GUIUtils.drawScaledCustomSizeModalRect(var21 + 8, offsetY.intValue() + 6, 8.0F, 16.0F, 8, -8, -6, -6, 64.0F, 64.0F);
                        }

                        ModernGui.drawScaledStringCustomFont(var20, (float)(var21 + 10), offsetY.floatValue(), 16777215, 0.5F, "left", false, "georamaSemiBold", 20);
                        ClientEventHandler.STYLE.bindTexture("faction_plots_3");
                        boolean var23 = mouseX >= var21 + 65 && mouseX < var21 + 65 + 6 && (float)mouseY >= offsetY.floatValue() && (float)mouseY < offsetY.floatValue() + 6.0F;
                        ModernGui.drawScaledCustomSizeModalRect((float)(var21 + 65), offsetY.floatValue(), (float)((var23 ? 262 : 254) * GUI_SCALE), (float)(195 * GUI_SCALE), 6 * GUI_SCALE, 6 * GUI_SCALE, 6, 6, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);

                        if (var23)
                        {
                            this.hoveredAction = "kickCoowner#" + var20;
                        }
                    }

                    GUIUtils.endGLScissor();
                    this.scrollBarCoowners.draw(mouseX, mouseY);
                    var18 = (float)mouseX >= (float)this.guiLeft + 86.5F && (float)mouseX < (float)this.guiLeft + 86.5F + 11.0F && mouseY >= this.guiTop + 163 && mouseY < this.guiTop + 163 + 10;

                    if (var18)
                    {
                        ClientEventHandler.STYLE.bindTexture("faction_plots_3");
                        ModernGui.drawScaledCustomSizeModalRect((float)this.guiLeft + 86.5F, (float)(this.guiTop + 163), (float)(242 * GUI_SCALE), (float)(196 * GUI_SCALE), 11 * GUI_SCALE, 10 * GUI_SCALE, 11, 10, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                        this.hoveredAction = "addCoowner#" + this.inputCoOwner.getText();
                    }

                    this.inputCoOwner.drawTextBox();
                }
                else
                {
                    ClientEventHandler.STYLE.bindTexture("faction_plots_3");
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 12), (float)(this.guiTop + 117), (float)(202 * GUI_SCALE), (float)(394 * GUI_SCALE), 93 * GUI_SCALE, 64 * GUI_SCALE, 93, 64, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);

                    if (mouseX >= this.guiLeft + 12 && mouseX < this.guiLeft + 12 + 93 && mouseY >= this.guiTop + 117 && mouseY < this.guiTop + 117 + 64)
                    {
                        tooltipToDraw.add(I18n.getString("faction.plots.tooltip.coowner_disabled"));
                    }
                }

                if (editedPlot.get("buyType").equals("rent"))
                {
                    ClientEventHandler.STYLE.bindTexture("faction_plots_3");
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 112), (float)(this.guiTop + 117), (float)(100 * GUI_SCALE), (float)(265 * GUI_SCALE), 93 * GUI_SCALE, 64 * GUI_SCALE, 93, 64, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                    ModernGui.drawScaledStringCustomFont(this.rentDaysToAdd + " " + I18n.getString("faction.plots.label.days"), (float)(this.guiLeft + 159), (float)(this.guiTop + 133), 16777215, 0.5F, "center", false, "georamaSemiBold", 22);
                    ModernGui.drawScaledStringCustomFont(I18n.getString("faction.plots.label.rent_duration"), (float)(this.guiLeft + 120), (float)(this.guiTop + 150), 7239406, 0.5F, "left", false, "georamaSemiBold", 24);
                    ModernGui.drawScaledStringCustomFont(String.format("%.0f", new Object[] {(Double)editedPlot.get("rent_left_days")}) + " " + I18n.getString("faction.plots.label.days"), (float)(this.guiLeft + 120 + 3) + semiBold24.getStringWidth(I18n.getString("faction.plots.label.rent_duration")) / 2.0F, (float)(this.guiTop + 150), 14606061, 0.5F, "left", false, "georamaSemiBold", 24);
                    ModernGui.drawScaledStringCustomFont(I18n.getString("faction.plots.label.rent_price"), (float)(this.guiLeft + 120), (float)(this.guiTop + 159), 7239406, 0.5F, "left", false, "georamaSemiBold", 24);
                    ModernGui.drawScaledStringCustomFont(String.format("%.0f", new Object[] {(Double)editedPlot.get("price")}) + "$", (float)(this.guiLeft + 120 + 3) + semiBold24.getStringWidth(I18n.getString("faction.plots.label.rent_price")) / 2.0F, (float)(this.guiTop + 159), 14606061, 0.5F, "left", false, "georamaSemiBold", 24);

                    if (this.rentDaysToAdd > 0)
                    {
                        ModernGui.drawScaledStringCustomFont(I18n.getString("faction.plots.label.rent_extend_price"), (float)(this.guiLeft + 120), (float)(this.guiTop + 168), 16000586, 0.5F, "left", false, "georamaSemiBold", 24);
                        ModernGui.drawScaledStringCustomFont(String.format("%.0f", new Object[] {Double.valueOf(((Double)editedPlot.get("price")).doubleValue() * (double)this.rentDaysToAdd)}) + "$", (float)(this.guiLeft + 120 + 3) + semiBold24.getStringWidth(I18n.getString("faction.plots.label.rent_extend_price")) / 2.0F, (float)(this.guiTop + 168), 14606061, 0.5F, "left", false, "georamaSemiBold", 24);
                    }

                    hoveringBtnprevisualize = mouseX >= this.guiLeft + 120 && mouseX < this.guiLeft + 120 + 16 && mouseY >= this.guiTop + 124 && mouseY < this.guiTop + 124 + 16;

                    if (hoveringBtnprevisualize)
                    {
                        ClientEventHandler.STYLE.bindTexture("faction_plots_3");
                        ModernGui.drawScaledCustomSizeModalRect((float)this.guiLeft + 120.5F, (float)(this.guiTop + 128), (float)(222 * GUI_SCALE), (float)(196 * GUI_SCALE), 17 * GUI_SCALE, 16 * GUI_SCALE, 17, 16, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                        this.hoveredAction = "rentMinus";
                    }

                    var14 = mouseX >= this.guiLeft + 181 && mouseX < this.guiLeft + 181 + 16 && mouseY >= this.guiTop + 124 && mouseY < this.guiTop + 124 + 16;

                    if (var14)
                    {
                        ClientEventHandler.STYLE.bindTexture("faction_plots_3");
                        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 181), (float)(this.guiTop + 128), (float)(202 * GUI_SCALE), (float)(196 * GUI_SCALE), 17 * GUI_SCALE, 16 * GUI_SCALE, 17, 16, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                        this.hoveredAction = "rentPlus";
                    }
                }
            }
        }
        else
        {
            ClientEventHandler.STYLE.bindTexture("faction_plots_3");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 12), (float)(this.guiTop + 117), (float)(0 * GUI_SCALE), (float)(190 * GUI_SCALE), 193 * GUI_SCALE, 66 * GUI_SCALE, 193, 66, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
            ModernGui.drawScaledStringCustomFont(I18n.getString("faction.plots.form.type"), (float)(this.guiLeft + 12), (float)(this.guiTop + 110), 16777215, 0.5F, "left", false, "georamaSemiBold", 22);
            int var13 = 0;
            Iterator isHoveringAddImageBtn;
            String hoveringBtnValidate;

            for (isHoveringAddImageBtn = types.iterator(); isHoveringAddImageBtn.hasNext(); ++var13)
            {
                hoveringBtnValidate = (String)isHoveringAddImageBtn.next();
                ClientEventHandler.STYLE.bindTexture("faction_plots_3");
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 20 + var13 / 2 * 50), (float)(this.guiTop + 123 + var13 % 2 * 14), (float)((this.selectedType.equals(hoveringBtnValidate) ? 202 : 212) * GUI_SCALE), (float)(187 * GUI_SCALE), 6 * GUI_SCALE, 6 * GUI_SCALE, 6, 6, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.plots.type." + hoveringBtnValidate), (float)(this.guiLeft + 28 + var13 / 2 * 50), (float)(this.guiTop + 123 + 1 + var13 % 2 * 14), 16777215, 0.5F, "left", false, "georamaSemiBold", 20);
                hoveringBtnCancel = mouseX >= this.guiLeft + 20 + var13 / 2 * 50 && mouseX < this.guiLeft + 20 + var13 / 2 * 50 + 6 && mouseY >= this.guiTop + 123 + var13 % 2 * 14 && mouseY < this.guiTop + 123 + var13 % 2 * 14 + 6;

                if (hoveringBtnCancel)
                {
                    this.hoveredType = hoveringBtnValidate;
                }
            }

            ModernGui.drawScaledStringCustomFont(I18n.getString("faction.plots.form.usage"), (float)(this.guiLeft + 112), (float)(this.guiTop + 110), 16777215, 0.5F, "left", false, "georamaSemiBold", 22);
            var13 = 0;

            for (isHoveringAddImageBtn = usages.iterator(); isHoveringAddImageBtn.hasNext(); ++var13)
            {
                hoveringBtnValidate = (String)isHoveringAddImageBtn.next();
                ClientEventHandler.STYLE.bindTexture("faction_plots_3");
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 120), (float)(this.guiTop + 123 + var13 % 2 * 14), (float)((this.selectedUsage.equals(hoveringBtnValidate) ? 202 : 212) * GUI_SCALE), (float)(187 * GUI_SCALE), 6 * GUI_SCALE, 6 * GUI_SCALE, 6, 6, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.plots.usage." + hoveringBtnValidate), (float)(this.guiLeft + 120 + 8), (float)(this.guiTop + 123 + 1 + var13 % 2 * 14), 16777215, 0.5F, "left", false, "georamaSemiBold", 20);
                hoveringBtnCancel = mouseX >= this.guiLeft + 120 && mouseX < this.guiLeft + 120 + 6 && mouseY >= this.guiTop + 123 + var13 % 2 * 14 && mouseY < this.guiTop + 123 + var13 % 2 * 14 + 6;

                if (hoveringBtnCancel)
                {
                    this.hoveredUsage = hoveringBtnValidate;
                }
            }

            ModernGui.drawScaledStringCustomFont(I18n.getString("faction.plots.form.coowner"), (float)(this.guiLeft + 12), (float)(this.guiTop + 153), 16777215, 0.5F, "left", false, "georamaSemiBold", 22);
            ModernGui.drawScaledStringCustomFont(I18n.getString("faction.plots.label.allow_coowner"), (float)(this.guiLeft + 20), (float)(this.guiTop + 169), 16777215, 0.5F, "left", false, "georamaSemiBold", 20);
            ClientEventHandler.STYLE.bindTexture("faction_plots_3");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 81), (float)(this.guiTop + 168), (float)((this.allowCoOwner ? 288 : 271) * GUI_SCALE), (float)(195 * GUI_SCALE), 15 * GUI_SCALE, 7 * GUI_SCALE, 15, 7, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
            var14 = mouseX >= this.guiLeft + 81 && mouseX < this.guiLeft + 81 + 15 && mouseY >= this.guiTop + 168 && mouseY < this.guiTop + 168 + 7;

            if (var14)
            {
                this.hoveredAction = "toggleCoOwner";
            }

            ModernGui.drawScaledStringCustomFont(I18n.getString("faction.plots.form.mode"), (float)(this.guiLeft + 112), (float)(this.guiTop + 153), 16777215, 0.5F, "left", false, "georamaSemiBold", 22);
            var13 = 0;

            for (var17 = modes.iterator(); var17.hasNext(); ++var13)
            {
                var20 = (String)var17.next();
                ClientEventHandler.STYLE.bindTexture("faction_plots_3");
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 120 + var13 * 50), (float)(this.guiTop + 168), (float)((this.selectedMode.equals(var20) ? 202 : 212) * GUI_SCALE), (float)(187 * GUI_SCALE), 6 * GUI_SCALE, 6 * GUI_SCALE, 6, 6, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.plots.mode." + var20), (float)(this.guiLeft + 120 + 8 + var13 * 50), (float)(this.guiTop + 168 + 1), 16777215, 0.5F, "left", false, "georamaSemiBold", 20);
                boolean action = mouseX >= this.guiLeft + 120 + var13 * 50 && mouseX < this.guiLeft + 120 + var13 * 50 + 6 && mouseY >= this.guiTop + 168 && mouseY < this.guiTop + 168 + 6;

                if (action)
                {
                    this.hoveredMode = var20;
                }
            }
        }

        ModernGui.drawScaledStringCustomFont(I18n.getString("faction.plots.size") + ":", (float)(this.guiLeft + 245), (float)(this.guiTop + 45), 7961015, 0.5F, "left", false, "georamaSemiBold", 25);
        ModernGui.drawScaledStringCustomFont(editedPlot.containsKey("size") ? (String)editedPlot.get("size") + " blocs" : "-", (float)(this.guiLeft + 245 + 5) + semiBold24.getStringWidth(I18n.getString("faction.plots.size")) / 2.0F, (float)(this.guiTop + 45), 14606061, 0.5F, "left", false, "georamaSemiBold", 25);
        ModernGui.drawScaledStringCustomFont(I18n.getString("faction.plots.area") + ":", (float)(this.guiLeft + 245), (float)(this.guiTop + 55), 7961015, 0.5F, "left", false, "georamaSemiBold", 25);
        ModernGui.drawScaledStringCustomFont(editedPlot.containsKey("area") ? String.format("%.0f", new Object[] {(Double)editedPlot.get("area")}) + " blocs": "-", (float)(this.guiLeft + 245 + 5) + semiBold24.getStringWidth(I18n.getString("faction.plots.area")) / 2.0F, (float)(this.guiTop + 55), 14606061, 0.5F, "left", false, "georamaSemiBold", 25);
        ModernGui.drawScaledStringCustomFont(I18n.getString("faction.plots.price_blocs") + ":", (float)(this.guiLeft + 245), (float)(this.guiTop + 65), 7961015, 0.5F, "left", false, "georamaSemiBold", 25);
        ModernGui.drawScaledStringCustomFont(editedPlot.containsKey("price") && editedPlot.containsKey("area") ? String.format("%.5f", new Object[] {Double.valueOf(((Double)editedPlot.get("price")).doubleValue() / ((Double)editedPlot.get("area")).doubleValue())}) + "$/bloc": "-", (float)(this.guiLeft + 245 + 5) + semiBold24.getStringWidth(I18n.getString("faction.plots.price_blocs")) / 2.0F, (float)(this.guiTop + 65), 14606061, 0.5F, "left", false, "georamaSemiBold", 25);
        ModernGui.drawScaledStringCustomFont(I18n.getString("faction.plots.position") + ":", (float)(this.guiLeft + 245), (float)(this.guiTop + 75), 7961015, 0.5F, "left", false, "georamaSemiBold", 25);
        ModernGui.drawScaledStringCustomFont(editedPlot.containsKey("position") ? ((String)editedPlot.get("position")).replaceAll("#", ", ").replaceAll("world, ", "") : "-", (float)(this.guiLeft + 245 + 5) + semiBold24.getStringWidth(I18n.getString("faction.plots.position")) / 2.0F, (float)(this.guiTop + 75), 14606061, 0.5F, "left", false, "georamaSemiBold", 25);
        hoveringBtnprevisualize = mouseX >= this.guiLeft + 245 && mouseX < this.guiLeft + 245 + 81 && mouseY >= this.guiTop + 90 && mouseY < this.guiTop + 90 + 17;
        ClientEventHandler.STYLE.bindTexture("faction_plots_3");
        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 245), (float)(this.guiTop + 90), (float)((editedPlot.isEmpty() ? 248 : (hoveringBtnprevisualize ? 202 : 291)) * GUI_SCALE), (float)((editedPlot.isEmpty() ? 276 : (hoveringBtnprevisualize ? 368 : 344)) * GUI_SCALE), 81 * GUI_SCALE, 17 * GUI_SCALE, 81, 17, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
        ModernGui.drawScaledStringCustomFont(I18n.getString(editedPlot.containsKey("playerHasPrevisualization") && ((Boolean)editedPlot.get("playerHasPrevisualization")).booleanValue() ? "faction.plots.stop_previsualize" : "faction.plots.previsualize"), (float)(this.guiLeft + 245 + 40), (float)(this.guiTop + 95), editedPlot.isEmpty() ? 0 : (hoveringBtnprevisualize ? 7239406 : 16777215), 0.5F, "center", false, "georamaSemiBold", 25);

        if (hoveringBtnprevisualize && !editedPlot.isEmpty())
        {
            this.hoveredAction = "previsualize";
        }

        if (this.editionMode.equals("creator"))
        {
            var14 = mouseX >= this.guiLeft + 334 && mouseX < this.guiLeft + 334 + 81 && mouseY >= this.guiTop + 90 && mouseY < this.guiTop + 90 + 17;
            ClientEventHandler.STYLE.bindTexture("faction_plots_3");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 334), (float)(this.guiTop + 90), (float)((var14 && !this.isFormValid() ? 248 : 202) * GUI_SCALE), (float)((var14 ? (this.isFormValid() ? 368 : 276) : 344) * GUI_SCALE), 81 * GUI_SCALE, 17 * GUI_SCALE, 81, 17, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
            ModernGui.drawScaledStringCustomFont(I18n.getString("faction.plots.define"), (float)(this.guiLeft + 334 + 40), (float)(this.guiTop + 95), var14 ? (this.isFormValid() ? 7239406 : 0) : 16777215, 0.5F, "center", false, "georamaSemiBold", 25);

            if (var14)
            {
                if (this.isFormValid())
                {
                    this.hoveredAction = "define";
                }
                else
                {
                    tooltipToDraw.add(I18n.getString("faction.plots.form.error"));
                }
            }
        }

        if (editedPlot.get("images") != null && !((ArrayList)editedPlot.get("images")).isEmpty())
        {
            for (var15 = 0; var15 < Math.min(3, ((ArrayList)editedPlot.get("images")).size()); ++var15)
            {
                Float var19 = Float.valueOf((float)(this.guiLeft + 237) + (float)var15 * 47.5F);
                ModernGui.drawScaledModalRectWithCustomSizedRemoteTexture(var19.intValue(), this.guiTop + 123, 54 * GUI_SCALE, 0, 116 * GUI_SCALE, 116 * GUI_SCALE, 43, 39, (float)(225 * GUI_SCALE), (float)(116 * GUI_SCALE), true, (String)((ArrayList)editedPlot.get("images")).get(var15));
                hoveringBtnCancel = (float)mouseX >= var19.floatValue() && (float)mouseX < var19.floatValue() + 43.0F && mouseY >= this.guiTop + 123 && mouseY < this.guiTop + 123 + 39;

                if (hoveringBtnCancel)
                {
                    Gui.drawRect(var19.intValue(), this.guiTop + 123, var19.intValue() + 43, this.guiTop + 123 + 39, -2131483062);
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    ClientEventHandler.STYLE.bindTexture("faction_plots_3");
                    ModernGui.drawScaledCustomSizeModalRect((float)(var19.intValue() + 15), (float)(this.guiTop + 123 + 12), (float)(318 * GUI_SCALE), (float)(190 * GUI_SCALE), 14 * GUI_SCALE, 15 * GUI_SCALE, 14, 15, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                    this.hoveredAction = "removeImage#" + var15;
                }
            }
        }

        var14 = mouseX >= this.guiLeft + 380 && (float)mouseX < (float)(this.guiLeft + 380) + 43.0F && mouseY >= this.guiTop + 123 && mouseY < this.guiTop + 123 + 39;
        ClientEventHandler.STYLE.bindTexture("faction_plots_3");

        if (var14)
        {
            if (editedPlot.containsKey("images") && ((ArrayList)editedPlot.get("images")).size() < 3)
            {
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 380), (float)(this.guiTop + 123), (float)(202 * GUI_SCALE), (float)(255 * GUI_SCALE), 47 * GUI_SCALE, 39 * GUI_SCALE, 47, 39, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                this.hoveredAction = "addImage";
            }
            else
            {
                tooltipToDraw.add(I18n.getString("faction.plots.max_images"));
            }
        }

        var18 = mouseX >= this.guiLeft + 225 && mouseX < this.guiLeft + 225 + 102 && mouseY >= this.guiTop + 182 && mouseY < this.guiTop + 182 + 17;

        if (var18)
        {
            if (this.isFormValid())
            {
                ClientEventHandler.STYLE.bindTexture("faction_plots_3");
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 225), (float)(this.guiTop + 182), (float)(202 * GUI_SCALE), (float)(215 * GUI_SCALE), 102 * GUI_SCALE, 17 * GUI_SCALE, 102, 17, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                this.hoveredAction = "validate";
            }
            else
            {
                ClientEventHandler.STYLE.bindTexture("faction_plots_3");
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 225), (float)(this.guiTop + 182), (float)(248 * GUI_SCALE), (float)(255 * GUI_SCALE), 102 * GUI_SCALE, 17 * GUI_SCALE, 102, 17, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                tooltipToDraw.add(I18n.getString("faction.plots.form.error"));
            }
        }

        ModernGui.drawScaledStringCustomFont(I18n.getString(this.rentDaysToAdd > 0 ? "faction.plots.validate_and_pay" : "faction.plots.validate"), (float)(this.guiLeft + 225 + 51), (float)(this.guiTop + 186), var18 ? (this.isFormValid() ? 7239406 : 0) : 16777215, 0.5F, "center", false, "minecraftDungeons", 21);
        hoveringBtnCancel = mouseX >= this.guiLeft + 333 && mouseX < this.guiLeft + 333 + 102 && mouseY >= this.guiTop + 182 && mouseY < this.guiTop + 182 + 17;
        String var22 = editedPlot.isEmpty() ? "cancel" : (!editedPlot.get("owner").equals("") ? "stop" : "delete");

        if (hoveringBtnCancel)
        {
            ClientEventHandler.STYLE.bindTexture("faction_plots_3");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 333), (float)(this.guiTop + 182), (float)(202 * GUI_SCALE), (float)(235 * GUI_SCALE), 102 * GUI_SCALE, 17 * GUI_SCALE, 102, 17, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
            this.hoveredAction = var22;
        }

        ModernGui.drawScaledStringCustomFont(I18n.getString("faction.plots.btn." + var22), (float)(this.guiLeft + 333 + 51), (float)(this.guiTop + 186), hoveringBtnCancel ? 16777215 : 16000586, 0.5F, "center", false, "minecraftDungeons", 21);

        if (tooltipToDraw != null && !tooltipToDraw.isEmpty())
        {
            this.drawHoveringText(tooltipToDraw, mouseX, mouseY, this.fontRenderer);
        }

        super.drawScreen(mouseX, mouseY, partialTick);
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (mouseButton == 0)
        {
            if (this.editionMode.equals("creator"))
            {
                this.inputName.mouseClicked(mouseX, mouseY, mouseButton);
                this.inputPrice.mouseClicked(mouseX, mouseY, mouseButton);
            }

            if (editedPlot.get("owner") != null && editedPlot.get("owner").equals(Minecraft.getMinecraft().thePlayer.username))
            {
                this.inputCoOwner.mouseClicked(mouseX, mouseY, mouseButton);
            }

            this.inputDescription.mouseClicked(mouseX, mouseY, mouseButton);

            if (!this.hoveredAction.isEmpty())
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);

                if (this.hoveredAction.equals("toggleEditionMode"))
                {
                    this.editionMode = this.editionMode.equals("owner") ? "creator" : "owner";
                }
                else if (this.hoveredAction.equals("close"))
                {
                    this.mc.displayGuiScreen((GuiScreen)null);
                }
                else if (this.hoveredAction.equals("return"))
                {
                    this.mc.displayGuiScreen(new FactionPlotsGUI());
                }
                else if (this.hoveredAction.equals("toggleCoOwner"))
                {
                    this.allowCoOwner = !this.allowCoOwner;
                }
                else if (this.hoveredAction.startsWith("removeImage"))
                {
                    int coOwner = Integer.parseInt(this.hoveredAction.split("#")[1]);
                    ((ArrayList)editedPlot.get("images")).remove(coOwner);
                }
                else
                {
                    HashMap var5;

                    if (this.hoveredAction.equals("validate"))
                    {
                        if (this.isFormValid())
                        {
                            var5 = new HashMap();
                            var5.put("name", this.inputName.getText());
                            var5.put("description", this.inputDescription.getText());
                            var5.put("price", Double.valueOf(Double.parseDouble(this.inputPrice.getText())));
                            var5.put("type", this.selectedType);
                            var5.put("usage", this.selectedUsage);
                            var5.put("buyType", this.selectedMode);
                            var5.put("canAddCoOwner", Boolean.valueOf(this.allowCoOwner));
                            var5.put("whitelisted_players", editedPlot.containsKey("whitelisted_players") ? editedPlot.get("whitelisted_players") : new ArrayList());
                            var5.put("images", editedPlot.containsKey("images") ? editedPlot.get("images") : new ArrayList());
                            var5.put("rentDaysToAdd", Integer.valueOf(this.rentDaysToAdd));

                            if (editedPlot.isEmpty())
                            {
                                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionPlotsActionPacket(-1, "create", var5)));
                                this.mc.displayGuiScreen((GuiScreen)null);
                            }
                            else
                            {
                                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionPlotsActionPacket(((Double)editedPlot.get("id")).intValue(), "edit", var5)));
                                this.mc.displayGuiScreen(new FactionPlotsGUI());
                            }
                        }
                    }
                    else if (this.hoveredAction.equals("cancel"))
                    {
                        this.mc.displayGuiScreen(new FactionPlotsGUI());
                    }
                    else if (!this.hoveredAction.equals("stop") && !this.hoveredAction.equals("delete"))
                    {
                        if (this.hoveredAction.equals("define"))
                        {
                            if (this.isFormValid())
                            {
                                var5 = new HashMap();
                                var5.put("id", Integer.valueOf(!editedPlot.isEmpty() ? ((Double)editedPlot.get("id")).intValue() : -1));
                                var5.put("name", this.inputName.getText());
                                var5.put("description", this.inputDescription.getText());
                                var5.put("price", Double.valueOf(Double.parseDouble(this.inputPrice.getText())));
                                var5.put("type", this.selectedType);
                                var5.put("usage", this.selectedUsage);
                                var5.put("buyType", this.selectedMode);
                                var5.put("canAddCoOwner", Boolean.valueOf(this.allowCoOwner));
                                var5.put("whitelisted_players", editedPlot.containsKey("whitelisted_players") ? editedPlot.get("whitelisted_players") : new ArrayList());
                                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionPlotsActionPacket(editedPlot.containsKey("id") ? ((Double)editedPlot.get("id")).intValue() : -1, "define", var5)));
                                this.mc.displayGuiScreen((GuiScreen)null);
                            }
                        }
                        else if (this.hoveredAction.equals("previsualize"))
                        {
                            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionPlotsActionPacket(((Double)editedPlot.get("id")).intValue(), "previsualize", new HashMap())));

                            if (!editedPlot.containsKey("playerHasPrevisualization") || !((Boolean)editedPlot.get("playerHasPrevisualization")).booleanValue())
                            {
                                this.mc.displayGuiScreen((GuiScreen)null);
                            }
                        }
                        else
                        {
                            String var6;

                            if (this.hoveredAction.contains("addCoowner"))
                            {
                                if (editedPlot.get("owner") != null && editedPlot.get("owner").equals(Minecraft.getMinecraft().thePlayer.username) && !this.inputCoOwner.getText().isEmpty())
                                {
                                    var6 = this.hoveredAction.split("#")[1];

                                    if (!((List)editedPlot.get("whitelisted_players")).contains(var6))
                                    {
                                        ((List)editedPlot.get("whitelisted_players")).add(var6);
                                    }

                                    this.inputCoOwner.setText("");
                                }
                            }
                            else if (this.hoveredAction.contains("kickCoowner"))
                            {
                                var6 = this.hoveredAction.split("#")[1];
                                ((List)editedPlot.get("whitelisted_players")).remove(var6);
                            }
                            else if (this.hoveredAction.equals("rentMinus"))
                            {
                                this.rentDaysToAdd = Math.max(0, this.rentDaysToAdd - 1);
                            }
                            else if (this.hoveredAction.equals("rentPlus"))
                            {
                                ++this.rentDaysToAdd;
                            }
                            else if (this.hoveredAction.equals("addImage"))
                            {
                                ClientData.lastCaptureScreenshot.put("plot#" + ((Double)editedPlot.get("id")).intValue(), Long.valueOf(System.currentTimeMillis()));
                                Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
                                Minecraft.getMinecraft().thePlayer.sendChatToPlayer(ChatMessageComponent.createFromText(I18n.getString("faction.plots.take_image")));
                            }
                        }
                    }
                    else
                    {
                        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionPlotsActionPacket(((Double)editedPlot.get("id")).intValue(), this.hoveredAction, new HashMap())));
                        this.mc.displayGuiScreen(new FactionPlotsGUI());
                    }
                }
            }
            else if (!this.hoveredType.isEmpty())
            {
                this.selectedType = this.hoveredType;
            }
            else if (!this.hoveredUsage.isEmpty())
            {
                this.selectedUsage = this.hoveredUsage;
            }
            else if (!this.hoveredMode.isEmpty())
            {
                this.selectedMode = this.hoveredMode;
            }
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.inputName.updateCursorCounter();
        this.inputPrice.updateCursorCounter();
        this.inputDescription.updateCursorCounter();
        this.inputCoOwner.updateCursorCounter();
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2)
    {
        this.inputName.textboxKeyTyped(par1, par2);
        this.inputPrice.textboxKeyTyped(par1, par2);
        this.inputDescription.textboxKeyTyped(par1, par2);
        this.inputCoOwner.textboxKeyTyped(par1, par2);
        super.keyTyped(par1, par2);
    }

    public boolean isFormValid()
    {
        return this.inputName.getText().isEmpty() ? false : (!this.inputPrice.getText().isEmpty() && isValidNonNegativeInteger(this.inputPrice.getText()) ? (this.selectedType.isEmpty() ? false : (this.selectedUsage.isEmpty() ? false : !this.selectedMode.isEmpty())) : false);
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
            j1 = par3 - 12;
            int k1 = 8;

            if (par1List.size() > 1)
            {
                k1 += 2 + (par1List.size() - 1) * 10;
            }

            if (var15 + k > this.width)
            {
                var15 -= 28 + k;
            }

            if (j1 + k1 + 6 > this.height)
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
}
