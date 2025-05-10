package net.ilexiconn.nationsgui.forge.client.gui.enterprise;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.imageio.ImageIO;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.MinimapRenderer;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.TexturedCenteredButtonGUI;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseGui$1;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseGui$2;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseGui$3;
import net.ilexiconn.nationsgui.forge.client.gui.faction.ProfilGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseMainDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterprisePlayerDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.MinimapRequestPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import sun.misc.BASE64Decoder;

@SideOnly(Side.CLIENT)
public class EnterpriseGui extends GuiScreen
{
    public static final List<GuiScreenTab> TABS = new ArrayList();
    public static HashMap<String, HashMap<String, Object>> playerTooltip;
    public static HashMap<String, Object> enterpriseInfos;
    public static boolean loaded = false;
    private String enterpriseName;
    private RenderItem itemRenderer = new RenderItem();
    protected int xSize = 400;
    protected int ySize = 250;
    protected String hoveredPlayer = "";
    private boolean showServices = false;
    private int guiLeft;
    private int guiTop;
    private GuiButton actionButton;
    private GuiButton contractButton;
    private GuiButton leaveButton;
    private GuiScrollBarFaction scrollBarOnline;
    private GuiScrollBarFaction scrollBarOffline;
    public MinimapRenderer minimapRenderer = new MinimapRenderer(6, 6);
    public static boolean mapLoaded = false;
    private DynamicTexture flagTexture;
    public static Long lastContractDemand = Long.valueOf(0L);
    public static List<String> availableTypes = new ArrayList();

