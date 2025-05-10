package net.ilexiconn.nationsgui.forge.client.gui.faction;

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
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGui_OLD$1;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGui_OLD$2;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGui_OLD$3;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGui_OLD$4;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGui_OLD$5;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGui_OLD$6;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGui_OLD$7;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionMainDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionMainJoinPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionMainTPPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionPlayerDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionRelationActionPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IncrementObjectivePacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.MinimapRequestPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.RemoteOpenOwnFactionMainPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import sun.misc.BASE64Decoder;

@SideOnly(Side.CLIENT)
public class FactionGui_OLD extends GuiScreen
{
    public static final List<GuiScreenTab> TABS = new ArrayList();
    public static HashMap<String, HashMap<String, Object>> playerTooltip;
    public static HashMap<String, Object> factionInfos;
    public static boolean loaded = false;
    public static boolean achievementDone = false;
    private String targetName;
    private RenderItem itemRenderer = new RenderItem();
    protected int xSize = 400;
    protected int ySize = 250;
    protected String hoveredPlayer = "";
    protected String hoveredAction = "";
    private int guiLeft;
    private int guiTop;
    private GuiScrollBarFaction scrollBarOnline;
    private GuiScrollBarFaction scrollBarOffline;
    private GuiButton joinButton;
    private GuiButton leaveButton;
    private GuiButton tpHomeButton;
    private GuiButton buyButton;
    private GuiButton empireButton;
    public MinimapRenderer minimapRenderer = new MinimapRenderer(6, 6);
    private EntityOtherPlayerMP leaderEntity = null;
    public static boolean mapLoaded = false;
    boolean relationExpanded = false;
    private GuiScrollBarFaction scrollBarRelations;
    protected String hoveredRelation = "";
    private ArrayList<HashMap<String, HashMap<String, String>>> countries = new ArrayList();
    private DynamicTexture flagTexture;

    public FactionGui_OLD(String targetName)
    {
        initTabs();
        this.targetName = targetName;
        mapLoaded = false;
        loaded = false;
        factionInfos = null;

        if (!achievementDone)
        {
            achievementDone = true;
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new IncrementObjectivePacket("player_open_country", 1)));
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
        this.scrollBarOnline = new GuiScrollBarFaction((float)(this.guiLeft + 248), (float)(this.guiTop + 145), 80);
        this.scrollBarOffline = new GuiScrollBarFaction((float)(this.guiLeft + 377), (float)(this.guiTop + 145), 80);
        this.scrollBarRelations = new GuiScrollBarFaction((float)(this.guiLeft + 105), (float)(this.guiTop + 83), 50);

