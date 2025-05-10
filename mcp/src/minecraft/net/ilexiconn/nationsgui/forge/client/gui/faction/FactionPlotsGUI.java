package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import net.halalaboos.cfont.CFontRenderer;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarGeneric;
import net.ilexiconn.nationsgui.forge.client.gui.main.component.CustomInputFieldGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionPlotsActionPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionPlotsDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class FactionPlotsGUI extends TabbedFactionGUI
{
    public static CFontRenderer dg22 = ModernGui.getCustomFont("minecraftDungeons", Integer.valueOf(22));
    public static String defaultImageList = "https://static.nationsglory.fr/N355G4y4_N.png";
    public static String defaultImageDetail = "https://static.nationsglory.fr/N4yG34N5NG.png";
    public static boolean loaded = false;
    public static boolean adminMode = false;
    public static List<HashMap<String, Object>> plots = new ArrayList();
    public static int countSell = 0;
    public static int countRent = 0;
    public static int countAvailable = 0;
    public static boolean canCreateNewPlot = false;
    public static HashMap<String, Object> selectedPlot = new HashMap();
    public static HashMap<String, Object> hoveredPlot = new HashMap();
    private GuiScrollBarGeneric scrollBarPlots;
    public static List<String> searchFilters = Arrays.asList(new String[] {"sell", "rent", "available"});
    private GuiTextField inputSearchName;
    private ArrayList<String> selectedSearchFilters = new ArrayList();
    private GuiTextField inputName;
    private GuiTextField inputDescription;
    private GuiTextField inputRooms;
    private GuiTextField inputPrice;

    public FactionPlotsGUI()
    {
        plots = new ArrayList();
        loaded = false;
        this.selectedSearchFilters = new ArrayList(Arrays.asList(new String[] {"sell", "rent"}));
        selectedPlot.clear();
        countSell = 0;
        countRent = 0;
        countAvailable = 0;
        canCreateNewPlot = false;
        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionPlotsDataPacket((String)FactionGUI.factionInfos.get("id"))));
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        this.scrollBarPlots = new GuiScrollBarGeneric((float)(this.guiLeft + 450), (float)(this.guiTop + 47), 172, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_parrot.png"), 5, 28);
        this.inputSearchName = new CustomInputFieldGUI(this.guiLeft + 177, this.guiTop + 21, 90, 12, "georamaMedium", 28);
        this.inputSearchName.setMaxStringLength(24);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTick)
    {
        this.drawDefaultBackground();
        tooltipToDraw = new ArrayList();
        this.hoveredAction = "";
        hoveredPlot.clear();
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        ClientEventHandler.STYLE.bindTexture("faction_global_2");
        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 30), (float)(this.guiTop + 0), (float)(0 * GUI_SCALE), (float)(0 * GUI_SCALE), (this.xSize - 30) * GUI_SCALE, this.ySize * GUI_SCALE, this.xSize - 30, this.ySize, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
        ModernGui.drawScaledStringCustomFont((String)FactionGUI.factionInfos.get("name"), (float)(this.guiLeft + 43), (float)(this.guiTop + 6), 10395075, 0.5F, "left", false, "georamaMedium", 32);

        if (loaded)
        {
            String owner;
            boolean hoveredBtnEdit;
            int var22;

            if (selectedPlot.isEmpty())
            {
                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.plots.title"), (float)(this.guiLeft + 43), (float)(this.guiTop + 16), 16777215, 0.75F, "left", false, "georamaSemiBold", 32);

                if (((Boolean)FactionGUI.factionInfos.get("isInCountry")).booleanValue() && FactionGUI.hasPermissions("locations"))
                {
                    ClientEventHandler.STYLE.bindTexture("faction_plots");
                    boolean offsetYDetails = mouseX >= this.guiLeft + 157 && mouseX <= this.guiLeft + 157 + 12 && mouseY >= this.guiTop + 22 && mouseY <= this.guiTop + 22 + 12;
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 157), (float)(this.guiTop + 22), (float)((offsetYDetails && canCreateNewPlot ? 19 : 4) * GUI_SCALE), (float)(101 * GUI_SCALE), 12 * GUI_SCALE, 12 * GUI_SCALE, 12, 12, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), offsetYDetails);

                    if (offsetYDetails)
                    {
                        if (canCreateNewPlot)
                        {
                            tooltipToDraw.add(I18n.getString("faction.plots.create"));
                            this.hoveredAction = "create";
                        }
                        else
                        {
                            tooltipToDraw.addAll(Arrays.asList(I18n.getString("faction.plots.create.max_plots").split("##")));
                        }
                    }
                }

                ClientEventHandler.STYLE.bindTexture("faction_plots");
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 173), (float)(this.guiTop + 22), (float)(280 * GUI_SCALE), (float)(412 * GUI_SCALE), 105 * GUI_SCALE, 12 * GUI_SCALE, 105, 12, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                this.inputSearchName.drawTextBox();
                var22 = 0;

                for (Iterator date = searchFilters.iterator(); date.hasNext(); ++var22)
                {
                    String simpleDateFormat = (String)date.next();
                    ClientEventHandler.STYLE.bindTexture("faction_plots");
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 282 + var22 * 54), (float)(this.guiTop + 22), (float)((this.selectedSearchFilters.contains(simpleDateFormat) ? 392 : 449) * GUI_SCALE), (float)(412 * GUI_SCALE), 51 * GUI_SCALE, 12 * GUI_SCALE, 51, 12, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                    owner = I18n.getString("faction.plots.filter." + simpleDateFormat);

                    if (simpleDateFormat.equals("sell"))
                    {
                        owner = owner + " (" + countSell + ")";
                    }
                    else if (simpleDateFormat.equals("rent"))
                    {
                        owner = owner + " (" + countRent + ")";
                    }
                    else if (simpleDateFormat.equals("available"))
                    {
                        owner = owner + " (" + countAvailable + ")";
                    }

                    ModernGui.drawScaledStringCustomFont(owner, (float)(this.guiLeft + 282 + var22 * 54) + 25.5F, (float)(this.guiTop + 25), this.selectedSearchFilters.contains(simpleDateFormat) ? 16777215 : 12237530, 0.5F, "center", false, "georamaBold", 24);

                    if (mouseX >= this.guiLeft + 282 + var22 * 54 && mouseX <= this.guiLeft + 282 + var22 * 54 + 51 && mouseY >= this.guiTop + 22 && mouseY <= this.guiTop + 22 + 12)
                    {
                        this.hoveredAction = "filter#" + simpleDateFormat;
                    }
                }

                GUIUtils.startGLScissor(this.guiLeft + 43, this.guiTop + 42, 428, 175);
                int var23 = 0;
                Iterator var26 = plots.iterator();

                while (var26.hasNext())
                {
                    HashMap var29 = (HashMap)var26.next();
                    int isOwner = this.guiLeft + 43 + var23 % 6 * 67;
                    Float hoveredBtnBuy = Float.valueOf((float)(this.guiTop + 45 + var23 / 6 * 104) + this.getSlidePlots());
                    hoveredBtnEdit = var29.get("owner") != null && var29.get("owner").equals(Minecraft.getMinecraft().thePlayer.username);

                    if ((this.inputSearchName.getText().isEmpty() || ((String)var29.get("name")).toLowerCase().contains(this.inputSearchName.getText().toLowerCase()) || var29.get("owner") != null && ((String)var29.get("owner")).toLowerCase().contains(this.inputSearchName.getText().toLowerCase())) && this.selectedSearchFilters.contains(var29.get("buyType")) && (!this.selectedSearchFilters.contains("available") || ((String)var29.get("owner")).isEmpty() || hoveredBtnEdit))
                    {
                        boolean hovered = mouseX >= isOwner && mouseX <= isOwner + 63 && (float)mouseY >= hoveredBtnBuy.floatValue() && (float)mouseY <= hoveredBtnBuy.floatValue() + 100.0F;

                        if (hovered)
                        {
                            hoveredPlot = (HashMap)var29.clone();
                        }

                        ClientEventHandler.STYLE.bindTexture("faction_plots");
                        short cardBackgroundTextureX = 248;

                        if (hovered)
                        {
                            cardBackgroundTextureX = 449;
                        }
                        else if (hoveredBtnEdit && ((String)var29.get("buyType")).equals("sell"))
                        {
                            cardBackgroundTextureX = 382;
                        }
                        else if (hoveredBtnEdit && ((String)var29.get("buyType")).equals("rent"))
                        {
                            cardBackgroundTextureX = 315;
                        }
                        else if (!((String)var29.get("owner")).isEmpty() && !var29.get("owner").equals(Minecraft.getMinecraft().thePlayer.username))
                        {
                            cardBackgroundTextureX = 181;
                        }

                        ModernGui.drawScaledCustomSizeModalRect((float)isOwner, hoveredBtnBuy.floatValue(), (float)(cardBackgroundTextureX * GUI_SCALE), (float)(0 * GUI_SCALE), 63 * GUI_SCALE, 100 * GUI_SCALE, 63, 100, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                        String img = var29.get("images") != null && !((ArrayList)var29.get("images")).isEmpty() ? (String)((ArrayList)var29.get("images")).get(0) : defaultImageList;
                        ModernGui.drawScaledModalRectWithCustomSizedRemoteTexture(isOwner + 4, hoveredBtnBuy.intValue() + 4, 61 * GUI_SCALE, 0, 116 * GUI_SCALE, 116 * GUI_SCALE, 55, 60, (float)(225 * GUI_SCALE), (float)(116 * GUI_SCALE), true, img);
                        int imageOverlayColor = -1;

                        if (hovered)
                        {
                            imageOverlayColor = -2130706433;
                        }
                        else if (hoveredBtnEdit && ((String)var29.get("buyType")).equals("sell"))
                        {
                            imageOverlayColor = -2139436705;
                        }
                        else if (hoveredBtnEdit && ((String)var29.get("buyType")).equals("rent"))
                        {
                            imageOverlayColor = -2140244242;
                        }
                        else if (!((String)var29.get("owner")).isEmpty() && !var29.get("owner").equals(Minecraft.getMinecraft().thePlayer.username))
                        {
                            imageOverlayColor = -1089400538;
                        }

                        if (imageOverlayColor != -1)
                        {
                            ModernGui.glColorHex(imageOverlayColor, 1.0F);
                            ModernGui.drawRectangle((float)(isOwner + 4), hoveredBtnBuy.floatValue() + 4.0F, this.zLevel, 55.0F, 60.0F);
                            GL11.glColor3f(1.0F, 1.0F, 1.0F);
                        }

                        if (((String)var29.get("owner")).isEmpty())
                        {
                            ClientEventHandler.STYLE.bindTexture("faction_plots");
                            ModernGui.drawScaledCustomSizeModalRect((float)(isOwner + 26), hoveredBtnBuy.floatValue() - 3.0F, (float)(180 * GUI_SCALE), (float)((var29.get("buyType").equals("sell") ? 106 : 119) * GUI_SCALE), 40 * GUI_SCALE, 11 * GUI_SCALE, 40, 11, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                            ModernGui.drawScaledStringCustomFont(I18n.getString("faction.plots.label.to." + var29.get("buyType")), (float)(isOwner + 26 + 20), hoveredBtnBuy.floatValue() + 0.5F, 2434373, 0.5F, "center", false, "georamaBold", 20);
                            String players = (String)var29.get("name");

                            if (players.length() > 19)
                            {
                                players = players.substring(0, 19) + ".";
                            }

                            ModernGui.drawScaledStringCustomFont(players, (float)(isOwner + 4), hoveredBtnBuy.floatValue() + 70.0F, hovered ? 1908021 : 16316667, 0.5F, "left", false, "georamaSemiBold", 25);
                            ModernGui.drawScaledStringCustomFont(String.format("%.0f", new Object[] {(Double)var29.get("price")}) + "$" + (var29.get("buyType").equals("rent") ? " " + I18n.getString("faction.plots.label.per_day") : ""), (float)(isOwner + 4), hoveredBtnBuy.floatValue() + 82.0F, hovered ? 1908021 : (var29.get("buyType").equals("sell") ? 8046943 : 7239406), 0.5F, "left", false, "georamaRegular", 22);
                            ModernGui.drawScaledStringCustomFont(I18n.getString("faction.plots.distance").replaceAll("XX", String.format("%.0f", new Object[] {(Double)var29.get("distance")})), (float)(isOwner + 4), hoveredBtnBuy.floatValue() + 90.0F, hovered ? 1908021 : 7961015, 0.5F, "left", false, "georamaMedium", 23);
                        }
                        else
                        {
                            ArrayList var33 = new ArrayList(Arrays.asList(new String[] {(String)var29.get("owner")}));
                            int indexHeadPlayers = 0;
                            Iterator ownerName = var33.iterator();
                            String plotName;

                            while (ownerName.hasNext())
                            {
                                plotName = (String)ownerName.next();

                                if (!ClientProxy.cacheHeadPlayer.containsKey(plotName))
                                {
                                    try
                                    {
                                        ResourceLocation e = AbstractClientPlayer.locationStevePng;
                                        e = AbstractClientPlayer.getLocationSkin(plotName);
                                        AbstractClientPlayer.getDownloadImageSkin(e, plotName);
                                        ClientProxy.cacheHeadPlayer.put(plotName, e);
                                    }
                                    catch (Exception var21)
                                    {
                                        System.out.println(var21.getMessage());
                                    }
                                }
                                else
                                {
                                    Minecraft.getMinecraft().renderEngine.bindTexture((ResourceLocation)ClientProxy.cacheHeadPlayer.get(plotName));
                                    this.mc.getTextureManager().bindTexture((ResourceLocation)ClientProxy.cacheHeadPlayer.get(plotName));
                                    GUIUtils.drawScaledCustomSizeModalRect(isOwner + 13 + indexHeadPlayers * 11, hoveredBtnBuy.intValue() + 77, 8.0F, 16.0F, 8, -8, -9, -9, 64.0F, 64.0F);
                                    ++indexHeadPlayers;
                                }
                            }

                            String var34 = (String)var29.get("owner");

                            if (var34.length() > 15)
                            {
                                var34 = var34.substring(0, 15) + ".";
                            }

                            ModernGui.drawScaledStringCustomFont(var34, (float)(isOwner + 16), hoveredBtnBuy.floatValue() + 70.0F, hovered ? 1908021 : (!hoveredBtnEdit ? 16316667 : 1118502), 0.5F, "left", false, "georamaSemiBold", 25);
                            plotName = (String)var29.get("name");
                            ModernGui.drawScaledStringCustomFont(plotName, (float)(isOwner + 4), hoveredBtnBuy.floatValue() + 82.0F, hovered ? 1908021 : (!hoveredBtnEdit ? 12434877 : 1118502), 0.5F, "left", false, "georamaRegular", 22);
                            ModernGui.drawScaledStringCustomFont(I18n.getString("faction.plots.label.owned." + var29.get("buyType")), (float)(isOwner + 4), hoveredBtnBuy.floatValue() + 90.0F, hovered ? 1908021 : (hoveredBtnEdit ? 1118502 : (var29.get("buyType").equals("rent") ? 7239406 : 8046943)), 0.5F, "left", false, "georamaMedium", 23);
                        }

                        ++var23;
                    }
                }

                if (var23 == 0)
                {
                    ModernGui.drawScaledStringCustomFont(I18n.getString("faction.plots.no_plots"), (float)(this.guiLeft + 43 + 201), (float)(this.guiTop + 45 + 86), 16777215, 0.5F, "center", false, "georamaSemiBold", 24);
                }

                GUIUtils.endGLScissor();
                ClientEventHandler.STYLE.bindTexture("faction_plots");
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 450), (float)(this.guiTop + 47), (float)(250 * GUI_SCALE), (float)(340 * GUI_SCALE), 5 * GUI_SCALE, 172 * GUI_SCALE, 5, 172, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                this.scrollBarPlots.draw(mouseX, mouseY);
            }
            else
            {
                ClientEventHandler.STYLE.bindTexture("faction_plots");

                if (mouseX >= this.guiLeft + 403 && mouseX <= this.guiLeft + 403 + 40 && mouseY >= this.guiTop + 9 && mouseY <= this.guiTop + 9 + 6)
                {
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 403), (float)(this.guiTop + 9), (float)(48 * GUI_SCALE), (float)(60 * GUI_SCALE), 6 * GUI_SCALE, 9 * GUI_SCALE, 6, 9, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                    ModernGui.drawScaledStringCustomFont(I18n.getString("faction.settings.label.back"), (float)(this.guiLeft + 412), (float)(this.guiTop + 10), 16777215, 0.5F, "left", false, "georamaSemiBold", 30);
                    this.hoveredAction = "back";
                }
                else
                {
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 403), (float)(this.guiTop + 9), (float)(39 * GUI_SCALE), (float)(60 * GUI_SCALE), 6 * GUI_SCALE, 9 * GUI_SCALE, 6, 9, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                    ModernGui.drawScaledStringCustomFont(I18n.getString("faction.settings.label.back"), (float)(this.guiLeft + 412), (float)(this.guiTop + 10), 10395075, 0.5F, "left", false, "georamaSemiBold", 30);
                }

                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.plots.plot") + " " + selectedPlot.get("name"), (float)(this.guiLeft + 43), (float)(this.guiTop + 16), 16777215, 0.75F, "left", false, "georamaSemiBold", 32);

                if (selectedPlot.get("images") == null || ((ArrayList)selectedPlot.get("images")).isEmpty())
                {
                    ((ArrayList)selectedPlot.get("images")).add(defaultImageDetail);
                }

                if (selectedPlot.get("images") != null && !((ArrayList)selectedPlot.get("images")).isEmpty())
                {
                    var22 = selectedPlot.containsKey("indexImage") && ((ArrayList)selectedPlot.get("images")).size() > ((Integer)selectedPlot.get("indexImage")).intValue() ? ((Integer)selectedPlot.get("indexImage")).intValue() : 0;
                    ModernGui.drawScaledModalRectWithCustomSizedRemoteTexture(this.guiLeft + 43, this.guiTop + 47, 0, 0, 225 * GUI_SCALE, 116 * GUI_SCALE, 225, 116, (float)(225 * GUI_SCALE), (float)(116 * GUI_SCALE), true, (String)((ArrayList)selectedPlot.get("images")).get(var22));

                    if (((ArrayList)selectedPlot.get("images")).size() > 1)
                    {
                        ClientEventHandler.STYLE.bindTexture("faction_plots");
                        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 33), (float)(this.guiTop + 47 + 58 - 10), (float)(6 * GUI_SCALE), (float)(33 * GUI_SCALE), 21 * GUI_SCALE, 21 * GUI_SCALE, 21, 21, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);

                        if (mouseX >= this.guiLeft + 33 && mouseX <= this.guiLeft + 33 + 21 && mouseY >= this.guiTop + 47 + 58 - 10 && mouseY <= this.guiTop + 47 + 58 - 10 + 21)
                        {
                            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 33), (float)(this.guiTop + 47 + 58 - 10), (float)(33 * GUI_SCALE), (float)(33 * GUI_SCALE), 21 * GUI_SCALE, 21 * GUI_SCALE, 21, 21, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                            this.hoveredAction = "carousel_previous";
                        }

                        ClientEventHandler.STYLE.bindTexture("faction_plots");
                        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 43 + 225 - 10), (float)(this.guiTop + 47 + 58 - 10), (float)(6 * GUI_SCALE), (float)(6 * GUI_SCALE), 21 * GUI_SCALE, 21 * GUI_SCALE, 21, 21, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);

                        if (mouseX >= this.guiLeft + 43 + 225 - 10 && mouseX <= this.guiLeft + 43 + 225 - 10 + 21 && mouseY >= this.guiTop + 47 + 58 - 10 && mouseY <= this.guiTop + 47 + 58 - 10 + 21)
                        {
                            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 43 + 225 - 10), (float)(this.guiTop + 47 + 58 - 10), (float)(33 * GUI_SCALE), (float)(6 * GUI_SCALE), 21 * GUI_SCALE, 21 * GUI_SCALE, 21, 21, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                            this.hoveredAction = "carousel_next";
                        }
                    }
                }
                else
                {
                    ClientEventHandler.STYLE.bindTexture("faction_plots_2");
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 43), (float)(this.guiTop + 47), (float)(0 * GUI_SCALE), (float)(0 * GUI_SCALE), 0 * GUI_SCALE, 0 * GUI_SCALE, 225, 116, (float)(0 * GUI_SCALE), (float)(0 * GUI_SCALE), true);
                }

                if (selectedPlot.get("description") != null && !((String)selectedPlot.get("description")).isEmpty())
                {
                    ModernGui.drawSectionStringCustomFont((String)selectedPlot.get("description"), (float)(this.guiLeft + 43), (float)(this.guiTop + 177), 12237530, 0.5F, "left", false, "georamaMedium", 28, 7, 450);
                }

                ClientEventHandler.STYLE.bindTexture("faction_plots");
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 282), (float)(this.guiTop + 47), (float)(161 * GUI_SCALE), (float)(218 * GUI_SCALE), 170 * GUI_SCALE, 116 * GUI_SCALE, 170, 116, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                byte var24 = 57;
                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.plots.type").toUpperCase() + ":", (float)(this.guiLeft + 296), (float)(this.guiTop + var24), 7961015, 0.5F, "left", false, "minecraftDungeons", 22);
                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.plots.type." + selectedPlot.get("type")), (float)(this.guiLeft + 296 + 5) + dg22.getStringWidth(I18n.getString("faction.plots.type").toUpperCase()) / 2.0F, (float)(this.guiTop + var24) + 0.5F, 14606061, 0.5F, "left", false, "georamaSemiBold", 30);
                var22 = var24 + 10;
                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.plots.form.usage").toUpperCase() + ":", (float)(this.guiLeft + 296), (float)(this.guiTop + var22), 7961015, 0.5F, "left", false, "minecraftDungeons", 22);
                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.plots.usage." + selectedPlot.get("usage")), (float)(this.guiLeft + 296 + 5) + dg22.getStringWidth(I18n.getString("faction.plots.form.usage").toUpperCase()) / 2.0F, (float)(this.guiTop + var22) + 0.5F, 14606061, 0.5F, "left", false, "georamaSemiBold", 30);
                var22 += 20;
                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.plots.size").toUpperCase() + ":", (float)(this.guiLeft + 296), (float)(this.guiTop + var22), 7961015, 0.5F, "left", false, "minecraftDungeons", 22);
                ModernGui.drawScaledStringCustomFont((String)selectedPlot.get("size") + " blocs", (float)(this.guiLeft + 296 + 5) + dg22.getStringWidth(I18n.getString("faction.plots.size").toUpperCase()) / 2.0F, (float)(this.guiTop + var22) + 0.5F, 14606061, 0.5F, "left", false, "georamaSemiBold", 30);
                var22 += 10;
                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.plots.area").toUpperCase() + ":", (float)(this.guiLeft + 296), (float)(this.guiTop + var22), 7961015, 0.5F, "left", false, "minecraftDungeons", 22);
                ModernGui.drawScaledStringCustomFont(String.format("%.0f", new Object[] {(Double)selectedPlot.get("area")}) + " blocs", (float)(this.guiLeft + 296 + 5) + dg22.getStringWidth(I18n.getString("faction.plots.area").toUpperCase()) / 2.0F, (float)(this.guiTop + var22) + 0.5F, 14606061, 0.5F, "left", false, "georamaSemiBold", 30);
                var22 += 10;
                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.plots.price_blocs").toUpperCase() + ":", (float)(this.guiLeft + 296), (float)(this.guiTop + var22), 7961015, 0.5F, "left", false, "minecraftDungeons", 22);
                ModernGui.drawScaledStringCustomFont(String.format("%.5f", new Object[] {Double.valueOf(((Double)selectedPlot.get("price")).doubleValue() / ((Double)selectedPlot.get("area")).doubleValue())}) + "$/bloc", (float)(this.guiLeft + 296 + 5) + dg22.getStringWidth(I18n.getString("faction.plots.price_blocs").toUpperCase()) / 2.0F, (float)(this.guiTop + var22) + 0.5F, 14606061, 0.5F, "left", false, "georamaSemiBold", 30);
                var22 += 20;
                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.plots.position").toUpperCase() + ":", (float)(this.guiLeft + 296), (float)(this.guiTop + var22), 7961015, 0.5F, "left", false, "minecraftDungeons", 22);
                ModernGui.drawScaledStringCustomFont(((String)selectedPlot.get("position")).replaceAll("#", ", ").replaceAll("world, ", ""), (float)(this.guiLeft + 296 + 5) + dg22.getStringWidth(I18n.getString("faction.plots.position").toUpperCase()) / 2.0F, (float)(this.guiTop + var22) + 0.5F, 14606061, 0.5F, "left", false, "georamaSemiBold", 30);
                var22 += 10;
                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.plots.seller").toUpperCase() + ":", (float)(this.guiLeft + 296), (float)(this.guiTop + var22), 7961015, 0.5F, "left", false, "minecraftDungeons", 22);
                ModernGui.drawScaledStringCustomFont((String)FactionGUI.factionInfos.get("name"), (float)(this.guiLeft + 296 + 5) + dg22.getStringWidth(I18n.getString("faction.plots.seller").toUpperCase()) / 2.0F, (float)(this.guiTop + var22) + 0.5F, 14606061, 0.5F, "left", false, "georamaSemiBold", 30);
                var22 += 10;
                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.plots.publish_date").toUpperCase() + ":", (float)(this.guiLeft + 296), (float)(this.guiTop + var22), 7961015, 0.5F, "left", false, "minecraftDungeons", 22);
                Date var25 = new Date(((Double)selectedPlot.get("publishTime")).longValue());
                SimpleDateFormat var27 = new SimpleDateFormat("dd/MM/yyy");
                ModernGui.drawScaledStringCustomFont(((Double)selectedPlot.get("publishTime")).doubleValue() > 0.0D ? var27.format(var25) : "-", (float)(this.guiLeft + 296 + 5) + dg22.getStringWidth(I18n.getString("faction.plots.publish_date").toUpperCase()) / 2.0F, (float)(this.guiTop + var22) + 0.5F, 14606061, 0.5F, "left", false, "georamaSemiBold", 30);
                owner = (String)selectedPlot.get("owner");
                boolean var28 = owner != null && owner.equals(Minecraft.getMinecraft().thePlayer.username);
                ClientEventHandler.STYLE.bindTexture("faction_plots");
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 282), (float)(this.guiTop + 176), (float)(333 * GUI_SCALE), (float)(252 * GUI_SCALE), 170 * GUI_SCALE, 42 * GUI_SCALE, 170, 42, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);

                if (!owner.isEmpty())
                {
                    if (!ClientProxy.cacheHeadPlayer.containsKey(owner))
                    {
                        try
                        {
                            ResourceLocation var30 = AbstractClientPlayer.locationStevePng;
                            var30 = AbstractClientPlayer.getLocationSkin(owner);
                            AbstractClientPlayer.getDownloadImageSkin(var30, owner);
                            ClientProxy.cacheHeadPlayer.put(owner, var30);
                        }
                        catch (Exception var20)
                        {
                            System.out.println(var20.getMessage());
                        }
                    }
                    else
                    {
                        Minecraft.getMinecraft().renderEngine.bindTexture((ResourceLocation)ClientProxy.cacheHeadPlayer.get(owner));
                        this.mc.getTextureManager().bindTexture((ResourceLocation)ClientProxy.cacheHeadPlayer.get(owner));
                        GUIUtils.drawScaledCustomSizeModalRect(this.guiLeft + 322, this.guiTop + 210, 8.0F, 16.0F, 8, -8, -27, -27, 64.0F, 64.0F);
                    }

                    ModernGui.drawScaledStringCustomFont(I18n.getString("faction.plots.occupant").toUpperCase() + ":", (float)(this.guiLeft + 330), (float)(this.guiTop + 183), 7961015, 0.5F, "left", false, "minecraftDungeons", 22);
                    ModernGui.drawScaledStringCustomFont(owner.isEmpty() ? I18n.getString("faction.plots.none") : owner, (float)(this.guiLeft + 332) + dg22.getStringWidth(I18n.getString("faction.plots.occupant").toUpperCase() + ":") / 2.0F, (float)this.guiTop + 184.0F, 14606061, 0.5F, "left", false, "georamaSemiBold", 30);
                    ModernGui.drawScaledStringCustomFont(I18n.getString("faction.plots.from").toUpperCase() + ":", (float)(this.guiLeft + 330), (float)(this.guiTop + 193), 7961015, 0.5F, "left", false, "minecraftDungeons", 22);

                    if (((Double)selectedPlot.get("transactionTime")).doubleValue() != 0.0D)
                    {
                        Date var31 = new Date(((Double)selectedPlot.get("transactionTime")).longValue());
                        ModernGui.drawScaledStringCustomFont(var27.format(var31), (float)(this.guiLeft + 332) + dg22.getStringWidth(I18n.getString("faction.plots.from").toUpperCase() + ":") / 2.0F, (float)this.guiTop + 194.0F, 14606061, 0.5F, "left", false, "georamaSemiBold", 30);
                    }
                    else
                    {
                        ModernGui.drawScaledStringCustomFont("-", (float)(this.guiLeft + 332) + dg22.getStringWidth(I18n.getString("faction.plots.from").toUpperCase() + ":") / 2.0F, (float)this.guiTop + 194.0F, 14606061, 0.5F, "left", false, "georamaSemiBold", 30);
                    }
                }

                if (!var28 && (selectedPlot.get("owner") == null || selectedPlot.get("owner").equals("") || !selectedPlot.get("creator").equals(FactionGUI.factionInfos.get("id")) || !FactionGUI.hasPermissions("locations")))
                {
                    boolean var32;

                    if (selectedPlot.get("creator").equals(FactionGUI.factionInfos.get("id")) && FactionGUI.hasPermissions("locations"))
                    {
                        ClientEventHandler.STYLE.bindTexture("faction_plots");
                        var32 = mouseX >= this.guiLeft + 300 && mouseX <= this.guiLeft + 300 + 110 && mouseY >= this.guiTop + 187 && mouseY <= this.guiTop + 207;
                        ClientEventHandler.STYLE.bindTexture("faction_plots_3");
                        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 300), (float)(this.guiTop + 187), (float)(0 * GUI_SCALE), (float)((var32 ? 367 : 343) * GUI_SCALE), 110 * GUI_SCALE, 20 * GUI_SCALE, 110, 20, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                        ModernGui.drawScaledStringCustomFont(I18n.getString("faction.plots.label." + selectedPlot.get("buyType")).toUpperCase() + " " + String.format("%.0f", new Object[] {(Double)selectedPlot.get("price")}) + "$", (float)(this.guiLeft + 300 + 55), (float)this.guiTop + 191.5F, var32 ? 7239406 : 16777215, 0.5F, "center", false, "minecraftDungeons", 26);

                        if (var32)
                        {
                            this.hoveredAction = "buyRent#" + selectedPlot.get("id");
                        }

                        hoveredBtnEdit = mouseX >= this.guiLeft + 415 && mouseX <= this.guiLeft + 415 + 20 && mouseY >= this.guiTop + 187 && mouseY <= this.guiTop + 187 + 20;
                        ClientEventHandler.STYLE.bindTexture("faction_plots");
                        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 415), (float)(this.guiTop + 187), (float)(86 * GUI_SCALE), (float)((hoveredBtnEdit ? 53 : 77) * GUI_SCALE), 20 * GUI_SCALE, 20 * GUI_SCALE, 20, 20, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);

                        if (hoveredBtnEdit)
                        {
                            this.hoveredAction = "edit#" + selectedPlot.get("id");
                            tooltipToDraw = Arrays.asList(new String[] {"\u00a7b" + I18n.getString("faction.plots.edit")});
                        }
                    }
                    else if (owner.isEmpty() && ((Boolean)FactionGUI.factionInfos.get("isInCountry")).booleanValue())
                    {
                        var32 = mouseX >= this.guiLeft + 300 && mouseX <= this.guiLeft + 300 + 138 && mouseY >= this.guiTop + 187 && mouseY <= this.guiTop + 187 + 20;
                        ClientEventHandler.STYLE.bindTexture("faction_plots_3");
                        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 300), (float)(this.guiTop + 187), (float)(0 * GUI_SCALE), (float)((var32 ? 419 : 395) * GUI_SCALE), 138 * GUI_SCALE, 20 * GUI_SCALE, 138, 20, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                        ModernGui.drawScaledStringCustomFont(I18n.getString("faction.plots.label." + selectedPlot.get("buyType")).toUpperCase() + " " + String.format("%.0f", new Object[] {(Double)selectedPlot.get("price")}) + "$", (float)(this.guiLeft + 300 + 69), (float)this.guiTop + 191.5F, var32 ? 7239406 : 16777215, 0.5F, "center", false, "minecraftDungeons", 26);

                        if (var32)
                        {
                            this.hoveredAction = "buyRent#" + selectedPlot.get("id");
                        }
                    }
                }
                else
                {
                    ClientEventHandler.STYLE.bindTexture("faction_plots");

                    if (mouseX >= this.guiLeft + 420 && mouseX <= this.guiLeft + 440 && mouseY >= this.guiTop + 187 && mouseY <= this.guiTop + 207)
                    {
                        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 420), (float)(this.guiTop + 187), (float)(86 * GUI_SCALE), (float)(53 * GUI_SCALE), 20 * GUI_SCALE, 20 * GUI_SCALE, 20, 20, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                        this.hoveredAction = "edit#" + selectedPlot.get("id");
                    }
                    else
                    {
                        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 420), (float)(this.guiTop + 187), (float)(86 * GUI_SCALE), (float)(77 * GUI_SCALE), 20 * GUI_SCALE, 20 * GUI_SCALE, 20, 20, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                    }

                    ModernGui.drawScaledStringCustomFont(I18n.getString("faction.plots.total_sent").toUpperCase() + ":", (float)(this.guiLeft + 330), (float)(this.guiTop + 205), 7961015, 0.5F, "left", false, "minecraftDungeons", 22);
                    ModernGui.drawScaledStringCustomFont(((String)selectedPlot.get("buyType")).equals("rent") ? (selectedPlot.containsKey("rent_total") ? String.format("%.0f", new Object[] {(Double)selectedPlot.get("rent_total")}): "0"): String.format("%.0f", new Object[] {(Double)selectedPlot.get("price")}) + "$", (float)(this.guiLeft + 332) + dg22.getStringWidth(I18n.getString("faction.plots.total_sent").toUpperCase() + ":") / 2.0F, (float)this.guiTop + 205.5F, 14606061, 0.5F, "left", false, "georamaSemiBold", 30);
                }
            }
        }

        super.drawScreen(mouseX, mouseY, partialTick);
    }

    public void drawScreen(int mouseX, int mouseY) {}

    private float getSlidePlots()
    {
        return plots.size() > 6 ? (float)(-(plots.size() - 6) * 35) * this.scrollBarPlots.getSliderValue() : 0.0F;
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (mouseButton == 0)
        {
            this.inputSearchName.mouseClicked(mouseX, mouseY, mouseButton);

            if (!this.hoveredAction.isEmpty())
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                String action;

                if (this.hoveredAction.contains("filter"))
                {
                    action = this.hoveredAction.replace("filter#", "");

                    if (this.selectedSearchFilters.contains(action))
                    {
                        this.selectedSearchFilters.remove(action);
                    }
                    else
                    {
                        this.selectedSearchFilters.add(action);
                    }
                }
                else
                {
                    int plotId;
                    int action1;

                    if (this.hoveredAction.equals("carousel_previous"))
                    {
                        if (!selectedPlot.isEmpty())
                        {
                            action1 = selectedPlot.containsKey("indexImage") ? ((Integer)selectedPlot.get("indexImage")).intValue() : 0;
                            plotId = action1 - 1 >= 0 ? action1 - 1 : ((ArrayList)selectedPlot.get("images")).size() - 1;
                            selectedPlot.put("indexImage", Integer.valueOf(plotId));
                        }
                    }
                    else if (this.hoveredAction.equals("carousel_next"))
                    {
                        if (!selectedPlot.isEmpty())
                        {
                            action1 = selectedPlot.containsKey("indexImage") ? ((Integer)selectedPlot.get("indexImage")).intValue() : 0;
                            plotId = action1 + 1 < ((ArrayList)selectedPlot.get("images")).size() ? action1 + 1 : 0;
                            selectedPlot.put("indexImage", Integer.valueOf(plotId));
                        }
                    }
                    else if (this.hoveredAction.equals("back"))
                    {
                        selectedPlot.clear();
                    }
                    else if (!this.hoveredAction.contains("remove#") && !this.hoveredAction.contains("exclude#") && !this.hoveredAction.contains("buyRent#"))
                    {
                        if (this.hoveredAction.contains("edit#"))
                        {
                            Minecraft.getMinecraft().displayGuiScreen(new FactionCreateEditPlotsGUI(((Double)selectedPlot.get("id")).intValue()));
                        }
                        else if (this.hoveredAction.equals("create"))
                        {
                            Minecraft.getMinecraft().displayGuiScreen(new FactionCreateEditPlotsGUI(-1));
                        }
                    }
                    else
                    {
                        action = this.hoveredAction.split("#")[0];
                        plotId = (int)Double.parseDouble(this.hoveredAction.split("#")[1]);
                        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionPlotsActionPacket(plotId, action, new HashMap())));
                        Minecraft.getMinecraft().displayGuiScreen(new FactionPlotsGUI());
                    }
                }
            }
            else if (!hoveredPlot.isEmpty())
            {
                selectedPlot = (HashMap)hoveredPlot.clone();
                hoveredPlot.clear();
            }
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.inputSearchName.updateCursorCounter();
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2)
    {
        this.inputSearchName.textboxKeyTyped(par1, par2);
        super.keyTyped(par1, par2);
    }
}
