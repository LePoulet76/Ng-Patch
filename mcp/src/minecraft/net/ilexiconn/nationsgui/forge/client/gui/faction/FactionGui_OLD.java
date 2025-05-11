/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.AbstractClientPlayer
 *  net.minecraft.client.entity.EntityOtherPlayerMP
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.inventory.GuiInventory
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.renderer.texture.DynamicTexture
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.world.World
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.imageio.ImageIO;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.MinimapRenderer;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.TexturedCenteredButtonGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.ActionsGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.BankGUI_OLD;
import net.ilexiconn.nationsgui.forge.client.gui.faction.BuyCountryConfirmGui;
import net.ilexiconn.nationsgui.forge.client.gui.faction.ColonyConfirmGui;
import net.ilexiconn.nationsgui.forge.client.gui.faction.CountrySkillsGUI_OLD;
import net.ilexiconn.nationsgui.forge.client.gui.faction.DiplomatieGUI_OLD;
import net.ilexiconn.nationsgui.forge.client.gui.faction.EmpireConfirmGui;
import net.ilexiconn.nationsgui.forge.client.gui.faction.EnemyConfirmGui;
import net.ilexiconn.nationsgui.forge.client.gui.faction.LeaveConfirmGui;
import net.ilexiconn.nationsgui.forge.client.gui.faction.LevelGui;
import net.ilexiconn.nationsgui.forge.client.gui.faction.PermGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.ProfilGui;
import net.ilexiconn.nationsgui.forge.client.gui.faction.SettingsGUI_OLD;
import net.ilexiconn.nationsgui.forge.client.gui.faction.WarGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.WarGUI_OLD;
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
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import sun.misc.BASE64Decoder;