    public EnterpriseGui(String enterpriseName)
    {
        initTabs();
        this.enterpriseName = enterpriseName;
        mapLoaded = false;
        loaded = false;
        enterpriseInfos = null;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        this.scrollBarOnline = new GuiScrollBarFaction((float)(this.guiLeft + 248), (float)(this.guiTop + 145), 80);
        this.scrollBarOffline = new GuiScrollBarFaction((float)(this.guiLeft + 377), (float)(this.guiTop + 145), 80);
        playerTooltip = new HashMap();

        if (enterpriseInfos != null && enterpriseInfos.size() != 0 && enterpriseInfos.get("name").equals(this.enterpriseName))
        {
            loaded = true;
            mapLoaded = false;
        }
        else
        {
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new EnterpriseMainDataPacket(this.enterpriseName)));
        }

        this.actionButton = new GuiButton(0, this.guiLeft + 10, this.guiTop + 165, 100, 20, I18n.getString("enterprise.home.button.services"));
        this.contractButton = new GuiButton(1, this.guiLeft + 10, this.guiTop + 188, 100, 20, I18n.getString("enterprise.home.button.contract"));
        this.leaveButton = new TexturedCenteredButtonGUI(2, this.guiLeft + 10, this.guiTop + 220, 100, 20, "faction_btn", 0, 0, I18n.getString("enterprise.home.button.leave"));
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float par3)
    {
        if (loaded && enterpriseInfos != null && this.flagTexture == null && enterpriseInfos != null && enterpriseInfos.get("flagImage") != null && !((String)enterpriseInfos.get("flagImage")).isEmpty())
        {
            BufferedImage tooltipToDraw = decodeToImage((String)enterpriseInfos.get("flagImage"));
            this.flagTexture = new DynamicTexture(tooltipToDraw);
        }

        this.drawDefaultBackground();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        ClientEventHandler.STYLE.bindTexture("enterprise_main");
        ModernGui.drawModalRectWithCustomSizedTexture((float)this.guiLeft, (float)this.guiTop, 0, 0, this.xSize, this.ySize, 512.0F, 512.0F, false);
        List var17 = null;
        String playerTooltipToDraw = "";

        if (loaded && enterpriseInfos != null && enterpriseInfos.size() > 0)
        {
            if (this.showServices)
            {
                this.actionButton.displayString = I18n.getString("enterprise.home.button.infos");
            }
            else
            {
                this.actionButton.displayString = I18n.getString("enterprise.home.button.services");
            }

            this.actionButton.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);

            if (!enterpriseInfos.get("type").equals("farm") && !enterpriseInfos.get("type").equals("electric") && !enterpriseInfos.get("type").equals("petrol") && !enterpriseInfos.get("type").equals("bet") && !enterpriseInfos.get("type").equals("casino") && !enterpriseInfos.get("type").equals("realestate") && (!((Boolean)enterpriseInfos.get("isInEnterprise")).booleanValue() || enterpriseInfos.get("type").equals("trader") || Minecraft.getMinecraft().thePlayer.username.equalsIgnoreCase("iBalix")) && ((Boolean)enterpriseInfos.get("canRequestContract")).booleanValue())
            {
                this.contractButton.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);

                if (!((Boolean)enterpriseInfos.get("isOpen")).booleanValue())
                {
                    this.contractButton.enabled = false;

                    if (mouseX >= this.guiLeft + 10 && mouseX <= this.guiLeft + 10 + 100 && mouseY >= this.guiTop + 188 && mouseY <= this.guiTop + 188 + 20)
                    {
                        var17 = Arrays.asList(I18n.getString("enterprise.home.button.contract.closed").split("##"));
                    }
                }
                else if (System.currentTimeMillis() - lastContractDemand.longValue() < 300000L && !Minecraft.getMinecraft().thePlayer.username.equalsIgnoreCase("iBalix"))
                {
                    this.contractButton.enabled = false;

                    if (mouseX >= this.guiLeft + 10 && mouseX <= this.guiLeft + 10 + 100 && mouseY >= this.guiTop + 188 && mouseY <= this.guiTop + 188 + 20)
                    {
                        var17 = Arrays.asList(I18n.getString("enterprise.home.button.contract.delay").split("##"));
                    }
                }
            }
            else
            {
                this.contractButton.enabled = false;
            }

            if (!enterpriseInfos.get("playerRole").equals("leader") && ((Boolean)enterpriseInfos.get("isInEnterprise")).booleanValue())
            {
                this.leaveButton.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);
            }

            ClientEventHandler.STYLE.bindTexture("enterprise_main");

            if (!mapLoaded && !enterpriseInfos.get("position").equals(""))
            {
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new MinimapRequestPacket(Integer.parseInt(((String)enterpriseInfos.get("position")).split("##")[0]), Integer.parseInt(((String)enterpriseInfos.get("position")).split("##")[1]), 6, 6)));
                mapLoaded = true;
            }

            if (mouseX > this.guiLeft + 385 && mouseX < this.guiLeft + 385 + 9 && mouseY > this.guiTop - 6 && mouseY < this.guiTop - 6 + 10)
            {
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 385), (float)(this.guiTop - 6), 138, 261, 9, 10, 512.0F, 512.0F, false);
            }
            else
            {
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 385), (float)(this.guiTop - 6), 138, 251, 9, 10, 512.0F, 512.0F, false);
            }

            if (!this.showServices)
            {
                this.drawScaledString(I18n.getString("enterprise.tab.home"), this.guiLeft + 131, this.guiTop + 16, 1644825, 1.4F, false, false);
            }
            else
            {
                this.drawScaledString(I18n.getString("enterprise.tab.home_services"), this.guiLeft + 131, this.guiTop + 16, 1644825, 1.4F, false, false);
            }

            if (((String)enterpriseInfos.get("name")).length() <= 9)
            {
                this.drawScaledString((String)enterpriseInfos.get("name"), this.guiLeft + 60, this.guiTop + 25, 16777215, 1.8F, true, true);
            }
            else
            {
                this.drawScaledString((String)enterpriseInfos.get("name"), this.guiLeft + 60, this.guiTop + 25, 16777215, 1.0F, true, true);
            }

            this.drawScaledString(I18n.getString("faction.common.age_1") + " " + enterpriseInfos.get("age") + " " + I18n.getString("faction.common.age_2"), this.guiLeft + 60, this.guiTop + 43, 11842740, 0.65F, true, false);
            ClientEventHandler.STYLE.bindTexture("enterprise_main");
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 12), (float)(this.guiTop + 61), getTypeOffsetX((String)enterpriseInfos.get("type")), 442, 16, 16, 512.0F, 512.0F, false);
            this.drawScaledString(I18n.getString("enterprise.type." + ((String)enterpriseInfos.get("type")).toLowerCase()), this.guiLeft + 32, this.guiTop + 66, 16777215, 1.0F, false, false);
            int x1;
            int var18;
            int var19;
            String var22;

            if (!this.showServices)
            {
                if (this.flagTexture != null)
                {
                    GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.flagTexture.getGlTextureId());
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 337), (float)(this.guiTop + 47), 0.0F, 0.0F, 150, 150, 22, 22, 150.0F, 150.0F, false);
                }

                String[] i = ((String)enterpriseInfos.get("description")).replaceAll("\u00a7[0-9a-z]{1}", "").split(" ");
                String x = "";
                x1 = 0;
                String[] i1 = i;
                int serviceLine = i.length;

                for (int playerNamePrefix = 0; playerNamePrefix < serviceLine; ++playerNamePrefix)
                {
                    String offsetX = i1[playerNamePrefix];

                    if (this.fontRenderer.getStringWidth(x + offsetX) <= 160)
                    {
                        if (!x.equals(""))
                        {
                            x = x + " ";
                        }

                        x = x + offsetX;
                    }
                    else
                    {
                        if (x1 == 0)
                        {
                            x = "\u00a7o\"" + x;
                        }

                        this.drawScaledString(x, this.guiLeft + 220, this.guiTop + 40 + x1 * 10, 16777215, 1.0F, true, true);
                        ++x1;
                        x = offsetX;
                    }
                }

                if (x1 == 0)
                {
                    x = "\u00a7o\"" + x;
                }

                this.drawScaledString(x + "\"", this.guiLeft + 220, this.guiTop + 40 + x1 * 10, 16777215, 1.0F, true, true);
                this.drawScaledString((String)enterpriseInfos.get("turnover"), this.guiLeft + 151, this.guiTop + 114, 11842740, 0.9F, false, false);

                if (mouseX >= this.guiLeft + 130 && mouseX <= this.guiLeft + 150 && mouseY >= this.guiTop + 109 && mouseY <= this.guiTop + 123)
                {
                    var17 = Arrays.asList(new String[] {I18n.getString("enterprise.infos.turnover")});
                }

                this.drawScaledString((String)enterpriseInfos.get("contractsDone"), this.guiLeft + 228, this.guiTop + 114, 11842740, 0.9F, false, false);

                if (mouseX >= this.guiLeft + 208 && mouseX <= this.guiLeft + 228 && mouseY >= this.guiTop + 109 && mouseY <= this.guiTop + 123)
                {
                    var17 = Arrays.asList(new String[] {I18n.getString("enterprise.infos.contractsDone")});
                }

                this.drawScaledString((String)enterpriseInfos.get("contractsSuccess"), this.guiLeft + 291, this.guiTop + 114, 11842740, 0.9F, false, false);

                if (mouseX >= this.guiLeft + 271 && mouseX <= this.guiLeft + 291 && mouseY >= this.guiTop + 109 && mouseY <= this.guiTop + 123)
                {
                    var17 = Arrays.asList(new String[] {I18n.getString("enterprise.infos.contractsSuccess")});
                }

                this.drawScaledString((String)enterpriseInfos.get("disputes"), this.guiLeft + 355, this.guiTop + 114, 11842740, 0.9F, false, false);

                if (mouseX >= this.guiLeft + 335 && mouseX <= this.guiLeft + 355 && mouseY >= this.guiTop + 109 && mouseY <= this.guiTop + 123)
                {
                    var17 = Arrays.asList(new String[] {I18n.getString("enterprise.infos.disputes")});
                }

                ClientEventHandler.STYLE.bindTexture("enterprise_main");
                this.hoveredPlayer = "";
                this.drawScaledString(I18n.getString("enterprise.home.players_online") + " (" + ((ArrayList)enterpriseInfos.get("players_online")).size() + ")", this.guiLeft + 131, this.guiTop + 133, 1644825, 0.85F, false, false);
                Float offsetY;
                ResourceLocation resourceLocation;
                String var24;
                int var25;

                if (((ArrayList)enterpriseInfos.get("players_online")).size() > 0)
                {
                    GUIUtils.startGLScissor(this.guiLeft + 132, this.guiTop + 142, 116, 86);

                    for (var19 = 0; var19 < ((ArrayList)enterpriseInfos.get("players_online")).size(); ++var19)
                    {
                        var22 = ((String)((ArrayList)enterpriseInfos.get("players_online")).get(var19)).split("#")[1];
                        var24 = "";

                        if (var22.split(" ").length > 1)
                        {
                            var24 = var22.split(" ")[0];
                            var22 = var22.split(" ")[1];
                        }

                        var25 = this.guiLeft + 132;
                        offsetY = Float.valueOf((float)(this.guiTop + 142 + var19 * 20) + this.getSlideOnline());

                        if (mouseX > var25 && mouseX < var25 + 116 && (float)mouseY > offsetY.floatValue() && (float)mouseY < offsetY.floatValue() + 20.0F)
                        {
                            this.hoveredPlayer = var22;
                        }

                        ClientEventHandler.STYLE.bindTexture("enterprise_main");
                        ModernGui.drawModalRectWithCustomSizedTexture((float)var25, (float)offsetY.intValue(), 132, 142, 116, 20, 512.0F, 512.0F, false);

                        try
                        {
                            resourceLocation = AbstractClientPlayer.locationStevePng;
                            resourceLocation = AbstractClientPlayer.getLocationSkin(var22);
                            AbstractClientPlayer.getDownloadImageSkin(resourceLocation, var22);
                            Minecraft.getMinecraft().renderEngine.bindTexture(resourceLocation);
                            this.mc.getTextureManager().bindTexture(resourceLocation);
                            GUIUtils.drawScaledCustomSizeModalRect(var25 + 6 + 10, offsetY.intValue() + 4 + 10, 8.0F, 16.0F, 8, -8, -10, -10, 64.0F, 64.0F);
                            GUIUtils.drawScaledCustomSizeModalRect(var25 + 6 + 10, offsetY.intValue() + 4 + 10, 40.0F, 16.0F, 8, -8, -10, -10, 64.0F, 64.0F);
                        }
                        catch (Exception var16)
                        {
                            System.out.println(var16.getMessage());
                        }

                        this.drawScaledString(var24 + " " + var22, var25 + 20, offsetY.intValue() + 6, 11842740, 0.8F, false, true);
                        ClientEventHandler.STYLE.bindTexture("enterprise_main");
                        ModernGui.drawModalRectWithCustomSizedTexture((float)(var25 + 104), (float)(offsetY.intValue() + 5), 148, 250, 10, 11, 512.0F, 512.0F, false);

                        if (mouseX >= var25 + 104 && mouseX <= var25 + 104 + 10 && (float)mouseY >= offsetY.floatValue() + 5.0F && (float)mouseY <= offsetY.floatValue() + 5.0F + 11.0F)
                        {
                            if (playerTooltip.containsKey(this.enterpriseName + "##" + var22))
                            {
                                playerTooltipToDraw = var22;
                            }
                            else
                            {
                                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new EnterprisePlayerDataPacket(this.enterpriseName, var22)));
                            }
                        }
                    }

                    GUIUtils.endGLScissor();
                }

                this.drawScaledString(I18n.getString("enterprise.home.players_offline") + " (" + ((ArrayList)enterpriseInfos.get("players_offline")).size() + ")", this.guiLeft + 260, this.guiTop + 133, 1644825, 0.85F, false, false);

                if (((ArrayList)enterpriseInfos.get("players_offline")).size() > 0)
                {
                    GUIUtils.startGLScissor(this.guiLeft + 261, this.guiTop + 142, 116, 86);

                    for (var19 = 0; var19 < ((ArrayList)enterpriseInfos.get("players_offline")).size(); ++var19)
                    {
                        var22 = ((String)((ArrayList)enterpriseInfos.get("players_offline")).get(var19)).split("#")[1];
                        var24 = "";

                        if (var22.split(" ").length > 1)
                        {
                            var24 = var22.split(" ")[0];
                            var22 = var22.split(" ")[1];
                        }

                        var25 = this.guiLeft + 261;
                        offsetY = Float.valueOf((float)(this.guiTop + 142 + var19 * 20) + this.getSlideOffline());

                        if (mouseX > var25 && mouseX < var25 + 116 && (float)mouseY > offsetY.floatValue() && (float)mouseY < offsetY.floatValue() + 20.0F)
                        {
                            this.hoveredPlayer = var22;
                        }

                        ClientEventHandler.STYLE.bindTexture("enterprise_main");
                        ModernGui.drawModalRectWithCustomSizedTexture((float)var25, (float)offsetY.intValue(), 261, 142, 116, 20, 512.0F, 512.0F, false);

                        try
                        {
                            resourceLocation = AbstractClientPlayer.locationStevePng;
                            resourceLocation = AbstractClientPlayer.getLocationSkin(var22);
                            AbstractClientPlayer.getDownloadImageSkin(resourceLocation, var22);
                            Minecraft.getMinecraft().renderEngine.bindTexture(resourceLocation);
                            this.mc.getTextureManager().bindTexture(resourceLocation);
                            GUIUtils.drawScaledCustomSizeModalRect(var25 + 6 + 10, offsetY.intValue() + 4 + 10, 8.0F, 16.0F, 8, -8, -10, -10, 64.0F, 64.0F);
                            GUIUtils.drawScaledCustomSizeModalRect(var25 + 6 + 10, offsetY.intValue() + 4 + 10, 40.0F, 16.0F, 8, -8, -10, -10, 64.0F, 64.0F);
                        }
                        catch (Exception var15)
                        {
                            ;
                        }

                        this.drawScaledString(var24 + " " + var22, var25 + 20, offsetY.intValue() + 6, 11842740, 0.8F, false, true);
                        ClientEventHandler.STYLE.bindTexture("enterprise_main");
                        ModernGui.drawModalRectWithCustomSizedTexture((float)(var25 + 104), (float)(offsetY.intValue() + 5), 148, 250, 10, 11, 512.0F, 512.0F, false);

                        if (mouseX >= var25 + 104 && mouseX <= var25 + 104 + 10 && (float)mouseY >= offsetY.floatValue() + 5.0F && (float)mouseY <= offsetY.floatValue() + 5.0F + 11.0F)
                        {
                            if (playerTooltip.containsKey(this.enterpriseName + "##" + var22))
                            {
                                playerTooltipToDraw = var22;
                            }
                            else
                            {
                                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new EnterprisePlayerDataPacket(this.enterpriseName, var22)));
                            }
                        }
                    }

                    GUIUtils.endGLScissor();
                }

                if (mouseX > this.guiLeft + 130 && mouseX < this.guiLeft + 255 && mouseY > this.guiTop + 139 && mouseY < this.guiTop + 229)
                {
                    this.scrollBarOnline.draw(mouseX, mouseY);
                }

                if (mouseX > this.guiLeft + 259 && mouseX < this.guiLeft + 384 && mouseY > this.guiTop + 139 && mouseY < this.guiTop + 229)
                {
                    this.scrollBarOffline.draw(mouseX, mouseY);
                }
            }
            else
            {
                ModernGui.drawNGBlackSquare(this.guiLeft + 130, this.guiTop + 30, 254, 200);
                var18 = 0;
                String[] var20 = ((String)enterpriseInfos.get("services")).split("##");
                x1 = var20.length;

                for (var19 = 0; var19 < x1; ++var19)
                {
                    var22 = var20[var19];
                    this.drawScaledString(var22.replace("&", "\u00a7"), this.guiLeft + 133, this.guiTop + 38 + var18 * 10, 16777215, 1.0F, false, false);
                    ++var18;
                }
            }

            if (var17 != null)
            {
                this.drawTooltip(var17, mouseX, mouseY);
            }

            if (!playerTooltipToDraw.isEmpty())
            {
                this.drawPlayerTooltip(playerTooltipToDraw, mouseX, mouseY);
            }

            ClientEventHandler.STYLE.bindTexture("enterprise_main");

            for (var18 = 0; var18 < TABS.size(); ++var18)
            {
                GuiScreenTab var21 = (GuiScreenTab)TABS.get(var18);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                x1 = getTabIndex((GuiScreenTab)TABS.get(var18));

                if (this.getClass() == var21.getClassReferent())
                {
                    ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23), (float)(this.guiTop + 20 + var18 * 31), 23, 250, 29, 31, 512.0F, 512.0F, false);
                    ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23 + 4), (float)(this.guiTop + 20 + var18 * 31 + 5), x1 * 20, 301, 20, 20, 512.0F, 512.0F, false);
                }
                else
                {
                    ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23), (float)(this.guiTop + 20 + var18 * 31), 0, 250, 23, 31, 512.0F, 512.0F, false);
                    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                    GL11.glEnable(GL11.GL_BLEND);
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.75F);
                    ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23 + 4), (float)(this.guiTop + 20 + var18 * 31 + 5), x1 * 20, 301, 20, 20, 512.0F, 512.0F, false);
                    GL11.glDisable(GL11.GL_BLEND);
                }
            }

            if (mapLoaded)
            {
                GUIUtils.startGLScissor(this.guiLeft + 11, this.guiTop + 83, 180, 78);
                GL11.glDisable(GL11.GL_DEPTH_TEST);
                this.minimapRenderer.renderMap(this.guiLeft + 12, this.guiTop + 74, mouseX, mouseY, true);
                GL11.glEnable(GL11.GL_DEPTH_TEST);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                ClientEventHandler.STYLE.bindTexture("enterprise_main");
                ModernGui.drawModalRectWithCustomSizedTextureWithTransparency((float)(this.guiLeft + 11), (float)(this.guiTop + 83), 0, 362, 98, 78, 512.0F, 512.0F, false);
                GUIUtils.endGLScissor();
            }

            for (var18 = 0; var18 < TABS.size(); ++var18)
            {
                int var23 = getTabIndex((GuiScreenTab)TABS.get(var18));

                if (mouseX >= this.guiLeft - 23 && mouseX <= this.guiLeft - 23 + 29 && mouseY >= this.guiTop + 20 + var18 * 31 && mouseY <= this.guiTop + 20 + 30 + var18 * 31)
                {
                    this.drawHoveringText(Arrays.asList(new String[] {I18n.getString("enterprise.tab." + var23)}), mouseX, mouseY, this.fontRenderer);
                }
            }

            super.drawScreen(mouseX, mouseY, par3);
            GL11.glEnable(GL11.GL_LIGHTING);
            RenderHelper.enableStandardItemLighting();
        }
        else if (loaded)
        {
            Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
        }
    }

    private void drawPlayerTooltip(String playerName, int mouseX, int mouseY)
    {
        ArrayList tooltipLines = this.getPlayerTooltip(playerName);
        this.drawHoveringText(tooltipLines, mouseX, mouseY, this.fontRenderer);
    }

    private void drawTooltip(List<String> lines, int mouseX, int mouseY)
    {
        this.drawHoveringText(lines, mouseX, mouseY, this.fontRenderer);
    }

    public ArrayList<String> getPlayerTooltip(String playerName)
    {
        HashMap playerInfo = (HashMap)playerTooltip.get(this.enterpriseName + "##" + playerName);
        ArrayList tooltipLines = new ArrayList();
        tooltipLines.add("\u00a77" + I18n.getString("enterprise.label.job") + " : " + I18n.getString("enterprise.role." + playerInfo.get("role")));
        tooltipLines.add("\u00a78----------------------");

        if (playerInfo.get("offline_days").equals("online"))
        {
            tooltipLines.add("\u00a7a" + I18n.getString("enterprise.home.tooltip.online"));
        }
        else
        {
            tooltipLines.add("\u00a77" + I18n.getString("enterprise.home.tooltip.offline_since_1") + " " + playerInfo.get("offline_days") + " " + I18n.getString("enterprise.home.tooltip.offline_since_2"));
        }

        return tooltipLines;
    }

    private float getSlideOnline()
    {
        return ((ArrayList)enterpriseInfos.get("players_online")).size() > 4 ? (float)(-(((ArrayList)enterpriseInfos.get("players_online")).size() - 4) * 20) * this.scrollBarOnline.getSliderValue() : 0.0F;
    }

    private float getSlideOffline()
    {
        return ((ArrayList)enterpriseInfos.get("players_offline")).size() > 4 ? (float)(-(((ArrayList)enterpriseInfos.get("players_offline")).size() - 4) * 20) * this.scrollBarOffline.getSliderValue() : 0.0F;
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (mouseButton == 0)
        {
            for (int i = 0; i < TABS.size(); ++i)
            {
                GuiScreenTab type = (GuiScreenTab)TABS.get(i);

                if (mouseX >= this.guiLeft - 20 && mouseX <= this.guiLeft + 3 && mouseY >= this.guiTop + 20 + i * 31 && mouseY <= this.guiTop + 50 + i * 31)
                {
                    this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);

                    if (this.getClass() != type.getClassReferent())
                    {
                        try
                        {
                            type.call();
                        }
                        catch (Exception var7)
                        {
                            var7.printStackTrace();
                        }
                    }
                }
            }

            if (mouseX > this.guiLeft + 385 && mouseX < this.guiLeft + 385 + 9 && mouseY > this.guiTop - 6 && mouseY < this.guiTop - 6 + 10)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
            }
            else if (this.actionButton.enabled && mouseX >= this.guiLeft + 10 && mouseX <= this.guiLeft + 10 + 100 && mouseY >= this.guiTop + 165 && mouseY <= this.guiTop + 165 + 20)
            {
                this.showServices = !this.showServices;
            }

            if (this.contractButton.enabled && mouseX >= this.guiLeft + 10 && mouseX <= this.guiLeft + 10 + 100 && mouseY >= this.guiTop + 188 && mouseY <= this.guiTop + 188 + 20)
            {
                if (enterpriseInfos.get("type").equals("pvp"))
                {
                    Minecraft.getMinecraft().displayGuiScreen(new EnterpriseContractForm_Pvp_Gui());
                }
                else if (enterpriseInfos.get("type").equals("trader"))
                {
                    Minecraft.getMinecraft().displayGuiScreen(new EnterpriseContractForm_Trader_Gui());
                }
                else if (enterpriseInfos.get("type").equals("repair"))
                {
                    Minecraft.getMinecraft().displayGuiScreen(new EnterpriseContractForm_Repair_Gui());
                }
                else if (enterpriseInfos.get("type").equals("loan"))
                {
                    Minecraft.getMinecraft().displayGuiScreen(new EnterpriseContractForm_Loan_Gui());
                }
                else
                {
                    Minecraft.getMinecraft().displayGuiScreen(new EnterpriseContractForm_Default_Gui());
                }
            }
            else if (enterpriseInfos != null && ((Boolean)enterpriseInfos.get("isInEnterprise")).booleanValue() && !enterpriseInfos.get("playerRole").equals("leader") && mouseX >= this.guiLeft + 10 && mouseX <= this.guiLeft + 10 + 100 && mouseY >= this.guiTop + 220 && mouseY <= this.guiTop + 220 + 20)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                Minecraft.getMinecraft().displayGuiScreen(new EnterpriseLeaveConfirmGui(this));
            }
            else if (!this.hoveredPlayer.isEmpty() && mouseX > this.guiLeft + 130 && mouseX < this.guiLeft + 130 + 254 && mouseY > this.guiTop + 140 && mouseY < this.guiTop + 140 + 90)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                Minecraft.getMinecraft().displayGuiScreen(new ProfilGui(this.hoveredPlayer, this.enterpriseName));
            }
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public static boolean hasPermission(String permName)
    {
        if (enterpriseInfos != null)
        {
            Iterator var1 = ((ArrayList)enterpriseInfos.get("permissions")).iterator();

            while (var1.hasNext())
            {
                String permissionNode = (String)var1.next();

                if (permissionNode.contains(permName) && permissionNode.contains((String)enterpriseInfos.get("playerRole")) || enterpriseInfos.containsKey("isPlayerOp") && ((Boolean)enterpriseInfos.get("isPlayerOp")).booleanValue())
                {
                    return true;
                }
            }
        }

        return false;
    }

    public static void initTabs()
    {
        TABS.clear();
        TABS.add(new EnterpriseGui$1());
        TABS.add(new EnterpriseGui$2());
        TABS.add(new EnterpriseGui$3());
    }

    public void drawScaledString(String text, int x, int y, int color, float scale, boolean centered, boolean shadow)
    {
        GL11.glPushMatrix();
        GL11.glScalef(scale, scale, scale);
        float newX = (float)x;

        if (centered)
        {
            newX = (float)x - (float)this.fontRenderer.getStringWidth(text) * scale / 2.0F;
        }

        if (shadow)
        {
            this.fontRenderer.drawString(text, (int)(newX / scale), (int)((float)(y + 1) / scale), (color & 16579836) >> 2 | color & -16777216, false);
        }

        this.fontRenderer.drawString(text, (int)(newX / scale), (int)((float)y / scale), color, false);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
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

    public static BufferedImage decodeToImage(String imageString)
    {
        BufferedImage image = null;

        try
        {
            BASE64Decoder e = new BASE64Decoder();
            byte[] imageByte = e.decodeBuffer(imageString);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        }
        catch (Exception var5)
        {
            var5.printStackTrace();
        }

        return image;
    }

    public static int getTypeOffsetX(String type)
    {
        return availableTypes.contains(type) ? (availableTypes.contains("all") ? (availableTypes.indexOf(type) - 1) * 16 : availableTypes.indexOf(type) * 16) : 0;
    }

    public static int getTabIndex(GuiScreenTab inventoryTab)
    {
        String classReferent = inventoryTab.getClassReferent().toString();
        return classReferent.contains("EnterpriseGui") ? 0 : (classReferent.contains("EnterpriseBankGUI") ? 1 : (classReferent.contains("EnterprisePermGUI") ? 2 : (classReferent.contains("EnterpriseContractGUI") ? 3 : (classReferent.contains("EnterpriseSettingsGUI") ? 4 : (classReferent.contains("EnterpriseParcelleGUI") ? 5 : (classReferent.contains("EnterpriseTraderGUI") ? 6 : (classReferent.contains("EnterpriseCasinoGUI") ? 7 : (classReferent.contains("EnterpriseBetGUI") ? 8 : (classReferent.contains("EnterpriseElectricGUI") ? 9 : (classReferent.contains("EnterprisePetrolGUI") ? 10 : (classReferent.contains("EnterpriseFarmGUI") ? 11 : 0)))))))))));
    }
}