        if (factionInfos != null && factionInfos.size() != 0 && factionInfos.get("name").equals(this.targetName))
        {
            loaded = true;
            mapLoaded = false;
        }
        else
        {
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionMainDataPacket(this.targetName, false)));
        }

        playerTooltip = new HashMap();
        this.leaderEntity = null;
        this.joinButton = new GuiButton(0, this.guiLeft + 10, this.guiTop + 165, 100, 20, I18n.getString("faction.home.button.join"));
        this.tpHomeButton = new GuiButton(1, this.guiLeft + 10, this.guiTop + 165, 100, 20, I18n.getString("faction.home.button.tphome"));
        this.leaveButton = new TexturedCenteredButtonGUI(2, this.guiLeft + 10, this.guiTop + 220, 100, 20, "faction_btn", 0, 0, I18n.getString("faction.home.button.leave"));
        this.buyButton = new TexturedCenteredButtonGUI(3, this.guiLeft + 10, this.guiTop + 190, 100, 20, "faction_btn", 0, 0, I18n.getString("faction.home.button.buy"));

        if (factionInfos != null && ((Boolean)factionInfos.get("isLeader")).booleanValue())
        {
            if (((String)factionInfos.get("name")).contains("Empire"))
            {
                this.empireButton = new GuiButton(4, this.guiLeft + 10, this.guiTop + 220, 100, 20, I18n.getString("faction.home.button.empire_down"));
            }
            else
            {
                this.empireButton = new GuiButton(4, this.guiLeft + 10, this.guiTop + 220, 100, 20, I18n.getString("faction.home.button.empire_up"));

                if (Integer.parseInt((String)factionInfos.get("level")) < 60)
                {
                    this.empireButton.enabled = false;
                }
            }
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float par3)
    {
        this.hoveredAction = "";
        Object toolTipLines = new ArrayList();

        if (this.leaderEntity == null && loaded && factionInfos.size() > 0 || loaded && factionInfos.size() > 0 && this.leaderEntity != null && !this.leaderEntity.getDisplayName().equals(factionInfos.get("leader")))
        {
            try
            {
                this.leaderEntity = new EntityOtherPlayerMP(this.mc.theWorld, (String)factionInfos.get("leader"));
            }
            catch (Exception var21)
            {
                this.leaderEntity = null;
            }
        }

        if (loaded && factionInfos != null && this.flagTexture == null && factionInfos != null && factionInfos.get("flagImage") != null && !((String)factionInfos.get("flagImage")).isEmpty())
        {
            BufferedImage possibleRelations = decodeToImage((String)factionInfos.get("flagImage"));
            this.flagTexture = new DynamicTexture(possibleRelations);
        }

        this.drawDefaultBackground();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        ClientEventHandler.STYLE.bindTexture("faction_main");
        ModernGui.drawModalRectWithCustomSizedTexture((float)this.guiLeft, (float)this.guiTop, 0, 0, this.xSize, this.ySize, 512.0F, 512.0F, false);

        if (loaded && factionInfos != null && factionInfos.size() > 0 && !((String)factionInfos.get("name")).contains("Wilderness") && !((String)factionInfos.get("name")).contains("SafeZone") && !((String)factionInfos.get("name")).contains("WarZone"))
        {
            if (!mapLoaded)
            {
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new MinimapRequestPacket(Integer.parseInt(((String)factionInfos.get("home")).split(",")[0]), Integer.parseInt(((String)factionInfos.get("home")).split(",")[1]), 6, 6)));
                mapLoaded = true;
            }

            if (this.empireButton == null && ((Boolean)factionInfos.get("isLeader")).booleanValue())
            {
                if (((String)factionInfos.get("name")).contains("Empire"))
                {
                    this.empireButton = new GuiButton(4, this.guiLeft + 10, this.guiTop + 220, 100, 20, I18n.getString("faction.home.button.empire_down"));
                }
                else
                {
                    this.empireButton = new GuiButton(4, this.guiLeft + 10, this.guiTop + 220, 100, 20, I18n.getString("faction.home.button.empire_up"));

                    if (Integer.parseInt((String)factionInfos.get("level")) < 60)
                    {
                        this.empireButton.enabled = false;
                    }
                }
            }

            if (this.empireButton != null)
            {
                this.empireButton.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);

                if (!this.empireButton.enabled && mouseX >= this.guiLeft + 10 && mouseX <= this.guiLeft + 10 + 100 && mouseY >= this.guiTop + 220 && mouseY <= this.guiTop + 220 + 20)
                {
                    toolTipLines = Arrays.asList(new String[] {I18n.getString("faction.home.tooltip.empire_under_level")});
                }
            }

            ClientEventHandler.STYLE.bindTexture("faction_main");

            if (mouseX > this.guiLeft + 385 && mouseX < this.guiLeft + 385 + 9 && mouseY > this.guiTop - 6 && mouseY < this.guiTop - 6 + 10)
            {
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 385), (float)(this.guiTop - 6), 138, 261, 9, 10, 512.0F, 512.0F, false);
            }
            else
            {
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 385), (float)(this.guiTop - 6), 138, 251, 9, 10, 512.0F, 512.0F, false);
            }

            if (!((Boolean)factionInfos.get("isInCountry")).booleanValue() && ((Boolean)factionInfos.get("playerHasFaction")).booleanValue())
            {
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 8), (float)(this.guiTop + 250), 187, 251, 104, 19, 512.0F, 512.0F, false);
                this.drawScaledString(I18n.getString("faction.home.button.own_country"), this.guiLeft + 31, this.guiTop + 254, 16777215, 1.0F, false, false);
            }

            this.drawScaledString(I18n.getString("faction.tab.home"), this.guiLeft + 131, this.guiTop + 16, 1644825, 1.4F, false, false);

            if (((String)factionInfos.get("name")).length() <= 9)
            {
                this.drawScaledString((String)factionInfos.get("name"), this.guiLeft + 60, this.guiTop + 25, 16777215, 1.8F, true, true);
            }
            else
            {
                this.drawScaledString((String)factionInfos.get("name"), this.guiLeft + 60, this.guiTop + 25, 16777215, 1.0F, true, true);
            }

            this.drawScaledString(I18n.getString("faction.common.age_1") + " " + factionInfos.get("age") + " " + I18n.getString("faction.common.age_2"), this.guiLeft + 60, this.guiTop + 43, 11842740, 0.65F, true, false);

            if (hasPermissions("relations") && !((Boolean)factionInfos.get("isInCountry")).booleanValue())
            {
                this.drawScaledString(I18n.getString("faction.common." + factionInfos.get("actualRelation")), this.guiLeft + 17, this.guiTop + 66, 1644825, 1.0F, false, false);
                ClientEventHandler.STYLE.bindTexture("faction_main");
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 91), (float)(this.guiTop + 59), 202, 362, 19, 20, 512.0F, 512.0F, false);
            }
            else
            {
                this.drawScaledString(I18n.getString("faction.common." + factionInfos.get("actualRelation")), this.guiLeft + 58, this.guiTop + 66, 1644825, 1.0F, true, false);
            }

            List var22 = (List)factionInfos.get("possibleRelations");
            this.hoveredRelation = "";

            if (hasPermissions("relations") && this.relationExpanded && var22.size() > 0 && !((Boolean)factionInfos.get("isInCountry")).booleanValue())
            {
                ClientEventHandler.STYLE.bindTexture("faction_main");
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 10), (float)(this.guiTop + 78), 100, 362, 100, 59, 512.0F, 512.0F, false);
                GUIUtils.startGLScissor(this.guiLeft + 11, this.guiTop + 79, 94, 57);

                if (var22.size() > 0)
                {
                    for (int descriptionWords = 0; descriptionWords < var22.size(); ++descriptionWords)
                    {
                        int line = this.guiLeft + 11;
                        Float lineNumber = Float.valueOf((float)(this.guiTop + 79 + descriptionWords * 20) + this.getSlideRelations());
                        ClientEventHandler.STYLE.bindTexture("faction_main");

                        if (mouseX >= line && mouseX <= line + 94 && (float)mouseY >= lineNumber.floatValue() && (float)mouseY <= lineNumber.floatValue() + 20.0F)
                        {
                            ModernGui.drawModalRectWithCustomSizedTexture((float)line, (float)lineNumber.intValue(), 100, 422, 94, 20, 512.0F, 512.0F, false);
                            this.hoveredRelation = (String)var22.get(descriptionWords);
                        }
                        else
                        {
                            ModernGui.drawModalRectWithCustomSizedTexture((float)line, (float)lineNumber.intValue(), 101, 363, 94, 20, 512.0F, 512.0F, false);
                        }

                        this.drawScaledString(I18n.getString("faction.common." + (String)var22.get(descriptionWords)), line + 6, lineNumber.intValue() + 5, 16777215, 1.0F, false, false);
                    }
                }

                GUIUtils.endGLScissor();
                this.scrollBarRelations.draw(mouseX, mouseY);
            }

            if (((Boolean)factionInfos.get("isOpen")).booleanValue() && !((Boolean)factionInfos.get("playerHasFaction")).booleanValue() && !((Boolean)factionInfos.get("isLeaderInHisCountry")).booleanValue())
            {
                this.joinButton.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);
            }
            else if (((Boolean)factionInfos.get("isInvited")).booleanValue() && !((Boolean)factionInfos.get("isInCountry")).booleanValue() && !((Boolean)factionInfos.get("isLeaderInHisCountry")).booleanValue())
            {
                this.joinButton.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);
            }
            else if (((Boolean)factionInfos.get("isInCountry")).booleanValue() && !((Boolean)factionInfos.get("isLeader")).booleanValue())
            {
                this.leaveButton.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);
            }

            if (!((Boolean)factionInfos.get("isLeader")).booleanValue() && !((Boolean)factionInfos.get("isLeaderInHisCountry")).booleanValue() && ((Boolean)factionInfos.get("isForSale")).booleanValue())
            {
                this.buyButton.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);
            }

            if ((((Boolean)factionInfos.get("isInCountry")).booleanValue() || factionInfos.containsKey("isOp") && ((Boolean)factionInfos.get("isOp")).booleanValue()) && ((Boolean)factionInfos.get("hasHome")).booleanValue())
            {
                this.tpHomeButton.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);
            }

            String[] var23 = ((String)factionInfos.get("description")).replaceAll("\u00a7[0-9a-z]{1}", "").split(" ");
            String var24 = "";
            int var25 = 0;
            String[] negativeOffsetX = var23;
            int infosLength = var23.length;

            for (int tooltipToDraw = 0; tooltipToDraw < infosLength; ++tooltipToDraw)
            {
                String countOfflinePlayers = negativeOffsetX[tooltipToDraw];

                if (this.fontRenderer.getStringWidth(var24 + countOfflinePlayers) <= 150)
                {
                    if (!var24.equals(""))
                    {
                        var24 = var24 + " ";
                    }

                    var24 = var24 + countOfflinePlayers;
                }
                else
                {
                    if (var25 == 0)
                    {
                        var24 = "\u00a7o\"" + var24;
                    }

                    this.drawScaledString(var24, this.guiLeft + 220, this.guiTop + 55 + var25 * 10, 16777215, 1.0F, true, true);
                    ++var25;
                    var24 = countOfflinePlayers;
                }
            }

            if (var25 == 0)
            {
                var24 = "\u00a7o\"" + var24;
            }

            this.drawScaledString(var24 + "\"", this.guiLeft + 220, this.guiTop + 55 + var25 * 10, 16777215, 1.0F, true, true);

            if (this.leaderEntity != null)
            {
                GUIUtils.startGLScissor(this.guiLeft + 290, this.guiTop + 13, 105, 86);
                GuiInventory.func_110423_a(this.guiLeft + 350, this.guiTop + 130, 60, 0.0F, 0.0F, this.leaderEntity);
                GUIUtils.endGLScissor();
                ClientEventHandler.STYLE.bindTexture("faction_main");
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glDisable(GL11.GL_DEPTH_TEST);
                GL11.glDepthMask(false);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glDisable(GL11.GL_ALPHA_TEST);
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 288), (float)(this.guiTop + 20), 182, 280, 39, 80, 512.0F, 512.0F, false);
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glEnable(GL11.GL_DEPTH_TEST);
                GL11.glDepthMask(true);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glEnable(GL11.GL_ALPHA_TEST);

                if (this.flagTexture != null)
                {
                    GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.flagTexture.getGlTextureId());
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 290), (float)(this.guiTop + 22), 0.0F, 0.0F, 156, 78, 35, 20, 156.0F, 78.0F, false);
                }
            }

            ClientEventHandler.STYLE.bindTexture("faction_main");
            int var26 = 0;

            if (Integer.parseInt((String)factionInfos.get("age")) < 2)
            {
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 105), (float)(this.guiTop + 50), 149, 263, 10, 10, 512.0F, 512.0F, false);

                if (mouseX >= this.guiLeft + 105 && mouseX <= this.guiLeft + 105 + 10 && mouseY >= this.guiTop + 50 && mouseY <= this.guiTop + 50 + 10)
                {
                    this.drawHoveringText(Arrays.asList(new String[] {I18n.getString("faction.common.badge.young")}), mouseX, mouseY, this.fontRenderer);
                }

                var26 += 12;
            }

            if (((Boolean)factionInfos.get("isEmpire")).booleanValue())
            {
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 105 - var26), (float)(this.guiTop + 50), 160, 251, 10, 10, 512.0F, 512.0F, false);

                if (mouseX >= this.guiLeft + 105 - var26 && mouseX <= this.guiLeft + 105 - var26 + 10 && mouseY >= this.guiTop + 50 && mouseY <= this.guiTop + 50 + 10)
                {
                    this.drawHoveringText(Arrays.asList(new String[] {I18n.getString("faction.common.badge.empire")}), mouseX, mouseY, this.fontRenderer);
                }

                var26 += 12;
            }
            else if (!((String)factionInfos.get("isColony")).isEmpty())
            {
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 105 - var26), (float)(this.guiTop + 50), 172, 251, 10, 10, 512.0F, 512.0F, false);

                if (mouseX >= this.guiLeft + 105 - var26 && mouseX <= this.guiLeft + 105 - var26 + 10 && mouseY >= this.guiTop + 50 && mouseY <= this.guiTop + 50 + 10)
                {
                    if (!((Boolean)factionInfos.get("isInCountry")).booleanValue() && !factionInfos.get("playerWhoSeeFactionId").equals(factionInfos.get("colonizedByFactionId")))
                    {
                        this.drawHoveringText(Arrays.asList(new String[] {I18n.getString("faction.common.badge.colony") + " " + factionInfos.get("isColony")}), mouseX, mouseY, this.fontRenderer);
                    }
                    else
                    {
                        this.drawHoveringText(Arrays.asList(new String[] {I18n.getString("faction.common.badge.colony") + " " + factionInfos.get("isColony"), I18n.getString("faction.common.badge.colony_tax") + " " + factionInfos.get("colonyTax") + "%"}), mouseX, mouseY, this.fontRenderer);
                    }
                }

                var26 += 12;
            }

            if (((Boolean)factionInfos.get("isTopWarzone")).booleanValue())
            {
                ClientEventHandler.STYLE.bindTexture("faction_main");
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 105 - var26), (float)(this.guiTop + 50), 173, 263, 10, 10, 512.0F, 512.0F, false);

                if (mouseX >= this.guiLeft + 105 - var26 && mouseX <= this.guiLeft + 105 - var26 + 10 && mouseY >= this.guiTop + 50 && mouseY <= this.guiTop + 50 + 10)
                {
                    this.drawHoveringText(Arrays.asList(new String[] {I18n.getString("faction.common.badge.warzone")}), mouseX, mouseY, this.fontRenderer);
                }
            }

            ClientEventHandler.STYLE.bindTexture("faction_main");
            this.drawScaledString(I18n.getString("faction.home.infos_level") + " : " + factionInfos.get("level") + "  " + I18n.getString("faction.home.infos_mmr") + " : " + factionInfos.get("mmr") + "  " + I18n.getString("faction.home.infos_power") + " : " + factionInfos.get("power") + "/" + factionInfos.get("maxpower") + "  " + I18n.getString("faction.home.infos_claims") + " : " + factionInfos.get("claims"), this.guiLeft + 257, this.guiTop + 114, 11842740, 0.8F, true, false);
            Double var27 = Double.valueOf((double)this.fontRenderer.getStringWidth(I18n.getString("faction.home.infos_level") + " : " + factionInfos.get("level") + "  " + I18n.getString("faction.home.infos_mmr") + " : " + factionInfos.get("mmr") + "  " + I18n.getString("faction.home.infos_power") + " : " + factionInfos.get("power") + "/" + factionInfos.get("maxpower") + "  " + I18n.getString("faction.home.infos_claims") + " : " + factionInfos.get("claims")) * 0.8D);

            if ((double)mouseX >= (double)(this.guiLeft + 257) - var27.doubleValue() / 2.0D && (double)mouseX <= (double)(this.guiLeft + 257) - var27.doubleValue() / 2.0D + 40.0D && mouseY >= this.guiTop + 112 && mouseY <= this.guiTop + 120)
            {
                this.hoveredAction = "level";
            }

            int x;

            if (Integer.parseInt((String)factionInfos.get("powerboost")) != 0)
            {
                Double var28 = Double.valueOf((double)this.fontRenderer.getStringWidth(I18n.getString("faction.home.infos_level") + " : " + factionInfos.get("level") + "  " + I18n.getString("faction.home.infos_mmr") + " : " + factionInfos.get("mmr") + "  ") * 0.8D);

                if ((double)mouseX >= (double)(this.guiLeft + 257) - var27.doubleValue() / 2.0D + var28.doubleValue() && (double)mouseX <= (double)(this.guiLeft + 257) - var27.doubleValue() / 2.0D + var28.doubleValue() + 65.0D && mouseY >= this.guiTop + 112 && mouseY <= this.guiTop + 120)
                {
                    ArrayList var30 = new ArrayList();

                    if (Integer.parseInt((String)factionInfos.get("powerboost_fixed")) != 0)
                    {
                        var30.add("\u00a77Powerboost: \u00a7f" + factionInfos.get("powerboost_fixed"));
                    }

                    if (Integer.parseInt((String)factionInfos.get("powerboost_real")) > 0)
                    {
                        var30.add("\u00a77Powerboost Warzone: \u00a7f" + factionInfos.get("powerboost_real"));
                    }

                    if (Integer.parseInt((String)factionInfos.get("powerboost_unesco")) > 0)
                    {
                        var30.add("\u00a77Powerboost Unesco: \u00a7f" + factionInfos.get("powerboost_unesco"));
                    }

                    if (!((String)factionInfos.get("powerboost_others")).isEmpty())
                    {
                        String[] i = ((String)factionInfos.get("powerboost_others")).split(",");
                        int type = i.length;

                        for (x = 0; x < type; ++x)
                        {
                            String y = i[x];

                            if (y.split("#")[1].contains("-"))
                            {
                                var30.add("\u00a7c" + I18n.getString("faction.home.tooltip.war_against") + " " + y.split("#")[0] + ": \u00a74" + y.split("#")[1]);
                            }
                            else
                            {
                                var30.add("\u00a7a" + I18n.getString("faction.home.tooltip.war_against") + " " + y.split("#")[0] + ": \u00a72" + y.split("#")[1]);
                            }
                        }
                    }

                    toolTipLines = var30;
                }
            }

            String var29 = "";
            this.hoveredPlayer = "";
            this.drawScaledString(I18n.getString("faction.home.players_online") + " (" + ((ArrayList)factionInfos.get("players_online")).size() + ")", this.guiLeft + 131, this.guiTop + 133, 1644825, 0.85F, false, false);
            int var31;
            String var33;

            if (((ArrayList)factionInfos.get("players_online")).size() > 0)
            {
                GUIUtils.startGLScissor(this.guiLeft + 132, this.guiTop + 142, 116, 86);

                for (var31 = 0; var31 < ((ArrayList)factionInfos.get("players_online")).size(); ++var31)
                {
                    String var32 = ((String)((ArrayList)factionInfos.get("players_online")).get(var31)).split("#")[1];
                    var33 = "";

                    if (var32.split(" ").length > 1)
                    {
                        var33 = var32.split(" ")[0];
                        var32 = var32.split(" ")[1];
                    }

                    x = this.guiLeft + 132;
                    Float var36 = Float.valueOf((float)(this.guiTop + 142 + var31 * 20) + this.getSlideOnline());

                    if (mouseX > x && mouseX < x + 116 && (float)mouseY > var36.floatValue() && (float)mouseY < var36.floatValue() + 20.0F)
                    {
                        this.hoveredPlayer = var32;
                    }

                    ClientEventHandler.STYLE.bindTexture("faction_main");
                    ModernGui.drawModalRectWithCustomSizedTexture((float)x, (float)var36.intValue(), 132, 142, 116, 20, 512.0F, 512.0F, false);

                    try
                    {
                        ResourceLocation offsetY = AbstractClientPlayer.locationStevePng;
                        offsetY = AbstractClientPlayer.getLocationSkin(var32);
                        AbstractClientPlayer.getDownloadImageSkin(offsetY, var32);
                        Minecraft.getMinecraft().renderEngine.bindTexture(offsetY);
                        this.mc.getTextureManager().bindTexture(offsetY);
                        GUIUtils.drawScaledCustomSizeModalRect(x + 6 + 10, var36.intValue() + 4 + 10, 8.0F, 16.0F, 8, -8, -10, -10, 64.0F, 64.0F);
                        GUIUtils.drawScaledCustomSizeModalRect(x + 6 + 10, var36.intValue() + 4 + 10, 40.0F, 16.0F, 8, -8, -10, -10, 64.0F, 64.0F);
                    }
                    catch (Exception var20)
                    {
                        System.out.println(var20.getMessage());
                    }

                    this.drawScaledString(var33 + " " + var32, x + 20, var36.intValue() + 6, 11842740, 0.8F, false, true);
                    ClientEventHandler.STYLE.bindTexture("faction_main");
                    ModernGui.drawModalRectWithCustomSizedTexture((float)(x + 104), (float)(var36.intValue() + 5), 148, 250, 10, 11, 512.0F, 512.0F, false);

                    if (mouseX >= x + 104 && mouseX <= x + 104 + 10 && (float)mouseY >= var36.floatValue() + 5.0F && (float)mouseY <= var36.floatValue() + 5.0F + 11.0F)
                    {
                        if (playerTooltip.containsKey(var32))
                        {
                            var29 = var32;
                        }
                        else
                        {
                            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionPlayerDataPacket(var32)));
                            playerTooltip.put(var32, (Object)null);
                        }
                    }
                }

                GUIUtils.endGLScissor();
            }

            var31 = ((ArrayList)factionInfos.get("players_offline")).size();

            if (factionInfos.containsKey("count_players_offline"))
            {
                var31 = ((Double)factionInfos.get("count_players_offline")).intValue();
            }

            this.drawScaledString(I18n.getString("faction.home.players_offline") + " (" + var31 + ")", this.guiLeft + 260, this.guiTop + 133, 1644825, 0.85F, false, false);
            int var34;
            int var37;

            if (((ArrayList)factionInfos.get("players_offline")).size() > 0)
            {
                GUIUtils.startGLScissor(this.guiLeft + 261, this.guiTop + 142, 116, 86);

                for (var34 = 0; var34 < ((ArrayList)factionInfos.get("players_offline")).size(); ++var34)
                {
                    var33 = ((String)((ArrayList)factionInfos.get("players_offline")).get(var34)).split("#")[1];
                    String var35 = "";

                    if (var33.split(" ").length > 1)
                    {
                        var35 = var33.split(" ")[0];
                        var33 = var33.split(" ")[1];
                    }

                    var37 = this.guiLeft + 261;
                    Float var39 = Float.valueOf((float)(this.guiTop + 142 + var34 * 20) + this.getSlideOffline());

                    if (mouseX > var37 && mouseX < var37 + 116 && (float)mouseY > var39.floatValue() && (float)mouseY < var39.floatValue() + 20.0F)
                    {
                        this.hoveredPlayer = var33;
                    }

                    ClientEventHandler.STYLE.bindTexture("faction_main");
                    ModernGui.drawModalRectWithCustomSizedTexture((float)var37, (float)var39.intValue(), 261, 142, 116, 20, 512.0F, 512.0F, false);

                    try
                    {
                        ResourceLocation resourceLocation = AbstractClientPlayer.locationStevePng;
                        resourceLocation = AbstractClientPlayer.getLocationSkin(var33);
                        AbstractClientPlayer.getDownloadImageSkin(resourceLocation, var33);
                        Minecraft.getMinecraft().renderEngine.bindTexture(resourceLocation);
                        this.mc.getTextureManager().bindTexture(resourceLocation);
                        GUIUtils.drawScaledCustomSizeModalRect(var37 + 6 + 10, var39.intValue() + 4 + 10, 8.0F, 16.0F, 8, -8, -10, -10, 64.0F, 64.0F);
                        GUIUtils.drawScaledCustomSizeModalRect(var37 + 6 + 10, var39.intValue() + 4 + 10, 40.0F, 16.0F, 8, -8, -10, -10, 64.0F, 64.0F);
                    }
                    catch (Exception var19)
                    {
                        ;
                    }

                    this.drawScaledString(var35 + " " + var33, var37 + 20, var39.intValue() + 6, 11842740, 0.8F, false, true);
                    ClientEventHandler.STYLE.bindTexture("faction_main");
                    ModernGui.drawModalRectWithCustomSizedTexture((float)(var37 + 104), (float)(var39.intValue() + 5), 148, 250, 10, 11, 512.0F, 512.0F, false);

                    if (mouseX >= var37 + 104 && mouseX <= var37 + 104 + 10 && (float)mouseY >= var39.floatValue() + 5.0F && (float)mouseY <= var39.floatValue() + 5.0F + 11.0F)
                    {
                        if (playerTooltip.containsKey(var33))
                        {
                            var29 = var33;
                        }
                        else
                        {
                            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionPlayerDataPacket(var33)));
                            playerTooltip.put(var33, (Object)null);
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

            ClientEventHandler.STYLE.bindTexture("faction_main");
            GuiScreenTab var38;

            for (var34 = 0; var34 < TABS.size(); ++var34)
            {
                var38 = (GuiScreenTab)TABS.get(var34);

                if (var38.getClassReferent().equals(SettingsGUI_OLD.class))
                {
                    ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 400), (float)(this.guiTop + 200), 114, 251, 23, 30, 512.0F, 512.0F, false);
                    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                    GL11.glEnable(GL11.GL_BLEND);
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.75F);
                    ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 400 + 2), (float)(this.guiTop + 200 + 4), 40, 321, 20, 20, 512.0F, 512.0F, false);
                    GL11.glDisable(GL11.GL_BLEND);
                }
                else
                {
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    x = var34 % 5;
                    var37 = var34 / 5;

                    if (this.getClass() == var38.getClassReferent())
                    {
                        ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23), (float)(this.guiTop + 20 + var34 * 31), 23, 251, 29, 31, 512.0F, 512.0F, false);
                        ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23 + 4), (float)(this.guiTop + 20 + var34 * 31 + 5), x * 20, 301 + var37 * 20, 20, 20, 512.0F, 512.0F, false);
                    }
                    else
                    {
                        ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23), (float)(this.guiTop + 20 + var34 * 31), 0, 251, 23, 31, 512.0F, 512.0F, false);
                        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                        GL11.glEnable(GL11.GL_BLEND);
                        GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.75F);
                        ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23 + 4), (float)(this.guiTop + 20 + var34 * 31 + 5), x * 20, 301 + var37 * 20, 20, 20, 512.0F, 512.0F, false);
                        GL11.glDisable(GL11.GL_BLEND);
                    }
                }
            }

            if (mapLoaded && !this.relationExpanded)
            {
                GUIUtils.startGLScissor(this.guiLeft + 11, this.guiTop + 83, 180, 78);
                GL11.glDisable(GL11.GL_DEPTH_TEST);
                this.minimapRenderer.renderMap(this.guiLeft + 12, this.guiTop + 74, mouseX, mouseY, true);
                GL11.glEnable(GL11.GL_DEPTH_TEST);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                ClientEventHandler.STYLE.bindTexture("faction_main");
                ModernGui.drawModalRectWithCustomSizedTextureWithTransparency((float)(this.guiLeft + 11), (float)(this.guiTop + 83), 0, 362, 98, 78, 512.0F, 512.0F, false);
                GUIUtils.endGLScissor();
            }

            for (var34 = 0; var34 < TABS.size(); ++var34)
            {
                var38 = (GuiScreenTab)TABS.get(var34);

                if (!var38.getClassReferent().equals(SettingsGUI_OLD.class))
                {
                    if (mouseX >= this.guiLeft - 23 && mouseX <= this.guiLeft - 23 + 29 && mouseY >= this.guiTop + 20 + var34 * 31 && mouseY <= this.guiTop + 20 + 30 + var34 * 31)
                    {
                        this.drawHoveringText(Arrays.asList(new String[] {I18n.getString("faction.tab." + var38.getClassReferent().getSimpleName())}), mouseX, mouseY, this.fontRenderer);
                    }
                }
                else if (mouseX >= this.guiLeft + 400 && mouseX <= this.guiLeft + 400 + 23 && mouseY >= this.guiTop + 200 && mouseY <= this.guiTop + 200 + 30)
                {
                    this.drawHoveringText(Arrays.asList(new String[] {I18n.getString("faction.tab." + var38.getClassReferent().getSimpleName())}), mouseX, mouseY, this.fontRenderer);
                }
            }

            if (!((List)toolTipLines).isEmpty())
            {
                this.drawHoveringText((List)toolTipLines, mouseX, mouseY, this.fontRenderer);
            }

            if (!var29.isEmpty())
            {
                this.drawTooltip(var29, mouseX, mouseY);
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

    private void drawTooltip(String playerName, int mouseX, int mouseY)
    {
        int var10000 = mouseX - this.guiLeft;
        var10000 = mouseY - this.guiTop;
        ArrayList tooltipLines = this.getPlayerTooltip(playerName);
        this.drawHoveringText(tooltipLines, mouseX, mouseY, this.fontRenderer);
    }

    public ArrayList<String> getPlayerTooltip(String playerName)
    {
        HashMap playerInfo = (HashMap)playerTooltip.get(playerName);
        ArrayList tooltipLines = new ArrayList();

        if (playerInfo != null)
        {
            if (!((String)playerInfo.get("title")).isEmpty() && !((String)playerInfo.get("title")).contains("no title set"))
            {
                tooltipLines.add((String)playerInfo.get("title"));
            }

            tooltipLines.add("\u00a7f" + I18n.getString("faction.home.tooltip.member_since_1") + " " + playerInfo.get("member_days") + " " + I18n.getString("faction.home.tooltip.member_since_2"));
            tooltipLines.add("\u00a77Power : " + playerInfo.get("power") + "/" + playerInfo.get("maxpower"));
            tooltipLines.add("\u00a78" + I18n.getString("faction.home.tooltip.salary") + " : " + playerInfo.get("salary") + "$ | " + I18n.getString("faction.home.tooltip.tax") + " : " + playerInfo.get("tax") + "$");

            if (playerInfo.containsKey("valid_playtime") && Long.parseLong((String)playerInfo.get("valid_playtime")) < 90000L)
            {
                tooltipLines.add("\u00a7b\u2605 " + I18n.getString("faction.home.tooltip.playtime"));
            }

            tooltipLines.add("\u00a78----------------------");

            if (playerInfo.get("offline_days").equals("online"))
            {
                tooltipLines.add("\u00a7a" + I18n.getString("faction.home.tooltip.online"));
            }
            else
            {
                tooltipLines.add("\u00a77" + I18n.getString("faction.home.tooltip.offline_since_1") + " " + playerInfo.get("offline_days") + " " + I18n.getString("faction.home.tooltip.offline_since_2"));
            }
        }

        return tooltipLines;
    }

    private float getSlideOnline()
    {
        return ((ArrayList)factionInfos.get("players_online")).size() > 4 ? (float)(-(((ArrayList)factionInfos.get("players_online")).size() - 4) * 20) * this.scrollBarOnline.getSliderValue() : 0.0F;
    }

    private float getSlideOffline()
    {
        return ((ArrayList)factionInfos.get("players_offline")).size() > 4 ? (float)(-(((ArrayList)factionInfos.get("players_offline")).size() - 4) * 20) * this.scrollBarOffline.getSliderValue() : 0.0F;
    }

    private float getSlideRelations()
    {
        return ((List)factionInfos.get("possibleRelations")).size() > 3 ? (float)(-(((List)factionInfos.get("possibleRelations")).size() - 3) * 20) * this.scrollBarRelations.getSliderValue() : 0.0F;
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

                if (!type.getClassReferent().equals(SettingsGUI_OLD.class))
                {
                    if (mouseX >= this.guiLeft - 20 && mouseX <= this.guiLeft + 3 && mouseY >= this.guiTop + 20 + i * 31 && mouseY <= this.guiTop + 50 + i * 31)
                    {
                        this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);

                        if (this.getClass() != type.getClassReferent())
                        {
                            try
                            {
                                type.call();
                            }
                            catch (Exception var8)
                            {
                                var8.printStackTrace();
                            }
                        }
                    }
                }
                else if (mouseX >= this.guiLeft + 400 && mouseX <= this.guiLeft + 400 + 23 && mouseY >= this.guiTop + 200 && mouseY <= this.guiTop + 200 + 30)
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
            else if (!this.hoveredPlayer.isEmpty() && mouseX > this.guiLeft + 130 && mouseX < this.guiLeft + 130 + 254 && mouseY > this.guiTop + 140 && mouseY < this.guiTop + 140 + 90)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                Minecraft.getMinecraft().displayGuiScreen(new ProfilGui(this.hoveredPlayer, ""));
            }

            if (factionInfos != null && (((Boolean)factionInfos.get("isInCountry")).booleanValue() || factionInfos.containsKey("isOp") && ((Boolean)factionInfos.get("isOp")).booleanValue()) && mouseX >= this.guiLeft + 10 && mouseX <= this.guiLeft + 10 + 100 && mouseY >= this.guiTop + 165 && mouseY <= this.guiTop + 165 + 20)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionMainTPPacket((String)factionInfos.get("name"))));
                Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
            }
            else if (factionInfos != null && ((Boolean)factionInfos.get("isOpen")).booleanValue() && !((Boolean)factionInfos.get("playerHasFaction")).booleanValue() && !((Boolean)factionInfos.get("isLeaderInHisCountry")).booleanValue() && mouseX >= this.guiLeft + 10 && mouseX <= this.guiLeft + 10 + 100 && mouseY >= this.guiTop + 165 && mouseY <= this.guiTop + 165 + 20)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionMainJoinPacket((String)factionInfos.get("name"))));
            }
            else if (factionInfos != null && ((Boolean)factionInfos.get("isInvited")).booleanValue() && !((Boolean)factionInfos.get("isInCountry")).booleanValue() && !((Boolean)factionInfos.get("isLeaderInHisCountry")).booleanValue() && mouseX >= this.guiLeft + 10 && mouseX <= this.guiLeft + 10 + 100 && mouseY >= this.guiTop + 165 && mouseY <= this.guiTop + 165 + 20)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionMainJoinPacket((String)factionInfos.get("name"))));
            }
            else if (factionInfos != null && ((Boolean)factionInfos.get("isInCountry")).booleanValue() && !((Boolean)factionInfos.get("isLeader")).booleanValue() && mouseX >= this.guiLeft + 10 && mouseX <= this.guiLeft + 10 + 100 && mouseY >= this.guiTop + 220 && mouseY <= this.guiTop + 220 + 20)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                Minecraft.getMinecraft().displayGuiScreen(new LeaveConfirmGui(this));
            }
            else if (factionInfos != null && ((Boolean)factionInfos.get("isLeader")).booleanValue() && this.empireButton != null && this.empireButton.enabled && mouseX >= this.guiLeft + 10 && mouseX <= this.guiLeft + 10 + 100 && mouseY >= this.guiTop + 220 && mouseY <= this.guiTop + 220 + 20)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                Minecraft.getMinecraft().displayGuiScreen(new EmpireConfirmGui(this));
            }
            else if (factionInfos != null && ((Boolean)factionInfos.get("isForSale")).booleanValue() && !((Boolean)factionInfos.get("isLeaderInHisCountry")).booleanValue() && !((Boolean)factionInfos.get("isLeader")).booleanValue() && mouseX >= this.guiLeft + 10 && mouseX <= this.guiLeft + 10 + 100 && mouseY >= this.guiTop + 190 && mouseY <= this.guiTop + 190 + 20)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                Minecraft.getMinecraft().displayGuiScreen(new BuyCountryConfirmGui(this));
            }
            else if (factionInfos != null && hasPermissions("relations") && mouseX >= this.guiLeft + 91 && mouseX <= this.guiLeft + 91 + 19 && mouseY >= this.guiTop + 59 && mouseY <= this.guiTop + 59 + 20)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                this.relationExpanded = !this.relationExpanded;
            }
            else if (factionInfos != null && !this.hoveredRelation.isEmpty() && mouseX >= this.guiLeft + 10 && mouseX <= this.guiLeft + 10 + 100 && mouseY >= this.guiTop + 78 && mouseY <= this.guiTop + 78 + 59)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);

                if (this.hoveredRelation.equals("enemy"))
                {
                    Minecraft.getMinecraft().displayGuiScreen(new EnemyConfirmGui(this));
                }
                else if (this.hoveredRelation.equals("colony"))
                {
                    Minecraft.getMinecraft().displayGuiScreen(new ColonyConfirmGui(this));
                }
                else
                {
                    PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionRelationActionPacket((String)factionInfos.get("name"), this.hoveredRelation)));
                }

                this.hoveredRelation = "";
                this.relationExpanded = false;
            }
            else if (factionInfos != null && mouseX >= this.guiLeft + 8 && mouseX <= this.guiLeft + 8 + 104 && mouseY >= this.guiTop + 250 && mouseY <= this.guiTop + 250 + 19)
            {
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new RemoteOpenOwnFactionMainPacket()));
                Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
            }
            else if (!this.hoveredAction.isEmpty() && this.hoveredAction.equals("level"))
            {
                Minecraft.getMinecraft().displayGuiScreen(new LevelGui(this));
            }
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public static boolean hasPermissions(String permName)
    {
        return ((ArrayList)((ArrayList)factionInfos.get("permissions"))).contains(permName + "##true");
    }

    public static void initTabs()
    {
        TABS.clear();
        TABS.add(new FactionGui_OLD$1());
        TABS.add(new FactionGui_OLD$2());
        TABS.add(new FactionGui_OLD$3());
        TABS.add(new FactionGui_OLD$4());
        TABS.add(new FactionGui_OLD$5());
        TABS.add(new FactionGui_OLD$6());
        TABS.add(new FactionGui_OLD$7());
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
}