@SideOnly(value=Side.CLIENT)
public class FactionGui_OLD
extends GuiScreen {
    public static final List<GuiScreenTab> TABS = new ArrayList<GuiScreenTab>();
    public static HashMap<String, HashMap<String, Object>> playerTooltip;
    public static HashMap<String, Object> factionInfos;
    public static boolean loaded;
    public static boolean achievementDone;
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
    public static boolean mapLoaded;
    boolean relationExpanded = false;
    private GuiScrollBarFaction scrollBarRelations;
    protected String hoveredRelation = "";
    private ArrayList<HashMap<String, HashMap<String, String>>> countries = new ArrayList();
    private DynamicTexture flagTexture;

    public FactionGui_OLD(String targetName) {
        FactionGui_OLD.initTabs();
        this.targetName = targetName;
        mapLoaded = false;
        loaded = false;
        factionInfos = null;
        if (!achievementDone) {
            achievementDone = true;
            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new IncrementObjectivePacket("player_open_country", 1)));
        }
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        this.guiLeft = (this.field_73880_f - this.xSize) / 2;
        this.guiTop = (this.field_73881_g - this.ySize) / 2;
        this.scrollBarOnline = new GuiScrollBarFaction(this.guiLeft + 248, this.guiTop + 145, 80);
        this.scrollBarOffline = new GuiScrollBarFaction(this.guiLeft + 377, this.guiTop + 145, 80);
        this.scrollBarRelations = new GuiScrollBarFaction(this.guiLeft + 105, this.guiTop + 83, 50);
        if (factionInfos == null || factionInfos.size() == 0 || !factionInfos.get("name").equals(this.targetName)) {
            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionMainDataPacket(this.targetName, false)));
        } else {
            loaded = true;
            mapLoaded = false;
        }
        playerTooltip = new HashMap();
        this.leaderEntity = null;
        this.joinButton = new GuiButton(0, this.guiLeft + 10, this.guiTop + 165, 100, 20, I18n.func_135053_a((String)"faction.home.button.join"));
        this.tpHomeButton = new GuiButton(1, this.guiLeft + 10, this.guiTop + 165, 100, 20, I18n.func_135053_a((String)"faction.home.button.tphome"));
        this.leaveButton = new TexturedCenteredButtonGUI(2, this.guiLeft + 10, this.guiTop + 220, 100, 20, "faction_btn", 0, 0, I18n.func_135053_a((String)"faction.home.button.leave"));
        this.buyButton = new TexturedCenteredButtonGUI(3, this.guiLeft + 10, this.guiTop + 190, 100, 20, "faction_btn", 0, 0, I18n.func_135053_a((String)"faction.home.button.buy"));
        if (factionInfos != null && ((Boolean)factionInfos.get("isLeader")).booleanValue()) {
            if (((String)factionInfos.get("name")).contains("Empire")) {
                this.empireButton = new GuiButton(4, this.guiLeft + 10, this.guiTop + 220, 100, 20, I18n.func_135053_a((String)"faction.home.button.empire_down"));
            } else {
                this.empireButton = new GuiButton(4, this.guiLeft + 10, this.guiTop + 220, 100, 20, I18n.func_135053_a((String)"faction.home.button.empire_up"));
                if (Integer.parseInt((String)factionInfos.get("level")) < 60) {
                    this.empireButton.field_73742_g = false;
                }
            }
        }
    }

    public void func_73863_a(int mouseX, int mouseY, float par3) {
        this.hoveredAction = "";
        List<Object> toolTipLines = new ArrayList();
        if (this.leaderEntity == null && loaded && factionInfos.size() > 0 || loaded && factionInfos.size() > 0 && this.leaderEntity != null && !this.leaderEntity.getDisplayName().equals(factionInfos.get("leader"))) {
            try {
                this.leaderEntity = new EntityOtherPlayerMP((World)this.field_73882_e.field_71441_e, (String)factionInfos.get("leader"));
            }
            catch (Exception e) {
                this.leaderEntity = null;
            }
        }
        if (loaded && factionInfos != null && this.flagTexture == null && factionInfos != null && factionInfos.get("flagImage") != null && !((String)factionInfos.get("flagImage")).isEmpty()) {
            BufferedImage image = FactionGui_OLD.decodeToImage((String)factionInfos.get("flagImage"));
            this.flagTexture = new DynamicTexture(image);
        }
        this.func_73873_v_();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        ClientEventHandler.STYLE.bindTexture("faction_main");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512.0f, 512.0f, false);
        if (loaded && factionInfos != null && factionInfos.size() > 0 && !((String)factionInfos.get("name")).contains("Wilderness") && !((String)factionInfos.get("name")).contains("SafeZone") && !((String)factionInfos.get("name")).contains("WarZone")) {
            int i;
            if (!mapLoaded) {
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new MinimapRequestPacket(Integer.parseInt(((String)factionInfos.get("home")).split(",")[0]), Integer.parseInt(((String)factionInfos.get("home")).split(",")[1]), 6, 6)));
                mapLoaded = true;
            }
            if (this.empireButton == null && ((Boolean)factionInfos.get("isLeader")).booleanValue()) {
                if (((String)factionInfos.get("name")).contains("Empire")) {
                    this.empireButton = new GuiButton(4, this.guiLeft + 10, this.guiTop + 220, 100, 20, I18n.func_135053_a((String)"faction.home.button.empire_down"));
                } else {
                    this.empireButton = new GuiButton(4, this.guiLeft + 10, this.guiTop + 220, 100, 20, I18n.func_135053_a((String)"faction.home.button.empire_up"));
                    if (Integer.parseInt((String)factionInfos.get("level")) < 60) {
                        this.empireButton.field_73742_g = false;
                    }
                }
            }
            if (this.empireButton != null) {
                this.empireButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
                if (!this.empireButton.field_73742_g && mouseX >= this.guiLeft + 10 && mouseX <= this.guiLeft + 10 + 100 && mouseY >= this.guiTop + 220 && mouseY <= this.guiTop + 220 + 20) {
                    toolTipLines = Arrays.asList(I18n.func_135053_a((String)"faction.home.tooltip.empire_under_level"));
                }
            }
            ClientEventHandler.STYLE.bindTexture("faction_main");
            if (mouseX > this.guiLeft + 385 && mouseX < this.guiLeft + 385 + 9 && mouseY > this.guiTop - 6 && mouseY < this.guiTop - 6 + 10) {
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 385, this.guiTop - 6, 138, 261, 9, 10, 512.0f, 512.0f, false);
            } else {
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 385, this.guiTop - 6, 138, 251, 9, 10, 512.0f, 512.0f, false);
            }
            if (!((Boolean)factionInfos.get("isInCountry")).booleanValue() && ((Boolean)factionInfos.get("playerHasFaction")).booleanValue()) {
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 8, this.guiTop + 250, 187, 251, 104, 19, 512.0f, 512.0f, false);
                this.drawScaledString(I18n.func_135053_a((String)"faction.home.button.own_country"), this.guiLeft + 31, this.guiTop + 254, 0xFFFFFF, 1.0f, false, false);
            }
            this.drawScaledString(I18n.func_135053_a((String)"faction.tab.home"), this.guiLeft + 131, this.guiTop + 16, 0x191919, 1.4f, false, false);
            if (((String)factionInfos.get("name")).length() <= 9) {
                this.drawScaledString((String)factionInfos.get("name"), this.guiLeft + 60, this.guiTop + 25, 0xFFFFFF, 1.8f, true, true);
            } else {
                this.drawScaledString((String)factionInfos.get("name"), this.guiLeft + 60, this.guiTop + 25, 0xFFFFFF, 1.0f, true, true);
            }
            this.drawScaledString(I18n.func_135053_a((String)"faction.common.age_1") + " " + factionInfos.get("age") + " " + I18n.func_135053_a((String)"faction.common.age_2"), this.guiLeft + 60, this.guiTop + 43, 0xB4B4B4, 0.65f, true, false);
            if (FactionGui_OLD.hasPermissions("relations") && !((Boolean)factionInfos.get("isInCountry")).booleanValue()) {
                this.drawScaledString(I18n.func_135053_a((String)("faction.common." + factionInfos.get("actualRelation"))), this.guiLeft + 17, this.guiTop + 66, 0x191919, 1.0f, false, false);
                ClientEventHandler.STYLE.bindTexture("faction_main");
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 91, this.guiTop + 59, 202, 362, 19, 20, 512.0f, 512.0f, false);
            } else {
                this.drawScaledString(I18n.func_135053_a((String)("faction.common." + factionInfos.get("actualRelation"))), this.guiLeft + 58, this.guiTop + 66, 0x191919, 1.0f, true, false);
            }
            List possibleRelations = (List)factionInfos.get("possibleRelations");
            this.hoveredRelation = "";
            if (FactionGui_OLD.hasPermissions("relations") && this.relationExpanded && possibleRelations.size() > 0 && !((Boolean)factionInfos.get("isInCountry")).booleanValue()) {
                ClientEventHandler.STYLE.bindTexture("faction_main");
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 10, this.guiTop + 78, 100, 362, 100, 59, 512.0f, 512.0f, false);
                GUIUtils.startGLScissor(this.guiLeft + 11, this.guiTop + 79, 94, 57);
                if (possibleRelations.size() > 0) {
                    for (int i2 = 0; i2 < possibleRelations.size(); ++i2) {
                        int offsetX = this.guiLeft + 11;
                        Float offsetY = Float.valueOf((float)(this.guiTop + 79 + i2 * 20) + this.getSlideRelations());
                        ClientEventHandler.STYLE.bindTexture("faction_main");
                        if (mouseX >= offsetX && mouseX <= offsetX + 94 && (float)mouseY >= offsetY.floatValue() && (float)mouseY <= offsetY.floatValue() + 20.0f) {
                            ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.intValue(), 100, 422, 94, 20, 512.0f, 512.0f, false);
                            this.hoveredRelation = (String)possibleRelations.get(i2);
                        } else {
                            ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.intValue(), 101, 363, 94, 20, 512.0f, 512.0f, false);
                        }
                        this.drawScaledString(I18n.func_135053_a((String)("faction.common." + (String)possibleRelations.get(i2))), offsetX + 6, offsetY.intValue() + 5, 0xFFFFFF, 1.0f, false, false);
                    }
                }
                GUIUtils.endGLScissor();
                this.scrollBarRelations.draw(mouseX, mouseY);
            }
            if (((Boolean)factionInfos.get("isOpen")).booleanValue() && !((Boolean)factionInfos.get("playerHasFaction")).booleanValue() && !((Boolean)factionInfos.get("isLeaderInHisCountry")).booleanValue()) {
                this.joinButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
            } else if (((Boolean)factionInfos.get("isInvited")).booleanValue() && !((Boolean)factionInfos.get("isInCountry")).booleanValue() && !((Boolean)factionInfos.get("isLeaderInHisCountry")).booleanValue()) {
                this.joinButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
            } else if (((Boolean)factionInfos.get("isInCountry")).booleanValue() && !((Boolean)factionInfos.get("isLeader")).booleanValue()) {
                this.leaveButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
            }
            if (!((Boolean)factionInfos.get("isLeader")).booleanValue() && !((Boolean)factionInfos.get("isLeaderInHisCountry")).booleanValue() && ((Boolean)factionInfos.get("isForSale")).booleanValue()) {
                this.buyButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
            }
            if ((((Boolean)factionInfos.get("isInCountry")).booleanValue() || factionInfos.containsKey("isOp") && ((Boolean)factionInfos.get("isOp")).booleanValue()) && ((Boolean)factionInfos.get("hasHome")).booleanValue()) {
                this.tpHomeButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
            }
            String[] descriptionWords = ((String)factionInfos.get("description")).replaceAll("\u00a7[0-9a-z]{1}", "").split(" ");
            String line = "";
            int lineNumber = 0;
            for (String descWord : descriptionWords) {
                if (this.field_73886_k.func_78256_a(line + descWord) <= 150) {
                    if (!line.equals("")) {
                        line = line + " ";
                    }
                    line = line + descWord;
                    continue;
                }
                if (lineNumber == 0) {
                    line = "\u00a7o\"" + line;
                }
                this.drawScaledString(line, this.guiLeft + 220, this.guiTop + 55 + lineNumber * 10, 0xFFFFFF, 1.0f, true, true);
                ++lineNumber;
                line = descWord;
            }
            if (lineNumber == 0) {
                line = "\u00a7o\"" + line;
            }
            this.drawScaledString(line + "\"", this.guiLeft + 220, this.guiTop + 55 + lineNumber * 10, 0xFFFFFF, 1.0f, true, true);
            if (this.leaderEntity != null) {
                GUIUtils.startGLScissor(this.guiLeft + 290, this.guiTop + 13, 105, 86);
                GuiInventory.func_110423_a((int)(this.guiLeft + 350), (int)(this.guiTop + 130), (int)60, (float)0.0f, (float)0.0f, (EntityLivingBase)this.leaderEntity);
                GUIUtils.endGLScissor();
                ClientEventHandler.STYLE.bindTexture("faction_main");
                GL11.glEnable((int)3042);
                GL11.glDisable((int)2929);
                GL11.glDepthMask((boolean)false);
                GL11.glBlendFunc((int)770, (int)771);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                GL11.glDisable((int)3008);
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 288, this.guiTop + 20, 182, 280, 39, 80, 512.0f, 512.0f, false);
                GL11.glDisable((int)3042);
                GL11.glEnable((int)2929);
                GL11.glDepthMask((boolean)true);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                GL11.glEnable((int)3008);
                if (this.flagTexture != null) {
                    GL11.glBindTexture((int)3553, (int)this.flagTexture.func_110552_b());
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 290, this.guiTop + 22, 0.0f, 0.0f, 156, 78, 35, 20, 156.0f, 78.0f, false);
                }
            }
            ClientEventHandler.STYLE.bindTexture("faction_main");
            int negativeOffsetX = 0;
            if (Integer.parseInt((String)factionInfos.get("age")) < 2) {
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 105, this.guiTop + 50, 149, 263, 10, 10, 512.0f, 512.0f, false);
                if (mouseX >= this.guiLeft + 105 && mouseX <= this.guiLeft + 105 + 10 && mouseY >= this.guiTop + 50 && mouseY <= this.guiTop + 50 + 10) {
                    this.drawHoveringText(Arrays.asList(I18n.func_135053_a((String)"faction.common.badge.young")), mouseX, mouseY, this.field_73886_k);
                }
                negativeOffsetX += 12;
            }
            if (((Boolean)factionInfos.get("isEmpire")).booleanValue()) {
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 105 - negativeOffsetX, this.guiTop + 50, 160, 251, 10, 10, 512.0f, 512.0f, false);
                if (mouseX >= this.guiLeft + 105 - negativeOffsetX && mouseX <= this.guiLeft + 105 - negativeOffsetX + 10 && mouseY >= this.guiTop + 50 && mouseY <= this.guiTop + 50 + 10) {
                    this.drawHoveringText(Arrays.asList(I18n.func_135053_a((String)"faction.common.badge.empire")), mouseX, mouseY, this.field_73886_k);
                }
                negativeOffsetX += 12;
            } else if (!((String)factionInfos.get("isColony")).isEmpty()) {
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 105 - negativeOffsetX, this.guiTop + 50, 172, 251, 10, 10, 512.0f, 512.0f, false);
                if (mouseX >= this.guiLeft + 105 - negativeOffsetX && mouseX <= this.guiLeft + 105 - negativeOffsetX + 10 && mouseY >= this.guiTop + 50 && mouseY <= this.guiTop + 50 + 10) {
                    if (((Boolean)factionInfos.get("isInCountry")).booleanValue() || factionInfos.get("playerWhoSeeFactionId").equals(factionInfos.get("colonizedByFactionId"))) {
                        this.drawHoveringText(Arrays.asList(I18n.func_135053_a((String)"faction.common.badge.colony") + " " + factionInfos.get("isColony"), I18n.func_135053_a((String)"faction.common.badge.colony_tax") + " " + factionInfos.get("colonyTax") + "%"), mouseX, mouseY, this.field_73886_k);
                    } else {
                        this.drawHoveringText(Arrays.asList(I18n.func_135053_a((String)"faction.common.badge.colony") + " " + factionInfos.get("isColony")), mouseX, mouseY, this.field_73886_k);
                    }
                }
                negativeOffsetX += 12;
            }
            if (((Boolean)factionInfos.get("isTopWarzone")).booleanValue()) {
                ClientEventHandler.STYLE.bindTexture("faction_main");
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 105 - negativeOffsetX, this.guiTop + 50, 173, 263, 10, 10, 512.0f, 512.0f, false);
                if (mouseX >= this.guiLeft + 105 - negativeOffsetX && mouseX <= this.guiLeft + 105 - negativeOffsetX + 10 && mouseY >= this.guiTop + 50 && mouseY <= this.guiTop + 50 + 10) {
                    this.drawHoveringText(Arrays.asList(I18n.func_135053_a((String)"faction.common.badge.warzone")), mouseX, mouseY, this.field_73886_k);
                }
            }
            ClientEventHandler.STYLE.bindTexture("faction_main");
            this.drawScaledString(I18n.func_135053_a((String)"faction.home.infos_level") + " : " + factionInfos.get("level") + "  " + I18n.func_135053_a((String)"faction.home.infos_mmr") + " : " + factionInfos.get("mmr") + "  " + I18n.func_135053_a((String)"faction.home.infos_power") + " : " + factionInfos.get("power") + "/" + factionInfos.get("maxpower") + "  " + I18n.func_135053_a((String)"faction.home.infos_claims") + " : " + factionInfos.get("claims"), this.guiLeft + 257, this.guiTop + 114, 0xB4B4B4, 0.8f, true, false);
            Double infosLength = (double)this.field_73886_k.func_78256_a(I18n.func_135053_a((String)"faction.home.infos_level") + " : " + factionInfos.get("level") + "  " + I18n.func_135053_a((String)"faction.home.infos_mmr") + " : " + factionInfos.get("mmr") + "  " + I18n.func_135053_a((String)"faction.home.infos_power") + " : " + factionInfos.get("power") + "/" + factionInfos.get("maxpower") + "  " + I18n.func_135053_a((String)"faction.home.infos_claims") + " : " + factionInfos.get("claims")) * 0.8;
            if ((double)mouseX >= (double)(this.guiLeft + 257) - infosLength / 2.0 && (double)mouseX <= (double)(this.guiLeft + 257) - infosLength / 2.0 + 40.0 && mouseY >= this.guiTop + 112 && mouseY <= this.guiTop + 120) {
                this.hoveredAction = "level";
            }
            if (Integer.parseInt((String)factionInfos.get("powerboost")) != 0) {
                Double beforePowerLength = (double)this.field_73886_k.func_78256_a(I18n.func_135053_a((String)"faction.home.infos_level") + " : " + factionInfos.get("level") + "  " + I18n.func_135053_a((String)"faction.home.infos_mmr") + " : " + factionInfos.get("mmr") + "  ") * 0.8;
                if ((double)mouseX >= (double)(this.guiLeft + 257) - infosLength / 2.0 + beforePowerLength && (double)mouseX <= (double)(this.guiLeft + 257) - infosLength / 2.0 + beforePowerLength + 65.0 && mouseY >= this.guiTop + 112 && mouseY <= this.guiTop + 120) {
                    ArrayList<String> texts = new ArrayList<String>();
                    if (Integer.parseInt((String)factionInfos.get("powerboost_fixed")) != 0) {
                        texts.add("\u00a77Powerboost: \u00a7f" + factionInfos.get("powerboost_fixed"));
                    }
                    if (Integer.parseInt((String)factionInfos.get("powerboost_real")) > 0) {
                        texts.add("\u00a77Powerboost Warzone: \u00a7f" + factionInfos.get("powerboost_real"));
                    }
                    if (Integer.parseInt((String)factionInfos.get("powerboost_unesco")) > 0) {
                        texts.add("\u00a77Powerboost Unesco: \u00a7f" + factionInfos.get("powerboost_unesco"));
                    }
                    if (!((String)factionInfos.get("powerboost_others")).isEmpty()) {
                        for (String powerBoostInfos : ((String)factionInfos.get("powerboost_others")).split(",")) {
                            if (powerBoostInfos.split("#")[1].contains("-")) {
                                texts.add("\u00a7c" + I18n.func_135053_a((String)"faction.home.tooltip.war_against") + " " + powerBoostInfos.split("#")[0] + ": \u00a74" + powerBoostInfos.split("#")[1]);
                                continue;
                            }
                            texts.add("\u00a7a" + I18n.func_135053_a((String)"faction.home.tooltip.war_against") + " " + powerBoostInfos.split("#")[0] + ": \u00a72" + powerBoostInfos.split("#")[1]);
                        }
                    }
                    toolTipLines = texts;
                }
            }
            String tooltipToDraw = "";
            this.hoveredPlayer = "";
            this.drawScaledString(I18n.func_135053_a((String)"faction.home.players_online") + " (" + ((ArrayList)factionInfos.get("players_online")).size() + ")", this.guiLeft + 131, this.guiTop + 133, 0x191919, 0.85f, false, false);
            if (((ArrayList)factionInfos.get("players_online")).size() > 0) {
                GUIUtils.startGLScissor(this.guiLeft + 132, this.guiTop + 142, 116, 86);
                for (int i3 = 0; i3 < ((ArrayList)factionInfos.get("players_online")).size(); ++i3) {
                    String playerName = ((String)((ArrayList)factionInfos.get("players_online")).get(i3)).split("#")[1];
                    String playerNamePrefix = "";
                    if (playerName.split(" ").length > 1) {
                        playerNamePrefix = playerName.split(" ")[0];
                        playerName = playerName.split(" ")[1];
                    }
                    int offsetX = this.guiLeft + 132;
                    Float offsetY = Float.valueOf((float)(this.guiTop + 142 + i3 * 20) + this.getSlideOnline());
                    if (mouseX > offsetX && mouseX < offsetX + 116 && (float)mouseY > offsetY.floatValue() && (float)mouseY < offsetY.floatValue() + 20.0f) {
                        this.hoveredPlayer = playerName;
                    }
                    ClientEventHandler.STYLE.bindTexture("faction_main");
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.intValue(), 132, 142, 116, 20, 512.0f, 512.0f, false);
                    try {
                        ResourceLocation resourceLocation = AbstractClientPlayer.field_110314_b;
                        resourceLocation = AbstractClientPlayer.func_110311_f((String)playerName);
                        AbstractClientPlayer.func_110304_a((ResourceLocation)resourceLocation, (String)playerName);
                        Minecraft.func_71410_x().field_71446_o.func_110577_a(resourceLocation);
                        this.field_73882_e.func_110434_K().func_110577_a(resourceLocation);
                        GUIUtils.drawScaledCustomSizeModalRect(offsetX + 6 + 10, offsetY.intValue() + 4 + 10, 8.0f, 16.0f, 8, -8, -10, -10, 64.0f, 64.0f);
                        GUIUtils.drawScaledCustomSizeModalRect(offsetX + 6 + 10, offsetY.intValue() + 4 + 10, 40.0f, 16.0f, 8, -8, -10, -10, 64.0f, 64.0f);
                    }
                    catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    this.drawScaledString(playerNamePrefix + " " + playerName, offsetX + 20, offsetY.intValue() + 6, 0xB4B4B4, 0.8f, false, true);
                    ClientEventHandler.STYLE.bindTexture("faction_main");
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 104, offsetY.intValue() + 5, 148, 250, 10, 11, 512.0f, 512.0f, false);
                    if (mouseX < offsetX + 104 || mouseX > offsetX + 104 + 10 || !((float)mouseY >= offsetY.floatValue() + 5.0f) || !((float)mouseY <= offsetY.floatValue() + 5.0f + 11.0f)) continue;
                    if (playerTooltip.containsKey(playerName)) {
                        tooltipToDraw = playerName;
                        continue;
                    }
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionPlayerDataPacket(playerName)));
                    playerTooltip.put(playerName, null);
                }
                GUIUtils.endGLScissor();
            }
            int countOfflinePlayers = ((ArrayList)factionInfos.get("players_offline")).size();
            if (factionInfos.containsKey("count_players_offline")) {
                countOfflinePlayers = ((Double)factionInfos.get("count_players_offline")).intValue();
            }
            this.drawScaledString(I18n.func_135053_a((String)"faction.home.players_offline") + " (" + countOfflinePlayers + ")", this.guiLeft + 260, this.guiTop + 133, 0x191919, 0.85f, false, false);
            if (((ArrayList)factionInfos.get("players_offline")).size() > 0) {
                GUIUtils.startGLScissor(this.guiLeft + 261, this.guiTop + 142, 116, 86);
                for (int i4 = 0; i4 < ((ArrayList)factionInfos.get("players_offline")).size(); ++i4) {
                    String playerName = ((String)((ArrayList)factionInfos.get("players_offline")).get(i4)).split("#")[1];
                    String playerNamePrefix = "";
                    if (playerName.split(" ").length > 1) {
                        playerNamePrefix = playerName.split(" ")[0];
                        playerName = playerName.split(" ")[1];
                    }
                    int offsetX = this.guiLeft + 261;
                    Float offsetY = Float.valueOf((float)(this.guiTop + 142 + i4 * 20) + this.getSlideOffline());
                    if (mouseX > offsetX && mouseX < offsetX + 116 && (float)mouseY > offsetY.floatValue() && (float)mouseY < offsetY.floatValue() + 20.0f) {
                        this.hoveredPlayer = playerName;
                    }
                    ClientEventHandler.STYLE.bindTexture("faction_main");
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.intValue(), 261, 142, 116, 20, 512.0f, 512.0f, false);
                    try {
                        ResourceLocation resourceLocation = AbstractClientPlayer.field_110314_b;
                        resourceLocation = AbstractClientPlayer.func_110311_f((String)playerName);
                        AbstractClientPlayer.func_110304_a((ResourceLocation)resourceLocation, (String)playerName);
                        Minecraft.func_71410_x().field_71446_o.func_110577_a(resourceLocation);
                        this.field_73882_e.func_110434_K().func_110577_a(resourceLocation);
                        GUIUtils.drawScaledCustomSizeModalRect(offsetX + 6 + 10, offsetY.intValue() + 4 + 10, 8.0f, 16.0f, 8, -8, -10, -10, 64.0f, 64.0f);
                        GUIUtils.drawScaledCustomSizeModalRect(offsetX + 6 + 10, offsetY.intValue() + 4 + 10, 40.0f, 16.0f, 8, -8, -10, -10, 64.0f, 64.0f);
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    this.drawScaledString(playerNamePrefix + " " + playerName, offsetX + 20, offsetY.intValue() + 6, 0xB4B4B4, 0.8f, false, true);
                    ClientEventHandler.STYLE.bindTexture("faction_main");
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 104, offsetY.intValue() + 5, 148, 250, 10, 11, 512.0f, 512.0f, false);
                    if (mouseX < offsetX + 104 || mouseX > offsetX + 104 + 10 || !((float)mouseY >= offsetY.floatValue() + 5.0f) || !((float)mouseY <= offsetY.floatValue() + 5.0f + 11.0f)) continue;
                    if (playerTooltip.containsKey(playerName)) {
                        tooltipToDraw = playerName;
                        continue;
                    }
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionPlayerDataPacket(playerName)));
                    playerTooltip.put(playerName, null);
                }
                GUIUtils.endGLScissor();
            }
            if (mouseX > this.guiLeft + 130 && mouseX < this.guiLeft + 255 && mouseY > this.guiTop + 139 && mouseY < this.guiTop + 229) {
                this.scrollBarOnline.draw(mouseX, mouseY);
            }
            if (mouseX > this.guiLeft + 259 && mouseX < this.guiLeft + 384 && mouseY > this.guiTop + 139 && mouseY < this.guiTop + 229) {
                this.scrollBarOffline.draw(mouseX, mouseY);
            }
            ClientEventHandler.STYLE.bindTexture("faction_main");
            for (i = 0; i < TABS.size(); ++i) {
                GuiScreenTab type = TABS.get(i);
                if (type.getClassReferent().equals(SettingsGUI_OLD.class)) {
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 400, this.guiTop + 200, 114, 251, 23, 30, 512.0f, 512.0f, false);
                    GL11.glBlendFunc((int)770, (int)771);
                    GL11.glEnable((int)3042);
                    GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)0.75f);
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 400 + 2, this.guiTop + 200 + 4, 40, 321, 20, 20, 512.0f, 512.0f, false);
                    GL11.glDisable((int)3042);
                    continue;
                }
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                int x = i % 5;
                int y = i / 5;
                if (((Object)((Object)this)).getClass() == type.getClassReferent()) {
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft - 23, this.guiTop + 20 + i * 31, 23, 251, 29, 31, 512.0f, 512.0f, false);
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft - 23 + 4, this.guiTop + 20 + i * 31 + 5, x * 20, 301 + y * 20, 20, 20, 512.0f, 512.0f, false);
                    continue;
                }
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft - 23, this.guiTop + 20 + i * 31, 0, 251, 23, 31, 512.0f, 512.0f, false);
                GL11.glBlendFunc((int)770, (int)771);
                GL11.glEnable((int)3042);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)0.75f);
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft - 23 + 4, this.guiTop + 20 + i * 31 + 5, x * 20, 301 + y * 20, 20, 20, 512.0f, 512.0f, false);
                GL11.glDisable((int)3042);
            }
            if (mapLoaded && !this.relationExpanded) {
                GUIUtils.startGLScissor(this.guiLeft + 11, this.guiTop + 83, 180, 78);
                GL11.glDisable((int)2929);
                this.minimapRenderer.renderMap(this.guiLeft + 12, this.guiTop + 74, mouseX, mouseY, true);
                GL11.glEnable((int)2929);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                ClientEventHandler.STYLE.bindTexture("faction_main");
                ModernGui.drawModalRectWithCustomSizedTextureWithTransparency(this.guiLeft + 11, this.guiTop + 83, 0, 362, 98, 78, 512.0f, 512.0f, false);
                GUIUtils.endGLScissor();
            }
            for (i = 0; i < TABS.size(); ++i) {
                GuiScreenTab type = TABS.get(i);
                if (!type.getClassReferent().equals(SettingsGUI_OLD.class)) {
                    if (mouseX < this.guiLeft - 23 || mouseX > this.guiLeft - 23 + 29 || mouseY < this.guiTop + 20 + i * 31 || mouseY > this.guiTop + 20 + 30 + i * 31) continue;
                    this.drawHoveringText(Arrays.asList(I18n.func_135053_a((String)("faction.tab." + type.getClassReferent().getSimpleName()))), mouseX, mouseY, this.field_73886_k);
                    continue;
                }
                if (mouseX < this.guiLeft + 400 || mouseX > this.guiLeft + 400 + 23 || mouseY < this.guiTop + 200 || mouseY > this.guiTop + 200 + 30) continue;
                this.drawHoveringText(Arrays.asList(I18n.func_135053_a((String)("faction.tab." + type.getClassReferent().getSimpleName()))), mouseX, mouseY, this.field_73886_k);
            }
            if (!toolTipLines.isEmpty()) {
                this.drawHoveringText(toolTipLines, mouseX, mouseY, this.field_73886_k);
            }
            if (!tooltipToDraw.isEmpty()) {
                this.drawTooltip(tooltipToDraw, mouseX, mouseY);
            }
            super.func_73863_a(mouseX, mouseY, par3);
            GL11.glEnable((int)2896);
            RenderHelper.func_74519_b();
        } else if (loaded) {
            Minecraft.func_71410_x().func_71373_a(null);
        }
    }

    private void drawTooltip(String playerName, int mouseX, int mouseY) {
        int mouseXGui = mouseX - this.guiLeft;
        int mouseYGui = mouseY - this.guiTop;
        ArrayList<String> tooltipLines = this.getPlayerTooltip(playerName);
        this.drawHoveringText(tooltipLines, mouseX, mouseY, this.field_73886_k);
    }

    public ArrayList<String> getPlayerTooltip(String playerName) {
        HashMap<String, Object> playerInfo = playerTooltip.get(playerName);
        ArrayList<String> tooltipLines = new ArrayList<String>();
        if (playerInfo != null) {
            if (!((String)playerInfo.get("title")).isEmpty() && !((String)playerInfo.get("title")).contains("no title set")) {
                tooltipLines.add((String)playerInfo.get("title"));
            }
            tooltipLines.add("\u00a7f" + I18n.func_135053_a((String)"faction.home.tooltip.member_since_1") + " " + playerInfo.get("member_days") + " " + I18n.func_135053_a((String)"faction.home.tooltip.member_since_2"));
            tooltipLines.add("\u00a77Power : " + playerInfo.get("power") + "/" + playerInfo.get("maxpower"));
            tooltipLines.add("\u00a78" + I18n.func_135053_a((String)"faction.home.tooltip.salary") + " : " + playerInfo.get("salary") + "$ | " + I18n.func_135053_a((String)"faction.home.tooltip.tax") + " : " + playerInfo.get("tax") + "$");
            if (playerInfo.containsKey("valid_playtime") && Long.parseLong((String)playerInfo.get("valid_playtime")) < 90000L) {
                tooltipLines.add("\u00a7b\u2605 " + I18n.func_135053_a((String)"faction.home.tooltip.playtime"));
            }
            tooltipLines.add("\u00a78----------------------");
            if (playerInfo.get("offline_days").equals("online")) {
                tooltipLines.add("\u00a7a" + I18n.func_135053_a((String)"faction.home.tooltip.online"));
            } else {
                tooltipLines.add("\u00a77" + I18n.func_135053_a((String)"faction.home.tooltip.offline_since_1") + " " + playerInfo.get("offline_days") + " " + I18n.func_135053_a((String)"faction.home.tooltip.offline_since_2"));
            }
        }
        return tooltipLines;
    }

    private float getSlideOnline() {
        return ((ArrayList)factionInfos.get("players_online")).size() > 4 ? (float)(-(((ArrayList)factionInfos.get("players_online")).size() - 4) * 20) * this.scrollBarOnline.getSliderValue() : 0.0f;
    }

    private float getSlideOffline() {
        return ((ArrayList)factionInfos.get("players_offline")).size() > 4 ? (float)(-(((ArrayList)factionInfos.get("players_offline")).size() - 4) * 20) * this.scrollBarOffline.getSliderValue() : 0.0f;
    }

    private float getSlideRelations() {
        return ((List)factionInfos.get("possibleRelations")).size() > 3 ? (float)(-(((List)factionInfos.get("possibleRelations")).size() - 3) * 20) * this.scrollBarRelations.getSliderValue() : 0.0f;
    }

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            for (int i = 0; i < TABS.size(); ++i) {
                GuiScreenTab type = TABS.get(i);
                if (!type.getClassReferent().equals(SettingsGUI_OLD.class)) {
                    if (mouseX < this.guiLeft - 20 || mouseX > this.guiLeft + 3 || mouseY < this.guiTop + 20 + i * 31 || mouseY > this.guiTop + 50 + i * 31) continue;
                    this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                    if (((Object)((Object)this)).getClass() == type.getClassReferent()) continue;
                    try {
                        type.call();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    continue;
                }
                if (mouseX < this.guiLeft + 400 || mouseX > this.guiLeft + 400 + 23 || mouseY < this.guiTop + 200 || mouseY > this.guiTop + 200 + 30) continue;
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                if (((Object)((Object)this)).getClass() == type.getClassReferent()) continue;
                try {
                    type.call();
                    continue;
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (mouseX > this.guiLeft + 385 && mouseX < this.guiLeft + 385 + 9 && mouseY > this.guiTop - 6 && mouseY < this.guiTop - 6 + 10) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a(null);
            } else if (!this.hoveredPlayer.isEmpty() && mouseX > this.guiLeft + 130 && mouseX < this.guiLeft + 130 + 254 && mouseY > this.guiTop + 140 && mouseY < this.guiTop + 140 + 90) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new ProfilGui(this.hoveredPlayer, ""));
            }
            if (factionInfos != null && (((Boolean)factionInfos.get("isInCountry")).booleanValue() || factionInfos.containsKey("isOp") && ((Boolean)factionInfos.get("isOp")).booleanValue()) && mouseX >= this.guiLeft + 10 && mouseX <= this.guiLeft + 10 + 100 && mouseY >= this.guiTop + 165 && mouseY <= this.guiTop + 165 + 20) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionMainTPPacket((String)factionInfos.get("name"))));
                Minecraft.func_71410_x().func_71373_a(null);
            } else if (factionInfos != null && ((Boolean)factionInfos.get("isOpen")).booleanValue() && !((Boolean)factionInfos.get("playerHasFaction")).booleanValue() && !((Boolean)factionInfos.get("isLeaderInHisCountry")).booleanValue() && mouseX >= this.guiLeft + 10 && mouseX <= this.guiLeft + 10 + 100 && mouseY >= this.guiTop + 165 && mouseY <= this.guiTop + 165 + 20) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionMainJoinPacket((String)factionInfos.get("name"))));
            } else if (factionInfos != null && ((Boolean)factionInfos.get("isInvited")).booleanValue() && !((Boolean)factionInfos.get("isInCountry")).booleanValue() && !((Boolean)factionInfos.get("isLeaderInHisCountry")).booleanValue() && mouseX >= this.guiLeft + 10 && mouseX <= this.guiLeft + 10 + 100 && mouseY >= this.guiTop + 165 && mouseY <= this.guiTop + 165 + 20) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionMainJoinPacket((String)factionInfos.get("name"))));
            } else if (factionInfos != null && ((Boolean)factionInfos.get("isInCountry")).booleanValue() && !((Boolean)factionInfos.get("isLeader")).booleanValue() && mouseX >= this.guiLeft + 10 && mouseX <= this.guiLeft + 10 + 100 && mouseY >= this.guiTop + 220 && mouseY <= this.guiTop + 220 + 20) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new LeaveConfirmGui(this));
            } else if (factionInfos != null && ((Boolean)factionInfos.get("isLeader")).booleanValue() && this.empireButton != null && this.empireButton.field_73742_g && mouseX >= this.guiLeft + 10 && mouseX <= this.guiLeft + 10 + 100 && mouseY >= this.guiTop + 220 && mouseY <= this.guiTop + 220 + 20) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new EmpireConfirmGui(this));
            } else if (factionInfos != null && ((Boolean)factionInfos.get("isForSale")).booleanValue() && !((Boolean)factionInfos.get("isLeaderInHisCountry")).booleanValue() && !((Boolean)factionInfos.get("isLeader")).booleanValue() && mouseX >= this.guiLeft + 10 && mouseX <= this.guiLeft + 10 + 100 && mouseY >= this.guiTop + 190 && mouseY <= this.guiTop + 190 + 20) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new BuyCountryConfirmGui(this));
            } else if (factionInfos != null && FactionGui_OLD.hasPermissions("relations") && mouseX >= this.guiLeft + 91 && mouseX <= this.guiLeft + 91 + 19 && mouseY >= this.guiTop + 59 && mouseY <= this.guiTop + 59 + 20) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                this.relationExpanded = !this.relationExpanded;
            } else if (factionInfos != null && !this.hoveredRelation.isEmpty() && mouseX >= this.guiLeft + 10 && mouseX <= this.guiLeft + 10 + 100 && mouseY >= this.guiTop + 78 && mouseY <= this.guiTop + 78 + 59) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                if (this.hoveredRelation.equals("enemy")) {
                    Minecraft.func_71410_x().func_71373_a((GuiScreen)new EnemyConfirmGui(this));
                } else if (this.hoveredRelation.equals("colony")) {
                    Minecraft.func_71410_x().func_71373_a((GuiScreen)new ColonyConfirmGui(this));
                } else {
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionRelationActionPacket((String)factionInfos.get("name"), this.hoveredRelation)));
                }
                this.hoveredRelation = "";
                this.relationExpanded = false;
            } else if (factionInfos != null && mouseX >= this.guiLeft + 8 && mouseX <= this.guiLeft + 8 + 104 && mouseY >= this.guiTop + 250 && mouseY <= this.guiTop + 250 + 19) {
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new RemoteOpenOwnFactionMainPacket()));
                Minecraft.func_71410_x().func_71373_a(null);
            } else if (!this.hoveredAction.isEmpty() && this.hoveredAction.equals("level")) {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new LevelGui(this));
            }
        }
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    public static boolean hasPermissions(String permName) {
        return ((ArrayList)factionInfos.get("permissions")).contains(permName + "##true");
    }

    public static void initTabs() {
        TABS.clear();
        TABS.add(new GuiScreenTab(){

            @Override
            public Class<? extends GuiScreen> getClassReferent() {
                return FactionGui_OLD.class;
            }

            @Override
            public void call() {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new FactionGui_OLD((String)factionInfos.get("name")));
            }
        });
        TABS.add(new GuiScreenTab(){

            @Override
            public Class<? extends GuiScreen> getClassReferent() {
                return BankGUI_OLD.class;
            }

            @Override
            public void call() {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new BankGUI_OLD((EntityPlayer)Minecraft.func_71410_x().field_71439_g, true));
            }
        });
        TABS.add(new GuiScreenTab(){

            @Override
            public Class<? extends GuiScreen> getClassReferent() {
                return DiplomatieGUI_OLD.class;
            }

            @Override
            public void call() {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new DiplomatieGUI_OLD((EntityPlayer)Minecraft.func_71410_x().field_71439_g));
            }
        });
        TABS.add(new GuiScreenTab(){

            @Override
            public Class<? extends GuiScreen> getClassReferent() {
                return WarGUI.class;
            }

            @Override
            public void call() {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new WarGUI_OLD((EntityPlayer)Minecraft.func_71410_x().field_71439_g));
            }
        });
        TABS.add(new GuiScreenTab(){

            @Override
            public Class<? extends GuiScreen> getClassReferent() {
                return PermGUI.class;
            }

            @Override
            public void call() {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new PermGUI((EntityPlayer)Minecraft.func_71410_x().field_71439_g));
            }
        });
        TABS.add(new GuiScreenTab(){

            @Override
            public Class<? extends GuiScreen> getClassReferent() {
                return ActionsGUI.class;
            }

            @Override
            public void call() {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new ActionsGUI((EntityPlayer)Minecraft.func_71410_x().field_71439_g));
            }
        });
        TABS.add(new GuiScreenTab(){

            @Override
            public Class<? extends GuiScreen> getClassReferent() {
                return CountrySkillsGUI_OLD.class;
            }

            @Override
            public void call() {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new CountrySkillsGUI_OLD((EntityPlayer)Minecraft.func_71410_x().field_71439_g));
            }
        });
    }

    public void drawScaledString(String text, int x, int y, int color, float scale, boolean centered, boolean shadow) {
        GL11.glPushMatrix();
        GL11.glScalef((float)scale, (float)scale, (float)scale);
        float newX = x;
        if (centered) {
            newX = (float)x - (float)this.field_73886_k.func_78256_a(text) * scale / 2.0f;
        }
        if (shadow) {
            this.field_73886_k.func_85187_a(text, (int)(newX / scale), (int)((float)(y + 1) / scale), (color & 0xFCFCFC) >> 2 | color & 0xFF000000, false);
        }
        this.field_73886_k.func_85187_a(text, (int)(newX / scale), (int)((float)y / scale), color, false);
        GL11.glPopMatrix();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
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

    public static BufferedImage decodeToImage(String imageString) {
        BufferedImage image = null;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] imageByte = decoder.decodeBuffer(imageString);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    static {
        loaded = false;
        achievementDone = false;
        mapLoaded = false;
    }
}

